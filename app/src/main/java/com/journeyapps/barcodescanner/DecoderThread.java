package com.journeyapps.barcodescanner;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.PreviewCallback;

/* loaded from: classes2.dex */
public class DecoderThread {
    private static final String a = "DecoderThread";
    private CameraInstance b;
    private HandlerThread c;
    private Handler d;
    private Decoder e;
    private Handler f;
    private Rect g;
    private boolean h = false;
    private final Object i = new Object();
    private final Handler.Callback j = new Handler.Callback() { // from class: com.journeyapps.barcodescanner.DecoderThread.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == R.id.zxing_decode) {
                DecoderThread.this.a((SourceData) message.obj);
                return true;
            } else if (message.what != R.id.zxing_preview_failed) {
                return true;
            } else {
                DecoderThread.this.a();
                return true;
            }
        }
    };
    private final PreviewCallback k = new PreviewCallback() { // from class: com.journeyapps.barcodescanner.DecoderThread.2
        @Override // com.journeyapps.barcodescanner.camera.PreviewCallback
        public void onPreview(SourceData sourceData) {
            synchronized (DecoderThread.this.i) {
                if (DecoderThread.this.h) {
                    DecoderThread.this.d.obtainMessage(R.id.zxing_decode, sourceData).sendToTarget();
                }
            }
        }

        @Override // com.journeyapps.barcodescanner.camera.PreviewCallback
        public void onPreviewError(Exception exc) {
            synchronized (DecoderThread.this.i) {
                if (DecoderThread.this.h) {
                    DecoderThread.this.d.obtainMessage(R.id.zxing_preview_failed).sendToTarget();
                }
            }
        }
    };

    public DecoderThread(CameraInstance cameraInstance, Decoder decoder, Handler handler) {
        Util.validateMainThread();
        this.b = cameraInstance;
        this.e = decoder;
        this.f = handler;
    }

    public Decoder getDecoder() {
        return this.e;
    }

    public void setDecoder(Decoder decoder) {
        this.e = decoder;
    }

    public Rect getCropRect() {
        return this.g;
    }

    public void setCropRect(Rect rect) {
        this.g = rect;
    }

    public void start() {
        Util.validateMainThread();
        this.c = new HandlerThread(a);
        this.c.start();
        this.d = new Handler(this.c.getLooper(), this.j);
        this.h = true;
        a();
    }

    public void stop() {
        Util.validateMainThread();
        synchronized (this.i) {
            this.h = false;
            this.d.removeCallbacksAndMessages(null);
            this.c.quit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.b.requestPreview(this.k);
    }

    protected LuminanceSource createSource(SourceData sourceData) {
        if (this.g == null) {
            return null;
        }
        return sourceData.createSource();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(SourceData sourceData) {
        long currentTimeMillis = System.currentTimeMillis();
        sourceData.setCropRect(this.g);
        LuminanceSource createSource = createSource(sourceData);
        Result decode = createSource != null ? this.e.decode(createSource) : null;
        if (decode != null) {
            long currentTimeMillis2 = System.currentTimeMillis();
            String str = a;
            Log.d(str, "Found barcode in " + (currentTimeMillis2 - currentTimeMillis) + " ms");
            if (this.f != null) {
                Message obtain = Message.obtain(this.f, R.id.zxing_decode_succeeded, new BarcodeResult(decode, sourceData));
                obtain.setData(new Bundle());
                obtain.sendToTarget();
            }
        } else {
            Handler handler = this.f;
            if (handler != null) {
                Message.obtain(handler, R.id.zxing_decode_failed).sendToTarget();
            }
        }
        if (this.f != null) {
            Message.obtain(this.f, R.id.zxing_possible_result_points, this.e.getPossibleResultPoints()).sendToTarget();
        }
        a();
    }
}
