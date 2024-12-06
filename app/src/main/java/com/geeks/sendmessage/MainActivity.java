package com.geeks.sendmessage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private Button sendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage = findViewById(R.id.editTextMessage);
        sendMessageButton = findViewById(R.id.sendMessageButton);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();

                if (message.isEmpty()) {
                    editTextMessage.setError("Введите сообщение");
                    return;
                }

                sendEmail(message);

                openSecondActivity(message);
            }
        });
    }

    private void sendEmail(String message) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@example.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Тема сообщения");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(emailIntent, "Выберите почтовое приложение"));
        } else {
            Toast.makeText(this, "Нет приложений для отправки Email", Toast.LENGTH_SHORT).show();
        }
    }

    private void openSecondActivity(String message) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("message_key", message);
        startActivity(intent);
    }
}