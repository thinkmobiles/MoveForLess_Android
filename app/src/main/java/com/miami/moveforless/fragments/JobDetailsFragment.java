package com.miami.moveforless.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.miami.moveforless.R;
import com.miami.moveforless.customviews.ParallaxScrollView;
import com.miami.moveforless.dialogs.RouteDialog;
import com.miami.moveforless.fragments.eventbus.BusProvider;
import com.miami.moveforless.fragments.eventbus.SwitchJobDetailsEvent;
import com.miami.moveforless.rest.response.JobResponse;
import com.miami.moveforless.utils.RxUtils;

import butterknife.Bind;

/**
 * Created by SetKrul on 03.11.2015.
 */
public class JobDetailsFragment extends BaseJobDetailFragment {
    private JobResponse mJob = new JobResponse();

    @Bind(R.id.scroll_view_FJD)
    ParallaxScrollView scrollView;
    @Bind(R.id.etNotes_FJD)
    EditText etNotes;

    @Bind(R.id.etNotes_container_FJD)
    LinearLayout mNotesContainer;
    @Bind(R.id.btnEditNotes_FJD)
    View btnEditNotes;
    @Bind(R.id.btnShowRoute_FJD)
    Button btnShowRoute;
    @Bind(R.id.btnStartJob_FJD)
    Button btnStartJob;
    @Bind(R.id.btnSave_FJD)
    Button btnSaveJob;

    //job details types merge
    @Bind(R.id.spJobTypeId)
    com.miami.moveforless.customviews.CustomSpinner spJobTypeId;
    @Bind(R.id.spCustTypeId)
    com.miami.moveforless.customviews.CustomSpinner spCustTypeId;
    @Bind(R.id.spQuoteTypeId)
    com.miami.moveforless.customviews.CustomSpinner spQuoteTypeId;

    //job details calendars merge
    @Bind(R.id.tvCreateDate)
    TextView tvCreateDate;
    @Bind(R.id.tvReceivedDate)
    TextView tvReceivedDate;
    @Bind(R.id.tvEstimatedDate)
    TextView tvEstimatedDate;
    @Bind(R.id.tvBookingDate)
    TextView tvBookingDate;

    //job details merge name
    @Bind(R.id.etAgentName)
    EditText etAgentName;
    @Bind(R.id.etManagerName)
    EditText etManagerName;
    @Bind(R.id.etEstimatorName)
    EditText etEstimatorName;
    @Bind(R.id.etDriverName)
    EditText etDriverName;
    @Bind(R.id.etTruckName)
    EditText etTruckName;
    @Bind(R.id.etMovers1Name)
    EditText etMovers1Name;
    @Bind(R.id.etMovers2Name)
    EditText etMovers2Name;

    //job details commission partnere merge
    @Bind(R.id.etCommissionNumber)
    EditText etCommissionNumber;
    @Bind(R.id.rbCommissionDol)
    RadioButton rbCommissionDol;
    @Bind(R.id.rbCommissionPercent)
    RadioButton rbCommissionPercent;

    //job details freight type merge
    @Bind(R.id.etJobCode)
    EditText etJobCode;
    @Bind(R.id.spResourceName)
    Spinner spResourceName;
    @Bind(R.id.spMoveTypeName)
    Spinner spMoveTypeName;
    @Bind(R.id.spMoveSizeName)
    Spinner spMoveSizeName;

    //job details confirm with client merge
    @Bind(R.id.etPartnerName)
    EditText etPartnerName;
    @Bind(R.id.spPaidStatusId)
    Spinner spPaidStatusId;

    //job details location merge
    @Bind(R.id.etFromState)
    EditText etFromState;
    @Bind(R.id.etZipCodeFrom2)
    EditText etFromZipCode2;
    @Bind(R.id.etToState)
    EditText etToState;
    @Bind(R.id.etToZipCode)
    EditText etToZipCode;
    @Bind(R.id.etDistanceTotal)
    EditText etDistanceTotal;
    @Bind(R.id.etDistanceOffice)
    EditText etDistanceOffice;

    //job details time job merge
    @Bind(R.id.tvRequiredPickupDate)
    TextView tvRequiredPickupDate;
    @Bind(R.id.tvRequiredPickupTime)
    TextView tvRequiredPickupTime;
    @Bind(R.id.tvFollowUpDate)
    TextView tvFollowUpDate;
    @Bind(R.id.tvFollowUpTime)
    TextView tvFollowUpTime;
    @Bind(R.id.tvStartDate)
    TextView tvStartDate;
    @Bind(R.id.spStartTime)
    Spinner spStartTime;
    @Bind(R.id.tvStopDate)
    TextView tvStopDate;
    @Bind(R.id.spStopTime)
    Spinner spStopTime;

