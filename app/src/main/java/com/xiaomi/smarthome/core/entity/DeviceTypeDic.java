package com.xiaomi.smarthome.core.entity;

import com.xiaomi.passport.ui.settings.UserAvatarUpdateActivity;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: SmartHomeBeans.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0016\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001a¨\u0006\u001b"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/DeviceTypeDic;", "", "category", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "deviceType", "", "(Ljava/lang/String;ILcom/xiaomi/smarthome/core/entity/CategoryDic;Ljava/lang/String;)V", "getCategory", "()Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "getDeviceType", "()Ljava/lang/String;", "DT_OS_OUTLET", "DT_OS_SWITCH", "DT_CURTAIN_CURTAIN", "DT_ENV_AIR_CONDITIONER", "DT_ENV_AIR_FRESH", "DT_ENV_AIR_MONITOR", "DT_ENV_AIR_PURIFIER", "DT_ENV_FAN", "DT_ENV_HEATER", "DT_ENV_HUMIDIFIER", "DT_SECURITY_CAMERA", "DT_HOUSE_WORK_AIRER", "DT_HOUSE_WORK_VACUUM", "DT_LIGHT_LIGHT", "DT_LIGHT_MIRROR", "DT_LIGHT_NIGHT_LIGHT", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public enum DeviceTypeDic {
    DT_OS_OUTLET(CategoryDic.CATEGORY_LIGHT, "outlet"),
    DT_OS_SWITCH(CategoryDic.CATEGORY_LIGHT, "switch"),
    DT_CURTAIN_CURTAIN(CategoryDic.CATEGORY_CURTAIN, "curtain"),
    DT_ENV_AIR_CONDITIONER(CategoryDic.CATEGORY_ENV, "air-conditioner"),
    DT_ENV_AIR_FRESH(CategoryDic.CATEGORY_ENV, "air-fresh"),
    DT_ENV_AIR_MONITOR(CategoryDic.CATEGORY_ENV, "air-monitor"),
    DT_ENV_AIR_PURIFIER(CategoryDic.CATEGORY_ENV, "air-purifier"),
    DT_ENV_FAN(CategoryDic.CATEGORY_ENV, "fan"),
    DT_ENV_HEATER(CategoryDic.CATEGORY_ENV, "heater"),
    DT_ENV_HUMIDIFIER(CategoryDic.CATEGORY_ENV, "humidifier"),
    DT_SECURITY_CAMERA(CategoryDic.CATEGORY_SECURITY, UserAvatarUpdateActivity.CAMERA),
    DT_HOUSE_WORK_AIRER(CategoryDic.CATEGORY_HOUSE_WORK, "airer"),
    DT_HOUSE_WORK_VACUUM(CategoryDic.CATEGORY_HOUSE_WORK, "vacuum"),
    DT_LIGHT_LIGHT(CategoryDic.CATEGORY_LIGHT, "light"),
    DT_LIGHT_MIRROR(CategoryDic.CATEGORY_LIGHT, "mirror"),
    DT_LIGHT_NIGHT_LIGHT(CategoryDic.CATEGORY_LIGHT, "night_light");
    
    @NotNull
    private final CategoryDic category;
    @NotNull
    private final String deviceType;

    DeviceTypeDic(CategoryDic categoryDic, String str) {
        this.category = categoryDic;
        this.deviceType = str;
    }

    @NotNull
    public final CategoryDic getCategory() {
        return this.category;
    }

    @NotNull
    public final String getDeviceType() {
        return this.deviceType;
    }
}
