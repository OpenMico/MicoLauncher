package com.xiaomi.miot.typedef.field;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class FieldDefinition implements Parcelable {
    public static final Parcelable.Creator<FieldDefinition> CREATOR = new Parcelable.Creator<FieldDefinition>() { // from class: com.xiaomi.miot.typedef.field.FieldDefinition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldDefinition createFromParcel(Parcel parcel) {
            return new FieldDefinition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FieldDefinition[] newArray(int i) {
            return new FieldDefinition[i];
        }
    };
    private String name;
    private FieldType type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FieldDefinition(String str, FieldType fieldType) {
        this.name = str;
        this.type = fieldType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public FieldType getType() {
        return this.type;
    }

    public void setType(FieldType fieldType) {
        this.type = fieldType;
    }

    public int hashCode() {
        String str = this.name;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FieldDefinition fieldDefinition = (FieldDefinition) obj;
        String str = this.name;
        if (str == null) {
            if (fieldDefinition.name != null) {
                return false;
            }
        } else if (!str.equals(fieldDefinition.name)) {
            return false;
        }
        return true;
    }

    private FieldDefinition() {
    }

    public FieldDefinition(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.name = parcel.readString();
        this.type = FieldType.retrieveType(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.type.toString());
    }
}
