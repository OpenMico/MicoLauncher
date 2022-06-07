package com.xiaomi.micolauncher.module.lockscreen.utils;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.analytics.Actions;
import com.xiaomi.analytics.AdAction;
import com.xiaomi.analytics.Analytics;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;

/* loaded from: classes3.dex */
public class AdHelper {
    private static final String APP_LAUNCH_START_DEEPLINK = "APP_LAUNCH_START_DEEPLINK";
    private static final String EVENT_TYPE_CLICK = "CLICK";
    private static final String EVENT_TYPE_VIEW = "VIEW";
    private static final String KEY_EX = "ex";
    private static final String PKG_NAME = "com.miui.systemAdSolution";
    private static final String PREVIEW_CONFIG_KEY = "systemadsolution_commonadeventsstaging";
    private static final String PRODUCT_CONFIG_KEY = "systemadsolution_commonadevents";

    /* loaded from: classes3.dex */
    public enum AdCategory {
        WAKEUP_AD_SHOW,
        WAKEUP_AD_CLICK,
        LOCKSCREEN_AD_SHOW,
        LOCKSCREEN_AD_CLICK,
        HOME_AD_SHOW,
        HOME_AD_CLICK
    }

    /* loaded from: classes3.dex */
    public enum AdKey {
        THEME,
        LOCKSCREEN,
        HOME
    }

    public static void recordAdShow(Context context, Picture.AdInfo adInfo) {
        recordAd(context, EVENT_TYPE_VIEW, adInfo.viewMonitorUrls, adInfo.ex);
    }

    public static void recordAdClicked(Context context, Picture.AdInfo adInfo) {
        recordAd(context, EVENT_TYPE_CLICK, adInfo.clickMonitorUrls, adInfo.ex);
    }

    public static void recordAdDeepLinkLaunchSuccess(Context context, Picture.AdInfo adInfo) {
        recordAd(context, APP_LAUNCH_START_DEEPLINK, null, adInfo.ex);
    }

    private static void recordAd(Context context, String str, List<String> list, String str2) {
        Analytics instance = Analytics.getInstance(context);
        AdAction newAdAction = Actions.newAdAction(str);
        newAdAction.addAdMonitor(list);
        newAdAction.addParam(KEY_EX, str2);
        instance.getTracker(ApiConstants.isProductionEnv() ? PRODUCT_CONFIG_KEY : PREVIEW_CONFIG_KEY).track(PKG_NAME, newAdAction);
    }

    public static void jump(final Context context, final Picture.AdInfo adInfo, Enum r5, Enum r6) {
        if (CommonUtil.checkHasNetworkAndShowToast(context)) {
            Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.lockscreen.utils.-$$Lambda$AdHelper$tYzRS2yE4UxyztgJHrVLVQ7MseU
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    AdHelper.recordAdClicked(context, adInfo);
                }
            }).subscribeOn(MicoSchedulers.io()).subscribe();
            if (adInfo.isUrl() && !TextUtils.isEmpty(adInfo.landingPageUrl)) {
                L.homepage.d("ad landing page url : %s", adInfo.deeplink);
                SimpleWebActivity.startSimpleWebActivity(context, adInfo.landingPageUrl);
            } else if (!TextUtils.isEmpty(adInfo.deeplink)) {
                L.homepage.d("ad updeeplink : %s", adInfo.deeplink);
                Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.lockscreen.utils.-$$Lambda$AdHelper$PvbeRSmQ539itDnLeLvBpmS3CQg
                    @Override // io.reactivex.ObservableOnSubscribe
                    public final void subscribe(ObservableEmitter observableEmitter) {
                        AdHelper.recordAdDeepLinkLaunchSuccess(context, adInfo);
                    }
                }).subscribeOn(MicoSchedulers.io()).subscribe();
                SchemaManager.handleSchema(context, adInfo.deeplink);
            }
        }
    }
}
