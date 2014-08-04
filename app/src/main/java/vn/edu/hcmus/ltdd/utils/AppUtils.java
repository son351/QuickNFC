package vn.edu.hcmus.ltdd.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mac on 7/8/14.
 */
public class AppUtils {
    private static SimpleDateFormat showDateFormatter;

    public static String getNfcTagId(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("NFC_ID", "");
    }

    public static int getNfcSize(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getInt("NFC_SIZE", 0);
    }

    public static boolean getNfcWritable(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getBoolean("NFC_WRITABLE", false);
    }

    public static String getNfcType(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("NFC_TYPE", "");
    }

    public static String getNfcTechList(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("NFC_TECHLIST", "");
    }

    public static void storeNewNfcTag(Context context, String id,
                                      int size, boolean writable, String type, String[] techList) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putString("NFC_ID", id);
        edit.putInt("NFC_SIZE", size);
        edit.putBoolean("NFC_WRITABLE", writable);
        edit.putString("NFC_TYPE", type);
        StringBuilder sb = new StringBuilder();
        for (String item : techList) {
            sb.append(item).append(",");
        }
        edit.putString("NFC_TECHLIST", sb.toString());
        edit.commit();
    }

    //-----------------------  NORMAL TAB  -----------------------
    public static void storeSms(Context context, List<String> sms) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(sms);
        edit.putStringSet("SMS", set);
        edit.commit();
    }

    public static List<String> getSms(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        Set<String> set = ref.getStringSet("SMS", new HashSet<String>());
        List<String> sms = new ArrayList<String>();
        for (String item : set) {
            sms.add(item);
        }
        return sms;
    }

    public static void storeUrl(Context context, String url) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putString("URL", url);
        edit.commit();
    }

    public static String getUrl(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("URL", "");
    }

    public static void storeMail(Context context, List<String> mail) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(mail);
        edit.putStringSet("MAIL", set);
        edit.commit();
    }

    public static List<String> getMail(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        Set<String> set = ref.getStringSet("MAIL", new HashSet<String>());
        List<String> mail = new ArrayList<String>();
        for (String item : set) {
            mail.add(item);
        }
        return mail;
    }

    public static void storeContact(Context context, List<String> contact) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(contact);
        edit.putStringSet("CONTACT", set);
        edit.commit();
    }

    public static List<String> getContact(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        Set<String> set = ref.getStringSet("CONTACT", new HashSet<String>());
        List<String> contact = new ArrayList<String>();
        for (String item : set) {
            contact.add(item);
        }
        return contact;
    }

    public static void storePhone(Context context, String phone) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putString("PHONE", phone);
        edit.commit();
    }

    public static String getPhone(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("PHONE", "");
    }

    public static void storeMapAddress(Context context, String add) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putString("MAP_ADDRESS", add);
        edit.commit();
    }

    public static String getMapAddress(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("MAP_ADDRESS", "");
    }

    //---------------------  SYSTEM TAB  -----------------------
    public static void storeOnOff(Context context, String type, String checked) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putString(type, checked);
        edit.commit();
    }

    public static String getOnOff(Context context, String type) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString(type, "");
    }

    public static void storeBrightnessValue(Context context, int value) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putInt("BRIGHTNESS", value);
        edit.commit();
    }

    public static int getBrightnessValue(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getInt("BRIGHTNESS", -1);
    }

    public static void storeSound(Context context, String value) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putString("SOUND", value);
        edit.commit();
    }

    public static String getSound(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("SOUND", "");
    }

    public static void storeAlarm(Context context, long value) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putLong("ALARM", value);
        edit.commit();
    }

    public static long getAlarm(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getLong("ALARM", 0);
    }

    public static void storeApp(Context context, String value) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = ref.edit();
        edit.putString("APP", value);
        edit.commit();
    }

    public static String getApp(Context context) {
        SharedPreferences ref = context.getSharedPreferences("NFC_VIET", Context.MODE_PRIVATE);
        return ref.getString("APP", "");
    }

    public static SimpleDateFormat getShowDateFormatter() {
        if (showDateFormatter == null) {
            showDateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        }
        return showDateFormatter;
    }
}
