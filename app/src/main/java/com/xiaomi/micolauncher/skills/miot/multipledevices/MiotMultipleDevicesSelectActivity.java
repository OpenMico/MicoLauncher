package com.xiaomi.micolauncher.skills.miot.multipledevices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.skills.miot.multipledevices.MiotMultipleDevicesAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class MiotMultipleDevicesSelectActivity extends BaseActivity {
    public static final String EXTRA_MIOT_DEVICE_INFO = "extra_iot_devices_info";
    private static final long e = TimeUnit.SECONDS.toMillis(15);
    private MiotMultipleDevicesAdapter c;
    private final String a = "MiotMultipleDevicesSelectActivity";
    private ArrayList<MultipleDevicesItem> b = new ArrayList<>();
    private final Handler d = new Handler(Looper.getMainLooper());
    private final Runnable f = new Runnable() { // from class: com.xiaomi.micolauncher.skills.miot.multipledevices.-$$Lambda$MiotMultipleDevicesSelectActivity$EsRymEc-CEIN9Ro93-eeUrJyD30
        @Override // java.lang.Runnable
        public final void run() {
            MiotMultipleDevicesSelectActivity.this.c();
        }
    };

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    public boolean isEphemeralActivity() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        getWindow().addFlags(128);
        setContentView(R.layout.activity_miot_multiple_devices);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.miot_muldevices_recyclerview);
        this.b = getIntent().getParcelableArrayListExtra(EXTRA_MIOT_DEVICE_INFO);
        L.miot.d("%s size: %s", "MiotMultipleDevicesSelectActivity", Integer.valueOf(this.b.size()));
        Iterator<MultipleDevicesItem> it = this.b.iterator();
        while (true) {
            i = 6;
            if (!it.hasNext()) {
                break;
            }
            MultipleDevicesItem next = it.next();
            L.miot.d("%s device1 name: %s, room name: %s, url: %s, did: %s, type: %s ", "MiotMultipleDevicesSelectActivity", next.getDeviceName(), next.getRoomName(), next.getViewUrl(), next.getDid(), next.getType());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.c = new MiotMultipleDevicesAdapter(this.b);
        if (Hardware.isBigScreen()) {
            i = getResources().getDimensionPixelSize(R.dimen.multi_device_select_item_decoration);
        }
        recyclerView.addItemDecoration(new MiotMultipleDevicesAdapter.SpacesItemDecoration(i));
        recyclerView.setAdapter(this.c);
        a();
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        L.miot.d("%s onNewIntent !", "MiotMultipleDevicesSelectActivity");
        resetTimer();
        this.b.clear();
        this.b = intent.getParcelableArrayListExtra(EXTRA_MIOT_DEVICE_INFO);
        L.miot.d("%s size: %s", "MiotMultipleDevicesSelectActivity", Integer.valueOf(this.b.size()));
        Iterator<MultipleDevicesItem> it = this.b.iterator();
        while (it.hasNext()) {
            MultipleDevicesItem next = it.next();
            L.miot.d("%s device1 name: %s, room name: %s, url: %s, did: %s, type: %s ", "MiotMultipleDevicesSelectActivity", next.getDeviceName(), next.getRoomName(), next.getViewUrl(), next.getDid(), next.getType());
        }
        MiotMultipleDevicesAdapter miotMultipleDevicesAdapter = this.c;
        if (miotMultipleDevicesAdapter != null) {
            miotMultipleDevicesAdapter.a(this.b);
        }
        super.onNewIntent(intent);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                b();
                break;
            case 1:
                resetTimer();
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void resetTimer() {
        b();
        a();
    }

    private void a() {
        L.miot.i("%s setWaitTimer", "MiotMultipleDevicesSelectActivity");
        this.d.postDelayed(this.f, e);
    }

    private void b() {
        L.miot.i("%s clearWaitTimer", "MiotMultipleDevicesSelectActivity");
        this.d.removeCallbacks(this.f);
    }

    public /* synthetic */ void c() {
        L.miot.i("%s waitTimer timeout !!", "MiotMultipleDevicesSelectActivity");
        finish();
    }
}
