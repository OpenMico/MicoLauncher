package com.xiaomi.smarthome.core.exts;

import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.miot.support.category.DeviceItemInfo;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.miot.support.category.ValueRange;
import com.xiaomi.smarthome.core.entity.PropDic;
import com.xiaomi.smarthome.core.utils.MiotDeviceUtils;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeviceExts.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0017\u001a\u0004\u0018\u00010\u0006*\u00020\u0002\u001a\u0011\u0010\u0018\u001a\u0004\u0018\u00010\u0014*\u00020\u0002¢\u0006\u0002\u0010\u0016\u001a\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u001b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0004\"\u0017\u0010\u000b\u001a\u0004\u0018\u00010\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\b\"\u0015\u0010\r\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0004\"\u0017\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u0017\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001c"}, d2 = {"did", "", "Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;", "getDid", "(Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;)Ljava/lang/String;", "fanLevel", "Lcom/xiaomi/miot/support/category/DevicePropertyBriefInfo;", "getFanLevel", "(Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;)Lcom/xiaomi/miot/support/category/DevicePropertyBriefInfo;", "mode", "getMode", "targetTempe", "getTargetTempe", "temperatureFormat", "getTemperatureFormat", "temperatureRange", "Lcom/xiaomi/miot/support/category/ValueRange;", "getTemperatureRange", "(Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;)Lcom/xiaomi/miot/support/category/ValueRange;", "temperatureValue", "", "getTemperatureValue", "(Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;)Ljava/lang/Double;", "brightnessProp", "brightnessValue", "exactValue", "propDic", "Lcom/xiaomi/smarthome/core/entity/PropDic;", "smarthome_release"}, k = 2, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class DeviceExtsKt {
    @Nullable
    public static final Double brightnessValue(@NotNull DeviceInfoWrapper brightnessValue) {
        Intrinsics.checkNotNullParameter(brightnessValue, "$this$brightnessValue");
        return MiotDeviceUtils.getBrightness(brightnessValue);
    }

    @Nullable
    public static final DevicePropertyBriefInfo brightnessProp(@NotNull DeviceInfoWrapper brightnessProp) {
        Intrinsics.checkNotNullParameter(brightnessProp, "$this$brightnessProp");
        DeviceItemInfo devicePropInfo = brightnessProp.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "devicePropInfo");
        List<DevicePropertyBriefInfo> list = devicePropInfo.getProperties().get(PropDic.P_BRIGHTNESS.getProp());
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    @Nullable
    public static final DevicePropertyBriefInfo getTargetTempe(@NotNull DeviceInfoWrapper targetTempe) {
        Intrinsics.checkNotNullParameter(targetTempe, "$this$targetTempe");
        DeviceItemInfo devicePropInfo = targetTempe.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "devicePropInfo");
        List<DevicePropertyBriefInfo> list = devicePropInfo.getProperties().get(PropDic.P_TARGET_TEMP.getProp());
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    @Nullable
    public static final String exactValue(@NotNull DeviceInfoWrapper exactValue, @NotNull PropDic propDic) {
        Intrinsics.checkNotNullParameter(exactValue, "$this$exactValue");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        String propValue = MiotDeviceUtils.getPropValue(exactValue, propDic);
        String valueFormat = MiotDeviceUtils.getValueFormat(exactValue, propDic);
        if (MiotDeviceUtils.isBool(valueFormat)) {
            return String.valueOf(Boolean.parseBoolean(propValue));
        }
        Integer num = null;
        if (MiotDeviceUtils.isFloat(valueFormat)) {
            if (propValue != null) {
                return String.valueOf(Float.parseFloat(propValue));
            }
            return null;
        } else if (!MiotDeviceUtils.isInt(valueFormat)) {
            return MiotDeviceUtils.isString(valueFormat) ? propValue : "";
        } else {
            if (propValue != null) {
                num = StringsKt.toIntOrNull(propValue);
            }
            return String.valueOf(num);
        }
    }

    @Nullable
    public static final DevicePropertyBriefInfo getFanLevel(@NotNull DeviceInfoWrapper fanLevel) {
        Intrinsics.checkNotNullParameter(fanLevel, "$this$fanLevel");
        DeviceItemInfo devicePropInfo = fanLevel.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "devicePropInfo");
        List<DevicePropertyBriefInfo> list = devicePropInfo.getProperties().get(PropDic.P_FAN_LEVEL.getProp());
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    @Nullable
    public static final ValueRange getTemperatureRange(@NotNull DeviceInfoWrapper temperatureRange) {
        Intrinsics.checkNotNullParameter(temperatureRange, "$this$temperatureRange");
        return MiotDeviceUtils.getValueRange(temperatureRange, PropDic.P_TARGET_TEMP);
    }

    @NotNull
    public static final String getTemperatureFormat(@NotNull DeviceInfoWrapper temperatureFormat) {
        Intrinsics.checkNotNullParameter(temperatureFormat, "$this$temperatureFormat");
        return MiotDeviceUtils.getValueFormat(temperatureFormat, PropDic.P_TARGET_TEMP);
    }

    @Nullable
    public static final Double getTemperatureValue(@NotNull DeviceInfoWrapper temperatureValue) {
        Intrinsics.checkNotNullParameter(temperatureValue, "$this$temperatureValue");
        String propValue = MiotDeviceUtils.getPropValue(temperatureValue, PropDic.P_TARGET_TEMP);
        if (propValue != null) {
            return StringsKt.toDoubleOrNull(propValue);
        }
        return null;
    }

    @NotNull
    public static final String getMode(@NotNull DeviceInfoWrapper mode) {
        Intrinsics.checkNotNullParameter(mode, "$this$mode");
        String propValue = MiotDeviceUtils.getPropValue(mode, PropDic.P_MODE);
        return propValue != null ? propValue : "";
    }

    @NotNull
    public static final String getDid(@NotNull DeviceInfoWrapper did) {
        Intrinsics.checkNotNullParameter(did, "$this$did");
        String str = did.getDeviceInfo().did;
        Intrinsics.checkNotNullExpressionValue(str, "deviceInfo.did");
        return str;
    }
}
