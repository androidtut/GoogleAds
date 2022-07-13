package com.example.googleads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class ActivityLoadAds extends AppCompatActivity implements OnUserEarnedRewardListener {
 private AdView adView;
 private AdRequest adRequest;
 private InterstitialAd mInterstitialAd;
private RewardedInterstitialAd rewardedInterstitialAd;
private String TAG = "ActivityLoadAds";
private Button btns;
private ImageView offerimage;
private TextView offertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_ads);

        btns = findViewById(R.id.btns);
        offerimage = findViewById(R.id.offerimg);
        offertext = findViewById(R.id.offers1);

        LoadAds();
        RewardedAdshow();

        btns.setOnClickListener(v->{
            if(rewardedInterstitialAd != null){
            rewardedInterstitialAd.show(/* Activity */ ActivityLoadAds.this,/*
    OnUserEarnedRewardListener */ ActivityLoadAds.this);
            }else{
                Toast.makeText(this, "adds not Loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    after click button then show video the user watch video and can give a coin
    private void RewardedAdshow(){
            // Use the test ad unit ID to load an ad.
            RewardedInterstitialAd.load(ActivityLoadAds.this, "ca-app-pub-3940256099942544/5354046379",
                    new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(RewardedInterstitialAd ad) {
                            Log.d(TAG, "Ad was loaded.");
                            rewardedInterstitialAd = ad;
                            rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {
                                    // Called when a click is recorded for an ad.
                                    Log.d(TAG, "Ad was clicked.");
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.
                                    Log.d(TAG, "Ad dismissed fullscreen content.");
                                    rewardedInterstitialAd = null;
                                    RewardedAdshow();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when ad fails to show.
                                    Log.e(TAG, "Ad failed to show fullscreen content.");
                                    rewardedInterstitialAd = null;
                                }

                                @Override
                                public void onAdImpression() {
                                    // Called when an impression is recorded for an ad.
                                    Log.d(TAG, "Ad recorded an impression.");
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when ad is shown.
                                    Log.d(TAG, "Ad showed fullscreen content.");
                                }
                            });
                        }
                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            Log.d(TAG, loadAdError.toString());
                            rewardedInterstitialAd = null;
                        }
                    });

        }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
        Toast.makeText(this, "User earned reward.", Toast.LENGTH_SHORT).show();
        int i = Integer.parseInt(offertext.getText().toString());
        offertext.setText(String.valueOf(i+1));
        offerimage.setImageResource(R.drawable.ic_launcher_foreground);
    }

    //loading Google Adds on our app
    private void LoadAds(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }


}