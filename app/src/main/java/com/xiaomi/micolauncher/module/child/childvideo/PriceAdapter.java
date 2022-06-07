package com.xiaomi.micolauncher.module.child.childvideo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ProductPrice;
import com.xiaomi.micolauncher.module.child.view.PriceView;
import java.math.BigDecimal;
import java.util.List;

/* loaded from: classes3.dex */
public class PriceAdapter extends RecyclerView.Adapter<PriceViewHolder> {
    private List<ProductPrice.PriceBean> a;
    private Context b;
    private int c = 0;
    private OnPriceItemClickListener d;
    private String e;

    /* loaded from: classes3.dex */
    public interface OnPriceItemClickListener {
        void onPriceClick(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PriceAdapter(Context context, String str) {
        this.b = context;
        this.e = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PriceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (ChildVideoDataManager.PCODE_ERTONG.equals(this.e)) {
            view = LayoutInflater.from(this.b).inflate(R.layout.item_child_vip_product, viewGroup, false);
        } else {
            view = LayoutInflater.from(this.b).inflate(R.layout.item_course_vip_product, viewGroup, false);
        }
        return new PriceViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PriceViewHolder priceViewHolder, int i) {
        if (ContainerUtil.hasData(this.a) && this.a.get(i) != null) {
            priceViewHolder.bindData(this.a.get(i), this.c == i);
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
    public void a(List<ProductPrice.PriceBean> list) {
        this.a = list;
        notifyDataSetChanged();
    }

    public void setSelectPosition(int i) {
        int i2 = this.c;
        if (i2 != i) {
            notifyItemChanged(i2);
            this.c = i;
            notifyItemChanged(this.c);
        }
    }

    /* loaded from: classes3.dex */
    public class PriceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a;
        TextView b;
        PriceView c;
        LinearLayout d;

        PriceViewHolder(View view) {
            super(view);
            this.b = (TextView) view.findViewById(R.id.child_vip_desc);
            this.a = (TextView) view.findViewById(R.id.child_vip_title);
            this.c = (PriceView) view.findViewById(R.id.child_vip_price_view);
            this.d = (LinearLayout) view.findViewById(R.id.child_vip_ll);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (PriceAdapter.this.c != getLayoutPosition() && getLayoutPosition() >= 0) {
                PriceAdapter priceAdapter = PriceAdapter.this;
                priceAdapter.notifyItemChanged(priceAdapter.c);
                PriceAdapter.this.c = getLayoutPosition();
                if (PriceAdapter.this.d != null) {
                    PriceAdapter.this.d.onPriceClick(getLayoutPosition());
                }
                PriceAdapter priceAdapter2 = PriceAdapter.this;
                priceAdapter2.notifyItemChanged(priceAdapter2.c);
            }
        }

        public void bindData(ProductPrice.PriceBean priceBean, boolean z) {
            if (priceBean == null) {
                this.b.setText("");
                this.a.setText("");
                return;
            }
            String unit_desc = priceBean.getUnit_desc();
            if (unit_desc != null && unit_desc.length() > 6) {
                TextView textView = this.a;
                textView.setWidth(((int) textView.getTextSize()) * 4);
                this.a.setSingleLine(false);
                this.a.setInputType(131073);
                this.a.setMaxLines(3);
                this.a.setEllipsize(TextUtils.TruncateAt.END);
            }
            this.a.setText(unit_desc);
            this.b.getPaint().setFlags(0);
            if (priceBean.isRenew()) {
                this.b.setText(priceBean.getCpData().getDesc());
                this.b.getPaint().setFlags(1);
            } else if (priceBean.getReal_price().equals(String.valueOf(priceBean.getCpData().getUi_origin_price()))) {
                this.b.setVisibility(8);
            } else {
                this.b.setVisibility(0);
                this.b.setText(PriceAdapter.this.b.getString(R.string.child_vip_origin_price, new BigDecimal(String.valueOf(priceBean.getCpData().getUi_origin_price() / 100.0f)).stripTrailingZeros().toPlainString()));
                this.b.getPaint().setFlags(17);
            }
            this.b.setSelected(z);
            this.a.setSelected(z);
            this.d.setSelected(z);
            if (z) {
                this.c.setPriceTextColor(ContextCompat.getColor(PriceAdapter.this.b, R.color.child_mode_vip_product_price_selected));
            } else {
                this.c.setPriceTextColor(ContextCompat.getColor(PriceAdapter.this.b, R.color.child_vip_product_price_unselected));
            }
            this.c.setPrice(Float.parseFloat(priceBean.getReal_price()) / 100.0f);
        }
    }

    public void setOnPriceItemClickListener(OnPriceItemClickListener onPriceItemClickListener) {
        this.d = onPriceItemClickListener;
    }
}
