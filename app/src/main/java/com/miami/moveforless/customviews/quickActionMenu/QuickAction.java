package com.miami.moveforless.customviews.quickActionMenu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.miami.moveforless.R;

import java.util.ArrayList;
import java.util.List;

/**
 * QuickAction dialog, shows action list as icon and text like the one in Gallery3D app. Currently supports vertical
 * and horizontal layout.
 *
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 *         <p>
 *         Contributors:
 *         - Kevin Peck <kevinwpeck@gmail.com>
 */
public class QuickAction extends PopupWindows implements OnDismissListener {
    private View mRootView;
    private LayoutInflater mInflater;
    private ViewGroup mTrack;
    private OnActionItemClickListener mItemClickListener;
    private OnDismissListener mDismissListener;

    private List<ActionItem> actionItems = new ArrayList<ActionItem>();

    private int mChildPos;
    private int mInsertPos;
    private int rootWidth = 0;
    private int rootHeight = 0;

    public static final int HORIZONTAL_TOP = 0;
    public static final int VERTICAL_TOP = 1;
    public static final int VERTICAL_BOTTOM = 2;
    public static final int VERTICAL_RIGHT = 3;

    /**
     * Constructor allowing orientation override
     *
     * @param context Context
     */
    public QuickAction(Context context) {
        super(context);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setRootViewId(R.layout.spinner_dropdown_layout);


        mChildPos = 0;

    }

    /**
     * Set root view.
     *
     * @param id Layout resource id
     */
    public void setRootViewId(int id) {
        mRootView = (ViewGroup) mInflater.inflate(id, null);
        mTrack = (ViewGroup) mRootView.findViewById(R.id.tracks);

        //This was previously defined on show() method, moved here to prevent force close that occured
        //when tapping fastly on a view to show quickaction dialog.
        //Thanx to zammbi (github.com/zammbi)
        mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        setContentView(mRootView);
    }

    /**
     * Set listener for action item clicked.
     *
     * @param listener Listener
     */
    public void setOnActionItemClickListener(OnActionItemClickListener listener) {
        mItemClickListener = listener;
    }

    /**
     * Add action item
     *
     * @param action {@link ActionItem}
     */
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

        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(QuickAction.this, pos, actionId);
                }

                dismiss();
            }
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
        preShow(anchor);
        boolean onTop = false;
        int xPos = 0, yPos = 0, arrowPos = 0;

        int[] location = new int[2];

        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1]
                + anchor.getHeight());

        mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        mRootView.getLayoutParams().width = anchor.getMeasuredWidth();
        rootWidth = anchor.getMeasuredWidth();
        rootHeight = mRootView.getMeasuredHeight();

        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int screenHeight = size.y;

        if (anchorRect.top < screenHeight / 2) {
            onTop = true;
        }
        xPos = anchorRect.left;
        yPos = calculateVerticalPosition(anchorRect, rootHeight, onTop);

        if (!onTop) mWindow.setAnimationStyle(R.style.Animations_PopUpMenu_Center);
        else mWindow.setAnimationStyle(R.style.Animations_PopDownMenu_Center);

        if (onTop && mTrack.getChildCount() > 0) {
            View arrow = mTrack.getChildAt(0).findViewById(R.id.ivArrow_SDRL);
            arrow.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) arrow.getLayoutParams();
            params.gravity = Gravity.TOP;
        }
        else if (!onTop && mTrack.getChildCount() > 0) {
            ImageView arrow = (ImageView) mTrack.getChildAt(mTrack.getChildCount() - 1).findViewById(R.id.ivArrow_SDRL);
            arrow.setVisibility(View.VISIBLE);
            arrow.setImageResource(R.drawable.ic_spinner_down);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) arrow.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
        }

        mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
    }

    private int calculateVerticalPosition(Rect anchorRect, int rootHeight, boolean onTop) {
        int y;

        if (onTop) y = anchorRect.top;
        else y = anchorRect.bottom - rootHeight;

        return y;
    }

    /**
     * Set listener for window dismissed. This listener will only be fired if the quicakction dialog is dismissed
     * by clicking outside the dialog or clicking on sticky item.
     */
    public void setOnDismissListener(OnDismissListener listener) {
        setOnDismissListener(this);

        mDismissListener = listener;
    }

    @Override
    public void onDismiss() {
        if (mDismissListener != null) {
            mDismissListener.onDismiss();
        }
    }

    /**
     * Listener for item click
     */
    public interface OnActionItemClickListener {
        void onItemClick(QuickAction source, int pos, int actionId);
    }

    /**
     * Listener for window dismiss
     */
    public interface OnDismissListener {
        void onDismiss();
    }
}