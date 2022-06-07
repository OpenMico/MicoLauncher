package com.xiaomi.accountsdk.request;

import android.text.TextUtils;
import com.xiaomi.accountsdk.account.PassportCAConstants;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;

/* loaded from: classes2.dex */
class IPDiagnosisStatHelper extends RequestWithIPStatHelper {
    private ArrayList<String> usedIps = new ArrayList<>();
    private ArrayList<String> usedNets = new ArrayList<>();

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onBackupIpSucceeded(int i) {
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onCachedIpSucceed() {
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onIpRequestStarted() {
        statCountEvent(BaseConstants.STAT_CATEGORY_IP, BaseConstants.STAT_KEY_IP_REQUEST_STARTED);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onIpRequest200Succeed() {
        statCountEvent(BaseConstants.STAT_CATEGORY_IP, BaseConstants.STAT_KEY_IP_REQUEST_SUCCEED);
        statAllIps(true);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onIpRequestIOSucceed() {
        statCountEvent(BaseConstants.STAT_CATEGORY_IP, BaseConstants.STAT_KEY_IP_REQUEST_IO_SUCCEEDED);
        statAllIps(true);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onIpRequestFailed() {
        statCountEvent(BaseConstants.STAT_CATEGORY_IP, BaseConstants.STAT_KEY_IP_REQUEST_FAILED);
        statAllIps(false);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onCachedIpStarted(String str) {
        this.usedIps.add(str);
        this.usedNets.add(getNetworkName());
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onCachedIpFailed() {
        statCountEvent(BaseConstants.STAT_CATEGORY_IP, BaseConstants.STAT_KEY_CACHED_IP_FAILED);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsip0Started(String str) {
        this.usedIps.add(str);
        this.usedNets.add(getNetworkName());
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsIp0Succeed() {
        statDummyUrl(String.format("http://dummyurl/cachedIpDiagonose?_ver=%s&cacheipnet=%s&cachedip=%s&dnsipnet=%s&dnsip=%s&finalnet=%s", PassportCAConstants.IMPL_VERSION, this.usedNets.get(0), this.usedIps.get(0), this.usedNets.get(1), this.usedIps.get(1), getNetworkName()));
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onDnsIp0Failed() {
        statCountEvent(BaseConstants.STAT_CATEGORY_IP, BaseConstants.STAT_KEY_DNS_IP_0_FAILED);
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onBackupIpStarted(int i, String str) {
        this.usedIps.add(str);
        this.usedNets.add(getNetworkName());
    }

    @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
    public void onBackupIpFailed(int i) {
        statCountEvent(BaseConstants.STAT_CATEGORY_IP, BaseConstants.STAT_KEY_BACKUP_IP_FAILED + i);
    }

    void statAllIps(boolean z) {
        statDummyUrl(String.format("http://dummyurl/IpDiagonose?_ver=%s&_ips=%s&_nets=%s&_ipResult=%s", PassportCAConstants.IMPL_VERSION, TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, this.usedIps), TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, this.usedNets), Boolean.valueOf(z)));
    }
}
