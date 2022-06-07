package com.xiaomi.miot.typedef.device;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.ActionType;
import com.xiaomi.miot.typedef.urn.EventType;
import com.xiaomi.miot.typedef.urn.PropertyType;
import com.xiaomi.miot.typedef.urn.ServiceType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class Service implements Parcelable {
    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() { // from class: com.xiaomi.miot.typedef.device.Service.1
        @Override // android.os.Parcelable.Creator
        public Service createFromParcel(Parcel parcel) {
            return new Service(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Service[] newArray(int i) {
            return new Service[i];
        }
    };
    private static final String TAG = "Service";
    private String description;
    private Device device;
    private int instanceID;
    private ServiceType type = new ServiceType();
    private volatile Map<String, Property> properties = new HashMap();
    private volatile Map<String, Action> actions = new HashMap();
    private volatile Map<String, Event> events = new HashMap();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Device getDevice() {
        return this.device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public int getInstanceID() {
        return this.instanceID;
    }

    public void setInstanceID(int i) {
        this.instanceID = i;
    }

    public ServiceType getType() {
        return this.type;
    }

    public void setType(ServiceType serviceType) {
        this.type = serviceType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Collection<Property> getProperties() {
        return this.properties.values();
    }

    public Property getProperty(PropertyType propertyType) {
        return this.properties.get(propertyType.toString());
    }

    public void addProperty(Property property) {
        this.properties.put(property.getDefinition().getType().toString(), property);
    }

    public boolean isPropertyChanged() {
        for (Property property : this.properties.values()) {
            if (property.getDefinition().isNotifiable() && property.isChanged()) {
                return true;
            }
        }
        return false;
    }

    public void cleanPropertyState() {
        for (Property property : this.properties.values()) {
            if (property.getDefinition().isNotifiable()) {
                property.cleanState();
            }
        }
    }

    public void addAction(Action action) {
        this.actions.put(action.getType().toString(), action);
    }

    public Collection<Action> getActions() {
        return this.actions.values();
    }

    public Action getAction(ActionType actionType) {
        return this.actions.get(actionType.toString());
    }

    public ActionInfo getActionInfo(ActionType actionType) {
        return ActionInfo.from(this, actionType);
    }

    public void addEvent(Event event) {
        this.events.put(event.getType().toString(), event);
    }

    public Collection<Event> getEvents() {
        return this.events.values();
    }

    public Event getEvent(EventType eventType) {
        return this.events.get(eventType.toString());
    }

    public Service(ServiceType serviceType) {
        setType(serviceType);
    }

    public Service(Parcel parcel) {
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.instanceID = parcel.readInt();
        this.type = ServiceType.valueOf(parcel.readString());
        this.description = parcel.readString();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            addProperty((Property) parcel.readParcelable(Property.class.getClassLoader()));
        }
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            addAction((Action) parcel.readParcelable(Action.class.getClassLoader()));
        }
        int readInt3 = parcel.readInt();
        for (int i3 = 0; i3 < readInt3; i3++) {
            addEvent((Event) parcel.readParcelable(Event.class.getClassLoader()));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.instanceID);
        parcel.writeString(this.type.toString());
        parcel.writeString(this.description);
        parcel.writeInt(this.properties.size());
        for (Property property : this.properties.values()) {
            parcel.writeParcelable(property, i);
        }
        parcel.writeInt(this.actions.size());
        for (Action action : this.actions.values()) {
            parcel.writeParcelable(action, i);
        }
        parcel.writeInt(this.events.size());
        for (Event event : this.events.values()) {
            parcel.writeParcelable(event, i);
        }
    }
}
