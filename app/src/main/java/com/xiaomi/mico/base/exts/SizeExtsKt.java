package com.xiaomi.mico.base.exts;

import com.blankj.utilcode.util.SizeUtils;
import kotlin.Metadata;

/* compiled from: SizeExts.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0003\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0003\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0005"}, d2 = {"toDp", "", "", "", "toPx", "share-lib_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes3.dex */
public final class SizeExtsKt {
    public static final int toDp(int i) {
        return SizeUtils.px2dp(i);
    }

    public static final int toPx(int i) {
        return SizeUtils.dp2px(i);
    }

    public static final int toDp(float f) {
        return SizeUtils.px2dp(f);
    }

    public static final int toPx(float f) {
        return SizeUtils.dp2px(f);
    }

    public static final int toDp(double d) {
        return SizeUtils.px2dp((float) d);
    }

    public static final int toPx(double d) {
        return SizeUtils.dp2px((float) d);
    }
}
