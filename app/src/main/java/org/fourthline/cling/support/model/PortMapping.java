package org.fourthline.cling.support.model;

import com.xiaomi.mipush.sdk.Constants;
import java.util.Map;
import org.fourthline.cling.model.action.ActionArgumentValue;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.model.types.UnsignedIntegerTwoBytes;

/* loaded from: classes5.dex */
public class PortMapping {
    private String description;
    private boolean enabled;
    private UnsignedIntegerTwoBytes externalPort;
    private String internalClient;
    private UnsignedIntegerTwoBytes internalPort;
    private UnsignedIntegerFourBytes leaseDurationSeconds;
    private Protocol protocol;
    private String remoteHost;

    /* loaded from: classes5.dex */
    public enum Protocol {
        UDP,
        TCP
    }

    public PortMapping() {
    }

    public PortMapping(Map<String, ActionArgumentValue<Service>> map) {
        this(((Boolean) map.get("NewEnabled").getValue()).booleanValue(), (UnsignedIntegerFourBytes) map.get("NewLeaseDuration").getValue(), (String) map.get("NewRemoteHost").getValue(), (UnsignedIntegerTwoBytes) map.get("NewExternalPort").getValue(), (UnsignedIntegerTwoBytes) map.get("NewInternalPort").getValue(), (String) map.get("NewInternalClient").getValue(), Protocol.valueOf(map.get("NewProtocol").toString()), (String) map.get("NewPortMappingDescription").getValue());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PortMapping(int r10, java.lang.String r11, org.fourthline.cling.support.model.PortMapping.Protocol r12) {
        /*
            r9 = this;
            org.fourthline.cling.model.types.UnsignedIntegerFourBytes r2 = new org.fourthline.cling.model.types.UnsignedIntegerFourBytes
            r0 = 0
            r2.<init>(r0)
            org.fourthline.cling.model.types.UnsignedIntegerTwoBytes r4 = new org.fourthline.cling.model.types.UnsignedIntegerTwoBytes
            long r0 = (long) r10
            r4.<init>(r0)
            org.fourthline.cling.model.types.UnsignedIntegerTwoBytes r5 = new org.fourthline.cling.model.types.UnsignedIntegerTwoBytes
            r5.<init>(r0)
            r1 = 1
            r3 = 0
            r8 = 0
            r0 = r9
            r6 = r11
            r7 = r12
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.support.model.PortMapping.<init>(int, java.lang.String, org.fourthline.cling.support.model.PortMapping$Protocol):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PortMapping(int r10, java.lang.String r11, org.fourthline.cling.support.model.PortMapping.Protocol r12, java.lang.String r13) {
        /*
            r9 = this;
            org.fourthline.cling.model.types.UnsignedIntegerFourBytes r2 = new org.fourthline.cling.model.types.UnsignedIntegerFourBytes
            r0 = 0
            r2.<init>(r0)
            org.fourthline.cling.model.types.UnsignedIntegerTwoBytes r4 = new org.fourthline.cling.model.types.UnsignedIntegerTwoBytes
            long r0 = (long) r10
            r4.<init>(r0)
            org.fourthline.cling.model.types.UnsignedIntegerTwoBytes r5 = new org.fourthline.cling.model.types.UnsignedIntegerTwoBytes
            r5.<init>(r0)
            r1 = 1
            r3 = 0
            r0 = r9
            r6 = r11
            r7 = r12
            r8 = r13
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.support.model.PortMapping.<init>(int, java.lang.String, org.fourthline.cling.support.model.PortMapping$Protocol, java.lang.String):void");
    }

    public PortMapping(String str, UnsignedIntegerTwoBytes unsignedIntegerTwoBytes, Protocol protocol) {
        this(true, new UnsignedIntegerFourBytes(0L), str, unsignedIntegerTwoBytes, null, null, protocol, null);
    }

    public PortMapping(boolean z, UnsignedIntegerFourBytes unsignedIntegerFourBytes, String str, UnsignedIntegerTwoBytes unsignedIntegerTwoBytes, UnsignedIntegerTwoBytes unsignedIntegerTwoBytes2, String str2, Protocol protocol, String str3) {
        this.enabled = z;
        this.leaseDurationSeconds = unsignedIntegerFourBytes;
        this.remoteHost = str;
        this.externalPort = unsignedIntegerTwoBytes;
        this.internalPort = unsignedIntegerTwoBytes2;
        this.internalClient = str2;
        this.protocol = protocol;
        this.description = str3;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public UnsignedIntegerFourBytes getLeaseDurationSeconds() {
        return this.leaseDurationSeconds;
    }

    public void setLeaseDurationSeconds(UnsignedIntegerFourBytes unsignedIntegerFourBytes) {
        this.leaseDurationSeconds = unsignedIntegerFourBytes;
    }

    public boolean hasRemoteHost() {
        String str = this.remoteHost;
        return str != null && str.length() > 0;
    }

    public String getRemoteHost() {
        String str = this.remoteHost;
        return str == null ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : str;
    }

    public void setRemoteHost(String str) {
        if (str == null || str.equals(Constants.ACCEPT_TIME_SEPARATOR_SERVER) || str.length() == 0) {
            str = null;
        }
        this.remoteHost = str;
    }

    public UnsignedIntegerTwoBytes getExternalPort() {
        return this.externalPort;
    }

    public void setExternalPort(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
        this.externalPort = unsignedIntegerTwoBytes;
    }

    public UnsignedIntegerTwoBytes getInternalPort() {
        return this.internalPort;
    }

    public void setInternalPort(UnsignedIntegerTwoBytes unsignedIntegerTwoBytes) {
        this.internalPort = unsignedIntegerTwoBytes;
    }

    public String getInternalClient() {
        return this.internalClient;
    }

    public void setInternalClient(String str) {
        this.internalClient = str;
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public boolean hasDescription() {
        return this.description != null;
    }

    public String getDescription() {
        String str = this.description;
        return str == null ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : str;
    }

    public void setDescription(String str) {
        if (str == null || str.equals(Constants.ACCEPT_TIME_SEPARATOR_SERVER) || str.length() == 0) {
            str = null;
        }
        this.description = str;
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") Protocol: " + getProtocol() + ", " + getExternalPort() + " => " + getInternalClient();
    }
}
