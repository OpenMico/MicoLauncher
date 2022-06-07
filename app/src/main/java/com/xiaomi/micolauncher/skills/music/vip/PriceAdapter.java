package com.xiaomi.micolauncher.skills.music.vip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.MusicPrice;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/* loaded from: classes3.dex */
public class PriceAdapter extends RecyclerView.Adapter<a> {
    private List<MusicPrice> a;
    private Context b;
    private int c = 0;
    private NumberFormat d = new DecimalFormat("#.#");
    private OnPriceItemClickListener e;

    /* loaded from: classes3.dex */
    public interface OnPriceItemClickListener {
        void onPriceClick(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PriceAdapter(Context context) {
        this.b = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(this.b).inflate(R.layout.item_music_vip_product, viewGroup, false));
    }

    @SuppressLint({"SetTextI18n"})
    public void onBindViewHolder(@NonNull a aVar, int i) {
        if (ContainerUtil.hasData(this.a) && this.a.get(i) != null) {
            MusicPrice musicPrice = this.a.get(i);
            aVar.a.setText(musicPrice.getShortName());
            aVar.d.setSelected(this.c == i);
            if (musicPrice.getOriginalSalePrice() > 0) {
                aVar.b.setVisibility(0);
                TextView textView = aVar.b;
                textView.setText("¥" + (musicPrice.getOriginalSalePrice() / 100));
                aVar.b.setPaintFlags(aVar.b.getPaintFlags() | 16);
            } else {
                aVar.b.setVisibility(4);
            }
            if (TextUtils.isEmpty(musicPrice.getSubtitle())) {
                aVar.e.setVisibility(4);
            } else {
                aVar.e.setVisibility(0);
                aVar.e.setText(musicPrice.getSubtitle());
            }
            aVar.c.setText(this.d.format(musicPrice.getSalePrice() / 100.0f));
            aVar.f.setText(this.b.getString(R.string.china_money));
            aVar.itemView.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (ContainerUtil.hasData(this.a)) {
            return ContainerUtil.getSize(this.a);
        }
        return 6;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(List<MusicPrice> list) {
        this.a = list;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a;
        TextView b;
        TextView c;
        ConstraintLayout d;
        TextView e;
        TextView f;

        a(View view) {
            super(view);
            this.b = (TextView) view.findViewById(R.id.child_vip_desc);
            this.a = (TextView) view.findViewById(R.id.child_vip_title);
            this.c = (TextView) view.findViewById(R.id.price);
            this.d = (ConstraintLayout) view.findViewById(R.id.child_vip_ll);
            this.e = (TextView) view.findViewById(R.id.subtitle);
            this.f = (TextView) view.findViewById(R.id.money_key);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (PriceAdapter.this.c != getLayoutPosition() && getLayoutPosition() >= 0) {
                PriceAdapter priceAdapter = PriceAdapter.this;
                priceAdapter.notifyItemChanged(priceAdapter.c);
                PriceAdapter.this.c = getLayoutPosition();
                if (PriceAdapter.this.e != null) {
                    PriceAdapter.this.e.onPriceClick(getLayoutPosition());
                }
                PriceAdapter priceAdapter2 = PriceAdapter.this;
                priceAdapter2.notifyItemChanged(priceAdapter2.c);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(OnPriceItemClickListener onPriceItemClickListener) {
        this.e = onPriceItemClickListener;
    }
}
