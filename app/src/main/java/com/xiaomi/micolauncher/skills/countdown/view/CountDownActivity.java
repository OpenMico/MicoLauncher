package com.xiaomi.micolauncher.skills.countdown.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RawRes;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.mico.base.utils.TimeCalculator;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.UserBehaviorProxy;
import com.xiaomi.micolauncher.api.model.MainScreen;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;
import com.xiaomi.micolauncher.module.camera2.event.GestureInfoEvent;
import com.xiaomi.micolauncher.module.cameradetection.CameraDetectionController;
import com.xiaomi.micolauncher.module.cameradetection.event.GestureControlEvent;
import com.xiaomi.micolauncher.skills.countdown.controller.uievent.CountDownCancelEvent;
import com.xiaomi.micolauncher.skills.countdown.controller.uievent.CountDownFireEvent;
import com.xiaomi.micolauncher.skills.countdown.controller.uievent.DismissCountDownUiEvent;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import com.xiaomi.micolauncher.skills.voip.model.VoipStartEvent;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import ui.view.GestureToast;

/* loaded from: classes3.dex */
public final class CountDownActivity extends BaseActivity implements View.OnClickListener {
    public static final int ANIMATION_PLAY_DELAY_MILLIS = 500;
    private static final String a = "CountDownActivity";
    private d b;
    private Handler c;
    private Runnable d;
    private Runnable e;
    private Runnable f;
    private Timer g;
    private View h;
    private View i;
    private View j;
    private TextView k;
    private Button l;
    private List<MainScreen.InstructionItem> m;
    private int n;
    private TextView o;
    private LottieAnimationView p;
    private Timer q;
    private a r;
    private c s;
    private ImageView t;
    private final b u = new b();
    private FakePlayer v;
    public static final long FINISH_DELAY = TimeUnit.SECONDS.toMillis(3);
    public static final long PERIOD_DEBUG = TimeUnit.SECONDS.toMillis(10);
    public static final long PERIOD_RELEASE = TimeUnit.MINUTES.toMillis(5);
    public static final long COUNT_DOWN_TEXT_UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(1);
    public static final long RING_DURATION = TimeUnit.MINUTES.toMillis(10);

    /* loaded from: classes3.dex */
    public enum d {
        CLOSE,
        OPEN
    }

