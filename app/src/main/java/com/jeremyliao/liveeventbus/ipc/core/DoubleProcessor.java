package com.jeremyliao.liveeventbus.ipc.core;

import android.os.Bundle;
import com.jeremyliao.liveeventbus.ipc.consts.IpcConst;

/* loaded from: classes2.dex */
public class DoubleProcessor implements Processor {
    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public boolean writeToBundle(Bundle bundle, Object obj) {
        if (!(obj instanceof Double)) {
            return false;
        }
        bundle.putDouble(IpcConst.KEY_VALUE, ((Double) obj).doubleValue());
        return true;
    }

    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public Object createFromBundle(Bundle bundle) {
        return Double.valueOf(bundle.getDouble(IpcConst.KEY_VALUE));
    }
}
