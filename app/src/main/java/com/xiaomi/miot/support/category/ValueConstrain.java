package com.xiaomi.miot.support.category;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ValueConstrain implements Parcelable {
    public static final Parcelable.Creator<ValueConstrain> CREATOR = new Parcelable.Creator<ValueConstrain>() { // from class: com.xiaomi.miot.support.category.ValueConstrain.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ValueConstrain createFromParcel(Parcel parcel) {
            return new ValueConstrain(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ValueConstrain[] newArray(int i) {
            return new ValueConstrain[i];
        }
    };
    private List<String> enumDescription;
    private List<String> enumValue;
    private ValueRange valueRange;
    private int valueType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ValueConstrain() {
    }

    protected ValueConstrain(Parcel parcel) {
        this.valueType = parcel.readInt();
        this.enumValue = parcel.createStringArrayList();
        this.enumDescription = parcel.createStringArrayList();
        this.valueRange = (ValueRange) parcel.readParcelable(ValueRange.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.valueType);
        parcel.writeStringList(this.enumValue);
        parcel.writeStringList(this.enumDescription);
        parcel.writeParcelable(this.valueRange, 0);
    }

    public int getValueType() {
        return this.valueType;
    }

    public void setValueType(int i) {
        this.valueType = i;
    }

    public List<String> getEnumValue() {
        return this.enumValue;
    }

    public void setEnumValue(List<String> list) {
        this.enumValue = list;
    }

    public List<String> getEnumDescription() {
        return this.enumDescription;
    }

    public void setEnumDescription(List<String> list) {
        this.enumDescription = list;
    }

    public String getDescriptionByStatus(String str) {
        return this.enumDescription.get(this.enumValue.indexOf(str));
    }

    public void setEnumValueByString(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
            if (split.length > 0) {
                this.enumValue = new ArrayList();
                this.enumDescription = new ArrayList();
                for (String str2 : split) {
                    String[] split2 = str2.split(Constants.COLON_SEPARATOR);
                    if (split2 != null) {
                        this.enumValue.add(split2[0]);
                        if (split2.length == 2) {
                            this.enumDescription.add(split2[1]);
                        } else {
                            this.enumDescription.add("null");
                        }
                    }
                }
            }
        }
    }

    public String getValueByDescription(String str) {
        List<String> list;
        if (TextUtils.isEmpty(str) || (list = this.enumDescription) == null || list.isEmpty()) {
            return null;
        }
        int size = this.enumDescription.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(this.enumDescription.get(i))) {
                return this.enumValue.get(i);
            }
        }
        return null;
    }

    public ValueRange getValueRange() {
        return this.valueRange;
    }

    public void setValueRange(ValueRange valueRange) {
        this.valueRange = valueRange;
    }
}
