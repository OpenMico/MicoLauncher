package org.apache.commons.logging;

import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.security.PrivilegedAction;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: LogFactory.java */
/* loaded from: classes5.dex */
public final class d implements PrivilegedAction {
    private final ClassLoader a;
    private final String b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(ClassLoader classLoader, String str) {
        this.a = classLoader;
        this.b = str;
    }

    @Override // java.security.PrivilegedAction
    public Object run() {
        try {
            if (this.a != null) {
                return this.a.getResources(this.b);
            }
            return ClassLoader.getSystemResources(this.b);
        } catch (IOException e) {
            if (LogFactory.isDiagnosticsEnabled()) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Exception while trying to find configuration file ");
                stringBuffer.append(this.b);
                stringBuffer.append(Constants.COLON_SEPARATOR);
                stringBuffer.append(e.getMessage());
                LogFactory.b(stringBuffer.toString());
            }
            return null;
        } catch (NoSuchMethodError unused) {
            return null;
        }
    }
}
