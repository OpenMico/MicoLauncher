package com.xiaomi.micolauncher.common.ubus.handlers;

import android.content.Context;
import com.elvishew.xlog.Logger;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ubus.UBus;
import com.xiaomi.micolauncher.common.utils.VideoMonitorHelper;

/* loaded from: classes3.dex */
public class VideoMonitorHandler implements UBus.UbusHandler {
    private static final String MESSAGE_EVENT = "event";
    private static final int MESSAGE_EVENT_GET_STATUS = 0;
    private static final int MESSAGE_EVENT_OPEN_MONITOR = 1;
    private static final int MESSAGE_EVENT_START_SERVICE = 2;
    private static final String METHOD_GET = "get";
    private static final String PROPERTY_CODE = "code";
    private static final String PROPERTY_INFO = "info";
    private static final String PROPERTY_STATUS = "status";
    private static final String TAG = "[VideoMonitorHandler] ";
    private static final String VIDEO_MONITOR_PATH = "camera";

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public boolean canHandle(Context context, String str, String str2) {
        return "camera".equals(str);
    }

    @Override // com.xiaomi.micolauncher.common.ubus.UBus.UbusHandler
    public String handle(Context context, String str, String str2, String str3) {
        VideoMonitorHelper instance = VideoMonitorHelper.getInstance();
        JsonObject jsonObject = new JsonObject();
        if ("get".equals(str2)) {
            jsonObject.addProperty("code", Integer.valueOf(instance.getCode()));
            if (instance.hasVideoMonitorFunction()) {
                JsonElement jsonElement = ((JsonObject) JsonParser.parseString(str3)).get("event");
                int i = -1;
                if (jsonElement != null) {
                    try {
                        i = Integer.parseInt(jsonElement.getAsString());
                    } catch (NumberFormatException e) {
                        Logger logger = L.videoMonitor;
                        logger.e("[VideoMonitorHandler] exception: " + e);
                    }
                }
                Logger logger2 = L.videoMonitor;
                logger2.d("[VideoMonitorHandler] message event " + i);
                switch (i) {
                    case 0:
                        addInfo(jsonObject, instance);
                        break;
                    case 1:
                        addInfo(jsonObject, instance);
                        closeLowLevelFunctions(instance);
                        break;
                    case 2:
                        if (instance.canOpenVideoMonitor()) {
                            instance.startVideoMonitorService();
                            break;
                        }
                        break;
                    default:
                        jsonObject.addProperty("code", (Number) 2);
                        break;
                }
            } else {
                return jsonObject.toString();
            }
        }
        return jsonObject.toString();
    }

    private void addInfo(JsonObject jsonObject, VideoMonitorHelper videoMonitorHelper) {
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("status", Integer.valueOf(videoMonitorHelper.getStatus()));
        jsonObject.addProperty("info", jsonObject2.toString());
    }

    private void closeLowLevelFunctions(VideoMonitorHelper videoMonitorHelper) {
        if (videoMonitorHelper.canOpenVideoMonitor()) {
            videoMonitorHelper.saveLowLevelFunctionsState();
            videoMonitorHelper.setGestureControl(false);
            videoMonitorHelper.setDistanceProtection(false);
            videoMonitorHelper.setQuickEnterChildMode(false);
        }
    }
}
