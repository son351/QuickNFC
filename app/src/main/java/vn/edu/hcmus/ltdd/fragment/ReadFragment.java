package vn.edu.hcmus.ltdd.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;

import vn.edu.hcmus.ltdd.adapter.ListViewAdapter;
import vn.edu.hcmus.ltdd.app.R;
import vn.edu.hcmus.ltdd.utils.AppUtils;

/**
 * Created by mac on 6/24/14.
 */
@EFragment(R.layout.read_fragment)
public class ReadFragment extends Fragment {
    private Context context;

    @ViewById
    LinearLayout emptyListView;
    @ViewById
    ListView listView;
    @Bean
    ListViewAdapter adapter;

    public ReadFragment() { }

    public static ReadFragment newInstance(Context context) {
        ReadFragment fm = new ReadFragment_();
        fm.context = context;
        return fm;
    }

    @AfterViews
    protected void initReadFragment() {
        if (AppUtils.getNfcTagId(context).equals("")) {
            listView.setEmptyView(emptyListView);
        } else {
            // Prepare value
            String techList = AppUtils.getNfcTechList(context);
            String[] items = techList.split(",");
            StringBuilder sb = new StringBuilder();
            for (String i : items) {
                sb.append(i.replace("android.nfc.tech.", " "));
            }
            String id = AppUtils.getNfcTagId(context);
            int size = AppUtils.getNfcSize(context);
            boolean writable = AppUtils.getNfcWritable(context);
            String write = "";
            if (writable)
                write = getString(R.string.can_write);
            else
                write = getString(R.string.not_write);
            String type = AppUtils.getNfcType(context).replace("org.nfcforum.","");
            String[] listResult = { sb.toString(), id, Integer.toString(size) + " bytes", write, type };

            List<String> title = Arrays.asList(getResources().getStringArray(R.array.title_read));
            List<String> detail = Arrays.asList(listResult);
            adapter.setListTitle(title);
            adapter.setListDetail(detail);
            adapter.setListIcon(getResources().obtainTypedArray(R.array.icon_read));
            listView.setAdapter(adapter);
        }
    }
}
