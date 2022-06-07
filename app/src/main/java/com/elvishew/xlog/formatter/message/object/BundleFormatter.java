package com.elvishew.xlog.formatter.message.object;

import android.os.Bundle;
import com.elvishew.xlog.internal.util.ObjectToStringUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/message/object/BundleFormatter.class */
public class BundleFormatter implements ObjectFormatter<Bundle> {
    public String format(Bundle data) {
        return ObjectToStringUtil.bundleToString(data);
    }
}
