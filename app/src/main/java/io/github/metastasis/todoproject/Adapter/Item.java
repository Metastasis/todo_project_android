package io.github.metastasis.todoproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Metastasis on 29.01.2017.
 */

public interface Item {
    public int getViewType();

    public View getView(LayoutInflater inflater, View convertView);
}
