package com.google.android.exoplayer2.source.rtsp;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RtspMessageChannel implements Closeable {
    public static final Charset a = Charsets.UTF_8;
    private final MessageListener b;
    private final Loader c = new Loader("ExoPlayer:RtspMessageChannel:ReceiverLoader");
    private final Map<Integer, InterleavedBinaryDataListener> d = Collections.synchronizedMap(new HashMap());
    private d e;
    private Socket f;
    private volatile boolean g;

    /* loaded from: classes2.dex */
    public interface InterleavedBinaryDataListener {
        void onInterleavedBinaryDataReceived(byte[] bArr);
    }

    /* loaded from: classes2.dex */
    public interface MessageListener {
        default void onReceivingFailed(Exception exc) {
        }

        void onRtspMessageReceived(List<String> list);

        default void onSendingFailed(List<String> list, Exception exc) {
        }
    }

    public RtspMessageChannel(MessageListener messageListener) {
        this.b = messageListener;
    }

    public void a(Socket socket) throws IOException {
        this.f = socket;
        this.e = new d(socket.getOutputStream());
        this.c.startLoading(new c(socket.getInputStream()), new a(), 0);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.g) {
            try {
                if (this.e != null) {
                    this.e.close();
                }
                this.c.release();
                if (this.f != null) {
                    this.f.close();
                }
            } finally {
                this.g = true;
            }
        }
    }

    public void a(List<String> list) {
        Assertions.checkStateNotNull(this.e);
        this.e.a(list);
    }

    public void a(int i, InterleavedBinaryDataListener interleavedBinaryDataListener) {
        this.d.put(Integer.valueOf(i), interleavedBinaryDataListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class d implements Closeable {
        private final OutputStream b;
        private final HandlerThread c = new HandlerThread("ExoPlayer:RtspMessageChannel:Sender");
        private final Handler d = new Handler(this.c.getLooper());

        public d(OutputStream outputStream) {
            RtspMessageChannel.this = r1;
            this.b = outputStream;
            this.c.start();
        }

        public void a(final List<String> list) {
            final byte[] a = RtspMessageUtil.a(list);
            this.d.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$RtspMessageChannel$d$lVljIIE3Kh14qMbhkFokBrSEQ4E
                @Override // java.lang.Runnable
                public final void run() {
                    RtspMessageChannel.d.this.a(a, list);
                }
            });
        }

        public /* synthetic */ void a(byte[] bArr, List list) {
            try {
                this.b.write(bArr);
            } catch (Exception e) {
                if (!RtspMessageChannel.this.g) {
                    RtspMessageChannel.this.b.onSendingFailed(list, e);
                }
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            Handler handler = this.d;
            final HandlerThread handlerThread = this.c;
            Objects.requireNonNull(handlerThread);
            handler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.-$$Lambda$WMxBxc5_9wbl5guHdVHlfVLTCIU
                @Override // java.lang.Runnable
                public final void run() {
                    handlerThread.quit();
                }
            });
            try {
                this.c.join();
            } catch (InterruptedException unused) {
                this.c.interrupt();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class c implements Loader.Loadable {
        private final DataInputStream b;
        private final b c = new b();
        private volatile boolean d;

        public c(InputStream inputStream) {
            RtspMessageChannel.this = r1;
            this.b = new DataInputStream(inputStream);
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
            this.d = true;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            while (!this.d) {
                byte readByte = this.b.readByte();
                if (readByte == 36) {
                    a();
                } else {
                    a(readByte);
                }
            }
        }

        private void a(byte b) throws IOException {
            if (!RtspMessageChannel.this.g) {
                RtspMessageChannel.this.b.onRtspMessageReceived(this.c.a(b, this.b));
            }
        }

        private void a() throws IOException {
            int readUnsignedByte = this.b.readUnsignedByte();
            int readUnsignedShort = this.b.readUnsignedShort();
            byte[] bArr = new byte[readUnsignedShort];
            this.b.readFully(bArr, 0, readUnsignedShort);
            InterleavedBinaryDataListener interleavedBinaryDataListener = (InterleavedBinaryDataListener) RtspMessageChannel.this.d.get(Integer.valueOf(readUnsignedByte));
            if (interleavedBinaryDataListener != null && !RtspMessageChannel.this.g) {
                interleavedBinaryDataListener.onInterleavedBinaryDataReceived(bArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public final class a implements Loader.Callback<c> {
        /* renamed from: a */
        public void onLoadCompleted(c cVar, long j, long j2) {
        }

        /* renamed from: a */
        public void onLoadCanceled(c cVar, long j, long j2, boolean z) {
        }

        private a() {
            RtspMessageChannel.this = r1;
        }

        /* renamed from: a */
        public Loader.LoadErrorAction onLoadError(c cVar, long j, long j2, IOException iOException, int i) {
            if (!RtspMessageChannel.this.g) {
                RtspMessageChannel.this.b.onReceivingFailed(iOException);
            }
            return Loader.DONT_RETRY;
        }
    }

    /* loaded from: classes2.dex */
    public static final class b {
        private final List<String> a = new ArrayList();
        @MessageParser.ReadingState
        private int b = 1;
        private long c;

        public ImmutableList<String> a(byte b, DataInputStream dataInputStream) throws IOException {
            ImmutableList<String> a = a(b(b, dataInputStream));
            while (a == null) {
                if (this.b == 3) {
                    long j = this.c;
                    if (j > 0) {
                        int checkedCast = Ints.checkedCast(j);
                        Assertions.checkState(checkedCast != -1);
                        byte[] bArr = new byte[checkedCast];
                        dataInputStream.readFully(bArr, 0, checkedCast);
                        a = b(bArr);
                    } else {
                        throw new IllegalStateException("Expects a greater than zero Content-Length.");
                    }
                } else {
                    a = a(b(dataInputStream.readByte(), dataInputStream));
                }
            }
            return a;
        }

        private static byte[] b(byte b, DataInputStream dataInputStream) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[2];
            bArr[0] = b;
            bArr[1] = dataInputStream.readByte();
            byteArrayOutputStream.write(bArr);
            while (true) {
                if (bArr[0] == 13 && bArr[1] == 10) {
                    return byteArrayOutputStream.toByteArray();
                }
                bArr[0] = bArr[1];
                bArr[1] = dataInputStream.readByte();
                byteArrayOutputStream.write(bArr[1]);
            }
        }

        @Nullable
        private ImmutableList<String> a(byte[] bArr) throws ParserException {
            boolean z = true;
            if (!(bArr.length >= 2 && bArr[bArr.length - 2] == 13 && bArr[bArr.length - 1] == 10)) {
                z = false;
            }
            Assertions.checkArgument(z);
            String str = new String(bArr, 0, bArr.length - 2, RtspMessageChannel.a);
            this.a.add(str);
            switch (this.b) {
                case 1:
                    if (!RtspMessageUtil.b(str)) {
                        return null;
                    }
                    this.b = 2;
                    return null;
                case 2:
                    long d = RtspMessageUtil.d(str);
                    if (d != -1) {
                        this.c = d;
                    }
                    if (!str.isEmpty()) {
                        return null;
                    }
                    if (this.c > 0) {
                        this.b = 3;
                        return null;
                    }
                    ImmutableList<String> copyOf = ImmutableList.copyOf((Collection) this.a);
                    a();
                    return copyOf;
                default:
                    throw new IllegalStateException();
            }
        }

        private ImmutableList<String> b(byte[] bArr) {
            String str;
            Assertions.checkState(this.b == 3);
            if (bArr.length <= 0 || bArr[bArr.length - 1] != 10) {
                throw new IllegalArgumentException("Message body is empty or does not end with a LF.");
            }
            if (bArr.length <= 1 || bArr[bArr.length - 2] != 13) {
                str = new String(bArr, 0, bArr.length - 1, RtspMessageChannel.a);
            } else {
                str = new String(bArr, 0, bArr.length - 2, RtspMessageChannel.a);
            }
            this.a.add(str);
            ImmutableList<String> copyOf = ImmutableList.copyOf((Collection) this.a);
            a();
            return copyOf;
        }

        private void a() {
            this.a.clear();
            this.b = 1;
            this.c = 0L;
        }
    }
}
