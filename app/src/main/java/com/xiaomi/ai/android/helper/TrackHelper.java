package com.xiaomi.ai.android.helper;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.android.track.b;
import com.xiaomi.ai.android.track.c;
import com.xiaomi.ai.android.utils.NetworkUtils;
import com.xiaomi.ai.api.StdStatuses;
import com.xiaomi.ai.api.TrackLogV3;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.error.AivsError;
import com.xiaomi.ai.log.Logger;
import java.io.IOException;

/* loaded from: classes2.dex */
public class TrackHelper {
    private OnErrorListener a;
    private c b;

    /* loaded from: classes2.dex */
    public interface OnErrorListener {
        void onError(AivsError aivsError);
    }

    public TrackHelper(Context context, String str, AivsConfig aivsConfig) {
        this.b = new c(context, str, aivsConfig, new b.c() { // from class: com.xiaomi.ai.android.helper.TrackHelper.1
            @Override // com.xiaomi.ai.android.track.b.c
            public void a() {
            }

            @Override // com.xiaomi.ai.android.track.b.c
            public void a(AivsError aivsError) {
                if (TrackHelper.this.a != null) {
                    TrackHelper.this.a.onError(aivsError);
                }
            }
        });
        if (TextUtils.isEmpty(str)) {
            Logger.d("TrackHelper", "TrackHelper:authorization is empty");
        }
        if (NetworkUtils.a(context)) {
            this.b.a();
        }
    }

    private ObjectNode a(TrackLogV3.TrackLog trackLog) {
        if (trackLog == null) {
            return null;
        }
        try {
            return (ObjectNode) APIUtils.getObjectMapper().readTree(APIUtils.toJsonString(trackLog));
        } catch (JsonProcessingException e) {
            OnErrorListener onErrorListener = this.a;
            if (onErrorListener != null) {
                onErrorListener.onError(new AivsError(StdStatuses.MISSING_PARAMETER, "required field not set"));
            }
            Logger.d("TrackHelper", Log.getStackTraceString(e));
            Logger.d("TrackHelper", "convert event_params to string failed");
            return null;
        } catch (IOException e2) {
            Logger.d("TrackHelper", Log.getStackTraceString(e2));
            return null;
        }
    }

    private synchronized boolean a(JsonNode jsonNode, boolean z) {
        if (jsonNode == null && z) {
            Logger.c("TrackHelper", "can not post empty data with wait more");
        }
        if (jsonNode != null) {
            this.b.a(jsonNode);
        }
        Logger.a("TrackHelper", "postTrackData: waitMore=" + z);
        return this.b.a(z);
    }

    public void clearTrackData() {
        Logger.b("TrackHelper", "clearTrackData");
        this.b.i();
    }

    public boolean postTrackData(TrackLogV3.TrackLog trackLog, boolean z) {
        return a(a(trackLog), z);
    }

    public void release() {
        Logger.b("TrackHelper", "release");
        this.b.a(false);
    }

    public void setAuthorization(String str) {
        this.b.a(str);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.a = onErrorListener;
    }
}
