package com.xiaomi.micolauncher.api;

import android.net.ParseException;
import android.util.ExceptionUtils;
import com.google.gson.JsonParseException;
import com.xiaomi.micolauncher.common.L;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import org.json.JSONException;
import retrofit2.HttpException;

/* loaded from: classes3.dex */
public abstract class DefaultObserver<T> implements Observer<T>, Disposable {
    private Disposable mDisposable;

    @Override // io.reactivex.Observer
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        this.mDisposable = disposable;
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        Disposable disposable = this.mDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDisposable.dispose();
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        Disposable disposable = this.mDisposable;
        if (disposable == null) {
            return true;
        }
        return disposable.isDisposed();
    }

    @Override // io.reactivex.Observer
    public void onNext(T t) {
        onSuccess(t);
        onComplete();
    }

    public void onFail(ApiError apiError) {
        L.base.e("Api Failed %s", apiError.toString());
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        if (th instanceof ApiError) {
            onFail((ApiError) th);
        } else if (th instanceof HttpException) {
            onFail(ErrorCode.HTTP_ERROR.toApiError(((HttpException) th).message()));
        } else if ((th instanceof ConnectException) || (th instanceof UnknownHostException)) {
            onFail(ErrorCode.CONNECT_ERROR.toApiError(th.getMessage()));
        } else if (th instanceof InterruptedIOException) {
            onFail(ErrorCode.CONNECT_TIMEOUT.toApiError(th.getMessage()));
        } else if ((th instanceof JsonParseException) || (th instanceof JSONException) || (th instanceof ParseException)) {
            onFail(ErrorCode.PARSE_ERROR.toApiError(th.getMessage()));
        } else {
            onFail(new ApiError(ErrorCode.UNKNOWN_ERROR.getCode(), ExceptionUtils.getCompleteMessage(th)));
        }
    }
}
