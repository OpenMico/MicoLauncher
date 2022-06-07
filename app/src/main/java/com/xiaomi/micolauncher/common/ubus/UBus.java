package com.xiaomi.micolauncher.common.ubus;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.xiaomi.mico.common.Gsons;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class UBus {

    /* loaded from: classes3.dex */
    public interface UbusHandler {
        boolean canHandle(Context context, String str, String str2);

        String handle(Context context, String str, String str2, String str3);
    }

    /* loaded from: classes3.dex */
    public static class Response {
        public int code;
        public String info;

        public String toString() {
            return Gsons.getGson().toJson(this);
        }
    }

    /* loaded from: classes3.dex */
    public static class InternalResponse {
        public int code;
        public String data;
        @Expose(deserialize = false, serialize = false)
        private HashMap<String, Object> params = new HashMap<>();

        public String toString() {
            this.data = Gsons.getGson().toJson(this.params);
            return Gsons.getGson().toJson(this);
        }

        public void put(String str, Object obj) {
            this.params.put(str, obj);
        }
    }

    /* loaded from: classes3.dex */
    public static class ResponseTemplate<T> {
        public static final Gson GSON_EXCLUDE_DATA = createExcludeFieldStrategy("data");
        public static final Gson GSON_EXCLUDE_INFO = createExcludeFieldStrategy("info");
        static final String GSON_FIELD_NAME = "gson";
        public int code;
        public T data;
        private final Gson gson;
        public String info;

        private static Gson createExcludeFieldStrategy(final String str) {
            return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() { // from class: com.xiaomi.micolauncher.common.ubus.UBus.ResponseTemplate.1
                @Override // com.google.gson.ExclusionStrategy
                public boolean shouldSkipClass(Class<?> cls) {
                    return false;
                }

                @Override // com.google.gson.ExclusionStrategy
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    String name = fieldAttributes.getName();
                    return name.equals(str) || name.equals(ResponseTemplate.GSON_FIELD_NAME) || fieldAttributes.hasModifier(8);
                }
            }).create();
        }

        public ResponseTemplate(@NonNull Gson gson) {
            if (gson == GSON_EXCLUDE_DATA || gson == GSON_EXCLUDE_INFO) {
                this.gson = gson;
                return;
            }
            throw new IllegalArgumentException("MUST be GSON_EXCLUDE_DATA or GSON_EXCLUDE_INFO");
        }

        public String toString() {
            return this.gson.toJson(this);
        }
    }

    /* loaded from: classes3.dex */
    public static class OtaInfo {
        public String checksum;
        public String extra;
        public String hardware;
        public String url;
        public String version;

        public boolean isValid() {
            return !TextUtils.isEmpty(this.extra) && !TextUtils.isEmpty(this.checksum) && !TextUtils.isEmpty(this.version) && !TextUtils.isEmpty(this.url) && !TextUtils.isEmpty(this.hardware);
        }
    }

    /* loaded from: classes3.dex */
    public static class InternalResponseV2 {
        public int code;
        private HashMap<String, Object> data = new HashMap<>();

        public String toString() {
            return Gsons.getGson().toJson(this);
        }

        public void put(String str, Object obj) {
            this.data.put(str, obj);
        }
    }
}
