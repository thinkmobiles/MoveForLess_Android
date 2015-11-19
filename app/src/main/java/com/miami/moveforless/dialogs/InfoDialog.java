package com.miami.moveforless.dialogs;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 29.10.15.
 */
public class InfoDialog extends BaseDialog {
    @BindString(R.string.close) String strClose;

    @Bind(R.id.tvMessage_DIL)
    TextView tvTitle;
    @Bind(R.id.btnNegative_DIL)
    Button btnClose;

    private String mMessage;
    private View.OnClickListener mListener;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_info_layout;
    }

    @Override
    protected void setupViews() {
        RxUtils.click(btnClose).subscribe(o -> onClick());
        btnClose.setText(strClose);

        if (!mMessage.isEmpty()) {
            tvTitle.setText(mMessage);
        }
    }

    private void onClick() {
        dismiss();
        if (mListener != null) mListener.onClick(null);
    }

    public void setMessage(String _text) {
        mMessage = _text;
    }

    public void setOnClickListener(View.OnClickListener _listener) {
        mListener = _listener;
    }
}
