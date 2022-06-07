package com.xiaomi.ai.track;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;

/* loaded from: classes3.dex */
public abstract class a {
    private ObjectNode a;

    public a(com.xiaomi.ai.core.a aVar) {
        a(aVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(com.xiaomi.ai.core.a r5) {
        /*
            r4 = this;
            com.xiaomi.ai.core.AivsConfig r0 = r5.getAivsConfig()
            com.fasterxml.jackson.databind.ObjectMapper r1 = com.xiaomi.ai.api.common.APIUtils.getObjectMapper()
            com.fasterxml.jackson.databind.node.ObjectNode r1 = r1.createObjectNode()
            r4.a = r1
            com.fasterxml.jackson.databind.node.ObjectNode r1 = r4.a
            java.lang.String r2 = "app_id"
            java.lang.String r3 = "auth.client_id"
            java.lang.String r3 = r0.getString(r3)
            r1.put(r2, r3)
            com.xiaomi.ai.api.Settings$ClientInfo r1 = r5.getClientInfo()
            com.xiaomi.common.Optional r1 = r1.getDeviceId()
            boolean r1 = r1.isPresent()
            if (r1 == 0) goto L_0x003e
            com.fasterxml.jackson.databind.node.ObjectNode r1 = r4.a
            java.lang.String r2 = "did"
            com.xiaomi.ai.api.Settings$ClientInfo r3 = r5.getClientInfo()
            com.xiaomi.common.Optional r3 = r3.getDeviceId()
            java.lang.Object r3 = r3.get()
            java.lang.String r3 = (java.lang.String) r3
            r1.put(r2, r3)
        L_0x003e:
            com.fasterxml.jackson.databind.ObjectMapper r1 = com.xiaomi.ai.api.common.APIUtils.getObjectMapper()
            com.fasterxml.jackson.databind.node.ObjectNode r1 = r1.createObjectNode()
            com.fasterxml.jackson.databind.node.ObjectNode r2 = r4.a
            java.lang.String r3 = "env"
            r2.set(r3, r1)
            java.lang.String r2 = "log.version"
            java.lang.String r3 = "3.0"
            r1.put(r2, r3)
            java.lang.String r2 = "aivs.env"
            int r2 = r0.getInt(r2)
            r3 = 2
            if (r2 != r3) goto L_0x0065
            java.lang.String r2 = "cloud"
            java.lang.String r3 = "staging"
        L_0x0061:
            r1.put(r2, r3)
            goto L_0x007c
        L_0x0065:
            r3 = 1
            if (r2 != r3) goto L_0x006d
            java.lang.String r2 = "cloud"
            java.lang.String r3 = "preview"
            goto L_0x0061
        L_0x006d:
            if (r2 != 0) goto L_0x0074
            java.lang.String r2 = "cloud"
            java.lang.String r3 = "production"
            goto L_0x0061
        L_0x0074:
            r3 = 3
            if (r2 != r3) goto L_0x007c
            java.lang.String r2 = "cloud"
            java.lang.String r3 = "preview4test"
            goto L_0x0061
        L_0x007c:
            java.lang.String r2 = "authmode"
            com.xiaomi.ai.a.a r3 = r5.getAuthProvider()
            int r3 = r3.b()
            r1.put(r2, r3)
            java.lang.String r2 = "sdk.type"
            java.lang.String r3 = "java"
            r1.put(r2, r3)
            java.lang.String r2 = "sdk.vad.type"
            java.lang.String r3 = "asr.vad_type"
            int r0 = r0.getInt(r3)
            r1.put(r2, r0)
            com.xiaomi.ai.api.Settings$ClientInfo r0 = r5.getClientInfo()
            com.xiaomi.common.Optional r0 = r0.getEngineId()
            boolean r0 = r0.isPresent()
            if (r0 == 0) goto L_0x00bc
            java.lang.String r0 = "engine.id"
            com.xiaomi.ai.api.Settings$ClientInfo r5 = r5.getClientInfo()
            com.xiaomi.common.Optional r5 = r5.getEngineId()
            java.lang.Object r5 = r5.get()
            java.lang.String r5 = (java.lang.String) r5
            r1.put(r0, r5)
        L_0x00bc:
            com.fasterxml.jackson.databind.node.ObjectNode r5 = r4.a
            java.lang.String r0 = "data"
            com.fasterxml.jackson.databind.ObjectMapper r1 = com.xiaomi.ai.api.common.APIUtils.getObjectMapper()
            com.fasterxml.jackson.databind.node.ArrayNode r1 = r1.createArrayNode()
            r5.set(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.track.a.a(com.xiaomi.ai.core.a):void");
    }

    public TrackData a() {
        ArrayNode arrayNode;
        TrackData trackData;
        synchronized (this) {
            if (this.a.path("data").isArray()) {
                arrayNode = (ArrayNode) this.a.path("data");
            } else {
                arrayNode = APIUtils.getObjectMapper().createArrayNode();
                this.a.set("data", arrayNode);
            }
            trackData = new TrackData(this, true);
            arrayNode.add(trackData.getJsonNode());
        }
        return trackData;
    }

    public void a(TrackData trackData) {
        ArrayNode arrayNode;
        synchronized (this) {
            if (this.a.path("data").isArray()) {
                arrayNode = (ArrayNode) this.a.path("data");
            } else {
                arrayNode = APIUtils.getObjectMapper().createArrayNode();
                this.a.set("data", arrayNode);
            }
            arrayNode.add(trackData.getJsonNode());
        }
    }

    public void a(String str, int i) {
        synchronized (this) {
            ((ObjectNode) this.a.path(SchemaActivity.KEY_ENV)).put(str, i);
        }
    }

    public void a(String str, String str2) {
        synchronized (this) {
            ((ObjectNode) this.a.path(SchemaActivity.KEY_ENV)).put(str, str2);
        }
    }

    public void c() {
        synchronized (this) {
            if (this.a.path("data").isArray()) {
                this.a.remove("data");
            }
        }
    }

    public int d() {
        int size;
        synchronized (this) {
            size = this.a.path("data").isArray() ? this.a.path("data").size() : 0;
        }
        return size;
    }

    public JsonNode e() {
        return this.a;
    }

    public String toString() {
        String jsonString;
        synchronized (this) {
            try {
                try {
                    jsonString = APIUtils.toJsonString(this.a);
                } catch (JsonProcessingException e) {
                    Logger.d("TrackInfo", Logger.throwableToString(e));
                    return super.toString();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return jsonString;
    }
}
