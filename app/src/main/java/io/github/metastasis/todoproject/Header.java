package io.github.metastasis.todoproject;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Header implements Item {
    private final String title;

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
            // Do some initialization
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.tv_title);
        text.setText(title);

        return view;
    }

}
