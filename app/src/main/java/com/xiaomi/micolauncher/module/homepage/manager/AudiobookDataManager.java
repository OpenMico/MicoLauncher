package com.xiaomi.micolauncher.module.homepage.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.common.MibrainConfig;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.cache.ApiRealmHelper;
import com.xiaomi.micolauncher.api.model.Station;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.homepage.bean.AudiobookContent;
import com.xiaomi.micolauncher.module.homepage.bean.OprationAudiobook;
import com.xiaomi.micolauncher.module.homepage.bean.Order;
import com.xiaomi.micolauncher.module.homepage.event.LoadAllCategoryDatasEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadAudiobookRecommendEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadBlackListEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadCategoryDatasEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadCollectEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadHistoryEvent;
import com.xiaomi.micolauncher.module.homepage.event.LoadPaymentOrderEvent;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class AudiobookDataManager {
    public static final String FIRST_ITEM_ID = "firstItemId";
    public static final String SECOND_ITEM_ID = "secondItemId";
    private final HashMap<String, String> a;
    private final HashMap<String, Integer> b;
    private int[] c;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a {
        private static AudiobookDataManager a = new AudiobookDataManager();
    }

    private AudiobookDataManager() {
        this.a = new HashMap<>();
        this.b = new HashMap<>();
        this.c = new int[]{R.drawable.book_culture_icon, R.drawable.book_history_icon, R.drawable.book_hot_icon, R.drawable.book_live_icon, R.drawable.book_news_icon, R.drawable.book_quyi_icon, R.drawable.book_recreation_icon};
    }

    public static AudiobookDataManager getManager() {
        return a.a;
    }

    @SuppressLint({"CheckResult"})
    public void loadRecommendData(final Context context, String str, String str2) {
        L.homepage.d("start loadRecommendData firstStartId %s,secondStartId %s", str, str2);
        String name = Hardware.current(context).getName();
        Observable create = Observable.create($$Lambda$AudiobookDataManager$PQ9fR0MvTZ_89EMP3lprVbsmfw.INSTANCE);
        Observable.zip(ApiManager.displayService.getFirstRegionData(name, str, "").onErrorResumeNext(create), ApiManager.displayService.getSecondRegionData(name, str2, "").onErrorResumeNext(create), ApiManager.displayService.getRecommendData(TokenManager.getInstance().getUserId(), "", 50), new Function3() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$ujp_zbK7nTZsvoVxS_bthdtDVck
            @Override // io.reactivex.functions.Function3
            public final Object apply(Object obj, Object obj2, Object obj3) {
                LoadAudiobookRecommendEvent a2;
                a2 = AudiobookDataManager.a(context, (OprationAudiobook) obj, (OprationAudiobook) obj2, (List) obj3);
                return a2;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$AudiobookDataManager$jd7Fege8HUzGlpNc_Dt_0Xq57dc.INSTANCE, $$Lambda$AudiobookDataManager$yZI67_xJhYMJB4LAwrAj0ZPCgcI.INSTANCE);
    }

    public static /* synthetic */ void e(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(new OprationAudiobook());
    }

    public static /* synthetic */ LoadAudiobookRecommendEvent a(Context context, OprationAudiobook oprationAudiobook, OprationAudiobook oprationAudiobook2, List list) throws Exception {
        if (ContainerUtil.hasData(oprationAudiobook.getItemList())) {
            PreferenceUtils.setSettingString(context, FIRST_ITEM_ID, oprationAudiobook.getItemList().get(0).getId());
        }
        int size = oprationAudiobook2.getItemList().size();
        if (size > 0) {
            if (size < 3) {
                PreferenceUtils.setSettingString(context, SECOND_ITEM_ID, oprationAudiobook2.getItemList().get(size - 1).getId());
            } else {
                PreferenceUtils.setSettingString(context, SECOND_ITEM_ID, oprationAudiobook2.getItemList().get(2).getId());
            }
        }
        if (ContainerUtil.isEmpty(oprationAudiobook.getItemList()) && ContainerUtil.hasData(list)) {
            oprationAudiobook.putContent((AudiobookContent) list.remove(0));
            if (ContainerUtil.hasData(list)) {
                oprationAudiobook.putContent((AudiobookContent) list.remove(0));
            }
        }
        if (size < 3 && ContainerUtil.hasData(list)) {
            for (int i = 0; i < 3 - size && ContainerUtil.hasData(list); i++) {
                oprationAudiobook2.putContent((AudiobookContent) list.remove(0));
            }
        }
        L.homepage.d("first content is %s,second content is %s,third content is %s", oprationAudiobook, oprationAudiobook2, list);
        return new LoadAudiobookRecommendEvent(oprationAudiobook, oprationAudiobook2, list);
    }

    public static /* synthetic */ void a(LoadAudiobookRecommendEvent loadAudiobookRecommendEvent) throws Exception {
        EventBusRegistry.getEventBus().post(loadAudiobookRecommendEvent);
    }

    public static /* synthetic */ void f(Throwable th) throws Exception {
        EventBusRegistry.getEventBus().post(new LoadAudiobookRecommendEvent(null, null, null));
        L.homepage.d(" load recommend data error : %s", th);
    }

    @SuppressLint({"CheckResult"})
    public void loadCollectList(Context context) {
        LocalPlayerManager.getInstance().loadBlackList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$r9EMkx09Q3JYcm2wQjrgMdTs0yI
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource k;
                k = AudiobookDataManager.this.k((List) obj);
                return k;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$40sGR0gZrq97PtG1RVYxTxyrePg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.j((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$eOVUpa6VfZdOter313HMxouAwcU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.e((Throwable) obj);
            }
        });
    }

    public /* synthetic */ ObservableSource k(List list) throws Exception {
        a(list);
        return ApiManager.minaService.getStationCollectedList("audioBooks");
    }

    public /* synthetic */ void j(List list) throws Exception {
        EventBusRegistry.getEventBus().post(new LoadCollectEvent(list));
        if (ContainerUtil.hasData(list)) {
            a("apphomepage/station/get_collected", (Object) list);
        }
    }

    public /* synthetic */ void e(Throwable th) throws Exception {
        a();
        L.homepage.d(" loadCollectList error : %s", th);
    }

    private void a(List<String> list) {
        if (ContainerUtil.hasData(list)) {
            EventBusRegistry.getEventBus().post(new LoadBlackListEvent(list));
        }
    }

    public Observable<List<Station.Item>> loadCollectListFor04() {
        return ApiManager.minaService.getStationCollectedList("audioBooks");
    }

    public Observable<List<String>> loadBlackList(Context context) {
        String str = MibrainConfig.getMibrainConfig(context).clientId + " || " + TokenManager.getInstance().getUserId();
        return ApiManager.aiMiService.getBlackList(SpeechManager.getInstance().getAuthorizationValueBlocked());
    }

    private void a() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$_EwFYxOW6OVcNbf5nk-ZS9y764w
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AudiobookDataManager.this.d(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$AudiobookDataManager$gV28bJSlbfjP9GaV_dMgPG6X5os.INSTANCE);
    }

    public /* synthetic */ void d(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("apphomepage/station/get_collected");
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (List) Gsons.getGson().fromJson(find, new TypeToken<List<Station.Item>>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager.1
        }.getType()) : null);
    }

    public static /* synthetic */ void i(List list) throws Exception {
        EventBusRegistry.getEventBus().post(new LoadCollectEvent(list));
    }

    public void loadHistory(final int i) {
        LocalPlayerManager.getInstance().loadBlackList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$UlJBBS2kWTO5tLuDPFQJNj9VZYo
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = AudiobookDataManager.this.a(i, (List) obj);
                return a2;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$TOsX_m8zaIW9gfZsO_3qZcUwIVU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.h((List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$ZTGnvEQetYdN_wIDnSqjCQsBa80
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.d((Throwable) obj);
            }
        });
    }

    public /* synthetic */ ObservableSource a(int i, List list) throws Exception {
        a(list);
        return ApiManager.minaService.getStationHistory(Hardware.HARDWARE.getName(), i, "audioBooks");
    }

    public /* synthetic */ void h(List list) throws Exception {
        EventBusRegistry.getEventBus().post(new LoadHistoryEvent(list));
        if (ContainerUtil.hasData(list)) {
            a("apphomepage/station/stationHistory", (Object) list);
        }
    }

    public /* synthetic */ void d(Throwable th) throws Exception {
        b();
        L.homepage.d(" loadHistory error : %s", th);
    }

    public Observable<List<Station.Item>> loadHistoryFor04(Context context, int i) {
        return ApiManager.minaService.getStationHistory(Hardware.current(context).getName(), i, "audioBooks");
    }

    private void b() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$aJT0OFetMJdv2rBgyiCju6hx66M
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AudiobookDataManager.this.c(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$AudiobookDataManager$ze_vj53uFqmulY2rzdI5vzgZAA.INSTANCE);
    }

    public /* synthetic */ void c(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("apphomepage/station/stationHistory");
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (List) Gsons.getGson().fromJson(find, new TypeToken<List<Station.Item>>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager.2
        }.getType()) : null);
    }

    public static /* synthetic */ void g(List list) throws Exception {
        EventBusRegistry.getEventBus().post(new LoadHistoryEvent(list));
    }

    @SuppressLint({"CheckResult"})
    public void loadCategoryDatas() {
        Observable<List<Station.CategoryItem>> d = d();
        if (d != null) {
            d.flatMap($$Lambda$AudiobookDataManager$_3eS2WKANh0GYT2z3YJA3KF5TMY.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$AudiobookDataManager$NOHta6MALSwidqO9wEG4nW7QQu4.INSTANCE, $$Lambda$AudiobookDataManager$0tSqCDu5sVB_ZVnhp8xeSc1mkeg.INSTANCE);
        }
    }

    public static /* synthetic */ ObservableSource f(List list) throws Exception {
        LoadAllCategoryDatasEvent loadAllCategoryDatasEvent = new LoadAllCategoryDatasEvent();
        loadAllCategoryDatasEvent.categorys = list;
        EventBusRegistry.getEventBus().post(loadAllCategoryDatasEvent);
        return ApiManager.displayService.getRecommendData(TokenManager.getInstance().getUserId(), ((Station.CategoryItem) list.get(0)).name, 50);
    }

    public static /* synthetic */ void e(List list) throws Exception {
        LoadAllCategoryDatasEvent loadAllCategoryDatasEvent = new LoadAllCategoryDatasEvent();
        loadAllCategoryDatasEvent.datas = list;
        EventBusRegistry.getEventBus().post(loadAllCategoryDatasEvent);
    }

    public static /* synthetic */ void c(Throwable th) throws Exception {
        L.homepage.d(" loadCategoryDatas error : %s", th);
    }

    @SuppressLint({"CheckResult"})
    public void loadAllCategory() {
        Observable<List<Station.CategoryItem>> d = d();
        if (d != null) {
            d.subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$7kZGn2feO3eORoarGM2BHOg_q4w
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AudiobookDataManager.this.d((List) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$doeOCkthOm04C74WUQNe_8MSO-Q
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AudiobookDataManager.this.b((Throwable) obj);
                }
            });
        }
    }

    public /* synthetic */ void d(List list) throws Exception {
        if (ContainerUtil.isEmpty(list)) {
            c();
            return;
        }
        a("apphomepage/station/category_list", (Object) list);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Station.CategoryItem categoryItem = (Station.CategoryItem) it.next();
            this.a.put(categoryItem.name, categoryItem.parentName);
        }
    }

    public /* synthetic */ void b(Throwable th) throws Exception {
        c();
        L.homepage.d(" loadAllCategory error : %s", th);
    }

    @SuppressLint({"CheckResult"})
    private void c() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$MIK8OHoSd4Hug07i-OKj2_Uyujc
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AudiobookDataManager.this.b(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$VpMYUfhbvptRr_1IJjEVzCDqSPc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.c((List) obj);
            }
        });
    }

    public /* synthetic */ void b(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("apphomepage/station/category_list");
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (List) Gsons.getGson().fromJson(find, new TypeToken<List<Station.CategoryItem>>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager.3
        }.getType()) : null);
    }

    public /* synthetic */ void c(List list) throws Exception {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Station.CategoryItem categoryItem = (Station.CategoryItem) it.next();
            this.a.put(categoryItem.name, categoryItem.parentName);
        }
    }

    private Observable<List<Station.CategoryItem>> d() {
        if (ApiManager.isInited()) {
            return ApiManager.minaService.getAudiobookCategoryList();
        }
        L.base.w("ApiManager is not inited!");
        return null;
    }

    @SuppressLint({"CheckResult"})
    public void loadAudiobookAll(final String str) {
        ApiManager.displayService.getRecommendData(TokenManager.getInstance().getUserId(), str, 50).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$lrf_3M4KGEyy9Z0w7gCMnYGOyws
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.a(str, (List) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$SqSddLPfiANxLwM3UpJiJsdOxpU
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.a(str, (Throwable) obj);
            }
        });
    }

    public /* synthetic */ void a(String str, List list) throws Exception {
        LoadCategoryDatasEvent loadCategoryDatasEvent = new LoadCategoryDatasEvent();
        loadCategoryDatasEvent.datas = list;
        EventBusRegistry.getEventBus().post(loadCategoryDatasEvent);
        if (ContainerUtil.hasData(list)) {
            a(str, (Object) list);
        }
    }

    public /* synthetic */ void a(String str, Throwable th) throws Exception {
        a(str);
        L.homepage.d(" loadAudiobookAll error : %s", th);
    }

    private void a(final String str) {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$5Gljpn_gWUVJ6VBP4Cge_I70gSw
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AudiobookDataManager.this.a(str, observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$AudiobookDataManager$JNsqqryy3uiEVdLltvVS6QsZJI.INSTANCE);
    }

    public /* synthetic */ void a(String str, ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find(str);
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (List) Gsons.getGson().fromJson(find, new TypeToken<List<AudiobookContent>>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager.4
        }.getType()) : null);
    }

    public static /* synthetic */ void b(List list) throws Exception {
        LoadCategoryDatasEvent loadCategoryDatasEvent = new LoadCategoryDatasEvent();
        loadCategoryDatasEvent.datas = list;
        EventBusRegistry.getEventBus().post(loadCategoryDatasEvent);
    }

    @SuppressLint({"CheckResult"})
    public void loadPaymentOrders(Context context, final long j) {
        LocalPlayerManager.getInstance().loadBlackList().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$B4RzCLax4GzUMTJ5EhZJSm3AGeg
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = AudiobookDataManager.this.a(j, (List) obj);
                return a2;
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$r-kr86jF4N_wi2RDA3IbYjXpadI
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource c;
                c = AudiobookDataManager.this.c((Order) obj);
                return c;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$toA3iqdpHp0lKKSn3pNMVoM0V8w
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.b((Order) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$oMU9513jWPSpz3agsYnHqjyhSM8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AudiobookDataManager.this.a((Throwable) obj);
            }
        });
    }

    public /* synthetic */ ObservableSource a(long j, List list) throws Exception {
        a(list);
        return ApiManager.paymentService.paymentOrder(j, 1000, "AudioBooks");
    }

    public /* synthetic */ ObservableSource c(Order order) throws Exception {
        String find = ApiRealmHelper.getInstance().find("apphomepage/station/get_collected");
        if (!TextUtils.isEmpty(find)) {
            List list = (List) Gsons.getGson().fromJson(find, new TypeToken<List<Station.Item>>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager.5
            }.getType());
            for (Order.OrderInfo orderInfo : order.list) {
                Iterator it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Station.Item item = (Station.Item) it.next();
                        if (TextUtils.equals(orderInfo.productId, item.getAlbumId())) {
                            orderInfo.broadcaster = item.getBroadcaster();
                            break;
                        }
                    }
                }
            }
        }
        return Observable.just(order);
    }

    public /* synthetic */ void b(Order order) throws Exception {
        EventBusRegistry.getEventBus().post(new LoadPaymentOrderEvent(order));
        if (ContainerUtil.hasData(order.list)) {
            a("/payment/scan_order", order);
        }
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        e();
        L.homepage.d(" loadPaymentOrders error : %s", th);
    }

    public Observable<Order> loadPaymentOrdersFor04(long j) {
        return ApiManager.paymentService.paymentOrder(j, 1000, "AudioBooks");
    }

    private void e() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$Dk3XPoJVMY2fWjqXjc_HaSwI14w
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                AudiobookDataManager.this.a(observableEmitter);
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$AudiobookDataManager$VYC00qVLbHQKyeyvuP1Ipe_iqV8.INSTANCE);
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        String find = ApiRealmHelper.getInstance().find("apphomepage/station/category_list");
        observableEmitter.onNext(!TextUtils.isEmpty(find) ? (Order) Gsons.getGson().fromJson(find, new TypeToken<Order>() { // from class: com.xiaomi.micolauncher.module.homepage.manager.AudiobookDataManager.6
        }.getType()) : null);
    }

    public static /* synthetic */ void a(Order order) throws Exception {
        EventBusRegistry.getEventBus().post(new LoadPaymentOrderEvent(order));
    }

    public void initResIdMap(Context context) {
        String[] stringArray = context.getResources().getStringArray(R.array.audiobook_category);
        for (int i = 0; i < stringArray.length; i++) {
            this.b.put(stringArray[i], Integer.valueOf(this.c[i]));
        }
    }

    public int getResId(String str) {
        Integer num = this.b.get(str);
        if (num != null) {
            return num.intValue();
        }
        Integer num2 = this.b.get(this.a.get(str));
        return num2 == null ? R.drawable.book_recreation_icon : num2.intValue();
    }

    @SuppressLint({"CheckResult"})
    private void a(final String str, final Object obj) {
        if (ThreadUtil.isMainThread()) {
            Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.homepage.manager.-$$Lambda$AudiobookDataManager$dANYjt6IVKGwHz4CMAhOw_7_UaY
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    AudiobookDataManager.a(obj, str, observableEmitter);
                }
            }).subscribeOn(MicoSchedulers.io()).subscribe();
        } else {
            ApiRealmHelper.getInstance().updateAsync(str, Gsons.getGson().toJson(obj));
        }
    }

    public static /* synthetic */ void a(Object obj, String str, ObservableEmitter observableEmitter) throws Exception {
        ApiRealmHelper.getInstance().updateAsync(str, Gsons.getGson().toJson(obj));
    }
}
