package com.calldorado.appvestor.activities.ui.investment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.calldorado.appvestor.data.InvestmentDao;
import com.calldorado.appvestor.data.db.AppvestorDatabase;
import com.calldorado.appvestor.data.db.entity.Investments;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InvestmentViewModel extends AndroidViewModel {

    private InvestmentDao investmentDao;
    private ExecutorService executorService;

    public InvestmentViewModel(@NonNull Application application) {
        super(application);
        investmentDao = AppvestorDatabase.getInstance(application).investmentDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Investments>> getAllInvestments() {
        return investmentDao.findAll();
    }


    void savePost(Investments post) {
        executorService.execute(() -> investmentDao.save(post));
    }
    void updateInvestment(Investments post) {
        executorService.execute(() -> investmentDao.update(post));
    }

    void deleteAll() {
        executorService.execute(() -> investmentDao.deleteAll());
    }
}