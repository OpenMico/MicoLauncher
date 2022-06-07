package org.fourthline.cling.model.gena;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.ServiceManager;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.StateVariable;
import org.fourthline.cling.model.state.StateVariableValue;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class LocalGENASubscription extends GENASubscription<LocalService> implements PropertyChangeListener {
    private static Logger log = Logger.getLogger(LocalGENASubscription.class.getName());
    final List<URL> callbackURLs;
    final Map<String, Long> lastSentTimestamp = new HashMap();
    final Map<String, Long> lastSentNumericValue = new HashMap();

    public abstract void ended(CancelReason cancelReason);

    protected LocalGENASubscription(LocalService localService, List<URL> list) throws Exception {
        super(localService);
        this.callbackURLs = list;
    }

    public LocalGENASubscription(LocalService localService, Integer num, List<URL> list) throws Exception {
        super(localService);
        setSubscriptionDuration(num);
        log.fine("Reading initial state of local service at subscription time");
        long time = new Date().getTime();
        this.currentValues.clear();
        Collection<StateVariableValue> currentState = getService().getManager().getCurrentState();
        Logger logger = log;
        logger.finer("Got evented state variable values: " + currentState.size());
        for (StateVariableValue stateVariableValue : currentState) {
            this.currentValues.put(stateVariableValue.getStateVariable().getName(), stateVariableValue);
            if (log.isLoggable(Level.FINEST)) {
                Logger logger2 = log;
                logger2.finer("Read state variable value '" + stateVariableValue.getStateVariable().getName() + "': " + stateVariableValue.toString());
            }
            this.lastSentTimestamp.put(stateVariableValue.getStateVariable().getName(), Long.valueOf(time));
            if (stateVariableValue.getStateVariable().isModeratedNumericType()) {
                this.lastSentNumericValue.put(stateVariableValue.getStateVariable().getName(), Long.valueOf(stateVariableValue.toString()));
            }
        }
        this.subscriptionId = "uuid:" + UUID.randomUUID();
        this.currentSequence = new UnsignedIntegerFourBytes(0L);
        this.callbackURLs = list;
    }

    public synchronized List<URL> getCallbackURLs() {
        return this.callbackURLs;
    }

    public synchronized void registerOnService() {
        getService().getManager().getPropertyChangeSupport().addPropertyChangeListener(this);
    }

    public synchronized void establish() {
        established();
    }

    public synchronized void end(CancelReason cancelReason) {
        try {
            getService().getManager().getPropertyChangeSupport().removePropertyChangeListener(this);
        } catch (Exception e) {
            Logger logger = log;
            logger.warning("Removal of local service property change listener failed: " + Exceptions.unwrap(e));
        }
        ended(cancelReason);
    }

    @Override // java.beans.PropertyChangeListener
    public synchronized void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(ServiceManager.EVENTED_STATE_VARIABLES)) {
            Logger logger = log;
            logger.fine("Eventing triggered, getting state for subscription: " + getSubscriptionId());
            long time = new Date().getTime();
            Collection<StateVariableValue> collection = (Collection) propertyChangeEvent.getNewValue();
            Set<String> moderateStateVariables = moderateStateVariables(time, collection);
            this.currentValues.clear();
            for (StateVariableValue stateVariableValue : collection) {
                String name = stateVariableValue.getStateVariable().getName();
                if (!moderateStateVariables.contains(name)) {
                    Logger logger2 = log;
                    logger2.fine("Adding state variable value to current values of event: " + stateVariableValue.getStateVariable() + " = " + stateVariableValue);
                    this.currentValues.put(stateVariableValue.getStateVariable().getName(), stateVariableValue);
                    this.lastSentTimestamp.put(name, Long.valueOf(time));
                    if (stateVariableValue.getStateVariable().isModeratedNumericType()) {
                        this.lastSentNumericValue.put(name, Long.valueOf(stateVariableValue.toString()));
                    }
                }
            }
            if (this.currentValues.size() > 0) {
                Logger logger3 = log;
                logger3.fine("Propagating new state variable values to subscription: " + this);
                eventReceived();
            } else {
                log.fine("No state variable values for event (all moderated out?), not triggering event");
            }
        }
    }

    protected synchronized Set<String> moderateStateVariables(long j, Collection<StateVariableValue> collection) {
        HashSet hashSet;
        hashSet = new HashSet();
        for (StateVariableValue stateVariableValue : collection) {
            StateVariable stateVariable = stateVariableValue.getStateVariable();
            String name = stateVariableValue.getStateVariable().getName();
            if (stateVariable.getEventDetails().getEventMaximumRateMilliseconds() == 0 && stateVariable.getEventDetails().getEventMinimumDelta() == 0) {
                log.finer("Variable is not moderated: " + stateVariable);
            } else if (!this.lastSentTimestamp.containsKey(name)) {
                log.finer("Variable is moderated but was never sent before: " + stateVariable);
            } else if (stateVariable.getEventDetails().getEventMaximumRateMilliseconds() > 0 && j <= this.lastSentTimestamp.get(name).longValue() + stateVariable.getEventDetails().getEventMaximumRateMilliseconds()) {
                log.finer("Excluding state variable with maximum rate: " + stateVariable);
                hashSet.add(name);
            } else if (stateVariable.isModeratedNumericType() && this.lastSentNumericValue.get(name) != null) {
                long longValue = Long.valueOf(this.lastSentNumericValue.get(name).longValue()).longValue();
                long longValue2 = Long.valueOf(stateVariableValue.toString()).longValue();
                long eventMinimumDelta = stateVariable.getEventDetails().getEventMinimumDelta();
                int i = (longValue2 > longValue ? 1 : (longValue2 == longValue ? 0 : -1));
                if (i > 0 && longValue2 - longValue < eventMinimumDelta) {
                    log.finer("Excluding state variable with minimum delta: " + stateVariable);
                    hashSet.add(name);
                } else if (i < 0 && longValue - longValue2 < eventMinimumDelta) {
                    log.finer("Excluding state variable with minimum delta: " + stateVariable);
                    hashSet.add(name);
                }
            }
        }
        return hashSet;
    }

    public synchronized void incrementSequence() {
        this.currentSequence.increment(true);
    }

    public synchronized void setSubscriptionDuration(Integer num) {
        this.requestedDurationSeconds = num == null ? 1800 : num.intValue();
        setActualSubscriptionDurationSeconds(this.requestedDurationSeconds);
    }
}
