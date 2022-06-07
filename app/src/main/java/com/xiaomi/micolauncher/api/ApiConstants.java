package com.xiaomi.micolauncher.api;

import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.api.service.AiMiService;
import com.xiaomi.micolauncher.api.service.AlbumImageDownloadService;
import com.xiaomi.micolauncher.api.service.DisplayService;
import com.xiaomi.micolauncher.api.service.MeshIconService;
import com.xiaomi.micolauncher.api.service.MiBrainService;
import com.xiaomi.micolauncher.api.service.MinaService;
import com.xiaomi.micolauncher.api.service.NLPService;
import com.xiaomi.micolauncher.api.service.PaymentService;
import com.xiaomi.micolauncher.api.service.RawService;
import com.xiaomi.micolauncher.api.service.ResourceOwnerPasswordCredentialsGrantService;
import com.xiaomi.micolauncher.api.service.SkillstoreService;
import com.xiaomi.micolauncher.api.service.SoundService;
import com.xiaomi.micolauncher.api.service.SpeakerService;
import com.xiaomi.micolauncher.api.service.ThirdPartyService;
import com.xiaomi.micolauncher.api.service.UserProfileService;
import com.xiaomi.micolauncher.api.service.mitv.ChildVideoHistoryService;
import com.xiaomi.micolauncher.api.service.mitv.ChildVideoService;
import com.xiaomi.micolauncher.api.service.mitv.MiTvBuyService;
import com.xiaomi.micolauncher.api.service.mitv.MiTvMediaService;
import com.xiaomi.micolauncher.api.service.mitv.MiTvPlayListService;
import com.xiaomi.micolauncher.api.service.mitv.MiTvService;
import com.xiaomi.micolauncher.api.service.mitv.MiTvVipService;
import com.xiaomi.micolauncher.application.MicoApplication;

/* loaded from: classes3.dex */
public class ApiConstants {
    public static final String ACCOUNT_TYPE = "com.xiaomi";
    public static final String API_SERVER_BASE_URL = "mina.mi.com";
    public static final int IMPOSSIBLE = 0;
    public static final String LEVEL_PREVIEW = "preview";
    public static final String LEVEL_PREVIEW4TEST = "preview4test";
    public static final String LEVEL_PRODUCTION = "product";
    public static final String MICO_CALLBACK = "";
    public static final String OAUTH_SID = "oauth2.0";
    public static final int PREVIEW = 2;
    public static final int PREVIEW4TEST = 4;
    public static final int PRODUCTION = 3;
    public static final String SID_SPEAKER = "speaker_api";
    public static final String MICO_SID = b();
    private static volatile int a = MicoSettings.getServiceEnv(MicoApplication.getGlobalContext(), 3);
    public static final ServiceConfig[] ServiceConfigs = c();

    public static synchronized void switchServerEnv(int i) {
        synchronized (ApiConstants.class) {
            if (i == 1) {
                i = 2;
            }
            if (a != i) {
                a = i;
                MicoSettings.setServiceEnv(MicoApplication.getGlobalContext(), i);
            }
        }
    }

    public static synchronized String getServiceEnvName() {
        synchronized (ApiConstants.class) {
            if (3 == a) {
                return LEVEL_PRODUCTION;
            }
            if (!(2 == a || 1 == a)) {
                return 4 == a ? "preview4test" : LEVEL_PRODUCTION;
            }
            return "preview";
        }
    }

    public static synchronized boolean isProductionEnv() {
        boolean z;
        synchronized (ApiConstants.class) {
            z = a == 3;
        }
        return z;
    }

    public static synchronized boolean isPrevEnv() {
        boolean z;
        synchronized (ApiConstants.class) {
            z = a == 2;
        }
        return z;
    }

    public static synchronized boolean isPreview4testEnv() {
        boolean z;
        synchronized (ApiConstants.class) {
            z = 4 == a;
        }
        return z;
    }

    public static synchronized String getMicoUplogHostEnv() {
        synchronized (ApiConstants.class) {
            if (a == 1) {
                return "speech-staging.ai.xiaomi.com";
            }
            if (a != 2) {
                if (a != 4) {
                    return "speech.ai.xiaomi.com";
                }
            }
            return "speech-preview.ai.xiaomi.com";
        }
    }

    public static boolean setMiBrainLevel(String str) {
        if (LEVEL_PRODUCTION.equals(str)) {
            switchServerEnv(3);
            return true;
        } else if ("preview".equals(str)) {
            switchServerEnv(2);
            return true;
        } else if (!"preview4test".equals(str)) {
            return false;
        } else {
            switchServerEnv(4);
            return true;
        }
    }

