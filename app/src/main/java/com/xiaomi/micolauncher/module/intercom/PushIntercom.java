package com.xiaomi.micolauncher.module.intercom;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mico.base.utils.ScreenUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.ApiHelper;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.push.MicoNotification;
import com.xiaomi.micolauncher.common.push.NotificationHelper;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.intercom.IntercomReceiveModel;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import com.xiaomi.micolauncher.skills.personalize.manager.StreamHelper;
import com.xiaomi.micolauncher.skills.voip.model.VoipModel;
import com.xiaomi.micolauncher.skills.voip.model.VoipStopEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class PushIntercom {
    private static volatile PushIntercom a;
    private volatile boolean d;
    private Context f;
    private BaseExoPlayer g;
    private FakePlayer h;
    private a k;
    private boolean b = false;
    private boolean c = false;
    private volatile boolean e = false;
    private volatile List<IntercomReceiveModel.a> i = new ArrayList();
    private final List<IntercomReceiveModel> j = Collections.synchronizedList(new ArrayList());
    private final Runnable l = new Runnable() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$PushIntercom$sVpiZl9vqFlKCepT9S2FhBVxsU0
        @Override // java.lang.Runnable
        public final void run() {
            PushIntercom.this.e();
        }
    };

    /* loaded from: classes3.dex */
    public interface a {
        void onAllPushProcessComplete();
    }

    private PushIntercom() {
        EventBusRegistry.getEventBus().register(this);
    }

    public static PushIntercom getInstance() {
        if (a == null) {
            synchronized (PushIntercom.class) {
                if (a == null) {
                    a = new PushIntercom();
                }
            }
        }
        return a;
    }

    public void setContext(Context context) {
        this.f = context;
    }

    public void setListener(a aVar) {
        this.k = aVar;
    }

    public void setNightModeNoDisturbing(boolean z) {
        this.b = z;
    }

    public void setShowNotification(boolean z) {
        this.c = z;
    }

    public void setProcessIntercomPlay(boolean z) {
        this.d = z;
    }

    public void setAudioStreams(List<IntercomReceiveModel.a> list) {
        this.i = list;
    }

    public List<IntercomReceiveModel> getPushModelList() {
        return this.j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0045, code lost:
        if (r12.equals(com.xiaomi.ai.api.AIApiConstants.AudioPlayer.Play) == false) goto L_0x005c;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01f4 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean addPushList(com.xiaomi.ai.api.General.Push r35) {
        /*
            Method dump skipped, instructions count: 642
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.intercom.PushIntercom.addPushList(com.xiaomi.ai.api.General$Push):boolean");
    }

    private void a() {
        Context context = this.f;
        if (context == null) {
            throw new IllegalArgumentException("should call setContext first");
        } else if (this.g == null) {
            this.g = new BaseExoPlayer(context, AudioSource.AUDIO_SOURCE_NULL, BasePlayer.StreamType.STREAM_TYPE_MUSIC, true) { // from class: com.xiaomi.micolauncher.module.intercom.PushIntercom.1
                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer
                protected void onStarted() {
                    setVolume(1.0f);
                }
            };
            this.g.setListener(new BaseExoPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.module.intercom.PushIntercom.2
                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
                public void onPrepared(Player player) {
                    player.start();
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
                public void onComplete(Player player) {
                    L.push.d("push intercom current audio play onComplete，play next audio");
                    PushIntercom.this.d();
                }

                @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
                public void onError(Player player, int i, Exception exc) {
                    L.push.e("on push play errorCode=%d, try play next audio", Integer.valueOf(i), exc);
                    PushIntercom.this.d();
                }
            });
            this.h = new FakePlayer(AudioSource.AUDIO_SOURCE_PUSH) { // from class: com.xiaomi.micolauncher.module.intercom.PushIntercom.3
            };
        }
    }

    public void rePlay() {
        if (this.g != null) {
            L.push.i("%s rePlay", "PushIntercom");
            this.g.restart();
        }
        FakePlayer fakePlayer = this.h;
        if (fakePlayer != null) {
            fakePlayer.start();
        }
    }

    public void pausePlay() {
        if (this.g != null) {
            L.push.i("%s pausePlay", "PushIntercom");
            this.g.pause();
        }
        FakePlayer fakePlayer = this.h;
        if (fakePlayer != null) {
            fakePlayer.pause();
        }
    }

    public void stopPlay() {
        this.d = false;
        BaseExoPlayer baseExoPlayer = this.g;
        if (baseExoPlayer != null) {
            baseExoPlayer.stop();
            this.g.release();
            this.g.setListener((BaseExoPlayer.PlayerListener) null);
            this.g = null;
        }
        FakePlayer fakePlayer = this.h;
        if (fakePlayer != null) {
            fakePlayer.stop();
            this.h.release();
            this.h = null;
        }
    }

    public void processPush() {
        Log.d("MICO.push", "receive new push from aivs");
        a();
        c();
    }

    /* renamed from: b */
    public void e() {
        this.d = false;
        c();
    }

    private void c() {
        if (this.d) {
            L.push.d("%s processPush wait for current push done", "PushIntercom");
            return;
        }
        boolean sleepStatus = SleepMode.getInstance().getSleepStatus();
        if (this.b && sleepStatus) {
            L.push.w("%s processNextPush ignore cause of nightModeNoDisturbing=true, isSleepModeNow=true", "PushIntercom");
            this.d = false;
            a aVar = this.k;
            if (aVar != null) {
                aVar.onAllPushProcessComplete();
            }
        } else if (this.j.isEmpty()) {
            L.push.w("%s processPush allover done", "PushIntercom");
            this.d = false;
            a aVar2 = this.k;
            if (aVar2 != null) {
                aVar2.onAllPushProcessComplete();
            }
        } else if (VoipModel.getInstance().isVoipActive()) {
            this.e = true;
            L.push.w("%s processNextPush ignore cause of voip is running!!!");
        } else {
            this.d = true;
            IntercomReceiveModel remove = this.j.remove(0);
            if (remove != null) {
                ScreenUtil.turnScreenOn(this.f);
                Intent intent = new Intent(this.f, IntercomActivity.class);
                intent.putExtra(IntercomActivity.EXTRA_PAGE_TYPE, 3);
                intent.putExtra(IntercomActivity.EXTRA_RECEIVE_MODEL, remove);
                ActivityLifeCycleManager.startActivityQuietly(intent);
                playAudioByPushModel(remove);
            }
        }
    }

    public void playAudioByPushModel(IntercomReceiveModel intercomReceiveModel) {
        this.i = intercomReceiveModel.getAudioStream();
        d();
    }

    public void d() {
        a();
        if (ContainerUtil.hasData(this.i)) {
            this.d = true;
            IntercomReceiveModel.a remove = this.i.remove(0);
            if (remove == null || TextUtils.isEmpty(remove.a())) {
                L.push.w("%s play current audioItem url=%s, try next audioItem", "PushIntercom", remove.a());
                d();
                return;
            }
            a(remove.b(), remove.c(), remove.a(), remove.d());
            return;
        }
        a aVar = this.k;
        if (aVar != null) {
            aVar.onAllPushProcessComplete();
        }
        L.push.w("%s play current audioItem is empty, try next pushInstruction after 2 seconds", "PushIntercom");
        autoProcessNextPush(2);
    }

    @SuppressLint({"CheckResult"})
    private void a(boolean z, boolean z2, String str, String str2) {
        StreamHelper.buildUrl(z, z2, str, str2).subscribeOn(MicoSchedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$PushIntercom$se2_EiC1Tik4ZnohiHCjZ5Dl9D4
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushIntercom.this.a((String) obj);
            }
        }, new Consumer() { // from class: com.xiaomi.micolauncher.module.intercom.-$$Lambda$PushIntercom$D3zjRvN8km4Z4GyM0VPGgAAXh_s
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushIntercom.this.b((Throwable) obj);
            }
        });
    }

    public /* synthetic */ void b(Throwable th) throws Exception {
        L.push.e("PushIntercom doAuth error try next audioItem", th);
        d();
    }

    public void a(String str) {
        L.push.i("PushIntercom onPlayResource url=%s", str);
        if (this.g == null) {
            L.push.e("PushIntercom onPlayResource player is null");
        } else if (!TextUtils.isEmpty(str)) {
            FakePlayer fakePlayer = this.h;
            if (fakePlayer != null) {
                fakePlayer.start();
            }
            this.g.setDataSource(str);
        } else {
            L.push.w("%s onPlayResource url is null, try next audioItem", "PushIntercom");
            d();
        }
    }

    public void autoProcessNextPush(int i) {
        ThreadUtil.postDelayedInMainThread(this.l, TimeUnit.SECONDS.toMillis(i));
    }

    public void cancelProcessNextPush() {
        L.push.e("PushIntercom cancelProcessNextPush");
        ThreadUtil.removeCallbacksInMainThread(this.l);
    }

    private void a(IntercomReceiveModel intercomReceiveModel) {
        String str;
        Context context = this.f;
        if (context != null) {
            Intent intent = new Intent(context, IntercomActivity.class);
            intent.putExtra(IntercomActivity.EXTRA_PAGE_TYPE, 3);
            intent.putExtra(IntercomActivity.EXTRA_RECEIVE_MODEL, intercomReceiveModel);
            PendingIntent activity = PendingIntent.getActivity(this.f, intercomReceiveModel.getNotificationId(), intent, 0);
            if (DebugHelper.isDebugInConfg()) {
                str = this.f.getString(R.string.intercom_notification_title) + "广播id:" + intercomReceiveModel.getNotificationId();
            } else {
                str = this.f.getString(R.string.intercom_notification_title);
            }
            MicoNotification.Builder builder = new MicoNotification.Builder(this.f);
            builder.setNotificationId(intercomReceiveModel.getNotificationId()).setSmallIcon(R.drawable.notice_intercom).setTitle(str).setText(this.f.getString(R.string.intercom_notification_msg, intercomReceiveModel.getFromStr())).setPendingIntent(activity);
            builder.build().show();
            return;
        }
        throw new IllegalArgumentException("should call setContext first");
    }

    public void cancelPushNotificationById(Context context, int i) {
        NotificationHelper.cancelNotification(context, i);
    }

    @SuppressLint({"CheckResult"})
    public void uploadMsgStatus(String str, String str2) {
        ApiHelper.uploadIntercomMsgStatus(str, str2).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).subscribe($$Lambda$PushIntercom$u2wJ90h5GLqOwZwGe6lhkBfVFo.INSTANCE, $$Lambda$PushIntercom$Vuug5J8S3R8NA_tTmlNVTtRokRY.INSTANCE);
    }

    public static /* synthetic */ void a(ResponseBody responseBody) throws Exception {
        L.push.d("PushIntercom uploadMsgStatus onSuccess");
    }

    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.push.e("PushIntercom uploadMsgStatus error", th);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVoipStopEvent(VoipStopEvent voipStopEvent) {
        if (this.e) {
            L.push.d("%s onVoipStopEvent, last push is interrupt by voip, so resume play after hang up voip", "PushIntercom");
            this.e = false;
            autoProcessNextPush(1);
        }
    }
}
