package com.xiaomi.accountsdk.request;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
class IPInfos {
    private Map<Index, List<String>> ipMap = new ConcurrentHashMap();
    private Map<Index, Long> timeStampMap = new ConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Index {
        final String host;
        final String networkName;

        public int hashCode() {
            if (this.host == null && this.networkName == null) {
                return "null#null".hashCode();
            }
            String str = this.host;
            if (str != null && this.networkName != null) {
                return str.hashCode() + this.networkName.hashCode();
            }
            String str2 = this.host;
            if (str2 != null) {
                return str2.hashCode();
            }
            return this.networkName.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Index)) {
                return false;
            }
            Index index = (Index) obj;
            return TextUtils.equals(this.host, index.host) && TextUtils.equals(this.networkName, index.networkName);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public Index(String str, String str2) {
            this.host = str;
            this.networkName = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<String> getIpList(Index index) {
        if (index == null) {
            return null;
        }
        return this.ipMap.get(index);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIpList(Index index, List<String> list) {
        if (index != null) {
            if (list == null) {
                this.ipMap.remove(index);
            } else {
                this.ipMap.put(index, list);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIpAsList(Index index, String str) {
        if (index != null) {
            if (str == null) {
                this.ipMap.remove(index);
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(str);
            this.ipMap.put(index, arrayList);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getIp0(Index index) {
        List<String> ipList;
        if (index == null || (ipList = getIpList(index)) == null || ipList.size() <= 0) {
            return null;
        }
        return ipList.get(0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Long getTimeStamp(Index index) {
        if (index == null) {
            return null;
        }
        return this.timeStampMap.get(index);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setTimeStamp(Index index, long j) {
        if (index != null) {
            this.timeStampMap.put(index, Long.valueOf(j));
        }
    }
}
