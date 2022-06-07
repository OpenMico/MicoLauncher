package com.elvishew.xlog.printer.file.naming;

import com.xiaomi.accountsdk.account.XMPassport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/printer/file/naming/DateFileNameGenerator.class */
public class DateFileNameGenerator implements FileNameGenerator {
    ThreadLocal<SimpleDateFormat> mLocalDateFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.elvishew.xlog.printer.file.naming.DateFileNameGenerator.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat(XMPassport.SIMPLE_DATE_FORMAT, Locale.US);
        }
    };

    @Override // com.elvishew.xlog.printer.file.naming.FileNameGenerator
    public boolean isFileNameChangeable() {
        return true;
    }

    @Override // com.elvishew.xlog.printer.file.naming.FileNameGenerator
    public String generateFileName(int logLevel, long timestamp) {
        SimpleDateFormat sdf = this.mLocalDateFormat.get();
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
