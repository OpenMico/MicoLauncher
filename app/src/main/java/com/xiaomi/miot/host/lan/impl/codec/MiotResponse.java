package com.xiaomi.miot.host.lan.impl.codec;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MiotResponse implements MiotMessage {
    private JSONObject content;
    private JSONObject error;
    private int id;
    private String originResponse;

    public String getOriginResponse() {
        return this.originResponse;
    }

    public void setOriginResponse(String str) {
        this.originResponse = str;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotMessage
    public void setId(int i) {
        this.id = i;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotMessage
    public int getId() {
        return this.id;
    }

    @Override // com.xiaomi.miot.host.lan.impl.codec.MiotMessage
    public MiotMessageType getType() {
        return MiotMessageType.RESPONSE;
    }

    public JSONObject getContent() {
        return this.content;
    }

    public void setContent(JSONObject jSONObject) {
        this.content = jSONObject;
    }

    public boolean hasError() {
        return this.error != null;
    }

    public JSONObject getError() {
        return this.error;
    }

    public void setError(JSONObject jSONObject) {
        this.error = jSONObject;
    }
}
