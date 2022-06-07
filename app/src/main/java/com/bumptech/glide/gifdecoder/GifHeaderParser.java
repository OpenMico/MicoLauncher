package com.bumptech.glide.gifdecoder;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import io.netty.handler.codec.dns.DnsRecord;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* loaded from: classes.dex */
public class GifHeaderParser {
    private ByteBuffer b;
    private GifHeader c;
    private final byte[] a = new byte[256];
    private int d = 0;

    public GifHeaderParser setData(@NonNull ByteBuffer byteBuffer) {
        a();
        this.b = byteBuffer.asReadOnlyBuffer();
        this.b.position(0);
        this.b.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    public GifHeaderParser setData(@Nullable byte[] bArr) {
        if (bArr != null) {
            setData(ByteBuffer.wrap(bArr));
        } else {
            this.b = null;
            this.c.b = 2;
        }
        return this;
    }

    public void clear() {
        this.b = null;
        this.c = null;
    }

    private void a() {
        this.b = null;
        Arrays.fill(this.a, (byte) 0);
        this.c = new GifHeader();
        this.d = 0;
    }

    @NonNull
    public GifHeader parseHeader() {
        if (this.b == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        } else if (m()) {
            return this.c;
        } else {
            f();
            if (!m()) {
                b();
                if (this.c.c < 0) {
                    this.c.b = 1;
                }
            }
            return this.c;
        }
    }

    public boolean isAnimated() {
        f();
        if (!m()) {
            a(2);
        }
        return this.c.c > 1;
    }

    private void b() {
        a(Integer.MAX_VALUE);
    }

    private void a(int i) {
        boolean z = false;
        while (!z && !m() && this.c.c <= i) {
            int k = k();
            if (k == 33) {
                int k2 = k();
                if (k2 == 1) {
                    i();
                } else if (k2 != 249) {
                    switch (k2) {
                        case DnsRecord.CLASS_NONE /* 254 */:
                            i();
                            continue;
                        case 255:
                            j();
                            StringBuilder sb = new StringBuilder();
                            for (int i2 = 0; i2 < 11; i2++) {
                                sb.append((char) this.a[i2]);
                            }
                            if (!sb.toString().equals("NETSCAPE2.0")) {
                                i();
                                break;
                            } else {
                                e();
                                continue;
                            }
                        default:
                            i();
                            continue;
                    }
                } else {
                    this.c.d = new a();
                    c();
                }
            } else if (k == 44) {
                if (this.c.d == null) {
                    this.c.d = new a();
                }
                d();
            } else if (k != 59) {
                this.c.b = 1;
            } else {
                z = true;
            }
        }
    }

    private void c() {
        k();
        int k = k();
        this.c.d.g = (k & 28) >> 2;
        boolean z = true;
        if (this.c.d.g == 0) {
            this.c.d.g = 1;
        }
        a aVar = this.c.d;
        if ((k & 1) == 0) {
            z = false;
        }
        aVar.f = z;
        int l = l();
        if (l < 2) {
            l = 10;
        }
        this.c.d.i = l * 10;
        this.c.d.h = k();
        k();
    }

    private void d() {
        this.c.d.a = l();
        this.c.d.b = l();
        this.c.d.c = l();
        this.c.d.d = l();
        int k = k();
        boolean z = false;
        boolean z2 = (k & 128) != 0;
        int pow = (int) Math.pow(2.0d, (k & 7) + 1);
        a aVar = this.c.d;
        if ((k & 64) != 0) {
            z = true;
        }
        aVar.e = z;
        if (z2) {
            this.c.d.k = b(pow);
        } else {
            this.c.d.k = null;
        }
        this.c.d.j = this.b.position();
        h();
        if (!m()) {
            this.c.c++;
            this.c.e.add(this.c.d);
        }
    }

    private void e() {
        do {
            j();
            byte[] bArr = this.a;
            if (bArr[0] == 1) {
                this.c.m = ((bArr[2] & 255) << 8) | (bArr[1] & 255);
            }
            if (this.d <= 0) {
                return;
            }
        } while (!m());
    }

    private void f() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) k());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.c.b = 1;
            return;
        }
        g();
        if (this.c.h && !m()) {
            GifHeader gifHeader = this.c;
            gifHeader.a = b(gifHeader.i);
            GifHeader gifHeader2 = this.c;
            gifHeader2.l = gifHeader2.a[this.c.j];
        }
    }

    private void g() {
        this.c.f = l();
        this.c.g = l();
        int k = k();
        this.c.h = (k & 128) != 0;
        this.c.i = (int) Math.pow(2.0d, (k & 7) + 1);
        this.c.j = k();
        this.c.k = k();
    }

    @Nullable
    private int[] b(int i) {
        byte[] bArr = new byte[i * 3];
        int[] iArr = null;
        try {
            this.b.get(bArr);
            iArr = new int[256];
            int i2 = 0;
            int i3 = 0;
            while (i2 < i) {
                int i4 = i3 + 1;
                int i5 = bArr[i3] & 255;
                int i6 = i4 + 1;
                i3 = i6 + 1;
                i2++;
                iArr[i2] = (i5 << 16) | ViewCompat.MEASURED_STATE_MASK | ((bArr[i4] & 255) << 8) | (bArr[i6] & 255);
            }
        } catch (BufferUnderflowException e) {
            if (Log.isLoggable("GifHeaderParser", 3)) {
                Log.d("GifHeaderParser", "Format Error Reading Color Table", e);
            }
            this.c.b = 1;
        }
        return iArr;
    }

    private void h() {
        k();
        i();
    }

    private void i() {
        int k;
        do {
            k = k();
            this.b.position(Math.min(this.b.position() + k, this.b.limit()));
        } while (k > 0);
    }

    private void j() {
        this.d = k();
        if (this.d > 0) {
            int i = 0;
            int i2 = 0;
            while (i < this.d) {
                try {
                    i2 = this.d - i;
                    this.b.get(this.a, i, i2);
                    i += i2;
                } catch (Exception e) {
                    if (Log.isLoggable("GifHeaderParser", 3)) {
                        Log.d("GifHeaderParser", "Error Reading Block n: " + i + " count: " + i2 + " blockSize: " + this.d, e);
                    }
                    this.c.b = 1;
                    return;
                }
            }
        }
    }

    private int k() {
        try {
            return this.b.get() & 255;
        } catch (Exception unused) {
            this.c.b = 1;
            return 0;
        }
    }

    private int l() {
        return this.b.getShort();
    }

    private boolean m() {
        return this.c.b != 0;
    }
}
