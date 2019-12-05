package com.calldorado.appvestor.activities.ui.investment.interfaces;

import com.calldorado.appvestor.data.db.entity.Investments;

import java.util.List;

public interface InvestmentInterface {

    void onDataDone(List<Investments> data);
}