    private static synchronized String b() {
        synchronized (ApiConstants.class) {
            return a == 1 ? LoginManager.MICO_STAGING_SID : "micoapi";
        }
    }

    private static ServiceConfig[] c() {
        return new ServiceConfig[]{new ServiceConfig(MinaService.class, "https://api2.mina.mi.com/", "https://preview-api.mina.mi.com/"), new ServiceConfig(MiBrainService.class, MiBrainService.API_PRODUCTION_URL, MiBrainService.API_PREVIEW_URL), new ServiceConfig(ThirdPartyService.class, "https://api2.mina.mi.com/", "https://preview-api.mina.mi.com/"), new ServiceConfig(RawService.class, "https://api2.mina.mi.com/", "https://preview-api.mina.mi.com/"), new ServiceConfig(DisplayService.class, DisplayService.BASE_URL, DisplayService.PREVIEW_URL), new ServiceConfig(SkillstoreService.class, SkillstoreService.BASE_URL, SkillstoreService.PREVIEW_URL), new ServiceConfig(UserProfileService.class, UserProfileService.BASE_URL, UserProfileService.PREVIEW_URL), new ServiceConfig(NLPService.class, NLPService.NLP_PRODUCTION_URL, NLPService.NLP_PREVIEW_URL), new ServiceConfig(AlbumImageDownloadService.class, AlbumImageDownloadService.BASE_URL, AlbumImageDownloadService.BASE_URL), new ServiceConfig(MeshIconService.class, "http://pv.api.io.mi.com/app/v2/public/get_product_config/", "http://pv.api.io.mi.com/app/v2/public/get_product_config/"), new ServiceConfig(SoundService.class, SoundService.API_PRODUCTION_URL, SoundService.API_PREVIEW_URL), new ServiceConfig(PaymentService.class, PaymentService.BASE_URL, PaymentService.PREVIEW_URL), new ServiceConfig(ChildVideoService.class, "https://pwapp.tv.mi.com", "https://pwapp.tv.mi.com"), new ServiceConfig(MiTvService.class, MiTvService.API_PRODUCTION_URL, MiTvService.API_PREVIEW_URL), new ServiceConfig(MiTvBuyService.class, MiTvBuyService.API_PRODUCTION_URL, MiTvBuyService.API_PREVIEW_URL), new ServiceConfig(ChildVideoHistoryService.class, "https://user.ptmi.gitv.tv", "https://user.ptmi.gitv.tv"), new ServiceConfig(MiTvMediaService.class, "https://media.ptmi.gitv.tv", "https://media.ptmi.gitv.tv"), new ServiceConfig(MiTvPlayListService.class, "https://pwapp.tv.mi.com", MiTvPlayListService.API_PREVIEW_URL), new ServiceConfig(ResourceOwnerPasswordCredentialsGrantService.class, "https://account.xiaomi.com/", "https://account.xiaomi.com/"), new ServiceConfig(MiTvVipService.class, MiTvVipService.API_PRODUCTION_URL, MiTvVipService.API_PREVIEW_URL), new ServiceConfig(SpeakerService.class, SpeakerService.API_PRODUCTION_URL, SpeakerService.API_PREVIEW_URL, SpeakerService.API_PREVIEW4TEST_URL), new ServiceConfig(AiMiService.class, AiMiService.API_PRODUCTION_URL, AiMiService.PREVIEW_URL, AiMiService.PREVIEW_4TEST_URL)};
    }

    public static ServiceConfig a(Class<?> cls) {
        int i = 0;
        while (true) {
            ServiceConfig[] serviceConfigArr = ServiceConfigs;
            if (i >= serviceConfigArr.length) {
                return null;
            }
            if (serviceConfigArr[i].a().equals(cls)) {
                return ServiceConfigs[i];
            }
            i++;
        }
    }

    /* loaded from: classes3.dex */
    public static class ServiceConfig {
        private final Class<?> a;
        private final String b;
        private final String c;
        private final String d;

        ServiceConfig(Class<?> cls, String str, String str2) {
            this(cls, str, str2, null);
        }

        ServiceConfig(Class<?> cls, String str, String str2, String str3) {
            this.a = cls;
            this.b = str;
            this.c = str2;
            this.d = str3;
        }

        Class<?> a() {
            return this.a;
        }

        public String getProductionUrl() {
            return this.b;
        }

        public String b() {
            if (ApiConstants.a == 3) {
                return this.b;
            }
            if (ApiConstants.a == 2) {
                return this.c;
            }
            if (ApiConstants.a != 4) {
                return "";
            }
            String str = this.d;
            return str != null ? str : this.c;
        }

        public String[] getServiceUrls() {
            return new String[]{this.b, this.c, this.d};
        }
    }
}
