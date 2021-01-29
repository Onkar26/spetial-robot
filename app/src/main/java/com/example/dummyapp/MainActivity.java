package com.example.dummyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    EditText input;
    Button start;
    MediaPlayer ring;
    MediaPlayer ringEnd;
    int sec;

    public void play(View view){
        if(input.getText() != null) sec = Integer.parseInt(input.getText().toString());
        start.setEnabled(false);
        startTimer();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.textView);
        input = findViewById(R.id.editTextNumber);
        start = findViewById(R.id.button);
        ring = MediaPlayer.create(this,R.raw.notification);
        ringEnd = MediaPlayer.create(this,R.raw.short_notification);
    }

    public void startTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int h = sec / 3600;
                int m = (sec % 3600) / 60;
                int s = sec % 60;
                String newText = String.format(Locale.getDefault(), "%d:%02d:%02d", h, m, s);
                timer.setText(newText);
                if (sec <= 0) {
                    ring.stop();
                    ringEnd.start();
                    start.setEnabled(true);
                    return;
                } else {
                    ring.seekTo(100);
                    ring.start();
                }
                sec--;

                handler.postDelayed(this, 1000);
            }
        });
    }
}
