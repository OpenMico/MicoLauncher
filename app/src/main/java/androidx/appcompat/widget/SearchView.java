package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.core.view.ViewCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.customview.view.AbsSavedState;
import com.alibaba.fastjson.asm.Opcodes;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class SearchView extends LinearLayoutCompat implements CollapsibleActionView {
    static final a i;
    private OnQueryTextListener A;
    private OnCloseListener B;
    private OnSuggestionListener C;
    private View.OnClickListener D;
    private boolean E;
    private boolean F;
    private boolean G;
    private CharSequence H;
    private boolean I;
    private boolean J;
    private int K;
    private boolean L;
    private CharSequence M;
    private CharSequence N;
    private boolean O;
    private int P;
    private Bundle Q;
    private final Runnable R;
    private Runnable S;
    private final WeakHashMap<String, Drawable.ConstantState> T;
    private final View.OnClickListener U;
    private final TextView.OnEditorActionListener V;
    private final AdapterView.OnItemClickListener W;
    final SearchAutoComplete a;
    private final AdapterView.OnItemSelectedListener aa;
    private TextWatcher ab;
    final ImageView b;
    final ImageView c;
    final ImageView d;
    final ImageView e;
    View.OnFocusChangeListener f;
    CursorAdapter g;
    SearchableInfo h;
    View.OnKeyListener j;
    private final View k;
    private final View l;
    private final View m;
    private final View n;
    private c o;
    private Rect p;
    private Rect q;
    private int[] r;
    private int[] s;
    private final ImageView t;
    private final Drawable u;
    private final int v;
    private final int w;
    private final Intent x;
    private final Intent y;
    private final CharSequence z;

    /* loaded from: classes.dex */
    public interface OnCloseListener {
        boolean onClose();
    }

    /* loaded from: classes.dex */
    public interface OnQueryTextListener {
        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    /* loaded from: classes.dex */
    public interface OnSuggestionListener {
        boolean onSuggestionClick(int i);

        boolean onSuggestionSelect(int i);
    }

    static {
        i = Build.VERSION.SDK_INT < 29 ? new a() : null;
    }

    public SearchView(@NonNull Context context) {
        this(context, null);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchViewStyle);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.p = new Rect();
        this.q = new Rect();
        this.r = new int[2];
        this.s = new int[2];
        this.R = new Runnable() { // from class: androidx.appcompat.widget.SearchView.1
            @Override // java.lang.Runnable
            public void run() {
                SearchView.this.a();
            }
        };
        this.S = new Runnable() { // from class: androidx.appcompat.widget.SearchView.3
            @Override // java.lang.Runnable
            public void run() {
                if (SearchView.this.g instanceof p) {
                    SearchView.this.g.changeCursor(null);
                }
            }
        };
        this.T = new WeakHashMap<>();
        this.U = new View.OnClickListener() { // from class: androidx.appcompat.widget.SearchView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view == SearchView.this.b) {
                    SearchView.this.d();
                } else if (view == SearchView.this.d) {
                    SearchView.this.c();
                } else if (view == SearchView.this.c) {
                    SearchView.this.b();
                } else if (view == SearchView.this.e) {
                    SearchView.this.e();
                } else if (view == SearchView.this.a) {
                    SearchView.this.h();
                }
            }
        };
        this.j = new View.OnKeyListener() { // from class: androidx.appcompat.widget.SearchView.7
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View view, int i3, KeyEvent keyEvent) {
                if (SearchView.this.h == null) {
                    return false;
                }
                if (SearchView.this.a.isPopupShowing() && SearchView.this.a.getListSelection() != -1) {
                    return SearchView.this.a(view, i3, keyEvent);
                }
                if (SearchView.this.a.a() || !keyEvent.hasNoModifiers() || keyEvent.getAction() != 1 || i3 != 66) {
                    return false;
                }
                view.cancelLongPress();
                SearchView searchView = SearchView.this;
                searchView.a(0, (String) null, searchView.a.getText().toString());
                return true;
            }
        };
        this.V = new TextView.OnEditorActionListener() { // from class: androidx.appcompat.widget.SearchView.8
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i3, KeyEvent keyEvent) {
                SearchView.this.b();
                return true;
            }
        };
        this.W = new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.widget.SearchView.9
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                SearchView.this.a(i3, 0, (String) null);
            }
        };
        this.aa = new AdapterView.OnItemSelectedListener() { // from class: androidx.appcompat.widget.SearchView.10
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i3, long j) {
                SearchView.this.c(i3);
            }
        };
        this.ab = new TextWatcher() { // from class: androidx.appcompat.widget.SearchView.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                SearchView.this.b(charSequence);
            }
        };
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.SearchView, i2, 0);
        LayoutInflater.from(context).inflate(obtainStyledAttributes.getResourceId(R.styleable.SearchView_layout, R.layout.abc_search_view), (ViewGroup) this, true);
        this.a = (SearchAutoComplete) findViewById(R.id.search_src_text);
        this.a.setSearchView(this);
        this.k = findViewById(R.id.search_edit_frame);
        this.l = findViewById(R.id.search_plate);
        this.m = findViewById(R.id.submit_area);
        this.b = (ImageView) findViewById(R.id.search_button);
        this.c = (ImageView) findViewById(R.id.search_go_btn);
        this.d = (ImageView) findViewById(R.id.search_close_btn);
        this.e = (ImageView) findViewById(R.id.search_voice_btn);
        this.t = (ImageView) findViewById(R.id.search_mag_icon);
        ViewCompat.setBackground(this.l, obtainStyledAttributes.getDrawable(R.styleable.SearchView_queryBackground));
        ViewCompat.setBackground(this.m, obtainStyledAttributes.getDrawable(R.styleable.SearchView_submitBackground));
        this.b.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_searchIcon));
        this.c.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_goIcon));
        this.d.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_closeIcon));
        this.e.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_voiceIcon));
        this.t.setImageDrawable(obtainStyledAttributes.getDrawable(R.styleable.SearchView_searchIcon));
        this.u = obtainStyledAttributes.getDrawable(R.styleable.SearchView_searchHintIcon);
        TooltipCompat.setTooltipText(this.b, getResources().getString(R.string.abc_searchview_description_search));
        this.v = obtainStyledAttributes.getResourceId(R.styleable.SearchView_suggestionRowLayout, R.layout.abc_search_dropdown_item_icons_2line);
        this.w = obtainStyledAttributes.getResourceId(R.styleable.SearchView_commitIcon, 0);
        this.b.setOnClickListener(this.U);
        this.d.setOnClickListener(this.U);
        this.c.setOnClickListener(this.U);
        this.e.setOnClickListener(this.U);
        this.a.setOnClickListener(this.U);
        this.a.addTextChangedListener(this.ab);
        this.a.setOnEditorActionListener(this.V);
        this.a.setOnItemClickListener(this.W);
        this.a.setOnItemSelectedListener(this.aa);
        this.a.setOnKeyListener(this.j);
        this.a.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.appcompat.widget.SearchView.4
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (SearchView.this.f != null) {
                    SearchView.this.f.onFocusChange(SearchView.this, z);
                }
            }
        });
        setIconifiedByDefault(obtainStyledAttributes.getBoolean(R.styleable.SearchView_iconifiedByDefault, true));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1);
        if (dimensionPixelSize != -1) {
            setMaxWidth(dimensionPixelSize);
        }
        this.z = obtainStyledAttributes.getText(R.styleable.SearchView_defaultQueryHint);
        this.H = obtainStyledAttributes.getText(R.styleable.SearchView_queryHint);
        int i3 = obtainStyledAttributes.getInt(R.styleable.SearchView_android_imeOptions, -1);
        if (i3 != -1) {
            setImeOptions(i3);
        }
        int i4 = obtainStyledAttributes.getInt(R.styleable.SearchView_android_inputType, -1);
        if (i4 != -1) {
            setInputType(i4);
        }
        setFocusable(obtainStyledAttributes.getBoolean(R.styleable.SearchView_android_focusable, true));
        obtainStyledAttributes.recycle();
        this.x = new Intent("android.speech.action.WEB_SEARCH");
        this.x.addFlags(268435456);
        this.x.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.y = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.y.addFlags(268435456);
        this.n = findViewById(this.a.getDropDownAnchor());
        View view = this.n;
        if (view != null) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.appcompat.widget.SearchView.5
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view2, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
                    SearchView.this.g();
                }
            });
        }
        a(this.E);
        n();
    }

    public int getSuggestionRowLayout() {
        return this.v;
    }

    public int getSuggestionCommitIconResId() {
        return this.w;
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.h = searchableInfo;
        if (this.h != null) {
            o();
            n();
        }
        this.L = i();
        if (this.L) {
            this.a.setPrivateImeOptions("nm");
        }
        a(isIconified());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setAppSearchData(Bundle bundle) {
        this.Q = bundle;
    }

    public void setImeOptions(int i2) {
        this.a.setImeOptions(i2);
    }

    public int getImeOptions() {
        return this.a.getImeOptions();
    }

    public void setInputType(int i2) {
        this.a.setInputType(i2);
    }

    public int getInputType() {
        return this.a.getInputType();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean requestFocus(int i2, Rect rect) {
        if (this.J || !isFocusable()) {
            return false;
        }
        if (isIconified()) {
            return super.requestFocus(i2, rect);
        }
        boolean requestFocus = this.a.requestFocus(i2, rect);
        if (requestFocus) {
            a(false);
        }
        return requestFocus;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void clearFocus() {
        this.J = true;
        super.clearFocus();
        this.a.clearFocus();
        this.a.setImeVisibility(false);
        this.J = false;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.A = onQueryTextListener;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.B = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.f = onFocusChangeListener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.C = onSuggestionListener;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.D = onClickListener;
    }

    public CharSequence getQuery() {
        return this.a.getText();
    }

    public void setQuery(CharSequence charSequence, boolean z) {
        this.a.setText(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.a;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.N = charSequence;
        }
        if (z && !TextUtils.isEmpty(charSequence)) {
            b();
        }
    }

    public void setQueryHint(@Nullable CharSequence charSequence) {
        this.H = charSequence;
        n();
    }

    @Nullable
    public CharSequence getQueryHint() {
        CharSequence charSequence = this.H;
        if (charSequence != null) {
            return charSequence;
        }
        SearchableInfo searchableInfo = this.h;
        if (searchableInfo == null || searchableInfo.getHintId() == 0) {
            return this.z;
        }
        return getContext().getText(this.h.getHintId());
    }

    public void setIconifiedByDefault(boolean z) {
        if (this.E != z) {
            this.E = z;
            a(z);
            n();
        }
    }

    public boolean isIconfiedByDefault() {
        return this.E;
    }

    public void setIconified(boolean z) {
        if (z) {
            c();
        } else {
            d();
        }
    }

    public boolean isIconified() {
        return this.F;
    }

    public void setSubmitButtonEnabled(boolean z) {
        this.G = z;
        a(isIconified());
    }

    public boolean isSubmitButtonEnabled() {
        return this.G;
    }

    public void setQueryRefinementEnabled(boolean z) {
        this.I = z;
        CursorAdapter cursorAdapter = this.g;
        if (cursorAdapter instanceof p) {
            ((p) cursorAdapter).a(z ? 2 : 1);
        }
    }

    public boolean isQueryRefinementEnabled() {
        return this.I;
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.g = cursorAdapter;
        this.a.setAdapter(this.g);
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.g;
    }

    public void setMaxWidth(int i2) {
        this.K = i2;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.K;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    public void onMeasure(int i2, int i3) {
        int i4;
        if (isIconified()) {
            super.onMeasure(i2, i3);
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE) {
            int i5 = this.K;
            size = i5 > 0 ? Math.min(i5, size) : Math.min(getPreferredWidth(), size);
        } else if (mode == 0) {
            size = this.K;
            if (size <= 0) {
                size = getPreferredWidth();
            }
        } else if (mode == 1073741824 && (i4 = this.K) > 0) {
            size = Math.min(i4, size);
        }
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(getPreferredHeight(), size2);
        } else if (mode2 == 0) {
            size2 = getPreferredHeight();
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        if (z) {
            a(this.a, this.p);
            this.q.set(this.p.left, 0, this.p.right, i5 - i3);
            c cVar = this.o;
            if (cVar == null) {
                this.o = new c(this.q, this.p, this.a);
                setTouchDelegate(this.o);
                return;
            }
            cVar.a(this.q, this.p);
        }
    }

    private void a(View view, Rect rect) {
        view.getLocationInWindow(this.r);
        getLocationInWindow(this.s);
        int[] iArr = this.r;
        int i2 = iArr[1];
        int[] iArr2 = this.s;
        int i3 = i2 - iArr2[1];
        int i4 = iArr[0] - iArr2[0];
        rect.set(i4, i3, view.getWidth() + i4, view.getHeight() + i3);
    }

    private int getPreferredWidth() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width);
    }

    private int getPreferredHeight() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_height);
    }

    private void a(boolean z) {
        this.F = z;
        int i2 = 8;
        boolean z2 = false;
        int i3 = z ? 0 : 8;
        boolean z3 = !TextUtils.isEmpty(this.a.getText());
        this.b.setVisibility(i3);
        b(z3);
        this.k.setVisibility(z ? 8 : 0);
        if (this.t.getDrawable() != null && !this.E) {
            i2 = 0;
        }
        this.t.setVisibility(i2);
        l();
        if (!z3) {
            z2 = true;
        }
        c(z2);
        k();
    }

    private boolean i() {
        SearchableInfo searchableInfo = this.h;
        if (searchableInfo != null && searchableInfo.getVoiceSearchEnabled()) {
            Intent intent = null;
            if (this.h.getVoiceSearchLaunchWebSearch()) {
                intent = this.x;
            } else if (this.h.getVoiceSearchLaunchRecognizer()) {
                intent = this.y;
            }
            return (intent == null || getContext().getPackageManager().resolveActivity(intent, 65536) == null) ? false : true;
        }
        return false;
    }

    private boolean j() {
        return (this.G || this.L) && !isIconified();
    }

    private void b(boolean z) {
        this.c.setVisibility((!this.G || !j() || !hasFocus() || (!z && this.L)) ? 8 : 0);
    }

    private void k() {
        this.m.setVisibility((!j() || !(this.c.getVisibility() == 0 || this.e.getVisibility() == 0)) ? 8 : 0);
    }

    private void l() {
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(this.a.getText());
        int i2 = 0;
        if (!z2 && (!this.E || this.O)) {
            z = false;
        }
        ImageView imageView = this.d;
        if (!z) {
            i2 = 8;
        }
        imageView.setVisibility(i2);
        Drawable drawable = this.d.getDrawable();
        if (drawable != null) {
            drawable.setState(z2 ? ENABLED_STATE_SET : EMPTY_STATE_SET);
        }
    }

    private void m() {
        post(this.R);
    }

    void a() {
        int[] iArr = this.a.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable background = this.l.getBackground();
        if (background != null) {
            background.setState(iArr);
        }
        Drawable background2 = this.m.getBackground();
        if (background2 != null) {
            background2.setState(iArr);
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        removeCallbacks(this.R);
        post(this.S);
        super.onDetachedFromWindow();
    }

    public void a(CharSequence charSequence) {
        setQuery(charSequence);
    }

    boolean a(View view, int i2, KeyEvent keyEvent) {
        if (this.h != null && this.g != null && keyEvent.getAction() == 0 && keyEvent.hasNoModifiers()) {
            if (i2 == 66 || i2 == 84 || i2 == 61) {
                return a(this.a.getListSelection(), 0, (String) null);
            }
            if (i2 == 21 || i2 == 22) {
                this.a.setSelection(i2 == 21 ? 0 : this.a.length());
                this.a.setListSelection(0);
                this.a.clearListSelection();
                this.a.c();
                return true;
            } else if (i2 != 19 || this.a.getListSelection() == 0) {
                return false;
            }
        }
        return false;
    }

    private CharSequence c(CharSequence charSequence) {
        if (!this.E || this.u == null) {
            return charSequence;
        }
        int textSize = (int) (this.a.getTextSize() * 1.25d);
        this.u.setBounds(0, 0, textSize, textSize);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
        spannableStringBuilder.setSpan(new ImageSpan(this.u), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private void n() {
        CharSequence queryHint = getQueryHint();
        SearchAutoComplete searchAutoComplete = this.a;
        if (queryHint == null) {
            queryHint = "";
        }
        searchAutoComplete.setHint(c(queryHint));
    }

    private void o() {
        this.a.setThreshold(this.h.getSuggestThreshold());
        this.a.setImeOptions(this.h.getImeOptions());
        int inputType = this.h.getInputType();
        int i2 = 1;
        if ((inputType & 15) == 1) {
            inputType &= -65537;
            if (this.h.getSuggestAuthority() != null) {
                inputType = inputType | 65536 | 524288;
            }
        }
        this.a.setInputType(inputType);
        CursorAdapter cursorAdapter = this.g;
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(null);
        }
        if (this.h.getSuggestAuthority() != null) {
            this.g = new p(getContext(), this, this.h, this.T);
            this.a.setAdapter(this.g);
            p pVar = (p) this.g;
            if (this.I) {
                i2 = 2;
            }
            pVar.a(i2);
        }
    }

    private void c(boolean z) {
        int i2;
        if (!this.L || isIconified() || !z) {
            i2 = 8;
        } else {
            i2 = 0;
            this.c.setVisibility(8);
        }
        this.e.setVisibility(i2);
    }

    void b(CharSequence charSequence) {
        Editable text = this.a.getText();
        this.N = text;
        boolean z = true;
        boolean z2 = !TextUtils.isEmpty(text);
        b(z2);
        if (z2) {
            z = false;
        }
        c(z);
        l();
        k();
        if (this.A != null && !TextUtils.equals(charSequence, this.M)) {
            this.A.onQueryTextChange(charSequence.toString());
        }
        this.M = charSequence.toString();
    }

    void b() {
        Editable text = this.a.getText();
        if (text != null && TextUtils.getTrimmedLength(text) > 0) {
            OnQueryTextListener onQueryTextListener = this.A;
            if (onQueryTextListener == null || !onQueryTextListener.onQueryTextSubmit(text.toString())) {
                if (this.h != null) {
                    a(0, (String) null, text.toString());
                }
                this.a.setImeVisibility(false);
                p();
            }
        }
    }

    private void p() {
        this.a.dismissDropDown();
    }

    void c() {
        if (!TextUtils.isEmpty(this.a.getText())) {
            this.a.setText("");
            this.a.requestFocus();
            this.a.setImeVisibility(true);
        } else if (this.E) {
            OnCloseListener onCloseListener = this.B;
            if (onCloseListener == null || !onCloseListener.onClose()) {
                clearFocus();
                a(true);
            }
        }
    }

    void d() {
        a(false);
        this.a.requestFocus();
        this.a.setImeVisibility(true);
        View.OnClickListener onClickListener = this.D;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    void e() {
        SearchableInfo searchableInfo = this.h;
        if (searchableInfo != null) {
            try {
                if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                    getContext().startActivity(a(this.x, searchableInfo));
                } else if (searchableInfo.getVoiceSearchLaunchRecognizer()) {
                    getContext().startActivity(b(this.y, searchableInfo));
                }
            } catch (ActivityNotFoundException unused) {
                Log.w("SearchView", "Could not find voice search activity");
            }
        }
    }

    void f() {
        a(isIconified());
        m();
        if (this.a.hasFocus()) {
            h();
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        m();
    }

    @Override // androidx.appcompat.view.CollapsibleActionView
    public void onActionViewCollapsed() {
        setQuery("", false);
        clearFocus();
        a(true);
        this.a.setImeOptions(this.P);
        this.O = false;
    }

    @Override // androidx.appcompat.view.CollapsibleActionView
    public void onActionViewExpanded() {
        if (!this.O) {
            this.O = true;
            this.P = this.a.getImeOptions();
            this.a.setImeOptions(this.P | 33554432);
            this.a.setText("");
            setIconified(false);
        }
    }

    /* loaded from: classes.dex */
    public static class b extends AbsSavedState {
        public static final Parcelable.Creator<b> CREATOR = new Parcelable.ClassLoaderCreator<b>() { // from class: androidx.appcompat.widget.SearchView.b.1
            /* renamed from: a */
            public b createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new b(parcel, classLoader);
            }

            /* renamed from: a */
            public b createFromParcel(Parcel parcel) {
                return new b(parcel, null);
            }

            /* renamed from: a */
            public b[] newArray(int i) {
                return new b[i];
            }
        };
        boolean a;

        b(Parcelable parcelable) {
            super(parcelable);
        }

        public b(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.a));
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.a + "}";
        }
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        b bVar = new b(super.onSaveInstanceState());
        bVar.a = isIconified();
        return bVar;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof b)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        b bVar = (b) parcelable;
        super.onRestoreInstanceState(bVar.getSuperState());
        a(bVar.a);
        requestLayout();
    }

    void g() {
        int i2;
        if (this.n.getWidth() > 1) {
            Resources resources = getContext().getResources();
            int paddingLeft = this.l.getPaddingLeft();
            Rect rect = new Rect();
            boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
            int dimensionPixelSize = this.E ? resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left) : 0;
            this.a.getDropDownBackground().getPadding(rect);
            if (isLayoutRtl) {
                i2 = -rect.left;
            } else {
                i2 = paddingLeft - (rect.left + dimensionPixelSize);
            }
            this.a.setDropDownHorizontalOffset(i2);
            this.a.setDropDownWidth((((this.n.getWidth() + rect.left) + rect.right) + dimensionPixelSize) - paddingLeft);
        }
    }

    boolean a(int i2, int i3, String str) {
        OnSuggestionListener onSuggestionListener = this.C;
        if (onSuggestionListener != null && onSuggestionListener.onSuggestionClick(i2)) {
            return false;
        }
        b(i2, 0, null);
        this.a.setImeVisibility(false);
        p();
        return true;
    }

    boolean c(int i2) {
        OnSuggestionListener onSuggestionListener = this.C;
        if (onSuggestionListener != null && onSuggestionListener.onSuggestionSelect(i2)) {
            return false;
        }
        d(i2);
        return true;
    }

    private void d(int i2) {
        Editable text = this.a.getText();
        Cursor cursor = this.g.getCursor();
        if (cursor != null) {
            if (cursor.moveToPosition(i2)) {
                CharSequence convertToString = this.g.convertToString(cursor);
                if (convertToString != null) {
                    setQuery(convertToString);
                } else {
                    setQuery(text);
                }
            } else {
                setQuery(text);
            }
        }
    }

    private boolean b(int i2, int i3, String str) {
        Cursor cursor = this.g.getCursor();
        if (cursor == null || !cursor.moveToPosition(i2)) {
            return false;
        }
        a(a(cursor, i3, str));
        return true;
    }

    private void a(Intent intent) {
        if (intent != null) {
            try {
                getContext().startActivity(intent);
            } catch (RuntimeException e) {
                Log.e("SearchView", "Failed launch activity: " + intent, e);
            }
        }
    }

    private void setQuery(CharSequence charSequence) {
        this.a.setText(charSequence);
        this.a.setSelection(TextUtils.isEmpty(charSequence) ? 0 : charSequence.length());
    }

    void a(int i2, String str, String str2) {
        getContext().startActivity(a("android.intent.action.SEARCH", (Uri) null, (String) null, str2, i2, str));
    }

    private Intent a(String str, Uri uri, String str2, String str3, int i2, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra("user_query", this.N);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra("intent_extra_data_key", str2);
        }
        Bundle bundle = this.Q;
        if (bundle != null) {
            intent.putExtra("app_data", bundle);
        }
        if (i2 != 0) {
            intent.putExtra("action_key", i2);
            intent.putExtra("action_msg", str4);
        }
        intent.setComponent(this.h.getSearchActivity());
        return intent;
    }

    private Intent a(Intent intent, SearchableInfo searchableInfo) {
        Intent intent2 = new Intent(intent);
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        intent2.putExtra("calling_package", searchActivity == null ? null : searchActivity.flattenToShortString());
        return intent2;
    }

    private Intent b(Intent intent, SearchableInfo searchableInfo) {
        ComponentName searchActivity = searchableInfo.getSearchActivity();
        Intent intent2 = new Intent("android.intent.action.SEARCH");
        intent2.setComponent(searchActivity);
        PendingIntent activity = PendingIntent.getActivity(getContext(), 0, intent2, 1073741824);
        Bundle bundle = new Bundle();
        Bundle bundle2 = this.Q;
        if (bundle2 != null) {
            bundle.putParcelable("app_data", bundle2);
        }
        Intent intent3 = new Intent(intent);
        String str = "free_form";
        int i2 = 1;
        Resources resources = getResources();
        if (searchableInfo.getVoiceLanguageModeId() != 0) {
            str = resources.getString(searchableInfo.getVoiceLanguageModeId());
        }
        String str2 = null;
        String string = searchableInfo.getVoicePromptTextId() != 0 ? resources.getString(searchableInfo.getVoicePromptTextId()) : null;
        String string2 = searchableInfo.getVoiceLanguageId() != 0 ? resources.getString(searchableInfo.getVoiceLanguageId()) : null;
        if (searchableInfo.getVoiceMaxResults() != 0) {
            i2 = searchableInfo.getVoiceMaxResults();
        }
        intent3.putExtra("android.speech.extra.LANGUAGE_MODEL", str);
        intent3.putExtra("android.speech.extra.PROMPT", string);
        intent3.putExtra("android.speech.extra.LANGUAGE", string2);
        intent3.putExtra("android.speech.extra.MAX_RESULTS", i2);
        if (searchActivity != null) {
            str2 = searchActivity.flattenToShortString();
        }
        intent3.putExtra("calling_package", str2);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", activity);
        intent3.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent3;
    }

    private Intent a(Cursor cursor, int i2, String str) {
        int i3;
        Uri uri;
        String a2;
        try {
            String a3 = p.a(cursor, "suggest_intent_action");
            if (a3 == null) {
                a3 = this.h.getSuggestIntentAction();
            }
            String str2 = a3 == null ? "android.intent.action.SEARCH" : a3;
            String a4 = p.a(cursor, "suggest_intent_data");
            if (a4 == null) {
                a4 = this.h.getSuggestIntentData();
            }
            if (!(a4 == null || (a2 = p.a(cursor, "suggest_intent_data_id")) == null)) {
                a4 = a4 + "/" + Uri.encode(a2);
            }
            if (a4 == null) {
                uri = null;
            } else {
                uri = Uri.parse(a4);
            }
            return a(str2, uri, p.a(cursor, "suggest_intent_extra_data"), p.a(cursor, "suggest_intent_query"), i2, str);
        } catch (RuntimeException e) {
            try {
                i3 = cursor.getPosition();
            } catch (RuntimeException unused) {
                i3 = -1;
            }
            Log.w("SearchView", "Search suggestions cursor at row " + i3 + " returned exception.", e);
            return null;
        }
    }

    void h() {
        if (Build.VERSION.SDK_INT >= 29) {
            this.a.refreshAutoCompleteResults();
            return;
        }
        i.a(this.a);
        i.b(this.a);
    }

    static boolean a(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    /* loaded from: classes.dex */
    private static class c extends TouchDelegate {
        private final View a;
        private final int e;
        private boolean f;
        private final Rect b = new Rect();
        private final Rect d = new Rect();
        private final Rect c = new Rect();

        public c(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.e = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            a(rect, rect2);
            this.a = view;
        }

        public void a(Rect rect, Rect rect2) {
            this.b.set(rect);
            this.d.set(rect);
            Rect rect3 = this.d;
            int i = this.e;
            rect3.inset(-i, -i);
            this.c.set(rect2);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean z;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            boolean z2 = true;
            switch (motionEvent.getAction()) {
                case 0:
                    if (this.b.contains(x, y)) {
                        this.f = true;
                        z = true;
                        break;
                    }
                    z = false;
                    break;
                case 1:
                case 2:
                    z = this.f;
                    if (z && !this.d.contains(x, y)) {
                        z2 = false;
                        break;
                    }
                    break;
                case 3:
                    z = this.f;
                    this.f = false;
                    break;
                default:
                    z = false;
                    break;
            }
            if (!z) {
                return false;
            }
            if (!z2 || this.c.contains(x, y)) {
                motionEvent.setLocation(x - this.c.left, y - this.c.top);
            } else {
                motionEvent.setLocation(this.a.getWidth() / 2, this.a.getHeight() / 2);
            }
            return this.a.dispatchTouchEvent(motionEvent);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public static class SearchAutoComplete extends AppCompatAutoCompleteTextView {
        final Runnable a;
        private int b;
        private SearchView c;
        private boolean d;

        @Override // android.widget.AutoCompleteTextView
        public void performCompletion() {
        }

        @Override // android.widget.AutoCompleteTextView
        protected void replaceText(CharSequence charSequence) {
        }

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            this.a = new Runnable() { // from class: androidx.appcompat.widget.SearchView.SearchAutoComplete.1
                @Override // java.lang.Runnable
                public void run() {
                    SearchAutoComplete.this.b();
                }
            };
            this.b = getThreshold();
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            super.onFinishInflate();
            setMinWidth((int) TypedValue.applyDimension(1, getSearchViewTextMinWidthDp(), getResources().getDisplayMetrics()));
        }

        void setSearchView(SearchView searchView) {
            this.c = searchView;
        }

        @Override // android.widget.AutoCompleteTextView
        public void setThreshold(int i) {
            super.setThreshold(i);
            this.b = i;
        }

        boolean a() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public void onWindowFocusChanged(boolean z) {
            super.onWindowFocusChanged(z);
            if (z && this.c.hasFocus() && getVisibility() == 0) {
                this.d = true;
                if (SearchView.a(getContext())) {
                    c();
                }
            }
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        protected void onFocusChanged(boolean z, int i, Rect rect) {
            super.onFocusChanged(z, i, rect);
            this.c.f();
        }

        @Override // android.widget.AutoCompleteTextView
        public boolean enoughToFilter() {
            return this.b <= 0 || super.enoughToFilter();
        }

        @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                } else if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                    if (keyDispatcherState2 != null) {
                        keyDispatcherState2.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.c.clearFocus();
                        setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(i, keyEvent);
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = getResources().getConfiguration();
            int i = configuration.screenWidthDp;
            int i2 = configuration.screenHeightDp;
            if (i >= 960 && i2 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (i >= 600) {
                return 192;
            }
            if (i < 640 || i2 < 480) {
                return Opcodes.IF_ICMPNE;
            }
            return 192;
        }

        @Override // androidx.appcompat.widget.AppCompatAutoCompleteTextView, android.widget.TextView, android.view.View
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
            if (this.d) {
                removeCallbacks(this.a);
                post(this.a);
            }
            return onCreateInputConnection;
        }

        void b() {
            if (this.d) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
                this.d = false;
            }
        }

        void setImeVisibility(boolean z) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (!z) {
                this.d = false;
                removeCallbacks(this.a);
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this)) {
                this.d = false;
                removeCallbacks(this.a);
                inputMethodManager.showSoftInput(this, 0);
            } else {
                this.d = true;
            }
        }

        void c() {
            if (Build.VERSION.SDK_INT >= 29) {
                setInputMethodMode(1);
                if (enoughToFilter()) {
                    showDropDown();
                    return;
                }
                return;
            }
            SearchView.i.c(this);
        }
    }

    /* loaded from: classes.dex */
    public static class a {
        private Method a;
        private Method b;
        private Method c;

        @SuppressLint({"DiscouragedPrivateApi", "SoonBlockedPrivateApi"})
        a() {
            this.a = null;
            this.b = null;
            this.c = null;
            a();
            try {
                this.a = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.a.setAccessible(true);
            } catch (NoSuchMethodException unused) {
            }
            try {
                this.b = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.b.setAccessible(true);
            } catch (NoSuchMethodException unused2) {
            }
            try {
                this.c = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                this.c.setAccessible(true);
            } catch (NoSuchMethodException unused3) {
            }
        }

        void a(AutoCompleteTextView autoCompleteTextView) {
            a();
            Method method = this.a;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }

        void b(AutoCompleteTextView autoCompleteTextView) {
            a();
            Method method = this.b;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, new Object[0]);
                } catch (Exception unused) {
                }
            }
        }

        void c(AutoCompleteTextView autoCompleteTextView) {
            a();
            Method method = this.c;
            if (method != null) {
                try {
                    method.invoke(autoCompleteTextView, true);
                } catch (Exception unused) {
                }
            }
        }

        private static void a() {
            if (Build.VERSION.SDK_INT >= 29) {
                throw new UnsupportedClassVersionError("This function can only be used for API Level < 29.");
            }
        }
    }
}
