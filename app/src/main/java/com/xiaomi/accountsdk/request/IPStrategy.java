package com.xiaomi.accountsdk.request;

import android.text.TextUtils;
import android.util.Pair;
import com.xiaomi.accountsdk.account.PassportCAConstants;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.request.IPInfos;
import com.xiaomi.accountsdk.request.PassportSimpleRequest;
import com.xiaomi.accountsdk.request.RequestWithIP;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.library.common.network.Network;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class IPStrategy {
    private static final String TAG = "IPStrategy";
    private static IPInfos sBackupIpInfos = new IPInfos();
    private static IPInfos sCachedIpInfos = new IPInfos();
    private static Map<String, List<String>> sHardCodedBackupIpLists = new HashMap();
    private static IPUtilExternal sIpUtilExternal;

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("183.84.5.8");
        arrayList.add("111.13.142.141");
        sHardCodedBackupIpLists.put("c.id.mi.com", arrayList);
    }

    public static void setIpUtilExternal(IPUtilExternal iPUtilExternal) {
        sIpUtilExternal = iPUtilExternal;
    }

    public String getCachedIp(String str) {
        IPUtilExternal iPUtilExternal;
        String networkName = getNetworkName();
        IPInfos.Index index = new IPInfos.Index(str, networkName);
        String ip0 = sCachedIpInfos.getIp0(index);
        if (!(ip0 != null || (iPUtilExternal = sIpUtilExternal) == null || (ip0 = iPUtilExternal.loadCachedIp(str, networkName, null)) == null)) {
            sCachedIpInfos.setIpAsList(index, ip0);
        }
        return ip0;
    }

    public List<String> getBackupIpList(String str) {
        IPUtilExternal iPUtilExternal;
        String networkName = getNetworkName();
        IPInfos.Index index = new IPInfos.Index(str, networkName);
        List<String> ipList = sBackupIpInfos.getIpList(index);
        if (!(ipList != null || (iPUtilExternal = sIpUtilExternal) == null || (ipList = iPUtilExternal.loadBackupIpList(str, networkName, null)) == null)) {
            sBackupIpInfos.setIpList(index, ipList);
        }
        return ipList == null ? sHardCodedBackupIpLists.get(str) : ipList;
    }

    public String getDnsIp0(String str, String str2) {
        InetAddress[] resolveDNS;
        try {
            resolveDNS = resolveDNS(str);
        } catch (UnknownHostException unused) {
        }
        if (resolveDNS == null) {
            return null;
        }
        if (resolveDNS.length == 1 && resolveDNS[0] != null) {
            return resolveDNS[0].getHostAddress();
        }
        for (InetAddress inetAddress : resolveDNS) {
            if (inetAddress != null) {
                String hostAddress = inetAddress.getHostAddress();
                if (!TextUtils.equals(hostAddress, str2)) {
                    return hostAddress;
                }
            }
        }
        return null;
    }

    protected InetAddress[] resolveDNS(String str) throws UnknownHostException {
        return Inet4Address.getAllByName(str);
    }

    public void updateCachedIpIfNeeded(final String str, String str2, final long j) {
        if (isCachedIpExpired(str)) {
            final CachedIpStatHelper cachedIpStatHelper = new CachedIpStatHelper();
            cachedIpStatHelper.putIpPingResult(str2, true, j);
            cachedIpStatHelper.setCachedIp(str2);
            if (j <= IPStrategyConfig.getPingThreshold()) {
                cachedIpStatHelper.onLessThenPingThreshold(j, IPStrategyConfig.getPingThreshold());
                updateCachedTimeStamp(str);
                return;
            }
            final String dnsIp0 = getDnsIp0(str, str2);
            final String backupIp0 = getBackupIp0(str);
            cachedIpStatHelper.setDnsIp0(dnsIp0);
            cachedIpStatHelper.setBackupip0(backupIp0);
            final String networkName = getNetworkName();
            IPExecutorService.execute(new Runnable() { // from class: com.xiaomi.accountsdk.request.IPStrategy.1
                @Override // java.lang.Runnable
                public void run() {
                    Pair<String, Long> ping = IPStrategy.this.ping(str, new String[]{dnsIp0, backupIp0}, cachedIpStatHelper);
                    String networkName2 = IPStrategy.this.getNetworkName();
                    if (!TextUtils.equals(networkName, networkName2)) {
                        cachedIpStatHelper.onNetworkChanged(networkName, networkName2);
                        AccountLog.w(IPStrategy.TAG, String.format("ping: network changed from %s to %s, will NOT update anything", networkName, networkName2));
                    } else if (ping == null) {
                        IPStrategy.this.updateCachedTimeStamp(str);
                    } else {
                        cachedIpStatHelper.onAllIpPingFinishes(IPStrategyConfig.getPingTimeCoefficient());
                        String str3 = (String) ping.first;
                        if (((Long) ping.second).longValue() * IPStrategyConfig.getPingTimeCoefficient() < j) {
                            IPStrategy.this.updateCachedIpAndTimeStamp(str, str3);
                        } else {
                            IPStrategy.this.updateCachedTimeStamp(str);
                        }
                    }
                }
            });
        }
    }

    public void updateBackupIpListIfNeeded(final String str) {
        if (isBackupListExpired(str)) {
            final String networkName = getNetworkName();
            IPExecutorService.execute(new Runnable() { // from class: com.xiaomi.accountsdk.request.IPStrategy.2
                @Override // java.lang.Runnable
                public void run() {
                    String networkName2 = IPStrategy.this.getNetworkName();
                    if (!TextUtils.equals(networkName, networkName2)) {
                        AccountLog.w(IPStrategy.TAG, String.format("backupList: network changed from %s to %s, will NOT update anything", networkName, networkName2));
                        new BackupIpStatHelper().onNetworkChangedBeforePullingBackupIps();
                        return;
                    }
                    List<String> backupIpListOnline = IPStrategy.this.getBackupIpListOnline(str);
                    new BackupIpStatHelper().onPullingBackupIpsSucceeded(backupIpListOnline);
                    IPStrategy.this.updateBackupIpList(str, networkName2, backupIpListOnline);
                }
            });
            IPExecutorService.execute(new Runnable() { // from class: com.xiaomi.accountsdk.request.IPStrategy.3
                @Override // java.lang.Runnable
                public void run() {
                    String networkName2 = IPStrategy.this.getNetworkName();
                    if (!TextUtils.equals(networkName, networkName2)) {
                        AccountLog.w(IPStrategy.TAG, String.format("config, network changed from %s to %s, will NOT update anything", networkName, networkName2));
                    } else {
                        IPStrategy.this.updateStrategyConfigOnline();
                    }
                }
            });
        }
    }

    public void updateCachedIpAndTimeStamp(String str, String str2) {
        updateCachedIp(str, getNetworkName(), str2);
        updateCachedTimeStamp(str);
    }

    void updateBackupIpListAndTimeStamp(String str, List<String> list) {
        updateBackupIpList(str, getNetworkName(), list);
        updateBackupTimeStamp(str);
    }

    protected List<String> getBackupIpListOnline(String str) {
        JSONObject jSONObject;
        JSONArray jSONArray;
        Boolean isWifi = isWifi();
        if (isWifi == null) {
            return null;
        }
        PassportRequestArguments passportRequestArguments = new PassportRequestArguments();
        passportRequestArguments.setUrl(String.format("http://resolver.msg.xiaomi.net/gslb/?ver=3.0&list=%s", str));
        try {
            JSONObject jSONObject2 = new JSONObject(new PassportSimpleRequest.GetAsString(passportRequestArguments).execute().getBody()).getJSONObject("R");
            if (isWifi.booleanValue()) {
                jSONObject = jSONObject2.getJSONObject(Network.NETWORK_TYPE_WIFI);
            } else {
                jSONObject = jSONObject2.getJSONObject("wap");
            }
            if (jSONObject == null || (jSONArray = jSONObject.getJSONArray(str)) == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                Object obj = jSONArray.get(i);
                if (obj != null) {
                    arrayList.add(obj.toString());
                }
            }
            return arrayList;
        } catch (PassportRequestException e) {
            AccountLog.w(TAG, "getBackupIpListOnline error, cause : ", e.getCause());
            return null;
        } catch (IOException e2) {
            AccountLog.w(TAG, "getBackupIpListOnline", e2);
            return null;
        } catch (JSONException e3) {
            AccountLog.w(TAG, "getBackupIpListOnline error, cause : ", e3.getCause());
            return null;
        }
    }

    protected Boolean isWifi() {
        IPUtilExternal iPUtilExternal = sIpUtilExternal;
        if (iPUtilExternal == null) {
            return null;
        }
        return Boolean.valueOf(iPUtilExternal.isWifi());
    }

    protected void updateStrategyConfigOnline() {
        long cachedIpExpireDurationMs = IPStrategyConfig.getCachedIpExpireDurationMs();
        long backupIpListExpireDurationMs = IPStrategyConfig.getBackupIpListExpireDurationMs();
        long pingThreshold = IPStrategyConfig.getPingThreshold();
        long pingTimeCoefficient = IPStrategyConfig.getPingTimeCoefficient();
        PassportRequestArguments passportRequestArguments = new PassportRequestArguments();
        passportRequestArguments.setUrl("http://c.id.mi.com/conn/getParams");
        try {
            String body = new PassportSimpleRequest.GetAsString(passportRequestArguments).execute().getBody();
            if (!TextUtils.isEmpty(body)) {
                if (body.startsWith(XMPassport.PASSPORT_SAFE_PREFIX)) {
                    body = body.substring(11);
                }
                JSONObject jSONObject = new JSONObject(body).getJSONObject("connectivity_params");
                int optInt = jSONObject.optInt("backup_ip_expire", -1);
                if (optInt != -1) {
                    backupIpListExpireDurationMs = optInt * 1000;
                }
                int optInt2 = jSONObject.optInt("cached_ip_expire", -1);
                if (optInt2 != -1) {
                    cachedIpExpireDurationMs = optInt2 * 1000;
                }
                int optInt3 = jSONObject.optInt("ping_threshold", -1);
                if (optInt3 != -1) {
                    pingThreshold = optInt3;
                }
                int optInt4 = jSONObject.optInt("ping_time_coefficient", -1);
                if (optInt4 != -1) {
                    pingTimeCoefficient = optInt4;
                }
                IPStrategyConfig.setCachedIpExpireDurationMs(cachedIpExpireDurationMs);
                IPStrategyConfig.setBackupIpListExpireDurationMs(backupIpListExpireDurationMs);
                IPStrategyConfig.setPingThreshold(pingThreshold);
                IPStrategyConfig.setPingTimeCoefficient(pingTimeCoefficient);
                IPUtilExternal iPUtilExternal = sIpUtilExternal;
                if (iPUtilExternal != null) {
                    iPUtilExternal.saveCachedIpExpireDuration(cachedIpExpireDurationMs);
                    sIpUtilExternal.saveBackupIpListExpireDuration(backupIpListExpireDurationMs);
                    sIpUtilExternal.savePingThreshold(pingThreshold);
                    sIpUtilExternal.savePingTimeCoefficient(pingTimeCoefficient);
                }
            }
        } catch (PassportRequestException e) {
            AccountLog.w(TAG, "updateStrategyConfigOnline", e.getCause());
        } catch (IOException e2) {
            AccountLog.w(TAG, "updateStrategyConfigOnline", e2);
        } catch (JSONException e3) {
            AccountLog.w(TAG, "updateStrategyConfigOnline", e3);
        }
    }

    protected void updateCachedTimeStamp(String str) {
        String networkName = getNetworkName();
        IPInfos.Index index = new IPInfos.Index(str, networkName);
        long currentTimeMillis = System.currentTimeMillis();
        sCachedIpInfos.setTimeStamp(index, currentTimeMillis);
        IPUtilExternal iPUtilExternal = sIpUtilExternal;
        if (iPUtilExternal != null) {
            iPUtilExternal.saveCachedTimeStamp(str, networkName, currentTimeMillis);
        }
    }

    protected void updateBackupTimeStamp(String str) {
        String networkName = getNetworkName();
        IPInfos.Index index = new IPInfos.Index(str, networkName);
        long currentTimeMillis = System.currentTimeMillis();
        sBackupIpInfos.setTimeStamp(index, currentTimeMillis);
        IPUtilExternal iPUtilExternal = sIpUtilExternal;
        if (iPUtilExternal != null) {
            iPUtilExternal.saveBackupTimeStamp(str, networkName, currentTimeMillis);
        }
    }

    protected Pair<String, Long> ping(String str, String[] strArr, CachedIpStatHelper cachedIpStatHelper) {
        long j = Long.MAX_VALUE;
        String str2 = null;
        for (String str3 : strArr) {
            try {
                RequestWithIP.IpRequestResult ipRequestResult = new RequestWithIP.IpRequestResult();
                PassportRequestArguments passportRequestArguments = new PassportRequestArguments();
                passportRequestArguments.setUrl(String.format("http://%s/conn/echo", str));
                boolean executeWithIp = RequestWithIP.executeWithIp(new PassportSimpleRequest.GetAsString(passportRequestArguments), str, str3, ipRequestResult);
                cachedIpStatHelper.putIpPingResult(str3, executeWithIp, ipRequestResult.timeSpent);
                if (executeWithIp && ipRequestResult.timeSpent < j) {
                    try {
                        j = ipRequestResult.timeSpent;
                        str2 = str3;
                    } catch (PassportRequestException unused) {
                        str2 = str3;
                        cachedIpStatHelper.putIpPingException(str3);
                    }
                }
            } catch (PassportRequestException unused2) {
            }
        }
        if (str2 == null) {
            return null;
        }
        return Pair.create(str2, Long.valueOf(j));
    }

    protected String getBackupIp0(String str) {
        List<String> backupIpList = getBackupIpList(str);
        if (backupIpList == null || backupIpList.size() <= 0) {
            return null;
        }
        return backupIpList.get(0);
    }

    protected String getNetworkName() {
        IPUtilExternal iPUtilExternal = sIpUtilExternal;
        if (iPUtilExternal == null) {
            return null;
        }
        return iPUtilExternal.getNetworkName();
    }

    protected void updateCachedIp(String str, String str2, String str3) {
        sCachedIpInfos.setIpAsList(new IPInfos.Index(str, str2), str3);
        IPUtilExternal iPUtilExternal = sIpUtilExternal;
        if (iPUtilExternal != null) {
            iPUtilExternal.saveCachedIp(str, str2, str3);
        }
    }

    protected void updateBackupIpList(String str, String str2, List<String> list) {
        sBackupIpInfos.setIpList(new IPInfos.Index(str, str2), list);
        IPUtilExternal iPUtilExternal = sIpUtilExternal;
        if (iPUtilExternal != null) {
            iPUtilExternal.saveBackupIpList(str, str2, list);
        }
    }

    protected boolean isExpired(IPInfos iPInfos, String str, long j) {
        Long timeStamp = iPInfos.getTimeStamp(new IPInfos.Index(str, getNetworkName()));
        if (timeStamp == null) {
            return true;
        }
        return isExpiredSinceLastTime(timeStamp.longValue(), j);
    }

    boolean isExpiredSinceLastTime(long j, long j2) {
        return Math.abs(System.currentTimeMillis() - j) > j2;
    }

    protected boolean isCachedIpExpired(String str) {
        return isExpired(sCachedIpInfos, str, IPStrategyConfig.getCachedIpExpireDurationMs());
    }

    protected boolean isBackupListExpired(String str) {
        return isExpired(sBackupIpInfos, str, IPStrategyConfig.getBackupIpListExpireDurationMs());
    }

    static void resetIpInfosForTest() {
        sBackupIpInfos = new IPInfos();
        sCachedIpInfos = new IPInfos();
    }

    /* loaded from: classes2.dex */
    static class BackupIpStatHelper extends IPStatHelper {
        BackupIpStatHelper() {
        }

        public void onNetworkChangedBeforePullingBackupIps() {
            statCountEvent(BaseConstants.STAT_CATEGORY_BACKUP_IP_DIAGNOSIS, BaseConstants.STAT_KEY_PULLING_BACKUP_IP_WIFI_CHANGED);
        }

        public void onPullingBackupIpsSucceeded(List<String> list) {
            statDummyUrl(String.format("http://dummyurl/backupIpDiagonose?_ver=%s&_ips=%s&_nets=%s", PassportCAConstants.IMPL_VERSION, list == null ? null : TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, list), getNetworkName()));
        }
    }

    /* loaded from: classes2.dex */
    public static class CachedIpStatHelper extends IPStatHelper {
        private static final long IP_PING_TIME_EXCEPTION = -2;
        private static final long IP_PING_TIME_FAILED = -1;
        private final HashMap<String, Long> pingTimeMap = new HashMap<>();
        private String cachedIp = null;
        private String dnsIp0 = null;
        private String backupip0 = null;

        CachedIpStatHelper() {
        }

        public void setCachedIp(String str) {
            this.cachedIp = str;
        }

        public void setDnsIp0(String str) {
            this.dnsIp0 = str;
        }

        public void setBackupip0(String str) {
            this.backupip0 = str;
        }

        public void putIpPingResult(String str, boolean z, long j) {
            HashMap<String, Long> hashMap = this.pingTimeMap;
            if (!z) {
                j = -1;
            }
            hashMap.put(str, Long.valueOf(j));
        }

        public void putIpPingException(String str) {
            this.pingTimeMap.put(str, -2L);
        }

        public void onLessThenPingThreshold(long j, long j2) {
            statDummyUrl(String.format("http://dummyurl/cachedipDiagonis?_ver=%s&_time=%d&_thres=%d", PassportCAConstants.IMPL_VERSION, Long.valueOf(j), Long.valueOf(j2)));
        }

        public void onNetworkChanged(String str, String str2) {
            statDummyUrl(String.format("http://dummyurl/cachedipDiagonis?_ver=%s&_netOld=%s&_netNew=%s", PassportCAConstants.IMPL_VERSION, str, str2));
        }

        public void onAllIpPingFinishes(long j) {
            String str = this.cachedIp;
            String str2 = this.dnsIp0;
            String str3 = this.backupip0;
            statDummyUrl(String.format("http://dummyurl/cachedipDiagonis?_ver=%s&_coef=%d&cachedip=%s&cachediptime=%d&dnsip=%s&dnsiptime=%d&backupip0=%s&backupip0time=%d", PassportCAConstants.IMPL_VERSION, Long.valueOf(j), str, this.pingTimeMap.get(str), str2, this.pingTimeMap.get(str2), str3, this.pingTimeMap.get(str3)));
        }
    }
}
