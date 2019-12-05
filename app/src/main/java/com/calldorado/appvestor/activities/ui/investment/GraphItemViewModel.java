package com.calldorado.appvestor.activities.ui.investment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.calldorado.appvestor.data.GraphItemDao;
import com.calldorado.appvestor.data.db.AppvestorDatabase;
import com.calldorado.appvestor.data.db.entity.GraphItem;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GraphItemViewModel extends AndroidViewModel {

    private GraphItemDao graphItemDao;
    private ExecutorService executorService;

    public GraphItemViewModel(@NonNull Application application) {
        super(application);
        graphItemDao = AppvestorDatabase.getInstance(application).graphItemDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    List<GraphItem> getAllByIdAndDate(String investment_id) {
        return graphItemDao.findAll(investment_id);
    }
    LiveData<List<GraphItem>> getAllByIdAndDate () {
        return graphItemDao.findAll();
    }

    public void savegraph(List<GraphItem> graphItem) {

        executorService.execute(() -> graphItemDao.saveAll(graphItem));
    }
    void deleteAll() {
        executorService.execute(() -> graphItemDao.deleteAll());
    }

    List<String> getIvenstmentIds(){
        return graphItemDao.getInvestmentIds();
    }

}