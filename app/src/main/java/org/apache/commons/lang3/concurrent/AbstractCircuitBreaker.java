package org.apache.commons.lang3.concurrent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes5.dex */
public abstract class AbstractCircuitBreaker<T> implements CircuitBreaker<T> {
    public static final String PROPERTY_NAME = "open";
    protected final AtomicReference<State> state = new AtomicReference<>(State.CLOSED);
    private final PropertyChangeSupport a = new PropertyChangeSupport(this);

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes5.dex */
    public enum State {
        CLOSED {
            @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker.State
            public State oppositeState() {
                return OPEN;
            }
        },
        OPEN {
            @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker.State
            public State oppositeState() {
                return CLOSED;
            }
        };

        public abstract State oppositeState();
    }

    @Override // org.apache.commons.lang3.concurrent.CircuitBreaker
    public abstract boolean checkState();

    @Override // org.apache.commons.lang3.concurrent.CircuitBreaker
    public abstract boolean incrementAndCheckState(T t);

    @Override // org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean isOpen() {
        return isOpen(this.state.get());
    }

    @Override // org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean isClosed() {
        return !isOpen();
    }

    @Override // org.apache.commons.lang3.concurrent.CircuitBreaker
    public void close() {
        changeState(State.CLOSED);
    }

    @Override // org.apache.commons.lang3.concurrent.CircuitBreaker
    public void open() {
        changeState(State.OPEN);
    }

    protected static boolean isOpen(State state) {
        return state == State.OPEN;
    }

    protected void changeState(State state) {
        if (this.state.compareAndSet(state.oppositeState(), state)) {
            this.a.firePropertyChange(PROPERTY_NAME, !isOpen(state), isOpen(state));
        }
    }

    public void addChangeListener(PropertyChangeListener propertyChangeListener) {
        this.a.addPropertyChangeListener(propertyChangeListener);
    }

    public void removeChangeListener(PropertyChangeListener propertyChangeListener) {
        this.a.removePropertyChangeListener(propertyChangeListener);
    }
}
