package com.xiaomi.smarthome.devicelibrary.bluetooth.channel;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import com.xiaomi.smarthome.devicelibrary.bluetooth.BluetoothContextManager;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Timer;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.ACKPacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.CTRPacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.DataPacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.MNGAckPacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.MNGPacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.Packet;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.SingleACKPacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.channel.packet.SinglePacket;
import com.xiaomi.smarthome.devicelibrary.bluetooth.proxy.ProxyBulk;
import com.xiaomi.smarthome.devicelibrary.bluetooth.proxy.ProxyInterceptor;
import com.xiaomi.smarthome.devicelibrary.bluetooth.proxy.ProxyUtils;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothLog;
import com.xiaomi.smarthome.devicelibrary.bluetooth.utils.BluetoothUtils;
import com.xiaomi.smarthome.devicelibrary.common.util.ByteUtils;
import com.xiaomi.smarthome.devicelibrary.common.util.MessageHandlerThread;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public abstract class Channel implements IChannel, ProxyInterceptor {
    public static final String ACTION_A4_RESULT = "com.miot.action.a4.result";
    public static final boolean DEBUG = false;
    public static final int WANTED_MAX_PACKAGE_NUM = 6;
    private byte[] b;
    private int e;
    private int f;
    private int g;
    private ChannelCallback h;
    private Handler i;
    private ChannelState a = ChannelState.IDLE;
    private List<Short> d = new ArrayList();
    private final IChannelStateHandler l = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.1
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            DataPacket dataPacket = (DataPacket) objArr[0];
            short seq = (short) dataPacket.getSeq();
            if (Channel.this.d.contains(Short.valueOf(seq)) && Channel.this.a(dataPacket)) {
                Channel.this.d.remove(Short.valueOf(seq));
                if (Channel.this.d.size() == 0) {
                    Channel.this.b();
                }
            }
        }
    };
    private final IChannelStateHandler m = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.12
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            DataPacket dataPacket = (DataPacket) objArr[0];
            if (Channel.this.a(dataPacket)) {
                if (dataPacket.getSeq() == Channel.this.f) {
                    Channel.this.b();
                } else {
                    Channel.this.a(6000L, new Timer.TimerCallback("WaitData") { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.12.1
                        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Timer.TimerCallback
                        public void onTimerCallback() {
                            Channel.this.b();
                        }

                        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Timer.TimerCallback
                        public void resetCallback() {
                            Channel.this.k.resetCallback();
                        }
                    });
                }
            }
        }
    };
    private final IChannelStateHandler n = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.16
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            CTRPacket cTRPacket = (CTRPacket) objArr[0];
            Channel.this.f = cTRPacket.getFrameCount();
            ACKPacket aCKPacket = new ACKPacket(1);
            Channel.this.g = cTRPacket.getPackageType();
            Channel.this.setCurrentState(ChannelState.READY);
            Channel.this.a(aCKPacket, new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.16.1
                @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                public void onCallback(int i) {
                    Channel.this.a(false);
                    if (i == 0) {
                        Channel.this.f();
                    } else {
                        Channel.this.resetChannelStatus();
                    }
                }
            });
            Channel.this.setCurrentState(ChannelState.READING);
        }
    };
    private final IChannelStateHandler o = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.17
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            SinglePacket singlePacket = (SinglePacket) objArr[0];
            byte[] data = singlePacket.getData();
            final byte[] a2 = Channel.this.a(ByteBuffer.wrap(data), data.length);
            Channel.this.g = singlePacket.getPackageType();
            SingleACKPacket singleACKPacket = new SingleACKPacket(0);
            Channel.this.setCurrentState(ChannelState.READY);
            Channel.this.a(singleACKPacket, new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.17.1
                @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                public void onCallback(int i) {
                    Channel.this.a(false);
                    Channel.this.resetChannelStatus();
                    if (i == 0) {
                        Channel.this.a(a2);
                    }
                }
            });
        }
    };
    private final IChannelStateHandler p = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.18
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            MNGPacket mNGPacket = (MNGPacket) objArr[0];
            mNGPacket.parse();
            if (mNGPacket.getPackageType() == 0) {
                int dmtu = Channel.this.getDMTU();
                int maxDMTU = mNGPacket.getMaxDMTU();
                mNGPacket.getMaxPackgeNum();
                Channel.this.a(new MNGAckPacket(0, new byte[]{(byte) 6, (byte) Math.min(dmtu, maxDMTU)}), new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.18.1
                    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                    public void onCallback(int i) {
                        Channel.this.setCurrentState(ChannelState.WAIT_MNG_ACK);
                    }
                });
            } else if (mNGPacket.getPackageType() == 1) {
                int dmtu2 = Channel.this.getDMTU();
                byte[] testBytes = mNGPacket.getTestBytes();
                int length = testBytes.length + 2;
                int min = Math.min(length, dmtu2);
                if (min != length || !ByteUtils.isSameElement(testBytes, (byte) min)) {
                    min = 18;
                    Channel.this.updateMinDMTU(18);
                    BluetoothLog.d("channel=> 1 sure dmtu is 18");
                } else {
                    Channel.this.updateMinDMTU(min);
                    BluetoothLog.d("channel=> 0 sure dmtu is " + min);
                }
                byte[] bArr = new byte[min - 2];
                Arrays.fill(bArr, (byte) min);
                Channel.this.a(new MNGAckPacket(1, bArr), new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.18.2
                    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                    public void onCallback(int i) {
                        Channel.this.resetChannelStatus();
                        if (i == 0) {
                            Channel.this.a();
                        }
                    }
                });
            }
        }
    };
    private final IChannelStateHandler q = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.19
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            Channel.this.setCurrentState(ChannelState.WAIT_START_ACK);
            Channel.this.f();
        }
    };
    private final IChannelStateHandler r = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.20
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            Channel.this.setCurrentState(ChannelState.WAIT_MNG_ACK);
            Channel.this.f();
        }
    };
    private final IChannelStateHandler s = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.21
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            Channel.this.setCurrentState(ChannelState.WAIT_SINGLE_ACK);
            Channel.this.f();
        }
    };
    private final Timer.TimerCallback t = new Timer.TimerCallback(getClass().getSimpleName()) { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.22
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Timer.TimerCallback
        public void onTimerCallback() {
            Channel.this.a(false);
            Channel.this.a(-2);
            Channel.this.resetChannelStatus();
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Timer.TimerCallback
        public void resetCallback() {
            Channel.this.k.resetCallback();
        }
    };
    private final IChannelStateHandler u = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.2
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            int status = ((SingleACKPacket) objArr[0]).getStatus();
            if (status == 0) {
                Channel.this.a(0);
                Channel.this.resetChannelStatus();
            } else if (status != 2) {
                Channel.this.a(-1);
                Channel.this.resetChannelStatus();
            } else {
                Channel.this.h();
                Channel.this.a(-3);
                Channel.this.resetChannelStatus();
            }
        }
    };
    private final IChannelStateHandler v = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.3
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            MNGAckPacket mNGAckPacket = (MNGAckPacket) objArr[0];
            mNGAckPacket.parse();
            if (mNGAckPacket.getPackageType() == 0) {
                int dmtu = Channel.this.getDMTU();
                int maxDMTU = mNGAckPacket.getMaxDMTU();
                int maxPackgeNum = mNGAckPacket.getMaxPackgeNum();
                int min = Math.min(dmtu, maxDMTU);
                if (6 == maxPackgeNum) {
                    byte[] bArr = new byte[min - 2];
                    Arrays.fill(bArr, (byte) min);
                    Channel.this.a(new MNGPacket(1, bArr), new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.3.1
                        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                        public void onCallback(int i) {
                            Channel.this.a(false);
                            if (i == 0) {
                                BluetoothLog.i("performWrite TYPE_SEND_TEST_PACKAGE success!");
                                Channel.this.f();
                                return;
                            }
                            BluetoothLog.e("performWrite TYPE_SEND_TEST_PACKAGE failed!");
                            Channel.this.resetChannelStatus();
                        }
                    });
                    BluetoothLog.d("channel=> send test package");
                    Channel.this.setCurrentState(ChannelState.WAIT_MNG_ACK);
                }
            } else if (mNGAckPacket.getPackageType() == 1) {
                byte[] testBytes = mNGAckPacket.getTestBytes();
                int dmtu2 = Channel.this.getDMTU();
                int length = testBytes.length + 2;
                int min2 = Math.min(dmtu2, length);
                BluetoothLog.formatLog("channel=> receive test package ack, byte length =%s, local dmtu = %d", Integer.valueOf(testBytes.length), Integer.valueOf(dmtu2));
                if (length != min2 || !ByteUtils.isSameElement(testBytes, (byte) min2)) {
                    Channel.this.updateMinDMTU(18);
                    Channel.this.a(-1);
                } else {
                    BluetoothLog.formatLog("channel=> receive device test ack package ,all is correct, dmtu = " + dmtu2, new Object[0]);
                    Channel.this.a(0);
                    Channel.this.updateMinDMTU(dmtu2);
                }
                Channel.this.resetChannelStatus();
            }
        }
    };
    private final IChannelStateHandler w = new IChannelStateHandler() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.4
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannelStateHandler
        public void handleState(Object... objArr) {
            Channel.this.a(false);
            ACKPacket aCKPacket = (ACKPacket) objArr[0];
            int status = aCKPacket.getStatus();
            if (status != 5) {
                switch (status) {
                    case 0:
                        Channel.this.a(0);
                        Channel.this.resetChannelStatus();
                        return;
                    case 1:
                        Channel.this.h();
                        Channel.this.setCurrentState(ChannelState.WRITING);
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < Channel.this.f; i++) {
                            arrayList.add(Integer.valueOf(i));
                        }
                        Channel.this.a((List<Integer>) arrayList, false);
                        return;
                    default:
                        Channel.this.a(-1);
                        Channel.this.resetChannelStatus();
                        return;
                }
            } else {
                List<Short> seq = aCKPacket.getSeq();
                if (seq.size() > 0) {
                    ArrayList arrayList2 = new ArrayList();
                    for (Short sh : seq) {
                        arrayList2.add(Integer.valueOf(sh.shortValue() - 1));
                    }
                    Channel.this.a((List<Integer>) arrayList2, true);
                }
            }
        }
    };
    private final ChannelStateBlock[] x = {new ChannelStateBlock(ChannelState.READY, ChannelEvent.SEND_CTR, this.q), new ChannelStateBlock(ChannelState.READY, ChannelEvent.SEND_MNG, this.r), new ChannelStateBlock(ChannelState.READY, ChannelEvent.SEND_SINGLE_CTR, this.s), new ChannelStateBlock(ChannelState.WAIT_START_ACK, ChannelEvent.RECV_ACK, this.w), new ChannelStateBlock(ChannelState.WAIT_MNG_ACK, ChannelEvent.RECV_MNG_ACK, this.v), new ChannelStateBlock(ChannelState.WAIT_SINGLE_ACK, ChannelEvent.RECV_SINGLE_ACK, this.u), new ChannelStateBlock(ChannelState.SYNC, ChannelEvent.RECV_ACK, this.w), new ChannelStateBlock(ChannelState.IDLE, ChannelEvent.RECV_CTR, this.n), new ChannelStateBlock(ChannelState.IDLE, ChannelEvent.RECV_SINGLE_CTR, this.o), new ChannelStateBlock(ChannelState.READING, ChannelEvent.RECV_DATA, this.m), new ChannelStateBlock(ChannelState.SYNC_ACK, ChannelEvent.RECV_DATA, this.l)};
    private final IChannel y = new IChannel() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.13
        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void setCurrentState(int i) {
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void write(byte[] bArr, ChannelCallback channelCallback, boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void writeBatchData(List<byte[]> list, ChannelCallback channelCallback) {
            throw new UnsupportedOperationException();
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void onRead(byte[] bArr) {
            Channel.this.b(bArr);
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void onRecv(byte[] bArr, int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void send(byte[] bArr, int i, ChannelCallback channelCallback) {
            Channel.this.a(0, bArr, i, channelCallback);
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void send(int i, byte[] bArr, int i2, ChannelCallback channelCallback) {
            Channel.this.a(i, bArr, i2, channelCallback);
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
        public void reset() {
            Channel.this.resetChannelStatus();
        }
    };
    private final Handler.Callback z = new Handler.Callback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.14
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                ProxyBulk.safeInvoke(message.obj);
                return false;
            }
            ((ChannelCallback) message.obj).onCallback(message.arg1);
            return false;
        }
    };
    private Timer k = Timer.newInstance();
    private SparseArray<Packet> c = new SparseArray<>();
    private IChannel j = (IChannel) ProxyUtils.getProxy(this.y, this);

    public abstract int getDMTU();

    public abstract int getMaxPackageNum();

    public abstract int getMinDMTU();

    public abstract void updateMinDMTU(int i);

    public abstract boolean useCrc32Verify();

    public Channel() {
        MessageHandlerThread messageHandlerThread = new MessageHandlerThread(getClass().getSimpleName());
        messageHandlerThread.start();
        this.i = new Handler(messageHandlerThread.getLooper(), this.z);
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
    public final void onRead(byte[] bArr) {
        this.j.onRead(bArr);
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
    public void send(int i, byte[] bArr, int i2, ChannelCallback channelCallback) {
        this.j.send(i, bArr, i2, channelCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        BluetoothLog.d(" broadcast A4 result");
        BluetoothUtils.sendBroadcastDelayed(ACTION_A4_RESULT, 100L);
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
    public final void send(byte[] bArr, int i, ChannelCallback channelCallback) {
        this.j.send(bArr, i, channelCallback);
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
    public void reset() {
        this.j.reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Packet packet, ChannelCallback channelCallback) {
        a(packet, channelCallback, false);
    }

    private void a(Packet packet, final ChannelCallback channelCallback, final boolean z) {
        a(false);
        if (channelCallback != null) {
            if (!i()) {
                g();
            }
            final byte[] bytes = packet.toBytes();
            BluetoothContextManager.post(new Runnable() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.5
                @Override // java.lang.Runnable
                public void run() {
                    Channel channel = Channel.this;
                    channel.write(bytes, new b(channelCallback), z);
                }
            });
            return;
        }
        e();
        throw new NullPointerException("callback can't be null");
    }

    private void a(final List<byte[]> list, final ChannelCallback channelCallback) {
        a(false);
        if (channelCallback != null) {
            if (!i()) {
                g();
            }
            BluetoothContextManager.post(new Runnable() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.6
                @Override // java.lang.Runnable
                public void run() {
                    BluetoothLog.d("perform batch write");
                    Channel channel = Channel.this;
                    channel.writeBatchData(list, new b(channelCallback));
                }
            });
            return;
        }
        e();
        throw new NullPointerException("callback can't be null");
    }

    /* loaded from: classes4.dex */
    private class b implements ChannelCallback {
        ChannelCallback a;

        b(ChannelCallback channelCallback) {
            this.a = channelCallback;
        }

        @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
        public void onCallback(int i) {
            if (Channel.this.j()) {
                Channel.this.h();
            }
            Channel.this.i.obtainMessage(1, i, 0, this.a).sendToTarget();
        }
    }

    private void a(int i, int i2, byte[] bArr) {
        Packet packet;
        a(false);
        if (i == 0 || this.f > 1) {
            packet = new CTRPacket(this.f, i2);
            BluetoothLog.formatLog("prepare send CMD, frame count = %d,package type =%d", Integer.valueOf(this.f), Integer.valueOf(i2));
        } else if (i == 4) {
            packet = new MNGPacket(i2, bArr);
        } else {
            packet = i == 2 ? new SinglePacket(i2, bArr) : null;
        }
        if (packet == null) {
            BluetoothLog.e("send start flow packet, opcode =" + i + ",packageType=" + i2 + ",but build flow packet is null");
            return;
        }
        a(packet, new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.7
            @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
            public void onCallback(int i3) {
                Channel.this.a(false);
                if (i3 != 0) {
                    Channel.this.a(-1);
                    Channel.this.resetChannelStatus();
                }
            }
        });
        if (i == 0 || this.f > 1) {
            a(ChannelEvent.SEND_CTR, new Object[0]);
        } else if (i == 2) {
            a(ChannelEvent.SEND_SINGLE_CTR, new Object[0]);
        } else if (i == 4) {
            a(ChannelEvent.SEND_MNG, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        a(false);
        ChannelCallback channelCallback = this.h;
        if (channelCallback != null) {
            channelCallback.onCallback(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(DataPacket dataPacket) {
        a(false);
        if (this.c.get(dataPacket.getSeq()) != null) {
            return false;
        }
        this.c.put(dataPacket.getSeq(), dataPacket);
        this.e += dataPacket.getDataLength();
        h();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        a(false);
        f();
        setCurrentState(ChannelState.SYNC);
        if (!d()) {
            final byte[] c = c();
            if (!ByteUtils.isEmpty(c)) {
                a(new ACKPacket(0), new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.8
                    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                    public void onCallback(int i) {
                        Channel.this.a(false);
                        Channel.this.resetChannelStatus();
                        if (i == 0) {
                            Channel.this.a(c);
                        }
                    }
                });
            } else {
                resetChannelStatus();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(byte[] bArr) {
        BluetoothContextManager.post(new a(bArr, this.g));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a implements Runnable {
        private byte[] b;
        private int c;

        a(byte[] bArr, int i) {
            this.b = bArr;
            this.c = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            Channel.this.onRecv(this.b, this.c);
        }
    }

    private byte[] c() {
        a(false);
        if (this.c.size() == this.f) {
            useCrc32Verify();
            ByteBuffer allocate = ByteBuffer.allocate(this.e);
            for (int i = 1; i <= this.f; i++) {
                ((DataPacket) this.c.get(i)).fillByteBuffer(allocate);
            }
            return a(allocate, this.e);
        }
        e();
        throw new IllegalStateException();
    }

    byte[] a(ByteBuffer byteBuffer, int i) {
        if (!useCrc32Verify()) {
            return byteBuffer.array();
        }
        int i2 = i - 4;
        byte[] bArr = {byteBuffer.get(i2), byteBuffer.get(i - 3), byteBuffer.get(i - 2), byteBuffer.get(i - 1)};
        byte[] bArr2 = new byte[i2];
        System.arraycopy(byteBuffer.array(), 0, bArr2, 0, i2);
        return !a(bArr2, bArr) ? ByteUtils.EMPTY_BYTES : bArr2;
    }

    private boolean d() {
        a(false);
        ArrayList arrayList = new ArrayList();
        int maxPackageNum = getMaxPackageNum();
        for (int i = 1; i <= this.f; i++) {
            if (this.c.get(i) == null) {
                arrayList.add(Short.valueOf((short) i));
            }
            if (arrayList.size() >= maxPackageNum) {
                break;
            }
        }
        if (arrayList.size() <= 0) {
            return false;
        }
        BluetoothLog.d("exit lost seq,start sync packet");
        this.d = arrayList;
        a(new ACKPacket(5, arrayList), new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.9
            @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
            public void onCallback(int i2) {
                Channel.this.a(false);
                if (i2 == 0) {
                    Channel.this.f();
                } else {
                    Channel.this.resetChannelStatus();
                }
            }
        });
        setCurrentState(ChannelState.SYNC_ACK);
        return true;
    }

    public void resetChannelStatus() {
        a(false);
        BluetoothLog.d("resetChannelStatus");
        h();
        setCurrentState(ChannelState.IDLE);
        this.b = null;
        this.f = 0;
        this.h = null;
        this.c.clear();
        this.d.clear();
        this.e = 0;
    }

    private void e() {
        h();
        this.a = ChannelState.IDLE;
        this.b = null;
        this.f = 0;
        this.h = null;
        this.c.clear();
        this.d.clear();
        this.e = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List<Integer> list, final boolean z) {
        a(false);
        int minDMTU = getMinDMTU();
        BluetoothLog.d("sendDataPacket list= " + list.toString());
        ArrayList arrayList = new ArrayList();
        for (Integer num : list) {
            int intValue = num.intValue();
            if (intValue < this.f) {
                int i = intValue * minDMTU;
                int i2 = intValue + 1;
                arrayList.add(new DataPacket(i2, this.b, i, Math.min(this.b.length, i2 * minDMTU)).toBytes());
            }
        }
        if (!arrayList.isEmpty()) {
            a(arrayList, new ChannelCallback() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.10
                @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.ChannelCallback
                public void onCallback(int i3) {
                    if (!z) {
                        if (Channel.this.h == null) {
                            BluetoothLog.d("send Data packet onCallback, channelCallback is null ,return");
                            return;
                        }
                        BluetoothLog.d("receive batch write callback ,start sync");
                        Channel.this.setCurrentState(ChannelState.SYNC);
                        Channel.this.a(18000L);
                    }
                }
            });
        }
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.IChannel
    public void setCurrentState(final int i) {
        try {
            this.i.post(new Runnable() { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.11
                @Override // java.lang.Runnable
                public void run() {
                    Channel.this.setCurrentState(ChannelState.values()[i]);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentState(ChannelState channelState) {
        a(false);
        this.a = channelState;
    }

    private void a(ChannelEvent channelEvent, Object... objArr) {
        a(false);
        ChannelStateBlock[] channelStateBlockArr = this.x;
        for (ChannelStateBlock channelStateBlock : channelStateBlockArr) {
            if (channelStateBlock.state == this.a && channelStateBlock.event == channelEvent) {
                channelStateBlock.handler.handleState(objArr);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        if (Looper.myLooper() != (z ? Looper.getMainLooper() : this.i.getLooper())) {
            e();
            throw new RuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void b(byte[] bArr) {
        char c;
        a(false);
        Packet packet = Packet.getPacket(bArr);
        String name = packet.getName();
        switch (name.hashCode()) {
            case 96393:
                if (name.equals(Packet.ACK)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 98849:
                if (name.equals(Packet.CTR)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3076010:
                if (name.equals("data")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 913950738:
                if (name.equals(Packet.SINGLE_ACK)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 913953194:
                if (name.equals(Packet.SINGLE_CMD)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1200909232:
                if (name.equals(Packet.MNG_ACK)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                a(ChannelEvent.RECV_ACK, packet);
                return;
            case 1:
                a(ChannelEvent.RECV_DATA, packet);
                return;
            case 2:
                a(ChannelEvent.RECV_CTR, packet);
                return;
            case 3:
                a(ChannelEvent.RECV_MNG_ACK, packet);
                return;
            case 4:
                a(ChannelEvent.RECV_SINGLE_ACK, packet);
                return;
            case 5:
                a(ChannelEvent.RECV_SINGLE_CTR, packet);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, byte[] bArr, int i2, ChannelCallback channelCallback) {
        a(false);
        if (this.a != ChannelState.IDLE) {
            BluetoothLog.d("preform send ,but channelState is not idle");
            channelCallback.onCallback(-3);
            return;
        }
        this.g = i2;
        this.a = ChannelState.READY;
        this.h = (ChannelCallback) ProxyUtils.getUIProxy(channelCallback);
        this.e = bArr.length;
        this.f = b(this.e);
        if (useCrc32Verify()) {
            this.b = Arrays.copyOf(bArr, bArr.length + 4);
            System.arraycopy(CRC32.get(bArr), 0, this.b, bArr.length, 4);
        } else {
            this.b = Arrays.copyOf(bArr, bArr.length);
        }
        a(i, i2, this.b);
    }

    @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.proxy.ProxyInterceptor
    public boolean onIntercept(Object obj, Method method, Object[] objArr) {
        this.i.obtainMessage(0, new ProxyBulk(obj, method, objArr)).sendToTarget();
        return true;
    }

    private int b(int i) {
        if (useCrc32Verify()) {
            i += 4;
        }
        return ((i - 1) / getMinDMTU()) + 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        a(6000L);
    }

    private void g() {
        a(6000L, new Timer.TimerCallback("exception") { // from class: com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Channel.15
            @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Timer.TimerCallback
            public void onTimerCallback() throws TimeoutException {
                throw new TimeoutException();
            }

            @Override // com.xiaomi.smarthome.devicelibrary.bluetooth.channel.Timer.TimerCallback
            public void resetCallback() {
                Channel.this.k.resetCallback();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j) {
        a(j, this.t);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j, Timer.TimerCallback timerCallback) {
        this.k.start(timerCallback, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        this.k.stop();
    }

    private boolean i() {
        return this.k.isRunning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean j() {
        return "exception".equals(this.k.getName());
    }

    private boolean a(byte[] bArr, byte[] bArr2) {
        return ByteUtils.equals(bArr2, CRC32.get(bArr));
    }
}
