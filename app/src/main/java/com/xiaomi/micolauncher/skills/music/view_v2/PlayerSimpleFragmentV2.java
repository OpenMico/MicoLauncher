package com.xiaomi.micolauncher.skills.music.view_v2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Remote;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer;
import com.xiaomi.micolauncher.common.player.widget.ShaderView;
import com.xiaomi.micolauncher.common.transformation.BlurTransformation;
import com.xiaomi.micolauncher.module.music.utils.MusicStatHelper;
import com.xiaomi.micolauncher.skills.common.WakeupUiEvent;
import com.xiaomi.micolauncher.skills.music.PlayerApi;
import com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import com.xiaomi.micolauncher.skills.music.model.MusicHelper;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class PlayerSimpleFragmentV2 extends BaseFragment implements ViewPager.OnPageChangeListener, ShaderRenderer.FadeOutListener, LocalPlayerManager.MetadataChangedCallback {
    public static final String GESTURE_CONTROL_ENABLE = "gesture_control_enable";
    public static final String VIEW_POSITION = "view_position";
    private ImageView a;
    private ShaderView b;
    private ViewPager c;
    private a d;
    private RequestOptions e;
    private Bitmap g;
    private int i;
    private boolean j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private int o;
    public Remote.Response.PlayingData playingData;
    private RectF t;
    private RectF u;
    private View v;
    private b x;
    private final int f = 8;
    private float h = 0.5f;
    private final int p = 800;
    private final int q = 650;
    private boolean r = true;
    private ViewTreeObserver.OnPreDrawListener s = null;
    private boolean w = true;
    private final Runnable y = new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerSimpleFragmentV2.4
        @Override // java.lang.Runnable
        public void run() {
            if (PlayerSimpleFragmentV2.this.n != PlayerSimpleFragmentV2.this.o) {
                int i = PlayerSimpleFragmentV2.this.n;
                if (!LocalPlayerManager.getInstance().isShuffleLoop() || (i = LocalPlayerManager.getInstance().getPlayingIndexInOriginalList(PlayerSimpleFragmentV2.this.n)) >= 0) {
                    L.player.d("PlayerSimpleFragmentV2 playByIndex currentIndex=%d, lastIndex=%d 将要实际播放 playIndex=%d", Integer.valueOf(PlayerSimpleFragmentV2.this.n), Integer.valueOf(PlayerSimpleFragmentV2.this.o), Integer.valueOf(i));
                    PlayerSimpleFragmentV2 playerSimpleFragmentV2 = PlayerSimpleFragmentV2.this;
                    playerSimpleFragmentV2.o = playerSimpleFragmentV2.n;
                    PlayerApi.playByIndex(i);
                }
            }
        }
    };

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onPlayerStateChanged(Remote.Response.PlayerStatus playerStatus) {
    }

    public static PlayerSimpleFragmentV2 newInstance(RectF rectF, boolean z) {
        Bundle bundle = new Bundle();
        PlayerSimpleFragmentV2 playerSimpleFragmentV2 = new PlayerSimpleFragmentV2();
        if (rectF != null) {
            bundle.putParcelable(VIEW_POSITION, rectF);
            bundle.putBoolean(GESTURE_CONTROL_ENABLE, z);
        }
        playerSimpleFragmentV2.setArguments(bundle);
        return playerSimpleFragmentV2;
    }

    @Override // androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int i, boolean z, int i2) {
        Animation animation;
        if (i == 4097) {
            animation = new AlphaAnimation(0.0f, 1.0f);
        } else {
            animation = 8194 == i ? new AlphaAnimation(1.0f, 0.0f) : null;
        }
        if (animation == null) {
            animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
        }
        animation.setDuration(800L);
        return animation;
    }

    private void a(View view, RectF rectF, RectF rectF2) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ofFloat);
        animatorSet.setDuration(650L);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.j = !getArguments().getBoolean(GESTURE_CONTROL_ENABLE, false);
        View inflate = layoutInflater.inflate(this.j ? R.layout.fragment_player_shader_v2 : R.layout.fragment_player_simple_v2, (ViewGroup) null);
        this.l = DisplayUtils.getScreenWidthPixels(getContext());
        this.m = DisplayUtils.getScreenHeightPixels(getContext());
        a(inflate);
        b();
        LocalPlayerManager.getInstance().registerCallback(this);
        return inflate;
    }

    private void a() {
        this.i = R.raw.fragment_a;
        switch (((PlayerActivityV2) getActivity()).effectIndex % 2) {
            case 0:
                this.i = R.raw.fragment_a;
                L.player.d("动效：fragment_a");
                break;
            case 1:
                this.i = R.raw.fragment_b;
                L.player.d("动效：fragment_b");
                break;
        }
        ((PlayerActivityV2) getActivity()).effectIndex++;
        this.b.getRenderer().setOnRendererListener(new ShaderRenderer.OnRendererListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerSimpleFragmentV2.1
            @Override // com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer.OnRendererListener
            public void onFramesPerSecond(int i) {
            }

            @Override // com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer.OnRendererListener
            public void onInfoLog(String str) {
            }
        });
    }

    private void a(View view) {
        if (this.j) {
            this.b = (ShaderView) view.findViewById(R.id.shader_view);
            a();
        } else {
            this.a = (ImageView) view.findViewById(R.id.background);
        }
        this.c = (ViewPager) view.findViewById(R.id.view_pager);
    }

    private void b() {
        List<Remote.Response.TrackData> list;
        boolean isShuffleLoop = LocalPlayerManager.getInstance().isShuffleLoop();
        if (isShuffleLoop) {
            list = LocalPlayerManager.getRandomPlayList();
        } else {
            list = LocalPlayerManager.getInstance().getPlayList();
            if (list != null) {
                list = new ArrayList(list);
            }
        }
        Logger logger = L.player;
        Object[] objArr = new Object[3];
        objArr[0] = Boolean.valueOf(isShuffleLoop);
        objArr[1] = Integer.valueOf(list == null ? 0 : list.hashCode());
        objArr[2] = Integer.valueOf(ContainerUtil.getSize(list));
        logger.i("PlayerSimpleFragmentV2 isShuffleLoop %s, playlist hash=%s, size=%s", objArr);
        if (!ContainerUtil.hasData(list)) {
            Logger logger2 = L.player;
            Object[] objArr2 = new Object[1];
            objArr2[0] = list == null ? "null" : "empty";
            logger2.e("PlayerSimpleFragmentV2 playList=%s", objArr2);
            EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayError());
            return;
        }
        this.d = new a(getChildFragmentManager(), getActivity());
        this.d.a(list);
        this.c.setAdapter(this.d);
        this.n = LocalPlayerManager.getInstance().getPlayingIndexInCurrentList();
        L.player.d("PlayerSimpleFragmentV2 currentIndex=%d", Integer.valueOf(this.n));
        this.c.setCurrentItem(this.n);
        this.o = this.n;
        this.c.addOnPageChangeListener(this);
        this.t = (RectF) getArguments().get(VIEW_POSITION);
        if (this.r) {
            if (this.s == null) {
                this.s = new ViewTreeObserver.OnPreDrawListener() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerSimpleFragmentV2$2bAVsqzYWsPPPXGdqlyPdkb6Yfo
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public final boolean onPreDraw() {
                        boolean h;
                        h = PlayerSimpleFragmentV2.this.h();
                        return h;
                    }
                };
            }
            ViewPager viewPager = this.c;
            if (viewPager != null) {
                viewPager.getViewTreeObserver().addOnPreDrawListener(this.s);
            }
        }
        Remote.Response.PlayerStatus playingPlayerStatus = LocalPlayerManager.getInstance().getPlayingPlayerStatus();
        if (playingPlayerStatus.play_song_detail != null) {
            this.d.a(playingPlayerStatus.media_type);
            if (!TextUtils.isEmpty(playingPlayerStatus.play_song_detail.cover)) {
                if (this.j && this.b != null) {
                    a(playingPlayerStatus.play_song_detail.cover);
                } else if (this.a != null) {
                    b(playingPlayerStatus.play_song_detail.cover);
                }
            } else if (this.j && this.b != null) {
                f();
            }
        }
    }

    public /* synthetic */ boolean h() {
        ViewPager viewPager = this.c;
        if (viewPager == null) {
            return true;
        }
        viewPager.getViewTreeObserver().removeOnPreDrawListener(this.s);
        if (this.d.getItem(this.n) == null) {
            return true;
        }
        this.v = ((PlayerChildFragmentV2) this.d.instantiateItem((ViewGroup) this.c, this.n)).getCoverView();
        if (this.u == null) {
            float dimension = getResources().getDimension(R.dimen.player_v2_simple_cover_size);
            float dimension2 = getResources().getDimension(R.dimen.player_v2_simple_lrc_margin_left);
            this.u = new RectF(dimension2, (this.m - dimension) - getResources().getDimension(R.dimen.player_v2_simple_cover_margin_bottom), dimension2 + dimension, this.m - dimension);
        }
        RectF rectF = this.t;
        if (rectF != null) {
            a(this.v, rectF, this.u);
        }
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        ShaderView shaderView;
        super.onResume();
        if (this.j && (shaderView = this.b) != null) {
            shaderView.onResume();
        }
        registerToEventBusIfNot();
        MusicStatHelper.recordPlayerShow(MusicStatHelper.PlayerPage.LYRIC);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        ShaderView shaderView;
        super.onPause();
        if (this.j && (shaderView = this.b) != null) {
            shaderView.onPause();
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        unregisterToEventBusIfNeed();
        LocalPlayerManager.getInstance().unregisterCallback(this);
        ThreadUtil.removeCallbacksInMainThread(this.y);
        this.c.clearOnPageChangeListeners();
    }

    private void a(Remote.Response.PlayerStatus playerStatus) {
        int randomPlayIndexByOriginalIndex;
        if (this.c != null) {
            int i = playerStatus.loop_type;
            int i2 = playerStatus.play_song_detail.screenExtend.index;
            L.player.d("PlayerSimpleFragmentV2 setViewPagerCurrentItem listLoop=%d currentItem=%d screenExtend.index=%d", Integer.valueOf(i), Integer.valueOf(this.c.getCurrentItem()), Integer.valueOf(i2));
            if (i != LoopType.SINGLE_LOOP.ordinal()) {
                if (i == LoopType.LIST_LOOP.ordinal() || i == LoopType.ORDER.ordinal()) {
                    if (this.c.getCurrentItem() != i2) {
                        this.n = i2;
                        this.o = i2;
                        this.c.setCurrentItem(i2, true);
                    }
                } else if (i == LoopType.SHUFFLE.ordinal() && this.c.getCurrentItem() != (randomPlayIndexByOriginalIndex = LocalPlayerManager.getInstance().getRandomPlayIndexByOriginalIndex(i2))) {
                    L.player.d("PlayerSimpleFragmentV2 onMediaFileChanged shuffleMode randomIndex=%d", Integer.valueOf(randomPlayIndexByOriginalIndex));
                    this.n = randomPlayIndexByOriginalIndex;
                    this.o = randomPlayIndexByOriginalIndex;
                    this.c.setCurrentItem(randomPlayIndexByOriginalIndex, true);
                }
            }
        }
    }

    private void a(String str) {
        if ((!isViewNotAvailable() || !isAdded()) && getActivity() != null) {
            Glide.with(this).load(str).error((int) R.drawable.bg_music_card).format(DecodeFormat.PREFER_ARGB_8888).apply((BaseRequestOptions<?>) GlideUtils.getDefaultRequestOptions().override(128)).into((RequestBuilder) new CustomTarget<Drawable>() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerSimpleFragmentV2.2
                @Override // com.bumptech.glide.request.target.Target
                public void onLoadCleared(@Nullable Drawable drawable) {
                }

                /* renamed from: a */
                public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                    PlayerSimpleFragmentV2 playerSimpleFragmentV2 = PlayerSimpleFragmentV2.this;
                    playerSimpleFragmentV2.x = new b((BitmapDrawable) drawable);
                    PlayerSimpleFragmentV2.this.e();
                }

                @Override // com.bumptech.glide.request.target.CustomTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    if (drawable != null) {
                        PlayerSimpleFragmentV2 playerSimpleFragmentV2 = PlayerSimpleFragmentV2.this;
                        playerSimpleFragmentV2.x = new b((BitmapDrawable) drawable);
                        PlayerSimpleFragmentV2.this.e();
                    }
                }
            });
        }
    }

    /* loaded from: classes3.dex */
    public class b implements ShaderRenderer.TextureLoader {
        private final BitmapDrawable b;

        public b(BitmapDrawable bitmapDrawable) {
            PlayerSimpleFragmentV2.this = r3;
            this.b = bitmapDrawable;
            r3.g = bitmapDrawable.getBitmap().copy(Bitmap.Config.RGB_565, true);
        }

        @Override // com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer.TextureLoader
        public Bitmap getTextureBitmap(String str) {
            return this.b.getBitmap();
        }

        @Override // com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer.TextureLoader
        public Bitmap getSwatchBitmap() {
            return PlayerSimpleFragmentV2.this.g;
        }
    }

    private void c() {
        try {
            InputStream openRawResource = getResources().openRawResource(this.i);
            byte[] bArr = new byte[openRawResource.available()];
            openRawResource.read(bArr);
            this.b.setFragmentShader(new String(bArr), this.h);
            openRawResource.close();
        } catch (Exception e) {
            L.player.e("PlayerSimpleFragmentV2 loadShaderScript", e);
        }
    }

    private void b(String str) {
        if ((!isViewNotAvailable() || !isAdded()) && getActivity() != null) {
            Glide.with(this).load(str).apply((BaseRequestOptions<?>) d()).into((RequestBuilder<Drawable>) new ImageViewTarget<Drawable>(this.a) { // from class: com.xiaomi.micolauncher.skills.music.view_v2.PlayerSimpleFragmentV2.3
                /* renamed from: a */
                public void setResource(@Nullable Drawable drawable) {
                    if (!PlayerSimpleFragmentV2.this.isViewNotAvailable()) {
                        PlayerSimpleFragmentV2.this.a.setImageDrawable(drawable);
                    }
                }
            });
        }
    }

    private RequestOptions d() {
        if (this.e == null) {
            this.e = GlideUtils.getDefaultRequestOptions().error(R.drawable.player_cover_default).override(this.l, this.m).transform(new BlurTransformation(getContext(), 8));
        }
        return this.e;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        this.n = i;
        Logger logger = L.player;
        logger.d("PlayerSimpleFragmentV2 onPageSelected:" + i);
        ShaderView shaderView = this.b;
        if (shaderView != null) {
            ShaderRenderer renderer = shaderView.getRenderer();
            this.x = null;
            if (renderer != null) {
                this.w = false;
                renderer.fadeOutByFactor(0.9f, this);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer.FadeOutListener
    public void onFadeOutFinish() {
        L.player.d("PlayerSimpleFragmentV2 onFadeOutFinish");
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerSimpleFragmentV2$1wQpgOsA_2a_K_DneMfgrCcPi5k
            @Override // java.lang.Runnable
            public final void run() {
                PlayerSimpleFragmentV2.this.g();
            }
        });
    }

    public /* synthetic */ void g() {
        this.w = true;
        e();
    }

    public void e() {
        if (this.x != null && this.w) {
            this.b.getRenderer().setTextureLoader(this.x);
            c();
            ShaderRenderer renderer = this.b.getRenderer();
            if (renderer != null) {
                renderer.fadeInByFactor(0.85f);
            }
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        if (i == 0) {
            ThreadUtil.postDelayedInMainThread(this.y, 150L);
        } else {
            ThreadUtil.removeCallbacksInMainThread(this.y);
        }
    }

    public int getCurrentIndex() {
        return this.n;
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaListChanged(Remote.Response.PlayerStatus playerStatus) {
        if (!isViewNotAvailable()) {
            List list = null;
            List<Remote.Response.TrackData> list2 = playerStatus.extra_track_list;
            if (playerStatus.loop_type == LoopType.SHUFFLE.ordinal()) {
                list = LocalPlayerManager.getRandomPlayList(list2);
            } else if (ContainerUtil.hasData(list2)) {
                list = new ArrayList(list2);
            }
            if (!ContainerUtil.hasData(list) || this.d == null) {
                L.player.w("PlayerSimpleFragmentV2 onMediaListChanged but tempList isEmpty");
                return;
            }
            ArrayList arrayList = new ArrayList(list);
            L.player.d("PlayerSimpleFragmentV2 onMediaListChanged tempListSize=%d changed, notifyDataSetChanged", Integer.valueOf(list.size()));
            this.d.a(arrayList);
            this.d.notifyDataSetChanged();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.music.controller.LocalPlayerManager.MetadataChangedCallback
    public void onMediaMetadataChanged(Remote.Response.PlayerStatus playerStatus) {
        if (playerStatus != null && playerStatus.play_song_detail != null && !isViewNotAvailable()) {
            if (!MusicHelper.isPlayRemote(playerStatus.media_type) || getActivity() == null) {
                L.player.d("PlayerSimpleFragmentV2 onMediaFileChanged play mediaType=%d", Integer.valueOf(playerStatus.media_type));
                if (MusicHelper.isPlayingSong(playerStatus.media_type) || getActivity() == null) {
                    if (LocalPlayerManager.isResourceChange(playerStatus, this.playingData)) {
                        a(playerStatus);
                        a aVar = this.d;
                        if (aVar != null) {
                            aVar.a(playerStatus.media_type);
                        }
                        if (!TextUtils.isEmpty(playerStatus.play_song_detail.cover)) {
                            if (this.j && this.b != null) {
                                a(playerStatus.play_song_detail.cover);
                            } else if (this.a != null) {
                                b(playerStatus.play_song_detail.cover);
                            }
                        }
                    }
                    this.playingData = playerStatus.play_song_detail;
                    return;
                }
                L.player.w("PlayerSimpleFragmentV2 onMediaFileChanged will show PlayerRadioFragmentV2 cause of play FM");
                ((PlayerActivityV2) getActivity()).setFragmentContainer(PlayerRadioFragmentV2.newInstance(), 3);
                return;
            }
            L.player.w("PlayerSimpleFragmentV2 onMediaFileChanged will show PlayerMainFragmentV2 cause of play ble");
            ((PlayerActivityV2) getActivity()).setFragmentContainer(PlayerMainFragmentV2.newInstance(), 3);
        }
    }

    private void f() {
        if (getContext() != null) {
            this.x = new b((BitmapDrawable) getContext().getResources().getDrawable(R.drawable.bg_music_card, null));
            e();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWakeupUiEvent(WakeupUiEvent wakeupUiEvent) {
        if (this.j && this.b != null) {
            if (wakeupUiEvent.getEvent() == 3) {
                this.k = false;
                this.b.onResume();
                return;
            }
            this.k = true;
            this.b.onPause();
        }
    }

    /* loaded from: classes3.dex */
    public static class a extends FragmentStatePagerAdapter {
        private List<Remote.Response.TrackData> a;
        private int b;
        private final Activity c;

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(@NonNull Object obj) {
            return -2;
        }

        public a(FragmentManager fragmentManager, Activity activity) {
            super(fragmentManager, 1);
            this.c = activity;
        }

        public void a(List<Remote.Response.TrackData> list) {
            this.a = list;
            Logger logger = L.player;
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(list == null ? 0 : list.hashCode());
            objArr[1] = Integer.valueOf(ContainerUtil.getSize(list));
            logger.i("PlayerSimpleFragmentV2 setTrackDataList play list %s, size %s", objArr);
        }

        public void a(int i) {
            this.b = i;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            List<Remote.Response.TrackData> list = this.a;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        @Override // androidx.fragment.app.FragmentStatePagerAdapter
        public Fragment getItem(int i) {
            List<Remote.Response.TrackData> list = this.a;
            if (list == null) {
                throw new IllegalStateException("should call setTrackDataList");
            } else if (ContainerUtil.isOutOfBound(i, list)) {
                L.player.e("PlayerSimpleFragmentV2#ChildFragmentAdapter trackDataList position invalid  %s", Integer.valueOf(i));
                Activity activity = this.c;
                if (activity != null) {
                    activity.finish();
                    return null;
                }
                EventBusRegistry.getEventBus().post(new PlayerEvent.OnPlayError());
                return null;
            } else {
                Remote.Response.TrackData trackData = this.a.get(i);
                L.player.i("PlayerSimpleFragmentV2 ChildFragmentAdapter getItem=%d trackData title=%s", Integer.valueOf(i), trackData.title);
                return PlayerChildFragmentV2.newInstance(trackData, this.b);
            }
        }
    }
}
