package com.example.theappguruz.phonestatereceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

//import com.internal.android.telephony.ITelephony;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
    }

    public void onEndCall(View view) {

        disconnectCall();

        finish();


//        Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
//        buttonUp.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(
//                KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
//        sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");

//        Intent headSetUnPluggedintent = new Intent(Intent.ACTION_HEADSET_PLUG);
//        headSetUnPluggedintent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
//        headSetUnPluggedintent.putExtra("state", 0);
//        headSetUnPluggedintent.putExtra("name", "Headset");
//        try {
//            sendOrderedBroadcast(headSetUnPluggedintent, null);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        Class clazz = null;
//        try {
//            clazz = Class.forName(telephonyManager.getClass().getName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Method method = null;
//        try {
//            method = clazz.getDeclaredMethod("getITelephony");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        method.setAccessible(true);
//        ITelephony telephonyService = null;
//        try {
//            telephonyService = (ITelephony) method.invoke(telephonyManager);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        telephonyService.endCall();

    }

    public void disconnectCall() {
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("",
                    "FATAL ERROR: could not connect to telephony subsystem");
            Log.e("", "Exception object: " + e);
        }
    }
}
