package com.xiaomi.micolauncher.module;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.widget.HorizontalRecyclerView;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class HorizontalListFragment extends Fragment {
    private Activity a;
    private TabViewPresenter b;

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.a = (Activity) context;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(@NotNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_horizontal_scroll, (ViewGroup) null);
        ((HorizontalRecyclerView) inflate.findViewById(R.id.rv_list)).setAdapter(new RecyclerAdapter(this.a, getData()));
        return inflate;
    }

    public List<Integer> getData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public void setTabViewPresenter(TabViewPresenter tabViewPresenter) {
        this.b = tabViewPresenter;
    }
}
