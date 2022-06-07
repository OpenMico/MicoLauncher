package com.blankj.utilcode.util;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public final class KeyboardUtils {
    private static long a;
    private static int b;

    /* loaded from: classes.dex */
    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int i);
    }

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(2, 1);
        }
    }

    public static void showSoftInput(@NonNull Activity activity) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (!isSoftInputVisible(activity)) {
            toggleSoftInput();
        }
    }

    public static void showSoftInput(@NonNull View view) {
        if (view != null) {
            showSoftInput(view, 0);
            return;
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void showSoftInput(@NonNull View view, int i) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
            if (inputMethodManager != null) {
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                inputMethodManager.showSoftInput(view, i, new ResultReceiver(new Handler()) { // from class: com.blankj.utilcode.util.KeyboardUtils.1
                    @Override // android.os.ResultReceiver
                    protected void onReceiveResult(int i2, Bundle bundle) {
                        if (i2 == 1 || i2 == 3) {
                            KeyboardUtils.toggleSoftInput();
                        }
                    }
                });
                inputMethodManager.toggleSoftInput(2, 1);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void hideSoftInput(@NonNull Activity activity) {
        if (activity != null) {
            hideSoftInput(activity.getWindow());
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void hideSoftInput(@NonNull Window window) {
        if (window != null) {
            View currentFocus = window.getCurrentFocus();
            if (currentFocus == null) {
                View decorView = window.getDecorView();
                View findViewWithTag = decorView.findViewWithTag("keyboardTagView");
                if (findViewWithTag == null) {
                    EditText editText = new EditText(window.getContext());
                    editText.setTag("keyboardTagView");
                    ((ViewGroup) decorView).addView(editText, 0, 0);
                    currentFocus = editText;
                } else {
                    currentFocus = findViewWithTag;
                }
                currentFocus.requestFocus();
            }
            hideSoftInput(currentFocus);
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void hideSoftInput(@NonNull View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void hideSoftInputByToggle(Activity activity) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (Math.abs(elapsedRealtime - a) > 500 && isSoftInputVisible(activity)) {
            toggleSoftInput();
        }
        a = elapsedRealtime;
    }

    public static void toggleSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(0, 0);
        }
    }

    public static boolean isSoftInputVisible(@NonNull Activity activity) {
        if (activity != null) {
            return c(activity.getWindow()) > 0;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int c(@NonNull Window window) {
        if (window != null) {
            View decorView = window.getDecorView();
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            Log.d("KeyboardUtils", "getDecorViewInvisibleHeight: " + (decorView.getBottom() - rect.bottom));
            int abs = Math.abs(decorView.getBottom() - rect.bottom);
            if (abs > b.k() + b.j()) {
                return abs - b;
            }
            b = abs;
            return 0;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void registerSoftInputChangedListener(@NonNull Activity activity, @NonNull OnSoftInputChangedListener onSoftInputChangedListener) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (onSoftInputChangedListener != null) {
            registerSoftInputChangedListener(activity.getWindow(), onSoftInputChangedListener);
        } else {
            throw new NullPointerException("Argument 'listener' of type OnSoftInputChangedListener (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void registerSoftInputChangedListener(@NonNull final Window window, @NonNull final OnSoftInputChangedListener onSoftInputChangedListener) {
        if (window == null) {
            throw new NullPointerException("Argument 'window' of type Window (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (onSoftInputChangedListener != null) {
            if ((window.getAttributes().flags & 512) != 0) {
                window.clearFlags(512);
            }
            FrameLayout frameLayout = (FrameLayout) window.findViewById(16908290);
            final int[] iArr = {c(window)};
            ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.blankj.utilcode.util.KeyboardUtils.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    int c = KeyboardUtils.c(window);
                    if (iArr[0] != c) {
                        onSoftInputChangedListener.onSoftInputChanged(c);
                        iArr[0] = c;
                    }
                }
            };
            frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
            frameLayout.setTag(-8, onGlobalLayoutListener);
        } else {
            throw new NullPointerException("Argument 'listener' of type OnSoftInputChangedListener (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public static void unregisterSoftInputChangedListener(@NonNull Window window) {
        if (window != null) {
            View findViewById = window.findViewById(16908290);
            if (findViewById != null) {
                Object tag = findViewById.getTag(-8);
                if ((tag instanceof ViewTreeObserver.OnGlobalLayoutListener) && Build.VERSION.SDK_INT >= 16) {
                    findViewById.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener) tag);
                    return;
                }
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void fixAndroidBug5497(@NonNull Activity activity) {
        if (activity != null) {
            fixAndroidBug5497(activity.getWindow());
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void fixAndroidBug5497(@NonNull final Window window) {
        if (window != null) {
            window.setSoftInputMode(window.getAttributes().softInputMode & (-17));
            FrameLayout frameLayout = (FrameLayout) window.findViewById(16908290);
            final View childAt = frameLayout.getChildAt(0);
            final int paddingBottom = childAt.getPaddingBottom();
            final int[] iArr = {d(window)};
            frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.blankj.utilcode.util.KeyboardUtils.3
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    int d = KeyboardUtils.d(window);
                    if (iArr[0] != d) {
                        View view = childAt;
                        view.setPadding(view.getPaddingLeft(), childAt.getPaddingTop(), childAt.getPaddingRight(), paddingBottom + KeyboardUtils.c(window));
                        iArr[0] = d;
                    }
                }
            });
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int d(Window window) {
        View findViewById = window.findViewById(16908290);
        if (findViewById == null) {
            return 0;
        }
        Rect rect = new Rect();
        findViewById.getWindowVisibleDisplayFrame(rect);
        Log.d("KeyboardUtils", "getContentViewInvisibleHeight: " + (findViewById.getBottom() - rect.bottom));
        int abs = Math.abs(findViewById.getBottom() - rect.bottom);
        if (abs <= b.j() + b.k()) {
            return 0;
        }
        return abs;
    }

    public static void fixSoftInputLeaks(@NonNull Activity activity) {
        if (activity != null) {
            fixSoftInputLeaks(activity.getWindow());
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void fixSoftInputLeaks(@NonNull Window window) {
        if (window != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) Utils.getApp().getSystemService("input_method");
            if (inputMethodManager != null) {
                for (String str : new String[]{"mLastSrvView", "mCurRootView", "mServedView", "mNextServedView"}) {
                    try {
                        Field declaredField = InputMethodManager.class.getDeclaredField(str);
                        if (!declaredField.isAccessible()) {
                            declaredField.setAccessible(true);
                        }
                        Object obj = declaredField.get(inputMethodManager);
                        if ((obj instanceof View) && ((View) obj).getRootView() == window.getDecorView().getRootView()) {
                            declaredField.set(inputMethodManager, null);
                        }
                    } catch (Throwable unused) {
                    }
                }
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'window' of type Window (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void clickBlankArea2HideSoftInput() {
        Log.i("KeyboardUtils", "Please refer to the following code.");
    }
}
