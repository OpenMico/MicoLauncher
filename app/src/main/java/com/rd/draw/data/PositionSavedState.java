package com.rd.draw.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* loaded from: classes2.dex */
public class PositionSavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<PositionSavedState> CREATOR = new Parcelable.Creator<PositionSavedState>() { // from class: com.rd.draw.data.PositionSavedState.1
        /* renamed from: a */
        public PositionSavedState createFromParcel(Parcel parcel) {
            return new PositionSavedState(parcel);
        }

        /* renamed from: a */
        public PositionSavedState[] newArray(int i) {
            return new PositionSavedState[i];
        }
    };
    private int a;
    private int b;
    private int c;

    public PositionSavedState(Parcelable parcelable) {
        super(parcelable);
    }

    private PositionSavedState(Parcel parcel) {
        super(parcel);
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
    }

    public int getSelectedPosition() {
        return this.a;
    }

    public void setSelectedPosition(int i) {
        this.a = i;
    }

    public int getSelectingPosition() {
        return this.b;
    }

    public void setSelectingPosition(int i) {
        this.b = i;
    }

    public int getLastSelectedPosition() {
        return this.c;
    }

    public void setLastSelectedPosition(int i) {
        this.c = i;
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
    }
}
