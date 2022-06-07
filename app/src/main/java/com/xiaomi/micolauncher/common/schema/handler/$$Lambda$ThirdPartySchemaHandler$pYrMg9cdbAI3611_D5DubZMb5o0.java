package com.xiaomi.micolauncher.common.schema.handler;

import android.content.Intent;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$ThirdPartySchemaHandler$pYrMg9cdbAI3611_D5DubZMb5o0  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ThirdPartySchemaHandler$pYrMg9cdbAI3611_D5DubZMb5o0 implements ThirdPartyAppProxy.OnFillIntentExtras {
    public static final /* synthetic */ $$Lambda$ThirdPartySchemaHandler$pYrMg9cdbAI3611_D5DubZMb5o0 INSTANCE = new $$Lambda$ThirdPartySchemaHandler$pYrMg9cdbAI3611_D5DubZMb5o0();

    private /* synthetic */ $$Lambda$ThirdPartySchemaHandler$pYrMg9cdbAI3611_D5DubZMb5o0() {
    }

    @Override // com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy.OnFillIntentExtras
    public final Intent fillExtra(Intent intent) {
        Intent putExtra;
        putExtra = intent.putExtra("autoMode", false);
        return putExtra;
    }
}
