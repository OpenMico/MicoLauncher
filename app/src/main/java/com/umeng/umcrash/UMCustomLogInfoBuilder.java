package com.umeng.umcrash;

import android.text.TextUtils;
import android.util.Log;
import com.uc.crashsdk.export.CustomLogInfo;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class UMCustomLogInfoBuilder {
    public static final String LINE_SEP = "\n";
    public static final String LOG_KEY_AC = "k_ac";
    public static final String LOG_KEY_CT = "k_ct";
    private static final String LOG_KEY_STACK_FUNC = "stackFunc";
    private static final String LOG_KEY_STACK_HASH = "stackHash";
    private static final String LOG_SECTION_SEP = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---";
    public static final String LOG_TYPE = "exception";
    private String stack;
    private Map<String, String> kvInfoMap = new HashMap(20);
    private List<String> sectionList = new ArrayList(5);
    private CustomLogInfo mCustomLogInfo = new CustomLogInfo(new StringBuffer(), "exception");

    public UMCustomLogInfoBuilder(String str) {
        this.kvInfoMap.put(LOG_KEY_CT, "exception");
        this.kvInfoMap.put(LOG_KEY_AC, str);
    }

    public UMCustomLogInfoBuilder put(String str, String str2) {
        if (LOG_KEY_AC.equals(str) || LOG_KEY_CT.equals(str)) {
            Log.w("crashsdk", "key can not be 'k_ac' and 'k_ct'");
            return this;
        }
        this.kvInfoMap.put(str, str2);
        return this;
    }

    public UMCustomLogInfoBuilder stackFunc(String str) {
        this.kvInfoMap.put(LOG_KEY_STACK_FUNC, str);
        return this;
    }

    public UMCustomLogInfoBuilder stackHash(String str) {
        this.kvInfoMap.put(LOG_KEY_STACK_HASH, str);
        return this;
    }

    public UMCustomLogInfoBuilder stack(Throwable th) {
        return stack(Log.getStackTraceString(th));
    }

    public UMCustomLogInfoBuilder stack(String str) {
        this.stack = "Exception message:\nBack traces starts.\n" + str + "Back traces ends.";
        return this;
    }

    public UMCustomLogInfoBuilder addSection(String str) {
        this.sectionList.add(str);
        return this;
    }

    public UMCustomLogInfoBuilder uploadNow(boolean z) {
        this.mCustomLogInfo.mUploadNow = z;
        return this;
    }

    public UMCustomLogInfoBuilder addLogCat(boolean z) {
        this.mCustomLogInfo.mAddLogcat = z;
        return this;
    }

    public CustomLogInfo build() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : this.kvInfoMap.entrySet()) {
            stringBuffer.append(entry.getKey());
            stringBuffer.append(Constants.COLON_SEPARATOR);
            stringBuffer.append(entry.getValue());
            stringBuffer.append("\n");
        }
        if (!TextUtils.isEmpty(this.stack)) {
            stringBuffer.append(this.stack);
            stringBuffer.append("\n");
        }
        for (String str : this.sectionList) {
            stringBuffer.append("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---");
            stringBuffer.append("\n");
            stringBuffer.append(str);
            stringBuffer.append("\n");
        }
        CustomLogInfo customLogInfo = this.mCustomLogInfo;
        customLogInfo.mData = stringBuffer;
        return customLogInfo;
    }
}
