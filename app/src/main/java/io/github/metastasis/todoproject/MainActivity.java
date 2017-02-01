package io.github.metastasis.todoproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.scalified.fab.ActionButton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.github.metastasis.todoproject.Adapter.Header;
import io.github.metastasis.todoproject.Adapter.Item;
import io.github.metastasis.todoproject.Adapter.ListItem;
import io.github.metastasis.todoproject.Adapter.TodoListAdapter;
import io.github.metastasis.todoproject.Model.Project;
import io.github.metastasis.todoproject.Model.Todo;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private ListView mTodoListView;
    private ActionButton mActionTodoAddActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig defaultFontConfig = new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensans-light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
        CalligraphyConfig.initDefault(defaultFontConfig);

        setContentView(R.layout.activity_main);

        ArrayList<Item> items = new ArrayList<>();
        final ArrayList<Project> projects = populateTodoList();
        if (projects != null) {
            items = makeTodoList(projects);
        }

        TodoListAdapter adapter = new TodoListAdapter(this, items);
        mTodoListView = (ListView) findViewById(R.id.lv_todo_list);
        mTodoListView.setAdapter(adapter);

        mActionTodoAddActionButton = (ActionButton) findViewById(R.id.ab_add_todo_item_button);
        mActionTodoAddActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> headers = getHeaders(projects);
                Intent intent = new Intent(MainActivity.this, TodoAddActivity.class);
                intent.putStringArrayListExtra("ProjectTitles", headers);
                startActivity(intent);
            }
        });
    }

    public ArrayList<Project> populateTodoList() {
        JsonArray result = null;
        ArrayList<Project> projects = null;

        try {
            result = Ion.with(this)
                    .load(getString(R.string.kIndexRequest))
                    .setLogging("HerokuApp", Log.DEBUG)
                    .setHeader("Accept", "application/json")
                    .asJsonArray()
                    .get();

            Log.d("HerokuApp", result.toString());

            Type listType = new TypeToken<ArrayList<Project>>() {
            }.getType();
            projects = (ArrayList<Project>) new Gson().fromJson(result, listType);
        } catch (InterruptedException e) {
            Toast.makeText(MainActivity.this, R.string.loading_error, Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(MainActivity.this, R.string.loading_error, Toast.LENGTH_LONG).show();
        }

        return projects;
    }

    public ArrayList<Item> makeTodoList(ArrayList<Project> projects) {
        ArrayList<Item> items = new ArrayList<>();

        for (Project project : projects) {
            items.add(new Header(project.title));
            for (Todo todo : project.todos) {
                items.add(new ListItem(todo.id, todo.text, todo.isCompleted));
            }
        }

        return items;
    }

//    private void fetchProjects() {
//        Future<JsonArray> futureProjectList = Ion.with(this)
//                .load(getString(R.string.kIndexRequest))
//                .setLogging("HerokuApp", Log.DEBUG)
//                .setHeader("Accept", "application/json")
//                .asJsonArray();
//
//        futureProjectList.setCallback(new FutureCallback<JsonArray>() {
//            @Override
//            public void onCompleted(Exception e, JsonArray result) {
//                if (null != e) {
//                    Toast.makeText(MainActivity.this, R.string.loading_error, Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                if (result != null) {
//                    Log.d("HerokuApp", result.toString());
//
//                    Type listType = new TypeToken<ArrayList<Project>>() {
//                    }.getType();
//                    ArrayList<Project> projects = (ArrayList<Project>) new Gson().fromJson(result, listType);
//                    ArrayList<Item> list = makeTodoList(projects);
//                }
//            }
//        });
//    }

    private ArrayList<String> getHeaders(ArrayList<Project> items) {
        ArrayList<String> headers = new ArrayList<>();

        for (Project item : items) {
            headers.add(item.title);
        }

        return headers;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
