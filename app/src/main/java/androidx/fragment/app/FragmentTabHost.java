package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes.dex */
public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private final ArrayList<c> a = new ArrayList<>();
    private FrameLayout b;
    private Context c;
    private FragmentManager d;
    private int e;
    private TabHost.OnTabChangeListener f;
    private c g;
    private boolean h;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class c {
        @NonNull
        final String a;
        @NonNull
        final Class<?> b;
        @Nullable
        final Bundle c;
        Fragment d;

        c(@NonNull String str, @NonNull Class<?> cls, @Nullable Bundle bundle) {
            this.a = str;
            this.b = cls;
            this.c = bundle;
        }
    }

    /* loaded from: classes.dex */
    static class a implements TabHost.TabContentFactory {
        private final Context a;

        public a(Context context) {
            this.a = context;
        }

        @Override // android.widget.TabHost.TabContentFactory
        public View createTabContent(String str) {
            View view = new View(this.a);
            view.setMinimumWidth(0);
            view.setMinimumHeight(0);
            return view;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class b extends View.BaseSavedState {
        public static final Parcelable.Creator<b> CREATOR = new Parcelable.Creator<b>() { // from class: androidx.fragment.app.FragmentTabHost.b.1
            /* renamed from: a */
            public b createFromParcel(Parcel parcel) {
                return new b(parcel);
            }

            /* renamed from: a */
            public b[] newArray(int i) {
                return new b[i];
            }
        };
        String a;

        b(Parcelable parcelable) {
            super(parcelable);
        }

        b(Parcel parcel) {
            super(parcel);
            this.a = parcel.readString();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a);
        }

        @NonNull
        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.a + "}";
        }
    }

    @Deprecated
    public FragmentTabHost(@NonNull Context context) {
        super(context, null);
        a(context, (AttributeSet) null);
    }

    @Deprecated
    public FragmentTabHost(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842995}, 0, 0);
        this.e = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
    }

    private void a(Context context) {
        if (findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setId(16908305);
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(0, 0, 0.0f));
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.b = frameLayout2;
            this.b.setId(this.e);
            linearLayout.addView(frameLayout2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    @Override // android.widget.TabHost
    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    @Deprecated
    public void setup(@NonNull Context context, @NonNull FragmentManager fragmentManager) {
        a(context);
        super.setup();
        this.c = context;
        this.d = fragmentManager;
        a();
    }

    @Deprecated
    public void setup(@NonNull Context context, @NonNull FragmentManager fragmentManager, int i) {
        a(context);
        super.setup();
        this.c = context;
        this.d = fragmentManager;
        this.e = i;
        a();
        this.b.setId(i);
        if (getId() == -1) {
            setId(16908306);
        }
    }

    private void a() {
        if (this.b == null) {
            this.b = (FrameLayout) findViewById(this.e);
            if (this.b == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.e);
            }
        }
    }

    @Override // android.widget.TabHost
    @Deprecated
    public void setOnTabChangedListener(@Nullable TabHost.OnTabChangeListener onTabChangeListener) {
        this.f = onTabChangeListener;
    }

    @Deprecated
    public void addTab(@NonNull TabHost.TabSpec tabSpec, @NonNull Class<?> cls, @Nullable Bundle bundle) {
        tabSpec.setContent(new a(this.c));
        String tag = tabSpec.getTag();
        c cVar = new c(tag, cls, bundle);
        if (this.h) {
            cVar.d = this.d.findFragmentByTag(tag);
            if (cVar.d != null && !cVar.d.isDetached()) {
                FragmentTransaction beginTransaction = this.d.beginTransaction();
                beginTransaction.detach(cVar.d);
                beginTransaction.commit();
            }
        }
        this.a.add(cVar);
        addTab(tabSpec);
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        int size = this.a.size();
        FragmentTransaction fragmentTransaction = null;
        for (int i = 0; i < size; i++) {
            c cVar = this.a.get(i);
            cVar.d = this.d.findFragmentByTag(cVar.a);
            if (cVar.d != null && !cVar.d.isDetached()) {
                if (cVar.a.equals(currentTabTag)) {
                    this.g = cVar;
                } else {
                    if (fragmentTransaction == null) {
                        fragmentTransaction = this.d.beginTransaction();
                    }
                    fragmentTransaction.detach(cVar.d);
                }
            }
        }
        this.h = true;
        FragmentTransaction a2 = a(currentTabTag, fragmentTransaction);
        if (a2 != null) {
            a2.commit();
            this.d.executePendingTransactions();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.h = false;
    }

    @Override // android.view.View
    @NonNull
    @Deprecated
    protected Parcelable onSaveInstanceState() {
        b bVar = new b(super.onSaveInstanceState());
        bVar.a = getCurrentTabTag();
        return bVar;
    }

    @Override // android.view.View
    @Deprecated
    protected void onRestoreInstanceState(@SuppressLint({"UnknownNullness"}) Parcelable parcelable) {
        if (!(parcelable instanceof b)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        b bVar = (b) parcelable;
        super.onRestoreInstanceState(bVar.getSuperState());
        setCurrentTabByTag(bVar.a);
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    @Deprecated
    public void onTabChanged(@Nullable String str) {
        FragmentTransaction a2;
        if (this.h && (a2 = a(str, (FragmentTransaction) null)) != null) {
            a2.commit();
        }
        TabHost.OnTabChangeListener onTabChangeListener = this.f;
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(str);
        }
    }

    @Nullable
    private FragmentTransaction a(@Nullable String str, @Nullable FragmentTransaction fragmentTransaction) {
        c a2 = a(str);
        if (this.g != a2) {
            if (fragmentTransaction == null) {
                fragmentTransaction = this.d.beginTransaction();
            }
            c cVar = this.g;
            if (!(cVar == null || cVar.d == null)) {
                fragmentTransaction.detach(this.g.d);
            }
            if (a2 != null) {
                if (a2.d == null) {
                    a2.d = this.d.getFragmentFactory().instantiate(this.c.getClassLoader(), a2.b.getName());
                    a2.d.setArguments(a2.c);
                    fragmentTransaction.add(this.e, a2.d, a2.a);
                } else {
                    fragmentTransaction.attach(a2.d);
                }
            }
            this.g = a2;
        }
        return fragmentTransaction;
    }

    @Nullable
    private c a(String str) {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            c cVar = this.a.get(i);
            if (cVar.a.equals(str)) {
                return cVar;
            }
        }
        return null;
    }
}
