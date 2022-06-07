package org.json;

import com.fasterxml.jackson.core.JsonPointer;
import java.util.Iterator;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public class JSONML {
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0175, code lost:
        throw r7.syntaxError("Reserved attribute.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008a, code lost:
        throw r7.syntaxError("Expected 'CDATA['");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.Object a(org.json.XMLTokener r7, boolean r8, org.json.JSONArray r9) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONML.a(org.json.XMLTokener, boolean, org.json.JSONArray):java.lang.Object");
    }

    public static JSONArray toJSONArray(String str) throws JSONException {
        return toJSONArray(new XMLTokener(str));
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener) throws JSONException {
        return (JSONArray) a(xMLTokener, true, null);
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener) throws JSONException {
        return (JSONObject) a(xMLTokener, false, null);
    }

    public static JSONObject toJSONObject(String str) throws JSONException {
        return toJSONObject(new XMLTokener(str));
    }

    public static String toString(JSONArray jSONArray) throws JSONException {
        int i;
        StringBuffer stringBuffer = new StringBuffer();
        String string = jSONArray.getString(0);
        XML.noSpace(string);
        String escape = XML.escape(string);
        stringBuffer.append(Typography.less);
        stringBuffer.append(escape);
        Object opt = jSONArray.opt(1);
        if (opt instanceof JSONObject) {
            i = 2;
            JSONObject jSONObject = (JSONObject) opt;
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String obj = keys.next().toString();
                XML.noSpace(obj);
                String optString = jSONObject.optString(obj);
                if (optString != null) {
                    stringBuffer.append(' ');
                    stringBuffer.append(XML.escape(obj));
                    stringBuffer.append('=');
                    stringBuffer.append('\"');
                    stringBuffer.append(XML.escape(optString));
                    stringBuffer.append('\"');
                }
            }
        } else {
            i = 1;
        }
        int length = jSONArray.length();
        if (i >= length) {
            stringBuffer.append(JsonPointer.SEPARATOR);
            stringBuffer.append(Typography.greater);
        } else {
            stringBuffer.append(Typography.greater);
            do {
                Object obj2 = jSONArray.get(i);
                i++;
                if (obj2 != null) {
                    if (obj2 instanceof String) {
                        stringBuffer.append(XML.escape(obj2.toString()));
                        continue;
                    } else if (obj2 instanceof JSONObject) {
                        stringBuffer.append(toString((JSONObject) obj2));
                        continue;
                    } else if (obj2 instanceof JSONArray) {
                        stringBuffer.append(toString((JSONArray) obj2));
                        continue;
                    } else {
                        continue;
                    }
                }
            } while (i < length);
            stringBuffer.append(Typography.less);
            stringBuffer.append(JsonPointer.SEPARATOR);
            stringBuffer.append(escape);
            stringBuffer.append(Typography.greater);
        }
        return stringBuffer.toString();
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        String optString = jSONObject.optString("tagName");
        if (optString == null) {
            return XML.escape(jSONObject.toString());
        }
        XML.noSpace(optString);
        String escape = XML.escape(optString);
        stringBuffer.append(Typography.less);
        stringBuffer.append(escape);
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String obj = keys.next().toString();
            if (!obj.equals("tagName") && !obj.equals("childNodes")) {
                XML.noSpace(obj);
                String optString2 = jSONObject.optString(obj);
                if (optString2 != null) {
                    stringBuffer.append(' ');
                    stringBuffer.append(XML.escape(obj));
                    stringBuffer.append('=');
                    stringBuffer.append('\"');
                    stringBuffer.append(XML.escape(optString2));
                    stringBuffer.append('\"');
                }
            }
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("childNodes");
        if (optJSONArray == null) {
            stringBuffer.append(JsonPointer.SEPARATOR);
            stringBuffer.append(Typography.greater);
        } else {
            stringBuffer.append(Typography.greater);
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                Object obj2 = optJSONArray.get(i);
                if (obj2 != null) {
                    if (obj2 instanceof String) {
                        stringBuffer.append(XML.escape(obj2.toString()));
                    } else if (obj2 instanceof JSONObject) {
                        stringBuffer.append(toString((JSONObject) obj2));
                    } else if (obj2 instanceof JSONArray) {
                        stringBuffer.append(toString((JSONArray) obj2));
                    }
                }
            }
            stringBuffer.append(Typography.less);
            stringBuffer.append(JsonPointer.SEPARATOR);
            stringBuffer.append(escape);
            stringBuffer.append(Typography.greater);
        }
        return stringBuffer.toString();
    }
}
