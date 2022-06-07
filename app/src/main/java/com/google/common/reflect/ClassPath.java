package com.google.common.reflect;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.base.StandardSystemProperty;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes2.dex */
public final class ClassPath {
    private static final Logger a = Logger.getLogger(ClassPath.class.getName());
    private static final Predicate<ClassInfo> b = new Predicate<ClassInfo>() { // from class: com.google.common.reflect.ClassPath.1
        /* renamed from: a */
        public boolean apply(ClassInfo classInfo) {
            return classInfo.b.indexOf(36) == -1;
        }
    };
    private static final Splitter c = Splitter.on(StringUtils.SPACE).omitEmptyStrings();
    private final ImmutableSet<ResourceInfo> d;

    private ClassPath(ImmutableSet<ResourceInfo> immutableSet) {
        this.d = immutableSet;
    }

    public static ClassPath from(ClassLoader classLoader) throws IOException {
        a aVar = new a();
        aVar.a(classLoader);
        return new ClassPath(aVar.a());
    }

    public ImmutableSet<ResourceInfo> getResources() {
        return this.d;
    }

    public ImmutableSet<ClassInfo> getAllClasses() {
        return FluentIterable.from(this.d).filter(ClassInfo.class).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses() {
        return FluentIterable.from(this.d).filter(ClassInfo.class).filter(b).toSet();
    }

    public ImmutableSet<ClassInfo> getTopLevelClasses(String str) {
        Preconditions.checkNotNull(str);
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator<ClassInfo> it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo next = it.next();
            if (next.getPackageName().equals(str)) {
                builder.add((ImmutableSet.Builder) next);
            }
        }
        return builder.build();
    }

    public ImmutableSet<ClassInfo> getTopLevelClassesRecursive(String str) {
        Preconditions.checkNotNull(str);
        String str2 = str + '.';
        ImmutableSet.Builder builder = ImmutableSet.builder();
        UnmodifiableIterator<ClassInfo> it = getTopLevelClasses().iterator();
        while (it.hasNext()) {
            ClassInfo next = it.next();
            if (next.getName().startsWith(str2)) {
                builder.add((ImmutableSet.Builder) next);
            }
        }
        return builder.build();
    }

    @Beta
    /* loaded from: classes2.dex */
    public static class ResourceInfo {
        final ClassLoader a;
        private final String b;

        static ResourceInfo a(String str, ClassLoader classLoader) {
            if (str.endsWith(".class")) {
                return new ClassInfo(str, classLoader);
            }
            return new ResourceInfo(str, classLoader);
        }

        ResourceInfo(String str, ClassLoader classLoader) {
            this.b = (String) Preconditions.checkNotNull(str);
            this.a = (ClassLoader) Preconditions.checkNotNull(classLoader);
        }

        public final URL url() {
            URL resource = this.a.getResource(this.b);
            if (resource != null) {
                return resource;
            }
            throw new NoSuchElementException(this.b);
        }

        public final ByteSource asByteSource() {
            return Resources.asByteSource(url());
        }

        public final CharSource asCharSource(Charset charset) {
            return Resources.asCharSource(url(), charset);
        }

        public final String getResourceName() {
            return this.b;
        }

