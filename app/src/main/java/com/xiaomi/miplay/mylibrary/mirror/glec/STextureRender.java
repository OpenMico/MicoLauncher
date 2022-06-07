package com.xiaomi.miplay.mylibrary.mirror.glec;

import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLES20;
import android.util.Log;
import java.nio.FloatBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class STextureRender {
    public static final int NeedBottomCrop = 4;
    public static final int NeedLeftCrop = 1;
    public static final int NeedLeftTrans = -1;
    public static final int NeedRightCrop = 2;
    public static final int NeedRightTrans = 1;
    public static final int NeedScale = 1;
    public static final int NeedTopCrop = 3;
    public static final int NotNeedCrop = 0;
    public static final int NotNeedScale = 0;
    public static final int NotNeedTrans = 0;
    public static final int NotVideoHandle = 0;
    public static final int VideoCutBlack = 1;
    public static final int VideoForcePull = 2;
    public static final int VideoHandleEnd = 3;
    public static final int VideoSelfAdaption = 3;
    static final /* synthetic */ boolean a = !STextureRender.class.desiredAssertionStatus();
    private static float b = 0.0f;
    private static float c = 0.0f;
    private static float d = 0.0f;
    private static float e = 0.0f;
    private static int f = 0;
    private static int g = 0;
    private static int h = 0;
    private static int i = 3;
    private static boolean j = false;
    private static final float[] k = {-1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    private static final FloatBuffer l = GlUtil.createFloatBuffer(k);
    private static FloatBuffer m;
    private int A;
    private float[] B;
    private Context C;
    private int D;
    private int E;
    private int F;
    private Lock G;
    private int H;
    private int I;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private boolean v;
    private int w;
    private float[] x;
    private int y;
    private float[] z;

    private float a(int i2, int i3, int i4, int i5, boolean z) {
        float f2 = (i2 * 1.0f) / i3;
        float f3 = (i4 * 1.0f) / i5;
        return f2 > f3 ? z ? f2 / f3 : (-f3) / f2 : z ? (-f3) / f2 : f2 / f3;
    }

    public void setTextureCropAttr(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = this.r;
        b = i2 / i9;
        c = i3 / i9;
        int i10 = this.s;
        d = i4 / i10;
        e = i5 / i10;
        this.G.lock();
        f = i6;
        g = i7;
        h = i8;
        this.G.unlock();
    }

    public STextureRender(int i2, int i3, int i4, int i5, Context context) {
        this();
        this.r = i2;
        this.s = i3;
        this.t = i4;
        this.u = i5;
        this.C = context;
        Log.i("MiplayQuickSTexture", "mSrcWidth:" + this.r + " mSrcHeight:" + this.s);
        Log.i("MiplayQuickSTexture", "mTextureWidth:" + this.t + " mTextureHeight:" + this.u);
        Context context2 = this.C;
        if (context2 != null) {
            int i6 = context2.getResources().getConfiguration().orientation;
            this.D = i6;
            this.F = i6;
        } else {
            this.D = 1;
            this.F = 1;
        }
        if (this.v) {
            this.x = new float[16];
            float[] fArr = this.x;
            fArr[15] = 1.0f;
            fArr[10] = 1.0f;
            fArr[5] = 1.0f;
            fArr[0] = 1.0f;
            this.z = new float[16];
            float[] fArr2 = this.z;
            fArr2[15] = 1.0f;
            fArr2[10] = 1.0f;
            fArr2[5] = 1.0f;
            fArr2[0] = 1.0f;
            this.B = new float[16];
            float[] fArr3 = this.B;
            fArr3[15] = 1.0f;
            fArr3[10] = 1.0f;
            fArr3[5] = 1.0f;
            fArr3[0] = 1.0f;
        }
        i = 3;
        this.G = new ReentrantLock();
    }

    public STextureRender() {
        this.o = -12345;
        this.v = true;
        this.w = -1;
        this.y = -1;
        this.A = -1;
        this.C = null;
        this.E = 0;
        this.H = 0;
        this.I = 0;
    }

    public void setVideoHandleMode(int i2) {
        i = i2;
    }

    public int getTextureId() {
        return this.o;
    }

    public void surfaceCreated() {
        this.n = GlUtil.createProgram("attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec4 vTextureCoord;\nuniform mat4 RotationMatrix;\nuniform mat4 ScacleMatrix;\nuniform mat4 TransMatrix;\nvoid main() {\n    gl_Position = TransMatrix*RotationMatrix*ScacleMatrix*aPosition;\n    vTextureCoord = aTextureCoord;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nvarying vec4 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord.xy/vTextureCoord.z);\n}\n");
        int i2 = this.n;
        if (i2 != 0) {
            this.p = GLES20.glGetAttribLocation(i2, "aPosition");
            this.q = GLES20.glGetAttribLocation(this.n, "aTextureCoord");
            if (this.v) {
                this.w = GLES20.glGetUniformLocation(this.n, "ScacleMatrix");
                Log.i("MiplayQuickSTexture", "gMScaleLocation:" + this.w);
                if (a || this.w != -1) {
                    this.y = GLES20.glGetUniformLocation(this.n, "RotationMatrix");
                    Log.i("MiplayQuickSTexture", "gMRotateLocation:" + this.y);
                    if (a || this.y != -1) {
                        this.A = GLES20.glGetUniformLocation(this.n, "TransMatrix");
                        Log.i("MiplayQuickSTexture", "gMTransLocation:" + this.A);
                        if (!a && this.A == -1) {
                            throw new AssertionError();
                        }
                    } else {
                        throw new AssertionError();
                    }
                } else {
                    throw new AssertionError();
                }
            }
            this.o = initTex();
            return;
        }
        throw new RuntimeException("failed creating program");
    }

    public static int initTex() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10240, 9729);
        return iArr[0];
    }

    private boolean a(Context context) {
        return (context == null || context.getResources().getConfiguration().orientation == 1) ? false : true;
    }

    private boolean b(Context context) {
        if (context != null) {
            Configuration configuration = context.getResources().getConfiguration();
            if (configuration.orientation == 1 && this.D != configuration.orientation) {
                this.D = configuration.orientation;
                Log.i("MiplayQuickSTexture", "current screen change,ORIENTATION_LANDSCAPE to ORIENTATION_PORTRAIT");
                return true;
            } else if (configuration.orientation == 2 && this.D != configuration.orientation) {
                this.D = configuration.orientation;
                Log.i("MiplayQuickSTexture", "current screen change,ORIENTATION_PORTRAIT to ORIENTATION_LANDSCAPE");
                return true;
            }
        }
        return false;
    }

    private void a(float[] fArr, float f2, float f3, float f4) {
        fArr[0] = f2;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = 0.0f;
        fArr[5] = f3;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = f4;
        fArr[11] = 0.0f;
        fArr[12] = 0.0f;
        fArr[13] = 0.0f;
        fArr[14] = 0.0f;
        fArr[15] = 1.0f;
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x037a  */
    /* JADX WARN: Removed duplicated region for block: B:185:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x012f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawFrame() {
        /*
            Method dump skipped, instructions count: 910
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.miplay.mylibrary.mirror.glec.STextureRender.drawFrame():void");
    }
}
