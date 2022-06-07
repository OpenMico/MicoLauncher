package io.realm;

/* loaded from: classes5.dex */
public enum Sort {
    ASCENDING(true),
    DESCENDING(false);
    
    private final boolean value;

    Sort(boolean z) {
        this.value = z;
    }

    public boolean getValue() {
        return this.value;
    }
}
