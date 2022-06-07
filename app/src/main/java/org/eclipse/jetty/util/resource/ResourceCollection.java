package org.eclipse.jetty.util.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;
import okhttp3.HttpUrl;

/* loaded from: classes5.dex */
public class ResourceCollection extends Resource {
    private Resource[] _resources;

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean isContainedIn(Resource resource) throws MalformedURLException {
        return false;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public long length() {
        return -1L;
    }

    public ResourceCollection() {
        this._resources = new Resource[0];
    }

    public ResourceCollection(Resource... resourceArr) {
        ArrayList arrayList = new ArrayList();
        for (Resource resource : resourceArr) {
            if (resource != null) {
                if (resource instanceof ResourceCollection) {
                    for (Resource resource2 : ((ResourceCollection) resource).getResources()) {
                        arrayList.add(resource2);
                    }
                } else {
                    arrayList.add(resource);
                }
            }
        }
        this._resources = (Resource[]) arrayList.toArray(new Resource[arrayList.size()]);
        Resource[] resourceArr2 = this._resources;
        for (Resource resource3 : resourceArr2) {
            if (!resource3.exists() || !resource3.isDirectory()) {
                throw new IllegalArgumentException(resource3 + " is not an existing directory.");
            }
        }
    }

    public ResourceCollection(String[] strArr) {
        this._resources = new Resource[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            try {
                this._resources[i] = Resource.newResource(strArr[i]);
                if (!this._resources[i].exists() || !this._resources[i].isDirectory()) {
                    throw new IllegalArgumentException(this._resources[i] + " is not an existing directory.");
                }
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public ResourceCollection(String str) {
        setResourcesAsCSV(str);
    }

    public Resource[] getResources() {
        return this._resources;
    }

    public void setResources(Resource[] resourceArr) {
        if (resourceArr == null) {
            resourceArr = new Resource[0];
        }
        this._resources = resourceArr;
    }

    public void setResourcesAsCSV(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",;");
        int countTokens = stringTokenizer.countTokens();
        if (countTokens != 0) {
            this._resources = new Resource[countTokens];
            int i = 0;
            while (stringTokenizer.hasMoreTokens()) {
                try {
                    this._resources[i] = Resource.newResource(stringTokenizer.nextToken().trim());
                    if (!this._resources[i].exists() || !this._resources[i].isDirectory()) {
                        throw new IllegalArgumentException(this._resources[i] + " is not an existing directory.");
                    }
                    i++;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return;
        }
        throw new IllegalArgumentException("ResourceCollection@setResourcesAsCSV(String)  argument must be a string containing one or more comma-separated resource strings.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public Resource addPath(String str) throws IOException, MalformedURLException {
        if (this._resources == null) {
            throw new IllegalStateException("*resources* not set.");
        } else if (str == null) {
            throw new MalformedURLException();
        } else if (str.length() == 0 || "/".equals(str)) {
            return this;
        } else {
            int i = 0;
            Resource resource = null;
            while (true) {
                Resource[] resourceArr = this._resources;
                if (i >= resourceArr.length) {
                    break;
                }
                resource = resourceArr[i].addPath(str);
                if (!resource.exists()) {
                    i++;
                } else if (!resource.isDirectory()) {
                    return resource;
                }
            }
            int i2 = i + 1;
            ArrayList arrayList = null;
            while (true) {
                Resource[] resourceArr2 = this._resources;
                if (i2 >= resourceArr2.length) {
                    break;
                }
                Resource addPath = resourceArr2[i2].addPath(str);
                if (addPath.exists() && addPath.isDirectory()) {
                    if (resource != null) {
                        arrayList = new ArrayList();
                        arrayList.add(resource);
                        resource = null;
                    }
                    arrayList.add(addPath);
                }
                i2++;
            }
            if (resource != null) {
                return resource;
            }
            if (arrayList != null) {
                return new ResourceCollection((Resource[]) arrayList.toArray(new Resource[arrayList.size()]));
            }
            return null;
        }
    }

    protected Object findResource(String str) throws IOException, MalformedURLException {
        int i = 0;
        Resource resource = null;
        while (true) {
            Resource[] resourceArr = this._resources;
            if (i >= resourceArr.length) {
                break;
            }
            resource = resourceArr[i].addPath(str);
            if (!resource.exists()) {
                i++;
            } else if (!resource.isDirectory()) {
                return resource;
            }
        }
        int i2 = i + 1;
        ArrayList arrayList = null;
        while (true) {
            Resource[] resourceArr2 = this._resources;
            if (i2 >= resourceArr2.length) {
                break;
            }
            Resource addPath = resourceArr2[i2].addPath(str);
            if (addPath.exists() && addPath.isDirectory()) {
                if (resource != null) {
                    arrayList = new ArrayList();
                    arrayList.add(resource);
                }
                arrayList.add(addPath);
            }
            i2++;
        }
        if (resource != null) {
            return resource;
        }
        if (arrayList != null) {
            return arrayList;
        }
        return null;
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean delete() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean exists() {
        if (this._resources != null) {
            return true;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public File getFile() throws IOException {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource resource : resourceArr) {
                File file = resource.getFile();
                if (file != null) {
                    return file;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public InputStream getInputStream() throws IOException {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource resource : resourceArr) {
                InputStream inputStream = resource.getInputStream();
                if (inputStream != null) {
                    return inputStream;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public String getName() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource resource : resourceArr) {
                String name = resource.getName();
                if (name != null) {
                    return name;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public OutputStream getOutputStream() throws IOException, SecurityException {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource resource : resourceArr) {
                OutputStream outputStream = resource.getOutputStream();
                if (outputStream != null) {
                    return outputStream;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public URL getURL() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource resource : resourceArr) {
                URL url = resource.getURL();
                if (url != null) {
                    return url;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean isDirectory() {
        if (this._resources != null) {
            return true;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public long lastModified() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource resource : resourceArr) {
                long lastModified = resource.lastModified();
                if (lastModified != -1) {
                    return lastModified;
                }
            }
            return -1L;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public String[] list() {
        if (this._resources != null) {
            HashSet hashSet = new HashSet();
            for (Resource resource : this._resources) {
                for (String str : resource.list()) {
                    hashSet.add(str);
                }
            }
            String[] strArr = (String[]) hashSet.toArray(new String[hashSet.size()]);
            Arrays.sort(strArr);
            return strArr;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public void release() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource resource : resourceArr) {
                resource.release();
            }
            return;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public boolean renameTo(Resource resource) throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jetty.util.resource.Resource
    public void copyTo(File file) throws IOException {
        int length = this._resources.length;
        while (true) {
            int i = length - 1;
            if (length > 0) {
                this._resources[i].copyTo(file);
                length = i;
            } else {
                return;
            }
        }
    }

    public String toString() {
        Resource[] resourceArr = this._resources;
        return resourceArr == null ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : String.valueOf(Arrays.asList(resourceArr));
    }
}
