package com.google.android.exoplayer2.drm;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.xiaomi.mipush.sdk.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ClearKeyUtil.java */
/* loaded from: classes2.dex */
final class a {
    public static byte[] a(byte[] bArr) {
        return Util.SDK_INT >= 27 ? bArr : Util.getUtf8Bytes(a(Util.fromUtf8Bytes(bArr)));
    }

    public static byte[] b(byte[] bArr) {
        if (Util.SDK_INT >= 27) {
            return bArr;
        }
        try {
            JSONObject jSONObject = new JSONObject(Util.fromUtf8Bytes(bArr));
            StringBuilder sb = new StringBuilder("{\"keys\":[");
            JSONArray jSONArray = jSONObject.getJSONArray("keys");
            for (int i = 0; i < jSONArray.length(); i++) {
                if (i != 0) {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                sb.append("{\"k\":\"");
                sb.append(b(jSONObject2.getString("k")));
                sb.append("\",\"kid\":\"");
                sb.append(b(jSONObject2.getString("kid")));
                sb.append("\",\"kty\":\"");
                sb.append(jSONObject2.getString("kty"));
                sb.append("\"}");
            }
            sb.append("]}");
            return Util.getUtf8Bytes(sb.toString());
        } catch (JSONException e) {
            String valueOf = String.valueOf(Util.fromUtf8Bytes(bArr));
            Log.e("ClearKeyUtil", valueOf.length() != 0 ? "Failed to adjust response data: ".concat(valueOf) : new String("Failed to adjust response data: "), e);
            return bArr;
        }
    }

    private static String a(String str) {
        return str.replace('+', '-').replace(JsonPointer.SEPARATOR, '_');
    }

    private static String b(String str) {
        return str.replace('-', '+').replace('_', JsonPointer.SEPARATOR);
    }
}
