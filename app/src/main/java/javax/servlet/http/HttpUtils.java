package javax.servlet.http;

import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.servlet.ServletInputStream;

/* loaded from: classes5.dex */
public class HttpUtils {
    private static ResourceBundle a = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");

    public static Hashtable<String, String[]> parseQueryString(String str) {
        String[] strArr;
        if (str != null) {
            Hashtable<String, String[]> hashtable = new Hashtable<>();
            StringBuilder sb = new StringBuilder();
            StringTokenizer stringTokenizer = new StringTokenizer(str, MusicGroupListActivity.SPECIAL_SYMBOL);
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                int indexOf = nextToken.indexOf(61);
                if (indexOf != -1) {
                    String a2 = a(nextToken.substring(0, indexOf), sb);
                    String a3 = a(nextToken.substring(indexOf + 1, nextToken.length()), sb);
                    if (hashtable.containsKey(a2)) {
                        String[] strArr2 = hashtable.get(a2);
                        strArr = new String[strArr2.length + 1];
                        for (int i = 0; i < strArr2.length; i++) {
                            strArr[i] = strArr2[i];
                        }
                        strArr[strArr2.length] = a3;
                    } else {
                        strArr = new String[]{a3};
                    }
                    hashtable.put(a2, strArr);
                } else {
                    throw new IllegalArgumentException();
                }
            }
            return hashtable;
        }
        throw new IllegalArgumentException();
    }

    public static Hashtable<String, String[]> parsePostData(int i, ServletInputStream servletInputStream) {
        if (i <= 0) {
            return new Hashtable<>();
        }
        if (servletInputStream != null) {
            byte[] bArr = new byte[i];
            int i2 = 0;
            do {
                try {
                    int read = servletInputStream.read(bArr, i2, i - i2);
                    if (read > 0) {
                        i2 += read;
                    } else {
                        throw new IllegalArgumentException(a.getString("err.io.short_read"));
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            } while (i - i2 > 0);
            try {
                return parseQueryString(new String(bArr, 0, i, "8859_1"));
            } catch (UnsupportedEncodingException e2) {
                throw new IllegalArgumentException(e2.getMessage());
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static String a(String str, StringBuilder sb) {
        int i = 0;
        sb.setLength(0);
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '%') {
                int i2 = i + 1;
                try {
                    sb.append((char) Integer.parseInt(str.substring(i2, i + 3), 16));
                    i += 2;
                } catch (NumberFormatException unused) {
                    throw new IllegalArgumentException();
                } catch (StringIndexOutOfBoundsException unused2) {
                    String substring = str.substring(i);
                    sb.append(substring);
                    if (substring.length() == 2) {
                        i = i2;
                    }
                }
            } else if (charAt != '+') {
                sb.append(charAt);
            } else {
                sb.append(' ');
            }
            i++;
        }
        return sb.toString();
    }

    public static StringBuffer getRequestURL(HttpServletRequest httpServletRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        String scheme = httpServletRequest.getScheme();
        int serverPort = httpServletRequest.getServerPort();
        String requestURI = httpServletRequest.getRequestURI();
        stringBuffer.append(scheme);
        stringBuffer.append("://");
        stringBuffer.append(httpServletRequest.getServerName());
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            stringBuffer.append(':');
            stringBuffer.append(httpServletRequest.getServerPort());
        }
        stringBuffer.append(requestURI);
        return stringBuffer;
    }
}
