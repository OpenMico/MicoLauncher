package org.fourthline.cling.binding.xml;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import org.fourthline.cling.binding.staging.MutableDevice;
import org.fourthline.cling.binding.staging.MutableIcon;
import org.fourthline.cling.binding.staging.MutableService;
import org.fourthline.cling.binding.staging.MutableUDAVersion;
import org.fourthline.cling.binding.xml.Descriptor;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.types.DLNACaps;
import org.fourthline.cling.model.types.DLNADoc;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDN;
import org.seamless.util.MimeType;
import org.seamless.xml.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: classes5.dex */
public class UDA10DeviceDescriptorBinderSAXImpl extends UDA10DeviceDescriptorBinderImpl {
    private static Logger log = Logger.getLogger(DeviceDescriptorBinder.class.getName());

    @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderImpl, org.fourthline.cling.binding.xml.DeviceDescriptorBinder
    public <D extends Device> D describe(D d, String str) throws DescriptorBindingException, ValidationException {
        if (str == null || str.length() == 0) {
            throw new DescriptorBindingException("Null or empty descriptor");
        }
        try {
            Logger logger = log;
            logger.fine("Populating device from XML descriptor: " + d);
            SAXParser sAXParser = new SAXParser();
            MutableDevice mutableDevice = new MutableDevice();
            new RootHandler(mutableDevice, sAXParser);
            sAXParser.parse(new InputSource(new StringReader(str.trim())));
            return (D) mutableDevice.build(d);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e2) {
            throw new DescriptorBindingException("Could not parse device descriptor: " + e2.toString(), e2);
        }
    }

