package androidx.renderscript;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import androidx.renderscript.Element;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* loaded from: classes.dex */
public class RenderScript {
    public static final int CREATE_FLAG_NONE = 0;
    static boolean a;
    private static boolean aN;
    static boolean b;
    static Object c;
    static Method d;
    static Method e;
    static int g;
    static String h;
    Element A;
    Element B;
    Element C;
    Element D;
    Element E;
    Element F;
    Element G;
    Element H;
    Element I;
    Element J;
    Element K;
    Element L;
    Element M;
    Element N;
    Element O;
    Element P;
    Element Q;
    Element R;
    Element S;
    Element T;
    Element U;
    Element V;
    Element W;
    Element X;
    Element Y;
    Element Z;
    private Context aH;
    private String aI;
    Element aa;
    Element ab;
    Element ac;
    Element ad;
    Element ae;
    Element af;
    Element ag;
    Element ah;
    Element ai;
    Element aj;
    Element ak;
    Element al;
    Element am;
    Element an;
    Element ao;
    Element ap;
    Element aq;
    Sampler ar;
    Sampler as;
    Sampler at;
    Sampler au;
    Sampler av;
    Sampler aw;
    Sampler ax;
    Sampler ay;
    long j;
    long k;
    boolean l;
    ReentrantReadWriteLock m;
    a n;
    Element o;
    Element p;
    Element q;
    Element r;
    Element s;
    Element t;
    Element u;
    Element v;
    Element w;
    Element x;
    Element y;
    Element z;
    private static ArrayList<RenderScript> aB = new ArrayList<>();
    private static String aJ = "";
    static Object f = new Object();
    private static int aK = -1;
    private static int aL = -1;
    private static boolean aM = false;
    private boolean aC = false;
    private boolean aD = false;
    private int aE = 0;
    private int aF = 0;
    private int aG = 0;
    private boolean aO = false;
    RSMessageHandler az = null;
    RSErrorHandler aA = null;
    ContextType i = ContextType.NORMAL;

    /* loaded from: classes.dex */
    public static class RSErrorHandler implements Runnable {
        protected String mErrorMessage;
        protected int mErrorNum;

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    /* loaded from: classes.dex */
    public static class RSMessageHandler implements Runnable {
        protected int[] mData;
        protected int mID;
        protected int mLength;

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    static native int rsnSystemGetPointerSize();

    native void nContextDeinitToClient(long j);

    native String nContextGetErrorMessage(long j);

    native int nContextGetUserMessage(long j, int[] iArr);

    native void nContextInitToClient(long j);

    native int nContextPeekMessage(long j, int[] iArr);

    native long nDeviceCreate();

    native long nIncDeviceCreate();

    native boolean nIncLoadSO(int i, String str);

    native boolean nLoadIOSO();

    native boolean nLoadSO(boolean z, int i, String str);

    native void rsnAllocationCopyFromBitmap(long j, long j2, Bitmap bitmap);

    native void rsnAllocationCopyToBitmap(long j, long j2, Bitmap bitmap);

    native long rsnAllocationCreateBitmapBackedAllocation(long j, long j2, int i, Bitmap bitmap, int i2);

    native long rsnAllocationCreateFromBitmap(long j, long j2, int i, Bitmap bitmap, int i2);

    native long rsnAllocationCreateTyped(long j, long j2, int i, int i2, long j3);

    native long rsnAllocationCubeCreateFromBitmap(long j, long j2, int i, Bitmap bitmap, int i2);

    native void rsnAllocationData1D(long j, long j2, int i, int i2, int i3, Object obj, int i4, int i5, int i6, boolean z);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, long j3, int i7, int i8, int i9, int i10);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, int i8, int i9, boolean z);

    native void rsnAllocationData2D(long j, long j2, int i, int i2, int i3, int i4, Bitmap bitmap);

