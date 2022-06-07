package com.xiaomi.micolauncher.skills.video.player.widget;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PlayerLoadingView extends RelativeLayout {
    private final Context a;
    private ImageView b;
    private TextView c;
    private TextView d;
    private LinearLayout e;
    private TextView f;
    private Disposable g;
    private ConnectivityManager h;
    private long i;
    private long j;
    private int k;
    private int l;
    private int m;
    private Animation n;
    private boolean o;
    private int p;

    public PlayerLoadingView(Context context) {
        this(context, null);
    }

    public PlayerLoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PlayerLoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.p = 1013;
        this.a = context;
        a();
    }

    private void a() {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(R.layout.player_loading_view, this);
        this.b = (ImageView) findViewById(R.id.loading_image);
        this.c = (TextView) findViewById(R.id.loading_percent);
        this.d = (TextView) findViewById(R.id.loading_percent_suffix);
        this.c.setVisibility(4);
        this.d.setVisibility(4);
        this.e = (LinearLayout) findViewById(R.id.network_speed_container);
        this.f = (TextView) findViewById(R.id.network_speed);
        this.n = AnimationUtils.loadAnimation(this.a, R.anim.rotate_anim);
        this.n.setInterpolator(new LinearInterpolator());
        this.b.startAnimation(this.n);
        this.h = (ConnectivityManager) getContext().getSystemService("connectivity");
    }

    public void show(boolean z) {
        Log.d("PlayerLoadingView", "show: " + z);
        setVisibility(0);
        this.b.clearAnimation();
        this.b.startAnimation(this.n);
        if (z) {
            this.e.setVisibility(0);
            scheduleCalculateSpeed();
            return;
        }
        this.e.setVisibility(8);
        this.c.setVisibility(8);
        this.d.setVisibility(8);
    }

    public void hide() {
        Log.d("PlayerLoadingView", "hide");
        setVisibility(8);
        c();
        this.b.clearAnimation();
    }

    public void setFullScreen(boolean z) {
        if (z != this.o) {
            this.o = z;
            a();
        }
    }

    public void setPercent(int i) {
        this.c.setText(String.valueOf(i));
    }

    private boolean b() {
        ConnectivityManager connectivityManager = this.h;
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void setTrafficStatsUid(int i) {
        this.p = i;
    }

    protected void scheduleCalculateSpeed() {
        c();
        this.g = Observable.interval(1L, TimeUnit.SECONDS, MicoSchedulers.io()).flatMap(new Function() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$PlayerLoadingView$Bu637kdZ8ZjL8nmeO3jtvXZFEK8
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                ObservableSource a;
                a = PlayerLoadingView.this.a((Long) obj);
                return a;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$PlayerLoadingView$kFzfM3CKnrkH7R3zuWySYIxIzlk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PlayerLoadingView.this.a((String) obj);
            }
        }, $$Lambda$PlayerLoadingView$h037KgdHZdpSKILTc4w_sq6fKQ.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ObservableSource a(Long l) throws Exception {
        return calculateSpeedObservable();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str) throws Exception {
        TextView textView = this.f;
        if (textView != null) {
            textView.setText(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.video.e(th);
    }

    private void c() {
        Disposable disposable = this.g;
        if (disposable != null && !disposable.isDisposed()) {
            this.g.dispose();
            this.g = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        c();
    }

    public Observable<String> calculateSpeedObservable() {
        return Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.video.player.widget.-$$Lambda$PlayerLoadingView$XRkGPQOMStNehiKSWmnWnTLv0W4
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) {
                PlayerLoadingView.this.a(observableEmitter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        observableEmitter.onNext(String.valueOf(d()));
        observableEmitter.onComplete();
    }

    private int d() {
        int i;
        long uidRxBytes = TrafficStats.getUidRxBytes(this.p);
        Log.d("PlayerLoadingView", "mUid: " + this.p + ", Total bytes: " + uidRxBytes + ", mRxBytes: " + this.j);
        long j = uidRxBytes - this.j;
        long currentTimeMillis = System.currentTimeMillis();
        long j2 = currentTimeMillis - this.i;
        if (j2 <= 0) {
            j2 = 500;
        }
        boolean b = b();
        if (!b) {
            if (j == 0) {
                Log.d("PlayerLoadingView", "isNetworkConnected == false");
                this.l = 0;
                this.m = 0;
            } else {
                Log.d("PlayerLoadingView", "isNetworkConnected == false && delta!=0 updateNetworkConnective");
            }
        }
        if (j <= 0 || !b) {
            this.k = 0;
        } else {
            this.k = (int) (((float) (j * 1000)) / ((float) (j2 * PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)));
        }
        this.j = uidRxBytes;
        this.i = currentTimeMillis;
        int i2 = this.l;
        if (i2 > 0 && (i = this.m) > 0) {
            this.k = ((this.k + i2) + i) / 3;
            Log.d("PlayerLoadingView", "mSpeed: " + this.k);
        }
        this.m = this.l;
        int i3 = this.k;
        this.l = i3;
        return i3;
    }
}
