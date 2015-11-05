package com.miami.moveforless.dialogs;

import com.miami.moveforless.R;

import butterknife.BindString;

/**
 * Created by klim on 04.11.15.
 */
public class ConfirmDialog extends BaseDialog {
    @BindString(R.string.cancel) String strCancel;
    @BindString(R.string.ok) String strOk;

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void setupViews() {
        setCancelable(false);
        setNegativeTitle(strCancel);
        setPositiveTitle(strOk);
    }
}
