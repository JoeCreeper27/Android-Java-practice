package com.example.drawerlayoutdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpActionBar();
        initDrawer();
        initBody();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void initBody() {
        Fragment fragment=new HomeFragment();
        switchFragment(fragment);
        setTitle(R.string.text_Home);
    }

    private void initDrawer() {
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.text_Open,R.string.text_Close);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        }else{
            drawerLayout.setDrawerListener(actionBarDrawerToggle);
        }
        NavigationView view_start=(NavigationView) findViewById(R.id.navigation_start);
        view_start.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.item_Home:
                        initBody();
                        break;
                    case R.id.item_News:
                        fragment=new NewsFragment();
                        switchFragment(fragment);
                        setTitle(R.string.text_News);
                        break;
                    case R.id.item_Map:
                        fragment=new MapFragment();
                        switchFragment(fragment);
                        setTitle(R.string.text_Map);
                        break;
                    case R.id.drawer_settings:
                        fragment=new SettingFragment();
                        switchFragment(fragment);
                        setTitle(R.string.text_Settings);
                        break;
                    default:
                        initBody();
                        break;
                }
                return true;
            }
        });
    }

    private void setUpActionBar() {
        ActionBar actionBar=getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

           }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void switchFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body,fragment);
        fragmentTransaction.commit();

    }
}