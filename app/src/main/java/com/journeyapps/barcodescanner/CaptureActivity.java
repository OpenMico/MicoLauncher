package com.journeyapps.barcodescanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import com.google.zxing.client.android.R;

/* loaded from: classes2.dex */
public class CaptureActivity extends Activity {
    private CaptureManager a;
    private DecoratedBarcodeView b;

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = initializeContent();
        this.a = new CaptureManager(this, this.b);
        this.a.initializeFromIntent(getIntent(), bundle);
        this.a.decode();
    }

    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.zxing_capture);
        return (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.a.onResume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.a.onPause();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.a.onDestroy();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.a.onSaveInstanceState(bundle);
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        this.a.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.b.onKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent);
    }
}
