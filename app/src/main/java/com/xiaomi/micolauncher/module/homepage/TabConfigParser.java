package com.xiaomi.micolauncher.module.homepage;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class TabConfigParser {
    public static final String TAG_FRAGMENT = "fragment";
    private final int a;
    private final Context b;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public interface b<T> {
        T b(XmlResourceParser xmlResourceParser);
    }

    public static TabConfigParser get(Context context) {
        return new TabConfigParser(context, ChildModeManager.getManager().isChildMode() ? R.xml.child_layout : R.xml.default_layout);
    }

    public TabConfigParser(Context context, int i) {
        this.b = context;
        this.a = i;
    }

    public List<ConfigInfo> loadLayout() throws IOException, XmlPullParserException {
        return a(this.a);
    }

    private List<ConfigInfo> a(int i) throws IOException, XmlPullParserException {
        XmlResourceParser xml = this.b.getResources().getXml(i);
        beginDocument(xml, "layouts");
        int depth = xml.getDepth();
        ArrayMap<String, b<ConfigInfo>> a2 = a();
        ArrayList arrayList = new ArrayList();
        while (true) {
            int next = xml.next();
            if ((next != 3 || xml.getDepth() > depth) && next != 1) {
                if (next == 2) {
                    arrayList.add(a2.get(xml.getName()).b(xml));
                }
            }
        }
        return arrayList;
    }

    private ArrayMap<String, b<ConfigInfo>> a() {
        ArrayMap<String, b<ConfigInfo>> arrayMap = new ArrayMap<>();
        arrayMap.put("fragment", new a());
        return arrayMap;
    }

    protected static void beginDocument(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        } else if (!xmlPullParser.getName().equals(str)) {
            throw new XmlPullParserException("Unexpected start tag: found " + xmlPullParser.getName() + ", expected " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a implements b<ConfigInfo> {
        private a() {
        }

        /* renamed from: a */
        public ConfigInfo b(XmlResourceParser xmlResourceParser) {
            String attributeValue = xmlResourceParser.getAttributeValue(null, "name");
            return new ConfigInfo(xmlResourceParser.getAttributeResourceValue(null, "icon", 0), attributeValue);
        }
    }

    /* loaded from: classes3.dex */
    public class ConfigInfo {
        int a;
        String b;

        public ConfigInfo(int i, String str) {
            this.a = i;
            this.b = str;
        }

        public int getIconId() {
            return this.a;
        }

        public void setIconId(int i) {
            this.a = i;
        }

        public String getName() {
            return this.b;
        }

        public void setName(String str) {
            this.b = str;
        }

        public String getTag() {
            if (TextUtils.isEmpty(this.b)) {
                return null;
            }
            int i = 0;
            int length = this.b.length() - 1;
            while (true) {
                if (length < 0) {
                    break;
                } else if (this.b.charAt(length) == '.') {
                    i = length + 1;
                    break;
                } else {
                    length--;
                }
            }
            return this.b.substring(i);
        }
    }
}
