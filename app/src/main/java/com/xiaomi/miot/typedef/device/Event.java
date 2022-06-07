package com.xiaomi.miot.typedef.device;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.EventType;
import com.xiaomi.miot.typedef.urn.PropertyType;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Event implements Parcelable {
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() { // from class: com.xiaomi.miot.typedef.device.Event.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Event[] newArray(int i) {
            return new Event[i];
        }
    };
    private String description;
    private int deviceInstanceID;
    private int instanceID;
    private List<Property> props = new ArrayList();
    private int serviceInstanceID;
    private EventType type;

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

    public String getDescription() {
        return this.description;
    }

    public List<Property> getArguments() {
        return this.props;
    }

    public EventType getType() {
        return this.type;
    }

    public Property getArgument(PropertyType propertyType) {
        for (Property property : this.props) {
            if (property.getDefinition().getType().equals(propertyType)) {
                return property;
            }
        }
        return null;
    }

    public void addArgument(Property property) {
        this.props.add(property);
    }

    public Event(EventType eventType) {
        this.type = eventType;
    }

    public Event(Parcel parcel) {
        this.deviceInstanceID = parcel.readInt();
        this.serviceInstanceID = parcel.readInt();
        this.instanceID = parcel.readInt();
        this.type = EventType.valueOf(parcel.readString());
        this.description = parcel.readString();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.props.add((Property) parcel.readParcelable(Property.class.getClassLoader()));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.deviceInstanceID);
        parcel.writeInt(this.serviceInstanceID);
        parcel.writeInt(this.instanceID);
        parcel.writeString(this.type.toString());
        parcel.writeString(this.description);
        parcel.writeInt(this.props.size());
        for (Property property : this.props) {
            parcel.writeParcelable(property, i);
        }
    }
}
