package com.xiaomi.smarthome.core.net;

import android.annotation.SuppressLint;
import android.util.Log;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.base.BaseResponse;
import com.xiaomi.smarthome.base.Optional;
import com.xiaomi.smarthome.core.net.NetworkObserver;
import com.xiaomi.smarthome.core.utils.UiUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

/* loaded from: classes4.dex */
public class NetworkObserver {
    public static final int ERROR_DEVICE_UNBOUND = 109;
    public static final int ERROR_ST_ERROR = 111;
    public static final int ERROR_TOKEN_TV_EXPIRE = 103;
    private static final String a = "NetworkObserver";

    public static <T> Observable<Optional<T>> on(Observable<BaseResponse<T>> observable) {
        return (Observable<Optional<T>>) observable.subscribeOn(Schedulers.io()).flatMap(new a());
    }

    public static <T> Observable<Optional<T>> onService(Observable<BaseResponse<T>> observable) {
        return (Observable<Optional<T>>) observable.subscribeOn(Schedulers.io()).flatMap(new b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"CheckResult"})
    /* loaded from: classes4.dex */
    public static class b<T> implements Function<BaseResponse<T>, Observable<Optional<T>>> {
        private b() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Integer num) throws Throwable {
            Log.e(NetworkObserver.a, "response=null ");
            UiUtils.showShortToast("连接失败");
        }

        /* renamed from: a */
        public Observable<Optional<T>> apply(final BaseResponse<T> baseResponse) {
            if (baseResponse == null) {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe($$Lambda$NetworkObserver$b$BSErxap2U8xTlckLZ2YGhAjjoI.INSTANCE);
                throw new RuntimeException("连接失败");
            } else if (baseResponse.getCode() == 200) {
                return Observable.just(Optional.ofNullable(baseResponse.getData()));
            } else {
                if (111 == baseResponse.getCode()) {
                    Logger logger = L.api;
                    logger.e(NetworkObserver.a + Constants.COLON_SEPARATOR + 111);
                } else {
                    Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.core.net.-$$Lambda$NetworkObserver$b$NcGUJkRRrFYzQj2qPawHgpbQidY
                        @Override // io.reactivex.rxjava3.functions.Consumer
                        public final void accept(Object obj) {
                            NetworkObserver.b.a(BaseResponse.this, (Integer) obj);
                        }
                    });
                }
                throw new RuntimeException(baseResponse.getMsg() + "");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(BaseResponse baseResponse, Integer num) throws Throwable {
            String str = NetworkObserver.a;
            Log.e(str, "response=null ,code is " + baseResponse.getCode());
            UiUtils.showShortToast(baseResponse.getMsg());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"CheckResult"})
    /* loaded from: classes4.dex */
    public static class a<T> implements Function<BaseResponse<T>, Observable<Optional<T>>> {
        private a() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Integer num) throws Throwable {
            Logger logger = L.api;
            logger.e(NetworkObserver.a + " response=null ");
            UiUtils.showShortToast("连接失败");
        }

        /* renamed from: a */
        public Observable<Optional<T>> apply(BaseResponse<T> baseResponse) {
            if (baseResponse != null) {
                int code = baseResponse.getCode();
                if (code == 200) {
                    return Observable.just(Optional.ofNullable(baseResponse.getData()));
                }
                if (111 == baseResponse.getCode()) {
                    Logger logger = L.api;
                    logger.e(NetworkObserver.a + "ERROR_ST_ERROR ,code is " + baseResponse.getCode());
                } else {
                    Logger logger2 = L.api;
                    logger2.e(NetworkObserver.a + String.format("error_code is %s,error_msg is %s", Integer.valueOf(code), baseResponse.getMsg()));
                }
                throw new RuntimeException(baseResponse.getMsg() + "");
            }
            Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe($$Lambda$NetworkObserver$a$TRa5CVoMEKIkZd29EFwYWW2BZV8.INSTANCE);
            throw new RuntimeException("连接失败");
        }
    }
}
