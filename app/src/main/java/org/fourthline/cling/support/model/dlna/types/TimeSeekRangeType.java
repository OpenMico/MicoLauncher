package org.fourthline.cling.support.model.dlna.types;

import org.fourthline.cling.model.types.BytesRange;

/* loaded from: classes5.dex */
public class TimeSeekRangeType {
    private BytesRange bytesRange;
    private NormalPlayTimeRange normalPlayTimeRange;

    public TimeSeekRangeType(NormalPlayTimeRange normalPlayTimeRange) {
        this.normalPlayTimeRange = normalPlayTimeRange;
    }

    public TimeSeekRangeType(NormalPlayTimeRange normalPlayTimeRange, BytesRange bytesRange) {
        this.normalPlayTimeRange = normalPlayTimeRange;
        this.bytesRange = bytesRange;
    }

    public NormalPlayTimeRange getNormalPlayTimeRange() {
        return this.normalPlayTimeRange;
    }

    public BytesRange getBytesRange() {
        return this.bytesRange;
    }

    public void setBytesRange(BytesRange bytesRange) {
        this.bytesRange = bytesRange;
    }
}
