package com.xiaomi.micolauncher.module.miot_v2;

import android.view.View;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTListAdapter$kcxC6WxsDgGKyxCbWg1TzXiSxuk  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$MIoTListAdapter$kcxC6WxsDgGKyxCbWg1TzXiSxuk implements View.OnClickListener {
    public static final /* synthetic */ $$Lambda$MIoTListAdapter$kcxC6WxsDgGKyxCbWg1TzXiSxuk INSTANCE = new $$Lambda$MIoTListAdapter$kcxC6WxsDgGKyxCbWg1TzXiSxuk();

    private /* synthetic */ $$Lambda$MIoTListAdapter$kcxC6WxsDgGKyxCbWg1TzXiSxuk() {
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        ToastUtil.showToast((int) R.string.miot_device_off_line);
    }
}
