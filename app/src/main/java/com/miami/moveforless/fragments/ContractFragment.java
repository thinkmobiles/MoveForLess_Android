package com.miami.moveforless.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.itextpdf.text.DocumentException;
import com.joanzapata.pdfview.PDFView;
import com.miami.moveforless.R;
import com.miami.moveforless.dialogs.SignatureDialog;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.CreatePdf;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;

/**
 * Created by SetKrul on 20.11.2015.
 */
public class ContractFragment extends BaseJobDetailFragment implements View.OnClickListener {
    @Bind(R.id.viewPdf_CF)
    PDFView mPDFView;
    @Bind(R.id.btnSign_CF)
    Button btnSing;

    public static ContractFragment newInstance() {
        return new ContractFragment();
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.pdf_view_fragment;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
        File file = null;
        try {
            file = new CreatePdf(getActivity(), null).createPdf();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        assert file != null;
        if (file.exists()) {
            mPDFView.fromFile(file).enableSwipe(true).showMinimap(true).load();
        }
        btnSing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSign_CF:
                new SignatureDialog().show(getChildFragmentManager(), "");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle extras = data.getExtras();
        Bitmap bm = (Bitmap) extras.get(Const.SIGNATURE_BITMAP_KEY);
        File file = null;
        try {
            file = new CreatePdf(getActivity(), bm).createPdf();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        assert file != null;
        if (file.exists()) {
            mPDFView.fromFile(file).enableSwipe(true).showMinimap(true).load();
        }
    }

    @Override
    public void onDetach() {
        btnSing.setOnClickListener(null);
        super.onDetach();
    }
}
