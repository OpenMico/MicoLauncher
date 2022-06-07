package com.xiaomi.miot.typedef.field;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class Field implements Parcelable {
    public static final Parcelable.Creator<Field> CREATOR = new Parcelable.Creator<Field>() { // from class: com.xiaomi.miot.typedef.field.Field.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Field createFromParcel(Parcel parcel) {
            return new Field(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Field[] newArray(int i) {
            return new Field[i];
        }
    };
    private FieldDefinition definition;
    private Object value;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Field(FieldDefinition fieldDefinition, Object obj) {
        init(fieldDefinition, obj);
    }

    private void init(FieldDefinition fieldDefinition, Object obj) {
        if (obj == null) {
            obj = fieldDefinition.getType().createObjectValue();
        }
        this.definition = fieldDefinition;
        this.value = obj;
    }

    public FieldDefinition getDefinition() {
        return this.definition;
    }

    public void setDefinition(FieldDefinition fieldDefinition) {
        this.definition = fieldDefinition;
        this.value = fieldDefinition.getType().createObjectValue();
    }

    public Object getValue() {
        return this.value;
    }

    public boolean setValue(Object obj) {
        this.value = obj;
        return true;
    }

    public Field(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.definition = (FieldDefinition) parcel.readParcelable(FieldDefinition.class.getClassLoader());
        this.value = parcel.readValue(Object.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.definition, i);
        parcel.writeValue(this.value);
    }
}
