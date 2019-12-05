package com.calldorado.appvestor.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.calldorado.appvestor.data.db.entity.GraphItem;
import com.calldorado.appvestor.data.db.entity.Investments;

import java.util.List;

@Dao
public interface GraphItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<GraphItem> graphItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(GraphItem graphItem);


    @Update
    void update(GraphItem graphItem);

    @Delete
    void delete(GraphItem graphItem);

    @Query("SELECT * FROM GraphItem WHERE investment_id_ = :investment_id")
    List<GraphItem>findAll(String investment_id);

    @Query("SELECT * FROM GraphItem")
    LiveData<List<GraphItem>>findAll();

    @Query("SELECT DISTINCT investment_id_ FROM graphitem")
    List<String> getInvestmentIds();

    @Query("DELETE FROM GraphItem")
    void deleteAll();

}
