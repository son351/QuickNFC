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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.edu.hcmus.ltdd.utils.AppUtils;

/**
 * Created by mac on 6/24/14.
 */
@EActivity(R.layout.write_detail_activity)
public class WriteDetailActivity extends Activity {
    private int type;
    private EditText receiverText, contentText, urlText, editMailTo, editSubject,
            editContent, editContactName, editCompany, editPhoneContact, editEmail, editWebsite, editPhone,
            editAddress;
    private Spinner spinner;

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
        icon.setImageDrawable(getResources().obtainTypedArray(R.array.icon).getDrawable(type));

        // INFLATE LAYOUT
        if (type == 0) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.text_address_layout, null);
            receiverText = (EditText) v.findViewById(R.id.edit_text_receiver);
            contentText = (EditText) v.findViewById(R.id.edit_text_address);
            write_frame.addView(v);
        } else if (type == 1) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.url_layout, null);

            spinner = (Spinner) v.findViewById(R.id.spinner_url);
            urlText = (EditText) v.findViewById(R.id.edit_url);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.url_items));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            write_frame.addView(v);
        } else if (type == 2) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.mail_layout, null);
            editMailTo = (EditText) v.findViewById(R.id.editMailTo);
            editSubject = (EditText) v.findViewById(R.id.editSubject);
            editContent = (EditText) v.findViewById(R.id.editContent);
            write_frame.addView(v);
        } else if (type == 3) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.phone_layout, null);
            editPhone = (EditText) v.findViewById(R.id.editPhone);
            Button btnFromContact = (Button) v.findViewById(R.id.btnFromContact);
            btnFromContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            write_frame.addView(v);
        } else if (type == 4) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View v = inflater.inflate(R.layout.address_layout, null);
            editAddress = (EditText) v.findViewById(R.id.editAddress);
            write_frame.addView(v);
        }
    }

    @UiThread
    protected void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.missing_value);
        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    protected void goToStartActivity() {
        Intent intent = new Intent(this, StartActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Click(R.id.btn_ok)
    protected void btn_okWasClicked() {
        if (type == 0) {
            if (receiverText.getText().toString().equals("") && contentText.getText().toString().equals("")) {
                showWarningDialog();
            } else {
                List<String> sms = new ArrayList<String>();
                sms.add(receiverText.getText().toString());
                sms.add(contentText.getText().toString());
                AppUtils.storeSms(this, sms);
                goToStartActivity();
            }
        } else if (type == 1) {
            if (urlText.getText().toString().equals("")) {
                showWarningDialog();
            } else {
                String spinnerText = spinner.getSelectedItem().toString();
                AppUtils.storeUrl(this, spinnerText + urlText.getText().toString());
                goToStartActivity();
            }
        } else if (type == 2) {
            if (editMailTo.getText().toString().equals("") && editSubject.getText().toString().equals("") &&
                    editContent.getText().toString().equals("")) {
                showWarningDialog();
            } else {
                List<String> mail = new ArrayList<String>();
                mail.add(editMailTo.getText().toString());
                mail.add(editSubject.getText().toString());
                mail.add(editContent.getText().toString());
                AppUtils.storeMail(this, mail);
                goToStartActivity();
            }
        }else if (type == 3) {
            if (editPhone.getText().toString().equals("")) {
                showWarningDialog();
            } else {
                AppUtils.storePhone(this, editPhone.getText().toString());
                goToStartActivity();
            }
        } else if (type == 4) {
            if (editAddress.getText().toString().equals("")) {
                showWarningDialog();
            } else {
                AppUtils.storeMapAddress(this, editAddress.getText().toString());
                goToStartActivity();
            }
        }
    }

    @Click(R.id.btn_cancel)
    protected void btn_cancelWasClicked() {
        this.finish();
    }
}
