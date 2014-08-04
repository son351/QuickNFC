package vn.edu.hcmus.ltdd.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmus.ltdd.adapter.InstalledAppAdapter;
import vn.edu.hcmus.ltdd.models.PackageInfoModel;

/**
 * Created by mac on 7/11/14.
 */
@EActivity(R.layout.installed_app_layout)
public class InstalledAppActivity extends Activity {
    @ViewById
    ListView listView;
    @Bean
    InstalledAppAdapter adapter;

    private ArrayList<PackageInfoModel> getInstalledApps(boolean getSysPackages) {
        ArrayList<PackageInfoModel> result = new ArrayList<PackageInfoModel>();
        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
        for (PackageInfo item : packageInfos) {
            if ((!getSysPackages) && (item.versionName == null))
                continue;
            PackageInfoModel model = new PackageInfoModel();
            model.setAppName(item.applicationInfo.loadLabel(getPackageManager()).toString());
            model.setPackageName(item.packageName);
            model.setVersionCode(item.versionCode);
            model.setVersionName(item.versionName);
            model.setIcon(item.applicationInfo.loadIcon(getPackageManager()));
            result.add(model);
        }
        return result;
    }

    @ItemClick
    protected void listViewItemClicked(final PackageInfoModel content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.choose_app));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("PACKAGE_NAME", content.getPackageName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void getData() {
        adapter.setDataSource(getInstalledApps(false));
        listView.setAdapter(adapter);
    }

    @AfterViews
    protected void initActivity() {
        getData();
    }
}
