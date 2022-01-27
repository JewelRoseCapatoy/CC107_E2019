package com.example.herbaly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


        EditText inputEmail, inputPassword;
        Button buttonLogin, buttonregisterhere;
        TextView textCreateAccount;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            textCreateAccount = findViewById(R.id.lnkRegister);


            inputEmail = findViewById(R.id.txtloginEmail);

            inputPassword = findViewById(R.id.txtloginPwd);

            buttonLogin = findViewById(R.id.btnlogin);
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputEmail.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    }else if (inputPassword.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    } else {
                        login();
                    }
                }
            });

            buttonregisterhere = (Button) findViewById(R.id.btnregisterhere);
            buttonregisterhere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });


        }

        public static void hideSoftKeyboard(Activity activity) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }


        private void login() {

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Logging in...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
            Call<LoginResponseModel> login = networkService.login(inputEmail.getText().toString(), inputPassword.getText().toString());
            login.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponseModel> call, @NonNull Response<LoginResponseModel> response) {
                    LoginResponseModel responseBody = response.body();
                    if (responseBody != null) {
                        if (responseBody.getSuccess().equals("1")) {
                            SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(Constants.KEY_ISE_LOGGED_IN, true);
                            editor.putString(Constants.KEY_USERNAME, responseBody.getUserDetailObject().getUserDetails().get(0).getFirstName() + " " + responseBody.getUserDetailObject().getUserDetails().get(0).getLastName());
                            editor.putString(Constants.KEY_EMAIL, responseBody.getUserDetailObject().getUserDetails().get(0).getEmail());
                            editor.apply();
                            Toast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponseModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }
}