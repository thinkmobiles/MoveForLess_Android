package com.miami.moveforless.customviews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by klim on 29.10.15.
 */
public class HintTextAdapter<S> extends ArrayAdapter {
    public HintTextAdapter(Context context, int resource) {
        super(context, resource);
    }

    public HintTextAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public HintTextAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    public HintTextAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public HintTextAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public HintTextAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public int getCount() {
        return super.getCount() - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        if (position == getCount()) {
            ((TextView) v.findViewById(android.R.id.text1)).setText("");
            ((TextView) v.findViewById(android.R.id.text1)).setHint((CharSequence) getItem(getCount())); //"Hint to
            // be displayed"
        }

        return v;
    }
}
