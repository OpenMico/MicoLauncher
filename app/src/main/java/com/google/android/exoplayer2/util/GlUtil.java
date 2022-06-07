package com.google.android.exoplayer2.util;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* loaded from: classes2.dex */
public final class GlUtil {

    /* loaded from: classes2.dex */
    public static final class Attribute {
        private final int a;
        private final int b;
        @Nullable
        private Buffer c;
        private int d;
        public final String name;

        public Attribute(int i, int i2) {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(i, 35722, iArr, 0);
            byte[] bArr = new byte[iArr[0]];
            int[] iArr2 = new int[1];
            int i3 = iArr[0];
            GLES20.glGetActiveAttrib(i, i2, i3, iArr2, 0, new int[1], 0, new int[1], 0, bArr, 0);
            this.name = new String(bArr, 0, GlUtil.b(bArr));
            this.b = GLES20.glGetAttribLocation(i, this.name);
            this.a = i2;
        }

        public void setBuffer(float[] fArr, int i) {
            this.c = GlUtil.createBuffer(fArr);
            this.d = i;
        }

        public void bind() {
            GLES20.glBindBuffer(34962, 0);
            GLES20.glVertexAttribPointer(this.b, this.d, 5126, false, 0, (Buffer) Assertions.checkNotNull(this.c, "call setBuffer before bind"));
            GLES20.glEnableVertexAttribArray(this.a);
            GlUtil.checkGlError();
        }
    }

    /* loaded from: classes2.dex */
    public static final class Uniform {
        private final int a;
        private final int b;
        private final float[] c = new float[16];
        private int d;
        private int e;
        public final String name;

        public Uniform(int i, int i2) {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(i, 35719, iArr, 0);
            int[] iArr2 = new int[1];
            byte[] bArr = new byte[iArr[0]];
            int i3 = iArr[0];
            GLES20.glGetActiveUniform(i, i2, i3, new int[1], 0, new int[1], 0, iArr2, 0, bArr, 0);
            this.name = new String(bArr, 0, GlUtil.b(bArr));
            this.a = GLES20.glGetUniformLocation(i, this.name);
            this.b = iArr2[0];
        }

        public void setSamplerTexId(int i, int i2) {
            this.d = i;
            this.e = i2;
        }

        public void setFloat(float f) {
            this.c[0] = f;
        }

        public void setFloats(float[] fArr) {
            System.arraycopy(fArr, 0, this.c, 0, fArr.length);
        }

        public void bind() {
            int i = this.b;
            if (i == 5126) {
                GLES20.glUniform1fv(this.a, 1, this.c, 0);
                GlUtil.checkGlError();
            } else if (i == 35676) {
                GLES20.glUniformMatrix4fv(this.a, 1, false, this.c, 0);
                GlUtil.checkGlError();
            } else if (this.d != 0) {
                GLES20.glActiveTexture(this.e + 33984);
                int i2 = this.b;
                if (i2 == 36198) {
                    GLES20.glBindTexture(36197, this.d);
                } else if (i2 == 35678) {
                    GLES20.glBindTexture(3553, this.d);
                } else {
                    StringBuilder sb = new StringBuilder(36);
                    sb.append("unexpected uniform type: ");
                    sb.append(i2);
                    throw new IllegalStateException(sb.toString());
                }
                GLES20.glUniform1i(this.a, this.e);
                GLES20.glTexParameteri(3553, 10240, 9729);
                GLES20.glTexParameteri(3553, 10241, 9729);
                GLES20.glTexParameteri(3553, 10242, 33071);
                GLES20.glTexParameteri(3553, 10243, 33071);
                GlUtil.checkGlError();
            } else {
                throw new IllegalStateException("call setSamplerTexId before bind");
            }
        }
    }

    private GlUtil() {
    }

    public static boolean isProtectedContentExtensionSupported(Context context) {
        String eglQueryString;
        if (Util.SDK_INT < 24) {
            return false;
        }
        if (Util.SDK_INT >= 26 || (!"samsung".equals(Util.MANUFACTURER) && !"XT1650".equals(Util.MODEL))) {
            return (Util.SDK_INT >= 26 || context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) && (eglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373)) != null && eglQueryString.contains("EGL_EXT_protected_content");
        }
        return false;
    }

    public static boolean isSurfacelessContextExtensionSupported() {
        String eglQueryString;
        return Util.SDK_INT >= 17 && (eglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373)) != null && eglQueryString.contains("EGL_KHR_surfaceless_context");
    }

    public static void checkGlError() {
        while (true) {
            int glGetError = GLES20.glGetError();
            if (glGetError != 0) {
                String valueOf = String.valueOf(GLU.gluErrorString(glGetError));
                Log.e("GlUtil", valueOf.length() != 0 ? "glError ".concat(valueOf) : new String("glError "));
            } else {
                return;
            }
        }
    }

    public static int compileProgram(String[] strArr, String[] strArr2) {
        return compileProgram(TextUtils.join("\n", strArr), TextUtils.join("\n", strArr2));
    }

    public static int compileProgram(String str, String str2) {
        int glCreateProgram = GLES20.glCreateProgram();
        checkGlError();
        a(35633, str, glCreateProgram);
        a(35632, str2, glCreateProgram);
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = {0};
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 1) {
            String valueOf = String.valueOf(GLES20.glGetProgramInfoLog(glCreateProgram));
            a(valueOf.length() != 0 ? "Unable to link shader program: \n".concat(valueOf) : new String("Unable to link shader program: \n"));
        }
        checkGlError();
        return glCreateProgram;
    }

    public static Attribute[] getAttributes(int i) {
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(i, 35721, iArr, 0);
        if (iArr[0] == 2) {
            Attribute[] attributeArr = new Attribute[iArr[0]];
            for (int i2 = 0; i2 < iArr[0]; i2++) {
                attributeArr[i2] = new Attribute(i, i2);
            }
            return attributeArr;
        }
        throw new IllegalStateException("expected two attributes");
    }

    public static Uniform[] getUniforms(int i) {
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(i, 35718, iArr, 0);
        Uniform[] uniformArr = new Uniform[iArr[0]];
        for (int i2 = 0; i2 < iArr[0]; i2++) {
            uniformArr[i2] = new Uniform(i, i2);
        }
        return uniformArr;
    }

    public static FloatBuffer createBuffer(float[] fArr) {
        return (FloatBuffer) createBuffer(fArr.length).put(fArr).flip();
    }

    public static FloatBuffer createBuffer(int i) {
        return ByteBuffer.allocateDirect(i * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public static int createExternalTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, IntBuffer.wrap(iArr));
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10240, 9729);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        checkGlError();
        return iArr[0];
    }

    private static void a(int i, String str, int i2) {
        int glCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = {0};
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 1) {
            String glGetShaderInfoLog = GLES20.glGetShaderInfoLog(glCreateShader);
            StringBuilder sb = new StringBuilder(String.valueOf(glGetShaderInfoLog).length() + 10 + String.valueOf(str).length());
            sb.append(glGetShaderInfoLog);
            sb.append(", source: ");
            sb.append(str);
            a(sb.toString());
        }
        GLES20.glAttachShader(i2, glCreateShader);
        GLES20.glDeleteShader(glCreateShader);
        checkGlError();
    }

    private static void a(String str) {
        Log.e("GlUtil", str);
    }

    public static int b(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] == 0) {
                return i;
            }
        }
        return bArr.length;
    }
}
