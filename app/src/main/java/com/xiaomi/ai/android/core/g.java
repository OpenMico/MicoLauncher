package com.xiaomi.ai.android.core;

import android.os.Build;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Execution;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.common.Context;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.common.Optional;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class g {
    private ScheduledThreadPoolExecutor a = new ScheduledThreadPoolExecutor(1);
    private d b;
    private int c;
    private int d;
    private Map<String, b> e;
    private Map<String, ScheduledFuture<?>> f;
    private ScheduledFuture<?> g;
    private String h;

    /* loaded from: classes2.dex */
    public enum a {
        DIALOG_START("DIALOG_START"),
        ASR_RESULT_RECEIVING("ASR_RESULT_RECEIVING"),
        ASR_STREAM_FINISH("ASR_STREAM_FINISH"),
        ASR_RESULT_FINISH("ASR_RESULT_FINISH"),
        TTS_START("TTS_START"),
        TTS_DATA_RECEIVING("TTS_DATA_RECEIVING"),
        TTS_FINISH("TTS_FINISH"),
        DIALOG_FINISH("DIALOG_FINISH");
        
        private String i;

        a(String str) {
            this.i = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class b {
        String a;
        String b;
        long c;
        boolean d;
        boolean e;
        boolean f;
        a g;
        int h;
        int i;

        private b(Event event) {
            this.a = event.getId();
            this.b = event.getFullName();
            this.c = System.currentTimeMillis() / 1000;
            this.d = true;
            this.f = true;
            this.e = true;
            List<Context> contexts = event.getContexts();
            this.g = AIApiConstants.SpeechSynthesizer.Synthesize.equals(event.getFullName()) ? a.TTS_START : a.DIALOG_START;
            for (Context context : contexts) {
                if (context.getFullName().equals(AIApiConstants.Execution.RequestControl)) {
                    Execution.RequestControl requestControl = (Execution.RequestControl) context.getPayload();
                    if (requestControl.getDisabled() == null) {
                        Logger.d("TimeoutManager", "Execution.RequestControl:disable option not set");
                        return;
                    }
                    for (Execution.RequestControlType requestControlType : requestControl.getDisabled()) {
                        if (requestControlType.equals(Execution.RequestControlType.NLP)) {
                            this.e = false;
                        } else if (requestControlType.equals(Execution.RequestControlType.TTS)) {
                            this.f = false;
                        }
                    }
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class c implements Runnable {
        private c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.xiaomi.ai.core.a g = g.this.b.g();
            if (g != null && g.isConnected()) {
                Logger.c("TimeoutManager", "KeepAliveCheckRunnable: stop channel");
                g.stop();
                g.this.b.k().a(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class d implements Runnable {
        private int b;
        private int c;
        private String d;
        private a e;

        d(b bVar) {
            this.d = bVar.a;
            this.e = bVar.g;
            this.b = bVar.h;
            this.c = bVar.i;
            Logger.a("TimeoutManager", "TimeoutCheckRunnable: init at: " + this.e + ", asrMidResultCount:" + this.b + ",ttsPackCount:" + this.c + ", eventId:" + this.d);
        }

        @Override // java.lang.Runnable
        public void run() {
            b bVar = (b) g.this.e.get(this.d);
            if (bVar == null) {
                Logger.b("TimeoutManager", "TimeoutCheckRunnable:dialogStatus is null, eventId:" + this.d);
            } else if (this.e.equals(bVar.g) && this.b == bVar.h && this.c == bVar.i) {
                int i = (bVar.f || bVar.d) ? (AIApiConstants.Nlp.Request.equals(bVar.b) || this.e.ordinal() >= a.TTS_START.ordinal()) ? StdStatuses.TTS_TIME_OUT : StdStatuses.ASR_TIME_OUT : StdStatuses.NLP_TIME_OUT;
                c d = g.this.b.d();
                d.obtainMessage(3, new AivsError(i, "timeout at stage:" + this.e, bVar.a)).sendToTarget();
                Logger.d("TimeoutManager", "timeout detected:" + bVar.a + ", stage:" + bVar.g);
            }
        }
    }

    public g(d dVar) {
        this.b = dVar;
        if (Build.VERSION.SDK_INT >= 21) {
            this.a.setRemoveOnCancelPolicy(true);
        }
        AivsConfig b2 = this.b.b();
        this.c = b2.getInt(AivsConfig.Asr.RECV_TIMEOUT, 5);
        this.d = b2.getInt(AivsConfig.Tts.RECV_TIMEOUT, 5);
        this.e = new ConcurrentHashMap();
        this.f = new ConcurrentHashMap();
    }

    private void a(b bVar) {
        ScheduledFuture<?> scheduledFuture = this.f.get(bVar.a);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            Logger.a("TimeoutManager", "cancel task at stat:" + bVar.g);
            this.f.remove(bVar.a);
        }
    }

    private void b(b bVar) {
        a(bVar);
        this.f.put(bVar.a, this.a.schedule(new d(bVar), (AIApiConstants.Nlp.Request.equals(bVar.b) || bVar.g.ordinal() >= a.TTS_START.ordinal()) ? this.d : this.c, TimeUnit.SECONDS));
    }

    private void f() {
        int i = this.b.b().getInt(AivsConfig.Connection.MAX_KEEP_ALIVE_TIME);
        synchronized (this) {
            if (this.g != null) {
                this.g.cancel(true);
                this.g = this.a.schedule(new c(), i, TimeUnit.SECONDS);
                Logger.a("TimeoutManager", "updateKeepAlive");
            }
        }
    }

    public void a() {
        f();
        String str = this.h;
        if (str == null) {
            Logger.a("TimeoutManager", "updateStat():mPcmEventId is null");
            return;
        }
        b bVar = this.e.get(str);
        if (bVar == null) {
            Logger.a("TimeoutManager", "updateStat():mDialogStatus is null,mPcmEventId=" + this.h);
            return;
        }
        a(bVar);
        bVar.g = a.TTS_DATA_RECEIVING;
        bVar.i++;
        b(bVar);
    }

    public void a(Event event) {
        char c2;
        f();
        b bVar = this.e.get(event.getId());
        String fullName = event.getFullName();
        int hashCode = fullName.hashCode();
        if (hashCode == -1718068525) {
            if (fullName.equals(AIApiConstants.Nlp.Request)) {
                c2 = 3;
            }
            c2 = 65535;
        } else if (hashCode == 861363398) {
            if (fullName.equals(AIApiConstants.SpeechRecognizer.Recognize)) {
                c2 = 0;
            }
            c2 = 65535;
        } else if (hashCode != 1343087634) {
            if (hashCode == 1866615416 && fullName.equals(AIApiConstants.SpeechRecognizer.RecognizeStreamFinished)) {
                c2 = 2;
            }
            c2 = 65535;
        } else {
            if (fullName.equals(AIApiConstants.SpeechSynthesizer.Synthesize)) {
                c2 = 1;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
            case 1:
                bVar = new b(event);
                this.e.put(event.getId(), bVar);
                break;
            case 2:
                if (bVar != null) {
                    a(bVar);
                    bVar.g = a.ASR_STREAM_FINISH;
                    break;
                } else {
                    Logger.b("TimeoutManager", "record:dialogStatus is null, eventId=" + event.getId());
                    return;
                }
            case 3:
                bVar = new b(event);
                bVar.d = false;
                this.e.put(event.getId(), bVar);
                break;
            default:
                return;
        }
        b(bVar);
    }

    public void a(Instruction instruction) {
        String str;
        StringBuilder sb;
        f();
        Optional<String> dialogId = instruction.getDialogId();
        if (dialogId == null) {
            Logger.d("TimeoutManager", "updateStat:dialogId is null," + instruction.getFullName());
        } else if (dialogId.isPresent()) {
            String str2 = dialogId.get();
            b bVar = this.e.get(str2);
            if (bVar == null) {
                Logger.b("TimeoutManager", "updateStat(Instruction instruction):dialogStatus is null, eventId=" + str2);
                return;
            }
            String fullName = instruction.getFullName();
            char c2 = 65535;
            switch (fullName.hashCode()) {
                case -349709590:
                    if (fullName.equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -65737731:
                    if (fullName.equals(AIApiConstants.System.Heartbeat)) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 274747385:
                    if (fullName.equals(AIApiConstants.Dialog.Finish)) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 978198135:
                    if (fullName.equals(AIApiConstants.SpeechSynthesizer.FinishSpeakStream)) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 1327948931:
                    if (fullName.equals(AIApiConstants.SpeechRecognizer.RecognizeResult)) {
                        c2 = 0;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    a(bVar);
                    if (!((SpeechRecognizer.RecognizeResult) instruction.getPayload()).isFinal()) {
                        bVar.g = a.ASR_RESULT_RECEIVING;
                        bVar.h++;
                    } else {
                        bVar.g = a.ASR_RESULT_FINISH;
                        if (!bVar.f) {
                            return;
                        }
                    }
                    b(bVar);
                    return;
                case 1:
                    b(bVar);
                    return;
                case 2:
                    a(bVar);
                    bVar.g = a.TTS_START;
                    this.h = bVar.a;
                    b(bVar);
                    return;
                case 3:
                    a(bVar);
                    bVar.g = a.TTS_FINISH;
                    str = "TimeoutManager";
                    sb = new StringBuilder();
                    sb.append("dialog finish at :");
                    sb.append(bVar.g);
                    Logger.a(str, sb.toString());
                    return;
                case 4:
                    bVar.g = a.DIALOG_FINISH;
                    a(bVar);
                    this.e.remove(bVar.a);
                    str = "TimeoutManager";
                    sb = new StringBuilder();
                    sb.append("dialog finish, remove : ");
                    sb.append(bVar.a);
                    Logger.a(str, sb.toString());
                    return;
                default:
                    return;
            }
        }
    }

    public void b() {
        for (b bVar : this.e.values()) {
            a(bVar);
        }
        this.e.clear();
    }

    public void c() {
        for (b bVar : this.e.values()) {
            a(bVar);
        }
        this.e.clear();
        synchronized (this) {
            if (this.g != null) {
                this.g.cancel(true);
                this.g = null;
            }
        }
    }

    public void d() {
        int i = this.b.b().getInt(AivsConfig.Connection.MAX_KEEP_ALIVE_TIME);
        synchronized (this) {
            if (this.g != null) {
                this.g.cancel(true);
            }
            this.g = this.a.schedule(new c(), i, TimeUnit.SECONDS);
            Logger.a("TimeoutManager", "startKeepAlive");
        }
    }

    public void e() {
        synchronized (this) {
            if (this.g != null) {
                Logger.a("TimeoutManager", "cancelKeepAlive");
                this.g.cancel(true);
                this.g = null;
            }
        }
    }
}
