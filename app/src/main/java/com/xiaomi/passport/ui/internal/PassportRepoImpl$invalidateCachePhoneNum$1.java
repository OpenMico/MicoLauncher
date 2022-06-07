package com.xiaomi.passport.ui.internal;

import android.content.Context;
import com.xiaomi.passport.v2.utils.ActivatorPhoneController;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: PassportCore.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 1, 10})
/* loaded from: classes4.dex */
final class PassportRepoImpl$invalidateCachePhoneNum$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ Context $context;
    final /* synthetic */ int $slotId;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PassportRepoImpl$invalidateCachePhoneNum$1(Context context, int i) {
        super(0);
        this.$context = context;
        this.$slotId = i;
    }

    @Override // kotlin.jvm.functions.Function0
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2() {
        new ActivatorPhoneController(this.$context).invalidateCachePhoneNum(this.$slotId);
    }
}
