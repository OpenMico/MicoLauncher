package com.elvishew.xlog.formatter.message.object;

import android.content.Intent;
import com.elvishew.xlog.internal.util.ObjectToStringUtil;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/message/object/IntentFormatter.class */
public class IntentFormatter implements ObjectFormatter<Intent> {
    public String format(Intent data) {
        return ObjectToStringUtil.intentToString(data);
    }
}
