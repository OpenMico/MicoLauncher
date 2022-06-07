package com.xiaomi.ai.track;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.log.Logger;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class TrackData {
    private ObjectNode a = APIUtils.getObjectMapper().createObjectNode();
    private a b;
    private boolean c;

    public TrackData(a aVar) {
        this.b = aVar;
        this.a.put("type", "client.perf.log.keep-alive");
    }

    public TrackData(a aVar, boolean z) {
        this.b = aVar;
        this.a.put("type", "client.perf.log.keep-alive");
        this.c = z;
    }

    public void addConnectProcess(ObjectNode objectNode) {
        ArrayNode arrayNode;
        synchronized (this) {
            if (this.a.path("sdk.connect.process").isArray()) {
                arrayNode = (ArrayNode) this.a.path("sdk.connect.process");
            } else {
                arrayNode = APIUtils.getObjectMapper().createArrayNode();
                this.a.set("sdk.connect.process", arrayNode);
            }
            arrayNode.add(objectNode);
        }
    }

    public void finishTrack() {
        Logger.b("TrackData", "finishTrack:mFinished=" + this.c);
        synchronized (this) {
            if (!this.c) {
                this.c = true;
                this.b.a(this);
            }
        }
    }

    public JsonNode getJsonNode() {
        return this.a;
    }

    public String getString(String str) {
        synchronized (this) {
            JsonNode path = this.a.path(str);
            if (path == null || !path.isTextual()) {
                return null;
            }
            return path.asText();
        }
    }

    public long getTimestamp(String str) {
        synchronized (this) {
            if (!this.a.path("timestamps").isObject()) {
                return 0L;
            }
            return ((ObjectNode) this.a.path("timestamps")).path(str).asLong(0L);
        }
    }

    public a getTrackInfo() {
        return this.b;
    }

    public boolean isFinished() {
        return this.c;
    }

    public void mergeAppData(ObjectNode objectNode) {
        synchronized (this) {
            if (objectNode.path("timestamps").isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fields = objectNode.path("timestamps").fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> next = fields.next();
                    setTimestamp(next.getKey(), next.getValue().asLong());
                }
            }
            if (objectNode.path("result").isObject()) {
                this.a.set("result", objectNode.path("result"));
            }
        }
    }

    public void set(String str, int i) {
        synchronized (this) {
            this.a.put(str, i);
        }
    }

    public void set(String str, String str2) {
        synchronized (this) {
            this.a.put(str, str2);
        }
    }

    public void setTimestamp(String str, long j) {
        ObjectNode objectNode;
        synchronized (this) {
            if (this.a.path("timestamps").isObject()) {
                objectNode = (ObjectNode) this.a.path("timestamps");
            } else {
                objectNode = APIUtils.getObjectMapper().createObjectNode();
                this.a.set("timestamps", objectNode);
            }
            objectNode.put(str, j);
        }
    }

    public String toString() {
        String aVar;
        synchronized (this) {
            aVar = this.b.toString();
        }
        return aVar;
    }
}
