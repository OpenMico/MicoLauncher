package com.xiaomi.micolauncher.skills.music;

import android.content.Intent;
import com.xiaomi.mico.base.utils.ScheduleRefreshManager;
import com.xiaomi.micolauncher.api.ApiError;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.DefaultObserver;
import com.xiaomi.micolauncher.api.model.MIBrain;
import com.xiaomi.micolauncher.api.model.ThirdParty;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.application.ScheduleKey;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.skills.music.MusicProfile;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.view.QQMusicAuthInvalidActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.Map;

/* loaded from: classes3.dex */
public class MusicProfile {
    private static volatile MusicProfile a;
    private long b;
    private final long c = 60000;
    private MIBrain.CPBindStatus d;

    /* loaded from: classes3.dex */
    public static class NotQQSourceException extends Exception {
    }

    /* loaded from: classes3.dex */
    public interface QQMusicAuthStatusListener {
        void onFail(Throwable th);

        void onLoadQQMusicAuthStatus(MIBrain.CPBindStatus cPBindStatus);

        void onNonQQMusicSource(String str);
    }

    public static MusicProfile shareInstance() {
        if (a == null) {
            synchronized (MusicProfile.class) {
                if (a == null) {
                    a = new MusicProfile();
                }
            }
        }
        return a;
    }

    public MIBrain.CPBindStatus getQQMusicAuthStatus() {
        return this.d;
    }

    public void setQQMusicAuthStatus(MIBrain.CPBindStatus cPBindStatus) {
        this.d = cPBindStatus;
    }

    private boolean a() {
        return System.currentTimeMillis() - this.b > 60000;
    }