    protected boolean getLoop() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_count_down);
        this.b = d.CLOSE;
        this.c = getHandler();
        this.d = new Runnable() { // from class: com.xiaomi.micolauncher.skills.countdown.view.-$$Lambda$CountDownActivity$URoiabdmfJWhPQEjGaIkezmiTxM
            @Override // java.lang.Runnable
            public final void run() {
                CountDownActivity.this.i();
            }
        };
        this.e = new Runnable() { // from class: com.xiaomi.micolauncher.skills.countdown.view.-$$Lambda$CountDownActivity$2Sl9ASVpXcdvs_eb2znze-l4P04
            @Override // java.lang.Runnable
            public final void run() {
                CountDownActivity.this.j();
            }
        };
        this.f = new $$Lambda$mi3xP8oaGjOO7xSiyEy7jhHp1bc(this);
        this.n = -1;
        int intExtra = getIntent().getIntExtra(CountDownModel.KEY_COUNTDOWN_SECONDS, 0);
        a(a(intExtra));
        b(intExtra);
        e();
        c();
        SpeechManager.getInstance().uiShow(QueryLatency.AlarmCountDown);
    }

    @NotNull
    private CountDownModel.CountDownStatus a(int i) {
        CountDownModel.CountDownStatus status = CountDownModel.getInstance().getStatus();
        L.countdown.d("%s count down status %s", a, status);
        switch (status) {
            case STOP:
                if (i > 0) {
                    CountDownModel.getInstance().start(i);
                    break;
                }
                break;
            case FIRING:
                a();
                break;
        }
        return status;
    }

    private void a(CountDownModel.CountDownStatus countDownStatus) {
        if (countDownStatus == CountDownModel.CountDownStatus.STOP || countDownStatus == CountDownModel.CountDownStatus.START) {
            FakePlayer fakePlayer = this.v;
            if (fakePlayer != null) {
                fakePlayer.stop();
                L.countdown.e("fake player %s already created %s", this.v, this);
            }
            this.v = new FakePlayer(AudioSource.AUDIO_SOURCE_UI) { // from class: com.xiaomi.micolauncher.skills.countdown.view.CountDownActivity.1
                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
                public void postStop(AudioSource audioSource) {
                    super.postStop(audioSource);
                    L.countdown.i("finish on postStop, %s, source %s", CountDownActivity.this, audioSource);
                    if (audioSource != AudioSource.AUDIO_SOURCE_ALARM) {
                        CountDownActivity.this.b();
                    }
                }

                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                public void suspend(AudioSource audioSource) {
                    super.suspend(audioSource);
                    L.countdown.i("ignore on suspend %s, source %s", CountDownActivity.this, audioSource);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                public void background(AudioSource audioSource) {
                    super.background(audioSource);
                    L.countdown.i("finish on background %s, source %s", CountDownActivity.this, audioSource);
                }

                @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
                public void forceStop(AudioSource audioSource) {
                    super.forceStop(audioSource);
                    L.countdown.i("finish on forceStop %s, source %s", CountDownActivity.this, audioSource);
                    CountDownActivity.this.b();
                }
            };
            this.v.start();
            L.countdown.i("start fake player");
        }
    }

    private void b(int i) {
        this.h = findViewById(R.id.countdownLayout);
        this.h.setOnClickListener(this);
        this.l = (Button) findViewById(R.id.cancelCountdownButton);
        this.l.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.countdown.view.-$$Lambda$CountDownActivity$75DYWIn7MOg7twDThXxxDj8fHZE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountDownActivity.this.c(view);
            }
        });
        ((Button) findViewById(R.id.okCountdownButton)).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.countdown.view.-$$Lambda$CountDownActivity$fvsjI9DL6fsjrkuPPM-W3Wenaoo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountDownActivity.this.b(view);
            }
        });
        this.i = findViewById(R.id.layoutStart);
        this.j = findViewById(R.id.layoutStop);
        this.o = (TextView) findViewById(R.id.textViewTips);
        this.o.setVisibility(4);
        this.k = (TextView) findViewById(R.id.textViewCountDown);
        this.p = (LottieAnimationView) findViewById(R.id.animation);
        this.l.setVisibility(0);
        this.p.setVisibility(8);
        this.o.setVisibility(0);
        this.o.setText(c(i));
        this.t = (ImageView) findViewById(R.id.back_iv);
        this.t.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.countdown.view.-$$Lambda$CountDownActivity$8p77j9m76CDQwIUomOJqS88DYWk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountDownActivity.this.a(view);
            }
        });
    }

    public /* synthetic */ void c(View view) {
        onCancelCountDownClicked();
    }

    public /* synthetic */ void b(View view) {
        onOkClicked();
    }

    public /* synthetic */ void a(View view) {
        finish();
    }

    private String c(int i) {
        int i2 = i / 60;
        int i3 = i % 60;
        StringBuilder sb = new StringBuilder();
        if (i2 > 0) {
            sb.append(i2);
            sb.append("分钟");
        }
        if (i3 > 0) {
            sb.append(i3);
            sb.append("秒");
        }
        sb.append("  倒计时");
        return sb.toString();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        e();
        a(intent.getIntExtra(CountDownModel.KEY_COUNTDOWN_SECONDS, 0));
        SpeechManager.getInstance().uiShow(QueryLatency.AlarmCountDown);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.u.a();
        FakePlayer fakePlayer = this.v;
        if (fakePlayer != null) {
            fakePlayer.stop();
            L.countdown.d("destroying fake player %s %s", this.v, this);
        }
        CountDownModel.CountDownStatus status = CountDownModel.getInstance().getStatus();
        L.countdown.d("onDestroy : count down status %s", status);
        if (status == CountDownModel.CountDownStatus.FIRING) {
            CountDownModel.getInstance().clear();
            CountDownModel.getInstance().clearAlarmItemAndReport();
        }
        this.c.removeCallbacks(this.f);
        d();
        a aVar = this.r;
        if (aVar != null) {
            aVar.cancel();
        }
        c cVar = this.s;
        if (cVar != null) {
            cVar.cancel();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVoipStartEvent(VoipStartEvent voipStartEvent) {
        L.countdown.i("onVoipStartEvent,wrappedFinish");
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDismissCountDownEvent(DismissCountDownUiEvent dismissCountDownUiEvent) {
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountDownCancelEvent(CountDownCancelEvent countDownCancelEvent) {
        g();
        this.p.pauseAnimation();
        this.c.postDelayed(new $$Lambda$mi3xP8oaGjOO7xSiyEy7jhHp1bc(this), FINISH_DELAY);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountDownFireEvent(CountDownFireEvent countDownFireEvent) {
        e();
        a();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    private void a() {
        this.u.a((int) R.raw.timer_default);
        this.c.postDelayed(this.f, RING_DURATION);
        CameraDetectionController.getManager().showSupportGestureNotification(this);
    }

    public void b() {
        DebugHelper.printStackTrace("wrappedFinish");
        finish();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        CountDownModel.CountDownStatus status = CountDownModel.getInstance().getStatus();
        L.base.d("onKeyDown, keyCode:%s event:%s, status is ", Integer.valueOf(i), keyEvent, status);
        if (keyEvent.getAction() != 0 || (status != CountDownModel.CountDownStatus.FIRING && status != CountDownModel.CountDownStatus.STOP)) {
            return super.onKeyDown(i, keyEvent);
        }
        b();
        return true;
    }

    protected void onCancelCountDownClicked() {
        CountDownModel.getInstance().cancel();
        b();
    }

    protected void onOkClicked() {
        b();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (CountDownModel.getInstance().getStatus() == CountDownModel.CountDownStatus.STOP) {
            return;
        }
        if (this.b == d.CLOSE) {
            this.c.removeCallbacks(this.d);
            this.c.postDelayed(this.d, 7000L);
            return;
        }
        this.o.clearAnimation();
        this.o.setVisibility(4);
        this.c.removeCallbacks(this.e);
        this.c.postDelayed(this.d, 7000L);
        this.l.setVisibility(0);
        this.b = d.CLOSE;
    }

    private void c() {
        long j;
        this.q = new Timer();
        this.r = new a(getApplicationContext());
        if (DebugHelper.isDebugVersion()) {
            j = PERIOD_DEBUG;
        } else {
            j = PERIOD_RELEASE;
        }
        this.q.schedule(this.r, 0L, j);
    }

    /* loaded from: classes3.dex */
    public static class a extends TimerTask {
        private Context a;

        a(Context context) {
            this.a = context;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            UserBehaviorProxy.setUserBehavior(this.a, "countdown");
        }
    }

    private void d() {
        this.q.cancel();
    }

    private void e() {
        this.i.setVisibility(4);
        this.j.setVisibility(4);
        switch (CountDownModel.getInstance().getStatus()) {
            case STOP:
            case FIRING:
                g();
                this.j.setVisibility(0);
                return;
            case START:
                f();
                this.i.setVisibility(0);
                return;
            default:
                return;
        }
    }

    private void f() {
        Timer timer = this.g;
        if (timer != null) {
            timer.cancel();
            this.g = null;
        }
        runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.countdown.view.-$$Lambda$CountDownActivity$kRd590uB9kaqgkq4XGzCyP2m7f8
            @Override // java.lang.Runnable
            public final void run() {
                CountDownActivity.this.h();
            }
        });
        this.g = new Timer();
        this.s = new c(this);
        this.g.schedule(this.s, 0L, COUNT_DOWN_TEXT_UPDATE_INTERVAL);
    }

    /* loaded from: classes3.dex */
    public static class c extends TimerTask {
        private WeakReference<CountDownActivity> a;

        public c(CountDownActivity countDownActivity) {
            this.a = new WeakReference<>(countDownActivity);
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            final CountDownActivity countDownActivity = this.a.get();
            if (countDownActivity != null) {
                countDownActivity.getClass();
                countDownActivity.runOnUiThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.countdown.view.-$$Lambda$CountDownActivity$c$Pl6drzc4jQLthn0h27pEOcdVu5Q
                    @Override // java.lang.Runnable
                    public final void run() {
                        CountDownActivity.this.h();
                    }
                });
            }
        }
    }

    private void g() {
        Timer timer = this.g;
        if (timer != null) {
            timer.cancel();
            this.g = null;
        }
        h();
    }

    public void h() {
        long remainSeconds = CountDownModel.getInstance().getRemainSeconds();
        if (remainSeconds < 0) {
            remainSeconds = 0;
        }
        long j = remainSeconds / 3600;
        long j2 = (remainSeconds - (3600 * j)) / 60;
        long j3 = remainSeconds % 60;
        if (j == 0) {
            this.k.setText(String.format("%s:%s", TimeCalculator.completedString(j2), TimeCalculator.completedString(j3)));
            this.k.setTextSize(150.0f);
            return;
        }
        this.k.setText(String.format("%s:%s:%s", TimeCalculator.completedString(j), TimeCalculator.completedString(j2), TimeCalculator.completedString(j3)));
        this.k.setTextSize(100.0f);
    }

    public void i() {
        k();
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim_in);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.skills.countdown.view.CountDownActivity.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (CountDownActivity.this.b == d.OPEN) {
                    CountDownActivity.this.c.postDelayed(CountDownActivity.this.e, 7000L);
                }
            }
        });
        this.b = d.OPEN;
        this.o.setVisibility(0);
        this.o.startAnimation(loadAnimation);
        this.l.setVisibility(4);
    }

    public void j() {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim_out);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.skills.countdown.view.CountDownActivity.3
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                if (CountDownActivity.this.b == d.OPEN) {
                    CountDownActivity.this.i();
                }
            }
        });
        this.o.startAnimation(loadAnimation);
    }

    private void k() {
        if (!ContainerUtil.isEmpty(this.m) && this.o != null) {
            int i = this.n;
            Random random = new Random();
            do {
                this.n = random.nextInt(this.m.size());
                if (i != this.n) {
                    break;
                }
            } while (this.m.size() != 1);
            this.o.setText(this.m.get(this.n).text);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGestureControlEvent(GestureControlEvent gestureControlEvent) {
        if (SystemSetting.getKeyGestureControlEnable()) {
            CountDownModel.CountDownStatus status = CountDownModel.getInstance().getStatus();
            GestureInfoEvent.GestureType gestureType = gestureControlEvent.gestureType;
            L.camera2.i("CountDown receive gesture control event %s,status=%s", gestureType.toString(), status);
            if (gestureType == GestureInfoEvent.GestureType.Stop && status == CountDownModel.CountDownStatus.FIRING) {
                GestureToast.showGesture(this, gestureType, getString(R.string.gesture_pause));
                b();
            }
        }
    }

    /* loaded from: classes3.dex */
    public class b implements BasePlayer.PlayerListener {
        private final AudioPolicyClient b;
        private BasePlayer c = null;

        @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
        public void onComplete(BasePlayer basePlayer) {
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
        public void onError(BasePlayer basePlayer, int i, int i2) {
        }

        b() {
            CountDownActivity.this = r3;
            this.b = new AudioPolicyClient(AudioSource.AUDIO_SOURCE_ALARM) { // from class: com.xiaomi.micolauncher.skills.countdown.view.CountDownActivity.b.1
                @Override // com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient
                public void onNotify(AudioPolicyClient.PlayControl playControl, AudioSource audioSource) {
                    L.countdown.d("sound delegate onNotify play control %s, source %s, activity %s", playControl, audioSource, CountDownActivity.this);
                    super.onNotify(playControl, audioSource);
                    switch (playControl) {
                        case FORCE_STOP:
                        case NOT_ALLOW:
                        case SUSPEND:
                            if (b.this.c != null) {
                                b.this.c.stop();
                            }
                            b.this.b();
                            return;
                        default:
                            return;
                    }
                }
            };
        }

        void a() {
            L.countdown.d("onDestroy player %s, activity %s", this.c, CountDownActivity.this);
            BasePlayer basePlayer = this.c;
            if (basePlayer != null) {
                basePlayer.stop();
            }
            this.b.requestFinish();
        }

        public void a(@RawRes int i) {
            this.b.requestPlayAsync();
            if (this.c == null) {
                this.c = new BasePlayer(BasePlayer.StreamType.STREAM_TYPE_ALARM);
                this.c.setListener(this);
            }
            this.c.setDataSource(i, CountDownActivity.this);
            this.c.setLooping(true);
            this.c.prepareAsync();
            DebugHelper.printStackTrace("playRing" + this);
        }

        @Override // com.xiaomi.micolauncher.common.player.base.BasePlayer.PlayerListener
        public void onPrepared(BasePlayer basePlayer) {
            L.countdown.i("onPrepared, start play");
            basePlayer.start();
        }

        void b() {
            CountDownModel.CountDownStatus status = CountDownModel.getInstance().getStatus();
            L.countdown.i("status when onStopped %s, activity %s", status, CountDownActivity.this);
            if (status != CountDownModel.CountDownStatus.START) {
                CountDownActivity.this.b();
            }
        }
    }
}
