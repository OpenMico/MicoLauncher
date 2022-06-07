package androidx.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableInt extends a implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableInt> CREATOR = new Parcelable.Creator<ObservableInt>() { // from class: androidx.databinding.ObservableInt.1
        /* renamed from: a */
        public ObservableInt createFromParcel(Parcel parcel) {
            return new ObservableInt(parcel.readInt());
        }

        /* renamed from: a */
        public ObservableInt[] newArray(int i) {
            return new ObservableInt[i];
        }
    };
    static final long serialVersionUID = 1;
    private int mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ObservableInt(int i) {
        this.mValue = i;
    }

    public ObservableInt() {
    }

    public ObservableInt(Observable... observableArr) {
        super(observableArr);
    }

    public int get() {
        return this.mValue;
    }

    public void set(int i) {
        if (i != this.mValue) {
            this.mValue = i;
            notifyChange();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mValue);
    }
}
