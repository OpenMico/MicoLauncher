package com.xiaomi.ai.android.core;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechRecognizer;
import com.xiaomi.ai.api.SpeechWakeup;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.api.common.Event;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.common.Optional;
import com.xiaomi.mipush.sdk.Constants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class e {
    private d a;
    private String c;
    private Map<String, Event> b = new ConcurrentHashMap();
    private Set<String> d = new HashSet<String>() { // from class: com.xiaomi.ai.android.core.e.1
        {
            e.this = this;
            add(AIApiConstants.SpeechRecognizer.Cancel);
            add(AIApiConstants.System.Ack);
            add(AIApiConstants.Settings.GlobalConfig);
            add(AIApiConstants.General.ContextUpdate);
        }
    };
    private Set<String> e = new HashSet<String>() { // from class: com.xiaomi.ai.android.core.e.2
        {
            e.this = this;
            add(AIApiConstants.General.Push);
        }
    };

    public e(d dVar) {
        this.a = dVar;
    }

    private boolean c(Event event) {
        SpeechWakeup.Wakeup wakeup;
        T payload = event.getPayload();
        return AIApiConstants.SpeechWakeup.Wakeup.equals(event.getFullName()) && (payload instanceof SpeechWakeup.Wakeup) && (wakeup = (SpeechWakeup.Wakeup) payload) != null && wakeup.isRecognizeFollowed() != null && wakeup.isRecognizeFollowed().isPresent() && wakeup.isRecognizeFollowed().get().booleanValue();
    }

    public void a() {
        Iterator<Map.Entry<String, Event>> it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            Event value = it.next().getValue();
            this.a.c().obtainMessage(2, APIUtils.buildEvent(new SpeechRecognizer.Cancel(), null, value.getId())).sendToTarget();
            Logger.b("EventManager", "interrupt: cancel eventId=" + value.getId());
            it.remove();
        }
    }

    public void a(Event event) {
        if (!this.d.contains(event.getFullName())) {
            this.b.put(event.getId(), event);
        }
        if (b(event)) {
            this.c = event.getId();
        }
    }

    public boolean a(Instruction instruction) {
        Optional<String> dialogId = instruction.getDialogId();
        if (dialogId == null) {
            Logger.d("EventManager", "process:dialogId is null," + instruction.getFullName());
            return true;
        } else if (!dialogId.isPresent() || this.e.contains(instruction.getFullName())) {
            return true;
        } else {
            String str = dialogId.get();
            if (this.b.get(str) == null) {
                Logger.c("EventManager", "process: not found for instruction " + instruction.getFullName() + Constants.ACCEPT_TIME_SEPARATOR_SP + str);
                return false;
            }
            if (AIApiConstants.Dialog.Finish.equals(instruction.getFullName())) {
                Logger.b("EventManager", "process: remove eventId=" + str);
                this.b.remove(str);
                String str2 = this.c;
                if (str2 != null && str2.equals(str)) {
                    this.c = null;
                }
            }
            return true;
        }
    }

    public boolean a(String str) {
        return str != null && this.b.containsKey(str);
    }

    public void b() {
        Logger.b("EventManager", "release: recorded event count=" + this.b.size());
        this.b.clear();
        this.c = null;
    }

    public boolean b(Event event) {
        return AIApiConstants.SpeechRecognizer.Recognize.equals(event.getFullName()) || AIApiConstants.SpeechSynthesizer.Synthesize.equals(event.getFullName()) || AIApiConstants.Nlp.Request.equals(event.getFullName()) || c(event);
    }

    public String c() {
        return this.c;
    }
}