    //job details local details merge
    @Bind(R.id.etLocationNumberEstimate)
    EditText etLocationNumberEstimate;
    @Bind(R.id.spLocalRateType)
    Spinner spLocalRateType;
    @Bind(R.id.etLocalLabor)
    EditText etLocalLabor;
    @Bind(R.id.etNumberMan)
    EditText etNumberMan;
    @Bind(R.id.spNumberMan)
    Spinner spNumberMan;
    @Bind(R.id.spNumberTruck)
    Spinner spNumberTruck;
    @Bind(R.id.etLocalTravel)
    EditText etLocalTravel;
    @Bind(R.id.etLocalRate)
    EditText etLocalRate;
    @Bind(R.id.etTotalPacking)
    EditText etTotalPacking;
    @Bind(R.id.etLocalTravelPlusLocalLabor)
    EditText etLocalTravelPlusLocalLabor;
    @Bind(R.id.etEstimatedTotal)
    EditText etEstimatedTotal;
    @Bind(R.id.etActualTotalFinal)
    EditText etActualTotalFinal;

    //job details from text details merge
    @Bind(R.id.etFromFullName)
    EditText etFromFullName;
    @Bind(R.id.etFromEmail)
    EditText etFromEmail;
    @Bind(R.id.etFromPhone)
    EditText etFromPhone;
    @Bind(R.id.etFromCellPhone)
    EditText etFromCellPhone;
    @Bind(R.id.etFromFax)
    EditText etFromFax;
    @Bind(R.id.etFromAddress)
    EditText etFromAddress;
    @Bind(R.id.etFromApt)
    EditText etFromApt;
    @Bind(R.id.cbHaveFromElevator)
    CheckBox cbHaveFromElevator;
    @Bind(R.id.tvElevatorFromStart)
    TextView tvElevatorFromStart;
    @Bind(R.id.tvElevatorFromEnd)
    TextView tvElevatorFromEnd;
    @Bind(R.id.etFromCity)
    EditText etFromCity;
    @Bind(R.id.cbIsFromInsurance)
    CheckBox cbIsFromInsurance;
    @Bind(R.id.etFromState_From)
    EditText etFromState_From;
//    @Bind(R.id.etZipCodeFrom2)
//    EditText etZipCodeFrom2;
    @Bind(R.id.cbHaveInventoryFrom)
    CheckBox cbHaveInventoryFrom;
    @Bind(R.id.cbIsReferralFrom)
    CheckBox cbIsReferralFrom;
    @Bind(R.id.cbIsFromRepeated)
    CheckBox cbIsFromRepeated;

    //job details from details check merge
    @Bind(R.id.cbHaveNotesFrom)
    CheckBox cbHaveNotesFrom;
    @Bind(R.id.cbHaveStopsFrom)
    CheckBox cbHaveStopsFrom;
    @Bind(R.id.cbHavePackageFrom)
    CheckBox cbHavePackageFrom;
    @Bind(R.id.cbHaveTacksFrom)
    CheckBox cbHaveTacksFrom;
    @Bind(R.id.cbHaveTrailerAccessFrom)
    CheckBox cbHaveTrailerAccessFrom;
    @Bind(R.id.etFromStairsFrom)
    EditText etFromStairsFrom;

    //job details to text details merge
    @Bind(R.id.etToFullName)
    EditText etToFullName;
    @Bind(R.id.etToEmail)
    EditText etToEmail;
    @Bind(R.id.etToPhone)
    EditText etToPhone;
    @Bind(R.id.etToCellPhone)
    EditText etToCellPhone;
    @Bind(R.id.etToFax)
    EditText etToFax;
    @Bind(R.id.etToAddress)
    EditText etToAddress;
    @Bind(R.id.etToApt)
    EditText etToApt;
    @Bind(R.id.cbHaveToElevator)
    CheckBox cbHaveToElevator;
    @Bind(R.id.tvElevatorToStart)
    TextView tvElevatorToStart;
    @Bind(R.id.tvElevatorToEnd)
    TextView tvElevatorToEnd;
    @Bind(R.id.etToCity)
    EditText etToCity;
    @Bind(R.id.cbIsToInsurance)
    CheckBox cbIsToInsurance;
    @Bind(R.id.etStateTo)
    EditText etStateTo;
    @Bind(R.id.etZipCodeTo)
    EditText etZipCodeTo;
    @Bind(R.id.cbHaveInventoryTo)
    CheckBox cbHaveInventoryTo;
    @Bind(R.id.cbIsReferralTo)
    CheckBox cbIsReferralTo;
    @Bind(R.id.cbIsToRepeated)
    CheckBox cbIsToRepeated;


