package com.xiaomi.micolauncher.common.player.opengl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.uc.crashsdk.export.LogType;
import com.umeng.analytics.pro.ai;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.micolauncher.common.player.color.ColorThief;
import com.xiaomi.micolauncher.common.player.color.MMCQ;
import com.xiaomi.micolauncher.common.player.hardware.AccelerometerListener;
import com.xiaomi.micolauncher.common.player.hardware.CameraListener;
import com.xiaomi.micolauncher.common.player.hardware.GravityListener;
import com.xiaomi.micolauncher.common.player.hardware.GyroscopeListener;
import com.xiaomi.micolauncher.common.player.hardware.LightListener;
import com.xiaomi.micolauncher.common.player.hardware.LinearAccelerationListener;
import com.xiaomi.micolauncher.common.player.hardware.MagneticFieldListener;
import com.xiaomi.micolauncher.common.player.hardware.PressureListener;
import com.xiaomi.micolauncher.common.player.hardware.ProximityListener;
import com.xiaomi.micolauncher.common.player.hardware.RotationVectorListener;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import java.io.ByteArrayOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import okhttp3.internal.connection.RealConnection;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class ShaderRenderer implements GLSurfaceView.Renderer {
    private static final int[] b = {33984, 33985, 33986, 33987, 33988, 33989, 33990, 33991, 33992, 33993, 33994, 33995, 33996, 33997, 33998, 33999, 34000, 34001, 34002, 34003, 34004, 34005, 34006, 34007, 34008, 34009, 34010, 34011, 34012, 34013, 34014, 34015};
    private static final int[] c = {34070, 34074, 34071, 34072, 34073, 34069};
    private static final Pattern d = Pattern.compile(String.format("uniform[ \t]+sampler(2D|Cube|ExternalOES)+[ \t]+(%s);[ \t]*(.*)", "[a-zA-Z0-9_]+"));
    private static final Pattern e = Pattern.compile("^#define[ \\t]+FTIME_PERIOD[ \\t]+([0-9\\.]+)[ \\t]*$", 8);
    private static String f = "precision highp float;\nuniform float iTime;\nuniform float iFade;\nuniform vec2 iResolution;\nuniform vec2 iMouse;\nuniform vec3 iColorMajor;\nuniform vec3 iColorMinor;\nuniform sampler2D iChannel0;\n#define fragCoord\tgl_FragCoord.xy\n#define fragColor\tgl_FragColor\n#define mainImage(X,Y) main()\n#define texture(X,Y,Z) texture2D(X,Y,Z)\n";
    private final Context D;
    private AccelerometerListener F;
    private CameraListener G;
    private GravityListener H;
    private GyroscopeListener I;
    private MagneticFieldListener J;
    private LightListener K;
    private LinearAccelerationListener L;
    private PressureListener M;
    private ProximityListener N;
    private RotationVectorListener O;
    private OnRendererListener P;
    private TextureLoader Q;
    private FadeOutListener R;
    private String S;
    private int U;
    private int W;
    private int X;
    private int Y;
    private int aA;
    private int aB;
    private int aC;
    private int aD;
    private int aE;
    private int aF;
    private int aG;
    private int aH;
    private int aJ;
    private long aK;
    private long aL;
    private long aM;
    private long aN;
    private float aO;
    private float aQ;
    private float aR;
    private float[] aS;
    private float[] aT;
    private int aU;
    private int aV;
    private volatile float aY;
    private volatile float aZ;
    private int aa;
    private int ab;
    private int ac;
    private int ad;
    private int ae;
    private int af;
    private int ag;
    private int ah;
    private int ai;
    private int aj;
    private int ak;
    private int al;
    private int am;
    private int an;
    private int ao;
    private int ap;
    private int aq;
    private int ar;
    private int as;
    private int at;
    private int au;
    private int av;
    private int aw;
    private int ax;
    private int ay;
    private int az;
    private volatile int ba;
    private long bf;
    private long bg;
    private Pattern g = Pattern.compile("#([A-Fa-f0-9]{6})");
    private Pattern h = Pattern.compile("#RANDOM([0-9]+)");
    private final b i = new b();
    private final ArrayList<String> j = new ArrayList<>();
    private final ArrayList<TextureParameters> k = new ArrayList<>();
    private final BackBufferParameters l = new BackBufferParameters();
    private final int[] m = {0, 0};
    private final int[] n = {0, 0};
    private final int[] o = new int[32];
    private final int[] p = new int[32];
    private final int[] q = new int[32];
    private final float[] r = {0.0f, 0.0f};
    private final float[] s = {0.0f, 0.0f};
    private final float[] t = {0.0f, 0.0f};
    private final float[] u = {0.0f, 0.0f};
    private final float[] v = new float[30];
    private final float[] w = {0.0f, 0.0f};
    private final float[] x = {0.0f, 0.0f, 0.0f, 0.0f};
    private final float[] y = new float[9];
    private final float[] z = new float[9];
    private final float[] A = {0.0f, 0.0f, 0.0f};
    private float[] B = {0.1f, 0.2f, 0.3f};
    private float[] C = {0.5f, 0.4f, 0.3f};
    private int T = 2;
    private int V = 0;
    private int Z = 0;
    private int aI = 1;
    private float aP = 1.0f;
    private volatile byte[] aW = new byte[1];
    private volatile long aX = 0;
    private boolean bb = false;
    private boolean bc = false;
    private boolean bd = false;
    private boolean be = false;
    private float bh = 0.0f;
    private float bi = 1.0f;
    private float bj = 1.0f;
    private boolean bk = false;
    int a = 0;
    private final ByteBuffer E = ByteBuffer.allocateDirect(8);

    /* loaded from: classes3.dex */
    public interface FadeOutListener {
        void onFadeOutFinish();
    }

    /* loaded from: classes3.dex */
    public interface OnRendererListener {
        void onFramesPerSecond(int i);

        void onInfoLog(String str);
    }

    /* loaded from: classes3.dex */
    public interface TextureLoader {
        Bitmap getSwatchBitmap();

        Bitmap getTextureBitmap(String str);
    }

    public ShaderRenderer(Context context) {
        this.D = context;
        this.E.put(new byte[]{-1, 1, -1, -1, 1, 1, 1, -1}).position(0);
    }

    public void setVersion(int i) {
        this.T = i;
    }

    public void setFragmentShader(String str, float f2) {
        setQuality(f2);
        b(str);
    }

    private void b(String str) {
        this.aR = c(str);
        b();
        String g = g(f(str));
        this.S = d(f + g);
    }

    public void setQuality(float f2) {
        this.aP = f2;
    }

    public void setOnRendererListener(OnRendererListener onRendererListener) {
        this.P = onRendererListener;
    }

    public void setTextureLoader(TextureLoader textureLoader) {
        this.Q = textureLoader;
    }

    public void fadeOutByTime(long j) {
        boolean z = this.bc;
        this.bd = true;
        this.bc = false;
        this.be = true;
        this.bg = j;
        this.bf = System.currentTimeMillis();
        float f2 = this.bh;
        if (f2 < 0.8f && z) {
            float f3 = (float) j;
            this.bf = ((float) this.bf) - ((1.0f - f2) * f3);
            Log.i("ShaderRenderer", "set fadeOutByTime " + this.bh + StringUtils.SPACE + ((1.0f - this.bh) * f3));
        }
    }

    public void fadeInByTime(long j) {
        this.bc = true;
        this.bd = false;
        this.be = true;
        this.bg = j;
        this.bf = System.currentTimeMillis();
    }

    public void fadeOutByFactor(float f2, FadeOutListener fadeOutListener) {
        this.bd = true;
        this.bc = false;
        this.be = false;
        this.bi = this.bh;
        if (this.bi < 0.1f) {
            this.bi = 0.1f;
        }
        this.bj = f2;
        this.R = fadeOutListener;
        this.a--;
        if (this.a < 0) {
            this.a = 0;
        }
        this.bk = true;
        Log.i("ShaderRenderer", "set fadeOutByFactor " + this.bi + StringUtils.SPACE + this.a);
        if (this.V == 0 || this.Z == 0) {
            this.bk = true;
            if (fadeOutListener != null) {
                fadeOutListener.onFadeOutFinish();
            }
        }
    }

    public void fadeInByFactor(float f2) {
        this.bd = false;
        this.bc = true;
        this.be = false;
        this.bi = 1.0f - this.bh;
        this.bj = f2;
        this.a++;
        Log.i("ShaderRenderer", "set fadeInByFactor " + this.bi + StringUtils.SPACE + this.a);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glDisable(2884);
        GLES20.glDisable(3042);
        GLES20.glDisable(2929);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        if (this.V != 0) {
            this.V = 0;
        }
        if (this.Z != 0) {
            this.Z = 0;
            h();
        }
        String str = this.S;
        if (str != null && str.length() > 0) {
            b();
            j();
            n();
            c();
            d();
            e();
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        long nanoTime = System.nanoTime();
        this.aL = nanoTime;
        this.aK = nanoTime;
        this.aQ = (float) Math.random();
        this.aJ = 0;
        float[] fArr = this.r;
        float f2 = i;
        fArr[0] = f2;
        float f3 = i2;
        fArr[1] = f3;
        this.U = a(this.D);
        float round = Math.round(f2 * this.aP);
        float round2 = Math.round(f3 * this.aP);
        float[] fArr2 = this.s;
        if (!(round == fArr2[0] && round2 == fArr2[1])) {
            h();
        }
        float[] fArr3 = this.s;
        fArr3[0] = round;
        fArr3[1] = round2;
        b();
        l();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        int i;
        RotationVectorListener rotationVectorListener;
        ProximityListener proximityListener;
        PressureListener pressureListener;
        LightListener lightListener;
        MagneticFieldListener magneticFieldListener;
        GyroscopeListener gyroscopeListener;
        float[] fArr;
        float[] fArr2;
        float f2;
        if (this.V == 0 || (i = this.Z) == 0) {
            GLES20.glClear(LogType.UNEXP_RESTART);
            return;
        }
        GLES20.glUseProgram(i);
        GLES20.glVertexAttribPointer(this.aa, 2, 5120, false, 0, (Buffer) this.E);
        long nanoTime = System.nanoTime();
        float f3 = ((float) (nanoTime - this.aK)) / 1.0E9f;
        int i2 = this.ab;
        if (i2 > -1) {
            GLES20.glUniform1f(i2, f3);
        }
        if (this.ac > -1) {
            if (this.bd) {
                if (!this.be) {
                    f2 = this.bi;
                    this.bi = this.bj * f2;
                } else if (System.currentTimeMillis() - this.bf < this.bg) {
                    f2 = 1.0f - (((float) (System.currentTimeMillis() - this.bf)) / ((float) this.bg));
                    if (f2 > 1.0f) {
                        f2 = 1.0f;
                    }
                } else {
                    f2 = 0.0f;
                }
                if (this.bi < 0.03f && this.bk) {
                    this.R.onFadeOutFinish();
                    this.bk = false;
                }
            } else if (!this.bc) {
                f2 = 1.0f;
            } else if (this.be) {
                f2 = System.currentTimeMillis() - this.bf < this.bg ? ((float) (System.currentTimeMillis() - this.bf)) / ((float) this.bg) : 1.0f;
            } else {
                float f4 = this.bi;
                f2 = 1.0f - f4;
                this.bi = f4 * this.bj;
            }
            this.bh = f2;
            GLES20.glUniform1f(this.ac, f2);
        }
        int i3 = this.ad;
        if (i3 > -1) {
            GLES20.glUniform1i(i3, (int) f3);
        }
        int i4 = this.ae;
        if (i4 > -1) {
            GLES20.glUniform1f(i4, f3 - ((int) f3));
        }
        int i5 = this.af;
        if (i5 > -1) {
            GLES20.glUniform1i(i5, this.aJ);
        }
        int i6 = this.ag;
        if (i6 > -1) {
            float f5 = this.aR;
            GLES20.glUniform1f(i6, (((f3 % f5) / f5) * 2.0f) - 1.0f);
        }
        int i7 = this.ah;
        if (i7 > -1) {
            GLES20.glUniform2fv(i7, 1, this.s, 0);
        }
        int i8 = this.ai;
        if (i8 > -1) {
            GLES20.glUniform2fv(i8, 1, this.t, 0);
        }
        int i9 = this.aj;
        if (i9 > -1) {
            GLES20.glUniform2fv(i9, 1, this.u, 0);
        }
        int i10 = this.ak;
        if (i10 > -1) {
            GLES20.glUniform1i(i10, this.aG);
        }
        int i11 = this.al;
        if (i11 > -1) {
            GLES20.glUniform3fv(i11, this.aG, this.v, 0);
        }
        int i12 = this.am;
        if (i12 > -1 && (fArr2 = this.aS) != null) {
            GLES20.glUniform3fv(i12, 1, fArr2, 0);
        }
        int i13 = this.an;
        if (i13 > -1 && (fArr = this.aT) != null) {
            GLES20.glUniform3fv(i13, 1, fArr, 0);
        }
        int i14 = this.ao;
        if (i14 > -1 && (gyroscopeListener = this.I) != null) {
            GLES20.glUniform3fv(i14, 1, gyroscopeListener.rotation, 0);
        }
        int i15 = this.ap;
        if (i15 > -1 && (magneticFieldListener = this.J) != null) {
            GLES20.glUniform3fv(i15, 1, magneticFieldListener.values, 0);
        }
        if ((this.aq > -1 || this.as > -1 || this.at > -1 || this.au > -1) && this.aS != null) {
            g();
        }
        int i16 = this.av;
        if (i16 > -1 && (lightListener = this.K) != null) {
            GLES20.glUniform1f(i16, lightListener.getAmbient());
        }
        int i17 = this.aw;
        if (i17 > -1 && (pressureListener = this.M) != null) {
            GLES20.glUniform1f(i17, pressureListener.getPressure());
        }
        int i18 = this.ax;
        if (i18 > -1 && (proximityListener = this.N) != null) {
            GLES20.glUniform1f(i18, proximityListener.getCentimeters());
        }
        int i19 = this.ar;
        if (i19 > -1 && (rotationVectorListener = this.O) != null) {
            GLES20.glUniform3fv(i19, 1, rotationVectorListener.values, 0);
        }
        int i20 = this.ay;
        if (i20 > -1) {
            GLES20.glUniform2fv(i20, 1, this.w, 0);
        }
        if (this.az > -1) {
            if (nanoTime - this.aM > RealConnection.IDLE_CONNECTION_HEALTHY_NS) {
                this.aO = o();
                this.aM = nanoTime;
            }
            GLES20.glUniform1f(this.az, this.aO);
        }
        if (this.aA > -1) {
            if (nanoTime - this.aN > C.NANOS_PER_SECOND) {
                Calendar instance = Calendar.getInstance();
                this.x[0] = instance.get(1);
                this.x[1] = instance.get(2);
                this.x[2] = instance.get(5);
                this.x[3] = (instance.get(11) * 3600.0f) + (instance.get(12) * 60.0f) + instance.get(13);
                this.aN = nanoTime;
            }
            GLES20.glUniform4fv(this.aA, 1, this.x, 0);
        }
        int i21 = this.aB;
        if (i21 > -1) {
            GLES20.glUniform1f(i21, this.aQ);
        }
        int i22 = this.aU;
        if (i22 > -1) {
            GLES20.glUniform3fv(i22, 1, this.B, 0);
        }
        int i23 = this.aV;
        if (i23 > -1) {
            GLES20.glUniform3fv(i23, 1, this.C, 0);
        }
        if (this.m[0] == 0) {
            float[] fArr3 = this.s;
            a((int) fArr3[0], (int) fArr3[1]);
        }
        float[] fArr4 = this.s;
        GLES20.glViewport(0, 0, (int) fArr4[0], (int) fArr4[1]);
        this.i.a();
        int i24 = this.aC;
        if (i24 > -1) {
            this.i.a(i24, 3553, this.n[this.aI]);
        }
        CameraListener cameraListener = this.G;
        if (cameraListener != null) {
            int i25 = this.aD;
            if (i25 > -1) {
                GLES20.glUniformMatrix2fv(i25, 1, false, cameraListener.getOrientationMatrix());
            }
            int i26 = this.aE;
            if (i26 > -1) {
                GLES20.glUniform2fv(i26, 1, this.G.addent, 0);
            }
            this.G.update();
        }
        for (int i27 = 0; i27 < this.aF; i27++) {
            this.i.a(this.o[i27], this.p[i27], this.q[i27]);
        }
        GLES20.glBindFramebuffer(36160, this.m[this.aH]);
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glBindFramebuffer(36160, 0);
        float[] fArr5 = this.r;
        GLES20.glViewport(0, 0, (int) fArr5[0], (int) fArr5[1]);
        GLES20.glUseProgram(this.V);
        GLES20.glVertexAttribPointer(this.W, 2, 5120, false, 0, (Buffer) this.E);
        GLES20.glUniform2fv(this.X, 1, this.r, 0);
        GLES20.glUniform1i(this.Y, 0);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.n[this.aH]);
        GLES20.glClear(16384);
        GLES20.glDrawArrays(5, 0, 4);
        int i28 = this.aH;
        this.aH = this.aI;
        this.aI = i28;
        if (this.aW == null) {
            this.aW = f();
        }
        if (this.P != null) {
            a(nanoTime);
        }
        this.aJ++;
    }

    public void unregisterListeners() {
        AccelerometerListener accelerometerListener = this.F;
        if (accelerometerListener != null) {
            accelerometerListener.unregister();
            this.aT = null;
            this.aS = null;
        }
        GravityListener gravityListener = this.H;
        if (gravityListener != null) {
            gravityListener.unregister();
            this.aS = null;
        }
        LinearAccelerationListener linearAccelerationListener = this.L;
        if (linearAccelerationListener != null) {
            linearAccelerationListener.unregister();
            this.aT = null;
        }
        GyroscopeListener gyroscopeListener = this.I;
        if (gyroscopeListener != null) {
            gyroscopeListener.unregister();
        }
        MagneticFieldListener magneticFieldListener = this.J;
        if (magneticFieldListener != null) {
            magneticFieldListener.unregister();
        }
        LightListener lightListener = this.K;
        if (lightListener != null) {
            lightListener.unregister();
        }
        PressureListener pressureListener = this.M;
        if (pressureListener != null) {
            pressureListener.unregister();
        }
        ProximityListener proximityListener = this.N;
        if (proximityListener != null) {
            proximityListener.unregister();
        }
        RotationVectorListener rotationVectorListener = this.O;
        if (rotationVectorListener != null) {
            rotationVectorListener.unregister();
        }
        k();
    }

    public void touchAt(MotionEvent motionEvent) {
        float x = motionEvent.getX() * this.aP;
        float y = motionEvent.getY() * this.aP;
        float[] fArr = this.t;
        fArr[0] = x;
        float[] fArr2 = this.s;
        fArr[1] = fArr2[1] - y;
        float[] fArr3 = this.u;
        fArr3[0] = x / fArr2[0];
        fArr3[1] = 1.0f - (y / fArr2[1]);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            this.aG = 0;
            return;
        }
        this.aG = Math.min(motionEvent.getPointerCount(), this.v.length / 3);
        int i = 0;
        for (int i2 = 0; i2 < this.aG; i2++) {
            int i3 = i + 1;
            this.v[i] = motionEvent.getX(i2) * this.aP;
            int i4 = i3 + 1;
            this.v[i3] = this.s[1] - (motionEvent.getY(i2) * this.aP);
            i = i4 + 1;
            this.v[i4] = motionEvent.getTouchMajor(i2);
        }
    }

    public void setOffset(float f2, float f3) {
        float[] fArr = this.w;
        fArr[0] = f2;
        fArr[1] = f3;
    }

    public byte[] getThumbnail() {
        this.aW = null;
        int i = 10;
        while (true) {
            i--;
            if (i <= 0) {
                break;
            }
            try {
                if (this.Z <= 0 || this.aW != null) {
                    break;
                }
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        return this.aW;
    }

    private void b() {
        this.aZ = 0.0f;
        this.aY = 0.0f;
        this.ba = 0;
        this.aX = 0L;
    }

    private void c() {
        String str = (!this.S.contains("#version 300") || this.T != 3) ? "attribute vec2 position;void main() {gl_Position = vec4(position, 0., 1.);}" : "#version 300 es\nin vec2 position;void main() {gl_Position = vec4(position, 0., 1.);}";
        int a2 = a.a("attribute vec2 position;void main() {gl_Position = vec4(position, 0., 1.);}", "#ifdef GL_FRAGMENT_PRECISION_HIGH\nprecision highp float;\n#else\nprecision mediump float;\n#endif\nuniform vec2 resolution;uniform sampler2D frame;void main(void) {gl_FragColor = texture2D(frame,gl_FragCoord.xy / resolution.xy).rgba;}");
        this.V = a2;
        if (a2 != 0) {
            int a3 = a.a(str, this.S);
            this.Z = a3;
            if (a3 != 0) {
                return;
            }
        }
        OnRendererListener onRendererListener = this.P;
        if (onRendererListener != null) {
            onRendererListener.onInfoLog(a.a());
        }
    }

    private void d() {
        this.W = GLES20.glGetAttribLocation(this.V, "position");
        this.X = GLES20.glGetUniformLocation(this.V, "resolution");
        this.Y = GLES20.glGetUniformLocation(this.V, "frame");
        this.aa = GLES20.glGetAttribLocation(this.Z, "position");
        this.ab = GLES20.glGetUniformLocation(this.Z, "iTime");
        this.ac = GLES20.glGetUniformLocation(this.Z, "iFade");
        this.ad = GLES20.glGetUniformLocation(this.Z, "second");
        this.ae = GLES20.glGetUniformLocation(this.Z, "subsecond");
        this.af = GLES20.glGetUniformLocation(this.Z, "frame");
        this.ag = GLES20.glGetUniformLocation(this.Z, "ftime");
        this.ah = GLES20.glGetUniformLocation(this.Z, "iResolution");
        this.ai = GLES20.glGetUniformLocation(this.Z, TrackConstant.DIALOG_ORIGIN_TOUCH);
        this.aj = GLES20.glGetUniformLocation(this.Z, "iMouse");
        this.ak = GLES20.glGetUniformLocation(this.Z, "pointerCount");
        this.al = GLES20.glGetUniformLocation(this.Z, "pointers");
        this.am = GLES20.glGetUniformLocation(this.Z, "gravity");
        this.ao = GLES20.glGetUniformLocation(this.Z, "gyroscope");
        this.an = GLES20.glGetUniformLocation(this.Z, "linear");
        this.ap = GLES20.glGetUniformLocation(this.Z, "magnetic");
        this.aq = GLES20.glGetUniformLocation(this.Z, "rotationMatrix");
        this.ar = GLES20.glGetUniformLocation(this.Z, "rotationVector");
        this.as = GLES20.glGetUniformLocation(this.Z, "orientation");
        this.at = GLES20.glGetUniformLocation(this.Z, "inclinationMatrix");
        this.au = GLES20.glGetUniformLocation(this.Z, "inclination");
        this.av = GLES20.glGetUniformLocation(this.Z, "light");
        this.aw = GLES20.glGetUniformLocation(this.Z, "pressure");
        this.ax = GLES20.glGetUniformLocation(this.Z, "proximity");
        this.ay = GLES20.glGetUniformLocation(this.Z, "offset");
        this.az = GLES20.glGetUniformLocation(this.Z, ai.Z);
        this.aA = GLES20.glGetUniformLocation(this.Z, Common.DATE);
        this.aB = GLES20.glGetUniformLocation(this.Z, "startRandom");
        this.aC = GLES20.glGetUniformLocation(this.Z, "backbuffer");
        this.aD = GLES20.glGetUniformLocation(this.Z, "cameraOrientation");
        this.aE = GLES20.glGetUniformLocation(this.Z, "cameraAddent");
        this.aU = GLES20.glGetUniformLocation(this.Z, "iColorMajor");
        this.aV = GLES20.glGetUniformLocation(this.Z, "iColorMinor");
        int i = this.aF;
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                this.o[i2] = GLES20.glGetUniformLocation(this.Z, this.j.get(i2));
                i = i2;
            } else {
                return;
            }
        }
    }

    private void e() {
        GLES20.glEnableVertexAttribArray(this.W);
        GLES20.glEnableVertexAttribArray(this.aa);
    }

    private byte[] f() {
        float[] fArr = this.r;
        int i = 0;
        int min = (int) Math.min(fArr[0], fArr[1]);
        int i2 = min * min;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        GLES20.glReadPixels(0, 0, min, min, 6408, 5121, IntBuffer.wrap(iArr));
        int i3 = i2;
        while (i < i2) {
            i3 -= min;
            int i4 = i;
            int i5 = i3;
            int i6 = min;
            while (true) {
                i6--;
                if (i6 > 0) {
                    int i7 = iArr[i4];
                    iArr2[i5] = (i7 & (-16711936)) | ((i7 >> 16) & 255) | ((i7 << 16) & 16711680);
                    i4++;
                    i5++;
                }
            }
            i = i4;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap.createScaledBitmap(Bitmap.createBitmap(iArr2, min, min, Bitmap.Config.ARGB_8888), 144, 144, true).compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private void g() {
        boolean z;
        MagneticFieldListener magneticFieldListener;
        if (this.H == null || (magneticFieldListener = this.J) == null || !SensorManager.getRotationMatrix(this.y, this.z, this.aS, magneticFieldListener.filtered)) {
            RotationVectorListener rotationVectorListener = this.O;
            if (rotationVectorListener != null) {
                SensorManager.getRotationMatrixFromVector(this.y, rotationVectorListener.values);
                z = false;
            } else {
                return;
            }
        } else {
            z = true;
        }
        int i = this.U;
        if (i != 0) {
            int i2 = 2;
            int i3 = 129;
            if (i == 270) {
                i2 = 130;
                i3 = 1;
            }
            float[] fArr = this.y;
            SensorManager.remapCoordinateSystem(fArr, i2, i3, fArr);
        }
        int i4 = this.aq;
        if (i4 > -1) {
            GLES20.glUniformMatrix3fv(i4, 1, true, this.y, 0);
        }
        if (this.as > -1) {
            SensorManager.getOrientation(this.y, this.A);
            GLES20.glUniform3fv(this.as, 1, this.A, 0);
        }
        int i5 = this.at;
        if (i5 > -1 && z) {
            GLES20.glUniformMatrix3fv(i5, 1, true, this.z, 0);
        }
        int i6 = this.au;
        if (i6 > -1 && z) {
            GLES20.glUniform1f(i6, SensorManager.getInclination(this.z));
        }
    }

    private void a(long j) {
        long j2 = j - this.aL;
        synchronized (this) {
            this.aY += Math.min(1.0E9f / ((float) j2), 60.0f);
            float f2 = this.aZ + 1.0f;
            this.aZ = f2;
            if (f2 > 65535.0f) {
                this.aY /= this.aZ;
                this.aZ = 1.0f;
            }
        }
        if (j > this.aX) {
            int round = Math.round(this.aY / this.aZ);
            if (round != this.ba) {
                this.P.onFramesPerSecond(round);
                this.ba = round;
            }
            this.aX = 200000000 + j;
        }
        this.aL = j;
    }

    private void h() {
        int[] iArr = this.m;
        if (iArr[0] != 0) {
            GLES20.glDeleteFramebuffers(2, iArr, 0);
            GLES20.glDeleteTextures(2, this.n, 0);
            this.m[0] = 0;
        }
    }

    private void a(int i, int i2) {
        h();
        GLES20.glGenFramebuffers(2, this.m, 0);
        GLES20.glGenTextures(2, this.n, 0);
        a(this.aH, i, i2, this.l);
        a(this.aI, i, i2, this.l);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    private void a(int i, int i2, int i3, BackBufferParameters backBufferParameters) {
        GLES20.glBindTexture(3553, this.n[i]);
        boolean a2 = backBufferParameters.a(i2, i3);
        if (!a2) {
            GLES20.glTexImage2D(3553, 0, 6408, i2, i3, 0, 6408, 5121, null);
        }
        backBufferParameters.a(3553);
        GLES20.glGenerateMipmap(3553);
        GLES20.glBindFramebuffer(36160, this.m[i]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.n[i], 0);
        if (!a2) {
            GLES20.glClear(LogType.UNEXP_RESTART);
        }
    }

    private void i() {
        int i;
        int[] iArr = this.q;
        if (iArr[0] != 1 && (i = this.aF) >= 1) {
            GLES20.glDeleteTextures(i, iArr, 0);
        }
    }

    private void j() {
        TextureLoader textureLoader;
        Bitmap textureBitmap;
        i();
        GLES20.glGenTextures(this.aF, this.q, 0);
        for (int i = 0; i < this.aF; i++) {
            String str = this.j.get(i);
            if (!"cameraBack".equals(str) && !"cameraFront".equals(str) && (textureLoader = this.Q) != null && (textureBitmap = textureLoader.getTextureBitmap(str)) != null) {
                int i2 = this.p[i];
                if (i2 == 3553) {
                    a(this.q[i], textureBitmap, this.k.get(i));
                } else if (i2 == 34067) {
                    b(this.q[i], textureBitmap, this.k.get(i));
                }
            }
        }
    }

    private void a(int i, Bitmap bitmap, TextureParameters textureParameters) {
        GLES20.glBindTexture(3553, i);
        textureParameters.a(3553);
        TextureParameters.a(bitmap);
        GLES20.glGenerateMipmap(3553);
    }

    private void b(int i, Bitmap bitmap, TextureParameters textureParameters) {
        GLES20.glBindTexture(34067, i);
        textureParameters.a(34067);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int ceil = (int) Math.ceil(width / 2.0f);
        int round = Math.round(height / 3.0f);
        int min = Math.min(ceil, round);
        int[] iArr = c;
        int i2 = 0;
        int i3 = 0;
        for (int i4 : iArr) {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, i2, i3, min, min, (Matrix) null, true);
            GLUtils.texImage2D(i4, 0, 6408, createBitmap, 5121, 0);
            createBitmap.recycle();
            int i5 = i2 + ceil;
            if (i5 >= width) {
                i3 += round;
                i2 = 0;
            } else {
                i2 = i5;
            }
        }
        GLES20.glGenerateMipmap(34067);
    }

    private void k() {
        CameraListener cameraListener = this.G;
        if (cameraListener != null) {
            cameraListener.unregister();
            this.G = null;
        }
    }

    private void l() {
        k();
        for (int i = 0; i < this.aF; i++) {
            String str = this.j.get(i);
            if ("cameraBack".equals(str) || "cameraFront".equals(str)) {
                a(str, this.q[i], this.k.get(i));
                return;
            }
        }
    }

    private void a(String str, int i, TextureParameters textureParameters) {
        int findCameraId;
        if (Build.VERSION.SDK_INT >= 15 && (findCameraId = CameraListener.findCameraId(!"cameraBack".equals(str) ? 1 : 0)) >= 0) {
            CameraListener cameraListener = this.G;
            if (cameraListener == null || cameraListener.cameraId != findCameraId) {
                k();
                m();
                a(i, textureParameters);
                float[] fArr = this.s;
                this.G = new CameraListener(i, findCameraId, (int) fArr[0], (int) fArr[1], this.U);
            }
            this.G.register();
        }
    }

    private void m() {
        if (ContextCompat.checkSelfPermission(this.D, "android.permission.CAMERA") != 0) {
            try {
                ActivityCompat.requestPermissions((Activity) this.D, new String[]{"android.permission.CAMERA"}, 1);
            } catch (ClassCastException unused) {
            }
        }
    }

    @TargetApi(15)
    private static void a(int i, TextureParameters textureParameters) {
        if (Build.VERSION.SDK_INT >= 15) {
            GLES20.glBindTexture(36197, i);
            textureParameters.a(36197);
        }
    }

    private static float c(String str) {
        if (str == null) {
            return 3.0f;
        }
        Matcher matcher = e.matcher(str);
        if (!matcher.find() || matcher.groupCount() <= 0) {
            return 3.0f;
        }
        return Float.parseFloat(matcher.group(1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x006f, code lost:
        if (r4.equals("Cube") != false) goto L_0x007d;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0081 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0099 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x009d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x001f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String d(java.lang.String r12) {
        /*
            r11 = this;
            if (r12 != 0) goto L_0x0004
            r12 = 0
            return r12
        L_0x0004:
            java.util.ArrayList<java.lang.String> r0 = r11.j
            r0.clear()
            java.util.ArrayList<com.xiaomi.micolauncher.common.player.opengl.TextureParameters> r0 = r11.k
            r0.clear()
            r0 = 0
            r11.aF = r0
            com.xiaomi.micolauncher.common.player.opengl.BackBufferParameters r1 = r11.l
            r1.a()
            int[] r1 = r11.q
            int r1 = r1.length
            java.util.regex.Pattern r2 = com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer.d
            java.util.regex.Matcher r2 = r2.matcher(r12)
        L_0x001f:
            boolean r3 = r2.find()
            if (r3 == 0) goto L_0x00ba
            int r3 = r11.aF
            if (r3 >= r1) goto L_0x00ba
            r3 = 1
            java.lang.String r4 = r2.group(r3)
            r5 = 2
            java.lang.String r6 = r2.group(r5)
            r7 = 3
            java.lang.String r7 = r2.group(r7)
            if (r4 == 0) goto L_0x001f
            if (r6 != 0) goto L_0x003d
            goto L_0x001f
        L_0x003d:
            java.lang.String r8 = "backbuffer"
            boolean r8 = r8.equals(r6)
            if (r8 == 0) goto L_0x004b
            com.xiaomi.micolauncher.common.player.opengl.BackBufferParameters r3 = r11.l
            r3.a(r7)
            goto L_0x001f
        L_0x004b:
            r8 = -1
            int r9 = r4.hashCode()
            r10 = 1618(0x652, float:2.267E-42)
            if (r9 == r10) goto L_0x0072
            r10 = 2111573(0x203855, float:2.958944E-39)
            if (r9 == r10) goto L_0x0069
            r3 = 514776434(0x1eaedd72, float:1.8514554E-20)
            if (r9 == r3) goto L_0x005f
            goto L_0x007c
        L_0x005f:
            java.lang.String r3 = "ExternalOES"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x007c
            r3 = r5
            goto L_0x007d
        L_0x0069:
            java.lang.String r5 = "Cube"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x007c
            goto L_0x007d
        L_0x0072:
            java.lang.String r3 = "2D"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x007c
            r3 = r0
            goto L_0x007d
        L_0x007c:
            r3 = r8
        L_0x007d:
            switch(r3) {
                case 0: goto L_0x009d;
                case 1: goto L_0x0099;
                case 2: goto L_0x0081;
                default: goto L_0x0080;
            }
        L_0x0080:
            goto L_0x001f
        L_0x0081:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 15
            if (r3 <= r4) goto L_0x001f
            r3 = 36197(0x8d65, float:5.0723E-41)
            java.lang.String r4 = "#extension GL_OES_EGL_image_external : require\n"
            boolean r4 = r12.contains(r4)
            if (r4 != 0) goto L_0x009f
            java.lang.String r4 = "#extension GL_OES_EGL_image_external : require\n"
            java.lang.String r12 = a(r12, r4)
            goto L_0x009f
        L_0x0099:
            r3 = 34067(0x8513, float:4.7738E-41)
            goto L_0x009f
        L_0x009d:
            r3 = 3553(0xde1, float:4.979E-42)
        L_0x009f:
            int[] r4 = r11.p
            int r5 = r11.aF
            int r8 = r5 + 1
            r11.aF = r8
            r4[r5] = r3
            java.util.ArrayList<java.lang.String> r3 = r11.j
            r3.add(r6)
            java.util.ArrayList<com.xiaomi.micolauncher.common.player.opengl.TextureParameters> r3 = r11.k
            com.xiaomi.micolauncher.common.player.opengl.TextureParameters r4 = new com.xiaomi.micolauncher.common.player.opengl.TextureParameters
            r4.<init>(r7)
            r3.add(r4)
            goto L_0x001f
        L_0x00ba:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer.d(java.lang.String):java.lang.String");
    }

    private int e(String str) {
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            int i3 = i << 4;
            char charAt = str.charAt(i2);
            i = i3 + ((charAt < 'a' || charAt > 'f') ? (charAt < 'A' || charAt > 'F') ? charAt - '0' : (charAt - 'A') + 10 : (charAt - 'a') + 10);
        }
        return i;
    }

    private String f(String str) {
        boolean z;
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length() * 2);
        Matcher matcher = this.g.matcher(str);
        while (true) {
            z = false;
            if (!matcher.find()) {
                break;
            }
            int e2 = e(matcher.group(1));
            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(String.format("vec3(%1.3f,%1.3f,%1.3f)", Float.valueOf(((16711680 & e2) >> 16) / 256.0f), Float.valueOf(((65280 & e2) >> 8) / 256.0f), Float.valueOf((e2 & 255) / 256.0f))));
        }
        matcher.appendTail(stringBuffer);
        String stringBuffer2 = stringBuffer.toString();
        if (stringBuffer2.indexOf("iColorMajor") > -1 || stringBuffer2.indexOf("iColorMinor") > -1) {
            z = true;
        }
        this.bb = z;
        Log.i("ShaderRenderer", "replaceColorDefines:" + this.bb);
        return stringBuffer2;
    }

    static float a(String str) {
        return (float) (new Random().nextDouble() * Integer.parseInt(str));
    }

    private String g(String str) {
        if (str == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(str.length() * 2);
        Matcher matcher = this.h.matcher(str);
        while (matcher.find()) {
            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(String.format("%1.3f", Float.valueOf(a(matcher.group(1))))));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private void n() {
        Bitmap swatchBitmap;
        boolean z;
        boolean z2;
        MMCQ.VBox vBox;
        int i;
        if (this.bb && (swatchBitmap = this.Q.getSwatchBitmap()) != null) {
            int width = swatchBitmap.getWidth();
            int height = swatchBitmap.getHeight();
            boolean z3 = false;
            while (true) {
                if (width <= 512 && height <= 512) {
                    break;
                }
                width /= 2;
                height /= 2;
                z3 = true;
            }
            if (!z3 || (swatchBitmap = Bitmap.createScaledBitmap(swatchBitmap, width, height, false)) != null) {
                MMCQ.VBox vBox2 = ColorThief.getColorMap(swatchBitmap, 5).vboxes.get(0);
                MMCQ.CMap colorMap = ColorThief.getColorMap(swatchBitmap, 10);
                Iterator<MMCQ.VBox> it = colorMap.vboxes.iterator();
                while (it.hasNext()) {
                    vBox2 = it.next();
                    int[] avg = vBox2.avg(false);
                    if (avg[0] < 128 || avg[1] < 128 || avg[2] < 128) {
                        if (avg[0] > 128 || avg[1] > 128 || avg[2] > 128) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                if (!z) {
                    Iterator<MMCQ.VBox> it2 = colorMap.vboxes.iterator();
                    while (it2.hasNext()) {
                        vBox2 = it2.next();
                        int[] avg2 = vBox2.avg(false);
                        if (avg2[0] >= 128) {
                            if (avg2[1] >= 128) {
                                if (avg2[2] < 128) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                double d2 = 0.0d;
                int[] avg3 = vBox2.avg(false);
                a aVar = new a((byte) avg3[0], (byte) avg3[1], (byte) avg3[2]);
                Iterator<MMCQ.VBox> it3 = colorMap.vboxes.iterator();
                while (it3.hasNext()) {
                    int[] avg4 = it3.next().avg(false);
                    double a2 = a(aVar, new a((byte) avg4[0], (byte) avg4[1], (byte) avg4[2]));
                    if (a2 > d2) {
                        d2 = a2;
                    }
                }
                int[] avg5 = vBox2.avg(false);
                int a3 = a(avg5);
                Iterator<MMCQ.VBox> it4 = colorMap.vboxes.iterator();
                while (true) {
                    if (!it4.hasNext()) {
                        z2 = false;
                        vBox = vBox2;
                        break;
                    }
                    vBox = it4.next();
                    int[] avg6 = vBox.avg(false);
                    if (avg5[0] + avg6[0] <= 228 || avg5[1] + avg6[1] <= 228 || avg5[2] + avg6[2] <= 228) {
                        if (avg6[0] >= 64 || avg6[1] >= 64 || avg6[2] >= 64) {
                            if ((a(avg6) & a3) == 0) {
                                z2 = true;
                                break;
                            }
                        }
                    }
                }
                if (!z2) {
                    Iterator<MMCQ.VBox> it5 = colorMap.vboxes.iterator();
                    while (it5.hasNext()) {
                        vBox = it5.next();
                        int[] avg7 = vBox.avg(false);
                        if (avg5[0] + avg7[0] <= 228 || avg5[1] + avg7[1] <= 228 || avg5[2] + avg7[2] <= 228) {
                            if (a3 != a(avg7)) {
                                break;
                            }
                        }
                    }
                }
                int[] avg8 = vBox2.avg(false);
                if (avg8[0] > 128 && avg8[1] > 128 && avg8[2] > 128) {
                    avg8[0] = avg8[0] / 2;
                    avg8[1] = avg8[1] / 2;
                    avg8[2] = avg8[2] / 2;
                }
                while (avg8[0] < 64 && avg8[1] < 64 && avg8[2] < 64) {
                    avg8[0] = avg8[0] * 2;
                    avg8[1] = avg8[1] * 2;
                    avg8[2] = avg8[2] * 2;
                    avg8[0] = avg8[0] + 1;
                    avg8[1] = avg8[1] + 1;
                    avg8[2] = avg8[2] + 1;
                }
                int[] avg9 = vBox.avg(false);
                if (avg9[0] > 128 && avg9[1] > 128 && avg9[2] > 128) {
                    avg9[0] = avg9[0] / 2;
                    avg9[1] = avg9[1] / 2;
                    avg9[2] = avg9[2] / 2;
                }
                while (avg9[0] < 64 && avg9[1] < 64 && avg9[2] < 64) {
                    avg9[0] = avg9[0] * 2;
                    avg9[1] = avg9[1] * 2;
                    avg9[2] = avg9[2] * 2;
                    avg9[0] = avg9[0] + 1;
                    avg9[1] = avg9[1] + 1;
                    avg9[2] = avg9[2] + 1;
                }
                while (Math.min(avg8[0] + avg9[0], 250) + Math.min(avg8[1] + avg9[1], (int) Opcodes.IFNULL) + Math.min(avg8[2] + avg9[2], 250) > 504) {
                    avg8[0] = (int) (avg8[0] * 0.9f);
                    avg8[1] = (int) (avg8[1] * 0.85f);
                    avg8[2] = (int) (avg8[2] * 0.9f);
                    avg9[0] = (int) (avg9[0] * 0.9f);
                    avg9[1] = (int) (avg9[1] * 0.85f);
                    avg9[2] = (int) (avg9[2] * 0.9f);
                    Log.i("ShaderRenderer", "punish color");
                }
                int i2 = 0;
                while (i2 < 3) {
                    while (true) {
                        i = i2 + 1;
                        int i3 = i % 3;
                        if (avg8[i2] + avg9[i2] + avg8[i3] + avg9[i3] > 400) {
                            avg8[i2] = (int) (avg8[i2] * 0.9f);
                            avg9[i2] = (int) (avg9[i2] * 0.9f);
                            avg8[i3] = (int) (avg8[i3] * 0.9f);
                            avg9[i3] = (int) (avg9[i3] * 0.9f);
                        }
                    }
                    i2 = i;
                }
                this.B = new float[]{avg8[0] / 256.0f, avg8[1] / 256.0f, avg8[2] / 256.0f};
                Log.i("ShaderRenderer", String.format("majorColor:%d,%d,%d=#%02x%02x%02x", Integer.valueOf(avg8[0]), Integer.valueOf(avg8[1]), Integer.valueOf(avg8[2]), Integer.valueOf(avg8[0]), Integer.valueOf(avg8[1]), Integer.valueOf(avg8[2])));
                this.C = new float[]{avg9[0] / 256.0f, avg9[1] / 256.0f, avg9[2] / 256.0f};
                Log.i("ShaderRenderer", String.format("minorColor:%d,%d,%d=#%02x%02x%02x", Integer.valueOf(avg9[0]), Integer.valueOf(avg9[1]), Integer.valueOf(avg9[2]), Integer.valueOf(avg9[0]), Integer.valueOf(avg9[1]), Integer.valueOf(avg9[2])));
            }
        }
    }

    private int a(int[] iArr) {
        int i = iArr[0] > 128 ? 1 : 0;
        if (iArr[1] > 128) {
            i |= 2;
        }
        if (iArr[2] > 128) {
            i |= 4;
        }
        return i == 0 ? iArr[0] > iArr[1] ? iArr[0] > iArr[2] ? i | 1 : i | 4 : iArr[1] > iArr[2] ? i | 2 : i | 4 : i;
    }

    /* loaded from: classes3.dex */
    public class a {
        byte a;
        byte b;
        byte c;

        a(byte b, byte b2, byte b3) {
            ShaderRenderer.this = r1;
            this.a = b;
            this.b = b2;
            this.c = b3;
        }
    }

    private double a(a aVar, a aVar2) {
        long j = (aVar.a + aVar2.a) / 2;
        long j2 = aVar.a - aVar2.a;
        long j3 = aVar.b - aVar2.b;
        long j4 = aVar.c - aVar2.c;
        return Math.sqrt(((((512 + j) * j2) * j2) >> 8) + (4 * j3 * j3) + ((((767 - j) * j4) * j4) >> 8));
    }

    private static String a(String str, String str2) {
        if (str.trim().startsWith("#version")) {
            int indexOf = str.indexOf("\n");
            if (indexOf < 0) {
                return str;
            }
            int i = indexOf + 1;
            return str.substring(0, i) + str2 + str.substring(i);
        }
        return str2 + str;
    }

    private float o() {
        Intent registerReceiver = this.D.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            return 0.0f;
        }
        return registerReceiver.getIntExtra(com.xiaomi.onetrack.a.a.d, -1) / registerReceiver.getIntExtra("scale", -1);
    }

    private static int a(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return 0;
        }
        switch (windowManager.getDefaultDisplay().getRotation()) {
            case 1:
                return 90;
            case 2:
                return Opcodes.GETFIELD;
            case 3:
                return 270;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class b {
        private int a;

        private b() {
        }

        public void a() {
            this.a = 0;
        }

        public void a(int i, int i2, int i3) {
            if (i >= 0 && this.a < ShaderRenderer.b.length) {
                GLES20.glUniform1i(i, this.a);
                GLES20.glActiveTexture(ShaderRenderer.b[this.a]);
                GLES20.glBindTexture(i2, i3);
                this.a++;
            }
        }
    }
}
