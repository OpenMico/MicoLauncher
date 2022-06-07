package com.chad.library.adapter.base;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes.dex */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private BaseQuickAdapter adapter;
    private Object associatedObject;
    @Deprecated
    public View convertView;
    private final SparseArray<View> views = new SparseArray<>();
    private final LinkedHashSet<Integer> childClickViewIds = new LinkedHashSet<>();
    private final LinkedHashSet<Integer> itemChildLongClickViewIds = new LinkedHashSet<>();
    private final HashSet<Integer> nestViews = new HashSet<>();

    public Set<Integer> getNestViews() {
        return this.nestViews;
    }

    public BaseViewHolder(View view) {
        super(view);
        this.convertView = view;
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return this.itemChildLongClickViewIds;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    @Deprecated
    public View getConvertView() {
        return this.convertView;
    }

    public BaseViewHolder setText(@IdRes int i, CharSequence charSequence) {
        ((TextView) getView(i)).setText(charSequence);
        return this;
    }

    public BaseViewHolder setText(@IdRes int i, @StringRes int i2) {
        ((TextView) getView(i)).setText(i2);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int i, @DrawableRes int i2) {
        ((ImageView) getView(i)).setImageResource(i2);
        return this;
    }

    public BaseViewHolder setBackgroundColor(@IdRes int i, @ColorInt int i2) {
        getView(i).setBackgroundColor(i2);
        return this;
    }

    public BaseViewHolder setBackgroundRes(@IdRes int i, @DrawableRes int i2) {
        getView(i).setBackgroundResource(i2);
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes int i, @ColorInt int i2) {
        ((TextView) getView(i)).setTextColor(i2);
        return this;
    }

    public BaseViewHolder setImageDrawable(@IdRes int i, Drawable drawable) {
        ((ImageView) getView(i)).setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageBitmap(@IdRes int i, Bitmap bitmap) {
        ((ImageView) getView(i)).setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setAlpha(@IdRes int i, float f) {
        if (Build.VERSION.SDK_INT >= 11) {
            getView(i).setAlpha(f);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(f, f);
            alphaAnimation.setDuration(0L);
            alphaAnimation.setFillAfter(true);
            getView(i).startAnimation(alphaAnimation);
        }
        return this;
    }

    public BaseViewHolder setGone(@IdRes int i, boolean z) {
        getView(i).setVisibility(z ? 0 : 8);
        return this;
    }

    public BaseViewHolder setVisible(@IdRes int i, boolean z) {
        getView(i).setVisibility(z ? 0 : 4);
        return this;
    }

    public BaseViewHolder linkify(@IdRes int i) {
        Linkify.addLinks((TextView) getView(i), 15);
        return this;
    }

    public BaseViewHolder setTypeface(@IdRes int i, Typeface typeface) {
        TextView textView = (TextView) getView(i);
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | 128);
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... iArr) {
        for (int i : iArr) {
            TextView textView = (TextView) getView(i);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | 128);
        }
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int i, int i2) {
        ((ProgressBar) getView(i)).setProgress(i2);
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int i, int i2, int i3) {
        ProgressBar progressBar = (ProgressBar) getView(i);
        progressBar.setMax(i3);
        progressBar.setProgress(i2);
        return this;
    }

    public BaseViewHolder setMax(@IdRes int i, int i2) {
        ((ProgressBar) getView(i)).setMax(i2);
        return this;
    }

    public BaseViewHolder setRating(@IdRes int i, float f) {
        ((RatingBar) getView(i)).setRating(f);
        return this;
    }

    public BaseViewHolder setRating(@IdRes int i, float f, int i2) {
        RatingBar ratingBar = (RatingBar) getView(i);
        ratingBar.setMax(i2);
        ratingBar.setRating(f);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnClickListener(@IdRes int i, View.OnClickListener onClickListener) {
        getView(i).setOnClickListener(onClickListener);
        return this;
    }

    public BaseViewHolder addOnClickListener(@IdRes int... iArr) {
        for (int i : iArr) {
            this.childClickViewIds.add(Integer.valueOf(i));
            View view = getView(i);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setClickable(true);
                }
                view.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.BaseViewHolder.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        int adapterPosition;
                        if (BaseViewHolder.this.adapter.getOnItemChildClickListener() != null && (adapterPosition = BaseViewHolder.this.getAdapterPosition()) != -1) {
                            BaseViewHolder.this.adapter.getOnItemChildClickListener().onItemChildClick(BaseViewHolder.this.adapter, view2, adapterPosition - BaseViewHolder.this.adapter.getHeaderLayoutCount());
                        }
                    }
                });
            }
        }
        return this;
    }

    public BaseViewHolder setNestView(@IdRes int... iArr) {
        for (int i : iArr) {
            this.nestViews.add(Integer.valueOf(i));
        }
        addOnClickListener(iArr);
        addOnLongClickListener(iArr);
        return this;
    }

    public BaseViewHolder addOnLongClickListener(@IdRes int... iArr) {
        for (int i : iArr) {
            this.itemChildLongClickViewIds.add(Integer.valueOf(i));
            View view = getView(i);
            if (view != null) {
                if (!view.isLongClickable()) {
                    view.setLongClickable(true);
                }
                view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.BaseViewHolder.2
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view2) {
                        int adapterPosition;
                        if (BaseViewHolder.this.adapter.getOnItemChildLongClickListener() == null || (adapterPosition = BaseViewHolder.this.getAdapterPosition()) == -1) {
                            return false;
                        }
                        return BaseViewHolder.this.adapter.getOnItemChildLongClickListener().onItemChildLongClick(BaseViewHolder.this.adapter, view2, adapterPosition - BaseViewHolder.this.adapter.getHeaderLayoutCount());
                    }
                });
            }
        }
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnTouchListener(@IdRes int i, View.OnTouchListener onTouchListener) {
        getView(i).setOnTouchListener(onTouchListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnLongClickListener(@IdRes int i, View.OnLongClickListener onLongClickListener) {
        getView(i).setOnLongClickListener(onLongClickListener);
        return this;
    }

    @Deprecated
    public BaseViewHolder setOnItemClickListener(@IdRes int i, AdapterView.OnItemClickListener onItemClickListener) {
        ((AdapterView) getView(i)).setOnItemClickListener(onItemClickListener);
        return this;
    }

    public BaseViewHolder setOnItemLongClickListener(@IdRes int i, AdapterView.OnItemLongClickListener onItemLongClickListener) {
        ((AdapterView) getView(i)).setOnItemLongClickListener(onItemLongClickListener);
        return this;
    }

    public BaseViewHolder setOnItemSelectedClickListener(@IdRes int i, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ((AdapterView) getView(i)).setOnItemSelectedListener(onItemSelectedListener);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(@IdRes int i, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        ((CompoundButton) getView(i)).setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }

    public BaseViewHolder setTag(@IdRes int i, Object obj) {
        getView(i).setTag(obj);
        return this;
    }

    public BaseViewHolder setTag(@IdRes int i, int i2, Object obj) {
        getView(i).setTag(i2, obj);
        return this;
    }

    public BaseViewHolder setChecked(@IdRes int i, boolean z) {
        View view = getView(i);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(z);
        }
        return this;
    }

    public BaseViewHolder setEnabled(@IdRes int i, boolean z) {
        getView(i).setEnabled(z);
        return this;
    }

    public BaseViewHolder setAdapter(@IdRes int i, Adapter adapter) {
        ((AdapterView) getView(i)).setAdapter(adapter);
        return this;
    }

    public BaseViewHolder setAdapter(BaseQuickAdapter baseQuickAdapter) {
        this.adapter = baseQuickAdapter;
        return this;
    }

    public <T extends View> T getView(@IdRes int i) {
        T t = (T) this.views.get(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) this.itemView.findViewById(i);
        this.views.put(i, t2);
        return t2;
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public void setAssociatedObject(Object obj) {
        this.associatedObject = obj;
    }
}
