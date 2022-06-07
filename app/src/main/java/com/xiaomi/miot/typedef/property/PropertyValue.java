package com.xiaomi.miot.typedef.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class PropertyValue implements Parcelable {
    public static final Parcelable.Creator<PropertyValue> CREATOR = new Parcelable.Creator<PropertyValue>() { // from class: com.xiaomi.miot.typedef.property.PropertyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PropertyValue createFromParcel(Parcel parcel) {
            return new PropertyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PropertyValue[] newArray(int i) {
            return new PropertyValue[i];
        }
    };
    private static final String TAG = "PropertyValue";
    private DataType type;
    private boolean isChanged = false;
    private boolean isOldValueAvailable = false;
    private volatile DataValue oldValue = null;
    private volatile DataValue currentValue = null;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static PropertyValue create(DataType dataType) {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.type = dataType;
        propertyValue.oldValue = dataType.createDefaultValue();
        propertyValue.isOldValueAvailable = true;
        return propertyValue;
    }

    public void update(DataValue dataValue) {
        if (dataValue == null) {
            Log.e(TAG, "value is null");
        } else if (!this.type.validate(dataValue)) {
            Log.e(TAG, String.format("DataType is: %s, Illegal Value: %s", this.type.toString(), dataValue.getClass().getSimpleName()));
        } else if (this.currentValue == null) {
            this.currentValue = dataValue;
            this.isChanged = true;
        } else if (!this.currentValue.equals(dataValue)) {
            this.oldValue = this.currentValue;
            this.isOldValueAvailable = true;
            this.currentValue = dataValue;
            this.isChanged = true;
        }
    }

    public DataValue getValue() {
        if (this.currentValue != null) {
            return this.currentValue;
        }
        return this.oldValue;
    }

    public DataValue getCurrentValue() {
        return this.currentValue;
    }

    public boolean isChanged() {
        return this.isChanged;
    }

    public boolean isOldValueAvailable() {
        return this.isOldValueAvailable;
    }

    public DataValue getOldValue() {
        return this.oldValue;
    }

    public void cleanState() {
        this.isChanged = false;
    }

    public PropertyValue() {
    }

    public PropertyValue(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.type = DataType.retrieveType(parcel.readString());
        boolean z = false;
        this.isChanged = parcel.readInt() == 1;
        if (parcel.readInt() == 1) {
            z = true;
        }
        this.isOldValueAvailable = z;
        if (parcel.readInt() == 1) {
            this.oldValue = (DataValue) parcel.readValue(this.type.getJavaClass().getClassLoader());
        }
        if (parcel.readInt() == 1) {
            this.currentValue = (DataValue) parcel.readValue(this.type.getJavaClass().getClassLoader());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.type.toString());
        parcel.writeInt(this.isChanged ? 1 : 0);
        parcel.writeInt(this.isOldValueAvailable ? 1 : 0);
        if (this.oldValue == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeValue(this.oldValue);
        }
        if (this.currentValue == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcel.writeValue(this.currentValue);
    }
}
