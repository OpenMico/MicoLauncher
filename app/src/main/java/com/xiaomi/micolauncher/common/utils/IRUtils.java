package com.xiaomi.micolauncher.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.util.Base64;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.utils.HeatShrink.HsInputStream;
import com.xiaomi.mipush.sdk.Constants;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class IRUtils {
    public static final int LOOK_AHEAD_SIZE = 4;
    public static final int WINDOW_SIZE = 11;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(String str, Long l) throws Exception {
        return str;
    }

    @SuppressLint({"CheckResult"})
    public static void sendIR(Context context, final int i, String str, int i2, int i3, boolean z) {
        final ConsumerIrManager consumerIrManager = (ConsumerIrManager) context.getSystemService("consumer_ir");
        if (!consumerIrManager.hasIrEmitter()) {
            L.ir.e("no available IR feature!");
        } else if (!context.getPackageManager().hasSystemFeature("android.hardware.consumerir")) {
            L.ir.e("no available IR feature!");
        } else {
            try {
                String a = a(11, 4, i2, Base64.decode(str, 0));
                if (z) {
                    Observable.zip(Observable.fromArray(a.split("\\|")), Observable.interval(i3, TimeUnit.MILLISECONDS), $$Lambda$IRUtils$a0PCbLr3m3wG0pync_Sdw5XlAKA.INSTANCE).subscribeOn(MicoSchedulers.io()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$IRUtils$2_RPLl60D66xZNDAdsQ8f4KKI9I
                        @Override // io.reactivex.functions.Consumer
                        public final void accept(Object obj) {
                            IRUtils.a(i, consumerIrManager, (String) obj);
                        }
                    });
                } else {
                    a(i, consumerIrManager, a);
                }
            } catch (Exception e) {
                L.ir.e("heat shrink decoded error:", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(int i, ConsumerIrManager consumerIrManager, String str) {
        consumerIrManager.transmit(i, Arrays.stream(str.split(Constants.ACCEPT_TIME_SEPARATOR_SP)).mapToInt($$Lambda$wddj3hVVrg0MkscpMtYt3BzY8Y.INSTANCE).toArray());
    }

    static String a(int i, int i2, int i3, byte[] bArr) throws IOException {
        if (2048 < i3 && i3 <= 4096) {
            i = 12;
        } else if (4096 < i3 && i3 <= 8192) {
            i = 13;
        } else if (8192 < i3 && i3 <= 16384) {
            i = 14;
        } else if (16384 < i3) {
            i = 15;
        }
        Throwable th = null;
        if (i2 > i) {
            return null;
        }
        HsInputStream hsInputStream = new HsInputStream(new ByteArrayInputStream(bArr), i, i2);
        try {
            byte[] bArr2 = new byte[1 << i];
            int read = hsInputStream.read(bArr2);
            Logger logger = L.ir;
            logger.d("heat shrink decoded:" + new String(bArr2, 0, read));
            String str = new String(bArr2, 0, read);
            hsInputStream.close();
            return str;
        } catch (Throwable th2) {
            if (0 != 0) {
                try {
                    hsInputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
            } else {
                hsInputStream.close();
            }
            throw th2;
        }
    }
}
