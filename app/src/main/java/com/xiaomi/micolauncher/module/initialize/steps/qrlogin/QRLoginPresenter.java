package com.xiaomi.micolauncher.module.initialize.steps.qrlogin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.elvishew.xlog.Logger;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.AccountInfo;
import com.xiaomi.accountsdk.account.exception.IllegalDeviceException;
import com.xiaomi.accountsdk.account.exception.InvalidCredentialException;
import com.xiaomi.accountsdk.account.exception.InvalidUserNameException;
import com.xiaomi.accountsdk.account.exception.NeedNotificationException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.mico.base.utils.Base64Coder;
import com.xiaomi.mico.base.utils.QRCodeUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiConstants;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.ThirdPartyResponse;
import com.xiaomi.micolauncher.application.LoginModel;
import com.xiaomi.micolauncher.common.AbstractHttpCallback;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.MicoHandler;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.initialize.steps.qrlogin.QRLoginPresenter;
import com.xiaomi.micolauncher.module.main.util.RandomTimeUtils;
import com.xiaomi.passport.utils.AccountHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class QRLoginPresenter extends AbstractHttpCallback {
    public static final String TAG = "QRLoginPresenter";
    static final /* synthetic */ boolean a = !QRLoginPresenter.class.desiredAssertionStatus();
    private static final long b = TimeUnit.SECONDS.toMillis(2);
    private static final long c = TimeUnit.MINUTES.toSeconds(5);
    private static final long d = TimeUnit.SECONDS.toMillis(c);
    private static final long e = TimeUnit.MINUTES.toMillis(1);
    private final IQRLoginView f;
    private final OkHttpClient g;
    private final Handler h;
    private final Context i;
    private final boolean j;
    private boolean k;
    private Call l;
    private Disposable m;
    private int n;
    private long o;
    private int p;

    public QRLoginPresenter(Context context, IQRLoginView iQRLoginView) {
        this(context, iQRLoginView, LoginModel.shouldMiHomeRedirectLongPollUrl());
    }

    public QRLoginPresenter(Context context, IQRLoginView iQRLoginView, boolean z) {
        this.f = iQRLoginView;
        this.i = context;
        this.j = z;
        this.g = ApiManager.createOkHttpClientBuilderWithNetworkInterceptor().connectTimeout(10L, TimeUnit.SECONDS).readTimeout(c, TimeUnit.SECONDS).build();
        this.h = new MicoHandler(Looper.getMainLooper(), TAG, new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$OAcj4Zd3FMY0i1ZGLtVuoKEyWGU
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean b2;
                b2 = QRLoginPresenter.this.b(message);
                return b2;
            }
        }) { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.QRLoginPresenter.1
            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public String getLogTag() {
                return QRLoginPresenter.TAG;
            }

            @Override // com.xiaomi.micolauncher.common.MicoHandler
            public void processMessage(Message message) {
                QRLoginPresenter.this.a(message);
            }
        };
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(Message message) {
        a(message);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        if (message.what == 1) {
            this.h.removeMessages(1);
            b();
            return;
        }
        L.login.e("unexpected code %s", Integer.valueOf(message.what));
    }

    private void b() {
        if (this.k) {
            L.init.i("is loading QR, ignore this load");
            return;
        }
        this.k = true;
        this.f.showProgressbar();
        L.init.i("qrloginpresenter : get login url callback is %s", "");
        this.m = ApiManager.rawService.getLoginUrl(ApiConstants.MICO_SID, "").map($$Lambda$QRLoginPresenter$EqxhLcwpkRzXw44LchcrQCwD_3Y.INSTANCE).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$H1hSdG1p5ZlAaCYOLPQD7j4xJmM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                QRLoginPresenter.this.a((ThirdPartyResponse.QrLoginResponse) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$1Hk_e5w3vrCT1xdY7qc0MiqRIX8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                QRLoginPresenter.this.a((Throwable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ThirdPartyResponse.QrLoginResponse a(ResponseBody responseBody) throws Exception {
        return (ThirdPartyResponse.QrLoginResponse) Gsons.getGson().fromJson(responseBody.string().replace(XMPassport.PASSPORT_SAFE_PREFIX, ""), (Class<Object>) ThirdPartyResponse.QrLoginResponse.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ThirdPartyResponse.QrLoginResponse qrLoginResponse) throws Exception {
        this.k = false;
        EventBusRegistry.getEventBus().post(new LoginResponseUpdateEvent(qrLoginResponse));
        a(qrLoginResponse.loginUrl, qrLoginResponse.qr);
        Call call = this.l;
        if (call != null) {
            call.cancel();
        }
        this.l = this.g.newCall(new Request.Builder().url(qrLoginResponse.lp).build());
        this.l.enqueue(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Throwable th) throws Exception {
        L.init.e("QRCode load qr failed", th);
        onLoadFailed();
    }

    public void onLoadFailed() {
        ToastUtil.showToast((int) R.string.init_step_reload_qr_code);
        this.k = false;
        this.n++;
        if (this.n <= 5) {
            a(b);
        } else {
            this.f.onTimeout();
        }
    }

    private void a(String str, String str2) {
        if (this.j) {
            a(str);
        } else {
            b(str2);
        }
    }

    String a(Context context, String str) {
        String format = String.format("https://home.mi.com/do/home.html?a=connect&did=%s&pid=%s&url=%s", SystemSetting.getMiotDeviceId(), Short.valueOf(Hardware.current(context).getMiotProductId()), Base64Coder.encode(str.getBytes()));
        L.init.i("InitByMiHomeAppProcessor wrapped loginUrl is %s", format);
        return format;
    }

    private void a(final String str) {
        Observable.fromCallable(new Callable() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$oYdqLecoYgWkI_qmLHomxBLVixA
            @Override // java.util.concurrent.Callable
            public final Object call() {
                Bitmap c2;
                c2 = QRLoginPresenter.this.c(str);
                return c2;
            }
        }).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Observer<Bitmap>() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.QRLoginPresenter.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            /* renamed from: a */
            public void onNext(Bitmap bitmap) {
                QRLoginPresenter.this.a(bitmap);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                QRLoginPresenter.this.d();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Bitmap c(String str) throws Exception {
        int dimensionPixelSize = this.i.getResources().getDimensionPixelSize(R.dimen.big_login_qr_size);
        return QRCodeUtil.createQRCodeBitmap(a(this.i, str), dimensionPixelSize, dimensionPixelSize);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.QRLoginPresenter$3  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass3 extends SimpleTarget<Bitmap> {
        AnonymousClass3() {
        }

        /* renamed from: a */
        public void onResourceReady(@NonNull final Bitmap bitmap, Transition<? super Bitmap> transition) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$3$TTTfKY1-NgF4HW7P83Jszw-2T1o
                @Override // java.lang.Runnable
                public final void run() {
                    QRLoginPresenter.AnonymousClass3.this.a(bitmap);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(@NonNull Bitmap bitmap) {
            QRLoginPresenter.this.f.hideProgressbar();
            QRLoginPresenter.this.f.showQr(bitmap);
            QRLoginPresenter.this.a(QRLoginPresenter.d);
        }

        @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
        public void onLoadFailed(Drawable drawable) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$3$U_Tfnz_zWebXF5gHRXiJFORJQIA
                @Override // java.lang.Runnable
                public final void run() {
                    QRLoginPresenter.AnonymousClass3.this.a();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a() {
            QRLoginPresenter.this.f.hideProgressbar();
            ToastUtil.showToast((int) R.string.init_step_wifi_load_fail);
            L.base.i("load qr failed");
            QRLoginPresenter.this.a(QRLoginPresenter.this.c());
        }
    }

    private void b(String str) {
        L.base.i("load QR image %s", str);
        Glide.with(this.i).asBitmap().load(str).into((RequestBuilder<Bitmap>) new AnonymousClass3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long c() {
        int i = this.p + 1;
        this.p = i;
        long exponentRandomTimeInMills = RandomTimeUtils.getExponentRandomTimeInMills(i, b);
        if (exponentRandomTimeInMills <= e) {
            return exponentRandomTimeInMills;
        }
        long j = b;
        this.p = 0;
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        this.f.hideProgressbar();
        ToastUtil.showToast((int) R.string.init_step_wifi_load_fail);
        L.base.i("load qr failed");
        a(c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@NonNull Bitmap bitmap) {
        this.f.hideProgressbar();
        this.f.showQr(bitmap);
    }

    @Override // com.xiaomi.micolauncher.common.AbstractHttpCallback, okhttp3.Callback
    public void onFailure(@NotNull Call call, @NotNull IOException iOException) {
        L.init.e("longPolling onFailure", iOException);
        if ((iOException instanceof SocketTimeoutException) || (iOException instanceof SSLException)) {
            a(c());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j) {
        if (j == 0 || (e() + j) - this.o >= j) {
            this.h.removeMessages(1);
            this.h.sendEmptyMessageDelayed(1, j);
            this.o = e() + j;
            L.init.d("will retry in %s millis", Long.valueOf(j));
            return;
        }
        L.init.d("won't retry for just sent");
    }

    private long e() {
        return System.currentTimeMillis();
    }

    @Override // com.xiaomi.micolauncher.common.AbstractHttpCallback
    public void processResponseOnce(@NotNull Call call, @NotNull Response response) throws IOException {
        if (!response.isSuccessful()) {
            onLoadFailed();
        } else if (a || response.body() != null) {
            String replace = response.body().string().replace(XMPassport.PASSPORT_SAFE_PREFIX, "");
            L.init.d("QRLoginPresenter Response is %s", replace);
            this.f.onAuthSuccess((ThirdPartyResponse.QrLoginResultResponse) Gsons.getGson().fromJson(replace, (Class<Object>) ThirdPartyResponse.QrLoginResultResponse.class));
        } else {
            throw new AssertionError();
        }
    }

    public void loadQr() {
        a(0L);
    }

    public void destroy() {
        Disposable disposable = this.m;
        if (disposable != null && !disposable.isDisposed()) {
            this.m.dispose();
            this.m = null;
        }
        this.h.removeCallbacksAndMessages(null);
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }

    public static Observable<AccountInfo> getServiceToken(ThirdPartyResponse.QrLoginResultResponse qrLoginResultResponse) {
        return getServiceToken(qrLoginResultResponse, ApiConstants.MICO_SID);
    }

    public static Observable<AccountInfo> getServiceToken(final ThirdPartyResponse.QrLoginResultResponse qrLoginResultResponse, final String str) {
        return Observable.unsafeCreate(new ObservableSource() { // from class: com.xiaomi.micolauncher.module.initialize.steps.qrlogin.-$$Lambda$QRLoginPresenter$Y48TSzl2gkgMS3ERbkztLA02KaI
            @Override // io.reactivex.ObservableSource
            public final void subscribe(Observer observer) {
                QRLoginPresenter.a(ThirdPartyResponse.QrLoginResultResponse.this, str, observer);
            }
        }).subscribeOn(MicoSchedulers.io());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(ThirdPartyResponse.QrLoginResultResponse qrLoginResultResponse, String str, Observer observer) {
        AccountInfo accountInfo;
        try {
            accountInfo = AccountHelper.getServiceTokenByPassToken(qrLoginResultResponse.userId, qrLoginResultResponse.passToken, str);
        } catch (IllegalDeviceException | InvalidCredentialException | InvalidUserNameException | NeedNotificationException | AccessDeniedException | AuthenticationFailureException | InvalidResponseException | IOException e2) {
            Logger logger = L.login;
            logger.e("getServiceTokenByPassToken " + qrLoginResultResponse, e2);
            observer.onError(e2);
            accountInfo = null;
        }
        observer.onNext(accountInfo);
        observer.onComplete();
    }
}
