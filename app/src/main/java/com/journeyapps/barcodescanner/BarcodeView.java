package com.journeyapps.barcodescanner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class BarcodeView extends CameraPreview {
    private DecoderThread c;
    private DecoderFactory d;
    private Handler e;
    private a a = a.NONE;
    private BarcodeCallback b = null;
    private final Handler.Callback f = new Handler.Callback() { // from class: com.journeyapps.barcodescanner.BarcodeView.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == R.id.zxing_decode_succeeded) {
                BarcodeResult barcodeResult = (BarcodeResult) message.obj;
                if (!(barcodeResult == null || BarcodeView.this.b == null || BarcodeView.this.a == a.NONE)) {
                    BarcodeView.this.b.barcodeResult(barcodeResult);
                    if (BarcodeView.this.a == a.SINGLE) {
                        BarcodeView.this.stopDecoding();
                    }
                }
                return true;
            } else if (message.what == R.id.zxing_decode_failed) {
                return true;
            } else {
                if (message.what != R.id.zxing_possible_result_points) {
                    return false;
                }
                List<ResultPoint> list = (List) message.obj;
                if (!(BarcodeView.this.b == null || BarcodeView.this.a == a.NONE)) {
                    BarcodeView.this.b.possibleResultPoints(list);
                }
                return true;
            }
        }
    };

    /* loaded from: classes2.dex */
    public enum a {
        NONE,
        SINGLE,
        CONTINUOUS
    }

    public BarcodeView(Context context) {
        super(context);
        b();
    }

    public BarcodeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        b();
    }

    public BarcodeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        b();
    }

    private void b() {
        this.d = new DefaultDecoderFactory();
        this.e = new Handler(this.f);
    }

    public void setDecoderFactory(DecoderFactory decoderFactory) {
        Util.validateMainThread();
        this.d = decoderFactory;
        DecoderThread decoderThread = this.c;
        if (decoderThread != null) {
            decoderThread.setDecoder(c());
        }
    }

    private Decoder c() {
        if (this.d == null) {
            this.d = createDefaultDecoderFactory();
        }
        DecoderResultPointCallback decoderResultPointCallback = new DecoderResultPointCallback();
        HashMap hashMap = new HashMap();
        hashMap.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, decoderResultPointCallback);
        Decoder createDecoder = this.d.createDecoder(hashMap);
        decoderResultPointCallback.setDecoder(createDecoder);
        return createDecoder;
    }

    public DecoderFactory getDecoderFactory() {
        return this.d;
    }

    public void decodeSingle(BarcodeCallback barcodeCallback) {
        this.a = a.SINGLE;
        this.b = barcodeCallback;
        d();
    }

    public void decodeContinuous(BarcodeCallback barcodeCallback) {
        this.a = a.CONTINUOUS;
        this.b = barcodeCallback;
        d();
    }

    public void stopDecoding() {
        this.a = a.NONE;
        this.b = null;
        e();
    }

    protected DecoderFactory createDefaultDecoderFactory() {
        return new DefaultDecoderFactory();
    }

    private void d() {
        e();
        if (this.a != a.NONE && isPreviewActive()) {
            this.c = new DecoderThread(getCameraInstance(), c(), this.e);
            this.c.setCropRect(getPreviewFramingRect());
            this.c.start();
        }
    }

    @Override // com.journeyapps.barcodescanner.CameraPreview
    public void previewStarted() {
        super.previewStarted();
        d();
    }

    private void e() {
        DecoderThread decoderThread = this.c;
        if (decoderThread != null) {
            decoderThread.stop();
            this.c = null;
        }
    }

    @Override // com.journeyapps.barcodescanner.CameraPreview
    public void pause() {
        e();
        super.pause();
    }
}
