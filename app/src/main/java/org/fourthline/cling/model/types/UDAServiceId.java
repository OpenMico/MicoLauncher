package org.fourthline.cling.model.types;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class UDAServiceId extends ServiceId {
    public static final String BROKEN_DEFAULT_NAMESPACE = "schemas-upnp-org";
    public static final String DEFAULT_NAMESPACE = "upnp-org";
    private static Logger log = Logger.getLogger(UDAServiceId.class.getName());
    public static final Pattern PATTERN = Pattern.compile("urn:upnp-org:serviceId:([a-zA-Z_0-9\\-:\\.]{1,64})");
    public static final Pattern BROKEN_PATTERN = Pattern.compile("urn:schemas-upnp-org:service:([a-zA-Z_0-9\\-:\\.]{1,64})");

    public UDAServiceId(String str) {
        super(DEFAULT_NAMESPACE, str);
    }

    public static UDAServiceId valueOf(String str) throws InvalidValueException {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.matches() && matcher.groupCount() >= 1) {
            return new UDAServiceId(matcher.group(1));
        }
        Matcher matcher2 = BROKEN_PATTERN.matcher(str);
        if (matcher2.matches() && matcher2.groupCount() >= 1) {
            return new UDAServiceId(matcher2.group(1));
        }
        Matcher matcher3 = Pattern.compile("urn:upnp-orgerviceId:urnchemas-upnp-orgervice:([a-zA-Z_0-9\\-:\\.]{1,64})").matcher(str);
        if (matcher3.matches()) {
            Logger logger = log;
            logger.warning("UPnP specification violation, recovering from Eyecon garbage: " + str);
            return new UDAServiceId(matcher3.group(1));
        } else if ("ContentDirectory".equals(str) || "ConnectionManager".equals(str) || "RenderingControl".equals(str) || "AVTransport".equals(str)) {
            Logger logger2 = log;
            logger2.warning("UPnP specification violation, fixing broken Service ID: " + str);
            return new UDAServiceId(str);
        } else {
            throw new InvalidValueException("Can't parse UDA service ID string (upnp-org/id): " + str);
        }
    }
}
