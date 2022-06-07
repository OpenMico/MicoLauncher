package org.fourthline.cling.model.message;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.message.header.UpnpHeader;
import org.seamless.http.Headers;

/* loaded from: classes5.dex */
public class UpnpHeaders extends Headers {
    private static final Logger log = Logger.getLogger(UpnpHeaders.class.getName());
    protected Map<UpnpHeader.Type, List<UpnpHeader>> parsedHeaders;

    public UpnpHeaders() {
    }

    public UpnpHeaders(Map<String, List<String>> map) {
        super(map);
    }

    public UpnpHeaders(ByteArrayInputStream byteArrayInputStream) {
        super(byteArrayInputStream);
    }

    public UpnpHeaders(boolean z) {
        super(z);
    }

    public void parseHeaders() {
        this.parsedHeaders = new LinkedHashMap();
        if (log.isLoggable(Level.FINE)) {
            Logger logger = log;
            logger.fine("Parsing all HTTP headers for known UPnP headers: " + size());
        }
        for (Map.Entry<String, List<String>> entry : entrySet()) {
            if (entry.getKey() != null) {
                UpnpHeader.Type byHttpName = UpnpHeader.Type.getByHttpName(entry.getKey());
                if (byHttpName != null) {
                    for (String str : entry.getValue()) {
                        UpnpHeader newInstance = UpnpHeader.newInstance(byHttpName, str);
                        if (newInstance != null && newInstance.getValue() != null) {
                            addParsedValue(byHttpName, newInstance);
                        } else if (log.isLoggable(Level.FINE)) {
                            Logger logger2 = log;
                            logger2.fine("Ignoring known but irrelevant header (value violates the UDA specification?) '" + byHttpName.getHttpName() + "': " + str);
                        }
                    }
                } else if (log.isLoggable(Level.FINE)) {
                    Logger logger3 = log;
                    logger3.fine("Ignoring non-UPNP HTTP header: " + entry.getKey());
                }
            }
        }
    }

    protected void addParsedValue(UpnpHeader.Type type, UpnpHeader upnpHeader) {
        if (log.isLoggable(Level.FINE)) {
            Logger logger = log;
            logger.fine("Adding parsed header: " + upnpHeader);
        }
        List<UpnpHeader> list = this.parsedHeaders.get(type);
        if (list == null) {
            list = new LinkedList<>();
            this.parsedHeaders.put(type, list);
        }
        list.add(upnpHeader);
    }

    @Override // org.seamless.http.Headers
    public List<String> put(String str, List<String> list) {
        this.parsedHeaders = null;
        return super.put(str, list);
    }

    @Override // org.seamless.http.Headers
    public void add(String str, String str2) {
        this.parsedHeaders = null;
        super.add(str, str2);
    }

    @Override // org.seamless.http.Headers, java.util.Map
    public List<String> remove(Object obj) {
        this.parsedHeaders = null;
        return super.remove(obj);
    }

    @Override // org.seamless.http.Headers, java.util.Map
    public void clear() {
        this.parsedHeaders = null;
        super.clear();
    }

    public boolean containsKey(UpnpHeader.Type type) {
        if (this.parsedHeaders == null) {
            parseHeaders();
        }
        return this.parsedHeaders.containsKey(type);
    }

    public List<UpnpHeader> get(UpnpHeader.Type type) {
        if (this.parsedHeaders == null) {
            parseHeaders();
        }
        return this.parsedHeaders.get(type);
    }

    public void add(UpnpHeader.Type type, UpnpHeader upnpHeader) {
        super.add(type.getHttpName(), upnpHeader.getString());
        if (this.parsedHeaders != null) {
            addParsedValue(type, upnpHeader);
        }
    }

    public void remove(UpnpHeader.Type type) {
        super.remove((Object) type.getHttpName());
        Map<UpnpHeader.Type, List<UpnpHeader>> map = this.parsedHeaders;
        if (map != null) {
            map.remove(type);
        }
    }

    public UpnpHeader[] getAsArray(UpnpHeader.Type type) {
        if (this.parsedHeaders == null) {
            parseHeaders();
        }
        return this.parsedHeaders.get(type) != null ? (UpnpHeader[]) this.parsedHeaders.get(type).toArray(new UpnpHeader[this.parsedHeaders.get(type).size()]) : new UpnpHeader[0];
    }

    public UpnpHeader getFirstHeader(UpnpHeader.Type type) {
        if (getAsArray(type).length > 0) {
            return getAsArray(type)[0];
        }
        return null;
    }

    public <H extends UpnpHeader> H getFirstHeader(UpnpHeader.Type type, Class<H> cls) {
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

    public String getFirstHeaderString(UpnpHeader.Type type) {
        UpnpHeader firstHeader = getFirstHeader(type);
        if (firstHeader != null) {
            return firstHeader.getString();
        }
        return null;
    }

    public void log() {
        if (log.isLoggable(Level.FINE)) {
            log.fine("############################ RAW HEADERS ###########################");
            for (Map.Entry<String, List<String>> entry : entrySet()) {
                Logger logger = log;
                logger.fine("=== NAME : " + entry.getKey());
                Iterator<String> it = entry.getValue().iterator();
                while (it.hasNext()) {
                    Logger logger2 = log;
                    logger2.fine("VALUE: " + it.next());
                }
            }
            Map<UpnpHeader.Type, List<UpnpHeader>> map = this.parsedHeaders;
            if (map != null && map.size() > 0) {
                log.fine("########################## PARSED HEADERS ##########################");
                for (Map.Entry<UpnpHeader.Type, List<UpnpHeader>> entry2 : this.parsedHeaders.entrySet()) {
                    Logger logger3 = log;
                    logger3.fine("=== TYPE: " + entry2.getKey());
                    Iterator<UpnpHeader> it2 = entry2.getValue().iterator();
                    while (it2.hasNext()) {
                        Logger logger4 = log;
                        logger4.fine("HEADER: " + it2.next());
                    }
                }
            }
            log.fine("####################################################################");
        }
    }
}
