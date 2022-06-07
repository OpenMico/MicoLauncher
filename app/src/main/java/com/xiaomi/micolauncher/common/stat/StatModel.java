package com.xiaomi.micolauncher.common.stat;

import android.annotation.SuppressLint;
import android.content.Context;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.MD5;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rx.OnSubscribeRealm;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.stat.StatModel;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.ImportFlag;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class StatModel {
    public static final int MAX_SAVE_COUNT = 10;
    private static final String SOURCE = "rom";
    private static final String TRACK_REALM_NAME = "mico_stat_realm";
    private static final int TRACK_REALM_VERSION = 0;
    private static RealmConfiguration realmConfiguration;
    public String deviceId;
    public String hardware;
    public List<StatEvent> list;
    public String serialNumber;
    public String source;
    public String version;

    public String toPostJson() {
        return Gsons.getGson().toJson(this);
    }

    @SuppressLint({"CheckResult"})
    public static void postEvents(final Context context, final int i, final StatUtils.StatPointsPostListener statPointsPostListener) {
        loadEventCount().subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$NUfhaw4hSWjDpOKpaP0z3Fby4WA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StatModel.lambda$postEvents$0(i, context, statPointsPostListener, (Integer) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$-jZxLmZgOWgPADR0QZYw1gwqMmE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StatModel.lambda$postEvents$1(StatUtils.StatPointsPostListener.this, (Throwable) obj);
            }
        });
    }

    public static /* synthetic */ void lambda$postEvents$0(int i, Context context, StatUtils.StatPointsPostListener statPointsPostListener, Integer num) throws Exception {
        L.base.i("post event local count = %s maxCount=%s", num, Integer.valueOf(i));
        if (num.intValue() > i) {
            doPostEvents(context, statPointsPostListener);
        } else if (statPointsPostListener != null) {
            statPointsPostListener.onFail("less max count");
        }
    }

    public static /* synthetic */ void lambda$postEvents$1(StatUtils.StatPointsPostListener statPointsPostListener, Throwable th) throws Exception {
        L.base.e("load event count error", th);
        if (statPointsPostListener != null) {
            statPointsPostListener.onFail("load event error");
        }
    }

    private static void doPostEvents(final Context context, final StatUtils.StatPointsPostListener statPointsPostListener) {
        loadEvents().flatMap(new Function() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$QMKSvJdvC2LvTE0zDB8T48Lvg44
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource buildEvents;
                buildEvents = StatModel.buildEvents(context, (RealmResults) obj);
                return buildEvents;
            }
        }).flatMap($$Lambda$StatModel$rDv6XRXIKfWc5aYN8Qj_zGOs18.INSTANCE).flatMap($$Lambda$StatModel$TXhbVkfAfVzvuEFkL_EPCUFd34o.INSTANCE).subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$YDbQCmmnfAqVi9fdA8E8tVt5K7c
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StatModel.lambda$doPostEvents$5(StatUtils.StatPointsPostListener.this, (Boolean) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$VMLmPzPXfvLJITurIIdEDq9JSrA
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                StatModel.lambda$doPostEvents$6(StatUtils.StatPointsPostListener.this, (Throwable) obj);
            }
        });
    }

    public static /* synthetic */ ObservableSource lambda$doPostEvents$3(StatModel statModel) throws Exception {
        if (ApiManager.isInited()) {
            return ApiManager.minaService.statLog(statModel.toPostJson());
        }
        return null;
    }

    public static /* synthetic */ void lambda$doPostEvents$5(StatUtils.StatPointsPostListener statPointsPostListener, Boolean bool) throws Exception {
        L.base.i("post event delete event ret =%s", bool);
        if (statPointsPostListener == null) {
            return;
        }
        if (bool.booleanValue()) {
            statPointsPostListener.onSuccess();
        } else {
            statPointsPostListener.onFail("delete event error");
        }
    }

    public static /* synthetic */ void lambda$doPostEvents$6(StatUtils.StatPointsPostListener statPointsPostListener, Throwable th) throws Exception {
        L.base.e("post event error", th);
        if (statPointsPostListener != null) {
            statPointsPostListener.onFail(th.getMessage());
        }
    }

    @SuppressLint({"MissingPermission"})
    public static Observable<StatModel> buildEvents(final Context context, final List<MicoTrackEvent> list) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$AmZoT-HlN73H-568PcUf5kwAlYs
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                StatModel.lambda$buildEvents$7(context, list, observableEmitter);
            }
        });
    }

    public static /* synthetic */ void lambda$buildEvents$7(Context context, List list, ObservableEmitter observableEmitter) throws Exception {
        StatModel statModel = new StatModel();
        statModel.serialNumber = Constants.getSn();
        statModel.hardware = Hardware.current(context).getName();
        statModel.source = "rom";
        statModel.deviceId = SystemSetting.getDeviceID();
        statModel.version = SystemSetting.getRomVersion() + "|" + SystemSetting.getRomChannel();
        statModel.list = new ArrayList();
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            StatEvent statEvent = ((MicoTrackEvent) it.next()).toStatEvent();
            StatEvent statEvent2 = (StatEvent) hashMap.get(statEvent.event);
            if (statEvent2 == null) {
                StatEvent statEvent3 = new StatEvent();
                statEvent3.event = statEvent.event;
                statEvent3.uuidHash = statEvent.buildUUIDHash();
                statEvent3.data = new ArrayList();
                statEvent3.data.addAll(statEvent.data);
                statModel.list.add(statEvent3);
                hashMap.put(statEvent.event, statEvent3);
            } else {
                statEvent2.data.addAll(statEvent.data);
            }
        }
        for (int i = 0; i < statModel.list.size(); i++) {
            statModel.list.get(i).compressData();
        }
        observableEmitter.onNext(statModel);
        observableEmitter.onComplete();
    }

    private static Observable<RealmResults<MicoTrackEvent>> loadEvents() {
        return Observable.create(new OnSubscribeRealm<RealmResults<MicoTrackEvent>>(false) { // from class: com.xiaomi.micolauncher.common.stat.StatModel.1
            @Override // com.xiaomi.micolauncher.common.rx.OnSubscribeRealm
            public RealmResults<MicoTrackEvent> operate(Realm realm) {
                return realm.where(MicoTrackEvent.class).findAll();
            }

            @Override // com.xiaomi.micolauncher.common.rx.OnSubscribeRealm
            public Realm getRealm() {
                return Realm.getInstance(StatModel.getRealmConfiguration());
            }
        });
    }

    public static Observable<Boolean> removeEvents() {
        return Observable.create(new OnSubscribeRealm<Boolean>(false) { // from class: com.xiaomi.micolauncher.common.stat.StatModel.2
            @Override // com.xiaomi.micolauncher.common.rx.OnSubscribeRealm
            public Boolean operate(Realm realm) {
                return Boolean.valueOf(realm.where(MicoTrackEvent.class).findAll().deleteAllFromRealm());
            }

            @Override // com.xiaomi.micolauncher.common.rx.OnSubscribeRealm
            public Realm getRealm() {
                return Realm.getInstance(StatModel.getRealmConfiguration());
            }
        });
    }

    private static Observable<Integer> loadEventCount() {
        return Observable.create($$Lambda$StatModel$EIsUobBinyNOtx1H02c4sGBuMRk.INSTANCE);
    }

    public static /* synthetic */ void lambda$loadEventCount$8(ObservableEmitter observableEmitter) throws Exception {
        Realm instance = Realm.getInstance(getRealmConfiguration());
        long count = instance.where(MicoTrackEvent.class).count();
        if (count >= 2147483647L) {
            L.base.e("too many records ! %s", Long.valueOf(count));
        }
        observableEmitter.onNext(Integer.valueOf((int) count));
        observableEmitter.onComplete();
        instance.close();
    }

    public static RealmConfiguration getRealmConfiguration() {
        if (realmConfiguration == null) {
            synchronized (StatModel.class) {
                if (realmConfiguration == null) {
                    realmConfiguration = new RealmConfiguration.Builder().name(TRACK_REALM_NAME).schemaVersion(0L).modules(new MicoTrackEventModule(), new Object[0]).deleteRealmIfMigrationNeeded().build();
                }
            }
        }
        return realmConfiguration;
    }

    /* loaded from: classes3.dex */
    public static class StatValue {
        public String c;
        public String t;
        public String v;

        public boolean equals(StatValue statValue) {
            return this.t.equals(statValue.t) && this.v.equals(statValue.v);
        }

        public String key() {
            return this.t + this.v;
        }
    }

    /* loaded from: classes3.dex */
    public static class StatEvent {
        public List<StatValue> data;
        public String event;
        transient String eventId;
        String uuidHash;

        public StatEvent() {
        }

        public StatEvent(String str) {
            this.event = str;
            this.eventId = String.format("%s_%s_%s", Long.valueOf(System.currentTimeMillis()), str, Double.valueOf(Math.random()));
            this.data = new ArrayList();
        }

        String buildUUIDHash() {
            return MD5.MD5_32(String.format(Locale.CHINA, "%s,%s,%d", this.event, TokenManager.getInstance().getUserId(), Long.valueOf(System.currentTimeMillis())));
        }

        void compressData() {
            HashMap hashMap = new HashMap();
            for (int i = 0; i < this.data.size(); i++) {
                StatValue statValue = this.data.get(i);
                StatValue statValue2 = (StatValue) hashMap.get(statValue.key());
                if (statValue2 == null) {
                    hashMap.put(statValue.key(), statValue);
                } else {
                    statValue2.c = String.valueOf(Integer.parseInt(statValue2.c) + 1);
                }
            }
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : hashMap.entrySet()) {
                arrayList.add(entry.getValue());
            }
            this.data.clear();
            this.data.addAll(arrayList);
        }

        @SuppressLint({"CheckResult"})
        public void save() {
            save(this).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.io()).subscribe($$Lambda$StatModel$StatEvent$Zyp420bpGnNT3MEBtreaEStHVns.INSTANCE, new Consumer() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$StatEvent$4p_1gk2wZgrY5DQMRQCwsJmJlk4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    StatModel.StatEvent.lambda$save$1(StatModel.StatEvent.this, (Throwable) obj);
                }
            });
        }

        public static /* synthetic */ void lambda$save$1(StatEvent statEvent, Throwable th) throws Exception {
            L.base.e("save event error", th);
            statEvent.postPoint(MicoApplication.getGlobalContext());
        }

        @SuppressLint({"CheckResult"})
        public void postPoint(Context context) {
            StatModel statModel = new StatModel();
            statModel.serialNumber = Constants.getSn();
            statModel.hardware = Hardware.current(context).getName();
            statModel.source = "rom";
            statModel.deviceId = SystemSetting.getDeviceID();
            statModel.version = SystemSetting.getRomVersion() + "|" + SystemSetting.getRomChannel();
            statModel.list = new ArrayList();
            statModel.list.add(this);
        }

        public static Observable<MicoTrackEvent> save(StatEvent statEvent) {
            return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.common.stat.-$$Lambda$StatModel$StatEvent$5pIemEr4nXARFiFP3isAgDhhWSU
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    StatModel.StatEvent.lambda$save$2(StatModel.StatEvent.this, observableEmitter);
                }
            });
        }

        public static /* synthetic */ void lambda$save$2(StatEvent statEvent, ObservableEmitter observableEmitter) throws Exception {
            Realm instance = Realm.getInstance(StatModel.getRealmConfiguration());
            if (instance != null) {
                try {
                    instance.beginTransaction();
                    MicoTrackEvent micoTrackEvent = (MicoTrackEvent) instance.copyToRealm((Realm) new MicoTrackEvent(statEvent), new ImportFlag[0]);
                    instance.commitTransaction();
                    if (micoTrackEvent != null) {
                        observableEmitter.onNext(micoTrackEvent);
                        observableEmitter.onComplete();
                        return;
                    }
                    observableEmitter.onError(new RealmException("realmStatEvent object is null"));
                } finally {
                    instance.close();
                }
            } else {
                observableEmitter.onError(new RealmException("realm object is null"));
            }
        }

        public static void recordEventMultiDimensions(String str, String... strArr) {
            if (strArr == null || strArr.length % 2 != 0) {
                Logger logger = L.base;
                logger.w("args format error, key:" + str);
                return;
            }
            StatEvent statEvent = new StatEvent(str);
            for (int i = 0; i < strArr.length; i += 2) {
                StatValue statValue = new StatValue();
                statValue.t = strArr[i];
                statValue.v = strArr[i + 1];
                statValue.c = "1";
                statEvent.data.add(statValue);
            }
            statEvent.save();
        }

        static void recordEvent(String str, String str2) {
            StatEvent statEvent = new StatEvent(str);
            StatValue statValue = new StatValue();
            statValue.t = str;
            statValue.v = str2;
            statValue.c = "1";
            statEvent.data.add(statValue);
            statEvent.save();
        }
    }
}
