package org.fourthline.cling.model;

/* loaded from: classes5.dex */
public class DiscoveryOptions {
    private static String simpleName = "DiscoveryOptions";
    protected boolean advertised;
    protected boolean byeByeBeforeFirstAlive;

    public DiscoveryOptions(boolean z) {
        this.advertised = z;
    }

    public DiscoveryOptions(boolean z, boolean z2) {
        this.advertised = z;
        this.byeByeBeforeFirstAlive = z2;
    }

    public boolean isAdvertised() {
        return this.advertised;
    }

    public boolean isByeByeBeforeFirstAlive() {
        return this.byeByeBeforeFirstAlive;
    }

    public String toString() {
        return "(" + simpleName + ") advertised: " + isAdvertised() + " byebyeBeforeFirstAlive: " + isByeByeBeforeFirstAlive();
    }
}
