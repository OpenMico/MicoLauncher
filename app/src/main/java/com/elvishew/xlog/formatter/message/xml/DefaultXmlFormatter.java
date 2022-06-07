package com.elvishew.xlog.formatter.message.xml;

import com.elvishew.xlog.internal.Platform;
import com.elvishew.xlog.internal.SystemCompat;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/* JADX WARN: Classes with same name are omitted:
  classes.dex
 */
/* loaded from: MicoLauncher.apk:classes.jar:com/elvishew/xlog/formatter/message/xml/DefaultXmlFormatter.class */
public class DefaultXmlFormatter implements XmlFormatter {
    private static final int XML_INDENT = 4;

    public String format(String xml) {
        if (xml == null || xml.trim().length() == 0) {
            Platform.get().warn("XML empty.");
            return "";
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(4));
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">" + SystemCompat.lineSeparator);
        } catch (Exception e) {
            Platform.get().warn(e.getMessage());
            return xml;
        }
    }
}
