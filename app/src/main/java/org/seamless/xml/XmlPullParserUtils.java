package org.seamless.xml;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes4.dex */
public class XmlPullParserUtils {
    private static final Logger log = Logger.getLogger(XmlPullParserUtils.class.getName());
    static XmlPullParserFactory xmlPullParserFactory;

    static {
        try {
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            log.severe("cannot create XmlPullParserFactory instance: " + e);
        }
    }

    public static XmlPullParser createParser(String str) throws XmlPullParserException {
        XmlPullParser createParser = createParser();
        try {
            createParser.setInput(new ByteArrayInputStream(str.getBytes("UTF-8")), "UTF-8");
            return createParser;
        } catch (UnsupportedEncodingException unused) {
            throw new XmlPullParserException("UTF-8: unsupported encoding");
        }
    }

    public static XmlPullParser createParser() throws XmlPullParserException {
        XmlPullParserFactory xmlPullParserFactory2 = xmlPullParserFactory;
        if (xmlPullParserFactory2 != null) {
            return xmlPullParserFactory2.newPullParser();
        }
        throw new XmlPullParserException("no XML Pull parser factory");
    }

    public static XmlSerializer createSerializer() throws XmlPullParserException {
        XmlPullParserFactory xmlPullParserFactory2 = xmlPullParserFactory;
        if (xmlPullParserFactory2 != null) {
            return xmlPullParserFactory2.newSerializer();
        }
        throw new XmlPullParserException("no XML Pull parser factory");
    }

    public static void setSerializerIndentation(XmlSerializer xmlSerializer, int i) {
        if (i > 0) {
            try {
                xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            } catch (Exception e) {
                Logger logger = log;
                logger.warning("error setting feature of XmlSerializer: " + e);
            }
        }
    }

    public static void skipTag(XmlPullParser xmlPullParser, String str) throws IOException, XmlPullParserException {
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getName().equals(str)) {
                return;
            }
        }
    }

    public static void searchTag(XmlPullParser xmlPullParser, String str) throws IOException, XmlPullParserException {
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                throw new IOException(String.format("tag '%s' not found", str));
            } else if (next == 2 && xmlPullParser.getName().equals(str)) {
                return;
            }
        }
    }

    public static void serializeIfNotNullOrEmpty(XmlSerializer xmlSerializer, String str, String str2, String str3) throws Exception {
        if (!isNullOrEmpty(str3)) {
            xmlSerializer.startTag(str, str2);
            xmlSerializer.text(str3);
            xmlSerializer.endTag(str, str2);
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static void serializeIfNotEqual(XmlSerializer xmlSerializer, String str, String str2, Object obj, Object obj2) throws Exception {
        if (obj != null && !obj.equals(obj2)) {
            xmlSerializer.startTag(str, str2);
            xmlSerializer.text(obj.toString());
            xmlSerializer.endTag(str, str2);
        }
    }

    public static String fixXMLEntities(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '&') {
                String substring = str.substring(i, Math.min(i + 10, str.length()));
                if (substring.startsWith("&#") || substring.startsWith("&lt;") || substring.startsWith("&gt;") || substring.startsWith("&amp;") || substring.startsWith(LrcRow.APOS) || substring.startsWith("&quot;")) {
                    sb.append(charAt);
                } else {
                    sb.append("&amp;");
                    z = true;
                }
            } else {
                sb.append(charAt);
            }
        }
        if (z) {
            log.warning("fixed badly encoded entities in XML");
        }
        return sb.toString();
    }
}
