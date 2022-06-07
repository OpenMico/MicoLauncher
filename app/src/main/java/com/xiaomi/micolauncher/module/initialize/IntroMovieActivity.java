package com.xiaomi.micolauncher.module.initialize;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.base.BaseFragment;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.systemui.SystemUIManager;
import com.xiaomi.micolauncher.common.widget.TitleBar;
import com.xiaomi.micolauncher.module.QuickSettingHelper;
import com.xiaomi.micolauncher.module.initialize.IntroMovieActivity;
import com.xiaomi.micolauncher.skills.common.WakeupUiEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class IntroMovieActivity extends BaseActivity {
    public static final String KEY_SOURCE = "KEY_SOURCE";
    private int a;

    /* loaded from: classes3.dex */
    public enum Source {
        Initial,
        ReInitial,
        Help
    }

    public static void startIntroMovieActivity(Activity activity) {
        startIntroMovieActivity(activity, Source.Initial, false, 0);
    }

    public static void startIntroMovieActivity(Activity activity, Source source, boolean z, int i) {
        Intent intent = new Intent(activity, IntroMovieActivity.class);
        intent.putExtra(KEY_SOURCE, source.ordinal());
        if (z) {
            activity.startActivityForResult(intent, i);
        } else {
            activity.startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        this.a = getIntent().getIntExtra(KEY_SOURCE, Source.Initial.ordinal());
        setContentView(R.layout.activity_intro_movie);
        IntroMovieFragment introMovieFragment = new IntroMovieFragment();
        introMovieFragment.setIntroMovieFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, introMovieFragment).commitNowAllowingStateLoss();
        if (a()) {
            QuickSettingHelper.enable(this, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.a != Source.Initial.ordinal()) {
            Threads.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$IntroMovieActivity$VIZPIxBaDBR-TghykwxtAePS3ZA
                @Override // java.lang.Runnable
                public final void run() {
                    IntroMovieActivity.this.b();
                }
            }, 800L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        SystemUIManager.getDefault().enableGlobalGesture(getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (a()) {
            QuickSettingHelper.enable(this, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        return this.a == Source.Initial.ordinal() || this.a == Source.ReInitial.ordinal();
    }

    /* loaded from: classes3.dex */
    public static class IntroMovieFragment extends BaseFragment implements View.OnClickListener {
        VideoView a;
        TextView b;
        ImageView c;
        TitleBar d;
        private MediaPlayer e;
        private int f;
        private IntroMovieActivity g;
        private String h;

        public void setIntroMovieFragment(IntroMovieActivity introMovieActivity) {
            this.g = introMovieActivity;
        }

        @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
        @Nullable
        public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
            View inflate = layoutInflater.inflate(R.layout.fragment_intro_movie, viewGroup, false);
            this.a = (VideoView) inflate.findViewById(R.id.video_view);
            this.b = (TextView) inflate.findViewById(R.id.skip);
            this.c = (ImageView) inflate.findViewById(R.id.video_cover);
            this.d = (TitleBar) inflate.findViewById(R.id.title_bar);
            this.c.setOnClickListener(this);
            this.b.setOnClickListener(this);
            this.d.setOnLeftIconClickListener(new TitleBar.OnLeftIconClickListener() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$IntroMovieActivity$IntroMovieFragment$0ptVqYiL1HSmsGaPgstELViV2eU
                @Override // com.xiaomi.micolauncher.common.widget.TitleBar.OnLeftIconClickListener
                public final void onLeftIconClick() {
                    IntroMovieActivity.IntroMovieFragment.this.b();
                }
            });
            if (this.g.a()) {
                this.d.setVisibility(8);
            }
            return inflate;
        }

        @Override // com.xiaomi.micolauncher.common.base.BaseFragment
        @NonNull
        public BaseFragment.EventBusFragmentMode getEventBusRegisterMode() {
            return BaseFragment.EventBusFragmentMode.WHOLE_LIFE;
        }

        @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
        public void onActivityCreated(@Nullable Bundle bundle) {
            super.onActivityCreated(bundle);
            this.a.setVideoPath(a());
            this.a.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$IntroMovieActivity$IntroMovieFragment$Psh69bfg01JW6DZVNUNvItqzFv8
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    IntroMovieActivity.IntroMovieFragment.this.d(mediaPlayer);
                }
            });
            this.a.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$IntroMovieActivity$IntroMovieFragment$Ye7k3ZrxVChilYjY-Tp3zhgwpOM
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    IntroMovieActivity.IntroMovieFragment.this.c(mediaPlayer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void d(MediaPlayer mediaPlayer) {
            this.e = mediaPlayer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c(MediaPlayer mediaPlayer) {
            b();
        }

        private String a() {
            String str = this.h;
            if (str != null) {
                return str;
            }
            this.h = "/system/media/init_intro.mp4";
            return this.h;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b() {
            this.a.stopPlayback();
            if (this.g.a()) {
                SystemSetting.setIntroMoviePlayed(getContext());
                if (this.g.a == Source.Initial.ordinal()) {
                    ActivityLifeCycleManager.getInstance().gotoMainActivity(this.g);
                }
            }
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.finish();
            }
        }

        @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
        public void onPause() {
            super.onPause();
            this.a.pause();
            this.f = this.a.getCurrentPosition();
            Logger logger = L.player;
            logger.d("pause at position:" + this.f);
        }

        @Override // com.xiaomi.micolauncher.common.base.BaseFragment, androidx.fragment.app.Fragment
        public void onResume() {
            super.onResume();
            if (this.c.getVisibility() != 0) {
                this.a.start();
                this.a.seekTo(this.f);
                Logger logger = L.player;
                logger.d("seek to position:" + this.f);
            }
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void resumeIntroVideo(TtsPlayEndEvent ttsPlayEndEvent) {
            if (this.e != null && this.c.getVisibility() == 8) {
                c();
            }
        }

        @Subscribe(threadMode = ThreadMode.MAIN)
        public void stopVideoPlay(WakeupUiEvent wakeupUiEvent) {
            if (wakeupUiEvent.getEvent() == 0 && this.c.getVisibility() == 8) {
                this.e.pause();
            }
        }

        private void c() {
            try {
                this.e.start();
            } catch (Exception e) {
                b();
                L.player.d("mediaPlayerStart() e: ", e);
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.skip) {
                b();
            } else if (id == R.id.video_cover) {
                if (this.e != null) {
                    c();
                } else {
                    this.a.setVideoPath(a());
                    this.a.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$IntroMovieActivity$IntroMovieFragment$VeE1R5uhIXHPhK10-UtbpMmuVBY
                        @Override // android.media.MediaPlayer.OnPreparedListener
                        public final void onPrepared(MediaPlayer mediaPlayer) {
                            IntroMovieActivity.IntroMovieFragment.this.b(mediaPlayer);
                        }
                    });
                    this.a.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$IntroMovieActivity$IntroMovieFragment$NfhTJiXF0bvkRlOrflOtKX9iy-Y
                        @Override // android.media.MediaPlayer.OnCompletionListener
                        public final void onCompletion(MediaPlayer mediaPlayer) {
                            IntroMovieActivity.IntroMovieFragment.this.a(mediaPlayer);
                        }
                    });
                }
                if (this.g.a()) {
                    Threads.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.initialize.-$$Lambda$IntroMovieActivity$IntroMovieFragment$IqXdw-EQvT6eV5n0j9VubLdpBv8
                        @Override // java.lang.Runnable
                        public final void run() {
                            IntroMovieActivity.IntroMovieFragment.this.d();
                        }
                    }, 5000L);
                }
                this.c.setVisibility(8);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(MediaPlayer mediaPlayer) {
            this.e = mediaPlayer;
            c();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(MediaPlayer mediaPlayer) {
            b();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void d() {
            TextView textView = this.b;
            if (textView != null) {
                textView.setVisibility(0);
            }
        }
    }
}
