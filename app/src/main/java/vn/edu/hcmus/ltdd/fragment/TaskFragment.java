package vn.edu.hcmus.ltdd.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.hcmus.ltdd.adapter.ListViewAdapter;
import vn.edu.hcmus.ltdd.app.R;
import vn.edu.hcmus.ltdd.app.TaskActivity_;
import vn.edu.hcmus.ltdd.utils.AppUtils;

/**
 * Created by mac on 6/24/14.
 */
@EFragment(R.layout.task_write_fragment)
public class TaskFragment extends Fragment {
    private Context context;
    private List<String> header;
    private List<String> detail;
    private String[] title;
    @ViewById
    ListView listView;
    @ViewById
    LinearLayout emptyListView;
    @Bean
    ListViewAdapter adapter;

    public TaskFragment() {
    }

    public static TaskFragment newInstance(Context context) {
        TaskFragment fm = new TaskFragment_();
        fm.context = context;
        return fm;
    }

    @Click(R.id.add_new)
    protected void add_newWasClicked() {
        goToTaskActivity();
    }

    protected void goToTaskActivity() {
        Intent intent = new Intent(context, TaskActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void getData() {
        header = new ArrayList<String>();
        detail = new ArrayList<String>();
        title = getResources().getStringArray(R.array.title_task);
        for (int i = 0; i < title.length; i++) {
            if (i == 0 || i == 1 || i == 2) {
                if (!AppUtils.getOnOff(context, title[i]).equals("")) {
                    header.add(title[i]);
                    detail.add(title[i] + " - " + AppUtils.getOnOff(context, title[i]));
                }
            }
        }
        if (AppUtils.getBrightnessValue(context) != -1) {
            header.add(title[3]);
            detail.add(AppUtils.getBrightnessValue(context) + "");
        }
        if (!AppUtils.getSound(context).equals("")) {
            header.add(title[4]);
            detail.add(AppUtils.getSound(context));
        }
        if (AppUtils.getAlarm(context) != 0) {
            header.add(title[5]);
            SimpleDateFormat format = AppUtils.getShowDateFormatter();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(AppUtils.getAlarm(context));
            detail.add(format.format(cal.getTime()));
        }
        if (!AppUtils.getApp(context).equals("")) {
            header.add(title[6]);
            detail.add(AppUtils.getApp(context));
        }

        if (header.size() > 0) {
            adapter.setListTitle(header);
            adapter.setListDetail(detail);
            listView.setAdapter(adapter);
        } else {
            listView.setEmptyView(emptyListView);
        }
    }

    @ItemClick
    protected void listViewItemClicked(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.asking_for_delete);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if ((header.get(position).equals(title[0])) || (header.get(position).equals(title[1]))
                        || (header.get(position).equals(title[2]))) {
                    AppUtils.storeOnOff(context, header.get(position), "");
                } else if (header.get(position).equals(title[3])) {
                    AppUtils.storeBrightnessValue(context, -1);
                } else if (header.get(position).equals(title[4])) {
                    AppUtils.storeSound(context, "");
                } else if (header.get(position).equals(title[5])) {
                    AppUtils.storeAlarm(context, 0);
                } else if (header.get(position).equals(title[6])) {
                    AppUtils.storeApp(context, "");
                }

                header.remove(position);
                detail.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @AfterViews
    protected void initTaskFragment() {
        getData();
    }
}
