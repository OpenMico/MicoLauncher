package com.jeremyliao.liveeventbus.ipc.core;

import android.os.Bundle;
import com.jeremyliao.liveeventbus.ipc.consts.IpcConst;
import com.jeremyliao.liveeventbus.ipc.json.GsonConverter;
import com.jeremyliao.liveeventbus.ipc.json.JsonConverter;

/* loaded from: classes2.dex */
public class GsonProcessor implements Processor {
    private final JsonConverter jsonConverter = new GsonConverter();

    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public boolean writeToBundle(Bundle bundle, Object obj) {
        bundle.putString(IpcConst.KEY_VALUE, this.jsonConverter.toJson(obj));
        bundle.putString(IpcConst.KEY_CLASS_NAME, obj.getClass().getCanonicalName());
        return true;
    }

    @Override // com.jeremyliao.liveeventbus.ipc.core.Processor
    public Object createFromBundle(Bundle bundle) throws ClassNotFoundException {
        Class<?> cls;
        String string = bundle.getString(IpcConst.KEY_VALUE);
        String string2 = bundle.getString(IpcConst.KEY_CLASS_NAME);
        try {
            cls = Class.forName(string2);
        } catch (ClassNotFoundException unused) {
            int lastIndexOf = string2.lastIndexOf(46);
            if (lastIndexOf != -1) {
                String substring = string2.substring(0, lastIndexOf);
                String substring2 = string2.substring(lastIndexOf + 1);
                cls = Class.forName(substring + "$" + substring2);
            } else {
                cls = null;
            }
        }
        return this.jsonConverter.fromJson(string, cls);
    }
}
