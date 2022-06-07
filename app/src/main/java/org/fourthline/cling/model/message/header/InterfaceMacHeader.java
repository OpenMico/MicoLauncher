package org.fourthline.cling.model.message.header;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import org.seamless.util.io.HexBin;

/* loaded from: classes5.dex */
public class InterfaceMacHeader extends UpnpHeader<byte[]> {
    public InterfaceMacHeader() {
    }

    public InterfaceMacHeader(byte[] bArr) {
        setValue(bArr);
    }

    public InterfaceMacHeader(String str) {
        setString(str);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public void setString(String str) throws InvalidHeaderException {
        byte[] stringToBytes = HexBin.stringToBytes(str, Constants.COLON_SEPARATOR);
        setValue(stringToBytes);
        if (stringToBytes.length != 6) {
            throw new InvalidHeaderException("Invalid MAC address: " + str);
        }
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String getString() {
        return HexBin.bytesToString(getValue(), Constants.COLON_SEPARATOR);
    }

    @Override // org.fourthline.cling.model.message.header.UpnpHeader
    public String toString() {
        return "(" + getClass().getSimpleName() + ") '" + getString() + LrcRow.SINGLE_QUOTE;
    }
}
