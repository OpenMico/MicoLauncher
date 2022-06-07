package com.xiaomi.micolauncher.api;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.xiaomi.debuglib.DebugSetup;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.DnsSelector;
import com.xiaomi.micolauncher.api.converter.GsonConverterFactory;
import com.xiaomi.micolauncher.api.interceptor.LoggingInterceptor;
import com.xiaomi.micolauncher.api.interceptor.ParamsInterceptor;
import com.xiaomi.micolauncher.api.interceptor.UAInterceptor;
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
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: classes3.dex */
public class ApiManager<T> {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile ApiManager a;
    public static AiMiService aiMiService;
    public static AlbumImageDownloadService albumImageDownloadService;
    public static ChildVideoHistoryService childVideoHistoryService;
    public static ChildVideoService childVideoService;
    public static DisplayService displayService;
    public static MeshIconService meshIconService;
    public static MiBrainService miBrainService;
    public static MiTvBuyService miTvBuyService;
    public static MiTvMediaService miTvMediaService;
    public static MiTvPlayListService miTvPlayListService;
    public static MiTvService miTvService;
    public static MiTvVipService miTvVipService;
    public static MinaService minaService;
    public static NLPService nlpService;
    public static ResourceOwnerPasswordCredentialsGrantService passwordCredentialsService;
    public static PaymentService paymentService;
    public static RawService rawService;
    public static SkillstoreService skillstoreService;
    public static SoundService soundService;
    public static SpeakerService speechMiService;
    public static ThirdPartyService thirdPartyService;
    public static UserProfileService userProfileService;
    private boolean b;
    private final OkHttpClient c;
    private final OkHttpClient d;
    private ConcurrentHashMap<String, Object> e = new ConcurrentHashMap<>();
    private volatile c f;

    /* loaded from: classes3.dex */
    public interface b {
        OkHttpClient.Builder config(OkHttpClient.Builder builder);
    }

    public static void init(Logger logger) {
        if (a == null) {
            a = new ApiManager(logger);
        }
    }

    public static ApiManager getInstance() {
        return a;
    }

