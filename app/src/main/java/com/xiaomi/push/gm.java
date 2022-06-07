package com.xiaomi.push;

import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.miplay.mylibrary.DataModel;
import com.xiaomi.push.gc;
import com.xiaomi.push.gg;
import com.xiaomi.push.gi;
import com.xiaomi.push.service.al;
import com.xiaomi.push.service.au;
import com.xiaomi.push.service.e;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.hapjs.features.channel.IChannel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes4.dex */
public class gm {
    private static XmlPullParser a;

    public static gb a(String str, String str2, XmlPullParser xmlPullParser) {
        Object a2 = gl.a().a("all", "xm:chat");
        if (a2 == null || !(a2 instanceof e)) {
            return null;
        }
        return ((e) a2).b(xmlPullParser);
    }

    public static gc a(XmlPullParser xmlPullParser, fn fnVar) {
        String attributeValue = xmlPullParser.getAttributeValue("", "id");
        String attributeValue2 = xmlPullParser.getAttributeValue("", "to");
        String attributeValue3 = xmlPullParser.getAttributeValue("", "from");
        String attributeValue4 = xmlPullParser.getAttributeValue("", "chid");
        gc.a a2 = gc.a.a(xmlPullParser.getAttributeValue("", "type"));
        HashMap hashMap = new HashMap();
        boolean z = false;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            hashMap.put(attributeName, xmlPullParser.getAttributeValue("", attributeName));
        }
        gc gcVar = null;
        gi giVar = null;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("error")) {
                    giVar = m947a(xmlPullParser);
                } else {
                    gcVar = new gc();
                    gcVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("iq")) {
                z = true;
            }
        }
        if (gcVar == null) {
            if (gc.a.a == a2 || gc.a.b == a2) {
                fm fmVar = new fm();
                fmVar.k(attributeValue);
                fmVar.m(attributeValue3);
                fmVar.n(attributeValue2);
                fmVar.a(gc.a.d);
                fmVar.l(attributeValue4);
                fmVar.a(new gi(gi.a.e));
                fnVar.a(fmVar);
                b.d("iq usage error. send packet in packet parser.");
                return null;
            }
            gcVar = new fv();
        }
        gcVar.k(attributeValue);
        gcVar.m(attributeValue2);
        gcVar.l(attributeValue4);
        gcVar.n(attributeValue3);
        gcVar.a(a2);
        gcVar.a(giVar);
        gcVar.a(hashMap);
        return gcVar;
    }

    public static ge a(XmlPullParser xmlPullParser) {
        String str;
        boolean z = false;
        String str2 = null;
        if ("1".equals(xmlPullParser.getAttributeValue("", ai.az))) {
            String attributeValue = xmlPullParser.getAttributeValue("", "chid");
            String attributeValue2 = xmlPullParser.getAttributeValue("", "id");
            String attributeValue3 = xmlPullParser.getAttributeValue("", "from");
            String attributeValue4 = xmlPullParser.getAttributeValue("", "to");
            String attributeValue5 = xmlPullParser.getAttributeValue("", "type");
            al.b a2 = al.a().a(attributeValue, attributeValue4);
            if (a2 == null) {
                a2 = al.a().a(attributeValue, attributeValue3);
            }
            if (a2 != null) {
                ge geVar = null;
                while (!z) {
                    int next = xmlPullParser.next();
                    if (next == 2) {
                        if (!ai.az.equals(xmlPullParser.getName())) {
                            throw new fy("error while receiving a encrypted message with wrong format");
                        } else if (xmlPullParser.next() == 4) {
                            String text = xmlPullParser.getText();
                            if (Commands.ResolutionValues.BITSTREAM_PANORAMIC_SOUND.equals(attributeValue) || "6".equals(attributeValue)) {
                                gd gdVar = new gd();
                                gdVar.l(attributeValue);
                                gdVar.b(true);
                                gdVar.n(attributeValue3);
                                gdVar.m(attributeValue4);
                                gdVar.k(attributeValue2);
                                gdVar.f(attributeValue5);
                                String[] strArr = null;
                                gb gbVar = new gb(ai.az, null, strArr, strArr);
                                gbVar.m935a(text);
                                gdVar.a(gbVar);
                                return gdVar;
                            }
                            a(au.a(au.a(a2.h, attributeValue2), text));
                            a.next();
                            geVar = a(a);
                        } else {
                            throw new fy("error while receiving a encrypted message with wrong format");
                        }
                    } else if (next == 3 && xmlPullParser.getName().equals("message")) {
                        z = true;
                    }
                }
                if (geVar != null) {
                    return geVar;
                }
                throw new fy("error while receiving a encrypted message with wrong format");
            }
            throw new fy("the channel id is wrong while receiving a encrypted message");
        }
        gd gdVar2 = new gd();
        String attributeValue6 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue6 == null) {
            attributeValue6 = "ID_NOT_AVAILABLE";
        }
        gdVar2.k(attributeValue6);
        gdVar2.m(xmlPullParser.getAttributeValue("", "to"));
        gdVar2.n(xmlPullParser.getAttributeValue("", "from"));
        gdVar2.l(xmlPullParser.getAttributeValue("", "chid"));
        gdVar2.a(xmlPullParser.getAttributeValue("", "appid"));
        try {
            str = xmlPullParser.getAttributeValue("", "transient");
        } catch (Exception unused) {
            str = null;
        }
        try {
            String attributeValue7 = xmlPullParser.getAttributeValue("", RtspHeaders.Values.SEQ);
            if (!TextUtils.isEmpty(attributeValue7)) {
                gdVar2.b(attributeValue7);
            }
        } catch (Exception unused2) {
        }
        try {
            String attributeValue8 = xmlPullParser.getAttributeValue("", "mseq");
            if (!TextUtils.isEmpty(attributeValue8)) {
                gdVar2.c(attributeValue8);
            }
        } catch (Exception unused3) {
        }
        try {
            String attributeValue9 = xmlPullParser.getAttributeValue("", "fseq");
            if (!TextUtils.isEmpty(attributeValue9)) {
                gdVar2.d(attributeValue9);
            }
        } catch (Exception unused4) {
        }
        try {
            String attributeValue10 = xmlPullParser.getAttributeValue("", "status");
            if (!TextUtils.isEmpty(attributeValue10)) {
                gdVar2.e(attributeValue10);
            }
        } catch (Exception unused5) {
        }
        gdVar2.a(!TextUtils.isEmpty(str) && str.equalsIgnoreCase("true"));
        gdVar2.f(xmlPullParser.getAttributeValue("", "type"));
        String c = c(xmlPullParser);
        if (c == null || "".equals(c.trim())) {
            ge.q();
        } else {
            gdVar2.j(c);
        }
        while (!z) {
            int next2 = xmlPullParser.next();
            if (next2 == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (TextUtils.isEmpty(namespace)) {
                    namespace = "xm";
                }
                if (name.equals("subject")) {
                    c(xmlPullParser);
                    gdVar2.g(b(xmlPullParser));
                } else if (name.equals("body")) {
                    String attributeValue11 = xmlPullParser.getAttributeValue("", "encode");
                    String b = b(xmlPullParser);
                    if (!TextUtils.isEmpty(attributeValue11)) {
                        gdVar2.a(b, attributeValue11);
                    } else {
                        gdVar2.h(b);
                    }
                } else if (name.equals("thread")) {
                    if (str2 == null) {
                        str2 = xmlPullParser.nextText();
                    }
                } else if (name.equals("error")) {
                    gdVar2.a(m947a(xmlPullParser));
                } else {
                    gdVar2.a(a(name, namespace, xmlPullParser));
                }
            } else if (next2 == 3 && xmlPullParser.getName().equals("message")) {
                z = true;
            }
        }
        gdVar2.i(str2);
        return gdVar2;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static gg m945a(XmlPullParser xmlPullParser) {
        gg.b bVar = gg.b.available;
        String attributeValue = xmlPullParser.getAttributeValue("", "type");
        if (attributeValue != null && !attributeValue.equals("")) {
            try {
                bVar = gg.b.valueOf(attributeValue);
            } catch (IllegalArgumentException unused) {
                System.err.println("Found invalid presence type " + attributeValue);
            }
        }
        gg ggVar = new gg(bVar);
        ggVar.m(xmlPullParser.getAttributeValue("", "to"));
        ggVar.n(xmlPullParser.getAttributeValue("", "from"));
        ggVar.l(xmlPullParser.getAttributeValue("", "chid"));
        String attributeValue2 = xmlPullParser.getAttributeValue("", "id");
        if (attributeValue2 == null) {
            attributeValue2 = "ID_NOT_AVAILABLE";
        }
        ggVar.k(attributeValue2);
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                String name = xmlPullParser.getName();
                String namespace = xmlPullParser.getNamespace();
                if (name.equals("status")) {
                    ggVar.a(xmlPullParser.nextText());
                } else if (name.equals("priority")) {
                    try {
                        ggVar.a(Integer.parseInt(xmlPullParser.nextText()));
                    } catch (NumberFormatException unused2) {
                    } catch (IllegalArgumentException unused3) {
                        ggVar.a(0);
                    }
                } else if (name.equals("show")) {
                    String nextText = xmlPullParser.nextText();
                    try {
                        ggVar.a(gg.a.valueOf(nextText));
                    } catch (IllegalArgumentException unused4) {
                        System.err.println("Found invalid presence mode " + nextText);
                    }
                } else if (name.equals("error")) {
                    ggVar.a(m947a(xmlPullParser));
                } else {
                    ggVar.a(a(name, namespace, xmlPullParser));
                }
            } else if (next == 3 && xmlPullParser.getName().equals("presence")) {
                z = true;
            }
        }
        return ggVar;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static gh m946a(XmlPullParser xmlPullParser) {
        gh ghVar = null;
        boolean z = false;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                ghVar = new gh(xmlPullParser.getName());
            } else if (next == 3 && xmlPullParser.getName().equals("error")) {
                z = true;
            }
        }
        return ghVar;
    }

    /* renamed from: a  reason: collision with other method in class */
    public static gi m947a(XmlPullParser xmlPullParser) {
        String str = DataModel.CIRCULATEFAIL_NO_SUPPORT;
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        String str2 = null;
        String str3 = null;
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            if (xmlPullParser.getAttributeName(i).equals("code")) {
                str = xmlPullParser.getAttributeValue("", "code");
            }
            if (xmlPullParser.getAttributeName(i).equals("type")) {
                str2 = xmlPullParser.getAttributeValue("", "type");
            }
            if (xmlPullParser.getAttributeName(i).equals(IChannel.EXTRA_CLOSE_REASON)) {
                str3 = xmlPullParser.getAttributeValue("", IChannel.EXTRA_CLOSE_REASON);
            }
        }
        String str4 = null;
        String str5 = null;
        while (!z) {
            int next = xmlPullParser.next();
            if (next == 2) {
                if (xmlPullParser.getName().equals("text")) {
                    str5 = xmlPullParser.nextText();
                } else {
                    String name = xmlPullParser.getName();
                    String namespace = xmlPullParser.getNamespace();
                    if ("urn:ietf:params:xml:ns:xmpp-stanzas".equals(namespace)) {
                        str4 = name;
                    } else {
                        arrayList.add(a(name, namespace, xmlPullParser));
                    }
                }
            } else if (next == 3) {
                if (xmlPullParser.getName().equals("error")) {
                    z = true;
                }
            } else if (next == 4) {
                str5 = xmlPullParser.getText();
            }
        }
        return new gi(Integer.parseInt(str), str2 == null ? "cancel" : str2, str3, str4, str5, arrayList);
    }

    private static void a(byte[] bArr) {
        if (a == null) {
            try {
                a = XmlPullParserFactory.newInstance().newPullParser();
                a.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        a.setInput(new InputStreamReader(new ByteArrayInputStream(bArr)));
    }

    private static String b(XmlPullParser xmlPullParser) {
        String str = "";
        int depth = xmlPullParser.getDepth();
        while (true) {
            if (xmlPullParser.next() == 3 && xmlPullParser.getDepth() == depth) {
                return str;
            }
            str = str + xmlPullParser.getText();
        }
    }

    private static String c(XmlPullParser xmlPullParser) {
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            if ("xml:lang".equals(attributeName) || ("lang".equals(attributeName) && "xml".equals(xmlPullParser.getAttributePrefix(i)))) {
                return xmlPullParser.getAttributeValue(i);
            }
        }
        return null;
    }
}
