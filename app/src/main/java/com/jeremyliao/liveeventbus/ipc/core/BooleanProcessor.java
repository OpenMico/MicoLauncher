package com.jeremyliao.liveeventbus.ipc.core;

import android.os.Bundle;
import com.jeremyliao.liveeventbus.ipc.consts.IpcConst;

/* loaded from: classes2.dex */
public class BooleanProcessor implements Processor {
    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public boolean writeToBundle(Bundle bundle, Object obj) {
        if (!(obj instanceof Boolean)) {
            return false;
        }
        bundle.putBoolean(IpcConst.KEY_VALUE, ((Boolean) obj).booleanValue());
        return true;
    }

    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public Object createFromBundle(Bundle bundle) {
        return Boolean.valueOf(bundle.getBoolean(IpcConst.KEY_VALUE));
    }
}
