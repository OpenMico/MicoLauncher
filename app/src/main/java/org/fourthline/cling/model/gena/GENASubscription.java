package org.fourthline.cling.model.gena;

import java.util.LinkedHashMap;
import java.util.Map;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

/* loaded from: classes5.dex */
public abstract class GENASubscription<S extends Service> {
    protected int actualDurationSeconds;
    protected UnsignedIntegerFourBytes currentSequence;
    protected Map<String, StateVariableValue<S>> currentValues;
    protected int requestedDurationSeconds;
    protected S service;
    protected String subscriptionId;

    public abstract void established();

    public abstract void eventReceived();

    /* JADX INFO: Access modifiers changed from: protected */
    public GENASubscription(S s) {
        this.requestedDurationSeconds = 1800;
        this.currentValues = new LinkedHashMap();
        this.service = s;
    }

    public GENASubscription(S s, int i) {
        this(s);
        this.requestedDurationSeconds = i;
    }

    public synchronized S getService() {
        return this.service;
    }

    public synchronized String getSubscriptionId() {
        return this.subscriptionId;
    }

    public synchronized void setSubscriptionId(String str) {
        this.subscriptionId = str;
    }

    public synchronized int getRequestedDurationSeconds() {
        return this.requestedDurationSeconds;
    }

    public synchronized int getActualDurationSeconds() {
        return this.actualDurationSeconds;
    }

    public synchronized void setActualSubscriptionDurationSeconds(int i) {
        this.actualDurationSeconds = i;
    }

    public synchronized UnsignedIntegerFourBytes getCurrentSequence() {
        return this.currentSequence;
    }

    public synchronized Map<String, StateVariableValue<S>> getCurrentValues() {
        return this.currentValues;
    }

    public String toString() {
        return "(GENASubscription, SID: " + getSubscriptionId() + ", SEQUENCE: " + getCurrentSequence() + ")";
    }
}
