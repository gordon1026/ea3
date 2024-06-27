package com.example.ea3;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Random;

public class gameActivity extends AppCompatActivity {
    private Button homebtn;
    private float plateX;
    private FrameLayout gameArea;
    private TextView scoreText, timerText;
    private int score = 0;
    private ImageView plate;
    private HashMap<ImageView, Boolean> foodMap;
    private Handler handler = new Handler();
    private MediaPlayer mediaPlayer;
    private boolean gameRunning = true;
    SoundPool sp;
    HashMap<Integer, Integer>hm;
    int currStreamId;
    private static final String[] sentences={"Provide nutrients needed by the body","Improve heart health","Maintain weight","Enhance immunity","Boost energy levels"};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initSoungPool();

        gameArea = findViewById(R.id.game_area);
        scoreText = findViewById(R.id.textscore);
        timerText = findViewById(R.id.timer);
        plate = findViewById(R.id.imageplate);

        homebtn = findViewById(R.id.button);
        homebtn.setOnClickListener(v -> {
            Intent intent = new Intent(gameActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.background);
        mediaPlayer.setLooping(true);

        foodMap = new HashMap<>();
        Drawable food1 = ContextCompat.getDrawable(this, R.drawable.food1);
        Drawable food2 = ContextCompat.getDrawable(this, R.drawable.food2);
        Drawable food3 = ContextCompat.getDrawable(this, R.drawable.food3);
        Drawable food4 = ContextCompat.getDrawable(this, R.drawable.food4);

        plate.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    plateX = v.getX() - event.getRawX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.animate().x(event.getRawX() + plateX).setDuration(0).start();
                    break;
                default:
                    return false;
            }
            return true;
        });

        startGame();
    }

    private void initSoungPool() {
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        hm = new HashMap<Integer, Integer>();
        hm.put(1, sp.load(this, R.raw.score,1));
    }

    private void startGame() {
        new CountDownTimer(40000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time: " + millisUntilFinished / 1000);
                mediaPlayer.start();
            }

            public void onFinish() {
                gameRunning = false;
                timerText.setText("Time: 0");
                showGameOverDialog();
                mediaPlayer.stop();
            }
        }.start();

        generateFood();
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Random random = new Random();
        builder.setTitle("Game Over")
                .setMessage("Your final score is: " + score+"\n"+sentences[random.nextInt(sentences.length)])
                .setPositiveButton("Home",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(gameActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("ReStart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void generateFood() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (gameRunning) {
                    addFood();
                    handler.postDelayed(this, 2000); // Generate a food item every 2 seconds
                }
            }
        }, 2000);
    }
    private void addFood() {
        ImageView foodView = new ImageView(this);
        Drawable foodDrawable;
        Random random = new Random();
        int foodType = random.nextInt(4); // Randomly choose between 0, 1, 2, and 3

        switch (foodType) {
            case 0:
                foodDrawable = ContextCompat.getDrawable(this, R.drawable.food1);
                foodMap.put(foodView, true); // food1 is +1
                break;
            case 1:
                foodDrawable = ContextCompat.getDrawable(this, R.drawable.food2);foodMap.put(foodView, true); // food2 is +1
                break;
            case 2:
                foodDrawable = ContextCompat.getDrawable(this, R.drawable.food3);
                foodMap.put(foodView, false); // food3 is -1
                break;
            case 3:
                foodDrawable = ContextCompat.getDrawable(this, R.drawable.food4);
                foodMap.put(foodView, false); // food4 is -1
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + foodType);
        }

        foodView.setImageDrawable(foodDrawable);
        foodView.setLayoutParams(new FrameLayout.LayoutParams(200, 200));

        // 隨機設置食物的 X 位置
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int foodWidth = 200; // 食物的寬度
        int maxX = screenWidth - foodWidth;
        int randomX = random.nextInt(maxX + 1);
        foodView.setX(randomX); // 設置隨機X坐標
        foodView.setY(0);

        foodView.setOnClickListener(v -> {
            if (foodMap.get(foodView)) {
                score++;
            } else {
                score--;
                if (score < 0) score = 0; // 保證分數不為負數
            }
            scoreText.setText("Score: " + score);
            gameArea.removeView(foodView); // 點擊後移除食物
        });

        gameArea.addView(foodView);

        // 開始食物下落動畫
        foodView.animate()
                .translationY(gameArea.getHeight())
                .setDuration(3000)
                .withEndAction(() -> {
                    // 檢查食物是否碰到盤子
                    if (isCollision(foodView, plate)) {
                        if (foodMap.get(foodView)) {
                            playSound(1,0);
                            score++;
                        } else {
                            score--;
                            if (score < 0) score = 0; // 保證分數不為負數
                        }
                        scoreText.setText("Score: " + score);
                    }
                    // 移除食物
                    gameArea.removeView(foodView);
                })
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playSound(int sound, int loop) {
        AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;
        currStreamId = sp.play(hm.get(sound), volume, volume, 1, loop, 1.0f);
    }

    private boolean isCollision(View view1, View view2) {
        int[] view1Location = new int[2];
        int[] view2Location = new int[2];
        view1.getLocationOnScreen(view1Location);
        view2.getLocationOnScreen(view2Location);

        int view1Left = view1Location[0];
        int view1Right = view1Left + view1.getWidth();
        int view1Top = view1Location[1];
        int view1Bottom = view1Top + view1.getHeight();

        int view2Left = view2Location[0];
        int view2Right = view2Left + view2.getWidth();
        int view2Top = view2Location[1];
        int view2Bottom = view2Top + view2.getHeight();

        return view1Left < view2Right && view1Right > view2Left &&
                view1Top < view2Bottom && view1Bottom > view2Top;
    }
}
