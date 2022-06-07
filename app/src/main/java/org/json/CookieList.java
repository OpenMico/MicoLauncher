package org.json;

import java.util.Iterator;

/* loaded from: classes3.dex */
public class CookieList {
    public static JSONObject toJSONObject(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(str);
        while (jSONTokener.more()) {
            String unescape = Cookie.unescape(jSONTokener.nextTo('='));
            jSONTokener.next('=');
            jSONObject.put(unescape, Cookie.unescape(jSONTokener.nextTo(';')));
            jSONTokener.next();
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        Iterator keys = jSONObject.keys();
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = false;
        while (keys.hasNext()) {
            String obj = keys.next().toString();
            if (!jSONObject.isNull(obj)) {
                if (z) {
                    stringBuffer.append(';');
                }
                stringBuffer.append(Cookie.escape(obj));
                stringBuffer.append("=");
                stringBuffer.append(Cookie.escape(jSONObject.getString(obj)));
                z = true;
            }
        }
        return stringBuffer.toString();
    }
}
