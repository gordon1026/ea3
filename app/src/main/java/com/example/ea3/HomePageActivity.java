package com.example.ea3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton btnMenu, btnProfile;
    private ViewPager viewPager;
    private ImageView img1, img2, img3, img4, img5, img6, img7, img8;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        btnMenu = findViewById(R.id.btnMenu);
        btnProfile = findViewById(R.id.btnProfile);
        viewPager = findViewById(R.id.viewPager);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img8 = findViewById(R.id.img8);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set up ViewPager
        PagerAdapter adapter = new ImagePagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Auto-scroll ViewPager
        runnable = new Runnable() {
            @Override
            public void run() {
                if (adapter.getCount() != 0) {
                    currentPage = (currentPage + 1) % adapter.getCount();
                    viewPager.setCurrentItem(currentPage, true);
                }
                handler.postDelayed(this, 3000); // Change page every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000);

        // Set up button click listeners
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(HomePageActivity.this, HomePageActivity.class));
                } else if (itemId == R.id.kcal) {
                    // Handle sign out action
                    // Clear the shared preferences

                    // Redirect to sign-in page
                    startActivity(new Intent(HomePageActivity.this, Kcal.class));
                    finish();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        // Set up BottomNavigationView item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_map) {
                    startActivity(new Intent(HomePageActivity.this, Countdown.class));
                    return true;
                } else if (itemId == R.id.action_home) {
                    // Handle home button click
                    return true;
                } else if (itemId == R.id.action_take_photo) {
                    startActivity(new Intent(HomePageActivity.this, ShowWeather.class));
                    return true;
                }
                return false;
            }
        });


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://heho.com.tw/archives/209441");
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://health.tvbs.com.tw/regimen/333428");
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://www.leaderkid.com.tw/2021/04/01/101010/");
            }
        });

        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://www.cuhkmc.hk/tc/health-corner/healthy-living/walking-tips/SMRC_Walking_Tips_2");
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://www.cuhkmc.hk/tc/health-corner/healthy-living/walking-tips/SMRC_Walking_Tips_2");
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://topick.hket.com/article/3758675/");
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://n.yam.com/Article/20201116368124");
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://sport.heho.com.tw/archives/86442");
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://www.careonline.com.tw/2021/01/sedentary.html");
            }
        });
    }







    private void openWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}