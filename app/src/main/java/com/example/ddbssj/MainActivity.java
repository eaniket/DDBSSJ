package com.example.ddbssj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.ddbssj.fragments.EventsFragment;
import com.example.ddbssj.fragments.HomeFragment;
import com.example.ddbssj.fragments.VideosFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.navColor));
        }
        setContentView(R.layout.activity_main);

        //Hooks
        toolbar = findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);

        bottomNavigationView = findViewById(R.id.bottomNav);

        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Fragments
        HomeFragment homeFragment = new HomeFragment();
        VideosFragment videosFragment = new VideosFragment();
        EventsFragment eventsFragment = new EventsFragment();

        //Bottom Navigation
        bottomNavigationView.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, homeFragment).commit();
                        return true;
                    case R.id.videos:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, videosFragment).commit();
                        return true;
                    case R.id.events:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, eventsFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.kidsection:
                Intent intent = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse("https://www.youtube.com/watch?v=7OHwpME1I9k"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                return true;
            case R.id.iGranth:
                Intent intent1 = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.kirpa.igranth"));
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent1);
                return true;
            case R.id.feedback:
                Intent intent2 = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse("https://forms.gle/LpFkfXPfNGZyaB3P8"));
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent2);
                return true;
            case R.id.donation:
                Intent intent3 = new Intent(getApplicationContext(), DonationActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent3);
                return true;
            case R.id.sakhis:
                Intent intent4 = new Intent(getApplicationContext(), SakhisActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent4);
                return true;
            case R.id.books:
                Intent intent5 = new Intent(getApplicationContext(), BooksActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(intent5);
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}