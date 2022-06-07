package androidx.transition;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.core.content.res.TypedArrayUtils;
import com.xiaomi.miplay.mylibrary.statistic.OneTrackWorldUrl;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class TransitionInflater {
    private static final Class<?>[] a = {Context.class, AttributeSet.class};
    private static final ArrayMap<String, Constructor<?>> b = new ArrayMap<>();
    private final Context c;

    private TransitionInflater(@NonNull Context context) {
        this.c = context;
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser] */
    public Transition inflateTransition(int i) {
        try {
            i = this.c.getResources().getXml(i);
            try {
                return a((XmlPullParser) i, Xml.asAttributeSet(i), (Transition) null);
            } catch (IOException e) {
                throw new InflateException(i.getPositionDescription() + ": " + e.getMessage(), e);
            } catch (XmlPullParserException e2) {
                throw new InflateException(e2.getMessage(), e2);
            }
        } finally {
            i.close();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser] */
    public TransitionManager inflateTransitionManager(int i, ViewGroup viewGroup) {
        try {
            i = this.c.getResources().getXml(i);
            try {
                return a((XmlPullParser) i, Xml.asAttributeSet(i), viewGroup);
            } catch (IOException e) {
                InflateException inflateException = new InflateException(i.getPositionDescription() + ": " + e.getMessage());
                inflateException.initCause(e);
                throw inflateException;
            } catch (XmlPullParserException e2) {
                InflateException inflateException2 = new InflateException(e2.getMessage());
                inflateException2.initCause(e2);
                throw inflateException2;
            }
        } finally {
            i.close();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x0183, code lost:
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private androidx.transition.Transition a(org.xmlpull.v1.XmlPullParser r7, android.util.AttributeSet r8, androidx.transition.Transition r9) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.TransitionInflater.a(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, androidx.transition.Transition):androidx.transition.Transition");
    }

    private Object a(AttributeSet attributeSet, Class<?> cls, String str) {
        Object newInstance;
        Class<? extends U> asSubclass;
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        if (attributeValue != null) {
            try {
                synchronized (b) {
                    Constructor<?> constructor = b.get(attributeValue);
                    if (constructor == null && (asSubclass = Class.forName(attributeValue, false, this.c.getClassLoader()).asSubclass(cls)) != 0) {
                        constructor = asSubclass.getConstructor(a);
                        constructor.setAccessible(true);
                        b.put(attributeValue, constructor);
                    }
                    newInstance = constructor.newInstance(this.c, attributeSet);
                }
                return newInstance;
            } catch (Exception e) {
                throw new InflateException("Could not instantiate " + cls + " class " + attributeValue, e);
            }
        } else {
            throw new InflateException(str + " tag must have a 'class' attribute");
        }
    }

    @SuppressLint({"RestrictedApi"})
    private void b(XmlPullParser xmlPullParser, AttributeSet attributeSet, Transition transition) throws XmlPullParserException, IOException {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if ((next == 3 && xmlPullParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                if (xmlPullParser.getName().equals(OneTrackWorldUrl.ACTION_TARGET)) {
                    TypedArray obtainStyledAttributes = this.c.obtainStyledAttributes(attributeSet, o.a);
                    int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "targetId", 1, 0);
                    if (namedResourceId != 0) {
                        transition.addTarget(namedResourceId);
                    } else {
                        int namedResourceId2 = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "excludeId", 2, 0);
                        if (namedResourceId2 != 0) {
                            transition.excludeTarget(namedResourceId2, true);
                        } else {
                            String namedString = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "targetName", 4);
                            if (namedString != null) {
                                transition.addTarget(namedString);
                            } else {
                                String namedString2 = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "excludeName", 5);
                                if (namedString2 != null) {
                                    transition.excludeTarget(namedString2, true);
                                } else {
                                    String namedString3 = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "excludeClass", 3);
                                    if (namedString3 != null) {
                                        try {
                                            transition.excludeTarget(Class.forName(namedString3), true);
                                        } catch (ClassNotFoundException e) {
                                            obtainStyledAttributes.recycle();
                                            throw new RuntimeException("Could not create " + namedString3, e);
                                        }
                                    } else {
                                        String namedString4 = TypedArrayUtils.getNamedString(obtainStyledAttributes, xmlPullParser, "targetClass", 0);
                                        if (namedString4 != null) {
                                            transition.addTarget(Class.forName(namedString4));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    obtainStyledAttributes.recycle();
                } else {
                    throw new RuntimeException("Unknown scene name: " + xmlPullParser.getName());
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0054, code lost:
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private androidx.transition.TransitionManager a(org.xmlpull.v1.XmlPullParser r5, android.util.AttributeSet r6, android.view.ViewGroup r7) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r4 = this;
            int r0 = r5.getDepth()
            r1 = 0
        L_0x0005:
            int r2 = r5.next()
            r3 = 3
            if (r2 != r3) goto L_0x0012
            int r3 = r5.getDepth()
            if (r3 <= r0) goto L_0x0054
        L_0x0012:
            r3 = 1
            if (r2 == r3) goto L_0x0054
            r3 = 2
            if (r2 == r3) goto L_0x0019
            goto L_0x0005
        L_0x0019:
            java.lang.String r2 = r5.getName()
            java.lang.String r3 = "transitionManager"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x002b
            androidx.transition.TransitionManager r1 = new androidx.transition.TransitionManager
            r1.<init>()
            goto L_0x0005
        L_0x002b:
            java.lang.String r3 = "transition"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0039
            if (r1 == 0) goto L_0x0039
            r4.a(r6, r5, r7, r1)
            goto L_0x0005
        L_0x0039:
            java.lang.RuntimeException r6 = new java.lang.RuntimeException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = "Unknown scene name: "
            r7.append(r0)
            java.lang.String r5 = r5.getName()
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            r6.<init>(r5)
            throw r6
        L_0x0054:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.TransitionInflater.a(org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.view.ViewGroup):androidx.transition.TransitionManager");
    }

    @SuppressLint({"RestrictedApi"})
    private void a(AttributeSet attributeSet, XmlPullParser xmlPullParser, ViewGroup viewGroup, TransitionManager transitionManager) throws Resources.NotFoundException {
        Transition inflateTransition;
        TypedArray obtainStyledAttributes = this.c.obtainStyledAttributes(attributeSet, o.b);
        int namedResourceId = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "transition", 2, -1);
        int namedResourceId2 = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "fromScene", 0, -1);
        Scene scene = null;
        Scene sceneForLayout = namedResourceId2 < 0 ? null : Scene.getSceneForLayout(viewGroup, namedResourceId2, this.c);
        int namedResourceId3 = TypedArrayUtils.getNamedResourceId(obtainStyledAttributes, xmlPullParser, "toScene", 1, -1);
        if (namedResourceId3 >= 0) {
            scene = Scene.getSceneForLayout(viewGroup, namedResourceId3, this.c);
        }
        if (namedResourceId >= 0 && (inflateTransition = inflateTransition(namedResourceId)) != null) {
            if (scene == null) {
                throw new RuntimeException("No toScene for transition ID " + namedResourceId);
            } else if (sceneForLayout == null) {
                transitionManager.setTransition(scene, inflateTransition);
            } else {
                transitionManager.setTransition(sceneForLayout, scene, inflateTransition);
            }
        }
        obtainStyledAttributes.recycle();
    }
}
