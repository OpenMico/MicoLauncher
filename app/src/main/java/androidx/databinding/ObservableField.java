package androidx.databinding;

import androidx.annotation.Nullable;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableField<T> extends a implements Serializable {
    static final long serialVersionUID = 1;
    private T mValue;

    public ObservableField(T t) {
        this.mValue = t;
    }

    public ObservableField() {
    }

    public ObservableField(Observable... observableArr) {
        super(observableArr);
    }

    @Nullable
    public T get() {
        return this.mValue;
    }

    public void set(T t) {
        if (t != this.mValue) {
            this.mValue = t;
            notifyChange();
        }
    }
}
