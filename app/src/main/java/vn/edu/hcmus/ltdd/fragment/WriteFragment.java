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

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmus.ltdd.adapter.ListViewAdapter;
import vn.edu.hcmus.ltdd.app.R;
import vn.edu.hcmus.ltdd.app.WriteActivity_;
import vn.edu.hcmus.ltdd.utils.AppUtils;

/**
 * Created by mac on 6/24/14.
 */
@EFragment(R.layout.task_write_fragment)
public class WriteFragment extends Fragment {
    private Context context;
    private List<String> title, detail;
    @ViewById
    ListView listView;
    @Bean
    ListViewAdapter adapter;
    @ViewById
    LinearLayout emptyListView;

    public WriteFragment() { }

    public static WriteFragment newInstance(Context context) {
        WriteFragment fm = new WriteFragment_();
        fm.context = context;
        return fm;
    }

    @Click(R.id.add_new)
    protected void add_newWasClicked() {
        goToWriteActivity();
    }

    protected void goToWriteActivity() {
        Intent intent = new Intent(context, WriteActivity_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void getData() {
        title = new ArrayList<String>();
        detail = new ArrayList<String>();

        List<String> sms = AppUtils.getSms(context);
        if (sms.size() > 0) {
            title.add(getResources().getStringArray(R.array.title)[0]);
            detail.add(sms.get(1) + " - " + sms.get(0));
        }

        String url = AppUtils.getUrl(context);
        if (!url.equals("")) {
            title.add(getResources().getStringArray(R.array.title)[1]);
            detail.add(url);
        }

        List<String> mail = AppUtils.getMail(context);
        if (mail.size() > 0) {
            title.add(getResources().getStringArray(R.array.title)[2]);
            detail.add(mail.get(0) + " - " + mail.get(1));
        }

        String phone = AppUtils.getPhone(context);
        if (!phone.equals("")) {
            title.add(getResources().getStringArray(R.array.title)[3]);
            detail.add(phone);
        }

        String mapAddress = AppUtils.getMapAddress(context);
        if (!mapAddress.equals("")) {
            title.add(getResources().getStringArray(R.array.title)[4]);
            detail.add(mapAddress);
        }

        if (title.size() > 0) {
            adapter.setListTitle(title);
            adapter.setListDetail(detail);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);
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
                if (title.get(position).equals(getResources().getStringArray(R.array.title)[0])) {
                    AppUtils.storeSms(context, new ArrayList<String>());
                } else if (title.get(position).equals(getResources().getStringArray(R.array.title)[1])) {
                    AppUtils.storeUrl(context, "");
                } else if (title.get(position).equals(getResources().getStringArray(R.array.title)[2])) {
                    AppUtils.storeMail(context, new ArrayList<String>());
                } else if (title.get(position).equals(getResources().getStringArray(R.array.title)[3])) {
                    AppUtils.storePhone(context, "");
                } else if (title.get(position).equals(getResources().getStringArray(R.array.title)[4])) {
                    AppUtils.storeMapAddress(context, "");
                }
                title.remove(position);
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
    protected void initWriteFragment() {
        getData();
    }
}
