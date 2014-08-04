package vn.edu.hcmus.ltdd.app;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.edu.hcmus.ltdd.utils.AppUtils;

/**
 * Created by mac on 7/25/14.
 */
@Fullscreen
@NoTitle
@EActivity(R.layout.ring_alarm)
public class RingAlarm extends Activity {
    MediaPlayer mp = null;

    @ViewById
    Button stopAlarm;
    @ViewById
    TextView lblTime;

    @AfterViews
    protected void initRingAlarm() {
        SimpleDateFormat format = AppUtils.getShowDateFormatter();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(AppUtils.getAlarm(this));
        lblTime.setText(format.format(cal.getTime()));
        mp = MediaPlayer.create(getBaseContext(), R.raw.audio);
        playSound(getAlarmUri());
    }

    @Background
    protected void playSound(Uri alert) {
        mp.start();
        mp.setLooping(true);
    }

    @Click(R.id.stopAlarm)
    protected void stopAlarmWasClicked() {
        mp.stop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    private Uri getAlarmUri() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null)
                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
        return alert;
    }
}
