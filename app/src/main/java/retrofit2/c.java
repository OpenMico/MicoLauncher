package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import okhttp3.Request;
import okio.Timeout;
import retrofit2.CallAdapter;
import retrofit2.c;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DefaultCallAdapterFactory.java */
/* loaded from: classes6.dex */
public final class c extends CallAdapter.Factory {
    @Nullable
    private final Executor a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(@Nullable Executor executor) {
        this.a = executor;
    }

    @Override // retrofit2.CallAdapter.Factory
    @Nullable
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        final Executor executor = null;
        if (getRawType(type) != Call.class) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            final Type a2 = m.a(0, (ParameterizedType) type);
            if (!m.a(annotationArr, (Class<? extends Annotation>) SkipCallbackExecutor.class)) {
                executor = this.a;
            }
            return new CallAdapter<Object, Call<?>>() { // from class: retrofit2.c.1
                @Override // retrofit2.CallAdapter
                public Type responseType() {
                    return a2;
                }

                /* renamed from: a */
                public Call<Object> adapt(Call<Object> call) {
                    Executor executor2 = executor;
                    return executor2 == null ? call : new a(executor2, call);
                }
            };
        }
        throw new IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: DefaultCallAdapterFactory.java */
    /* loaded from: classes6.dex */
    public static final class a<T> implements Call<T> {
        final Executor a;
        final Call<T> b;

        a(Executor executor, Call<T> call) {
            this.a = executor;
            this.b = call;
        }

        @Override // retrofit2.Call
        public void enqueue(Callback<T> callback) {
            Objects.requireNonNull(callback, "callback == null");
            this.b.enqueue(new AnonymousClass1(callback));
        }

        /* compiled from: DefaultCallAdapterFactory.java */
        /* renamed from: retrofit2.c$a$1  reason: invalid class name */
        /* loaded from: classes6.dex */
        class AnonymousClass1 implements Callback<T> {
            final /* synthetic */ Callback a;

            AnonymousClass1(Callback callback) {
                this.a = callback;
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<T> call, final Response<T> response) {
                Executor executor = a.this.a;
                final Callback callback = this.a;
                executor.execute(new Runnable() { // from class: retrofit2.-$$Lambda$c$a$1$RrQlzgefmxZxVn4EIshvzlZpEC8
                    @Override // java.lang.Runnable
                    public final void run() {
                        c.a.AnonymousClass1.this.a(callback, response);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void a(Callback callback, Response response) {
                if (a.this.b.isCanceled()) {
                    callback.onFailure(a.this, new IOException("Canceled"));
                } else {
                    callback.onResponse(a.this, response);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void a(Callback callback, Throwable th) {
                callback.onFailure(a.this, th);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<T> call, final Throwable th) {
                Executor executor = a.this.a;
                final Callback callback = this.a;
                executor.execute(new Runnable() { // from class: retrofit2.-$$Lambda$c$a$1$BLOOmnFlUykHOfI5rBW9cfYeG3A
                    @Override // java.lang.Runnable
                    public final void run() {
                        c.a.AnonymousClass1.this.a(callback, th);
                    }
                });
            }
        }

        @Override // retrofit2.Call
        public boolean isExecuted() {
            return this.b.isExecuted();
        }

        @Override // retrofit2.Call
        public Response<T> execute() throws IOException {
            return this.b.execute();
        }

        @Override // retrofit2.Call
        public void cancel() {
            this.b.cancel();
        }

        @Override // retrofit2.Call
        public boolean isCanceled() {
            return this.b.isCanceled();
        }

        @Override // retrofit2.Call
        public Call<T> clone() {
            return new a(this.a, this.b.clone());
        }

        @Override // retrofit2.Call
        public Request request() {
            return this.b.request();
        }

        @Override // retrofit2.Call
        public Timeout timeout() {
            return this.b.timeout();
        }
    }
}
