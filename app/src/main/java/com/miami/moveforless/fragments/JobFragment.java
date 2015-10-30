package com.miami.moveforless.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.miami.moveforless.R;
import com.miami.moveforless.adapters.JobPageAdapter;
import com.miami.moveforless.customviews.CustomTabLayout;

import butterknife.Bind;
import butterknife.BindColor;

/**
 * Created by klim on 22.10.15.
 */
public class JobFragment extends BaseFragment {
    @Bind(R.id.tabLayout_FJ)
    CustomTabLayout mTabLayout;
    @BindColor(R.color.unactivated_tab)
    int unactivated_tab;
    @Bind(R.id.viewPager_FJ)
    ViewPager mViewPager;



    private int selectedTab = 0;

    public static Fragment newInstance() {
        return new JobFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job;
    }

    @Override
    protected void setupViews() {

//        try {
//            new CreatePdf(this).createPdf();
//        } catch (DocumentException | IOException e) {
//            e.printStackTrace();
//        }
//        File sdCard = Environment.getExternalStorageDirectory();
//        File dir = new File(sdCard.getAbsolutePath());
//        File file = new File(dir, "sample2.pdf");
//        if (file.exists()) {
//            pdfView.fromFile(file).enableSwipe(true).showMinimap(true).load();
//        }


        mTabLayout.setBackgroundColor(unactivated_tab);
        JobPageAdapter adapter = new JobPageAdapter(getChildFragmentManager());

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.customizeTabs(mViewPager);
        mViewPager.setCurrentItem(selectedTab);
    }

}