package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public abstract class Ticker {
    private static final Ticker a = new Ticker() { // from class: com.google.common.base.Ticker.1
        @Override // com.google.common.base.Ticker
        public long read() {
            return j.a();
        }
    };

    @CanIgnoreReturnValue
    public abstract long read();

    public static Ticker systemTicker() {
        return a;
    }
}
