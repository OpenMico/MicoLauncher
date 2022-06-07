package com.xiaomi.micolauncher.module.child.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.child.view.BannerAdapter;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* loaded from: classes3.dex */
public class BannerView extends ConstraintLayout implements BannerAdapter.a {
    ViewPager2 a;
    ImageView b;
    ImageView c;
    List<IListItem> d;
    int e;
    Context f;
    private OnBannerClickListener g;
    private OnBannerChangeListener h;
    private a i;
    private int j;
    private BannerAdapter k;

    /* loaded from: classes3.dex */
    public interface OnBannerChangeListener {
        void onBannerChange(int i);
    }

    /* loaded from: classes3.dex */
    public interface OnBannerClickListener {
        void onBannerClick(int i);
    }

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BannerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f = context;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.BannerView, 0, 0);
            this.j = obtainStyledAttributes.getResourceId(0, R.layout.banner_view);
            obtainStyledAttributes.recycle();
        }
        LayoutInflater.from(context).inflate(this.j, this);
        this.a = (ViewPager2) findViewById(R.id.banner_viewpager);
        this.b = (ImageView) findViewById(R.id.pre_img);
        this.c = (ImageView) findViewById(R.id.next_img);
        a();
        this.i = new a(this);
        this.i.sendEmptyMessageDelayed(17, RtspMediaSource.DEFAULT_TIMEOUT_MS);
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* loaded from: classes3.dex */
    static class a extends Handler {
        private final WeakReference<BannerView> a;

        a(BannerView bannerView) {
            this.a = new WeakReference<>(bannerView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            BannerView bannerView = this.a.get();
            if (bannerView != null) {
                bannerView.b();
                sendEmptyMessageDelayed(17, RtspMediaSource.DEFAULT_TIMEOUT_MS);
            }
        }
    }

    public void removeMessages() {
        this.i.removeCallbacksAndMessages(null);
    }

    public void startMessages() {
        this.i.sendEmptyMessageDelayed(17, RtspMediaSource.DEFAULT_TIMEOUT_MS);
    }

    public void setData(List<IListItem> list) {
        this.d = list;
        this.e = getStartIndex();
        this.k = new BannerAdapter(this.f, list);
        this.a.setAdapter(this.k);
        this.k.setOnItemClickListener(this);
        this.a.setCurrentItem(this.e, false);
    }

    private void a() {
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.view.-$$Lambda$BannerView$kpsl_WW7__T9BzBBXuTcSm70ZDk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BannerView.this.b(view);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.view.-$$Lambda$BannerView$T2k9MicqJA3iOY1Vp3m0nmxAzwE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BannerView.this.a(view);
            }
        });
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        b();
    }

    private void getNextVideo() {
        if (this.e == ContainerUtil.getSize(this.d) - 1) {
            this.e = 0;
        } else {
            this.e++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        if (!ContainerUtil.isEmpty(this.d)) {
            getNextVideo();
            c();
            OnBannerChangeListener onBannerChangeListener = this.h;
            if (onBannerChangeListener != null) {
                onBannerChangeListener.onBannerChange(this.e % this.d.size());
                L.childContent.i("banner current index %d", Integer.valueOf(this.e % this.d.size()));
            }
        }
    }

    private void c() {
        L.childContent.i("viewPager setCurrentItem %s", Integer.valueOf(this.e));
        this.a.setCurrentItem(this.e, true);
    }

    private void getPreVideo() {
        this.e--;
    }

    private void d() {
        if (!ContainerUtil.isEmpty(this.d)) {
            getPreVideo();
            c();
            OnBannerChangeListener onBannerChangeListener = this.h;
            if (onBannerChangeListener != null) {
                onBannerChangeListener.onBannerChange(this.e % this.d.size());
                L.childContent.i("banner current index %d", Integer.valueOf(this.e % this.d.size()));
            }
        }
    }

    private int getStartIndex() {
        if (ContainerUtil.isEmpty(this.d)) {
            return 0;
        }
        int i = LockFreeTaskQueueCore.MAX_CAPACITY_MASK;
        if (LockFreeTaskQueueCore.MAX_CAPACITY_MASK % ContainerUtil.getSize(this.d) == 0) {
            return LockFreeTaskQueueCore.MAX_CAPACITY_MASK;
        }
        while (i % ContainerUtil.getSize(this.d) != 0) {
            i++;
        }
        return i;
    }

    protected int getIndex(int i, int i2) {
        return i % i2;
    }

    @Override // com.xiaomi.micolauncher.module.child.view.BannerAdapter.a
    public void onItemClick(int i) {
        OnBannerClickListener onBannerClickListener = this.g;
        if (onBannerClickListener != null) {
            onBannerClickListener.onBannerClick(i);
        }
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.g = onBannerClickListener;
    }

    public void setOnBannerChangeListener(OnBannerChangeListener onBannerChangeListener) {
        this.h = onBannerChangeListener;
    }
}
