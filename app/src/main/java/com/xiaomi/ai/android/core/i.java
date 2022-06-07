package com.xiaomi.ai.android.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.android.track.b;
import com.xiaomi.ai.android.track.d;
import com.xiaomi.ai.android.utils.NetworkUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.track.TrackData;
import com.xiaomi.ai.track.a;
import com.xiaomi.common.Optional;
import com.xiaomi.micolauncher.api.model.PlayV3Pact;
import com.xiaomi.mipush.sdk.Constants;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes2.dex */
public class i {
    private HandlerThread a;
    private Handler b;
    private d c;
    private a d;
    private d e;
    private Map<String, TrackData> f = new ConcurrentHashMap();
    private Set<String> g = new CopyOnWriteArraySet();
    private Map<String, Long> h = new ConcurrentHashMap();
    private Map<String, Long> i = new ConcurrentHashMap();
    private long j = 0;
    private boolean k = false;
    private long l = 0;
    private boolean m = false;
    private String n;
    private String o;

    public i(d dVar) {
        this.c = dVar;
        this.d = new com.xiaomi.ai.android.track.a(this.c);
        this.e = new d(this.c, this.d, new b.c() { // from class: com.xiaomi.ai.android.core.i.1
            @Override // com.xiaomi.ai.android.track.b.c
            public void a() {
                Logger.a("TrackManager", "onTrackFinish");
                if (i.this.g != null && i.this.g.size() > 0) {
                    for (String str : i.this.g) {
                        i.this.f.remove(str);
                    }
                    i.this.g.clear();
                }
            }

            @Override // com.xiaomi.ai.android.track.b.c
            public void a(AivsError aivsError) {
            }
        });
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            this.a = new HandlerThread("TrackThread");
            this.a.start();
            this.b = new Handler(this.a.getLooper(), new Handler.Callback() { // from class: com.xiaomi.ai.android.core.i.2
                @Override // android.os.Handler.Callback
                public boolean handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            i.this.b(((Boolean) message.obj).booleanValue());
                            return true;
                        case 1:
                            i.this.h();
                            return true;
                        case 2:
                            i.this.b((ObjectNode) message.obj);
                            return true;
                        case 3:
                            i.this.b((String) message.obj);
                            return true;
                        case 4:
                            i.this.g();
                            return true;
                        case 5:
                            i.this.i();
                            return true;
                        default:
                            return true;
                    }
                }
            });
            a();
        }
    }

    private void a(Event event, TrackData trackData) {
        String str;
        String str2;
        String c = NetworkUtils.c(this.c.a());
        trackData.set("network", c);
        if ("WIFI".equals(c)) {
            trackData.set("network.wifi.signal.level", NetworkUtils.d(this.c.a()));
        } else {
            trackData.set("network.data.carrier.type", NetworkUtils.e(this.c.a()));
        }
        trackData.set("request.id", event.getId());
        if (AIApiConstants.SpeechRecognizer.Recognize.equals(event.getFullName())) {
            trackData.set("request.cmd", "ASR");
            trackData.setTimestamp("v5.sdk.asr.send.recognizer.recognize.in", System.currentTimeMillis());
            this.j = 0L;
            this.l = 0L;
            this.k = false;
            this.m = false;
            this.h.clear();
            this.i.clear();
            this.n = null;
            this.o = null;
            return;
        }
        if (AIApiConstants.Nlp.Request.equals(event.getFullName())) {
            str = "request.cmd";
            str2 = "NLP";
        } else if (AIApiConstants.SpeechSynthesizer.Synthesize.equals(event.getFullName())) {
            str = "request.cmd";
            str2 = PlayV3Pact.TYPE_TTS;
        } else {
            return;
        }
        trackData.set(str, str2);
    }

    public void b(ObjectNode objectNode) {
        TrackData trackData;
        String asText = objectNode.path("eventId").asText();
        if (f.a(asText)) {
            Logger.b("TrackManager", "mergeAppData: eventId is not set, create new trackData");
            trackData = this.d.a();
        } else {
            trackData = this.f.get(asText);
            if (trackData == null) {
                Logger.c("TrackManager", "mergeAppData: eventId:" + asText + " is not existed");
                trackData = this.d.a();
                trackData.set("request.id", asText);
            }
            trackData.finishTrack();
        }
        trackData.mergeAppData(objectNode);
        Logger.a("TrackManager", "mergeAppData:" + objectNode.toString());
        b(true);
    }

    public void b(String str) {
        if (!f.a(str)) {
            TrackData trackData = this.f.get(str);
            if (trackData == null) {
                Logger.b("TrackManager", "checkAppData: " + str + " has been tracked");
                return;
            }
            trackData.finishTrack();
            this.g.add(str);
            Logger.b("TrackManager", "checkAppData: force post track, " + str);
            a(true);
        }
    }

    private void b(String str, boolean z) {
        TrackData trackData;
        if (f.a(str) || (trackData = this.f.get(str)) == null) {
            return;
        }
        if (trackData.getTimestamp("v5.sdk.asr.send.recognizer.recognize.in") == 0) {
            trackData.setTimestamp(trackData.getTimestamp("v5.sdk.wakeup.send.first.binary.in") == 0 ? "v5.sdk.wakeup.send.first.binary.in" : "v5.sdk.wakeup.send.last.binary.in", System.currentTimeMillis());
            return;
        }
        trackData.setTimestamp(trackData.getTimestamp("v5.sdk.asr.send.first.binary.in") == 0 ? "v5.sdk.asr.send.first.binary.in" : "v5.sdk.asr.send.last.binary.in", System.currentTimeMillis());
        if (!z) {
            if (this.j == 0) {
                this.j = System.currentTimeMillis();
                return;
            }
            long currentTimeMillis = System.currentTimeMillis() - this.j;
            this.j = System.currentTimeMillis();
            String str2 = this.k ? "v5.sdk.asr.send.binary.include.event.interval.in" : "v5.sdk.asr.send.binary.exclude.event.interval.in";
            if (currentTimeMillis > trackData.getTimestamp(str2)) {
                trackData.setTimestamp(str2, currentTimeMillis);
            }
            this.k = false;
        }
    }

    public void b(boolean z) {
        this.e.a(z);
    }

    private void c(Event event) {
        if (this.c.e().b(event)) {
            TrackData trackData = this.f.get(event.getId());
            if (trackData == null) {
                trackData = new TrackData(this.d);
                this.f.put(event.getId(), trackData);
            }
            a(event, trackData);
            this.b.sendMessageDelayed(this.b.obtainMessage(3, event.getId()), this.c.b().getInt(AivsConfig.Track.MAX_WAIT_TIME) * 1000);
        } else if (AIApiConstants.SpeechRecognizer.RecognizeStreamFinished.equals(event.getFullName())) {
            TrackData trackData2 = this.f.get(event.getId());
            if (trackData2 != null) {
                trackData2.setTimestamp("v5.sdk.asr.send.recognizer.recognizefinished.in", System.currentTimeMillis());
            }
        } else if (AIApiConstants.General.ContextUpdate.equals(event.getFullName())) {
            this.k = true;
        }
    }

    private void c(String str) {
        TrackData trackData;
        if (!f.a(str) && (trackData = this.f.get(str)) != null && trackData.getTimestamp("v5.sdk.tts.recv.first.binary.in") == 0) {
            Logger.a("TrackManager", "handleTtsStream: " + str, false);
            trackData.setTimestamp("v5.sdk.tts.recv.first.binary.in", System.currentTimeMillis());
        }
    }

    private void d(Instruction instruction) {
        Long l;
        String str;
        Optional<String> dialogId = instruction.getDialogId();
        if (dialogId != null && dialogId.isPresent()) {
            TrackData trackData = this.f.get(dialogId.get());
            Logger.a("TrackManager", "handleInstruction: " + instruction.getFullName() + ", " + ((Object) dialogId.get()));
            if (trackData != null) {
                String fullName = instruction.getFullName();
                char c = 65535;
                switch (fullName.hashCode()) {
                    case -788476173:
                        if (fullName.equals(AIApiConstants.System.TruncationNotification)) {
                            c = 1;
                            break;
                        }
                        break;
                    case -529211283:
                        if (fullName.equals(AIApiConstants.Nlp.FinishAnswer)) {
                            c = 5;
                            break;
                        }
                        break;
                    case -349709590:
                        if (fullName.equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 274747385:
                        if (fullName.equals(AIApiConstants.Dialog.Finish)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 978198135:
                        if (fullName.equals(AIApiConstants.SpeechSynthesizer.FinishSpeakStream)) {
                            c = 3;
                            break;
                        }
                        break;
                    case 986531076:
                        if (fullName.equals(AIApiConstants.Nlp.StartAnswer)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1327948931:
                        if (fullName.equals(AIApiConstants.SpeechRecognizer.RecognizeResult)) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        SpeechRecognizer.RecognizeResult recognizeResult = (SpeechRecognizer.RecognizeResult) instruction.getPayload();
                        List<SpeechRecognizer.RecognizeResultItem> results = recognizeResult.getResults();
                        if (results.size() > 0) {
                            SpeechRecognizer.RecognizeResultItem recognizeResultItem = results.get(0);
                            String text = recognizeResultItem.getText();
                            if (recognizeResultItem.isNlpRequest().isPresent() && recognizeResultItem.isNlpRequest().get().booleanValue()) {
                                trackData.setTimestamp("v5.sdk.asr.recv.last.partial.for.nlp.out", System.currentTimeMillis());
                                if (!f.a(text)) {
                                    this.o = text;
                                }
                            }
                            if (trackData.getTimestamp("v5.sdk.asr.recv.first.partial.out") == 0) {
                                trackData.setTimestamp("v5.sdk.asr.recv.first.partial.out", System.currentTimeMillis());
                            }
                            if (!f.a(text)) {
                                if (this.i.get(text) == null) {
                                    this.i.put(text, Long.valueOf(System.currentTimeMillis()));
                                }
                                if (trackData.getTimestamp("v5.sdk.asr.recv.first.text.out") == 0) {
                                    trackData.setTimestamp("v5.sdk.asr.recv.first.text.out", System.currentTimeMillis());
                                }
                            }
                            if (recognizeResult.isFinal()) {
                                trackData.setTimestamp("v5.sdk.asr.recv.final.out", System.currentTimeMillis());
                                if (this.i.size() > 0 && !f.a(this.o) && (l = this.i.get(this.o)) != null) {
                                    trackData.setTimestamp("v5.sdk.asr.recv.speak.finish.out", l.longValue());
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    case 1:
                        str = "v5.sdk.asr.recv.system.truncationnotification.out";
                        break;
                    case 2:
                        SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) instruction.getPayload();
                        if (speak == null || !speak.getUrl().isPresent()) {
                            if (trackData.getTimestamp("v5.sdk.nlp.recv.speak.stream.out") == 0) {
                                str = "v5.sdk.nlp.recv.speak.stream.out";
                                break;
                            } else {
                                return;
                            }
                        } else if (trackData.getTimestamp("v5.sdk.nlp.recv.speak.url.out") == 0) {
                            str = "v5.sdk.nlp.recv.speak.url.out";
                            break;
                        } else {
                            return;
                        }
                        break;
                    case 3:
                        str = "v5.sdk.tts.recv.synthesizer.finishspeakstream.out";
                        break;
                    case 4:
                        str = "v5.sdk.nlp.recv.startanswer.out";
                        break;
                    case 5:
                        str = "v5.sdk.nlp.recv.finishanswer.out";
                        break;
                    case 6:
                        str = "v5.sdk.dialog.finish.out";
                        break;
                    default:
                        return;
                }
                trackData.setTimestamp(str, System.currentTimeMillis());
            }
        }
    }

    private void d(String str) {
        TrackData trackData;
        if (!f.a(str) && (trackData = this.f.get(str)) != null && trackData.getTimestamp("v5.sdk.tts.recv.first.binary.out") == 0) {
            Logger.a("TrackManager", "handleTtsStreamInQueue: " + str, false);
            trackData.setTimestamp("v5.sdk.tts.recv.first.binary.out", System.currentTimeMillis());
        }
    }

    private void e(Instruction instruction) {
        Optional<String> dialogId = instruction.getDialogId();
        if (dialogId != null && dialogId.isPresent()) {
            TrackData trackData = this.f.get(dialogId.get());
            Logger.a("TrackManager", "handle InstructionCall: " + instruction.getFullName() + ", " + ((Object) dialogId.get()));
            if (trackData != null) {
                String fullName = instruction.getFullName();
                char c = 65535;
                if (fullName.hashCode() == 1327948931 && fullName.equals(AIApiConstants.SpeechRecognizer.RecognizeResult)) {
                    c = 0;
                }
                if (c == 0 && trackData.getTimestamp("v5.sdk.asr.recv.first.partial.call") == 0) {
                    trackData.setTimestamp("v5.sdk.asr.recv.first.partial.call", System.currentTimeMillis());
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void f(Instruction instruction) {
        Long l;
        String str;
        Optional<String> dialogId = instruction.getDialogId();
        if (dialogId != null && dialogId.isPresent()) {
            TrackData trackData = this.f.get(dialogId.get());
            Logger.a("TrackManager", "handleInstruction0: " + instruction.getFullName() + ", " + ((Object) dialogId.get()), false);
            if (trackData != null) {
                String fullName = instruction.getFullName();
                char c = 65535;
                switch (fullName.hashCode()) {
                    case -788476173:
                        if (fullName.equals(AIApiConstants.System.TruncationNotification)) {
                            c = 1;
                            break;
                        }
                        break;
                    case -529211283:
                        if (fullName.equals(AIApiConstants.Nlp.FinishAnswer)) {
                            c = 5;
                            break;
                        }
                        break;
                    case -349709590:
                        if (fullName.equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                            c = 2;
                            break;
                        }
                        break;
                    case 274747385:
                        if (fullName.equals(AIApiConstants.Dialog.Finish)) {
                            c = 6;
                            break;
                        }
                        break;
                    case 978198135:
                        if (fullName.equals(AIApiConstants.SpeechSynthesizer.FinishSpeakStream)) {
                            c = 3;
                            break;
                        }
                        break;
                    case 986531076:
                        if (fullName.equals(AIApiConstants.Nlp.StartAnswer)) {
                            c = 4;
                            break;
                        }
                        break;
                    case 1327948931:
                        if (fullName.equals(AIApiConstants.SpeechRecognizer.RecognizeResult)) {
                            c = 0;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        SpeechRecognizer.RecognizeResult recognizeResult = (SpeechRecognizer.RecognizeResult) instruction.getPayload();
                        List<SpeechRecognizer.RecognizeResultItem> results = recognizeResult.getResults();
                        if (results.size() > 0) {
                            SpeechRecognizer.RecognizeResultItem recognizeResultItem = results.get(0);
                            String text = recognizeResultItem.getText();
                            if (recognizeResultItem.isNlpRequest().isPresent() && recognizeResultItem.isNlpRequest().get().booleanValue()) {
                                trackData.setTimestamp("v5.sdk.asr.recv.last.partial.for.nlp.in", System.currentTimeMillis());
                                if (!f.a(text)) {
                                    this.n = text;
                                }
                            }
                            if (trackData.getTimestamp("v5.sdk.asr.recv.first.partial.in") == 0) {
                                trackData.setTimestamp("v5.sdk.asr.recv.first.partial.in", System.currentTimeMillis());
                            }
                            if (!f.a(text)) {
                                if (this.h.get(text) == null) {
                                    this.h.put(text, Long.valueOf(System.currentTimeMillis()));
                                }
                                if (trackData.getTimestamp("v5.sdk.asr.recv.first.text.in") == 0) {
                                    trackData.setTimestamp("v5.sdk.asr.recv.first.text.in", System.currentTimeMillis());
                                }
                            }
                            if (recognizeResult.isFinal()) {
                                trackData.setTimestamp("v5.sdk.asr.recv.final.in", System.currentTimeMillis());
                                Optional<Long> beginOffset = recognizeResultItem.getBeginOffset();
                                if (beginOffset.isPresent()) {
                                    trackData.setTimestamp("v5.sdk.asr.start.talking.offset", beginOffset.get().longValue());
                                }
                                Optional<Long> endOffset = recognizeResultItem.getEndOffset();
                                if (endOffset.isPresent()) {
                                    trackData.setTimestamp("v5.sdk.asr.finish.talking.offset", endOffset.get().longValue());
                                }
                                trackData.setTimestamp("v5.sdk.asr.final.size", recognizeResultItem.getText().length());
                                if (this.h.size() > 0 && !f.a(this.n) && (l = this.h.get(this.n)) != null) {
                                    trackData.setTimestamp("v5.sdk.asr.recv.speak.finish.in", l.longValue());
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    case 1:
                        str = "v5.sdk.asr.recv.system.truncationnotification.in";
                        break;
                    case 2:
                        SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) instruction.getPayload();
                        if (speak == null || !speak.getUrl().isPresent()) {
                            if (trackData.getTimestamp("v5.sdk.nlp.recv.speak.stream.in") == 0) {
                                str = "v5.sdk.nlp.recv.speak.stream.in";
                                break;
                            } else {
                                return;
                            }
                        } else if (trackData.getTimestamp("v5.sdk.nlp.recv.speak.url.in") == 0) {
                            str = "v5.sdk.nlp.recv.speak.url.in";
                            break;
                        } else {
                            return;
                        }
                        break;
                    case 3:
                        str = "v5.sdk.tts.recv.synthesizer.finishspeakstream.in";
                        break;
                    case 4:
                        str = "v5.sdk.nlp.recv.startanswer.in";
                        break;
                    case 5:
                        str = "v5.sdk.nlp.recv.finishanswer.in";
                        break;
                    case 6:
                        str = "v5.sdk.dialog.finish.in";
                        break;
                    default:
                        return;
                }
                trackData.setTimestamp(str, System.currentTimeMillis());
            }
        }
    }

    public void g() {
        b(false);
        this.e = null;
        this.b.removeCallbacksAndMessages(null);
        this.a.quit();
    }

    public void h() {
        this.e.a();
    }

    public void i() {
        a aVar = this.d;
        if (aVar instanceof com.xiaomi.ai.android.track.a) {
            ((com.xiaomi.ai.android.track.a) aVar).b();
        }
    }

    public void a() {
        if (NetworkUtils.a(this.c.a())) {
            this.b.obtainMessage(1).sendToTarget();
        }
    }

    public void a(ObjectNode objectNode) {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            this.b.obtainMessage(2, objectNode).sendToTarget();
        }
    }

    public void a(Event event) {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            Logger.a("TrackManager", "trackEvent:" + event.getId() + Constants.ACCEPT_TIME_SEPARATOR_SP + event.getFullName(), false);
            c(event);
        }
    }

    public void a(Instruction instruction) {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            d(instruction);
        }
    }

    public void a(String str) {
        TrackData trackData;
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE) && str != null && (trackData = this.f.get(str)) != null) {
            if (trackData.getTimestamp("v5.sdk.wakeup.send.first.binary.in") == 0 || trackData.getTimestamp("v5.sdk.asr.send.recognizer.recognize.out") != 0) {
                trackData.setTimestamp(trackData.getTimestamp("v5.sdk.asr.send.first.binary.out") == 0 ? "v5.sdk.asr.send.first.binary.out" : "v5.sdk.asr.send.last.binary.out", System.currentTimeMillis());
                if (this.l == 0) {
                    this.l = System.currentTimeMillis();
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis() - this.l;
                this.l = System.currentTimeMillis();
                String str2 = this.m ? "v5.sdk.asr.send.binary.include.event.interval.out" : "v5.sdk.asr.send.binary.exclude.event.interval.out";
                if (currentTimeMillis > trackData.getTimestamp(str2)) {
                    trackData.setTimestamp(str2, currentTimeMillis);
                }
                this.m = false;
                return;
            }
            trackData.setTimestamp(trackData.getTimestamp("v5.sdk.wakeup.send.first.binary.out") == 0 ? "v5.sdk.wakeup.send.first.binary.out" : "v5.sdk.wakeup.send.last.binary.out", System.currentTimeMillis());
        }
    }

    public void a(String str, long j) {
        TrackData trackData;
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE) && str != null && (trackData = this.f.get(str)) != null && trackData.getTimestamp("v5.sdk.asr.send.recognizer.recognize.out") != 0) {
            if (j > 0 && j > trackData.getTimestamp("v5.sdk.asr.send.binary.max.delay")) {
                trackData.setTimestamp("v5.sdk.asr.send.binary.max.delay", j);
            }
            trackData.setTimestamp(trackData.getTimestamp("v5.sdk.asr.send.first.binary.succ") == 0 ? "v5.sdk.asr.send.first.binary.succ" : "v5.sdk.asr.send.last.binary.succ", System.currentTimeMillis());
        }
    }

    public void a(String str, boolean z) {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE) && str != null) {
            b(str, z);
        }
    }

    public void a(boolean z) {
        synchronized (i.class) {
            if (!this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
                Logger.c("TrackManager", "postTrackInfo: track not enable");
                return;
            }
            Logger.b("TrackManager", "postTrackInfo " + z);
            this.b.obtainMessage(0, Boolean.valueOf(z)).sendToTarget();
        }
    }

    public void b() {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            this.b.obtainMessage(5).sendToTarget();
        }
    }

    public void b(Event event) {
        TrackData trackData;
        String str;
        String id = event.getId();
        Logger.a("TrackManager", id);
        if (!f.a(id) && (trackData = this.f.get(id)) != null) {
            if (AIApiConstants.SpeechRecognizer.Recognize.equals(event.getFullName())) {
                str = "v5.sdk.asr.send.recognizer.recognize.out";
            } else if (AIApiConstants.SpeechRecognizer.RecognizeStreamFinished.equals(event.getFullName())) {
                str = "v5.sdk.asr.send.recognizer.recognizefinished.out";
            } else if (AIApiConstants.General.ContextUpdate.equals(event.getFullName())) {
                this.m = true;
                return;
            } else {
                return;
            }
            trackData.setTimestamp(str, System.currentTimeMillis());
        }
    }

    public void b(Instruction instruction) {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            e(instruction);
        }
    }

    public void c() {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            Logger.b("TrackManager", "release:remain track data num:" + this.d.d());
            this.b.obtainMessage(4).sendToTarget();
        }
    }

    public void c(Instruction instruction) {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            f(instruction);
        }
    }

    public void d() {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            String c = this.c.e().c();
            if (!f.a(c)) {
                c(c);
            }
        }
    }

    public void e() {
        if (this.c.b().getBoolean(AivsConfig.Track.ENABLE)) {
            String c = this.c.e().c();
            if (!f.a(c)) {
                d(c);
            }
        }
    }

    public a f() {
        return this.d;
    }
}
