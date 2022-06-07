package com.xiaomi.smarthome.core.entity;

import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeBeans.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\b\u0086\u0001\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0017B!\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016¨\u0006\u0018"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "", "type", "", "typeName", "", "defaultShowCount", "(Ljava/lang/String;IILjava/lang/String;I)V", "getDefaultShowCount", "()I", "getType", "getTypeName", "()Ljava/lang/String;", "CATEGORY_MEDIA", "CATEGORY_SCENE", "CATEGORY_EMPTY", "CATEGORY_LIGHT", "CATEGORY_ENV", "CATEGORY_SOCKET", "CATEGORY_SECURITY", "CATEGORY_CURTAIN", "CATEGORY_HOUSE_WORK", "CATEGORY_ENTRANCE", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public enum CategoryDic {
    CATEGORY_MEDIA(-1, "媒体", 1),
    CATEGORY_SCENE(-2, "场景", 1),
    CATEGORY_EMPTY(-3, "为空图", 1),
    CATEGORY_LIGHT(0, "照明", 3),
    CATEGORY_ENV(1, "环境", 3),
    CATEGORY_SOCKET(2, "插座开关", 3),
    CATEGORY_SECURITY(3, "监控", 3),
    CATEGORY_CURTAIN(4, "窗帘", 3),
    CATEGORY_HOUSE_WORK(5, "家务", 3),
    CATEGORY_ENTRANCE(Integer.MAX_VALUE, "类别排序", 0, 4, null);
    
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int defaultShowCount;
    private final int type;
    @NotNull
    private final String typeName;

    CategoryDic(int i, String str, int i2) {
        this.type = i;
        this.typeName = str;
        this.defaultShowCount = i2;
    }

    /* synthetic */ CategoryDic(int i, String str, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, (i3 & 4) != 0 ? 1 : i2);
    }

    public final int getDefaultShowCount() {
        return this.defaultShowCount;
    }

    public final int getType() {
        return this.type;
    }

    @NotNull
    public final String getTypeName() {
        return this.typeName;
    }

    /* compiled from: SmartHomeBeans.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0006J\u0010\u0010\n\u001a\u00020\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0006¨\u0006\u000f"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/CategoryDic$Companion;", "", "()V", "deviceShouldBeShow", "", "deviceType", "", "getDefaultShowCount", "", "categoryName", "getSweepRobotStatusText", "description", "mapToEnum", "Lcom/xiaomi/smarthome/core/entity/CategoryDic;", "shouldBeShow", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int getDefaultShowCount(@NotNull String categoryName) {
            Intrinsics.checkNotNullParameter(categoryName, "categoryName");
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_LIGHT.getTypeName())) {
                return CategoryDic.CATEGORY_LIGHT.getDefaultShowCount();
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_ENV.getTypeName())) {
                return CategoryDic.CATEGORY_ENV.getDefaultShowCount();
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_SOCKET.getTypeName())) {
                return CategoryDic.CATEGORY_SOCKET.getDefaultShowCount();
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_MEDIA.getTypeName())) {
                return CategoryDic.CATEGORY_MEDIA.getDefaultShowCount();
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_CURTAIN.getTypeName())) {
                return CategoryDic.CATEGORY_CURTAIN.getDefaultShowCount();
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_HOUSE_WORK.getTypeName())) {
                return CategoryDic.CATEGORY_HOUSE_WORK.getDefaultShowCount();
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_SECURITY.getTypeName())) {
                return CategoryDic.CATEGORY_SECURITY.getDefaultShowCount();
            }
            return 1;
        }

        @NotNull
        public final CategoryDic mapToEnum(@NotNull String categoryName) {
            Intrinsics.checkNotNullParameter(categoryName, "categoryName");
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_MEDIA.getTypeName())) {
                return CategoryDic.CATEGORY_MEDIA;
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_SOCKET.getTypeName())) {
                return CategoryDic.CATEGORY_SOCKET;
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_SECURITY.getTypeName())) {
                return CategoryDic.CATEGORY_SECURITY;
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_HOUSE_WORK.getTypeName())) {
                return CategoryDic.CATEGORY_HOUSE_WORK;
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_CURTAIN.getTypeName())) {
                return CategoryDic.CATEGORY_CURTAIN;
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_ENV.getTypeName())) {
                return CategoryDic.CATEGORY_ENV;
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_LIGHT.getTypeName())) {
                return CategoryDic.CATEGORY_LIGHT;
            }
            if (Intrinsics.areEqual(categoryName, CategoryDic.CATEGORY_ENTRANCE.getTypeName())) {
                return CategoryDic.CATEGORY_ENTRANCE;
            }
            return CategoryDic.CATEGORY_SCENE;
        }

        public final boolean shouldBeShow(@NotNull String categoryName) {
            Intrinsics.checkNotNullParameter(categoryName, "categoryName");
            return Intrinsics.areEqual(CategoryDic.CATEGORY_LIGHT.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_MEDIA.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_ENV.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_LIGHT.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_SOCKET.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_SECURITY.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_HOUSE_WORK.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_CURTAIN.getTypeName(), categoryName) || Intrinsics.areEqual(CategoryDic.CATEGORY_EMPTY.getTypeName(), categoryName);
        }

        @NotNull
        public final String getSweepRobotStatusText(@Nullable String str) {
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_PAUSED.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_PAUSED.getStatus())) {
                return SweepRobotStatus.MODE_PAUSED.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_CHARGING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_CHARGING.getStatus())) {
                return SweepRobotStatus.MODE_CHARGING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_BACK_CHARGING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_BACK_CHARGING.getStatus())) {
                return SweepRobotStatus.MODE_BACK_CHARGING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_SWEEPING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_SWEEPING.getStatus())) {
                return SweepRobotStatus.MODE_SWEEPING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_MOPPING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_MOPPING.getStatus())) {
                return SweepRobotStatus.MODE_MOPPING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_WASHING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_WASHING.getStatus())) {
                return SweepRobotStatus.MODE_WASHING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_SWEEPING_AND_MOPPING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_SWEEPING_AND_MOPPING.getStatus())) {
                return SweepRobotStatus.MODE_SWEEPING_AND_MOPPING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_CHARGING_COMPLETED.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_CHARGING_COMPLETED.getStatus())) {
                return SweepRobotStatus.MODE_CHARGING_COMPLETED.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_DRYING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_DRYING.getStatus())) {
                return SweepRobotStatus.MODE_DRYING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_GO_WASHING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_GO_WASHING.getStatus())) {
                return SweepRobotStatus.MODE_GO_WASHING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_UPGRADING.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_UPGRADING.getStatus())) {
                return SweepRobotStatus.MODE_UPGRADING.getText();
            }
            if (Intrinsics.areEqual(str, SweepRobotStatus.MODE_IN_THE_WASH.getDescription()) || Intrinsics.areEqual(str, SweepRobotStatus.MODE_IN_THE_WASH.getStatus())) {
                return SweepRobotStatus.MODE_IN_THE_WASH.getText();
            }
            if (!Intrinsics.areEqual(str, SweepRobotStatus.MODE_IN_THE_DRY.getDescription()) && !Intrinsics.areEqual(str, SweepRobotStatus.MODE_IN_THE_DRY.getStatus())) {
                return (!Intrinsics.areEqual(str, SweepRobotStatus.MODE_SLEEP.getDescription()) && !Intrinsics.areEqual(str, SweepRobotStatus.MODE_SLEEP.getStatus())) ? "" : SweepRobotStatus.MODE_SLEEP.getText();
            }
            return SweepRobotStatus.MODE_IN_THE_DRY.getText();
        }

        public final boolean deviceShouldBeShow(@NotNull String deviceType) {
            Intrinsics.checkNotNullParameter(deviceType, "deviceType");
            return !TextUtils.equals(DeviceTypeDic.DT_ENV_AIR_MONITOR.getDeviceType(), deviceType);
        }
    }
}
