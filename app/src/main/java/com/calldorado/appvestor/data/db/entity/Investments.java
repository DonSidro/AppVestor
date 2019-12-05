package com.calldorado.appvestor.data.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Investments {

    @PrimaryKey(autoGenerate = true)
    int id;
    String id_;
    String created_;
    String state_;
    String application_id_;
    String application_name_;
    String application_package_;
    String wallet_id_;
    String amount_invested_;
    String amount_activated_;
    String amount_used_;
    String amount_earned_;
    String roi_after_day_;
    String color_;

    public Investments(){}


    @Ignore
    public Investments(String id_, String created_, String state_, String application_id_, String application_name_, String application_package_, String wallet_id_, String amount_invested_
    ,String amount_activated_, String amount_used_, String amount_earned_, String roi_after_day_, String color_){
        this.id_ = id_;
        this.created_ = created_;
        this.state_ = state_;
        this.application_id_ = application_id_;
        this.application_name_ = application_name_;
        this.application_package_ = application_package_;
        this.wallet_id_ = wallet_id_;
        this.amount_invested_ = amount_invested_;
        this.amount_activated_ = amount_activated_;
        this.amount_used_ = amount_used_;
        this.amount_earned_ = amount_earned_;
        this.roi_after_day_ = roi_after_day_;
        this.color_ = color_;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public String getCreated_() {
        return created_;
    }

    public void setCreated_(String created_) {
        this.created_ = created_;
    }

    public String getState_() {
        return state_;
    }

    public void setState_(String state_) {
        this.state_ = state_;
    }

    public String getApplication_id_() {
        return application_id_;
    }

    public void setApplication_id_(String application_id_) {
        this.application_id_ = application_id_;
    }

    public String getApplication_name_() {
        return application_name_;
    }

    public void setApplication_name_(String application_name_) {
        this.application_name_ = application_name_;
    }

    public String getApplication_package_() {
        return application_package_;
    }

    public void setApplication_package_(String application_package_) {
        this.application_package_ = application_package_;
    }

    public String getWallet_id_() {
        return wallet_id_;
    }

    public void setWallet_id_(String wallet_id_) {
        this.wallet_id_ = wallet_id_;
    }

    public String getAmount_invested_() {
        return amount_invested_;
    }

    public void setAmount_invested_(String amount_invested_) {
        this.amount_invested_ = amount_invested_;
    }

    public String getAmount_activated_() {
        return amount_activated_;
    }

    public void setAmount_activated_(String amount_activated_) {
        this.amount_activated_ = amount_activated_;
    }

    public String getAmount_used_() {
        return amount_used_;
    }

    public void setAmount_used_(String amount_used_) {
        this.amount_used_ = amount_used_;
    }

    public String getAmount_earned_() {
        return amount_earned_;
    }

    public void setAmount_earned_(String amount_earned_) {
        this.amount_earned_ = amount_earned_;
    }

    public String getRoi_after_day_() {
        return roi_after_day_;
    }

    public void setRoi_after_day_(String roi_after_day_) {
        this.roi_after_day_ = roi_after_day_;
    }

    public String getColor_() {
        return color_;
    }

    public void setColor_(String color_) {
        this.color_ = color_;
    }
}
