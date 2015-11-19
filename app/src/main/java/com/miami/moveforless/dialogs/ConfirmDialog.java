package com.miami.moveforless.dialogs;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by klim on 04.11.15.
 */
public class ConfirmDialog extends BaseDialog {
    @BindString(R.string.cancel)
    String strCancel;
    @BindString(R.string.ok)
    String strOk;

    @Bind(R.id.tvMessage_DCL)
    TextView tvMessage;
    @Bind(R.id.btnNegative_DCL)
    Button btnNegative;
    @Bind(R.id.btnPositive_DCL)
    Button btnPositive;

    private String mMessage;
    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_confirm_layout;
    }

    @Override
    protected void setupViews() {
        setCancelable(false);

        btnPositive.setText(strOk);
        btnNegative.setText(strCancel);

        RxUtils.click(btnNegative).subscribe(o -> dismiss());
        RxUtils.click(btnPositive).subscribe(o -> {
            if (mPositiveListener != null) mPositiveListener.onClick(null);
            dismiss();
        });

        RxUtils.click(btnNegative).subscribe(o -> {
            if (mNegativeListener != null) mNegativeListener.onClick(null);
            dismiss();
        });


        if (!mMessage.isEmpty()) tvMessage.setText(mMessage);

    }

    public void setMessage(String _message) {
        mMessage = _message;
    }

    public void setOnPositiveListener(View.OnClickListener _listener) {
        mPositiveListener = _listener;
    }

    public void setOnNegativeListener(View.OnClickListener _listener) {
        mNegativeListener = _listener;
    }

}
