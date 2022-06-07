package org.hapjs.sdk.platform;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
class e {
    public Map<String, String> a;
    public String b;

    e() {
    }

    public static e a(String str) {
        String string;
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            string = jSONObject2.getString("sign");
            jSONObject = jSONObject2.getJSONObject("platforms");
        } catch (Exception unused) {
            Log.e("PlatformsConfig", "parse data error");
        }
        if (!a(string, jSONObject.toString())) {
            return null;
        }
        e eVar = new e();
        eVar.b = jSONObject.toString();
        eVar.a = b(eVar.b);
        return null;
    }

    private static boolean a(String str, String str2) {
        try {
            String a = c.a(str2.getBytes());
            String a2 = a(str, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA08GfvBdlHww024L+a1udalfeYZ2QyO7e\ndXPapwldvsAfzIr1atsChQF32mqgH4OqSyLP1Sobs+g6thhdC0Ae9Nyc7Wo37UGMluHHO3MWHeht\n2JpqoPQtIf6ymyH+n8W7TFX/koj19lL+3hFQlhF75QeZbET14AahAleR+0we2tS8y7V18p8nm5/q\nWASa2wn/zF/SwbzwH9FuOcyjMzlmBsQIzkoa6bvicdsnJirxFgskUjBOYNNmGZC4YvtfPQMk/pE/\n5Xkv/TgLoSUKnjxI18hvEi0VYx9TJHAefop9+GCu84yPA9yqQt2msMHXucYw9AHnvI04cUyWx1gM\nhmnJcwIDAQAB\n", "RSA/None/OAEPwithSHA-256andMGF1Padding");
            if (!TextUtils.isEmpty(a)) {
                return a.equals(a2);
            }
            return false;
        } catch (Exception unused) {
            Log.e("PlatformsConfig", "check sign error");
            return false;
        }
    }

    public static Map<String, String> b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        HashMap hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                hashMap.put(str2, jSONObject.optString(str2));
            }
        } catch (JSONException unused) {
        }
        return hashMap;
    }

    private static String a(String str, String str2, String str3) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str2, 0)));
        Cipher instance = Cipher.getInstance(str3);
        instance.init(2, generatePublic);
        return new String(instance.doFinal(Base64.decode(str, 0)), "UTF-8");
    }
}
