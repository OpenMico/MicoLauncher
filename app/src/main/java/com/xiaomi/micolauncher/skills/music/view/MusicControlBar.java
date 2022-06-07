package com.xiaomi.micolauncher.skills.music.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.stat.PlaybackTrackHelper;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.CollectionManager;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MusicControlBar extends LinearLayout implements CollectionManager.CollectionListener, LocalPlayerManager.MetadataChangedCallback {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;
    ImageView e;
    private boolean f;
    private boolean g;
    private Remote.Response.PlayerStatus j;
    private Disposable m;
    private final String h = "1";
    private final String i = "0";
    private PublishSubject<Integer> k = PublishSubject.create();
    private final long l = 6;

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    public MusicControlBar(Context context) {
        super(context);
    }

    public MusicControlBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MusicControlBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.a = (ImageView) findViewById(R.id.play);
        this.b = (ImageView) findViewById(R.id.love);
        this.c = (ImageView) findViewById(R.id.play_mode);
        this.d = (ImageView) findViewById(R.id.next);
        this.e = (ImageView) findViewById(R.id.prev);
        a();
    }

    private void a() {
        RxViewHelp.debounceClicks(this.c, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$MusicControlBar$FyUVg7hNGqlK3borKrtyF8UbLn8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicControlBar.this.e(obj);
            }
        });
        RxViewHelp.debounceClicks(this.e).subscribe($$Lambda$MusicControlBar$QUPg5_TxZm345jP1u_xkEShfkoQ.INSTANCE);
        RxViewHelp.debounceClicks(this.a, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$MusicControlBar$AMYVjnOs3XEhqQR95sSCWHQwsOw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicControlBar.this.c(obj);
            }
        });
        RxViewHelp.debounceClicks(this.d).subscribe($$Lambda$MusicControlBar$pCMaw9t9h4XSeHCWNzV9NgvEHOA.INSTANCE);
        RxViewHelp.debounceClicks(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$MusicControlBar$omRR-MFw-Y0lEkixE6YUD3lAHZE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicControlBar.this.a(obj);
            }
        });
        this.k.subscribeOn(MicoSchedulers.mainThread()).doOnNext(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$MusicControlBar$8-G_AGrEBYK7IxMNMvu6IUNcNec
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicControlBar.this.b((Integer) obj);
            }
        }).debounce(6L, TimeUnit.SECONDS).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$MusicControlBar$S9ezUunJ0eRk5rYciUWHgwtwcto
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MusicControlBar.this.a((Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void e(Object obj) throws Exception {
        setLoopModeView(PlayerApi.nextLoop());
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.RECYCLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void d(Object obj) throws Exception {
        PlayerApi.prev();
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PREV);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(Object obj) throws Exception {
        PlayerApi.playOrPause();
        if ("1".equals(this.a.getTag())) {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PAUSE);
        } else {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.PLAY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Object obj) throws Exception {
        PlayerApi.next();
        MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.NEXT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        PlayerApi.toggleLove(!this.b.isSelected());
        if (this.b.isSelected()) {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.CANCEL_COLLECT);
        } else {
            MusicStatHelper.recordPlayerClick(MusicStatHelper.PlayerAction.COLLECT);
        }
        Remote.Response.PlayerStatus playerStatus = this.j;
        if (playerStatus != null) {
            PlaybackTrackHelper.postFavoriteEvent(playerStatus.play_song_detail, "click", !this.b.isSelected());
        }
        ImageView imageView = this.b;
        imageView.setSelected(!imageView.isSelected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(Integer num) throws Exception {
        a(this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Integer num) throws Exception {
        hide();
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus.play_song_detail == null) {
            this.a.setImageResource(R.drawable.icon_play);
            this.a.setTag("0");
        } else if (playerStatus.isPlayingStatus()) {
            this.a.setImageResource(R.drawable.icon_pause);
            this.a.setTag("1");
        } else {
            this.a.setImageResource(R.drawable.icon_play);
            this.a.setTag("0");
        }
        this.j = playerStatus;
        if (!(this.j.play_song_detail == null || this.j.play_song_detail.screenExtend == null)) {
            this.b.setEnabled(CollectionManager.canLovable(this.j.media_type));
        }
        if (MusicHelper.canResetLoop(playerStatus.media_type)) {
            this.c.setEnabled(true);
            setLoopModeView(playerStatus.loop_type);
            return;
        }
        this.c.setEnabled(false);
        this.c.setImageResource(R.drawable.playmode_loop_disable);
    }

    private void b() {
        Remote.Response.PlayerStatus playerStatus = this.j;
        if (playerStatus != null && playerStatus.play_song_detail != null) {
            this.m = CollectionManager.getInstance().loadLoveStatus(this.j.play_song_detail.audioID, this.j.play_song_detail.albumGlobalID, this.j.media_type).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$MusicControlBar$9BYRQ0Eqznz63mWcvZwQHtasVaM
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MusicControlBar.this.a((Boolean) obj);
                }
            }, $$Lambda$MusicControlBar$k5yFmKi8fdpubZTOCYZxIk_DWXA.INSTANCE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Boolean bool) throws Exception {
        this.b.setSelected(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.player.e("setLoveState error %s", th);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            c();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private void setLoopModeView(int i) {
        if (i == LoopType.ORDER.ordinal()) {
            this.c.setImageResource(R.drawable.playmode_loop);
        } else if (i == LoopType.SINGLE_LOOP.ordinal()) {
            this.c.setImageResource(R.drawable.playmode_loop_single);
        } else if (i == LoopType.LIST_LOOP.ordinal()) {
            this.c.setImageResource(R.drawable.playmode_loop);
        } else if (i == LoopType.SHUFFLE.ordinal()) {
            this.c.setImageResource(R.drawable.playmode_loop_shuffle);
        } else if (i == LoopType.NONE.ordinal()) {
            this.c.setImageResource(R.drawable.playmode_loop_disable);
        } else {
            this.c.setImageResource(R.drawable.playmode_loop);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        CollectionManager.getInstance().registerCollectionListener(this);
        LocalPlayerManager.getInstance().registerCallback(this);
        this.f = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBusRegistry.getEventBus().unregister(this);
        this.f = false;
        Disposable disposable = this.m;
        if (disposable != null && !disposable.isDisposed()) {
            this.m.dispose();
        }
        CollectionManager.getInstance().unregisterCollectionListener(this);
        LocalPlayerManager.getInstance().unregisterCallback(this);
    }

    private void c() {
        this.k.onNext(1);
    }

    public void hide() {
        if (this.f && getAlpha() >= 1.0f) {
            animate().alpha(0.0f).translationY(getHeight() * (-1)).setDuration(300L).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.skills.music.view.MusicControlBar.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    MusicControlBar.this.setVisibility(8);
                }
            }).start();
        }
    }

    public void show(boolean z) {
        if (!isShowing()) {
            this.g = z;
            this.k.onNext(1);
        }
    }

    private void a(boolean z) {
        if (getVisibility() == 8) {
            setAlpha(0.0f);
            setTranslationY(getHeight() * (-1));
            setVisibility(0);
            animate().alpha(1.0f).translationY(0.0f).setDuration(300L).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.skills.music.view.MusicControlBar.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                }
            }).start();
        }
        if (z) {
            this.b.setVisibility(8);
            this.c.setVisibility(8);
            return;
        }
        this.b.setVisibility(0);
        this.c.setVisibility(0);
        b();
    }

    public boolean isShowing() {
        return getVisibility() == 0;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.CollectionManager.CollectionListener
    public void onCollectionChange(String str, final boolean z) {
        ImageView imageView = this.b;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.view.-$$Lambda$MusicControlBar$3qoyNYdS8iqKy7LFK5ato6ix68Q
                @Override // java.lang.Runnable
                public final void run() {
                    MusicControlBar.this.b(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(boolean z) {
        this.b.setSelected(z);
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        a(playerStatus);
        if (isShowing()) {
            b();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
        if (isShowing()) {
            a(playerStatus);
        }
    }
}
