package com.xiaomi.smarthome.core.utils;

import android.os.Bundle;
import com.blankj.utilcode.util.NetworkUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.miot.mesh.IotDevicesProvisionRoomSettingCompleteActivity;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.service.ICallback;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: OperationUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/xiaomi/smarthome/core/utils/OperationUtils;", "", "()V", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class OperationUtils {
    @NotNull
    public static final Companion Companion = new Companion(null);

    @JvmStatic
    public static final void toggleStatus(@NotNull DeviceInfo deviceInfo, @NotNull Function1<? super Boolean, Unit> function1) {
        Companion.toggleStatus(deviceInfo, function1);
    }

    @JvmStatic
    public static final void toggleStatus(@NotNull DeviceInfo deviceInfo, boolean z) {
        Companion.toggleStatus(deviceInfo, z);
    }

    /* compiled from: OperationUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J3\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062!\u0010\u0007\u001a\u001d\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u00040\bH\u0007J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\tH\u0007¨\u0006\u000e"}, d2 = {"Lcom/xiaomi/smarthome/core/utils/OperationUtils$Companion;", "", "()V", "toggleStatus", "", IotDevicesProvisionRoomSettingCompleteActivity.DEVICE_INFO, "Lcom/xiaomi/miot/DeviceInfo;", "callback", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "currentStatus", "turnStatus", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final void toggleStatus(@NotNull final DeviceInfo deviceInfo, @NotNull final Function1<? super Boolean, Unit> callback) {
            Intrinsics.checkNotNullParameter(deviceInfo, "deviceInfo");
            Intrinsics.checkNotNullParameter(callback, "callback");
            if (!NetworkUtils.isConnected()) {
                ToastUtil.showToast(R.string.tip_network_is_broken);
                return;
            }
            final boolean z = !deviceInfo.currentStatus;
            MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
            Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
            instance.getDeviceHelper().onDeviceSwitch(deviceInfo.did, deviceInfo.currentStatus, new ICallback.Stub() { // from class: com.xiaomi.smarthome.core.utils.OperationUtils$Companion$toggleStatus$1
                @Override // com.xiaomi.miot.service.ICallback
                public void onSuccess(@NotNull Bundle bundle) {
                    Intrinsics.checkNotNullParameter(bundle, "bundle");
                    Logger logger = L.miot;
                    StringBuilder sb = new StringBuilder();
                    sb.append("turn ");
                    sb.append(z ? "on" : "off");
                    sb.append(" device Name=%s, Did=%s onSuccess");
                    logger.d(sb.toString(), deviceInfo.deviceName, deviceInfo.did);
                    callback.invoke(Boolean.valueOf(z));
                }

                @Override // com.xiaomi.miot.service.ICallback
                public void onFailure(@NotNull Bundle bundle) {
                    Intrinsics.checkNotNullParameter(bundle, "bundle");
                    Logger logger = L.miot;
                    StringBuilder sb = new StringBuilder();
                    sb.append("turn ");
                    sb.append(z ? "on" : "off");
                    sb.append(" device Name=%s, Did=%s failure");
                    logger.w(sb.toString(), deviceInfo.deviceName, deviceInfo.did);
                }
            });
        }

        @JvmStatic
        public final void toggleStatus(@NotNull final DeviceInfo deviceInfo, final boolean z) {
            Intrinsics.checkNotNullParameter(deviceInfo, "deviceInfo");
            MicoMiotDeviceManager instance = MicoMiotDeviceManager.getInstance();
            Intrinsics.checkNotNullExpressionValue(instance, "MicoMiotDeviceManager.getInstance()");
            instance.getDeviceHelper().onDeviceSwitch(deviceInfo.did, !z, new ICallback.Stub() { // from class: com.xiaomi.smarthome.core.utils.OperationUtils$Companion$toggleStatus$2
                @Override // com.xiaomi.miot.service.ICallback
                public void onSuccess(@NotNull Bundle bundle) {
                    Intrinsics.checkNotNullParameter(bundle, "bundle");
                    Logger logger = L.miot;
                    StringBuilder sb = new StringBuilder();
                    sb.append("turn ");
                    sb.append(z ? "on" : "off");
                    sb.append(" device Name=%s, Did=%s onSuccess");
                    logger.d(sb.toString(), deviceInfo.deviceName, deviceInfo.did);
                }

                @Override // com.xiaomi.miot.service.ICallback
                public void onFailure(@NotNull Bundle bundle) {
                    Intrinsics.checkNotNullParameter(bundle, "bundle");
                    Logger logger = L.miot;
                    StringBuilder sb = new StringBuilder();
                    sb.append("turn ");
                    sb.append(z ? "on" : "off");
                    sb.append(" device Name=%s, Did=%s failure");
                    logger.w(sb.toString(), deviceInfo.deviceName, deviceInfo.did);
                }
            });
        }
    }
}
