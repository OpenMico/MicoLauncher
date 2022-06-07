package io.netty.channel;

import io.netty.buffer.ByteBufUtil;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.MacAddressUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThreadLocalRandom;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class DefaultChannelId implements ChannelId {
    private static final byte[] d;
    private static final int e;
    private static final long serialVersionUID = 3884076183504074063L;
    private final byte[] data = new byte[28];
    private transient String g;
    private transient String h;
    private int hashCode;
    static final /* synthetic */ boolean a = !DefaultChannelId.class.desiredAssertionStatus();
    private static final InternalLogger b = InternalLoggerFactory.getInstance(DefaultChannelId.class);
    private static final Pattern c = Pattern.compile("^(?:[0-9a-fA-F][:-]?){6,8}$");
    private static final AtomicInteger f = new AtomicInteger();

    public int compareTo(ChannelId channelId) {
        return 0;
    }

    static {
        int i;
        String str = SystemPropertyUtil.get("io.netty.processId");
        int i2 = -1;
        if (str != null) {
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                i = -1;
            }
            if (i < 0 || i > 4194304) {
                b.warn("-Dio.netty.processId: {} (malformed)", str);
            } else {
                if (b.isDebugEnabled()) {
                    b.debug("-Dio.netty.processId: {} (user-set)", Integer.valueOf(i));
                }
                i2 = i;
            }
        }
        if (i2 < 0) {
            i2 = b();
            if (b.isDebugEnabled()) {
                b.debug("-Dio.netty.processId: {} (auto-detected)", Integer.valueOf(i2));
            }
        }
        e = i2;
        byte[] bArr = null;
        String str2 = SystemPropertyUtil.get("io.netty.machineId");
        if (str2 != null) {
            if (c.matcher(str2).matches()) {
                bArr = a(str2);
                b.debug("-Dio.netty.machineId: {} (user-set)", str2);
            } else {
                b.warn("-Dio.netty.machineId: {} (malformed)", str2);
            }
        }
        if (bArr == null) {
            bArr = a();
            if (b.isDebugEnabled()) {
                b.debug("-Dio.netty.machineId: {} (auto-detected)", MacAddressUtil.formatAddress(bArr));
            }
        }
        d = bArr;
    }

    public static DefaultChannelId newInstance() {
        DefaultChannelId defaultChannelId = new DefaultChannelId();
        defaultChannelId.c();
        return defaultChannelId;
    }

    private static byte[] a(String str) {
        String replaceAll = str.replaceAll("[:-]", "");
        byte[] bArr = new byte[8];
        int i = 0;
        while (i < replaceAll.length()) {
            int i2 = i + 2;
            bArr[i] = (byte) Integer.parseInt(replaceAll.substring(i, i2), 16);
            i = i2;
        }
        return bArr;
    }

    private static byte[] a() {
        byte[] bestAvailableMac = MacAddressUtil.bestAvailableMac();
        if (bestAvailableMac != null) {
            return bestAvailableMac;
        }
        byte[] bArr = new byte[8];
        ThreadLocalRandom.current().nextBytes(bArr);
        b.warn("Failed to find a usable hardware address from the network interfaces; using random bytes: {}", MacAddressUtil.formatAddress(bArr));
        return bArr;
    }

    private static int b() {
        String str;
        int i;
        ClassLoader systemClassLoader = PlatformDependent.getSystemClassLoader();
        try {
            Class<?> cls = Class.forName("java.lang.management.ManagementFactory", true, systemClassLoader);
            Class<?> cls2 = Class.forName("java.lang.management.RuntimeMXBean", true, systemClassLoader);
            str = (String) cls2.getDeclaredMethod("getName", EmptyArrays.EMPTY_CLASSES).invoke(cls.getMethod("getRuntimeMXBean", EmptyArrays.EMPTY_CLASSES).invoke(null, EmptyArrays.EMPTY_OBJECTS), EmptyArrays.EMPTY_OBJECTS);
        } catch (Exception e2) {
            b.debug("Could not invoke ManagementFactory.getRuntimeMXBean().getName(); Android?", (Throwable) e2);
            try {
                str = Class.forName("android.os.Process", true, systemClassLoader).getMethod("myPid", EmptyArrays.EMPTY_CLASSES).invoke(null, EmptyArrays.EMPTY_OBJECTS).toString();
            } catch (Exception e3) {
                b.debug("Could not invoke Process.myPid(); not Android?", (Throwable) e3);
                str = "";
            }
        }
        int indexOf = str.indexOf(64);
        if (indexOf >= 0) {
            str = str.substring(0, indexOf);
        }
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            i = -1;
        }
        if (i >= 0 && i <= 4194304) {
            return i;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(4194305);
        b.warn("Failed to find the current process ID from '{}'; using a random value: {}", str, Integer.valueOf(nextInt));
        return nextInt;
    }

    private DefaultChannelId() {
    }

    private void c() {
        System.arraycopy(d, 0, this.data, 0, 8);
        int a2 = a(a(a(8, e), f.getAndIncrement()), Long.reverse(System.nanoTime()) ^ System.currentTimeMillis());
        int nextInt = ThreadLocalRandom.current().nextInt();
        this.hashCode = nextInt;
        int a3 = a(a2, nextInt);
        if (!a && a3 != this.data.length) {
            throw new AssertionError();
        }
    }

    private int a(int i, int i2) {
        byte[] bArr = this.data;
        int i3 = i + 1;
        bArr[i] = (byte) (i2 >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) i2;
        return i6;
    }

    private int a(int i, long j) {
        byte[] bArr = this.data;
        int i2 = i + 1;
        bArr[i] = (byte) (j >>> 56);
        int i3 = i2 + 1;
        bArr[i2] = (byte) (j >>> 48);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (j >>> 40);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (j >>> 32);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (j >>> 24);
        int i7 = i6 + 1;
        bArr[i6] = (byte) (j >>> 16);
        int i8 = i7 + 1;
        bArr[i7] = (byte) (j >>> 8);
        int i9 = i8 + 1;
        bArr[i8] = (byte) j;
        return i9;
    }

    @Override // io.netty.channel.ChannelId
    public String asShortText() {
        String str = this.g;
        if (str != null) {
            return str;
        }
        String hexDump = ByteBufUtil.hexDump(this.data, 24, 4);
        this.g = hexDump;
        return hexDump;
    }

    @Override // io.netty.channel.ChannelId
    public String asLongText() {
        String str = this.h;
        if (str != null) {
            return str;
        }
        String d2 = d();
        this.h = d2;
        return d2;
    }

    private String d() {
        StringBuilder sb = new StringBuilder((this.data.length * 2) + 5);
        int a2 = a(sb, a(sb, a(sb, a(sb, a(sb, 0, 8), 4), 4), 8), 4);
        if (a || a2 == this.data.length) {
            return sb.substring(0, sb.length() - 1);
        }
        throw new AssertionError();
    }

    private int a(StringBuilder sb, int i, int i2) {
        sb.append(ByteBufUtil.hexDump(this.data, i, i2));
        sb.append('-');
        return i + i2;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultChannelId)) {
            return false;
        }
        return Arrays.equals(this.data, ((DefaultChannelId) obj).data);
    }

    public String toString() {
        return asShortText();
    }
}
