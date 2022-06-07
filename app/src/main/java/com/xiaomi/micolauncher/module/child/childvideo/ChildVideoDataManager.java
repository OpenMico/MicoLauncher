package com.xiaomi.micolauncher.module.child.childvideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import com.elvishew.xlog.Logger;
import com.google.common.base.Joiner;
import com.umeng.analytics.pro.ai;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.converter.GsonConverterFactory;
import com.xiaomi.micolauncher.api.interceptor.LoggingInterceptor;
import com.xiaomi.micolauncher.api.model.CheckOrder;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.ChildVideoDetail;
import com.xiaomi.micolauncher.api.model.ChildVideoHistory;
import com.xiaomi.micolauncher.api.model.ChildVideoMediaInfo;
import com.xiaomi.micolauncher.api.model.ChildVideoPlayList;
import com.xiaomi.micolauncher.api.model.CourseTab;
import com.xiaomi.micolauncher.api.model.ProductPrice;
import com.xiaomi.micolauncher.api.service.mitv.MiTvVipService;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.utils.GenerateOpaqueUtil;
import com.xiaomi.micolauncher.module.video.db.VideoRealmHelper;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.b;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: classes3.dex */
public class ChildVideoDataManager {
    public static final int APP = 21;
    public static final String BIZ_CHANNEL = "MI_SOUND";
    public static int CODEVER = RomUpdateAdapter.getInstance().getVersion().toIntVersion();
    public static final int MITV_CAN_NOT_GET_VIDEO_URL_27 = 27;
    public static final String MI_TV_URL_PREFIX = "/tv/lean/fl/in?id=";
    public static final String PCODE_ERTONG = "com.gitv.xiaomiertong.vip";
    public static final String PCODE_HIGH = "com.gitv.education.high.vip";
    public static final String PCODE_JUNIOR = "com.gitv.education.junior.vip";
    public static final String PCODE_PRIMARY = "com.gitv.education.primary.vip";
    public static final String PCODE_UNKNOWN = "unknown";
    public static final int PTF = 13201;
    public static final String SHORT_KEY_WORD = "/store/pricetag/shortkey/";
    public static final String TEST_USER_ID = "2201893804";
    private List<ChildVideo.ItemsBean> a;
    private Retrofit b;
    private MiTvVipService c;
    private OkHttpClient d;
    private CourseTab e;
    private ChildVideo.MiTvType f;
    private List<VideoItem> g;
    private CopyOnWriteArrayList<VideoItem> h;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static final ChildVideoDataManager a = new ChildVideoDataManager();
    }

    public static ChildVideoDataManager getManager() {
        return a.a;
    }

    private ChildVideoDataManager() {
        this.g = new ArrayList();
        this.h = new CopyOnWriteArrayList<>();
        this.a = new ArrayList();
        Dispatcher dispatcher = new Dispatcher(ThreadUtil.getIoThreadPool());
        dispatcher.setMaxRequests(ThreadUtil.getMaxThreadsOfIoThreadPool());
        final Logger logger = L.api;
        OkHttpClient.Builder addInterceptor = ApiManager.createOkHttpClientBuilderWithNetworkInterceptor().dispatcher(dispatcher).retryOnConnectionFailure(true).connectTimeout(5L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).readTimeout(15L, TimeUnit.SECONDS).addInterceptor(new LoggingInterceptor(logger));
        if (DebugHelper.isDebugInConfg()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$EPYF_kWejOxk8xjNyRtijPtTXkQ
                @Override // okhttp3.logging.HttpLoggingInterceptor.Logger
                public final void log(String str) {
                    ChildVideoDataManager.a(Logger.this, str);
                }
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            addInterceptor.addNetworkInterceptor(httpLoggingInterceptor);
        }
        this.d = addInterceptor.build();
        this.b = new Retrofit.Builder().client(this.d).baseUrl(ApiConstants.isProductionEnv() ? MiTvVipService.API_PRODUCTION_URL : MiTvVipService.API_PREVIEW_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(MicoSchedulers.io())).build();
        this.c = (MiTvVipService) this.b.create(MiTvVipService.class);
    }

    public static /* synthetic */ void a(Logger logger, String str) {
        logger.i("Http logging " + str);
    }

    public Observable<ChildVideo> loadVideoDataFromRemote(String str) {
        if (ApiManager.isInited()) {
            return ApiManager.childVideoService.getChildVideo(str, PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString(str)).doOnNext($$Lambda$ChildVideoDataManager$G8mrf3X62LbqSC78xqC0Bl_0WcM.INSTANCE);
        }
        L.base.w("ApiManager is not inited!");
        return null;
    }

    public static /* synthetic */ void b(ChildVideo childVideo) throws Exception {
        L.childContent.i("ChildVideoDataManager load data from remote  size  %d", Integer.valueOf(ContainerUtil.getSize(childVideo.getBlocks())));
    }

    public Observable<ChildVideoDetail> loadMiTvVideoDetail(String str) {
        if (!ApiManager.isInited()) {
            L.base.w("ApiManager is not inited!!");
            return Observable.just(null);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(1);
        linkedHashMap.put("id", str);
        return ApiManager.childVideoService.getMiTvDetail(str, PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString("/tv/lean/v", linkedHashMap)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<ChildVideoHistory> loadVideoHistory() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(1);
        linkedHashMap.put(AuthorizeActivityBase.KEY_USERID, TEST_USER_ID);
        return ApiManager.childVideoHistoryService.getChildVideoHistory("/tvservice/getplayhistory", TEST_USER_ID, PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString("/tvservice/getplayhistory", linkedHashMap)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<ChildVideoMediaInfo> getMediaList(List<String> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(1);
        ArrayList arrayList = new ArrayList();
        if (ContainerUtil.hasData(list)) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next() + "_0");
            }
            linkedHashMap.put("id", Joiner.on("%2C").join(arrayList));
        }
        return ApiManager.miTvMediaService.getMediaList("/tvservice/getmedialist", Joiner.on(Constants.ACCEPT_TIME_SEPARATOR_SP).join(arrayList), PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString("/tvservice/getmedialist", linkedHashMap)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<ChildVideo.BlocksBean> loadCategoryContent(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$4Y3pVBZHxeFukzUL0fIg0QOmmsM
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ChildVideoDataManager.this.b(str, observableEmitter);
            }
        });
    }

    @SuppressLint({"CheckResult"})
    /* renamed from: a */
    public void b(final Emitter<ChildVideo.BlocksBean> emitter, String str) {
        ApiManager.childVideoService.getChildVideo(str, PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString(str)).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$jr1HCidVyRdxYJt8kyHYyKe3Al0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildVideoDataManager.a(Emitter.this, (ChildVideo) obj);
            }
        });
    }

    public static /* synthetic */ void a(Emitter emitter, ChildVideo childVideo) throws Exception {
        ChildVideo.BlocksBean blocksBean = childVideo.getBlocks().get(0);
        blocksBean.setMeta(childVideo.getMeta());
        emitter.onNext(blocksBean);
        emitter.onComplete();
    }

    public Observable<List<ChildVideo.ItemsBean>> a() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$sceLuZzf-GpiinxfQHbCH_lKCgQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ChildVideoDataManager.this.c(observableEmitter);
            }
        });
    }

    public /* synthetic */ void c(ObservableEmitter observableEmitter) throws Exception {
        if (!ContainerUtil.isEmpty(this.a)) {
            observableEmitter.onNext(this.a);
            observableEmitter.onComplete();
            return;
        }
        loadVideoTypeFromRemote();
    }

    @SuppressLint({"CheckResult"})
    public void loadVideoTypeFromRemote() {
        this.a.clear();
        ApiManager.childVideoService.getChildVideo("/tv/lean/fl/in?id=2146", PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString("/tv/lean/fl/in?id=2146")).flatMap($$Lambda$ChildVideoDataManager$I6XPLIY3nV29lBczSmELT9w68c.INSTANCE).flatMap($$Lambda$ChildVideoDataManager$C_RbCYhgpvaUmEOqT1H7tWuh8TU.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$88TG-lJPRRIKKnMhxllxFJEpkUI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildVideoDataManager.this.a((ChildVideo.ItemsBean) obj);
            }
        });
    }

    public static /* synthetic */ ObservableSource a(ChildVideo childVideo) throws Exception {
        return Observable.fromIterable(childVideo.getBlocks());
    }

    public static /* synthetic */ ObservableSource a(ChildVideo.BlocksBean blocksBean) throws Exception {
        return Observable.fromIterable(blocksBean.getItems());
    }

    public /* synthetic */ void a(ChildVideo.ItemsBean itemsBean) throws Exception {
        this.a.add(itemsBean);
        L.childContent.i("save video types  %s", itemsBean.getItemTitle());
    }

    public Observable<ProductPrice> a(Context context, String str) {
        return ApiManager.miTvService.getMiTvProductPrice(SystemSetting.getDeviceID(), PTF, Build.VERSION.SDK_INT, "zh", "CN", "[\"".concat(str.concat("\"]")), WiFiUtil.getMacAddress(), BIZ_CHANNEL, MiTvVipActivity.PRICE_CODEVER, LoginManager.get().getUserId()).doOnNext($$Lambda$ChildVideoDataManager$9a6GLDS6WV546ZAfxnixSM2s8A.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public static /* synthetic */ void a(ProductPrice productPrice) throws Exception {
        for (ProductPrice.PriceBean priceBean : productPrice.getData()) {
            ProductPrice.PriceBean.CpData cpData = (ProductPrice.PriceBean.CpData) Gsons.getGson().fromJson(priceBean.getCp_data(), (Class<Object>) ProductPrice.PriceBean.CpData.class);
            priceBean.setRenew(cpData.getType() == 101);
            priceBean.setCpData(cpData);
        }
    }

    public Observable<ChildVideo.VipStatus> getVipStatus(final String str, final String str2) {
        return generateOttMiniServiceCookie().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$Sfi5dH0M37yUh1SMFzDe0nowbow
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = ChildVideoDataManager.this.b(str2, str, (String) obj);
                return b;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public /* synthetic */ ObservableSource b(String str, String str2, String str3) throws Exception {
        return this.c.getUserBuyCheck(str3, "zh", "CN", "[\"".concat(str.concat("\"]")), str2, String.valueOf((int) PTF), String.valueOf(Build.VERSION.SDK_INT), SystemSetting.getDeviceID(), String.valueOf(107)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<ChildVideo.VipStatus> getSevenDayVip(final String str, final String str2) {
        return generateOttMiniServiceCookie().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$t659BFxYdezEyS2h9XVAgX4DwNk
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = ChildVideoDataManager.this.a(str2, str, (String) obj);
                return a2;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public /* synthetic */ ObservableSource a(String str, String str2, String str3) throws Exception {
        return this.c.getSevenDayVip(str3, "zh", "CN", "[\"".concat(str.concat("\"]")), str2, String.valueOf((int) PTF), String.valueOf(Build.VERSION.SDK_INT), SystemSetting.getDeviceID(), String.valueOf(107)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<String> a(Context context, String str, String str2, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("deviceID", SystemSetting.getDeviceID());
            jSONObject.put("platform", PTF);
            jSONObject.put("sdk_version", Build.VERSION.SDK_INT);
            jSONObject.put(ai.N, "zh");
            jSONObject.put(ai.O, "CN");
            jSONObject.put("sn", "unknow");
            jSONObject.put(b.B, WiFiUtil.getMacAddress());
            jSONObject.put("biz", 107);
            jSONObject.put("codever", CODEVER);
            jSONObject.put(AuthorizeActivityBase.KEY_USERID, TokenManager.getInstance().getUserId());
            jSONObject.put("pcode", str);
            jSONObject.put("partner", "voice_children");
            jSONObject.put("renew", 1);
            jSONObject.put("bizChannel", BIZ_CHANNEL);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("id", str2);
            jSONObject2.put("quantity", 1);
            jSONObject.put("products", jSONObject2);
        } catch (JSONException e) {
            Logger logger = L.childmode;
            logger.e("getShortKey " + str2 + " isRenew" + z, e);
        }
        return ApiManager.miTvBuyService.getPriceTagShortKey(1, z ? 1 : 0, jSONObject.toString()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).map($$Lambda$ChildVideoDataManager$qFjSkEee5osMyQwb6Pz_TKUgPwQ.INSTANCE);
    }

    public static /* synthetic */ String a(ProductPrice.PriceTag priceTag) throws Exception {
        return priceTag.getData().getShortKey();
    }

    public Observable<CheckOrder.CheckRenewOrder> a(String str) {
        return ApiManager.miTvBuyService.checkRenewOrder(str, 1, str).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<CheckOrder.CheckNotRenewOrder> b(String str) {
        return ApiManager.miTvBuyService.checkNotRenewOrder(str, 0, str).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<ChildVideoPlayList> getMiTvPlayList(String str, int i, int i2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(1);
        linkedHashMap.put("id", str);
        linkedHashMap.put("page", Integer.valueOf(i));
        linkedHashMap.put("page_size", Integer.valueOf(i2));
        return ApiManager.miTvPlayListService.getMiTvPlayList(str, i, i2, PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString("/tv2/lean/v/play/list", linkedHashMap)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<ChildVideoPlayList> getMiTvPlayListAsync(String str, int i, int i2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(1);
        linkedHashMap.put("id", str);
        linkedHashMap.put("page", Integer.valueOf(i));
        linkedHashMap.put("page_size", Integer.valueOf(i2));
        return ApiManager.miTvPlayListService.getMiTvPlayList(str, i, i2, PTF, 21, CODEVER, SystemSetting.getDeviceID(), GenerateOpaqueUtil.getOpaqueString("/tv2/lean/v/play/list", linkedHashMap)).subscribeOn(MicoSchedulers.io());
    }

    public Observable<ChildVideo.DueTime> c(String str) {
        if (ApiManager.isInited()) {
            return ApiManager.displayService.getVipDueTime(Build.VERSION.SDK_INT, 107, str, String.valueOf((int) PTF)).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
        }
        return null;
    }

    @SuppressLint({"CheckResult"})
    public void updateVipStatus(String str) {
        if (ApiManager.isInited()) {
            ApiManager.displayService.getVipDueTime(Build.VERSION.SDK_INT, 107, str, String.valueOf((int) PTF)).subscribeOn(MicoSchedulers.io()).subscribe($$Lambda$ChildVideoDataManager$RH58J248WQcnOu5BiN9Tn_uG63g.INSTANCE);
        } else {
            L.base.w("ApiManager is not inited!");
        }
    }

    public static /* synthetic */ void a(ChildVideo.DueTime dueTime) throws Exception {
        if (dueTime.getDueTime() == 0 || dueTime.getCode() != 0 || dueTime.getDueTime() * 1000 < System.currentTimeMillis()) {
            SystemSetting.setKeyMiTvVipStatus(false);
        } else {
            SystemSetting.setKeyMiTvVipStatus(true);
        }
    }

    public Observable<ChildVideo.MiTvPromotion> getVipPromotion(Context context) {
        return ApiManager.miTvService.getMiTvPromotion(SystemSetting.getDeviceID(), PTF, Build.VERSION.SDK_INT, "zh", "CN", WiFiUtil.getMacAddress(), System.currentTimeMillis(), TokenManager.getInstance().getUserId()).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread());
    }

    public Observable<List<VideoItem>> loadVideoListFromDb() {
        this.h.clear();
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$8zBtchQE4m_HgJIAsYCSZeAyD2Q
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ChildVideoDataManager.this.b(observableEmitter);
            }
        });
    }

    public /* synthetic */ void b(ObservableEmitter observableEmitter) throws Exception {
        List<VideoItem> findAll = VideoRealmHelper.getInstance().findAll("mitv");
        if (ContainerUtil.hasData(findAll)) {
            for (VideoItem videoItem : findAll) {
                if (!PCODE_PRIMARY.equals(videoItem.getpCode()) && !PCODE_JUNIOR.equals(videoItem.getpCode()) && !PCODE_HIGH.equals(videoItem.getpCode())) {
                    this.h.add(videoItem);
                }
            }
        }
        observableEmitter.onNext(this.h);
        observableEmitter.onComplete();
    }

    public Observable<List<VideoItem>> loadCourseListFromDb() {
        this.g.clear();
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$XgVF49t0uRaBCjfRw1pzLzSA-nw
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ChildVideoDataManager.this.a(observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        List<VideoItem> findAll = VideoRealmHelper.getInstance().findAll("mitv");
        if (ContainerUtil.hasData(findAll)) {
            for (VideoItem videoItem : findAll) {
                if (PCODE_PRIMARY.equals(videoItem.getpCode()) || PCODE_JUNIOR.equals(videoItem.getpCode()) || PCODE_HIGH.equals(videoItem.getpCode())) {
                    this.g.add(videoItem);
                }
            }
        }
        observableEmitter.onNext(this.g);
        observableEmitter.onComplete();
    }

    public Observable<VideoItem> loadVideoItemFromDb(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$ChildVideoDataManager$GkHRb8qzi0sAW_PsBNBa4kN3DS0
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                ChildVideoDataManager.a(str, observableEmitter);
            }
        });
    }

    public static /* synthetic */ void a(String str, ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(VideoRealmHelper.getInstance().findVideoItem("mitv", str));
        observableEmitter.onComplete();
    }

    public Observable<String> getVideoHistoryCover() {
        return loadVideoListFromDb().map($$Lambda$ChildVideoDataManager$1cZhtWOzSZFgCibz71MOBPuTTw.INSTANCE);
    }

    public static /* synthetic */ String a(List list) throws Exception {
        if (ContainerUtil.hasData(list)) {
            return ((VideoItem) list.get(0)).getImageUrl();
        }
        return "";
    }

    public CourseTab getCurrentCourseTab() {
        return this.e;
    }

    public void setCurrentCourseTab(CourseTab courseTab) {
        this.e = courseTab;
        this.f = d(courseTab.getPcode());
    }

    private ChildVideo.MiTvType d(String str) {
        return new ChildVideo.ItemsBean(str).getMiTvType();
    }

    public ChildVideo.MiTvType getMiTvType() {
        return this.f;
    }

    public void setMiTvType(ChildVideo.MiTvType miTvType) {
        this.f = miTvType;
    }

    public static Observable<String> generateOttMiniServiceCookie() {
        return TokenManager.getInstance().refreshServiceToken(com.xiaomi.micolauncher.application.Constants.OTT_MINI_PROGRAM_SID).map($$Lambda$ChildVideoDataManager$sgenZKcE1RGQ39rs2s9NTp3Mofc.INSTANCE);
    }

    public static /* synthetic */ String e(String str) throws Exception {
        if (ContainerUtil.isEmpty(str)) {
            str = TokenManager.getInstance().getServiceInfo(com.xiaomi.micolauncher.application.Constants.OTT_MINI_PROGRAM_SID).getServiceToken();
        }
        return String.format("serviceToken=%s;userId=%s;", str, TokenManager.getInstance().getUserId());
    }
}
