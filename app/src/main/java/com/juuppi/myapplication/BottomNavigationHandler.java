package com.juuppi.myapplication;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationHandler implements NavigationBarView.OnItemSelectedListener {

    private Context context;
    private BottomNavigationView bottomNavigationView;

    public BottomNavigationHandler(Context context, BottomNavigationView bottomNavigationView) {
        this.context = context;
        this.bottomNavigationView = bottomNavigationView;
        this.bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_timer) {
            // Handle Timer menu item selection
            // For example, start Timer.class activity
            startNewActivity(Timer.class);
            return true;
        } else if (itemId == R.id.menu_notes) {
            // Handle Notes menu item selection
            // For example, start Notes.class activity
            startNewActivity(Notes.class);
            return true;
        }
        // Add more if-else blocks for other menu items if needed

        BottomNavigationHelper.setSelectedItem(bottomNavigationView, itemId); // Set the selected item in BottomNavigationView

        return false;
    }

    private void startNewActivity(Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }
}
