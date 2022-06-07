package com.xiaomi.micolauncher.common.speech.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaomi.ai.android.capability.ErrorCapability;
import com.xiaomi.ai.android.capability.InstructionCapability;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Nlp;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechContextHelper;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.speech.capability.MIAuthCapabilityImpl;
import com.xiaomi.micolauncher.common.speech.capability.SpeechSynthesizerCapabilityImpl;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class SpeechEventUtil {
    private static final long a = TimeUnit.MINUTES.toMillis(10);
    private static final long b = TimeUnit.SECONDS.toMillis(6);
    private static volatile SpeechEventUtil c;
    private static Context j;
    private Engine d;
    private final List<Instruction> e;
    private volatile Handler f;
    private final AtomicBoolean g = new AtomicBoolean();
    private final LinkedList<RequestEvent> h;
    private volatile RequestEvent i;

    /* loaded from: classes3.dex */
    public interface EventListener {
        void onEventFail(Event event, boolean z);

        void onEventResult(List<Instruction> list, Event event);
    }

    /* loaded from: classes3.dex */
    public interface NLPListener {
        void onNlpFail(String str, boolean z);

        void onNlpResult(List<Instruction> list, String str);
    }

    /* loaded from: classes3.dex */
    public interface TTSListener {
        void onTtsFail(String str, boolean z);

        void onTtsUrl(String str, String str2);
    }

    /* loaded from: classes3.dex */
    public enum a {
        TTS,
        NLP,
        EVENT,
        NLP_TTS
    }

    public static void init(Context context) {
        j = context.getApplicationContext();
    }

    public static SpeechEventUtil getInstance() {
        if (c == null) {
            synchronized (SpeechEventUtil.class) {
                if (c == null) {
                    c = new SpeechEventUtil();
                }
            }
        }
        return c;
    }

    public static SpeechEventUtil peekInstance() {
        if (j != null) {
            return getInstance();
        }
        return c;
    }

    private SpeechEventUtil() {
        if (j != null) {
            this.h = new LinkedList<>();
            this.e = new ArrayList();
            return;
        }
        throw new IllegalStateException(" SpeechSkillUtil : Context not set");
    }

    private synchronized void a() {
        if (!this.g.get() || this.f == null) {
            if (this.f == null) {
                c();
            }
            if (this.f != null) {
                this.f.removeMessages(11);
                this.f.sendEmptyMessage(11);
            } else {
                L.speech.d("%s initEngine handler is null", "[SpeechEventUtil]:");
            }
            L.speech.d("%s initEngine", "[SpeechEventUtil]:");
            return;
        }
        L.speech.d("%s initEngine engineStarted", "[SpeechEventUtil]:");
        this.f.removeMessages(2);
    }

    private synchronized void b() {
        if (this.d == null) {
            this.d = Engine.create(j.getApplicationContext(), SpeechEngineHelper.createConfiguration(j, false), SpeechEngineHelper.createClientInfo(j), 1);
            a(this.d);
            SpeechEngineHelper.setLoggerLevelAndHooker();
            this.g.set(false);
            if (this.f != null) {
                this.f.sendEmptyMessage(1);
            } else {
                k();
            }
        } else if (this.g.get()) {
            this.f.sendEmptyMessage(3);
        } else {
            k();
        }
        L.speech.d("%s createEngine", "[SpeechEventUtil]:");
    }

    private void c() {
        this.f = new Handler(ThreadUtil.getLightWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$rHYG6wdajsnwbL3MlSW46v0BxC0
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                boolean a2;
                a2 = SpeechEventUtil.this.a(message);
                return a2;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ boolean a(android.os.Message r7) {
        /*
            r6 = this;
            int r7 = r7.what
            r0 = 11
            r1 = 0
            if (r7 == r0) goto L_0x00b4
            r0 = 6
            r2 = 1
            switch(r7) {
                case 1: goto L_0x0076;
                case 2: goto L_0x005f;
                case 3: goto L_0x004e;
                case 4: goto L_0x0031;
                case 5: goto L_0x0013;
                case 6: goto L_0x000e;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x00b7
        L_0x000e:
            r6.d(r1)
            goto L_0x00b7
        L_0x0013:
            com.elvishew.xlog.Logger r7 = com.xiaomi.micolauncher.common.L.speech
            java.lang.String r3 = "%s Engine request error"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r4 = "[SpeechEventUtil]:"
            r2[r1] = r4
            r7.d(r3, r2)
            android.os.Handler r7 = r6.f
            if (r7 == 0) goto L_0x0029
            android.os.Handler r7 = r6.f
            r7.removeMessages(r0)
        L_0x0029:
            r6.d(r1)
            r6.j()
            goto L_0x00b7
        L_0x0031:
            com.elvishew.xlog.Logger r7 = com.xiaomi.micolauncher.common.L.speech
            java.lang.String r3 = "%s Engine request finish"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r4 = "[SpeechEventUtil]:"
            r2[r1] = r4
            r7.d(r3, r2)
            android.os.Handler r7 = r6.f
            if (r7 == 0) goto L_0x0047
            android.os.Handler r7 = r6.f
            r7.removeMessages(r0)
        L_0x0047:
            r6.h()
            r6.j()
            goto L_0x00b7
        L_0x004e:
            com.elvishew.xlog.Logger r7 = com.xiaomi.micolauncher.common.L.speech
            java.lang.String r0 = "%s Engine request"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "[SpeechEventUtil]:"
            r2[r1] = r3
            r7.d(r0, r2)
            r6.i()
            goto L_0x00b7
        L_0x005f:
            com.elvishew.xlog.Logger r7 = com.xiaomi.micolauncher.common.L.speech
            java.lang.String r0 = "%s Engine release"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "[SpeechEventUtil]:"
            r2[r1] = r3
            r7.d(r0, r2)
            boolean r7 = r6.g()
            if (r7 != 0) goto L_0x00b7
            r6.e()
            goto L_0x00b7
        L_0x0076:
            com.xiaomi.ai.android.core.Engine r7 = r6.d
            if (r7 == 0) goto L_0x0087
            boolean r7 = r7.start()     // Catch: RejectedExecutionException -> 0x007f
            goto L_0x0088
        L_0x007f:
            r7 = move-exception
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.speech
            java.lang.String r3 = "SpeechEventUtil Engine start"
            r0.e(r3, r7)
        L_0x0087:
            r7 = r1
        L_0x0088:
            com.elvishew.xlog.Logger r0 = com.xiaomi.micolauncher.common.L.speech
            java.lang.String r3 = "%s Engine startResult=%s"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r5 = "[SpeechEventUtil]:"
            r4[r1] = r5
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r7)
            r4[r2] = r5
            r0.d(r3, r4)
            java.util.concurrent.atomic.AtomicBoolean r0 = r6.g
            r0.set(r7)
            if (r7 == 0) goto L_0x00a7
            r6.j()
            goto L_0x00b7
        L_0x00a7:
            r6.k()
            boolean r7 = r6.g()
            if (r7 != 0) goto L_0x00b7
            r6.e()
            goto L_0x00b7
        L_0x00b4:
            r6.b()
        L_0x00b7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.a(android.os.Message):boolean");
    }

    private void d() {
        if (this.f != null) {
            this.f.removeMessages(2);
            this.f.sendEmptyMessageDelayed(2, a);
        }
    }

    private void e() {
        L.speech.d("%s releaseEngine", "[SpeechEventUtil]:");
        this.g.set(false);
        f();
        this.e.clear();
        Engine engine = this.d;
        if (engine != null) {
            engine.release();
            this.d = null;
        }
    }

    private void f() {
        if (this.f != null) {
            this.f.removeCallbacksAndMessages(null);
            this.f = null;
        }
    }

    private boolean g() {
        boolean z = this.i != null;
        L.speech.d("%s isRequestNotNull=%s", "[SpeechEventUtil]:", Boolean.valueOf(z));
        return z;
    }

    private void a(Engine engine) {
        if (engine != null) {
            engine.registerCapability(new InstructionCapability() { // from class: com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.1
                @Override // com.xiaomi.ai.android.capability.InstructionCapability
                public boolean process(Instruction instruction) {
                    L.speech.d("%s instruction=%s", "[SpeechEventUtil]:", instruction.toString());
                    SpeechEventUtil.this.e.add(instruction);
                    if (AIApiConstants.Dialog.Finish.equals(instruction.getFullName())) {
                        if (SpeechEventUtil.this.f != null) {
                            SpeechEventUtil.this.f.sendEmptyMessage(4);
                        }
                    } else if (AIApiConstants.General.Push.equals(instruction.getFullName())) {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(instruction);
                        String dialogId = InstructionUtil.getDialogId(instruction);
                        OperationManager.getInstance().addInstructions(dialogId, arrayList);
                        SpeechManager.getInstance().getUiHandler().obtainMessage(2004, dialogId).sendToTarget();
                    }
                    return true;
                }
            });
            engine.registerCapability(new SpeechSynthesizerCapabilityImpl(SpeechManager.getInstance().getUiHandler()));
            engine.registerCapability(new ErrorCapability() { // from class: com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.2
                @Override // com.xiaomi.ai.android.capability.ErrorCapability
                public void onError(AivsError aivsError) {
                    if (SpeechEventUtil.this.f != null) {
                        SpeechEventUtil.this.f.sendEmptyMessage(5);
                    }
                }
            });
            engine.registerCapability(new MIAuthCapabilityImpl());
        }
    }

    private void h() {
        if (this.i == null) {
            L.speech.i("processInstruction currentRequestEvent is null");
            return;
        }
        a aVar = this.i.b;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.e);
        if (aVar == a.TTS) {
            a(arrayList);
        } else if (aVar == a.NLP) {
            b(arrayList);
        } else if (aVar == a.EVENT) {
            c(arrayList);
        } else {
            d(false);
            L.speech.i("processInstruction requestType=%s", aVar);
        }
    }

    private void a(List<Instruction> list) {
        Instruction intention = InstructionUtil.getIntention(list, AIApiConstants.SpeechSynthesizer.Speak);
        if (intention == null) {
            c(false);
        } else if (!InstructionUtil.getDialogId(intention).equals(this.i.requestDialogId)) {
            c(false);
        } else {
            SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) intention.getPayload();
            if (speak.getUrl().isPresent()) {
                a(speak.getUrl().get());
            } else {
                c(false);
            }
        }
    }

    private void b(List<Instruction> list) {
        if (ContainerUtil.isEmpty(list)) {
            a(false);
        } else if (!InstructionUtil.getDialogId(list.get(0)).equals(this.i.requestDialogId)) {
            a(false);
        } else {
            d(list);
        }
    }

    private void c(List<Instruction> list) {
        if (ContainerUtil.isEmpty(list)) {
            b(false);
        } else if (!InstructionUtil.getDialogId(list.get(0)).equals(this.i.requestDialogId)) {
            b(false);
        } else {
            e(list);
        }
    }

    private void d(List<Instruction> list) {
        if (this.i != null && this.i.b == a.NLP) {
            L.speech.d("%s onLoadNlpResult=%s", "[SpeechEventUtil]:", this.i);
            final String str = this.i.content;
            if (!TextUtils.isEmpty(str) && this.i.nlpListener != null) {
                final ArrayList arrayList = new ArrayList(list);
                final NLPListener nLPListener = this.i.nlpListener;
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$ET-_iaNktFaDFlZxeXpH9SDJhqg
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpeechEventUtil.NLPListener.this.onNlpResult(arrayList, str);
                    }
                });
            }
            this.i.nlpListener = null;
            this.i = null;
        }
    }

    private void a(final boolean z) {
        L.speech.d("%s onLoadNlpFail=%s", "[SpeechEventUtil]:", this.i);
        if (this.i != null && this.i.b == a.NLP) {
            final NLPListener nLPListener = this.i.nlpListener;
            final String str = this.i.content;
            if (!TextUtils.isEmpty(str) && nLPListener != null) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$0LOcWMAR-NFB6yJAqA4Fq9NpWwU
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpeechEventUtil.NLPListener.this.onNlpFail(str, z);
                    }
                });
            }
            this.i.nlpListener = null;
            this.i = null;
        }
    }

    private void e(List<Instruction> list) {
        L.speech.d("%s onLoadEventResult=%s", "[SpeechEventUtil]:", this.i);
        if (this.i != null && this.i.b == a.EVENT) {
            final EventListener eventListener = this.i.eventListener;
            final Event event = this.i.event;
            if (eventListener != null) {
                final ArrayList arrayList = new ArrayList(list);
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$8eY8iZ0xqIm9C44TipYsMDFcjjM
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpeechEventUtil.EventListener.this.onEventResult(arrayList, event);
                    }
                });
            }
            this.i.eventListener = null;
            this.i = null;
        }
    }

    private void b(final boolean z) {
        L.speech.d("%s onLoadEventFail=%s", "[SpeechEventUtil]:", this.i);
        if (this.i != null && this.i.b == a.EVENT) {
            final EventListener eventListener = this.i.eventListener;
            final Event event = this.i.event;
            if (eventListener != null) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$yzjKAeKk9LcxPBMDXO1PiKPm0sc
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpeechEventUtil.EventListener.this.onEventFail(event, z);
                    }
                });
            }
            this.i.eventListener = null;
            this.i = null;
        }
    }

    private void a(final String str) {
        L.speech.d("%s onLoadTtsUrl=%s", "[SpeechEventUtil]:", this.i);
        if (this.i != null && this.i.b == a.TTS) {
            final String str2 = this.i.content;
            final TTSListener tTSListener = this.i.ttsListener;
            if (!TextUtils.isEmpty(str2) && tTSListener != null) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$dAziYPYYsYc2JvfKrdk424NACok
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpeechEventUtil.TTSListener.this.onTtsUrl(str, str2);
                    }
                });
            }
            this.i.ttsListener = null;
            this.i = null;
        }
    }

    private void c(final boolean z) {
        L.speech.d("%s onLoadTtsFail=%s", "[SpeechEventUtil]:", this.i);
        if (this.i != null && this.i.b == a.TTS) {
            final String str = this.i.content;
            final TTSListener tTSListener = this.i.ttsListener;
            if (!TextUtils.isEmpty(str) && tTSListener != null) {
                ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$NVg3yCo32LDbso3ri8iQJUKJA8Q
                    @Override // java.lang.Runnable
                    public final void run() {
                        SpeechEventUtil.TTSListener.this.onTtsFail(str, z);
                    }
                });
            }
            this.i.ttsListener = null;
            this.i = null;
        }
    }

    public void ttsRequest(final String str, final TTSListener tTSListener) {
        L.speech.d("%s ttsRequest.content=%s", "[SpeechEventUtil]:", str);
        a();
        if (!TextUtils.isEmpty(str) && this.f != null) {
            this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$HBKTDaW503qp5b0nmkvzekq8y14
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechEventUtil.this.a(str, tTSListener);
                }
            });
        } else if (tTSListener != null) {
            tTSListener.onTtsFail(str, false);
        }
    }

    public /* synthetic */ void a(String str, TTSListener tTSListener) {
        RequestEvent requestEvent = new RequestEvent(a.TTS);
        requestEvent.content = str;
        requestEvent.ttsListener = tTSListener;
        this.h.add(requestEvent);
        if (this.g.get() && !g()) {
            j();
        }
    }

    public void nlpRequest(final String str, final NLPListener nLPListener) {
        L.speech.d("%s nlpRequest.content=%s", "[SpeechEventUtil]:", str);
        a();
        if (!TextUtils.isEmpty(str) && this.f != null) {
            this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$ZMr6yn_K2yifWaIWH1ursS6FqZA
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechEventUtil.this.a(str, nLPListener);
                }
            });
        } else if (nLPListener != null) {
            nLPListener.onNlpFail(str, false);
        }
    }

    public /* synthetic */ void a(String str, NLPListener nLPListener) {
        RequestEvent requestEvent = new RequestEvent(a.NLP);
        requestEvent.content = str;
        requestEvent.nlpListener = nLPListener;
        this.h.add(requestEvent);
        if (this.g.get() && !g()) {
            j();
        }
    }

    public void eventRequest(final Event event, final EventListener eventListener) {
        L.speech.d("%s eventRequest.event=%s", "[SpeechEventUtil]:", event);
        a();
        if (event != null && this.f != null) {
            this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$OHIloTCRGjV4j0DKAkmGkzaKPU8
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechEventUtil.this.a(event, eventListener);
                }
            });
        } else if (eventListener != null) {
            eventListener.onEventFail(event, false);
        }
    }

    public /* synthetic */ void a(Event event, EventListener eventListener) {
        RequestEvent requestEvent = new RequestEvent(a.EVENT);
        requestEvent.event = event;
        requestEvent.eventListener = eventListener;
        this.h.add(requestEvent);
        if (this.g.get() && !g()) {
            j();
        }
    }

    public void sendEventRequest(final Event event) {
        L.speech.d("%s sendEventRequest.event=%s", "[SpeechEventUtil]:", event);
        a();
        if (this.f != null) {
            this.f.post(new Runnable() { // from class: com.xiaomi.micolauncher.common.speech.utils.-$$Lambda$SpeechEventUtil$ditFAcZxtmN6kZEIxabpwc4OCUo
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechEventUtil.this.b(event);
                }
            });
        }
    }

    public /* synthetic */ void b(Event event) {
        RequestEvent requestEvent = new RequestEvent(a.EVENT);
        requestEvent.event = event;
        this.h.add(requestEvent);
        if (this.g.get() && !g()) {
            j();
        }
    }

    private void i() {
        if (this.h.isEmpty()) {
            L.speech.d("%s events is empty", "[SpeechEventUtil]:");
            d();
            return;
        }
        RequestEvent poll = this.h.poll();
        if (poll == null) {
            L.speech.d("%s  doRequest requestEvent is empty", "[SpeechEventUtil]:");
            return;
        }
        this.i = poll;
        if (poll.b == a.TTS) {
            Event buildEvent = APIUtils.buildEvent(new SpeechSynthesizer.Synthesize(poll.content));
            buildEvent.setContext(SpeechContextHelper.getContexts(false));
            poll.requestDialogId = buildEvent.getId();
            L.speech.d("%s ttsRequest dialogId=%s", "[SpeechEventUtil]:", poll.requestDialogId);
            a(buildEvent);
        } else if (poll.b == a.NLP) {
            Event buildEvent2 = APIUtils.buildEvent(new Nlp.Request(poll.content));
            buildEvent2.addContext(SpeechContextHelper.requestControlContext(true, false));
            poll.requestDialogId = buildEvent2.getId();
            L.speech.d("%s nlpRequest dialogId=%s", "[SpeechEventUtil]:", poll.requestDialogId);
            a(buildEvent2);
        } else if (poll.b == a.EVENT) {
            poll.requestDialogId = poll.event.getId();
            poll.event.setContext(SpeechContextHelper.getContexts(false));
            L.speech.d("%s eventRequest dialogId=%s", "[SpeechEventUtil]:", poll.requestDialogId);
            a(poll.event);
        } else {
            j();
        }
    }

    private void j() {
        if (this.f != null && !this.f.hasMessages(3)) {
            L.speech.d("%s startRequest", "[SpeechEventUtil]:");
            this.f.removeMessages(6);
            this.f.sendEmptyMessage(3);
        }
    }

    private void a(@NonNull Event event) {
        if (this.d != null) {
            this.e.clear();
            boolean postEvent = this.d.postEvent(event);
            try {
                L.speech.d("%s postEvent=%s,jsonString=%s", "[SpeechEventUtil]:", Boolean.valueOf(postEvent), event.toJsonString());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (!postEvent) {
                d(false);
                j();
            } else if (this.f != null) {
                this.f.sendEmptyMessageDelayed(6, b);
            }
        }
    }

    private void k() {
        while (!this.h.isEmpty()) {
            try {
                this.i = this.h.poll();
                d(false);
            } catch (Exception e) {
                L.speech.e(" events.forEach exceptions occurred %s", e);
                return;
            }
        }
    }

    private void d(boolean z) {
        a(z);
        c(z);
        b(z);
    }

    /* loaded from: classes3.dex */
    public class RequestEvent {
        private a b;
        public String content;
        public Event event;
        public EventListener eventListener;
        public NLPListener nlpListener;
        public String requestDialogId;
        public TTSListener ttsListener;

        public RequestEvent(a aVar) {
            SpeechEventUtil.this = r1;
            this.b = aVar;
        }

        public String toString() {
            return "RequestEvent{content=" + this.content + ", requestType=" + this.b + ", requestDialogId='" + this.requestDialogId + "'}";
        }
    }
}
