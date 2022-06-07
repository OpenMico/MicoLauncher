package org.fourthline.cling.support.model.dlna.message;

import java.io.ByteArrayInputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.message.UpnpHeaders;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.fourthline.cling.support.model.dlna.message.header.DLNAHeader;

/* loaded from: classes5.dex */
public class DLNAHeaders extends UpnpHeaders {
    private static final Logger log = Logger.getLogger(DLNAHeaders.class.getName());
    protected Map<DLNAHeader.Type, List<UpnpHeader>> parsedDLNAHeaders;

    public DLNAHeaders() {
    }

    public DLNAHeaders(Map<String, List<String>> map) {
        super(map);
    }

    public DLNAHeaders(ByteArrayInputStream byteArrayInputStream) {
        super(byteArrayInputStream);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.fourthline.cling.model.message.UpnpHeaders
    public void parseHeaders() {
        if (this.parsedHeaders == null) {
            super.parseHeaders();
        }
        this.parsedDLNAHeaders = new LinkedHashMap();
        log.log(Level.FINE, "Parsing all HTTP headers for known UPnP headers: {0}", Integer.valueOf(size()));
        for (Map.Entry<String, List<String>> entry : entrySet()) {
            if (entry.getKey() != null) {
                DLNAHeader.Type byHttpName = DLNAHeader.Type.getByHttpName(entry.getKey());
                if (byHttpName == null) {
                    log.log(Level.FINE, "Ignoring non-UPNP HTTP header: {0}", entry.getKey());
                } else {
                    for (String str : entry.getValue()) {
                        DLNAHeader newInstance = DLNAHeader.newInstance(byHttpName, str);
                        if (newInstance == null || newInstance.getValue() == 0) {
                            log.log(Level.FINE, "Ignoring known but non-parsable header (value violates the UDA specification?) '{0}': {1}", new Object[]{byHttpName.getHttpName(), str});
                        } else {
                            addParsedValue(byHttpName, newInstance);
                        }
                    }
                }
            }
        }
    }

    protected void addParsedValue(DLNAHeader.Type type, UpnpHeader upnpHeader) {
        log.log(Level.FINE, "Adding parsed header: {0}", upnpHeader);
        List<UpnpHeader> list = this.parsedDLNAHeaders.get(type);
        if (list == null) {
            list = new LinkedList<>();
            this.parsedDLNAHeaders.put(type, list);
        }
        list.add(upnpHeader);
    }

    @Override // org.fourthline.cling.model.message.UpnpHeaders, org.seamless.http.Headers
    public List<String> put(String str, List<String> list) {
        this.parsedDLNAHeaders = null;
        return super.put(str, list);
    }

    @Override // org.fourthline.cling.model.message.UpnpHeaders, org.seamless.http.Headers
    public void add(String str, String str2) {
        this.parsedDLNAHeaders = null;
        super.add(str, str2);
    }

    @Override // org.fourthline.cling.model.message.UpnpHeaders, org.seamless.http.Headers, java.util.Map
    public List<String> remove(Object obj) {
        this.parsedDLNAHeaders = null;
        return super.remove(obj);
    }

    @Override // org.fourthline.cling.model.message.UpnpHeaders, org.seamless.http.Headers, java.util.Map
    public void clear() {
        this.parsedDLNAHeaders = null;
        super.clear();
    }

    public boolean containsKey(DLNAHeader.Type type) {
        if (this.parsedDLNAHeaders == null) {
            parseHeaders();
        }
        return this.parsedDLNAHeaders.containsKey(type);
    }

    public List<UpnpHeader> get(DLNAHeader.Type type) {
        if (this.parsedDLNAHeaders == null) {
            parseHeaders();
        }
        return this.parsedDLNAHeaders.get(type);
    }

    public void add(DLNAHeader.Type type, UpnpHeader upnpHeader) {
        super.add(type.getHttpName(), upnpHeader.getString());
        if (this.parsedDLNAHeaders != null) {
            addParsedValue(type, upnpHeader);
        }
    }

    public void remove(DLNAHeader.Type type) {
        super.remove((Object) type.getHttpName());
        Map<DLNAHeader.Type, List<UpnpHeader>> map = this.parsedDLNAHeaders;
        if (map != null) {
            map.remove(type);
        }
    }

    public UpnpHeader[] getAsArray(DLNAHeader.Type type) {
        if (this.parsedDLNAHeaders == null) {
            parseHeaders();
        }
        return this.parsedDLNAHeaders.get(type) != null ? (UpnpHeader[]) this.parsedDLNAHeaders.get(type).toArray(new UpnpHeader[this.parsedDLNAHeaders.get(type).size()]) : new UpnpHeader[0];
    }

    public UpnpHeader getFirstHeader(DLNAHeader.Type type) {
        if (getAsArray(type).length > 0) {
            return getAsArray(type)[0];
        }
        return null;
    }

    public <H extends UpnpHeader> H getFirstHeader(DLNAHeader.Type type, Class<H> cls) {
        UpnpHeader[] asArray = getAsArray(type);
        if (asArray.length == 0) {
            return null;
        }
        for (UpnpHeader upnpHeader : asArray) {
            H h = (H) upnpHeader;
            if (cls.isAssignableFrom(h.getClass())) {
                return h;
            }
        }
        return null;
    }

    @Override // org.fourthline.cling.model.message.UpnpHeaders
    public void log() {
        if (log.isLoggable(Level.FINE)) {
            super.log();
            Map<DLNAHeader.Type, List<UpnpHeader>> map = this.parsedDLNAHeaders;
            if (map != null && map.size() > 0) {
                log.fine("########################## PARSED DLNA HEADERS ##########################");
                for (Map.Entry<DLNAHeader.Type, List<UpnpHeader>> entry : this.parsedDLNAHeaders.entrySet()) {
                    log.log(Level.FINE, "=== TYPE: {0}", entry.getKey());
                    for (UpnpHeader upnpHeader : entry.getValue()) {
                        log.log(Level.FINE, "HEADER: {0}", upnpHeader);
                    }
                }
            }
            log.fine("####################################################################");
        }
    }
}
