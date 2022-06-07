package com.xiaomi.micolauncher.common.schema;

import android.app.Service;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.IBinder;
import com.xiaomi.micolauncher.common.L;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class BatteryPrintService extends Service {
    public static final String KEY_STOP = "stop";
    private Observable a;
    private boolean b;
    private BatteryManager c;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.c = (BatteryManager) getSystemService("batterymanager");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        this.b = intent.getBooleanExtra("stop", false);
        if (this.a == null) {
            this.a = Observable.interval(10L, TimeUnit.SECONDS);
            this.a.takeWhile(new Predicate() { // from class: com.xiaomi.micolauncher.common.schema.-$$Lambda$BatteryPrintService$xnoEpr--_aKlLF9S_vY5b9XzPEE
                @Override // io.reactivex.functions.Predicate
                public final boolean test(Object obj) {
                    boolean b;
                    b = BatteryPrintService.this.b(obj);
                    return b;
                }
            }).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.schema.-$$Lambda$BatteryPrintService$PNKjYETWcnRTKhnH1M9P-xinSV4
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BatteryPrintService.this.a(obj);
                }
            });
        }
        return super.onStartCommand(intent, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(Object obj) throws Exception {
        a();
        return !this.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        b();
    }

    private void a() {
        if (this.b) {
            Observable.timer(100L, TimeUnit.MILLISECONDS).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.schema.-$$Lambda$BatteryPrintService$fEUA0I3oGUb0Qm2Oq5ZHNDYoEJA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    BatteryPrintService.this.a((Long) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Long l) throws Exception {
        stopSelf();
    }

    private void b() {
        L.base.d("current battery level : %d", Integer.valueOf(this.c.getIntProperty(4)));
    }
}
