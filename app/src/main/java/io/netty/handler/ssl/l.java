package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: PemReader.java */
/* loaded from: classes4.dex */
final class l {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(l.class);
    private static final Pattern b = Pattern.compile("-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*CERTIFICATE[^-]*-+", 2);
    private static final Pattern c = Pattern.compile("-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*PRIVATE\\s+KEY[^-]*-+", 2);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteBuf[] a(File file) throws CertificateException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteBuf[] a2 = a(fileInputStream);
            d(fileInputStream);
            return a2;
        } catch (FileNotFoundException unused) {
            throw new CertificateException("could not find certificate file: " + file);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteBuf[] a(InputStream inputStream) throws CertificateException {
        try {
            String c2 = c(inputStream);
            ArrayList arrayList = new ArrayList();
            Matcher matcher = b.matcher(c2);
            for (int i = 0; matcher.find(i); i = matcher.end()) {
                ByteBuf copiedBuffer = Unpooled.copiedBuffer(matcher.group(1), CharsetUtil.US_ASCII);
                ByteBuf decode = Base64.decode(copiedBuffer);
                copiedBuffer.release();
                arrayList.add(decode);
            }
            if (!arrayList.isEmpty()) {
                return (ByteBuf[]) arrayList.toArray(new ByteBuf[arrayList.size()]);
            }
            throw new CertificateException("found no certificates in input stream");
        } catch (IOException e) {
            throw new CertificateException("failed to read certificate input stream", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteBuf b(File file) throws KeyException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteBuf b2 = b(fileInputStream);
            d(fileInputStream);
            return b2;
        } catch (FileNotFoundException unused) {
            throw new KeyException("could not fine key file: " + file);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ByteBuf b(InputStream inputStream) throws KeyException {
        try {
            Matcher matcher = c.matcher(c(inputStream));
            if (matcher.find()) {
                ByteBuf copiedBuffer = Unpooled.copiedBuffer(matcher.group(1), CharsetUtil.US_ASCII);
                ByteBuf decode = Base64.decode(copiedBuffer);
                copiedBuffer.release();
                return decode;
            }
            throw new KeyException("could not find a PKCS #8 private key in input stream (see http://netty.io/wiki/sslcontextbuilder-and-private-key.html for more information)");
        } catch (IOException e) {
            throw new KeyException("failed to read key input stream", e);
        }
    }

    private static String c(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    return byteArrayOutputStream.toString(CharsetUtil.US_ASCII.name());
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } finally {
            a(byteArrayOutputStream);
        }
    }

    private static void d(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            a.warn("Failed to close a stream.", (Throwable) e);
        }
    }

    private static void a(OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            a.warn("Failed to close a stream.", (Throwable) e);
        }
    }

    private l() {
    }
}
