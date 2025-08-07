package com.example.robotarmcontrolpanel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar1, seekBar2, seekBar3, seekBar4;
    private TextView value1, value2, value3, value4;
    private Button saveBtn, loadBtn, resetBtn;

    // Change this to match your server IP or domain
    private final String SERVER_URL = "http://10.0.2.2/robot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar1 = findViewById(R.id.seekBar1);
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar4 = findViewById(R.id.seekBar4);

        value1 = findViewById(R.id.value1);
        value2 = findViewById(R.id.value2);
        value3 = findViewById(R.id.value3);
        value4 = findViewById(R.id.value4);

        saveBtn = findViewById(R.id.btnSave);
        loadBtn = findViewById(R.id.btnLoad);
        resetBtn = findViewById(R.id.btnReset);

        setupSeekBars();

        saveBtn.setOnClickListener(v -> savePose());
        loadBtn.setOnClickListener(v -> loadPose());
        resetBtn.setOnClickListener(v -> resetPose());
    }

    private void setupSeekBars() {
        setupSeekBar(seekBar1, value1);
        setupSeekBar(seekBar2, value2);
        setupSeekBar(seekBar3, value3);
        setupSeekBar(seekBar4, value4);
    }

    private void setupSeekBar(SeekBar seekBar, TextView value) {
        seekBar.setMax(180);
        seekBar.setProgress(90);
        value.setText("90");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value.setText(String.valueOf(progress));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void savePose() {
        String url = SERVER_URL + "/save_pose.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Pose Saved!", Toast.LENGTH_SHORT).show();
                    Log.d("RobotApp", "Response: " + response);
                },
                error -> {
                    Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
                    Log.e("RobotApp", "Error: " + error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("motor1", String.valueOf(seekBar1.getProgress()));
                params.put("motor2", String.valueOf(seekBar2.getProgress()));
                params.put("motor3", String.valueOf(seekBar3.getProgress()));
                params.put("motor4", String.valueOf(seekBar4.getProgress()));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void loadPose() {
        String url = SERVER_URL + "/get_run_pose.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);

                        int m1 = json.getInt("motor1");
                        int m2 = json.getInt("motor2");
                        int m3 = json.getInt("motor3");
                        int m4 = json.getInt("motor4");

                        seekBar1.setProgress(m1);
                        seekBar2.setProgress(m2);
                        seekBar3.setProgress(m3);
                        seekBar4.setProgress(m4);

                        value1.setText(String.valueOf(m1));
                        value2.setText(String.valueOf(m2));
                        value3.setText(String.valueOf(m3));
                        value4.setText(String.valueOf(m4));

                        Toast.makeText(this, "Pose Loaded!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("RobotApp", "Parse Error: " + e.getMessage());
                        Toast.makeText(this, "Load Failed", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Load Error", Toast.LENGTH_SHORT).show();
                    Log.e("RobotApp", "Error: " + error.toString());
                });

        Volley.newRequestQueue(this).add(request);
    }

    private void resetPose() {
        String url = SERVER_URL + "/update_status.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    seekBar1.setProgress(0);
                    seekBar2.setProgress(0);
                    seekBar3.setProgress(0);
                    seekBar4.setProgress(0);

                    value1.setText("0");
                    value2.setText("0");
                    value3.setText("0");
                    value4.setText("0");

                    Toast.makeText(this, "Pose Reset!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "Reset Failed", Toast.LENGTH_SHORT).show();
                    Log.e("RobotApp", "Error: " + error.toString());
                });

        Volley.newRequestQueue(this).add(request);
    }
}