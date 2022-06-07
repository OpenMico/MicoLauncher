package org.fourthline.cling.model.types;

/* loaded from: classes5.dex */
public class NamedServiceType {
    private ServiceType serviceType;
    private UDN udn;

    public NamedServiceType(UDN udn, ServiceType serviceType) {
        this.udn = udn;
        this.serviceType = serviceType;
    }

    public UDN getUdn() {
        return this.udn;
    }

    public ServiceType getServiceType() {
        return this.serviceType;
    }

    public static NamedServiceType valueOf(String str) throws InvalidValueException {
        String[] split = str.split("::");
        if (split.length == 2) {
            try {
                return new NamedServiceType(UDN.valueOf(split[0]), ServiceType.valueOf(split[1]));
            } catch (Exception unused) {
                throw new InvalidValueException("Can't parse UDN: " + split[0]);
            }
        } else {
            throw new InvalidValueException("Can't parse UDN::ServiceType from: " + str);
        }
    }

    public String toString() {
        return getUdn().toString() + "::" + getServiceType().toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NamedServiceType)) {
            return false;
        }
        NamedServiceType namedServiceType = (NamedServiceType) obj;
        return this.serviceType.equals(namedServiceType.serviceType) && this.udn.equals(namedServiceType.udn);
    }

    public int hashCode() {
        return (this.udn.hashCode() * 31) + this.serviceType.hashCode();
    }
}
