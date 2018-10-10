package com.home.paginglocaldemo.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.home.paginglocaldemo.app.Constants;
import com.home.paginglocaldemo.db.entity.AnimalEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AnimalListViewModel extends AndroidViewModel {

    private AnimalRepository mRepository;
    private LiveData<PagedList<AnimalEntity>> mAnimals;

    /**
     * PagedList会从Datasource中加载数据, 更准确的说是通过Datasource加载数据
     * 通过Config的配置, 可以设置一次加载的数量以及预加载的数量
     */
    private final static PagedList.Config config
            = new PagedList.Config.Builder()
            .setPageSize(Constants.PAGE_SIZE)                                                       // 设置每页加载的数量
            .setInitialLoadSizeHint(Constants.PAGE_INITIAL_LOAD_SIZE_HINT)                         // 初始化数据时加载的数量
            .setPrefetchDistance(Constants.PAGE_PREFETCH_DISTANCE)                               // 预加载的数量
            .setEnablePlaceholders(true)                                                            // 当item为null是否使用PlaceHolder展示
            .build();

    public AnimalListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new AnimalRepository(application);
    }

    public LiveData<PagedList<AnimalEntity>> getAnimals() {
        if (mAnimals == null) {
            mAnimals = mRepository.getAllAnimals(config);
        }
        return mAnimals;
    }

    public void insertSampleData() {
        mRepository.insertSampleAnimals();
    }

    public void insertAnimals(List<AnimalEntity> animals) {
        mRepository.insertAnimals(animals);
    }

    public AnimalEntity getAnimal(int id) throws ExecutionException, InterruptedException {
        return mRepository.getAnimal(id);
    }

    public void updateAnimal(AnimalEntity animal) {
        mRepository.updateAnimal(animal);
    }
}
