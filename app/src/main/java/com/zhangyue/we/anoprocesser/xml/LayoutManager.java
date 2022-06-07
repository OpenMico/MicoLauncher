package com.zhangyue.we.anoprocesser.xml;

import com.xiaomi.idm.service.iot.LightService;
import com.zhangyue.we.anoprocesser.FileFilter;
import com.zhangyue.we.anoprocesser.Log;
import com.zhangyue.we.anoprocesser.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes4.dex */
public class LayoutManager {
    private static LayoutManager a;
    private File b;
    private String c;
    private int d;
    private Filer e;
    private HashMap<String, Integer> g;
    private HashMap<String, Style> i;
    private HashMap<String, Attr> k;
    private HashMap<Integer, String> f = new HashMap<>();
    private HashMap<Integer, String> h = new HashMap<>();
    private HashMap<String, String> j = new HashMap<>();
    private HashMap<String, ArrayList<File>> l = new HashMap<>();

    private LayoutManager() {
    }

    public static LayoutManager instance() {
        if (a == null) {
            synchronized (LayoutManager.class) {
                if (a == null) {
                    a = new LayoutManager();
                }
            }
        }
        return a;
    }

    public void setFiler(Filer filer) {
        this.l.clear();
        this.e = filer;
        this.b = a();
        b();
        this.g = c();
        this.k = new Attr2FuncReader(new File(this.b, "X2C_CONFIG.xml")).parse();
    }

    public void setGroupId(int i) {
        this.d = i;
    }

    public Integer getLayoutId(String str) {
        return this.g.get(str);
    }

    public String translate(String str) {
        if (this.l.size() == 0) {
            this.l = a(this.b);
        }
        String str2 = null;
        Integer layoutId = getLayoutId(str);
        if (this.f.containsKey(layoutId)) {
            str2 = this.f.get(layoutId);
        } else {
            ArrayList<File> arrayList = this.l.get(str);
            if (arrayList != null) {
                Util.sortLayout(arrayList);
                ArrayList arrayList2 = new ArrayList();
                Iterator<File> it = arrayList.iterator();
                while (it.hasNext()) {
                    str2 = new LayoutReader(it.next(), str, this.e, this.c, this.d).parse();
                    arrayList2.add(str2);
                    this.f.put(layoutId, str2);
                }
                new MapWriter(this.d, arrayList, arrayList2, this.e).write();
            }
        }
        if (str2 != null) {
            this.j.put(str2 + ".java", str + ".xml");
        }
        return str2;
    }

    private HashMap<String, ArrayList<File>> a(File file) {
        return new FileFilter(file).include("layout").include("layout-land").include("layout-v28").include("layout-v27").include("layout-v26").include("layout-v25").include("layout-v24").include("layout-v23").include("layout-v22").include("layout-v21").include("layout-v20").include("layout-v19").include("layout-v18").include("layout-v17").include("layout-v16").include("layout-v15").include("layout-v14").exclude("build").exclude("java").exclude("libs").exclude("mipmap").exclude("values").exclude("drawable").exclude("anim").exclude(LightService.LightPropertyCommand.COLOR).exclude("menu").exclude("raw").exclude("xml").filter();
    }

    public Style getStyle(String str) {
        if (str == null) {
            return null;
        }
        if (this.i == null) {
            this.i = new HashMap<>();
            new StyleReader(this.b, this.i).parse();
        }
        return this.i.get(str);
    }

    public void printTranslate() {
        if (this.j.size() != 0) {
            int i = 0;
            for (String str : this.j.values()) {
                int length = (str.length() / 4) + 1;
                if (length > i) {
                    i = length;
                }
            }
            for (String str2 : this.j.keySet()) {
                String str3 = this.j.get(str2);
                int length2 = str3.length() / 4;
                StringBuilder sb = new StringBuilder(str3);
                if (length2 < i) {
                    for (int i2 = 0; i2 < i - length2; i2++) {
                        sb.append("\t");
                    }
                }
                Log.w(String.format("%s->\t%s", sb.toString(), str2));
            }
            this.j.clear();
        }
    }

