package com.xiaomi.passport.ui.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "it", "invoke", "(Lkotlin/Unit;)V"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportRepoImpl$invalidateCachePhoneNum$2 extends Lambda implements Function1<Unit, Unit> {
    public static final PassportRepoImpl$invalidateCachePhoneNum$2 INSTANCE = new PassportRepoImpl$invalidateCachePhoneNum$2();

    PassportRepoImpl$invalidateCachePhoneNum$2() {
        super(1);
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Unit it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Unit unit) {
        invoke2(unit);
        return Unit.INSTANCE;
    }
}
