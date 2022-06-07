package org.eclipse.jetty.util.resource;

import com.fasterxml.jackson.core.JsonPointer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes5.dex */
public class JarResource extends URLResource {
    private static final Logger LOG = Log.getLogger(JarResource.class);
    protected JarURLConnection _jarConnection;

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public File getFile() throws IOException {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JarResource(URL url) {
        super(url, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public JarResource(URL url, boolean z) {
        super(url, null, z);
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public synchronized void release() {
        this._jarConnection = null;
        super.release();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.eclipse.jetty.util.resource.URLResource
    public synchronized boolean checkConnection() {
        super.checkConnection();
        try {
            if (this._jarConnection != this._connection) {
                newConnection();
            }
        } catch (IOException e) {
            LOG.ignore(e);
            this._jarConnection = null;
        }
        return this._jarConnection != null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void newConnection() throws IOException {
        this._jarConnection = (JarURLConnection) this._connection;
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public boolean exists() {
        if (this._urlString.endsWith("!/")) {
            return checkConnection();
        }
        return super.exists();
    }

    @Override // org.eclipse.jetty.util.resource.URLResource, org.eclipse.jetty.util.resource.Resource
    public InputStream getInputStream() throws IOException {
        checkConnection();
        if (!this._urlString.endsWith("!/")) {
            return new FilterInputStream(super.getInputStream()) { // from class: org.eclipse.jetty.util.resource.JarResource.1
                @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    this.in = IO.getClosedStream();
                }
            };
        }
        return new URL(this._urlString.substring(4, this._urlString.length() - 2)).openStream();
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public void copyTo(File file) throws IOException {
        Manifest manifest;
        boolean z;
        Throwable th;
        if (exists()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Extract " + this + " to " + file, new Object[0]);
            }
            String trim = getURL().toExternalForm().trim();
            int indexOf = trim.indexOf("!/");
            int i = indexOf >= 0 ? 4 : 0;
            if (indexOf >= 0) {
                URL url = new URL(trim.substring(i, indexOf));
                int i2 = indexOf + 2;
                FileOutputStream fileOutputStream = null;
                String substring = i2 < trim.length() ? trim.substring(i2) : null;
                boolean z2 = substring != null && substring.endsWith("/");
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Extracting entry = " + substring + " from jar " + url, new Object[0]);
                }
                JarInputStream jarInputStream = new JarInputStream(url.openConnection().getInputStream());
                while (true) {
                    JarEntry nextJarEntry = jarInputStream.getNextJarEntry();
                    if (nextJarEntry == null) {
                        break;
                    }
                    String name = nextJarEntry.getName();
                    if (substring != null && name.startsWith(substring)) {
                        if (!z2 && substring.length() + 1 == name.length() && name.endsWith("/")) {
                            z2 = true;
                        }
                        if (z2) {
                            name = name.substring(substring.length());
                            if (!name.equals("")) {
                                z2 = z2;
                                z = true;
                            } else {
                                z2 = z2;
                                z = false;
                            }
                        } else {
                            z2 = z2;
                            z = true;
                        }
                    } else if (substring == null || name.startsWith(substring)) {
                        z2 = z2;
                        z = true;
                    } else {
                        z2 = z2;
                        z = false;
                    }
                    if (!z) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Skipping entry: " + name, new Object[0]);
                        }
                    } else if (URIUtil.canonicalPath(name.replace('\\', JsonPointer.SEPARATOR)) != null) {
                        File file2 = new File(file, name);
                        if (!nextJarEntry.isDirectory()) {
                            File file3 = new File(file2.getParent());
                            if (!file3.exists()) {
                                file3.mkdirs();
                            }
                            try {
                                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                                try {
                                    IO.copy(jarInputStream, fileOutputStream2);
                                    IO.close(fileOutputStream2);
                                    if (nextJarEntry.getTime() >= 0) {
                                        file2.setLastModified(nextJarEntry.getTime());
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    fileOutputStream = fileOutputStream2;
                                    IO.close(fileOutputStream);
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } else if (!file2.exists()) {
                            file2.mkdirs();
                        }
                    } else if (LOG.isDebugEnabled()) {
                        LOG.debug("Invalid entry: " + name, new Object[0]);
                    }
                }
                if ((substring == null || (substring != null && substring.equalsIgnoreCase("META-INF/MANIFEST.MF"))) && (manifest = jarInputStream.getManifest()) != null) {
                    File file4 = new File(file, "META-INF");
                    file4.mkdir();
                    FileOutputStream fileOutputStream3 = new FileOutputStream(new File(file4, "MANIFEST.MF"));
                    manifest.write(fileOutputStream3);
                    fileOutputStream3.close();
                }
                IO.close(jarInputStream);
                return;
            }
            throw new IOException("Not a valid jar url: " + trim);
        }
    }

    public static Resource newJarResource(Resource resource) throws IOException {
        if (resource instanceof JarResource) {
            return resource;
        }
        return Resource.newResource("jar:" + resource + "!/");
    }
}
