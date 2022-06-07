package com.xiaomi.smarthome.core.server.internal.bluetooth.channel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import com.mijiasdk.bleserver.api.BleServerManager;
import com.mijiasdk.bleserver.service.BleServerImpl;
import com.mijiasdk.bleserver.util.BluetoothConstants;
import com.xiaomi.smarthome.core.server.bluetooth.IBleChannelReader;
import com.xiaomi.smarthome.core.server.bluetooth.IBleChannelWriter;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothLog;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothUtils;
import java.util.List;
import java.util.UUID;

/* loaded from: classes4.dex */
public class StandardAuthChannelManager extends ChannelManager {
    private static final String a = "com.xiaomi.smarthome.core.server.internal.bluetooth.channel.StandardAuthChannelManager";
    private static StandardAuthChannelManager b;

    @Override // com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager
    public boolean useCrc32Verify() {
        return false;
    }

    private StandardAuthChannelManager() {
    }

    public static StandardAuthChannelManager getInstance() {
        if (b == null) {
            synchronized (StandardAuthChannelManager.class) {
                if (b == null) {
                    b = new StandardAuthChannelManager();
                }
            }
        }
        return b;
    }

    @Override // com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager
    public synchronized IBleChannelWriter registerChannelReader(String str, IBleChannelReader iBleChannelReader) {
        return registerChannelReader(str, true, iBleChannelReader);
    }

    public void init() {
        BluetoothLog.d("StandChannelManager init");
        IntentFilter intentFilter = new IntentFilter(BluetoothConstants.ACTION_CHARACTER_CHANGED);
        intentFilter.addAction("action.online.status.changed");
        BluetoothUtils.registerReceiver(new a(this), intentFilter);
    }

    @Override // com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager
    public void writeBle(String str, byte[] bArr, final ChannelCallback channelCallback, boolean z) {
        String str2 = a;
        Log.i(str2, "writeBatchBleData bytes=" + BleServerManager.bytesToHexString(bArr));
        if (bArr == null) {
            channelCallback.onCallback(-1);
        }
        BleServerImpl.getInstance().sendData(str, BluetoothConstants.MISERVICE, BluetoothConstants.CHARACTER_STANDARD_AUTH, bArr, new BleServerImpl.SendDataCallback() { // from class: com.xiaomi.smarthome.core.server.internal.bluetooth.channel.StandardAuthChannelManager.1
            @Override // com.mijiasdk.bleserver.service.BleServerImpl.SendDataCallback
            public void onResult(boolean z2) {
                ChannelCallback channelCallback2 = channelCallback;
                if (channelCallback2 != null) {
                    channelCallback2.onCallback(z2 ? 0 : -1);
                }
            }
        });
    }

    @Override // com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager
    public void writeBatchBleData(String str, List<byte[]> list, ChannelCallback channelCallback) {
        String str2 = a;
        Log.i(str2, "writeBatchBleData: mac=" + str + ", bytes size" + list.size());
        for (int i = 0; i < list.size(); i++) {
            String str3 = a;
            Log.i(str3, "writeBatchBleData bytes[" + i + "]=" + BleServerManager.bytesToHexString(list.get(i)));
        }
        if (list == null) {
            channelCallback.onCallback(-1);
        }
        if (list.size() == 0) {
            channelCallback.onCallback(-1);
        }
        a(str, list, 0, channelCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str, final List<byte[]> list, final int i, final ChannelCallback channelCallback) {
        if (list == null) {
            channelCallback.onCallback(-1);
        }
        final int size = list.size();
        if (size == 0) {
            channelCallback.onCallback(-1);
        }
        if (i == size - 1) {
            channelCallback.onCallback(0);
        }
        BleServerImpl.getInstance().sendData(str, BluetoothConstants.MISERVICE, BluetoothConstants.CHARACTER_STANDARD_AUTH, list.get(i), new BleServerImpl.SendDataCallback() { // from class: com.xiaomi.smarthome.core.server.internal.bluetooth.channel.StandardAuthChannelManager.2
            @Override // com.mijiasdk.bleserver.service.BleServerImpl.SendDataCallback
            public void onResult(boolean z) {
                if (z) {
                    int i2 = i;
                    if (i2 == size - 1) {
                        channelCallback.onCallback(0);
                    } else {
                        StandardAuthChannelManager.this.a(str, list, i2 + 1, channelCallback);
                    }
                } else {
                    channelCallback.onCallback(-1);
                }
            }
        });
    }

    /* loaded from: classes4.dex */
    private static class a extends BroadcastReceiver {
        private ChannelManager a;

        public a(ChannelManager channelManager) {
            this.a = channelManager;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothConstants.ACTION_CHARACTER_CHANGED.equals(action)) {
                intent.getStringExtra(BluetoothConstants.KEY_DEVICE_ADDRESS);
                UUID uuid = (UUID) intent.getSerializableExtra(BluetoothConstants.KEY_SERVICE_UUID);
                UUID uuid2 = (UUID) intent.getSerializableExtra(BluetoothConstants.KEY_CHARACTER_UUID);
                intent.getByteArrayExtra(BluetoothConstants.KEY_CHARACTER_VALUE);
            } else if (TextUtils.equals(action, "action.online.status.changed")) {
                BluetoothLog.formatLog("%s,channel Manager receive mac %s, status %s", "standardAuthManager", intent.getStringExtra(ChannelManager.EXTRA_MAC), String.valueOf(intent.getIntExtra(ChannelManager.EXTRA_ONLINE_STATUS, -1)));
            }
        }
    }

    public static int compareFirmwareVersion4Ota(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return -1;
        }
        String[] split = str.split("[._]");
        String[] split2 = str2.split("[._]");
        int min = Math.min(split.length, split2.length);
        for (int i = 0; i < min; i++) {
            try {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt != parseInt2) {
                    return parseInt - parseInt2;
                }
            } catch (Exception e) {
                BluetoothLog.e(e);
                return 0;
            }
        }
        return 0;
    }
}
