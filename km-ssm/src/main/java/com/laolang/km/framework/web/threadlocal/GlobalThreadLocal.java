package com.laolang.km.framework.web.threadlocal;

public class GlobalThreadLocal {

    private static final ThreadLocal<GlobalInterceptorContext> threadLocal = new ThreadLocal<>();

    public static GlobalInterceptorContext getContext() {
        return threadLocal.get();
    }

    public static void setContext(GlobalInterceptorContext context) {
        threadLocal.set(context);
    }

    public static void removeContext() {
        threadLocal.remove();
    }

    public static long getWatchTime(){
        threadLocal.get().getStopWatch().stop();
        return threadLocal.get().getStopWatch().getLastTaskTimeMillis();
    }

}
