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
@EActivity(R.layout.task_activity)
public class TaskActivity extends Activity {
    @ViewById
    ListView list_item;
    @Bean
    ListViewAdapter adapter;

    @AfterViews
    protected void initActivity() {
        List<String> title = Arrays.asList(getResources().getStringArray(R.array.title_task));
        List<String> detail = Arrays.asList(getResources().getStringArray(R.array.detail_task));
        adapter.setListTitle(title);
        adapter.setListDetail(detail);
        adapter.setListIcon(getResources().obtainTypedArray(R.array.icon_task));
        list_item.setAdapter(adapter);
    }

    @ItemClick
    protected void list_itemItemClicked(int position) {
        Intent intent = new Intent(this, TaskDetailActivity_.class);
        intent.putExtra("POSITION", position);
        intent.putExtra("CONTENT", adapter.getListDetail().get(position));
        intent.putExtra("TITLE", adapter.getListTitle().get(position));
        startActivity(intent);
    }
}
