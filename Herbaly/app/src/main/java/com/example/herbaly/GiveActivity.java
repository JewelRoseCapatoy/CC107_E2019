package com.example.herbaly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class GiveActivity extends AppCompatActivity {

    ImageButton buttonBack;
    Button buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give);

        buttonDone = findViewById(R.id.btndone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GiveActivity.this,"Thank you for donating!", Toast.LENGTH_SHORT).show();
            }
        });
        buttonBack = findViewById(R.id.btnback);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiveActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}