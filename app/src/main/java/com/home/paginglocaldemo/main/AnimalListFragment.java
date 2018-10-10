package com.home.paginglocaldemo.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.home.paginglocaldemo.R;
import com.home.paginglocaldemo.db.entity.AnimalEntity;
import com.home.paginglocaldemo.utils.MyDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AnimalListFragment extends Fragment implements AnimalsAdapter.AnimalsAdapterListener {

    private AnimalListViewModel mViewModel;
    private Unbinder unbinder;
    private AnimalsAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static AnimalListFragment newInstance() {
        return new AnimalListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animal_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AnimalsAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 當加入本Fragment的Activity被建立時, 該Activity的onCreate方法執行完成後, 會自動執行此方法
     * 執行完此方法後, Fragment才出現在畫面上
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /** 透過ViewModelProviders協助我們取得ViewModel, 其中of()的參數代表著ViewModel的生命範圍(scope)
         * 在MainActivity中用of(this), 表示ViewModel的生命週期會持續到MainActivity不再活動(destroy且沒有re-create)為止
         * 只要MainActivity還在活動中, ViewModel就不會被清除, 每次create都可以取得同一個ViewModel */
        mViewModel = ViewModelProviders.of(this).get(AnimalListViewModel.class);

        /** 通过 observe()方法连接观察者和LiveData
         * observe()方法需要携带一个LifecycleOwner类, 这样就可以让观察者订阅LiveData中的数据, 实现实时更新 */
        mViewModel.getAnimals().observe(this, new Observer<PagedList<AnimalEntity>>() {
            @Override
            public void onChanged(@Nullable PagedList<AnimalEntity> animals) {
                if (animals == null || animals.size() == 0) {
                    mViewModel.insertSampleData();                                                  // add data when data is empty
                }
                mAdapter.submitList(animals);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onAnimalClick(int id) {
        Toast.makeText(getContext(), "onAnimalClick, id: " + id, Toast.LENGTH_SHORT).show();
    }
}
