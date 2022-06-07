package org.fourthline.cling.support.model.dlna.message.header;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.message.header.InvalidHeaderException;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class DLNAHeader<T> extends UpnpHeader<T> {
    private static final Logger log = Logger.getLogger(DLNAHeader.class.getName());

    /* loaded from: classes5.dex */
    public enum Type {
        TimeSeekRange("TimeSeekRange.dlna.org", TimeSeekRangeHeader.class),
        XSeekRange("X-Seek-Range", TimeSeekRangeHeader.class),
        PlaySpeed("PlaySpeed.dlna.org", PlaySpeedHeader.class),
        AvailableSeekRange("availableSeekRange.dlna.org", AvailableSeekRangeHeader.class),
        GetAvailableSeekRange("getAvailableSeekRange.dlna.org", GetAvailableSeekRangeHeader.class),
        GetContentFeatures("getcontentFeatures.dlna.org", GetContentFeaturesHeader.class),
        ContentFeatures("contentFeatures.dlna.org", ContentFeaturesHeader.class),
        TransferMode("transferMode.dlna.org", TransferModeHeader.class),
        FriendlyName("friendlyName.dlna.org", FriendlyNameHeader.class),
        PeerManager("peerManager.dlna.org", PeerManagerHeader.class),
        AvailableRange("Available-Range.dlna.org", AvailableRangeHeader.class),
        SCID("scid.dlna.org", SCIDHeader.class),
        RealTimeInfo("realTimeInfo.dlna.org", RealTimeInfoHeader.class),
        ScmsFlag("scmsFlag.dlna.org", ScmsFlagHeader.class),
        WCT("WCT.dlna.org", WCTHeader.class),
        MaxPrate("Max-Prate.dlna.org", MaxPrateHeader.class),
        EventType("Event-Type.dlna.org", EventTypeHeader.class),
        Supported("Supported", SupportedHeader.class),
        BufferInfo("Buffer-Info.dlna.org", BufferInfoHeader.class),
        RTPH264DeInterleaving("rtp-h264-deint-buf-cap.dlna.org", BufferBytesHeader.class),
        RTPAACDeInterleaving("rtp-aac-deint-buf-cap.dlna.org", BufferBytesHeader.class),
        RTPAMRDeInterleaving("rtp-amr-deint-buf-cap.dlna.org", BufferBytesHeader.class),
        RTPAMRWBPlusDeInterleaving("rtp-amrwbplus-deint-buf-cap.dlna.org", BufferBytesHeader.class),
        PRAGMA("PRAGMA", PragmaHeader.class);
        
        private static Map<String, Type> byName = new HashMap<String, Type>() { // from class: org.fourthline.cling.support.model.dlna.message.header.DLNAHeader.Type.1
            {
                Type[] values = Type.values();
                for (Type type : values) {
                    put(type.getHttpName(), type);
                }
            }
        };
        private Class<? extends DLNAHeader>[] headerTypes;
        private String httpName;

        @SafeVarargs
        Type(String str, Class... clsArr) {
            this.httpName = str;
            this.headerTypes = clsArr;
        }

        public String getHttpName() {
            return this.httpName;
        }

        public Class<? extends DLNAHeader>[] getHeaderTypes() {
            return this.headerTypes;
        }

        public boolean isValidHeaderType(Class<? extends DLNAHeader> cls) {
            for (Class<? extends DLNAHeader> cls2 : getHeaderTypes()) {
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
            return byName.get(str);
        }
    }

    public static DLNAHeader newInstance(Type type, String str) {
        Exception e;
        DLNAHeader dLNAHeader = null;
        for (int i = 0; i < type.getHeaderTypes().length && dLNAHeader == null; i++) {
            Class<? extends DLNAHeader> cls = type.getHeaderTypes()[i];
            try {
                try {
                    log.finest("Trying to parse '" + type + "' with class: " + cls.getSimpleName());
                    DLNAHeader dLNAHeader2 = (DLNAHeader) cls.newInstance();
                    if (str != null) {
                        try {
                            dLNAHeader2.setString(str);
                        } catch (Exception e2) {
                            e = e2;
                            dLNAHeader = dLNAHeader2;
                            log.severe("Error instantiating header of type '" + type + "' with value: " + str);
                            log.log(Level.SEVERE, "Exception root cause: ", Exceptions.unwrap(e));
                        }
                    }
                    dLNAHeader = dLNAHeader2;
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (InvalidHeaderException e4) {
                log.finest("Invalid header value for tested type: " + cls.getSimpleName() + " - " + e4.getMessage());
                dLNAHeader = null;
            }
        }
        return dLNAHeader;
    }
}
