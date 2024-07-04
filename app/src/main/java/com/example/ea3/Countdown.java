package com.example.ea3;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;

public class Countdown extends AppCompatActivity {

    private NumberPicker pickerHours, pickerMinutes, pickerSeconds;
    private TextView stopwatchTextView;
    private Button startButton, resetButton, toggleModeButton;
    private boolean isTimerMode = true;
    private boolean isRunning = false;
    private Handler handler = new Handler();
    private long startTime, elapsedTime;
    private MediaPlayer mediaPlayer;
    private ImageView btnBackTo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown_main);

        pickerHours = findViewById(R.id.pickerHours);
        pickerMinutes = findViewById(R.id.pickerMinutes);
        pickerSeconds = findViewById(R.id.pickerSeconds);
        stopwatchTextView = findViewById(R.id.stopwatchTextView);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        toggleModeButton = findViewById(R.id.toggleModeButton);
        btnBackTo = findViewById(R.id.BackTo);

        pickerHours.setMaxValue(23);
        pickerHours.setMinValue(0);
        pickerMinutes.setMaxValue(59);
        pickerMinutes.setMinValue(0);
        pickerSeconds.setMaxValue(59);
        pickerSeconds.setMinValue(0);

        btnBackTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        startButton.setOnClickListener(view -> {
            if (isTimerMode) {
                if (isRunning) {
                    stopTimer();
                } else {
                    startTimer();
                }
            } else {
                if (isRunning) {
                    stopStopwatch();
                } else {
                    startStopwatch();
                }
            }
        });

        resetButton.setOnClickListener(view -> {
            if (isTimerMode) {
                resetTimer();
            } else {
                resetStopwatch();
            }
        });

        toggleModeButton.setOnClickListener(view -> {
            isTimerMode = !isTimerMode;
            updateUIForMode();
        });

        updateUIForMode();
    }

    private void startTimer() {
        int hours = pickerHours.getValue();
        int minutes = pickerMinutes.getValue();
        int seconds = pickerSeconds.getValue();
        long totalMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

        startTime = System.currentTimeMillis();
        elapsedTime = totalMillis;

        handler.post(timerRunnable);
        isRunning = true;
        startButton.setText("Pause");
    }

    private void stopTimer() {
        handler.removeCallbacks(timerRunnable);
        isRunning = false;
        startButton.setText("Start");
    }

    private void resetTimer() {
        stopTimer();
        pickerHours.setValue(0);
        pickerMinutes.setValue(0);
        pickerSeconds.setValue(0);
    }

    private void startStopwatch() {
        startTime = System.currentTimeMillis() - elapsedTime;
        handler.post(stopwatchRunnable);
        isRunning = true;
        startButton.setText("Pause");
    }

    private void stopStopwatch() {
        handler.removeCallbacks(stopwatchRunnable);
        isRunning = false;
        startButton.setText("Start");
    }

    private void resetStopwatch() {
        stopStopwatch();
        elapsedTime = 0;
        updateStopwatchText();
    }

    private void updateUIForMode() {
        if (isTimerMode) {
            pickerHours.setVisibility(View.VISIBLE);
            pickerMinutes.setVisibility(View.VISIBLE);
            pickerSeconds.setVisibility(View.VISIBLE);
            stopwatchTextView.setVisibility(View.GONE);
        } else {
            pickerHours.setVisibility(View.GONE);
            pickerMinutes.setVisibility(View.GONE);
            pickerSeconds.setVisibility(View.GONE);
            stopwatchTextView.setVisibility(View.VISIBLE);
            resetStopwatch(); // Reset stopwatch when switching to stopwatch mode
        }
    }

    private void updateStopwatchText() {
        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        stopwatchTextView.setText(timeString);
    }

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime -= currentTime - startTime;
            startTime = currentTime;

            if (elapsedTime <= 0) {
                stopTimer();
                resetTimer();
                playSound(); // 只在倒計時結束時播放音響
                return;
            }

            int seconds = (int) (elapsedTime / 1000) % 60;
            int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
            int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);

            pickerHours.setValue(hours);
            pickerMinutes.setValue(minutes);
            pickerSeconds.setValue(seconds);

            handler.postDelayed(this, 1000);
        }
    };

    private final Runnable stopwatchRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
            updateStopwatchText();
            handler.postDelayed(this, 1000);
        }
    };

    private void playSound() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.mp3);
        }
        mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}