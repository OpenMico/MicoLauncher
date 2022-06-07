package androidx.appcompat.widget;

import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.ResourceCursorAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: SuggestionsAdapter.java */
/* loaded from: classes.dex */
class p extends ResourceCursorAdapter implements View.OnClickListener {
    private final SearchView a;
    private final SearchableInfo b;
    private final Context c;
    private final WeakHashMap<String, Drawable.ConstantState> d;
    private final int e;
    private ColorStateList h;
    private boolean f = false;
    private int g = 1;
    private int i = -1;
    private int j = -1;
    private int k = -1;
    private int l = -1;
    private int m = -1;
    private int n = -1;

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return false;
    }

    public p(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), (Cursor) null, true);
        this.a = searchView;
        this.b = searchableInfo;
        this.e = searchView.getSuggestionCommitIconResId();
        this.c = context;
        this.d = weakHashMap;
    }

    public void a(int i) {
        this.g = i;
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.a.AbstractC0013a
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        String charSequence2 = charSequence == null ? "" : charSequence.toString();
        if (this.a.getVisibility() != 0 || this.a.getWindowVisibility() != 0) {
            return null;
        }
        try {
            Cursor a2 = a(this.b, charSequence2, 50);
            if (a2 != null) {
                a2.getCount();
                return a2;
            }
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions query threw an exception.", e);
        }
        return null;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        a(getCursor());
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        a(getCursor());
    }

    private void a(Cursor cursor) {
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null && !extras.getBoolean("in_progress")) {
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.a.AbstractC0013a
    public void changeCursor(Cursor cursor) {
        if (this.f) {
            Log.w("SuggestionsAdapter", "Tried to change cursor after adapter was closed.");
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        try {
            super.changeCursor(cursor);
            if (cursor != null) {
                this.i = cursor.getColumnIndex("suggest_text_1");
                this.j = cursor.getColumnIndex("suggest_text_2");
                this.k = cursor.getColumnIndex("suggest_text_2_url");
                this.l = cursor.getColumnIndex("suggest_icon_1");
                this.m = cursor.getColumnIndex("suggest_icon_2");
                this.n = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Exception e) {
            Log.e("SuggestionsAdapter", "error changing cursor and caching columns", e);
        }
    }

    @Override // androidx.cursoradapter.widget.ResourceCursorAdapter, androidx.cursoradapter.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View newView = super.newView(context, cursor, viewGroup);
        newView.setTag(new a(newView));
        ((ImageView) newView.findViewById(R.id.edit_query)).setImageResource(this.e);
        return newView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: SuggestionsAdapter.java */
    /* loaded from: classes.dex */
    public static final class a {
        public final TextView a;
        public final TextView b;
        public final ImageView c;
        public final ImageView d;
        public final ImageView e;

        public a(View view) {
            this.a = (TextView) view.findViewById(16908308);
            this.b = (TextView) view.findViewById(16908309);
            this.c = (ImageView) view.findViewById(16908295);
            this.d = (ImageView) view.findViewById(16908296);
            this.e = (ImageView) view.findViewById(R.id.edit_query);
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        CharSequence charSequence;
        a aVar = (a) view.getTag();
        int i = this.n;
        int i2 = i != -1 ? cursor.getInt(i) : 0;
        if (aVar.a != null) {
            a(aVar.a, a(cursor, this.i));
        }
        if (aVar.b != null) {
            String a2 = a(cursor, this.k);
            if (a2 != null) {
                charSequence = a((CharSequence) a2);
            } else {
                charSequence = a(cursor, this.j);
            }
            if (TextUtils.isEmpty(charSequence)) {
                if (aVar.a != null) {
                    aVar.a.setSingleLine(false);
                    aVar.a.setMaxLines(2);
                }
            } else if (aVar.a != null) {
                aVar.a.setSingleLine(true);
                aVar.a.setMaxLines(1);
            }
            a(aVar.b, charSequence);
        }
        if (aVar.c != null) {
            a(aVar.c, b(cursor), 4);
        }
        if (aVar.d != null) {
            a(aVar.d, c(cursor), 8);
        }
        int i3 = this.g;
        if (i3 == 2 || (i3 == 1 && (i2 & 1) != 0)) {
            aVar.e.setVisibility(0);
            aVar.e.setTag(aVar.a.getText());
            aVar.e.setOnClickListener(this);
            return;
        }
        aVar.e.setVisibility(8);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.a.a((CharSequence) tag);
        }
    }

    private CharSequence a(CharSequence charSequence) {
        if (this.h == null) {
            TypedValue typedValue = new TypedValue();
            this.c.getTheme().resolveAttribute(R.attr.textColorSearchUrl, typedValue, true);
            this.h = this.c.getResources().getColorStateList(typedValue.resourceId);
        }
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new TextAppearanceSpan(null, 0, 0, this.h, null), 0, charSequence.length(), 33);
        return spannableString;
    }

    private void a(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
        }
    }

    private Drawable b(Cursor cursor) {
        int i = this.l;
        if (i == -1) {
            return null;
        }
        Drawable a2 = a(cursor.getString(i));
        return a2 != null ? a2 : a();
    }

    private Drawable c(Cursor cursor) {
        int i = this.m;
        if (i == -1) {
            return null;
        }
        return a(cursor.getString(i));
    }

    private void a(ImageView imageView, Drawable drawable, int i) {
        imageView.setImageDrawable(drawable);
        if (drawable == null) {
            imageView.setVisibility(i);
            return;
        }
        imageView.setVisibility(0);
        drawable.setVisible(false, false);
        drawable.setVisible(true, false);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.a.AbstractC0013a
    public CharSequence convertToString(Cursor cursor) {
        String a2;
        String a3;
        if (cursor == null) {
            return null;
        }
        String a4 = a(cursor, "suggest_intent_query");
        if (a4 != null) {
            return a4;
        }
        if (this.b.shouldRewriteQueryFromData() && (a3 = a(cursor, "suggest_intent_data")) != null) {
            return a3;
        }
        if (!this.b.shouldRewriteQueryFromText() || (a2 = a(cursor, "suggest_text_1")) == null) {
            return null;
        }
        return a2;
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i, view, viewGroup);
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e);
            View newView = newView(this.c, getCursor(), viewGroup);
            if (newView != null) {
                ((a) newView.getTag()).a.setText(e.toString());
            }
            return newView;
        }
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i, view, viewGroup);
        } catch (RuntimeException e) {
            Log.w("SuggestionsAdapter", "Search suggestions cursor threw exception.", e);
            View newDropDownView = newDropDownView(this.c, getCursor(), viewGroup);
            if (newDropDownView != null) {
                ((a) newDropDownView.getTag()).a.setText(e.toString());
            }
            return newDropDownView;
        }
    }

    private Drawable a(String str) {
        if (str == null || str.isEmpty() || "0".equals(str)) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(str);
            String str2 = "android.resource://" + this.c.getPackageName() + "/" + parseInt;
            Drawable b = b(str2);
            if (b != null) {
                return b;
            }
            Drawable drawable = ContextCompat.getDrawable(this.c, parseInt);
            a(str2, drawable);
            return drawable;
        } catch (Resources.NotFoundException unused) {
            Log.w("SuggestionsAdapter", "Icon resource not found: " + str);
            return null;
        } catch (NumberFormatException unused2) {
            Drawable b2 = b(str);
            if (b2 != null) {
                return b2;
            }
            Drawable b3 = b(Uri.parse(str));
            a(str, b3);
            return b3;
        }
    }

    private Drawable b(Uri uri) {
        try {
            if ("android.resource".equals(uri.getScheme())) {
                try {
                    return a(uri);
                } catch (Resources.NotFoundException unused) {
                    throw new FileNotFoundException("Resource does not exist: " + uri);
                }
            } else {
                InputStream openInputStream = this.c.getContentResolver().openInputStream(uri);
                if (openInputStream != null) {
                    Drawable createFromStream = Drawable.createFromStream(openInputStream, null);
                    try {
                        openInputStream.close();
                    } catch (IOException e) {
                        Log.e("SuggestionsAdapter", "Error closing icon stream for " + uri, e);
                    }
                    return createFromStream;
                }
                throw new FileNotFoundException("Failed to open " + uri);
            }
        } catch (FileNotFoundException e2) {
            Log.w("SuggestionsAdapter", "Icon not found: " + uri + ", " + e2.getMessage());
            return null;
        }
        Log.w("SuggestionsAdapter", "Icon not found: " + uri + ", " + e2.getMessage());
        return null;
    }

    private Drawable b(String str) {
        Drawable.ConstantState constantState = this.d.get(str);
        if (constantState == null) {
            return null;
        }
        return constantState.newDrawable();
    }

    private void a(String str, Drawable drawable) {
        if (drawable != null) {
            this.d.put(str, drawable.getConstantState());
        }
    }

    private Drawable a() {
        Drawable a2 = a(this.b.getSearchActivity());
        return a2 != null ? a2 : this.c.getPackageManager().getDefaultActivityIcon();
    }

    private Drawable a(ComponentName componentName) {
        String flattenToShortString = componentName.flattenToShortString();
        Drawable.ConstantState constantState = null;
        if (this.d.containsKey(flattenToShortString)) {
            Drawable.ConstantState constantState2 = this.d.get(flattenToShortString);
            if (constantState2 == null) {
                return null;
            }
            return constantState2.newDrawable(this.c.getResources());
        }
        Drawable b = b(componentName);
        if (b != null) {
            constantState = b.getConstantState();
        }
        this.d.put(flattenToShortString, constantState);
        return b;
    }

    private Drawable b(ComponentName componentName) {
        PackageManager packageManager = this.c.getPackageManager();
        try {
            ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 128);
            int iconResource = activityInfo.getIconResource();
            if (iconResource == 0) {
                return null;
            }
            Drawable drawable = packageManager.getDrawable(componentName.getPackageName(), iconResource, activityInfo.applicationInfo);
            if (drawable != null) {
                return drawable;
            }
            Log.w("SuggestionsAdapter", "Invalid icon resource " + iconResource + " for " + componentName.flattenToShortString());
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("SuggestionsAdapter", e.toString());
            return null;
        }
    }

    public static String a(Cursor cursor, String str) {
        return a(cursor, cursor.getColumnIndex(str));
    }

    private static String a(Cursor cursor, int i) {
        if (i == -1) {
            return null;
        }
        try {
            return cursor.getString(i);
        } catch (Exception e) {
            Log.e("SuggestionsAdapter", "unexpected error retrieving valid column from cursor, did the remote process die?", e);
            return null;
        }
    }

    Drawable a(Uri uri) throws FileNotFoundException {
        int parseInt;
        String authority = uri.getAuthority();
        if (!TextUtils.isEmpty(authority)) {
            try {
                Resources resourcesForApplication = this.c.getPackageManager().getResourcesForApplication(authority);
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments != null) {
                    int size = pathSegments.size();
                    if (size == 1) {
                        try {
                            parseInt = Integer.parseInt(pathSegments.get(0));
                        } catch (NumberFormatException unused) {
                            throw new FileNotFoundException("Single path segment is not a resource ID: " + uri);
                        }
                    } else if (size == 2) {
                        parseInt = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
                    } else {
                        throw new FileNotFoundException("More than two path segments: " + uri);
                    }
                    if (parseInt != 0) {
                        return resourcesForApplication.getDrawable(parseInt);
                    }
                    throw new FileNotFoundException("No resource found for: " + uri);
                }
                throw new FileNotFoundException("No path: " + uri);
            } catch (PackageManager.NameNotFoundException unused2) {
                throw new FileNotFoundException("No package found for authority: " + uri);
            }
        } else {
            throw new FileNotFoundException("No authority: " + uri);
        }
    }

    Cursor a(SearchableInfo searchableInfo, String str, int i) {
        String suggestAuthority;
        String[] strArr;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder fragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            fragment.appendEncodedPath(suggestPath);
        }
        fragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            fragment.appendPath(str);
            strArr = null;
        }
        if (i > 0) {
            fragment.appendQueryParameter("limit", String.valueOf(i));
        }
        return this.c.getContentResolver().query(fragment.build(), null, suggestSelection, strArr, null);
    }
}
