package com.example.snickers.auto;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener (this);


        loadFragment(new Calculator());
        int i=getIntent().getIntExtra("Create_Note",1);
        switch (i){
            case 1:
                loadFragment(new HomeFragment());
        }

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
                fragment = new Calculator();
                break;

            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_notifications2:
                fragment = new Graphics();
                break;
            case R.id.navigation_notifications3:
                fragment = new Reminder();
                break;

        }

        return loadFragment(fragment);
    }
}
