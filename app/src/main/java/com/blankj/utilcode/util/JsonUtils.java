package com.blankj.utilcode.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class JsonUtils {
    private JsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean getBoolean(JSONObject jSONObject, String str) {
        return getBoolean(jSONObject, str, false);
    }

    public static boolean getBoolean(JSONObject jSONObject, String str, boolean z) {
        return ((Boolean) a(jSONObject, str, Boolean.valueOf(z), (byte) 0)).booleanValue();
    }

    public static boolean getBoolean(String str, String str2) {
        return getBoolean(str, str2, false);
    }

    public static boolean getBoolean(String str, String str2, boolean z) {
        return ((Boolean) a(str, str2, Boolean.valueOf(z), (byte) 0)).booleanValue();
    }

    public static int getInt(JSONObject jSONObject, String str) {
        return getInt(jSONObject, str, -1);
    }

    public static int getInt(JSONObject jSONObject, String str, int i) {
        return ((Integer) a(jSONObject, str, Integer.valueOf(i), (byte) 1)).intValue();
    }

    public static int getInt(String str, String str2) {
        return getInt(str, str2, -1);
    }

    public static int getInt(String str, String str2, int i) {
        return ((Integer) a(str, str2, Integer.valueOf(i), (byte) 1)).intValue();
    }

    public static long getLong(JSONObject jSONObject, String str) {
        return getLong(jSONObject, str, -1L);
    }

    public static long getLong(JSONObject jSONObject, String str, long j) {
        return ((Long) a(jSONObject, str, Long.valueOf(j), (byte) 2)).longValue();
    }

    public static long getLong(String str, String str2) {
        return getLong(str, str2, -1L);
    }

    public static long getLong(String str, String str2, long j) {
        return ((Long) a(str, str2, Long.valueOf(j), (byte) 2)).longValue();
    }

    public static double getDouble(JSONObject jSONObject, String str) {
        return getDouble(jSONObject, str, -1.0d);
    }

    public static double getDouble(JSONObject jSONObject, String str, double d) {
        return ((Double) a(jSONObject, str, Double.valueOf(d), (byte) 3)).doubleValue();
    }

    public static double getDouble(String str, String str2) {
        return getDouble(str, str2, -1.0d);
    }

    public static double getDouble(String str, String str2, double d) {
        return ((Double) a(str, str2, Double.valueOf(d), (byte) 3)).doubleValue();
    }

    public static String getString(JSONObject jSONObject, String str) {
        return getString(jSONObject, str, "");
    }

    public static String getString(JSONObject jSONObject, String str, String str2) {
        return (String) a(jSONObject, str, str2, (byte) 4);
    }

    public static String getString(String str, String str2) {
        return getString(str, str2, "");
    }

    public static String getString(String str, String str2, String str3) {
        return (String) a(str, str2, str3, (byte) 4);
    }

    public static JSONObject getJSONObject(JSONObject jSONObject, String str, JSONObject jSONObject2) {
        return (JSONObject) a(jSONObject, str, jSONObject2, (byte) 5);
    }

    public static JSONObject getJSONObject(String str, String str2, JSONObject jSONObject) {
        return (JSONObject) a(str, str2, jSONObject, (byte) 5);
    }

    public static JSONArray getJSONArray(JSONObject jSONObject, String str, JSONArray jSONArray) {
        return (JSONArray) a(jSONObject, str, jSONArray, (byte) 6);
    }

    public static JSONArray getJSONArray(String str, String str2, JSONArray jSONArray) {
        return (JSONArray) a(str, str2, jSONArray, (byte) 6);
    }

    private static <T> T a(JSONObject jSONObject, String str, T t, byte b) {
        if (jSONObject == null || str == null || str.length() == 0) {
            return t;
        }
        try {
            if (b == 0) {
                return (T) Boolean.valueOf(jSONObject.getBoolean(str));
            }
            if (b == 1) {
                return (T) Integer.valueOf(jSONObject.getInt(str));
            }
            if (b == 2) {
                return (T) Long.valueOf(jSONObject.getLong(str));
            }
            if (b == 3) {
                return (T) Double.valueOf(jSONObject.getDouble(str));
            }
            if (b == 4) {
                return (T) jSONObject.getString(str);
            }
            if (b == 5) {
                return (T) jSONObject.getJSONObject(str);
            }
            return b == 6 ? (T) jSONObject.getJSONArray(str) : t;
        } catch (JSONException e) {
            e.printStackTrace();
            return t;
        }
    }

    private static <T> T a(String str, String str2, T t, byte b) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return t;
        }
        try {
            return (T) a(new JSONObject(str), str2, t, b);
        } catch (JSONException e) {
            e.printStackTrace();
            return t;
        }
    }

    public static String formatJson(String str) {
        return formatJson(str, 4);
    }

    public static String formatJson(String str, int i) {
        try {
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                char charAt = str.charAt(i2);
                if (charAt == '{') {
                    return new JSONObject(str).toString(i);
                }
                if (charAt == '[') {
                    return new JSONArray(str).toString(i);
                }
                if (!Character.isWhitespace(charAt)) {
                    return str;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }
}
