package com.xiaomi.ai.android.track;

import android.text.TextUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.xiaomi.ai.android.capability.TrackCapability;
import com.xiaomi.ai.android.track.b;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.track.a;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class d extends b {
    private a e;
    private final ArrayNode f = APIUtils.getObjectMapper().createArrayNode();
    private com.xiaomi.ai.android.core.d g;
    private int h;

    public d(com.xiaomi.ai.android.core.d dVar, a aVar, b.c cVar) {
        super(dVar.b().getInt(AivsConfig.Track.CACHE_PERIOD_CHECK_INTERVAL, 10), dVar.b().getInt(AivsConfig.Track.DISK_PERIOD_CHECK_INTERVAL, 1200), true, cVar);
        this.g = dVar;
        this.e = aVar;
        this.h = dVar.b().getInt(AivsConfig.Track.MAX_TRACK_DATA_SIZE);
    }

    private Boolean a(JsonNode jsonNode) {
        boolean z;
        if (jsonNode == null || TextUtils.isEmpty(jsonNode.textValue())) {
            z = true;
        } else {
            Logger.b("InternalTrackStrategy", "sendTrackInfo: ");
            TrackCapability trackCapability = (TrackCapability) this.g.a(TrackCapability.class);
            if (trackCapability != null) {
                z = trackCapability.onEventTrack(jsonNode.textValue());
            } else {
                Logger.d("InternalTrackStrategy", "TrackCapability was not registered");
                z = false;
            }
        }
        return Boolean.valueOf(z);
    }

    private ArrayNode h() {
        ArrayNode createArrayNode;
        String arrayNode;
        synchronized (this.f) {
            if (this.e.d() > 0) {
                this.f.add(this.e.e().deepCopy());
                this.e.c();
            }
            createArrayNode = APIUtils.getObjectMapper().createArrayNode();
            if (this.f.size() > this.h) {
                ArrayNode createArrayNode2 = APIUtils.getObjectMapper().createArrayNode();
                Iterator<JsonNode> it = this.f.iterator();
                while (it.hasNext()) {
                    createArrayNode2.add(it.next());
                    if (createArrayNode2.size() == this.h) {
                        createArrayNode.add(createArrayNode2.toString());
                        createArrayNode2.removeAll();
                    }
                }
                if (createArrayNode2.size() > 0) {
                    arrayNode = createArrayNode2.toString();
                }
                this.f.removeAll();
            } else {
                arrayNode = this.f.toString();
            }
            createArrayNode.add(arrayNode);
            this.f.removeAll();
        }
        return createArrayNode;
    }

    private int i() {
        if (this.e.d() >= this.g.b().getInt(AivsConfig.Track.MAX_TRACK_INTERNAL_DATA_SIZE)) {
            this.f.add(this.e.e().deepCopy());
            this.e.c();
        }
        return this.f.size();
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean b() {
        return i() < this.h;
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean c() {
        ArrayNode h = h();
        if (h == null || h.size() == 0) {
            return true;
        }
        Iterator<JsonNode> it = h.iterator();
        while (true) {
            boolean z = true;
            while (it.hasNext()) {
                JsonNode next = it.next();
                if (!z || !a(next).booleanValue()) {
                    z = false;
                }
            }
            return false;
        }
    }

    @Override // com.xiaomi.ai.android.track.b
    protected void d() {
        Logger.a("InternalTrackStrategy", "readLocalCache");
        TrackCapability trackCapability = (TrackCapability) this.g.a(TrackCapability.class);
        if (trackCapability != null) {
            ArrayNode readLocalCache = trackCapability.readLocalCache();
            if (readLocalCache != null && readLocalCache.size() > 0) {
                this.f.addAll(readLocalCache);
                return;
            }
            return;
        }
        Logger.b("InternalTrackStrategy", "readLocalCache error:empty TrackCapability ");
        throw new IllegalArgumentException("readLocalCache error: TrackCapability was not registered");
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean e() {
        TrackCapability trackCapability = (TrackCapability) this.g.a(TrackCapability.class);
        if (trackCapability != null) {
            ArrayNode readLocalFailData = trackCapability.readLocalFailData();
            if (readLocalFailData == null || readLocalFailData.size() <= 0) {
                return false;
            }
            this.f.addAll(readLocalFailData);
            return true;
        }
        throw new IllegalArgumentException("readLocalTrackData error:TrackCapability null");
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean f() {
        int d = this.e.d();
        int size = this.f.size();
        Logger.a("InternalTrackStrategy", "isTrackDataEmpty ,bufferedTrackDataNum=" + d + ",bufferedTrackInfoNum=" + size);
        return d == 0 && size == 0;
    }

    @Override // com.xiaomi.ai.android.track.b
    protected boolean g() {
        ArrayNode h = h();
        TrackCapability trackCapability = (TrackCapability) this.g.a(TrackCapability.class);
        if (trackCapability != null) {
            return trackCapability.saveTrackData(h);
        }
        return false;
    }
}