    private File a() {
        try {
            String decode = URLDecoder.decode(this.e.createSourceFile("bb", new Element[0]).toUri().toString(), "utf-8");
            if (decode.startsWith("file:/")) {
                decode = decode.substring(5);
            }
            File file = new File(decode);
            while (!file.getName().equals("build")) {
                file = file.getParentFile();
            }
            return file.getParentFile();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void b() {
        String str = File.separator;
        File file = new File(this.b + str + "src" + str + "main" + str + "AndroidManifest.xml");
        SAXParser sAXParser = str;
        if (!file.exists()) {
            String str2 = this.b + str + "build" + str + "intermediates" + str + "manifests" + str + "full" + str + "debug" + str + "AndroidManifest.xml";
            file = new File(str2);
            sAXParser = str2;
        }
        try {
            try {
                sAXParser = null;
                sAXParser = 0;
                try {
                    sAXParser = SAXParserFactory.newInstance().newSAXParser();
                    sAXParser.parse(file, new DefaultHandler() { // from class: com.zhangyue.we.anoprocesser.xml.LayoutManager.1
                        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
                        public void startElement(String str3, String str4, String str5, Attributes attributes) throws SAXException {
                            super.startElement(str3, str4, str5, attributes);
                            if (str5.equals("manifest")) {
                                LayoutManager.this.c = attributes.getValue("package");
                            }
                        }
                    });
                    sAXParser.reset();
                } catch (Exception e) {
                    e.printStackTrace();
                    if (sAXParser != null) {
                        sAXParser.reset();
                    } else {
                        return;
                    }
                }
                if (sAXParser != 0) {
                    sAXParser.reset();
                }
            } catch (Throwable th) {
                if (sAXParser != 0) {
                    try {
                        sAXParser.reset();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private HashMap<String, Integer> c() {
        Throwable th;
        Exception e;
        try {
            HashMap<String, Integer> hashMap = new HashMap<>();
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(d()));
                bufferedReader = null;
                boolean z = false;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else if (readLine.contains("public static final class layout")) {
                            z = true;
                        } else if (!z) {
                            continue;
                        } else if (readLine.contains("}")) {
                            break;
                        } else {
                            String[] split = readLine.substring(readLine.indexOf("int") + 3, readLine.indexOf(";")).replaceAll(StringUtils.SPACE, "").trim().split("=");
                            int intValue = Integer.decode(split[1]).intValue();
                            hashMap.put(split[0], Integer.valueOf(intValue));
                            this.h.put(Integer.valueOf(intValue), split[0]);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        bufferedReader = bufferedReader;
                        e.printStackTrace();
                        Util.close(bufferedReader);
                        return hashMap;
                    } catch (Throwable th2) {
                        th = th2;
                        Util.close(bufferedReader);
                        throw th;
                    }
                }
                Util.close(bufferedReader);
            } catch (Exception e3) {
                e = e3;
            }
            return hashMap;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private File d() {
        IOException e;
        int i;
        String path;
        String str = File.separator;
        File file = null;
        try {
            i = 0;
            path = this.e.createSourceFile("test", new Element[0]).toUri().getPath();
            String replace = path.replace("apt", "r").replace("test.java", "");
            file = new File(replace, this.c.replace(".", str) + str + "R.java");
        } catch (IOException e2) {
            e = e2;
        }
        try {
            if (!file.exists()) {
                String substring = path.substring(0, path.indexOf("apt"));
                File[] listFiles = new File(new File(new File(substring).getParentFile(), "not_namespaced_r_class_sources"), path.substring(path.indexOf("apt") + 3)).getParentFile().listFiles();
                if (listFiles != null) {
                    int length = listFiles.length;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        File file2 = listFiles[i];
                        File file3 = new File(file2, "r" + str + this.c.replace(".", str) + str + "R.java");
                        if (file3.exists()) {
                            file = file3;
                            break;
                        }
                        i++;
                    }
                }
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            if (file != null) {
            }
            Log.e("X2C not find R.java!!!");
            return file;
        }
        if (file != null || !file.exists()) {
            Log.e("X2C not find R.java!!!");
        }
        return file;
    }

    public HashMap<String, Attr> getAttrs() {
        return this.k;
    }
}
