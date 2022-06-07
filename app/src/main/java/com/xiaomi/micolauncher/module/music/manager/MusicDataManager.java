package com.xiaomi.micolauncher.module.music.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.homepage.HomePageUtils;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MusicDataManager {
    private static final long a = TimeUnit.HOURS.toMillis(3);
    private PatchWall b;
    private String c;
    private long d;
    private boolean e;

    /* loaded from: classes3.dex */
    public static class QQExpiredException extends Exception {
    }

    /* loaded from: classes3.dex */
    public static class QQNotBindedException extends Exception {
    }

    /* loaded from: classes3.dex */
    public static class a {
        private static MusicDataManager a = new MusicDataManager();
    }

    public static MusicDataManager getManager() {
        return a.a;
    }

    private MusicDataManager() {
        this.e = ChildModeManager.getManager().isChildMode();
    }

    public void clearCache() {
        this.b = null;
        this.c = null;
        this.e = ChildModeManager.getManager().isChildMode();
    }

    public Observable<PatchWall> loadMusicDataWithCache(final int i) {
        return loadMusicSource().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$NIzQ71qrTqfrkNt3hmKDmQp8Czo
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = MusicDataManager.this.a((String) obj);
                return a2;
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$Bj0eDpuidS2K2Gpm5WmkANV2R1A
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = MusicDataManager.this.a((MIBrain.CPBindStatus) obj);
                return a2;
            }
        }).flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$y2X-p4D96A9TOuSIU7JaPH9uhws
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource b;
                b = MusicDataManager.this.b(i, (String) obj);
                return b;
            }
        });
    }

    public /* synthetic */ ObservableSource a(MIBrain.CPBindStatus cPBindStatus) throws Exception {
        if (cPBindStatus.isExpired()) {
            throw new QQExpiredException();
        } else if (!cPBindStatus.isNotBinded()) {
            return loadMusicSource();
        } else {
            throw new QQNotBindedException();
        }
    }

    public /* synthetic */ ObservableSource b(int i, String str) throws Exception {
        return a(i, str, true);
    }

    public Observable<PatchWall> loadMusicDataWithCacheFilter(int i, final Context context) {
        return loadMusicDataWithCache(i).filter(new Predicate() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$AGLZMoAqR11Goa4k1iIszDJi8qs
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                boolean a2;
                a2 = MusicDataManager.a(context, (PatchWall) obj);
                return a2;
            }
        });
    }

    public static /* synthetic */ boolean a(Context context, PatchWall patchWall) throws Exception {
        if (!Hardware.isBigScreen()) {
            return true;
        }
        PatchWall.Item item = null;
        for (PatchWall.Item item2 : patchWall.blocks.get(0).items) {
            if (context.getString(R.string.recommend_today_music).equals(item2.title)) {
                item = item2;
            }
        }
        if (item == null) {
            return true;
        }
        patchWall.blocks.get(0).items.remove(item);
        return true;
    }

    public Observable<MIBrain.CPBindStatus> checkAuthStatus() {
        return loadMusicSource().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$Oibj9CXSicubBxjcpB42soayhfw
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return MusicDataManager.this.a((String) obj);
            }
        });
    }

    public Observable<PatchWall> loadMusicDataWithoutCacheAndAuthCheck(final int i) {
        return loadMusicSource().flatMap(new Function() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$2aklvtkEEdc4wFgJuMT-IHck05o
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a2;
                a2 = MusicDataManager.this.a(i, (String) obj);
                return a2;
            }
        });
    }

    public /* synthetic */ ObservableSource a(int i, String str) throws Exception {
        return a(i, str, false);
    }

    private Observable<PatchWall> a(int i, String str, boolean z) throws Exception {
        String str2 = this.c;
        if (str2 == null || !str2.equals(str)) {
            return a(i, str, true, z);
        }
        return a(i, str, false, z);
    }

    private Observable<PatchWall> a(int i, String str, boolean z, boolean z2) throws Exception {
        this.c = str;
        if (str.equalsIgnoreCase("QQ")) {
            return b(i, z, z2);
        }
        if (str.equalsIgnoreCase(HomePageUtils.MUSIC_SOURCE_MI)) {
            return a(i, z, z2);
        }
        throw new Exception("unsupported source");
    }

    @SuppressLint({"CheckResult"})
    public Observable<String> loadMusicSource() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$4A7VM58GvtNA6qHFtqT7XbZtHkQ
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                MusicDataManager.this.a(observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(final ObservableEmitter observableEmitter) throws Exception {
        if (!TextUtils.isEmpty(this.c)) {
            observableEmitter.onNext(this.c);
            observableEmitter.onComplete();
        } else if (ApiManager.userProfileService != null) {
            ApiManager.userProfileService.getMusicSource().subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$N63CmunEQ1SDk2MY6ZiUwA3Z99o
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.this.a(observableEmitter, (String) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$lDYp_TNFSlklitMGG0pCgg14PX0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.d(ObservableEmitter.this, (Throwable) obj);
                }
            });
        } else {
            L.base.w("ApiManager is not inited!");
        }
    }

    public /* synthetic */ void a(ObservableEmitter observableEmitter, String str) throws Exception {
        HomePageUtils.setMusicSource(MicoApplication.getGlobalContext(), str);
        this.c = str;
        observableEmitter.onNext(this.c);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void d(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    /* renamed from: getAuthStatus */
    public Observable<MIBrain.CPBindStatus> a(final String str) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$7UKUu2e0tvAFW-HCFHyA1ebIk8c
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                MusicDataManager.a(str, observableEmitter);
            }
        });
    }

    public static /* synthetic */ void a(String str, final ObservableEmitter observableEmitter) throws Exception {
        if (SystemSetting.MusicSuorce.QQ.getSource().equalsIgnoreCase(str)) {
            ApiManager.minaService.qqMusicAuthStatus("").subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$PP8KWCY3mH9OH51bHJi2owtN6uM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.a(ObservableEmitter.this, (MIBrain.CPBindStatus) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$sUTNaaPreN2sP-3G10OsLgO6jfk
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.c(ObservableEmitter.this, (Throwable) obj);
                }
            });
            return;
        }
        observableEmitter.onNext(new MIBrain.CPBindStatus());
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter, MIBrain.CPBindStatus cPBindStatus) throws Exception {
        observableEmitter.onNext(cPBindStatus);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void c(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    private Observable<PatchWall> a(final int i, final boolean z, final boolean z2) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$rj2quaas945a1Y4WIo8-V3Gb8jM
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                MusicDataManager.this.b(z, z2, i, observableEmitter);
            }
        });
    }

    public /* synthetic */ void b(boolean z, final boolean z2, int i, final ObservableEmitter observableEmitter) throws Exception {
        PatchWall patchWall;
        if (z || !z2 || (patchWall = this.b) == null || patchWall.blocks == null || !a()) {
            ApiManager.displayService.getMiPathWallFlow(i, 1).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$fxUnDXWF1Bkiglws4tsPhuEQ52o
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.this.b(z2, observableEmitter, (PatchWall) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$5JM_4GKFDFbOc6-SqdKv-IenBx4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.b(ObservableEmitter.this, (Throwable) obj);
                }
            });
            return;
        }
        observableEmitter.onNext(this.b);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void b(boolean z, ObservableEmitter observableEmitter, PatchWall patchWall) throws Exception {
        if (z) {
            this.b = patchWall;
            this.d = System.currentTimeMillis();
        }
        observableEmitter.onNext(patchWall);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void b(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    private Observable<PatchWall> b(final int i, final boolean z, final boolean z2) {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$N7Nu9J565Wx3rba-DHueWs9xDKo
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                MusicDataManager.this.a(z, z2, i, observableEmitter);
            }
        });
    }

    public /* synthetic */ void a(boolean z, final boolean z2, int i, final ObservableEmitter observableEmitter) throws Exception {
        PatchWall patchWall;
        if (z || !z2 || (patchWall = this.b) == null || patchWall.blocks == null || !a()) {
            HashMap hashMap = new HashMap();
            hashMap.put("pageNum", Integer.toString(i));
            hashMap.put("hardware", Hardware.HARDWARE.getName());
            ApiManager.displayService.getQQPathWallFlow(hashMap).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$90qUPb0JKSu9ej6sbEA1ctjTJ_Y
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.this.a(z2, observableEmitter, (PatchWall) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.module.music.manager.-$$Lambda$MusicDataManager$ozQrs6I8-moX-ISk7rbMXebYlEM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicDataManager.a(ObservableEmitter.this, (Throwable) obj);
                }
            });
            return;
        }
        observableEmitter.onNext(this.b);
        observableEmitter.onComplete();
    }

    public /* synthetic */ void a(boolean z, ObservableEmitter observableEmitter, PatchWall patchWall) throws Exception {
        if (z) {
            this.b = patchWall;
            this.d = System.currentTimeMillis();
        }
        observableEmitter.onNext(patchWall);
        observableEmitter.onComplete();
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter, Throwable th) throws Exception {
        observableEmitter.onError(th);
        observableEmitter.onComplete();
    }

    private boolean a() {
        return Math.abs(System.currentTimeMillis() - this.d) <= a;
    }

    public boolean isQQMusicSource() {
        String str = this.c;
        return str != null && str.equalsIgnoreCase("QQ");
    }
}
