package com.zhangyue.we.anoprocesser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class FileFilter {
    private File a;
    private ArrayList<String> b;
    private ArrayList<String> c;
    private String d;

    public FileFilter(File file) {
        this.a = file;
    }

    public FileFilter include(String str) {
        if (this.c == null) {
            this.c = new ArrayList<>();
        }
        this.c.add(str);
        return this;
    }

    public FileFilter fileStart(String str) {
        this.d = str;
        return this;
    }

    public FileFilter exclude(String str) {
        if (this.b == null) {
            this.b = new ArrayList<>();
        }
        this.b.add(str);
        return this;
    }

    public HashMap<String, ArrayList<File>> filter() {
        HashMap<String, ArrayList<File>> hashMap = new HashMap<>();
        a(this.a, hashMap);
        return hashMap;
    }

    private void a(File file, HashMap<String, ArrayList<File>> hashMap) {
        String name = file.getName();
        if (file.isDirectory() && !a(name)) {
            File[] listFiles = file.listFiles();
            boolean b = b(name);
            for (File file2 : listFiles) {
                if (b) {
                    String name2 = file2.getName();
                    String str = this.d;
                    if (str == null || name2.startsWith(str)) {
                        String substring = name2.substring(0, name2.lastIndexOf("."));
                        ArrayList<File> orDefault = hashMap.getOrDefault(substring, null);
                        if (orDefault == null) {
                            orDefault = new ArrayList<>();
                            hashMap.put(substring, orDefault);
                        }
                        orDefault.add(file2);
                    }
                } else {
                    a(file2, hashMap);
                }
            }
        }
    }

    private boolean a(String str) {
        ArrayList<String> arrayList = this.b;
        if (arrayList == null) {
            return false;
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            if (str.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean b(String str) {
        ArrayList<String> arrayList = this.c;
        if (arrayList == null) {
            return false;
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            if (str.contains(it.next())) {
                return true;
            }
        }
        return false;
    }
}
