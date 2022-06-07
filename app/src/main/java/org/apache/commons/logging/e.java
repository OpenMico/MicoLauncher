package org.apache.commons.logging;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.PrivilegedAction;
import java.util.Properties;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LogFactory.java */
/* loaded from: classes5.dex */
public final class e implements PrivilegedAction {
    private final URL a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(URL url) {
        this.a = url;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        Throwable th;
        InputStream inputStream;
        InputStream inputStream2;
        StringBuffer stringBuffer;
        try {
            inputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            URLConnection openConnection = this.a.openConnection();
            openConnection.setUseCaches(false);
            inputStream2 = openConnection.getInputStream();
        } catch (IOException unused) {
            inputStream2 = null;
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
                try {
                    inputStream.close();
                } catch (IOException unused2) {
                    if (LogFactory.isDiagnosticsEnabled()) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Unable to close stream for URL ");
                        stringBuffer2.append(this.a);
                        LogFactory.b(stringBuffer2.toString());
                    }
                }
            }
            throw th;
        }
        if (inputStream2 != null) {
            try {
                Properties properties = new Properties();
                properties.load(inputStream2);
                inputStream2.close();
                return properties;
            } catch (IOException unused3) {
                if (LogFactory.isDiagnosticsEnabled()) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("Unable to read URL ");
                    stringBuffer3.append(this.a);
                    LogFactory.b(stringBuffer3.toString());
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (IOException unused4) {
                        if (LogFactory.isDiagnosticsEnabled()) {
                            stringBuffer = new StringBuffer();
                            stringBuffer.append("Unable to close stream for URL ");
                            stringBuffer.append(this.a);
                            LogFactory.b(stringBuffer.toString());
                        }
                    }
                }
                return null;
            }
        } else {
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (IOException unused5) {
                    if (LogFactory.isDiagnosticsEnabled()) {
                        stringBuffer = new StringBuffer();
                        stringBuffer.append("Unable to close stream for URL ");
                        stringBuffer.append(this.a);
                        LogFactory.b(stringBuffer.toString());
                    }
                }
            }
            return null;
        }
    }
}
