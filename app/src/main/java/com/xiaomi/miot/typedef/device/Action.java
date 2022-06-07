package com.xiaomi.miot.typedef.device;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.typedef.urn.ActionType;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Action implements Parcelable {
    public static final Parcelable.Creator<Action> CREATOR = new Parcelable.Creator<Action>() { // from class: com.xiaomi.miot.typedef.device.Action.1
        @Override // android.os.Parcelable.Creator
        public Action createFromParcel(Parcel parcel) {
            return new Action(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Action[] newArray(int i) {
            return new Action[i];
        }
    };
    private String description;
    private int deviceInstanceID;
    private int instanceID;
    private int serviceInstanceID;
    private ActionType type;
    private List<String> ins = new ArrayList();
    private List<String> outs = new ArrayList();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public ActionType getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public List<String> getArguments() {
        return this.ins;
    }

    public void addArgument(String str) {
        this.ins.add(str);
    }

    public List<String> getResults() {
        return this.outs;
    }

    public void addResult(String str) {
        this.outs.add(str);
    }

    public Action(ActionType actionType) {
        this.type = actionType;
    }

    public Action(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.deviceInstanceID = parcel.readInt();
        this.serviceInstanceID = parcel.readInt();
        this.instanceID = parcel.readInt();
        this.type = ActionType.valueOf(parcel.readString());
        this.description = parcel.readString();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.ins.add(parcel.readString());
        }
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.outs.add(parcel.readString());
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.deviceInstanceID);
        parcel.writeInt(this.serviceInstanceID);
        parcel.writeInt(this.instanceID);
        parcel.writeString(this.type.toString());
        parcel.writeString(this.description);
        parcel.writeInt(this.ins.size());
        for (String str : this.ins) {
            parcel.writeString(str);
        }
        parcel.writeInt(this.outs.size());
        for (String str2 : this.outs) {
            parcel.writeString(str2);
        }
    }
}
