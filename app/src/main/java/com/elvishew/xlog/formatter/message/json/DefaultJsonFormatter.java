package com.elvishew.xlog.formatter.message.json;

import com.elvishew.xlog.internal.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/message/json/DefaultJsonFormatter.class */
public class DefaultJsonFormatter implements JsonFormatter {
    private static final int JSON_INDENT = 4;

    public String format(String json) {
        String formattedString;
        if (json == null || json.trim().length() == 0) {
            Platform.get().warn("JSON empty.");
            return "";
        }
        try {
            if (json.startsWith("{")) {
                formattedString = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                formattedString = new JSONArray(json).toString(4);
            } else {
                Platform.get().warn("JSON should start with { or [");
                return json;
            }
            return formattedString;
        } catch (Exception e) {
            Platform.get().warn(e.getMessage());
            return json;
        }
    }
}
