package com.calldorado.appvestor.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.calldorado.appvestor.data.db.entity.Investments;

import java.util.List;

@Dao
public interface InvestmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Investments> investments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Investments investments);


    @Update
    void update(Investments investments);

    @Delete
    void delete(Investments investments);

    @Query("SELECT * FROM Investments")
    LiveData<List<Investments>> findAll();


    @Query("DELETE FROM Investments")
    void deleteAll();
}
