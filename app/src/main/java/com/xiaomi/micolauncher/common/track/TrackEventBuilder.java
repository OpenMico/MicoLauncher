package com.xiaomi.micolauncher.common.track;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.android.helper.TrackHelper;
import com.xiaomi.ai.api.TrackLogV3;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.micolauncher.common.L;
import java.io.IOException;

/* loaded from: classes3.dex */
public class TrackEventBuilder {
    private String a;
    private EventType b;
    private ExtendJsonWrapper c;
    private String d;

    public TrackEventBuilder setWidget(String str) {
        this.a = str;
        return this;
    }

    public TrackEventBuilder setEventType(EventType eventType) {
        this.b = eventType;
        return this;
    }

    public TrackEventBuilder setExtendJsonWrapper(ExtendJsonWrapper extendJsonWrapper) {
        this.c = extendJsonWrapper;
        return this;
    }

    public TrackEventBuilder setEventDataType(String str) {
        this.d = str;
        return this;
    }

    private TrackLogV3.ScreenBoxEventType a(EventType eventType) {
        return TrackLogV3.ScreenBoxEventType.valueOf(eventType.name());
    }

    public void post() {
        post(true);
    }

    public void post(boolean z) {
        TrackLogV3.ScreenBoxEventContext a = TrackLog.shareInstance().a();
        if (a != null) {
            a(a(a(this.b), a(this.a, this.d, this.c.getEventExtendJson(), a)), z);
            this.c.recycle();
        }
    }

    private TrackLogV3.ScreenBoxTrackLog a(TrackLogV3.ScreenBoxEventType screenBoxEventType, TrackLogV3.ScreenBoxEventParams screenBoxEventParams) {
        TrackLogV3.ScreenBoxTrackLog screenBoxTrackLog = new TrackLogV3.ScreenBoxTrackLog();
        screenBoxTrackLog.setEventType(screenBoxEventType);
        screenBoxTrackLog.setEventParams(screenBoxEventParams);
        return screenBoxTrackLog;
    }

    private TrackLogV3.ScreenBoxEventParams a(String str, String str2, TrackLogV3.ScreenBoxExtendJson screenBoxExtendJson, TrackLogV3.ScreenBoxEventContext screenBoxEventContext) {
        TrackLogV3.ScreenBoxEventParams screenBoxEventParams = new TrackLogV3.ScreenBoxEventParams();
        screenBoxEventParams.setEventContext(screenBoxEventContext);
        screenBoxEventParams.setTimestamp(System.currentTimeMillis());
        screenBoxEventParams.setEventDataType(str2);
        screenBoxEventParams.setExtendJson(screenBoxExtendJson);
        screenBoxEventParams.setWidget(str);
        return screenBoxEventParams;
    }

    private void a(TrackLogV3.ScreenBoxTrackLog screenBoxTrackLog, boolean z) {
        TrackLogV3.TrackLog trackLog = new TrackLogV3.TrackLog();
        trackLog.setDataType(TrackLogV3.DataType.SCREEN_BOX);
        ObjectNode a = a(screenBoxTrackLog);
        if (a != null) {
            trackLog.setData(a);
            TrackHelper b = TrackLog.shareInstance().b();
            if (b != null) {
                b.postTrackData(trackLog, z);
            } else {
                L.base.e("trackHelper is empty");
            }
        } else {
            L.base.i("build object node fail");
        }
    }

    private ObjectNode a(TrackLogV3.ScreenBoxTrackLog screenBoxTrackLog) {
        try {
            return (ObjectNode) APIUtils.getObjectMapper().readTree(APIUtils.toJsonString(screenBoxTrackLog));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            L.base.e("build track log json error", e);
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
