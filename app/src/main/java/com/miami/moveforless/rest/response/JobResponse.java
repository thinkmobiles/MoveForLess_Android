package com.miami.moveforless.rest.response;

import com.google.gson.annotations.SerializedName;
import com.miami.moveforless.database.MoveForLessDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by SetKrul on 30.10.2015.
 */

@Table(databaseName = MoveForLessDatabase.NAME)
public class JobResponse extends BaseModel {



    @Column
    @PrimaryKey(autoincrement = true)
    public long _id;

    @Column
    public Integer isActive;

    @Column
    @SerializedName("ID")
    public Integer id;
    @Column
    @SerializedName("post_author")
    public String post_author;
    @Column
    @SerializedName("post_date")
    public String post_date;
    @Column
    @SerializedName("post_date_gmt")
    public String post_date_gmt;
    @Column
    @SerializedName("post_content")
    public String post_content;
    @Column
    @SerializedName("post_title")
    public String post_title;
    @Column
    @SerializedName("post_excerpt")
    public String post_excerpt;
    @Column
    @SerializedName("post_status")
    public String post_status;
    @Column
    @SerializedName("comment_status")
    public String comment_status;
    //@SerializedName("ping_status");
    //public String ping_status;
    @Column
    @SerializedName("post_password")
    public String post_password;
    @Column
    @SerializedName("post_name")
    public String post_name;
    @Column
    @SerializedName("to_ping")
    public String to_ping;
    @Column
    @SerializedName("pinged")
    public String pinged;
    @Column
    @SerializedName("post_modified")
    public String post_modified;
    @Column
    @SerializedName("post_modified_gmt")
    public String post_modified_gmt;
    @Column
    @SerializedName("post_content_filtered")
    public String post_content_filtered;
    @Column
    @SerializedName("post_parent")
    public Integer post_parent;
    @Column
    @SerializedName("guid")
    public String guid;
    @Column
    @SerializedName("menu_order")
    public Integer menu_order;
    @Column
    @SerializedName("post_type")
    public String post_type;
    @Column
    @SerializedName("post_mime_type")
    public String post_mime_type;
    @Column
    @SerializedName("comment_count")
    public String comment_count;
    @Column
    @SerializedName("filter")
    public String filter;
    @Column
    @SerializedName("views")
    public String views;
    @Column
    @SerializedName("manager")
    public String manager;
    @Column
    @SerializedName("partner")
    public String partner;
    @Column
    @SerializedName("commission_partner")
    public String commission_partner;
    @Column
    @SerializedName("hear_about_us")
    public String hear_about_us;
    @Column
    @SerializedName("LocalLabor")
    public String LocalLabor;
    @Column
    @SerializedName("CreatedDate")
    public Integer CreatedDate;
    @Column
    @SerializedName("ReceivedDate")
    public Integer ReceivedDate;
    @Column
    @SerializedName("RCVD")
    public String RCVD;
    @Column
    @SerializedName("EstimatedDate")
    public Integer EstimatedDate;
    @Column
    @SerializedName("BookingDate")
    public Integer BookingDate;
    @Column
    @SerializedName("RequiredPickupDate")
    public Integer RequiredPickupDate;
    @Column
    @SerializedName("FollowUpDate")
    public Integer FollowUpDate;
    @Column
    @SerializedName("StartTime")
    public String StartTime;
    @Column
    @SerializedName("StopTime")
    public String StopTime;
    @Column
    @SerializedName("pickup_date")
    public Integer pickup_date;
    @Column
    @SerializedName("from_fullname")
    public String from_fullname;
    @Column
    @SerializedName("from_email")
    public String from_email;
    @Column
    @SerializedName("from_phone")
    public String from_phone;
    @Column
    @SerializedName("from_cellphone")
    public String from_cellphone;
    @Column
    @SerializedName("from_fax")
    public String from_fax;
    @Column
    @SerializedName("from_address")
    public String from_address;
    @Column
    @SerializedName("from_apt")
    public String from_apt;
    @Column
    @SerializedName("from_zipcode")
    public String from_zipcode;
    @Column
    @SerializedName("from_city")
    public String from_city;
    @Column
    @SerializedName("from_state")
    public String from_state;
    @Column
    @SerializedName("to_fullname")
    public String to_fullname;
    @Column
    @SerializedName("to_email")
    public String to_email;
    @Column
    @SerializedName("to_phone")
    public String to_phone;
    @Column
    @SerializedName("to_cellphone")
    public String to_cellphone;
    @Column
    @SerializedName("to_fax")
    public String to_fax;
    @Column
    @SerializedName("to_address")
    public String to_address;
    @Column
    @SerializedName("to_apt")
    public String to_apt;
    @Column
    @SerializedName("to_zipcode")
    public String to_zipcode;
    @Column
    @SerializedName("to_city")
    public String to_city;
    @Column
    @SerializedName("to_state")
    public String to_state;
    @Column
    @SerializedName("DistanceTotal")
    public String DistanceTotal;
    @Column
    @SerializedName("DistanceOffice")
    public String DistanceOffice;
    @Column
    @SerializedName("from_elevator_start")
    public String from_elevator_start;
    @Column
    @SerializedName("from_elevator_end")
    public String from_elevator_end;
    @Column
    @SerializedName("to_elevator_start")
    public String to_elevator_start;
    @Column
    @SerializedName("to_elevator_end")
    public String to_elevator_end;
    @Column
    @SerializedName("there_any_stairs")
    public String there_any_stairs;
    @Column
    @SerializedName("from_trailer_access")
    public String from_trailer_access;
    @Column
    @SerializedName("is_from_insurance")
    public String is_from_insurance;
    @Column
    @SerializedName("is_to_insurance")
    public String is_to_insurance;
    @Column
    @SerializedName("NumberMen")
    public String NumberMen;
    @Column
    @SerializedName("number_truck")
    public String number_truck;
    @Column
    @SerializedName("additional_inf_bedrooms")
    public String additional_inf_bedrooms;
    @Column
    @SerializedName("LocalRateType")
    public String LocalRateType;
    @Column
    @SerializedName("LocalNumberEstimate")
    public String LocalNumberEstimate;
    @Column
    @SerializedName("LocalTravel")
    public String LocalTravel;
    @Column
    @SerializedName("LocalRate")
    public String LocalRate;
    @Column
    @SerializedName("EstimatedTotal")
    public String EstimatedTotal;
    @Column
    @SerializedName("additional_inf_bedrooms_sets")
    public String additional_inf_bedrooms_sets;
    @Column
    @SerializedName("kind_of_building")
    public String kind_of_building;
    @Column
    @SerializedName("apartment_fully_furnished")
    public String apartment_fully_furnished;
    @Column
    @SerializedName("any_time_restrictions")
    public String any_time_restrictions;
    @Column
    @SerializedName("packing_miscellaneous_items")
    public String packing_miscellaneous_items;
    @Column
    @SerializedName("fragile_items")
    public String fragile_items;
    @Column
    @SerializedName("bubble_wrap")
    public String bubble_wrap;
    @Column
    @SerializedName("reason_cancel")
    public String reason_cancel;
    @Column
    @SerializedName("reason_cancel_date")
    public Integer reason_cancel_date;
    @Column
    @SerializedName("Estimator")
    public String Estimator;
    @Column
    @SerializedName("driver")
    public String driver;
    @Column
    @SerializedName("mover")
    public String mover;
    @Column
    @SerializedName("movers")
    public String movers;
    @Column
    @SerializedName("ActualPickupDate")
    public Integer ActualPickupDate;
    @Column
    @SerializedName("RequiredDeliveryDate")
    public Integer RequiredDeliveryDate;
    @Column
    @SerializedName("RatePerCF")
    public String RatePerCF;
    @Column
    @SerializedName("TotalCF")
    public String TotalCF;
    @Column
    @SerializedName("PickupBalance")
    public String PickupBalance;
    @Column
    @SerializedName("TotalPacking")
    public String TotalPacking;
    @Column
    @SerializedName("TotalLB")
    public String TotalLB;
    @Column
    @SerializedName("DeliveryBalance")
    public String DeliveryBalance;
    @Column
    @SerializedName("GrandTotal")
    public String GrandTotal;
    @Column
    @SerializedName("ActualTotal")
    public String ActualTotal;
    @Column
    @SerializedName("estimated_total")
    public String estimated_total;
    @Column
    @SerializedName("EstimatedPackingTax")
    public String EstimatedPackingTax;
    @Column
    @SerializedName("ActualPackingTax")
    public String ActualPackingTax;
    @Column
    @SerializedName("agent_commission")
    public String agent_commission;
    @Column
    @SerializedName("total_salary")
    public String total_salary;
    @Column
    @SerializedName("total_expense")
    public String total_expense;
    @Column
    @SerializedName("is_from_repeated")
    public String is_from_repeated;
    @Column
    @SerializedName("is_to_repeated")
    public String is_to_repeated;
    @Column
    @SerializedName("is_referral")
    public String is_referral;
    @Column
    @SerializedName("from_mobile")
    public String from_mobile;
    @Column
    @SerializedName("from_stairs")
    public String from_stairs;
    @Column
    @SerializedName("to_mobile")
    public String to_mobile;
    @Column
    @SerializedName("to_trailer_access")
    public String to_trailer_access;
    @Column
    @SerializedName("to_stairs")
    public String to_stairs;
    @Column
    @SerializedName("truck_id")
    public String truck_id;
    @Column
    @SerializedName("Stop1")
    public String Stop1;
    @Column
    @SerializedName("Stop2")
    public String Stop2;
    @Column
    @SerializedName("Stop3")
    public String Stop3;
    @Column
    @SerializedName("driver_name")
    public String driver_name;
    @Column
    @SerializedName("agent_name")
    public String agent_name;
    @Column
    @SerializedName("company_name")
    public String company_name;
    @Column
    @SerializedName("move_size_name")
    public String move_size_name;
    @Column
    @SerializedName("job_code")
    public String job_code;
    @Column
    @SerializedName("status_slug")
    public String status_slug;


