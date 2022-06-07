package com.uc.crashsdk.a;

import com.uc.crashsdk.a;
import com.uc.crashsdk.b;
import com.uc.crashsdk.f;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class e implements Runnable {
    static final /* synthetic */ boolean a = !e.class.desiredAssertionStatus();
    private final int b;
    private final Object[] c;

    public e(int i) {
        this.b = i;
        this.c = null;
    }

    public e(int i, Object[] objArr) {
        this.b = i;
        this.c = objArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        int i = this.b;
        if (i == 10) {
            f.a(i, this.c);
        } else if (i == 500) {
            d.a(i);
        } else if (i == 700) {
            f.b(i);
        } else if (i != 800) {
            switch (i) {
                case 100:
                case 101:
                case 102:
                case 103:
                case 104:
                    b.a(i);
                    return;
                default:
                    switch (i) {
                        case 201:
                        case 202:
                            a.a(i);
                            return;
                        default:
                            switch (i) {
                                case 301:
                                case 302:
                                case 303:
                                    h.a(i, this.c);
                                    return;
                                default:
                                    switch (i) {
                                        case 401:
                                        case 402:
                                        case 403:
                                            break;
                                        default:
                                            switch (i) {
                                                case 405:
                                                case 406:
                                                case 407:
                                                case 408:
                                                case 409:
                                                case 410:
                                                case 411:
                                                case 412:
                                                case 413:
                                                case 414:
                                                case 415:
                                                case 416:
                                                    break;
                                                default:
                                                    a.d("crashsdk", "Unknown async runnable: " + toString());
                                                    if (!a) {
                                                        throw new AssertionError();
                                                    }
                                                    return;
                                            }
                                    }
                                    com.uc.crashsdk.e.a(this.b, this.c);
                                    return;
                            }
                    }
            }
        } else {
            g.a(i);
        }
    }

    public final boolean a() {
        int i = this.b;
        switch (i) {
            case 351:
            case 352:
            case 353:
            case 354:
                return h.b(i, this.c);
            default:
                switch (i) {
                    case 451:
                    case 452:
                        return com.uc.crashsdk.e.b(i, this.c);
                    default:
                        switch (i) {
                            case 751:
                            case 752:
                            case 753:
                                return f.a(i, this.c);
                            default:
                                a.d("crashsdk", "Unknown sync runnable: " + toString());
                                if (a) {
                                    return false;
                                }
                                throw new AssertionError();
                        }
                }
        }
    }

    public String toString() {
        return super.toString() + "@action_" + this.b;
    }
}
