package com.miami.moveforless.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.miami.moveforless.R;

/**
 * Created by Sergbek on 18.11.2015.
 */

public final class DetailAddressPopupWindow {

    private WindowManager mWindowManager;

    private Context mContext;
    private PopupWindow mWindow;

    private TextView mHelpTextView;
    private ImageView mUpImageView;
    private ImageView mDownImageView;
    private View mView;

    private Drawable mBackgroundDrawable = null;
    private ShowListener showListener;

    private static final int CORRECTION_TOP_COORDINATES = 15;
    private static final int CORRECTION_BOTTOM_COORDINATES = -20;

    public DetailAddressPopupWindow(Context context, String text, int viewResource) {
        mContext = context;
        mWindow = new PopupWindow(context);

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);

        setContentView(layoutInflater.inflate(viewResource, null));

        mHelpTextView = (TextView) mView.findViewById(R.id.text);
        mUpImageView = (ImageView) mView.findViewById(R.id.arrow_up);
        mDownImageView = (ImageView) mView.findViewById(R.id.arrow_down);

        mHelpTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        mHelpTextView.setSelected(true);
    }

    public DetailAddressPopupWindow(Context context) {
        this(context, "", R.layout.popup_window);

    }

    public DetailAddressPopupWindow(Context context, String text) {
        this(context);

        setText(text);
    }

    public void show(View anchor) {
        preShow();

        int[] location = new int[2];

        anchor.getLocationOnScreen(location);

        Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(),
                location[1] + anchor.getHeight());

        mView.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams
                .WRAP_CONTENT);

        int rootHeight = mView.getMeasuredHeight();
        int rootWidth = mView.getMeasuredWidth();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(displaymetrics);

        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        int yPos = anchorRect.top - rootHeight;

        boolean onTop = true;

        if (anchorRect.top < screenHeight / 2 + 15) {
            yPos = anchorRect.bottom;
            onTop = false;
        }

        int whichArrow, requestedX;

        whichArrow = ((onTop) ? R.id.arrow_down : R.id.arrow_up);
        requestedX = anchorRect.centerX();

        View arrow = whichArrow == R.id.arrow_up ? mUpImageView : mDownImageView;
        View hideArrow = whichArrow == R.id.arrow_up ? mDownImageView : mUpImageView;

        final int arrowWidth = arrow.getMeasuredWidth();

        arrow.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) arrow.getLayoutParams();

        hideArrow.setVisibility(View.INVISIBLE);

        int xPos = 0;

        // ETXTREME RIGHT CLIKED
        if (anchorRect.left + rootWidth > screenWidth) {
            xPos = (screenWidth - rootWidth);
        }
        // ETXTREME LEFT CLIKED
        else if (anchorRect.left - (rootWidth / 2) < 0) {
            xPos = anchorRect.left;
        }
        // INBETWEEN
        else {
            xPos = (anchorRect.centerX() - (rootWidth / 2));
        }

        param.leftMargin = (requestedX - xPos) - (arrowWidth / 2);

        if (onTop) {
            mHelpTextView.setMaxHeight(anchorRect.top - anchorRect.height());
            mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos -
                    CORRECTION_BOTTOM_COORDINATES);


        } else {
            mHelpTextView.setMaxHeight(screenHeight - yPos);
            mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos -
                    CORRECTION_TOP_COORDINATES);

        }

        mView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.float_anim));

    }

    private void preShow() {
        if (mView == null) throw new IllegalStateException("view undefined");


        if (showListener != null) {
            showListener.onPreShow();
            showListener.onShow();
        }

        if (mBackgroundDrawable == null)
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        else
            mWindow.setBackgroundDrawable(mBackgroundDrawable);

        mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mWindow.setTouchable(true);
        mWindow.setFocusable(true);
        mWindow.setOutsideTouchable(true);

        mWindow.setContentView(mView);
    }

    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
    }

    public void setContentView(View root) {
        mView = root;

        mWindow.setContentView(root);
    }

    public void setContentView(int layoutResID) {
        LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);

        setContentView(inflator.inflate(layoutResID, null));
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mWindow.setOnDismissListener(listener);
    }

    public void dismiss() {
        mWindow.dismiss();
        if (showListener != null) {
            showListener.onDismiss();
        }
    }

    public void setText(String text) {
        mHelpTextView.setText(text);
    }

    public interface ShowListener {
        void onPreShow();

        void onDismiss();

        void onShow();
    }

    public void setShowListener(ShowListener showListener) {
        this.showListener = showListener;
    }
}
