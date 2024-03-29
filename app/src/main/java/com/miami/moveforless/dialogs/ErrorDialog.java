package com.miami.moveforless.dialogs;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 11.11.15.
 */
public class ErrorDialog extends BaseDialog {
    @Bind(R.id.tvMessage_DEL)
    TextView tvMessage;
    @Bind(R.id.btnNegative_DEL)
    TextView btnClose;

    @BindString(R.string.close) String strClose;

    private String mMessage = "";
    private View.OnClickListener mListener;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_error_layout;
    }

    @Override
    protected void setupViews() {
        RxUtils.click(btnClose).subscribe(o -> dismiss());
        btnClose.setText(strClose);
        if (!mMessage.isEmpty()) {
            tvMessage.setText(mMessage);
        }
        if (mListener != null) {
            RxUtils.click(btnClose, o -> mListener.onClick(null));
        }
    }

    public void setMessage(String _message) {
        mMessage = _message;
    }

    public void setOnClickListener(View.OnClickListener _listener) {
        mListener = _listener;
    }
}
