package vn.edu.hcmus.ltdd.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Locale;

import vn.edu.hcmus.ltdd.app.R;
import vn.edu.hcmus.ltdd.fragment.ReadFragment;
import vn.edu.hcmus.ltdd.fragment.TaskFragment;
import vn.edu.hcmus.ltdd.fragment.WeatherFragment;
import vn.edu.hcmus.ltdd.fragment.WriteFragment;

/**
 * Created by mac on 6/24/14.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ReadFragment fm = ReadFragment.newInstance(context);
                return fm;
            case 1:
                WriteFragment fm1 = WriteFragment.newInstance(context);
                return fm1;
            case 2:
                TaskFragment fm2 = TaskFragment.newInstance(context);
                return fm2;
            case 3:
                WeatherFragment fm3 = WeatherFragment.newInstance(context);
                return fm3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return context.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return context.getString(R.string.title_section3).toUpperCase(l);
            case 3:
                return context.getString(R.string.title_section4).toUpperCase(l);
        }
        return null;
    }
}