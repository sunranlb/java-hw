package com.sr.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyProxy {
    private static final Map<Class<?>, Object> sProxyMap = new ConcurrentHashMap<>();

    public static <T> void set(Class<? super T> proxyClazz, T impl) {
        if (proxyClazz == null) {
            return;
        }
        if (impl == null) {
            sProxyMap.remove(proxyClazz);
        }
        if (!proxyClazz.isInterface()) {
            throw new IllegalArgumentException("proxy must a interface: " + proxyClazz);
        }
        if (!IProxy.class.isAssignableFrom(proxyClazz)) {
            throw new IllegalArgumentException("proxy must a IProxy: " + proxyClazz);
        }
        if (!proxyClazz.isInstance(impl)) {
            throw new IllegalArgumentException(impl.getClass().getName() + " is not instance of " + proxyClazz);
        }
        synchronized (sProxyMap) {
            sProxyMap.put(proxyClazz, impl);
        }
    }

    public static <T> T get(Class<T> proxyClazz) {
        if (!proxyClazz.isInterface()) {
            throw new IllegalArgumentException("proxy must a interface: " + proxyClazz);
        }

        Object impl = null;
        synchronized (sProxyMap) {
            impl = sProxyMap.get(proxyClazz);
            if (impl != null) {
                return (T) impl;
            }
        }

        DefaultImpl defaultImpl = proxyClazz.getAnnotation(DefaultImpl.class);
        if (defaultImpl != null) {
            try {
                Class<?> clazz = Class.forName(defaultImpl.name());
                if (proxyClazz.isAssignableFrom(clazz)) {
                    impl = clazz.newInstance();
                    set(proxyClazz, (T) impl);
                    return (T) impl;
                }
            } catch (Throwable t) {
                // log
            }
        }

        impl = Proxy.newProxyInstance(proxyClazz.getClassLoader(), new Class[]{proxyClazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Class<?> retType = method.getReturnType();
                if (retType.isPrimitive()) {
                    if (retType == Boolean.TYPE || retType == boolean.class) {
                        return false;
                    } else {
                        return 0;
                    }
                } else {
                    return null;
                }
            }
        });
        set(proxyClazz, (T) impl);
        return (T) impl;
    }
}
