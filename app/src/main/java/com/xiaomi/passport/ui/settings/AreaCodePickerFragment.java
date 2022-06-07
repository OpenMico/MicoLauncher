package com.xiaomi.passport.ui.settings;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.settings.utils.PhoneNumUtil;

/* loaded from: classes4.dex */
public class AreaCodePickerFragment extends Fragment {
    public static final String KEY_COUNTRY_ISO = "country_iso";
    public static final String TAG = "AreaCodePickerFragment";
    protected Activity mActivity;
    protected AreaCodePickerAdapter mAdapter;
    private AlphabetFastIndexer mFastIndexer;
    protected ListView mListView;
    protected String mPreviousThumb;
    protected View mRoot;

    protected void setupPickerUi(View view, Bundle bundle) {
        prepareAdapter(view, bundle);
        prepareListView(view);
        setupListView(view);
    }

    @Override // android.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        PhoneNumUtil.initializeCountryPhoneData(getActivity());
        this.mRoot = layoutInflater.inflate(R.layout.passport_area_code_picker_fragment, viewGroup, false);
        setupPickerUi(this.mRoot, getArguments());
        return this.mRoot;
    }

    protected void prepareListView(View view) {
        this.mListView = (ListView) view.findViewById(R.id.list);
    }

    protected PhoneNumUtil.CountryPhoneNumData getItem(int i) {
        return this.mAdapter.getItem(i);
    }

    protected void prepareAdapter(View view, Bundle bundle) {
        this.mAdapter = new AreaCodePickerAdapter(getActivity(), bundle);
    }

    protected void setupListView(View view) {
        this.mListView.setAdapter((ListAdapter) this.mAdapter);
        this.mListView.setOnItemClickListener(getListViewItemOnClickListener());
        this.mFastIndexer = (AlphabetFastIndexer) view.findViewById(R.id.fast_indexer);
        this.mFastIndexer.attatch(this.mListView);
        this.mFastIndexer.setVisibility(0);
        this.mListView.setOnScrollListener(this.mFastIndexer.decorateScrollListener(new AbsListView.OnScrollListener() { // from class: com.xiaomi.passport.ui.settings.AreaCodePickerFragment.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (1 == i) {
                    View view2 = null;
                    if (absListView.getContext() instanceof Activity) {
                        view2 = ((Activity) absListView.getContext()).getCurrentFocus();
                    }
                    if (view2 != null) {
                        view2.clearFocus();
                    }
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                String sectionTitleForPostion = AreaCodePickerFragment.this.mAdapter.getSectionTitleForPostion(i + 1);
                if (!TextUtils.equals(sectionTitleForPostion, AreaCodePickerFragment.this.mPreviousThumb)) {
                    AreaCodePickerFragment.this.mFastIndexer.drawThumb(sectionTitleForPostion);
                    AreaCodePickerFragment.this.mPreviousThumb = sectionTitleForPostion;
                }
            }
        }));
    }

    protected AdapterView.OnItemClickListener getListViewItemOnClickListener() {
        return new AdapterView.OnItemClickListener() { // from class: com.xiaomi.passport.ui.settings.AreaCodePickerFragment.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                PhoneNumUtil.CountryPhoneNumData item = AreaCodePickerFragment.this.getItem(i);
                Intent intent = new Intent();
                intent.putExtra(AreaCodePickerFragment.KEY_COUNTRY_ISO, item.countryISO);
                AreaCodePickerFragment.this.mActivity.setResult(-1, intent);
                AreaCodePickerFragment.this.mActivity.finish();
            }
        };
    }
}
