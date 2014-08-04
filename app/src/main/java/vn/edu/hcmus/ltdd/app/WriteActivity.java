package vn.edu.hcmus.ltdd.app;

import android.app.Activity;
import android.content.Intent;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;

import vn.edu.hcmus.ltdd.adapter.ListViewAdapter;

/**
 * Created by mac on 6/24/14.
 */
@EActivity(R.layout.write_activity)
public class WriteActivity extends Activity {
    @ViewById
    ListView list_item;
    @Bean
    ListViewAdapter adapter;

    @AfterViews
    protected void initActivity() {
        List<String> title = Arrays.asList(getResources().getStringArray(R.array.title));
        List<String> detail = Arrays.asList(getResources().getStringArray(R.array.detail));
        adapter.setListDetail(detail);
        adapter.setListTitle(title);
        adapter.setListIcon(getResources().obtainTypedArray(R.array.icon));
        list_item.setAdapter(adapter);
    }

    @ItemClick
    protected void list_itemItemClicked(int position) {
        Intent intent = new Intent(this, WriteDetailActivity_.class);
        intent.putExtra("POSITION", position);
        intent.putExtra("CONTENT", adapter.getListDetail().get(position));
        intent.putExtra("TITLE", adapter.getListTitle().get(position));
        startActivity(intent);
    }
}
