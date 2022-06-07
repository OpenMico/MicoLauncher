package org.fourthline.cling.binding.xml;

import org.w3c.dom.Node;

/* loaded from: classes5.dex */
public abstract class Descriptor {

    /* loaded from: classes5.dex */
    public interface Device {
        public static final String DLNA_NAMESPACE_URI = "urn:schemas-dlna-org:device-1-0";
        public static final String DLNA_PREFIX = "dlna";
        public static final String NAMESPACE_URI = "urn:schemas-upnp-org:device-1-0";
        public static final String SEC_NAMESPACE_URI = "http://www.sec.co.kr/dlna";
        public static final String SEC_PREFIX = "sec";

        /* loaded from: classes5.dex */
        public enum ELEMENT {
            root,
            specVersion,
            major,
            minor,
            URLBase,
            device,
            UDN,
            X_DLNADOC,
            X_DLNACAP,
            ProductCap,
            X_ProductCap,
            deviceType,
            friendlyName,
            manufacturer,
            manufacturerURL,
            modelDescription,
            modelName,
            modelNumber,
            modelURL,
            presentationURL,
            UPC,
            serialNumber,
            iconList,
            icon,
            width,
            height,
            depth,
            url,
            mimetype,
            serviceList,
            service,
            serviceType,
            serviceId,
            SCPDURL,
            controlURL,
            eventSubURL,
            deviceList;

            public static ELEMENT valueOrNullOf(String str) {
                try {
                    return valueOf(str);
                } catch (IllegalArgumentException unused) {
                    return null;
                }
            }

            public boolean equals(Node node) {
                return toString().equals(node.getLocalName());
            }
        }
    }

    /* loaded from: classes5.dex */
    public interface Service {
        public static final String NAMESPACE_URI = "urn:schemas-upnp-org:service-1-0";

        /* loaded from: classes5.dex */
        public enum ATTRIBUTE {
            sendEvents
        }

        /* loaded from: classes5.dex */
        public enum ELEMENT {
            scpd,
            specVersion,
            major,
            minor,
            actionList,
            action,
            name,
            argumentList,
            argument,
            direction,
            relatedStateVariable,
            retval,
            serviceStateTable,
            stateVariable,
            dataType,
            defaultValue,
            allowedValueList,
            allowedValue,
            allowedValueRange,
            minimum,
            maximum,
            step;

            public static ELEMENT valueOrNullOf(String str) {
                try {
                    return valueOf(str);
                } catch (IllegalArgumentException unused) {
                    return null;
                }
            }

            public boolean equals(Node node) {
                return toString().equals(node.getLocalName());
            }
        }
    }
}
