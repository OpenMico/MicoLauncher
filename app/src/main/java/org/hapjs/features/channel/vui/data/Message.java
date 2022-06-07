package org.hapjs.features.channel.vui.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import io.netty.handler.codec.rtsp.RtspHeaders;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
/* loaded from: classes5.dex */
public class Message<T> {
    public static int CODE_DEFAULT = 0;
    public static int MODE_DEFAULT = 1;
    public static int RESP_DEFAULT = -1;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = CodeFilter.class)
    @JsonProperty("code")
    private int a;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = RespFilter.class)
    @JsonProperty("resp")
    private long b;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = ModeFilter.class)
    @JsonProperty("mode")
    private int c;
    @JsonProperty(SchemaActivity.KEY_PAYLOAD)
    private T d;
    @JsonProperty("action")
    private String e;
    @JsonProperty("message")
    private String f;
    @JsonProperty(RtspHeaders.Values.SEQ)
    private long g;

    public void setMode(int i) {
        this.c = i;
    }

    public int getMode() {
        return this.c;
    }

    public void setCode(int i) {
        this.a = i;
    }

    public int getCode() {
        return this.a;
    }

    public void setResp(long j) {
        this.b = j;
    }

    public long getResp() {
        return this.b;
    }

    public void setPayload(T t) {
        this.d = t;
    }

    public T getPayload() {
        return this.d;
    }

    public void setAction(String str) {
        this.e = str;
    }

    public String getAction() {
        return this.e;
    }

    public void setMessage(String str) {
        this.f = str;
    }

    public String getMessage() {
        return this.f;
    }

    public void setSeq(long j) {
        this.g = j;
    }

    public long getSeq() {
        return this.g;
    }

    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "error parse";
        }
    }
}
