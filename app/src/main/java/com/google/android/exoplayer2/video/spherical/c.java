package com.google.android.exoplayer2.video.spherical;

import android.opengl.GLES20;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.video.spherical.Projection;
import java.nio.Buffer;
import java.nio.FloatBuffer;

/* compiled from: ProjectionRenderer.java */
/* loaded from: classes2.dex */
final class c {
    private static final String[] a = {"uniform mat4 uMvpMatrix;", "uniform mat3 uTexMatrix;", "attribute vec4 aPosition;", "attribute vec2 aTexCoords;", "varying vec2 vTexCoords;", "void main() {", "  gl_Position = uMvpMatrix * aPosition;", "  vTexCoords = (uTexMatrix * vec3(aTexCoords, 1)).xy;", "}"};
    private static final String[] b = {"#extension GL_OES_EGL_image_external : require", "precision mediump float;", "uniform samplerExternalOES uTexture;", "varying vec2 vTexCoords;", "void main() {", "  gl_FragColor = texture2D(uTexture, vTexCoords);", "}"};
    private static final float[] c = {1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] d = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 0.5f, 1.0f};
    private static final float[] e = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] f = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] g = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.5f, 1.0f, 1.0f};
    private int h;
    @Nullable
    private a i;
    @Nullable
    private a j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;

    public static boolean a(Projection projection) {
        Projection.Mesh mesh = projection.a;
        Projection.Mesh mesh2 = projection.b;
        return mesh.getSubMeshCount() == 1 && mesh.getSubMesh(0).textureId == 0 && mesh2.getSubMeshCount() == 1 && mesh2.getSubMesh(0).textureId == 0;
    }

    public void b(Projection projection) {
        if (a(projection)) {
            this.h = projection.c;
            this.i = new a(projection.a.getSubMesh(0));
            this.j = projection.d ? this.i : new a(projection.b.getSubMesh(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.k = GlUtil.compileProgram(a, b);
        this.l = GLES20.glGetUniformLocation(this.k, "uMvpMatrix");
        this.m = GLES20.glGetUniformLocation(this.k, "uTexMatrix");
        this.n = GLES20.glGetAttribLocation(this.k, "aPosition");
        this.o = GLES20.glGetAttribLocation(this.k, "aTexCoords");
        this.p = GLES20.glGetUniformLocation(this.k, "uTexture");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, float[] fArr, boolean z) {
        float[] fArr2;
        a aVar = z ? this.j : this.i;
        if (aVar != null) {
            GLES20.glUseProgram(this.k);
            GlUtil.checkGlError();
            GLES20.glEnableVertexAttribArray(this.n);
            GLES20.glEnableVertexAttribArray(this.o);
            GlUtil.checkGlError();
            int i2 = this.h;
            if (i2 == 1) {
                fArr2 = z ? e : d;
            } else if (i2 == 2) {
                fArr2 = z ? g : f;
            } else {
                fArr2 = c;
            }
            GLES20.glUniformMatrix3fv(this.m, 1, false, fArr2, 0);
            GLES20.glUniformMatrix4fv(this.l, 1, false, fArr, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
            GLES20.glUniform1i(this.p, 0);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(this.n, 3, 5126, false, 12, (Buffer) aVar.b);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(this.o, 2, 5126, false, 8, (Buffer) aVar.c);
            GlUtil.checkGlError();
            GLES20.glDrawArrays(aVar.d, 0, aVar.a);
            GlUtil.checkGlError();
            GLES20.glDisableVertexAttribArray(this.n);
            GLES20.glDisableVertexAttribArray(this.o);
        }
    }

    /* compiled from: ProjectionRenderer.java */
    /* loaded from: classes2.dex */
    private static class a {
        private final int a;
        private final FloatBuffer b;
        private final FloatBuffer c;
        private final int d;

        public a(Projection.SubMesh subMesh) {
            this.a = subMesh.getVertexCount();
            this.b = GlUtil.createBuffer(subMesh.vertices);
            this.c = GlUtil.createBuffer(subMesh.textureCoords);
            switch (subMesh.mode) {
                case 1:
                    this.d = 5;
                    return;
                case 2:
                    this.d = 6;
                    return;
                default:
                    this.d = 4;
                    return;
            }
        }
    }
}
