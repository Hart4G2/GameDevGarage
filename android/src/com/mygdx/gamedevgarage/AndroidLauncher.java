package com.mygdx.gamedevgarage;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.gamedevgarage.ads.AdHandler;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;


public class AndroidLauncher extends AndroidApplication  {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		AdHandler adHandler = new AndroidAdHandler();

		Game game = Game.getInstance();
		game.setAdHandler(adHandler);
		initialize(game, config);

		UnityAds.initialize(this, "5428154", new IUnityAdsInitializationListener() {
			@Override
			public void onInitializationComplete() {
				System.out.println("ad initialisation complete");
			}

			@Override
			public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
				System.out.println(message);
			}
		});
	}

	class AndroidAdHandler implements AdHandler{

		private final IUnityAdsLoadListener loadListener;
		private IUnityAdsShowListener showListener;

		public AndroidAdHandler() {
			loadListener = new IUnityAdsLoadListener() {
				@Override
				public void onUnityAdsAdLoaded(String placementId) {
					UnityAds.show(AndroidLauncher.this, placementId, new UnityAdsShowOptions(), showListener);
				}

				@Override
				public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
					System.out.println("Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
				}
			};
		}

		@Override
		public void showInterstitialAd() {
			showListener = new IUnityAdsShowListener() {
				@Override
				public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
					System.out.println("Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
				}

				@Override
				public void onUnityAdsShowStart(String placementId) {
					System.out.println("onUnityAdsShowStart: " + placementId);
				}

				@Override
				public void onUnityAdsShowClick(String placementId) {
					System.out.println("onUnityAdsShowClick: " + placementId);
				}

				@Override
				public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
					System.out.println("onUnityAdsShowComplete: " + placementId);
				}
			};

			UnityAds.load("Interstitial_Android", loadListener);
		}

		@Override
		public void showRewardAd(String fromWhere) {
			showListener = new IUnityAdsShowListener() {
				@Override
				public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
					System.out.println("Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
				}

				@Override
				public void onUnityAdsShowStart(String placementId) {
					System.out.println("onUnityAdsShowStart: " + placementId);
				}

				@Override
				public void onUnityAdsShowClick(String placementId) {
					System.out.println("onUnityAdsShowClick: " + placementId);
				}

				@Override
				public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
					boolean isCompleted = state.equals(UnityAds.UnityAdsShowCompletionState.COMPLETED);

					Game.getInstance().setAdFinished(fromWhere, isCompleted);
				}
			};

			UnityAds.load("Rewarded_Android", loadListener);
		}
	}
}
