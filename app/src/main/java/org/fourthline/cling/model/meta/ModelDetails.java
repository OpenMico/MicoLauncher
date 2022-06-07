package org.fourthline.cling.model.meta;

import java.net.URI;

/* loaded from: classes5.dex */
public class ModelDetails {
    private String modelDescription;
    private String modelName;
    private String modelNumber;
    private URI modelURI;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ModelDetails() {
    }

    public ModelDetails(String str) {
        this.modelName = str;
    }

    public ModelDetails(String str, String str2) {
        this.modelName = str;
        this.modelDescription = str2;
    }

    public ModelDetails(String str, String str2, String str3) {
        this.modelName = str;
        this.modelDescription = str2;
        this.modelNumber = str3;
    }

    public ModelDetails(String str, String str2, String str3, URI uri) {
        this.modelName = str;
        this.modelDescription = str2;
        this.modelNumber = str3;
        this.modelURI = uri;
    }

    public ModelDetails(String str, String str2, String str3, String str4) throws IllegalArgumentException {
        this.modelName = str;
        this.modelDescription = str2;
        this.modelNumber = str3;
        this.modelURI = URI.create(str4);
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getModelDescription() {
        return this.modelDescription;
    }

    public String getModelNumber() {
        return this.modelNumber;
    }

    public URI getModelURI() {
        return this.modelURI;
    }
}
