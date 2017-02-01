package io.github.metastasis.todoproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.github.metastasis.todoproject.Adapter.Item;
import io.github.metastasis.todoproject.Adapter.TodoListAdapter;
import io.github.metastasis.todoproject.R;

public class Header implements Item {
    final String title;

    public Header(String title) {
        this.title = title;
    }

    @Override
    public int getViewType() {
        return TodoListAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.todo_list_header, null);
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.tv_title);
        text.setText(title);

        return view;
    }

}
