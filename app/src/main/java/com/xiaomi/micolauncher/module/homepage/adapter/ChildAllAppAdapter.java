package com.xiaomi.micolauncher.module.homepage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AllAppViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppSelectedHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.OperationListHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.OperationSingleHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class ChildAllAppAdapter extends RecyclerView.Adapter<BaseAppHolder> {
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

    public ChildAllAppAdapter(Context context) {
        this.b = context;
    }

    public void setDataList(List<RecommendCard> list, String str) {
        this.a = list;
        this.d = str;
        L.homepage.i("recommend cards is : %s", list);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseAppHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseAppHolder baseAppHolder;
        L.homepage.i("viewType is %d", Integer.valueOf(i));
        switch (i) {
            case 1:
                baseAppHolder = new OperationSingleHolder(a(R.layout.child_app_operation_single_layout, viewGroup));
                break;
            case 2:
                baseAppHolder = new AppSelectedHolder(a(R.layout.child_app_selected_layout, viewGroup));
                break;
            case 3:
                baseAppHolder = new OperationListHolder(a(R.layout.child_app_operation_list_layout, viewGroup));
                break;
            default:
                baseAppHolder = new AllAppViewHolder(a(R.layout.child_app_all_layout, viewGroup));
                break;
        }
        baseAppHolder.initInMain();
        return baseAppHolder;
    }

    public void onBindViewHolder(@NonNull BaseAppHolder baseAppHolder, int i) {
        if (getItemViewType(i) == 2) {
            this.c = this.a.get(i).getAppKeyList();
        }
        if (baseAppHolder instanceof AllAppViewHolder) {
            ((AllAppViewHolder) baseAppHolder).bindData(this.c, this.d, this.a.get(i).getTrackKey());
        } else {
            baseAppHolder.bindData(this.a.get(i), this.d);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<RecommendCard> list = this.a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.a.get(i).getCardType();
    }

    private View a(int i, ViewGroup viewGroup) {
        return X2CWrapper.inflate(this.b, i, viewGroup, false);
    }
}
