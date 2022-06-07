package com.xiaomi.micolauncher.common.player.opengl;

import android.opengl.GLES20;
import android.util.Log;
import com.xiaomi.mipush.sdk.Constants;

/* compiled from: Program.java */
/* loaded from: classes3.dex */
class a {
    private static String a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a(String str, String str2) {
        int a2 = a(35633, str);
        int i = 0;
        if (a2 != 0) {
            int a3 = a(35632, str2);
            if (a3 != 0) {
                i = a(a2, a3);
                GLES20.glDeleteShader(a3);
            }
            GLES20.glDeleteShader(a2);
        }
        return i;
    }

    private static int a(int... iArr) {
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram == 0) {
            a = "Cannot create program";
            return 0;
        }
        for (int i : iArr) {
            GLES20.glAttachShader(glCreateProgram, i);
        }
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr2 = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr2, 0);
        if (iArr2[0] == 1) {
            return glCreateProgram;
        }
        a = GLES20.glGetProgramInfoLog(glCreateProgram);
        Log.e("ContentValues", "Could not compile shader " + iArr + Constants.COLON_SEPARATOR + a);
        GLES20.glDeleteProgram(glCreateProgram);
        return 0;
    }

    private static int a(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader == 0) {
            a = "Cannot create shader";
            return 0;
        }
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        a = GLES20.glGetShaderInfoLog(glCreateShader);
        Log.e("ContentValues", "Could not compile shader " + i + Constants.COLON_SEPARATOR + a);
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }
}
