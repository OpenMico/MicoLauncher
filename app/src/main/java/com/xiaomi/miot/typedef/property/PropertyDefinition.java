package com.xiaomi.miot.typedef.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.DataValue;
import com.xiaomi.miot.typedef.urn.PropertyType;

/* loaded from: classes3.dex */
public class PropertyDefinition implements Parcelable {
    public static final Parcelable.Creator<PropertyDefinition> CREATOR = new Parcelable.Creator<PropertyDefinition>() { // from class: com.xiaomi.miot.typedef.property.PropertyDefinition.1
        @Override // android.os.Parcelable.Creator
        public PropertyDefinition createFromParcel(Parcel parcel) {
            return new PropertyDefinition(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public PropertyDefinition[] newArray(int i) {
            return new PropertyDefinition[i];
        }
    };
    private static final String TAG = "PropertyDefinition";
    private AccessType accessType;
    private AllowedValueList allowedValueList;
    private AllowedValueRange allowedValueRange;
    private AllowedValueType allowedValueType;
    private DataType dataType;
    private String defaultValue;
    private String description;
    private PropertyType type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PropertyDefinition(PropertyType propertyType, AccessType accessType, DataType dataType) {
        this.allowedValueType = AllowedValueType.ANY;
        this.type = propertyType;
        this.accessType = accessType;
        this.dataType = dataType;
    }

    public PropertyDefinition(PropertyType propertyType, AccessType accessType, DataType dataType, AllowedValueRange allowedValueRange) {
        this.allowedValueType = AllowedValueType.ANY;
        this.type = propertyType;
        this.accessType = accessType;
        this.dataType = dataType;
        if (this.allowedValueRange != null) {
            this.allowedValueType = AllowedValueType.RANGE;
            this.allowedValueRange = allowedValueRange;
        }
    }

    public AccessType getAccessType() {
        return this.accessType;
    }

    public boolean isSettable() {
        return this.accessType.isSettable();
    }

    public void setSettable(boolean z) {
        this.accessType.setSettable(z);
    }

    public boolean isGettable() {
        return this.accessType.isGettable();
    }

    public void setGettable(boolean z) {
        this.accessType.setGettable(z);
    }

    public boolean isNotifiable() {
        return this.accessType.isNotifiable();
    }

    public void setNotifiable(boolean z) {
        this.accessType.setNotifiable(z);
    }

    public PropertyType getType() {
        return this.type;
    }

    public void setTag(PropertyType propertyType) {
        this.type = propertyType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String str) {
        this.defaultValue = str;
    }

    public AllowedValueType getAllowedValueType() {
        return this.allowedValueType;
    }

    public void setAllowedValueType(AllowedValueType allowedValueType) {
        this.allowedValueType = allowedValueType;
    }

    public AllowedValueList getAllowedValueList() {
        return this.allowedValueList;
    }

    public void setAllowedValueList(AllowedValueList allowedValueList) {
        this.allowedValueType = AllowedValueType.LIST;
        this.allowedValueList = allowedValueList;
    }

    public AllowedValueRange getAllowedValueRange() {
        return this.allowedValueRange;
    }

    public void setAllowedValueRange(AllowedValueRange allowedValueRange) {
        this.allowedValueType = AllowedValueType.RANGE;
        this.allowedValueRange = allowedValueRange;
    }

    public boolean validate(DataValue dataValue) {
        if (dataValue == null) {
            return false;
        }
        if (!this.dataType.getJavaClass().equals(dataValue.getClass())) {
            Log.e(TAG, String.format("%s dataType is %s, value type is %s, invalid!", this.type.toString(), this.dataType.toString(), dataValue.getClass().getSimpleName()));
            return false;
        }
        AllowedValueList allowedValueList = this.allowedValueList;
        if (allowedValueList != null) {
            return allowedValueList.isValid(dataValue);
        }
        AllowedValueRange allowedValueRange = this.allowedValueRange;
        if (allowedValueRange != null) {
            return allowedValueRange.isValid(dataValue);
        }
        return true;
    }

    public int hashCode() {
        PropertyType propertyType = this.type;
        return 31 + (propertyType == null ? 0 : propertyType.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PropertyDefinition propertyDefinition = (PropertyDefinition) obj;
        PropertyType propertyType = this.type;
        if (propertyType == null) {
            if (propertyDefinition.type != null) {
                return false;
            }
        } else if (!propertyType.equals(propertyDefinition.type)) {
            return false;
        }
        return true;
    }

    public PropertyDefinition() {
        this.allowedValueType = AllowedValueType.ANY;
    }

    public PropertyDefinition(Parcel parcel) {
        this.allowedValueType = AllowedValueType.ANY;
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.accessType = AccessType.valueOf(parcel.readInt());
        this.description = parcel.readString();
        this.type = (PropertyType) parcel.readParcelable(PropertyType.class.getClassLoader());
        this.dataType = DataType.retrieveType(parcel.readString());
        this.defaultValue = parcel.readString();
        this.allowedValueType = AllowedValueType.retrieveType(parcel.readInt());
        switch (this.allowedValueType) {
            case ANY:
            default:
                return;
            case LIST:
                this.allowedValueList = (AllowedValueList) parcel.readParcelable(AllowedValueList.class.getClassLoader());
                return;
            case RANGE:
                this.allowedValueRange = (AllowedValueRange) parcel.readParcelable(AllowedValueRange.class.getClassLoader());
                return;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.accessType.value());
        parcel.writeString(this.description);
        parcel.writeParcelable(this.type, i);
        parcel.writeString(this.dataType.toString());
        parcel.writeString(this.defaultValue);
        parcel.writeInt(this.allowedValueType.toInt());
        switch (this.allowedValueType) {
            case ANY:
            default:
                return;
            case LIST:
                parcel.writeParcelable(this.allowedValueList, i);
                return;
            case RANGE:
                parcel.writeParcelable(this.allowedValueRange, i);
                return;
        }
    }
}
