package com.xiaomi.push;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Process;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.ai;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.service.module.PushChannelRegion;
import com.xiaomi.smarthome.library.common.network.Network;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class cv {
    protected static Context a;
    private static cv i;
    private static a j;
    private static String k;
    private static String l;

    /* renamed from: a */
    protected b f21a;

    /* renamed from: a */
    protected Map<String, cm> f22a;
    private cu c;
    private String d;
    private long e;
    private final long f;
    private long g;
    private String h;
    protected static Map<String, cr> b = new HashMap();

    /* renamed from: a */
    protected static boolean f20a = false;

    /* loaded from: classes4.dex */
    public interface a {
        cv a(Context context, cu cuVar, b bVar, String str);
    }

    /* loaded from: classes4.dex */
    public interface b {
        String a(String str);
    }

    public cv(Context context, cu cuVar, b bVar, String str) {
        this(context, cuVar, bVar, str, null, null);
    }

    protected cv(Context context, cu cuVar, b bVar, String str, String str2, String str3) {
        this.f22a = new HashMap();
        this.d = "0";
        this.e = 0L;
        this.f = 15L;
        this.g = 0L;
        this.h = "isp_prov_city_country_ip";
        this.f21a = bVar;
        this.c = cuVar == null ? new cn(this) : cuVar;
        this.d = str;
        k = str2 == null ? context.getPackageName() : str2;
        l = str3 == null ? g() : str3;
    }

    public static synchronized cv a() {
        cv cvVar;
        synchronized (cv.class) {
            if (i != null) {
                cvVar = i;
            } else {
                throw new IllegalStateException("the host manager is not initialized yet.");
            }
        }
        return cvVar;
    }

    private ArrayList<cr> a(ArrayList<String> arrayList) {
        m822d();
        synchronized (this.f22a) {
            m818a();
            for (String str : this.f22a.keySet()) {
                if (!arrayList.contains(str)) {
                    arrayList.add(str);
                }
            }
        }
        boolean isEmpty = b.isEmpty();
        synchronized (b) {
            for (Object obj : b.values().toArray()) {
                cr crVar = (cr) obj;
                if (!crVar.b()) {
                    b.remove(crVar.b);
                    isEmpty = true;
                }
            }
        }
        if (!arrayList.contains(b())) {
            arrayList.add(b());
        }
        ArrayList<cr> arrayList2 = new ArrayList<>(arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList2.add(null);
        }
        try {
            String str2 = at.d(a) ? Network.NETWORK_TYPE_WIFI : "wap";
            String a2 = a(arrayList, str2, this.d, isEmpty);
            if (!TextUtils.isEmpty(a2)) {
                JSONObject jSONObject = new JSONObject(a2);
                com.xiaomi.channel.commonutils.logger.b.b(a2);
                if ("OK".equalsIgnoreCase(jSONObject.getString(ExifInterface.LATITUDE_SOUTH))) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("R");
                    String string = jSONObject2.getString("province");
                    String string2 = jSONObject2.getString("city");
                    String string3 = jSONObject2.getString("isp");
                    String string4 = jSONObject2.getString("ip");
                    String string5 = jSONObject2.getString(ai.O);
                    JSONObject jSONObject3 = jSONObject2.getJSONObject(str2);
                    com.xiaomi.channel.commonutils.logger.b.c("get bucket: ip = " + string4 + " net = " + string3 + " hosts = " + jSONObject3.toString());
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        String str3 = arrayList.get(i3);
                        JSONArray optJSONArray = jSONObject3.optJSONArray(str3);
                        if (optJSONArray == null) {
                            com.xiaomi.channel.commonutils.logger.b.m149a("no bucket found for " + str3);
                            jSONObject3 = jSONObject3;
                        } else {
                            cr crVar2 = new cr(str3);
                            for (int i4 = 0; i4 < optJSONArray.length(); i4++) {
                                String string6 = optJSONArray.getString(i4);
                                if (!TextUtils.isEmpty(string6)) {
                                    jSONObject3 = jSONObject3;
                                    crVar2.a(new cp(string6, optJSONArray.length() - i4));
                                } else {
                                    jSONObject3 = jSONObject3;
                                }
                            }
                            jSONObject3 = jSONObject3;
                            arrayList2.set(i3, crVar2);
                            crVar2.g = string5;
                            crVar2.c = string;
                            crVar2.e = string3;
                            crVar2.f = string4;
                            crVar2.d = string2;
                            if (jSONObject2.has("stat-percent")) {
                                crVar2.a(jSONObject2.getDouble("stat-percent"));
                            }
                            if (jSONObject2.has("stat-domain")) {
                                crVar2.b(jSONObject2.getString("stat-domain"));
                            }
                            if (jSONObject2.has(RtspHeaders.Values.TTL)) {
                                crVar2.a(jSONObject2.getInt(RtspHeaders.Values.TTL) * 1000);
                            }
                            m817a(crVar2.a());
                        }
                    }
                    JSONObject optJSONObject = jSONObject2.optJSONObject("reserved");
                    if (optJSONObject != null) {
                        long j2 = 604800000;
                        if (jSONObject2.has("reserved-ttl")) {
                            j2 = jSONObject2.getInt("reserved-ttl") * 1000;
                        }
                        Iterator keys = optJSONObject.keys();
                        while (keys.hasNext()) {
                            String str4 = (String) keys.next();
                            JSONArray optJSONArray2 = optJSONObject.optJSONArray(str4);
                            if (optJSONArray2 == null) {
                                com.xiaomi.channel.commonutils.logger.b.m149a("no bucket found for " + str4);
                            } else {
                                cr crVar3 = new cr(str4);
                                crVar3.a(j2);
                                for (int i5 = 0; i5 < optJSONArray2.length(); i5++) {
                                    String string7 = optJSONArray2.getString(i5);
                                    if (!TextUtils.isEmpty(string7)) {
                                        crVar3.a(new cp(string7, optJSONArray2.length() - i5));
                                    }
                                }
                                synchronized (b) {
                                    if (this.c.a(str4)) {
                                        b.put(str4, crVar3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.m149a("failed to get bucket " + e.getMessage());
        }
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            cr crVar4 = arrayList2.get(i6);
            if (crVar4 != null) {
                a(arrayList.get(i6), crVar4);
            }
        }
        m821c();
        return arrayList2;
    }

    public static synchronized void a(Context context, cu cuVar, b bVar, String str, String str2, String str3) {
        synchronized (cv.class) {
            a = context.getApplicationContext();
            if (a == null) {
                a = context;
            }
            if (i == null) {
                if (j == null) {
                    i = new cv(context, cuVar, bVar, str, str2, str3);
                } else {
                    i = j.a(context, cuVar, bVar, str);
                }
            }
        }
    }

    public static synchronized void a(a aVar) {
        synchronized (cv.class) {
            j = aVar;
            i = null;
        }
    }

    public static void a(String str, String str2) {
        cr crVar = b.get(str);
        synchronized (b) {
            if (crVar == null) {
                cr crVar2 = new cr(str);
                crVar2.a(604800000L);
                crVar2.m811a(str2);
                b.put(str, crVar2);
            } else {
                crVar.m811a(str2);
            }
        }
    }

    static String e(String str) {
        try {
            int length = str.length();
            byte[] bytes = str.getBytes("UTF-8");
            for (int i2 = 0; i2 < bytes.length; i2++) {
                byte b2 = bytes[i2];
                int i3 = b2 & 240;
                if (i3 != 240) {
                    bytes[i2] = (byte) (((b2 & 15) ^ ((byte) (((b2 >> 4) + length) & 15))) | i3);
                }
            }
            return new String(bytes);
        } catch (UnsupportedEncodingException unused) {
            return str;
        }
    }

    public static String f() {
        NetworkInfo activeNetworkInfo;
        Context context = a;
        if (context == null) {
            return "unknown";
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return "unknown";
            }
            if (activeNetworkInfo.getType() == 1) {
                WifiManager wifiManager = (WifiManager) a.getSystemService(Network.NETWORK_TYPE_WIFI);
                if (wifiManager == null || wifiManager.getConnectionInfo() == null) {
                    return "unknown";
                }
                return "WIFI-" + wifiManager.getConnectionInfo().getSSID();
            }
            return activeNetworkInfo.getTypeName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + activeNetworkInfo.getSubtypeName();
        } catch (Throwable unused) {
            return "unknown";
        }
    }

    private String g() {
        try {
            PackageInfo packageInfo = a.getPackageManager().getPackageInfo(a.getPackageName(), 16384);
            return packageInfo != null ? packageInfo.versionName : "0";
        } catch (Exception unused) {
            return "0";
        }
    }

    public cr a(String str) {
        if (!TextUtils.isEmpty(str)) {
            return a(new URL(str).getHost(), true);
        }
        throw new IllegalArgumentException("the url is empty");
    }

    public cr a(String str, boolean z) {
        cr d;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        } else if (!this.c.a(str)) {
            return null;
        } else {
            cr c = c(str);
            return (c == null || !c.b()) ? (!z || !at.b(a) || (d = d(str)) == null) ? new co(this, str, c) : d : c;
        }
    }

    public String a(ArrayList<String> arrayList, String str, String str2, boolean z) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        ArrayList<as> arrayList3 = new ArrayList();
        arrayList3.add(new aq("type", str));
        if (str.equals("wap")) {
            arrayList3.add(new aq("conpt", e(at.m756a(a))));
        }
        if (z) {
            arrayList3.add(new aq("reserved", "1"));
        }
        arrayList3.add(new aq("uuid", str2));
        arrayList3.add(new aq("list", az.a(arrayList, Constants.ACCEPT_TIME_SEPARATOR_SP)));
        arrayList3.add(new aq("countrycode", com.xiaomi.push.service.a.a(a).b()));
        cr c = c(b());
        String format = String.format(Locale.US, "http://%1$s/gslb/?ver=4.0", b());
        if (c == null) {
            arrayList2.add(format);
            synchronized (b) {
                cr crVar = b.get("resolver.msg.xiaomi.net");
                if (crVar != null) {
                    Iterator<String> it = crVar.a(true).iterator();
                    while (it.hasNext()) {
                        arrayList2.add(String.format(Locale.US, "http://%1$s/gslb/?ver=4.0", it.next()));
                    }
                }
            }
        } else {
            arrayList2 = c.a(format);
        }
        Iterator<String> it2 = arrayList2.iterator();
        IOException e = null;
        while (it2.hasNext()) {
            Uri.Builder buildUpon = Uri.parse(it2.next()).buildUpon();
            for (as asVar : arrayList3) {
                buildUpon.appendQueryParameter(asVar.a(), asVar.b());
            }
            try {
                return this.f21a == null ? at.a(a, new URL(buildUpon.toString())) : this.f21a.a(buildUpon.toString());
            } catch (IOException e2) {
                e = e2;
            }
        }
        if (e == null) {
            return null;
        }
        com.xiaomi.channel.commonutils.logger.b.m149a("network exception: " + e.getMessage());
        throw e;
    }

    /* renamed from: a */
    protected JSONObject m815a() {
        JSONObject jSONObject;
        synchronized (this.f22a) {
            jSONObject = new JSONObject();
            jSONObject.put("ver", 2);
            JSONArray jSONArray = new JSONArray();
            for (cm cmVar : this.f22a.values()) {
                jSONArray.put(cmVar.d());
            }
            jSONObject.put("data", jSONArray);
            JSONArray jSONArray2 = new JSONArray();
            for (cr crVar : b.values()) {
                jSONArray2.put(crVar.m810a());
            }
            jSONObject.put("reserved", jSONArray2);
        }
        return jSONObject;
    }

    /* renamed from: a */
    public void m816a() {
        synchronized (this.f22a) {
            this.f22a.clear();
        }
    }

    /* renamed from: a */
    public void m817a(String str) {
        this.h = str;
    }

    public void a(String str, cr crVar) {
        if (TextUtils.isEmpty(str) || crVar == null) {
            throw new IllegalArgumentException("the argument is invalid " + str + ", " + crVar);
        } else if (this.c.a(str)) {
            synchronized (this.f22a) {
                m818a();
                if (this.f22a.containsKey(str)) {
                    this.f22a.get(str).a(crVar);
                } else {
                    cm cmVar = new cm(str);
                    cmVar.a(crVar);
                    this.f22a.put(str, cmVar);
                }
            }
        }
    }

    /* renamed from: a */
    protected boolean m818a() {
        synchronized (this.f22a) {
            if (f20a) {
                return true;
            }
            f20a = true;
            this.f22a.clear();
            String d = d();
            if (TextUtils.isEmpty(d)) {
                return false;
            }
            m820b(d);
            com.xiaomi.channel.commonutils.logger.b.b("loading the new hosts succeed");
            return true;
        }
    }

    public cr b(String str) {
        return a(str, true);
    }

    protected String b() {
        String a2 = com.xiaomi.push.service.a.a(a).a();
        return (TextUtils.isEmpty(a2) || PushChannelRegion.China.name().equals(a2)) ? "resolver.msg.xiaomi.net" : "resolver.msg.global.xiaomi.net";
    }

    /* renamed from: b */
    public void m819b() {
        ArrayList<String> arrayList;
        synchronized (this.f22a) {
            m818a();
            arrayList = new ArrayList<>(this.f22a.keySet());
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                cm cmVar = this.f22a.get(arrayList.get(size));
                if (!(cmVar == null || cmVar.a() == null)) {
                    arrayList.remove(size);
                }
            }
        }
        ArrayList<cr> a2 = a(arrayList);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (a2.get(i2) != null) {
                a(arrayList.get(i2), a2.get(i2));
            }
        }
    }

    /* renamed from: b */
    protected void m820b(String str) {
        synchronized (this.f22a) {
            this.f22a.clear();
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt("ver") == 2) {
                JSONArray optJSONArray = jSONObject.optJSONArray("data");
                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                    cm a2 = new cm().a(optJSONArray.getJSONObject(i2));
                    this.f22a.put(a2.c(), a2);
                }
                JSONArray optJSONArray2 = jSONObject.optJSONArray("reserved");
                for (int i3 = 0; i3 < optJSONArray2.length(); i3++) {
                    cr a3 = new cr("").a(optJSONArray2.getJSONObject(i3));
                    b.put(a3.b, a3);
                }
            } else {
                throw new JSONException("Bad version");
            }
        }
    }

    protected cr c(String str) {
        cm cmVar;
        cr a2;
        synchronized (this.f22a) {
            m818a();
            cmVar = this.f22a.get(str);
        }
        if (cmVar == null || (a2 = cmVar.a()) == null) {
            return null;
        }
        return a2;
    }

    public String c() {
        StringBuilder sb = new StringBuilder();
        synchronized (this.f22a) {
            for (Map.Entry<String, cm> entry : this.f22a.entrySet()) {
                sb.append(entry.getKey());
                sb.append(":\n");
                sb.append(entry.getValue().toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /* renamed from: c */
    public void m821c() {
        synchronized (this.f22a) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(a.openFileOutput(e(), 0)));
                String jSONObject = m815a().toString();
                if (!TextUtils.isEmpty(jSONObject)) {
                    bufferedWriter.write(jSONObject);
                }
                bufferedWriter.close();
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.m149a("persist bucket failure: " + e.getMessage());
            }
        }
    }

    protected cr d(String str) {
        if (System.currentTimeMillis() - this.g <= this.e * 60 * 1000) {
            return null;
        }
        this.g = System.currentTimeMillis();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        cr crVar = a(arrayList).get(0);
        if (crVar != null) {
            this.e = 0L;
            return crVar;
        }
        long j2 = this.e;
        if (j2 >= 15) {
            return null;
        }
        this.e = j2 + 1;
        return null;
    }

    protected String d() {
        BufferedReader bufferedReader;
        Throwable th;
        Throwable th2;
        File file;
        try {
            file = new File(a.getFilesDir(), e());
        } catch (Throwable th3) {
            th2 = th3;
            bufferedReader = null;
        }
        if (file.isFile()) {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            try {
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            sb.append(readLine);
                        } else {
                            String sb2 = sb.toString();
                            z.a(bufferedReader);
                            return sb2;
                        }
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                    com.xiaomi.channel.commonutils.logger.b.m149a("load host exception " + th2.getMessage());
                    z.a(bufferedReader);
                    return null;
                }
            } catch (Throwable th5) {
                th = th5;
                z.a(bufferedReader);
                throw th;
            }
        } else {
            z.a((Closeable) null);
            return null;
        }
    }

    /* renamed from: d */
    public void m822d() {
        String next;
        synchronized (this.f22a) {
            for (cm cmVar : this.f22a.values()) {
                cmVar.a(true);
            }
            while (true) {
                for (boolean z = false; !z; z = true) {
                    Iterator<String> it = this.f22a.keySet().iterator();
                    while (it.hasNext()) {
                        next = it.next();
                        if (this.f22a.get(next).b().isEmpty()) {
                            break;
                        }
                    }
                }
                this.f22a.remove(next);
            }
        }
    }

    protected String e() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) a.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return "com.xiaomi";
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == Process.myPid()) {
                return runningAppProcessInfo.processName;
            }
        }
        return "com.xiaomi";
    }
}
