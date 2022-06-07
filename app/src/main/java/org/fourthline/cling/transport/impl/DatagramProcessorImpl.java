package org.fourthline.cling.transport.impl;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.http.HttpVersions;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.message.IncomingDatagramMessage;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.UpnpRequest;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.transport.spi.DatagramProcessor;
import org.seamless.http.Headers;

/* loaded from: classes5.dex */
public class DatagramProcessorImpl implements DatagramProcessor {
    private static Logger log = Logger.getLogger(DatagramProcessor.class.getName());

    @Override // org.fourthline.cling.transport.spi.DatagramProcessor
    public IncomingDatagramMessage read(InetAddress inetAddress, DatagramPacket datagramPacket) throws UnsupportedDataException {
        try {
            if (log.isLoggable(Level.FINER)) {
                log.finer("===================================== DATAGRAM BEGIN ============================================");
                log.finer(new String(datagramPacket.getData(), "UTF-8"));
                log.finer("-===================================== DATAGRAM END =============================================");
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData());
            String[] split = Headers.readLine(byteArrayInputStream).split(StringUtils.SPACE);
            if (split[0].startsWith("HTTP/1.")) {
                return readResponseMessage(inetAddress, datagramPacket, byteArrayInputStream, Integer.valueOf(split[1]).intValue(), split[2], split[0]);
            }
            return readRequestMessage(inetAddress, datagramPacket, byteArrayInputStream, split[0], split[2]);
        } catch (Exception e) {
            throw new UnsupportedDataException("Could not parse headers: " + e, e, datagramPacket.getData());
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [org.fourthline.cling.model.message.UpnpOperation] */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // org.fourthline.cling.transport.spi.DatagramProcessor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.net.DatagramPacket write(org.fourthline.cling.model.message.OutgoingDatagramMessage r6) throws org.fourthline.cling.model.UnsupportedDataException {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.transport.impl.DatagramProcessorImpl.write(org.fourthline.cling.model.message.OutgoingDatagramMessage):java.net.DatagramPacket");
    }

    protected IncomingDatagramMessage readRequestMessage(InetAddress inetAddress, DatagramPacket datagramPacket, ByteArrayInputStream byteArrayInputStream, String str, String str2) throws Exception {
        UpnpHeaders upnpHeaders = new UpnpHeaders(byteArrayInputStream);
        UpnpRequest upnpRequest = new UpnpRequest(UpnpRequest.Method.getByHttpName(str));
        upnpRequest.setHttpMinorVersion(str2.toUpperCase(Locale.ROOT).equals(HttpVersions.HTTP_1_1) ? 1 : 0);
        IncomingDatagramMessage incomingDatagramMessage = new IncomingDatagramMessage(upnpRequest, datagramPacket.getAddress(), datagramPacket.getPort(), inetAddress);
        incomingDatagramMessage.setHeaders(upnpHeaders);
        return incomingDatagramMessage;
    }

    protected IncomingDatagramMessage readResponseMessage(InetAddress inetAddress, DatagramPacket datagramPacket, ByteArrayInputStream byteArrayInputStream, int i, String str, String str2) throws Exception {
        UpnpHeaders upnpHeaders = new UpnpHeaders(byteArrayInputStream);
        UpnpResponse upnpResponse = new UpnpResponse(i, str);
        upnpResponse.setHttpMinorVersion(str2.toUpperCase(Locale.ROOT).equals(HttpVersions.HTTP_1_1) ? 1 : 0);
        IncomingDatagramMessage incomingDatagramMessage = new IncomingDatagramMessage(upnpResponse, datagramPacket.getAddress(), datagramPacket.getPort(), inetAddress);
        incomingDatagramMessage.setHeaders(upnpHeaders);
        return incomingDatagramMessage;
    }
}
