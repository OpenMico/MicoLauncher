package org.fourthline.cling.binding.staging;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.ModelDetails;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.meta.UDAVersion;
import org.fourthline.cling.model.types.DLNACaps;
import org.fourthline.cling.model.types.DLNADoc;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDN;

/* loaded from: classes5.dex */
public class MutableDevice {
    public URL baseURL;
    public String deviceType;
    public DLNACaps dlnaCaps;
    public String friendlyName;
    public String manufacturer;
    public URI manufacturerURI;
    public String modelDescription;
    public String modelName;
    public String modelNumber;
    public URI modelURI;
    public MutableDevice parentDevice;
    public URI presentationURI;
    public String serialNumber;
    public UDN udn;
    public String upc;
    public MutableUDAVersion udaVersion = new MutableUDAVersion();
    public List<DLNADoc> dlnaDocs = new ArrayList();
    public List<MutableIcon> icons = new ArrayList();
    public List<MutableService> services = new ArrayList();
    public List<MutableDevice> embeddedDevices = new ArrayList();

    public Device build(Device device) throws ValidationException {
        return build(device, createDeviceVersion(), this.baseURL);
    }

    public Device build(Device device, UDAVersion uDAVersion, URL url) throws ValidationException {
        ArrayList arrayList = new ArrayList();
        for (MutableDevice mutableDevice : this.embeddedDevices) {
            arrayList.add(mutableDevice.build(device, uDAVersion, url));
        }
        return device.newInstance(this.udn, uDAVersion, createDeviceType(), createDeviceDetails(url), createIcons(), createServices(device), arrayList);
    }

    public UDAVersion createDeviceVersion() {
        return new UDAVersion(this.udaVersion.major, this.udaVersion.minor);
    }

    public DeviceType createDeviceType() {
        return DeviceType.valueOf(this.deviceType);
    }

    public DeviceDetails createDeviceDetails(URL url) {
        String str = this.friendlyName;
        ManufacturerDetails manufacturerDetails = new ManufacturerDetails(this.manufacturer, this.manufacturerURI);
        ModelDetails modelDetails = new ModelDetails(this.modelName, this.modelDescription, this.modelNumber, this.modelURI);
        String str2 = this.serialNumber;
        String str3 = this.upc;
        URI uri = this.presentationURI;
        List<DLNADoc> list = this.dlnaDocs;
        return new DeviceDetails(url, str, manufacturerDetails, modelDetails, str2, str3, uri, (DLNADoc[]) list.toArray(new DLNADoc[list.size()]), this.dlnaCaps);
    }

    public Icon[] createIcons() {
        Icon[] iconArr = new Icon[this.icons.size()];
        int i = 0;
        for (MutableIcon mutableIcon : this.icons) {
            i++;
            iconArr[i] = mutableIcon.build();
        }
        return iconArr;
    }

    public Service[] createServices(Device device) throws ValidationException {
        Service[] newServiceArray = device.newServiceArray(this.services.size());
        int i = 0;
        for (MutableService mutableService : this.services) {
            i++;
            newServiceArray[i] = mutableService.build(device);
        }
        return newServiceArray;
    }
}
