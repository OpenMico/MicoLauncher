package com.xiaomi.micolauncher.module.miot.mesh;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.elvishew.xlog.Logger;
import com.xiaomi.mesh.provision.MiotMeshDeviceItem;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.miot.mesh.MeshPairState;
import com.xiaomi.micolauncher.module.setting.utils.MiotProvisionManagerWrapper;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class MeshDevicesShowAdapter extends RecyclerView.Adapter<a> {
    private static String a = "[MeshDevicesShowAdapter]: ";
    private static List<MiotMeshDeviceItem> b = null;
    private static MeshPairState.MeshState c = null;
    private static int d = 0;
    private static String e = "Yeelight LED筒灯";
    private static String f = "Yeelight LED射灯";
    private static String g = "Yeelight LED球泡";
    private static String h = "Yeelight LED烛泡";
    private static String i = "未知设备";
    private static AlphaAnimation j;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.ViewHolder {
        RelativeLayout a;
        ImageView b;
        ImageView c;
        TextView d;
        TextView e;

        public a(View view) {
            super(view);
            if (MeshDevicesShowAdapter.b.size() == 1) {
                this.a = (RelativeLayout) view.findViewById(R.id.layoutMeshSingleDeviceItem);
            } else if (MeshDevicesShowAdapter.b.size() > 1) {
                this.a = (RelativeLayout) view.findViewById(R.id.layoutMeshMultipleDevicesItem);
            }
            this.e = (TextView) view.findViewById(R.id.connectingName);
            this.c = (ImageView) view.findViewById(R.id.connectStateView);
            this.b = (ImageView) view.findViewById(R.id.imageView);
            this.d = (TextView) view.findViewById(R.id.deviceName);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(MiotMeshDeviceItem miotMeshDeviceItem) {
            Logger logger = L.mesh;
            logger.i("refreshUI:" + miotMeshDeviceItem);
            this.b.setImageDrawable(null);
            b(miotMeshDeviceItem);
            c(miotMeshDeviceItem);
            a(this.b);
        }

        private void a(ImageView imageView) {
            switch (MeshDevicesShowAdapter.c) {
                case MESH_BIND_IN_OPERATION:
                    if (MeshDevicesShowAdapter.d == 0) {
                        MeshDevicesShowAdapter.this.a(imageView);
                        return;
                    } else {
                        MeshDevicesShowAdapter.this.b(imageView);
                        return;
                    }
                case MESH_BIND_COMPLETE:
                    MeshDevicesShowAdapter.this.b(imageView);
                    return;
                default:
                    return;
            }
        }

        private void b(MiotMeshDeviceItem miotMeshDeviceItem) {
            switch (MeshDevicesShowAdapter.c) {
                case MESH_BIND_IN_OPERATION:
                    this.c.setVisibility(8);
                    this.e.setVisibility(0);
                    return;
                case MESH_BIND_COMPLETE:
                    this.e.setVisibility(8);
                    this.c.setVisibility(0);
                    if (miotMeshDeviceItem.getConnectState().equals(MiotMeshDeviceItem.CONNECT_STATE_SUCCEEDED)) {
                        GlideUtils.bindImageView(this.c.getContext(), (int) R.drawable.mesh_connect_succeeded, this.c);
                        return;
                    } else if (miotMeshDeviceItem.getConnectState().equals(MiotMeshDeviceItem.CONNECT_STATE_FAILED)) {
                        GlideUtils.bindImageView(this.c.getContext(), (int) R.drawable.mesh_connect_failed, this.c);
                        return;
                    } else {
                        return;
                    }
                case MESH_SCAN_COMPLETE:
                    this.c.setVisibility(8);
                    this.e.setVisibility(8);
                    this.b.clearAnimation();
                    return;
                default:
                    return;
            }
        }

        @SuppressLint({"CheckResult"})
        private void c(MiotMeshDeviceItem miotMeshDeviceItem) {
            if (MeshDevicesShowAdapter.this.a(miotMeshDeviceItem.getDeviceName())) {
                if (miotMeshDeviceItem.getDeviceName().equals(MeshDevicesShowAdapter.g)) {
                    GlideUtils.bindImageView(this.b.getContext(), (int) R.drawable.yeelight_led_qiupaodeng, this.b);
                } else if (miotMeshDeviceItem.getDeviceName().equals(MeshDevicesShowAdapter.e)) {
                    GlideUtils.bindImageView(this.b.getContext(), (int) R.drawable.yeelight_led_tongdeng, this.b);
                } else if (miotMeshDeviceItem.getDeviceName().equals(MeshDevicesShowAdapter.h)) {
                    GlideUtils.bindImageView(this.b.getContext(), (int) R.drawable.yeelight_led_lazhudeng, this.b);
                } else if (miotMeshDeviceItem.getDeviceName().equals(MeshDevicesShowAdapter.f)) {
                    GlideUtils.bindImageView(this.b.getContext(), (int) R.drawable.yeelight_led_shedeng, this.b);
                } else if (miotMeshDeviceItem.getDeviceName().equals(MeshDevicesShowAdapter.i)) {
                    GlideUtils.bindImageView(this.b.getContext(), (int) R.drawable.yeelight_led_shedeng, this.b);
                } else {
                    Logger logger = L.mesh;
                    logger.e("!! not handle this device Name: " + miotMeshDeviceItem.getDeviceName() + " drawable");
                }
                this.d.setText(miotMeshDeviceItem.getDeviceName());
                return;
            }
            String str = "";
            if (!TextUtils.isEmpty(miotMeshDeviceItem.getModel())) {
                str = miotMeshDeviceItem.getModel();
            } else if (!TextUtils.isEmpty(miotMeshDeviceItem.getPid())) {
                str = String.valueOf(miotMeshDeviceItem.getPid());
            } else {
                Logger logger2 = L.mesh;
                logger2.i("unknown meshDevicesItem: " + miotMeshDeviceItem);
            }
            MiotMeshIconInfo miotMeshIconInfoById = MiotProvisionManagerWrapper.getInstance().getMiotMeshIconInfoById(str);
            if (miotMeshIconInfoById == null || miotMeshIconInfoById.result == null || miotMeshIconInfoById.result.getConfigs() == null || miotMeshIconInfoById.result.getConfigs().size() <= 0) {
                L.mesh.e("get device image and device name from cache fail");
                return;
            }
            miotMeshDeviceItem.setViewUrl(miotMeshIconInfoById.result.getConfigs().get(0).getIcon_real());
            miotMeshDeviceItem.setDeviceName(miotMeshIconInfoById.result.getConfigs().get(0).getName());
            Glide.with(this.b.getContext()).load(miotMeshDeviceItem.getViewUrl()).into(this.b);
            this.d.setText(miotMeshDeviceItem.getDeviceName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str) {
        String[] strArr = {e, g, f, h, i};
        for (String str2 : strArr) {
            if (!TextUtils.isEmpty(str) && str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MeshDevicesShowAdapter(List<MiotMeshDeviceItem> list, MeshPairState.MeshState meshState) {
        b = list;
        c = meshState;
        Logger logger = L.mesh;
        logger.d(a + "! MeshDevicesShowAdapter meshState=%s", c.name());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(List<MiotMeshDeviceItem> list, MeshPairState.MeshState meshState) {
        c = meshState;
        b = list;
        Logger logger = L.mesh;
        logger.i(a + "! setMeshDevicesItems meshState=%s", c.name());
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i2) {
        if (d != i2) {
            Logger logger = L.mesh;
            logger.i(a + "[Change ScrollState] oldState: " + d + " newState: " + i2);
            d = i2;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NotNull ViewGroup viewGroup, int i2) {
        return new a(i2 == 1 ? LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mesh_multiple_devices_item, viewGroup, false) : i2 == 0 ? LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mesh_single_device_item, viewGroup, false) : null);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        return b.size() > 1 ? 1 : 0;
    }

    @SuppressLint({"CheckResult"})
    public void onBindViewHolder(@NotNull a aVar, int i2) {
        aVar.a(b.get(i2));
        aVar.a.setOnClickListener($$Lambda$MeshDevicesShowAdapter$2HhnIdgpzkbCYqHsBkWWuqvBk0.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view) {
        Logger logger = L.mesh;
        logger.d(a + "!! onClickListener");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(b);
    }

    /* loaded from: classes3.dex */
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public SpacesItemDecoration(int i) {
            this.a = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (MeshDevicesShowAdapter.b.size() > 1) {
                rect.left = this.a;
                if (recyclerView.getChildAdapterPosition(view) == 0) {
                    rect.left = (this.a * 84) / 100;
                }
                if (recyclerView.getChildAdapterPosition(view) == MeshDevicesShowAdapter.b.size() - 1) {
                    rect.right = this.a;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ImageView imageView) {
        if (j == null) {
            j = new AlphaAnimation(1.0f, 0.4f);
            j.setDuration(1500L);
            j.setFillAfter(false);
            j.setFillBefore(true);
            j.setRepeatMode(2);
            j.setRepeatCount(-1);
        }
        imageView.clearAnimation();
        imageView.startAnimation(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ImageView imageView) {
        imageView.clearAnimation();
    }

    public void cancelAnimationOnStop() {
        AlphaAnimation alphaAnimation = j;
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
    }
}