    public Observable<MIBrain.CPBindStatus> getQQMusicAuthInfo() {
        if (this.d == null || !a()) {
            return ApiManager.minaService.qqMusicAuthStatus("").flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.music.-$$Lambda$MusicProfile$Sf7PoD9UqMaQePztCuM_ZeumlCs
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    ObservableSource a2;
                    a2 = MusicProfile.this.a((MIBrain.CPBindStatus) obj);
                    return a2;
                }
            });
        }
        return Observable.just(this.d);
    }

    public /* synthetic */ ObservableSource a(MIBrain.CPBindStatus cPBindStatus) throws Exception {
        this.b = System.currentTimeMillis();
        this.d = cPBindStatus;
        return Observable.just(cPBindStatus);
    }

    public void checkAuthStateAndShowView() {
        ApiManager.userProfileService.getMusicSource().map($$Lambda$MusicProfile$_rAlJmxBZmJ8cfP2rkYSjOisCIQ.INSTANCE).flatMap($$Lambda$MusicProfile$qtJs9ZfOp25Gl5Yp9s6Xy4__LQ.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<MIBrain.CPBindStatus>() { // from class: com.xiaomi.micolauncher.skills.music.MusicProfile.1
            /* renamed from: a */
            public void onSuccess(MIBrain.CPBindStatus cPBindStatus) {
                MusicProfile.this.setQQMusicAuthStatus(cPBindStatus);
                if ((cPBindStatus.isExpired() || cPBindStatus.isNotBinded()) && MicoApplication.getGlobalContext() != null) {
                    Intent intent = new Intent(MicoApplication.getGlobalContext(), QQMusicAuthInvalidActivity.class);
                    intent.addFlags(536870912);
                    MicoApplication.getGlobalContext().startActivity(intent);
                }
            }

            @Override // com.xiaomi.micolauncher.api.DefaultObserver
            public void onFail(ApiError apiError) {
                super.onFail(apiError);
            }
        });
    }

    public static /* synthetic */ Boolean a(String str) throws Exception {
        return Boolean.valueOf("QQ".equalsIgnoreCase(str));
    }

    public static /* synthetic */ ObservableSource b(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            return ApiManager.minaService.qqMusicAuthStatus("");
        }
        return Observable.error(new Throwable("music source is not qq"));
    }

    public static void loadCpConfig() {
        if (ScheduleRefreshManager.getInstance().shouldRefresh(ScheduleKey.MUSIC_CP_INFO) && ApiManager.minaService != null) {
            ApiManager.minaService.getOriginNameMapV2().subscribe(new DefaultObserver<Map<String, ThirdParty.CpInfo>>() { // from class: com.xiaomi.micolauncher.skills.music.MusicProfile.2
                /* renamed from: a */
                public void onSuccess(Map<String, ThirdParty.CpInfo> map) {
                    MusicHelper.CP_INFO_MAP.putAll(map);
                    ScheduleRefreshManager.getInstance().setRefreshed(ScheduleKey.MUSIC_CP_INFO);
                }
            });
            ApiManager.minaService.getOriginNameMap().subscribe(new DefaultObserver<Map<String, String>>() { // from class: com.xiaomi.micolauncher.skills.music.MusicProfile.3
                /* renamed from: a */
                public void onSuccess(Map<String, String> map) {
                    MusicHelper.CP_TO_NAME_SERVER.putAll(map);
                    ScheduleRefreshManager.getInstance().setRefreshed(ScheduleKey.MUSIC_CP_INFO);
                }
            });
        }
    }

    public static void checkQQMusicAuthStatus(final QQMusicAuthStatusListener qQMusicAuthStatusListener) {
        ApiManager.userProfileService.getMusicSource().subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.-$$Lambda$MusicProfile$VEbrhhVYtYYGZkyUiZS0T9TaR9k
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicProfile.a(MusicProfile.QQMusicAuthStatusListener.this, (String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.-$$Lambda$MusicProfile$9mRbMUsYgWgMzAnN3OnK59YHjdE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicProfile.a(MusicProfile.QQMusicAuthStatusListener.this, (Throwable) obj);
            }
        });
    }

    public static /* synthetic */ void a(final QQMusicAuthStatusListener qQMusicAuthStatusListener, String str) throws Exception {
        if ("QQ".equalsIgnoreCase(str)) {
            ApiManager.minaService.qqMusicAuthStatus("").subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.-$$Lambda$MusicProfile$3uADbQ_pmogov1ZpUgccoTeWQ0c
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicProfile.a(MusicProfile.QQMusicAuthStatusListener.this, (MIBrain.CPBindStatus) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.-$$Lambda$MusicProfile$xw2LQKtS0iSTv67ZASkVAUnS1uk
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicProfile.b(MusicProfile.QQMusicAuthStatusListener.this, (Throwable) obj);
                }
            });
        } else if (qQMusicAuthStatusListener != null) {
            qQMusicAuthStatusListener.onNonQQMusicSource(str);
        }
    }

    public static /* synthetic */ void a(QQMusicAuthStatusListener qQMusicAuthStatusListener, MIBrain.CPBindStatus cPBindStatus) throws Exception {
        if (qQMusicAuthStatusListener != null) {
            qQMusicAuthStatusListener.onLoadQQMusicAuthStatus(cPBindStatus);
        }
    }

    public static /* synthetic */ void b(QQMusicAuthStatusListener qQMusicAuthStatusListener, Throwable th) throws Exception {
        if (qQMusicAuthStatusListener != null) {
            qQMusicAuthStatusListener.onFail(th);
        }
    }

    public static /* synthetic */ void a(QQMusicAuthStatusListener qQMusicAuthStatusListener, Throwable th) throws Exception {
        if (qQMusicAuthStatusListener != null) {
            qQMusicAuthStatusListener.onFail(th);
        }
    }

    public static Observable<MIBrain.CPBindStatus> checkQQMusicAuthStatus() {
        return ApiManager.userProfileService.getMusicSource().map(new Function() { // from class: com.xiaomi.micolauncher.skills.music.-$$Lambda$rflHUcD9W-78Bj-7i3n7SBywR34
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(r1.equalsIgnoreCase((String) obj));
            }
        }).flatMap($$Lambda$MusicProfile$y3JfcJOd052KjwB4o_4pfskfjdQ.INSTANCE).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static /* synthetic */ ObservableSource a(Boolean bool) throws Exception {
        if (bool.booleanValue()) {
            return ApiManager.minaService.qqMusicAuthStatus("");
        }
        return Observable.error(new NotQQSourceException());
    }
}
