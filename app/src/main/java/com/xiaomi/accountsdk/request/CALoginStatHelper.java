package com.xiaomi.accountsdk.request;

import android.os.SystemClock;
import com.umeng.analytics.pro.ai;
import com.xiaomi.accountsdk.account.PassportCAConstants;
import com.xiaomi.accountsdk.account.XMPassportSettings;
import com.xiaomi.accountsdk.hasheddeviceidlib.HashedDeviceIdUtil;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
class CALoginStatHelper extends RequestWithIPStatHelper {
    final Info cachedIpInfo = new Info();
    final Info dnsIpInfo = new Info();
    final Map<Integer, Info> backupIpInfos = new LinkedHashMap();
    final Info hostNameInfo = new Info();
    final Info dnsResolvingInfo = new Info();
    final Info overallInfo = new Info();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Info {
        String ip = null;
        Long startTime = null;
        Long finishTime = null;
        Boolean result = null;

        Info() {
        }

        String timeSpent(Long l) {
            if (l == null) {
                l = 0L;
            }
            Long l2 = this.startTime;
            if (l2 != null && this.finishTime != null) {
                return String.format("%d_%d", Long.valueOf(l2.longValue() - l.longValue()), Long.valueOf(this.finishTime.longValue() - l.longValue()));
            }
            Long l3 = this.startTime;
            if (l3 != null) {
                return String.format("%d_", Long.valueOf(l3.longValue() - l.longValue()));
            }
            Long l4 = this.finishTime;
            if (l4 != null) {
                return String.format("_%d", Long.valueOf(l4.longValue() - l.longValue()));
            }
            return null;
        }

        String resultString() {
            Boolean bool = this.result;
            if (bool == null) {
                return null;
            }
            return bool.booleanValue() ? "1" : "0";
        }

        void start(String str) {
            this.ip = str;
            this.startTime = Long.valueOf(SystemClock.elapsedRealtime());
        }

        void finish(boolean z) {
            this.result = Boolean.valueOf(z);
            this.finishTime = Long.valueOf(SystemClock.elapsedRealtime());
        }
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onCachedIpStarted(String str) {
        this.cachedIpInfo.start(str);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onCachedIpSucceed() {
        this.cachedIpInfo.finish(true);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onCachedIpFailed() {
        this.cachedIpInfo.finish(false);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsip0Started(String str) {
        this.dnsIpInfo.start(str);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsIp0Succeed() {
        this.dnsIpInfo.finish(true);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsIp0Failed() {
        this.dnsIpInfo.finish(false);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onBackupIpStarted(int i, String str) {
        backupIpInfo(i).start(str);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onBackupIpSucceeded(int i) {
        backupIpInfo(i).finish(true);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onBackupIpFailed(int i) {
        backupIpInfo(i).finish(false);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onHostNameRequestStarted() {
        this.hostNameInfo.start(null);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onHostNameRequestFinished(boolean z) {
        this.hostNameInfo.finish(z);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsResolvingStarted() {
        this.dnsResolvingInfo.start(null);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsResolvingFinished(String str) {
        this.dnsResolvingInfo.finish(str != null);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void start() {
        this.overallInfo.start(null);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void finish() {
        this.overallInfo.finish(computeOverallResult());
        statDummyUrl(buildDummyUrl());
    }

    final boolean computeOverallResult() {
        boolean z = (this.cachedIpInfo.result != null && this.cachedIpInfo.result.booleanValue()) | false | (this.dnsIpInfo.result != null && this.dnsIpInfo.result.booleanValue()) | (this.hostNameInfo.result != null && this.hostNameInfo.result.booleanValue());
        for (Info info : this.backupIpInfos.values()) {
            z |= info.result != null && info.result.booleanValue();
        }
        return z;
    }

    private final Info backupIpInfo(int i) {
        Info info = this.backupIpInfos.get(Integer.valueOf(i));
        if (info != null) {
            return info;
        }
        Info info2 = new Info();
        this.backupIpInfos.put(Integer.valueOf(i), info2);
        return info2;
    }

    final String buildDummyUrl() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("c_ip", this.cachedIpInfo.ip);
        linkedHashMap.put("c_t", this.cachedIpInfo.timeSpent(this.overallInfo.startTime));
        linkedHashMap.put("c_r", this.cachedIpInfo.resultString());
        linkedHashMap.put("r_t", this.dnsResolvingInfo.timeSpent(this.overallInfo.startTime));
        linkedHashMap.put("d_ip", this.dnsIpInfo.ip);
        linkedHashMap.put("d_t", this.dnsIpInfo.timeSpent(this.overallInfo.startTime));
        linkedHashMap.put("d_r", this.dnsIpInfo.resultString());
        for (Integer num : this.backupIpInfos.keySet()) {
            Info info = this.backupIpInfos.get(num);
            if (info != null) {
                String str = "b" + num + "_";
                linkedHashMap.put(str + "ip", info.ip);
                linkedHashMap.put(str + ai.aF, info.timeSpent(this.overallInfo.startTime));
                linkedHashMap.put(str + "r", info.resultString());
            } else {
                throw new NullPointerException("should no be null here");
            }
        }
        linkedHashMap.put("h_t", this.hostNameInfo.timeSpent(this.overallInfo.startTime));
        linkedHashMap.put("h_r", this.hostNameInfo.resultString());
        Info info2 = this.overallInfo;
        linkedHashMap.put("all_t", info2.timeSpent(info2.startTime));
        linkedHashMap.put("all_r", this.overallInfo.resultString());
        linkedHashMap.put("hdid", new HashedDeviceIdUtil(XMPassportSettings.getApplicationContext()).getHashedDeviceIdNoThrow());
        StringBuffer stringBuffer = new StringBuffer("http://dummyurl/caLoginDiagnosis?");
        stringBuffer.append("_ver");
        stringBuffer.append('=');
        stringBuffer.append(PassportCAConstants.IMPL_VERSION);
        for (String str2 : linkedHashMap.keySet()) {
            String str3 = (String) linkedHashMap.get(str2);
            if (!(str2 == null || str3 == null)) {
                stringBuffer.append(MusicGroupListActivity.SPECIAL_SYMBOL);
                stringBuffer.append(str2);
                stringBuffer.append('=');
                stringBuffer.append(str3);
            }
        }
        return stringBuffer.toString();
    }
}
