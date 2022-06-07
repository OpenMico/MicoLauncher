package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.settings.utils.PhoneNumUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/* loaded from: classes4.dex */
public class AreaCodePickerAdapter extends BaseAdapter implements SectionIndexer {
    private Context mContext;
    private List<PhoneNumUtil.CountryPhoneNumData> mList;
    private Map<Integer, String> mSectionText;
    private int[] mSectionToPosition;
    private String[] mSections;
    private boolean mShowAreaCode;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public AreaCodePickerAdapter(Context context, Bundle bundle) {
        this.mShowAreaCode = true;
        this.mContext = context;
        buildPickerSectionList();
        if (bundle != null) {
            this.mShowAreaCode = bundle.getBoolean("show_country_code", true);
        }
    }

    private void buildPickerSectionList() {
        int i;
        this.mList = PhoneNumUtil.getCountryPhoneNumDataList();
        TreeSet treeSet = new TreeSet();
        ArrayList arrayList = new ArrayList();
        Iterator<PhoneNumUtil.CountryPhoneNumData> it = this.mList.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            String upperCase = it.next().countryName.substring(0, 1).toUpperCase();
            arrayList.add(upperCase);
            treeSet.add(upperCase);
        }
        this.mSections = (String[]) treeSet.toArray(new String[0]);
        this.mSectionToPosition = new int[this.mSections.length];
        this.mSectionText = new HashMap();
        while (true) {
            String[] strArr = this.mSections;
            if (i < strArr.length) {
                this.mSectionToPosition[i] = arrayList.indexOf(strArr[i]);
                this.mSectionText.put(Integer.valueOf(this.mSectionToPosition[i]), this.mSections[i]);
                i++;
            } else {
                return;
            }
        }
    }

    public String getSectionTitleForPostion(int i) {
        return this.mList.get(i).countryName.substring(0, 1).toUpperCase();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mList.size();
    }

    @Override // android.widget.Adapter
    public PhoneNumUtil.CountryPhoneNumData getItem(int i) {
        return this.mList.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        AreaCodePickerListItem areaCodePickerListItem = (AreaCodePickerListItem) view;
        if (areaCodePickerListItem == null) {
            areaCodePickerListItem = (AreaCodePickerListItem) ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.passport_area_code_list_item, (ViewGroup) null);
        }
        ((TextView) areaCodePickerListItem.findViewById(R.id.area_code)).setVisibility(this.mShowAreaCode ? 0 : 8);
        areaCodePickerListItem.bind(getItem(i), this.mSectionText.get(Integer.valueOf(i)));
        return areaCodePickerListItem;
    }

    @Override // android.widget.SectionIndexer
    public Object[] getSections() {
        return this.mSections;
    }

    @Override // android.widget.SectionIndexer
    public int getPositionForSection(int i) {
        return this.mSectionToPosition[i];
    }

    @Override // android.widget.SectionIndexer
    public int getSectionForPosition(int i) {
        int i2 = 1;
        int i3 = 0;
        while (true) {
            int[] iArr = this.mSectionToPosition;
            if (i2 >= iArr.length || iArr[i2] > i) {
                break;
            }
            i3++;
            i2++;
        }
        return i3;
    }
}
