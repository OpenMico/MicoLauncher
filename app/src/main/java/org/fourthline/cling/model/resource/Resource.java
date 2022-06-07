package org.fourthline.cling.model.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.fourthline.cling.model.ExpirationDetails;

/* loaded from: classes5.dex */
public class Resource<M> {
    private M model;
    private URI pathQuery;

    public void maintain(List<Runnable> list, ExpirationDetails expirationDetails) {
    }

    public void shutdown() {
    }

    public Resource(URI uri, M m) {
        try {
            this.pathQuery = new URI(null, null, uri.getPath(), uri.getQuery(), null);
            this.model = m;
            if (m == null) {
                throw new IllegalArgumentException("Model instance must not be null");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public URI getPathQuery() {
        return this.pathQuery;
    }

    public M getModel() {
        return this.model;
    }

    public boolean matches(URI uri) {
        return uri.equals(getPathQuery());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && getPathQuery().equals(((Resource) obj).getPathQuery());
    }

    public int hashCode() {
        return getPathQuery().hashCode();
    }

    public String toString() {
        return "(" + getClass().getSimpleName() + ") URI: " + getPathQuery();
    }
}
