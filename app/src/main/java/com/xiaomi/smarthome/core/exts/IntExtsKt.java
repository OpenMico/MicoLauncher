package com.xiaomi.smarthome.core.exts;

import kotlin.Metadata;

/* compiled from: IntExts.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\b\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"isOdd", "", "", "smarthome_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class IntExtsKt {
    public static final boolean isOdd(int i) {
        return (i & 1) != 1;
    }
}
