package io.github.metastasis.todoproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class TodoAddActivity extends AppCompatActivity {
    private CheckedTextView mProjectTitle;
    private ListView mProjectsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);

        mProjectsListView = (ListView) findViewById(R.id.lv_projects_list);

        ArrayList<String> projectsArray = getIntent().getStringArrayListExtra("ProjectTitles");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.todo_add_row, R.id.ctv_project_title, projectsArray);
        mProjectsListView.setAdapter(adapter);

        mProjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
//                Toast.makeText(getApplicationContext(), "Checked", Toast.LENGTH_SHORT).show();
                View v = parent.getChildAt(position);
                mProjectTitle = (CheckedTextView) v.findViewById(R.id.ctv_project_title);
                mProjectTitle.toggle();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.todo_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();

        switch (selectedItemId) {
            case R.id.create_todo:
                createTodo();
                return true;
            case R.id.back_to_main:
                TodoAddActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createTodo() {
        JsonObject params = new JsonObject();
        mProjectTitle.getText();
    }
}
