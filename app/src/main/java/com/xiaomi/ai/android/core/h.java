package com.xiaomi.ai.android.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.log.Logger;

/* loaded from: classes2.dex */
public class h {
    private ObjectNode a;
    private ObjectNode b;
    private ObjectNode c;
    private ObjectMapper d = APIUtils.getObjectMapper();

    public synchronized void a() {
        this.a = this.d.createObjectNode();
        this.b = this.d.createObjectNode();
        this.c = this.d.createObjectNode();
    }

    public synchronized void a(String str) {
        a(str, System.currentTimeMillis());
    }

    public synchronized void a(String str, long j) {
        if (this.b != null) {
            this.b.put(str, j);
        } else {
            Logger.b("TraceManager", "traceTimeStamps: not startTrace");
        }
    }

    public synchronized void a(String str, String str2) {
        if (this.c != null) {
            this.c.put(str, str2);
        } else {
            Logger.b("TraceManager", "traceResult2: not startTrace");
        }
    }

    public synchronized ObjectNode b() {
        if (this.a == null || this.b == null || this.c == null) {
            return null;
        }
        a(TraceConstants.TimeStamp.EXEC.FINISH_TRACE);
        this.a.set("result", this.c);
        this.a.set("timestamps", this.b);
        ObjectNode deepCopy = this.a.deepCopy();
        this.b.removeAll();
        this.c.removeAll();
        this.a.removeAll();
        this.c = null;
        this.b = null;
        this.a = null;
        return deepCopy;
    }

    public synchronized void b(String str) {
        if (this.a != null) {
            this.a.put("eventId", str);
        } else {
            Logger.b("TraceManager", "traceRequestId: not startTrace");
        }
    }

    public synchronized void b(String str, long j) {
        if (this.c != null) {
            this.c.put(str, j);
        } else {
            Logger.b("TraceManager", "traceResult: not startTrace");
        }
    }
}
