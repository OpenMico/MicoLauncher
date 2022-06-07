package com.jeremyliao.liveeventbus.ipc.core;

import android.os.Bundle;
import android.os.Parcelable;
import com.jeremyliao.liveeventbus.ipc.consts.IpcConst;

/* loaded from: classes2.dex */
public class ParcelableProcessor implements Processor {
    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public boolean writeToBundle(Bundle bundle, Object obj) {
        if (!(obj instanceof Parcelable)) {
            return false;
        }
        bundle.putParcelable(IpcConst.KEY_VALUE, (Parcelable) obj);
        return true;
    }

    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public Object createFromBundle(Bundle bundle) {
        return bundle.getParcelable(IpcConst.KEY_VALUE);
    }
}
