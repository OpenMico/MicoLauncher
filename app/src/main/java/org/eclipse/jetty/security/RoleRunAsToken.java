package org.eclipse.jetty.security;

/* loaded from: classes5.dex */
public class RoleRunAsToken implements RunAsToken {
    private final String _runAsRole;

    public RoleRunAsToken(String str) {
        this._runAsRole = str;
    }

    public String getRunAsRole() {
        return this._runAsRole;
    }

    public String toString() {
        return "RoleRunAsToken(" + this._runAsRole + ")";
    }
}
