package com.xiaomi.smarthome.library.apache.http.util;

import com.xiaomi.smarthome.library.apache.http.HeaderElement;
import com.xiaomi.smarthome.library.apache.http.HttpEntity;
import com.xiaomi.smarthome.library.apache.http.NameValuePair;
import com.xiaomi.smarthome.library.apache.http.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* loaded from: classes4.dex */
public final class EntityUtils {
    private EntityUtils() {
    }

    /* JADX WARN: Finally extract failed */
    public static byte[] toByteArray(HttpEntity httpEntity) throws IOException {
        if (httpEntity != null) {
            InputStream content = httpEntity.getContent();
            if (content == null) {
                return new byte[0];
            }
            if (httpEntity.getContentLength() <= 2147483647L) {
                int contentLength = (int) httpEntity.getContentLength();
                if (contentLength < 0) {
                    contentLength = 4096;
                }
                ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(contentLength);
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = content.read(bArr);
                        if (read != -1) {
                            byteArrayBuffer.append(bArr, 0, read);
                        } else {
                            content.close();
                            return byteArrayBuffer.toByteArray();
                        }
                    }
                } catch (Throwable th) {
                    content.close();
                    throw th;
                }
            } else {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
        } else {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
    }

    public static String getContentCharSet(HttpEntity httpEntity) throws ParseException {
        NameValuePair parameterByName;
        if (httpEntity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        } else if (httpEntity.getContentType() == null) {
            return null;
        } else {
            HeaderElement[] elements = httpEntity.getContentType().getElements();
            if (elements.length <= 0 || (parameterByName = elements[0].getParameterByName("charset")) == null) {
                return null;
            }
            return parameterByName.getValue();
        }
    }

    /* JADX WARN: Finally extract failed */
    public static String toString(HttpEntity httpEntity, String str) throws IOException, ParseException {
        if (httpEntity != null) {
            InputStream content = httpEntity.getContent();
            if (content == null) {
                return "";
            }
            if (httpEntity.getContentLength() <= 2147483647L) {
                int contentLength = (int) httpEntity.getContentLength();
                if (contentLength < 0) {
                    contentLength = 4096;
                }
                String contentCharSet = getContentCharSet(httpEntity);
                if (contentCharSet == null) {
                    contentCharSet = str;
                }
                if (contentCharSet == null) {
                    contentCharSet = "ISO-8859-1";
                }
                InputStreamReader inputStreamReader = new InputStreamReader(content, contentCharSet);
                CharArrayBuffer charArrayBuffer = new CharArrayBuffer(contentLength);
                try {
                    char[] cArr = new char[1024];
                    while (true) {
                        int read = inputStreamReader.read(cArr);
                        if (read != -1) {
                            charArrayBuffer.append(cArr, 0, read);
                        } else {
                            inputStreamReader.close();
                            return charArrayBuffer.toString();
                        }
                    }
                } catch (Throwable th) {
                    inputStreamReader.close();
                    throw th;
                }
            } else {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
        } else {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
    }

    public static String toString(HttpEntity httpEntity) throws IOException, ParseException {
        return toString(httpEntity, null);
    }
}
