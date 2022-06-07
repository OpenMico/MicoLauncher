package com.efs.sdk.pa;

import android.text.TextUtils;
import android.util.Log;
import com.efs.sdk.base.EfsReporter;
import com.efs.sdk.base.protocol.file.EfsTextFile;
import com.efs.sdk.base.protocol.file.section.AbsSection;
import com.efs.sdk.base.protocol.file.section.KVSection;
import com.efs.sdk.base.protocol.file.section.TextSection;
import java.util.HashMap;
import java.util.Map;
import xcrash.TombstoneParser;

/* loaded from: classes.dex */
public final class c {
    public static synchronized void a(PAFactory pAFactory, String str, String str2) {
        synchronized (c.class) {
            EfsTextFile efsTextFile = new EfsTextFile(str);
            KVSection createAndAddKVSection = efsTextFile.createAndAddKVSection("custom_info");
            createAndAddKVSection.put("bserial", pAFactory.getSerial());
            createAndAddKVSection.put("bsver", pAFactory.getSver());
            HashMap<String, String> extend = pAFactory.getExtend();
            if (extend != null && !extend.isEmpty()) {
                for (Map.Entry<String, String> entry : extend.entrySet()) {
                    createAndAddKVSection.put(entry.getKey(), entry.getValue());
                }
            }
            createAndAddKVSection.put("crver", "2.1.154.umeng");
            if (!TextUtils.isEmpty(str2)) {
                TextSection createAndAddTextSection = efsTextFile.createAndAddTextSection(TombstoneParser.keyStack);
                createAndAddTextSection.setBody(str2);
                createAndAddTextSection.setSep(AbsSection.SEP_LINE_BREAK);
            }
            EfsReporter reporter = pAFactory.getReporter();
            if (reporter != null) {
                reporter.send(efsTextFile);
            }
            pAFactory.getConfigManager().increaseUploadSmoothLogCnt();
            Log.d("fred_xx", "reportPaWpkStats: stack: ".concat(String.valueOf(str2)));
        }
    }
}
