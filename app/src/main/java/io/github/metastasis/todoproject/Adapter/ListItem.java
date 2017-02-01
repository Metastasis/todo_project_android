package io.github.metastasis.todoproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import io.github.metastasis.todoproject.Adapter.Item;
import io.github.metastasis.todoproject.Adapter.TodoListAdapter;
import io.github.metastasis.todoproject.R;

public class ListItem implements Item {
    private String id = "";
    private String text;
    private boolean isCompleted;

    public ListItem(String id, String text, boolean isCompleted) {
        this.id = id;
        this.text = text;
        this.isCompleted = isCompleted;
    }

    @Override
    public int getViewType() {
        return TodoListAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.todo_list_row, null);
        } else {
            view = convertView;
        }

        TextView tvText = (TextView) view.findViewById(R.id.tv_text);
        tvText.setText(this.text);

        final Context ctx = view.getContext();
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_is_completed);
        checkBox.setChecked(this.isCompleted);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isCompleted == isChecked) return;

                String flag = isCompleted ? "1" : "0";
                String url = ctx.getString(R.string.kUpdateRequest);

//                JsonObject params = new JsonObject();
//                JsonObject todo = new JsonObject();

//                todo.addProperty("id", id);
//                todo.addProperty("text", text);
//                todo.addProperty("isCompleted", isCompleted);
//                params.add("todo", todo);
//                Log.d("TodoUpdate", params.toString());

                Ion.with(ctx)
                        .load(url)
                        .setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .setHeader("Referer", ctx.getString(R.string.kIndexRequest))
                        .setBodyParameter("_method", "patch")
                        .setBodyParameter("todo[id]", id)
                        .setBodyParameter("todo[isCompleted]", flag)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                if (null != e) {
                                    Log.d("TodoUpdate", "Cannot update");
                                    e.printStackTrace();
                                    return;
                                }
                                Log.d("TodoUpdate", "Update is successful");
                                Log.d("TodoUpdate", result);
                            }
                        });
            }
        });

        return view;
    }
}
