package com.miami.moveforless.dialogs;

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

    private String mMessage = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_info_layout;
    }

    @Override
    protected void setupViews() {
        RxUtils.click(btnClose).subscribe(o -> dismiss());
        btnClose.setText(strClose);

        if (!mMessage.isEmpty()) {
            tvTitle.setText(mMessage);
        }
    }

    public void setMessage(String _text) {
        mMessage = _text;
    }

}
