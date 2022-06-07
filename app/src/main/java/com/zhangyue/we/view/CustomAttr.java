package com.zhangyue.we.view;

import com.zhangyue.we.anoprocesser.xml.Attr;
import com.zhangyue.we.anoprocesser.xml.LayoutManager;
import java.util.HashMap;
import java.util.TreeSet;

/* loaded from: classes4.dex */
public class CustomAttr implements ITranslator {
    private TreeSet<String> a;
    private String b;
    private String c;
    private HashMap<String, Attr> d = LayoutManager.instance().getAttrs();

    @Override // com.zhangyue.we.view.ITranslator
    public void onAttributeEnd(StringBuilder sb) {
    }

    public CustomAttr(TreeSet<String> treeSet, String str, String str2) {
        this.a = treeSet;
        this.b = str2;
        this.c = str;
    }

    @Override // com.zhangyue.we.view.ITranslator
    public boolean translate(StringBuilder sb, String str, String str2) {
        Attr attr;
        HashMap<String, Attr> hashMap = this.d;
        if (hashMap == null || hashMap.size() == 0 || (attr = this.d.get(str)) == null) {
            return false;
        }
        if (!(attr.toFunc == null || attr.toFunc.name == null)) {
            String str3 = attr.toFunc.name + "\n";
            Object[] objArr = new Object[2];
            objArr[0] = attr.toFunc.isView ? this.b : this.c;
            objArr[1] = a(attr, str2);
            sb.append(String.format(str3, objArr));
        }
        return true;
    }

    private String a(Attr attr, String str) {
        String[] split = str.split("\\|");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            sb.append(b(attr, split[i]));
            if (i < split.length - 1) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0106, code lost:
        if (r5.equals("string") != false) goto L_0x010a;
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x010d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b(com.zhangyue.we.anoprocesser.xml.Attr r5, java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 358
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zhangyue.we.view.CustomAttr.b(com.zhangyue.we.anoprocesser.xml.Attr, java.lang.String):java.lang.String");
    }
}
