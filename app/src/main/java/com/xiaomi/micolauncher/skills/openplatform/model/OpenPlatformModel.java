package com.xiaomi.micolauncher.skills.openplatform.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import com.elvishew.xlog.Logger;
import com.xiaomi.ai.api.FullScreenTemplate;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.api.model.Directive;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.event.WifiConnectivityChangedEvent;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.player.wrapped.BizControlCallback;
import com.xiaomi.micolauncher.common.player.wrapped.BizPlayer;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.statemodel.StateModel;
import com.xiaomi.micolauncher.common.statemodel.StateModelHelper;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import com.xiaomi.micolauncher.skills.countdown.model.CountDownModel;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.FarewellEvent;
import com.xiaomi.micolauncher.skills.openplatform.controller.uievent.OpenPlatformChatListUpdateEvent;
import com.xiaomi.micolauncher.skills.openplatform.model.SkillChatItem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class OpenPlatformModel implements Handler.Callback, MultiAudioPlayer.PlayerListener {
    private static volatile OpenPlatformModel a;
    private Drawable d;
    @GuardedBy("eventBusRegisterLock")
    private volatile MultiAudioPlayer l;
    private String m;
    private boolean n;
    @GuardedBy("directives")
    private final List<FullScreenTemplate.Message> c = new ArrayList();
    private final AtomicBoolean f = new AtomicBoolean(false);
    private final AtomicBoolean g = new AtomicBoolean(false);
    private boolean h = true;
    private final Object i = new Object();
    private AtomicBoolean j = new AtomicBoolean(false);
    private LinkedList<Directive.PlayerSource> o = new LinkedList<>();
    private List<SkillChatItem> b = new ArrayList();
    private Handler e = new Handler(Looper.getMainLooper(), this);
    private BizPlayer<String> k = new BizPlayer<>(AudioSource.AUDIO_SOURCE_OPEN_PLATFORM, new a());

    /* loaded from: classes3.dex */
    public enum QuitType {
        NLP_QUIET,
        NLP_AND_TTS_RESPONSE,
        DO_NOTHING
    }

    private OpenPlatformModel() {
    }

    public static OpenPlatformModel getInstance() {
        if (a == null) {
            synchronized (OpenPlatformModel.class) {
                if (a == null) {
                    a = new OpenPlatformModel();
                }
            }
        }
        return a;
    }

    public void setIsFarewell(boolean z) {
        this.g.set(z);
    }

    public boolean isFarewellPending() {
        return this.g.get();
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        L.openplatform.v("model handle thread %s %s", Thread.currentThread().getName(), Long.valueOf(Thread.currentThread().getId()));
        if (message.what != 1) {
            return false;
        }
        FullScreenTemplate.Message message2 = null;
        synchronized (this.c) {
            if (ContainerUtil.hasData(this.c)) {
                message2 = this.c.get(0);
            }
        }
        if (message2 == null) {
            return true;
        }
        if (message2.getType() == FullScreenTemplate.MessageType.TTS && message2.getTts().isPresent()) {
            AsrTtsCard.getInstance().dismissForce();
            FullScreenTemplate.TTS tts = message2.getTts().get();
            String str = tts.getUrl().isPresent() ? tts.getUrl().get() : "";
            String str2 = tts.getDisplayText().isPresent() ? tts.getDisplayText().get() : "";
            if (TextUtils.isEmpty(str2)) {
                str2 = tts.getText().isPresent() ? tts.getText().get() : "";
            }
            if (ContainerUtil.isEmpty(str2)) {
                str2 = this.m;
            }
            L.openplatform.d("OpenPlatformModel text=%s to request play audio url=%s", str2, str);
            b(new SkillChatItem(SkillChatItem.Type.RECV, str2, message2.getAvatar()));
            if (!(TextUtils.isEmpty(str) || this.l == null || this.k == null)) {
                L.openplatform.d("OpenPlatformModel isplaying %b ,ispreparing %b,play sound %s", Boolean.valueOf(this.l.isPlaying()), Boolean.valueOf(this.l.isPreparing()), str);
                if (this.l.isPlaying()) {
                    L.openplatform.d("OpenPlatformModel offer ... ");
                    this.o.offer(new Directive.PlayerSource("audio", str));
                } else {
                    L.openplatform.d("OpenPlatformModel soundPlayer play url ... ");
                    this.l.playUrl(str);
                }
            }
        }
        synchronized (this.c) {
            if (ContainerUtil.hasData(this.c)) {
                this.c.remove(0);
            }
        }
        return true;
    }

    public /* synthetic */ void g() {
        this.b.clear();
    }

    public void clear() {
        this.e.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.openplatform.model.-$$Lambda$OpenPlatformModel$XEw3Jw6m3QO6b2OFrP6vrx9SfoQ
            @Override // java.lang.Runnable
            public final void run() {
                OpenPlatformModel.this.g();
            }
        });
        synchronized (this.c) {
            this.c.clear();
        }
    }

    public void clearMessageList() {
        synchronized (this.c) {
            this.c.clear();
        }
        this.o.clear();
    }

    public void reset() {
        clear();
        setSkillIcon(null);
        this.g.set(false);
        this.j.set(false);
        if (this.l != null) {
            this.l.stop();
            this.l.release();
            this.l = null;
        }
    }

    public void playTtsUrl(String str) {
        if (this.l != null) {
            this.l.playUrl(str);
        }
    }

    public void addChatItem(final SkillChatItem skillChatItem) {
        if (skillChatItem != null) {
            L.openplatform.v("chat time type [%s] content [%s]", skillChatItem.getType(), skillChatItem.getContent());
            a();
            this.e.post(new Runnable() { // from class: com.xiaomi.micolauncher.skills.openplatform.model.-$$Lambda$OpenPlatformModel$gr-_gZyIJWqewfD1LmIU5D1pTh8
                @Override // java.lang.Runnable
                public final void run() {
                    OpenPlatformModel.this.b(skillChatItem);
                }
            });
        }
    }

    /* renamed from: a */
    public void b(SkillChatItem skillChatItem) {
        ThreadUtil.verifyMainThread();
        if (skillChatItem != null) {
            this.b.add(skillChatItem);
            notifyUpdateList();
        }
    }

    public void addMessage(List<FullScreenTemplate.Message> list) {
        L.openplatform.d("OpenPlatform add Message");
        if (ContainerUtil.hasData(list)) {
            a();
            synchronized (this.c) {
                Logger logger = L.openplatform;
                logger.d("add directive and process: .list_size=" + list.size() + " total:" + this.c.size());
                this.c.clear();
                this.c.addAll(list);
            }
        }
    }

    private void a() {
        synchronized (this.i) {
            if (!EventBusRegistry.getEventBus().isRegistered(this)) {
                EventBusRegistry.getEventBus().register(this);
            }
        }
    }

    public void notifyUpdateList() {
        EventBusRegistry.getEventBus().post(new OpenPlatformChatListUpdateEvent());
    }

    public List<SkillChatItem> getChatList() {
        ThreadUtil.verifyMainThread();
        return ContainerUtil.hasData(this.b) ? new ArrayList(this.b) : new ArrayList();
    }

    public void prepareQuitSkill(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.g.set(true);
            L.openplatform.i("prepare quit %s", str);
            SpeechManager.getInstance().ttsRequest(str);
            return;
        }
        synchronized (this.c) {
            L.openplatform.i("quit, directive left %s", Integer.valueOf(ContainerUtil.getSize(this.c)));
        }
        b();
    }

    private void b() {
        quit(false, true);
    }

    public void setDefaultText(String str) {
        this.m = str;
    }

    public String getDefaultText() {
        return this.m;
    }

    public void setDefaultText(Context context, int i) {
        if (TextUtils.isEmpty(this.m)) {
            this.m = context.getString(i);
        }
    }

    @Deprecated
    public void quit(boolean z, boolean z2) {
        quit(z ? QuitType.NLP_AND_TTS_RESPONSE : QuitType.DO_NOTHING, z2);
    }

    public void quit(QuitType quitType, boolean z) {
        if (isStarted()) {
            if (!WiFiUtil.isWifiConnected(MicoApplication.getGlobalContext())) {
                this.n = true;
                return;
            }
            switch (quitType) {
                case NLP_QUIET:
                    setQuitByVoice(false);
                    SpeechManager.getInstance().nlpRequest("退出", true);
                    SpeechManager.getInstance().setNewSession();
                    break;
                case NLP_AND_TTS_RESPONSE:
                    setQuitByVoice(true);
                    SpeechManager.getInstance().nlpRequest("退出", true);
                    SpeechManager.getInstance().setNewSession();
                    break;
            }
            L.openplatform.i("OpenPlatformModel quit, type=%s, notifyUiQuit=%s", quitType, Boolean.valueOf(z));
            d();
            SpeechManager.getInstance().stopTts();
            if (z) {
                EventBusRegistry.getEventBus().post(new FarewellEvent(""));
            }
            reset();
            StateModel.getInstance().stop(c());
            SpeechManager.getInstance().setNewSession();
            BizPlayer<String> bizPlayer = this.k;
            if (bizPlayer != null) {
                bizPlayer.stop();
            }
        }
    }

    public boolean isStarted() {
        return this.j.get();
    }

    @NotNull
    private StateModel.StateInfo c() {
        return StateModelHelper.fromSkillClass(OpenPlatformModel.class);
    }

    private void d() {
        synchronized (this.i) {
            if (EventBusRegistry.getEventBus().isRegistered(this)) {
                EventBusRegistry.getEventBus().unregister(this);
            }
        }
    }

    public void setQuitByVoice(boolean z) {
        this.h = z;
    }

    public boolean isQuitByVoice() {
        return this.h;
    }

    public void setSkillIcon(Drawable drawable) {
        this.d = drawable;
    }

    public Drawable getSkillIcon() {
        return this.d;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delayQuit(WifiConnectivityChangedEvent wifiConnectivityChangedEvent) {
        if (wifiConnectivityChangedEvent.connected && this.n) {
            quit(QuitType.NLP_QUIET, false);
            this.n = !this.n;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTtsPlayEndEvent(TtsPlayEndEvent ttsPlayEndEvent) {
        if (isStarted()) {
            if (this.l != null) {
                this.l.setPlaying(false);
            }
            String str = ttsPlayEndEvent.query;
            boolean z = this.g.get();
            L.openplatform.i("received TtsPlayEndEvent %s, is pending farewell=%s", str, Boolean.valueOf(z));
            if (z) {
                b();
            } else {
                processNext();
            }
        }
    }

    public void processNext() {
        this.e.removeMessages(1);
        synchronized (this.c) {
            boolean hasData = ContainerUtil.hasData(this.c);
            if (!this.j.get()) {
                L.openplatform.w("OpenPlatformModel ignore %s directives for not started", Integer.valueOf(ContainerUtil.getSize(this.c)));
                return;
            }
            L.openplatform.d("OpenPlatformModel process next hasMsg(%s), isMicOpen(%s)", Boolean.valueOf(hasData), Boolean.valueOf(e()));
            if (hasData) {
                this.e.sendEmptyMessage(1);
            }
        }
    }

    private boolean e() {
        return this.f.get();
    }

    public void openMic(boolean z) {
        this.f.set(z);
        L.openplatform.i("OpenPlatformModel openMic set=%s", Boolean.valueOf(z));
    }

    public void onStart() {
        L.openplatform.i("OpenPlatformModel onStart");
        this.j.set(true);
        BizPlayer<String> bizPlayer = this.k;
        if (bizPlayer != null) {
            bizPlayer.play(null);
        }
        if (this.l == null) {
            this.l = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_MUSIC, false);
            this.l.setListener(this);
        }
        StateModel.getInstance().start(c());
        this.n = false;
        CountDownModel.getInstance().notifyDismissUi();
    }

    @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
    public void onComplete() {
        if (!isStarted()) {
            L.openplatform.e("OpenPlatformModel unexpected player onComplete");
            return;
        }
        L.openplatform.i("OpenPlatformBizControlCallback on play complete");
        Directive.PlayerSource poll = this.o.poll();
        if (!(poll == null || this.l == null)) {
            L.openplatform.i("OpenPlatformBizControlCallback on play complete %s %s", poll.type, poll.content);
            a(poll);
        }
        f();
    }

    private void a(Directive.PlayerSource playerSource) {
        char c;
        String str = playerSource.type;
        int hashCode = str.hashCode();
        if (hashCode != 3556653) {
            if (hashCode == 93166550 && str.equals("audio")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("text")) {
                c = 0;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                this.l.playTts(playerSource.content);
                return;
            case 1:
                this.l.playUrl(playerSource.content);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
    public void onError(int i, Exception exc) {
        if (!isStarted()) {
            L.openplatform.e("OpenPlatformModel unexpected player onError");
            return;
        }
        L.openplatform.e("OpenPlatformModel MultiAudioPlayer error", exc);
        f();
    }

    private void f() {
        boolean isEmpty;
        synchronized (this.c) {
            isEmpty = ContainerUtil.isEmpty(this.c);
        }
        if (isEmpty) {
            if (!e()) {
                b();
            } else {
                SpeechManager.getInstance().setWakeup();
            }
        } else if (isStarted()) {
            processNext();
        } else {
            L.openplatform.e("OpenPlatformModel onComplete : not started");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends BizControlCallback<String> {
        private a() {
            OpenPlatformModel.this = r1;
        }

        /* renamed from: a */
        public void onForbidden(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, String str) {
            L.openplatform.i("OpenPlatformModel#OpenPlatformBizControlCallback onForbidden");
            if (OpenPlatformModel.this.j.get()) {
                OpenPlatformModel.this.l.stop();
            }
            OpenPlatformModel.this.quit(QuitType.NLP_AND_TTS_RESPONSE, true);
        }

        /* renamed from: b */
        public void onBackground(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, String str) {
            L.openplatform.i("OpenPlatformModel#OpenPlatformBizControlCallback onBackground");
            if (OpenPlatformModel.this.j.get()) {
                OpenPlatformModel.this.l.stop();
            }
        }

        /* renamed from: c */
        public void onForeground(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, String str) {
            Logger logger = L.openplatform;
            logger.i("OpenPlatformModel#OpenPlatformBizControlCallback onForeground " + str);
            synchronized (OpenPlatformModel.this.c) {
                if (ContainerUtil.hasData(OpenPlatformModel.this.c) && OpenPlatformModel.this.j.get() && !OpenPlatformModel.this.l.isPlaying()) {
                    L.openplatform.i("OpenPlatformModel#OpenPlatformBizControlCallback process next");
                    OpenPlatformModel.this.processNext();
                } else if (OpenPlatformModel.this.j.get() && str != null) {
                    L.openplatform.i("OpenPlatformModel#OpenPlatformBizControlCallback continue %s", str);
                    OpenPlatformModel.this.l.playUrl(str);
                }
            }
        }

        /* renamed from: d */
        public void onResume(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, String str) {
            Logger logger = L.openplatform;
            logger.i("OpenPlatformModel#OpenPlatformBizControlCallback onResume " + str);
            if (OpenPlatformModel.this.j.get() && str != null) {
                OpenPlatformModel.this.l.playUrl(str);
            }
        }

        /* renamed from: e */
        public void onAllow(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, String str) {
            L.openplatform.i("OpenPlatformModel#OpenPlatformBizControlCallback onAllow bizContext %s", str);
        }

        /* renamed from: f */
        public void onIdle(AudioPolicyClient.PlayControl playControl, AudioSource audioSource, String str) {
            Logger logger = L.openplatform;
            logger.i("OpenPlatformModel#OpenPlatformBizControlCallback onIdle " + str);
            if (OpenPlatformModel.this.j.get() && str != null) {
                OpenPlatformModel.this.l.playUrl(str);
            }
        }
    }
}
