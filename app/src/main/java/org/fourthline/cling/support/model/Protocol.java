package org.fourthline.cling.support.model;

import com.umeng.commonsdk.framework.UMModuleRegister;
import java.util.logging.Logger;

/* loaded from: classes5.dex */
public enum Protocol {
    ALL("*"),
    HTTP_GET("http-get"),
    RTSP_RTP_UDP("rtsp-rtp-udp"),
    INTERNAL(UMModuleRegister.INNER),
    IEC61883("iec61883"),
    XBMC_GET("xbmc-get"),
    OTHER("other");
    
    private static final Logger LOG = Logger.getLogger(Protocol.class.getName());
    private String protocolString;

    Protocol(String str) {
        this.protocolString = str;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.protocolString;
    }

    public static Protocol value(String str) {
        Protocol[] values = values();
        for (Protocol protocol : values) {
            if (protocol.toString().equals(str)) {
                return protocol;
            }
        }
        LOG.info("Unsupported OTHER protocol string: " + str);
        return OTHER;
    }
}
