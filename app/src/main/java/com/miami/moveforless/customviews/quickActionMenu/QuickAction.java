package com.miami.moveforless.customviews.quickActionMenu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.miami.moveforless.R;

import java.util.ArrayList;
import java.util.List;

public class QuickAction extends PopupWindows {
    private View mRootView;
    private LayoutInflater mInflater;
    private ViewGroup mTrack;
    private OnActionItemClickListener mItemClickListener;
    private List<ActionItem> actionItems = new ArrayList<ActionItem>();

    private int mChildPos;
    private int mInsertPos;
    private int rootHeight = 0;

    public QuickAction(Context context) {
        super(context);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setRootViewId(R.layout.spinner_dropdown_layout);
        mChildPos = 0;

    }

    public void setRootViewId(int id) {
        mRootView = mInflater.inflate(id, null);
        mTrack = (ViewGroup) mRootView.findViewById(R.id.tracks);
        mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        setContentView(mRootView);
    }

    public void setOnActionItemClickListener(OnActionItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void addActionItem(ActionItem action) {
        actionItems.add(action);

        String title = action.getTitle();
        int icon = action.getIcon();

        View container;

        container = mInflater.inflate(R.layout.spinner_dropdown_row_layout, mTrack, false);

        TextView tvTitle = (TextView) container.findViewById(R.id.tvTitle_SDRL);
        ImageView ivIcon = (ImageView) container.findViewById(R.id.ivIcon_SDRL);

        if (title != null) {
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (icon != 0)
            ivIcon.setImageResource(icon);

        final int pos = mChildPos;
        final int actionId = action.getActionId();

        container.setOnClickListener(view -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(QuickAction.this, pos, actionId);
            }

            dismiss();

        });

        container.setFocusable(true);
        container.setClickable(true);

        mTrack.addView(container, mInsertPos);

        mChildPos++;
        mInsertPos++;
    }

    /**
     * Show quickaction popup. Popup is automatically positioned, on top or bottom of anchor view.
     */
    public void show(View anchor) {

        boolean onTop = false;
        int xPos = 0, yPos = 0, arrowPos = 0;

        int[] location = new int[2];

        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1]
                + anchor.getHeight());

        mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        mRootView.getLayoutParams().width = anchor.getMeasuredWidth();
        mRootView.getLayoutParams().height = 200;
        rootHeight = mRootView.getMeasuredHeight();

        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenHeight = size.y;

        if (anchorRect.top < screenHeight / 2) {
            onTop = true;
        }
        xPos = anchorRect.left;
        yPos = calculateVerticalPosition(anchorRect, screenHeight, onTop);

        if (!onTop) mWindow.setAnimationStyle(R.style.Animations_PopUpMenu_Center);
        else mWindow.setAnimationStyle(R.style.Animations_PopDownMenu_Center);

        if (onTop && mTrack.getChildCount() > 0) {
            View arrow = mTrack.getChildAt(0).findViewById(R.id.ivArrow_SDRL);
            arrow.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) arrow.getLayoutParams();
            params.gravity = Gravity.TOP;
        } else if (!onTop && mTrack.getChildCount() > 0) {
            ImageView arrow = (ImageView) mTrack.getChildAt(mTrack.getChildCount() - 1).findViewById(R.id.ivArrow_SDRL);
            arrow.setVisibility(View.VISIBLE);
            arrow.setImageResource(R.drawable.ic_spinner_down);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) arrow.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
        }

        preShow(anchor, anchor.getMeasuredWidth(), rootHeight);
        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
    }

    private int calculateVerticalPosition(Rect anchorRect, int screenHeight, boolean onTop) {
        int y;

        if (!onTop) {
            if (anchorRect.bottom - rootHeight < 0) {
                y = 0;
                rootHeight = anchorRect.top;
            } else y = anchorRect.bottom - rootHeight;
        } else {
            y = anchorRect.top;
            if (anchorRect.top + rootHeight > screenHeight) {
                rootHeight = screenHeight - anchorRect.top;
            }
        }

        return y;
    }

    /**
     * Listener for item click
     */
    public interface OnActionItemClickListener {
        void onItemClick(QuickAction source, int pos, int actionId);
    }

}