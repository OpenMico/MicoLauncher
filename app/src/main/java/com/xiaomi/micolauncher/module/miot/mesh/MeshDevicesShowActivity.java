package com.xiaomi.micolauncher.module.miot.mesh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.event.StartMiotUiEvent;
import com.xiaomi.micolauncher.module.miot.MiotMainActivity;
import com.xiaomi.micolauncher.module.miot.mesh.MeshDevicesShowAdapter;
import com.xiaomi.micolauncher.module.miot.mesh.MeshPairState;
import com.xiaomi.micolauncher.module.miot.mesh.uievent.MeshDeviceShowEvent;
import com.xiaomi.micolauncher.module.setting.utils.MiotMeshUtils;
import java.util.ArrayList;
import java.util.Iterator;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class MeshDevicesShowActivity extends BaseActivity {
    public static final String EXTRA_KEY_MESH_DEVICES = "mesh_devices";
    public static final String EXTRA_KEY_MESH_STATE = "mesh_state";
    public static final String SINGLE_BIND_DEVICE_KEY = "singleBindDevice";
    private RecyclerView c;
    private MeshDevicesShowAdapter d;
    private Runnable e;
    private MeshPairState.MeshState f;
    private MiotMeshDeviceItem l;
    private final String a = "[MeshDevicesShowActivity]: ";
    private ArrayList<MiotMeshDeviceItem> b = new ArrayList<>();
    private boolean g = false;
    private boolean h = false;
    private boolean i = false;
    private int j = 0;
    private int k = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mesh_devices_show);
        this.c = (RecyclerView) findViewById(R.id.mesh_devices_show_recyclerView);
        this.b = getIntent().getParcelableArrayListExtra(EXTRA_KEY_MESH_DEVICES);
        this.f = (MeshPairState.MeshState) getIntent().getSerializableExtra(EXTRA_KEY_MESH_STATE);
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        L.mesh.d("%s onCreate meshStateName:%s size:%s ", "[MeshDevicesShowActivity]: ", this.f.name(), Integer.valueOf(this.b.size()));
        Iterator<MiotMeshDeviceItem> it = this.b.iterator();
        while (it.hasNext()) {
            MiotMeshDeviceItem next = it.next();
            Logger logger = L.mesh;
            logger.d("[MeshDevicesShowActivity]: device name: " + next.getDeviceName() + " pid: " + next.getPid());
        }
        this.c.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.c.addItemDecoration(new MeshDevicesShowAdapter.SpacesItemDecoration(DisplayUtils.dip2px((Activity) this, 48.0f)));
        this.d = new MeshDevicesShowAdapter(this.b, this.f);
        this.c.setAdapter(this.d);
        this.c.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.xiaomi.micolauncher.module.miot.mesh.MeshDevicesShowActivity.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (MeshDevicesShowActivity.this.j != i) {
                    MeshDevicesShowActivity.this.j = i;
                    MeshDevicesShowActivity.this.d.a(i);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NotNull RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        L.mesh.d("[MeshDevicesShowActivity]: onDestroy()");
        this.d.cancelAnimationOnStop();
        this.c.setAdapter(null);
        b();
        if (!this.i) {
            ToastUtil.showToast((int) R.string.mesh_connecting_in_background, 3);
        }
        EventBusRegistry.getEventBus().unregister(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.i = false;
        this.b = intent.getParcelableArrayListExtra(EXTRA_KEY_MESH_DEVICES);
        this.f = (MeshPairState.MeshState) intent.getSerializableExtra(EXTRA_KEY_MESH_STATE);
        Logger logger = L.mesh;
        logger.d("[MeshDevicesShowActivity]: onNewIntent() meshState: " + this.f.name());
        this.d.a(this.b, this.f);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                if (this.f == MeshPairState.MeshState.MESH_BIND_COMPLETE) {
                    clearWaitTimer();
                    break;
                }
                break;
            case 1:
                if (this.f == MeshPairState.MeshState.MESH_BIND_COMPLETE) {
                    setWaitTimer();
                    break;
                }
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setWaitTimer() {
        int i = (this.g || !this.h) ? 15 : 7;
        L.mesh.i("[MeshDevicesShowActivity]: setWaitTimer waitTimeout=%d", Integer.valueOf(i));
        this.e = new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.mesh.-$$Lambda$MeshDevicesShowActivity$lSi8UmjEIXFGrGcJY31ZIx_ds5Y
            @Override // java.lang.Runnable
            public final void run() {
                MeshDevicesShowActivity.this.c();
            }
        };
        getHandler().postDelayed(this.e, i * 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c() {
        MiotMeshDeviceItem miotMeshDeviceItem;
        L.mesh.i("[MeshDevicesShowActivity]: waitTimer timeout ");
        if (!this.g || this.h) {
            this.i = true;
            if (Hardware.isBigScreen() && 1 == this.k && this.h && (miotMeshDeviceItem = this.l) != null && !TextUtils.isEmpty(miotMeshDeviceItem.getDid())) {
                Intent intent = new Intent(getApplicationContext(), IotDevicesProvisionRoomSettingActivity.class);
                intent.putExtra(SINGLE_BIND_DEVICE_KEY, this.l);
                getApplicationContext().startActivity(intent);
            } else if (Hardware.isBigScreen()) {
                EventBusRegistry.getEventBus().post(new StartMiotUiEvent());
            } else {
                ActivityLifeCycleManager.startActivityQuietly(new Intent(getApplicationContext(), MiotMainActivity.class));
            }
            finish();
            return;
        }
        this.i = true;
        finish();
    }

    public void clearWaitTimer() {
        if (this.e != null) {
            L.mesh.i("[MeshDevicesShowActivity]: clearWaitTimer");
            getHandler().removeCallbacks(this.e);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeshPairStateEvent(MeshPairState meshPairState) {
        L.mesh.i("[MeshDevicesShowActivity]: onMeshPairStateEvent: " + meshPairState.state.name());
        this.f = meshPairState.state;
        switch (meshPairState.state) {
            case MESH_SCAN_IN_OPERATION:
            case MESH_SCAN_COMPLETE:
            default:
                return;
            case MESH_BIND_IN_OPERATION:
                MeshDevicesShowAdapter meshDevicesShowAdapter = this.d;
                if (meshDevicesShowAdapter != null) {
                    meshDevicesShowAdapter.a(this.b, MeshPairState.MeshState.MESH_BIND_IN_OPERATION);
                }
                a();
                return;
            case MESH_BIND_COMPLETE:
                ArrayList<MiotMeshDeviceItem> a = meshPairState.a();
                b();
                this.g = false;
                this.h = false;
                this.i = true;
                this.k = 0;
                Iterator<MiotMeshDeviceItem> it = a.iterator();
                while (it.hasNext()) {
                    MiotMeshDeviceItem next = it.next();
                    if (MiotMeshDeviceItem.CONNECT_STATE_FAILED.equals(next.getConnectState())) {
                        this.g = true;
                    } else if (MiotMeshDeviceItem.CONNECT_STATE_SUCCEEDED.equals(next.getConnectState())) {
                        this.h = true;
                        this.l = next;
                        this.k++;
                    }
                }
                MeshDevicesShowAdapter meshDevicesShowAdapter2 = this.d;
                if (meshDevicesShowAdapter2 != null) {
                    meshDevicesShowAdapter2.a(a, MeshPairState.MeshState.MESH_BIND_COMPLETE);
                }
                setWaitTimer();
                return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeshDeviceShowEvent(MeshDeviceShowEvent meshDeviceShowEvent) {
        MeshDeviceShowEvent.MeshDeviceShowEnum meshDeviceShowEnum = meshDeviceShowEvent.event;
        Logger logger = L.mesh;
        logger.i("[MeshDevicesShowActivity]: onMeshDeviceShowEvent event: " + meshDeviceShowEvent.event.name());
        switch (meshDeviceShowEnum) {
            case FINISH:
                this.i = true;
                finish();
                b();
                return;
            case FINISH_TO_MAINACTIVITY:
                this.i = true;
                finish();
                ActivityLifeCycleManager.getInstance().gotoMainActivity(getApplicationContext());
                return;
            default:
                return;
        }
    }

    private void a() {
        L.mesh.i("[MeshDevicesShowActivity]: !! playConnectingSound");
        MiotMeshUtils.playSound(R.raw.mesh_connecting, this);
    }

    private void b() {
        L.mesh.i("[MeshDevicesShowActivity]: !! stopConnectingSound");
        MiotMeshUtils.stopSound();
    }
}
