package com.example.recyclerviewwithmultiselect;

import java.util.Arrays;
import java.util.List;

/**
 * Created by maciejwitowski on 3/14/15.
 */
public class ItemsProducer {

    public static final String[] ITEMS = new String[]{
        "about", "after", "again", "air", "all",
        "back", "be", "because", "been", "before", "below",
        "came", "can", "come", "could",
        "day", "did", "different"
    };

    public static List<String> getItems() {
        return Arrays.asList(ITEMS);
    }

}
