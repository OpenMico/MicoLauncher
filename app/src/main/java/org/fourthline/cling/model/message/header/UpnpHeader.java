package org.fourthline.cling.model.message.header;

import com.blankj.utilcode.constant.PermissionConstants;
import com.google.zxing.client.android.Intents;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class UpnpHeader<T> {
    private static final Logger log = Logger.getLogger(UpnpHeader.class.getName());
    private T value;

    public abstract String getString();

    public abstract void setString(String str) throws InvalidHeaderException;

    /* loaded from: classes5.dex */
    public enum Type {
        USN("USN", USNRootDeviceHeader.class, DeviceUSNHeader.class, ServiceUSNHeader.class, UDNHeader.class),
        NT("NT", RootDeviceHeader.class, UDADeviceTypeHeader.class, UDAServiceTypeHeader.class, DeviceTypeHeader.class, ServiceTypeHeader.class, UDNHeader.class, NTEventHeader.class),
        NTS("NTS", NTSHeader.class),
        HOST("HOST", HostHeader.class),
        SERVER("SERVER", ServerHeader.class),
        LOCATION(PermissionConstants.LOCATION, LocationHeader.class),
        MAX_AGE("CACHE-CONTROL", MaxAgeHeader.class),
        USER_AGENT("USER-AGENT", UserAgentHeader.class),
        CONTENT_TYPE("CONTENT-TYPE", ContentTypeHeader.class),
        MAN("MAN", MANHeader.class),
        MX("MX", MXHeader.class),
        ST("ST", STAllHeader.class, RootDeviceHeader.class, UDADeviceTypeHeader.class, UDAServiceTypeHeader.class, DeviceTypeHeader.class, ServiceTypeHeader.class, UDNHeader.class),
        EXT("EXT", EXTHeader.class),
        SOAPACTION("SOAPACTION", SoapActionHeader.class),
        TIMEOUT(Intents.Scan.TIMEOUT, TimeoutHeader.class),
        CALLBACK("CALLBACK", CallbackHeader.class),
        SID("SID", SubscriptionIdHeader.class),
        SEQ("SEQ", EventSequenceHeader.class),
        RANGE("RANGE", RangeHeader.class),
        CONTENT_RANGE("CONTENT-RANGE", ContentRangeHeader.class),
        PRAGMA("PRAGMA", PragmaHeader.class),
        EXT_IFACE_MAC("X-CLING-IFACE-MAC", InterfaceMacHeader.class),
        EXT_AV_CLIENT_INFO("X-AV-CLIENT-INFO", AVClientInfoHeader.class);
        
        private static Map<String, Type> byName = new HashMap<String, Type>() { // from class: org.fourthline.cling.model.message.header.UpnpHeader.Type.1
            {
                Type[] values = Type.values();
                for (Type type : values) {
                    put(type.getHttpName(), type);
                }
            }
        };
        private Class<? extends UpnpHeader>[] headerTypes;
        private String httpName;

        @SafeVarargs
        Type(String str, Class... clsArr) {
            this.httpName = str;
            this.headerTypes = clsArr;
        }

        public String getHttpName() {
            return this.httpName;
        }

        public Class<? extends UpnpHeader>[] getHeaderTypes() {
            return this.headerTypes;
        }

        public boolean isValidHeaderType(Class<? extends UpnpHeader> cls) {
            for (Class<? extends UpnpHeader> cls2 : getHeaderTypes()) {
                if (cls2.isAssignableFrom(cls)) {
                    return true;
                }
            }
            return false;
        }

        public static Type getByHttpName(String str) {
            if (str == null) {
                return null;
            }
            return byName.get(str.toUpperCase(Locale.ROOT));
        }
    }

    public void setValue(T t) {
        this.value = t;
    }

    public T getValue() {
        return this.value;
    }

    public static UpnpHeader newInstance(Type type, String str) {
        Exception e;
        UpnpHeader upnpHeader = null;
        for (int i = 0; i < type.getHeaderTypes().length && upnpHeader == null; i++) {
            Class<? extends UpnpHeader> cls = type.getHeaderTypes()[i];
            try {
                try {
                    log.finest("Trying to parse '" + type + "' with class: " + cls.getSimpleName());
                    UpnpHeader upnpHeader2 = (UpnpHeader) cls.newInstance();
                    if (str != null) {
                        try {
                            upnpHeader2.setString(str);
                        } catch (Exception e2) {
                            e = e2;
                            upnpHeader = upnpHeader2;
                            log.severe("Error instantiating header of type '" + type + "' with value: " + str);
                            log.log(Level.SEVERE, "Exception root cause: ", Exceptions.unwrap(e));
                        }
                    }
                    upnpHeader = upnpHeader2;
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (InvalidHeaderException e4) {
                log.finest("Invalid header value for tested type: " + cls.getSimpleName() + " - " + e4.getMessage());
                upnpHeader = null;
            }
        }
        return upnpHeader;
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") '" + getValue() + LrcRow.SINGLE_QUOTE;
    }
}
