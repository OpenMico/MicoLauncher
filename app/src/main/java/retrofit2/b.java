package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.CallAdapter;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: CompletableFutureCallAdapterFactory.java */
@IgnoreJRERequirement
/* loaded from: classes6.dex */
public final class b extends CallAdapter.Factory {
    static final CallAdapter.Factory a = new b();

    b() {
    }

    @Override // retrofit2.CallAdapter.Factory
    @Nullable
    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        if (getRawType(type) != CompletableFuture.class) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            Type parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) type);
            if (getRawType(parameterUpperBound) != Response.class) {
                return new a(parameterUpperBound);
            }
            if (parameterUpperBound instanceof ParameterizedType) {
                return new c(getParameterUpperBound(0, (ParameterizedType) parameterUpperBound));
            }
            throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
        }
        throw new IllegalStateException("CompletableFuture return type must be parameterized as CompletableFuture<Foo> or CompletableFuture<? extends Foo>");
    }

    /* compiled from: CompletableFutureCallAdapterFactory.java */
    @IgnoreJRERequirement
    /* loaded from: classes6.dex */
    private static final class a<R> implements CallAdapter<R, CompletableFuture<R>> {
        private final Type a;

        a(Type type) {
            this.a = type;
        }

        @Override // retrofit2.CallAdapter
        public Type responseType() {
            return this.a;
        }

        /* renamed from: a */
        public CompletableFuture<R> adapt(Call<R> call) {
            C0392b bVar = new C0392b(call);
            call.enqueue(new C0391a(bVar));
            return bVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: CompletableFutureCallAdapterFactory.java */
        @IgnoreJRERequirement
        /* renamed from: retrofit2.b$a$a  reason: collision with other inner class name */
        /* loaded from: classes6.dex */
        public class C0391a implements Callback<R> {
            private final CompletableFuture<R> b;

            public C0391a(CompletableFuture<R> completableFuture) {
                this.b = completableFuture;
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<R> call, Response<R> response) {
                if (response.isSuccessful()) {
                    this.b.complete(response.body());
                } else {
                    this.b.completeExceptionally(new HttpException(response));
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<R> call, Throwable th) {
                this.b.completeExceptionally(th);
            }
        }
    }

    /* compiled from: CompletableFutureCallAdapterFactory.java */
    @IgnoreJRERequirement
    /* loaded from: classes6.dex */
    private static final class c<R> implements CallAdapter<R, CompletableFuture<Response<R>>> {
        private final Type a;

        c(Type type) {
            this.a = type;
        }

        @Override // retrofit2.CallAdapter
        public Type responseType() {
            return this.a;
        }

        /* renamed from: a */
        public CompletableFuture<Response<R>> adapt(Call<R> call) {
            C0392b bVar = new C0392b(call);
            call.enqueue(new a(bVar));
            return bVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: CompletableFutureCallAdapterFactory.java */
        @IgnoreJRERequirement
        /* loaded from: classes6.dex */
        public class a implements Callback<R> {
            private final CompletableFuture<Response<R>> b;

            public a(CompletableFuture<Response<R>> completableFuture) {
                this.b = completableFuture;
            }

            @Override // retrofit2.Callback
            public void onResponse(Call<R> call, Response<R> response) {
                this.b.complete(response);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<R> call, Throwable th) {
                this.b.completeExceptionally(th);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: CompletableFutureCallAdapterFactory.java */
    @IgnoreJRERequirement
    /* renamed from: retrofit2.b$b  reason: collision with other inner class name */
    /* loaded from: classes6.dex */
    public static final class C0392b<T> extends CompletableFuture<T> {
        private final Call<?> a;

        C0392b(Call<?> call) {
            this.a = call;
        }

        @Override // java.util.concurrent.CompletableFuture, java.util.concurrent.Future
        public boolean cancel(boolean z) {
            if (z) {
                this.a.cancel();
            }
            return super.cancel(z);
        }
    }
}