        public int hashCode() {
            return this.b.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ResourceInfo)) {
                return false;
            }
            ResourceInfo resourceInfo = (ResourceInfo) obj;
            return this.b.equals(resourceInfo.b) && this.a == resourceInfo.a;
        }

        public String toString() {
            return this.b;
        }
    }

    @Beta
    /* loaded from: classes2.dex */
    public static final class ClassInfo extends ResourceInfo {
        private final String b;

        ClassInfo(String str, ClassLoader classLoader) {
            super(str, classLoader);
            this.b = ClassPath.a(str);
        }

        public String getPackageName() {
            return Reflection.getPackageName(this.b);
        }

        public String getSimpleName() {
            int lastIndexOf = this.b.lastIndexOf(36);
            if (lastIndexOf != -1) {
                return CharMatcher.digit().trimLeadingFrom(this.b.substring(lastIndexOf + 1));
            }
            String packageName = getPackageName();
            if (packageName.isEmpty()) {
                return this.b;
            }
            return this.b.substring(packageName.length() + 1);
        }

        public String getName() {
            return this.b;
        }

        public Class<?> load() {
            try {
                return this.a.loadClass(this.b);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // com.google.common.reflect.ClassPath.ResourceInfo
        public String toString() {
            return this.b;
        }
    }

    /* loaded from: classes2.dex */
    static abstract class b {
        private final Set<File> a = Sets.newHashSet();

        protected abstract void a(ClassLoader classLoader, File file) throws IOException;

        protected abstract void a(ClassLoader classLoader, JarFile jarFile) throws IOException;

        b() {
        }

        public final void a(ClassLoader classLoader) throws IOException {
            UnmodifiableIterator<Map.Entry<File, ClassLoader>> it = b(classLoader).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<File, ClassLoader> next = it.next();
                a(next.getKey(), next.getValue());
            }
        }

        @VisibleForTesting
        final void a(File file, ClassLoader classLoader) throws IOException {
            if (this.a.add(file.getCanonicalFile())) {
                b(file, classLoader);
            }
        }

        private void b(File file, ClassLoader classLoader) throws IOException {
            try {
                if (file.exists()) {
                    if (file.isDirectory()) {
                        a(classLoader, file);
                    } else {
                        c(file, classLoader);
                    }
                }
            } catch (SecurityException e) {
                Logger logger = ClassPath.a;
                logger.warning("Cannot access " + file + ": " + e);
            }
        }

        private void c(File file, ClassLoader classLoader) throws IOException {
            try {
                JarFile jarFile = new JarFile(file);
                try {
                    UnmodifiableIterator<File> it = a(file, jarFile.getManifest()).iterator();
                    while (it.hasNext()) {
                        a(it.next(), classLoader);
                    }
                    a(classLoader, jarFile);
                } finally {
                    try {
                        jarFile.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (IOException unused2) {
            }
        }

        @VisibleForTesting
        static ImmutableSet<File> a(File file, @NullableDecl Manifest manifest) {
            if (manifest == null) {
                return ImmutableSet.of();
            }
            ImmutableSet.Builder builder = ImmutableSet.builder();
            String value = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH.toString());
            if (value != null) {
                for (String str : ClassPath.c.split(value)) {
                    try {
                        URL a = a(file, str);
                        if (a.getProtocol().equals("file")) {
                            builder.add((ImmutableSet.Builder) ClassPath.a(a));
                        }
                    } catch (MalformedURLException unused) {
                        Logger logger = ClassPath.a;
                        logger.warning("Invalid Class-Path entry: " + str);
                    }
                }
            }
            return builder.build();
        }

        @VisibleForTesting
        static ImmutableMap<File, ClassLoader> b(ClassLoader classLoader) {
            LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
            ClassLoader parent = classLoader.getParent();
            if (parent != null) {
                newLinkedHashMap.putAll(b(parent));
            }
            UnmodifiableIterator<URL> it = c(classLoader).iterator();
            while (it.hasNext()) {
                URL next = it.next();
                if (next.getProtocol().equals("file")) {
                    File a = ClassPath.a(next);
                    if (!newLinkedHashMap.containsKey(a)) {
                        newLinkedHashMap.put(a, classLoader);
                    }
                }
            }
            return ImmutableMap.copyOf(newLinkedHashMap);
        }

        private static ImmutableList<URL> c(ClassLoader classLoader) {
            if (classLoader instanceof URLClassLoader) {
                return ImmutableList.copyOf(((URLClassLoader) classLoader).getURLs());
            }
            if (classLoader.equals(ClassLoader.getSystemClassLoader())) {
                return b();
            }
            return ImmutableList.of();
        }

        @VisibleForTesting
        static ImmutableList<URL> b() {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (String str : Splitter.on(StandardSystemProperty.PATH_SEPARATOR.value()).split(StandardSystemProperty.JAVA_CLASS_PATH.value())) {
                try {
                    try {
                        builder.add((ImmutableList.Builder) new File(str).toURI().toURL());
                    } catch (SecurityException unused) {
                        builder.add((ImmutableList.Builder) new URL("file", (String) null, new File(str).getAbsolutePath()));
                    }
                } catch (MalformedURLException e) {
                    Logger logger = ClassPath.a;
                    Level level = Level.WARNING;
                    logger.log(level, "malformed classpath entry: " + str, (Throwable) e);
                }
            }
            return builder.build();
        }

        @VisibleForTesting
        static URL a(File file, String str) throws MalformedURLException {
            return new URL(file.toURI().toURL(), str);
        }
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    static final class a extends b {
        private final SetMultimap<ClassLoader, String> a = MultimapBuilder.hashKeys().linkedHashSetValues().build();

        a() {
        }

        ImmutableSet<ResourceInfo> a() {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            for (Map.Entry<ClassLoader, String> entry : this.a.entries()) {
                builder.add((ImmutableSet.Builder) ResourceInfo.a(entry.getValue(), entry.getKey()));
            }
            return builder.build();
        }

        @Override // com.google.common.reflect.ClassPath.b
        protected void a(ClassLoader classLoader, JarFile jarFile) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry nextElement = entries.nextElement();
                if (!nextElement.isDirectory() && !nextElement.getName().equals("META-INF/MANIFEST.MF")) {
                    this.a.get((SetMultimap<ClassLoader, String>) classLoader).add(nextElement.getName());
                }
            }
        }

        @Override // com.google.common.reflect.ClassPath.b
        protected void a(ClassLoader classLoader, File file) throws IOException {
            HashSet hashSet = new HashSet();
            hashSet.add(file.getCanonicalFile());
            a(file, classLoader, "", hashSet);
        }

        private void a(File file, ClassLoader classLoader, String str, Set<File> set) throws IOException {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                ClassPath.a.warning("Cannot read directory " + file);
                return;
            }
            for (File file2 : listFiles) {
                String name = file2.getName();
                if (file2.isDirectory()) {
                    File canonicalFile = file2.getCanonicalFile();
                    if (set.add(canonicalFile)) {
                        a(canonicalFile, classLoader, str + name + "/", set);
                        set.remove(canonicalFile);
                    }
                } else {
                    String str2 = str + name;
                    if (!str2.equals("META-INF/MANIFEST.MF")) {
                        this.a.get((SetMultimap<ClassLoader, String>) classLoader).add(str2);
                    }
                }
            }
        }
    }

    @VisibleForTesting
    static String a(String str) {
        return str.substring(0, str.length() - 6).replace(JsonPointer.SEPARATOR, '.');
    }

    @VisibleForTesting
    static File a(URL url) {
        Preconditions.checkArgument(url.getProtocol().equals("file"));
        try {
            return new File(url.toURI());
        } catch (URISyntaxException unused) {
            return new File(url.getPath());
        }
    }
}
