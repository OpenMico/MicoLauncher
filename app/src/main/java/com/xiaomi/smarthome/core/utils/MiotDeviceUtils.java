package com.xiaomi.smarthome.core.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import com.blankj.utilcode.util.SpanUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.router.proxy.Proxies;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaActivity;
import com.xiaomi.micolauncher.module.miot.mesh.IotDevicesProvisionRoomSettingCompleteActivity;
import com.xiaomi.miot.DeviceInfo;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.miot.support.category.DeviceItemInfo;
import com.xiaomi.miot.support.category.DevicePropValue;
import com.xiaomi.miot.support.category.DevicePropertyBriefInfo;
import com.xiaomi.miot.support.category.ModelInfo;
import com.xiaomi.miot.support.category.ValueConstrain;
import com.xiaomi.miot.support.category.ValueRange;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.CategoryDic;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.IDevice;
import com.xiaomi.smarthome.core.entity.PropDic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MiotDeviceUtils.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001FB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nH\u0002J\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0017\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0011J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0004H\u0007J \u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u001e\u0010\u0018\u001a\u0004\u0018\u00010\u00042\b\u0010\u0019\u001a\u0004\u0018\u00010\u00042\b\u0010\u001a\u001a\u0004\u0018\u00010\u0004H\u0007J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u000f\u001a\u00020\u0010J(\u0010\u001c\u001a\u00020\u00062\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001e\u0018\u00010\n2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0014\u001a\u00020\u0004H\u0007J\u0010\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0010H\u0007J\u001a\u0010#\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u001a\u0010$\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u001a\u0010%\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J \u0010&\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0012\u0010'\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010)\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0017\u0010*\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0011J\u0015\u0010+\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010,J\u0018\u0010-\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u001a\u0010.\u001a\u0004\u0018\u00010/2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0016\u00100\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u00101\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\u0010H\u0003J\u0010\u00102\u001a\u00020\b2\u0006\u00103\u001a\u00020\u0010H\u0007J\u0012\u00104\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0004H\u0007J\u0012\u00105\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0004H\u0007J\u0012\u00106\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0004H\u0007J\u0012\u00107\u001a\u00020\b2\b\u0010\"\u001a\u0004\u0018\u00010\u0010H\u0007J\u0012\u00108\u001a\u00020\b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0004H\u0007J\u0015\u00109\u001a\u00020\b2\b\u0010:\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010;J\u001e\u0010<\u001a\u00020\u00042\u0006\u0010=\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017J\u0018\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020A2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J \u0010B\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00042\u0006\u0010@\u001a\u00020A2\u0006\u0010\u0007\u001a\u00020\bH\u0002J \u0010D\u001a\u00020?2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0004H\u0007J\u0018\u0010E\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lcom/xiaomi/smarthome/core/utils/MiotDeviceUtils;", "", "()V", "TAG", "", "get2SwitchIcon", "", "isOnline", "", "ons", "", "Lcom/xiaomi/miot/support/category/DevicePropertyBriefInfo;", "get3SwitchIcon", "getBrightness", "", "deviceInfoWrapper", "Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;", "(Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;)Ljava/lang/Double;", "getBrightnessProp", "getCategoryRes", "categoryName", "getEnumValues", "propDic", "Lcom/xiaomi/smarthome/core/entity/PropDic;", "getExactValue", com.xiaomi.onetrack.api.b.p, "format", "getFanLevelArray", "getItemBackgroundRes", "list", "Lcom/xiaomi/smarthome/core/entity/IDevice;", "item", "Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "getMultiSwitchRes", "device", "getProp", "getPropKey", "getPropValue", "getProps", "getSweetRobotStatus", "getSwitchStatusList", "getSwitchStatusText", "getTargetTemperature", "getTotalShifts", "(Lcom/xiaomi/miot/support/category/DeviceInfoWrapper;)Ljava/lang/Integer;", "getValueFormat", "getValueRange", "Lcom/xiaomi/miot/support/category/ValueRange;", "getValueType", "hasNoPm25", "hasPm25", IotDevicesProvisionRoomSettingCompleteActivity.DEVICE_INFO, "isBool", "isFloat", "isInt", "isSeekViewCanDrag", "isString", "isValidTotalShifts", "totalShifts", "(Ljava/lang/Integer;)Z", "seekViewVolume2Value", SchemaActivity.KEY_VOLUME, "setPm25", "", "tv", "Landroid/widget/TextView;", "setPmText", "pmStr", "setPropValue", "value2SeekViewVolume", "DataFormat", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class MiotDeviceUtils {
    @NotNull
    public static final MiotDeviceUtils INSTANCE = new MiotDeviceUtils();
    @NotNull
    public static final String TAG = "MiotDeviceUtils:";

    private MiotDeviceUtils() {
    }

    @JvmStatic
    @NotNull
    public static final String getSwitchStatusText(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        String str;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        DeviceItemInfo devicePropInfo = deviceInfoWrapper.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "deviceInfoWrapper.devicePropInfo");
        Map<String, List<DevicePropertyBriefInfo>> properties = devicePropInfo.getProperties();
        int i = 0;
        if (properties.containsKey(PropDic.P_ON.getProp())) {
            StringBuilder sb = new StringBuilder();
            if (properties.get(PropDic.P_ON.getProp()) != null) {
                List<DevicePropertyBriefInfo> list = properties.get(PropDic.P_ON.getProp());
                Intrinsics.checkNotNull(list);
                for (DevicePropertyBriefInfo prop : list) {
                    Intrinsics.checkNotNullExpressionValue(prop, "prop");
                    if (prop.getPropValue() != null) {
                        DevicePropValue propValue = prop.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue, "prop.propValue");
                        if (isBool(propValue.getFormat())) {
                            DevicePropValue propValue2 = prop.getPropValue();
                            Intrinsics.checkNotNullExpressionValue(propValue2, "prop.propValue");
                            if (propValue2.getValue() == null) {
                                DevicePropValue propValue3 = prop.getPropValue();
                                Intrinsics.checkNotNullExpressionValue(propValue3, "prop.propValue");
                                str = propValue3.getValueStr();
                                Intrinsics.checkNotNullExpressionValue(str, "prop.propValue.valueStr");
                            } else {
                                DevicePropValue propValue4 = prop.getPropValue();
                                Intrinsics.checkNotNullExpressionValue(propValue4, "prop.propValue");
                                str = propValue4.getValue().toString();
                            }
                            if (Intrinsics.areEqual("true", str)) {
                                i++;
                            }
                        }
                    }
                }
                if (i > 0) {
                    Context app2 = Proxies.Instance.getApp();
                    Intrinsics.checkNotNull(app2);
                    sb.append(app2.getResources().getString(R.string.opened));
                    sb.append(StringUtils.SPACE);
                    sb.append(i);
                } else {
                    Context app3 = Proxies.Instance.getApp();
                    Intrinsics.checkNotNull(app3);
                    sb.append(app3.getResources().getString(R.string.device_status_off));
                }
            } else {
                L.smarthome.e("MiotDeviceUtils:%s  prop '[on]' is null'", deviceInfoWrapper.getDeviceInfo().deviceName);
            }
            String sb2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(sb2, "text.toString()");
            return sb2;
        }
        L.smarthome.e("MiotDeviceUtils:%s has no on props", deviceInfoWrapper.getDeviceInfo().deviceName);
        Context app4 = Proxies.Instance.getApp();
        Intrinsics.checkNotNull(app4);
        String string = app4.getResources().getString(R.string.device_status_offline);
        Intrinsics.checkNotNullExpressionValue(string, "Proxies.App!!.resources.…ng.device_status_offline)");
        return string;
    }

    @JvmStatic
    @NotNull
    public static final List<Boolean> getSwitchStatusList(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        String str;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        DeviceItemInfo devicePropInfo = deviceInfoWrapper.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "deviceInfoWrapper.devicePropInfo");
        Map<String, List<DevicePropertyBriefInfo>> properties = devicePropInfo.getProperties();
        ArrayList arrayList = new ArrayList();
        if (!properties.containsKey(PropDic.P_ON.getProp())) {
            L.smarthome.e("MiotDeviceUtils:%s has no on props", deviceInfoWrapper.getDeviceInfo().deviceName);
        } else if (properties.get(PropDic.P_ON.getProp()) != null) {
            List<DevicePropertyBriefInfo> list = properties.get(PropDic.P_ON.getProp());
            Intrinsics.checkNotNull(list);
            for (DevicePropertyBriefInfo prop : list) {
                Intrinsics.checkNotNullExpressionValue(prop, "prop");
                if (prop.getPropValue() != null) {
                    DevicePropValue propValue = prop.getPropValue();
                    Intrinsics.checkNotNullExpressionValue(propValue, "prop.propValue");
                    if (isBool(propValue.getFormat())) {
                        DevicePropValue propValue2 = prop.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue2, "prop.propValue");
                        if (propValue2.getValue() == null) {
                            DevicePropValue propValue3 = prop.getPropValue();
                            Intrinsics.checkNotNullExpressionValue(propValue3, "prop.propValue");
                            str = propValue3.getValueStr();
                            Intrinsics.checkNotNullExpressionValue(str, "prop.propValue.valueStr");
                        } else {
                            DevicePropValue propValue4 = prop.getPropValue();
                            Intrinsics.checkNotNullExpressionValue(propValue4, "prop.propValue");
                            str = propValue4.getValue().toString();
                        }
                        arrayList.add(Boolean.valueOf(deviceInfoWrapper.getDeviceInfo().isOnline && Intrinsics.areEqual("true", str)));
                    }
                }
            }
        } else {
            L.smarthome.e("MiotDeviceUtils:%s  prop '[on]' is null'", deviceInfoWrapper.getDeviceInfo().deviceName);
        }
        return arrayList;
    }

    @NotNull
    public final String seekViewVolume2Value(double d, @NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        DevicePropertyBriefInfo devicePropertyBriefInfo = props != null ? props.get(0) : null;
        if (!(devicePropertyBriefInfo == null || devicePropertyBriefInfo.getPropValue() == null)) {
            DevicePropValue propValue = devicePropertyBriefInfo.getPropValue();
            Intrinsics.checkNotNullExpressionValue(propValue, "targetProp.propValue");
            if (propValue.getValueConstrain() != null) {
                DevicePropValue propValue2 = devicePropertyBriefInfo.getPropValue();
                Intrinsics.checkNotNullExpressionValue(propValue2, "targetProp.propValue");
                ValueConstrain valueConstrain = propValue2.getValueConstrain();
                Intrinsics.checkNotNullExpressionValue(valueConstrain, "targetProp.propValue.valueConstrain");
                if (valueConstrain.getValueType() == 1) {
                    DevicePropValue propValue3 = devicePropertyBriefInfo.getPropValue();
                    Intrinsics.checkNotNullExpressionValue(propValue3, "targetProp.propValue");
                    ValueConstrain valueConstrain2 = propValue3.getValueConstrain();
                    Intrinsics.checkNotNullExpressionValue(valueConstrain2, "targetProp.propValue.valueConstrain");
                    ValueRange valueRange = valueConstrain2.getValueRange();
                    Intrinsics.checkNotNullExpressionValue(valueRange, "targetProp.propValue.valueConstrain.valueRange");
                    String maxValue = valueRange.getMaxValue();
                    Intrinsics.checkNotNullExpressionValue(maxValue, "targetProp.propValue.val…train.valueRange.maxValue");
                    double parseDouble = Double.parseDouble(maxValue);
                    DevicePropValue propValue4 = devicePropertyBriefInfo.getPropValue();
                    Intrinsics.checkNotNullExpressionValue(propValue4, "targetProp.propValue");
                    ValueConstrain valueConstrain3 = propValue4.getValueConstrain();
                    Intrinsics.checkNotNullExpressionValue(valueConstrain3, "targetProp.propValue.valueConstrain");
                    ValueRange valueRange2 = valueConstrain3.getValueRange();
                    Intrinsics.checkNotNullExpressionValue(valueRange2, "targetProp.propValue.valueConstrain.valueRange");
                    String minValue = valueRange2.getMinValue();
                    Intrinsics.checkNotNullExpressionValue(minValue, "targetProp.propValue.val…train.valueRange.minValue");
                    double parseDouble2 = Double.parseDouble(minValue);
                    return String.valueOf(MathKt.roundToInt((d * (parseDouble - parseDouble2)) + parseDouble2));
                }
            }
        }
        Logger logger = L.smarthome;
        StringBuilder sb = new StringBuilder();
        sb.append("MiotDeviceUtils: ");
        ModelInfo modelInfo = deviceInfoWrapper.getModelInfo();
        Intrinsics.checkNotNullExpressionValue(modelInfo, "deviceInfoWrapper.modelInfo");
        sb.append(modelInfo.getName());
        sb.append(" get ");
        sb.append(propDic.getProp());
        sb.append(" props error,");
        sb.append(devicePropertyBriefInfo);
        logger.e(sb.toString());
        return "";
    }

    @JvmStatic
    public static final double value2SeekViewVolume(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        String str;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        DevicePropertyBriefInfo devicePropertyBriefInfo = props != null ? props.get(0) : null;
        if (devicePropertyBriefInfo == null || devicePropertyBriefInfo.getPropValue() == null) {
            Logger logger = L.smarthome;
            StringBuilder sb = new StringBuilder();
            sb.append("MiotDeviceUtils: ");
            ModelInfo modelInfo = deviceInfoWrapper.getModelInfo();
            Intrinsics.checkNotNullExpressionValue(modelInfo, "deviceInfoWrapper.modelInfo");
            sb.append(modelInfo.getName());
            sb.append(" has no ");
            sb.append(propDic.getProp());
            sb.append(" props,");
            sb.append(devicePropertyBriefInfo);
            logger.e(sb.toString());
            return 0.0d;
        }
        DevicePropValue propValue = devicePropertyBriefInfo.getPropValue();
        Intrinsics.checkNotNullExpressionValue(propValue, "targetProp.propValue");
        if (propValue.getValue() != null) {
            DevicePropValue propValue2 = devicePropertyBriefInfo.getPropValue();
            Intrinsics.checkNotNullExpressionValue(propValue2, "targetProp.propValue");
            if (!propValue2.getValue().equals("")) {
                DevicePropValue propValue3 = devicePropertyBriefInfo.getPropValue();
                Intrinsics.checkNotNullExpressionValue(propValue3, "targetProp.propValue");
                str = propValue3.getValue().toString();
                if (str != null || str.equals("")) {
                    return 0.0d;
                }
                DevicePropValue propValue4 = devicePropertyBriefInfo.getPropValue();
                Intrinsics.checkNotNullExpressionValue(propValue4, "targetProp.propValue");
                if (propValue4.getValueConstrain() != null) {
                    DevicePropValue propValue5 = devicePropertyBriefInfo.getPropValue();
                    Intrinsics.checkNotNullExpressionValue(propValue5, "targetProp.propValue");
                    ValueConstrain valueConstrain = propValue5.getValueConstrain();
                    Intrinsics.checkNotNullExpressionValue(valueConstrain, "targetProp.propValue.valueConstrain");
                    if (valueConstrain.getValueType() == 1) {
                        DevicePropValue propValue6 = devicePropertyBriefInfo.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue6, "targetProp.propValue");
                        ValueConstrain valueConstrain2 = propValue6.getValueConstrain();
                        Intrinsics.checkNotNullExpressionValue(valueConstrain2, "targetProp.propValue.valueConstrain");
                        ValueRange valueRange = valueConstrain2.getValueRange();
                        Intrinsics.checkNotNullExpressionValue(valueRange, "targetProp.propValue.valueConstrain.valueRange");
                        String maxValue = valueRange.getMaxValue();
                        Intrinsics.checkNotNullExpressionValue(maxValue, "targetProp.propValue.val…train.valueRange.maxValue");
                        double parseDouble = Double.parseDouble(maxValue);
                        DevicePropValue propValue7 = devicePropertyBriefInfo.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue7, "targetProp.propValue");
                        ValueConstrain valueConstrain3 = propValue7.getValueConstrain();
                        Intrinsics.checkNotNullExpressionValue(valueConstrain3, "targetProp.propValue.valueConstrain");
                        ValueRange valueRange2 = valueConstrain3.getValueRange();
                        Intrinsics.checkNotNullExpressionValue(valueRange2, "targetProp.propValue.valueConstrain.valueRange");
                        String minValue = valueRange2.getMinValue();
                        Intrinsics.checkNotNullExpressionValue(minValue, "targetProp.propValue.val…train.valueRange.minValue");
                        double parseDouble2 = Double.parseDouble(minValue);
                        return (Double.parseDouble(str) - parseDouble2) / (parseDouble - parseDouble2);
                    }
                    Logger logger2 = L.smarthome;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("MiotDeviceUtils: ");
                    ModelInfo modelInfo2 = deviceInfoWrapper.getModelInfo();
                    Intrinsics.checkNotNullExpressionValue(modelInfo2, "deviceInfoWrapper.modelInfo");
                    sb2.append(modelInfo2.getName());
                    sb2.append(" valueConstrain is null");
                    logger2.w(sb2.toString());
                } else {
                    Logger logger3 = L.smarthome;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("MiotDeviceUtils: ");
                    ModelInfo modelInfo3 = deviceInfoWrapper.getModelInfo();
                    Intrinsics.checkNotNullExpressionValue(modelInfo3, "deviceInfoWrapper.modelInfo");
                    sb3.append(modelInfo3.getName());
                    sb3.append(" has no valueConstrain");
                    logger3.w(sb3.toString());
                }
                return 0.0d;
            }
        }
        DevicePropValue propValue8 = devicePropertyBriefInfo.getPropValue();
        Intrinsics.checkNotNullExpressionValue(propValue8, "targetProp.propValue");
        str = propValue8.getValueStr();
        if (str != null) {
        }
        return 0.0d;
    }

    @JvmStatic
    public static final boolean isBool(@Nullable String str) {
        return Intrinsics.areEqual(DataFormat.BOOL.getFormat(), str);
    }

    @JvmStatic
    public static final boolean isInt(@Nullable String str) {
        return Intrinsics.areEqual(DataFormat.INT8.getFormat(), str) || Intrinsics.areEqual(DataFormat.INT16.getFormat(), str) || Intrinsics.areEqual(DataFormat.INT32.getFormat(), str) || Intrinsics.areEqual(DataFormat.INT64.getFormat(), str) || Intrinsics.areEqual(DataFormat.UINT8.getFormat(), str) || Intrinsics.areEqual(DataFormat.UINT16.getFormat(), str) || Intrinsics.areEqual(DataFormat.UINT32.getFormat(), str);
    }

    @JvmStatic
    public static final boolean isFloat(@Nullable String str) {
        return Intrinsics.areEqual(DataFormat.FLOAT.getFormat(), str);
    }

    @JvmStatic
    public static final boolean isString(@Nullable String str) {
        return Intrinsics.areEqual(DataFormat.STRING.getFormat(), str);
    }

    @JvmStatic
    @Nullable
    public static final Double getBrightness(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        DevicePropertyBriefInfo devicePropertyBriefInfo2;
        DevicePropValue propValue2;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        DeviceItemInfo devicePropInfo = deviceInfoWrapper.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "deviceInfoWrapper.devicePropInfo");
        List<DevicePropertyBriefInfo> list = devicePropInfo.getProperties().get(PropDic.P_BRIGHTNESS.getProp());
        Object obj = null;
        if (((list == null || (devicePropertyBriefInfo2 = list.get(0)) == null || (propValue2 = devicePropertyBriefInfo2.getPropValue()) == null) ? null : propValue2.getValue()) != null) {
            DevicePropertyBriefInfo devicePropertyBriefInfo3 = list.get(0);
            Intrinsics.checkNotNullExpressionValue(devicePropertyBriefInfo3, "brightnessProp[0]");
            DevicePropValue propValue3 = devicePropertyBriefInfo3.getPropValue();
            if (propValue3 != null) {
                obj = propValue3.getValue();
            }
            return StringsKt.toDoubleOrNull(String.valueOf(obj));
        }
        if (!(list == null || (devicePropertyBriefInfo = list.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null)) {
            obj = propValue.getValueStr();
        }
        if (obj != null) {
            DevicePropertyBriefInfo devicePropertyBriefInfo4 = list.get(0);
            Intrinsics.checkNotNullExpressionValue(devicePropertyBriefInfo4, "brightnessProp[0]");
            DevicePropValue propValue4 = devicePropertyBriefInfo4.getPropValue();
            Intrinsics.checkNotNullExpressionValue(propValue4, "brightnessProp[0].propValue");
            String valueStr = propValue4.getValueStr();
            Intrinsics.checkNotNullExpressionValue(valueStr, "brightnessProp[0].propValue.valueStr");
            return StringsKt.toDoubleOrNull(valueStr);
        }
        Logger logger = L.smarthome;
        StringBuilder sb = new StringBuilder();
        sb.append("MiotDeviceUtils: ");
        ModelInfo modelInfo = deviceInfoWrapper.getModelInfo();
        Intrinsics.checkNotNullExpressionValue(modelInfo, "deviceInfoWrapper.modelInfo");
        sb.append(modelInfo.getName());
        sb.append(" has no ");
        sb.append(PropDic.P_TARGET_TEMP.getProp());
        sb.append(" props");
        logger.e(sb.toString());
        return Double.valueOf(0.0d);
    }

    @JvmStatic
    @Nullable
    public static final DevicePropertyBriefInfo getBrightnessProp(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        DeviceItemInfo devicePropInfo = deviceInfoWrapper.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "deviceInfoWrapper.devicePropInfo");
        List<DevicePropertyBriefInfo> list = devicePropInfo.getProperties().get(PropDic.P_BRIGHTNESS.getProp());
        if (list != null) {
            return list.get(0);
        }
        return null;
    }

    @JvmStatic
    @Nullable
    public static final Double getTargetTemperature(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        DevicePropertyBriefInfo devicePropertyBriefInfo2;
        DevicePropValue propValue2;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, PropDic.P_TARGET_TEMP);
        if (((props == null || (devicePropertyBriefInfo2 = props.get(0)) == null || (propValue2 = devicePropertyBriefInfo2.getPropValue()) == null) ? null : propValue2.getValue()) != null) {
            DevicePropValue propValue3 = props.get(0).getPropValue();
            Intrinsics.checkNotNullExpressionValue(propValue3, "targetTemp[0].propValue");
            return StringsKt.toDoubleOrNull(propValue3.getValue().toString());
        }
        if (((props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null) ? null : propValue.getValueStr()) == null) {
            return null;
        }
        DevicePropValue propValue4 = props.get(0).getPropValue();
        Intrinsics.checkNotNullExpressionValue(propValue4, "targetTemp[0].propValue");
        String valueStr = propValue4.getValueStr();
        Intrinsics.checkNotNullExpressionValue(valueStr, "targetTemp[0].propValue.valueStr");
        return StringsKt.toDoubleOrNull(valueStr);
    }

    public final int getValueType(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        ValueConstrain valueConstrain;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        if (props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null || (valueConstrain = propValue.getValueConstrain()) == null) {
            return 0;
        }
        return valueConstrain.getValueType();
    }

    @JvmStatic
    @Nullable
    public static final ValueRange getValueRange(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        ValueConstrain valueConstrain;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        if (props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null || (valueConstrain = propValue.getValueConstrain()) == null) {
            return null;
        }
        return valueConstrain.getValueRange();
    }

    @JvmStatic
    @Nullable
    public static final List<String> getEnumValues(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        ValueConstrain valueConstrain;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        if (props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null || (valueConstrain = propValue.getValueConstrain()) == null) {
            return null;
        }
        return valueConstrain.getEnumValue();
    }

    @JvmStatic
    @NotNull
    public static final String getValueFormat(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        String format;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        return (props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null || (format = propValue.getFormat()) == null) ? "" : format;
    }

    @JvmStatic
    @Nullable
    public static final String getSweetRobotStatus(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        DevicePropertyBriefInfo devicePropertyBriefInfo2;
        DevicePropValue propValue2;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, PropDic.P_STATUS);
        if (((props == null || (devicePropertyBriefInfo2 = props.get(0)) == null || (propValue2 = devicePropertyBriefInfo2.getPropValue()) == null) ? null : propValue2.getValue()) != null) {
            DevicePropValue propValue3 = props.get(0).getPropValue();
            Intrinsics.checkNotNullExpressionValue(propValue3, "targetTemp[0].propValue");
            return propValue3.getValue().toString();
        }
        if (((props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null) ? null : propValue.getValueStr()) == null) {
            return null;
        }
        DevicePropValue propValue4 = props.get(0).getPropValue();
        Intrinsics.checkNotNullExpressionValue(propValue4, "targetTemp[0].propValue");
        return propValue4.getValueStr();
    }

    @Nullable
    public final Integer getTotalShifts(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        ValueConstrain valueConstrain;
        DevicePropValue propValue2;
        ValueConstrain valueConstrain2;
        List<String> enumValue;
        DevicePropertyBriefInfo devicePropertyBriefInfo2;
        DevicePropValue propValue3;
        ValueConstrain valueConstrain3;
        ValueConstrain valueConstrain4;
        ValueRange valueRange;
        String step;
        Integer intOrNull;
        ValueConstrain valueConstrain5;
        ValueRange valueRange2;
        ValueConstrain valueConstrain6;
        ValueRange valueRange3;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, PropDic.P_FAN_LEVEL);
        int i = 0;
        if (props != null && (devicePropertyBriefInfo2 = props.get(0)) != null && (propValue3 = devicePropertyBriefInfo2.getPropValue()) != null && (valueConstrain3 = propValue3.getValueConstrain()) != null && valueConstrain3.getValueType() == 1) {
            DevicePropValue propValue4 = props.get(0).getPropValue();
            String maxValue = (propValue4 == null || (valueConstrain6 = propValue4.getValueConstrain()) == null || (valueRange3 = valueConstrain6.getValueRange()) == null) ? null : valueRange3.getMaxValue();
            DevicePropValue propValue5 = props.get(0).getPropValue();
            String minValue = (propValue5 == null || (valueConstrain5 = propValue5.getValueConstrain()) == null || (valueRange2 = valueConstrain5.getValueRange()) == null) ? null : valueRange2.getMinValue();
            DevicePropValue propValue6 = props.get(0).getPropValue();
            int intValue = (propValue6 == null || (valueConstrain4 = propValue6.getValueConstrain()) == null || (valueRange = valueConstrain4.getValueRange()) == null || (step = valueRange.getStep()) == null || (intOrNull = StringsKt.toIntOrNull(step)) == null) ? 1 : intOrNull.intValue();
            if (maxValue == null) {
                return null;
            }
            int parseInt = Integer.parseInt(maxValue);
            if (minValue != null) {
                i = Integer.parseInt(minValue);
            }
            return Integer.valueOf(((parseInt - i) / intValue) + 1);
        } else if (props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null || (valueConstrain = propValue.getValueConstrain()) == null || valueConstrain.getValueType() != 2 || (propValue2 = props.get(0).getPropValue()) == null || (valueConstrain2 = propValue2.getValueConstrain()) == null || (enumValue = valueConstrain2.getEnumValue()) == null) {
            return null;
        } else {
            return Integer.valueOf(enumValue.size());
        }
    }

    @NotNull
    public final List<Integer> getFanLevelArray(@NotNull DeviceInfoWrapper deviceInfoWrapper) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        ValueConstrain valueConstrain;
        DevicePropValue propValue2;
        ValueConstrain valueConstrain2;
        List<String> enumValue;
        DevicePropertyBriefInfo devicePropertyBriefInfo2;
        DevicePropValue propValue3;
        ValueConstrain valueConstrain3;
        DevicePropValue propValue4;
        ValueConstrain valueConstrain4;
        ValueRange valueRange;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, PropDic.P_FAN_LEVEL);
        ArrayList arrayList = new ArrayList();
        Integer totalShifts = getTotalShifts(deviceInfoWrapper);
        if (totalShifts != null) {
            totalShifts.intValue();
            if (props != null && (devicePropertyBriefInfo2 = props.get(0)) != null && (propValue3 = devicePropertyBriefInfo2.getPropValue()) != null && (valueConstrain3 = propValue3.getValueConstrain()) != null && valueConstrain3.getValueType() == 1 && (propValue4 = props.get(0).getPropValue()) != null && (valueConstrain4 = propValue4.getValueConstrain()) != null && (valueRange = valueConstrain4.getValueRange()) != null) {
                String maxValue = valueRange.getMaxValue();
                Intrinsics.checkNotNullExpressionValue(maxValue, "maxValue");
                int parseInt = Integer.parseInt(maxValue);
                String minValue = valueRange.getMinValue();
                Intrinsics.checkNotNullExpressionValue(minValue, "minValue");
                int parseInt2 = Integer.parseInt(minValue);
                String step = valueRange.getStep();
                Intrinsics.checkNotNullExpressionValue(step, "step");
                IntProgression step2 = RangesKt.step(new IntRange(parseInt2, parseInt), Integer.parseInt(step));
                int first = step2.getFirst();
                int last = step2.getLast();
                int step3 = step2.getStep();
                if (step3 < 0 ? first >= last : first <= last) {
                    while (true) {
                        arrayList.add(Integer.valueOf(first));
                        if (first == last) {
                            break;
                        }
                        first += step3;
                    }
                }
            }
            if (!(props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null || (valueConstrain = propValue.getValueConstrain()) == null || valueConstrain.getValueType() != 2 || (propValue2 = props.get(0).getPropValue()) == null || (valueConstrain2 = propValue2.getValueConstrain()) == null || (enumValue = valueConstrain2.getEnumValue()) == null)) {
                for (String it : enumValue) {
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    arrayList.add(Integer.valueOf(Integer.parseInt(it)));
                }
            }
        }
        return arrayList;
    }

    @JvmStatic
    public static final void setPropValue(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic, @NotNull String value) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        Intrinsics.checkNotNullParameter(value, "value");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        if ((props != null ? props.get(0) : null) == null || props.get(0).getPropValue() == null) {
            L.smarthome.e("setPropValue  prop is null or propValue is null");
            return;
        }
        DevicePropertyBriefInfo devicePropertyBriefInfo = props.get(0);
        DevicePropValue propValue = devicePropertyBriefInfo.getPropValue();
        if (propValue != null) {
            propValue.setValue(value);
        }
        DevicePropValue propValue2 = devicePropertyBriefInfo.getPropValue();
        if (propValue2 != null) {
            propValue2.setValueStr(value);
        }
    }

    @JvmStatic
    @Nullable
    public static final String getPropValue(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        DevicePropValue propValue;
        DevicePropertyBriefInfo devicePropertyBriefInfo2;
        DevicePropValue propValue2;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        if (((props == null || (devicePropertyBriefInfo2 = props.get(0)) == null || (propValue2 = devicePropertyBriefInfo2.getPropValue()) == null) ? null : propValue2.getValue()) != null) {
            DevicePropValue propValue3 = props.get(0).getPropValue();
            Intrinsics.checkNotNullExpressionValue(propValue3, "prop[0].propValue");
            return propValue3.getValue().toString();
        }
        if (((props == null || (devicePropertyBriefInfo = props.get(0)) == null || (propValue = devicePropertyBriefInfo.getPropValue()) == null) ? null : propValue.getValueStr()) != null) {
            DevicePropValue propValue4 = props.get(0).getPropValue();
            Intrinsics.checkNotNullExpressionValue(propValue4, "prop[0].propValue");
            return propValue4.getValueStr();
        }
        Logger logger = L.smarthome;
        logger.e("MiotDeviceUtils: data error," + deviceInfoWrapper.getDeviceInfo().deviceName + " has no propValue for " + propDic.getProp());
        return null;
    }

    @JvmStatic
    @Nullable
    public static final String getPropKey(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        if (props == null || (devicePropertyBriefInfo = props.get(0)) == null) {
            return null;
        }
        return devicePropertyBriefInfo.getKey();
    }

    @JvmStatic
    @Nullable
    public static final String getExactValue(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        String propValue = getPropValue(deviceInfoWrapper, propDic);
        String valueFormat = getValueFormat(deviceInfoWrapper, propDic);
        if (isBool(valueFormat)) {
            return String.valueOf(Boolean.parseBoolean(propValue));
        }
        Integer num = null;
        if (isFloat(valueFormat)) {
            if (propValue != null) {
                return String.valueOf(Float.parseFloat(propValue));
            }
            return null;
        } else if (isInt(valueFormat)) {
            if (propValue != null) {
                num = StringsKt.toIntOrNull(propValue);
            }
            return String.valueOf(num);
        } else if (isString(valueFormat)) {
            return propValue;
        } else {
            return null;
        }
    }

    @JvmStatic
    @Nullable
    public static final String getExactValue(@Nullable String str, @Nullable String str2) {
        if (isBool(str2)) {
            return String.valueOf(Boolean.parseBoolean(str));
        }
        Integer num = null;
        if (isFloat(str2)) {
            if (str != null) {
                return String.valueOf(Float.parseFloat(str));
            }
            return null;
        } else if (isInt(str2)) {
            if (str != null) {
                num = StringsKt.toIntOrNull(str);
            }
            return String.valueOf(num);
        } else if (isString(str2)) {
            return str;
        } else {
            return null;
        }
    }

    @BindingAdapter({"envData"})
    @JvmStatic
    public static final void setPm25(@NotNull TextView tv, @NotNull DeviceInfoWrapper deviceInfoWrapper) {
        DevicePropertyBriefInfo devicePropertyBriefInfo;
        Intrinsics.checkNotNullParameter(tv, "tv");
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        try {
            List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, PropDic.P_PM25);
            String str = null;
            DevicePropValue propValue = (props == null || (devicePropertyBriefInfo = props.get(0)) == null) ? null : devicePropertyBriefInfo.getPropValue();
            if ((propValue != null ? propValue.getValue() : null) != null) {
                INSTANCE.a(propValue.getValue().toString(), tv, deviceInfoWrapper.getDeviceInfo().currentStatus);
                return;
            }
            if (propValue != null) {
                str = propValue.getValueStr();
            }
            if (str != null) {
                String pmStr = propValue.getValueStr();
                MiotDeviceUtils miotDeviceUtils = INSTANCE;
                Intrinsics.checkNotNullExpressionValue(pmStr, "pmStr");
                miotDeviceUtils.a(pmStr, tv, deviceInfoWrapper.getDeviceInfo().currentStatus);
                return;
            }
            Logger logger = L.smarthome;
            StringBuilder sb = new StringBuilder();
            sb.append("MiotDeviceUtils:  setPm25 ");
            ModelInfo modelInfo = deviceInfoWrapper.getModelInfo();
            Intrinsics.checkNotNullExpressionValue(modelInfo, "deviceInfoWrapper.modelInfo");
            sb.append(modelInfo.getName());
            sb.append(" has no pm25 props");
            logger.e(sb.toString());
        } catch (Exception e) {
            Logger logger2 = L.smarthome;
            logger2.e("MiotDeviceUtils: setPm25 error " + e);
        }
    }

    private final void a(String str, TextView textView, boolean z) {
        String str2 = str;
        int parseInt = (TextUtils.isEmpty(str2) || !TextUtils.isDigitsOnly(str2)) ? 0 : Integer.parseInt(str);
        if (parseInt <= 75) {
            SpanUtils.with(textView).append(parseInt < 10 ? "00" : "0").setForegroundColor(ContextCompat.getColor(textView.getContext(), z ? R.color.pm25_green_20 : R.color.pm25_off_line_color_light)).append(str2).setForegroundColor(ContextCompat.getColor(textView.getContext(), z ? R.color.pm25_green : R.color.pm25_off_line_color)).create();
        } else if (76 <= parseInt && 99 >= parseInt) {
            SpanUtils.with(textView).append("0").setForegroundColor(ContextCompat.getColor(textView.getContext(), z ? R.color.pm25_orange_20 : R.color.pm25_off_line_color_light)).append(str2).setForegroundColor(ContextCompat.getColor(textView.getContext(), z ? R.color.pm25_orange : R.color.pm25_off_line_color)).create();
        } else if (100 <= parseInt && 150 >= parseInt) {
            SpanUtils.with(textView).append(str2).setForegroundColor(ContextCompat.getColor(textView.getContext(), z ? R.color.pm25_orange : R.color.pm25_off_line_color)).create();
        } else if (parseInt > 150) {
            SpanUtils.with(textView).append(str2).setForegroundColor(ContextCompat.getColor(textView.getContext(), z ? R.color.pm25_red : R.color.pm25_off_line_color)).create();
        }
    }

    @JvmStatic
    private static final boolean a(DeviceInfoWrapper deviceInfoWrapper) {
        DevicePropValue propValue;
        DevicePropValue propValue2;
        DevicePropertyBriefInfo prop = getProp(deviceInfoWrapper, PropDic.P_PM25);
        String str = null;
        if (((prop == null || (propValue2 = prop.getPropValue()) == null) ? null : propValue2.getValue()) == null) {
            if (!(prop == null || (propValue = prop.getPropValue()) == null)) {
                str = propValue.getValueStr();
            }
            String str2 = str;
            if (str2 == null || str2.length() == 0) {
                return true;
            }
        }
        return false;
    }

    @JvmStatic
    public static final boolean hasPm25(@NotNull DeviceInfoWrapper deviceInfo) {
        Intrinsics.checkNotNullParameter(deviceInfo, "deviceInfo");
        return !a(deviceInfo);
    }

    @JvmStatic
    @Nullable
    public static final List<DevicePropertyBriefInfo> getProps(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        DeviceItemInfo devicePropInfo = deviceInfoWrapper.getDevicePropInfo();
        Intrinsics.checkNotNullExpressionValue(devicePropInfo, "deviceInfoWrapper.devicePropInfo");
        return devicePropInfo.getProperties().get(propDic.getProp());
    }

    @JvmStatic
    @Nullable
    public static final DevicePropertyBriefInfo getProp(@NotNull DeviceInfoWrapper deviceInfoWrapper, @NotNull PropDic propDic) {
        Intrinsics.checkNotNullParameter(deviceInfoWrapper, "deviceInfoWrapper");
        Intrinsics.checkNotNullParameter(propDic, "propDic");
        List<DevicePropertyBriefInfo> props = getProps(deviceInfoWrapper, propDic);
        if (props != null) {
            return props.get(0);
        }
        return null;
    }

    @JvmStatic
    public static final int getItemBackgroundRes(@Nullable List<? extends IDevice> list, @NotNull DeviceInfoBean item, @NotNull String categoryName) {
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        boolean z = item.getDeviceInfoWrapper().getDeviceInfo().isOnline;
        if (list == null) {
            return z ? R.drawable.shape_widgets_online : R.drawable.shape_widgets_offline;
        }
        int indexOf = list.indexOf(item);
        switch (list.size()) {
            case 1:
                if (z) {
                    return R.drawable.shape_widgets_online;
                }
                return R.drawable.shape_widgets_offline;
            case 2:
                if (z) {
                    if (indexOf == 0) {
                        return R.drawable.shape_round_corners_left_half_online;
                    }
                    return R.drawable.shape_round_corners_right_half_online;
                } else if (indexOf == 0) {
                    return R.drawable.shape_round_corners_left_half_offline;
                } else {
                    return R.drawable.shape_round_corners_right_half_offline;
                }
            default:
                if (z) {
                    if (indexOf == 0) {
                        return R.drawable.shape_round_corners_left_half_online;
                    }
                    if (indexOf == list.size() - 1) {
                        return R.drawable.shape_round_corners_right_half_online;
                    }
                    return R.drawable.shape_middle_item_online;
                } else if (indexOf == 0) {
                    return R.drawable.shape_round_corners_left_half_offline;
                } else {
                    if (indexOf == list.size() - 1) {
                        return R.drawable.shape_round_corners_right_half_offline;
                    }
                    return R.drawable.shape_middle_item_offline;
                }
        }
    }

    @JvmStatic
    public static final int getMultiSwitchRes(@NotNull DeviceInfoWrapper device) {
        Intrinsics.checkNotNullParameter(device, "device");
        List<DevicePropertyBriefInfo> props = getProps(device, PropDic.P_ON);
        if (props == null) {
            L.smarthome.e("getMultiSwitchRes device has no 'on' prop");
            return INSTANCE.b(device.getDeviceInfo().isOnline, props);
        }
        long count = props.stream().filter(c.a).count();
        if (count == 2) {
            return INSTANCE.b(device.getDeviceInfo().isOnline, props);
        }
        if (count == 3) {
            return INSTANCE.a(device.getDeviceInfo().isOnline, props);
        }
        return INSTANCE.b(device.getDeviceInfo().isOnline, props);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: MiotDeviceUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/miot/support/category/DevicePropertyBriefInfo;", "kotlin.jvm.PlatformType", "test"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class c<T> implements Predicate<DevicePropertyBriefInfo> {
        public static final c a = new c();

        c() {
        }

        /* renamed from: a */
        public final boolean test(DevicePropertyBriefInfo it) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            return Intrinsics.areEqual("switch", it.getServiceTypeName());
        }
    }

    /* compiled from: MiotDeviceUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/miot/support/category/DevicePropertyBriefInfo;", "kotlin.jvm.PlatformType", "test"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class b<T> implements Predicate<DevicePropertyBriefInfo> {
        public static final b a = new b();

        b() {
        }

        /* renamed from: a */
        public final boolean test(DevicePropertyBriefInfo it) {
            String str;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.getPropValue() != null) {
                DevicePropValue propValue = it.getPropValue();
                Intrinsics.checkNotNullExpressionValue(propValue, "it.propValue");
                if (MiotDeviceUtils.isBool(propValue.getFormat())) {
                    DevicePropValue propValue2 = it.getPropValue();
                    Intrinsics.checkNotNullExpressionValue(propValue2, "it.propValue");
                    if (propValue2.getValue() == null) {
                        DevicePropValue propValue3 = it.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue3, "it.propValue");
                        str = propValue3.getValueStr();
                        Intrinsics.checkNotNullExpressionValue(str, "it.propValue.valueStr");
                    } else {
                        DevicePropValue propValue4 = it.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue4, "it.propValue");
                        str = propValue4.getValue().toString();
                    }
                    return Intrinsics.areEqual("true", str);
                }
            }
            return false;
        }
    }

    private final int a(boolean z, List<? extends DevicePropertyBriefInfo> list) {
        if (z) {
            return ((int) list.stream().filter(b.a).count()) > 0 ? R.drawable.sh_ic_switch_multi_3_online_on : R.drawable.sh_ic_switch_multi_3_online_off;
        }
        return R.drawable.sh_ic_switch_multi_3_offline;
    }

    /* compiled from: MiotDeviceUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/miot/support/category/DevicePropertyBriefInfo;", "kotlin.jvm.PlatformType", "test"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class a<T> implements Predicate<DevicePropertyBriefInfo> {
        public static final a a = new a();

        a() {
        }

        /* renamed from: a */
        public final boolean test(DevicePropertyBriefInfo it) {
            String str;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (it.getPropValue() != null) {
                DevicePropValue propValue = it.getPropValue();
                Intrinsics.checkNotNullExpressionValue(propValue, "it.propValue");
                if (MiotDeviceUtils.isBool(propValue.getFormat())) {
                    DevicePropValue propValue2 = it.getPropValue();
                    Intrinsics.checkNotNullExpressionValue(propValue2, "it.propValue");
                    if (propValue2.getValue() == null) {
                        DevicePropValue propValue3 = it.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue3, "it.propValue");
                        str = propValue3.getValueStr();
                        Intrinsics.checkNotNullExpressionValue(str, "it.propValue.valueStr");
                    } else {
                        DevicePropValue propValue4 = it.getPropValue();
                        Intrinsics.checkNotNullExpressionValue(propValue4, "it.propValue");
                        str = propValue4.getValue().toString();
                    }
                    return Intrinsics.areEqual("true", str);
                }
            }
            return false;
        }
    }

    private final int b(boolean z, List<? extends DevicePropertyBriefInfo> list) {
        Stream<? extends DevicePropertyBriefInfo> stream;
        Stream<? extends DevicePropertyBriefInfo> filter;
        if (!z) {
            return R.drawable.sh_ic_switch_multi_2_offline;
        }
        return ((list == null || (stream = list.stream()) == null || (filter = stream.filter(a.a)) == null) ? 0 : (int) filter.count()) > 0 ? R.drawable.sh_ic_switch_multi_2_online_on : R.drawable.sh_ic_switch_multi_2_online_off;
    }

    /* compiled from: MiotDeviceUtils.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013¨\u0006\u0014"}, d2 = {"Lcom/xiaomi/smarthome/core/utils/MiotDeviceUtils$DataFormat;", "", "format", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getFormat", "()Ljava/lang/String;", "setFormat", "(Ljava/lang/String;)V", "UNKNOWN", "BOOL", "UINT8", "UINT16", "UINT32", "INT8", "INT16", "INT32", "INT64", "FLOAT", "STRING", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public enum DataFormat {
        UNKNOWN("unknown"),
        BOOL("bool"),
        UINT8("uint8"),
        UINT16("uint16"),
        UINT32("uint32"),
        INT8("int8"),
        INT16("int16"),
        INT32("int32"),
        INT64("int64"),
        FLOAT("float"),
        STRING("string");
        
        @NotNull
        private String format;

        DataFormat(String str) {
            this.format = str;
        }

        @NotNull
        public final String getFormat() {
            return this.format;
        }

        public final void setFormat(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.format = str;
        }
    }

    @JvmStatic
    @DrawableRes
    public static final int getCategoryRes(@NotNull String categoryName) {
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_SOCKET.getTypeName())) {
            return R.drawable.sh_ic_category_socket_small;
        }
        if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_LIGHT.getTypeName())) {
            return R.drawable.sh_ic_category_light_small;
        }
        if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_ENV.getTypeName())) {
            return R.drawable.sh_ic_category_environment_small;
        }
        if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_MEDIA.getTypeName())) {
            return R.drawable.sh_ic_category_media_small;
        }
        if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_CURTAIN.getTypeName())) {
            return R.drawable.sh_ic_category_curtain_small;
        }
        if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_HOUSE_WORK.getTypeName())) {
            return R.drawable.sh_ic_category_housework_small;
        }
        return R.drawable.sh_ic_category_security_small;
    }

    @JvmStatic
    public static final boolean isSeekViewCanDrag(@Nullable DeviceInfoWrapper deviceInfoWrapper) {
        DeviceInfo deviceInfo;
        DeviceInfo deviceInfo2;
        if (!((deviceInfoWrapper == null || (deviceInfo2 = deviceInfoWrapper.getDeviceInfo()) == null) ? false : deviceInfo2.isOnline)) {
            return false;
        }
        return (deviceInfoWrapper == null || (deviceInfo = deviceInfoWrapper.getDeviceInfo()) == null) ? false : deviceInfo.currentStatus;
    }

    public final boolean isValidTotalShifts(@Nullable Integer num) {
        return num != null && num.intValue() > 1 && num.intValue() <= 4;
    }
}
