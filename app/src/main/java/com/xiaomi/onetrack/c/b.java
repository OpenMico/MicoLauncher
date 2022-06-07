package com.xiaomi.onetrack.c;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;
import com.google.common.primitives.SignedBytes;
import com.xiaomi.onetrack.util.p;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheOpcodes;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.GregorianCalendar;
import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes4.dex */
public class b {
    public static final String a = b();
    private static final String b = "AndroidKeyStore";
    private static final String c = "AndroidKeyStore";
    private static final String d = "RSA/ECB/PKCS1Padding";
    private static final String e = "RSA_KEY";

    public static synchronized String a(Context context, String str) throws Exception {
        synchronized (b.class) {
            Cipher instance = Cipher.getInstance(d);
            KeyStore instance2 = KeyStore.getInstance("AndroidKeyStore");
            instance2.load(null);
            a(context, instance2);
            Certificate certificate = instance2.getCertificate(e);
            if (certificate == null) {
                return null;
            }
            instance.init(1, certificate.getPublicKey());
            return Base64.encodeToString(instance.doFinal(str.getBytes("UTF-8")), 0);
        }
    }

    public static synchronized String b(Context context, String str) throws Exception {
        String str2;
        synchronized (b.class) {
            Cipher instance = Cipher.getInstance(d);
            KeyStore instance2 = KeyStore.getInstance("AndroidKeyStore");
            instance2.load(null);
            a(context, instance2);
            instance.init(2, (PrivateKey) instance2.getKey(e, null));
            str2 = new String(instance.doFinal(Base64.decode(str, 0)), "UTF-8");
        }
        return str2;
    }

    private static void a(Context context, KeyStore keyStore) {
        try {
            if (!keyStore.containsAlias(e)) {
                if (Build.VERSION.SDK_INT >= 18) {
                    if (Build.VERSION.SDK_INT < 23) {
                        a(context);
                    } else {
                        a();
                    }
                }
            }
        } catch (Throwable th) {
            p.b("AndroidKeyStore", "createKey e", th);
        }
    }

    @TargetApi(18)
    private static void a(Context context) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
        gregorianCalendar2.add(1, 1);
        KeyPairGeneratorSpec build = new KeyPairGeneratorSpec.Builder(context).setAlias(e).setSubject(new X500Principal("CN=RSA_KEY")).setSerialNumber(BigInteger.valueOf(1337L)).setStartDate(gregorianCalendar.getTime()).setEndDate(gregorianCalendar2.getTime()).build();
        KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
        instance.initialize(build);
        instance.generateKeyPair();
    }

    private static void a() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, InvalidAlgorithmParameterException, NoSuchProviderException, NoSuchAlgorithmException, LinkageError {
        Class<?> cls = Class.forName("android.security.keystore.KeyGenParameterSpec$Builder");
        if (cls != null) {
            Constructor<?> constructor = cls.getConstructor(String.class, Integer.TYPE);
            Class<?> cls2 = Class.forName("android.security.keystore.KeyProperties");
            Object newInstance = constructor.newInstance(e, Integer.valueOf(cls2.getDeclaredField("PURPOSE_ENCRYPT").getInt(null) | cls2.getDeclaredField("PURPOSE_DECRYPT").getInt(null)));
            cls.getMethod("setDigests", String[].class).invoke(newInstance, new String[]{(String) cls2.getDeclaredField("DIGEST_SHA256").get(null), (String) cls2.getDeclaredField("DIGEST_SHA512").get(null)});
            cls.getMethod("setEncryptionPaddings", String[].class).invoke(newInstance, new String[]{(String) cls2.getDeclaredField("ENCRYPTION_PADDING_RSA_PKCS1").get(null)});
            Object invoke = cls.getMethod("build", new Class[0]).invoke(newInstance, new Object[0]);
            Class<?> cls3 = Class.forName("java.security.KeyPairGenerator");
            if (cls3 != null) {
                KeyPairGenerator keyPairGenerator = (KeyPairGenerator) cls3.getMethod("getInstance", String.class, String.class).invoke(null, "RSA", "AndroidKeyStore");
                cls3.getMethod("initialize", AlgorithmParameterSpec.class).invoke(keyPairGenerator, invoke);
                keyPairGenerator.generateKeyPair();
            }
        }
    }

    private static String b() {
        try {
            return new String(new byte[]{104, 42, 89, 51, 49, 93, 99, BinaryMemcacheOpcodes.SASL_AUTH, SignedBytes.MAX_POWER_OF_TWO, 53, 78, 115, 95, 106, 80, 115, 118, 101, 106, BinaryMemcacheOpcodes.SASL_AUTH, SignedBytes.MAX_POWER_OF_TWO, 51, 91, 40, 42, 119, 40, BinaryMemcacheOpcodes.SASL_AUTH, 108}, "UTF-8");
        } catch (Exception e2) {
            p.b("AndroidKeyStore", e2.getMessage());
            return "";
        }
    }
}
