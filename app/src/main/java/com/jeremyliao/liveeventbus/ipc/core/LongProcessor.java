package com.jeremyliao.liveeventbus.ipc.core;

import android.os.Bundle;
import com.jeremyliao.liveeventbus.ipc.consts.IpcConst;

/* loaded from: classes2.dex */
public class LongProcessor implements Processor {
    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public boolean writeToBundle(Bundle bundle, Object obj) {
        if (!(obj instanceof Long)) {
            return false;
        }
        bundle.putLong(IpcConst.KEY_VALUE, ((Long) obj).longValue());
        return true;
    }

    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public Object createFromBundle(Bundle bundle) {
        return Long.valueOf(bundle.getLong(IpcConst.KEY_VALUE));
    }
}
