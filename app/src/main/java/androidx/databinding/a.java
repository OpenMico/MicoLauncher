package androidx.databinding;

import androidx.databinding.Observable;

/* compiled from: BaseObservableField.java */
/* loaded from: classes.dex */
abstract class a extends BaseObservable {
    public a() {
    }

    public a(Observable... observableArr) {
        if (!(observableArr == null || observableArr.length == 0)) {
            C0014a aVar = new C0014a();
            for (Observable observable : observableArr) {
                observable.addOnPropertyChangedCallback(aVar);
            }
        }
    }

    /* compiled from: BaseObservableField.java */
    /* renamed from: androidx.databinding.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    class C0014a extends Observable.OnPropertyChangedCallback {
        C0014a() {
        }

        @Override // androidx.databinding.Observable.OnPropertyChangedCallback
        public void onPropertyChanged(Observable observable, int i) {
            a.this.notifyChange();
        }
    }
}