    private ApiManager(Logger logger) {
        this.c = a(logger, new b() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiManager$UmpevJhT_G0w60cKgu00DkWXPbo
            @Override // com.xiaomi.micolauncher.api.ApiManager.b
            public final OkHttpClient.Builder config(OkHttpClient.Builder builder) {
                OkHttpClient.Builder b2;
                b2 = ApiManager.this.b(builder);
                return b2;
            }
        });
        this.d = a(logger, $$Lambda$ApiManager$lMJ79rdFBc55e7ebocLRVuQg1E.INSTANCE);
        setupServices();
        fillCookieJar();
    }

    public /* synthetic */ OkHttpClient.Builder b(OkHttpClient.Builder builder) {
        this.f = new c(null);
        fillCookieJar();
        return builder.authenticator(new MicoAuthenticator()).cookieJar(this.f);
    }

    public static /* synthetic */ OkHttpClient.Builder a(OkHttpClient.Builder builder) {
        return builder.cookieJar(new c(new a()));
    }

    public void fillCookieJar() {
        if (this.f != null) {
            this.f.a(TokenManager.getInstance().getCookieJar());
        }
    }

    /* loaded from: classes3.dex */
    public static class a implements CookieJar {
        @Override // okhttp3.CookieJar
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        }

        private a() {
        }

        @Override // okhttp3.CookieJar
        @NotNull
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            ArrayList arrayList = new ArrayList();
            ServiceTokenResult blockGetServiceToken = TokenManager.getInstance().blockGetServiceToken(ApiConstants.OAUTH_SID);
            if (blockGetServiceToken != null) {
                c.a(httpUrl, arrayList, ApiConstants.OAUTH_SID + "_serviceToken", blockGetServiceToken.serviceToken);
            } else {
                L.login.e("failed to get service token for %s", ApiConstants.OAUTH_SID);
            }
            return arrayList;
        }
    }

    public static OkHttpClient.Builder createOkHttpClientBuilderWithNetworkInterceptor() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Interceptor networkInterceptor = DebugSetup.getNetworkInterceptor();
        if (networkInterceptor != null) {
            builder.addNetworkInterceptor(networkInterceptor);
        }
        return builder;
    }

    private OkHttpClient a(final Logger logger, b bVar) {
        Dispatcher dispatcher = new Dispatcher(ThreadUtil.getIoThreadPool());
        dispatcher.setMaxRequests(ThreadUtil.getMaxThreadsOfIoThreadPool());
        OkHttpClient.Builder dns = createOkHttpClientBuilderWithNetworkInterceptor().dispatcher(dispatcher).retryOnConnectionFailure(true).connectTimeout(5L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).readTimeout(15L, TimeUnit.SECONDS).addInterceptor(new LoggingInterceptor(logger)).addInterceptor(new UAInterceptor()).addInterceptor(new ParamsInterceptor()).dns(DnsSelector.byName(DnsSelector.Mode.IPV6_FIRST));
        bVar.config(dns);
        if (DebugHelper.isDebugInConfg()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.xiaomi.micolauncher.api.-$$Lambda$ApiManager$ZzlLAP8wseR3l3S_H7yDxCpUq_A
                @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
                public final void log(String str) {
                    ApiManager.a(Logger.this, str);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            dns.addNetworkInterceptor(httpLoggingInterceptor);
        }
        return dns.build();
    }

    public static /* synthetic */ void a(Logger logger, String str) {
        logger.i("Http logging " + str);
    }

    public void setupServices() {
        minaService = (MinaService) a(MinaService.class);
        displayService = (DisplayService) a(DisplayService.class);
        miBrainService = (MiBrainService) a(MiBrainService.class);
        thirdPartyService = (ThirdPartyService) a(ThirdPartyService.class);
        rawService = (RawService) a(RawService.class);
        skillstoreService = (SkillstoreService) a(SkillstoreService.class);
        userProfileService = (UserProfileService) a(UserProfileService.class);
        nlpService = (NLPService) a(NLPService.class);
        albumImageDownloadService = (AlbumImageDownloadService) a(AlbumImageDownloadService.class);
        meshIconService = (MeshIconService) a(MeshIconService.class);
        soundService = (SoundService) a(SoundService.class);
        paymentService = (PaymentService) a(PaymentService.class);
        childVideoService = (ChildVideoService) a(ChildVideoService.class);
        miTvService = (MiTvService) a(MiTvService.class);
        miTvBuyService = (MiTvBuyService) a(MiTvBuyService.class);
        childVideoHistoryService = (ChildVideoHistoryService) a(ChildVideoHistoryService.class);
        miTvMediaService = (MiTvMediaService) a(MiTvMediaService.class);
        miTvPlayListService = (MiTvPlayListService) a(MiTvPlayListService.class);
        passwordCredentialsService = (ResourceOwnerPasswordCredentialsGrantService) a(ResourceOwnerPasswordCredentialsGrantService.class, this.d);
        miTvVipService = (MiTvVipService) a(MiTvVipService.class);
        aiMiService = (AiMiService) a(AiMiService.class);
        speechMiService = (SpeakerService) a(SpeakerService.class);
        this.b = true;
    }

    public void onEnvSwitched() {
        this.e.clear();
        setupServices();
    }

    private <S> S a(Class<S> cls) {
        return (S) a(cls, this.c);
    }

    private <S> S a(Class<S> cls, OkHttpClient okHttpClient) {
        Retrofit retrofit;
        String format = String.format("%s_%s", cls.getSimpleName(), ApiConstants.getServiceEnvName());
        S s = (S) this.e.get(format);
        if (s != null) {
            return s;
        }
        ApiConstants.ServiceConfig a2 = ApiConstants.a(cls);
        Retrofit.Builder builder = new Retrofit.Builder();
        if (a2 != null) {
            builder.baseUrl(a2.b());
            if (cls.equals(RawService.class)) {
                retrofit = builder.client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(MicoSchedulers.io())).build();
            } else {
                retrofit = builder.client(okHttpClient).addConverterFactory(GsonConverterFactory.create(Gsons.getGson())).addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(MicoSchedulers.io())).build();
            }
            S s2 = (S) retrofit.create(cls);
            this.e.put(format, s2);
            return s2;
        }
        throw new IllegalArgumentException("ApiManager.getService.serviceConfig=null");
    }

    /* loaded from: classes3.dex */
    public static class c implements CookieJar {
        private CookieJar a;

        private c(CookieJar cookieJar) {
            this.a = cookieJar;
        }

        public void a(CookieJar cookieJar) {
            this.a = cookieJar;
        }

        @Override // okhttp3.CookieJar
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            CookieJar cookieJar = this.a;
            if (cookieJar != null) {
                cookieJar.saveFromResponse(httpUrl, list);
            }
        }

        @Override // okhttp3.CookieJar
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            CookieJar cookieJar = this.a;
            List<Cookie> loadForRequest = cookieJar == null ? null : cookieJar.loadForRequest(httpUrl);
            LinkedList linkedList = new LinkedList();
            a(httpUrl, linkedList, "model", Hardware.current(MicoApplication.getGlobalContext()).getName());
            a(httpUrl, linkedList, "sn", Constants.getSn());
            a(httpUrl, linkedList, "channel", SystemSetting.getRomChannel());
            a(httpUrl, linkedList, "romVersion", SystemSetting.getRomVersion());
            if (ContainerUtil.hasData(loadForRequest)) {
                linkedList.addAll(loadForRequest);
            }
            return linkedList;
        }

        static void a(HttpUrl httpUrl, @NonNull List<Cookie> list, String str, String str2) {
            if (str2 != null) {
                Cookie.Builder path = new Cookie.Builder().hostOnlyDomain(httpUrl.host()).httpOnly().path("/");
                if (httpUrl.isHttps()) {
                    path.secure();
                }
                list.add(path.name(str).value(str2).build());
            }
        }
    }

    public static boolean isInited() {
        if (a == null) {
            return false;
        }
        return a.b;
    }
}
