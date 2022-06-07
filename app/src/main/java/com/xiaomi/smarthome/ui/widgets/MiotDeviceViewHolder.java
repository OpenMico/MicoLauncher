package com.xiaomi.smarthome.ui.widgets;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.alibaba.fastjson.asm.Opcodes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4BigButton;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.ICallback;
import com.xiaomi.miot.support.core.MiotRoom;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.core.miot.MicoMiotUtils;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;
import io.reactivex.functions.Consumer;
import java.util.Set;

/* loaded from: classes4.dex */
public class MiotDeviceViewHolder extends BaseViewHolder {
    private final TextView a;
    private final ImageView b;
    private final ImageView c;
    private final TextView d;
    private final TextView e;
    private DeviceInfo f;
    private boolean g = false;

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    public MiotDeviceViewHolder(@NonNull View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.device_name);
        this.b = (ImageView) view.findViewById(R.id.device_icon);
        this.c = (ImageView) view.findViewById(R.id.device_status);
        this.d = (TextView) view.findViewById(R.id.device_desc);
        this.e = (TextView) view.findViewById(R.id.device_status_offline);
        this.a.setPaintFlags(Opcodes.INSTANCEOF);
        this.d.setPaintFlags(Opcodes.INSTANCEOF);
    }

    public void bindData(DeviceInfo deviceInfo, MiotRoom miotRoom) {
        this.f = deviceInfo;
        int i = 0;
        this.g = false;
        if (deviceInfo != null) {
            this.a.setText(deviceInfo.deviceName);
            this.c.setImageResource(!deviceInfo.isOnline ? R.drawable.sh_ic_status_offline_home : deviceInfo.currentStatus ? R.drawable.sh_ic_status_on_home : R.drawable.sh_ic_status_off_home);
            this.c.setVisibility(deviceInfo.showSlideButton ? 0 : 8);
            this.c.setClickable(deviceInfo.isOnline);
            TextView textView = this.e;
            if (deviceInfo.isOnline) {
                i = 8;
            }
            textView.setVisibility(i);
            String str = MicoMiotUtils.isCommonUsedRoom(miotRoom) ? deviceInfo.roomInfo : null;
            str = "";
            this.itemView.setBackgroundResource(R.drawable.shape_mico_device_bg);
            this.a.setTextColor(this.itemView.getResources().getColor(R.color.device_name_color, null));
            this.d.setTextColor(this.itemView.getResources().getColor(R.color.device_desc_color, null));
            this.b.setAlpha(1.0f);
            if (deviceInfo.isOnline) {
                Set<String> keySet = deviceInfo.subtitleMap.keySet();
                if (keySet != null && keySet.size() == 1) {
                    for (String str2 : keySet) {
                        if (!"PM2.5".equals(str2)) {
                            str = deviceInfo.subtitleMap.getString(str2);
                        }
                    }
                }
                if (str == null) {
                    str = str;
                } else if (!TextUtils.isEmpty(str)) {
                    str = str + " | " + str;
                }
            } else if (str != null) {
                str = str + " | ";
            }
            this.d.setText(str);
            String str3 = deviceInfo.icon;
            if (!TextUtils.isEmpty(str3)) {
                Glide.with(this.itemView).load(Uri.parse(str3)).transform(new RoundedCorners(this.itemView.getResources().getDimensionPixelSize(R.dimen.room_device_corner_radius))).into(this.b);
            }
        }
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    public void initInMain() {
        super.initInMain();
        AnimLifecycleManager.repeatOnAttach(this.itemView, MicoAnimConfigurator4BigButton.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.itemView).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MiotDeviceViewHolder$DSkRoI1fpmV0FuOPK4PEi9Lpvhc
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiotDeviceViewHolder.this.b(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.c, MicoAnimConfigurator4TabAndSwitch.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.c).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$MiotDeviceViewHolder$ZaktaOEgMFrYLN2T5IG_8YsRKGs
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiotDeviceViewHolder.this.a(obj);
            }
        });
    }

    public /* synthetic */ void b(Object obj) throws Exception {
        if (this.f != null) {
            if (!NetworkUtil.isNetworkConnected(this.d.getContext())) {
                ToastUtil.showToast(R.string.tip_network_is_broken);
            } else if (!MicoMiotDeviceManager.getInstance().isDeviceInfoReady()) {
                ToastUtil.showToast(R.string.device_info_is_loading);
            } else if (this.f.isOnline) {
                MicoMiotDeviceManager.getInstance().getDeviceHelper().onDevicePageShow(this.itemView, this.f);
                L.smarthome.d("MiotDeviceViewHolder:clickItem deviceName=%s,model=%s", this.f.deviceName, this.f.model);
                SmartHomeStatHelper.recordSmartTabRoomModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT_MORE);
            } else {
                ToastUtil.showToast(R.string.miot_device_off_line);
            }
        }
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        if (!this.g) {
            if (!NetworkUtil.isNetworkConnected(this.d.getContext())) {
                ToastUtil.showToast(R.string.tip_network_is_broken);
                return;
            }
            this.g = true;
            final boolean z = true ^ this.f.currentStatus;
            this.c.setImageResource(z ? R.drawable.sh_ic_status_on : R.drawable.sh_ic_status_off);
            MicoMiotDeviceManager.getInstance().getDeviceHelper().onDeviceSwitch(this.f.did, this.f.currentStatus, new ICallback.Stub() { // from class: com.xiaomi.smarthome.ui.widgets.MiotDeviceViewHolder.1
                @Override // com.xiaomi.miot.service.ICallback
                public void onSuccess(Bundle bundle) {
                    Logger logger = L.miot;
                    StringBuilder sb = new StringBuilder();
                    sb.append("turn to ");
                    sb.append(z ? "on" : "off");
                    sb.append(" device Name=%s, Did=%s onSuccess");
                    logger.d(sb.toString(), MiotDeviceViewHolder.this.f.deviceName, MiotDeviceViewHolder.this.f.did);
                    MiotDeviceViewHolder.this.g = false;
                }

                @Override // com.xiaomi.miot.service.ICallback
                public void onFailure(Bundle bundle) {
                    Logger logger = L.miot;
                    StringBuilder sb = new StringBuilder();
                    sb.append("turn to ");
                    sb.append(z ? "on" : "off");
                    sb.append(" device Name=%s, Did=%s failure");
                    logger.w(sb.toString(), MiotDeviceViewHolder.this.f.deviceName, MiotDeviceViewHolder.this.f.did);
                    MiotDeviceViewHolder.this.g = false;
                    MiotDeviceViewHolder.this.c.setImageResource(!z ? R.drawable.sh_ic_status_on : R.drawable.sh_ic_status_off);
                }
            });
            SmartHomeStatHelper.recordSmartTabRoomModeClick(SmartHomeStatHelper.PARAM_VALUE_EQUIPMENT);
        }
    }
}
