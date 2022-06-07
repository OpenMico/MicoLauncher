package androidx.databinding.adapters;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.SearchView;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({@BindingMethod(attribute = "android:onQueryTextFocusChange", method = "setOnQueryTextFocusChangeListener", type = SearchView.class), @BindingMethod(attribute = "android:onSearchClick", method = "setOnSearchClickListener", type = SearchView.class), @BindingMethod(attribute = "android:onClose", method = "setOnCloseListener", type = SearchView.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class SearchViewBindingAdapter {

    @TargetApi(11)
    /* loaded from: classes.dex */
    public interface OnQueryTextChange {
        boolean onQueryTextChange(String str);
    }

    @TargetApi(11)
    /* loaded from: classes.dex */
    public interface OnQueryTextSubmit {
        boolean onQueryTextSubmit(String str);
    }

    @TargetApi(11)
    /* loaded from: classes.dex */
    public interface OnSuggestionClick {
        boolean onSuggestionClick(int i);
    }

    @TargetApi(11)
    /* loaded from: classes.dex */
    public interface OnSuggestionSelect {
        boolean onSuggestionSelect(int i);
    }

    @BindingAdapter(requireAll = false, value = {"android:onQueryTextSubmit", "android:onQueryTextChange"})
    public static void setOnQueryTextListener(SearchView searchView, final OnQueryTextSubmit onQueryTextSubmit, final OnQueryTextChange onQueryTextChange) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        if (onQueryTextSubmit == null && onQueryTextChange == null) {
            searchView.setOnQueryTextListener(null);
        } else {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: androidx.databinding.adapters.SearchViewBindingAdapter.1
                @Override // android.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    OnQueryTextSubmit onQueryTextSubmit2 = OnQueryTextSubmit.this;
                    if (onQueryTextSubmit2 != null) {
                        return onQueryTextSubmit2.onQueryTextSubmit(str);
                    }
                    return false;
                }

                @Override // android.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    OnQueryTextChange onQueryTextChange2 = onQueryTextChange;
                    if (onQueryTextChange2 != null) {
                        return onQueryTextChange2.onQueryTextChange(str);
                    }
                    return false;
                }
            });
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onSuggestionSelect", "android:onSuggestionClick"})
    public static void setOnSuggestListener(SearchView searchView, final OnSuggestionSelect onSuggestionSelect, final OnSuggestionClick onSuggestionClick) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        if (onSuggestionSelect == null && onSuggestionClick == null) {
            searchView.setOnSuggestionListener(null);
        } else {
            searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() { // from class: androidx.databinding.adapters.SearchViewBindingAdapter.2
                @Override // android.widget.SearchView.OnSuggestionListener
                public boolean onSuggestionSelect(int i) {
                    OnSuggestionSelect onSuggestionSelect2 = OnSuggestionSelect.this;
                    if (onSuggestionSelect2 != null) {
                        return onSuggestionSelect2.onSuggestionSelect(i);
                    }
                    return false;
                }

                @Override // android.widget.SearchView.OnSuggestionListener
                public boolean onSuggestionClick(int i) {
                    OnSuggestionClick onSuggestionClick2 = onSuggestionClick;
                    if (onSuggestionClick2 != null) {
                        return onSuggestionClick2.onSuggestionClick(i);
                    }
                    return false;
                }
            });
        }
    }
}
