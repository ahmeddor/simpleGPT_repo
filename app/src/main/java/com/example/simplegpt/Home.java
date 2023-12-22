package com.example.simplegpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {

    ImageButton chatGpt,dallyE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        chatGpt = findViewById(R.id.chatGpt);
        dallyE = findViewById(R.id.dallyE);
        chatGpt.setOnClickListener((v)->{
            openChat();
        });
        dallyE.setOnClickListener((v)->{
            openDall();
        });
    }
    public void openChat() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openDall() {
        Intent intent = new Intent(this, simpleDally.class);
        startActivity(intent);
    }
}
