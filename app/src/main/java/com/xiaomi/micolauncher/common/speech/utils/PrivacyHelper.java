package com.xiaomi.micolauncher.common.speech.utils;

import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;

/* loaded from: classes3.dex */
public class PrivacyHelper {
    private static PrivacyStatus a = PrivacyStatus.PRIVACY_STATUS_UN_INIT;

    /* loaded from: classes3.dex */
    public enum PrivacyStatus {
        PRIVACY_STATUS_UN_INIT,
        PRIVACY_STATUS_OPEN,
        PRIVACY_STATUS_OFF
    }

    public static void privacyInit() {
        ApiManager.userProfileService.getUserPrivacy().subscribeOn(MicoSchedulers.io()).subscribe(new DefaultObserver<ThirdPartyResponse.UserPrivacy>() { // from class: com.xiaomi.micolauncher.common.speech.utils.PrivacyHelper.1
            /* renamed from: a */
            public void onSuccess(ThirdPartyResponse.UserPrivacy userPrivacy) {
                L.speech.d("privacyInit.onSuccess");
                PrivacyHelper.setPrivacyEnable(userPrivacy.wakeWordUpload);
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                super.onFail(apiError);
                L.speech.d("privacyInit.onFail.msg=%s", apiError.toString());
                PrivacyHelper.setPrivacyEnable(false);
            }
        });
    }

    public static void setPrivacyEnable(boolean z) {
        L.speech.d("setPrivacyEnable.enable=%b", Boolean.valueOf(z));
        if (z) {
            a = PrivacyStatus.PRIVACY_STATUS_OPEN;
            SpeechManager.getInstance().enablePrivacy();
            return;
        }
        a = PrivacyStatus.PRIVACY_STATUS_OFF;
        SpeechManager.getInstance().disablePrivacy();
    }

    public static boolean privacyNeedInit() {
        return a == PrivacyStatus.PRIVACY_STATUS_UN_INIT;
    }

    public static boolean isPrivacyEnable() {
        return a == PrivacyStatus.PRIVACY_STATUS_OPEN;
    }
}
