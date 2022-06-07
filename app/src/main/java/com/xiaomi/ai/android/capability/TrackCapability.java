package com.xiaomi.ai.android.capability;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.xiaomi.ai.android.core.Engine;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.android.core.i;
import com.xiaomi.ai.android.utils.a;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import java.io.IOException;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class TrackCapability implements Capability {
    protected d a;
    protected volatile boolean b = false;
    private String c;
    private boolean d;

    public TrackCapability(Engine engine) {
        this.d = false;
        this.a = (d) engine;
        int i = this.a.b().getInt(AivsConfig.ENV);
        this.c = i == 2 ? "staging" : i == 1 ? "preview" : SchemaActivity.VALUE_ENV_PRODUCTION;
        this.d = this.a.b().getBoolean(AivsConfig.LimitedDiskCache.ENABLE);
    }

    private ArrayNode a(String str) {
        ArrayNode arrayNode;
        synchronized (i.class) {
            arrayNode = null;
            String b = b(str);
            if (!f.a(b)) {
                try {
                    arrayNode = APIUtils.getObjectMapper().createArrayNode();
                    ArrayNode arrayNode2 = (ArrayNode) APIUtils.getObjectMapper().readTree(b);
                    if (arrayNode2 != null && arrayNode2.size() > 0) {
                        Iterator<JsonNode> it = arrayNode2.iterator();
                        while (it.hasNext()) {
                            arrayNode.addAll((ArrayNode) APIUtils.getObjectMapper().readTree(it.next().asText()));
                        }
                    }
                    Logger.b("TrackCapability", "readLocal  key:" + str + " ,size = " + arrayNode.size());
                } catch (IOException e) {
                    Logger.d("TrackCapability", Log.getStackTraceString(e));
                }
            }
            removeKeyValue(str);
        }
        return arrayNode;
    }

    private void a(String str, String str2) {
        com.xiaomi.ai.android.track.f a = com.xiaomi.ai.android.track.f.a();
        Context a2 = this.a.a();
        a.a(a2, "aivs_track", this.c + str, str2, this.d);
    }

    private boolean a(String str, String str2, ArrayNode arrayNode) {
        synchronized (i.class) {
            if (f.a(str)) {
                Logger.b("TrackCapability", "saveTrackData :empty key");
                return false;
            } else if (!f.a(str2) || !(arrayNode == null || arrayNode.size() == 0)) {
                ArrayNode arrayNode2 = null;
                String b = b(str);
                if (!f.a(b)) {
                    try {
                        arrayNode2 = (ArrayNode) APIUtils.getObjectMapper().readTree(b);
                    } catch (IOException e) {
                        removeKeyValue(str);
                        Logger.d("TrackCapability", Log.getStackTraceString(e));
                    }
                }
                if (arrayNode2 == null) {
                    arrayNode2 = APIUtils.getObjectMapper().createArrayNode();
                }
                if (!f.a(str2)) {
                    arrayNode2.add(str2);
                }
                if (arrayNode != null && arrayNode.size() > 0) {
                    arrayNode2.addAll(arrayNode);
                }
                long j = this.a.b().getLong(AivsConfig.Track.MAX_LOCAL_TRACK_LENGTH);
                Logger.a("TrackCapability", "saveTrackData " + str + " maxLocalTackLength: " + j);
                if (a.a(arrayNode2) > j) {
                    Logger.c("TrackCapability", str + ",save error: cache full !!! ");
                    return false;
                }
                a(str, arrayNode2.toString());
                Logger.a("TrackCapability", str + ",save success: array:" + arrayNode2);
                return true;
            } else {
                Logger.b("TrackCapability", "saveTrackData :empty");
                return false;
            }
        }
    }

    private String b(String str) {
        com.xiaomi.ai.android.track.f a = com.xiaomi.ai.android.track.f.a();
        Context a2 = this.a.a();
        return a.a(a2, "aivs_track", this.c + str, this.d);
    }

    public abstract boolean onEventTrack(String str);

    public ArrayNode readLocalCache() {
        return a("track_cached_info");
    }

    public ArrayNode readLocalFailData() {
        ArrayNode a = a("track_failed_info");
        this.b = false;
        return a;
    }

    public void removeKeyValue(String str) {
        com.xiaomi.ai.android.track.f a = com.xiaomi.ai.android.track.f.a();
        Context a2 = this.a.a();
        a.b(a2, "aivs_track", this.c + str, this.d);
    }

    public void saveFailData(String str) {
        if (a("track_failed_info", str, null)) {
            this.b = true;
        }
    }

    public boolean saveTrackData(ArrayNode arrayNode) {
        return a("track_cached_info", null, arrayNode);
    }
}
