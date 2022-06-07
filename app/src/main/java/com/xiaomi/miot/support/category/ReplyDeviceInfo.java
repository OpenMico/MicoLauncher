package com.xiaomi.miot.support.category;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class ReplyDeviceInfo {
    private long pkgId;
    private int pkgTotalSize;
    private int receivedCount;
    private Map<Integer, String> pkgData = new HashMap(2);
    private long firstReceiveTime = System.currentTimeMillis();

    public ReplyDeviceInfo(long j, int i) {
        this.pkgId = j;
        this.pkgTotalSize = i;
    }

    public boolean isFinished() {
        return this.receivedCount == this.pkgTotalSize;
    }

    public void addPkgData(int i, String str) {
        this.pkgData.put(Integer.valueOf(i), str);
        this.receivedCount++;
    }

    public String getReplyDeviceInfo() {
        if (!isFinished()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= this.pkgTotalSize; i++) {
            sb.append(this.pkgData.get(Integer.valueOf(i)));
        }
        return sb.toString();
    }

    public boolean canClear() {
        return System.currentTimeMillis() - this.firstReceiveTime >= 5000;
    }
}
