package gun0912.tedadhelperdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import gun0912.tedadhelper.TedAdHelper;
import gun0912.tedadhelper.backpress.OnBackPressListener;
import gun0912.tedadhelper.backpress.TedBackPressDialog;
import gun0912.tedadhelper.banner.OnBannerAdListener;
import gun0912.tedadhelper.banner.TedAdBanner;
import gun0912.tedadhelper.front.OnFrontAdListener;
import gun0912.tedadhelper.front.TedAdFront;
import gun0912.tedadhelper.nativead.OnNativeAdListener;
import gun0912.tedadhelper.nativead.TedNativeAd;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "ted";

    public static final String FACEBOOK_KEY_BANNER = "468024734632632_468024784632627";
    public static final String FACEBOOK_KEY_FRONT = "468024734632632_468024791299293";
    public static final String FACEBOOK_KEY_BACKPRESS = "468024734632632_468024794632626";
    public static final String FACEBOOK_KEY_NATIVE = "468024734632632_468024787965960";


    public static final String ADMOB_KEY_BANNER = "ca-app-pub-3940256099942544/6300978111";
    public static final String ADMOB_KEY_FRONT = "ca-app-pub-3940256099942544/1033173712";
    public static final String ADMOB_KEY_BACKPRESS = "ca-app-pub-3940256099942544/2247696110";
    public static final String ADMOB_KEY_NATIVE = "ca-app-pub-3940256099942544/2247696110";
    public static final String ADMOB_KEY_NATIVE_BANNER = "ca-app-pub-3940256099942544/6300978111";
    public static final String ADMOB_KEY_NATIVE_ADVANCED = "ca-app-pub-3940256099942544/2247696110";

    InterstitialAd facebookFrontAD;
    com.facebook.ads.AdView facebookBanner;
    TedNativeAd tedNativeAd;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AudienceNetworkAds.initialize(this);
        TedAdHelper.setFacebookTestDeviceId("a23b55e1-7f1f-4668-84a0-9f44a76f33c3");
        //TedAdHelper.showAdOnlyFacebookInstalledUser(true);

        TedAdHelper.setAdmobTestDeviceId("39FCA51243B7C2F2D542A942F2B243D5");

        /**
         * Banner
         */

        FrameLayout bannerContainer = (FrameLayout) findViewById(R.id.bannerContainer);

        //TedAdBanner.showFacebookBanner();
        //TedAdBanner.showAdmobBanner();
        TedAdBanner.showBanner(bannerContainer, FACEBOOK_KEY_BANNER, ADMOB_KEY_BANNER, TedAdHelper.AD_FACEBOOK, new OnBannerAdListener() {
            @Override
            public void onError(String errorMessage) {

            }

            @Override
            public void onLoaded(int adType) {

            }

            @Override
            public void onAdClicked(int adType) {

            }

            @Override
            public void onFacebookAdCreated(com.facebook.ads.AdView facebookBanner) {
                MainActivity.this.facebookBanner = facebookBanner;
            }

        });


        /**
         * Front AD
         */

        TedAdFront.showFrontAD(this, FACEBOOK_KEY_FRONT, ADMOB_KEY_FRONT,new Integer[]{TedAdHelper.AD_FACEBOOK,TedAdHelper.AD_ADMOB}, new OnFrontAdListener() {
            @Override
            public void onDismissed(int adType) {

            }

            @Override
            public void onError(String errorMessage) {
            }

            @Override
            public void onLoaded(int adType) {

            }

            @Override
            public void onAdClicked(int adType) {

            }

            @Override
            public void onFacebookAdCreated(InterstitialAd facebookFrontAD) {
                MainActivity.this.facebookFrontAD = facebookFrontAD;
            }
        });


        /**
         * Native AD
         */
        ViewGroup cardview = (ViewGroup) findViewById(R.id.cardview);
        String facebookKeyNative = FACEBOOK_KEY_NATIVE;
        tedNativeAd = new TedNativeAd(cardview, this, getString(R.string.app_name), facebookKeyNative, ADMOB_KEY_NATIVE_ADVANCED, new TedAdHelper.ImageProvider() {
            @Override
            public void onProvideImage(ImageView imageView, String imageUrl) {
                Glide.with(MainActivity.this).load(imageUrl).into(imageView);
            }
        }, TedAdHelper.ADMOB_NATIVE_AD_TYPE.NATIVE_ADVANCED);


        //tedNativeAdHolder.loadAD(TedAdHelper.AD_FACEBOOK, new OnNativeAdListener() {
        tedNativeAd.loadAD(new Integer[]{TedAdHelper.AD_FACEBOOK,TedAdHelper.AD_ADMOB}, new OnNativeAdListener() {
            @Override
            public void onError(String errorMessage) {

            }

            @Override
            public void onLoaded(int adType) {

            }

            @Override
            public void onAdClicked(int adType) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        TedBackPressDialog.startDialog(this, getString(R.string.app_name), FACEBOOK_KEY_BACKPRESS, ADMOB_KEY_BACKPRESS
                ,new Integer[]{TedAdHelper.AD_ADMOB,TedAdHelper.AD_FACEBOOK},TedAdHelper.ADMOB_NATIVE_AD_TYPE.NATIVE_ADVANCED,true, new OnBackPressListener() {
            @Override
            public void onReviewClick() {
            }

            @Override
            public void onFinish() {
                finish();
            }

            @Override
            public void onError(String errorMessage) {
            }

            @Override
            public void onLoaded(int adType) {
            }

            @Override
            public void onAdClicked(int adType) {
            }
        });

    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        if (tedNativeAd != null) {
            tedNativeAd.onDestroy();
        }

        if (facebookFrontAD != null) {
            facebookFrontAD.destroy();
        }

        if (facebookBanner != null) {
            facebookBanner.destroy();
        }

        super.onDestroy();
    }

    public void ShowInter(View view) {
            TedAdFront.showFacebookFrontAd(this, FACEBOOK_KEY_FRONT, new OnFrontAdListener() {
                @Override
                public void onDismissed(int adType) {

                }

                @Override
                public void onError(String errorMessage) {

                }

                @Override
                public void onLoaded(int adType) {

                }

                @Override
                public void onAdClicked(int adType) {

                }

                @Override
                public void onFacebookAdCreated(InterstitialAd facebookFrontAD) {

                }
            });
    }
}


