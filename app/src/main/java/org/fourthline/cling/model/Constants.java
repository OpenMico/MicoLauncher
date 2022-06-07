package org.fourthline.cling.model;

/* loaded from: classes5.dex */
public interface Constants {
    public static final String ARG_TYPE_PREFIX = "A_ARG_TYPE_";
    public static final String IPV4_UPNP_MULTICAST_GROUP = "239.255.255.250";
    public static final String IPV6_UPNP_ADMINISTRATIVE_ADDRESS = "FF04::C";
    public static final String IPV6_UPNP_GLOBAL_ADDRESS = "FF0E::C";
    public static final String IPV6_UPNP_LINK_LOCAL_ADDRESS = "FF02::C";
    public static final String IPV6_UPNP_SITE_LOCAL_ADDRESS = "FF05::C";
    public static final String IPV6_UPNP_SUBNET_ADDRESS = "FF03::C";
    public static final int MIN_ADVERTISEMENT_AGE_SECONDS = 1800;
    public static final String NS_UPNP_CONTROL_10 = "urn:schemas-upnp-org:control-1-0";
    public static final String NS_UPNP_EVENT_10 = "urn:schemas-upnp-org:event-1-0";
    public static final String REGEX_ID = "[a-zA-Z_0-9\\-:\\.]{1,64}";
    public static final String REGEX_NAMESPACE = "[a-zA-Z0-9\\-\\.]+";
    public static final String REGEX_TYPE = "[a-zA-Z_0-9\\-]{1,64}";
    public static final String REGEX_UDA_NAME = "[a-zA-Z0-9^-_\\p{L}\\p{N}]{1}[a-zA-Z0-9^-_\\.\\\\p{L}\\\\p{N}\\p{Mc}\\p{Sk}]*";
    public static final String SOAP_NS_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String SOAP_URI_ENCODING_STYLE = "http://schemas.xmlsoap.org/soap/encoding/";
    public static final String SYSTEM_PROPERTY_ANNOUNCE_MAC_ADDRESS = "org.fourthline.cling.network.announceMACAddress";
    public static final int UPNP_MULTICAST_PORT = 1900;
}
