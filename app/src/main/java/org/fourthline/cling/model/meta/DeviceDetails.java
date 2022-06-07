package org.fourthline.cling.model.meta;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.fourthline.cling.model.Validatable;
import org.fourthline.cling.model.ValidationError;
import org.fourthline.cling.model.types.DLNACaps;
import org.fourthline.cling.model.types.DLNADoc;

/* loaded from: classes5.dex */
public class DeviceDetails implements Validatable {
    private static final Logger log = Logger.getLogger(DeviceDetails.class.getName());
    private final URL baseURL;
    private final DLNACaps dlnaCaps;
    private final DLNADoc[] dlnaDocs;
    private final String friendlyName;
    private final ManufacturerDetails manufacturerDetails;
    private final ModelDetails modelDetails;
    private final URI presentationURI;
    private final DLNACaps secProductCaps;
    private final String serialNumber;
    private final String upc;

    public DeviceDetails(String str) {
        this((URL) null, str, (ManufacturerDetails) null, (ModelDetails) null, (String) null, (String) null, (URI) null);
    }

    public DeviceDetails(String str, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(null, str, null, null, null, null, null, dLNADocArr, dLNACaps);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails) {
        this((URL) null, str, manufacturerDetails, (ModelDetails) null, (String) null, (String) null, (URI) null);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(null, str, manufacturerDetails, null, null, null, null, dLNADocArr, dLNACaps);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails) {
        this((URL) null, str, manufacturerDetails, modelDetails, (String) null, (String) null, (URI) null);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(null, str, manufacturerDetails, modelDetails, null, null, null, dLNADocArr, dLNACaps);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, DLNADoc[] dLNADocArr, DLNACaps dLNACaps, DLNACaps dLNACaps2) {
        this(null, str, manufacturerDetails, modelDetails, null, null, null, dLNADocArr, dLNACaps, dLNACaps2);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3) {
        this((URL) null, str, manufacturerDetails, modelDetails, str2, str3, (URI) null);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(null, str, manufacturerDetails, modelDetails, str2, str3, null, dLNADocArr, dLNACaps);
    }

    public DeviceDetails(String str, URI uri) {
        this((URL) null, str, (ManufacturerDetails) null, (ModelDetails) null, (String) null, (String) null, uri);
    }

    public DeviceDetails(String str, URI uri, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(null, str, null, null, null, null, uri, dLNADocArr, dLNACaps);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, URI uri) {
        this((URL) null, str, manufacturerDetails, modelDetails, (String) null, (String) null, uri);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, URI uri, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(null, str, manufacturerDetails, modelDetails, null, null, uri, dLNADocArr, dLNACaps);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, URI uri) {
        this((URL) null, str, manufacturerDetails, modelDetails, str2, str3, uri);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, URI uri, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(null, str, manufacturerDetails, modelDetails, str2, str3, uri, dLNADocArr, dLNACaps);
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, String str4) throws IllegalArgumentException {
        this((URL) null, str, manufacturerDetails, modelDetails, str2, str3, URI.create(str4));
    }

    public DeviceDetails(String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, String str4, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) throws IllegalArgumentException {
        this(null, str, manufacturerDetails, modelDetails, str2, str3, URI.create(str4), dLNADocArr, dLNACaps);
    }

    public DeviceDetails(URL url, String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, URI uri) {
        this(url, str, manufacturerDetails, modelDetails, str2, str3, uri, null, null);
    }

    public DeviceDetails(URL url, String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, URI uri, DLNADoc[] dLNADocArr, DLNACaps dLNACaps) {
        this(url, str, manufacturerDetails, modelDetails, str2, str3, uri, dLNADocArr, dLNACaps, null);
    }

    public DeviceDetails(URL url, String str, ManufacturerDetails manufacturerDetails, ModelDetails modelDetails, String str2, String str3, URI uri, DLNADoc[] dLNADocArr, DLNACaps dLNACaps, DLNACaps dLNACaps2) {
        this.baseURL = url;
        this.friendlyName = str;
        this.manufacturerDetails = manufacturerDetails == null ? new ManufacturerDetails() : manufacturerDetails;
        this.modelDetails = modelDetails == null ? new ModelDetails() : modelDetails;
        this.serialNumber = str2;
        this.upc = str3;
        this.presentationURI = uri;
        this.dlnaDocs = dLNADocArr == null ? new DLNADoc[0] : dLNADocArr;
        this.dlnaCaps = dLNACaps;
        this.secProductCaps = dLNACaps2;
    }

    public URL getBaseURL() {
        return this.baseURL;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public ManufacturerDetails getManufacturerDetails() {
        return this.manufacturerDetails;
    }

    public ModelDetails getModelDetails() {
        return this.modelDetails;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getUpc() {
        return this.upc;
    }

    public URI getPresentationURI() {
        return this.presentationURI;
    }

    public DLNADoc[] getDlnaDocs() {
        return this.dlnaDocs;
    }

    public DLNACaps getDlnaCaps() {
        return this.dlnaCaps;
    }

    public DLNACaps getSecProductCaps() {
        return this.secProductCaps;
    }

    @Override // org.fourthline.cling.model.Validatable
    public List<ValidationError> validate() {
        ArrayList arrayList = new ArrayList();
        if (getUpc() != null) {
            if (getUpc().length() != 12) {
                Logger logger = log;
                logger.fine("UPnP specification violation, UPC must be 12 digits: " + getUpc());
            } else {
                try {
                    Long.parseLong(getUpc());
                } catch (NumberFormatException unused) {
                    Logger logger2 = log;
                    logger2.fine("UPnP specification violation, UPC must be 12 digits all-numeric: " + getUpc());
                }
            }
        }
        return arrayList;
    }
}
