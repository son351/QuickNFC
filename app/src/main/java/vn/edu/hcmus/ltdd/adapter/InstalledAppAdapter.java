package vn.edu.hcmus.ltdd.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import vn.edu.hcmus.ltdd.model.PackageInfoModel;
import vn.edu.hcmus.ltdd.view.InstalledAppItemView;
import vn.edu.hcmus.ltdd.view.InstalledAppItemView_;

/**
 * Created by mac on 7/11/14.
 */
@EBean
public class InstalledAppAdapter extends BaseAdapter {
    private List<PackageInfoModel> dataSource;

    @RootContext
    Context context;

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        PackageInfoModel content = dataSource.get(position);
        InstalledAppItemView installedAppItemView;
        if (convertView == null)
            installedAppItemView = InstalledAppItemView_.build(context);
        else
            installedAppItemView = (InstalledAppItemView) convertView;
        installedAppItemView.bind(content);
        return installedAppItemView;
    }

    public List<PackageInfoModel> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<PackageInfoModel> dataSource) {
        this.dataSource = dataSource;
    }
}