    native void rsnAllocationData3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j3, int i8, int i9, int i10, int i11);

    native void rsnAllocationData3D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, Object obj, int i8, int i9, int i10, boolean z);

    native void rsnAllocationElementData1D(long j, long j2, int i, int i2, int i3, byte[] bArr, int i4);

    native void rsnAllocationGenerateMipmaps(long j, long j2);

    native ByteBuffer rsnAllocationGetByteBuffer(long j, long j2, int i, int i2, int i3);

    native long rsnAllocationGetStride(long j, long j2);

    native void rsnAllocationIoReceive(long j, long j2);

    native void rsnAllocationIoSend(long j, long j2);

    native void rsnAllocationRead(long j, long j2, Object obj, int i, int i2, boolean z);

    native void rsnAllocationRead1D(long j, long j2, int i, int i2, int i3, Object obj, int i4, int i5, int i6, boolean z);

    native void rsnAllocationRead2D(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, int i8, int i9, boolean z);

    native void rsnAllocationSetSurface(long j, long j2, Surface surface);

    native void rsnAllocationSyncAll(long j, long j2, int i);

    native long rsnClosureCreate(long j, long j2, long j3, long[] jArr, long[] jArr2, int[] iArr, long[] jArr3, long[] jArr4);

    native void rsnClosureSetArg(long j, long j2, int i, long j3, int i2);

    native void rsnClosureSetGlobal(long j, long j2, long j3, long j4, int i);

    native long rsnContextCreate(long j, int i, int i2, int i3, String str);

    native void rsnContextDestroy(long j);

    native void rsnContextDump(long j, int i);

    native void rsnContextFinish(long j);

    native void rsnContextSendMessage(long j, int i, int[] iArr);

    native void rsnContextSetPriority(long j, int i);

    native long rsnElementCreate(long j, long j2, int i, boolean z, int i2);

    native long rsnElementCreate2(long j, long[] jArr, String[] strArr, int[] iArr);

    native long rsnIncAllocationCreateTyped(long j, long j2, long j3, long j4, int i);

    native long rsnIncContextCreate(long j, int i, int i2, int i3);

    native void rsnIncContextDestroy(long j);

    native void rsnIncContextFinish(long j);

    native long rsnIncElementCreate(long j, long j2, int i, boolean z, int i2);

    native void rsnIncObjDestroy(long j, long j2);

    native long rsnIncTypeCreate(long j, long j2, int i, int i2, int i3, boolean z, boolean z2, int i4);

    native long rsnInvokeClosureCreate(long j, long j2, byte[] bArr, long[] jArr, long[] jArr2, int[] iArr);

    native void rsnObjDestroy(long j, long j2);

    native long rsnSamplerCreate(long j, int i, int i2, int i3, int i4, int i5, float f2);

    native void rsnScriptBindAllocation(long j, long j2, long j3, int i, boolean z);

    native long rsnScriptCCreate(long j, String str, String str2, byte[] bArr, int i);

    native long rsnScriptFieldIDCreate(long j, long j2, int i, boolean z);

    native void rsnScriptForEach(long j, long j2, int i, long[] jArr, long j3, byte[] bArr, int[] iArr);

    native void rsnScriptForEach(long j, long j2, long j3, int i, long j4, long j5, boolean z);

    native void rsnScriptForEach(long j, long j2, long j3, int i, long j4, long j5, byte[] bArr, boolean z);

    native void rsnScriptForEachClipped(long j, long j2, long j3, int i, long j4, long j5, int i2, int i3, int i4, int i5, int i6, int i7, boolean z);

    native void rsnScriptForEachClipped(long j, long j2, long j3, int i, long j4, long j5, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, boolean z);

    native long rsnScriptGroup2Create(long j, String str, String str2, long[] jArr);

    native void rsnScriptGroup2Execute(long j, long j2);

    native long rsnScriptGroupCreate(long j, long[] jArr, long[] jArr2, long[] jArr3, long[] jArr4, long[] jArr5);

    native void rsnScriptGroupExecute(long j, long j2);

    native void rsnScriptGroupSetInput(long j, long j2, long j3, long j4);

    native void rsnScriptGroupSetOutput(long j, long j2, long j3, long j4);

    native void rsnScriptIntrinsicBLAS_BNNM(long j, long j2, long j3, int i, int i2, int i3, long j4, int i4, long j5, int i5, long j6, int i6, int i7, boolean z);

    native void rsnScriptIntrinsicBLAS_Complex(long j, long j2, long j3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f2, float f3, long j4, long j5, float f4, float f5, long j6, int i10, int i11, int i12, int i13, boolean z);

    native void rsnScriptIntrinsicBLAS_Double(long j, long j2, long j3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d2, long j4, long j5, double d3, long j6, int i10, int i11, int i12, int i13, boolean z);

    native void rsnScriptIntrinsicBLAS_Single(long j, long j2, long j3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f2, long j4, long j5, float f3, long j6, int i10, int i11, int i12, int i13, boolean z);

    native void rsnScriptIntrinsicBLAS_Z(long j, long j2, long j3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d2, double d3, long j4, long j5, double d4, double d5, long j6, int i10, int i11, int i12, int i13, boolean z);

    native long rsnScriptIntrinsicCreate(long j, int i, long j2, boolean z);

    native void rsnScriptInvoke(long j, long j2, int i, boolean z);

    native long rsnScriptInvokeIDCreate(long j, long j2, int i);

    native void rsnScriptInvokeV(long j, long j2, int i, byte[] bArr, boolean z);

    native long rsnScriptKernelIDCreate(long j, long j2, int i, int i2, boolean z);

    native void rsnScriptReduce(long j, long j2, int i, long[] jArr, long j3, int[] iArr);

    native void rsnScriptSetTimeZone(long j, long j2, byte[] bArr, boolean z);

    native void rsnScriptSetVarD(long j, long j2, int i, double d2, boolean z);

    native void rsnScriptSetVarF(long j, long j2, int i, float f2, boolean z);

    native void rsnScriptSetVarI(long j, long j2, int i, int i2, boolean z);

    native void rsnScriptSetVarJ(long j, long j2, int i, long j3, boolean z);

    native void rsnScriptSetVarObj(long j, long j2, int i, long j3, boolean z);

    native void rsnScriptSetVarV(long j, long j2, int i, byte[] bArr, boolean z);

    native void rsnScriptSetVarVE(long j, long j2, int i, byte[] bArr, long j3, int[] iArr, boolean z);

    native long rsnTypeCreate(long j, long j2, int i, int i2, int i3, boolean z, boolean z2, int i4);

    public static void setBlackList(String str) {
        if (str != null) {
            aJ = str;
        }
    }

    public static void forceCompat() {
        aK = 0;
    }

    public int a() {
        return this.aE;
    }

    public boolean b() {
        return aN;
    }

    public static int getPointerSize() {
        synchronized (f) {
            if (!a) {
                throw new RSInvalidStateException("Calling getPointerSize() before any RenderScript instantiated");
            }
        }
        return g;
    }

    private static boolean a(int i, Context context) {
        int i2;
        long j;
        if (Build.VERSION.SDK_INT < i && Build.VERSION.SDK_INT < 21) {
            aK = 0;
        }
        if (aK == -1) {
            try {
                i2 = ((Integer) Class.forName("android.os.SystemProperties").getDeclaredMethod("getInt", String.class, Integer.TYPE).invoke(null, "debug.rs.forcecompat", new Integer(0))).intValue();
            } catch (Exception unused) {
                i2 = 0;
            }
            if (Build.VERSION.SDK_INT < 19 || i2 != 0) {
                aK = 0;
            } else {
                aK = 1;
            }
            if (aK == 1) {
                try {
                    ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                    try {
                        j = ((Long) Class.forName("android.renderscript.RenderScript").getDeclaredMethod("getMinorID", new Class[0]).invoke(null, new Object[0])).longValue();
                    } catch (Exception unused2) {
                        j = 0;
                    }
                    if (applicationInfo.metaData != null) {
                        if (applicationInfo.metaData.getBoolean("androidx.renderscript.EnableAsyncTeardown") && j == 0) {
                            aK = 0;
                        }
                        if (applicationInfo.metaData.getBoolean("androidx.renderscript.EnableBlurWorkaround") && Build.VERSION.SDK_INT <= 19) {
                            aK = 0;
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused3) {
                    return true;
                }
            }
        }
        if (aK != 1) {
            return false;
        }
        if (aJ.length() > 0) {
            if (aJ.contains('(' + Build.MANUFACTURER + ':' + Build.PRODUCT + ':' + Build.MODEL + ')')) {
                aK = 0;
                return false;
            }
        }
        return true;
    }

    public static void setupDiskCache(File file) {
        File file2 = new File(file, "com.android.renderscript.cache");
        h = file2.getAbsolutePath();
        file2.mkdirs();
    }

    /* loaded from: classes.dex */
    public enum ContextType {
        NORMAL(0),
        DEBUG(1),
        PROFILE(2);
        
        int mID;

        ContextType(int i) {
            this.mID = i;
        }
    }

    synchronized long a(long j, int i, int i2, int i3, String str) {
        return rsnContextCreate(j, i, i2, i3, str);
    }

    synchronized void c() {
        g();
        ReentrantReadWriteLock.WriteLock writeLock = this.m.writeLock();
        writeLock.lock();
        long j = this.j;
        this.j = 0L;
        writeLock.unlock();
        rsnContextDestroy(j);
    }

    synchronized void a(int i) {
        g();
        rsnContextSetPriority(this.j, i);
    }

    synchronized void b(int i) {
        g();
        rsnContextDump(this.j, i);
    }

    synchronized void d() {
        g();
        rsnContextFinish(this.j);
    }

    synchronized void a(int i, int[] iArr) {
        g();
        rsnContextSendMessage(this.j, i, iArr);
    }

    public void a(long j) {
        long j2 = this.j;
        if (j2 != 0) {
            rsnObjDestroy(j2, j);
        }
    }

    public synchronized long a(long j, int i, boolean z, int i2) {
        g();
        return rsnElementCreate(this.j, j, i, z, i2);
    }

    public synchronized long a(long[] jArr, String[] strArr, int[] iArr) {
        g();
        return rsnElementCreate2(this.j, jArr, strArr, iArr);
    }

    public synchronized long a(long j, int i, int i2, int i3, boolean z, boolean z2, int i4) {
        g();
        return rsnTypeCreate(this.j, j, i, i2, i3, z, z2, i4);
    }

    public synchronized long a(long j, int i, int i2, long j2) {
        g();
        return rsnAllocationCreateTyped(this.j, j, i, i2, j2);
    }

    public synchronized long a(long j, int i, Bitmap bitmap, int i2) {
        g();
        return rsnAllocationCreateFromBitmap(this.j, j, i, bitmap, i2);
    }

    public synchronized long b(long j, int i, Bitmap bitmap, int i2) {
        g();
        return rsnAllocationCreateBitmapBackedAllocation(this.j, j, i, bitmap, i2);
    }

    public synchronized long c(long j, int i, Bitmap bitmap, int i2) {
        g();
        return rsnAllocationCubeCreateFromBitmap(this.j, j, i, bitmap, i2);
    }

    public synchronized void a(long j, Bitmap bitmap) {
        g();
        rsnAllocationCopyToBitmap(this.j, j, bitmap);
    }

    public synchronized void a(long j, int i) {
        g();
        rsnAllocationSyncAll(this.j, j, i);
    }

    public synchronized void a(long j, Surface surface) {
        g();
        rsnAllocationSetSurface(this.j, j, surface);
    }

    public synchronized void b(long j) {
        g();
        rsnAllocationIoSend(this.j, j);
    }

    public synchronized void c(long j) {
        g();
        rsnAllocationIoReceive(this.j, j);
    }

    public synchronized ByteBuffer a(long j, int i, int i2, int i3) {
        g();
        return rsnAllocationGetByteBuffer(this.j, j, i, i2, i3);
    }

    public synchronized long d(long j) {
        g();
        return rsnAllocationGetStride(this.j, j);
    }

    public synchronized void e(long j) {
        g();
        rsnAllocationGenerateMipmaps(this.j, j);
    }

    public synchronized void b(long j, Bitmap bitmap) {
        g();
        rsnAllocationCopyFromBitmap(this.j, j, bitmap);
    }

    public synchronized void a(long j, int i, int i2, int i3, Object obj, int i4, Element.DataType dataType, int i5, boolean z) {
        g();
        rsnAllocationData1D(this.j, j, i, i2, i3, obj, i4, dataType.mID, i5, z);
    }

    public synchronized void a(long j, int i, int i2, int i3, byte[] bArr, int i4) {
        g();
        rsnAllocationElementData1D(this.j, j, i, i2, i3, bArr, i4);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, long j2, int i7, int i8, int i9, int i10) {
        g();
        rsnAllocationData2D(this.j, j, i, i2, i3, i4, i5, i6, j2, i7, i8, i9, i10);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, Element.DataType dataType, int i8, boolean z) {
        g();
        rsnAllocationData2D(this.j, j, i, i2, i3, i4, i5, i6, obj, i7, dataType.mID, i8, z);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, Bitmap bitmap) {
        g();
        rsnAllocationData2D(this.j, j, i, i2, i3, i4, bitmap);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8, int i9, int i10, int i11) {
        g();
        rsnAllocationData3D(this.j, j, i, i2, i3, i4, i5, i6, i7, j2, i8, i9, i10, i11);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, Object obj, int i8, Element.DataType dataType, int i9, boolean z) {
        g();
        rsnAllocationData3D(this.j, j, i, i2, i3, i4, i5, i6, i7, obj, i8, dataType.mID, i9, z);
    }

    public synchronized void a(long j, Object obj, Element.DataType dataType, int i, boolean z) {
        g();
        rsnAllocationRead(this.j, j, obj, dataType.mID, i, z);
    }

    public synchronized void b(long j, int i, int i2, int i3, Object obj, int i4, Element.DataType dataType, int i5, boolean z) {
        g();
        rsnAllocationRead1D(this.j, j, i, i2, i3, obj, i4, dataType.mID, i5, z);
    }

    public synchronized void b(long j, int i, int i2, int i3, int i4, int i5, int i6, Object obj, int i7, Element.DataType dataType, int i8, boolean z) {
        g();
        rsnAllocationRead2D(this.j, j, i, i2, i3, i4, i5, i6, obj, i7, dataType.mID, i8, z);
    }

    public synchronized void a(long j, long j2, int i, boolean z) {
        g();
        rsnScriptBindAllocation(z ? this.k : this.j, j, j2, i, z);
    }

    public synchronized void a(long j, byte[] bArr, boolean z) {
        g();
        rsnScriptSetTimeZone(z ? this.k : this.j, j, bArr, z);
    }

    public synchronized void a(long j, int i, boolean z) {
        g();
        rsnScriptInvoke(z ? this.k : this.j, j, i, z);
    }

    public synchronized void a(long j, int i, long j2, long j3, byte[] bArr, boolean z) {
        g();
        if (bArr == null) {
            rsnScriptForEach(this.j, this.k, j, i, j2, j3, z);
        } else {
            rsnScriptForEach(this.j, this.k, j, i, j2, j3, bArr, z);
        }
    }

    public synchronized void a(long j, int i, long j2, long j3, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        g();
        if (bArr == null) {
            rsnScriptForEachClipped(this.j, this.k, j, i, j2, j3, i2, i3, i4, i5, i6, i7, z);
        } else {
            rsnScriptForEachClipped(this.j, this.k, j, i, j2, j3, bArr, i2, i3, i4, i5, i6, i7, z);
        }
    }

    public synchronized void a(long j, int i, long[] jArr, long j2, byte[] bArr, int[] iArr) {
        if (this.aD) {
            g();
            rsnScriptForEach(this.j, j, i, jArr, j2, bArr, iArr);
        } else {
            Log.e("RenderScript_jni", "Multi-input kernels are not supported, please change targetSdkVersion to >= 23");
            throw new RSRuntimeException("Multi-input kernels are not supported before API 23)");
        }
    }

    public synchronized void a(long j, int i, long[] jArr, long j2, int[] iArr) {
        g();
        rsnScriptReduce(this.j, j, i, jArr, j2, iArr);
    }

    public synchronized void a(long j, int i, byte[] bArr, boolean z) {
        g();
        rsnScriptInvokeV(z ? this.k : this.j, j, i, bArr, z);
    }

    public synchronized void a(long j, int i, int i2, boolean z) {
        g();
        rsnScriptSetVarI(z ? this.k : this.j, j, i, i2, z);
    }

    public synchronized void a(long j, int i, long j2, boolean z) {
        g();
        rsnScriptSetVarJ(z ? this.k : this.j, j, i, j2, z);
    }

    public synchronized void a(long j, int i, float f2, boolean z) {
        g();
        rsnScriptSetVarF(z ? this.k : this.j, j, i, f2, z);
    }

    public synchronized void a(long j, int i, double d2, boolean z) {
        g();
        rsnScriptSetVarD(z ? this.k : this.j, j, i, d2, z);
    }

    public synchronized void b(long j, int i, byte[] bArr, boolean z) {
        g();
        rsnScriptSetVarV(z ? this.k : this.j, j, i, bArr, z);
    }

    public synchronized void a(long j, int i, byte[] bArr, long j2, int[] iArr, boolean z) {
        g();
        rsnScriptSetVarVE(z ? this.k : this.j, j, i, bArr, j2, iArr, z);
    }

    public synchronized void b(long j, int i, long j2, boolean z) {
        g();
        rsnScriptSetVarObj(z ? this.k : this.j, j, i, j2, z);
    }

    public synchronized long a(String str, String str2, byte[] bArr, int i) {
        g();
        return rsnScriptCCreate(this.j, str, str2, bArr, i);
    }

    public synchronized long a(int i, long j, boolean z) {
        g();
        if (!z) {
            return rsnScriptIntrinsicCreate(this.j, i, j, z);
        } else if (Build.VERSION.SDK_INT >= 21) {
            if (!this.l) {
                try {
                    System.loadLibrary("RSSupport");
                    if (nIncLoadSO(23, this.aI + "/libRSSupport.so")) {
                        this.l = true;
                    } else {
                        throw new RSRuntimeException("Error loading libRSSupport library for Incremental Intrinsic Support");
                    }
                } catch (UnsatisfiedLinkError e2) {
                    Log.e("RenderScript_jni", "Error loading RS Compat library for Incremental Intrinsic Support: " + e2);
                    throw new RSRuntimeException("Error loading RS Compat library for Incremental Intrinsic Support: " + e2);
                }
            }
            if (this.k == 0) {
                this.k = b(nIncDeviceCreate(), 0, 0, 0);
            }
            return rsnScriptIntrinsicCreate(this.k, i, j, z);
        } else {
            Log.e("RenderScript_jni", "Incremental Intrinsics are not supported, please change targetSdkVersion to >= 21");
            throw new RSRuntimeException("Incremental Intrinsics are not supported before Lollipop (API 21)");
        }
    }

    public synchronized long b(long j, int i, int i2, boolean z) {
        g();
        return rsnScriptKernelIDCreate(z ? this.k : this.j, j, i, i2, z);
    }

    public synchronized long b(long j, int i) {
        g();
        return rsnScriptInvokeIDCreate(this.j, j, i);
    }

    public synchronized long b(long j, int i, boolean z) {
        g();
        return rsnScriptFieldIDCreate(z ? this.k : this.j, j, i, z);
    }

    public synchronized long a(long[] jArr, long[] jArr2, long[] jArr3, long[] jArr4, long[] jArr5) {
        g();
        return rsnScriptGroupCreate(this.j, jArr, jArr2, jArr3, jArr4, jArr5);
    }

    public synchronized void a(long j, long j2, long j3) {
        g();
        rsnScriptGroupSetInput(this.j, j, j2, j3);
    }

    public synchronized void b(long j, long j2, long j3) {
        g();
        rsnScriptGroupSetOutput(this.j, j, j2, j3);
    }

    public synchronized void f(long j) {
        g();
        rsnScriptGroupExecute(this.j, j);
    }

    public synchronized long a(int i, int i2, int i3, int i4, int i5, float f2) {
        g();
        return rsnSamplerCreate(this.j, i, i2, i3, i4, i5, f2);
    }

    public synchronized long a(long j, long j2, long[] jArr, long[] jArr2, int[] iArr, long[] jArr3, long[] jArr4) {
        long rsnClosureCreate;
        g();
        rsnClosureCreate = rsnClosureCreate(this.j, j, j2, jArr, jArr2, iArr, jArr3, jArr4);
        if (rsnClosureCreate == 0) {
            throw new RSRuntimeException("Failed creating closure.");
        }
        return rsnClosureCreate;
    }

    public synchronized long a(long j, byte[] bArr, long[] jArr, long[] jArr2, int[] iArr) {
        long rsnInvokeClosureCreate;
        g();
        rsnInvokeClosureCreate = rsnInvokeClosureCreate(this.j, j, bArr, jArr, jArr2, iArr);
        if (rsnInvokeClosureCreate == 0) {
            throw new RSRuntimeException("Failed creating closure.");
        }
        return rsnInvokeClosureCreate;
    }

    public synchronized void a(long j, int i, long j2, int i2) {
        g();
        rsnClosureSetArg(this.j, j, i, j2, i2);
    }

    public synchronized void a(long j, long j2, long j3, int i) {
        g();
        rsnClosureSetGlobal(this.j, j, j2, j3, i);
    }

    public synchronized long a(String str, String str2, long[] jArr) {
        g();
        return rsnScriptGroup2Create(this.j, str, str2, jArr);
    }

    public synchronized void g(long j) {
        g();
        rsnScriptGroup2Execute(this.j, j);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f2, long j2, long j3, float f3, long j4, int i10, int i11, int i12, int i13, boolean z) {
        g();
        rsnScriptIntrinsicBLAS_Single(this.j, this.k, j, i, i2, i3, i4, i5, i6, i7, i8, i9, f2, j2, j3, f3, j4, i10, i11, i12, i13, z);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d2, long j2, long j3, double d3, long j4, int i10, int i11, int i12, int i13, boolean z) {
        g();
        rsnScriptIntrinsicBLAS_Double(this.j, this.k, j, i, i2, i3, i4, i5, i6, i7, i8, i9, d2, j2, j3, d3, j4, i10, i11, i12, i13, z);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, float f2, float f3, long j2, long j3, float f4, float f5, long j4, int i10, int i11, int i12, int i13, boolean z) {
        g();
        rsnScriptIntrinsicBLAS_Complex(this.j, this.k, j, i, i2, i3, i4, i5, i6, i7, i8, i9, f2, f3, j2, j3, f4, f5, j4, i10, i11, i12, i13, z);
    }

    public synchronized void a(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, double d2, double d3, long j2, long j3, double d4, double d5, long j4, int i10, int i11, int i12, int i13, boolean z) {
        g();
        rsnScriptIntrinsicBLAS_Z(this.j, this.k, j, i, i2, i3, i4, i5, i6, i7, i8, i9, d2, d3, j2, j3, d4, d5, j4, i10, i11, i12, i13, z);
    }

    public synchronized void a(long j, int i, int i2, int i3, long j2, int i4, long j3, int i5, long j4, int i6, int i7, boolean z) {
        g();
        rsnScriptIntrinsicBLAS_BNNM(this.j, this.k, j, i, i2, i3, j2, i4, j3, i5, j4, i6, i7, z);
    }

    synchronized long b(long j, int i, int i2, int i3) {
        return rsnIncContextCreate(j, i, i2, i3);
    }

    synchronized void e() {
        g();
        ReentrantReadWriteLock.WriteLock writeLock = this.m.writeLock();
        writeLock.lock();
        long j = this.k;
        this.k = 0L;
        writeLock.unlock();
        rsnIncContextDestroy(j);
    }

    synchronized void f() {
        g();
        rsnIncContextFinish(this.k);
    }

    public void h(long j) {
        long j2 = this.k;
        if (j2 != 0) {
            rsnIncObjDestroy(j2, j);
        }
    }

    public synchronized long b(long j, int i, boolean z, int i2) {
        g();
        return rsnIncElementCreate(this.k, j, i, z, i2);
    }

    public synchronized long b(long j, int i, int i2, int i3, boolean z, boolean z2, int i4) {
        g();
        return rsnIncTypeCreate(this.k, j, i, i2, i3, z, z2, i4);
    }

    public synchronized long a(long j, long j2, int i) {
        g();
        return rsnIncAllocationCreateTyped(this.j, this.k, j, j2, i);
    }

    public void setMessageHandler(RSMessageHandler rSMessageHandler) {
        this.az = rSMessageHandler;
    }

    public RSMessageHandler getMessageHandler() {
        return this.az;
    }

    public void sendMessage(int i, int[] iArr) {
        a(i, iArr);
    }

    public void setErrorHandler(RSErrorHandler rSErrorHandler) {
        this.aA = rSErrorHandler;
    }

    public RSErrorHandler getErrorHandler() {
        return this.aA;
    }

    /* loaded from: classes.dex */
    public enum Priority {
        LOW(15),
        NORMAL(-4);
        
        int mID;

        Priority(int i) {
            this.mID = i;
        }
    }

    public void a(BaseObj baseObj) {
        if (baseObj != null && baseObj.t != this) {
            throw new RSIllegalArgumentException("Attempting to use an object across contexts.");
        }
    }

    public void g() {
        if (this.j == 0) {
            throw new RSInvalidStateException("Calling RS with no Context active.");
        }
    }

    public boolean h() {
        return aM;
    }

    public void setPriority(Priority priority) {
        g();
        a(priority.mID);
    }

    /* loaded from: classes.dex */
    public static class a extends Thread {
        RenderScript a;
        boolean b = true;
        int[] c = new int[2];

        a(RenderScript renderScript) {
            super("RSMessageThread");
            this.a = renderScript;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            int[] iArr = new int[16];
            RenderScript renderScript = this.a;
            renderScript.nContextInitToClient(renderScript.j);
            while (this.b) {
                iArr[0] = 0;
                RenderScript renderScript2 = this.a;
                int nContextPeekMessage = renderScript2.nContextPeekMessage(renderScript2.j, this.c);
                int[] iArr2 = this.c;
                int i = iArr2[1];
                int i2 = iArr2[0];
                if (nContextPeekMessage == 4) {
                    if ((i >> 2) >= iArr.length) {
                        iArr = new int[(i + 3) >> 2];
                    }
                    RenderScript renderScript3 = this.a;
                    if (renderScript3.nContextGetUserMessage(renderScript3.j, iArr) != 4) {
                        throw new RSDriverException("Error processing message from RenderScript.");
                    } else if (this.a.az != null) {
                        this.a.az.mData = iArr;
                        this.a.az.mID = i2;
                        this.a.az.mLength = i;
                        this.a.az.run();
                    } else {
                        throw new RSInvalidStateException("Received a message from the script with no message handler installed.");
                    }
                } else if (nContextPeekMessage == 3) {
                    RenderScript renderScript4 = this.a;
                    String nContextGetErrorMessage = renderScript4.nContextGetErrorMessage(renderScript4.j);
                    if (i2 >= 4096 || (i2 >= 2048 && (this.a.i != ContextType.DEBUG || this.a.aA == null))) {
                        Log.e("RenderScript_jni", "fatal RS error, " + nContextGetErrorMessage);
                        throw new RSRuntimeException("Fatal error " + i2 + ", details: " + nContextGetErrorMessage);
                    } else if (this.a.aA != null) {
                        this.a.aA.mErrorMessage = nContextGetErrorMessage;
                        this.a.aA.mErrorNum = i2;
                        this.a.aA.run();
                    } else {
                        Log.e("RenderScript_jni", "non fatal RS error, " + nContextGetErrorMessage);
                    }
                } else {
                    try {
                        sleep(1L, 0);
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
    }

    RenderScript(Context context) {
        if (context != null) {
            this.aH = context.getApplicationContext();
            this.aI = this.aH.getApplicationInfo().nativeLibraryDir;
        }
        this.k = 0L;
        this.l = false;
        this.m = new ReentrantReadWriteLock();
    }

    public final Context getApplicationContext() {
        return this.aH;
    }

    private static RenderScript a(Context context, int i, ContextType contextType, int i2) {
        String str;
        RenderScript renderScript = new RenderScript(context);
        int i3 = aL;
        if (i3 == -1) {
            aL = i;
        } else if (i3 != i) {
            throw new RSRuntimeException("Can't have two contexts with different SDK versions in support lib");
        }
        aN = a(aL, context);
        synchronized (f) {
            str = null;
            if (!a) {
                try {
                    Class<?> cls = Class.forName("dalvik.system.VMRuntime");
                    c = cls.getDeclaredMethod("getRuntime", new Class[0]).invoke(null, new Object[0]);
                    d = cls.getDeclaredMethod("registerNativeAllocation", Integer.TYPE);
                    e = cls.getDeclaredMethod("registerNativeFree", Integer.TYPE);
                    b = true;
                } catch (Exception unused) {
                    Log.e("RenderScript_jni", "No GC methods");
                    b = false;
                }
                try {
                    if (Build.VERSION.SDK_INT >= 23 || renderScript.aI == null) {
                        System.loadLibrary("rsjni_androidx");
                    } else {
                        System.load(renderScript.aI + "/librsjni_androidx.so");
                    }
                    a = true;
                    g = rsnSystemGetPointerSize();
                } catch (UnsatisfiedLinkError e2) {
                    Log.e("RenderScript_jni", "Error loading RS jni library: " + e2);
                    throw new RSRuntimeException("Error loading RS jni library: " + e2 + " Support lib API: 2301");
                }
            }
        }
        if (aN) {
            Log.v("RenderScript_jni", "RS native mode");
        } else {
            Log.v("RenderScript_jni", "RS compat mode");
        }
        if (Build.VERSION.SDK_INT >= 14) {
            aM = true;
        }
        int i4 = i < Build.VERSION.SDK_INT ? Build.VERSION.SDK_INT : i;
        if (Build.VERSION.SDK_INT < 23 && renderScript.aI != null) {
            str = renderScript.aI + "/libRSSupport.so";
        }
        if (!renderScript.nLoadSO(aN, i4, str)) {
            if (aN) {
                Log.v("RenderScript_jni", "Unable to load libRS.so, falling back to compat mode");
                aN = false;
            }
            try {
                if (Build.VERSION.SDK_INT >= 23 || renderScript.aI == null) {
                    System.loadLibrary("RSSupport");
                } else {
                    System.load(str);
                }
                if (!renderScript.nLoadSO(false, i4, str)) {
                    Log.e("RenderScript_jni", "Error loading RS Compat library: nLoadSO() failed; Support lib version: 2301");
                    throw new RSRuntimeException("Error loading libRSSupport library, Support lib version: 2301");
                }
            } catch (UnsatisfiedLinkError e3) {
                Log.e("RenderScript_jni", "Error loading RS Compat library: " + e3 + " Support lib version: 2301");
                throw new RSRuntimeException("Error loading RS Compat library: " + e3 + " Support lib version: 2301");
            }
        }
        if (aM) {
            try {
                System.loadLibrary("RSSupportIO");
            } catch (UnsatisfiedLinkError unused2) {
                aM = false;
            }
            if (!aM || !renderScript.nLoadIOSO()) {
                Log.v("RenderScript_jni", "Unable to load libRSSupportIO.so, USAGE_IO not supported");
                aM = false;
            }
        }
        if (i4 >= 23) {
            renderScript.aD = true;
            try {
                System.loadLibrary("blasV8");
            } catch (UnsatisfiedLinkError e4) {
                Log.v("RenderScript_jni", "Unable to load BLAS lib, ONLY BNNM will be supported: " + e4);
            }
        }
        renderScript.j = renderScript.a(renderScript.nDeviceCreate(), 0, i, contextType.mID, renderScript.aI);
        renderScript.i = contextType;
        renderScript.aF = i2;
        renderScript.aG = i;
        renderScript.aE = i4;
        if (renderScript.j != 0) {
            renderScript.n = new a(renderScript);
            renderScript.n.start();
            return renderScript;
        }
        throw new RSDriverException("Failed to create RS context.");
    }

    public static RenderScript create(Context context) {
        return create(context, ContextType.NORMAL);
    }

    public static RenderScript create(Context context, ContextType contextType) {
        return create(context, contextType, 0);
    }

    public static RenderScript create(Context context, ContextType contextType, int i) {
        return create(context, context.getApplicationInfo().targetSdkVersion, contextType, i);
    }

    public static RenderScript create(Context context, int i) {
        return create(context, i, ContextType.NORMAL, 0);
    }

    public static RenderScript create(Context context, int i, ContextType contextType) {
        return create(context, i, contextType, 0);
    }

    public static RenderScript create(Context context, int i, ContextType contextType, int i2) {
        synchronized (aB) {
            Iterator<RenderScript> it = aB.iterator();
            while (it.hasNext()) {
                RenderScript next = it.next();
                if (next.i == contextType && next.aF == i2 && next.aG == i) {
                    return next;
                }
            }
            RenderScript a2 = a(context, i, contextType, i2);
            a2.aC = true;
            aB.add(a2);
            return a2;
        }
    }

    public static void releaseAllContexts() {
        ArrayList<RenderScript> arrayList;
        synchronized (aB) {
            arrayList = aB;
            aB = new ArrayList<>();
        }
        Iterator<RenderScript> it = arrayList.iterator();
        while (it.hasNext()) {
            RenderScript next = it.next();
            next.aC = false;
            next.destroy();
        }
        arrayList.clear();
    }

    public static RenderScript createMultiContext(Context context, ContextType contextType, int i, int i2) {
        return a(context, i2, contextType, i);
    }

    public void contextDump() {
        g();
        b(0);
    }

    public void finish() {
        d();
    }

    private void j() {
        boolean z;
        boolean z2;
        synchronized (this) {
            z = false;
            if (!this.aO) {
                this.aO = true;
                z2 = true;
            } else {
                z2 = false;
            }
        }
        if (z2) {
            d();
            if (this.k != 0) {
                f();
                e();
                this.k = 0L;
            }
            nContextDeinitToClient(this.j);
            a aVar = this.n;
            aVar.b = false;
            aVar.interrupt();
            boolean z3 = false;
            while (!z) {
                try {
                    this.n.join();
                    z = true;
                } catch (InterruptedException unused) {
                    z3 = true;
                }
            }
            if (z3) {
                Log.v("RenderScript_jni", "Interrupted during wait for MessageThread to join");
                Thread.currentThread().interrupt();
            }
            c();
        }
    }

    protected void finalize() throws Throwable {
        j();
        super.finalize();
    }

    public void destroy() {
        if (!this.aC) {
            g();
            j();
        }
    }

    public boolean i() {
        return this.j != 0;
    }

    public long b(BaseObj baseObj) {
        if (baseObj != null) {
            return baseObj.a(this);
        }
        return 0L;
    }
}
