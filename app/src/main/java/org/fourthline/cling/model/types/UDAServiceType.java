package org.fourthline.cling.model.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class UDAServiceType extends ServiceType {
    public static final String DEFAULT_NAMESPACE = "schemas-upnp-org";
    public static final Pattern PATTERN = Pattern.compile("urn:schemas-upnp-org:service:([a-zA-Z_0-9\\-]{1,64}):([0-9]+).*");

    public UDAServiceType(String str) {
        this(str, 1);
    }

    public UDAServiceType(String str, int i) {
        super("schemas-upnp-org", str, i);
    }

    public static UDAServiceType valueOf(String str) throws InvalidValueException {
        Matcher matcher = PATTERN.matcher(str);
        try {
            if (matcher.matches()) {
                return new UDAServiceType(matcher.group(1), Integer.valueOf(matcher.group(2)).intValue());
            }
            throw new InvalidValueException("Can't parse UDA service type string (namespace/type/version): " + str);
        } catch (RuntimeException e) {
            throw new InvalidValueException(String.format("Can't parse UDA service type string (namespace/type/version) '%s': %s", str, e.toString()));
        }
    }
}
