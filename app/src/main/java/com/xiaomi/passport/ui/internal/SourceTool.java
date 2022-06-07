package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: Source.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SourceTool;", "", "()V", "Companion", "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class SourceTool {
    public static final Companion Companion = new Companion(null);
    private static boolean ENABLE_TEST;

    /* compiled from: Source.kt */
    @Metadata(bv = {1, 0, 2}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"Lcom/xiaomi/passport/ui/internal/SourceTool$Companion;", "", "()V", "ENABLE_TEST", "", "getENABLE_TEST", "()Z", "setENABLE_TEST", "(Z)V", "enableTest", "", "passportui_release"}, k = 1, mv = {1, 1, 10})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean getENABLE_TEST() {
            return SourceTool.ENABLE_TEST;
        }

        public final void setENABLE_TEST(boolean z) {
            SourceTool.ENABLE_TEST = z;
        }

        public final void enableTest() {
            setENABLE_TEST(true);
        }
    }
}
