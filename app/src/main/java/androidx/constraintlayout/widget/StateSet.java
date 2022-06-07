package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class StateSet {
    public static final String TAG = "ConstraintLayoutStates";
    int a = -1;
    int b = -1;
    int c = -1;
    private SparseArray<a> d = new SparseArray<>();
    private SparseArray<ConstraintSet> e = new SparseArray<>();
    private ConstraintsChangedListener f = null;

    public StateSet(Context context, XmlPullParser xmlPullParser) {
        a(context, xmlPullParser);
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0090 A[Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7, TryCatch #2 {IOException -> 0x00c7, XmlPullParserException -> 0x00cc, blocks: (B:9:0x0029, B:13:0x0032, B:15:0x0037, B:18:0x0044, B:27:0x0062, B:30:0x006c, B:33:0x0075, B:36:0x007f, B:40:0x008a, B:42:0x0090, B:44:0x0097, B:45:0x009b, B:46:0x00a8, B:47:0x00be, B:48:0x00c1), top: B:54:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x009b A[Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7, TryCatch #2 {IOException -> 0x00c7, XmlPullParserException -> 0x00cc, blocks: (B:9:0x0029, B:13:0x0032, B:15:0x0037, B:18:0x0044, B:27:0x0062, B:30:0x006c, B:33:0x0075, B:36:0x007f, B:40:0x008a, B:42:0x0090, B:44:0x0097, B:45:0x009b, B:46:0x00a8, B:47:0x00be, B:48:0x00c1), top: B:54:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00c1 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(android.content.Context r8, org.xmlpull.v1.XmlPullParser r9) {
        /*
            r7 = this;
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r9)
            int[] r1 = androidx.constraintlayout.widget.R.styleable.StateSet
            android.content.res.TypedArray r0 = r8.obtainStyledAttributes(r0, r1)
            int r1 = r0.getIndexCount()
            r2 = 0
            r3 = r2
        L_0x0010:
            if (r3 >= r1) goto L_0x0025
            int r4 = r0.getIndex(r3)
            int r5 = androidx.constraintlayout.widget.R.styleable.StateSet_defaultState
            if (r4 != r5) goto L_0x0022
            int r5 = r7.a
            int r4 = r0.getResourceId(r4, r5)
            r7.a = r4
        L_0x0022:
            int r3 = r3 + 1
            goto L_0x0010
        L_0x0025:
            r0.recycle()
            r0 = 0
            int r1 = r9.getEventType()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
        L_0x002d:
            r3 = 1
            if (r1 == r3) goto L_0x00d0
            if (r1 == 0) goto L_0x00be
            switch(r1) {
                case 2: goto L_0x0044;
                case 3: goto L_0x0037;
                default: goto L_0x0035;
            }     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
        L_0x0035:
            goto L_0x00c1
        L_0x0037:
            java.lang.String r1 = "StateSet"
            java.lang.String r3 = r9.getName()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            boolean r1 = r1.equals(r3)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            if (r1 == 0) goto L_0x00c1
            return
        L_0x0044:
            java.lang.String r1 = r9.getName()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            r4 = -1
            int r5 = r1.hashCode()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            r6 = 80204913(0x4c7d471, float:4.697977E-36)
            if (r5 == r6) goto L_0x007f
            r6 = 1301459538(0x4d92b252, float:3.07644992E8)
            if (r5 == r6) goto L_0x0075
            r6 = 1382829617(0x526c4e31, float:2.53731029E11)
            if (r5 == r6) goto L_0x006c
            r3 = 1901439077(0x7155a865, float:1.0579821E30)
            if (r5 == r3) goto L_0x0062
            goto L_0x0089
        L_0x0062:
            java.lang.String r3 = "Variant"
            boolean r3 = r1.equals(r3)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            if (r3 == 0) goto L_0x0089
            r3 = 3
            goto L_0x008a
        L_0x006c:
            java.lang.String r5 = "StateSet"
            boolean r5 = r1.equals(r5)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            if (r5 == 0) goto L_0x0089
            goto L_0x008a
        L_0x0075:
            java.lang.String r3 = "LayoutDescription"
            boolean r3 = r1.equals(r3)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            if (r3 == 0) goto L_0x0089
            r3 = r2
            goto L_0x008a
        L_0x007f:
            java.lang.String r3 = "State"
            boolean r3 = r1.equals(r3)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            if (r3 == 0) goto L_0x0089
            r3 = 2
            goto L_0x008a
        L_0x0089:
            r3 = r4
        L_0x008a:
            switch(r3) {
                case 0: goto L_0x00c1;
                case 1: goto L_0x00c1;
                case 2: goto L_0x009b;
                case 3: goto L_0x0090;
                default: goto L_0x008d;
            }     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
        L_0x008d:
            java.lang.String r3 = "ConstraintLayoutStates"
            goto L_0x00a8
        L_0x0090:
            androidx.constraintlayout.widget.StateSet$b r1 = new androidx.constraintlayout.widget.StateSet$b     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            r1.<init>(r8, r9)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            if (r0 == 0) goto L_0x00c1
            r0.a(r1)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            goto L_0x00c1
        L_0x009b:
            androidx.constraintlayout.widget.StateSet$a r0 = new androidx.constraintlayout.widget.StateSet$a     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            r0.<init>(r8, r9)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            android.util.SparseArray<androidx.constraintlayout.widget.StateSet$a> r1 = r7.d     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            int r3 = r0.a     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            r1.put(r3, r0)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            goto L_0x00c1
        L_0x00a8:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            r4.<init>()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            java.lang.String r5 = "unknown tag "
            r4.append(r5)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            r4.append(r1)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            java.lang.String r1 = r4.toString()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            android.util.Log.v(r3, r1)     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            goto L_0x00c1
        L_0x00be:
            r9.getName()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
        L_0x00c1:
            int r1 = r9.next()     // Catch: XmlPullParserException -> 0x00cc, IOException -> 0x00c7
            goto L_0x002d
        L_0x00c7:
            r8 = move-exception
            r8.printStackTrace()
            goto L_0x00d0
        L_0x00cc:
            r8 = move-exception
            r8.printStackTrace()
        L_0x00d0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.StateSet.a(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    public boolean needsToChange(int i, float f, float f2) {
        int i2 = this.b;
        if (i2 != i) {
            return true;
        }
        a valueAt = i == -1 ? this.d.valueAt(0) : this.d.get(i2);
        return (this.c == -1 || !valueAt.b.get(this.c).a(f, f2)) && this.c != valueAt.a(f, f2);
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.f = constraintsChangedListener;
    }

    public int stateGetConstraintID(int i, int i2, int i3) {
        return updateConstraints(-1, i, i2, i3);
    }

    public int convertToConstraintSet(int i, int i2, float f, float f2) {
        a aVar = this.d.get(i2);
        if (aVar == null) {
            return i2;
        }
        if (f != -1.0f && f2 != -1.0f) {
            b bVar = null;
            Iterator<b> it = aVar.b.iterator();
            while (it.hasNext()) {
                b next = it.next();
                if (next.a(f, f2)) {
                    if (i == next.e) {
                        return i;
                    }
                    bVar = next;
                }
            }
            if (bVar != null) {
                return bVar.e;
            }
            return aVar.c;
        } else if (aVar.c == i) {
            return i;
        } else {
            Iterator<b> it2 = aVar.b.iterator();
            while (it2.hasNext()) {
                if (i == it2.next().e) {
                    return i;
                }
            }
            return aVar.c;
        }
    }

    public int updateConstraints(int i, int i2, float f, float f2) {
        a aVar;
        int a2;
        if (i == i2) {
            if (i2 == -1) {
                aVar = this.d.valueAt(0);
            } else {
                aVar = this.d.get(this.b);
            }
            if (aVar == null) {
                return -1;
            }
            return ((this.c == -1 || !aVar.b.get(i).a(f, f2)) && i != (a2 = aVar.a(f, f2))) ? a2 == -1 ? aVar.c : aVar.b.get(a2).e : i;
        }
        a aVar2 = this.d.get(i2);
        if (aVar2 == null) {
            return -1;
        }
        int a3 = aVar2.a(f, f2);
        return a3 == -1 ? aVar2.c : aVar2.b.get(a3).e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class a {
        int a;
        ArrayList<b> b = new ArrayList<>();
        int c;
        boolean d;

        public a(Context context, XmlPullParser xmlPullParser) {
            this.c = -1;
            this.d = false;
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
                        this.d = true;
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
        boolean f;

        public b(Context context, XmlPullParser xmlPullParser) {
            this.a = Float.NaN;
            this.b = Float.NaN;
            this.c = Float.NaN;
            this.d = Float.NaN;
            this.e = -1;
            this.f = false;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.Variant);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.Variant_constraints) {
                    this.e = obtainStyledAttributes.getResourceId(index, this.e);
                    String resourceTypeName = context.getResources().getResourceTypeName(this.e);
                    context.getResources().getResourceName(this.e);
                    if ("layout".equals(resourceTypeName)) {
                        this.f = true;
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
}
