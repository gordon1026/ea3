package com.example.ea3;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TestSepCounter extends AppCompatActivity implements SensorEventListener {

    private TextView steps;
    private TextView rewardMessage;
    private TextView calories;
    private EditText stepGoalInput;
    private Button setGoalButton;
    private RecyclerView rvMedals;

    private SensorManager mSensorManager;
    private Sensor stepSensor;

    private int totalSteps = 0;
    private int previousTotalSteps = 0;
    private int stepGoal = 10000; // 默认步数目标
    private static final double CALORIES_PER_STEP = 0.045; // 每一步消耗的卡路里（假设值）

    private List<Medal> medals = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private MedalAdapter medalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teststepcounter);

        // 初始化UI组件
        steps = findViewById(R.id.tv_step_count);
        rewardMessage = findViewById(R.id.tv_show_calories);
        calories = findViewById(R.id.tv_label_calories);
        stepGoalInput = findViewById(R.id.et_step_goal);
        setGoalButton = findViewById(R.id.btn_set_goal);
        //rvMedals = findViewById(R.id.rv_medals);

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalInput = stepGoalInput.getText().toString();
                if (!goalInput.isEmpty()) {
                    stepGoal = Integer.parseInt(goalInput);
                    saveData(); // 保存新的步数目标
                    Toast.makeText(TestSepCounter.this, "步数目标已更新！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestSepCounter.this, "请输入有效的步数目标", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 初始化传感器
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (mSensorManager != null) {
            stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences("stepCounter", Context.MODE_PRIVATE);

        // 加载保存的数据
        loadData();
        loadMedals();

        // 设置RecyclerView
        rvMedals.setLayoutManager(new LinearLayoutManager(this));
        medalAdapter = new MedalAdapter(medals);
        rvMedals.setAdapter(medalAdapter);

        resetSteps(); // 设置重置步数的监听器

        // 初始化步数显示
        steps.setText(String.valueOf(0));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (stepSensor == null) {
            Toast.makeText(this, "This device has no step sensor", Toast.LENGTH_SHORT).show();
        } else {
            mSensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            totalSteps = (int) event.values[0];
            int currentSteps = totalSteps - previousTotalSteps;
            steps.setText(String.valueOf(currentSteps));

            // 计算卡路里消耗
            double caloriesBurned = currentSteps * CALORIES_PER_STEP;
            calories.setText(String.format("Calories: %.2f", caloriesBurned));

            // 检查并授予奖牌
            checkAndAwardMedals(currentSteps, caloriesBurned);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 这里可以处理传感器精度变化事件，目前不需要特殊处理
    }

    private void resetSteps() {
        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestSepCounter.this, "长按以重置步数", Toast.LENGTH_SHORT).show();
            }
        });

        steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                previousTotalSteps = totalSteps;
                steps.setText("0");
                saveData(); // 保存重置后的步数
                return true;
            }
        });
    }

    private void checkAndAwardMedals(int currentSteps, double caloriesBurned) {
        boolean newMedalAwarded = false;

        if (currentSteps >= stepGoal && !isMedalAwarded("达成目标步数")) {
            rewardMessage.setText("恭喜你达到了目标步数！");
            rewardMessage.setVisibility(View.VISIBLE);
            medals.add(new Medal("达成目标步数", "你已经达到了目标步数！"));
            newMedalAwarded = true;
        }

        if (caloriesBurned >= 500 && !isMedalAwarded("500卡路里")) {
            medals.add(new Medal("500卡路里", "你已经燃烧了500卡路里！"));
            newMedalAwarded = true;
        }

        if (caloriesBurned >= 1000 && !isMedalAwarded("1000卡路里")) {
            medals.add(new Medal("1000卡路里", "你已经燃烧了1000卡路里！"));
            newMedalAwarded = true;
        }

        if (newMedalAwarded) {
            medalAdapter.notifyDataSetChanged();
            saveMedals();
        }

        // 隐藏奖励消息
        rewardMessage.postDelayed(new Runnable() {
            @Override
            public void run() {
                rewardMessage.setVisibility(View.GONE);
            }
        }, 2000); // 2秒后隐藏
    }

    private boolean isMedalAwarded(String title) {
        for (Medal medal : medals) {
            if (medal.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("previousTotalSteps", previousTotalSteps);
        editor.putInt("stepGoal", stepGoal);
        editor.apply();
    }

    private void loadData() {
        previousTotalSteps = sharedPreferences.getInt("previousTotalSteps", 0);
        stepGoal = sharedPreferences.getInt("stepGoal", 10000);
    }

    private void saveMedals() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medals);
        editor.putString("medals", json);
        editor.apply();
    }

    private void loadMedals() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("medals", null);
        Type type = new TypeToken<ArrayList<Medal>>() {}.getType();
        medals = gson.fromJson(json, type);

        if (medals == null) {
            medals = new ArrayList<>();
        }
    }
}