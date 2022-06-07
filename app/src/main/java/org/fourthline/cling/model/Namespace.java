package org.fourthline.cling.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.resource.Resource;
import org.seamless.util.URIUtil;

/* loaded from: classes5.dex */
public class Namespace {
    public static final String CALLBACK_FILE = "/cb";
    public static final String CONTROL = "/action";
    public static final String DESCRIPTOR_FILE = "/desc";
    public static final String DEVICE = "/dev";
    public static final String EVENTS = "/event";
    public static final String SERVICE = "/svc";
    private static final Logger log = Logger.getLogger(Namespace.class.getName());
    protected final URI basePath;
    protected final String decodedPath;

    public Namespace() {
        this("");
    }

    public Namespace(String str) {
        this(URI.create(str));
    }

    public Namespace(URI uri) {
        this.basePath = uri;
        this.decodedPath = uri.getPath();
    }

    public URI getBasePath() {
        return this.basePath;
    }

    public URI getPath(Device device) {
        return appendPathToBaseURI(getDevicePath(device));
    }

    public URI getPath(Service service) {
        return appendPathToBaseURI(getServicePath(service));
    }

    public URI getDescriptorPath(Device device) {
        return appendPathToBaseURI(getDevicePath(device.getRoot()) + DESCRIPTOR_FILE);
    }

    public String getDescriptorPathString(Device device) {
        return this.decodedPath + getDevicePath(device.getRoot()) + DESCRIPTOR_FILE;
    }

    public URI getDescriptorPath(Service service) {
        return appendPathToBaseURI(getServicePath(service) + DESCRIPTOR_FILE);
    }

    public URI getControlPath(Service service) {
        return appendPathToBaseURI(getServicePath(service) + CONTROL);
    }

    public URI getIconPath(Icon icon) {
        return appendPathToBaseURI(getDevicePath(icon.getDevice()) + "/" + icon.getUri().toString());
    }

    public URI getEventSubscriptionPath(Service service) {
        return appendPathToBaseURI(getServicePath(service) + EVENTS);
    }

    public URI getEventCallbackPath(Service service) {
        return appendPathToBaseURI(getServicePath(service) + EVENTS + CALLBACK_FILE);
    }

    public String getEventCallbackPathString(Service service) {
        return this.decodedPath + getServicePath(service) + EVENTS + CALLBACK_FILE;
    }

    public URI prefixIfRelative(Device device, URI uri) {
        if (uri.isAbsolute() || uri.getPath().startsWith("/")) {
            return uri;
        }
        return appendPathToBaseURI(getDevicePath(device) + "/" + uri);
    }

    public boolean isControlPath(URI uri) {
        return uri.toString().endsWith(CONTROL);
    }

    public boolean isEventSubscriptionPath(URI uri) {
        return uri.toString().endsWith(EVENTS);
    }

    public boolean isEventCallbackPath(URI uri) {
        return uri.toString().endsWith(CALLBACK_FILE);
    }

    public Resource[] getResources(Device device) throws ValidationException {
        if (!device.isRoot()) {
            return null;
        }
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        log.fine("Discovering local resources of device graph");
        Resource[] discoverResources = device.discoverResources(this);
        for (Resource resource : discoverResources) {
            log.finer("Discovered: " + resource);
            if (!hashSet.add(resource)) {
                log.finer("Local resource already exists, queueing validation error");
                arrayList.add(new ValidationError(getClass(), "resources", "Local URI namespace conflict between resources of device: " + resource));
            }
        }
        if (arrayList.size() <= 0) {
            return (Resource[]) hashSet.toArray(new Resource[hashSet.size()]);
        }
        throw new ValidationException("Validation of device graph failed, call getErrors() on exception", arrayList);
    }

    protected URI appendPathToBaseURI(String str) {
        try {
            String scheme = this.basePath.getScheme();
            String host = this.basePath.getHost();
            int port = this.basePath.getPort();
            return new URI(scheme, null, host, port, this.decodedPath + str, null, null);
        } catch (URISyntaxException unused) {
            return URI.create(this.basePath + str);
        }
    }

    protected String getDevicePath(Device device) {
        if (device.getIdentity().getUdn() != null) {
            return DEVICE + "/" + URIUtil.encodePathSegment(device.getIdentity().getUdn().getIdentifierString());
        }
        throw new IllegalStateException("Can't generate local URI prefix without UDN");
    }

    protected String getServicePath(Service service) {
        if (service.getServiceId() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getDevicePath(service.getDevice()));
            sb.append(SERVICE + "/" + service.getServiceId().getNamespace() + "/" + service.getServiceId().getId());
            return sb.toString();
        }
        throw new IllegalStateException("Can't generate local URI prefix without service ID");
    }
}
