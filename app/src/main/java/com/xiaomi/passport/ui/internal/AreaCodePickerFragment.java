package com.xiaomi.passport.ui.internal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.AlphabetFastIndexer;
import com.xiaomi.passport.ui.internal.PhoneNumUtil;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AreaCodePicker.kt */
@Metadata(bv = {1, 0, 2}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u001a\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016¨\u0006\u000e"}, d2 = {"Lcom/xiaomi/passport/ui/internal/AreaCodePickerFragment;", "Landroidx/fragment/app/Fragment;", "()V", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", OneTrack.Event.VIEW, "passportui_release"}, k = 1, mv = {1, 1, 10})
/* loaded from: classes4.dex */
public final class AreaCodePickerFragment extends Fragment {
    private HashMap _$_findViewCache;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View view2 = getView();
        if (view2 == null) {
            return null;
        }
        View findViewById = view2.findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    @Override // androidx.fragment.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(inflater, "inflater");
        return inflater.inflate(R.layout.area_code_picker, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(@NotNull View view, @Nullable Bundle bundle) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        super.onViewCreated(view, bundle);
        View findViewById = view.findViewById(R.id.fast_indexer_list);
        if (findViewById != null) {
            final ListView listView = (ListView) findViewById;
            listView.setDividerHeight(0);
            FragmentActivity activity = getActivity();
            if (activity == null) {
                Intrinsics.throwNpe();
            }
            listView.setAdapter((ListAdapter) new AreaCodePickerAdapter(activity));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.xiaomi.passport.ui.internal.AreaCodePickerFragment$onViewCreated$1
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                    Object item = listView.getAdapter().getItem(i);
                    if (item != null) {
                        PhoneNumUtil.CountryPhoneNumData countryPhoneNumData = (PhoneNumUtil.CountryPhoneNumData) item;
                        Intent intent = new Intent();
                        intent.putExtra("iso", countryPhoneNumData.countryISO);
                        intent.putExtra("code", countryPhoneNumData.countryCode);
                        FragmentActivity activity2 = AreaCodePickerFragment.this.getActivity();
                        if (activity2 != null) {
                            activity2.setResult(-1, intent);
                        }
                        FragmentActivity activity3 = AreaCodePickerFragment.this.getActivity();
                        if (activity3 != null) {
                            activity3.finish();
                            return;
                        }
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.PhoneNumUtil.CountryPhoneNumData");
                }
            });
            View findViewById2 = view.findViewById(R.id.fast_indexer);
            if (findViewById2 != null) {
                final AlphabetFastIndexer alphabetFastIndexer = (AlphabetFastIndexer) findViewById2;
                listView.setOnScrollListener(new AlphabetFastIndexer.OnScrollerDecorator(alphabetFastIndexer, null) { // from class: com.xiaomi.passport.ui.internal.AreaCodePickerFragment$onViewCreated$2
                    @Override // com.xiaomi.passport.ui.internal.AlphabetFastIndexer.OnScrollerDecorator
                    @NotNull
                    protected String itemToString(@Nullable Object obj) {
                        if (obj != null) {
                            String str = ((PhoneNumUtil.CountryPhoneNumData) obj).countryName;
                            Intrinsics.checkExpressionValueIsNotNull(str, "(item as PhoneNumUtil.Co…PhoneNumData).countryName");
                            return str;
                        }
                        throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.PhoneNumUtil.CountryPhoneNumData");
                    }
                });
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type com.xiaomi.passport.ui.internal.AlphabetFastIndexer");
        }
        throw new TypeCastException("null cannot be cast to non-null type android.widget.ListView");
    }
}
