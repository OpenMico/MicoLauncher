package com.journeyapps.barcodescanner;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.DecodeFormatManager;
import com.google.zxing.client.android.DecodeHintManager;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.camera.CameraParametersCallback;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class DecoratedBarcodeView extends FrameLayout {
    private BarcodeView a;
    private ViewfinderView b;
    private TextView c;
    private TorchListener d;

    /* loaded from: classes2.dex */
    public interface TorchListener {
        void onTorchOff();

        void onTorchOn();
    }

    /* loaded from: classes2.dex */
    private class a implements BarcodeCallback {
        private BarcodeCallback b;

        public a(BarcodeCallback barcodeCallback) {
            this.b = barcodeCallback;
        }

        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void barcodeResult(BarcodeResult barcodeResult) {
            this.b.barcodeResult(barcodeResult);
        }

        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void possibleResultPoints(List<ResultPoint> list) {
            for (ResultPoint resultPoint : list) {
                DecoratedBarcodeView.this.b.addPossibleResultPoint(resultPoint);
            }
            this.b.possibleResultPoints(list);
        }
    }

    public DecoratedBarcodeView(Context context) {
        super(context);
        a();
    }

    public DecoratedBarcodeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public DecoratedBarcodeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(attributeSet);
    }

    private void a(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.zxing_view);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.zxing_view_zxing_scanner_layout, R.layout.zxing_barcode_scanner);
        obtainStyledAttributes.recycle();
        inflate(getContext(), resourceId, this);
        this.a = (BarcodeView) findViewById(R.id.zxing_barcode_surface);
        BarcodeView barcodeView = this.a;
        if (barcodeView != null) {
            barcodeView.initializeAttributes(attributeSet);
            this.b = (ViewfinderView) findViewById(R.id.zxing_viewfinder_view);
            ViewfinderView viewfinderView = this.b;
            if (viewfinderView != null) {
                viewfinderView.setCameraPreview(this.a);
                this.c = (TextView) findViewById(R.id.zxing_status_view);
                return;
            }
            throw new IllegalArgumentException("There is no a com.journeyapps.barcodescanner.ViewfinderView on provided layout with the id \"zxing_viewfinder_view\".");
        }
        throw new IllegalArgumentException("There is no a com.journeyapps.barcodescanner.BarcodeView on provided layout with the id \"zxing_barcode_surface\".");
    }

    private void a() {
        a((AttributeSet) null);
    }

    public void initializeFromIntent(Intent intent) {
        int intExtra;
        Set<BarcodeFormat> parseDecodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
        Map<DecodeHintType, ?> parseDecodeHints = DecodeHintManager.parseDecodeHints(intent);
        CameraSettings cameraSettings = new CameraSettings();
        if (intent.hasExtra(Intents.Scan.CAMERA_ID) && (intExtra = intent.getIntExtra(Intents.Scan.CAMERA_ID, -1)) >= 0) {
            cameraSettings.setRequestedCameraId(intExtra);
        }
        String stringExtra = intent.getStringExtra(Intents.Scan.PROMPT_MESSAGE);
        if (stringExtra != null) {
            setStatusText(stringExtra);
        }
        int intExtra2 = intent.getIntExtra(Intents.Scan.SCAN_TYPE, 0);
        String stringExtra2 = intent.getStringExtra(Intents.Scan.CHARACTER_SET);
        new MultiFormatReader().setHints(parseDecodeHints);
        this.a.setCameraSettings(cameraSettings);
        this.a.setDecoderFactory(new DefaultDecoderFactory(parseDecodeFormats, parseDecodeHints, stringExtra2, intExtra2));
    }

    public void setStatusText(String str) {
        TextView textView = this.c;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void pause() {
        this.a.pause();
    }

    public void pauseAndWait() {
        this.a.pauseAndWait();
    }

    public void resume() {
        this.a.resume();
    }

    public BarcodeView getBarcodeView() {
        return (BarcodeView) findViewById(R.id.zxing_barcode_surface);
    }

    public ViewfinderView getViewFinder() {
        return this.b;
    }

    public TextView getStatusView() {
        return this.c;
    }

    public void decodeSingle(BarcodeCallback barcodeCallback) {
        this.a.decodeSingle(new a(barcodeCallback));
    }

    public void decodeContinuous(BarcodeCallback barcodeCallback) {
        this.a.decodeContinuous(new a(barcodeCallback));
    }

    public void setTorchOn() {
        this.a.setTorch(true);
        TorchListener torchListener = this.d;
        if (torchListener != null) {
            torchListener.onTorchOn();
        }
    }

    public void setTorchOff() {
        this.a.setTorch(false);
        TorchListener torchListener = this.d;
        if (torchListener != null) {
            torchListener.onTorchOff();
        }
    }

    public void changeCameraParameters(CameraParametersCallback cameraParametersCallback) {
        this.a.changeCameraParameters(cameraParametersCallback);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 27 || i == 80) {
            return true;
        }
        switch (i) {
            case 24:
                setTorchOn();
                return true;
            case 25:
                setTorchOff();
                return true;
            default:
                return super.onKeyDown(i, keyEvent);
        }
    }

    public void setTorchListener(TorchListener torchListener) {
        this.d = torchListener;
    }
}
