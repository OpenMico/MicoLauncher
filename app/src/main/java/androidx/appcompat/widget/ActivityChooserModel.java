package androidx.appcompat.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.xiaomi.idm.api.IDMServer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ActivityChooserModel extends DataSetObservable {
    static final String a = "ActivityChooserModel";
    private static final Object e = new Object();
    private static final Map<String, ActivityChooserModel> f = new HashMap();
    final Context b;
    final String c;
    private Intent j;
    private OnChooseActivityListener p;
    private final Object g = new Object();
    private final List<ActivityResolveInfo> h = new ArrayList();
    private final List<HistoricalRecord> i = new ArrayList();
    private ActivitySorter k = new a();
    private int l = 50;
    boolean d = true;
    private boolean m = false;
    private boolean n = true;
    private boolean o = false;

    /* loaded from: classes.dex */
    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    /* loaded from: classes.dex */
    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    /* loaded from: classes.dex */
    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    public static ActivityChooserModel a(Context context, String str) {
        ActivityChooserModel activityChooserModel;
        synchronized (e) {
            activityChooserModel = f.get(str);
            if (activityChooserModel == null) {
                activityChooserModel = new ActivityChooserModel(context, str);
                f.put(str, activityChooserModel);
            }
        }
        return activityChooserModel;
    }

    private ActivityChooserModel(Context context, String str) {
        this.b = context.getApplicationContext();
        if (TextUtils.isEmpty(str) || str.endsWith(".xml")) {
            this.c = str;
            return;
        }
        this.c = str + ".xml";
    }

    public void a(Intent intent) {
        synchronized (this.g) {
            if (this.j != intent) {
                this.j = intent;
                this.o = true;
                e();
            }
        }
    }

    public int a() {
        int size;
        synchronized (this.g) {
            e();
            size = this.h.size();
        }
        return size;
    }

    public ResolveInfo a(int i) {
        ResolveInfo resolveInfo;
        synchronized (this.g) {
            e();
            resolveInfo = this.h.get(i).resolveInfo;
        }
        return resolveInfo;
    }

    public int a(ResolveInfo resolveInfo) {
        synchronized (this.g) {
            e();
            List<ActivityResolveInfo> list = this.h;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).resolveInfo == resolveInfo) {
                    return i;
                }
            }
            return -1;
        }
    }

    public Intent b(int i) {
        synchronized (this.g) {
            if (this.j == null) {
                return null;
            }
            e();
            ActivityResolveInfo activityResolveInfo = this.h.get(i);
            ComponentName componentName = new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
            Intent intent = new Intent(this.j);
            intent.setComponent(componentName);
            if (this.p != null) {
                if (this.p.onChooseActivity(this, new Intent(intent))) {
                    return null;
                }
            }
            a(new HistoricalRecord(componentName, System.currentTimeMillis(), 1.0f));
            return intent;
        }
    }

    public void a(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.g) {
            this.p = onChooseActivityListener;
        }
    }

    public ResolveInfo b() {
        synchronized (this.g) {
            e();
            if (this.h.isEmpty()) {
                return null;
            }
            return this.h.get(0).resolveInfo;
        }
    }

    public void c(int i) {
        synchronized (this.g) {
            e();
            ActivityResolveInfo activityResolveInfo = this.h.get(i);
            ActivityResolveInfo activityResolveInfo2 = this.h.get(0);
            a(new HistoricalRecord(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), System.currentTimeMillis(), activityResolveInfo2 != null ? (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f : 1.0f));
        }
    }

    private void d() {
        if (!this.m) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.n) {
            this.n = false;
            if (!TextUtils.isEmpty(this.c)) {
                new b().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ArrayList(this.i), this.c);
            }
        }
    }

    public int c() {
        int size;
        synchronized (this.g) {
            e();
            size = this.i.size();
        }
        return size;
    }

    private void e() {
        boolean g = g() | h();
        i();
        if (g) {
            f();
            notifyChanged();
        }
    }

    private boolean f() {
        if (this.k == null || this.j == null || this.h.isEmpty() || this.i.isEmpty()) {
            return false;
        }
        this.k.sort(this.j, this.h, Collections.unmodifiableList(this.i));
        return true;
    }

    private boolean g() {
        if (!this.o || this.j == null) {
            return false;
        }
        this.o = false;
        this.h.clear();
        List<ResolveInfo> queryIntentActivities = this.b.getPackageManager().queryIntentActivities(this.j, 0);
        int size = queryIntentActivities.size();
        for (int i = 0; i < size; i++) {
            this.h.add(new ActivityResolveInfo(queryIntentActivities.get(i)));
        }
        return true;
    }

    private boolean h() {
        if (!this.d || !this.n || TextUtils.isEmpty(this.c)) {
            return false;
        }
        this.d = false;
        this.m = true;
        j();
        return true;
    }

    private boolean a(HistoricalRecord historicalRecord) {
        boolean add = this.i.add(historicalRecord);
        if (add) {
            this.n = true;
            i();
            d();
            f();
            notifyChanged();
        }
        return add;
    }

    private void i() {
        int size = this.i.size() - this.l;
        if (size > 0) {
            this.n = true;
            for (int i = 0; i < size; i++) {
                this.i.remove(0);
            }
        }
    }

    /* loaded from: classes.dex */
    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String str, long j, float f) {
            this(ComponentName.unflattenFromString(str), j, f);
        }

        public HistoricalRecord(ComponentName componentName, long j, float f) {
            this.activity = componentName;
            this.time = j;
            this.weight = f;
        }

        public int hashCode() {
            ComponentName componentName = this.activity;
            int hashCode = componentName == null ? 0 : componentName.hashCode();
            long j = this.time;
            return ((((hashCode + 31) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord) obj;
            ComponentName componentName = this.activity;
            if (componentName == null) {
                if (historicalRecord.activity != null) {
                    return false;
                }
            } else if (!componentName.equals(historicalRecord.activity)) {
                return false;
            }
            return this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight);
        }

        public String toString() {
            return "[; activity:" + this.activity + "; time:" + this.time + "; weight:" + new BigDecimal(this.weight) + "]";
        }
    }

    /* loaded from: classes.dex */
    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight);
        }

        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() {
            return "[resolveInfo:" + this.resolveInfo.toString() + "; weight:" + new BigDecimal(this.weight) + "]";
        }
    }

    /* loaded from: classes.dex */
    private static final class a implements ActivitySorter {
        private final Map<ComponentName, ActivityResolveInfo> a = new HashMap();

        a() {
        }

        @Override // androidx.appcompat.widget.ActivityChooserModel.ActivitySorter
        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Map<ComponentName, ActivityResolveInfo> map = this.a;
            map.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ActivityResolveInfo activityResolveInfo = list.get(i);
                activityResolveInfo.weight = 0.0f;
                map.put(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), activityResolveInfo);
            }
            float f = 1.0f;
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                HistoricalRecord historicalRecord = list2.get(size2);
                ActivityResolveInfo activityResolveInfo2 = map.get(historicalRecord.activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight += historicalRecord.weight * f;
                    f *= 0.95f;
                }
            }
            Collections.sort(list);
        }
    }

    private void j() {
        FileInputStream fileInputStream;
        XmlPullParser newPullParser;
        try {
            try {
                fileInputStream = this.b.openFileInput(this.c);
                try {
                    try {
                        newPullParser = Xml.newPullParser();
                        newPullParser.setInput(fileInputStream, "UTF-8");
                        for (int i = 0; i != 1 && i != 2; i = newPullParser.next()) {
                        }
                    } catch (IOException e2) {
                        String str = a;
                        Log.e(str, "Error reading historical recrod file: " + this.c, e2);
                        if (fileInputStream == null) {
                            return;
                        }
                    }
                } catch (XmlPullParserException e3) {
                    String str2 = a;
                    Log.e(str2, "Error reading historical recrod file: " + this.c, e3);
                    if (fileInputStream == null) {
                        return;
                    }
                }
                if ("historical-records".equals(newPullParser.getName())) {
                    List<HistoricalRecord> list = this.i;
                    list.clear();
                    while (true) {
                        int next = newPullParser.next();
                        if (next == 1) {
                            if (fileInputStream == null) {
                                return;
                            }
                        } else if (!(next == 3 || next == 4)) {
                            if ("historical-record".equals(newPullParser.getName())) {
                                list.add(new HistoricalRecord(newPullParser.getAttributeValue(null, IDMServer.PERSIST_TYPE_ACTIVITY), Long.parseLong(newPullParser.getAttributeValue(null, "time")), Float.parseFloat(newPullParser.getAttributeValue(null, "weight"))));
                            } else {
                                throw new XmlPullParserException("Share records file not well-formed.");
                            }
                        }
                    }
                } else {
                    throw new XmlPullParserException("Share records file does not start with historical-records tag.");
                }
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                    }
                }
            }
        } catch (FileNotFoundException unused2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public final class b extends AsyncTask<Object, Void, Void> {
        b() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x006f, code lost:
            if (r12 != null) goto L_0x0071;
         */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0071, code lost:
            r12.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0096, code lost:
            if (r12 == null) goto L_0x00dd;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x00b8, code lost:
            if (r12 == null) goto L_0x00dd;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x00da, code lost:
            if (r12 == null) goto L_0x00dd;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x00dd, code lost:
            return null;
         */
        /* renamed from: a */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Void doInBackground(java.lang.Object... r12) {
            /*
                Method dump skipped, instructions count: 256
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserModel.b.doInBackground(java.lang.Object[]):java.lang.Void");
        }
    }
}
