package com.xiaomi.miot.typedef.device;

import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.miot.typedef.data.KeyType;
import com.xiaomi.miot.typedef.field.FieldDefinition;
import com.xiaomi.miot.typedef.field.FieldList;
import com.xiaomi.miot.typedef.field.FieldType;
import com.xiaomi.miot.typedef.property.Property;
import com.xiaomi.miot.typedef.urn.DeviceType;
import com.xiaomi.miot.typedef.urn.ServiceType;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Device implements Parcelable {
    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() { // from class: com.xiaomi.miot.typedef.device.Device.1
        @Override // android.os.Parcelable.Creator
        public Device createFromParcel(Parcel parcel) {
            return new Device(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Device[] newArray(int i) {
            return new Device[i];
        }
    };
    private String deviceId;
    private String sn;
    private int instanceID = 0;
    private int keyType = 0;
    private List<DiscoveryType> discoveryTypes = new ArrayList();
    private DeviceType type = new DeviceType();
    private FieldList fields = new FieldList();
    private List<Service> services = new ArrayList();

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* loaded from: classes3.dex */
    public static class Field {
        private static final String FRIENDLY_NAME = "friendlyName";
        private static FieldDefinition FriendlyName = new FieldDefinition(FRIENDLY_NAME, FieldType.STRING);
        private static final String MAC_ADDRESS = "macAddress";
        private static FieldDefinition MacAddress = new FieldDefinition(MAC_ADDRESS, FieldType.STRING);
        private static final String MANUFACTURER = "manufacturer";
        private static FieldDefinition Manufacturer = new FieldDefinition(MANUFACTURER, FieldType.STRING);
        private static final String MODEL_NAME = "modelName";
        private static FieldDefinition ModelName = new FieldDefinition(MODEL_NAME, FieldType.STRING);
        private static final String MIOT_TOKEN = "miot:X_TOKEN";
        private static FieldDefinition MiotToken = new FieldDefinition(MIOT_TOKEN, FieldType.STRING);
        private static final String MIOT_INFO = "miot:X_INFO";
        private static FieldDefinition MiotInfo = new FieldDefinition(MIOT_INFO, FieldType.STRING);
        private static final String MIOT_DYNAMIC_KEY_TYPE = "miot:X_KEY_TYPE";
        private static FieldDefinition MiotDynamicDidKeyType = new FieldDefinition(MIOT_DYNAMIC_KEY_TYPE, FieldType.STRING);
        private static final String SMART_CONFIG = "smart_config";
        private static FieldDefinition SmartConfig = new FieldDefinition(SMART_CONFIG, FieldType.BOOLEAN);
        private static final String DEV_CERT = "dev_cert";
        private static FieldDefinition DevCert = new FieldDefinition(DEV_CERT, FieldType.STRING);
        private static final String MANU_CERT = "manu_cert";
        private static FieldDefinition ManuCert = new FieldDefinition(MANU_CERT, FieldType.STRING);

        private Field() {
        }
    }

    public List<DiscoveryType> getDiscoveryTypes() {
        return this.discoveryTypes;
    }

    public void setDiscoveryTypes(List<DiscoveryType> list) {
        this.discoveryTypes = list;
    }

    public void addDiscoveryType(DiscoveryType discoveryType) {
        if (!this.discoveryTypes.contains(discoveryType)) {
            this.discoveryTypes.add(discoveryType);
        }
    }

    public DeviceType getType() {
        return this.type;
    }

    public void setType(DeviceType deviceType) {
        this.type = deviceType;
    }

    public String getFriendlyName() {
        return (String) this.fields.getValue(Field.FriendlyName);
    }

    public boolean setFriendlyName(String str) {
        return this.fields.setValue(Field.FriendlyName, str);
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String str) {
        this.sn = str;
    }

    public void setKeyType(KeyType keyType) {
        if (keyType != null) {
            this.keyType = keyType.ordinal();
        }
    }

    public int getKeyType() {
        return this.keyType;
    }

    public Boolean getSmartConfig() {
        return (Boolean) this.fields.getValue(Field.SmartConfig);
    }

    public boolean setSmartConfig(Boolean bool) {
        return this.fields.setValue(Field.SmartConfig, bool);
    }

    public String getDevCert() {
        return (String) this.fields.getValue(Field.DevCert);
    }

    public boolean setDevCert(String str) {
        return this.fields.setValue(Field.DevCert, str);
    }

    public String getManuCert() {
        return (String) this.fields.getValue(Field.ManuCert);
    }

    public boolean setManuCert(String str) {
        return this.fields.setValue(Field.ManuCert, str);
    }

    public String getMacAddress() {
        return (String) this.fields.getValue(Field.MacAddress);
    }

    public boolean setMacAddress(String str) {
        return this.fields.setValue(Field.MacAddress, str);
    }

    public String getManufacturer() {
        return (String) this.fields.getValue(Field.Manufacturer);
    }

    public boolean setManufacturer(String str) {
        return this.fields.setValue(Field.Manufacturer, str);
    }

    public String getModelName() {
        return (String) this.fields.getValue(Field.ModelName);
    }

    public boolean setModelName(String str) {
        return this.fields.setValue(Field.ModelName, str);
    }

    public String getMiotToken() {
        return (String) this.fields.getValue(Field.MiotToken);
    }

    public boolean setMiotToken(String str) {
        return this.fields.setValue(Field.MiotToken, str);
    }

    public String getMiotInfo() {
        return (String) this.fields.getValue(Field.MiotInfo);
    }

    public boolean setMiotInfo(String str) {
        return this.fields.setValue(Field.MiotInfo, str);
    }

    public List<Service> getServices() {
        return this.services;
    }

    public void addService(Service service) {
        service.setDevice(this);
        this.services.add(service);
    }

    public Service getService(ServiceType serviceType) {
        for (Service service : this.services) {
            if (service.getType().equals(serviceType)) {
                return service;
            }
        }
        return null;
    }

    public Service getService(int i) {
        for (Service service : this.services) {
            if (service.getInstanceID() == i) {
                return service;
            }
        }
        return null;
    }

    public void initializeInstanceID() {
        int i = 1;
        for (Service service : this.services) {
            int i2 = i + 1;
            service.setInstanceID(i);
            for (Property property : service.getProperties()) {
                property.setDeviceInstanceID(this.instanceID);
                property.setServiceInstanceID(i);
                i2++;
                property.setInstanceID(i2);
            }
            for (Action action : service.getActions()) {
                action.setDeviceInstanceID(this.instanceID);
                action.setServiceInstanceID(i);
                i2++;
                action.setInstanceID(i2);
            }
            i = i2;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Device device = (Device) obj;
        String str = this.deviceId;
        return str != null ? str.equals(device.deviceId) : device.deviceId == null;
    }

    public int hashCode() {
        String str = this.deviceId;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public Device() {
        initialize();
    }

    public Device(DeviceType deviceType) {
        initialize();
        setType(deviceType);
    }

    private void initialize() {
        this.fields.initField(Field.FriendlyName, null);
        this.fields.initField(Field.MacAddress, null);
        this.fields.initField(Field.Manufacturer, null);
        this.fields.initField(Field.ModelName, null);
        this.fields.initField(Field.MiotToken, null);
        this.fields.initField(Field.MiotInfo, null);
        this.fields.initField(Field.SmartConfig, null);
        this.fields.initField(Field.DevCert, null);
        this.fields.initField(Field.ManuCert, null);
    }

    public Device(Parcel parcel) {
        initialize();
        readFromParcel(parcel);
    }

    public void readFromParcel(Parcel parcel) {
        this.deviceId = parcel.readString();
        this.sn = parcel.readString();
        this.keyType = parcel.readInt();
        this.type = DeviceType.valueOf(parcel.readString());
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.discoveryTypes.add(DiscoveryType.retrieveType(parcel.readString()));
        }
        this.fields = (FieldList) parcel.readParcelable(FieldList.class.getClassLoader());
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            addService((Service) parcel.readParcelable(Service.class.getClassLoader()));
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
        parcel.writeString(this.sn);
        parcel.writeInt(this.keyType);
        parcel.writeString(this.type.toString());
        parcel.writeInt(this.discoveryTypes.size());
        for (DiscoveryType discoveryType : this.discoveryTypes) {
            parcel.writeString(discoveryType.toString());
        }
        parcel.writeParcelable(this.fields, i);
        parcel.writeInt(this.services.size());
        for (Service service : this.services) {
            parcel.writeParcelable(service, i);
        }
    }
}
