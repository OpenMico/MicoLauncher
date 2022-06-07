package org.fourthline.cling.support.model.dlna.types;

import org.fourthline.cling.model.types.BytesRange;

/* loaded from: classes5.dex */
public class AvailableSeekRangeType {
    private BytesRange bytesRange;
    private Mode modeFlag;
    private NormalPlayTimeRange normalPlayTimeRange;

    /* loaded from: classes5.dex */
    public enum Mode {
        MODE_0,
        MODE_1
    }

    public AvailableSeekRangeType(Mode mode, NormalPlayTimeRange normalPlayTimeRange) {
        this.modeFlag = mode;
        this.normalPlayTimeRange = normalPlayTimeRange;
    }

    public AvailableSeekRangeType(Mode mode, BytesRange bytesRange) {
        this.modeFlag = mode;
        this.bytesRange = bytesRange;
    }

    public AvailableSeekRangeType(Mode mode, NormalPlayTimeRange normalPlayTimeRange, BytesRange bytesRange) {
        this.modeFlag = mode;
        this.normalPlayTimeRange = normalPlayTimeRange;
        this.bytesRange = bytesRange;
    }

    public NormalPlayTimeRange getNormalPlayTimeRange() {
        return this.normalPlayTimeRange;
    }

    public BytesRange getBytesRange() {
        return this.bytesRange;
    }

    public Mode getModeFlag() {
        return this.modeFlag;
    }
}
