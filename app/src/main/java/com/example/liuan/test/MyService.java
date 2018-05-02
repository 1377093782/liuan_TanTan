package com.example.liuan.test;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MyService extends AccessibilityService {
    private static final String TAG = "MyService";
    // 大多数的手机包名一样，联想部分机型的手机不一样
    private String[] packageNames = {"com.p1.mobile.putong"};
    int startFlag = 0;

    AccessibilityNodeInfo[] noteInfo = new AccessibilityNodeInfo[2];



    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() != null) {
            findNodesByText(event, "继续探索");
        }

        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:

                if (SpUtils.getBoolean(this, "reSet", false)) {
                    SpUtils.putBoolean(this, "reSet", false);
                    startFlag = 0;
                }

                if (startFlag == 0) {
                    //记录正确
                    startFlag = 1;
                    noteInfo[0] = event.getSource();
                } else if (startFlag == 1) {
                    //记录错误  只记录一次即可
                    startFlag = 2;
                    noteInfo[1] = event.getSource();
                }

                if (startFlag == 2) {
                    autoClick();
                }


                break;


        }

    }

    private void autoClick() {
        try {
            String time = SpUtils.getString(this, "time", "1000");
            Thread.sleep(Integer.parseInt(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String time = SpUtils.getString(this, "like", "50");
        int percent = Integer.parseInt(time);
//随机数字 是1-100
        int random = new Random().nextInt(100) + 1;
//如果设置了百分之50  那么就是各一半
//如果设置了百分之10  那么就是点第一个 8   10
        Log.e(TAG, "onAccessibilityEvent:random " + random);
        Log.e(TAG, "onAccessibilityEvent:percent " + percent);
        if (random <= percent) {
            //第一次按键
            noteInfo[0].performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            noteInfo[1].performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }

    }


    @Override
    public void onInterrupt() {
        startFlag = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startFlag = 0;
    }

    private void findNodesByText(AccessibilityEvent event, String text) {
        List<AccessibilityNodeInfo> nodes = event.getSource().findAccessibilityNodeInfosByText(text);

        if (nodes != null && !nodes.isEmpty()) {
            for (AccessibilityNodeInfo info : nodes) {
                if (info.isClickable()) {// 只有根据节点信息是下一步，安装，完成，打开，且是可以点击的时候，才执行后面的点击操作
                    if ("继续探索".equals(text)) {
                        info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
    }

    private boolean isTextExist(AccessibilityEvent event, String text) {
        List<AccessibilityNodeInfo> nodes = event.getSource().findAccessibilityNodeInfosByText(text);

        if (nodes != null && !nodes.isEmpty()) {
            for (AccessibilityNodeInfo info : nodes) {
                if (info.isClickable()) {// 只有根据节点信息是下一步，安装，完成，打开，且是可以点击的时候，才执行后面的点击操作
                    if ("继续探索".equals(text)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

}