    //job details to details check merge
    @Bind(R.id.cbHaveNotesTo)
    CheckBox cbHaveNotesTo;
    @Bind(R.id.cbHaveStopsTo)
    CheckBox cbHaveStopsTo;
    @Bind(R.id.cbHavePackageTo)
    CheckBox cbHavePackageTo;
    @Bind(R.id.cbHaveTacksTo)
    CheckBox cbHaveTacksTo;
    @Bind(R.id.cbHaveTrailerAccessTo)
    CheckBox cbHaveTrailerAccessTo;
    @Bind(R.id.cbFromStairsTo)
    EditText cbFromStairsTo;

    public static JobDetailsFragment newInstance() {
        return new JobDetailsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_job_details;
    }

    @Override
    protected void setupViews(Bundle _savedInstanceState) {
//        //left
//        setJobTypeSpinner();
//        setCalendars();
//        setCommissions();
//        setFreight();
//        TimeJob();
//        setFromTextDetails();
//        setToTextDetails();
//
//        //right
//        setNames();
//        setConfrimWithClient();
//        setLocation();
//        localDetails();
//        setFromDetailsCheck();
//        setToDetailsCheck();

        scrollView.parallaxViewBackgroundBy(scrollView, ContextCompat.getDrawable(getContext(), R.drawable
                .job_details_background), .2f);

        mNotesContainer.setEnabled(false);
        etNotes.setEnabled(false);

        RxUtils.click(btnEditNotes, o -> switchNotes());
        RxUtils.click(btnShowRoute, o -> showRoute());
        RxUtils.click(btnStartJob, o -> startJob());
        RxUtils.click(btnSaveJob, o -> saveJob());
    }


    @Override
    protected boolean isAllowGoHome() {
        return false;
    }

    @Override
    public void onPageSelected() {

    }

    private void showRoute() {
        RouteDialog dialog = new RouteDialog();
        dialog.show(getChildFragmentManager(), "");
    }

    private void startJob() {
        BusProvider.getInstance().post(new SwitchJobDetailsEvent());
    }

    private void saveJob() {
        // TODO: add updating job in database and on server
    }

    private void switchNotes() {
        if (etNotes.isEnabled()) {
            mNotesContainer.setEnabled(false);
            etNotes.setEnabled(false);
            etNotes.clearFocus();
        } else {
            mNotesContainer.setEnabled(true);
            etNotes.setEnabled(true);
            etNotes.requestFocus();
        }
    }


    //left
    private void setJobTypeSpinner() {
//        ArrayList<String> moving = new ArrayList<>();
//        ArrayAdapter<String> adapterMoving = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
//                moving);
//        spJobTypeId.setAdapter(adapterMoving); //not found
//
//        ArrayList<String> reidenting = new ArrayList<>();
//        ArrayAdapter<String> adapterReidenting = new ArrayAdapter<>(getActivity(), android.R.layout
//                .simple_spinner_item, reidenting);
//        spJobTypeId.setAdapter(adapterReidenting); //not found
//
//        ArrayList<String> nonBinding = new ArrayList<>();
//        ArrayAdapter<String> adapterNonBinding = new ArrayAdapter<>(getActivity(), android.R.layout
//                .simple_spinner_item, nonBinding);
//        spJobTypeId.setAdapter(adapterNonBinding); //not found
    }

    private void setCalendars() {
        tvCreateDate.setText(mJob.CreatedDate);
        tvReceivedDate.setText(mJob.ReceivedDate);
        tvEstimatedDate.setText(mJob.EstimatedDate);
        tvBookingDate.setText(mJob.BookingDate);
    }


    private void setCommissions() {
        etCommissionNumber.setText(mJob.commission_partner);
//                rbCommissionDol  not found
//                rbCommissionPercent  not found
    }


    private void setFreight() {
        etJobCode.setText(mJob.job_code);
        //spResourceName not found
        //spMoveTypeName not found
        //spMoveSizeName not found
    }

    private void TimeJob() {
        tvRequiredPickupDate.setText(mJob.RequiredPickupDate);
        tvRequiredPickupTime.setText(mJob.RequiredPickupDate);
        tvFollowUpDate.setText(mJob.FollowUpDate);
        tvFollowUpTime.setText(mJob.FollowUpDate);
        tvStartDate.setText(mJob.StartTime);
        //spStartTime ???
        tvStopDate.setText(mJob.StopTime);
        //spStopTime ???
    }

