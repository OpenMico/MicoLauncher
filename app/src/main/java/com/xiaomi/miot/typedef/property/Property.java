package com.xiaomi.miot.typedef.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataValue;

/* loaded from: classes3.dex */
public class Property implements Parcelable {
    public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() { // from class: com.xiaomi.miot.typedef.property.Property.1
        @Override // android.os.Parcelable.Creator
        public Property createFromParcel(Parcel parcel) {
            return new Property(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Property[] newArray(int i) {
            return new Property[i];
        }
    };
    private static final String TAG = "Property";
    private PropertyDefinition definition;
    private int deviceInstanceID;
    private int instanceID;
    private int serviceInstanceID;
    private volatile PropertyValue value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Property() {
    }

    public Property(PropertyDefinition propertyDefinition) {
        this.definition = propertyDefinition;
        this.value = PropertyValue.create(propertyDefinition.getDataType());
    }

    public int getDeviceInstanceID() {
        return this.deviceInstanceID;
    }

    public void setDeviceInstanceID(int i) {
        this.deviceInstanceID = i;
    }

    public int getServiceInstanceID() {
        return this.serviceInstanceID;
    }

    public void setServiceInstanceID(int i) {
        this.serviceInstanceID = i;
    }

    public int getInstanceID() {
        return this.instanceID;
    }

    public void setInstanceID(int i) {
        this.instanceID = i;
    }

    public PropertyDefinition getDefinition() {
        return this.definition;
    }

    public PropertyValue getPropertyValue() {
        return this.value;
    }

    public DataValue getOldValue() {
        return this.value.getOldValue();
    }

    public DataValue getCurrentValue() {
        return this.value.getValue();
    }

    public boolean setValue(Object obj) {
        return setDataValue(this.definition.getDataType().createValue(obj));
    }

    public boolean setDataValue(DataValue dataValue) {
        if (dataValue == null) {
            Log.e(TAG, "newValue is null");
            return false;
        } else if (!this.definition.validate(dataValue)) {
            Log.e(TAG, String.format("invalid value: %s", dataValue));
            return false;
        } else {
            this.value.update(dataValue);
            return true;
        }
    }

    public boolean isChanged() {
        return this.value.isChanged();
    }

    public void cleanState() {
        this.value.cleanState();
    }

    public Property(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.deviceInstanceID = parcel.readInt();
        this.serviceInstanceID = parcel.readInt();
        this.instanceID = parcel.readInt();
        this.definition = (PropertyDefinition) parcel.readParcelable(PropertyDefinition.class.getClassLoader());
        this.value = (PropertyValue) parcel.readParcelable(PropertyValue.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.deviceInstanceID);
        parcel.writeInt(this.serviceInstanceID);
        parcel.writeInt(this.instanceID);
        parcel.writeParcelable(this.definition, i);
        parcel.writeParcelable(this.value, i);
    }
}
