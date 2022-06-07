package com.google.android.material.navigation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ParcelableSparseArray;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class NavigationBarPresenter implements MenuPresenter {
    private MenuBuilder a;
    private NavigationBarMenuView b;
    private boolean c = false;
    private int d;

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean collapseItemActionView(@Nullable MenuBuilder menuBuilder, @Nullable MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean expandItemActionView(@Nullable MenuBuilder menuBuilder, @Nullable MenuItemImpl menuItemImpl) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean flagActionItems() {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onCloseMenu(@Nullable MenuBuilder menuBuilder, boolean z) {
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public boolean onSubMenuSelected(@Nullable SubMenuBuilder subMenuBuilder) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void setCallback(@Nullable MenuPresenter.Callback callback) {
    }

    public void setMenuView(@NonNull NavigationBarMenuView navigationBarMenuView) {
        this.b = navigationBarMenuView;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void initForMenu(@NonNull Context context, @NonNull MenuBuilder menuBuilder) {
        this.a = menuBuilder;
        this.b.initialize(this.a);
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    @Nullable
    public MenuView getMenuView(@Nullable ViewGroup viewGroup) {
        return this.b;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        if (!this.c) {
            if (z) {
                this.b.buildMenuView();
            } else {
                this.b.updateMenuView();
            }
        }
    }

    public void setId(int i) {
        this.d = i;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public int getId() {
        return this.d;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    @NonNull
    public Parcelable onSaveInstanceState() {
        a aVar = new a();
        aVar.a = this.b.getSelectedItemId();
        aVar.b = BadgeUtils.createParcelableBadgeStates(this.b.getBadgeDrawables());
        return aVar;
    }

    @Override // androidx.appcompat.view.menu.MenuPresenter
    public void onRestoreInstanceState(@NonNull Parcelable parcelable) {
        if (parcelable instanceof a) {
            a aVar = (a) parcelable;
            this.b.a(aVar.a);
            this.b.setBadgeDrawables(BadgeUtils.createBadgeDrawablesFromSavedStates(this.b.getContext(), aVar.b));
        }
    }

    public void setUpdateSuspended(boolean z) {
        this.c = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class a implements Parcelable {
        public static final Parcelable.Creator<a> CREATOR = new Parcelable.Creator<a>() { // from class: com.google.android.material.navigation.NavigationBarPresenter.a.1
            @NonNull
            /* renamed from: a */
            public a createFromParcel(@NonNull Parcel parcel) {
                return new a(parcel);
            }

            @NonNull
            /* renamed from: a */
            public a[] newArray(int i) {
                return new a[i];
            }
        };
        int a;
        @Nullable
        ParcelableSparseArray b;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        a() {
        }

        a(@NonNull Parcel parcel) {
            this.a = parcel.readInt();
            this.b = (ParcelableSparseArray) parcel.readParcelable(getClass().getClassLoader());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeParcelable(this.b, 0);
        }
    }
}
