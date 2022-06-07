package com.xiaomi.micolauncher.common.player.policy;

import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.util.SparseArray;
import androidx.core.app.FrameMetricsAggregator;
import com.elvishew.xlog.Logger;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.policy.AudioPolicyClient;
import com.xiaomi.micolauncher.common.player.policy.IAudioPolicy;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.setting.utils.SleepMode;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class AudioPolicyService {
    private static volatile AudioPolicyService d;
    private final SparseArray<IAudioPolicy.AudioSourceProperty> b = new SparseArray<>();
    private final ArrayMap<IAudioPolicy.AudioSourcePriority, ArrayList<AudioPolicyClient>> c = new ArrayMap<>();
    private Handler a = new Handler(ThreadUtil.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.player.policy.-$$Lambda$AudioPolicyService$MXuD5573lwNJYnQYpxTIh0kELLY
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = AudioPolicyService.this.a(message);
            return a2;
        }
    });

    public static AudioPolicyService getInstance() {
        if (d == null) {
            synchronized (AudioPolicyService.class) {
                d = new AudioPolicyService();
            }
        }
        return d;
    }

    private AudioPolicyService() {
        this.b.put(1, new IAudioPolicy.AudioSourceProperty(1, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_SIX).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 257).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 1786).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 4).a(IAudioPolicy.SourceSuspendType.SOURCE_SUSPEND_TYPE_STOP_OTHER));
        this.b.put(128, new IAudioPolicy.AudioSourceProperty(128, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_SIX).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 385).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 1086).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 16).a(IAudioPolicy.SourceSuspendType.SOURCE_SUSPEND_TYPE_STOP_OTHER));
        this.b.put(4, new IAudioPolicy.AudioSourceProperty(4, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_FIVE).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW, 0).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND, 0).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND, 9).a(IAudioPolicy.AudioSourceProperty.a.ALLOW, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 0).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 64));
        this.b.put(2, new IAudioPolicy.AudioSourceProperty(2, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_FIVE).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW, 0).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND, 640).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND, 257).a(IAudioPolicy.AudioSourceProperty.a.ALLOW, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 0).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 64));
        this.b.put(8, new IAudioPolicy.AudioSourceProperty(8, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_TWO).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW, 0).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND, 0).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND, 0).a(IAudioPolicy.AudioSourceProperty.a.ALLOW, FrameMetricsAggregator.EVERY_DURATION).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 441).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 4).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 66));
        this.b.put(16, new IAudioPolicy.AudioSourceProperty(16, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_TWO).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW, 130).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND, 9).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND, 0).a(IAudioPolicy.AudioSourceProperty.a.ALLOW, 116).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 48).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 4).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 64));
        this.b.put(32, new IAudioPolicy.AudioSourceProperty(32, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_TWO).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW, 48).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND, 9).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND, 0).a(IAudioPolicy.AudioSourceProperty.a.ALLOW, 66).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 32).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 0).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 66));
        this.b.put(64, new IAudioPolicy.AudioSourceProperty(64, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_ONE).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW, 0).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND, 0).a(IAudioPolicy.AudioSourceProperty.a.ALLOW, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 0).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, 0).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 0));
        this.b.put(256, new IAudioPolicy.AudioSourceProperty(256, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_SIX).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 1).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL));
        this.b.put(512, new IAudioPolicy.AudioSourceProperty(512, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_SIX).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND, 0).a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 297).a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF, AnalyticsListener.EVENT_VIDEO_FRAME_PROCESSING_OFFSET).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, IAudioPolicy.AUDIO_SOURCE_TYPE_ALL));
        this.b.put(1024, new IAudioPolicy.AudioSourceProperty(1024, IAudioPolicy.AudioSourcePriority.AUDIO_SOURCE_PRIORITY_SIX).b(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF, 64).a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF, 64).a(IAudioPolicy.SourceSuspendType.SOURCE_SUSPEND_TYPE_STOP_OTHER));
    }

    public /* synthetic */ boolean a(Message message) {
        Logger logger = L.policy;
        logger.d("AudioPolicyService handleMessage.what=" + message.what);
        switch (message.what) {
            case 1:
                if (!(message.obj instanceof AudioPolicyClient)) {
                    return false;
                }
                f((AudioPolicyClient) message.obj);
                return false;
            case 2:
                if (!(message.obj instanceof AudioPolicyClient)) {
                    return false;
                }
                g((AudioPolicyClient) message.obj);
                return false;
            case 3:
                if (!(message.obj instanceof a)) {
                    return false;
                }
                a((a) message.obj);
                return false;
            case 4:
                try {
                    SpeechManager.getInstance().screenOff();
                } catch (IllegalStateException e) {
                    L.policy.d("processSourceChanged.exception=", e);
                }
                a((AudioSource) message.obj);
                return false;
            default:
                return false;
        }
    }

    private void a() {
        IAudioPolicy.AudioSourcePriority[] values = IAudioPolicy.AudioSourcePriority.values();
        for (IAudioPolicy.AudioSourcePriority audioSourcePriority : values) {
            ArrayList<AudioPolicyClient> arrayList = this.c.get(audioSourcePriority);
            if (arrayList != null) {
                Iterator<AudioPolicyClient> it = arrayList.iterator();
                while (it.hasNext()) {
                    AudioPolicyClient next = it.next();
                    L.policy.i("Clients[%s].source=%s, .status=%s", audioSourcePriority, next.b(), next.c());
                }
            }
        }
    }

    private AudioPolicyClient.PlayControl a(AudioPolicyClient audioPolicyClient, IAudioPolicy.RequestRet requestRet, AudioSource audioSource) {
        if (audioPolicyClient == null) {
            return AudioPolicyClient.PlayControl.IDLE;
        }
        L.policy.i("notify.audioPolicyClient(%d).source=%s, .ret=%s", Integer.valueOf(audioPolicyClient.hashCode()), audioPolicyClient.b(), requestRet);
        return audioPolicyClient.a(requestRet, audioSource);
    }

    private AudioPolicyClient.PlayControl a(AudioPolicyClient audioPolicyClient, IAudioPolicy.RequestRet requestRet, AudioPolicyClient audioPolicyClient2) {
        AudioSource audioSource = AudioSource.AUDIO_SOURCE_NULL;
        if (audioPolicyClient2 != null) {
            audioSource = audioPolicyClient2.b();
        }
        return a(audioPolicyClient, requestRet, audioSource);
    }

    private AudioPolicyClient.PlayControl a(AudioPolicyClient audioPolicyClient, IAudioPolicy.RequestRet requestRet) {
        return a(audioPolicyClient, requestRet, b());
    }

    private void c(AudioPolicyClient audioPolicyClient) {
        IAudioPolicy.AudioSourceProperty audioSourceProperty = this.b.get(audioPolicyClient.a());
        if (audioSourceProperty == null) {
            L.policy.e("checkAddClient.audioPolicyClient=null");
            return;
        }
        ArrayList<AudioPolicyClient> arrayList = this.c.get(audioSourceProperty.b);
        if (arrayList == null) {
            ArrayList<AudioPolicyClient> arrayList2 = new ArrayList<>();
            arrayList2.add(audioPolicyClient);
            this.c.put(audioSourceProperty.b, arrayList2);
        } else if (arrayList.size() > 0) {
            ArrayList arrayList3 = new ArrayList();
            Iterator<AudioPolicyClient> it = arrayList.iterator();
            while (it.hasNext()) {
                AudioPolicyClient next = it.next();
                if (next != null && next.b() == audioPolicyClient.b()) {
                    if (!audioPolicyClient.equals(next)) {
                        a(next, IAudioPolicy.RequestRet.REQUEST_RET_NOT_ALLOW);
                    }
                    arrayList3.add(next);
                }
            }
            if (arrayList3.size() > 0) {
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    AudioPolicyClient audioPolicyClient2 = (AudioPolicyClient) it2.next();
                    L.policy.d("checkAddClient remove client=%s(%d)", audioPolicyClient2.b(), Integer.valueOf(audioPolicyClient2.hashCode()));
                    arrayList.remove(audioPolicyClient2);
                }
            }
            if (!arrayList.contains(audioPolicyClient)) {
                arrayList.add(audioPolicyClient);
            }
        } else {
            arrayList.add(audioPolicyClient);
        }
    }

    private boolean d(AudioPolicyClient audioPolicyClient) {
        if (audioPolicyClient == null) {
            L.policy.e("checkRemoveClient.audioPolicyClient=null");
            return false;
        }
        IAudioPolicy.AudioSourceProperty audioSourceProperty = this.b.get(audioPolicyClient.a());
        if (audioSourceProperty == null) {
            L.policy.e("checkRemoveClient.asp=null");
            return false;
        }
        ArrayList<AudioPolicyClient> arrayList = this.c.get(audioSourceProperty.b);
        if (arrayList == null || !arrayList.contains(audioPolicyClient)) {
            return false;
        }
        if (audioPolicyClient.isReleased() || !audioPolicyClient.a(AudioSource.AUDIO_SOURCE_POWER_KEY)) {
            L.policy.d("checkRemoveClient remove client=%s(%d)", audioPolicyClient.b(), Integer.valueOf(audioPolicyClient.hashCode()));
            arrayList.remove(audioPolicyClient);
        }
        return true;
    }

    public boolean hasActiveAudioSource() {
        AudioPolicyClient b = b();
        return b != null && !b.getPauseStatus();
    }

    private AudioPolicyClient b() {
        for (IAudioPolicy.AudioSourcePriority audioSourcePriority : IAudioPolicy.AudioSourcePriority.values()) {
            ArrayList<AudioPolicyClient> arrayList = this.c.get(audioSourcePriority);
            if (arrayList != null) {
                Iterator<AudioPolicyClient> it = arrayList.iterator();
                while (it.hasNext()) {
                    AudioPolicyClient next = it.next();
                    if (next != null && next.c() == AudioPolicyClient.PlayControl.FOREGROUND) {
                        return next;
                    }
                }
                continue;
            }
        }
        return null;
    }

    private AudioPolicyClient c() {
        IAudioPolicy.AudioSourcePriority[] values = IAudioPolicy.AudioSourcePriority.values();
        int length = values.length;
        int i = 0;
        while (true) {
            AudioPolicyClient audioPolicyClient = null;
            if (i >= length) {
                return null;
            }
            ArrayList<AudioPolicyClient> arrayList = this.c.get(values[i]);
            if (arrayList != null) {
                Iterator<AudioPolicyClient> it = arrayList.iterator();
                while (it.hasNext()) {
                    AudioPolicyClient next = it.next();
                    if (next.c() == AudioPolicyClient.PlayControl.BACKGROUND) {
                        audioPolicyClient = next;
                    }
                }
                if (audioPolicyClient != null) {
                    return audioPolicyClient;
                }
            }
            i++;
        }
    }

    private AudioPolicyClient d() {
        IAudioPolicy.AudioSourcePriority[] values = IAudioPolicy.AudioSourcePriority.values();
        int length = values.length;
        int i = 0;
        while (true) {
            AudioPolicyClient audioPolicyClient = null;
            if (i >= length) {
                return null;
            }
            ArrayList<AudioPolicyClient> arrayList = this.c.get(values[i]);
            if (arrayList != null) {
                Iterator<AudioPolicyClient> it = arrayList.iterator();
                while (it.hasNext()) {
                    AudioPolicyClient next = it.next();
                    if (next != null && next.c() == AudioPolicyClient.PlayControl.SUSPEND) {
                        audioPolicyClient = next;
                    }
                }
                if (audioPolicyClient != null) {
                    return audioPolicyClient;
                }
            }
            i++;
        }
    }

    private ArrayList<AudioPolicyClient> e() {
        ArrayList<AudioPolicyClient> arrayList = new ArrayList<>(3);
        for (IAudioPolicy.AudioSourcePriority audioSourcePriority : IAudioPolicy.AudioSourcePriority.values()) {
            ArrayList<AudioPolicyClient> arrayList2 = this.c.get(audioSourcePriority);
            if (arrayList2 != null) {
                for (int size = arrayList2.size(); size > 0; size--) {
                    AudioPolicyClient audioPolicyClient = arrayList2.get(size - 1);
                    if (audioPolicyClient.c() == AudioPolicyClient.PlayControl.BACKGROUND) {
                        arrayList.add(audioPolicyClient);
                    }
                }
            }
        }
        return arrayList;
    }

    private ArrayList<AudioPolicyClient> f() {
        ArrayList<AudioPolicyClient> arrayList = new ArrayList<>(3);
        for (IAudioPolicy.AudioSourcePriority audioSourcePriority : IAudioPolicy.AudioSourcePriority.values()) {
            ArrayList<AudioPolicyClient> arrayList2 = this.c.get(audioSourcePriority);
            if (arrayList2 != null) {
                for (int size = arrayList2.size(); size > 0; size--) {
                    AudioPolicyClient audioPolicyClient = arrayList2.get(size - 1);
                    if (audioPolicyClient.c() == AudioPolicyClient.PlayControl.SUSPEND) {
                        arrayList.add(audioPolicyClient);
                    }
                }
            }
        }
        return arrayList;
    }

    private boolean a(IAudioPolicy.AudioSourceProperty audioSourceProperty, int i) {
        return (audioSourceProperty == null || (audioSourceProperty.a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW) & i) == 0) ? false : true;
    }

    private boolean b(IAudioPolicy.AudioSourceProperty audioSourceProperty, int i) {
        return (audioSourceProperty == null || (audioSourceProperty.a(IAudioPolicy.AudioSourceProperty.a.SUSPEND) & i) == 0) ? false : true;
    }

    private boolean c(IAudioPolicy.AudioSourceProperty audioSourceProperty, int i) {
        return (audioSourceProperty == null || (audioSourceProperty.a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND) & i) == 0) ? false : true;
    }

    private boolean d(IAudioPolicy.AudioSourceProperty audioSourceProperty, int i) {
        return (audioSourceProperty == null || (audioSourceProperty.a(IAudioPolicy.AudioSourceProperty.a.ALLOW) & i) == 0) ? false : true;
    }

    private boolean e(IAudioPolicy.AudioSourceProperty audioSourceProperty, int i) {
        return (audioSourceProperty == null || (audioSourceProperty.a(IAudioPolicy.AudioSourceProperty.a.NOT_ALLOW_SELF) & i) == 0) ? false : true;
    }

    private boolean f(IAudioPolicy.AudioSourceProperty audioSourceProperty, int i) {
        return (audioSourceProperty == null || (audioSourceProperty.a(IAudioPolicy.AudioSourceProperty.a.SUSPEND_SELF) & i) == 0) ? false : true;
    }

    private boolean g(IAudioPolicy.AudioSourceProperty audioSourceProperty, int i) {
        return (audioSourceProperty == null || (audioSourceProperty.a(IAudioPolicy.AudioSourceProperty.a.BACKGROUND_SELF) & i) == 0) ? false : true;
    }

    private void a(AudioSource audioSource, AudioSource audioSource2) {
        L.policy.d("notifySourceChanged: %s --> %s", audioSource, audioSource2);
        this.a.removeMessages(3);
        this.a.obtainMessage(3, new a(audioSource, audioSource2)).sendToTarget();
    }

    private void e(AudioPolicyClient audioPolicyClient) {
        if (audioPolicyClient != null) {
            int a2 = audioPolicyClient.a();
            Iterator<AudioPolicyClient> it = f().iterator();
            while (it.hasNext()) {
                AudioPolicyClient next = it.next();
                if (!(next == null || next == audioPolicyClient)) {
                    IAudioPolicy.AudioSourceProperty audioSourceProperty = this.b.get(next.a());
                    if (e(audioSourceProperty, a2)) {
                        d(next);
                        a(next, IAudioPolicy.RequestRet.REQUEST_RET_NOT_ALLOW, audioPolicyClient.b());
                    } else if (g(audioSourceProperty, a2)) {
                        return;
                    }
                }
            }
            Iterator<AudioPolicyClient> it2 = e().iterator();
            while (it2.hasNext()) {
                AudioPolicyClient next2 = it2.next();
                if (!(next2 == null || next2 == audioPolicyClient)) {
                    IAudioPolicy.AudioSourceProperty audioSourceProperty2 = this.b.get(next2.a());
                    if (e(audioSourceProperty2, a2)) {
                        d(next2);
                        a(next2, IAudioPolicy.RequestRet.REQUEST_RET_NOT_ALLOW, audioPolicyClient.b());
                    } else if (f(audioSourceProperty2, a2)) {
                        a(next2, IAudioPolicy.RequestRet.REQUEST_RET_SUSPEND, audioPolicyClient.b());
                    }
                }
            }
        }
    }

    private AudioPolicyClient.PlayControl a(AudioPolicyClient audioPolicyClient, AudioPolicyClient audioPolicyClient2) {
        if (audioPolicyClient == null || audioPolicyClient.equals(audioPolicyClient2)) {
            return AudioPolicyClient.PlayControl.NOT_ALLOW;
        }
        int a2 = audioPolicyClient2.a();
        int a3 = audioPolicyClient.a();
        IAudioPolicy.AudioSourceProperty audioSourceProperty = this.b.get(a2);
        IAudioPolicy.AudioSourceProperty audioSourceProperty2 = this.b.get(a3);
        if (audioSourceProperty == null || audioSourceProperty2 == null) {
            return AudioPolicyClient.PlayControl.NOT_ALLOW;
        }
        if (a(audioSourceProperty2, a2)) {
            return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_NOT_ALLOW);
        }
        if (b(audioSourceProperty2, a2)) {
            c(audioPolicyClient2);
            e(audioPolicyClient2);
            return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_SUSPEND);
        } else if (c(audioSourceProperty2, a2)) {
            c(audioPolicyClient2);
            e(audioPolicyClient2);
            return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_BACKGROUND);
        } else if (!d(audioSourceProperty2, a2)) {
            return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_NOT_ALLOW);
        } else {
            if (e(audioSourceProperty2, a2)) {
                a(audioPolicyClient, IAudioPolicy.RequestRet.REQUEST_RET_NOT_ALLOW, audioPolicyClient2.b());
                d(audioPolicyClient);
                c(audioPolicyClient2);
                e(audioPolicyClient2);
                return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_ALLOW);
            } else if (f(audioSourceProperty2, a2)) {
                a(audioPolicyClient, IAudioPolicy.RequestRet.REQUEST_RET_SUSPEND);
                c(audioPolicyClient2);
                return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_ALLOW);
            } else if (g(audioSourceProperty2, a2)) {
                a(audioPolicyClient, IAudioPolicy.RequestRet.REQUEST_RET_BACKGROUND);
                c(audioPolicyClient2);
                return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_ALLOW);
            } else {
                c(audioPolicyClient2);
                return a(audioPolicyClient2, IAudioPolicy.RequestRet.REQUEST_RET_ALLOW);
            }
        }
    }

    private synchronized AudioPolicyClient.PlayControl f(AudioPolicyClient audioPolicyClient) {
        AudioPolicyClient.PlayControl playControl;
        int a2 = audioPolicyClient.a();
        if (this.b.get(a2) == null) {
            L.policy.e("processRequest.error, source: %s, type: %s", audioPolicyClient.b(), Integer.valueOf(a2));
            return AudioPolicyClient.PlayControl.NOT_ALLOW;
        }
        L.policy.i("processRequest.audioPolicyClient(%d).source=%s, .type=%s", Integer.valueOf(audioPolicyClient.hashCode()), audioPolicyClient.b(), Integer.valueOf(a2));
        if (audioPolicyClient.d()) {
            FakeClient.getInstance().a(audioPolicyClient);
            return AudioPolicyClient.PlayControl.IDLE;
        }
        L.policy.i("DUMP_CLIENTS.before:");
        a();
        AudioPolicyClient b = b();
        AudioSource audioSource = AudioSource.AUDIO_SOURCE_NULL;
        if (b == null) {
            c(audioPolicyClient);
            playControl = a(audioPolicyClient, IAudioPolicy.RequestRet.REQUEST_RET_ALLOW);
        } else {
            audioSource = b.b();
            if (b.equals(audioPolicyClient)) {
                playControl = a(audioPolicyClient, IAudioPolicy.RequestRet.REQUEST_RET_ALLOW);
            } else if (e().contains(audioPolicyClient)) {
                playControl = a(audioPolicyClient, IAudioPolicy.RequestRet.REQUEST_RET_BACKGROUND);
            } else {
                playControl = a(b, audioPolicyClient);
            }
        }
        L.policy.i("DUMP_CLIENTS.after:");
        a();
        AudioPolicyClient b2 = b();
        if (b2 != null) {
            a(audioSource, b2.b());
        }
        return playControl;
    }

    private synchronized boolean g(AudioPolicyClient audioPolicyClient) {
        L.policy.i("processFinish.audioPolicyClient(%d).type=%s, .source=%s", Integer.valueOf(audioPolicyClient.hashCode()), Integer.valueOf(audioPolicyClient.a()), audioPolicyClient.b());
        if (audioPolicyClient.d()) {
            FakeClient.getInstance().b(audioPolicyClient);
        }
        if (!d(audioPolicyClient)) {
            return false;
        }
        a(audioPolicyClient, IAudioPolicy.RequestRet.REQUEST_RET_FINISH);
        if (b() != null) {
            return false;
        }
        L.policy.i("DUMP_CLIENTS.before:");
        a();
        AudioPolicyClient c = c();
        if (c != null) {
            a(c, IAudioPolicy.RequestRet.REQUEST_RET_FOREGROUND, audioPolicyClient);
        } else {
            AudioPolicyClient d2 = d();
            if (d2 != null) {
                a(d2, IAudioPolicy.RequestRet.REQUEST_RET_RESUME, audioPolicyClient);
            }
        }
        AudioPolicyClient b = b();
        AudioSource audioSource = AudioSource.AUDIO_SOURCE_NULL;
        if (b != null) {
            audioSource = b.b();
        }
        L.policy.i("DUMP_CLIENTS.after:");
        a();
        a(audioPolicyClient.b(), audioSource);
        return true;
    }

    public AudioPolicyClient.PlayControl a(AudioPolicyClient audioPolicyClient) {
        this.a.removeMessages(2, audioPolicyClient);
        return f(audioPolicyClient);
    }

    private void a(AudioSource audioSource) {
        AudioSource audioSource2;
        L.policy.d("processForceStop, source=%s", audioSource);
        AudioPolicyClient b = b();
        boolean z = false;
        for (IAudioPolicy.AudioSourcePriority audioSourcePriority : IAudioPolicy.AudioSourcePriority.values()) {
            ArrayList<AudioPolicyClient> arrayList = this.c.get(audioSourcePriority);
            if (arrayList != null) {
                ArrayList arrayList2 = new ArrayList(arrayList.size());
                Iterator<AudioPolicyClient> it = arrayList.iterator();
                while (it.hasNext()) {
                    AudioPolicyClient next = it.next();
                    if (next != null && !next.a(audioSource)) {
                        if (z || b != next) {
                            next.a(IAudioPolicy.RequestRet.REQUEST_RET_FORCE_STOP, audioSource);
                            if (!next.a(AudioSource.AUDIO_SOURCE_POWER_KEY)) {
                                L.policy.d("processForceStop remove client=%s(%d)", next.b(), Integer.valueOf(next.hashCode()));
                                arrayList2.add(next);
                            }
                        } else {
                            z = true;
                        }
                    }
                }
                arrayList.removeAll(arrayList2);
            }
        }
        if (b != null) {
            AudioSource b2 = b.b();
            if (z) {
                g(b);
                b.a(IAudioPolicy.RequestRet.REQUEST_RET_FORCE_STOP, audioSource);
            } else if (b != b()) {
                AudioPolicyClient b3 = b();
                if (b3 == null) {
                    audioSource2 = AudioSource.AUDIO_SOURCE_NULL;
                } else {
                    audioSource2 = b3.b();
                }
                a(b2, audioSource2);
            }
        }
    }

    private void a(a aVar) {
        L.policy.d("processSourceChanged.old=%s, .new=%s", aVar.a, aVar.b);
        try {
        } catch (IllegalStateException e) {
            L.policy.e("processSourceChanged.now exception=", e);
        }
        if (SleepMode.peekInstance() == null) {
            L.policy.e("sleep mode instance not created yet.");
            return;
        }
        if (aVar.b == AudioSource.AUDIO_SOURCE_NULL) {
            SleepMode.getInstance().notifyPlayerStop();
        } else {
            SleepMode.getInstance().notifyPlayerStart();
        }
        int a2 = aVar.a.a();
        if (a2 == 512 || a2 == 1024) {
            try {
                SpeechManager.getInstance().checkNeedToOpenMic();
            } catch (IllegalStateException e2) {
                L.policy.e("processSourceChanged.old exception=", e2);
            }
        }
    }

    public void b(AudioPolicyClient audioPolicyClient) {
        this.a.removeMessages(1, audioPolicyClient);
        this.a.removeMessages(2, audioPolicyClient);
        this.a.obtainMessage(1, audioPolicyClient).sendToTarget();
    }

    public void a(AudioPolicyClient audioPolicyClient, long j) {
        this.a.removeMessages(1, audioPolicyClient);
        this.a.removeMessages(2, audioPolicyClient);
        Handler handler = this.a;
        handler.sendMessageDelayed(handler.obtainMessage(2, audioPolicyClient), j);
    }

    public void requestForceStopAll(AudioSource audioSource) {
        this.a.removeMessages(1);
        this.a.removeMessages(4);
        this.a.obtainMessage(4, audioSource).sendToTarget();
    }

    public AudioPolicyClient.PlayControl requestPause(AudioPolicyClient audioPolicyClient) {
        if (!hasActiveAudioSource()) {
            SleepMode.getInstance().notifyPlayerStop();
        }
        return AudioPolicyClient.PlayControl.ALLOW;
    }

    /* loaded from: classes3.dex */
    public static final class a {
        private AudioSource a;
        private AudioSource b;

        private a(AudioSource audioSource, AudioSource audioSource2) {
            this.a = audioSource;
            this.b = audioSource2;
        }
    }

    public void playbackController(PlaybackControllerInfo playbackControllerInfo) {
        AudioPolicyClient b = b();
        if (b == null || !b.supportPlaybackControl()) {
            b = c();
            L.policy.i("BackgroundClient=%s", b);
        }
        if (b == null || !b.supportPlaybackControl()) {
            b = d();
            L.policy.i("SuspendClient=%s", b);
        }
        L.policy.i("playbackController playbackControl=%s", playbackControllerInfo);
        if (b != null) {
            L.policy.i("playbackController getSource=%s,getSourceType=%s,getPlayControl=%s", b.b(), Integer.valueOf(b.a()), b.c());
            b.a(playbackControllerInfo);
            return;
        }
        L.policy.w("AudioPolicyClient is empty");
    }

    public void playbackController(PlaybackControl playbackControl) {
        playbackController(new PlaybackControllerInfo(playbackControl));
    }
}
