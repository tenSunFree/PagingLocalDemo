package com.home.paginglocaldemo.db;

import com.home.paginglocaldemo.R;
import com.home.paginglocaldemo.db.entity.AnimalEntity;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    private static final String[] animals = new String[]{
            "albatross", "anteater", "baboon", "cassowary",
            "cod", "dinosaur", "eland", "ferret", "heron"
    };

    private static final int[] animalPictures = new int[]{
            R.drawable.icon_albatross, R.drawable.icon_anteater, R.drawable.icon_baboon,
            R.drawable.icon_cassowary, R.drawable.icon_cod, R.drawable.icon_dinosaur,
            R.drawable.icon_eland, R.drawable.icon_ferret, R.drawable.icon_heron
    };

    public static List<AnimalEntity> getSampleAnimalList() {
        List<AnimalEntity> animalList = new ArrayList<>();
        for (int i = 0; i < animals.length; i++) {
            animalList.add(new AnimalEntity(i + 1, animals[i], animalPictures[i]));
        }
        return animalList;
    }
}
