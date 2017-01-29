package io.github.metastasis.todoproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private TodoListAdapter mTodoListAdapter;
    private ListView mTodoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig defaultFontConfig = new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensans-light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
        CalligraphyConfig.initDefault(defaultFontConfig);

        setContentView(R.layout.activity_main);

        List<Item> items = new ArrayList<>();
        items.add(new Header("Header 1"));
        items.add(new ListItem("Text 1", false));
        items.add(new ListItem("Text 2", false));
        items.add(new ListItem("Text 3", true));
        items.add(new ListItem("Text 4", false));
        items.add(new Header("Header 2"));
        items.add(new ListItem("Text 5", true));
        items.add(new ListItem("Text 6", false));
        items.add(new ListItem("Text 7", true));
        items.add(new ListItem("Text 8", false));
        TodoListAdapter adapter = new TodoListAdapter(this, items);
        mTodoListView = (ListView) findViewById(R.id.lv_todo_list);
        mTodoListView.setAdapter(adapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
