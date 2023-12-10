package com.juuppi.myapplication;

import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHelper {

    public static void setSelectedItem(BottomNavigationView bottomNavigationView, int itemId) {
        if (bottomNavigationView != null) {
            bottomNavigationView.post(() -> {
                bottomNavigationView.setSelectedItemId(itemId);
            });
        }
    }
}