package com.home.paginglocaldemo.main;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.AsyncTask;

import com.home.paginglocaldemo.db.AppDatabase;
import com.home.paginglocaldemo.db.DataGenerator;
import com.home.paginglocaldemo.db.dao.AnimalDao;
import com.home.paginglocaldemo.db.entity.AnimalEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AnimalRepository {

    private AnimalDao mAnimalDao;

    public AnimalRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mAnimalDao = db.animalDao();
    }

    public void insertSampleAnimals() {
        insertAnimals(DataGenerator.getSampleAnimalList());
    }

    public LiveData<PagedList<AnimalEntity>> getAllAnimals(PagedList.Config config) {

        /** 取得資料庫中的所有排序後的資料, 並放入factory */
        DataSource.Factory<Integer, AnimalEntity> factory = mAnimalDao.getAllAnimalsPaged();

        return new LivePagedListBuilder<>(factory, config)
                .build();
    }

    public AnimalEntity getAnimal(int id) throws ExecutionException, InterruptedException {
        return new getAnimalAsync(mAnimalDao).execute(id).get();
    }

    public void updateAnimal(AnimalEntity animal) {
        AsyncTask.execute(() -> {
            mAnimalDao.update(animal);
        });
    }

    public void insertAnimal(AnimalEntity animal) {
        new insertAnimalAsync(mAnimalDao).execute(animal);
    }

    public void insertAnimals(List<AnimalEntity> animals) {
        new insertAnimalsAsync(mAnimalDao).execute(animals);
    }

    private static class getAnimalAsync extends AsyncTask<Integer, Void, AnimalEntity> {

        private AnimalDao mAnimalDaoAsync;

        getAnimalAsync(AnimalDao animalDao) {
            mAnimalDaoAsync = animalDao;
        }

        @Override
        protected AnimalEntity doInBackground(Integer... ids) {
            return mAnimalDaoAsync.getAnimal(ids[0]);
        }
    }

    private static class insertAnimalAsync extends AsyncTask<AnimalEntity, Void, Long> {

        private AnimalDao mAnimalDaoAsync;

        insertAnimalAsync(AnimalDao noteDao) {
            mAnimalDaoAsync = noteDao;
        }

        @Override
        protected Long doInBackground(AnimalEntity... animals) {
            long id = mAnimalDaoAsync.insert(animals[0]);
            return id;
        }
    }

    private static class insertAnimalsAsync extends AsyncTask<List<AnimalEntity>, Void, Void> {

        private AnimalDao mAnimalDaoAsync;

        insertAnimalsAsync(AnimalDao noteDao) {
            mAnimalDaoAsync = noteDao;
        }

        @Override
        protected Void doInBackground(List<AnimalEntity>... animals) {
            mAnimalDaoAsync.insertAnimals(animals[0]);
            return null;
        }
    }
}
