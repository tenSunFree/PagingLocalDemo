package com.home.paginglocaldemo.main;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.home.paginglocaldemo.R;
import com.home.paginglocaldemo.db.entity.AnimalEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimalsAdapter extends PagedListAdapter<AnimalEntity, AnimalsAdapter.MyViewHolder> {

    private static final String TAG = AnimalsAdapter.class.getSimpleName();

    private AnimalsAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView note;
        @BindView(R.id.pictureImageView)
        ImageView pictureImageView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAnimalClick(getAnimal(getLayoutPosition()).getId());
                }
            });
        }
    }

    public AnimalsAdapter(AnimalsAdapterListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_animal, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AnimalEntity animal = getAnimal(position);
        if (animal != null) {
            String name = animal.getName().substring(0, 1).toUpperCase() + animal.getName().substring(1);
            holder.note.setText(name);
            int pictureId = animal.getPictureId();
            holder.pictureImageView.setImageResource(pictureId);
        }
    }

    public AnimalEntity getAnimal(int position) {
        return getItem(position);
    }

    /**
     * 主要是为了配合RecyclerView 使用, 通过比对新旧两个数据集的差异, 生成旧数据到新数据的最小变动
     * 然后对有变动的数据项, 进行局部刷新
     */
    private static final DiffUtil.ItemCallback<AnimalEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AnimalEntity>() {

                /** 判断是否是同一个Item */
                @Override
                public boolean areItemsTheSame(@NonNull AnimalEntity oldItem, @NonNull AnimalEntity newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                /** 如果是通一个Item, 此方法用于判断是否同一个 Item 的内容也相同 */
                @Override
                public boolean areContentsTheSame(@NonNull AnimalEntity oldItem, @NonNull AnimalEntity newItem) {
                    return oldItem.getId() == newItem.getId() && oldItem.getName().equalsIgnoreCase(newItem.getName());
                }
            };

    /** 提供外部使用的接口, 可以進而控制對方 */
    public interface AnimalsAdapterListener {
        void onAnimalClick(int id);
    }
}
