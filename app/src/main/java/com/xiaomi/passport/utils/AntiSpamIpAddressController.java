package com.xiaomi.passport.utils;

import android.text.TextUtils;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class AntiSpamIpAddressController {
    private static final String IP_ADDRESS_KEY = "ipAddress";
    private static final String TAG = "AntiSpamIpAddressController";
    private static final String URL_CHECK_IPV6_REQUEST = URLs.URL_ACCOUNT_BASE + "/ip/next";
    private static final Map<String, String> ipAddressCookie = new HashMap();
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public void init() {
        Executors.newCachedThreadPool().execute(new Runnable() { // from class: com.xiaomi.passport.utils.AntiSpamIpAddressController.1
            @Override // java.lang.Runnable
            public void run() {
                if (AntiSpamIpAddressController.hasIPv6()) {
                    String antiSpamIPAddress = AntiSpamIpAddressController.this.getAntiSpamIPAddress();
                    if (!TextUtils.isEmpty(antiSpamIPAddress)) {
                        AccountLog.i(AntiSpamIpAddressController.TAG, "ipAddress not empty");
                        AntiSpamIpAddressController.ipAddressCookie.clear();
                        AntiSpamIpAddressController.ipAddressCookie.put(AntiSpamIpAddressController.IP_ADDRESS_KEY, antiSpamIPAddress);
                    }
                }
                AntiSpamIpAddressController.countDownLatch.countDown();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasIPv6() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement = inetAddresses.nextElement();
                    if (!nextElement.isLoopbackAddress() && (nextElement instanceof Inet6Address) && !nextElement.getHostAddress().startsWith("fe80")) {
                        return true;
                    }
                }
            }
            return false;
        } catch (SocketException e) {
            AccountLog.w(TAG, e);
            return true;
        }
    }

    public Map<String, String> blockingGetIPAddressCookie() {
        try {
            countDownLatch.await(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            AccountLog.w(TAG, "blockingGetIPAddressCookie", e);
        }
        return ipAddressCookie;
    }

    public Map<String, String> getIPAddressCookie() {
        return ipAddressCookie;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAntiSpamIPAddress() {
        SimpleRequest.StringContent asString;
        try {
            asString = SimpleRequestForAccount.getAsString(URL_CHECK_IPV6_REQUEST, null, null, true);
        } catch (AccessDeniedException | AuthenticationFailureException | IOException | JSONException e) {
            AccountLog.w(TAG, "getAntiSpamIPAddress", e);
        }
        if (asString == null) {
            AccountLog.i(TAG, "getAntiSpamIPAddress: response content is null");
            return null;
        }
        JSONObject jSONObject = new JSONObject(XMPassport.removeSafePrefixAndGetRealBody(asString));
        int i = jSONObject.getInt("code");
        String optString = jSONObject.optString("description");
        AccountLog.i(TAG, "getAntiSpamIPAddress--code: " + i + " ,desc: " + optString);
        if (i == 0) {
            JSONObject jSONObject2 = new JSONObject(jSONObject.getString("data"));
            boolean z = jSONObject2.getBoolean("hasNextUrl");
            AccountLog.i(TAG, "next: " + z);
            if (z) {
                return ipv6NextRequest(jSONObject2.getString("url"));
            }
        }
        return null;
    }

    private String ipv6NextRequest(String str) {
        SimpleRequest.StringContent asString;
        try {
            asString = SimpleRequestForAccount.getAsString(str, null, null, true);
        } catch (AccessDeniedException | AuthenticationFailureException | IOException | JSONException e) {
            AccountLog.w(TAG, "ipv6NextRequest", e);
        }
        if (asString == null) {
            AccountLog.i(TAG, "ipv6NextRequest: next url response content is null");
            return null;
        }
        JSONObject jSONObject = new JSONObject(XMPassport.removeSafePrefixAndGetRealBody(asString));
        int i = jSONObject.getInt("code");
        String string = jSONObject.getString("description");
        AccountLog.i(TAG, "ipv6NextRequest--code: " + i + " ,desc: " + string);
        if (i == 0) {
            return asString.getHeader(IP_ADDRESS_KEY);
        }
        return null;
    }
}
