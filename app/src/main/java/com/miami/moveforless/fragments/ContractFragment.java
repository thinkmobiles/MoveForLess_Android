package com.miami.moveforless.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.itextpdf.text.DocumentException;
import com.joanzapata.pdfview.PDFView;
import com.miami.moveforless.R;
import com.miami.moveforless.dialogs.SignatureDialog;
import com.miami.moveforless.globalconstants.Const;
import com.miami.moveforless.utils.CreatePdf;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by SetKrul on 20.11.2015.
 */
public class ContractFragment extends BaseJobDetailFragment implements View.OnClickListener {

    @Bind(R.id.btnSign_CF)
    Button btnSing;
    @Bind(R.id.pdf_container_PVF)
    FrameLayout mPdfContainer;

    public static ContractFragment newInstance() {
        return new ContractFragment();
    }

    @Override
    protected boolean isAllowGoHome() {
        return false;
    }

    @Override
    public void onPageSelected() {
        generatePdf(null, 200);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.pdf_view_fragment;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {

        btnSing.setOnClickListener(this);
    }

    private void showPdf(File _file) {
        hideLoadingDialog();

        if (_file.exists()) {
            mPdfContainer.removeAllViews();
            PDFView pdfView = new PDFView(getContext(), null);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            pdfView.setLayoutParams(params);

            pdfView.setZOrderOnTop(true);    // necessary
            SurfaceHolder sfhTrackHolder = pdfView.getHolder();
            sfhTrackHolder.setFormat(PixelFormat.TRANSPARENT);

            mPdfContainer.addView(pdfView);
            pdfView.fromFile(_file).enableSwipe(true).showMinimap(true).load();
        }

    }

    private void generatePdf(Bitmap _signature, int delay) {
        showLoadingDialog("Generating Contract...");
            Observable.timer(delay, TimeUnit.MILLISECONDS)
                    .map(aLong -> {
                        File file = null;
                        try {
                            file = new CreatePdf(getContext(), _signature).createPdf();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                        return file;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::showPdf, throwable -> {
                    });
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
        generatePdf(bm, 0);
    }

}
