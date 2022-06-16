package com.sr.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 调用链
 * new A(new B(new C())) 组成 A->B->C 调用链
 * 目标是调用最后一个 C 里的方法，不过在 C 之前可能有很多预处理逻辑 A 和 B
 */
public abstract class MyInvoker implements InvocationHandler {

    protected final Callback cb;
    protected final MyInvoker next;
    private AtomicBoolean isOwner = new AtomicBoolean(false);

    public MyInvoker(MyInvoker next) {
        this.next = next;
        if (next != null) {
            this.cb = next.cb;
        } else {
            cb = null;
        }
    }

    public MyInvoker(Callback cb) {
        next = null;
        this.cb = cb;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Result result = onInvoke(proxy, method, args);
        if (result == null) {
            return proceed(proxy, method, args);
        }

        if (!result.isPending) {
            if (isOwner.get() && cb != null) {
                cb.onComplete(result.result);
            }
        }
        return result.result;
    }

    /**
     * 继续下一个
     */
    protected Object proceed(Object proxy, Method method, Object[] args) throws Throwable {
        if (!hasNext()) {
            // make final invoke here
        }

        return next.invoke(proxy, method, args);
    }

    protected abstract Result onInvoke(Object proxy, Method method, Object[] args);

    final boolean hasNext() {
        return next != null;
    }

    /**
     * 结果只能是 decide、proceed、pending
     * 若返回pending，要最终保证 proceed 恢复
     */
    public static class Result<T> {
        private boolean isPending;
        private T result;

        private Result(boolean isPending, T result) {
            this.isPending = isPending;
            this.result = result;
        }
        public static Result decide(Object result) {
            return new Result(false, result);
        }

        public static Result proceed() {
            return null;
        }

        public static Result pending() {
            return new Result(true, null);
        }
    }

    public interface Callback<T> {
        void onFail(Throwable t);

        void onComplete(T result);
    }
}
