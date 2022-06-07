package com.xiaomi.micolauncher.module.setting.utils;

import android.os.Environment;
import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.mico.common.Gsons;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.onetrack.api.b;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/* loaded from: classes3.dex */
public class BluetoothWhiteList {
    private static final String c = Environment.getExternalStorageDirectory().getPath() + "/mico/bluetooth/";
    @SerializedName("isEnable")
    private boolean a;
    @SerializedName("devices")
    private List<DevicesBean> b;

    public boolean isEnable() {
        return this.a;
    }

    public void setEnable(boolean z) {
        this.a = z;
    }

    public List<DevicesBean> getDevices() {
        return this.b;
    }

    public void setDevices(List<DevicesBean> list) {
        this.b = list;
    }

    /* loaded from: classes3.dex */
    public static class DevicesBean {
        @SerializedName(b.B)
        private String a;
        @SerializedName("expiry_date")
        private String b;
        @SerializedName("always_valid")
        private boolean c;

        public String getMac() {
            return this.a;
        }

        public void setMac(String str) {
            this.a = str;
        }

        String a() {
            return this.b;
        }

        void a(String str) {
            this.b = str;
        }

        boolean b() {
            return this.c;
        }

        void a(boolean z) {
            this.c = z;
        }
    }

    public boolean isDeviceChecked(DevicesBean devicesBean) {
        List<DevicesBean> list = this.b;
        if (list == null) {
            L.bluetooth.d("%s isDeviceChecked devices is null, not checked device", "[BluetoothWhiteList]: ");
            return false;
        }
        for (DevicesBean devicesBean2 : list) {
            String mac = devicesBean2.getMac();
            String mac2 = devicesBean.getMac();
            if (!(TextUtils.isEmpty(mac) || TextUtils.isEmpty(mac2) || !mac.equals(mac2))) {
                L.bluetooth.d("%s checked device", "[BluetoothWhiteList]: ");
                return true;
            }
        }
        L.bluetooth.d("%s not checked device", "[BluetoothWhiteList]: ");
        return false;
    }

    public void updateDeviceInfo(DevicesBean devicesBean) {
        L.bluetooth.d("%s updateDeviceInfo", "[BluetoothWhiteList]: ");
        a();
        for (int i = 0; i < this.b.size(); i++) {
            if (this.b.get(i).getMac().equals(devicesBean.getMac())) {
                L.bluetooth.d("%s update device mac: %s, expiry_date: %s, always_valid: %s", "[BluetoothWhiteList]: ", devicesBean.getMac(), devicesBean.a(), Boolean.valueOf(devicesBean.b()));
                this.b.get(i).setMac(devicesBean.getMac());
                this.b.get(i).a(devicesBean.b());
                this.b.get(i).a(devicesBean.a());
                return;
            }
        }
        a();
    }

    private void a() {
        L.bluetooth.d("%s dumpDevicesInfo", "[BluetoothWhiteList]: ");
        for (DevicesBean devicesBean : this.b) {
            L.bluetooth.d("%s device mac: %s, expiry_date: %s, always_valid: %s", "[BluetoothWhiteList]: ", devicesBean.getMac(), devicesBean.a(), Boolean.valueOf(devicesBean.b()));
        }
    }

    public boolean isFileExist() {
        return new File(c + "bluetooth_white_list.log").exists();
    }

    public String readFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(c + "bluetooth_white_list.log"));
            byte[] bArr = new byte[1024];
            StringBuilder sb = new StringBuilder();
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    sb.append(new String(bArr, 0, read));
                } else {
                    fileInputStream.close();
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            L.bluetooth.e("%s Exception: %s", "[BluetoothWhiteList]: ", e);
            return null;
        }
    }

    public void writeToFile(BluetoothWhiteList bluetoothWhiteList) {
        String json = Gsons.getGson().toJson(bluetoothWhiteList);
        File file = new File(c);
        File file2 = new File(c + "bluetooth_white_list.log");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(json.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            L.bluetooth.e("%s Exception: %s", "[BluetoothWhiteList]: ", e);
        }
    }
}
