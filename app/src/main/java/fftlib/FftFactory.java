package fftlib;

import com.zlw.main.recorderlib.utils.Logger;

/* loaded from: classes4.dex */
public class FftFactory {
    private static final String a = "FftFactory";
    private Level b = Level.Original;

    /* loaded from: classes4.dex */
    public enum Level {
        Original,
        Music,
        People,
        Maximal
    }

    public FftFactory(Level level) {
    }

    public byte[] makeFftData(byte[] bArr) {
        if (bArr.length < 1024) {
            Logger.d(a, "makeFftData", new Object[0]);
            return null;
        }
        double[] fft = FFT.fft(ByteUtils.toHardDouble(ByteUtils.toShorts(bArr)), 0);
        if (AnonymousClass1.a[this.b.ordinal()] != 1) {
            return ByteUtils.toHardBytes(fft);
        }
        return ByteUtils.toSoftBytes(fft);
    }

    /* renamed from: fftlib.FftFactory$1  reason: invalid class name */
    /* loaded from: classes4.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Level.values().length];

        static {
            try {
                a[Level.Original.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[Level.Maximal.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
