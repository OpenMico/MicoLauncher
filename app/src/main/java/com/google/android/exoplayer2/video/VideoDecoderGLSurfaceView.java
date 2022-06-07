package com.google.android.exoplayer2.video;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.concurrent.atomic.AtomicReference;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes2.dex */
public final class VideoDecoderGLSurfaceView extends GLSurfaceView implements VideoDecoderOutputBufferRenderer {
    private final a a;

    @Deprecated
    public VideoDecoderOutputBufferRenderer getVideoDecoderOutputBufferRenderer() {
        return this;
    }

    public VideoDecoderGLSurfaceView(Context context) {
        this(context, null);
    }

    public VideoDecoderGLSurfaceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new a(this);
        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        setRenderer(this.a);
        setRenderMode(0);
    }

    @Override // com.google.android.exoplayer2.video.VideoDecoderOutputBufferRenderer
    public void setOutputBuffer(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
        this.a.a(videoDecoderOutputBuffer);
    }

    /* loaded from: classes2.dex */
    private static final class a implements GLSurfaceView.Renderer {
        private static final float[] a = {1.164f, 1.164f, 1.164f, 0.0f, -0.392f, 2.017f, 1.596f, -0.813f, 0.0f};
        private static final float[] b = {1.164f, 1.164f, 1.164f, 0.0f, -0.213f, 2.112f, 1.793f, -0.533f, 0.0f};
        private static final float[] c = {1.168f, 1.168f, 1.168f, 0.0f, -0.188f, 2.148f, 1.683f, -0.652f, 0.0f};
        private static final String[] d = {"y_tex", "u_tex", "v_tex"};
        private static final FloatBuffer e = GlUtil.createBuffer(new float[]{-1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f, -1.0f});
        private final GLSurfaceView f;
        private final int[] g = new int[3];
        private final int[] h = new int[3];
        private final int[] i = new int[3];
        private final int[] j = new int[3];
        private final AtomicReference<VideoDecoderOutputBuffer> k = new AtomicReference<>();
        private final FloatBuffer[] l = new FloatBuffer[3];
        private int m;
        private int n;
        private VideoDecoderOutputBuffer o;

        public a(GLSurfaceView gLSurfaceView) {
            this.f = gLSurfaceView;
            for (int i = 0; i < 3; i++) {
                int[] iArr = this.i;
                this.j[i] = -1;
                iArr[i] = -1;
            }
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            this.m = GlUtil.compileProgram("varying vec2 interp_tc_y;\nvarying vec2 interp_tc_u;\nvarying vec2 interp_tc_v;\nattribute vec4 in_pos;\nattribute vec2 in_tc_y;\nattribute vec2 in_tc_u;\nattribute vec2 in_tc_v;\nvoid main() {\n  gl_Position = in_pos;\n  interp_tc_y = in_tc_y;\n  interp_tc_u = in_tc_u;\n  interp_tc_v = in_tc_v;\n}\n", "precision mediump float;\nvarying vec2 interp_tc_y;\nvarying vec2 interp_tc_u;\nvarying vec2 interp_tc_v;\nuniform sampler2D y_tex;\nuniform sampler2D u_tex;\nuniform sampler2D v_tex;\nuniform mat3 mColorConversion;\nvoid main() {\n  vec3 yuv;\n  yuv.x = texture2D(y_tex, interp_tc_y).r - 0.0625;\n  yuv.y = texture2D(u_tex, interp_tc_u).r - 0.5;\n  yuv.z = texture2D(v_tex, interp_tc_v).r - 0.5;\n  gl_FragColor = vec4(mColorConversion * yuv, 1.0);\n}\n");
            GLES20.glUseProgram(this.m);
            int glGetAttribLocation = GLES20.glGetAttribLocation(this.m, "in_pos");
            GLES20.glEnableVertexAttribArray(glGetAttribLocation);
            GLES20.glVertexAttribPointer(glGetAttribLocation, 2, 5126, false, 0, (Buffer) e);
            this.h[0] = GLES20.glGetAttribLocation(this.m, "in_tc_y");
            GLES20.glEnableVertexAttribArray(this.h[0]);
            this.h[1] = GLES20.glGetAttribLocation(this.m, "in_tc_u");
            GLES20.glEnableVertexAttribArray(this.h[1]);
            this.h[2] = GLES20.glGetAttribLocation(this.m, "in_tc_v");
            GLES20.glEnableVertexAttribArray(this.h[2]);
            GlUtil.checkGlError();
            this.n = GLES20.glGetUniformLocation(this.m, "mColorConversion");
            GlUtil.checkGlError();
            a();
            GlUtil.checkGlError();
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
            GLES20.glViewport(0, 0, i, i2);
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onDrawFrame(GL10 gl10) {
            VideoDecoderOutputBuffer andSet = this.k.getAndSet(null);
            if (!(andSet == null && this.o == null)) {
                if (andSet != null) {
                    VideoDecoderOutputBuffer videoDecoderOutputBuffer = this.o;
                    if (videoDecoderOutputBuffer != null) {
                        videoDecoderOutputBuffer.release();
                    }
                    this.o = andSet;
                }
                VideoDecoderOutputBuffer videoDecoderOutputBuffer2 = (VideoDecoderOutputBuffer) Assertions.checkNotNull(this.o);
                float[] fArr = b;
                int i = videoDecoderOutputBuffer2.colorspace;
                if (i == 1) {
                    fArr = a;
                } else if (i == 3) {
                    fArr = c;
                }
                GLES20.glUniformMatrix3fv(this.n, 1, false, fArr, 0);
                int[] iArr = (int[]) Assertions.checkNotNull(videoDecoderOutputBuffer2.yuvStrides);
                ByteBuffer[] byteBufferArr = (ByteBuffer[]) Assertions.checkNotNull(videoDecoderOutputBuffer2.yuvPlanes);
                int i2 = 0;
                while (i2 < 3) {
                    int i3 = i2 == 0 ? videoDecoderOutputBuffer2.height : (videoDecoderOutputBuffer2.height + 1) / 2;
                    GLES20.glActiveTexture(33984 + i2);
                    GLES20.glBindTexture(3553, this.g[i2]);
                    GLES20.glPixelStorei(3317, 1);
                    GLES20.glTexImage2D(3553, 0, 6409, iArr[i2], i3, 0, 6409, 5121, byteBufferArr[i2]);
                    i2++;
                }
                int i4 = (r3[0] + 1) / 2;
                int[] iArr2 = {videoDecoderOutputBuffer2.width, i4, i4};
                for (int i5 = 0; i5 < 3; i5++) {
                    if (this.i[i5] != iArr2[i5] || this.j[i5] != iArr[i5]) {
                        Assertions.checkState(iArr[i5] != 0);
                        float f = iArr2[i5] / iArr[i5];
                        this.l[i5] = GlUtil.createBuffer(new float[]{0.0f, 0.0f, 0.0f, 1.0f, f, 0.0f, f, 1.0f});
                        GLES20.glVertexAttribPointer(this.h[i5], 2, 5126, false, 0, (Buffer) this.l[i5]);
                        this.i[i5] = iArr2[i5];
                        this.j[i5] = iArr[i5];
                    }
                }
                GLES20.glClear(16384);
                GLES20.glDrawArrays(5, 0, 4);
                GlUtil.checkGlError();
            }
        }

        public void a(VideoDecoderOutputBuffer videoDecoderOutputBuffer) {
            VideoDecoderOutputBuffer andSet = this.k.getAndSet(videoDecoderOutputBuffer);
            if (andSet != null) {
                andSet.release();
            }
            this.f.requestRender();
        }

        private void a() {
            GLES20.glGenTextures(3, this.g, 0);
            for (int i = 0; i < 3; i++) {
                GLES20.glUniform1i(GLES20.glGetUniformLocation(this.m, d[i]), i);
                GLES20.glActiveTexture(33984 + i);
                GLES20.glBindTexture(3553, this.g[i]);
                GLES20.glTexParameterf(3553, 10241, 9729.0f);
                GLES20.glTexParameterf(3553, 10240, 9729.0f);
                GLES20.glTexParameterf(3553, 10242, 33071.0f);
                GLES20.glTexParameterf(3553, 10243, 33071.0f);
            }
            GlUtil.checkGlError();
        }
    }
}
