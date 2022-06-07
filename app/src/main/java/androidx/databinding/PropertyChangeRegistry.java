package androidx.databinding;

import androidx.annotation.NonNull;
import androidx.databinding.CallbackRegistry;
import androidx.databinding.Observable;

/* loaded from: classes.dex */
public class PropertyChangeRegistry extends CallbackRegistry<Observable.OnPropertyChangedCallback, Observable, Void> {
    private static final CallbackRegistry.NotifierCallback<Observable.OnPropertyChangedCallback, Observable, Void> a = new CallbackRegistry.NotifierCallback<Observable.OnPropertyChangedCallback, Observable, Void>() { // from class: androidx.databinding.PropertyChangeRegistry.1
        /* renamed from: a */
        public void onNotifyCallback(Observable.OnPropertyChangedCallback onPropertyChangedCallback, Observable observable, int i, Void r4) {
            onPropertyChangedCallback.onPropertyChanged(observable, i);
        }
    };

    public PropertyChangeRegistry() {
        super(a);
    }

    public void notifyChange(@NonNull Observable observable, int i) {
        notifyCallbacks(observable, i, null);
    }
}
