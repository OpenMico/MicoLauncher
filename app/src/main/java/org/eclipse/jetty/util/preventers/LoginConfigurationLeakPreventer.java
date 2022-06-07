package org.eclipse.jetty.util.preventers;

/* loaded from: classes5.dex */
public class LoginConfigurationLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("javax.security.auth.login.Configuration", true, classLoader);
        } catch (ClassNotFoundException e) {
            LOG.warn(e);
        }
    }
}
