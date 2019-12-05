package com.calldorado.appvestor.data.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class GraphItem {

    @PrimaryKey(autoGenerate = true)
    int id;

    String date_;
    String investment_id_;
    String amount_;
    String color_;


    public GraphItem(){}


    @Ignore
    public GraphItem(String date, String investment_id, String amount, String color){
        this.date_ = date;
        this.investment_id_ = investment_id;
        this.amount_ = amount;
        this.color_ = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_() {
        return date_;
    }

    public void setDate_(String date_) {
        this.date_ = date_;
    }

    public String getInvestment_id_() {
        return investment_id_;
    }

    public void setInvestment_id_(String investment_id_) {
        this.investment_id_ = investment_id_;
    }

    public String getAmount_() {
        return amount_;
    }

    public void setAmount_(String amount_) {
        this.amount_ = amount_;
    }


    public String getColor_() {
        return color_;
    }

    public void setColor_(String color_) {
        this.color_ = color_;
    }
}
