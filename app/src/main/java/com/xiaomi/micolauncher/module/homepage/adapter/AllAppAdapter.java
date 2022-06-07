package com.xiaomi.micolauncher.module.homepage.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.module.homepage.AppHelpUtils;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.view.AppSkillHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AllAppItemViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppSelectedHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.OperationListHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.OperationSingleHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AllAppAdapter extends RecyclerView.Adapter<BaseAppHolder> {
    public static final int TYPE_ALL_APP = 5;
    public static final int TYPE_OPERATE_MULTI = 3;
    public static final int TYPE_OPERATE_MULTI_FOURTH = 4;
    public static final int TYPE_OPERATE_MULTI_THIRD = 3;
    public static final int TYPE_OPERATE_SINGLE_APP = 1;
    public static final int TYPE_OPERATE_SYSTEM_SELECT = 2;
    private List<RecommendCard> a;
    private final Context b;
    private List<Long> c;
    private String d;
    private List<AppInfo> e;

    public AllAppAdapter(Context context) {
        this.b = context;
    }

    @SuppressLint({"CheckResult"})
    public void setDataList(List<RecommendCard> list, String str) {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        this.a.clear();
        this.a.addAll(list);
        this.d = str;
        a();
        notifyDataSetChanged();
    }

    public List<RecommendCard> getRecommendCards() {
        return this.a;
    }

    private void a() {
        this.c = b();
        this.e = AppHelpUtils.getAllAppsExcludeKeys(this.c);
    }

    private List<Long> b() {
        List<RecommendCard> list = this.a;
        if (list == null) {
            return null;
        }
        for (RecommendCard recommendCard : list) {
            if (recommendCard.getCardType() == 2) {
                return recommendCard.getAppKeyList();
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseAppHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        L.homepage.i("AllAppAdapter viewType is %d", Integer.valueOf(i));
        BaseAppHolder a = a(viewGroup, i);
        a.initInMain();
        return a;
    }

    private BaseAppHolder a(@NonNull ViewGroup viewGroup, int i) {
        BaseAppHolder fetchBaseHolder = AppSkillHolderCacheManager.getManager().fetchBaseHolder(i);
        if (fetchBaseHolder != null) {
            return fetchBaseHolder;
        }
        switch (i) {
            case 1:
                return new OperationSingleHolder(a(R.layout.app_operation_single_layout, viewGroup));
            case 2:
                return new AppSelectedHolder(a(R.layout.app_selected_layout, viewGroup));
            case 3:
                return new OperationListHolder(a(R.layout.app_operation_list_layout, viewGroup));
            default:
                return new AllAppItemViewHolder(a(R.layout.app_all_item_layout, viewGroup));
        }
    }

    public void onBindViewHolder(@NonNull BaseAppHolder baseAppHolder, int i) {
        if (baseAppHolder instanceof AllAppItemViewHolder) {
            List<RecommendCard> list = this.a;
            AllAppItemViewHolder allAppItemViewHolder = (AllAppItemViewHolder) baseAppHolder;
            allAppItemViewHolder.bindAppInfo(this.e.get((i - this.a.size()) + 1), this.d, list.get(list.size() - 1).getTrackKey());
            return;
        }
        baseAppHolder.bindData(this.a.get(i), this.d);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<AppInfo> list = this.e;
        int size = list == null ? 0 : list.size();
        List<RecommendCard> list2 = this.a;
        if (list2 == null) {
            return 0;
        }
        return (list2.size() - 1) + size;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        List<RecommendCard> list = this.a;
        if (list == null || i >= list.size() - 1) {
            return 5;
        }
        return this.a.get(i).getCardType();
    }

    private View a(int i, ViewGroup viewGroup) {
        return X2CWrapper.inflate(this.b, i, viewGroup, false);
    }
}
