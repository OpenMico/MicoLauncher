package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.versionedparcelable.CustomVersionedParcelable;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.mipush.sdk.Constants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class IconCompat extends CustomVersionedParcelable {
    public static final int TYPE_ADAPTIVE_BITMAP = 5;
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_URI = 4;
    public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
    static final PorterDuff.Mode b = PorterDuff.Mode.SRC_IN;
    Object a;
    PorterDuff.Mode c;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public byte[] mData;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt1;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int mInt2;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Parcelable mParcelable;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mString1;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ColorStateList mTintList;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public String mTintModeStr;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int mType;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface IconType {
    }

    private static String a(int i) {
        switch (i) {
            case 1:
                return "BITMAP";
            case 2:
                return "RESOURCE";
            case 3:
                return "DATA";
            case 4:
                return "URI";
            case 5:
                return "BITMAP_MASKABLE";
            case 6:
                return "URI_MASKABLE";
            default:
                return "UNKNOWN";
        }
    }

    public static IconCompat createWithResource(Context context, @DrawableRes int i) {
        if (context != null) {
            return createWithResource(context.getResources(), context.getPackageName(), i);
        }
        throw new IllegalArgumentException("Context must not be null.");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createWithResource(Resources resources, String str, @DrawableRes int i) {
        if (str == null) {
            throw new IllegalArgumentException("Package must not be null.");
        } else if (i != 0) {
            IconCompat iconCompat = new IconCompat(2);
            iconCompat.mInt1 = i;
            if (resources != null) {
                try {
                    iconCompat.a = resources.getResourceName(i);
                } catch (Resources.NotFoundException unused) {
                    throw new IllegalArgumentException("Icon resource cannot be found");
                }
            } else {
                iconCompat.a = str;
            }
            iconCompat.mString1 = str;
            return iconCompat;
        } else {
            throw new IllegalArgumentException("Drawable resource ID must not be 0");
        }
    }

    public static IconCompat createWithBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            IconCompat iconCompat = new IconCompat(1);
            iconCompat.a = bitmap;
            return iconCompat;
        }
        throw new IllegalArgumentException("Bitmap must not be null.");
    }

    public static IconCompat createWithAdaptiveBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            IconCompat iconCompat = new IconCompat(5);
            iconCompat.a = bitmap;
            return iconCompat;
        }
        throw new IllegalArgumentException("Bitmap must not be null.");
    }

    public static IconCompat createWithData(byte[] bArr, int i, int i2) {
        if (bArr != null) {
            IconCompat iconCompat = new IconCompat(3);
            iconCompat.a = bArr;
            iconCompat.mInt1 = i;
            iconCompat.mInt2 = i2;
            return iconCompat;
        }
        throw new IllegalArgumentException("Data must not be null.");
    }

    public static IconCompat createWithContentUri(String str) {
        if (str != null) {
            IconCompat iconCompat = new IconCompat(4);
            iconCompat.a = str;
            return iconCompat;
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    public static IconCompat createWithContentUri(Uri uri) {
        if (uri != null) {
            return createWithContentUri(uri.toString());
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull String str) {
        if (str != null) {
            IconCompat iconCompat = new IconCompat(6);
            iconCompat.a = str;
            return iconCompat;
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    @NonNull
    public static IconCompat createWithAdaptiveBitmapContentUri(@NonNull Uri uri) {
        if (uri != null) {
            return createWithAdaptiveBitmapContentUri(uri.toString());
        }
        throw new IllegalArgumentException("Uri must not be null.");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public IconCompat() {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.c = b;
        this.mTintModeStr = null;
    }

    private IconCompat(int i) {
        this.mType = -1;
        this.mData = null;
        this.mParcelable = null;
        this.mInt1 = 0;
        this.mInt2 = 0;
        this.mTintList = null;
        this.c = b;
        this.mTintModeStr = null;
        this.mType = i;
    }

    public int getType() {
        if (this.mType != -1 || Build.VERSION.SDK_INT < 23) {
            return this.mType;
        }
        return a((Icon) this.a);
    }

    @NonNull
    public String getResPackage() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return b((Icon) this.a);
        }
        if (this.mType != 2) {
            throw new IllegalStateException("called getResPackage() on " + this);
        } else if (TextUtils.isEmpty(this.mString1)) {
            return ((String) this.a).split(Constants.COLON_SEPARATOR, -1)[0];
        } else {
            return this.mString1;
        }
    }

    @IdRes
    public int getResId() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return c((Icon) this.a);
        }
        if (this.mType == 2) {
            return this.mInt1;
        }
        throw new IllegalStateException("called getResId() on " + this);
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public Bitmap getBitmap() {
        if (this.mType != -1 || Build.VERSION.SDK_INT < 23) {
            int i = this.mType;
            if (i == 1) {
                return (Bitmap) this.a;
            }
            if (i == 5) {
                return a((Bitmap) this.a, true);
            }
            throw new IllegalStateException("called getBitmap() on " + this);
        }
        Object obj = this.a;
        if (obj instanceof Bitmap) {
            return (Bitmap) obj;
        }
        return null;
    }

    @NonNull
    public Uri getUri() {
        if (this.mType == -1 && Build.VERSION.SDK_INT >= 23) {
            return d((Icon) this.a);
        }
        int i = this.mType;
        if (i == 4 || i == 6) {
            return Uri.parse((String) this.a);
        }
        throw new IllegalStateException("called getUri() on " + this);
    }

    public IconCompat setTint(@ColorInt int i) {
        return setTintList(ColorStateList.valueOf(i));
    }

    public IconCompat setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        return this;
    }

    public IconCompat setTintMode(PorterDuff.Mode mode) {
        this.c = mode;
        return this;
    }

    @NonNull
    @RequiresApi(23)
    @Deprecated
    public Icon toIcon() {
        return toIcon(null);
    }

    @NonNull
    @RequiresApi(23)
    public Icon toIcon(@Nullable Context context) {
        Icon icon;
        int i = this.mType;
        if (i == -1) {
            return (Icon) this.a;
        }
        switch (i) {
            case 1:
                icon = Icon.createWithBitmap((Bitmap) this.a);
                break;
            case 2:
                icon = Icon.createWithResource(getResPackage(), this.mInt1);
                break;
            case 3:
                icon = Icon.createWithData((byte[]) this.a, this.mInt1, this.mInt2);
                break;
            case 4:
                icon = Icon.createWithContentUri((String) this.a);
                break;
            case 5:
                if (Build.VERSION.SDK_INT < 26) {
                    icon = Icon.createWithBitmap(a((Bitmap) this.a, false));
                    break;
                } else {
                    icon = Icon.createWithAdaptiveBitmap((Bitmap) this.a);
                    break;
                }
            case 6:
                if (Build.VERSION.SDK_INT >= 30) {
                    icon = Icon.createWithAdaptiveBitmapContentUri(getUri());
                    break;
                } else if (context != null) {
                    InputStream uriInputStream = getUriInputStream(context);
                    if (uriInputStream != null) {
                        if (Build.VERSION.SDK_INT < 26) {
                            icon = Icon.createWithBitmap(a(BitmapFactory.decodeStream(uriInputStream), false));
                            break;
                        } else {
                            icon = Icon.createWithAdaptiveBitmap(BitmapFactory.decodeStream(uriInputStream));
                            break;
                        }
                    } else {
                        throw new IllegalStateException("Cannot load adaptive icon from uri: " + getUri());
                    }
                } else {
                    throw new IllegalArgumentException("Context is required to resolve the file uri of the icon: " + getUri());
                }
            default:
                throw new IllegalArgumentException("Unknown type");
        }
        ColorStateList colorStateList = this.mTintList;
        if (colorStateList != null) {
            icon.setTintList(colorStateList);
        }
        PorterDuff.Mode mode = this.c;
        if (mode != b) {
            icon.setTintMode(mode);
        }
        return icon;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void checkResource(@NonNull Context context) {
        Object obj;
        if (this.mType == 2 && (obj = this.a) != null) {
            String str = (String) obj;
            if (str.contains(Constants.COLON_SEPARATOR)) {
                String str2 = str.split(Constants.COLON_SEPARATOR, -1)[1];
                String str3 = str2.split("/", -1)[0];
                String str4 = str2.split("/", -1)[1];
                String str5 = str.split(Constants.COLON_SEPARATOR, -1)[0];
                if ("0_resource_name_obfuscated".equals(str4)) {
                    Log.i("IconCompat", "Found obfuscated resource, not trying to update resource id for it");
                    return;
                }
                String resPackage = getResPackage();
                int identifier = a(context, resPackage).getIdentifier(str4, str3, str5);
                if (this.mInt1 != identifier) {
                    Log.i("IconCompat", "Id has changed for " + resPackage + StringUtils.SPACE + str);
                    this.mInt1 = identifier;
                }
            }
        }
    }

    @Nullable
    public Drawable loadDrawable(@NonNull Context context) {
        checkResource(context);
        if (Build.VERSION.SDK_INT >= 23) {
            return toIcon(context).loadDrawable(context);
        }
        Drawable a = a(context);
        if (!(a == null || (this.mTintList == null && this.c == b))) {
            a.mutate();
            DrawableCompat.setTintList(a, this.mTintList);
            DrawableCompat.setTintMode(a, this.c);
        }
        return a;
    }

    private Drawable a(Context context) {
        switch (this.mType) {
            case 1:
                return new BitmapDrawable(context.getResources(), (Bitmap) this.a);
            case 2:
                String resPackage = getResPackage();
                if (TextUtils.isEmpty(resPackage)) {
                    resPackage = context.getPackageName();
                }
                try {
                    return ResourcesCompat.getDrawable(a(context, resPackage), this.mInt1, context.getTheme());
                } catch (RuntimeException e) {
                    Log.e("IconCompat", String.format("Unable to load resource 0x%08x from pkg=%s", Integer.valueOf(this.mInt1), this.a), e);
                    break;
                }
            case 3:
                return new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray((byte[]) this.a, this.mInt1, this.mInt2));
            case 4:
                InputStream uriInputStream = getUriInputStream(context);
                if (uriInputStream != null) {
                    return new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(uriInputStream));
                }
                break;
            case 5:
                return new BitmapDrawable(context.getResources(), a((Bitmap) this.a, false));
            case 6:
                InputStream uriInputStream2 = getUriInputStream(context);
                if (uriInputStream2 != null) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        return new AdaptiveIconDrawable(null, new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(uriInputStream2)));
                    }
                    return new BitmapDrawable(context.getResources(), a(BitmapFactory.decodeStream(uriInputStream2), false));
                }
                break;
        }
        return null;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public InputStream getUriInputStream(@NonNull Context context) {
        Uri uri = getUri();
        String scheme = uri.getScheme();
        if ("content".equals(scheme) || "file".equals(scheme)) {
            try {
                return context.getContentResolver().openInputStream(uri);
            } catch (Exception e) {
                Log.w("IconCompat", "Unable to load image from URI: " + uri, e);
                return null;
            }
        } else {
            try {
                return new FileInputStream(new File((String) this.a));
            } catch (FileNotFoundException e2) {
                Log.w("IconCompat", "Unable to load image from path: " + uri, e2);
                return null;
            }
        }
    }

    private static Resources a(Context context, String str) {
        if ("android".equals(str)) {
            return Resources.getSystem();
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 8192);
            if (applicationInfo != null) {
                return packageManager.getResourcesForApplication(applicationInfo);
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("IconCompat", String.format("Unable to find pkg=%s for icon", str), e);
            return null;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void addToShortcutIntent(@NonNull Intent intent, @Nullable Drawable drawable, @NonNull Context context) {
        Bitmap bitmap;
        checkResource(context);
        int i = this.mType;
        if (i != 5) {
            switch (i) {
                case 1:
                    bitmap = (Bitmap) this.a;
                    if (drawable != null) {
                        bitmap = bitmap.copy(bitmap.getConfig(), true);
                        break;
                    }
                    break;
                case 2:
                    try {
                        Context createPackageContext = context.createPackageContext(getResPackage(), 0);
                        if (drawable == null) {
                            intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(createPackageContext, this.mInt1));
                            return;
                        }
                        Drawable drawable2 = ContextCompat.getDrawable(createPackageContext, this.mInt1);
                        if (drawable2.getIntrinsicWidth() > 0 && drawable2.getIntrinsicHeight() > 0) {
                            bitmap = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            drawable2.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                            drawable2.draw(new Canvas(bitmap));
                            break;
                        }
                        int launcherLargeIconSize = ((ActivityManager) createPackageContext.getSystemService(IDMServer.PERSIST_TYPE_ACTIVITY)).getLauncherLargeIconSize();
                        bitmap = Bitmap.createBitmap(launcherLargeIconSize, launcherLargeIconSize, Bitmap.Config.ARGB_8888);
                        drawable2.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        drawable2.draw(new Canvas(bitmap));
                    } catch (PackageManager.NameNotFoundException e) {
                        throw new IllegalArgumentException("Can't find package " + this.a, e);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
            }
        } else {
            bitmap = a((Bitmap) this.a, true);
        }
        if (drawable != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            drawable.setBounds(width / 2, height / 2, width, height);
            drawable.draw(new Canvas(bitmap));
        }
        intent.putExtra("android.intent.extra.shortcut.ICON", bitmap);
    }

    @NonNull
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        int i = this.mType;
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    bundle.putParcelable("obj", (Bitmap) this.a);
                    break;
                case 2:
                case 4:
                case 6:
                    bundle.putString("obj", (String) this.a);
                    break;
                case 3:
                    bundle.putByteArray("obj", (byte[]) this.a);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid icon");
            }
        } else {
            bundle.putParcelable("obj", (Parcelable) this.a);
        }
        bundle.putInt("type", this.mType);
        bundle.putInt("int1", this.mInt1);
        bundle.putInt("int2", this.mInt2);
        bundle.putString("string1", this.mString1);
        ColorStateList colorStateList = this.mTintList;
        if (colorStateList != null) {
            bundle.putParcelable("tint_list", colorStateList);
        }
        PorterDuff.Mode mode = this.c;
        if (mode != b) {
            bundle.putString("tint_mode", mode.name());
        }
        return bundle;
    }

    @NonNull
    public String toString() {
        if (this.mType == -1) {
            return String.valueOf(this.a);
        }
        StringBuilder sb = new StringBuilder("Icon(typ=");
        sb.append(a(this.mType));
        switch (this.mType) {
            case 1:
            case 5:
                sb.append(" size=");
                sb.append(((Bitmap) this.a).getWidth());
                sb.append("x");
                sb.append(((Bitmap) this.a).getHeight());
                break;
            case 2:
                sb.append(" pkg=");
                sb.append(this.mString1);
                sb.append(" id=");
                sb.append(String.format("0x%08x", Integer.valueOf(getResId())));
                break;
            case 3:
                sb.append(" len=");
                sb.append(this.mInt1);
                if (this.mInt2 != 0) {
                    sb.append(" off=");
                    sb.append(this.mInt2);
                    break;
                }
                break;
            case 4:
            case 6:
                sb.append(" uri=");
                sb.append(this.a);
                break;
        }
        if (this.mTintList != null) {
            sb.append(" tint=");
            sb.append(this.mTintList);
        }
        if (this.c != b) {
            sb.append(" mode=");
            sb.append(this.c);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPreParceling(boolean z) {
        this.mTintModeStr = this.c.name();
        int i = this.mType;
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    if (z) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ((Bitmap) this.a).compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                        this.mData = byteArrayOutputStream.toByteArray();
                        return;
                    }
                    this.mParcelable = (Parcelable) this.a;
                    return;
                case 2:
                    this.mData = ((String) this.a).getBytes(Charset.forName("UTF-16"));
                    return;
                case 3:
                    this.mData = (byte[]) this.a;
                    return;
                case 4:
                case 6:
                    this.mData = this.a.toString().getBytes(Charset.forName("UTF-16"));
                    return;
                default:
                    return;
            }
        } else if (!z) {
            this.mParcelable = (Parcelable) this.a;
        } else {
            throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
        }
    }

    @Override // androidx.versionedparcelable.CustomVersionedParcelable
    public void onPostParceling() {
        this.c = PorterDuff.Mode.valueOf(this.mTintModeStr);
        int i = this.mType;
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    Parcelable parcelable = this.mParcelable;
                    if (parcelable != null) {
                        this.a = parcelable;
                        return;
                    }
                    byte[] bArr = this.mData;
                    this.a = bArr;
                    this.mType = 3;
                    this.mInt1 = 0;
                    this.mInt2 = bArr.length;
                    return;
                case 2:
                case 4:
                case 6:
                    this.a = new String(this.mData, Charset.forName("UTF-16"));
                    if (this.mType == 2 && this.mString1 == null) {
                        this.mString1 = ((String) this.a).split(Constants.COLON_SEPARATOR, -1)[0];
                        return;
                    }
                    return;
                case 3:
                    this.a = this.mData;
                    return;
                default:
                    return;
            }
        } else {
            Parcelable parcelable2 = this.mParcelable;
            if (parcelable2 != null) {
                this.a = parcelable2;
                return;
            }
            throw new IllegalArgumentException("Invalid icon");
        }
    }

    @Nullable
    public static IconCompat createFromBundle(@NonNull Bundle bundle) {
        int i = bundle.getInt("type");
        IconCompat iconCompat = new IconCompat(i);
        iconCompat.mInt1 = bundle.getInt("int1");
        iconCompat.mInt2 = bundle.getInt("int2");
        iconCompat.mString1 = bundle.getString("string1");
        if (bundle.containsKey("tint_list")) {
            iconCompat.mTintList = (ColorStateList) bundle.getParcelable("tint_list");
        }
        if (bundle.containsKey("tint_mode")) {
            iconCompat.c = PorterDuff.Mode.valueOf(bundle.getString("tint_mode"));
        }
        if (i != -1) {
            switch (i) {
                case 1:
                case 5:
                    break;
                case 2:
                case 4:
                case 6:
                    iconCompat.a = bundle.getString("obj");
                    break;
                case 3:
                    iconCompat.a = bundle.getByteArray("obj");
                    break;
                default:
                    Log.w("IconCompat", "Unknown type " + i);
                    return null;
            }
            return iconCompat;
        }
        iconCompat.a = bundle.getParcelable("obj");
        return iconCompat;
    }

    @Nullable
    @RequiresApi(23)
    public static IconCompat createFromIcon(@NonNull Context context, @NonNull Icon icon) {
        Preconditions.checkNotNull(icon);
        int a = a(icon);
        if (a == 2) {
            String b2 = b(icon);
            try {
                return createWithResource(a(context, b2), b2, c(icon));
            } catch (Resources.NotFoundException unused) {
                throw new IllegalArgumentException("Icon resource cannot be found");
            }
        } else if (a == 4) {
            return createWithContentUri(d(icon));
        } else {
            if (a == 6) {
                return createWithAdaptiveBitmapContentUri(d(icon));
            }
            IconCompat iconCompat = new IconCompat(-1);
            iconCompat.a = icon;
            return iconCompat;
        }
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIcon(@NonNull Icon icon) {
        Preconditions.checkNotNull(icon);
        int a = a(icon);
        if (a == 2) {
            return createWithResource(null, b(icon), c(icon));
        }
        if (a == 4) {
            return createWithContentUri(d(icon));
        }
        if (a == 6) {
            return createWithAdaptiveBitmapContentUri(d(icon));
        }
        IconCompat iconCompat = new IconCompat(-1);
        iconCompat.a = icon;
        return iconCompat;
    }

    @Nullable
    @RequiresApi(23)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static IconCompat createFromIconOrNullIfZeroResId(@NonNull Icon icon) {
        if (a(icon) == 2 && c(icon) == 0) {
            return null;
        }
        return createFromIcon(icon);
    }

    @RequiresApi(23)
    private static int a(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getType();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getType", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException e) {
            Log.e("IconCompat", "Unable to get icon type " + icon, e);
            return -1;
        } catch (NoSuchMethodException e2) {
            Log.e("IconCompat", "Unable to get icon type " + icon, e2);
            return -1;
        } catch (InvocationTargetException e3) {
            Log.e("IconCompat", "Unable to get icon type " + icon, e3);
            return -1;
        }
    }

    @Nullable
    @RequiresApi(23)
    private static String b(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResPackage();
        }
        try {
            return (String) icon.getClass().getMethod("getResPackage", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException e) {
            Log.e("IconCompat", "Unable to get icon package", e);
            return null;
        } catch (NoSuchMethodException e2) {
            Log.e("IconCompat", "Unable to get icon package", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e("IconCompat", "Unable to get icon package", e3);
            return null;
        }
    }

    @DrawableRes
    @IdRes
    @RequiresApi(23)
    private static int c(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getResId();
        }
        try {
            return ((Integer) icon.getClass().getMethod("getResId", new Class[0]).invoke(icon, new Object[0])).intValue();
        } catch (IllegalAccessException e) {
            Log.e("IconCompat", "Unable to get icon resource", e);
            return 0;
        } catch (NoSuchMethodException e2) {
            Log.e("IconCompat", "Unable to get icon resource", e2);
            return 0;
        } catch (InvocationTargetException e3) {
            Log.e("IconCompat", "Unable to get icon resource", e3);
            return 0;
        }
    }

    @Nullable
    @RequiresApi(23)
    private static Uri d(@NonNull Icon icon) {
        if (Build.VERSION.SDK_INT >= 28) {
            return icon.getUri();
        }
        try {
            return (Uri) icon.getClass().getMethod("getUri", new Class[0]).invoke(icon, new Object[0]);
        } catch (IllegalAccessException e) {
            Log.e("IconCompat", "Unable to get icon uri", e);
            return null;
        } catch (NoSuchMethodException e2) {
            Log.e("IconCompat", "Unable to get icon uri", e2);
            return null;
        } catch (InvocationTargetException e3) {
            Log.e("IconCompat", "Unable to get icon uri", e3);
            return null;
        }
    }

    @VisibleForTesting
    static Bitmap a(Bitmap bitmap, boolean z) {
        int min = (int) (Math.min(bitmap.getWidth(), bitmap.getHeight()) * 0.6666667f);
        Bitmap createBitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(3);
        float f = min;
        float f2 = 0.5f * f;
        float f3 = 0.9166667f * f2;
        if (z) {
            float f4 = 0.010416667f * f;
            paint.setColor(0);
            paint.setShadowLayer(f4, 0.0f, f * 0.020833334f, 1023410176);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.setShadowLayer(f4, 0.0f, 0.0f, 503316480);
            canvas.drawCircle(f2, f2, f3, paint);
            paint.clearShadowLayer();
        }
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setTranslate((-(bitmap.getWidth() - min)) / 2, (-(bitmap.getHeight() - min)) / 2);
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        canvas.drawCircle(f2, f2, f3, paint);
        canvas.setBitmap(null);
        return createBitmap;
    }
}