    /* loaded from: classes5.dex */
    protected static class RootHandler extends DeviceDescriptorHandler<MutableDevice> {
        public RootHandler(MutableDevice mutableDevice, SAXParser sAXParser) {
            super(mutableDevice, sAXParser);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void startElement(Descriptor.Device.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(SpecVersionHandler.EL)) {
                MutableUDAVersion mutableUDAVersion = new MutableUDAVersion();
                getInstance().udaVersion = mutableUDAVersion;
                new SpecVersionHandler(mutableUDAVersion, this);
            }
            if (element.equals(DeviceHandler.EL)) {
                new DeviceHandler(getInstance(), this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void endElement(Descriptor.Device.ELEMENT element) throws SAXException {
            if (AnonymousClass1.$SwitchMap$org$fourthline$cling$binding$xml$Descriptor$Device$ELEMENT[element.ordinal()] == 1) {
                try {
                    String characters = getCharacters();
                    if (characters != null && characters.length() > 0) {
                        getInstance().baseURL = new URL(characters);
                    }
                } catch (Exception e) {
                    throw new SAXException("Invalid URLBase: " + e.toString());
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    protected static class SpecVersionHandler extends DeviceDescriptorHandler<MutableUDAVersion> {
        public static final Descriptor.Device.ELEMENT EL = Descriptor.Device.ELEMENT.specVersion;

        public SpecVersionHandler(MutableUDAVersion mutableUDAVersion, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(mutableUDAVersion, deviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void endElement(Descriptor.Device.ELEMENT element) throws SAXException {
            switch (element) {
                case major:
                    String trim = getCharacters().trim();
                    if (!trim.equals("1")) {
                        Logger logger = UDA10DeviceDescriptorBinderSAXImpl.log;
                        logger.warning("Unsupported UDA major version, ignoring: " + trim);
                        trim = "1";
                    }
                    getInstance().major = Integer.valueOf(trim).intValue();
                    return;
                case minor:
                    String trim2 = getCharacters().trim();
                    if (!trim2.equals("0")) {
                        Logger logger2 = UDA10DeviceDescriptorBinderSAXImpl.log;
                        logger2.warning("Unsupported UDA minor version, ignoring: " + trim2);
                        trim2 = "0";
                    }
                    getInstance().minor = Integer.valueOf(trim2).intValue();
                    return;
                default:
                    return;
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class DeviceHandler extends DeviceDescriptorHandler<MutableDevice> {
        public static final Descriptor.Device.ELEMENT EL = Descriptor.Device.ELEMENT.device;

        public DeviceHandler(MutableDevice mutableDevice, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(mutableDevice, deviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void startElement(Descriptor.Device.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(IconListHandler.EL)) {
                ArrayList arrayList = new ArrayList();
                getInstance().icons = arrayList;
                new IconListHandler(arrayList, this);
            }
            if (element.equals(ServiceListHandler.EL)) {
                ArrayList arrayList2 = new ArrayList();
                getInstance().services = arrayList2;
                new ServiceListHandler(arrayList2, this);
            }
            if (element.equals(DeviceListHandler.EL)) {
                ArrayList arrayList3 = new ArrayList();
                getInstance().embeddedDevices = arrayList3;
                new DeviceListHandler(arrayList3, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void endElement(Descriptor.Device.ELEMENT element) throws SAXException {
            switch (element) {
                case deviceType:
                    getInstance().deviceType = getCharacters();
                    return;
                case friendlyName:
                    getInstance().friendlyName = getCharacters();
                    return;
                case manufacturer:
                    getInstance().manufacturer = getCharacters();
                    return;
                case manufacturerURL:
                    getInstance().manufacturerURI = UDA10DeviceDescriptorBinderImpl.parseURI(getCharacters());
                    return;
                case modelDescription:
                    getInstance().modelDescription = getCharacters();
                    return;
                case modelName:
                    getInstance().modelName = getCharacters();
                    return;
                case modelNumber:
                    getInstance().modelNumber = getCharacters();
                    return;
                case modelURL:
                    getInstance().modelURI = UDA10DeviceDescriptorBinderImpl.parseURI(getCharacters());
                    return;
                case presentationURL:
                    getInstance().presentationURI = UDA10DeviceDescriptorBinderImpl.parseURI(getCharacters());
                    return;
                case UPC:
                    getInstance().upc = getCharacters();
                    return;
                case serialNumber:
                    getInstance().serialNumber = getCharacters();
                    return;
                case UDN:
                    getInstance().udn = UDN.valueOf(getCharacters());
                    return;
                case X_DLNADOC:
                    String characters = getCharacters();
                    try {
                        getInstance().dlnaDocs.add(DLNADoc.valueOf(characters));
                        return;
                    } catch (InvalidValueException unused) {
                        Logger logger = UDA10DeviceDescriptorBinderSAXImpl.log;
                        logger.info("Invalid X_DLNADOC value, ignoring value: " + characters);
                        return;
                    }
                case X_DLNACAP:
                    getInstance().dlnaCaps = DLNACaps.valueOf(getCharacters());
                    return;
                default:
                    return;
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class IconListHandler extends DeviceDescriptorHandler<List<MutableIcon>> {
        public static final Descriptor.Device.ELEMENT EL = Descriptor.Device.ELEMENT.iconList;

        public IconListHandler(List<MutableIcon> list, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(list, deviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void startElement(Descriptor.Device.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(IconHandler.EL)) {
                MutableIcon mutableIcon = new MutableIcon();
                getInstance().add(mutableIcon);
                new IconHandler(mutableIcon, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class IconHandler extends DeviceDescriptorHandler<MutableIcon> {
        public static final Descriptor.Device.ELEMENT EL = Descriptor.Device.ELEMENT.icon;

        public IconHandler(MutableIcon mutableIcon, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(mutableIcon, deviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void endElement(Descriptor.Device.ELEMENT element) throws SAXException {
            switch (element) {
                case width:
                    getInstance().width = Integer.valueOf(getCharacters()).intValue();
                    return;
                case height:
                    getInstance().height = Integer.valueOf(getCharacters()).intValue();
                    return;
                case depth:
                    try {
                        getInstance().depth = Integer.valueOf(getCharacters()).intValue();
                        return;
                    } catch (NumberFormatException e) {
                        Logger logger = UDA10DeviceDescriptorBinderSAXImpl.log;
                        logger.warning("Invalid icon depth '" + getCharacters() + "', using 16 as default: " + e);
                        getInstance().depth = 16;
                        return;
                    }
                case url:
                    getInstance().uri = UDA10DeviceDescriptorBinderImpl.parseURI(getCharacters());
                    return;
                case mimetype:
                    try {
                        getInstance().mimeType = getCharacters();
                        MimeType.valueOf(getInstance().mimeType);
                        return;
                    } catch (IllegalArgumentException unused) {
                        Logger logger2 = UDA10DeviceDescriptorBinderSAXImpl.log;
                        logger2.warning("Ignoring invalid icon mime type: " + getInstance().mimeType);
                        getInstance().mimeType = "";
                        return;
                    }
                default:
                    return;
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class ServiceListHandler extends DeviceDescriptorHandler<List<MutableService>> {
        public static final Descriptor.Device.ELEMENT EL = Descriptor.Device.ELEMENT.serviceList;

        public ServiceListHandler(List<MutableService> list, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(list, deviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void startElement(Descriptor.Device.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(ServiceHandler.EL)) {
                MutableService mutableService = new MutableService();
                getInstance().add(mutableService);
                new ServiceHandler(mutableService, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            boolean equals = element.equals(EL);
            if (equals) {
                Iterator<MutableService> it = getInstance().iterator();
                while (it.hasNext()) {
                    MutableService next = it.next();
                    if (next.serviceType == null || next.serviceId == null) {
                        it.remove();
                    }
                }
            }
            return equals;
        }
    }

    /* loaded from: classes5.dex */
    protected static class ServiceHandler extends DeviceDescriptorHandler<MutableService> {
        public static final Descriptor.Device.ELEMENT EL = Descriptor.Device.ELEMENT.service;

        public ServiceHandler(MutableService mutableService, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(mutableService, deviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void endElement(Descriptor.Device.ELEMENT element) throws SAXException {
            try {
                switch (element) {
                    case serviceType:
                        getInstance().serviceType = ServiceType.valueOf(getCharacters());
                        break;
                    case serviceId:
                        getInstance().serviceId = ServiceId.valueOf(getCharacters());
                        break;
                    case SCPDURL:
                        getInstance().descriptorURI = UDA10DeviceDescriptorBinderImpl.parseURI(getCharacters());
                        break;
                    case controlURL:
                        getInstance().controlURI = UDA10DeviceDescriptorBinderImpl.parseURI(getCharacters());
                        break;
                    case eventSubURL:
                        getInstance().eventSubscriptionURI = UDA10DeviceDescriptorBinderImpl.parseURI(getCharacters());
                        break;
                }
            } catch (InvalidValueException e) {
                Logger logger = UDA10DeviceDescriptorBinderSAXImpl.log;
                logger.warning("UPnP specification violation, skipping invalid service declaration. " + e.getMessage());
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class DeviceListHandler extends DeviceDescriptorHandler<List<MutableDevice>> {
        public static final Descriptor.Device.ELEMENT EL = Descriptor.Device.ELEMENT.deviceList;

        public DeviceListHandler(List<MutableDevice> list, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(list, deviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public void startElement(Descriptor.Device.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(DeviceHandler.EL)) {
                MutableDevice mutableDevice = new MutableDevice();
                getInstance().add(mutableDevice);
                new DeviceHandler(mutableDevice, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderSAXImpl.DeviceDescriptorHandler
        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class DeviceDescriptorHandler<I> extends SAXParser.Handler<I> {
        public void endElement(Descriptor.Device.ELEMENT element) throws SAXException {
        }

        public boolean isLastElement(Descriptor.Device.ELEMENT element) {
            return false;
        }

        public void startElement(Descriptor.Device.ELEMENT element, Attributes attributes) throws SAXException {
        }

        public DeviceDescriptorHandler(I i) {
            super(i);
        }

        public DeviceDescriptorHandler(I i, SAXParser sAXParser) {
            super(i, sAXParser);
        }

        public DeviceDescriptorHandler(I i, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(i, deviceDescriptorHandler);
        }

        public DeviceDescriptorHandler(I i, SAXParser sAXParser, DeviceDescriptorHandler deviceDescriptorHandler) {
            super(i, sAXParser, deviceDescriptorHandler);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            Descriptor.Device.ELEMENT valueOrNullOf = Descriptor.Device.ELEMENT.valueOrNullOf(str2);
            if (valueOrNullOf != null) {
                startElement(valueOrNullOf, attributes);
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            Descriptor.Device.ELEMENT valueOrNullOf = Descriptor.Device.ELEMENT.valueOrNullOf(str2);
            if (valueOrNullOf != null) {
                endElement(valueOrNullOf);
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            Descriptor.Device.ELEMENT valueOrNullOf = Descriptor.Device.ELEMENT.valueOrNullOf(str2);
            return valueOrNullOf != null && isLastElement(valueOrNullOf);
        }
    }
}