    private void setFromTextDetails() {
        etFromFullName.setText(mJob.from_fullname);
        etFromEmail.setText(mJob.from_email);
        etFromPhone.setText(mJob.from_phone);
        etFromCellPhone.setText(mJob.from_cellphone);
        etFromFax.setText(mJob.from_fax);
        etFromAddress.setText(mJob.from_address);
        etFromApt.setText(mJob.from_apt);
        //cbHaveFromElevator not found
        //tvElevatorFromStart.setText(mJob.); not found
        //tvElevatorFromEnd.setText(mJob.); not found
        etFromCity.setText(mJob.from_city);
        cbIsFromInsurance.setChecked(!mJob.is_from_insurance.equals("0"));
        etFromState_From.setText(mJob.from_state);
//        etZipCodeFrom.setText(mJob.from_zipcode);
        //cbHaveInventoryFrom not found
        cbIsReferralFrom.setChecked(!mJob.is_referral.equals("0"));
        cbIsFromRepeated.setChecked(!mJob.is_from_repeated.equals("0"));
    }

    private void setToTextDetails() {
        etToFullName.setText(mJob.to_fullname);
        etToEmail.setText(mJob.to_email);
        etToPhone.setText(mJob.to_phone);
        etToCellPhone.setText(mJob.to_cellphone);
        etToFax.setText(mJob.to_fax);
        etToAddress.setText(mJob.to_address);
        etToApt.setText(mJob.to_apt);
        //cbHaveToElevator not found
        //tvElevatorToStart.setText(mJob.); not found
        //tvElevatorToEnd.setText(mJob.); not found
        etToCity.setText(mJob.to_city);
        cbIsToInsurance.setChecked(!mJob.is_to_insurance.equals("0"));
        etStateTo.setText(mJob.to_state);
        etZipCodeTo.setText(mJob.to_zipcode);
        //cbHaveInventoryTo not found
        cbIsReferralTo.setChecked(!mJob.is_referral.equals("0"));//???
        cbIsToRepeated.setChecked(!mJob.is_from_repeated.equals("0"));//???
    }

    //right
    private void setNames() {
        etAgentName.setText(mJob.agent_name);
        etManagerName.setText(mJob.manager);//???
        etEstimatorName.setText("");//not found
        etDriverName.setText(mJob.driver_name);
        etTruckName.setText("");//not found
        etMovers1Name.setText("");//not found
        etMovers2Name.setText("");//not found
    }

    private void setConfrimWithClient() {
        etPartnerName.setText(mJob.partner); // ???
        //spPaidStatusId not found
    }

    private void setLocation() {
        etFromState.setText(mJob.from_state);
        etFromZipCode2.setText(mJob.from_zipcode);
        etToState.setText(mJob.to_state);
        etToZipCode.setText(mJob.to_zipcode);
        etDistanceTotal.setText(mJob.DistanceTotal);
        etDistanceOffice.setText(mJob.DistanceOffice);
    }

    @SuppressLint("SetTextI18n")
    private void localDetails() {
        etLocationNumberEstimate.setText(mJob.LocalNumberEstimate);
        //spLocalRateType mJob.LocalRateType ???
        etLocalLabor.setText(mJob.LocalLabor);
        etNumberMan.setText(mJob.NumberMen);
        //spNumberMan simple ???
        //spNumberTruck ???
        etLocalTravel.setText(mJob.LocalTravel);
        etLocalRate.setText(mJob.LocalRate);
        etTotalPacking.setText(mJob.TotalPacking);
        etLocalTravelPlusLocalLabor.setText(mJob.LocalLabor + mJob.LocalTravel);
        etEstimatedTotal.setText(mJob.EstimatedTotal); //finished not found
        etActualTotalFinal.setText(mJob.ActualTotal); //finished not found
    }

    private void setFromDetailsCheck() {
        //cbHaveNotesFrom.setChecked(mJob.); not found
        //cbHaveStopsFrom not found
        //cbHavePackageFrom not found
        //cbHaveTacksFrom not found
        //cbHaveTrailerAccessFrom not found
        etFromStairsFrom.setText(mJob.from_stairs);
    }

    private void setToDetailsCheck() {
        //cbHaveNotesTo.setChecked(mJob.); not found
        //cbHaveStopsTo not found
        //cbHavePackageTo not found
        //cbHaveTacksTo not found
        //cbHaveTrailerAccessTo not found
        cbFromStairsTo.setText(mJob.to_stairs);
    }
}
