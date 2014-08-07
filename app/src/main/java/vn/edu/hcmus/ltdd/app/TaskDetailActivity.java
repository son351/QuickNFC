package vn.edu.hcmus.ltdd.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Locale;

import vn.edu.hcmus.ltdd.utils.AppUtils;

/**
 * Created by mac on 6/25/14.
 */
@EActivity(R.layout.task_detail_activity)
public class TaskDetailActivity extends Activity {
    private int type;
    private Switch switchOnOff;
    private SeekBar seekBarBrightness;
    private Spinner spinnerSoundProfile;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText editPackageName;
    private boolean checked;

    @ViewById
    LinearLayout write_frame;
    @ViewById
    LinearLayout layout_main;
    @ViewById
    ImageView icon;
    @ViewById
    TextView lbl_content;

    @AfterViews
    protected void initActivity() {
        Bundle data = getIntent().getExtras();
        type = data.getInt("POSITION");
        lbl_content.setText(data.getString("CONTENT"));
        Locale l = Locale.getDefault();
        getActionBar().setTitle(data.getString("TITLE").toUpperCase(l));
        icon.setImageDrawable(getResources().obtainTypedArray(R.array.icon_task).getDrawable(type));

        // INFLATE LAYOUT
        if (type == 0 || type == 1 || type == 2) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.on_off_layout, null);
            switchOnOff = (Switch) v.findViewById(R.id.switchOnOff);
            switchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    checked = isChecked;
                }
            });
            write_frame.addView(v);
        } else if (type == 3) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.display_layout, null);
            seekBarBrightness = (SeekBar) v.findViewById(R.id.seekBarBrightness);
            write_frame.addView(v);
        } else if (type == 4) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.sound_profile_layout, null);
            spinnerSoundProfile = (Spinner) v.findViewById(R.id.spinnerSoundProfile);
            ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sound_profile));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSoundProfile.setAdapter(adapter);
            write_frame.addView(v);
        } else if (type == 5) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.alarm_layout, null);
            datePicker = (DatePicker) v.findViewById(R.id.datePicker);
            timePicker = (TimePicker) v.findViewById(R.id.timePicker);
            write_frame.addView(v);
        } else if (type == 6) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.app_layout, null);
            editPackageName = (EditText) v.findViewById(R.id.editPackageName);
            Button btnChooseApp = (Button) v.findViewById(R.id.btnChooseApp);
            btnChooseApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TaskDetailActivity.this, InstalledAppActivity_.class);
                    startActivityForResult(intent, 1);
                }
            });
            write_frame.addView(v);
        }
    }

    protected void goToStartActivity() {
        Intent intent = new Intent(this, StartActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Click(R.id.btn_ok)
    protected void btn_okWasClicked() {
        if (type == 0 || type == 1 || type == 2) {
            String title = getResources().getStringArray(R.array.title_task)[type];
            String checkedSwitch;
            if (checked) {
                checkedSwitch = getString(R.string.on);
            } else {
                checkedSwitch = getString(R.string.off);
            }
            AppUtils.storeOnOff(this,title,checkedSwitch);
        } else if (type == 3) {
            AppUtils.storeBrightnessValue(this, seekBarBrightness.getProgress());
        } else if (type == 4) {
            AppUtils.storeSound(this, spinnerSoundProfile.getSelectedItem().toString());
        } else if (type == 5) {
            Calendar cal = Calendar.getInstance();
            cal.set(datePicker.getYear(),datePicker.getMonth(),
                    datePicker.getDayOfMonth(),timePicker.getCurrentHour(),timePicker.getCurrentMinute(),0);
            AppUtils.storeAlarm(this, cal.getTimeInMillis());
        } else if (type == 6) {
            if (editPackageName.getText().toString().equals("")) {
                warningMessage();
            } else {
                AppUtils.storeApp(this, editPackageName.getText().toString());
            }
        }
        goToStartActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                editPackageName.setText(data.getStringExtra("PACKAGE_NAME"));
            }
        }
    }

    @UiThread
    protected void warningMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.missing_value));
        builder.setNeutralButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Click(R.id.btn_cancel)
    protected void btn_cancelWasClicked() {
        this.finish();
    }
}
