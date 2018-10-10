package com.home.paginglocaldemo.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/** 要寫入資料庫的 Java bean */
@Entity(tableName = "animals")
public class AnimalEntity {

    @PrimaryKey(autoGenerate = true)
    int id;

    String name;
    int pictureId;

    public AnimalEntity(int id, String name, int pictureId) {
        this.id = id;
        this.name = name;
        this.pictureId = pictureId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
