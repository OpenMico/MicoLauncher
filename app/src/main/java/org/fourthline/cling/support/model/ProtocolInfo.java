package org.fourthline.cling.support.model;

import com.xiaomi.mipush.sdk.Constants;
import org.fourthline.cling.model.types.InvalidValueException;
import org.seamless.util.MimeType;

/* loaded from: classes5.dex */
public class ProtocolInfo {
    public static final String WILDCARD = "*";
    protected String additionalInfo;
    protected String contentFormat;
    protected String network;
    protected Protocol protocol;

    public ProtocolInfo(String str) throws InvalidValueException {
        this.protocol = Protocol.ALL;
        this.network = "*";
        this.contentFormat = "*";
        this.additionalInfo = "*";
        if (str != null) {
            String trim = str.trim();
            String[] split = trim.split(Constants.COLON_SEPARATOR);
            if (split.length == 4) {
                this.protocol = Protocol.value(split[0]);
                this.network = split[1];
                this.contentFormat = split[2];
                this.additionalInfo = split[3];
                return;
            }
            throw new InvalidValueException("Can't parse ProtocolInfo string: " + trim);
        }
        throw new NullPointerException();
    }

    public ProtocolInfo(MimeType mimeType) {
        this.protocol = Protocol.ALL;
        this.network = "*";
        this.contentFormat = "*";
        this.additionalInfo = "*";
        this.protocol = Protocol.HTTP_GET;
        this.contentFormat = mimeType.toString();
    }

    public ProtocolInfo(Protocol protocol, String str, String str2, String str3) {
        this.protocol = Protocol.ALL;
        this.network = "*";
        this.contentFormat = "*";
        this.additionalInfo = "*";
        this.protocol = protocol;
        this.network = str;
        this.contentFormat = str2;
        this.additionalInfo = str3;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public String getNetwork() {
        return this.network;
    }

    public String getContentFormat() {
        return this.contentFormat;
    }

    public MimeType getContentFormatMimeType() throws IllegalArgumentException {
        return MimeType.valueOf(this.contentFormat);
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ProtocolInfo protocolInfo = (ProtocolInfo) obj;
        return this.additionalInfo.equals(protocolInfo.additionalInfo) && this.contentFormat.equals(protocolInfo.contentFormat) && this.network.equals(protocolInfo.network) && this.protocol == protocolInfo.protocol;
    }

    public int hashCode() {
        return (((((this.protocol.hashCode() * 31) + this.network.hashCode()) * 31) + this.contentFormat.hashCode()) * 31) + this.additionalInfo.hashCode();
    }

    public String toString() {
        return this.protocol.toString() + Constants.COLON_SEPARATOR + this.network + Constants.COLON_SEPARATOR + this.contentFormat + Constants.COLON_SEPARATOR + this.additionalInfo;
    }
}
