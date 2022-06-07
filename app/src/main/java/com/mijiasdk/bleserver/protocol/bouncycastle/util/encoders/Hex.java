package com.mijiasdk.bleserver.protocol.bouncycastle.util.encoders;

import com.mijiasdk.bleserver.protocol.bouncycastle.util.Strings;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes2.dex */
public class Hex {
    private static final Encoder a = new HexEncoder();

    public static String toHexString(byte[] bArr) {
        return toHexString(bArr, 0, bArr.length);
    }

    public static String toHexString(byte[] bArr, int i, int i2) {
        return Strings.fromByteArray(encode(bArr, i, i2));
    }

    public static byte[] encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length);
    }

    public static byte[] encode(byte[] bArr, int i, int i2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.encode(bArr, i, i2, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new EncoderException("exception encoding Hex string: " + e.getMessage(), e);
        }
    }

    public static int encode(byte[] bArr, OutputStream outputStream) throws IOException {
        return a.encode(bArr, 0, bArr.length, outputStream);
    }

    public static int encode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        return a.encode(bArr, i, i2, outputStream);
    }

    public static byte[] decode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.decode(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new DecoderException("exception decoding Hex data: " + e.getMessage(), e);
        }
    }

    public static byte[] decode(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.decode(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new DecoderException("exception decoding Hex string: " + e.getMessage(), e);
        }
    }

    public static int decode(String str, OutputStream outputStream) throws IOException {
        return a.decode(str, outputStream);
    }
}
