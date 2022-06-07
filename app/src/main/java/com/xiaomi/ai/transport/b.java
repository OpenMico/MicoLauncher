package com.xiaomi.ai.transport;

import com.blankj.utilcode.constant.CacheConstants;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xiaomi.ai.api.Network;
import com.xiaomi.ai.api.common.APIUtils;
import com.xiaomi.ai.b.f;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.core.a;
import com.xiaomi.ai.core.d;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class b {
    private a a;
    private String b;
    private String d;
    private String e;
    private long f;
    private OkHttpClient g;
    private d i;
    private int c = -1;
    private boolean h = false;

    public b(a aVar, String str) {
        this.a = aVar;
        this.d = str;
        Logger.b("HttpDns", "init url:" + str + ", channel type:" + aVar.getChannelType());
        e(str);
        if (this.a.getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_HTTP_DNS, true)) {
            this.g = new OkHttpClient.Builder().connectTimeout(this.a.getAivsConfig().getInt(AivsConfig.Connection.CONNECT_TIMEOUT), TimeUnit.SECONDS).build();
            if (this.a.getChannelType().equals("ws") && this.a.getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_HORSE_RACE, true)) {
                this.i = new d(this.a, this);
            }
        }
    }

    public ArrayNode a(ObjectNode objectNode, String str) {
        if (!objectNode.path(str).isObject()) {
            return null;
        }
        ObjectNode objectNode2 = (ObjectNode) objectNode.path(str);
        if (!objectNode2.path(this.b).isArray()) {
            return null;
        }
        ArrayNode arrayNode = (ArrayNode) objectNode2.path(this.b);
        if (arrayNode.size() > 0) {
            return arrayNode;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r4, java.lang.String r5, java.lang.String r6) {
        /*
            r3 = this;
            com.xiaomi.ai.core.a r0 = r3.a
            com.xiaomi.ai.core.b r0 = r0.getListener()
            com.xiaomi.ai.core.a r1 = r3.a
            java.lang.String r0 = r0.a(r1, r6)
            boolean r1 = com.xiaomi.ai.b.f.a(r0)
            r2 = 0
            if (r1 != 0) goto L_0x004d
            com.fasterxml.jackson.databind.ObjectMapper r1 = com.xiaomi.ai.api.common.APIUtils.getObjectMapper()     // Catch: IOException -> 0x0038
            com.fasterxml.jackson.databind.JsonNode r0 = r1.readTree(r0)     // Catch: IOException -> 0x0038
            com.fasterxml.jackson.databind.node.ObjectNode r0 = (com.fasterxml.jackson.databind.node.ObjectNode) r0     // Catch: IOException -> 0x0038
            if (r0 == 0) goto L_0x0036
            int r1 = r0.size()     // Catch: IOException -> 0x0033
            r2 = 32
            if (r1 <= r2) goto L_0x0036
            java.lang.String r1 = "HttpDns"
            java.lang.String r2 = "writeHttpDnsCache: dns cache size over limit, clear all"
            com.xiaomi.ai.log.Logger.c(r1, r2)     // Catch: IOException -> 0x0033
            r0.removeAll()     // Catch: IOException -> 0x0033
            goto L_0x0036
        L_0x0033:
            r1 = move-exception
            r2 = r0
            goto L_0x0039
        L_0x0036:
            r2 = r0
            goto L_0x004d
        L_0x0038:
            r1 = move-exception
        L_0x0039:
            java.lang.String r0 = "HttpDns"
            java.lang.String r1 = com.xiaomi.ai.log.Logger.throwableToString(r1)
            com.xiaomi.ai.log.Logger.d(r0, r1)
            com.xiaomi.ai.core.a r0 = r3.a
            com.xiaomi.ai.core.b r0 = r0.getListener()
            com.xiaomi.ai.core.a r1 = r3.a
            r0.b(r1, r6)
        L_0x004d:
            if (r2 != 0) goto L_0x0057
            com.fasterxml.jackson.databind.ObjectMapper r0 = com.xiaomi.ai.api.common.APIUtils.getObjectMapper()
            com.fasterxml.jackson.databind.node.ObjectNode r2 = r0.createObjectNode()
        L_0x0057:
            java.lang.String r4 = r3.f(r4)
            r2.put(r4, r5)
            com.xiaomi.ai.core.a r4 = r3.a
            com.xiaomi.ai.core.b r4 = r4.getListener()
            com.xiaomi.ai.core.a r5 = r3.a
            java.lang.String r0 = r2.toString()
            r4.a(r5, r6, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.ai.transport.b.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    private String c(String str, String str2) {
        String a = this.a.getListener().a(this.a, str2);
        Logger.a("HttpDns", "readHttpDnsCache at " + str2 + ", networkType:" + str + ", httpDnsCache:" + a);
        if (f.a(a)) {
            return null;
        }
        try {
            ObjectNode objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(a);
            if (objectNode.hasNonNull(f(str))) {
                return objectNode.get(f(str)).asText();
            }
        } catch (IOException e) {
            Logger.d("HttpDns", Logger.throwableToString(e));
            this.a.getListener().b(this.a, str2);
        }
        return null;
    }

    private void c(final String str) {
        Logger.b("HttpDns", "getOnlineIp: networkType:" + str);
        if (this.g != null) {
            String a = this.a.getListener().a(this.a, "last_refresh_http_dns_at");
            if (!f.a(a)) {
                long parseLong = Long.parseLong(a) + (this.a.getAivsConfig().getInt(AivsConfig.Connection.REFRESH_HTTP_DNS_INTERVAL, 30) * 1000);
                if (System.currentTimeMillis() < parseLong) {
                    Logger.b("HttpDns", "frequency limited, wait until " + new Date(parseLong).toString());
                    return;
                }
            }
            this.a.getListener().a(this.a, "last_refresh_http_dns_at", String.valueOf(System.currentTimeMillis()));
            StringBuilder sb = new StringBuilder("https://resolver.mi.xiaomi.com/gslb/");
            sb.append("?protocol=");
            sb.append(this.a.getChannelType());
            sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            sb.append("list=");
            sb.append(this.b);
            sb.append("&did=");
            sb.append(this.a.getClientInfo().getDeviceId().get());
            Logger.b("HttpDns", "getOnlineIp: requestURL=" + sb.toString());
            this.g.newCall(new Request.Builder().url(sb.toString()).get().build()).enqueue(new Callback() { // from class: com.xiaomi.ai.transport.b.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    Logger.d("HttpDns", Logger.throwableToString(iOException));
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    ObjectNode objectNode;
                    ArrayNode a2;
                    String str2;
                    String str3;
                    ArrayNode a3;
                    if (response != null) {
                        try {
                            if (response.isSuccessful()) {
                                String string = response.body().string();
                                Logger.a("HttpDns", "getOnlineIp: response body=" + string);
                                if (!f.a(string) && (objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(string)) != null && objectNode.path("R").isObject()) {
                                    ObjectNode objectNode2 = (ObjectNode) objectNode.path("R");
                                    if (Network.NetworkType.DATA.name().equals(str)) {
                                        a2 = b.this.a(objectNode2, "wap");
                                        str2 = "HttpDns";
                                        str3 = "getOnlineIp: save network type wap";
                                    } else {
                                        a2 = b.this.a(objectNode2, com.xiaomi.smarthome.library.common.network.Network.NETWORK_TYPE_WIFI);
                                        str2 = "HttpDns";
                                        str3 = "getOnlineIp: save network type wifi";
                                    }
                                    Logger.a(str2, str3);
                                    if (a2 != null) {
                                        if (b.this.a.getChannelType().equals("xmd")) {
                                            b.this.a(a2, true, str, "xmd_dns_cache");
                                        } else {
                                            b.this.a(a2, true, str, "http_dns_cache");
                                            if (b.this.i != null) {
                                                b.this.i.a(str);
                                            }
                                        }
                                    }
                                    if (b.this.a.getChannelType().equals("ws") && b.this.a.getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_IPV6_HTTP_DNS) && objectNode2.path("ipv6").isObject() && (a3 = b.this.a(objectNode2, "ipv6")) != null) {
                                        Logger.a("HttpDns", "getOnlineIp: save network type ipv6");
                                        b.this.a(a3, true, str, "ipv6_http_dns_cache");
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                        } catch (Exception e) {
                            Logger.d("HttpDns", Logger.throwableToString(e));
                            return;
                        }
                    }
                    Logger.d("HttpDns", "getOnlineIp: response=" + response);
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private String d(String str) {
        String str2;
        if (this.a.getChannelType().equals("xmd")) {
            str2 = "xmd_dns_cache";
        } else {
            str2 = "http_dns_cache";
            if (this.a.getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_IPV6_HTTP_DNS)) {
                boolean c = this.a.getListener().c();
                if (!this.h && c) {
                    str2 = "ipv6_http_dns_cache";
                }
                Logger.b("HttpDns", "getLocalIp: networkType:" + str + ", ipv6Available:" + c + ",mIpv6ConnectFailed:" + this.h);
            }
        }
        String c2 = c(str, str2);
        if (!f.a(c2)) {
            try {
                ObjectNode objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(c2);
                this.f = objectNode.path("expire_at").asLong();
                if (System.currentTimeMillis() > this.f + 10000) {
                    Logger.c("HttpDns", "getLocalIp: local dns expired, mExpireAt=" + this.f);
                    c2 = c2;
                } else {
                    c2 = c2;
                    if (objectNode.path(com.xiaomi.onetrack.api.b.P).isArray()) {
                        ArrayNode arrayNode = (ArrayNode) objectNode.path(com.xiaomi.onetrack.api.b.P);
                        c2 = c2;
                        if (arrayNode.size() > 0) {
                            String asText = arrayNode.get(0).asText();
                            boolean a = f.a(asText);
                            c2 = a;
                            if (a == 0) {
                                return asText;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Logger.d("HttpDns", Logger.throwableToString(e));
                Logger.d("HttpDns", "getLocalIp:parse local ip failed: " + c2);
            }
        }
        b(str, str2);
        return null;
    }

    private void e(String str) {
        if (!f.a(str)) {
            try {
                URI uri = new URI(str);
                this.b = uri.getHost();
                if (uri.getPort() != -1) {
                    this.c = uri.getPort();
                }
                Logger.b("HttpDns", "parse: host=" + this.b + ", port=" + this.c);
            } catch (URISyntaxException e) {
                Logger.d("HttpDns", Logger.throwableToString(e));
                throw new IllegalArgumentException("url=" + str);
            }
        } else {
            throw new IllegalArgumentException("url=" + str);
        }
    }

    private String f(String str) {
        return this.b + Constants.ACCEPT_TIME_SEPARATOR_SERVER + str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + g(str);
    }

    private String g(String str) {
        if (!Network.NetworkType.WIFI.name().equals(str)) {
            return "not-wifi";
        }
        String b = this.a.getListener().b();
        return f.a(b) ? "unknown-wifi-ssid" : b;
    }

    public ArrayNode a(String str, String str2) {
        String c = c(str2, str);
        if (!f.a(c)) {
            try {
                ObjectNode objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(c);
                this.f = objectNode.path("expire_at").asLong();
                if (System.currentTimeMillis() > this.f + 10000) {
                    Logger.c("HttpDns", "getLocalIp: local dns expired, mExpireAt=" + this.f);
                    c = c;
                } else {
                    c = c;
                    if (objectNode.path(com.xiaomi.onetrack.api.b.P).isArray()) {
                        ArrayNode arrayNode = (ArrayNode) objectNode.path(com.xiaomi.onetrack.api.b.P);
                        int size = arrayNode.size();
                        c = size;
                        if (size > 0) {
                            return arrayNode;
                        }
                    }
                }
            } catch (Exception e) {
                Logger.d("HttpDns", Logger.throwableToString(e));
                Logger.d("HttpDns", "getLocalIp:parse local ip failed: " + c);
            }
        }
        b(str2, str);
        return null;
    }

    public String a() {
        return this.b;
    }

    public String a(String str) {
        Logger.b("HttpDns", "getDnsUrl: networkType:" + str);
        if (!this.a.getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_HTTP_DNS, true) || this.d.startsWith("wss://")) {
            Logger.b("HttpDns", "getDnsUrl: httpdns is disabled");
            return this.d;
        }
        synchronized (b.class) {
            String d = d(str);
            if (d != null) {
                Logger.b("HttpDns", "getDnsUrl: localIp=" + d);
                this.e = d;
                if (this.c != -1) {
                    String str2 = this.d;
                    return str2.replaceFirst(this.b + Constants.COLON_SEPARATOR + this.c, d);
                }
                return this.d.replaceFirst(this.b, d);
            }
            this.e = null;
            c(str);
            Logger.b("HttpDns", "getDnsUrl:  local dns failed, use default dns");
            return this.d;
        }
    }

    public void a(ArrayNode arrayNode, boolean z, String str, String str2) {
        Logger.a("HttpDns", "saveDns: networkType:" + str);
        ObjectNode createObjectNode = APIUtils.getObjectMapper().createObjectNode();
        createObjectNode.set(com.xiaomi.onetrack.api.b.P, arrayNode);
        if (z) {
            this.f = System.currentTimeMillis() + (this.a.getAivsConfig().getInt(AivsConfig.Connection.HTTP_DNS_EXPIRE_IN) * 1000);
        }
        createObjectNode.put("expire_at", this.f);
        Logger.b("HttpDns", "saveDns:" + createObjectNode.toString());
        a(str, createObjectNode.toString(), str2);
    }

    public String b() {
        return this.d;
    }

    public void b(String str) {
        String str2;
        ObjectNode objectNode;
        if (this.e != null) {
            Logger.b("HttpDns", "discardDns: networkType:" + str);
            synchronized (b.class) {
                if (this.a.getChannelType().equals("xmd")) {
                    str2 = "xmd_dns_cache";
                } else if (!this.a.getAivsConfig().getBoolean(AivsConfig.Connection.ENABLE_IPV6_HTTP_DNS) || !this.e.contains("[") || !this.e.contains("]")) {
                    str2 = "http_dns_cache";
                } else {
                    str2 = "ipv6_http_dns_cache";
                    this.h = true;
                    Logger.d("HttpDns", "connect ipv6 address " + this.e + " failed!");
                }
                String c = c(str, str2);
                if (!f.a(c)) {
                    try {
                        objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(c);
                    } catch (IOException e) {
                        Logger.d("HttpDns", Logger.throwableToString(e));
                    }
                    if (!objectNode.path(com.xiaomi.onetrack.api.b.P).isArray()) {
                        b(str, str2);
                        return;
                    }
                    ArrayNode arrayNode = (ArrayNode) objectNode.path(com.xiaomi.onetrack.api.b.P);
                    int i = 0;
                    while (true) {
                        if (i >= arrayNode.size()) {
                            break;
                        } else if (!this.e.equals(arrayNode.get(i).asText())) {
                            i++;
                        } else {
                            Logger.b("HttpDns", "discardDns: remove " + this.e);
                            arrayNode.remove(i);
                            if (arrayNode.size() > 0) {
                                a(arrayNode, false, str, str2);
                            } else {
                                if (this.a.getChannelType().equals("xmd")) {
                                    this.a.getListener().a(this.a, "xmd_ws_expire_at", String.valueOf((System.currentTimeMillis() / 1000) + ((long) CacheConstants.DAY)));
                                    Logger.b("HttpDns", "switch from xmd to ws next time");
                                }
                                b(str, str2);
                            }
                            this.e = null;
                        }
                    }
                    if (this.i != null) {
                        this.i.a(str);
                    }
                }
            }
        }
    }

    public void b(String str, String str2) {
        String a = this.a.getListener().a(this.a, str2);
        if (!f.a(a)) {
            try {
                ObjectNode objectNode = (ObjectNode) APIUtils.getObjectMapper().readTree(a);
                if (objectNode != null) {
                    objectNode.remove(f(str));
                    this.a.getListener().a(this.a, str2, objectNode.toString());
                }
            } catch (IOException e) {
                Logger.d("HttpDns", Logger.throwableToString(e));
                this.a.getListener().b(this.a, str2);
            }
        }
    }
}
