package org.fourthline.cling.support.model.dlna;

import java.util.EnumMap;
import java.util.Map;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.support.model.Protocol;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.dlna.DLNAAttribute;
import org.seamless.util.MimeType;

/* loaded from: classes5.dex */
public class DLNAProtocolInfo extends ProtocolInfo {
    protected final Map<DLNAAttribute.Type, DLNAAttribute> attributes;

    public DLNAProtocolInfo(String str) throws InvalidValueException {
        super(str);
        this.attributes = new EnumMap(DLNAAttribute.Type.class);
        parseAdditionalInfo();
    }

    public DLNAProtocolInfo(MimeType mimeType) {
        super(mimeType);
        this.attributes = new EnumMap(DLNAAttribute.Type.class);
    }

    public DLNAProtocolInfo(DLNAProfiles dLNAProfiles) {
        super(MimeType.valueOf(dLNAProfiles.getContentFormat()));
        this.attributes = new EnumMap(DLNAAttribute.Type.class);
        this.attributes.put(DLNAAttribute.Type.DLNA_ORG_PN, new DLNAProfileAttribute(dLNAProfiles));
        this.additionalInfo = getAttributesString();
    }

    public DLNAProtocolInfo(DLNAProfiles dLNAProfiles, EnumMap<DLNAAttribute.Type, DLNAAttribute> enumMap) {
        super(MimeType.valueOf(dLNAProfiles.getContentFormat()));
        this.attributes = new EnumMap(DLNAAttribute.Type.class);
        this.attributes.putAll(enumMap);
        this.attributes.put(DLNAAttribute.Type.DLNA_ORG_PN, new DLNAProfileAttribute(dLNAProfiles));
        this.additionalInfo = getAttributesString();
    }

    public DLNAProtocolInfo(Protocol protocol, String str, String str2, String str3) {
        super(protocol, str, str2, str3);
        this.attributes = new EnumMap(DLNAAttribute.Type.class);
        parseAdditionalInfo();
    }

    public DLNAProtocolInfo(Protocol protocol, String str, String str2, EnumMap<DLNAAttribute.Type, DLNAAttribute> enumMap) {
        super(protocol, str, str2, "");
        this.attributes = new EnumMap(DLNAAttribute.Type.class);
        this.attributes.putAll(enumMap);
        this.additionalInfo = getAttributesString();
    }

    public DLNAProtocolInfo(ProtocolInfo protocolInfo) {
        this(protocolInfo.getProtocol(), protocolInfo.getNetwork(), protocolInfo.getContentFormat(), protocolInfo.getAdditionalInfo());
    }

    public boolean contains(DLNAAttribute.Type type) {
        return this.attributes.containsKey(type);
    }

    public DLNAAttribute getAttribute(DLNAAttribute.Type type) {
        return this.attributes.get(type);
    }

    public Map<DLNAAttribute.Type, DLNAAttribute> getAttributes() {
        return this.attributes;
    }

    protected String getAttributesString() {
        String str = "";
        DLNAAttribute.Type[] values = DLNAAttribute.Type.values();
        for (DLNAAttribute.Type type : values) {
            String string = this.attributes.containsKey(type) ? this.attributes.get(type).getString() : null;
            if (!(string == null || string.length() == 0)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(str.length() == 0 ? "" : ";");
                sb.append(type.getAttributeName());
                sb.append("=");
                sb.append(string);
                str = sb.toString();
            }
        }
        return str;
    }

    protected void parseAdditionalInfo() {
        DLNAAttribute.Type valueOfAttributeName;
        if (this.additionalInfo != null) {
            for (String str : this.additionalInfo.split(";")) {
                String[] split = str.split("=");
                if (split.length == 2 && (valueOfAttributeName = DLNAAttribute.Type.valueOfAttributeName(split[0])) != null) {
                    this.attributes.put(valueOfAttributeName, DLNAAttribute.newInstance(valueOfAttributeName, split[1], getContentFormat()));
                }
            }
        }
    }
}
