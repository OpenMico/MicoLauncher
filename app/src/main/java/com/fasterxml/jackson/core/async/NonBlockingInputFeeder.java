package com.fasterxml.jackson.core.async;

/* loaded from: classes.dex */
public interface NonBlockingInputFeeder {
    void endOfInput();

    boolean needMoreInput();
}
