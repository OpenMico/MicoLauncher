package com.xiaomi.miot.typedef.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.xiaomi.miot.typedef.data.DataType;
import com.xiaomi.miot.typedef.data.DataValue;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class AllowedValueList implements Parcelable {
    public static final Parcelable.Creator<AllowedValueList> CREATOR = new Parcelable.Creator<AllowedValueList>() { // from class: com.xiaomi.miot.typedef.property.AllowedValueList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AllowedValueList createFromParcel(Parcel parcel) {
            return new AllowedValueList(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AllowedValueList[] newArray(int i) {
            return new AllowedValueList[i];
        }
    };
    private static final String TAG = "AllowedValueList";
    private ArrayList<DataValue> allowedValues = new ArrayList<>();
    private DataType dataType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AllowedValueList(DataType dataType) {
        this.dataType = dataType;
    }

    public ArrayList<DataValue> getAllowedValues() {
        return this.allowedValues;
    }

    public boolean appendAllowedValue(DataValue dataValue) {
        if (!this.dataType.getJavaClass().isInstance(dataValue)) {
            Log.d(TAG, "append dataType invalid");
            return false;
        }
        this.allowedValues.add(dataValue);
        return true;
    }

    public boolean isValid(DataValue dataValue) {
        if (this.dataType.getJavaClass().isInstance(dataValue)) {
            return this.allowedValues.contains(dataValue);
        }
        Log.d(TAG, "dataType invalid");
        return false;
    }

    private AllowedValueList() {
    }

    public AllowedValueList(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.dataType = DataType.valueOf(parcel.readString());
        parcel.readList(this.allowedValues, Object.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.dataType.toString());
        parcel.writeList(this.allowedValues);
    }
}
