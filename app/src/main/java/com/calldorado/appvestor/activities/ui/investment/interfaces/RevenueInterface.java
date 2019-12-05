package com.calldorado.appvestor.activities.ui.investment.interfaces;


import com.calldorado.appvestor.data.db.entity.GraphItem;
import com.calldorado.appvestor.data.models.GraphItems;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public interface RevenueInterface {

    void onDataDone(ArrayList<GraphItem> dateArrayListMap);
}
