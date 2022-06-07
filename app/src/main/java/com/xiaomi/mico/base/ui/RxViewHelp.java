package com.xiaomi.mico.base.ui;

import android.view.View;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class RxViewHelp {
    public static final long CLICK_DURATION = 1800;
    public static final long CLICK_DURATION_NORMAL = 500;
    public static final long CLICK_DURATION_ONE_SECONDS = 1000;

    public static Observable<Object> debounceClicks(View view) {
        return RxView.clicks(view).throttleFirst(CLICK_DURATION, TimeUnit.MILLISECONDS);
    }

    public static Observable<Object> debounceClicks(View view, long j) {
        return RxView.clicks(view).throttleFirst(j, TimeUnit.MILLISECONDS);
    }

    public static Observable<Object> debounceClicksWithOneSeconds(View view) {
        return RxView.clicks(view).throttleFirst(1000L, TimeUnit.MILLISECONDS);
    }
}
