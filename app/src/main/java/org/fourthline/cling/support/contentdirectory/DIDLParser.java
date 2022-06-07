package org.fourthline.cling.support.contentdirectory;

import com.umeng.analytics.pro.ai;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.passport.StatConstants;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.fourthline.cling.binding.xml.Descriptor;
import org.fourthline.cling.model.XMLUtil;
import org.fourthline.cling.model.types.Datatype;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.support.model.DIDLAttribute;
import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.DescMeta;
import org.fourthline.cling.support.model.Person;
import org.fourthline.cling.support.model.PersonWithRole;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.StorageMedium;
import org.fourthline.cling.support.model.WriteStatus;
import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.item.Item;
import org.hapjs.features.channel.IChannel;
import org.seamless.util.Exceptions;
import org.seamless.util.io.IO;
import org.seamless.xml.SAXParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: classes5.dex */
public class DIDLParser extends SAXParser {
    public static final String UNKNOWN_TITLE = "Unknown Title";
    private static final Logger log = Logger.getLogger(DIDLParser.class.getName());

    protected String booleanToInt(boolean z) {
        return z ? "1" : "0";
    }

    public DIDLContent parseResource(String str) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
            return parse(IO.readLines(inputStream));
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public DIDLContent parse(String str) throws Exception {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("Null or empty XML");
        }
        DIDLContent dIDLContent = new DIDLContent();
        createRootHandler(dIDLContent, this);
        log.fine("Parsing DIDL XML content");
        parse(new InputSource(new StringReader(str)));
        return dIDLContent;
    }

    protected RootHandler createRootHandler(DIDLContent dIDLContent, SAXParser sAXParser) {
        return new RootHandler(dIDLContent, sAXParser);
    }

    protected ContainerHandler createContainerHandler(Container container, SAXParser.Handler handler) {
        return new ContainerHandler(container, handler);
    }

    protected ItemHandler createItemHandler(Item item, SAXParser.Handler handler) {
        return new ItemHandler(item, handler);
    }

    protected ResHandler createResHandler(Res res, SAXParser.Handler handler) {
        return new ResHandler(res, handler);
    }

    protected DescMetaHandler createDescMetaHandler(DescMeta descMeta, SAXParser.Handler handler) {
        return new DescMetaHandler(descMeta, handler);
    }

    protected Container createContainer(Attributes attributes) {
        Container container = new Container();
        container.setId(attributes.getValue("id"));
        container.setParentID(attributes.getValue("parentID"));
        if (attributes.getValue("childCount") != null) {
            container.setChildCount(Integer.valueOf(attributes.getValue("childCount")));
        }
        try {
            Boolean bool = (Boolean) Datatype.Builtin.BOOLEAN.getDatatype().valueOf(attributes.getValue(StatConstants.ERROR_RESTRICTED));
            if (bool != null) {
                container.setRestricted(bool.booleanValue());
            }
            Boolean bool2 = (Boolean) Datatype.Builtin.BOOLEAN.getDatatype().valueOf(attributes.getValue("searchable"));
            if (bool2 != null) {
                container.setSearchable(bool2.booleanValue());
            }
        } catch (Exception unused) {
        }
        return container;
    }

    protected Item createItem(Attributes attributes) {
        Item item = new Item();
        item.setId(attributes.getValue("id"));
        item.setParentID(attributes.getValue("parentID"));
        try {
            Boolean bool = (Boolean) Datatype.Builtin.BOOLEAN.getDatatype().valueOf(attributes.getValue(StatConstants.ERROR_RESTRICTED));
            if (bool != null) {
                item.setRestricted(bool.booleanValue());
            }
        } catch (Exception unused) {
        }
        if (attributes.getValue("refID") != null) {
            item.setRefID(attributes.getValue("refID"));
        }
        return item;
    }

    protected Res createResource(Attributes attributes) {
        Res res = new Res();
        if (attributes.getValue("importUri") != null) {
            res.setImportUri(URI.create(attributes.getValue("importUri")));
        }
        try {
            res.setProtocolInfo(new ProtocolInfo(attributes.getValue("protocolInfo")));
            if (attributes.getValue("size") != null) {
                res.setSize(toLongOrNull(attributes.getValue("size")));
            }
            if (attributes.getValue("duration") != null) {
                res.setDuration(attributes.getValue("duration"));
            }
            if (attributes.getValue("bitrate") != null) {
                res.setBitrate(toLongOrNull(attributes.getValue("bitrate")));
            }
            if (attributes.getValue("sampleFrequency") != null) {
                res.setSampleFrequency(toLongOrNull(attributes.getValue("sampleFrequency")));
            }
            if (attributes.getValue("bitsPerSample") != null) {
                res.setBitsPerSample(toLongOrNull(attributes.getValue("bitsPerSample")));
            }
            if (attributes.getValue("nrAudioChannels") != null) {
                res.setNrAudioChannels(toLongOrNull(attributes.getValue("nrAudioChannels")));
            }
            if (attributes.getValue("colorDepth") != null) {
                res.setColorDepth(toLongOrNull(attributes.getValue("colorDepth")));
            }
            if (attributes.getValue("protection") != null) {
                res.setProtection(attributes.getValue("protection"));
            }
            if (attributes.getValue("resolution") != null) {
                res.setResolution(attributes.getValue("resolution"));
            }
            return res;
        } catch (InvalidValueException e) {
            Logger logger = log;
            logger.warning("In DIDL content, invalid resource protocol info: " + Exceptions.unwrap(e));
            return null;
        }
    }

    private Long toLongOrNull(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    protected DescMeta createDescMeta(Attributes attributes) {
        DescMeta descMeta = new DescMeta();
        descMeta.setId(attributes.getValue("id"));
        if (attributes.getValue("type") != null) {
            descMeta.setType(attributes.getValue("type"));
        }
        if (attributes.getValue("nameSpace") != null) {
            descMeta.setNameSpace(URI.create(attributes.getValue("nameSpace")));
        }
        return descMeta;
    }

    public String generate(DIDLContent dIDLContent) throws Exception {
        return generate(dIDLContent, false);
    }

    public String generate(DIDLContent dIDLContent, boolean z) throws Exception {
        return documentToString(buildDOM(dIDLContent, z), true);
    }

    protected String documentToString(Document document, boolean z) throws Exception {
        Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
        if (z) {
            newTransformer.setOutputProperty("omit-xml-declaration", "yes");
        }
        StringWriter stringWriter = new StringWriter();
        newTransformer.transform(new DOMSource(document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    protected Document buildDOM(DIDLContent dIDLContent, boolean z) throws Exception {
        DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
        newInstance.setNamespaceAware(true);
        Document newDocument = newInstance.newDocumentBuilder().newDocument();
        generateRoot(dIDLContent, newDocument, z);
        return newDocument;
    }

    protected void generateRoot(DIDLContent dIDLContent, Document document, boolean z) {
        Element createElementNS = document.createElementNS(DIDLContent.NAMESPACE_URI, "DIDL-Lite");
        document.appendChild(createElementNS);
        createElementNS.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:upnp", DIDLObject.Property.UPNP.NAMESPACE.URI);
        createElementNS.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:dc", DIDLObject.Property.DC.NAMESPACE.URI);
        createElementNS.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:sec", DIDLObject.Property.SEC.NAMESPACE.URI);
        for (Container container : dIDLContent.getContainers()) {
            if (container != null) {
                generateContainer(container, document, createElementNS, z);
            }
        }
        for (Item item : dIDLContent.getItems()) {
            if (item != null) {
                generateItem(item, document, createElementNS);
            }
        }
        for (DescMeta descMeta : dIDLContent.getDescMetadata()) {
            if (descMeta != null) {
                generateDescMetadata(descMeta, document, createElementNS);
            }
        }
    }

    protected void generateContainer(Container container, Document document, Element element, boolean z) {
        if (container.getClazz() != null) {
            Element appendNewElement = XMLUtil.appendNewElement(document, element, "container");
            if (container.getId() != null) {
                appendNewElement.setAttribute("id", container.getId());
                if (container.getParentID() != null) {
                    appendNewElement.setAttribute("parentID", container.getParentID());
                    if (container.getChildCount() != null) {
                        appendNewElement.setAttribute("childCount", Integer.toString(container.getChildCount().intValue()));
                    }
                    appendNewElement.setAttribute(StatConstants.ERROR_RESTRICTED, booleanToInt(container.isRestricted()));
                    appendNewElement.setAttribute("searchable", booleanToInt(container.isSearchable()));
                    String title = container.getTitle();
                    if (title == null) {
                        Logger logger = log;
                        logger.warning("Missing 'dc:title' element for container: " + container.getId());
                        title = UNKNOWN_TITLE;
                    }
                    XMLUtil.appendNewElementIfNotNull(document, appendNewElement, "dc:title", title, DIDLObject.Property.DC.NAMESPACE.URI);
                    XMLUtil.appendNewElementIfNotNull(document, appendNewElement, "dc:creator", container.getCreator(), DIDLObject.Property.DC.NAMESPACE.URI);
                    XMLUtil.appendNewElementIfNotNull(document, appendNewElement, "upnp:writeStatus", container.getWriteStatus(), DIDLObject.Property.UPNP.NAMESPACE.URI);
                    appendClass(document, appendNewElement, container.getClazz(), "upnp:class", false);
                    for (DIDLObject.Class r1 : container.getSearchClasses()) {
                        appendClass(document, appendNewElement, r1, "upnp:searchClass", true);
                    }
                    for (DIDLObject.Class r12 : container.getCreateClasses()) {
                        appendClass(document, appendNewElement, r12, "upnp:createClass", true);
                    }
                    appendProperties(document, appendNewElement, container, "upnp", DIDLObject.Property.UPNP.NAMESPACE.class, DIDLObject.Property.UPNP.NAMESPACE.URI);
                    appendProperties(document, appendNewElement, container, "dc", DIDLObject.Property.DC.NAMESPACE.class, DIDLObject.Property.DC.NAMESPACE.URI);
                    if (z) {
                        for (Item item : container.getItems()) {
                            if (item != null) {
                                generateItem(item, document, appendNewElement);
                            }
                        }
                    }
                    for (Res res : container.getResources()) {
                        if (res != null) {
                            generateResource(res, document, appendNewElement);
                        }
                    }
                    for (DescMeta descMeta : container.getDescMetadata()) {
                        if (descMeta != null) {
                            generateDescMetadata(descMeta, document, appendNewElement);
                        }
                    }
                    return;
                }
                throw new NullPointerException("Missing parent id on container: " + container);
            }
            throw new NullPointerException("Missing id on container: " + container);
        }
        throw new RuntimeException("Missing 'upnp:class' element for container: " + container.getId());
    }

    protected void generateItem(Item item, Document document, Element element) {
        if (item.getClazz() != null) {
            Element appendNewElement = XMLUtil.appendNewElement(document, element, "item");
            if (item.getId() != null) {
                appendNewElement.setAttribute("id", item.getId());
                if (item.getParentID() != null) {
                    appendNewElement.setAttribute("parentID", item.getParentID());
                    if (item.getRefID() != null) {
                        appendNewElement.setAttribute("refID", item.getRefID());
                    }
                    appendNewElement.setAttribute(StatConstants.ERROR_RESTRICTED, booleanToInt(item.isRestricted()));
                    String title = item.getTitle();
                    if (title == null) {
                        Logger logger = log;
                        logger.warning("Missing 'dc:title' element for item: " + item.getId());
                        title = UNKNOWN_TITLE;
                    }
                    XMLUtil.appendNewElementIfNotNull(document, appendNewElement, "dc:title", title, DIDLObject.Property.DC.NAMESPACE.URI);
                    XMLUtil.appendNewElementIfNotNull(document, appendNewElement, "dc:creator", item.getCreator(), DIDLObject.Property.DC.NAMESPACE.URI);
                    XMLUtil.appendNewElementIfNotNull(document, appendNewElement, "upnp:writeStatus", item.getWriteStatus(), DIDLObject.Property.UPNP.NAMESPACE.URI);
                    appendClass(document, appendNewElement, item.getClazz(), "upnp:class", false);
                    appendProperties(document, appendNewElement, item, "upnp", DIDLObject.Property.UPNP.NAMESPACE.class, DIDLObject.Property.UPNP.NAMESPACE.URI);
                    appendProperties(document, appendNewElement, item, "dc", DIDLObject.Property.DC.NAMESPACE.class, DIDLObject.Property.DC.NAMESPACE.URI);
                    appendProperties(document, appendNewElement, item, Descriptor.Device.SEC_PREFIX, DIDLObject.Property.SEC.NAMESPACE.class, DIDLObject.Property.SEC.NAMESPACE.URI);
                    for (Res res : item.getResources()) {
                        if (res != null) {
                            generateResource(res, document, appendNewElement);
                        }
                    }
                    for (DescMeta descMeta : item.getDescMetadata()) {
                        if (descMeta != null) {
                            generateDescMetadata(descMeta, document, appendNewElement);
                        }
                    }
                    return;
                }
                throw new NullPointerException("Missing parent id on item: " + item);
            }
            throw new NullPointerException("Missing id on item: " + item);
        }
        throw new RuntimeException("Missing 'upnp:class' element for item: " + item.getId());
    }

    protected void generateResource(Res res, Document document, Element element) {
        if (res.getValue() == null) {
            throw new RuntimeException("Missing resource URI value" + res);
        } else if (res.getProtocolInfo() != null) {
            Element appendNewElement = XMLUtil.appendNewElement(document, element, "res", res.getValue());
            appendNewElement.setAttribute("protocolInfo", res.getProtocolInfo().toString());
            if (res.getImportUri() != null) {
                appendNewElement.setAttribute("importUri", res.getImportUri().toString());
            }
            if (res.getSize() != null) {
                appendNewElement.setAttribute("size", res.getSize().toString());
            }
            if (res.getDuration() != null) {
                appendNewElement.setAttribute("duration", res.getDuration());
            }
            if (res.getBitrate() != null) {
                appendNewElement.setAttribute("bitrate", res.getBitrate().toString());
            }
            if (res.getSampleFrequency() != null) {
                appendNewElement.setAttribute("sampleFrequency", res.getSampleFrequency().toString());
            }
            if (res.getBitsPerSample() != null) {
                appendNewElement.setAttribute("bitsPerSample", res.getBitsPerSample().toString());
            }
            if (res.getNrAudioChannels() != null) {
                appendNewElement.setAttribute("nrAudioChannels", res.getNrAudioChannels().toString());
            }
            if (res.getColorDepth() != null) {
                appendNewElement.setAttribute("colorDepth", res.getColorDepth().toString());
            }
            if (res.getProtection() != null) {
                appendNewElement.setAttribute("protection", res.getProtection());
            }
            if (res.getResolution() != null) {
                appendNewElement.setAttribute("resolution", res.getResolution());
            }
        } else {
            throw new RuntimeException("Missing resource protocol info: " + res);
        }
    }

    protected void generateDescMetadata(DescMeta descMeta, Document document, Element element) {
        if (descMeta.getId() == null) {
            throw new RuntimeException("Missing id of description metadata: " + descMeta);
        } else if (descMeta.getNameSpace() != null) {
            Element appendNewElement = XMLUtil.appendNewElement(document, element, IChannel.EXTRA_ERROR_DESC);
            appendNewElement.setAttribute("id", descMeta.getId());
            appendNewElement.setAttribute("nameSpace", descMeta.getNameSpace().toString());
            if (descMeta.getType() != null) {
                appendNewElement.setAttribute("type", descMeta.getType());
            }
            populateDescMetadata(appendNewElement, descMeta);
        } else {
            throw new RuntimeException("Missing namespace of description metadata: " + descMeta);
        }
    }

    protected void populateDescMetadata(Element element, DescMeta descMeta) {
        if (descMeta.getMetadata() instanceof Document) {
            NodeList childNodes = ((Document) descMeta.getMetadata()).getDocumentElement().getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item.getNodeType() == 1) {
                    element.appendChild(element.getOwnerDocument().importNode(item, true));
                }
            }
            return;
        }
        Logger logger = log;
        logger.warning("Unknown desc metadata content, please override populateDescMetadata(): " + descMeta.getMetadata());
    }

    protected void appendProperties(Document document, Element element, DIDLObject dIDLObject, String str, Class<? extends DIDLObject.Property.NAMESPACE> cls, String str2) {
        DIDLObject.Property[] propertiesByNamespace = dIDLObject.getPropertiesByNamespace(cls);
        for (DIDLObject.Property property : propertiesByNamespace) {
            Element createElementNS = document.createElementNS(str2, str + Constants.COLON_SEPARATOR + property.getDescriptorName());
            element.appendChild(createElementNS);
            property.setOnElement(createElementNS);
        }
    }

    protected void appendClass(Document document, Element element, DIDLObject.Class r5, String str, boolean z) {
        Element appendNewElementIfNotNull = XMLUtil.appendNewElementIfNotNull(document, element, str, r5.getValue(), DIDLObject.Property.UPNP.NAMESPACE.URI);
        if (r5.getFriendlyName() != null && r5.getFriendlyName().length() > 0) {
            appendNewElementIfNotNull.setAttribute("name", r5.getFriendlyName());
        }
        if (z) {
            appendNewElementIfNotNull.setAttribute("includeDerived", Boolean.toString(r5.isIncludeDerived()));
        }
    }

    public void debugXML(String str) {
        if (log.isLoggable(Level.FINE)) {
            log.fine("-------------------------------------------------------------------------------------");
            Logger logger = log;
            logger.fine("\n" + str);
            log.fine("-------------------------------------------------------------------------------------");
        }
    }

    /* loaded from: classes5.dex */
    public abstract class DIDLObjectHandler<I extends DIDLObject> extends SAXParser.Handler<I> {
        protected DIDLObjectHandler(I i, SAXParser.Handler handler) {
            super(i, handler);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            if (DIDLObject.Property.DC.NAMESPACE.URI.equals(str)) {
                if ("title".equals(str2)) {
                    ((DIDLObject) getInstance()).setTitle(getCharacters());
                } else if ("creator".equals(str2)) {
                    ((DIDLObject) getInstance()).setCreator(getCharacters());
                } else if ("description".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.DC.DESCRIPTION(getCharacters()));
                } else if ("publisher".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.DC.PUBLISHER(new Person(getCharacters())));
                } else if ("contributor".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.DC.CONTRIBUTOR(new Person(getCharacters())));
                } else if (Common.DATE.equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.DC.DATE(getCharacters()));
                } else if (ai.N.equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.DC.LANGUAGE(getCharacters()));
                } else if ("rights".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.DC.RIGHTS(getCharacters()));
                } else if ("relation".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.DC.RELATION(URI.create(getCharacters())));
                }
            } else if (DIDLObject.Property.UPNP.NAMESPACE.URI.equals(str)) {
                if ("writeStatus".equals(str2)) {
                    try {
                        ((DIDLObject) getInstance()).setWriteStatus(WriteStatus.valueOf(getCharacters()));
                    } catch (Exception unused) {
                        Logger logger = DIDLParser.log;
                        logger.info("Ignoring invalid writeStatus value: " + getCharacters());
                    }
                } else if ("class".equals(str2)) {
                    ((DIDLObject) getInstance()).setClazz(new DIDLObject.Class(getCharacters(), getAttributes().getValue("name")));
                } else if ("artist".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.ARTIST(new PersonWithRole(getCharacters(), getAttributes().getValue("role"))));
                } else if ("actor".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.ACTOR(new PersonWithRole(getCharacters(), getAttributes().getValue("role"))));
                } else if ("author".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.AUTHOR(new PersonWithRole(getCharacters(), getAttributes().getValue("role"))));
                } else if ("producer".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.PRODUCER(new Person(getCharacters())));
                } else if ("director".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.DIRECTOR(new Person(getCharacters())));
                } else if ("longDescription".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.LONG_DESCRIPTION(getCharacters()));
                } else if ("storageUsed".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.STORAGE_USED(Long.valueOf(getCharacters())));
                } else if ("storageTotal".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.STORAGE_TOTAL(Long.valueOf(getCharacters())));
                } else if ("storageFree".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.STORAGE_FREE(Long.valueOf(getCharacters())));
                } else if ("storageMaxPartition".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.STORAGE_MAX_PARTITION(Long.valueOf(getCharacters())));
                } else if ("storageMedium".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.STORAGE_MEDIUM(StorageMedium.valueOrVendorSpecificOf(getCharacters())));
                } else if ("genre".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.GENRE(getCharacters()));
                } else if ("album".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.ALBUM(getCharacters()));
                } else if ("playlist".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.PLAYLIST(getCharacters()));
                } else if ("region".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.REGION(getCharacters()));
                } else if (SkillStore.SkillDetailSection.TYPE_RATING.equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.RATING(getCharacters()));
                } else if ("toc".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.TOC(getCharacters()));
                } else if ("albumArtURI".equals(str2)) {
                    DIDLObject.Property.UPNP.ALBUM_ART_URI album_art_uri = new DIDLObject.Property.UPNP.ALBUM_ART_URI(URI.create(getCharacters()));
                    Attributes attributes = getAttributes();
                    for (int i = 0; i < attributes.getLength(); i++) {
                        if ("profileID".equals(attributes.getLocalName(i))) {
                            album_art_uri.addAttribute(new DIDLObject.Property.DLNA.PROFILE_ID(new DIDLAttribute(DIDLObject.Property.DLNA.NAMESPACE.URI, Descriptor.Device.DLNA_PREFIX, attributes.getValue(i))));
                        }
                    }
                    ((DIDLObject) getInstance()).addProperty(album_art_uri);
                } else if ("artistDiscographyURI".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.ARTIST_DISCO_URI(URI.create(getCharacters())));
                } else if ("lyricsURI".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.LYRICS_URI(URI.create(getCharacters())));
                } else if ("icon".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.ICON(URI.create(getCharacters())));
                } else if ("radioCallSign".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.RADIO_CALL_SIGN(getCharacters()));
                } else if ("radioStationID".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.RADIO_STATION_ID(getCharacters()));
                } else if ("radioBand".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.RADIO_BAND(getCharacters()));
                } else if ("channelNr".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.CHANNEL_NR(Integer.valueOf(getCharacters())));
                } else if ("channelName".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.CHANNEL_NAME(getCharacters()));
                } else if ("scheduledStartTime".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.SCHEDULED_START_TIME(getCharacters()));
                } else if ("scheduledEndTime".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.SCHEDULED_END_TIME(getCharacters()));
                } else if ("DVDRegionCode".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.DVD_REGION_CODE(Integer.valueOf(getCharacters())));
                } else if ("originalTrackNumber".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.ORIGINAL_TRACK_NUMBER(Integer.valueOf(getCharacters())));
                } else if ("userAnnotation".equals(str2)) {
                    ((DIDLObject) getInstance()).addProperty(new DIDLObject.Property.UPNP.USER_ANNOTATION(getCharacters()));
                }
            }
        }
    }

    /* loaded from: classes5.dex */
    public class RootHandler extends SAXParser.Handler<DIDLContent> {
        RootHandler(DIDLContent dIDLContent, SAXParser sAXParser) {
            super(dIDLContent, sAXParser);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            if (DIDLContent.NAMESPACE_URI.equals(str)) {
                if (str2.equals("container")) {
                    Container createContainer = DIDLParser.this.createContainer(attributes);
                    getInstance().addContainer(createContainer);
                    DIDLParser.this.createContainerHandler(createContainer, this);
                } else if (str2.equals("item")) {
                    Item createItem = DIDLParser.this.createItem(attributes);
                    getInstance().addItem(createItem);
                    DIDLParser.this.createItemHandler(createItem, this);
                } else if (str2.equals(IChannel.EXTRA_ERROR_DESC)) {
                    DescMeta createDescMeta = DIDLParser.this.createDescMeta(attributes);
                    getInstance().addDescMetadata(createDescMeta);
                    DIDLParser.this.createDescMetaHandler(createDescMeta, this);
                }
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            if (!DIDLContent.NAMESPACE_URI.equals(str) || !"DIDL-Lite".equals(str2)) {
                return false;
            }
            getInstance().replaceGenericContainerAndItems();
            return true;
        }
    }

    /* loaded from: classes5.dex */
    public class ContainerHandler extends DIDLObjectHandler<Container> {
        public ContainerHandler(Container container, SAXParser.Handler handler) {
            super(container, handler);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            Res createResource;
            super.startElement(str, str2, str3, attributes);
            if (DIDLContent.NAMESPACE_URI.equals(str)) {
                if (str2.equals("item")) {
                    Item createItem = DIDLParser.this.createItem(attributes);
                    ((Container) getInstance()).addItem(createItem);
                    DIDLParser.this.createItemHandler(createItem, this);
                } else if (str2.equals(IChannel.EXTRA_ERROR_DESC)) {
                    DescMeta createDescMeta = DIDLParser.this.createDescMeta(attributes);
                    ((Container) getInstance()).addDescMetadata(createDescMeta);
                    DIDLParser.this.createDescMetaHandler(createDescMeta, this);
                } else if (str2.equals("res") && (createResource = DIDLParser.this.createResource(attributes)) != null) {
                    ((Container) getInstance()).addResource(createResource);
                    DIDLParser.this.createResHandler(createResource, this);
                }
            }
        }

        @Override // org.fourthline.cling.support.contentdirectory.DIDLParser.DIDLObjectHandler, org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            if (!DIDLObject.Property.UPNP.NAMESPACE.URI.equals(str)) {
                return;
            }
            if ("searchClass".equals(str2)) {
                ((Container) getInstance()).getSearchClasses().add(new DIDLObject.Class(getCharacters(), getAttributes().getValue("name"), "true".equals(getAttributes().getValue("includeDerived"))));
            } else if ("createClass".equals(str2)) {
                ((Container) getInstance()).getCreateClasses().add(new DIDLObject.Class(getCharacters(), getAttributes().getValue("name"), "true".equals(getAttributes().getValue("includeDerived"))));
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            if (!DIDLContent.NAMESPACE_URI.equals(str) || !"container".equals(str2)) {
                return false;
            }
            if (((Container) getInstance()).getTitle() == null) {
                Logger logger = DIDLParser.log;
                logger.warning("In DIDL content, missing 'dc:title' element for container: " + ((Container) getInstance()).getId());
            }
            if (((Container) getInstance()).getClazz() != null) {
                return true;
            }
            Logger logger2 = DIDLParser.log;
            logger2.warning("In DIDL content, missing 'upnp:class' element for container: " + ((Container) getInstance()).getId());
            return true;
        }
    }

    /* loaded from: classes5.dex */
    public class ItemHandler extends DIDLObjectHandler<Item> {
        public ItemHandler(Item item, SAXParser.Handler handler) {
            super(item, handler);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            if (DIDLContent.NAMESPACE_URI.equals(str)) {
                if (str2.equals("res")) {
                    Res createResource = DIDLParser.this.createResource(attributes);
                    if (createResource != null) {
                        ((Item) getInstance()).addResource(createResource);
                        DIDLParser.this.createResHandler(createResource, this);
                    }
                } else if (str2.equals(IChannel.EXTRA_ERROR_DESC)) {
                    DescMeta createDescMeta = DIDLParser.this.createDescMeta(attributes);
                    ((Item) getInstance()).addDescMetadata(createDescMeta);
                    DIDLParser.this.createDescMetaHandler(createDescMeta, this);
                }
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            if (!DIDLContent.NAMESPACE_URI.equals(str) || !"item".equals(str2)) {
                return false;
            }
            if (((Item) getInstance()).getTitle() == null) {
                Logger logger = DIDLParser.log;
                logger.warning("In DIDL content, missing 'dc:title' element for item: " + ((Item) getInstance()).getId());
            }
            if (((Item) getInstance()).getClazz() != null) {
                return true;
            }
            Logger logger2 = DIDLParser.log;
            logger2.warning("In DIDL content, missing 'upnp:class' element for item: " + ((Item) getInstance()).getId());
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes5.dex */
    public class ResHandler extends SAXParser.Handler<Res> {
        public ResHandler(Res res, SAXParser.Handler handler) {
            super(res, handler);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            getInstance().setValue(getCharacters());
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            return DIDLContent.NAMESPACE_URI.equals(str) && "res".equals(str2);
        }
    }

    /* loaded from: classes5.dex */
    public class DescMetaHandler extends SAXParser.Handler<DescMeta> {
        protected Element current = ((Document) getInstance().getMetadata()).getDocumentElement();

        public DescMetaHandler(DescMeta descMeta, SAXParser.Handler handler) {
            super(descMeta, handler);
            descMeta.setMetadata(descMeta.createMetadataDocument());
        }

        @Override // org.seamless.xml.SAXParser.Handler
        public DescMeta getInstance() {
            return (DescMeta) super.getInstance();
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            Element createElementNS = ((Document) getInstance().getMetadata()).createElementNS(str, str3);
            for (int i = 0; i < attributes.getLength(); i++) {
                createElementNS.setAttributeNS(attributes.getURI(i), attributes.getQName(i), attributes.getValue(i));
            }
            this.current.appendChild(createElementNS);
            this.current = createElementNS;
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            if (!isLastElement(str, str2, str3)) {
                if (getCharacters().length() > 0 && !getCharacters().matches("[\\t\\n\\x0B\\f\\r\\s]+")) {
                    this.current.appendChild(((Document) getInstance().getMetadata()).createTextNode(getCharacters()));
                }
                this.current = (Element) this.current.getParentNode();
                this.characters = new StringBuilder();
                this.attributes = null;
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            return DIDLContent.NAMESPACE_URI.equals(str) && IChannel.EXTRA_ERROR_DESC.equals(str2);
        }
    }
}
