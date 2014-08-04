package vn.edu.hcmus.ltdd.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import vn.edu.hcmus.ltdd.view.ItemView;
import vn.edu.hcmus.ltdd.view.ItemView_;

/**
 * Created by mac on 6/24/14.
 */
@EBean
public class ListViewAdapter extends BaseAdapter {
    private List<String> listTitle;
    private List<String> listDetail;
    private TypedArray listIcon;

    @RootContext
    Context context;

    @Override
    public int getCount() {
        return getListTitle().size();
    }

    @Override
    public Object getItem(int i) {
        return getListTitle().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ItemView itemView;
        String title = getListTitle().get(position);
        String detail = getListDetail().get(position);
        Drawable icon = null;
        if (getListIcon() != null)
            icon = getListIcon().getDrawable(position);
        if (convertView == null)
            itemView = ItemView_.build(context);
        else
            itemView = (ItemView) convertView;
        itemView.bind(icon, title, detail);
        return itemView;
    }

    public TypedArray getListIcon() {
        return listIcon;
    }

    public void setListIcon(TypedArray listIcon) {
        this.listIcon = listIcon;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public void setListTitle(List<String> listTitle) {
        this.listTitle = listTitle;
    }

    public List<String> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<String> listDetail) {
        this.listDetail = listDetail;
    }
}
