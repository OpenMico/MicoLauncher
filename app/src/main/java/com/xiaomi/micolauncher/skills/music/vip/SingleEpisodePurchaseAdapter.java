package com.xiaomi.micolauncher.skills.music.vip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import java.text.DecimalFormat;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class SingleEpisodePurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<SingleEpisodePurchaseItem> a;
    private SingleEpisodePurchaseItem b;
    private OnPurchaseChoiceChangeListener c;
    private int d = 0;

    /* loaded from: classes3.dex */
    public interface OnPurchaseChoiceChangeListener {
        void onPurchaseChoiceChange(SingleEpisodePurchaseItem singleEpisodePurchaseItem);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book_vip_product, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SingleEpisodePurchaseItem item = getItem(i);
        if (item != null) {
            ((a) viewHolder).a(item, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int size = ContainerUtil.getSize(this.a);
        if (size == 0) {
            return 4;
        }
        return size;
    }

    public SingleEpisodePurchaseItem getItem(int i) {
        if (ContainerUtil.isEmpty(this.a) || i >= ContainerUtil.getSize(this.a)) {
            return null;
        }
        return this.a.get(i);
    }

    public void setData(ArrayList<SingleEpisodePurchaseItem> arrayList) {
        this.a = arrayList;
        if (!ContainerUtil.isEmpty(arrayList)) {
            this.b = arrayList.get(0);
            notifyDataSetChanged();
        }
    }

    public SingleEpisodePurchaseItem getCurrentPurchaseItem() {
        return this.b;
    }

    /* loaded from: classes3.dex */
    class a extends RecyclerView.ViewHolder {
        private TextView b;
        private TextView c;
        private ConstraintLayout d;
        private TextView e;
        private SingleEpisodePurchaseItem f;
        private View g;

        a(View view) {
            super(view);
            this.b = (TextView) view.findViewById(R.id.tvEpisodeCount);
            this.c = (TextView) view.findViewById(R.id.tvPrice);
            this.d = (ConstraintLayout) view.findViewById(R.id.itemRoot);
            this.e = (TextView) view.findViewById(R.id.money_key);
            this.g = view.findViewById(R.id.itemRoot);
            this.g.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.music.vip.SingleEpisodePurchaseAdapter.a.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    a.this.a();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a() {
            if (SingleEpisodePurchaseAdapter.this.d != getLayoutPosition() && getLayoutPosition() >= 0) {
                SingleEpisodePurchaseAdapter.this.b = this.f;
                SingleEpisodePurchaseAdapter singleEpisodePurchaseAdapter = SingleEpisodePurchaseAdapter.this;
                singleEpisodePurchaseAdapter.notifyItemChanged(singleEpisodePurchaseAdapter.d);
                SingleEpisodePurchaseAdapter.this.d = getLayoutPosition();
                if (!(SingleEpisodePurchaseAdapter.this.c == null || SingleEpisodePurchaseAdapter.this.b == null)) {
                    SingleEpisodePurchaseAdapter.this.c.onPurchaseChoiceChange(SingleEpisodePurchaseAdapter.this.b);
                }
                SingleEpisodePurchaseAdapter singleEpisodePurchaseAdapter2 = SingleEpisodePurchaseAdapter.this;
                singleEpisodePurchaseAdapter2.notifyItemChanged(singleEpisodePurchaseAdapter2.d);
            }
        }

        public void a(SingleEpisodePurchaseItem singleEpisodePurchaseItem, int i) {
            this.f = singleEpisodePurchaseItem;
            this.b.setText(singleEpisodePurchaseItem.purchaseCount);
            this.c.setText(singleEpisodePurchaseItem.purchasePriceStr);
            this.d.setSelected(SingleEpisodePurchaseAdapter.this.d == i);
            this.e.setVisibility(0);
        }
    }

    public void setOnPurchaseChoiceChangeListener(OnPurchaseChoiceChangeListener onPurchaseChoiceChangeListener) {
        this.c = onPurchaseChoiceChangeListener;
    }

    /* loaded from: classes3.dex */
    public static class SingleEpisodePurchaseItem {
        float a;
        int b;
        public boolean isSelected;
        public String purchaseCount;
        public String purchasePriceStr;

        /* JADX INFO: Access modifiers changed from: package-private */
        public SingleEpisodePurchaseItem(Context context, String str, int i, float f) {
            this.purchaseCount = str;
            this.a = i * f;
            this.b = i;
            this.purchasePriceStr = SingleEpisodePurchaseAdapter.b(this.a);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(float f) {
        return new DecimalFormat("#.##").format(f);
    }
}
