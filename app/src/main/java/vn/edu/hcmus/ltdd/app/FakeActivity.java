package vn.edu.hcmus.ltdd.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import vn.edu.hcmus.ltdd.receiver.AlarmReceiver;
import vn.edu.hcmus.ltdd.utils.AppUtils;

/**
 * Created by mac on 7/8/14.
 */
@EActivity(R.layout.fake_layout)
public class FakeActivity extends Activity {
    private String newId;
    private int size;
    private boolean writable;
    private String type;
    private String[] techList;
    private Camera camera;
    private Camera.Parameters params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the camera
        getCamera();
        Intent intent = getIntent();
        String action = intent.getAction();
        if (action.equals("android.nfc.action.TECH_DISCOVERED")) {
            Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String id = AppUtils.getNfcTagId(this);
            newId = bytesToHexString(tag.getId());
            Ndef ndef = Ndef.get(tag);
            size = ndef.getMaxSize();
            writable = ndef.isWritable();
            type = ndef.getType();
            techList = tag.getTechList();
            if (id.equals(newId)) {
                // Doing task store in share preferences
                // If There's no task -> Require user to add one
                if (checkTask()) {
                    Toast.makeText(this, R.string.task_success, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        } else {
            goToStartActivity();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // on pause turn off the flash
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        turnOnFlash();
    }

    // Turning On flash
    private void turnOnFlash() {
        if (camera == null || params == null) {
            return;
        }

        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    // Turning Off flash
    private void turnOffFlash() {
        if (camera == null || params == null) {
            return;
        }
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
    }

    // CHECK IF EXISTS TASK
    private boolean checkTask() {
        // CHECK ON OFF
        boolean flag = false;
        String[] title = getResources().getStringArray(R.array.title_task);
        for (int i = 0; i < title.length; i++) {
            if (i == 0 || i == 1 || i == 2 || i == 5 || i == 7) {
                if (!AppUtils.getOnOff(this, title[i]).equals("")) {
                    flag = true;
                    doingOnOffTask(i, AppUtils.getOnOff(this, title[i]));
                }
            }
        }
        // ALARM
        if (AppUtils.getAlarm(this) != 0 && AppUtils.getAlarm(this) > System.currentTimeMillis()) {
            flag = true;
            startAlarmService();
        } else if (AppUtils.getAlarm(this) == 0)
            stopAlarmService();

        // SOUND PROFILE
        if (!AppUtils.getSound(this).equals("")) {
            flag = true;
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (AppUtils.getSound(this).equals(getResources().getStringArray(R.array.sound_profile)[0])) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            } else if (AppUtils.getSound(this).equals(getResources().getStringArray(R.array.sound_profile)[1])) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            } else if (AppUtils.getSound(this).equals(getResources().getStringArray(R.array.sound_profile)[2])) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        }

        // CHANGE BRIGHTNESS
        if (AppUtils.getBrightnessValue(this) != -1) {
            flag = true;
            changeBrightness(AppUtils.getBrightnessValue(this));
        }
        // SEND SMS
        if (AppUtils.getSms(this).size() > 0) {
            flag = true;
            sendSms(AppUtils.getSms(this).get(0), AppUtils.getSms(this).get(1));
        }
        // OPEN URL
        if (!AppUtils.getUrl(this).equals("")) {
            flag = true;
            Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(AppUtils.getUrl(this)));
            startActivity(browser);
        }
        // SEND MAIL
        if (AppUtils.getMail(this).size() > 0) {
            flag = true;
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{AppUtils.getMail(this).get(2)});
            i.putExtra(Intent.EXTRA_SUBJECT, AppUtils.getMail(this).get(0));
            i.putExtra(Intent.EXTRA_TEXT   , AppUtils.getMail(this).get(1));
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, getString(R.string.no_gmail), Toast.LENGTH_SHORT).show();
            }
        }
        // ADD CONTACT

        // CALL PHONE
        if (!AppUtils.getPhone(this).equals("")) {
            flag = true;
            Intent callPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + AppUtils.getPhone(this)));
            startActivity(callPhone);
        }
        // OPEN MAP
        if (!AppUtils.getMapAddress(this).equals("")) {
            flag = true;
            String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", AppUtils.getMapAddress(this));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }

        // OPEN APP
        if (!AppUtils.getApp(this).equals("")) {
            flag = true;
            Intent lauchApp = getPackageManager().getLaunchIntentForPackage(AppUtils.getApp(this));
            startActivity(lauchApp);
        }

        if (flag)
            return true;
        else
            return false;
    }

    private void stopAlarmService() {
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        am.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private void startAlarmService() {
        Intent intent = new Intent(this, RingAlarm_.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        ((AlarmManager) getSystemService(ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, AppUtils.getAlarm(this), pendingIntent);
    }

    private void sendSms(String phoneNumberReceiver, String message) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");

        smsIntent.putExtra("address"  , message);
        smsIntent.putExtra("sms_body", phoneNumberReceiver);
        startActivity(smsIntent);
    }

    private void changeBrightness(int value) {
        Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, value);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = (float) value/100;// 100 / 100.0f;
        getWindow().setAttributes(lp);
        goToStartActivity();
    }

    private void doingOnOffTask(int type, String state) {
        if (type == 0)
            turnOnOffWifi(state);
        if (type == 1)
            turnOnOffData(state);
        if (type == 2)
            turnOnOffBluetooth(state);
        if (type == 5)
            turnOnOffFlash(state);
        if (type == 7)
            turnOnOffAirplaneMode(state);
    }

    // DOING TASK
    private void turnOnOffWifi(String state) {
        WifiManager wifiManager = (WifiManager) this.getSystemService(WIFI_SERVICE);
        if (state.equals(getString(R.string.on)))
            wifiManager.setWifiEnabled(true);
        else
            wifiManager.setWifiEnabled(false);
    }

    private void turnOnOffBluetooth(String state) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (state.equals(getString(R.string.on)))
            bluetoothAdapter.enable();
        else
            bluetoothAdapter.disable();
    }

    private void turnOnOffData(String state) {
        final ConnectivityManager conman = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Class conmanClass = null;
        try {
            conmanClass = Class.forName(conman.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field iConnectivityManagerField = null;
        try {
            iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        iConnectivityManagerField.setAccessible(true);
        Object iConnectivityManager = null;
        try {
            iConnectivityManager = iConnectivityManagerField.get(conman);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class iConnectivityManagerClass = null;
        try {
            iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method setMobileDataEnabledMethod = null;
        try {
            setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        setMobileDataEnabledMethod.setAccessible(true);

        if (state.equals(getString(R.string.on))) {
            try {
                setMobileDataEnabledMethod.invoke(iConnectivityManager, true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                setMobileDataEnabledMethod.invoke(iConnectivityManager, false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void turnOnOffAirplaneMode(String state) {
        Toast.makeText(this, R.string.airplane_sorry, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        if (state.equals(R.string.on))
//            intent.putExtra("state", true);
//        else
//            intent.putExtra("state", false);
//        sendBroadcast(intent);
    }

    // Get the camera
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }

    private void turnOnOffFlash(String state) {
        if (state.equals(getString(R.string.on))) {
            turnOnFlash();
        } else {
            turnOffFlash();
        }
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();
    }

    @Click(R.id.btnCancel)
    protected void btnCancelWasClicked() {
        finish();
    }

    private void goToStartActivity() {
        Intent intent = new Intent(this, StartActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Click(R.id.btnOk)
    protected void btnOkWasClicked() {
        // Store new NFC Tag
        AppUtils.storeNewNfcTag(this, newId, size, writable, type, techList);
        goToStartActivity();
        finish();
    }
}
