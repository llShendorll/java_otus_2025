package ru.otus.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public final class LoggingProxy {
    private LoggingProxy() {}

    public static TestLoggingInterface createProxy(TestLoggingInterface target) {
        InvocationHandler handler = new LogInvocationHandler(target);
        return (TestLoggingInterface) Proxy.newProxyInstance(
                target.getClass().getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    private static class LogInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface target;

        LogInvocationHandler(TestLoggingInterface target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method implMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

            if (implMethod.isAnnotationPresent(Log.class)) {
                String params = "";
                if (args != null) {
                    params = String.join(
                            ", ", Arrays.stream(args).map(String::valueOf).toArray(String[]::new));
                }
                if (params.isEmpty()) {
                    System.out.println("executed method: " + method.getName());
                } else {
                    System.out.println("executed method: " + method.getName() + ", param: " + params);
                }
            }

            return implMethod.invoke(target, args);
        }
    }
}
