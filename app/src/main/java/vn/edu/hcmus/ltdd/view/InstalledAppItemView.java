package vn.edu.hcmus.ltdd.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import vn.edu.hcmus.ltdd.app.R;
import vn.edu.hcmus.ltdd.models.PackageInfoModel;

/**
 * Created by mac on 7/11/14.
 */
@EViewGroup(R.layout.installed_app_item_view)
public class InstalledAppItemView extends LinearLayout {
    @ViewById
    ImageView imgAppIcon;
    @ViewById
    TextView lblAppName;
    @ViewById
    TextView lblPackageName;

    public InstalledAppItemView(Context context) {
        super(context);
    }

    public void bind(PackageInfoModel content) {
        if (content.getAppName() != null)
            lblAppName.setText(content.getAppName());
        if (content.getPackageName() != null)
            lblPackageName.setText(content.getPackageName());
        if (content.getIcon() != null)
            imgAppIcon.setImageDrawable(content.getIcon());
    }
}
