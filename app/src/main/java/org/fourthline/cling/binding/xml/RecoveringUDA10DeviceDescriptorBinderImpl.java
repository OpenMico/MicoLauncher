package org.fourthline.cling.binding.xml;

import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Device;
import org.seamless.xml.ParserException;
import org.xml.sax.SAXParseException;

/* loaded from: classes5.dex */
public class RecoveringUDA10DeviceDescriptorBinderImpl extends UDA10DeviceDescriptorBinderImpl {
    private static Logger log = Logger.getLogger(RecoveringUDA10DeviceDescriptorBinderImpl.class.getName());

    /* JADX WARN: Removed duplicated region for block: B:23:0x0087 A[Catch: ValidationException -> 0x000b, TRY_LEAVE, TryCatch #0 {ValidationException -> 0x000b, blocks: (B:3:0x0002, B:4:0x0006, B:8:0x000f, B:10:0x0033, B:13:0x0039, B:14:0x0057, B:16:0x005d, B:19:0x0063, B:23:0x0087, B:25:0x008d, B:28:0x0093, B:29:0x00b4, B:31:0x00be, B:34:0x00c4, B:35:0x00e2), top: B:41:0x0000, inners: #1, #2, #3, #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x005d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00be A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // org.fourthline.cling.binding.xml.UDA10DeviceDescriptorBinderImpl, org.fourthline.cling.binding.xml.DeviceDescriptorBinder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public <D extends org.fourthline.cling.model.meta.Device> D describe(D r8, java.lang.String r9) throws org.fourthline.cling.binding.xml.DescriptorBindingException, org.fourthline.cling.model.ValidationException {
        /*
            r7 = this;
            if (r9 == 0) goto L_0x0006
            java.lang.String r9 = r9.trim()     // Catch: DescriptorBindingException -> 0x000e, ValidationException -> 0x000b
        L_0x0006:
            org.fourthline.cling.model.meta.Device r8 = super.describe(r8, r9)     // Catch: DescriptorBindingException -> 0x000e, ValidationException -> 0x000b
            return r8
        L_0x000b:
            r8 = move-exception
            goto L_0x00e6
        L_0x000e:
            r0 = move-exception
            java.util.logging.Logger r1 = org.fourthline.cling.binding.xml.RecoveringUDA10DeviceDescriptorBinderImpl.log     // Catch: ValidationException -> 0x000b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: ValidationException -> 0x000b
            r2.<init>()     // Catch: ValidationException -> 0x000b
            java.lang.String r3 = "Regular parsing failed: "
            r2.append(r3)     // Catch: ValidationException -> 0x000b
            java.lang.Throwable r3 = org.seamless.util.Exceptions.unwrap(r0)     // Catch: ValidationException -> 0x000b
            java.lang.String r3 = r3.getMessage()     // Catch: ValidationException -> 0x000b
            r2.append(r3)     // Catch: ValidationException -> 0x000b
            java.lang.String r2 = r2.toString()     // Catch: ValidationException -> 0x000b
            r1.warning(r2)     // Catch: ValidationException -> 0x000b
            java.lang.String r1 = r7.fixGarbageLeadingChars(r9)     // Catch: ValidationException -> 0x000b
            if (r1 == 0) goto L_0x0057
            org.fourthline.cling.model.meta.Device r8 = super.describe(r8, r1)     // Catch: DescriptorBindingException -> 0x0038, ValidationException -> 0x000b
            return r8
        L_0x0038:
            r1 = move-exception
            java.util.logging.Logger r2 = org.fourthline.cling.binding.xml.RecoveringUDA10DeviceDescriptorBinderImpl.log     // Catch: ValidationException -> 0x000b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: ValidationException -> 0x000b
            r3.<init>()     // Catch: ValidationException -> 0x000b
            java.lang.String r4 = "Removing leading garbage didn't work: "
            r3.append(r4)     // Catch: ValidationException -> 0x000b
            java.lang.Throwable r1 = org.seamless.util.Exceptions.unwrap(r1)     // Catch: ValidationException -> 0x000b
            java.lang.String r1 = r1.getMessage()     // Catch: ValidationException -> 0x000b
            r3.append(r1)     // Catch: ValidationException -> 0x000b
            java.lang.String r1 = r3.toString()     // Catch: ValidationException -> 0x000b
            r2.warning(r1)     // Catch: ValidationException -> 0x000b
        L_0x0057:
            java.lang.String r1 = r7.fixGarbageTrailingChars(r9, r0)     // Catch: ValidationException -> 0x000b
            if (r1 == 0) goto L_0x0081
            org.fourthline.cling.model.meta.Device r8 = super.describe(r8, r1)     // Catch: DescriptorBindingException -> 0x0062, ValidationException -> 0x000b
            return r8
        L_0x0062:
            r1 = move-exception
            java.util.logging.Logger r2 = org.fourthline.cling.binding.xml.RecoveringUDA10DeviceDescriptorBinderImpl.log     // Catch: ValidationException -> 0x000b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: ValidationException -> 0x000b
            r3.<init>()     // Catch: ValidationException -> 0x000b
            java.lang.String r4 = "Removing trailing garbage didn't work: "
            r3.append(r4)     // Catch: ValidationException -> 0x000b
            java.lang.Throwable r1 = org.seamless.util.Exceptions.unwrap(r1)     // Catch: ValidationException -> 0x000b
            java.lang.String r1 = r1.getMessage()     // Catch: ValidationException -> 0x000b
            r3.append(r1)     // Catch: ValidationException -> 0x000b
            java.lang.String r1 = r3.toString()     // Catch: ValidationException -> 0x000b
            r2.warning(r1)     // Catch: ValidationException -> 0x000b
        L_0x0081:
            r1 = 0
            r2 = r9
            r3 = r0
        L_0x0084:
            r4 = 5
            if (r1 >= r4) goto L_0x00b4
            java.lang.String r2 = r7.fixMissingNamespaces(r2, r3)     // Catch: ValidationException -> 0x000b
            if (r2 == 0) goto L_0x00b4
            org.fourthline.cling.model.meta.Device r8 = super.describe(r8, r2)     // Catch: DescriptorBindingException -> 0x0092, ValidationException -> 0x000b
            return r8
        L_0x0092:
            r3 = move-exception
            java.util.logging.Logger r4 = org.fourthline.cling.binding.xml.RecoveringUDA10DeviceDescriptorBinderImpl.log     // Catch: ValidationException -> 0x000b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: ValidationException -> 0x000b
            r5.<init>()     // Catch: ValidationException -> 0x000b
            java.lang.String r6 = "Fixing namespace prefix didn't work: "
            r5.append(r6)     // Catch: ValidationException -> 0x000b
            java.lang.Throwable r6 = org.seamless.util.Exceptions.unwrap(r3)     // Catch: ValidationException -> 0x000b
            java.lang.String r6 = r6.getMessage()     // Catch: ValidationException -> 0x000b
            r5.append(r6)     // Catch: ValidationException -> 0x000b
            java.lang.String r5 = r5.toString()     // Catch: ValidationException -> 0x000b
            r4.warning(r5)     // Catch: ValidationException -> 0x000b
            int r1 = r1 + 1
            goto L_0x0084
        L_0x00b4:
            java.lang.String r1 = org.seamless.xml.XmlPullParserUtils.fixXMLEntities(r9)     // Catch: ValidationException -> 0x000b
            boolean r2 = r1.equals(r9)     // Catch: ValidationException -> 0x000b
            if (r2 != 0) goto L_0x00e2
            org.fourthline.cling.model.meta.Device r8 = super.describe(r8, r1)     // Catch: DescriptorBindingException -> 0x00c3, ValidationException -> 0x000b
            return r8
        L_0x00c3:
            r8 = move-exception
            java.util.logging.Logger r1 = org.fourthline.cling.binding.xml.RecoveringUDA10DeviceDescriptorBinderImpl.log     // Catch: ValidationException -> 0x000b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: ValidationException -> 0x000b
            r2.<init>()     // Catch: ValidationException -> 0x000b
            java.lang.String r3 = "Fixing XML entities didn't work: "
            r2.append(r3)     // Catch: ValidationException -> 0x000b
            java.lang.Throwable r8 = org.seamless.util.Exceptions.unwrap(r8)     // Catch: ValidationException -> 0x000b
            java.lang.String r8 = r8.getMessage()     // Catch: ValidationException -> 0x000b
            r2.append(r8)     // Catch: ValidationException -> 0x000b
            java.lang.String r8 = r2.toString()     // Catch: ValidationException -> 0x000b
            r1.warning(r8)     // Catch: ValidationException -> 0x000b
        L_0x00e2:
            r7.handleInvalidDescriptor(r9, r0)     // Catch: ValidationException -> 0x000b
            goto L_0x00ee
        L_0x00e6:
            r0 = 0
            org.fourthline.cling.model.meta.Device r8 = r7.handleInvalidDevice(r9, r0, r8)
            if (r8 == 0) goto L_0x00ee
            return r8
        L_0x00ee:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "No device produced, did you swallow exceptions in your subclass?"
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.fourthline.cling.binding.xml.RecoveringUDA10DeviceDescriptorBinderImpl.describe(org.fourthline.cling.model.meta.Device, java.lang.String):org.fourthline.cling.model.meta.Device");
    }

    private String fixGarbageLeadingChars(String str) {
        int indexOf = str.indexOf("<?xml");
        return indexOf == -1 ? str : str.substring(indexOf);
    }

    protected String fixGarbageTrailingChars(String str, DescriptorBindingException descriptorBindingException) {
        int indexOf = str.indexOf("</root>");
        if (indexOf == -1) {
            log.warning("No closing </root> element in descriptor");
            return null;
        } else if (str.length() == indexOf + 7) {
            return null;
        } else {
            log.warning("Detected garbage characters after <root> node, removing");
            return str.substring(0, indexOf) + "</root>";
        }
    }

    protected String fixMissingNamespaces(String str, DescriptorBindingException descriptorBindingException) {
        String message;
        Throwable cause = descriptorBindingException.getCause();
        if ((!(cause instanceof SAXParseException) && !(cause instanceof ParserException)) || (message = cause.getMessage()) == null) {
            return null;
        }
        Matcher matcher = Pattern.compile("The prefix \"(.*)\" for element").matcher(message);
        if (!matcher.find() || matcher.groupCount() != 1) {
            matcher = Pattern.compile("undefined prefix: ([^ ]*)").matcher(message);
            if (!matcher.find() || matcher.groupCount() != 1) {
                return null;
            }
        }
        String group = matcher.group(1);
        Logger logger = log;
        logger.warning("Fixing missing namespace declaration for: " + group);
        Matcher matcher2 = Pattern.compile("<root([^>]*)").matcher(str);
        if (!matcher2.find() || matcher2.groupCount() != 1) {
            log.fine("Could not find <root> element attributes");
            return null;
        }
        String group2 = matcher2.group(1);
        Logger logger2 = log;
        logger2.fine("Preserving existing <root> element attributes/namespace declarations: " + matcher2.group(0));
        Matcher matcher3 = Pattern.compile("<root[^>]*>(.*)</root>", 32).matcher(str);
        if (!matcher3.find() || matcher3.groupCount() != 1) {
            log.fine("Could not extract body of <root> element");
            return null;
        }
        String group3 = matcher3.group(1);
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root " + String.format(Locale.ROOT, "xmlns:%s=\"urn:schemas-dlna-org:device-1-0\"", group) + group2 + ">" + group3 + "</root>";
    }

    protected void handleInvalidDescriptor(String str, DescriptorBindingException descriptorBindingException) throws DescriptorBindingException {
        throw descriptorBindingException;
    }

    protected <D extends Device> D handleInvalidDevice(String str, D d, ValidationException validationException) throws ValidationException {
        throw validationException;
    }
}
