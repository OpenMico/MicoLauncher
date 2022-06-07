package org.eclipse.jetty.util.preventers;

/* loaded from: classes5.dex */
public class LDAPLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) {
        try {
            Class.forName("com.sun.jndi.LdapPoolManager", true, classLoader);
        } catch (ClassNotFoundException e) {
            LOG.ignore(e);
        }
    }
}
