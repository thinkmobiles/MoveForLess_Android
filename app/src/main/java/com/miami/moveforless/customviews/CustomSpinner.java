package com.miami.moveforless.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.miami.moveforless.customviews.quickActionMenu.ActionItem;
import com.miami.moveforless.customviews.quickActionMenu.PaymentActionItem;
import com.miami.moveforless.customviews.quickActionMenu.QuickAction;

import java.util.List;

/**
 * Created by klim on 09.11.15.
 */
public class CustomSpinner extends TextView {
    private OnItemClickListener listener;
    private List<? extends ActionItem> mItems;

    public CustomSpinner(Context context) {
        this(context, null);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(view -> showDropDown(view));
    }

    public void setDropDownItems(List<? extends ActionItem>_items) {
        mItems = _items;
    }

    public void setOnDropDownItemClicked(OnItemClickListener _listener) {
        listener = _listener;
    }

    private void showDropDown(View _anchor) {
        final QuickAction quickAction = new QuickAction(getContext());
        for (int i = 0; i < mItems.size(); i++) {
            quickAction.addActionItem(mItems.get(i));
        }
        quickAction.setOnActionItemClickListener((source, pos, actionId) -> onItemClicked(pos));
        quickAction.show(_anchor);
    }

    public interface OnItemClickListener {
        void onClicked(int _position);
    }

    private void onItemClicked(int _position) {
        setText(mItems.get(_position).getTitle());
        if (listener != null) {
            listener.onClicked(_position);
        }
    }
}
