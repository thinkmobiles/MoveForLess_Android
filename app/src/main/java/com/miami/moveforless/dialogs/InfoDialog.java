package com.miami.moveforless.dialogs;

import com.miami.moveforless.R;

import butterknife.BindString;

/**
 * Created by klim on 29.10.15.
 */
public class InfoDialog extends BaseDialog {
    @BindString(R.string.close) String mMessage;

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void setupViews() {
        setNegativeTitle(mMessage);
        setCancelable(false);
    }
}
