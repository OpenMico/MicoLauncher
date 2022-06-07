package com.zhangyue.we.anoprocesser;

import com.zhangyue.we.anoprocesser.xml.LayoutManager;
import com.zhangyue.we.x2c.ano.Xml;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.zhangyue.we.x2c.ano.Xml"})
/* loaded from: classes4.dex */
public class XmlProcessor extends AbstractProcessor {
    private int a = 0;
    private LayoutManager b;

    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        XmlProcessor.super.init(processingEnvironment);
        Log.init(processingEnvironment.getMessager());
        this.b = LayoutManager.instance();
        this.b.setFiler(processingEnvironment.getFiler());
    }

    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(Xml.class);
        TreeSet treeSet = new TreeSet();
        Iterator it = elementsAnnotatedWith.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String[] layouts = ((Xml) ((Element) it.next()).getAnnotation(Xml.class)).layouts();
            for (String str : layouts) {
                treeSet.add(str.substring(str.lastIndexOf(".") + 1));
            }
        }
        Iterator it2 = treeSet.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (this.a == 0 && this.b.getLayoutId(str2) != null) {
                this.a = this.b.getLayoutId(str2).intValue() >> 24;
            }
            this.b.setGroupId(this.a);
            this.b.translate(str2);
        }
        this.b.printTranslate();
        return false;
    }
}
