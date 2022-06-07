package com.xiaomi.smarthome.core.server.internal.bluetooth.channel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.text.TextUtils;
import com.mijiasdk.bleserver.util.BluetoothConstants;
import com.xiaomi.smarthome.core.server.bluetooth.IBleChannelReader;
import com.xiaomi.smarthome.core.server.bluetooth.IBleChannelWriter;
import com.xiaomi.smarthome.core.server.bluetooth.IBleResponse;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothLog;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes4.dex */
public abstract class ChannelManager {
    protected static final String ACTION_ONLINE_CHANGED = "action.online.status.changed";
    public static final String EXTRA_MAC = "extra_mac";
    public static final String EXTRA_ONLINE_STATUS = "extra_online_status";
    public static final int MAX_PACKAGE_NUM = 6;
    private static ConcurrentMap<String, Integer> b = new ConcurrentHashMap();
    private static ConcurrentMap<String, Integer> c = new ConcurrentHashMap();
    private static ConcurrentMap<String, Boolean> d = new ConcurrentHashMap();
    private ConcurrentMap<String, BleChannel> a = new ConcurrentHashMap();
    private BroadcastReceiver e = new BroadcastReceiver() { // from class: com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothLog.d("channel manager receive action=" + action);
            if (TextUtils.equals(action, BluetoothConstants.ACTION_CONNECT_STATUS_CHANGED)) {
                String stringExtra = intent.getStringExtra(BluetoothConstants.KEY_DEVICE_ADDRESS);
                if (intent.getIntExtra(BluetoothConstants.KEY_CONNECT_STATUS, 5) == 32) {
                    ChannelManager.d.put(stringExtra, false);
                    ChannelManager.clearDMTU(stringExtra);
                    BluetoothLog.formatLog("channel manager receive device mac=%s, disconnected", stringExtra);
                    ChannelManager.this.notifyDisconnect(stringExtra);
                }
            }
        }
    };

    /* loaded from: classes4.dex */
    public static abstract class BleChannelReader extends IBleChannelReader.Stub {
    }

    protected void notifyDisconnect(String str) {
    }

    public abstract boolean useCrc32Verify();

    public abstract void writeBatchBleData(String str, List<byte[]> list, ChannelCallback channelCallback);

    public abstract void writeBle(String str, byte[] bArr, ChannelCallback channelCallback, boolean z);

    /* JADX INFO: Access modifiers changed from: package-private */
    public ChannelManager() {
        IntentFilter intentFilter = new IntentFilter(BluetoothConstants.ACTION_CONNECT_STATUS_CHANGED);
        intentFilter.addAction(ACTION_ONLINE_CHANGED);
        BluetoothUtils.registerReceiver(this.e, intentFilter);
    }

    public static void clearDMTU(String str) {
        if (!TextUtils.isEmpty(str)) {
            c.remove(str);
            b.remove(str);
        }
    }

    public void setMtu(String str, int i) {
        b.put(str, Integer.valueOf(i));
    }

    public synchronized void unregisterChannelReader(String str, IBleChannelReader iBleChannelReader) {
        BleChannel bleChannel = this.a.get(str);
        if (bleChannel != null) {
            bleChannel.b(iBleChannelReader);
        }
    }

    public synchronized IBleChannelWriter registerChannelReader(String str, IBleChannelReader iBleChannelReader) {
        BleChannel bleChannel;
        BluetoothLog.v(String.format("registerChannelReader mac =%s ", str));
        bleChannel = this.a.get(str);
        if (bleChannel == null) {
            bleChannel = new BleChannel(str, this, useCrc32Verify());
            this.a.put(str, bleChannel);
        }
        bleChannel.a(iBleChannelReader);
        return bleChannel.b;
    }

    public synchronized IBleChannelWriter registerChannelReader(String str, boolean z, IBleChannelReader iBleChannelReader) {
        BleChannel bleChannel;
        BluetoothLog.v(String.format("registerChannelReader mac =%s ", str));
        bleChannel = this.a.get(str);
        if (bleChannel == null) {
            bleChannel = new BleChannel(str, this, useCrc32Verify());
            this.a.put(str, bleChannel);
        }
        bleChannel.a(iBleChannelReader);
        return bleChannel.b;
    }

    public synchronized void setState(String str, int i) {
        BluetoothLog.v(String.format("setState mac =%s state = %d", str, Integer.valueOf(i)));
        BleChannel bleChannel = this.a.get(str);
        if (bleChannel == null) {
            bleChannel = new BleChannel(str, this, useCrc32Verify());
            this.a.put(str, bleChannel);
        }
        bleChannel.setState(i);
    }

    public synchronized IBleChannelWriter getWriter(String str) {
        BleChannel bleChannel = this.a.get(str);
        if (bleChannel == null) {
            return null;
        }
        return bleChannel.b;
    }

    public synchronized BleChannel getChannel(String str) {
        return this.a.get(str);
    }

    /* loaded from: classes4.dex */
    public static class BleChannel extends Channel {
        private String a;
        private ChannelManager d;
        private boolean e;
        private List<IBleChannelReader> c = new ArrayList();
        private IBleChannelWriter b = new IBleChannelWriter.Stub() { // from class: com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager.BleChannel.1
            @Override // com.xiaomi.smarthome.core.server.bluetooth.IBleChannelWriter
            public void write(byte[] bArr, int i, final IBleResponse iBleResponse) throws RemoteException {
                BleChannel.this.send(bArr, i, new ChannelCallback() { // from class: com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager.BleChannel.1.1
                    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                    public void onCallback(int i2) {
                        try {
                            if (iBleResponse != null) {
                                iBleResponse.onResponse(i2, null);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override // com.xiaomi.smarthome.core.server.bluetooth.IBleChannelWriter
            public void writeWithOpCode(int i, byte[] bArr, int i2, final IBleResponse iBleResponse) throws RemoteException {
                BleChannel.this.send(i, bArr, i2, new ChannelCallback() { // from class: com.xiaomi.smarthome.core.server.internal.bluetooth.channel.ChannelManager.BleChannel.1.2
                    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                    public void onCallback(int i3) {
                        try {
                            if (iBleResponse != null) {
                                iBleResponse.onResponse(i3, null);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        BleChannel(String str, ChannelManager channelManager, boolean z) {
            this.a = str;
            this.d = channelManager;
            this.e = z;
        }

        public void setState(int i) {
            setCurrentState(i);
        }

        void a(IBleChannelReader iBleChannelReader) {
            if (iBleChannelReader != null) {
                Iterator<IBleChannelReader> it = this.c.iterator();
                while (it.hasNext()) {
                    IBleChannelReader next = it.next();
                    if (next != null && next.asBinder() != null && next.asBinder().equals(iBleChannelReader.asBinder())) {
                        return;
                    }
                    if (next == null || next.asBinder() == null) {
                        it.remove();
                    }
                }
                this.c.add(iBleChannelReader);
            }
        }

        public int readerSize() {
            return this.c.size();
        }

        void b(IBleChannelReader iBleChannelReader) {
            if (iBleChannelReader != null) {
                this.c.remove(iBleChannelReader);
            }
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void write(byte[] bArr, ChannelCallback channelCallback, boolean z) {
            this.d.writeBle(this.a, bArr, channelCallback, z);
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void writeBatchData(List<byte[]> list, ChannelCallback channelCallback) {
            this.d.writeBatchBleData(this.a, list, channelCallback);
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void onRecv(byte[] bArr, int i) {
            for (IBleChannelReader iBleChannelReader : this.c) {
                if (iBleChannelReader != null) {
                    try {
                        iBleChannelReader.onRead(this.a, bArr, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel
        public boolean useCrc32Verify() {
            return this.e;
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel
        public int getDMTU() {
            ChannelManager channelManager = this.d;
            Integer num = (Integer) ChannelManager.b.get(this.a);
            if (num == null || num.intValue() < 5) {
                return 18;
            }
            return num.intValue() - 5;
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel
        public int getMinDMTU() {
            ChannelManager channelManager = this.d;
            Integer num = (Integer) ChannelManager.c.get(this.a);
            if (num == null || num.intValue() < 5) {
                return 18;
            }
            return num.intValue();
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel
        public void updateMinDMTU(int i) {
            ChannelManager channelManager = this.d;
            ChannelManager.c.put(this.a, Integer.valueOf(i));
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel
        public int getMaxPackageNum() {
            return getMinDMTU() == 18 ? 1 : 6;
        }
    }
}