    public JobResponse() {
    }

//    protected JobResponse(Parcel in) {
//        id = in.readInt();
//        post_parent = in.readInt();
//        menu_order = in.readInt();
//        CreatedDate = in.readInt();
//        ReceivedDate = in.readInt();
//        EstimatedDate = in.readInt();
//        BookingDate = in.readInt();
//        RequiredPickupDate = in.readInt();
//        FollowUpDate = in.readInt();
//        pickup_date = in.readInt();
//        reason_cancel_date = in.readInt();
//        ActualPickupDate = in.readInt();
//        RequiredDeliveryDate = in.readInt();
//        post_author = in.readString();
//        post_date = in.readString();
//        post_date_gmt = in.readString();
//        post_content = in.readString();
//        post_title = in.readString();
//        post_excerpt = in.readString();
//        post_status = in.readString();
//        comment_status = in.readString();
//        post_password = in.readString();
//        post_name = in.readString();
//        to_ping = in.readString();
//        pinged = in.readString();
//        post_modified = in.readString();
//        post_modified_gmt = in.readString();
//        post_content_filtered = in.readString();
//        guid = in.readString();
//        post_type = in.readString();
//        post_mime_type = in.readString();
//        comment_count = in.readString();
//        filter = in.readString();
//        views = in.readString();
//        manager = in.readString();
//        partner = in.readString();
//        commission_partner = in.readString();
//        hear_about_us = in.readString();
//        LocalLabor = in.readString();
//        RCVD = in.readString();
//        StartTime = in.readString();
//        StopTime = in.readString();
//        from_fullname = in.readString();
//        from_email = in.readString();
//        from_phone = in.readString();
//        from_cellphone = in.readString();
//        from_fax = in.readString();
//        from_address = in.readString();
//        from_apt = in.readString();
//        from_zipcode = in.readString();
//        from_city = in.readString();
//        from_state = in.readString();
//        to_fullname = in.readString();
//        to_email = in.readString();
//        to_phone = in.readString();
//        to_cellphone = in.readString();
//        to_fax = in.readString();
//        to_address = in.readString();
//        to_apt = in.readString();
//        to_zipcode = in.readString();
//        to_city = in.readString();
//        to_state = in.readString();
//        DistanceTotal = in.readString();
//        DistanceOffice = in.readString();
//        from_elevator_start = in.readString();
//        from_elevator_end = in.readString();
//        to_elevator_start = in.readString();
//        to_elevator_end = in.readString();
//        there_any_stairs = in.readString();
//        from_trailer_access = in.readString();
//        is_from_insurance = in.readString();
//        is_to_insurance = in.readString();
//        NumberMen = in.readString();
//        number_truck = in.readString();
//        additional_inf_bedrooms = in.readString();
//        LocalRateType = in.readString();
//        LocalNumberEstimate = in.readString();
//        LocalTravel = in.readString();
//        LocalRate = in.readString();
//        EstimatedTotal = in.readString();
//        additional_inf_bedrooms_sets = in.readString();
//        kind_of_building = in.readString();
//        apartment_fully_furnished = in.readString();
//        any_time_restrictions = in.readString();
//        packing_miscellaneous_items = in.readString();
//        fragile_items = in.readString();
//        bubble_wrap = in.readString();
//        reason_cancel = in.readString();
//        Estimator = in.readString();
//        driver = in.readString();
//        mover = in.readString();
//        movers = in.readString();
//        RatePerCF = in.readString();
//        TotalCF = in.readString();
//        PickupBalance = in.readString();
//        TotalPacking = in.readString();
//        TotalLB = in.readString();
//        DeliveryBalance = in.readString();
//        GrandTotal = in.readString();
//        ActualTotal = in.readString();
//        estimated_total = in.readString();
//        EstimatedPackingTax = in.readString();
//        ActualPackingTax = in.readString();
//        agent_commission = in.readString();
//        total_salary = in.readString();
//        total_expense = in.readString();
//        is_from_repeated = in.readString();
//        is_to_repeated = in.readString();
//        is_referral = in.readString();
//        from_mobile = in.readString();
//        from_stairs = in.readString();
//        to_mobile = in.readString();
//        to_trailer_access = in.readString();
//        to_stairs = in.readString();
//        truck_id = in.readString();
//        Stop1 = in.readString();
//        Stop2 = in.readString();
//        Stop3 = in.readString();
//        driver_name = in.readString();
//        agent_name = in.readString();
//        company_name = in.readString();
//        move_size_name = in.readString();
//        job_code = in.readString();
//        status_slug = in.readString();
//    }

//    public static final Creator<JobResponse> CREATOR = new Creator<JobResponse>() {
//        @Override
//        public JobResponse createFromParcel(Parcel in) {
//            return new JobResponse(in);
//        }
//
//        @Override
//        public JobResponse[] newArray(int size) {
//            return new JobResponse[size];
//        }
//    };

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeInt(post_parent);
//        dest.writeInt(menu_order);
//        dest.writeInt(CreatedDate);
//        dest.writeInt(ReceivedDate);
//        dest.writeInt(EstimatedDate);
//        dest.writeInt(BookingDate);
//        dest.writeInt(RequiredPickupDate);
//        dest.writeInt(FollowUpDate);
//        dest.writeInt(pickup_date);
//        dest.writeInt(reason_cancel_date);
//        dest.writeInt(ActualPickupDate);
//        dest.writeInt(RequiredDeliveryDate);
//        dest.writeString(post_author);
//        dest.writeString(post_date);
//        dest.writeString(post_date_gmt);
//        dest.writeString(post_content);
//        dest.writeString(post_title);
//        dest.writeString(post_excerpt);
//        dest.writeString(post_status);
//        dest.writeString(comment_status);
//        dest.writeString(post_password);
//        dest.writeString(post_name);
//        dest.writeString(to_ping);
//        dest.writeString(pinged);
//        dest.writeString(post_modified);
//        dest.writeString(post_modified_gmt);
//        dest.writeString(post_content_filtered);
//        dest.writeString(guid);
//        dest.writeString(post_type);
//        dest.writeString(post_mime_type);
//        dest.writeString(comment_count);
//        dest.writeString(filter);
//        dest.writeString(views);
//        dest.writeString(manager);
//        dest.writeString(partner);
//        dest.writeString(commission_partner);
//        dest.writeString(hear_about_us);
//        dest.writeString(LocalLabor);
//        dest.writeString(RCVD);
//        dest.writeString(StartTime);
//        dest.writeString(StopTime);
//        dest.writeString(from_fullname);
//        dest.writeString(from_email);
//        dest.writeString(from_phone);
//        dest.writeString(from_cellphone);
//        dest.writeString(from_fax);
//        dest.writeString(from_address);
//        dest.writeString(from_apt);
//        dest.writeString(from_zipcode);
//        dest.writeString(from_city);
//        dest.writeString(from_state);
//        dest.writeString(to_fullname);
//        dest.writeString(to_email);
//        dest.writeString(to_phone);
//        dest.writeString(to_cellphone);
//        dest.writeString(to_fax);
//        dest.writeString(to_address);
//        dest.writeString(to_apt);
//        dest.writeString(to_zipcode);
//        dest.writeString(to_city);
//        dest.writeString(to_state);
//        dest.writeString(DistanceTotal);
//        dest.writeString(DistanceOffice);
//        dest.writeString(from_elevator_start);
//        dest.writeString(from_elevator_end);
//        dest.writeString(to_elevator_start);
//        dest.writeString(to_elevator_end);
//        dest.writeString(there_any_stairs);
//        dest.writeString(from_trailer_access);
//        dest.writeString(is_from_insurance);
//        dest.writeString(is_to_insurance);
//        dest.writeString(NumberMen);
//        dest.writeString(number_truck);
//        dest.writeString(additional_inf_bedrooms);
//        dest.writeString(LocalRateType);
//        dest.writeString(LocalNumberEstimate);
//        dest.writeString(LocalTravel);
//        dest.writeString(LocalRate);
//        dest.writeString(EstimatedTotal);
//        dest.writeString(additional_inf_bedrooms_sets);
//        dest.writeString(kind_of_building);
//        dest.writeString(apartment_fully_furnished);
//        dest.writeString(any_time_restrictions);
//        dest.writeString(packing_miscellaneous_items);
//        dest.writeString(fragile_items);
//        dest.writeString(bubble_wrap);
//        dest.writeString(reason_cancel);
//        dest.writeString(Estimator);
//        dest.writeString(driver);
//        dest.writeString(mover);
//        dest.writeString(movers);
//        dest.writeString(RatePerCF);
//        dest.writeString(TotalCF);
//        dest.writeString(PickupBalance);
//        dest.writeString(TotalPacking);
//        dest.writeString(TotalLB);
//        dest.writeString(DeliveryBalance);
//        dest.writeString(GrandTotal);
//        dest.writeString(ActualTotal);
//        dest.writeString(estimated_total);
//        dest.writeString(EstimatedPackingTax);
//        dest.writeString(ActualPackingTax);
//        dest.writeString(agent_commission);
//        dest.writeString(total_salary);
//        dest.writeString(total_expense);
//        dest.writeString(is_from_repeated);
//        dest.writeString(is_to_repeated);
//        dest.writeString(is_referral);
//        dest.writeString(from_mobile);
//        dest.writeString(from_stairs);
//        dest.writeString(to_mobile);
//        dest.writeString(to_trailer_access);
//        dest.writeString(to_stairs);
//        dest.writeString(truck_id);
//        dest.writeString(Stop1);
//        dest.writeString(Stop2);
//        dest.writeString(Stop3);
//        dest.writeString(driver_name);
//        dest.writeString(agent_name);
//        dest.writeString(company_name);
//        dest.writeString(move_size_name);
//        dest.writeString(job_code);
//        dest.writeString(status_slug);
//    }
}
