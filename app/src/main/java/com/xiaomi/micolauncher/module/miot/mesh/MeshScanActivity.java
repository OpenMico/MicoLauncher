package com.xiaomi.micolauncher.module.miot.mesh;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.module.miot.mesh.uievent.MeshScanEvent;
import com.xiaomi.micolauncher.module.setting.utils.MiotMeshUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class MeshScanActivity extends BaseActivity {
    private LottieAnimationView a;
    private TextView b;
    private ImageView c;
    private TextView d;
    private MeshScanEvent.MeshScanEnum e;
    private boolean f;
    private Handler g = new Handler(Looper.getMainLooper());
    private Runnable h = new Runnable() { // from class: com.xiaomi.micolauncher.module.miot.mesh.MeshScanActivity.1
        @Override // java.lang.Runnable
        public void run() {
            L.mesh.i("%s waitTimer timeout !!", "[MeshScanActivity]: ");
            MeshScanActivity.this.f = true;
            MeshScanActivity.this.finish();
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        b((MeshScanEvent.MeshScanEnum) getIntent().getSerializableExtra("meshScanState"));
        if (!EventBusRegistry.getEventBus().isRegistered(this)) {
            EventBusRegistry.getEventBus().register(this);
        }
        setContentView(R.layout.activity_mesh_scanning);
        this.a = (LottieAnimationView) findViewById(R.id.animation_scan_view);
        this.b = (TextView) findViewById(R.id.scanTextView);
        this.c = (ImageView) findViewById(R.id.scanImageView);
        this.d = (TextView) findViewById(R.id.scanNoDeviceTextView);
        if (Hardware.isLx04()) {
            this.c.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.img_mesh_scan_no_device));
            this.b.setText(R.string.mesh_scanning);
            this.d.setText(R.string.mesh_scan_no_device);
        } else {
            this.c.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.scan_find_failure));
            this.b.setText(R.string.iot_devices_provision_scanning);
            this.d.setText(R.string.iot_devices_provision_scan_no_device);
        }
        this.f = false;
        a(this.e);
        if (this.e == MeshScanEvent.MeshScanEnum.SCANNING) {
            f();
            d();
        } else if (this.e == MeshScanEvent.MeshScanEnum.SCANNED_NO_DEVICE) {
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        L.mesh.d("%s onDestroy()", "[MeshScanActivity]: ");
        e();
        if (!this.f) {
            ToastUtil.showToast((int) R.string.mesh_connecting_in_background, 3);
        }
        EventBusRegistry.getEventBus().unregister(this);
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        L.mesh.d("%s onNewIntent()", "[MeshScanActivity]: ");
        c();
        b((MeshScanEvent.MeshScanEnum) intent.getSerializableExtra("meshScanState"));
        L.mesh.d("%s Set mesh state: %s", "[MeshScanActivity]: ", this.e);
        if (this.e == MeshScanEvent.MeshScanEnum.SCANNING) {
            this.f = false;
            d();
            a(this.e);
        } else if (this.e == MeshScanEvent.MeshScanEnum.SCANNED_NO_DEVICE) {
            a();
        }
    }

    private void a(MeshScanEvent.MeshScanEnum meshScanEnum) {
        if (meshScanEnum == MeshScanEvent.MeshScanEnum.SCANNING) {
            this.c.setVisibility(8);
            this.d.setVisibility(8);
            this.a.setVisibility(0);
        } else if (meshScanEnum == MeshScanEvent.MeshScanEnum.SCANNED_NO_DEVICE) {
            this.a.setVisibility(8);
            this.b.setVisibility(8);
            this.c.setVisibility(0);
            this.d.setVisibility(0);
        }
    }

    private void a() {
        a(this.e);
        e();
        b();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMeshScanEvent(MeshScanEvent meshScanEvent) {
        MeshScanEvent.MeshScanEnum meshScanEnum = meshScanEvent.event;
        L.mesh.d("%s onMeshScanEvent event: %s", "[MeshScanActivity]: ", meshScanEvent.event.name());
        switch (meshScanEnum) {
            case FINISH:
                e();
                this.f = true;
                finish();
                return;
            case SCANNING:
                b(MeshScanEvent.MeshScanEnum.SCANNING);
                a(this.e);
                return;
            case SCANNED_NO_DEVICE:
                b(MeshScanEvent.MeshScanEnum.SCANNED_NO_DEVICE);
                a(this.e);
                e();
                b();
                return;
            default:
                return;
        }
    }

    private void b(MeshScanEvent.MeshScanEnum meshScanEnum) {
        L.mesh.d("%s set setMeshScanState: %s", "[MeshScanActivity]: ", meshScanEnum.name());
        this.e = meshScanEnum;
    }

    private void b() {
        L.mesh.i("%s setWaitTimer", "[MeshScanActivity]: ");
        this.g.postDelayed(this.h, 7000L);
    }

    private void c() {
        L.mesh.i("%s clearWaitTimer", "[MeshScanActivity]: ");
        this.g.removeCallbacks(this.h);
    }

    private void d() {
        MiotMeshUtils.playSound(R.raw.mesh_scanning, this);
    }

    private void e() {
        MiotMeshUtils.stopSound();
    }

    private void f() {
        this.a.cancelAnimation();
        this.a.setProgress(0.5f);
        this.a.setImageAssetsFolder("mesh/scan/images");
        this.a.setAnimation("mesh/scan/data.json");
        this.a.setRepeatMode(2);
        this.a.setRepeatCount(-1);
        this.a.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.module.miot.mesh.MeshScanActivity.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                L.mesh.d("[MeshScanActivity]: onAnimationStart");
                MeshScanActivity.this.b.setVisibility(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                L.mesh.d("[MeshScanActivity]: !! onAnimationEnd");
            }
        });
        this.a.playAnimation();
    }
}
