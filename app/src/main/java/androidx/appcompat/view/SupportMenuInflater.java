package androidx.appcompat.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class SupportMenuInflater extends MenuInflater {
    static final Class<?>[] a = {Context.class};
    static final Class<?>[] b = a;
    final Object[] c;
    final Object[] d;
    Context e;
    private Object f;

    public SupportMenuInflater(Context context) {
        super(context);
        this.e = context;
        this.c = new Object[]{context};
        this.d = this.c;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser] */
    @Override // android.view.MenuInflater
    public void inflate(@LayoutRes int i, Menu menu) {
        XmlResourceParser xmlResourceParser = menu instanceof SupportMenu;
        if (xmlResourceParser == 0) {
            super.inflate(i, menu);
            return;
        }
        try {
            xmlResourceParser = 0;
            try {
                try {
                    xmlResourceParser = this.e.getResources().getLayout(i);
                    a(xmlResourceParser, Xml.asAttributeSet(xmlResourceParser), menu);
                } catch (XmlPullParserException e) {
                    throw new InflateException("Error inflating menu XML", e);
                }
            } catch (IOException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } finally {
            if (xmlResourceParser != 0) {
                xmlResourceParser.close();
            }
        }
    }

    private void a(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IOException {
        b bVar = new b(menu);
        int eventType = xmlPullParser.getEventType();
        while (true) {
            if (eventType != 2) {
                eventType = xmlPullParser.next();
                if (eventType == 1) {
                    break;
                }
            } else {
                String name = xmlPullParser.getName();
                if (name.equals("menu")) {
                    eventType = xmlPullParser.next();
                } else {
                    throw new RuntimeException("Expecting menu, got " + name);
                }
            }
        }
        String str = null;
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            switch (eventType) {
                case 1:
                    throw new RuntimeException("Unexpected end of document");
                case 2:
                    if (!z2) {
                        String name2 = xmlPullParser.getName();
                        if (!name2.equals("group")) {
                            if (!name2.equals("item")) {
                                if (!name2.equals("menu")) {
                                    z2 = true;
                                    str = name2;
                                    break;
                                } else {
                                    a(xmlPullParser, attributeSet, bVar.c());
                                    break;
                                }
                            } else {
                                bVar.b(attributeSet);
                                break;
                            }
                        } else {
                            bVar.a(attributeSet);
                            break;
                        }
                    } else {
                        break;
                    }
                case 3:
                    String name3 = xmlPullParser.getName();
                    if (!z2 || !name3.equals(str)) {
                        if (!name3.equals("group")) {
                            if (!name3.equals("item")) {
                                if (!name3.equals("menu")) {
                                    break;
                                } else {
                                    z = true;
                                    break;
                                }
                            } else if (!bVar.d()) {
                                if (bVar.a != null && bVar.a.hasSubMenu()) {
                                    bVar.c();
                                    break;
                                } else {
                                    bVar.b();
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            bVar.a();
                            break;
                        }
                    } else {
                        str = null;
                        z2 = false;
                        break;
                    }
            }
            eventType = xmlPullParser.next();
        }
    }

    Object a() {
        if (this.f == null) {
            this.f = a(this.e);
        }
        return this.f;
    }

    private Object a(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? a(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    /* loaded from: classes.dex */
    public static class a implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] a = {MenuItem.class};
        private Object b;
        private Method c;

        public a(Object obj, String str) {
            this.b = obj;
            Class<?> cls = obj.getClass();
            try {
                this.c = cls.getMethod(str, a);
            } catch (Exception e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.c.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.c.invoke(this.b, menuItem)).booleanValue();
                }
                this.c.invoke(this.b, menuItem);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* loaded from: classes.dex */
    public class b {
        private String A;
        private String B;
        private CharSequence C;
        private CharSequence D;
        private ColorStateList E = null;
        private PorterDuff.Mode F = null;
        ActionProvider a;
        private Menu c;
        private int d;
        private int e;
        private int f;
        private int g;
        private boolean h;
        private boolean i;
        private boolean j;
        private int k;
        private int l;
        private CharSequence m;
        private CharSequence n;
        private int o;
        private char p;
        private int q;
        private char r;
        private int s;
        private int t;
        private boolean u;
        private boolean v;
        private boolean w;
        private int x;
        private int y;
        private String z;

        public b(Menu menu) {
            SupportMenuInflater.this = r1;
            this.c = menu;
            a();
        }

        public void a() {
            this.d = 0;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.h = true;
            this.i = true;
        }

        public void a(AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = SupportMenuInflater.this.e.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
            this.d = obtainStyledAttributes.getResourceId(R.styleable.MenuGroup_android_id, 0);
            this.e = obtainStyledAttributes.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
            this.f = obtainStyledAttributes.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
            this.g = obtainStyledAttributes.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.h = obtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_visible, true);
            this.i = obtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_enabled, true);
            obtainStyledAttributes.recycle();
        }

        public void b(AttributeSet attributeSet) {
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(SupportMenuInflater.this.e, attributeSet, R.styleable.MenuItem);
            this.k = obtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_id, 0);
            this.l = (obtainStyledAttributes.getInt(R.styleable.MenuItem_android_menuCategory, this.e) & (-65536)) | (obtainStyledAttributes.getInt(R.styleable.MenuItem_android_orderInCategory, this.f) & 65535);
            this.m = obtainStyledAttributes.getText(R.styleable.MenuItem_android_title);
            this.n = obtainStyledAttributes.getText(R.styleable.MenuItem_android_titleCondensed);
            this.o = obtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_icon, 0);
            this.p = a(obtainStyledAttributes.getString(R.styleable.MenuItem_android_alphabeticShortcut));
            this.q = obtainStyledAttributes.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
            this.r = a(obtainStyledAttributes.getString(R.styleable.MenuItem_android_numericShortcut));
            this.s = obtainStyledAttributes.getInt(R.styleable.MenuItem_numericModifiers, 4096);
            if (obtainStyledAttributes.hasValue(R.styleable.MenuItem_android_checkable)) {
                this.t = obtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checkable, false) ? 1 : 0;
            } else {
                this.t = this.g;
            }
            this.u = obtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checked, false);
            this.v = obtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_visible, this.h);
            this.w = obtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_enabled, this.i);
            this.x = obtainStyledAttributes.getInt(R.styleable.MenuItem_showAsAction, -1);
            this.B = obtainStyledAttributes.getString(R.styleable.MenuItem_android_onClick);
            this.y = obtainStyledAttributes.getResourceId(R.styleable.MenuItem_actionLayout, 0);
            this.z = obtainStyledAttributes.getString(R.styleable.MenuItem_actionViewClass);
            this.A = obtainStyledAttributes.getString(R.styleable.MenuItem_actionProviderClass);
            boolean z = this.A != null;
            if (z && this.y == 0 && this.z == null) {
                this.a = (ActionProvider) a(this.A, SupportMenuInflater.b, SupportMenuInflater.this.d);
            } else {
                if (z) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.a = null;
            }
            this.C = obtainStyledAttributes.getText(R.styleable.MenuItem_contentDescription);
            this.D = obtainStyledAttributes.getText(R.styleable.MenuItem_tooltipText);
            if (obtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTintMode)) {
                this.F = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.MenuItem_iconTintMode, -1), this.F);
            } else {
                this.F = null;
            }
            if (obtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTint)) {
                this.E = obtainStyledAttributes.getColorStateList(R.styleable.MenuItem_iconTint);
            } else {
                this.E = null;
            }
            obtainStyledAttributes.recycle();
            this.j = false;
        }

        private char a(String str) {
            if (str == null) {
                return (char) 0;
            }
            return str.charAt(0);
        }

        private void a(MenuItem menuItem) {
            boolean z = false;
            menuItem.setChecked(this.u).setVisible(this.v).setEnabled(this.w).setCheckable(this.t >= 1).setTitleCondensed(this.n).setIcon(this.o);
            int i = this.x;
            if (i >= 0) {
                menuItem.setShowAsAction(i);
            }
            if (this.B != null) {
                if (!SupportMenuInflater.this.e.isRestricted()) {
                    menuItem.setOnMenuItemClickListener(new a(SupportMenuInflater.this.a(), this.B));
                } else {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
            }
            if (this.t >= 2) {
                if (menuItem instanceof MenuItemImpl) {
                    ((MenuItemImpl) menuItem).setExclusiveCheckable(true);
                } else if (menuItem instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS) menuItem).setExclusiveCheckable(true);
                }
            }
            String str = this.z;
            if (str != null) {
                menuItem.setActionView((View) a(str, SupportMenuInflater.a, SupportMenuInflater.this.c));
                z = true;
            }
            int i2 = this.y;
            if (i2 > 0) {
                if (!z) {
                    menuItem.setActionView(i2);
                } else {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            ActionProvider actionProvider = this.a;
            if (actionProvider != null) {
                MenuItemCompat.setActionProvider(menuItem, actionProvider);
            }
            MenuItemCompat.setContentDescription(menuItem, this.C);
            MenuItemCompat.setTooltipText(menuItem, this.D);
            MenuItemCompat.setAlphabeticShortcut(menuItem, this.p, this.q);
            MenuItemCompat.setNumericShortcut(menuItem, this.r, this.s);
            PorterDuff.Mode mode = this.F;
            if (mode != null) {
                MenuItemCompat.setIconTintMode(menuItem, mode);
            }
            ColorStateList colorStateList = this.E;
            if (colorStateList != null) {
                MenuItemCompat.setIconTintList(menuItem, colorStateList);
            }
        }

        public void b() {
            this.j = true;
            a(this.c.add(this.d, this.k, this.l, this.m));
        }

        public SubMenu c() {
            this.j = true;
            SubMenu addSubMenu = this.c.addSubMenu(this.d, this.k, this.l, this.m);
            a(addSubMenu.getItem());
            return addSubMenu;
        }

        public boolean d() {
            return this.j;
        }

        private <T> T a(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = Class.forName(str, false, SupportMenuInflater.this.e.getClassLoader()).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
                return null;
            }
        }
    }
}
