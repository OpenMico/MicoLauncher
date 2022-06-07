package com.xiaomi.micolauncher.module.miot_v2;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.miot_v2.MIoTListAdapter;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.ICallback;
import com.xiaomi.miot.support.ui.DeviceOperator;
import java.util.List;

/* loaded from: classes3.dex */
public class MIoTListAdapter extends RecyclerView.Adapter<a> {
    private Context a;
    private DeviceOperator b;
    private List<DeviceInfo> c;
    private String d;
    private View.OnClickListener e;

    public MIoTListAdapter(Context context) {
        this.a = context;
    }

    public void setDeviceOperator(DeviceOperator deviceOperator) {
        this.b = deviceOperator;
    }

    public void setDeviceInfoList(List<DeviceInfo> list) {
        this.c = list;
        notifyDataSetChanged();
    }

    public void setTabRoomName(String str) {
        this.d = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_miot_device_v2, viewGroup, false);
        this.e = $$Lambda$MIoTListAdapter$kcxC6WxsDgGKyxCbWg1TzXiSxuk.INSTANCE;
        return new a(inflate);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x0174  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(@androidx.annotation.NonNull final com.xiaomi.micolauncher.module.miot_v2.MIoTListAdapter.a r9, final int r10) {
        /*
            Method dump skipped, instructions count: 595
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.miot_v2.MIoTListAdapter.onBindViewHolder(com.xiaomi.micolauncher.module.miot_v2.MIoTListAdapter$a, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(@NonNull a aVar, DeviceInfo deviceInfo, View view) {
        DeviceOperator deviceOperator = this.b;
        if (deviceOperator != null) {
            deviceOperator.onDevicePageShow(aVar.a, deviceInfo);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(DeviceInfo deviceInfo, @NonNull a aVar, int i, View view) {
        if (this.b != null) {
            this.b.onDeviceSwitch(deviceInfo.did, deviceInfo.currentStatus, new AnonymousClass1(!deviceInfo.currentStatus, deviceInfo, aVar, i));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.module.miot_v2.MIoTListAdapter$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends ICallback.Stub {
        final /* synthetic */ boolean a;
        final /* synthetic */ DeviceInfo b;
        final /* synthetic */ a c;
        final /* synthetic */ int d;

        AnonymousClass1(boolean z, DeviceInfo deviceInfo, a aVar, int i) {
            this.a = z;
            this.b = deviceInfo;
            this.c = aVar;
            this.d = i;
        }

        @Override // com.xiaomi.miot.service.ICallback
        public void onSuccess(Bundle bundle) throws RemoteException {
            Logger logger = L.miot;
            StringBuilder sb = new StringBuilder();
            sb.append("turn ");
            sb.append(this.a ? "on" : "off");
            sb.append(" device Name=%s, Did=%s onSuccess");
            logger.d(sb.toString(), this.b.deviceName, this.b.did);
            ImageView imageView = this.c.c;
            final a aVar = this.c;
            final boolean z = this.a;
            final DeviceInfo deviceInfo = this.b;
            final int i = this.d;
            imageView.post(new Runnable() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTListAdapter$1$cxjMZdlt0YKaHN0RzypDwi91XwM
                @Override // java.lang.Runnable
                public final void run() {
                    MIoTListAdapter.AnonymousClass1.this.a(aVar, z, deviceInfo, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(@NonNull a aVar, boolean z, DeviceInfo deviceInfo, int i) {
            aVar.c.setImageResource(z ? R.drawable.miot_device_turn_on : R.drawable.miot_device_turn_off);
            deviceInfo.currentStatus = z;
            MIoTListAdapter.this.notifyItemChanged(i);
        }

        @Override // com.xiaomi.miot.service.ICallback
        public void onFailure(Bundle bundle) throws RemoteException {
            Logger logger = L.miot;
            StringBuilder sb = new StringBuilder();
            sb.append("turn ");
            sb.append(this.a ? "on" : "off");
            sb.append(" device Name=%s, Did=%s failure");
            logger.w(sb.toString(), this.b.deviceName, this.b.did);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.c);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        View a;
        ImageView b;
        ImageView c;
        View d;
        TextView e;
        TextView f;
        TextView g;
        TextView h;

        public a(View view) {
            super(view);
            this.a = view.findViewById(R.id.device_root_view);
            this.b = (ImageView) view.findViewById(R.id.device_image);
            this.c = (ImageView) view.findViewById(R.id.device_switch_btn);
            this.d = view.findViewById(R.id.device_status_layout);
            this.e = (TextView) view.findViewById(R.id.device_status_description);
            this.f = (TextView) view.findViewById(R.id.device_status_value);
            this.g = (TextView) view.findViewById(R.id.device_name);
            this.h = (TextView) view.findViewById(R.id.device_room_and_status);
        }
    }
}
