package org.fourthline.cling.binding.xml;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.xml.parsers.SAXParserFactory;
import org.fourthline.cling.binding.staging.MutableAction;
import org.fourthline.cling.binding.staging.MutableActionArgument;
import org.fourthline.cling.binding.staging.MutableAllowedValueRange;
import org.fourthline.cling.binding.staging.MutableService;
import org.fourthline.cling.binding.staging.MutableStateVariable;
import org.fourthline.cling.binding.xml.Descriptor;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.ActionArgument;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.meta.StateVariableEventDetails;
import org.fourthline.cling.model.types.CustomDatatype;
import org.fourthline.cling.model.types.Datatype;
import org.seamless.xml.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/* loaded from: classes5.dex */
public class UDA10ServiceDescriptorBinderSAXImpl extends UDA10ServiceDescriptorBinderImpl {
    private static Logger log = Logger.getLogger(ServiceDescriptorBinder.class.getName());

    /* loaded from: classes5.dex */
    protected static class DLNASAXParser extends SAXParser {
        protected DLNASAXParser() {
        }

        @Override // org.seamless.xml.SAXParser
        protected XMLReader create() {
            try {
                SAXParserFactory newInstance = SAXParserFactory.newInstance();
                newInstance.setFeature("http://xml.org/sax/features/external-general-entities", false);
                newInstance.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                if (getSchemaSources() != null) {
                    newInstance.setSchema(createSchema(getSchemaSources()));
                }
                XMLReader xMLReader = newInstance.newSAXParser().getXMLReader();
                xMLReader.setErrorHandler(getErrorHandler());
                return xMLReader;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderImpl, org.fourthline.cling.binding.xml.ServiceDescriptorBinder
    public <S extends Service> S describe(S s, String str) throws DescriptorBindingException, ValidationException {
        if (str == null || str.length() == 0) {
            throw new DescriptorBindingException("Null or empty descriptor");
        }
        try {
            log.fine("Reading service from XML descriptor");
            DLNASAXParser dLNASAXParser = new DLNASAXParser();
            MutableService mutableService = new MutableService();
            hydrateBasic(mutableService, s);
            new RootHandler(mutableService, dLNASAXParser);
            dLNASAXParser.parse(new InputSource(new StringReader(str.trim())));
            return (S) mutableService.build(s.getDevice());
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e2) {
            throw new DescriptorBindingException("Could not parse service descriptor: " + e2.toString(), e2);
        }
    }

    /* loaded from: classes5.dex */
    protected static class RootHandler extends ServiceDescriptorHandler<MutableService> {
        public RootHandler(MutableService mutableService, SAXParser sAXParser) {
            super(mutableService, sAXParser);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void startElement(Descriptor.Service.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(ActionListHandler.EL)) {
                ArrayList arrayList = new ArrayList();
                getInstance().actions = arrayList;
                new ActionListHandler(arrayList, this);
            }
            if (element.equals(StateVariableListHandler.EL)) {
                ArrayList arrayList2 = new ArrayList();
                getInstance().stateVariables = arrayList2;
                new StateVariableListHandler(arrayList2, this);
            }
        }
    }

    /* loaded from: classes5.dex */
    protected static class ActionListHandler extends ServiceDescriptorHandler<List<MutableAction>> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.actionList;

        public ActionListHandler(List<MutableAction> list, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(list, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void startElement(Descriptor.Service.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(ActionHandler.EL)) {
                MutableAction mutableAction = new MutableAction();
                getInstance().add(mutableAction);
                new ActionHandler(mutableAction, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class ActionHandler extends ServiceDescriptorHandler<MutableAction> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.action;

        public ActionHandler(MutableAction mutableAction, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(mutableAction, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void startElement(Descriptor.Service.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(ActionArgumentListHandler.EL)) {
                ArrayList arrayList = new ArrayList();
                getInstance().arguments = arrayList;
                new ActionArgumentListHandler(arrayList, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void endElement(Descriptor.Service.ELEMENT element) throws SAXException {
            if (AnonymousClass1.$SwitchMap$org$fourthline$cling$binding$xml$Descriptor$Service$ELEMENT[element.ordinal()] == 1) {
                getInstance().name = getCharacters();
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class ActionArgumentListHandler extends ServiceDescriptorHandler<List<MutableActionArgument>> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.argumentList;

        public ActionArgumentListHandler(List<MutableActionArgument> list, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(list, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void startElement(Descriptor.Service.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(ActionArgumentHandler.EL)) {
                MutableActionArgument mutableActionArgument = new MutableActionArgument();
                getInstance().add(mutableActionArgument);
                new ActionArgumentHandler(mutableActionArgument, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class ActionArgumentHandler extends ServiceDescriptorHandler<MutableActionArgument> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.argument;

        public ActionArgumentHandler(MutableActionArgument mutableActionArgument, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(mutableActionArgument, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void endElement(Descriptor.Service.ELEMENT element) throws SAXException {
            switch (element) {
                case name:
                    getInstance().name = getCharacters();
                    return;
                case direction:
                    String characters = getCharacters();
                    try {
                        getInstance().direction = ActionArgument.Direction.valueOf(characters.toUpperCase(Locale.ROOT));
                        return;
                    } catch (IllegalArgumentException unused) {
                        Logger logger = UDA10ServiceDescriptorBinderSAXImpl.log;
                        logger.warning("UPnP specification violation: Invalid action argument direction, assuming 'IN': " + characters);
                        getInstance().direction = ActionArgument.Direction.IN;
                        return;
                    }
                case relatedStateVariable:
                    getInstance().relatedStateVariable = getCharacters();
                    return;
                case retval:
                    getInstance().retval = true;
                    return;
                default:
                    return;
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class StateVariableListHandler extends ServiceDescriptorHandler<List<MutableStateVariable>> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.serviceStateTable;

        public StateVariableListHandler(List<MutableStateVariable> list, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(list, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void startElement(Descriptor.Service.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(StateVariableHandler.EL)) {
                MutableStateVariable mutableStateVariable = new MutableStateVariable();
                String value = attributes.getValue(Descriptor.Service.ATTRIBUTE.sendEvents.toString());
                mutableStateVariable.eventDetails = new StateVariableEventDetails(value != null && value.toUpperCase(Locale.ROOT).equals("YES"));
                getInstance().add(mutableStateVariable);
                new StateVariableHandler(mutableStateVariable, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class StateVariableHandler extends ServiceDescriptorHandler<MutableStateVariable> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.stateVariable;

        public StateVariableHandler(MutableStateVariable mutableStateVariable, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(mutableStateVariable, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void startElement(Descriptor.Service.ELEMENT element, Attributes attributes) throws SAXException {
            if (element.equals(AllowedValueListHandler.EL)) {
                ArrayList arrayList = new ArrayList();
                getInstance().allowedValues = arrayList;
                new AllowedValueListHandler(arrayList, this);
            }
            if (element.equals(AllowedValueRangeHandler.EL)) {
                MutableAllowedValueRange mutableAllowedValueRange = new MutableAllowedValueRange();
                getInstance().allowedValueRange = mutableAllowedValueRange;
                new AllowedValueRangeHandler(mutableAllowedValueRange, this);
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void endElement(Descriptor.Service.ELEMENT element) throws SAXException {
            int i = AnonymousClass1.$SwitchMap$org$fourthline$cling$binding$xml$Descriptor$Service$ELEMENT[element.ordinal()];
            if (i != 1) {
                switch (i) {
                    case 5:
                        String characters = getCharacters();
                        Datatype.Builtin byDescriptorName = Datatype.Builtin.getByDescriptorName(characters);
                        getInstance().dataType = byDescriptorName != null ? byDescriptorName.getDatatype() : new CustomDatatype(characters);
                        return;
                    case 6:
                        getInstance().defaultValue = getCharacters();
                        return;
                    default:
                        return;
                }
            } else {
                getInstance().name = getCharacters();
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class AllowedValueListHandler extends ServiceDescriptorHandler<List<String>> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.allowedValueList;

        public AllowedValueListHandler(List<String> list, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(list, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void endElement(Descriptor.Service.ELEMENT element) throws SAXException {
            if (AnonymousClass1.$SwitchMap$org$fourthline$cling$binding$xml$Descriptor$Service$ELEMENT[element.ordinal()] == 7) {
                getInstance().add(getCharacters());
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class AllowedValueRangeHandler extends ServiceDescriptorHandler<MutableAllowedValueRange> {
        public static final Descriptor.Service.ELEMENT EL = Descriptor.Service.ELEMENT.allowedValueRange;

        public AllowedValueRangeHandler(MutableAllowedValueRange mutableAllowedValueRange, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(mutableAllowedValueRange, serviceDescriptorHandler);
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public void endElement(Descriptor.Service.ELEMENT element) throws SAXException {
            try {
                switch (element) {
                    case minimum:
                        getInstance().minimum = Long.valueOf(getCharacters());
                        break;
                    case maximum:
                        getInstance().maximum = Long.valueOf(getCharacters());
                        break;
                    case step:
                        getInstance().step = Long.valueOf(getCharacters());
                        break;
                }
            } catch (Exception unused) {
            }
        }

        @Override // org.fourthline.cling.binding.xml.UDA10ServiceDescriptorBinderSAXImpl.ServiceDescriptorHandler
        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return element.equals(EL);
        }
    }

    /* loaded from: classes5.dex */
    protected static class ServiceDescriptorHandler<I> extends SAXParser.Handler<I> {
        public void endElement(Descriptor.Service.ELEMENT element) throws SAXException {
        }

        public boolean isLastElement(Descriptor.Service.ELEMENT element) {
            return false;
        }

        public void startElement(Descriptor.Service.ELEMENT element, Attributes attributes) throws SAXException {
        }

        public ServiceDescriptorHandler(I i) {
            super(i);
        }

        public ServiceDescriptorHandler(I i, SAXParser sAXParser) {
            super(i, sAXParser);
        }

        public ServiceDescriptorHandler(I i, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(i, serviceDescriptorHandler);
        }

        public ServiceDescriptorHandler(I i, SAXParser sAXParser, ServiceDescriptorHandler serviceDescriptorHandler) {
            super(i, sAXParser, serviceDescriptorHandler);
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            super.startElement(str, str2, str3, attributes);
            Descriptor.Service.ELEMENT valueOrNullOf = Descriptor.Service.ELEMENT.valueOrNullOf(str2);
            if (valueOrNullOf != null) {
                startElement(valueOrNullOf, attributes);
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler, org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) throws SAXException {
            super.endElement(str, str2, str3);
            Descriptor.Service.ELEMENT valueOrNullOf = Descriptor.Service.ELEMENT.valueOrNullOf(str2);
            if (valueOrNullOf != null) {
                endElement(valueOrNullOf);
            }
        }

        @Override // org.seamless.xml.SAXParser.Handler
        protected boolean isLastElement(String str, String str2, String str3) {
            Descriptor.Service.ELEMENT valueOrNullOf = Descriptor.Service.ELEMENT.valueOrNullOf(str2);
            return valueOrNullOf != null && isLastElement(valueOrNullOf);
        }
    }
}
