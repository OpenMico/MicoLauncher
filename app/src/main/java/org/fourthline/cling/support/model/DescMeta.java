package org.fourthline.cling.support.model;

import java.net.URI;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/* loaded from: classes5.dex */
public class DescMeta<M> {
    protected String id;
    protected M metadata;
    protected URI nameSpace;
    protected String type;

    public DescMeta() {
    }

    public DescMeta(String str, String str2, URI uri, M m) {
        this.id = str;
        this.type = str2;
        this.nameSpace = uri;
        this.metadata = m;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public URI getNameSpace() {
        return this.nameSpace;
    }

    public void setNameSpace(URI uri) {
        this.nameSpace = uri;
    }

    public M getMetadata() {
        return this.metadata;
    }

    public void setMetadata(M m) {
        this.metadata = m;
    }

    public Document createMetadataDocument() {
        try {
            DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
            newInstance.setNamespaceAware(true);
            Document newDocument = newInstance.newDocumentBuilder().newDocument();
            newDocument.appendChild(newDocument.createElementNS(DIDLContent.DESC_WRAPPER_NAMESPACE_URI, "desc-wrapper"));
            return newDocument;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
