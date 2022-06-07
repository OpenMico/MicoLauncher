package com.google.zxing.integration.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.CaptureActivity;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class IntentIntegrator {
    public static final String DATA_MATRIX = "DATA_MATRIX";
    public static final String PDF_417 = "PDF_417";
    public static final String QR_CODE = "QR_CODE";
    public static final int REQUEST_CODE = 49374;
    private static final String a = "IntentIntegrator";
    private final Activity b;
    private Fragment c;
    private androidx.fragment.app.Fragment d;
    private Collection<String> f;
    private Class<?> g;
    public static final String UPC_A = "UPC_A";
    public static final String UPC_E = "UPC_E";
    public static final String EAN_8 = "EAN_8";
    public static final String EAN_13 = "EAN_13";
    public static final String RSS_14 = "RSS_14";
    public static final Collection<String> PRODUCT_CODE_TYPES = a(UPC_A, UPC_E, EAN_8, EAN_13, RSS_14);
    public static final String CODE_39 = "CODE_39";
    public static final String CODE_93 = "CODE_93";
    public static final String CODE_128 = "CODE_128";
    public static final String ITF = "ITF";
    public static final String RSS_EXPANDED = "RSS_EXPANDED";
    public static final Collection<String> ONE_D_CODE_TYPES = a(UPC_A, UPC_E, EAN_8, EAN_13, RSS_14, CODE_39, CODE_93, CODE_128, ITF, RSS_14, RSS_EXPANDED);
    public static final Collection<String> ALL_CODE_TYPES = null;
    private final Map<String, Object> e = new HashMap(3);
    private int h = REQUEST_CODE;

    protected Class<?> getDefaultCaptureActivity() {
        return CaptureActivity.class;
    }

    public IntentIntegrator(Activity activity) {
        this.b = activity;
    }

    public Class<?> getCaptureActivity() {
        if (this.g == null) {
            this.g = getDefaultCaptureActivity();
        }
        return this.g;
    }

    public IntentIntegrator setCaptureActivity(Class<?> cls) {
        this.g = cls;
        return this;
    }

    public IntentIntegrator setRequestCode(int i) {
        if (i <= 0 || i > 65535) {
            throw new IllegalArgumentException("requestCode out of range");
        }
        this.h = i;
        return this;
    }

    public static IntentIntegrator forSupportFragment(androidx.fragment.app.Fragment fragment) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(fragment.getActivity());
        intentIntegrator.d = fragment;
        return intentIntegrator;
    }

    @TargetApi(11)
    public static IntentIntegrator forFragment(Fragment fragment) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(fragment.getActivity());
        intentIntegrator.c = fragment;
        return intentIntegrator;
    }

    public Map<String, ?> getMoreExtras() {
        return this.e;
    }

    public final IntentIntegrator addExtra(String str, Object obj) {
        this.e.put(str, obj);
        return this;
    }

    public final IntentIntegrator setPrompt(String str) {
        if (str != null) {
            addExtra(Intents.Scan.PROMPT_MESSAGE, str);
        }
        return this;
    }

    public IntentIntegrator setOrientationLocked(boolean z) {
        addExtra(Intents.Scan.ORIENTATION_LOCKED, Boolean.valueOf(z));
        return this;
    }

    public IntentIntegrator setCameraId(int i) {
        if (i >= 0) {
            addExtra(Intents.Scan.CAMERA_ID, Integer.valueOf(i));
        }
        return this;
    }

    public IntentIntegrator setBeepEnabled(boolean z) {
        addExtra(Intents.Scan.BEEP_ENABLED, Boolean.valueOf(z));
        return this;
    }

    public IntentIntegrator setBarcodeImageEnabled(boolean z) {
        addExtra(Intents.Scan.BARCODE_IMAGE_ENABLED, Boolean.valueOf(z));
        return this;
    }

    public IntentIntegrator setDesiredBarcodeFormats(Collection<String> collection) {
        this.f = collection;
        return this;
    }

    public IntentIntegrator setDesiredBarcodeFormats(String... strArr) {
        this.f = Arrays.asList(strArr);
        return this;
    }

    public final void initiateScan() {
        startActivityForResult(createScanIntent(), this.h);
    }

    public IntentIntegrator setTimeout(long j) {
        addExtra(Intents.Scan.TIMEOUT, Long.valueOf(j));
        return this;
    }

    public Intent createScanIntent() {
        Intent intent = new Intent(this.b, getCaptureActivity());
        intent.setAction(Intents.Scan.ACTION);
        if (this.f != null) {
            StringBuilder sb = new StringBuilder();
            for (String str : this.f) {
                if (sb.length() > 0) {
                    sb.append(StringUtil.COMMA);
                }
                sb.append(str);
            }
            intent.putExtra(Intents.Scan.FORMATS, sb.toString());
        }
        intent.addFlags(67108864);
        intent.addFlags(524288);
        a(intent);
        return intent;
    }

    public final void initiateScan(Collection<String> collection) {
        setDesiredBarcodeFormats(collection);
        initiateScan();
    }

    protected void startActivityForResult(Intent intent, int i) {
        Fragment fragment = this.c;
        if (fragment != null) {
            fragment.startActivityForResult(intent, i);
            return;
        }
        androidx.fragment.app.Fragment fragment2 = this.d;
        if (fragment2 != null) {
            fragment2.startActivityForResult(intent, i);
        } else {
            this.b.startActivityForResult(intent, i);
        }
    }

    protected void startActivity(Intent intent) {
        Fragment fragment = this.c;
        if (fragment != null) {
            fragment.startActivity(intent);
            return;
        }
        androidx.fragment.app.Fragment fragment2 = this.d;
        if (fragment2 != null) {
            fragment2.startActivity(intent);
        } else {
            this.b.startActivity(intent);
        }
    }

    public static IntentResult parseActivityResult(int i, int i2, Intent intent) {
        if (i == 49374) {
            return parseActivityResult(i2, intent);
        }
        return null;
    }

    public static IntentResult parseActivityResult(int i, Intent intent) {
        if (i != -1) {
            return new IntentResult();
        }
        String stringExtra = intent.getStringExtra(Intents.Scan.RESULT);
        String stringExtra2 = intent.getStringExtra(Intents.Scan.RESULT_FORMAT);
        byte[] byteArrayExtra = intent.getByteArrayExtra(Intents.Scan.RESULT_BYTES);
        int intExtra = intent.getIntExtra(Intents.Scan.RESULT_ORIENTATION, Integer.MIN_VALUE);
        return new IntentResult(stringExtra, stringExtra2, byteArrayExtra, intExtra == Integer.MIN_VALUE ? null : Integer.valueOf(intExtra), intent.getStringExtra(Intents.Scan.RESULT_ERROR_CORRECTION_LEVEL), intent.getStringExtra(Intents.Scan.RESULT_BARCODE_IMAGE_PATH));
    }

    private static List<String> a(String... strArr) {
        return Collections.unmodifiableList(Arrays.asList(strArr));
    }

    private void a(Intent intent) {
        for (Map.Entry<String, Object> entry : this.e.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Integer) {
                intent.putExtra(key, (Integer) value);
            } else if (value instanceof Long) {
                intent.putExtra(key, (Long) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean) value);
            } else if (value instanceof Double) {
                intent.putExtra(key, (Double) value);
            } else if (value instanceof Float) {
                intent.putExtra(key, (Float) value);
            } else if (value instanceof Bundle) {
                intent.putExtra(key, (Bundle) value);
            } else {
                intent.putExtra(key, value.toString());
            }
        }
    }
}
