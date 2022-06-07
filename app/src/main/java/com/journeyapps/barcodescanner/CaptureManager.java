package com.journeyapps.barcodescanner;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.CameraPreview;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class CaptureManager {
    private static final String a = "CaptureManager";
    private static int b = 250;
    private Activity c;
    private DecoratedBarcodeView d;
    private InactivityTimer h;
    private BeepManager i;
    private int e = -1;
    private boolean f = false;
    private boolean g = false;
    private boolean k = false;
    private BarcodeCallback l = new BarcodeCallback() { // from class: com.journeyapps.barcodescanner.CaptureManager.1
        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void possibleResultPoints(List<ResultPoint> list) {
        }

        @Override // com.journeyapps.barcodescanner.BarcodeCallback
        public void barcodeResult(final BarcodeResult barcodeResult) {
            CaptureManager.this.d.pause();
            CaptureManager.this.i.playBeepSoundAndVibrate();
            CaptureManager.this.j.post(new Runnable() { // from class: com.journeyapps.barcodescanner.CaptureManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    CaptureManager.this.returnResult(barcodeResult);
                }
            });
        }
    };
    private final CameraPreview.StateListener m = new CameraPreview.StateListener() { // from class: com.journeyapps.barcodescanner.CaptureManager.2
        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewSized() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStarted() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStopped() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraError(Exception exc) {
            CaptureManager.this.displayFrameworkBugMessageAndExit();
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraClosed() {
            if (CaptureManager.this.k) {
                Log.d(CaptureManager.a, "Camera closed; finishing activity");
                CaptureManager.this.c();
            }
        }
    };
    private boolean n = false;
    private Handler j = new Handler();

    public CaptureManager(Activity activity, DecoratedBarcodeView decoratedBarcodeView) {
        this.c = activity;
        this.d = decoratedBarcodeView;
        decoratedBarcodeView.getBarcodeView().addStateListener(this.m);
        this.h = new InactivityTimer(activity, new Runnable() { // from class: com.journeyapps.barcodescanner.CaptureManager.3
            @Override // java.lang.Runnable
            public void run() {
                Log.d(CaptureManager.a, "Finishing due to inactivity");
                CaptureManager.this.c();
            }
        });
        this.i = new BeepManager(activity);
    }

    public void initializeFromIntent(Intent intent, Bundle bundle) {
        this.c.getWindow().addFlags(128);
        if (bundle != null) {
            this.e = bundle.getInt("SAVED_ORIENTATION_LOCK", -1);
        }
        if (intent != null) {
            if (intent.getBooleanExtra(Intents.Scan.ORIENTATION_LOCKED, true)) {
                lockOrientation();
            }
            if (Intents.Scan.ACTION.equals(intent.getAction())) {
                this.d.initializeFromIntent(intent);
            }
            if (!intent.getBooleanExtra(Intents.Scan.BEEP_ENABLED, true)) {
                this.i.setBeepEnabled(false);
            }
            if (intent.hasExtra(Intents.Scan.TIMEOUT)) {
                this.j.postDelayed(new Runnable() { // from class: com.journeyapps.barcodescanner.CaptureManager.4
                    @Override // java.lang.Runnable
                    public void run() {
                        CaptureManager.this.returnResultTimeout();
                    }
                }, intent.getLongExtra(Intents.Scan.TIMEOUT, 0L));
            }
            if (intent.getBooleanExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, false)) {
                this.f = true;
            }
        }
    }

    protected void lockOrientation() {
        if (this.e == -1) {
            int rotation = this.c.getWindowManager().getDefaultDisplay().getRotation();
            int i = this.c.getResources().getConfiguration().orientation;
            int i2 = 0;
            if (i == 2) {
                if (!(rotation == 0 || rotation == 1)) {
                    i2 = 8;
                }
            } else if (i == 1) {
                i2 = (rotation == 0 || rotation == 3) ? 1 : 9;
            }
            this.e = i2;
        }
        this.c.setRequestedOrientation(this.e);
    }

    public void decode() {
        this.d.decodeSingle(this.l);
    }

    public void onResume() {
        if (Build.VERSION.SDK_INT >= 23) {
            b();
        } else {
            this.d.resume();
        }
        this.h.start();
    }

    @TargetApi(23)
    private void b() {
        if (ContextCompat.checkSelfPermission(this.c, "android.permission.CAMERA") == 0) {
            this.d.resume();
        } else if (!this.n) {
            ActivityCompat.requestPermissions(this.c, new String[]{"android.permission.CAMERA"}, b);
            this.n = true;
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != b) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            displayFrameworkBugMessageAndExit();
        } else {
            this.d.resume();
        }
    }

    public void onPause() {
        this.h.cancel();
        this.d.pauseAndWait();
    }

    public void onDestroy() {
        this.g = true;
        this.h.cancel();
        this.j.removeCallbacksAndMessages(null);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("SAVED_ORIENTATION_LOCK", this.e);
    }

    public static Intent resultIntent(BarcodeResult barcodeResult, String str) {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.addFlags(524288);
        intent.putExtra(Intents.Scan.RESULT, barcodeResult.toString());
        intent.putExtra(Intents.Scan.RESULT_FORMAT, barcodeResult.getBarcodeFormat().toString());
        byte[] rawBytes = barcodeResult.getRawBytes();
        if (rawBytes != null && rawBytes.length > 0) {
            intent.putExtra(Intents.Scan.RESULT_BYTES, rawBytes);
        }
        Map<ResultMetadataType, Object> resultMetadata = barcodeResult.getResultMetadata();
        if (resultMetadata != null) {
            if (resultMetadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
                intent.putExtra(Intents.Scan.RESULT_UPC_EAN_EXTENSION, resultMetadata.get(ResultMetadataType.UPC_EAN_EXTENSION).toString());
            }
            Number number = (Number) resultMetadata.get(ResultMetadataType.ORIENTATION);
            if (number != null) {
                intent.putExtra(Intents.Scan.RESULT_ORIENTATION, number.intValue());
            }
            String str2 = (String) resultMetadata.get(ResultMetadataType.ERROR_CORRECTION_LEVEL);
            if (str2 != null) {
                intent.putExtra(Intents.Scan.RESULT_ERROR_CORRECTION_LEVEL, str2);
            }
            Iterable<byte[]> iterable = (Iterable) resultMetadata.get(ResultMetadataType.BYTE_SEGMENTS);
            if (iterable != null) {
                int i = 0;
                for (byte[] bArr : iterable) {
                    intent.putExtra(Intents.Scan.RESULT_BYTE_SEGMENTS_PREFIX + i, bArr);
                    i++;
                }
            }
        }
        if (str != null) {
            intent.putExtra(Intents.Scan.RESULT_BARCODE_IMAGE_PATH, str);
        }
        return intent;
    }

    private String a(BarcodeResult barcodeResult) {
        if (this.f) {
            Bitmap bitmap = barcodeResult.getBitmap();
            try {
                File createTempFile = File.createTempFile("barcodeimage", ".jpg", this.c.getCacheDir());
                FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
                return createTempFile.getAbsolutePath();
            } catch (IOException e) {
                String str = a;
                Log.w(str, "Unable to create temporary file and store bitmap! " + e);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        this.c.finish();
    }

    protected void closeAndFinish() {
        if (this.d.getBarcodeView().isCameraClosed()) {
            c();
        } else {
            this.k = true;
        }
        this.d.pause();
        this.h.cancel();
    }

    protected void returnResultTimeout() {
        Intent intent = new Intent(Intents.Scan.ACTION);
        intent.putExtra(Intents.Scan.TIMEOUT, true);
        this.c.setResult(0, intent);
        closeAndFinish();
    }

    protected void returnResult(BarcodeResult barcodeResult) {
        this.c.setResult(-1, resultIntent(barcodeResult, a(barcodeResult)));
        closeAndFinish();
    }

    protected void displayFrameworkBugMessageAndExit() {
        if (!this.c.isFinishing() && !this.g && !this.k) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.c);
            builder.setTitle(this.c.getString(R.string.zxing_app_name));
            builder.setMessage(this.c.getString(R.string.zxing_msg_camera_framework_bug));
            builder.setPositiveButton(R.string.zxing_button_ok, new DialogInterface.OnClickListener() { // from class: com.journeyapps.barcodescanner.CaptureManager.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    CaptureManager.this.c();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.journeyapps.barcodescanner.CaptureManager.6
                @Override // android.content.DialogInterface.OnCancelListener
                public void onCancel(DialogInterface dialogInterface) {
                    CaptureManager.this.c();
                }
            });
            builder.show();
        }
    }

    public static int getCameraPermissionReqCode() {
        return b;
    }

    public static void setCameraPermissionReqCode(int i) {
        b = i;
    }
}
