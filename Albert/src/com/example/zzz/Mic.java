package com.example.zzz;

import org.roboid.robot.Device;
import org.smartrobot.android.action.Action;

import android.app.Activity;
import android.os.Bundle;

public class Mic extends Activity
{
    private Action mAction;
     private Device mSensitivityDevice;

     @Override
     public void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.screen_mic);

         mAction = Action.obtain(this, Action.WalkieTalkie.ID);
         mSensitivityDevice = mAction.findDeviceById(Action.WalkieTalkie.EFFECTOR_SENSITIVITY);
         mSensitivityDevice.write(30); // 감도 값을 쓴다.
         mAction.activate();
     }

     @Override
     public void onDestroy()
     {
         super.onDestroy();

         mAction.deactivate();
         mAction.dispose();
     }
 }