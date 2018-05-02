package com.example.liuan.test;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mBtOpen;
    private EditText mEtDelay;
    private EditText mEtLike;
    /**
     * 保存配置
     */
    private Button mBtReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String apkRoot="chmod 777 "+getPackageCodePath();
//
//        SystemManager.RootCommand(apkRoot);
        initView();
        initData();

    }

    private void initData() {
        SpUtils.putString(this, "time", mEtDelay.getText().toString().trim());
        SpUtils.putString(this, "like", mEtLike.getText().toString().trim());
        SpUtils.putBoolean(MainActivity.this, "reSet", true);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Toast.makeText(MainActivity.this, "半径", Toast.LENGTH_LONG);
        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
                Toast.makeText(MainActivity.this, "半径", Toast.LENGTH_LONG);
                mBtOpen.setText("已经关闭");
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        mBtOpen = (Button) findViewById(R.id.bt_open);
        mBtOpen.setOnClickListener(this);

        mEtDelay = (EditText) findViewById(R.id.et_delay);
        mEtLike = (EditText) findViewById(R.id.et_like);
        mEtLike.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initData();

            }
        });
        mEtDelay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initData();
            }
        });

        mBtReset = (Button) findViewById(R.id.bt_reset);
        mBtReset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_open:
                initData();
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                break;

            case R.id.bt_reset:
                initData();
                break;
        }
    }
}
