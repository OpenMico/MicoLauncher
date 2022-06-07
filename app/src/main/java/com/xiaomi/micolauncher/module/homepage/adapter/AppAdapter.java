package com.xiaomi.micolauncher.module.homepage.adapter;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.module.homepage.ItemTouchCallBack;
import com.xiaomi.micolauncher.module.homepage.bean.CategoryTab;
import com.xiaomi.micolauncher.module.homepage.cache.AppRealmHelper;
import com.xiaomi.micolauncher.module.homepage.view.AppSkillHolderCacheManager;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppAlarmViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppGalleryViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppWeatherViewHolder;
import com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class AppAdapter extends RecyclerView.Adapter<BaseAppHolder> implements ItemTouchCallBack.OnItemTouchListener {
    public static final int ITEM_TYPE_ALARM = 3;
    public static final int ITEM_TYPE_GALLERY = 2;
    public static final int ITEM_TYPE_NORMAL = 0;
    public static final int ITEM_TYPE_WEATHER = 1;
    private List<AppInfo> a;
    private final Context b;
    private boolean c;
    private CategoryTab d;
    private final boolean e = ChildModeManager.getManager().isChildMode();

    public AppAdapter(Context context) {
        this.b = context;
    }

    public void setManaged(boolean z) {
        this.c = z;
    }

    public void setDataList(List<AppInfo> list, CategoryTab categoryTab) {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        this.a.clear();
        this.a.addAll(list);
        this.d = categoryTab;
        notifyDataSetChanged();
    }

    public boolean removeAppData(int i) {
        if (i < 0 || i >= this.a.size()) {
            return false;
        }
        this.a.remove(i);
        notifyItemRemoved(i);
        return true;
    }

    public void addAppData(AppInfo appInfo) {
        int itemCount = getItemCount();
        this.a.add(appInfo);
        notifyItemInserted(itemCount);
    }

    public List<AppInfo> getAppList() {
        return this.a;
    }

    public void onViewAttachedToWindow(@NonNull BaseAppHolder baseAppHolder) {
        super.onViewAttachedToWindow((AppAdapter) baseAppHolder);
        if (baseAppHolder instanceof AppAlarmViewHolder) {
            ((AppAlarmViewHolder) baseAppHolder).register();
        }
    }

    public void onViewDetachedFromWindow(@NonNull BaseAppHolder baseAppHolder) {
        super.onViewDetachedFromWindow((AppAdapter) baseAppHolder);
        if (baseAppHolder instanceof AppAlarmViewHolder) {
            ((AppAlarmViewHolder) baseAppHolder).unRegister();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseAppHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BaseAppHolder baseAppHolder;
        if (this.e) {
            baseAppHolder = a(viewGroup, i);
        } else {
            BaseAppHolder fetchViewHolderHolder = AppSkillHolderCacheManager.getManager().fetchViewHolderHolder(i);
            baseAppHolder = fetchViewHolderHolder == null ? a(viewGroup, i) : fetchViewHolderHolder;
        }
        baseAppHolder.initInMain();
        return baseAppHolder;
    }

    private BaseAppHolder a(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case 1:
                return new AppWeatherViewHolder(X2CWrapper.inflate(this.b, (int) R.layout.apps_card_weather, viewGroup, false));
            case 2:
                return new AppGalleryViewHolder(X2CWrapper.inflate(this.b, (int) R.layout.apps_card_gallery, viewGroup, false));
            case 3:
                return new AppAlarmViewHolder(X2CWrapper.inflate(this.b, (int) R.layout.apps_card_alarm, viewGroup, false));
            default:
                return new AppViewHolder(X2CWrapper.inflate(this.b, this.e ? R.layout.child_app_item : R.layout.app_item, viewGroup, false));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<AppInfo> list = this.a;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void onBindViewHolder(@NonNull BaseAppHolder baseAppHolder, int i) {
        if (baseAppHolder instanceof AppWeatherViewHolder) {
            ((AppWeatherViewHolder) baseAppHolder).bindAppInfo(this.a.get(i), this.d.getAppTabName());
        } else if (baseAppHolder instanceof AppGalleryViewHolder) {
            ((AppGalleryViewHolder) baseAppHolder).bindAppInfo(this.a.get(i), this.d.getAppTabName());
        } else if (baseAppHolder instanceof AppAlarmViewHolder) {
            ((AppAlarmViewHolder) baseAppHolder).bindAppInfo(this.a.get(i), this.d.getAppTabName());
        } else {
            AppViewHolder appViewHolder = (AppViewHolder) baseAppHolder;
            appViewHolder.setManaged(this.c);
            appViewHolder.setPosition(i);
            appViewHolder.bindAppInfo(this.a.get(i), this.d.getAppTabName());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (!ChildModeManager.getManager().isChildMode() && SkillDataManager.TAB_KEY_MINE.equals(this.d.getAppTabKey())) {
            String appName = this.a.get(i).getAppName();
            char c = 65535;
            int hashCode = appName.hashCode();
            if (hashCode != 735243) {
                if (hashCode != 1228230) {
                    if (hashCode == 723689867 && appName.equals(SkillDataManager.APP_GALLERY)) {
                        c = 1;
                    }
                } else if (appName.equals(SkillDataManager.APP_ALARM)) {
                    c = 0;
                }
            } else if (appName.equals(SkillDataManager.APP_WEATHER)) {
                c = 2;
            }
            switch (c) {
                case 0:
                    return 3;
                case 1:
                    return 2;
                case 2:
                    return 1;
            }
        }
        return 0;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.ItemTouchCallBack.OnItemTouchListener
    public void onMove(int i, int i2) {
        if (this.c) {
            if (!SkillDataManager.TAB_KEY_MINE.equals(this.d.getAppTabKey()) || (i2 > 2 && i > 2)) {
                L.homepage.i("swap before : %s", this.a);
                if (i < i2) {
                    int i3 = i;
                    while (i3 < i2) {
                        int i4 = i3 + 1;
                        Collections.swap(this.a, i3, i4);
                        i3 = i4;
                    }
                } else {
                    for (int i5 = i; i5 > i2; i5--) {
                        Collections.swap(this.a, i5, i5 - 1);
                    }
                }
                L.homepage.i("swap after : %s", this.a);
                AppRealmHelper.getInstance().insert(this.a);
                synchronized (RecyclerView.class) {
                    notifyItemMoved(i, i2);
                }
            }
        }
    }

    public void onViewRecycled(@NonNull BaseAppHolder baseAppHolder) {
        super.onViewRecycled((AppAdapter) baseAppHolder);
        baseAppHolder.recycle();
    }
}
