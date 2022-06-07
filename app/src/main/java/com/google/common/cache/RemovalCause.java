package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
/* loaded from: classes2.dex */
public enum RemovalCause {
    EXPLICIT {
        @Override // com.google.common.cache.RemovalCause
        boolean a() {
            return false;
        }
    },
    REPLACED {
        @Override // com.google.common.cache.RemovalCause
        boolean a() {
            return false;
        }
    },
    COLLECTED {
        @Override // com.google.common.cache.RemovalCause
        boolean a() {
            return true;
        }
    },
    EXPIRED {
        @Override // com.google.common.cache.RemovalCause
        boolean a() {
            return true;
        }
    },
    SIZE {
        @Override // com.google.common.cache.RemovalCause
        boolean a() {
            return true;
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean a();
}
