package com.home.paginglocaldemo.db.dao;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.home.paginglocaldemo.db.entity.AnimalEntity;

import java.util.List;

@Dao
public interface AnimalDao {

    /** 搜尋animals表格, 並顯示所有欄位資料, 並將資料按照name順序排列 */
    /** ASC代表結果會以由小往大的順序列出 */
    @Query("SELECT * FROM animals ORDER BY name ASC")
    DataSource.Factory<Integer, AnimalEntity> getAllAnimalsPaged();

    /** 搜尋animals表格, 並選擇所有欄位資料, 然後只取出符合傳入id的相關所有欄位資料 */
    @Query("SELECT * FROM animals WHERE id=:id")
    AnimalEntity getAnimal(int id);

    /** 將animals插入數據庫的實現 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnimals(List<AnimalEntity> animals);

    /** 將animal插入數據庫的實現 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AnimalEntity animal);

    /** 更新數據庫 */
    @Update
    void update(AnimalEntity animal);


    @Query("DELETE FROM animals")
    void deleteAll();
}
