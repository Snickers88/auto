package com.example.snickers.auto;

//import android.app.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.snickers.auto.Fagment.CalculatorFragment;
import com.example.snickers.auto.Fagment.GraphicsFragment;
import com.example.snickers.auto.Fagment.HomeFragment;
import com.example.snickers.auto.Fagment.ReminderFragment;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        Menu menu = navigation.getMenu();

        MenuItem menuItem = menu.getItem(0);
        loadFragment(new HomeFragment());
        int i = getIntent().getIntExtra("Create_Note", 0);
        switch (i) {
            case 1:
                loadFragment(new HomeFragment());break;
            case 2:{
                loadFragment(new ReminderFragment());
                menuItem=menu.getItem(3);
                break;
            }
        }
        menuItem.setChecked(true);

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_dashboard:
                fragment = new CalculatorFragment();
                break;

            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_notifications2:
                fragment = new GraphicsFragment();
                break;
            case R.id.navigation_notifications3:
                fragment = new ReminderFragment();
                break;

        }

        return loadFragment(fragment);
    }
}
