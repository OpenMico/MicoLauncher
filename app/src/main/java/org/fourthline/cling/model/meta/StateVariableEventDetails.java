package org.fourthline.cling.model.meta;

/* loaded from: classes5.dex */
public class StateVariableEventDetails {
    private final int eventMaximumRateMilliseconds;
    private final int eventMinimumDelta;
    private final boolean sendEvents;

    public StateVariableEventDetails() {
        this(true, 0, 0);
    }

    public StateVariableEventDetails(boolean z) {
        this(z, 0, 0);
    }

    public StateVariableEventDetails(boolean z, int i, int i2) {
        this.sendEvents = z;
        this.eventMaximumRateMilliseconds = i;
        this.eventMinimumDelta = i2;
    }

    public boolean isSendEvents() {
        return this.sendEvents;
    }

    public int getEventMaximumRateMilliseconds() {
        return this.eventMaximumRateMilliseconds;
    }

    public int getEventMinimumDelta() {
        return this.eventMinimumDelta;
    }
}
