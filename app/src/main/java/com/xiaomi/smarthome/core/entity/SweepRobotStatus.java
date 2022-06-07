package com.xiaomi.smarthome.core.entity;

import com.xiaomi.mi_soundbox_command_sdk.Commands;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: SmartHomeBeans.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0017\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019¨\u0006\u001a"}, d2 = {"Lcom/xiaomi/smarthome/core/entity/SweepRobotStatus;", "", "status", "", "description", "text", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getStatus", "getText", "MODE_STANDBY", "MODE_SWEEPING", "MODE_CHARGING", "MODE_PAUSED", "MODE_BACK_CHARGING", "MODE_MOPPING", "MODE_WASHING", "MODE_SWEEPING_AND_MOPPING", "MODE_CHARGING_COMPLETED", "MODE_DRYING", "MODE_GO_WASHING", "MODE_UPGRADING", "MODE_IN_THE_WASH", "MODE_IN_THE_DRY", "MODE_SLEEP", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public enum SweepRobotStatus {
    MODE_STANDBY("1", "Idle", "待机中"),
    MODE_SWEEPING("2", "Sweeping", "清扫中"),
    MODE_CHARGING("3", "Charging", "充电中"),
    MODE_PAUSED(Commands.ResolutionValues.BITSTREAM_BLUE_HIGH, "Paused", "暂停中"),
    MODE_BACK_CHARGING(Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND, "Go Charging", "回充中"),
    MODE_MOPPING("6", "Mopping", "拖地中"),
    MODE_WASHING("7", "Washing", "正在清洗拖布"),
    MODE_SWEEPING_AND_MOPPING("8", "Sweeping and Mopping", "正在扫拖"),
    MODE_CHARGING_COMPLETED(Commands.ResolutionValues.BITSTREAM_4K, "Charging Completed", "充电完成"),
    MODE_DRYING("10", "Drying", "正在烘干拖布"),
    MODE_GO_WASHING("11", "Go Washing", "正在回洗"),
    MODE_UPGRADING("12", "Upgrading", "升级中"),
    MODE_IN_THE_WASH("13", "In The Wash", "清洗中"),
    MODE_IN_THE_DRY("14", "In The Dry", "风干中"),
    MODE_SLEEP("15", "Sleep", "睡眠");
    
    @NotNull
    private final String description;
    @NotNull
    private final String status;
    @NotNull
    private final String text;

    SweepRobotStatus(String str, String str2, String str3) {
        this.status = str;
        this.description = str2;
        this.text = str3;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    @NotNull
    public final String getStatus() {
        return this.status;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }
}
