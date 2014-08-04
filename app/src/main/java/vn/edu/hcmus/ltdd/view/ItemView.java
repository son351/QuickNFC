package vn.edu.hcmus.ltdd.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import vn.edu.hcmus.ltdd.app.R;

/**
 * Created by mac on 6/24/14.
 */
@EViewGroup(R.layout.item_view)
public class ItemView extends LinearLayout {
    @ViewById
    ImageView icon;
    @ViewById
    TextView lbl_title;
    @ViewById
    TextView lbl_detail;

    public ItemView(Context context) {
        super(context);
    }

    public void bind(Drawable imgResource, String title, String detail) {
        if (imgResource != null)
            icon.setImageDrawable(imgResource);
        lbl_detail.setText(detail);
        lbl_title.setText(title);
    }
}
