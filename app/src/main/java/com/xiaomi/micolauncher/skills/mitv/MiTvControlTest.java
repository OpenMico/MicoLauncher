package com.xiaomi.micolauncher.skills.mitv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.mitv.MiTVDevices;
import com.xiaomi.passport.StatConstants;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class MiTvControlTest {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile MiTvControlTest a;
    private Context c;
    private Handler e;
    private String f;
    private MiTVDevices b = new MiTVDevices();
    private int d = -1;

    public static MiTvControlTest getInstance() {
        if (a == null) {
            synchronized (MiTvManager.class) {
                if (a == null) {
                    a = new MiTvControlTest();
                }
            }
        }
        return a;
    }

    public void start(Context context) {
        this.c = context;
        this.d = -1;
        L.dlna.i("%s start!", "[MiTvControlTest]: ");
        d();
        HandlerThread handlerThread = new HandlerThread("MiTvManager");
        handlerThread.start();
        this.e = new a(handlerThread.getLooper());
        L.dlna.i("%s 开始电视测试!", "[MiTvControlTest]: ");
        L.dlna.i("%s ping 电视是否在线!", "[MiTvControlTest]: ");
        this.f = b();
        if (!TextUtils.isEmpty(this.f)) {
            a(this.f, 0);
        } else {
            L.dlna.i("%s host is null", "[MiTvControlTest]: ");
        }
    }

    /* loaded from: classes3.dex */
    private final class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == -1) {
                String str = (String) message.obj;
                L.dlna.i("%s currentState: %s", "[MiTvControlTest]: ", MiTvControlTest.this.a(MiTvControlTest.this.a()));
                if (TextUtils.isEmpty(str)) {
                    L.dlna.i("%s pingResult is null", "[MiTvControlTest]: ");
                } else {
                    if (str.equals(MiotMeshDeviceItem.CONNECT_STATE_FAILED)) {
                        ToastUtil.showToast("Ping 电视失败, IP: " + MiTvControlTest.this.f, 50);
                    }
                    MiTvManager.getInstance().queryMiTv("让电视进入电视剧", "query");
                    SystemClock.sleep(6000L);
                    MiTvControlTest miTvControlTest = MiTvControlTest.this;
                    miTvControlTest.f = miTvControlTest.b();
                    if (!TextUtils.isEmpty(MiTvControlTest.this.f)) {
                        MiTvControlTest miTvControlTest2 = MiTvControlTest.this;
                        miTvControlTest2.a(miTvControlTest2.f, 0);
                    } else {
                        L.dlna.i("%s host is null", "[MiTvControlTest]: ");
                    }
                }
            }
            super.handleMessage(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(int i) {
        if (i == -1) {
            return "MITV_STATE_IDLE";
        }
        switch (i) {
            case 1:
                return "MITV_STATE_OPENINIG";
            case 2:
                return "MITV_STATE_OPENED";
            case 3:
                return "MITV_STATE_CLOSING";
            case 4:
                return "MITV_STATE_CLOSED";
            default:
                L.dlna.e("%s not handle this state: %s", "[MiTvControlTest]: ", Integer.valueOf(i));
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b() {
        int c = c();
        if (c < 0) {
            return null;
        }
        String host = this.b.getDevice_list().get(c).getHost();
        if (!TextUtils.isEmpty(host)) {
            return host;
        }
        return null;
    }

    private int c() {
        List<MiTVDevices.DeviceListBean> device_list = this.b.getDevice_list();
        if (device_list == null) {
            return -1;
        }
        for (int i = 0; i < device_list.size(); i++) {
            if (device_list.get(i).getSelected() == 1) {
                L.dlna.i("%s discovery selected device num: %s", "[MiTvControlTest]: ", Integer.valueOf(i));
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final String str, final int i) {
        ThreadUtil.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.skills.mitv.-$$Lambda$MiTvControlTest$RDOExE-MjKD7wTbY2ewhQMR9e2k
            @Override // java.lang.Runnable
            public final void run() {
                MiTvControlTest.this.b(str, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(String str, int i) {
        int i2;
        Runtime runtime = Runtime.getRuntime();
        String str2 = "ping -c 1 -w 5 " + str;
        L.dlna.i("%s command: %s", "[MiTvControlTest]: ", str);
        try {
            try {
                i2 = runtime.exec(str2).waitFor();
            } catch (InterruptedException e) {
                L.dlna.e("%s ipProcess.waitFor failed: ", "[MiTvControlTest]: ", e);
                i2 = -1;
            }
        } catch (IOException e2) {
            L.dlna.e("%s runtime.exec failed: ", "[MiTvControlTest]: ", e2);
            i2 = -1;
        }
        if (i2 == 0) {
            L.dlna.i("%s ping 小米电视成功!", "[MiTvControlTest]: ");
            a(StatConstants.BIND_SUCCESS, -1, i);
        } else if (i2 == 1) {
            L.dlna.i("%s ping 小米电视失败!", "[MiTvControlTest]: ");
            a(MiotMeshDeviceItem.CONNECT_STATE_FAILED, -1, i);
        } else if (i2 == -1) {
            L.dlna.e("%s not ipProcess.waitFor() !", "[MiTvControlTest]: ");
        }
    }

    private void a(Object obj, int i, int i2) {
        L.dlna.i("%s send MiTv Message: %s, delay: %s s", "[MiTvControlTest]: ", Integer.valueOf(i), Integer.valueOf(i2 / 1000));
        Message obtain = Message.obtain();
        obtain.obj = obj;
        obtain.what = i;
        if (i2 == 0) {
            this.e.sendMessage(obtain);
        } else {
            this.e.sendMessageDelayed(obtain, i2);
        }
    }

    private void d() {
        String string = this.c.getSharedPreferences("mitv_devices", 0).getString("dev", null);
        if (!TextUtils.isEmpty(string)) {
            L.dlna.d("%s 找到本地MiTv设备信息", "[MiTvControlTest]: ");
            L.dlna.i("%s dev: %s", "[MiTvControlTest]: ", string);
            this.b = (MiTVDevices) JSONObject.parseObject(string, MiTVDevices.class);
            return;
        }
        L.dlna.d("%s 未找到本地MiTv设备信息", "[MiTvControlTest]: ");
    }
}
