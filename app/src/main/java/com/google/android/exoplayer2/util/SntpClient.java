package com.google.android.exoplayer2.util;

import android.os.SystemClock;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/* loaded from: classes2.dex */
public final class SntpClient {
    @GuardedBy("valueLock")
    private static boolean c;
    @GuardedBy("valueLock")
    private static long d;
    private static final Object a = new Object();
    private static final Object b = new Object();
    public static final String DEFAULT_NTP_HOST = "time.android.com";
    @GuardedBy("valueLock")
    private static String e = DEFAULT_NTP_HOST;

    /* loaded from: classes2.dex */
    public interface InitializationCallback {
        void onInitializationFailed(IOException iOException);

        void onInitialized();
    }

    private SntpClient() {
    }

    public static String getNtpHost() {
        String str;
        synchronized (b) {
            str = e;
        }
        return str;
    }

    public static void setNtpHost(String str) {
        synchronized (b) {
            if (!e.equals(str)) {
                e = str;
                c = false;
            }
        }
    }

    public static boolean isInitialized() {
        boolean z;
        synchronized (b) {
            z = c;
        }
        return z;
    }

    public static long getElapsedRealtimeOffsetMs() {
        long j;
        synchronized (b) {
            j = c ? d : C.TIME_UNSET;
        }
        return j;
    }

    public static void initialize(@Nullable Loader loader, @Nullable InitializationCallback initializationCallback) {
        if (!isInitialized()) {
            if (loader == null) {
                loader = new Loader("SntpClient");
            }
            loader.startLoading(new b(), new a(initializationCallback), 1);
        } else if (initializationCallback != null) {
            initializationCallback.onInitialized();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long e() throws IOException {
        DatagramSocket datagramSocket;
        Throwable th;
        InetAddress byName = InetAddress.getByName(getNtpHost());
        DatagramSocket datagramSocket2 = new DatagramSocket();
        try {
            datagramSocket2.setSoTimeout(10000);
            byte[] bArr = new byte[48];
            DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length, byName, 123);
            bArr[0] = Ascii.ESC;
            long currentTimeMillis = System.currentTimeMillis();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            a(bArr, 40, currentTimeMillis);
            datagramSocket2.send(datagramPacket);
            datagramSocket2.receive(new DatagramPacket(bArr, bArr.length));
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            long j = currentTimeMillis + (elapsedRealtime2 - elapsedRealtime);
            byte b2 = (byte) ((bArr[0] >> 6) & 3);
            byte b3 = (byte) (bArr[0] & 7);
            int i = bArr[1] & 255;
            long a2 = a(bArr, 24);
            long a3 = a(bArr, 32);
            datagramSocket = datagramSocket2;
            try {
                long a4 = a(bArr, 40);
                a(b2, b3, i, a4);
                long j2 = (j + (((a3 - a2) + (a4 - j)) / 2)) - elapsedRealtime2;
                datagramSocket.close();
                return j2;
            } catch (Throwable th2) {
                th = th2;
                try {
                    datagramSocket.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            datagramSocket = datagramSocket2;
        }
    }

    private static long a(byte[] bArr, int i) {
        long b2 = b(bArr, i);
        long b3 = b(bArr, i + 4);
        if (b2 == 0 && b3 == 0) {
            return 0L;
        }
        return ((b2 - 2208988800L) * 1000) + ((b3 * 1000) / 4294967296L);
    }

    private static void a(byte[] bArr, int i, long j) {
        if (j == 0) {
            Arrays.fill(bArr, i, i + 8, (byte) 0);
            return;
        }
        long j2 = j / 1000;
        long j3 = j2 + 2208988800L;
        int i2 = i + 1;
        bArr[i] = (byte) (j3 >> 24);
        int i3 = i2 + 1;
        bArr[i2] = (byte) (j3 >> 16);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (j3 >> 8);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (j3 >> 0);
        long j4 = ((j - (j2 * 1000)) * 4294967296L) / 1000;
        int i6 = i5 + 1;
        bArr[i5] = (byte) (j4 >> 24);
        int i7 = i6 + 1;
        bArr[i6] = (byte) (j4 >> 16);
        bArr[i7] = (byte) (j4 >> 8);
        bArr[i7 + 1] = (byte) (Math.random() * 255.0d);
    }

    private static long b(byte[] bArr, int i) {
        byte b2 = bArr[i];
        byte b3 = bArr[i + 1];
        byte b4 = bArr[i + 2];
        byte b5 = bArr[i + 3];
        int i2 = b2 & 128;
        byte b6 = b2;
        if (i2 == 128) {
            b6 = (b2 & Byte.MAX_VALUE) + 128;
        }
        int i3 = b3 & 128;
        byte b7 = b3;
        if (i3 == 128) {
            b7 = (b3 & Byte.MAX_VALUE) + 128;
        }
        int i4 = b4 & 128;
        byte b8 = b4;
        if (i4 == 128) {
            b8 = (b4 & Byte.MAX_VALUE) + 128;
        }
        int i5 = b5 & 128;
        byte b9 = b5;
        if (i5 == 128) {
            b9 = (b5 & Byte.MAX_VALUE) + 128;
        }
        return ((b6 == 1 ? 1L : 0L) << 24) + ((b7 == 1 ? 1L : 0L) << 16) + ((b8 == 1 ? 1L : 0L) << 8) + (b9 == 1 ? 1L : 0L);
    }

    private static void a(byte b2, byte b3, int i, long j) throws IOException {
        if (b2 == 3) {
            throw new IOException("SNTP: Unsynchronized server");
        } else if (b3 != 4 && b3 != 5) {
            StringBuilder sb = new StringBuilder(26);
            sb.append("SNTP: Untrusted mode: ");
            sb.append((int) b3);
            throw new IOException(sb.toString());
        } else if (i == 0 || i > 15) {
            StringBuilder sb2 = new StringBuilder(36);
            sb2.append("SNTP: Untrusted stratum: ");
            sb2.append(i);
            throw new IOException(sb2.toString());
        } else if (j == 0) {
            throw new IOException("SNTP: Zero transmitTime");
        }
    }

    /* loaded from: classes2.dex */
    private static final class b implements Loader.Loadable {
        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
        }

        private b() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            synchronized (SntpClient.a) {
                synchronized (SntpClient.b) {
                    if (!SntpClient.c) {
                        long e = SntpClient.e();
                        synchronized (SntpClient.b) {
                            long unused = SntpClient.d = e;
                            boolean unused2 = SntpClient.c = true;
                        }
                    }
                }
            }
        }
    }

    /* loaded from: classes2.dex */
    private static final class a implements Loader.Callback<Loader.Loadable> {
        @Nullable
        private final InitializationCallback a;

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(Loader.Loadable loadable, long j, long j2, boolean z) {
        }

        public a(@Nullable InitializationCallback initializationCallback) {
            this.a = initializationCallback;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(Loader.Loadable loadable, long j, long j2) {
            if (this.a == null) {
                return;
            }
            if (!SntpClient.isInitialized()) {
                this.a.onInitializationFailed(new IOException(new ConcurrentModificationException()));
            } else {
                this.a.onInitialized();
            }
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j, long j2, IOException iOException, int i) {
            InitializationCallback initializationCallback = this.a;
            if (initializationCallback != null) {
                initializationCallback.onInitializationFailed(iOException);
            }
            return Loader.DONT_RETRY;
        }
    }
}
