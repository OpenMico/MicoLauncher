package com.xiaomi.miio;

import android.util.Log;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class MiioLocalAPI {
    public static final String BODY_MIIO_INFO = "{\"id\":1,\"method\":\"miIO.info\",\"params\":\"\"}";
    public static final String LOG_TAG = "miio-localapi";
    private static final byte[] a = "ffffffffffffffffffffffffffffffff".getBytes();
    private static final byte[] b = "00000000000000000000000000000000".getBytes();
    private static boolean c = false;
    private static String d = null;
    private static final int e = Runtime.getRuntime().availableProcessors();
    private static final int f = e + 1;
    private static final ExecutorService g = Executors.newFixedThreadPool(f);

    private static boolean a(long j) {
        return ((int) ((j >> 32) & (-1))) != 0;
    }

    private static void a(DatagramSocket datagramSocket, InetAddress inetAddress, byte[] bArr) throws IOException {
        datagramSocket.send(new DatagramPacket(bArr, bArr.length, inetAddress, 54321));
    }

    private static void a(MulticastSocket multicastSocket, String str) throws IOException {
        InetAddress byName = InetAddress.getByName(str);
        multicastSocket.joinGroup(byName);
        multicastSocket.send(new DatagramPacket("miio".getBytes(), 4, byName, 5007));
        multicastSocket.leaveGroup(byName);
    }

    private static void a(String str, MulticastSocket multicastSocket, byte[] bArr, boolean z) throws IOException, InterruptedException {
        a(multicastSocket, String.format(str, 0, Integer.valueOf(bArr.length + 1)));
        int i = 0;
        while (i < bArr.length) {
            int i2 = i + 1;
            a(multicastSocket, String.format(str, Integer.valueOf(i2), Integer.valueOf(bArr[i] & 255)));
            if (i != 0 && i % 10 == 0) {
                Thread.sleep(4L);
                a(multicastSocket, String.format(str, 0, Integer.valueOf(bArr.length + 1)));
            }
            if (z) {
                Thread.sleep(4L);
            }
            i = i2;
        }
    }

    private static String a(DatagramSocket datagramSocket, byte[] bArr, int i) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
        datagramSocket.setSoTimeout(i);
        datagramSocket.receive(datagramPacket);
        return datagramPacket.getAddress().getHostAddress();
    }

    public static void async_trigger(final MiioLocalResponse miioLocalResponse) {
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.1
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(MiioLocalResponse.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(MiioLocalResponse miioLocalResponse) {
        Throwable th;
        MulticastSocket multicastSocket;
        MulticastSocket multicastSocket2;
        Exception e2;
        int i;
        try {
            multicastSocket = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            multicastSocket2 = new MulticastSocket();
            i = 0;
        } catch (Exception e3) {
            e2 = e3;
            multicastSocket2 = null;
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
                multicastSocket.close();
            }
            throw th;
        }
        while (true) {
            i++;
            if (i >= 30) {
                break;
            }
            try {
                a(multicastSocket2, String.format("224.126.%s.%s", 0, 1));
                Thread.sleep(10L);
            } catch (Exception e4) {
                e2 = e4;
            }
            e2 = e4;
            StringWriter stringWriter = new StringWriter();
            e2.printStackTrace(new PrintWriter(stringWriter));
            Log.e(LOG_TAG, e2.toString() + stringWriter.toString());
            if (miioLocalResponse != null) {
                miioLocalResponse.onResponse(new MiioLocalResult(MiioLocalErrorCode.EXCEPTION));
            }
            if (multicastSocket2 == null) {
                return;
            }
            multicastSocket2.close();
        }
        if (miioLocalResponse != null) {
            miioLocalResponse.onResponse(new MiioLocalResult(MiioLocalErrorCode.SUCCESS));
        }
        multicastSocket2.close();
    }

    public static void async_smart_config(final String str, final String str2, final String str3, final String str4, final MiioLocalResponse miioLocalResponse) {
        c = false;
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.6
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, str3, str4, miioLocalResponse);
            }
        });
    }

    public static void async_smart_config_mac(final String str, final String str2, final String str3, final String str4, final String str5, final MiioLocalResponse miioLocalResponse) {
        c = false;
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.7
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, str3, str4, str5, miioLocalResponse);
            }
        });
    }

    public static void async_smart_config_setupcode(final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final MiioLocalResponse miioLocalResponse) {
        c = false;
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.8
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, str3, str4, str5, str6, miioLocalResponse);
            }
        });
    }

    public static void async_smart_config_with_uid(final String str, final String str2, final String str3, final String str4, final long j, final MiioLocalResponse miioLocalResponse) {
        c = false;
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.9
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, str3, str4, j, miioLocalResponse);
            }
        });
    }

    public static void async_smart_config_mac_with_uid(final String str, final String str2, final String str3, final String str4, final String str5, final long j, final MiioLocalResponse miioLocalResponse) {
        c = false;
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.10
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, str3, str4, str5, j, miioLocalResponse);
            }
        });
    }

    public static void async_smart_config_setupcode_with_uid(final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final long j, final MiioLocalResponse miioLocalResponse) {
        c = false;
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.11
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, str3, str4, str5, str6, j, miioLocalResponse);
            }
        });
    }

    public static void stop_smart_config() {
        c = true;
    }

    public static void set_device_local(String str) {
        if (str != null && str.length() == 2) {
            d = str.toLowerCase();
        }
    }

    private static byte[] a(String str) {
        byte[] bArr = new byte[6];
        int i = 0;
        for (String str2 : str.toLowerCase().split(Constants.COLON_SEPARATOR)) {
            i++;
            bArr[i] = (byte) Integer.parseInt(str2, 16);
        }
        return bArr;
    }

    /* loaded from: classes3.dex */
    public enum WifiEnc {
        OPEN(1, ""),
        WEP_PSK(2, "[WEP]"),
        WEP_SHARED(3, "[WEP]"),
        WPA_TKIP_PSK(4, "[WPA-PSK-TKIP]"),
        WPA_AES_PSK(5, "[WPA-PSK-CCMP]"),
        WPA2_AES_PSK(6, "[WPA2-PSK-CCMP]"),
        WPA2_TKIP_PSK(7, "[WPA2-PSK-TKIP]"),
        WPA2_MIXED_PSK(8, "[WPA2-PSK-CCMP+TKIP]"),
        WPA2_MIXED_PSK1(8, "[WPA2-PSK-TKIP+CCMP]"),
        WPA_MIXED_PSK(9, "[WPA-PSK-CCMP+TKIP]"),
        WPA_MIXED_PSK1(9, "[WPA-PSK-TKIP+CCMP]");
        
        private String capability;
        private byte type;

        public byte getType() {
            return this.type;
        }

        public void setType(byte b) {
            this.type = b;
        }

        public String getCapability() {
            return this.capability;
        }

        public void setCapability(String str) {
            this.capability = str;
        }

        WifiEnc(int i, String str) {
            this.type = (byte) i;
            this.capability = str;
        }
    }

    private static byte b(String str) {
        if (str.contains(WifiEnc.WPA2_MIXED_PSK.getCapability())) {
            return WifiEnc.WPA2_MIXED_PSK.getType();
        }
        if (str.contains(WifiEnc.WPA2_MIXED_PSK1.getCapability())) {
            return WifiEnc.WPA2_MIXED_PSK1.getType();
        }
        if (str.contains(WifiEnc.WPA2_AES_PSK.getCapability())) {
            return WifiEnc.WPA2_AES_PSK.getType();
        }
        if (str.contains(WifiEnc.WPA2_TKIP_PSK.getCapability())) {
            return WifiEnc.WPA2_TKIP_PSK.getType();
        }
        if (str.contains(WifiEnc.WPA_MIXED_PSK.getCapability())) {
            return WifiEnc.WPA_MIXED_PSK.getType();
        }
        if (str.contains(WifiEnc.WPA_MIXED_PSK1.getCapability())) {
            return WifiEnc.WPA_MIXED_PSK1.getType();
        }
        if (str.contains(WifiEnc.WPA_AES_PSK.getCapability())) {
            return WifiEnc.WPA_AES_PSK.getType();
        }
        if (str.contains(WifiEnc.WPA_TKIP_PSK.getCapability())) {
            return WifiEnc.WPA_TKIP_PSK.getType();
        }
        if (str.contains(WifiEnc.WEP_PSK.getCapability())) {
            return WifiEnc.WEP_PSK.getType();
        }
        if (str.contains(WifiEnc.WEP_SHARED.getCapability())) {
            return WifiEnc.WEP_SHARED.getType();
        }
        if (str.length() <= 0 || str.replaceAll("\\[WPS\\]", "").replaceAll("\\[ESS\\]", "").length() != 0) {
            return (byte) 0;
        }
        return WifiEnc.OPEN.getType();
    }

    private static String c(String str) {
        return "224." + ((Integer.parseInt("0000" + str, 16) % 124) + 127) + ".%s.%s";
    }

    private static void a(String str, String str2, String str3, String str4, String str5, String str6, long j, long j2, MiioLocalResponse miioLocalResponse) {
        Throwable th;
        Exception e2;
        int i;
        String str7;
        byte[] bArr;
        Log.e(LOG_TAG, "start kuailian");
        MulticastSocket multicastSocket = null;
        try {
            try {
                multicastSocket = new MulticastSocket();
            } catch (Exception e3) {
                e2 = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            byte[] bytes = str.getBytes("UTF-8");
            byte[] bytes2 = str2.getBytes("UTF-8");
            int length = bytes.length + bytes2.length + 1;
            int i2 = 0;
            boolean z = (str3 == null || str4 == null || b(str4) == 0) ? false : true;
            int i3 = length + 9;
            int i4 = (j2 > 0L ? 1 : (j2 == 0L ? 0 : -1));
            if (i4 != 0) {
                i3 += 6;
                if (a(j2)) {
                    i3 += 6;
                }
            }
            if (d != null && d.length() == 2) {
                i3 += 4;
            }
            byte[] bArr2 = new byte[i3];
            int i5 = 0;
            while (i5 < bytes.length) {
                bArr2[i5] = bytes[i5];
                i5++;
            }
            int i6 = i5 + 1;
            bArr2[i5] = 0;
            int i7 = 0;
            while (i7 < bytes2.length) {
                bArr2[i6] = bytes2[i7];
                i7++;
                i6++;
            }
            if (z) {
                int i8 = i6 + 1;
                bArr2[i6] = 0;
                byte[] a2 = a(str3);
                int i9 = 0;
                while (i9 < a2.length) {
                    bArr2[i8] = a2[i9];
                    i9++;
                    i8++;
                }
                int i10 = i8 + 1;
                bArr2[i8] = 0;
                i = i10 + 1;
                bArr2[i10] = b(str4);
            } else {
                int i11 = i6 + 1;
                bArr2[i6] = 0;
                byte[] bArr3 = {0, 0, 0, 0, 0, 0};
                int i12 = 0;
                while (i12 < bArr3.length) {
                    bArr2[i11] = bArr3[i12];
                    i12++;
                    i11++;
                }
                int i13 = i11 + 1;
                bArr2[i11] = 0;
                i = i13 + 1;
                bArr2[i13] = -1;
            }
            if (i4 != 0) {
                int i14 = i + 1;
                bArr2[i] = 16;
                int i15 = i14 + 1;
                bArr2[i14] = 6;
                int i16 = i15 + 1;
                bArr2[i15] = (byte) ((j2 >> 24) & 255);
                int i17 = i16 + 1;
                bArr2[i16] = (byte) ((j2 >> 16) & 255);
                int i18 = i17 + 1;
                bArr2[i17] = (byte) ((j2 >> 8) & 255);
                i = i18 + 1;
                bArr2[i18] = (byte) (j2 & 255);
                if (a(j2)) {
                    int i19 = i + 1;
                    bArr2[i] = 18;
                    int i20 = i19 + 1;
                    bArr2[i19] = 6;
                    int i21 = i20 + 1;
                    bArr2[i20] = (byte) ((j2 >> 56) & 255);
                    int i22 = i21 + 1;
                    bArr2[i21] = (byte) ((j2 >> 48) & 255);
                    int i23 = i22 + 1;
                    bArr2[i22] = (byte) ((j2 >> 40) & 255);
                    i = i23 + 1;
                    bArr2[i23] = (byte) ((j2 >> 32) & 255);
                }
            }
            if (d != null && d.length() == 2) {
                int i24 = i + 1;
                bArr2[i] = 17;
                int i25 = i24 + 1;
                bArr2[i24] = 4;
                bArr2[i25] = (byte) d.charAt(0);
                bArr2[i25 + 1] = (byte) d.charAt(1);
            }
            if (str5 != null && str6 == null && str5.length() == 4) {
                bArr = JNIBridge.smencryptpk(bArr2, j, str5.toLowerCase().getBytes("UTF-8"));
                str7 = c(str5);
            } else if (str5 == null || str6 == null || str5.length() != 4 || str6.length() != 4) {
                bArr = JNIBridge.smencrypt(bArr2, j);
                str7 = "224.126.%s.%s";
            } else {
                bArr = JNIBridge.smencryptpk(bArr2, j, str6.getBytes("UTF-8"));
                str7 = c(str5);
            }
            if (bArr.length > 254) {
                miioLocalResponse.onResponse(new MiioLocalResult(MiioLocalErrorCode.MSG_TOO_LONG));
            } else {
                while (true) {
                    i2++;
                    if (i2 >= 30 || c) {
                        break;
                    }
                    a(str7, multicastSocket, bArr, true);
                    Thread.sleep(50L);
                }
                if (miioLocalResponse != null) {
                    miioLocalResponse.onResponse(new MiioLocalResult(MiioLocalErrorCode.SUCCESS));
                }
            }
            Log.e(LOG_TAG, "end kuailian");
            multicastSocket.close();
        } catch (Exception e4) {
            e2 = e4;
            multicastSocket = multicastSocket;
            StringWriter stringWriter = new StringWriter();
            e2.printStackTrace(new PrintWriter(stringWriter));
            Log.e(LOG_TAG, e2.toString() + stringWriter.toString());
            if (miioLocalResponse != null) {
                miioLocalResponse.onResponse(new MiioLocalResult(MiioLocalErrorCode.EXCEPTION));
            }
            Log.e(LOG_TAG, "end kuailian");
            if (multicastSocket != null) {
                multicastSocket.close();
            }
        } catch (Throwable th3) {
            th = th3;
            Log.e(LOG_TAG, "end kuailian");
            if (multicastSocket != null) {
                multicastSocket.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, String str3, String str4, String str5, MiioLocalResponse miioLocalResponse) {
        a(str, str2, str3, str4, str5, null, 0L, 0L, miioLocalResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, String str3, String str4, String str5, String str6, MiioLocalResponse miioLocalResponse) {
        a(str, str2, str3, str4, str5, str6, 1L, 0L, miioLocalResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, String str3, String str4, MiioLocalResponse miioLocalResponse) {
        a(str, str2, str3, str4, null, null, System.currentTimeMillis(), 0L, miioLocalResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, String str3, String str4, String str5, long j, MiioLocalResponse miioLocalResponse) {
        a(str, str2, str3, str4, str5, null, 0L, j, miioLocalResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, String str3, String str4, String str5, String str6, long j, MiioLocalResponse miioLocalResponse) {
        a(str, str2, str3, str4, str5, str6, 1L, j, miioLocalResponse);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str, String str2, String str3, String str4, long j, MiioLocalResponse miioLocalResponse) {
        a(str, str2, str3, str4, null, null, System.currentTimeMillis(), j, miioLocalResponse);
    }

    public static void async_device_list(final InetAddress inetAddress, final MiioLocalDeviceListResponse miioLocalDeviceListResponse) {
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.12
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(inetAddress, miioLocalDeviceListResponse);
            }
        });
    }

    public static void async_device_detect(final InetAddress inetAddress, final MiioLocalDeviceResponse miioLocalDeviceResponse) {
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.13
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(inetAddress, miioLocalDeviceResponse);
            }
        });
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x005f: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:17:0x005f
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public static void b(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x005f: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:17:0x005f
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r10v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0083: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:17:0x0083
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public static void b(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0083: MOVE  (r1 I:??[OBJECT, ARRAY]) = (r0 I:??[OBJECT, ARRAY]), block:B:17:0x0083
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r11v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void b(String str, String str2, long j, String str3, int i, MiioLocalRpcResponse miioLocalRpcResponse) {
        synchronized (MiioLocalAPI.class) {
            a(str, str2, j, str3, i, 2000, miioLocalRpcResponse);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 3, insn: 0x00e1: MOVE  (r4 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:42:0x00e1
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    private static synchronized void a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 3, insn: 0x00e1: MOVE  (r4 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:42:0x00e1
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r22v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    public static void async_rpc(final String str, final String str2, final long j, final String str3, final int i, final MiioLocalRpcResponse miioLocalRpcResponse) {
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.2
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, j, str3, i, miioLocalRpcResponse);
            }
        });
    }

    public static void async_rpc(String str, String str2, long j, String str3, MiioLocalRpcResponse miioLocalRpcResponse) {
        async_rpc(str, str2, j, str3, miioLocalRpcResponse, 2000);
    }

    public static void async_rpc(final String str, final String str2, final long j, final String str3, final MiioLocalRpcResponse miioLocalRpcResponse, final int i) {
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.3
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, str2, j, str3, miioLocalRpcResponse, i);
            }
        });
    }

    public static void async_rpc(final String str, final String str2, final MiioLocalRpcResponse miioLocalRpcResponse) {
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.4
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.rpc(str, str2, miioLocalRpcResponse);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00dc, code lost:
        if (r1 == null) goto L_0x011f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00de, code lost:
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x011c, code lost:
        if (r1 == null) goto L_0x011f;
     */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ce A[Catch: all -> 0x009f, TRY_LEAVE, TryCatch #10 {all -> 0x009f, blocks: (B:5:0x0006, B:7:0x000c, B:42:0x00a3, B:44:0x00ce, B:48:0x00e3, B:50:0x010e), top: B:61:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x010e A[Catch: all -> 0x009f, TRY_LEAVE, TryCatch #10 {all -> 0x009f, blocks: (B:5:0x0006, B:7:0x000c, B:42:0x00a3, B:44:0x00ce, B:48:0x00e3, B:50:0x010e), top: B:61:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0123 A[Catch: all -> 0x0127, TRY_ENTER, TryCatch #5 {, blocks: (B:33:0x0090, B:46:0x00de, B:55:0x0123, B:56:0x0126), top: B:61:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void b(java.lang.String r12, java.lang.String r13, long r14, java.lang.String r16, com.xiaomi.miio.MiioLocalRpcResponse r17, int r18) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miio.MiioLocalAPI.b(java.lang.String, java.lang.String, long, java.lang.String, com.xiaomi.miio.MiioLocalRpcResponse, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00bb, code lost:
        if (r1 != null) goto L_0x00bd;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00bd, code lost:
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00fb, code lost:
        if (r1 != null) goto L_0x00bd;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void rpc(java.lang.String r10, java.lang.String r11, com.xiaomi.miio.MiioLocalRpcResponse r12) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miio.MiioLocalAPI.rpc(java.lang.String, java.lang.String, com.xiaomi.miio.MiioLocalRpcResponse):void");
    }

    public static Cancelable async_get_token(String str, MiioLocalRpcResponse miioLocalRpcResponse, int i) {
        return async_get_token(str, miioLocalRpcResponse, 2000, i);
    }

    public static Cancelable async_get_token(final String str, final MiioLocalRpcResponse miioLocalRpcResponse, final int i, final int i2) {
        final Cancelable cancelable = new Cancelable();
        g.execute(new Runnable() { // from class: com.xiaomi.miio.MiioLocalAPI.5
            @Override // java.lang.Runnable
            public void run() {
                MiioLocalAPI.b(str, miioLocalRpcResponse, i, i2, cancelable);
            }
        });
        return cancelable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0029, code lost:
        if (r5 != null) goto L_0x002b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x012e, code lost:
        if (r5 != null) goto L_0x002b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0132, code lost:
        if (r5 != null) goto L_0x0134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0134, code lost:
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0176, code lost:
        if (r5 != null) goto L_0x002b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x017a, code lost:
        if (r5 != null) goto L_0x0134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x017f, code lost:
        java.lang.Thread.sleep(com.google.android.exoplayer2.SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0184, code lost:
        android.util.Log.i("sleep", "get_token InterruptedException");
     */
    /* JADX WARN: Removed duplicated region for block: B:56:0x018f A[Catch: all -> 0x0193, TryCatch #3 {, blocks: (B:15:0x002b, B:23:0x00d6, B:43:0x0134, B:53:0x017f, B:54:0x0184, B:56:0x018f, B:57:0x0192), top: B:64:0x0134, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void b(java.lang.String r16, com.xiaomi.miio.MiioLocalRpcResponse r17, int r18, int r19, com.xiaomi.miio.MiioLocalAPI.Cancelable r20) {
        /*
            Method dump skipped, instructions count: 408
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miio.MiioLocalAPI.b(java.lang.String, com.xiaomi.miio.MiioLocalRpcResponse, int, int, com.xiaomi.miio.MiioLocalAPI$Cancelable):void");
    }

    /* loaded from: classes3.dex */
    public static class Cancelable {
        private boolean a;

        public void cancel() {
            this.a = true;
        }
    }
}
