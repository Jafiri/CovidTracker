package com.example.coronatracker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronatracker.API_Country.CountryDetailActivity;
import com.example.coronatracker.CALLnSMS.CallActiviy;
import com.example.coronatracker.News.NewsActivity;
import com.example.coronatracker.OTPLogin.SendOPTActivity;
import com.example.coronatracker.OTPLogin.UserModel;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnUserEarnedRewardListener {

    BottomAppBar bottomNavigationView;
    FloatingActionButton floatingActionButton;
    ImageView logout;
    TextView callNow;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    FirebaseAuth mAuth;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomAppBar) findViewById(R.id.bottom_nav);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        callNow = (TextView) findViewById(R.id.callnow);
        logout = (ImageView) findViewById(R.id.logout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        BannerAd();
        InterstitialAdShow();


        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userid = user.getUid();

        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userdata = snapshot.getValue(UserModel.class);

                if (userdata != null) {
                    int userNumber = Integer.parseInt(userdata.getPhonenumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(HomeActivity.this, error.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, SendOPTActivity.class));
                finish();
            }
        });

        callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CallActiviy.class);
                startActivity(intent);

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowRewaredAd();

                Intent intent = new Intent(HomeActivity.this, CountryDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomNavigationView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.News:
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(HomeActivity.this);
                            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    startActivity(new Intent(HomeActivity.this, NewsActivity.class));
                                    finish();
                                }
                            });

                        } else {
                            startActivity(new Intent(HomeActivity.this, NewsActivity.class));
                            finish();
                        }
                        break;


                    case R.id.payment:
                        startActivity(new Intent(HomeActivity.this, PaymentActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
        Viewpagerdata();
    }

    private void ShowRewaredAd() {

        RewardedInterstitialAd.load(HomeActivity.this, "ca-app-pub-3940256099942544/5354046379", new AdRequest.Builder().build(),
                                new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                rewardedInterstitialAd.show(HomeActivity.this,HomeActivity.this);
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }
        });
    }

    private void InterstitialAdShow() {

        AdRequest adRequest = new AdRequest.Builder().build();
        

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        super.onAdFailedToLoad(loadAdError);

                    }
                });


    }

    private void BannerAd() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(HomeActivity.this, "Ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }
        });


    }

    private void Viewpagerdata() {

        List<BannerModel> list = new ArrayList<>();
        list.add(new BannerModel(R.drawable.corona_virus_posterrrr));
        list.add(new BannerModel(R.drawable.corona_virus_poster_two));
        // list.add(new BannerModel(R.drawable.corona_virus_poster_three));
        list.add(new BannerModel(R.drawable.corona_virus_poster_four));

        BannerAdapter adapter = new BannerAdapter(HomeActivity.this, list);
        ViewPager viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(HomeActivity.this).setTitle("Exit !").setMessage("Are you sure to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HomeActivity.this, "All is Well !", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

        Toast.makeText(this, rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
    }
}