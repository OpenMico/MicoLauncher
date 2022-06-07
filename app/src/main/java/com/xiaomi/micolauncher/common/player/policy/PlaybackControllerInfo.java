package com.xiaomi.micolauncher.common.player.policy;

import com.xiaomi.micolauncher.skills.music.controller.LoopType;

/* loaded from: classes3.dex */
public class PlaybackControllerInfo {
    public static final String RESOLUTION_4K = "4k";
    public static final String RESOLUTION_BD = "BD";
    public static final String RESOLUTION_FD = "FD";
    public static final String RESOLUTION_FULL_HD = "FULL_HD";
    public static final String RESOLUTION_HD = "HD";
    public static final String RESOLUTION_SD = "SD";
    public int countOfEnd;
    public CountType countType;
    public int deltaInMs;
    public String dialogId;
    public int index;
    public LoopType loopMode;
    public PlaybackControl playbackControl;
    public String propertyValue;
    public ReferenceDef referenceDef;
    public SetProperty setPropertyName;
    public int speed;
    public int timeoutInMs;

    public PlaybackControllerInfo(PlaybackControl playbackControl) {
        this.playbackControl = playbackControl;
    }

    public PlaybackControllerInfo(PlaybackControl playbackControl, String str) {
        this.playbackControl = playbackControl;
        this.dialogId = str;
    }

    public boolean isSettingLoopMode() {
        return this.playbackControl == PlaybackControl.SET_PROPERTY && this.setPropertyName == SetProperty.LOOP_MODE;
    }

    public String toString() {
        return "PlaybackControllerInfo{playbackControl=" + this.playbackControl + ", deltaInMs=" + this.deltaInMs + ", referenceDef=" + this.referenceDef + ", speed=" + this.speed + ", countType='" + this.countType + "', count=" + this.countOfEnd + ", timeoutInMs=" + this.timeoutInMs + ", setPropertyName='" + this.setPropertyName + "', loopMode=" + this.loopMode + ", propertyValue=" + this.propertyValue + ", dialogId=" + this.dialogId + '}';
    }

    /* loaded from: classes3.dex */
    public enum ReferenceDef {
        UNKNOWN(-1),
        START(0),
        CURRENT(1),
        END(2);
        
        private int id;

        ReferenceDef(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        public static ReferenceDef valueOf(int i) {
            if (i == START.getId()) {
                return START;
            }
            if (i == CURRENT.getId()) {
                return CURRENT;
            }
            if (i == END.getId()) {
                return END;
            }
            return UNKNOWN;
        }
    }

    /* loaded from: classes3.dex */
    public enum CountType {
        UNKNOWN(-1),
        EPISODE(0),
        LIST(1);
        
        private int id;

        CountType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }

        public static CountType valueOf(int i) {
            if (i == EPISODE.getId()) {
                return EPISODE;
            }
            if (i == LIST.getId()) {
                return LIST;
            }
            return UNKNOWN;
        }
    }

    /* loaded from: classes3.dex */
    public enum SetProperty {
        UNKNOWN("UNKNOWN"),
        LOOP_MODE("LOOP_MODE"),
        RESOLUTION("RESOLUTION"),
        SKIP_START("SKIP_START"),
        SKIP_END("SKIP_END");
        
        private String id;

        SetProperty(String str) {
            this.id = str;
        }

        public String getId() {
            return this.id;
        }

        public static SetProperty value(String str) {
            if (LOOP_MODE.getId().equalsIgnoreCase(str)) {
                return LOOP_MODE;
            }
            if (RESOLUTION.getId().equalsIgnoreCase(str)) {
                return RESOLUTION;
            }
            if (SKIP_START.getId().equalsIgnoreCase(str)) {
                return SKIP_START;
            }
            if (SKIP_END.getId().equalsIgnoreCase(str)) {
                return SKIP_END;
            }
            return UNKNOWN;
        }
    }
}
