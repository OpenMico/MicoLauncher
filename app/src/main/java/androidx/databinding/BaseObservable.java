package androidx.databinding;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;

/* loaded from: classes.dex */
public class BaseObservable implements Observable {
    private transient PropertyChangeRegistry a;

    @Override // androidx.databinding.Observable
    public void addOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        synchronized (this) {
            if (this.a == null) {
                this.a = new PropertyChangeRegistry();
            }
        }
        this.a.add(onPropertyChangedCallback);
    }

    @Override // androidx.databinding.Observable
    public void removeOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        synchronized (this) {
            if (this.a != null) {
                this.a.remove(onPropertyChangedCallback);
            }
        }
    }

    public void notifyChange() {
        synchronized (this) {
            if (this.a != null) {
                this.a.notifyCallbacks(this, 0, null);
            }
        }
    }

    public void notifyPropertyChanged(int i) {
        synchronized (this) {
            if (this.a != null) {
                this.a.notifyCallbacks(this, i, null);
            }
        }
    }
}
