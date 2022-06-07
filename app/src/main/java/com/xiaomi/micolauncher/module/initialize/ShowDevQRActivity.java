package com.xiaomi.micolauncher.module.initialize;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mico.base.utils.QRCodeUtil;
import com.xiaomi.mico.base.utils.WiFiUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import java.lang.ref.WeakReference;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

/* loaded from: classes3.dex */
public class ShowDevQRActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_show_dev_qr);
        new a((ImageView) findViewById(R.id.imageView)).execute(a(a()));
    }

    String a(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        int i = 0;
        for (char c : str.toCharArray()) {
            sb.append((char) (c ^ "xiaomi.com".charAt(i % 10)));
            i++;
        }
        return Base64.encodeToString(sb.toString().getBytes(), 2);
    }

    private String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("SN", (Object) Constants.getSn());
            jSONObject.put("MAC", (Object) b());
            jSONObject.put("BTMAC", (Object) BluetoothAdapter.getDefaultAdapter().getAddress());
            jSONObject.put("DID", (Object) (SystemSetting.getMiotDeviceId() + "|" + SystemSetting.getMiotToken()));
        } catch (JSONException e) {
            L.init.e("getDeviceInfoString json put failed", e);
        }
        return jSONObject.toString();
    }

    private static String b() {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (WiFiUtil.WLAN_0.equalsIgnoreCase(networkInterface.getName())) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress != null && hardwareAddress.length == 6) {
                        return String.format("%02X:%02X:%02X:%02X:%02X:%02X", Byte.valueOf(hardwareAddress[0]), Byte.valueOf(hardwareAddress[1]), Byte.valueOf(hardwareAddress[2]), Byte.valueOf(hardwareAddress[3]), Byte.valueOf(hardwareAddress[4]), Byte.valueOf(hardwareAddress[5]));
                    }
                    return null;
                }
            }
        } catch (SocketException e) {
            L.init.i("getWiFiMac", e);
        }
        return null;
    }

    /* loaded from: classes3.dex */
    private static class a extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<ImageView> a;

        public a(ImageView imageView) {
            this.a = new WeakReference<>(imageView);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public Bitmap doInBackground(String... strArr) {
            return QRCodeUtil.createQRCodeBitmap(strArr[0], 400, 400);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(Bitmap bitmap) {
            ImageView imageView;
            if (bitmap != null && (imageView = this.a.get()) != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
