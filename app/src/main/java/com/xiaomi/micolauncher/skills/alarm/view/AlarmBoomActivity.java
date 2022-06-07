package com.xiaomi.micolauncher.skills.alarm.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiManager;
import com.xiaomi.micolauncher.api.model.Picture;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.TimeUtils;
import com.xiaomi.micolauncher.common.base.BaseEventActivity;
import com.xiaomi.micolauncher.common.event.PlayByRemoteEvent;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.module.lockscreen.manager.WallPaperDataManager;
import com.xiaomi.micolauncher.module.lockscreen.utils.AdHelper;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.micolauncher.skills.alarm.AlarmHelper;
import com.xiaomi.micolauncher.skills.alarm.AlarmStatHelper;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmBoomEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmTimeoutEvent;
import com.xiaomi.micolauncher.skills.alarm.controller.uievent.AlarmVoiceCloseEvent;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmModel;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.alarm.model.bean.Circle;
import com.xiaomi.micolauncher.skills.alarm.view.AlarmBoomActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public final class AlarmBoomActivity extends BaseEventActivity implements AlarmAudioPlayer.AlarmAudioPlayerListener {
    public static final String KEY_AD_INFO = "ad_info";
    private static final long o = TimeUnit.SECONDS.toMillis(6);
    private static final long p = TimeUnit.SECONDS.toMillis(1);
    private boolean A;
    private CountDownTimer B;
    private MediaPlayer C;
    private Picture.Pictorial D;
    private ContentObserver E;
    private BroadcastReceiver F;
    VideoView a;
    ImageView b;
    TextView c;
    View d;
    TextView e;
    TextView f;
    RelativeLayout g;
    ImageView h;
    TextView i;
    TextView j;
    TextView k;
    RelativeLayout l;
    ImageView m;
    TextView n;
    private ImageView q;
    private TextView r;
    private TextView s;
    private String u;
    private AlarmRealmObjectBean v;
    private FakePlayer y;
    private AlarmAudioPlayer z;
    private boolean t = true;
    private boolean w = false;
    private final AtomicBoolean x = new AtomicBoolean(false);

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmTimeoutEvent(AlarmTimeoutEvent alarmTimeoutEvent) {
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @SuppressLint({"CheckResult"})
    public void onCreate(Bundle bundle) {
        L.alarm.d("AlarmBoomActivity onCreate");
        super.onCreate(bundle);
        getWindow().addFlags(128);
        this.D = WallPaperDataManager.getManager().getAlarmAd();
        if (this.D != null) {
            setContentView(R.layout.activity_alarm_boom_ad);
            this.q = (ImageView) findViewById(R.id.ad_img);
            this.r = (TextView) findViewById(R.id.ad_title);
            this.s = (TextView) findViewById(R.id.ad_detail);
            this.s.setVisibility(this.D.adInfo.onlyShow() ? 8 : 0);
            this.r.setText(this.D.title.trim());
            GlideUtils.bindImageView(this, this.D.url, this.q);
            Observable.create(new ObservableOnSubscribe() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$roCwd1gYVVGzoRs7vO_4oTwlurg
                @Override // io.reactivex.ObservableOnSubscribe
                public final void subscribe(ObservableEmitter observableEmitter) {
                    AlarmBoomActivity.this.d(observableEmitter);
                }
            }).subscribeOn(MicoSchedulers.io()).subscribe();
            RxViewHelp.debounceClicksWithOneSeconds(this.r).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$5n7cSMC5hn7rO1wMSfG90HsTTl0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AlarmBoomActivity.this.b(obj);
                }
            });
            RxViewHelp.debounceClicksWithOneSeconds(this.s).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$M69KiwNa8uZTfoj9YqEySNthWcY
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AlarmBoomActivity.this.a(obj);
                }
            });
        } else {
            setContentView(R.layout.activity_alarm_boom);
            Observable.create($$Lambda$AlarmBoomActivity$SJEqw5mn_uByFlErnCc5W_X83Bc.INSTANCE).subscribeOn(MicoSchedulers.io()).subscribe();
        }
        this.a = (VideoView) findViewById(R.id.video_view);
        this.b = (ImageView) findViewById(R.id.video_preview_iv);
        this.c = (TextView) findViewById(R.id.video_time_tv);
        this.d = findViewById(R.id.video_divider_view);
        this.e = (TextView) findViewById(R.id.video_reminder_tv);
        this.f = (TextView) findViewById(R.id.video_stop_btn);
        this.g = (RelativeLayout) findViewById(R.id.video_layout);
        this.h = (ImageView) findViewById(R.id.cover_iv);
        this.i = (TextView) findViewById(R.id.time_tv);
        this.j = (TextView) findViewById(R.id.reminder_tv);
        this.k = (TextView) findViewById(R.id.stop_btn);
        this.l = (RelativeLayout) findViewById(R.id.music_layout);
        this.m = (ImageView) findViewById(R.id.dark_cover_iv);
        this.n = (TextView) findViewById(R.id.cycle_tv);
        AlarmModel.getInstance().setFiringStatus();
        this.y = new FakePlayer(AudioSource.AUDIO_SOURCE_ALARM) { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmBoomActivity.1
            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void background(AudioSource audioSource) {
                L.alarm.i("FakePlayer background");
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            protected void foreground(AudioSource audioSource) {
                L.alarm.i("FakePlayer foreground");
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
            public void postStop(AudioSource audioSource) {
                L.alarm.i("FakePlayer postStop=%s", audioSource.name());
                if (audioSource != AudioSource.AUDIO_SOURCE_ALARM) {
                    AlarmBoomActivity.this.e();
                    ThreadUtil.postDelayedInMainThread(new $$Lambda$Bm33niMu8yPLql4_R3ERUX9eUwQ(AlarmBoomActivity.this), AlarmBoomActivity.p);
                }
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void suspend(AudioSource audioSource) {
                L.alarm.i("FakePlayer suspend");
                if (AlarmBoomActivity.this.z != null) {
                    AlarmBoomActivity.this.z.stop();
                }
                ThreadUtil.postDelayedInMainThread(new $$Lambda$Bm33niMu8yPLql4_R3ERUX9eUwQ(AlarmBoomActivity.this), AlarmBoomActivity.p);
            }

            @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
            public void forceStop(AudioSource audioSource) {
                if (AlarmBoomActivity.this.z != null) {
                    AlarmBoomActivity.this.z.stop();
                }
                AlarmBoomActivity.this.d("forceStop");
            }
        };
        this.y.start();
        Screen.getInstance().onResume();
        Screen.getInstance().systemUiDismiss(0);
        getWindow().addFlags(128);
        k();
        CameraDetectionController.getManager().showSupportGestureNotification(this);
        b();
    }

    public /* synthetic */ void d(ObservableEmitter observableEmitter) throws Exception {
        AdHelper.recordAdShow(this, this.D.adInfo);
        AlarmStatHelper.recordAlarmCount(AlarmStatHelper.AlarmCategory.alarm_show, AlarmStatHelper.AlarmKey.ad);
    }

    public /* synthetic */ void b(Object obj) throws Exception {
        d();
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        d();
    }

    public static /* synthetic */ void c(ObservableEmitter observableEmitter) throws Exception {
        AlarmStatHelper.recordAlarmCount(AlarmStatHelper.AlarmCategory.alarm_show, AlarmStatHelper.AlarmKey.normal);
    }

    private void b() {
        if (Build.VERSION.SDK_INT >= 28) {
            this.F = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmBoomActivity.2
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent != null && TextUtils.equals("android.media.action.MICROPHONE_MUTE_CHANGED", intent.getAction())) {
                        AlarmBoomActivity.this.d("mute_key_down");
                    }
                }
            };
            registerReceiver(this.F, new IntentFilter("android.media.action.MICROPHONE_MUTE_CHANGED"));
        }
        Uri uriFor = Settings.Secure.getUriFor("SW_CAMERA_LENS_COVER");
        if (uriFor != null) {
            this.E = new ContentObserver(getHandler()) { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmBoomActivity.3
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    super.onChange(z);
                    AlarmBoomActivity.this.d("SW_CAMERA_LENS_COVER");
                }
            };
            getApplicationContext().getContentResolver().registerContentObserver(uriFor, false, this.E);
        }
    }

    private void c() {
        BroadcastReceiver broadcastReceiver = this.F;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            this.F = null;
        }
        if (this.E != null) {
            getApplicationContext().getContentResolver().unregisterContentObserver(this.E);
            this.E = null;
        }
    }

    private void d() {
        AdHelper.jump(this, this.D.adInfo, AlarmStatHelper.AlarmCategory.alarm_click, AlarmStatHelper.AlarmKey.ad);
        d("AlarmBoomActivity click ad detail");
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        L.alarm.i("AlarmBoomActivity onNewIntent");
        super.onNewIntent(intent);
        g();
        h();
        i();
        AlarmModel.getInstance().clearFiringStatus();
        AlarmModel.getInstance().setFiringStatus();
        k();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        e();
        this.A = false;
        c();
    }

    public void e() {
        L.alarm.d("AlarmBoomActivity releaseAlarm");
        AlarmModel.getInstance().clearFiringStatus();
        CountDownTimer countDownTimer = this.B;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        FakePlayer fakePlayer = this.y;
        if (fakePlayer != null) {
            fakePlayer.stop();
        }
        AlarmAudioPlayer alarmAudioPlayer = this.z;
        if (alarmAudioPlayer != null) {
            alarmAudioPlayer.release();
            this.z = null;
        }
        AlarmRealmObjectBean alarmRealmObjectBean = this.v;
        if (alarmRealmObjectBean != null && Circle.ONCE.equalsIgnoreCase(alarmRealmObjectBean.getCircle())) {
            L.alarm.i("AlarmBoomActivity removeAlarm type is once");
            AlarmModel.getInstance().removeAlarm(this.v.getIdStr());
        }
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseEventActivity, com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.AlarmAudioPlayerListener
    public void onTtsPlayEnd() {
        this.w = true;
        if (t()) {
            m();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.AlarmAudioPlayerListener
    public void onTtsError() {
        if (t()) {
            m();
        }
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.AlarmAudioPlayerListener
    public void onRingtoneStop() {
        if (f()) {
            SpeechManager.getInstance().nlpTtsRequest("早上好");
        }
        d("onRingtoneStop");
    }

    private boolean f() {
        int i = Calendar.getInstance().get(11);
        return i > 5 && i < 11;
    }

    @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.AlarmAudioPlayerListener
    public void onMusicResourceChange(final String str) {
        if (!isDestroyed() && !isFinishing() && Hardware.isSmallScreen()) {
            runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$qvWGLfpVKu-9tkktg88KjHCE-cY
                @Override // java.lang.Runnable
                public final void run() {
                    AlarmBoomActivity.this.g(str);
                }
            });
        }
    }

    /* renamed from: a */
    public void g(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                GlideUtils.bindImageViewWithListener(this, str, this.h, new ImageViewTarget<Bitmap>(this.h) { // from class: com.xiaomi.micolauncher.skills.alarm.view.AlarmBoomActivity.4
                    /* renamed from: a */
                    public void setResource(@Nullable Bitmap bitmap) {
                        AlarmBoomActivity.this.h.setImageBitmap(bitmap);
                        if (AlarmBoomActivity.this.m != null && !AlarmBoomActivity.this.isDestroyed() && !AlarmBoomActivity.this.isFinishing()) {
                            AlarmBoomActivity.this.m.setBackgroundColor(AlarmBoomActivity.this.getColor(R.color.black_50_transparent));
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void g() {
        if (this.h != null && Hardware.isSmallScreen()) {
            this.h.setImageDrawable(null);
        }
        VideoView videoView = this.a;
        if (videoView != null) {
            videoView.setVisibility(8);
        }
        ImageView imageView = this.b;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        this.g.setVisibility(8);
        this.l.setVisibility(0);
        CountDownTimer countDownTimer = this.B;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void h() {
        AlarmAudioPlayer alarmAudioPlayer = this.z;
        if (alarmAudioPlayer != null) {
            alarmAudioPlayer.stop();
            this.z = null;
        }
    }

    private void i() {
        VideoView videoView = this.a;
        if (videoView != null && videoView.isPlaying()) {
            this.a.stopPlayback();
        }
    }

    private void j() {
        String event = this.v.getEvent();
        String currentTimeInHourAndMinute = TimeUtils.getCurrentTimeInHourAndMinute();
        if (Hardware.isBigScreen()) {
            if (this.n != null) {
                String circleString = AlarmHelper.getCircleString(this, this.v);
                this.n.setText(circleString);
                this.c.setText(circleString);
            }
            this.i.setText(currentTimeInHourAndMinute);
            if (!ContainerUtil.isEmpty(event)) {
                currentTimeInHourAndMinute = currentTimeInHourAndMinute + StringUtils.SPACE + event;
                this.j.setText(event);
            }
            this.e.setText(currentTimeInHourAndMinute);
        } else {
            if (!ContainerUtil.isEmpty(event)) {
                this.j.setVisibility(0);
                this.j.setText(event);
                this.e.setText(event);
                this.i.setTextSize(2, 70.0f);
            } else {
                this.j.setVisibility(8);
                L.homepage.d("no event time");
                if (this.D != null) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.i.getLayoutParams();
                    layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.no_event_time_margin_top);
                    this.i.setLayoutParams(layoutParams);
                }
                this.i.setTextSize(2, 94.0f);
                this.d.setVisibility(8);
            }
            this.i.setText(currentTimeInHourAndMinute);
            this.c.setText(currentTimeInHourAndMinute);
        }
        this.k.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$J2THlBZ3ADf9HmErQ4nR0PVnMp0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmBoomActivity.this.b(view);
            }
        });
        this.f.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$jzeTs1FEdFyaAzGNjTW_JjvRd80
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlarmBoomActivity.this.a(view);
            }
        });
    }

    public /* synthetic */ void b(View view) {
        d("AlarmBoomActivity click stopBtn");
        Observable.create($$Lambda$AlarmBoomActivity$z8QnvA2r5UQbYlMbz4bI3qHipI0.INSTANCE).subscribeOn(MicoSchedulers.io()).subscribe();
    }

    public static /* synthetic */ void b(ObservableEmitter observableEmitter) throws Exception {
        AlarmStatHelper.recordAlarmCount(AlarmStatHelper.AlarmCategory.alarm_click, AlarmStatHelper.AlarmKey.stop);
    }

    public /* synthetic */ void a(View view) {
        d("AlarmBoomActivity click videoStopBtn");
        Observable.create($$Lambda$AlarmBoomActivity$qrhJMO7SvMs98uWX8DonJW0Ayak.INSTANCE).subscribeOn(MicoSchedulers.io()).subscribe();
    }

    public static /* synthetic */ void a(ObservableEmitter observableEmitter) throws Exception {
        AlarmStatHelper.recordAlarmCount(AlarmStatHelper.AlarmCategory.alarm_click, AlarmStatHelper.AlarmKey.stop);
    }

    private void k() {
        this.v = AlarmModel.getInstance().getLastFiringAlarmItem();
        if (this.v != null) {
            l();
            j();
            if (this.z == null) {
                this.z = new AlarmAudioPlayer(this, this);
            }
            this.z.bell(this.v, this.A);
            return;
        }
        ToastUtil.showToast((int) R.string.mico_alarm_error);
        d("alarm is null");
    }

    private void l() {
        this.u = this.v.getRingtoneType();
        if (AlarmHelper.isVideoRingtone(this.u)) {
            o();
            return;
        }
        this.g.setVisibility(8);
        this.l.setVisibility(0);
    }

    private void m() {
        L.alarm.i("playVideoAlarm");
        CountDownTimer countDownTimer = this.B;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.B = new AnonymousClass5(o, 300L);
        this.B.start();
    }

    /* renamed from: com.xiaomi.micolauncher.skills.alarm.view.AlarmBoomActivity$5 */
    /* loaded from: classes3.dex */
    public class AnonymousClass5 extends CountDownTimer {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(long j, long j2) {
            super(j, j2);
            AlarmBoomActivity.this = r1;
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            if (AlarmBoomActivity.this.x.get()) {
                onFinish();
                cancel();
            }
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (AlarmBoomActivity.this.x.get()) {
                L.alarm.i("play video, video load succeed");
                AlarmBoomActivity.this.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$5$KfV1Srs2B-NetPxQfzngeo5sTbM
                    @Override // java.lang.Runnable
                    public final void run() {
                        AlarmBoomActivity.AnonymousClass5.this.b();
                    }
                });
                return;
            }
            L.alarm.i("play video, video load failed, play default ring tone");
            AlarmBoomActivity.this.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$5$hBKcHOfPLgfX9nxct6SA6Wyf70g
                @Override // java.lang.Runnable
                public final void run() {
                    AlarmBoomActivity.AnonymousClass5.this.a();
                }
            });
        }

        public /* synthetic */ void b() {
            AlarmBoomActivity.this.s();
        }

        public /* synthetic */ void a() {
            AlarmBoomActivity.this.n();
            AlarmBoomActivity.this.r();
        }
    }

    public void n() {
        AlarmAudioPlayer alarmAudioPlayer = this.z;
        if (alarmAudioPlayer != null) {
            alarmAudioPlayer.playDefaultAlarm();
        }
        L.alarm.i("play default alarm");
    }

    private void o() {
        p();
        this.x.set(false);
        String ringtoneVideo = this.v.getRingtoneVideo();
        Logger logger = L.alarm;
        logger.i("set up video view " + ringtoneVideo);
        if (!TextUtils.isEmpty(ringtoneVideo)) {
            AudioAttributes build = new AudioAttributes.Builder().setUsage(4).setContentType(4).build();
            this.a.setAudioFocusRequest(0);
            this.a.setAudioAttributes(build);
            this.a.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$LvVbn8DBi3ixmpRcM98dIlQJHs0
                @Override // android.media.MediaPlayer.OnErrorListener
                public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    boolean a;
                    a = AlarmBoomActivity.this.a(mediaPlayer, i, i2);
                    return a;
                }
            });
            this.a.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$jTHtVP1K8UcBLfaxNeLYr9zoUHA
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    AlarmBoomActivity.this.b(mediaPlayer);
                }
            });
            this.a.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$WI3ULu71kdp3YgMWEy48FzFKFXI
                @Override // android.media.MediaPlayer.OnCompletionListener
                public final void onCompletion(MediaPlayer mediaPlayer) {
                    AlarmBoomActivity.this.a(mediaPlayer);
                }
            });
            if (ContainerUtil.isEmpty(ringtoneVideo) || !ringtoneVideo.startsWith("http")) {
                b(ringtoneVideo);
                return;
            }
            L.alarm.i("set video path and start");
            this.a.setVideoPath(ringtoneVideo);
        }
    }

    public /* synthetic */ boolean a(MediaPlayer mediaPlayer, int i, int i2) {
        L.alarm.i("video load failed, play default ring tone");
        n();
        r();
        this.x.set(false);
        return true;
    }

    public /* synthetic */ void b(MediaPlayer mediaPlayer) {
        this.x.set(true);
        this.C = mediaPlayer;
        if (this.w) {
            L.alarm.i("video on prepared, tts play end");
            return;
        }
        L.alarm.i("video on prepared, tts playing");
        this.a.pause();
    }

    public /* synthetic */ void a(MediaPlayer mediaPlayer) {
        AlarmAudioPlayer alarmAudioPlayer = this.z;
        if (alarmAudioPlayer == null || !alarmAudioPlayer.isMoreThanTenMinute()) {
            this.a.start();
        } else {
            onRingtoneStop();
        }
    }

    private void p() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.a.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        this.a.setLayoutParams(layoutParams);
        this.g.setVisibility(8);
        this.l.setVisibility(0);
        this.a.setVisibility(0);
        this.b.setVisibility(8);
    }

    private void q() {
        this.g.setVisibility(0);
        this.l.setVisibility(8);
        this.a.setVisibility(0);
        this.b.setVisibility(8);
    }

    public void r() {
        this.g.setVisibility(8);
        this.l.setVisibility(0);
        this.m.setVisibility(0);
        this.a.setVisibility(8);
        this.b.setVisibility(8);
    }

    @SuppressLint({"CheckResult"})
    private void b(String str) {
        c(str).observeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$weaXqls8k0JqcWUmET2OmdC6eGg
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AlarmBoomActivity.this.f((String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$_FLn8nzkCVCL_MJL_CaxhsklDoY
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AlarmBoomActivity.this.a((Throwable) obj);
            }
        });
    }

    public /* synthetic */ void f(final String str) throws Exception {
        if (!TextUtils.isEmpty(str)) {
            Logger logger = L.alarm;
            logger.i("get url succeed " + str);
            generateAIServiceCookie().observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$CITl2MBuVZV50A75skM4uPwJQLg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AlarmBoomActivity.this.a(str, (String) obj);
                }
            }, new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.view.-$$Lambda$AlarmBoomActivity$1OOvIV5z5DLnDXEJUtHXW0HZdLg
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AlarmBoomActivity.this.b((Throwable) obj);
                }
            });
            return;
        }
        this.x.set(false);
    }

    public /* synthetic */ void a(String str, String str2) throws Exception {
        if (TextUtils.isEmpty(str2)) {
            L.alarm.e("video cookie is empty");
            this.x.set(false);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("Cookie", str2 + "\r\n");
        L.alarm.i("set video path and start");
        this.a.setVideoURI(Uri.parse(str), hashMap);
    }

    public /* synthetic */ void b(Throwable th) throws Exception {
        Logger logger = L.alarm;
        logger.e("refresh service token error:" + th);
        this.x.set(false);
    }

    public /* synthetic */ void a(Throwable th) throws Exception {
        Logger logger = L.alarm;
        logger.e("build video url error:" + th);
        this.x.set(false);
    }

    public void s() {
        q();
        try {
            a(this.a, this.C);
            this.a.start();
        } catch (IllegalStateException e) {
            L.alarm.e("videoView start error", e);
            r();
            n();
        }
    }

    private void a(VideoView videoView, MediaPlayer mediaPlayer) {
        RelativeLayout.LayoutParams layoutParams;
        if (videoView != null && mediaPlayer != null) {
            int videoWidth = mediaPlayer.getVideoWidth();
            int videoHeight = mediaPlayer.getVideoHeight();
            int screenWidthPixels = DisplayUtils.getScreenWidthPixels(this);
            int screenHeightPixels = DisplayUtils.getScreenHeightPixels(this);
            float f = videoWidth;
            float f2 = screenWidthPixels;
            float f3 = videoHeight;
            float f4 = screenHeightPixels;
            if (f / f2 > f3 / f4) {
                int i = (int) (f3 * (f2 / f));
                layoutParams = new RelativeLayout.LayoutParams(screenWidthPixels, i);
                layoutParams.topMargin = (screenHeightPixels - i) / 2;
            } else {
                int i2 = (int) (f * (f4 / f3));
                layoutParams = new RelativeLayout.LayoutParams(i2, screenHeightPixels);
                layoutParams.leftMargin = (screenWidthPixels - i2) / 2;
            }
            videoView.setLayoutParams(layoutParams);
        }
    }

    private Observable<String> c(String str) {
        return ApiManager.displayService.getVideoFileUrl(str).subscribeOn(MicoSchedulers.io());
    }

    private boolean t() {
        return AlarmHelper.isVideoRingtone(this.u);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlarmVoiceCloseEvent(AlarmVoiceCloseEvent alarmVoiceCloseEvent) {
        d("onAlarmVoiceCloseEvent");
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventAlarmBoom(AlarmBoomEvent alarmBoomEvent) {
        String time = alarmBoomEvent.getTime();
        L.alarm.d("AlarmBoomActivity onEventAlarmBoom time: %s", time);
        this.A = TimeUtils.isMorningPeak(time);
        EventBusRegistry.getEventBus().removeStickyEvent(alarmBoomEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayByRemoteEvent(PlayByRemoteEvent playByRemoteEvent) {
        d("onPlayerByRemoteEvent");
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        L.base.d("onKeyDown, keyCode:%s event %s", Integer.valueOf(i), keyEvent);
        if (keyEvent.getAction() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        d("onKeyDown");
        return true;
    }

    public void d(String str) {
        L.alarm.i("finish event=%s", str);
        e();
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        GestureInfoEvent.GestureType gestureType;
        L.camera2.i("alarm receive gesture control event  %s", gestureControlEvent.gestureType);
        if (SystemSetting.getKeyGestureControlEnable() && (gestureType = gestureControlEvent.gestureType) == GestureInfoEvent.GestureType.Stop) {
            GestureToast.showGesture(this, gestureType, getString(R.string.gesture_pause));
            d("onGestureControlEvent");
        }
    }

    public Observable<String> generateAIServiceCookie() {
        return TokenManager.getInstance().refreshServiceToken("ai-service").map($$Lambda$AlarmBoomActivity$4LZDOtF2Jyvk4QJ8Qiyk0iWxflc.INSTANCE);
    }

    public static /* synthetic */ String e(String str) throws Exception {
        if (ContainerUtil.isEmpty(str)) {
            str = TokenManager.getInstance().getServiceInfo("ai-service").getServiceToken();
        }
        return String.format("serviceToken=%s;userId=%s", str, TokenManager.getInstance().getUserId());
    }
}
