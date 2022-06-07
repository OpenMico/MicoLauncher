package org.fourthline.cling.model.types;

/* loaded from: classes5.dex */
public class NamedDeviceType {
    private DeviceType deviceType;
    private UDN udn;

    public NamedDeviceType(UDN udn, DeviceType deviceType) {
        this.udn = udn;
        this.deviceType = deviceType;
    }

    public UDN getUdn() {
        return this.udn;
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public static NamedDeviceType valueOf(String str) throws InvalidValueException {
        String[] split = str.split("::");
        if (split.length == 2) {
            try {
                return new NamedDeviceType(UDN.valueOf(split[0]), DeviceType.valueOf(split[1]));
            } catch (Exception unused) {
                throw new InvalidValueException("Can't parse UDN: " + split[0]);
            }
        } else {
            throw new InvalidValueException("Can't parse UDN::DeviceType from: " + str);
        }
    }

    public String toString() {
        return getUdn().toString() + "::" + getDeviceType().toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NamedDeviceType)) {
            return false;
        }
        NamedDeviceType namedDeviceType = (NamedDeviceType) obj;
        return this.deviceType.equals(namedDeviceType.deviceType) && this.udn.equals(namedDeviceType.udn);
    }

    public int hashCode() {
        return (this.udn.hashCode() * 31) + this.deviceType.hashCode();
    }
}
