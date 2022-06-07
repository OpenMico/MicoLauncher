package org.fourthline.cling.model.message.header;

import com.xiaomi.mipush.sdk.Constants;
import org.apache.commons.lang3.StringUtils;
import org.fourthline.cling.model.ServerClientTokens;

/* loaded from: classes5.dex */
public class ServerHeader extends UpnpHeader<ServerClientTokens> {
    public ServerHeader() {
        setValue(new ServerClientTokens());
    }

    public ServerHeader(ServerClientTokens serverClientTokens) {
        setValue(serverClientTokens);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        String[] strArr;
        String[] strArr2;
        ServerClientTokens serverClientTokens = new ServerClientTokens();
        serverClientTokens.setOsName("UNKNOWN");
        serverClientTokens.setOsVersion("UNKNOWN");
        serverClientTokens.setProductName("UNKNOWN");
        serverClientTokens.setProductVersion("UNKNOWN");
        if (str.contains("UPnP/1.1")) {
            serverClientTokens.setMinorVersion(1);
        } else if (!str.contains("UPnP/1.")) {
            throw new InvalidHeaderException("Missing 'UPnP/1.' in server information: " + str);
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            try {
                if (str.charAt(i2) == ' ') {
                    i++;
                }
            } catch (Exception unused) {
                serverClientTokens.setOsName("UNKNOWN");
                serverClientTokens.setOsVersion("UNKNOWN");
                serverClientTokens.setProductName("UNKNOWN");
                serverClientTokens.setProductVersion("UNKNOWN");
            }
        }
        if (str.contains(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
            String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP);
            strArr2 = split[0].split("/");
            strArr = split[2].split("/");
        } else if (i > 2) {
            String trim = str.substring(0, str.indexOf("UPnP/1.")).trim();
            String trim2 = str.substring(str.indexOf("UPnP/1.") + 8).trim();
            strArr2 = trim.split("/");
            strArr = trim2.split("/");
        } else {
            String[] split2 = str.split(StringUtils.SPACE);
            strArr2 = split2[0].split("/");
            strArr = split2[2].split("/");
        }
        serverClientTokens.setOsName(strArr2[0].trim());
        if (strArr2.length > 1) {
            serverClientTokens.setOsVersion(strArr2[1].trim());
        }
        serverClientTokens.setProductName(strArr[0].trim());
        if (strArr.length > 1) {
            serverClientTokens.setProductVersion(strArr[1].trim());
        }
        setValue(serverClientTokens);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return getValue().getHttpToken();
    }
}
