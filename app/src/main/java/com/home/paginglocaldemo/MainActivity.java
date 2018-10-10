package com.home.paginglocaldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.home.paginglocaldemo.main.AnimalListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /** 先判斷Bundle是否null, 如果是null 就加載AnimalListFragment, 讓它顯示出來 */
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AnimalListFragment.newInstance())
                    .commitNow();                                                                   // 同步提交事务
        }
    }
}
