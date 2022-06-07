package com.xiaomi.miot.typedef.device;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.typedef.data.DataValue;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ActionType;
import com.xiaomi.miot.typedef.urn.PropertyType;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ActionInfo implements Parcelable {
    public static final Parcelable.Creator<ActionInfo> CREATOR = new Parcelable.Creator<ActionInfo>() { // from class: com.xiaomi.miot.typedef.device.ActionInfo.1
        @Override // android.os.Parcelable.Creator
        public ActionInfo createFromParcel(Parcel parcel) {
            return new ActionInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ActionInfo[] newArray(int i) {
            return new ActionInfo[i];
        }
    };
    private String description;
    private int deviceInstanceID;
    private int instanceID;
    private int serviceInstanceID;
    private ActionType type;
    private List<Property> ins = new ArrayList();
    private List<Property> outs = new ArrayList();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static ActionInfo from(Service service, ActionType actionType) {
        Action action = service.getAction(actionType);
        if (action == null) {
            return null;
        }
        ActionInfo actionInfo = new ActionInfo(action.getType());
        actionInfo.description = action.getDescription();
        actionInfo.deviceInstanceID = action.getDeviceInstanceID();
        actionInfo.serviceInstanceID = action.getServiceInstanceID();
        actionInfo.instanceID = action.getInstanceID();
        for (String str : action.getArguments()) {
            actionInfo.ins.add(service.getProperty(PropertyType.valueOf(str)));
        }
        for (String str2 : action.getResults()) {
            actionInfo.outs.add(service.getProperty(PropertyType.valueOf(str2)));
        }
        return actionInfo;
    }

    public int getDeviceInstanceID() {
        return this.deviceInstanceID;
    }

    public int getServiceInstanceID() {
        return this.serviceInstanceID;
    }

    public int getInstanceID() {
        return this.instanceID;
    }

    public ActionType getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Property> getArguments() {
        return this.ins;
    }

    public Property getArgument(PropertyType propertyType) {
        for (Property property : this.ins) {
            if (property.getDefinition().getType().equals(propertyType)) {
                return property;
            }
        }
        return null;
    }

    public DataValue getArgumentValue(PropertyType propertyType) {
        return getArgument(propertyType).getCurrentValue();
    }

    public List<Property> getResults() {
        return this.outs;
    }

    public Property getResult(PropertyType propertyType) {
        for (Property property : this.outs) {
            if (property.getDefinition().getType().equals(propertyType)) {
                return property;
            }
        }
        return null;
    }

    public ActionInfo(ActionType actionType) {
        this.type = actionType;
    }

    public ActionInfo(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.deviceInstanceID = parcel.readInt();
        this.serviceInstanceID = parcel.readInt();
        this.instanceID = parcel.readInt();
        this.type = ActionType.valueOf(parcel.readString());
        this.description = parcel.readString();
        this.ins.clear();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.ins.add((Property) parcel.readParcelable(Property.class.getClassLoader()));
        }
        this.outs.clear();
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.outs.add((Property) parcel.readParcelable(Property.class.getClassLoader()));
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
        for (Property property : this.ins) {
            parcel.writeParcelable(property, i);
        }
        parcel.writeInt(this.outs.size());
        for (Property property2 : this.outs) {
            parcel.writeParcelable(property2, i);
        }
    }
}
