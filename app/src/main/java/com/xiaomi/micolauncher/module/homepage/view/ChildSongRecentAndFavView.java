package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.module.homepage.event.ChildSongFavUpdateEvent;
import com.xiaomi.micolauncher.module.homepage.event.ChildSongRecentCoverUpdateEvent;
import com.xiaomi.micolauncher.module.homepage.manager.ChildSongDataManager;
import io.reactivex.functions.Consumer;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class ChildSongRecentAndFavView extends ConstraintLayout {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;
    private final Context e;

    public ChildSongRecentAndFavView(Context context) {
        this(context, null);
    }

    public ChildSongRecentAndFavView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSongRecentAndFavView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = context;
        b();
        initInMain();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(Object obj) throws Exception {
        a(this.e);
    }

    public void initInMain() {
        RxViewHelp.debounceClicksWithOneSeconds(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$ChildSongRecentAndFavView$QPJ9YazDrWZlnMLCGFUnnLRZmmk
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongRecentAndFavView.this.d(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$ChildSongRecentAndFavView$E828ddgky7y4tGpltXwNynu7tis
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongRecentAndFavView.this.c(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$ChildSongRecentAndFavView$tqU3copQO-42yL-sh8BfbWceO7o
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongRecentAndFavView.this.b(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.d).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$ChildSongRecentAndFavView$Am9ju9Jl5wyKAk2Q7DFT9aQv9V8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ChildSongRecentAndFavView.this.a(obj);
            }
        });
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.d.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Object obj) throws Exception {
        a(this.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Object obj) throws Exception {
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        a();
    }

    private void a() {
        ChildSongDataManager.getManager().playBabyLike(getContext());
    }

    private void a(Context context) {
        ChildSongDataManager.getManager().playRecent(context);
    }

    private void b() {
        View.inflate(getContext(), R.layout.child_song_recent_and_fav_view, this);
        this.a = (ImageView) findViewById(R.id.ivChildSongRecent);
        this.b = (ImageView) findViewById(R.id.ivChildSongFav);
        this.c = (ImageView) findViewById(R.id.ivRecentBg);
        this.d = (ImageView) findViewById(R.id.ivFavBg);
        EventBusRegistry.getEventBus().register(this);
    }

    public void setPatchBlockData() {
        c();
        d();
    }

    private void c() {
        GlideUtils.bindImageViewWithRoundCorners(getContext(), ChildSongDataManager.getManager().getRecentPlayCover(), this.a, this.e.getResources().getDimensionPixelSize(R.dimen.child_song_recent_icon_radius), (int) R.drawable.home_child_song_img_holder_with_bg_color_2cebd2);
    }

    private void d() {
        GlideUtils.bindImageViewWithRoundCorners(getContext(), ChildSongDataManager.getManager().getBabyLikeCover(), this.b, this.e.getResources().getDimensionPixelSize(R.dimen.child_song_fav_icon_radius), (int) R.drawable.home_child_song_img_holder_with_bg_color_white);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChildSongFavUpdate(ChildSongFavUpdateEvent childSongFavUpdateEvent) {
        d();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChildSongRecentCoverUpdate(ChildSongRecentCoverUpdateEvent childSongRecentCoverUpdateEvent) {
        c();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().unregister(this);
        }
    }
}
