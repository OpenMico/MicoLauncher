package com.xiaomi.ai.android.utils;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.xiaomi.ai.log.Logger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes2.dex */
public final class a {
    public static long a(ArrayNode arrayNode) {
        long currentTimeMillis = System.currentTimeMillis();
        long length = (arrayNode == null || arrayNode.size() <= 0) ? 0L : arrayNode.toString().getBytes().length;
        Logger.a("CommonUtils", "getArrayLength : " + length + " ,time: " + (System.currentTimeMillis() - currentTimeMillis));
        return length;
    }

    public static String a(byte[] bArr, String str) {
        if (!(bArr == null || bArr.length == 0)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(bArr));
                byte[] bArr2 = new byte[256];
                while (true) {
                    int read = gZIPInputStream.read(bArr2);
                    if (read < 0) {
                        return byteArrayOutputStream.toString(str);
                    }
                    byteArrayOutputStream.write(bArr2, 0, read);
                }
            } catch (IOException e) {
                Logger.c("CommonUtils", "gzip uncompress error." + e.getMessage(), false);
            }
        }
        return null;
    }

    public static byte[] a(String str, String str2) {
        if (str == null || str.length() == 0) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(str.getBytes(str2));
            gZIPOutputStream.close();
        } catch (IOException e) {
            Logger.c("CommonUtils", "gzipOutputStream compress error." + e.getMessage(), false);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
