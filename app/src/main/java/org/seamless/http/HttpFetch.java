package org.seamless.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.seamless.util.io.IO;

/* loaded from: classes4.dex */
public class HttpFetch {

    /* loaded from: classes4.dex */
    public interface RepresentationFactory<E> {
        Representation<E> createRepresentation(URLConnection uRLConnection, InputStream inputStream) throws IOException;
    }

    public static Representation<byte[]> fetchBinary(URL url) throws IOException {
        return fetchBinary(url, 500, 500);
    }

    public static Representation<byte[]> fetchBinary(URL url, int i, int i2) throws IOException {
        return fetch(url, i, i2, new RepresentationFactory<byte[]>() { // from class: org.seamless.http.HttpFetch.1
            @Override // org.seamless.http.HttpFetch.RepresentationFactory
            public Representation<byte[]> createRepresentation(URLConnection uRLConnection, InputStream inputStream) throws IOException {
                return new Representation<>(uRLConnection, IO.readBytes(inputStream));
            }
        });
    }

    public static Representation<String> fetchString(URL url, int i, int i2) throws IOException {
        return fetch(url, i, i2, new RepresentationFactory<String>() { // from class: org.seamless.http.HttpFetch.2
            @Override // org.seamless.http.HttpFetch.RepresentationFactory
            public Representation<String> createRepresentation(URLConnection uRLConnection, InputStream inputStream) throws IOException {
                return new Representation<>(uRLConnection, IO.readLines(inputStream));
            }
        });
    }

    public static <E> Representation<E> fetch(URL url, int i, int i2, RepresentationFactory<E> representationFactory) throws IOException {
        return fetch(url, "GET", i, i2, representationFactory);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0042: IF  (r0 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:18:0x0047, block:B:16:0x0042
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public static <E> org.seamless.http.Representation<E> fetch(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0042: IF  (r0 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:18:0x0047, block:B:16:0x0042
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r1v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    public static void validate(URL url) throws IOException {
        fetch(url, "HEAD", 500, 500, new RepresentationFactory() { // from class: org.seamless.http.HttpFetch.3
            @Override // org.seamless.http.HttpFetch.RepresentationFactory
            public Representation createRepresentation(URLConnection uRLConnection, InputStream inputStream) throws IOException {
                return new Representation(uRLConnection, null);
            }
        });
    }
}
