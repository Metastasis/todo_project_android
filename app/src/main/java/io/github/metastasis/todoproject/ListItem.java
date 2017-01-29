package io.github.metastasis.todoproject;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ListItem implements Item {
    private final String text;
    private final boolean isCompleted;

    public ListItem(String text, boolean isCompleted) {
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

        TextView text = (TextView) view.findViewById(R.id.tv_text);
        text.setText(this.text);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_is_completed);
        checkBox.setChecked(this.isCompleted);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isCompleted == isChecked) return;

            }
        });

        return view;
    }
}
