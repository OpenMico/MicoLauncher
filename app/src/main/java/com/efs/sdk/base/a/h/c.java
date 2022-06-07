package com.efs.sdk.base.a.h;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public final class c {
    public static byte[] a(byte[] bArr) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Exception e) {
            d.b("efs.base", "gzip error", e);
            return null;
        }
    }
}
