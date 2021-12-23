package com.example.manageractivityproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.manageractivityproject.Fragments.AddActivityFragment;
import com.example.manageractivityproject.Fragments.ChangerPasswordFragment;
import com.example.manageractivityproject.Fragments.ChartFragment;
import com.example.manageractivityproject.Fragments.HomeFragment;
import com.example.manageractivityproject.Fragments.ManagerFragment;
import com.example.manageractivityproject.Fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private SharedPreferences userPref;
    private DrawerLayout ndDrawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_MANAGER = 1;
    private static final int FRAGMENT_CHART = 2;
    private static final int FRAGMENT_ADD_ACTIVITY = 3;
    private static final int FRAGMENT_PROFILE = 4;
    private static final int FRAGMENT_CHANGER_PASSWORD = 5;
    private static final int LOG_OUT = 6;


    private int ndCurrentFragment = FRAGMENT_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, ndDrawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ndDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        // sử lý khi vào navigation drawer
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

    }
    public void init(){
        toolbar = findViewById(R.id.toolbar);
        ndDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            if(ndCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                ndCurrentFragment = FRAGMENT_HOME;
            }
        } else if(id == R.id.nav_manager){
            if(ndCurrentFragment != FRAGMENT_MANAGER){
                replaceFragment(new ManagerFragment());
                ndCurrentFragment = FRAGMENT_MANAGER;
            }
        } else if (id == R.id.nav_chart){
            if(ndCurrentFragment != FRAGMENT_CHART){
                replaceFragment(new ChartFragment());
                ndCurrentFragment = FRAGMENT_CHART;
            }
        } else if(id == R.id.nav_add){
            if(ndCurrentFragment != FRAGMENT_ADD_ACTIVITY){
                replaceFragment(new AddActivityFragment());
                ndCurrentFragment = FRAGMENT_ADD_ACTIVITY;
            }
        } else if(id == R.id.nav_my_profile){
            if(ndCurrentFragment != FRAGMENT_PROFILE){
                replaceFragment(new ProfileFragment());
                ndCurrentFragment = FRAGMENT_PROFILE;
            }
        } else if(id == R.id.nav_changer_password){
            if(ndCurrentFragment != FRAGMENT_CHANGER_PASSWORD){
                replaceFragment(new ChangerPasswordFragment());
                ndCurrentFragment = FRAGMENT_CHANGER_PASSWORD;
            }
        } else if(id == R.id.nav_logout){
            ndCurrentFragment = LOG_OUT;
            SharedPreferences.Editor editor = userPref.edit();
            /*
            // Xoá SharedPreferences sử dụng key
            // Cách 1:
            editor.remove("id");
            editor.remove("username");
            editor.remove("password");
            editor.remove("photo");
            editor.remove("isLoggedIn");
            editor.commit();*/
            // Cách 2: xoá tất cả
            editor.clear().commit();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
            finish();
        }

        ndDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(ndDrawerLayout.isDrawerOpen(GravityCompat.START)){
            ndDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}