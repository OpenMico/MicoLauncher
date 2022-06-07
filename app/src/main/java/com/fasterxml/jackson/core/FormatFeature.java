package com.fasterxml.jackson.core;

/* loaded from: classes.dex */
public interface FormatFeature {
    boolean enabledByDefault();

    boolean enabledIn(int i);

    int getMask();
}
