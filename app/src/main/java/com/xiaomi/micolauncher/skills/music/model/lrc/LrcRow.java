package com.xiaomi.micolauncher.skills.music.model.lrc;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.mipush.sdk.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class LrcRow implements Serializable, Comparable<LrcRow> {
    public static final String APOS = "&apos;";
    public static final String SINGLE_QUOTE = "'";
    private static Pattern a = Pattern.compile("\\[.*?\\]");
    private final String mContent;
    private final int mTimeMillis;
    private final String mTimeString;
    private int mTotalTime;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<LrcRow> a(String str) {
        if (!str.startsWith("[")) {
            return null;
        }
        if (!(str.indexOf("]") == 9 || str.indexOf("]") == 10)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf("]") + 1;
        String substring = str.substring(lastIndexOf);
        String[] split = str.substring(0, lastIndexOf).replace("[", Constants.ACCEPT_TIME_SEPARATOR_SERVER).replace("]", Constants.ACCEPT_TIME_SEPARATOR_SERVER).split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        ArrayList arrayList = new ArrayList();
        String str2 = substring;
        for (String str3 : split) {
            if (!TextUtils.isEmpty(str3.trim()) && !TextUtils.isEmpty(str2.trim())) {
                try {
                    if (str2.contains(APOS)) {
                        str2 = str2.replace(APOS, SINGLE_QUOTE);
                    }
                    arrayList.add(new LrcRow(str3, b(str3), str2));
                } catch (Exception e) {
                    L.player.e("LrcRow", e.getMessage());
                }
            }
        }
        return arrayList;
    }

    private static int b(String str) {
        String[] split = str.replace('.', ':').split(Constants.COLON_SEPARATOR);
        return (Integer.parseInt(split[0]) * 60 * 1000) + (Integer.parseInt(split[1]) * 1000) + (Integer.parseInt(split[2]) * 10);
    }

    private LrcRow(String str, int i, String str2) {
        this.mTimeString = str;
        this.mTimeMillis = i;
        this.mContent = str2;
    }

    public String getTimeString() {
        return this.mTimeString;
    }

    public int getTimeMillis() {
        return this.mTimeMillis;
    }

    public String getContent() {
        return this.mContent;
    }

    public long getTotalTime() {
        return this.mTotalTime;
    }

    public void setTotalTime(int i) {
        this.mTotalTime = i;
    }

    public int compareTo(@NonNull LrcRow lrcRow) {
        return this.mTimeMillis - lrcRow.mTimeMillis;
    }

    public String toString() {
        return "LrcRow [timeString=" + this.mTimeString + ", timeMillis=" + this.mTimeMillis + ", content=" + this.mContent + "]";
    }

    public static LrcRow buildLoadingLrcRow(String str) {
        return new LrcRow("[00:00.00]", 0, str);
    }
}
