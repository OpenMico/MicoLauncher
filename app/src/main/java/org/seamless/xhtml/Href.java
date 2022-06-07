package org.seamless.xhtml;

import java.net.URI;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class Href {
    private URI uri;

    public Href(URI uri) {
        this.uri = uri;
    }

    public URI getURI() {
        return this.uri;
    }

    public static Href fromString(String str) {
        if (str == null) {
            return null;
        }
        return new Href(URI.create(str.replaceAll(StringUtils.SPACE, "%20")));
    }

    public String toString() {
        return getURI().toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.uri.equals(((Href) obj).uri);
    }

    public int hashCode() {
        return this.uri.hashCode();
    }
}
