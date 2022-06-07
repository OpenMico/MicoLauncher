package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ConstraintLayoutStates {
    public static final String TAG = "ConstraintLayoutStates";
    ConstraintSet a;
    private final ConstraintLayout d;
    int b = -1;
    int c = -1;
    private SparseArray<a> e = new SparseArray<>();
    private SparseArray<ConstraintSet> f = new SparseArray<>();
    private ConstraintsChangedListener g = null;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ConstraintLayoutStates(Context context, ConstraintLayout constraintLayout, int i) {
        this.d = constraintLayout;
        a(context, i);
    }

    public boolean needsToChange(int i, float f, float f2) {
        int i2 = this.b;
        if (i2 != i) {
            return true;
        }
        a valueAt = i == -1 ? this.e.valueAt(0) : this.e.get(i2);
        return (this.c == -1 || !valueAt.b.get(this.c).a(f, f2)) && this.c != valueAt.a(f, f2);
    }

    public void updateConstraints(int i, float f, float f2) {
        ConstraintSet constraintSet;
        int i2;
        a aVar;
        int a2;
        ConstraintSet constraintSet2;
        int i3;
        int i4 = this.b;
        if (i4 == i) {
            if (i == -1) {
                aVar = this.e.valueAt(0);
            } else {
                aVar = this.e.get(i4);
            }
            if ((this.c == -1 || !aVar.b.get(this.c).a(f, f2)) && this.c != (a2 = aVar.a(f, f2))) {
                if (a2 == -1) {
                    constraintSet2 = this.a;
                } else {
                    constraintSet2 = aVar.b.get(a2).f;
                }
                if (a2 == -1) {
                    i3 = aVar.c;
                } else {
                    i3 = aVar.b.get(a2).e;
                }
                if (constraintSet2 != null) {
                    this.c = a2;
                    ConstraintsChangedListener constraintsChangedListener = this.g;
                    if (constraintsChangedListener != null) {
                        constraintsChangedListener.preLayoutChange(-1, i3);
                    }
                    constraintSet2.applyTo(this.d);
                    ConstraintsChangedListener constraintsChangedListener2 = this.g;
                    if (constraintsChangedListener2 != null) {
                        constraintsChangedListener2.postLayoutChange(-1, i3);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        this.b = i;
        a aVar2 = this.e.get(this.b);
        int a3 = aVar2.a(f, f2);
        if (a3 == -1) {
            constraintSet = aVar2.d;
        } else {
            constraintSet = aVar2.b.get(a3).f;
        }
        if (a3 == -1) {
            i2 = aVar2.c;
        } else {
            i2 = aVar2.b.get(a3).e;
        }
        if (constraintSet == null) {
            Log.v("ConstraintLayoutStates", "NO Constraint set found ! id=" + i + ", dim =" + f + ", " + f2);
            return;
        }
        this.c = a3;
        ConstraintsChangedListener constraintsChangedListener3 = this.g;
        if (constraintsChangedListener3 != null) {
            constraintsChangedListener3.preLayoutChange(i, i2);
        }
        constraintSet.applyTo(this.d);
        ConstraintsChangedListener constraintsChangedListener4 = this.g;
        if (constraintsChangedListener4 != null) {
            constraintsChangedListener4.postLayoutChange(i, i2);
        }
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.g = constraintsChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        int a;
        ArrayList<b> b = new ArrayList<>();
        int c;
        ConstraintSet d;

        public a(Context context, XmlPullParser xmlPullParser) {
            this.c = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.State);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.State_android_id) {
                    this.a = obtainStyledAttributes.getResourceId(index, this.a);
                } else if (index == R.styleable.State_constraints) {
                    this.c = obtainStyledAttributes.getResourceId(index, this.c);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.c);
                    context.getResources().getResourceName(this.c);
                    if ("layout".equals(resourceTypeName)) {
                        this.d = new ConstraintSet();
                        this.d.clone(context, this.c);
                    }
                }
            }
            obtainStyledAttributes.recycle();
        }

        void a(b bVar) {
            this.b.add(bVar);
        }

        public int a(float f, float f2) {
            for (int i = 0; i < this.b.size(); i++) {
                if (this.b.get(i).a(f, f2)) {
                    return i;
                }
            }
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b {
        float a;
        float b;
        float c;
        float d;
        int e;
        ConstraintSet f;

        public b(Context context, XmlPullParser xmlPullParser) {
            this.a = Float.NaN;
            this.b = Float.NaN;
            this.c = Float.NaN;
            this.d = Float.NaN;
            this.e = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Variant);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.Variant_constraints) {
                    this.e = obtainStyledAttributes.getResourceId(index, this.e);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.e);
                    context.getResources().getResourceName(this.e);
                    if ("layout".equals(resourceTypeName)) {
                        this.f = new ConstraintSet();
                        this.f.clone(context, this.e);
                    }
                } else if (index == R.styleable.Variant_region_heightLessThan) {
                    this.d = obtainStyledAttributes.getDimension(index, this.d);
                } else if (index == R.styleable.Variant_region_heightMoreThan) {
                    this.b = obtainStyledAttributes.getDimension(index, this.b);
                } else if (index == R.styleable.Variant_region_widthLessThan) {
                    this.c = obtainStyledAttributes.getDimension(index, this.c);
                } else if (index == R.styleable.Variant_region_widthMoreThan) {
                    this.a = obtainStyledAttributes.getDimension(index, this.a);
                } else {
                    Log.v("ConstraintLayoutStates", "Unknown tag");
                }
            }
            obtainStyledAttributes.recycle();
        }

        boolean a(float f, float f2) {
            if (!Float.isNaN(this.a) && f < this.a) {
                return false;
            }
            if (!Float.isNaN(this.b) && f2 < this.b) {
                return false;
            }
            if (Float.isNaN(this.c) || f <= this.c) {
                return Float.isNaN(this.d) || f2 <= this.d;
            }
            return false;
        }
    }

    private void a(Context context, int i) {
        XmlResourceParser xml = context.getResources().getXml(i);
        a aVar = null;
        try {
            int eventType = xml.getEventType();
            while (true) {
                char c = 1;
                if (eventType != 1) {
                    if (eventType != 0) {
                        switch (eventType) {
                            case 2:
                                String name = xml.getName();
                                c = 65535;
                                switch (name.hashCode()) {
                                    case -1349929691:
                                        if (name.equals("ConstraintSet")) {
                                            c = 4;
                                            break;
                                        }
                                        break;
                                    case 80204913:
                                        if (name.equals("State")) {
                                            c = 2;
                                            break;
                                        }
                                        break;
                                    case 1382829617:
                                        if (name.equals("StateSet")) {
                                            break;
                                        }
                                        break;
                                    case 1657696882:
                                        if (name.equals("layoutDescription")) {
                                            c = 0;
                                            break;
                                        }
                                        break;
                                    case 1901439077:
                                        if (name.equals("Variant")) {
                                            c = 3;
                                            break;
                                        }
                                        break;
                                }
                                switch (c) {
                                    case 0:
                                    case 1:
                                        continue;
                                    case 2:
                                        aVar = new a(context, xml);
                                        this.e.put(aVar.a, aVar);
                                        continue;
                                    case 3:
                                        b bVar = new b(context, xml);
                                        if (aVar != null) {
                                            aVar.a(bVar);
                                            break;
                                        } else {
                                            continue;
                                        }
                                    case 4:
                                        a(context, xml);
                                        continue;
                                    default:
                                        Log.v("ConstraintLayoutStates", "unknown tag " + name);
                                        continue;
                                }
                            case 3:
                                continue;
                            default:
                                continue;
                        }
                    } else {
                        xml.getName();
                    }
                    eventType = xml.next();
                } else {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    private void a(Context context, XmlPullParser xmlPullParser) {
        ConstraintSet constraintSet = new ConstraintSet();
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if ("id".equals(xmlPullParser.getAttributeName(i))) {
                String attributeValue = xmlPullParser.getAttributeValue(i);
                int identifier = attributeValue.contains("/") ? context.getResources().getIdentifier(attributeValue.substring(attributeValue.indexOf(47) + 1), "id", context.getPackageName()) : -1;
                if (identifier == -1) {
                    if (attributeValue == null || attributeValue.length() <= 1) {
                        Log.e("ConstraintLayoutStates", "error in parsing id");
                    } else {
                        identifier = Integer.parseInt(attributeValue.substring(1));
                    }
                }
                constraintSet.load(context, xmlPullParser);
                this.f.put(identifier, constraintSet);
                return;
            }
        }
    }
}
