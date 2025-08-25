package ru.otus.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LoggingProxy {
    private LoggingProxy() {}

    public static TestLoggingInterface createProxy(TestLoggingInterface target) {
        InvocationHandler handler = new LogInvocationHandler(target);
        return (TestLoggingInterface) Proxy.newProxyInstance(
                target.getClass().getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    private static class LogInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface target;
        private final Map<Method, Method> implByInterface;
        private final Set<Method> methodsToLog;

        LogInvocationHandler(TestLoggingInterface target) {
            this.target = target;
            this.implByInterface = new HashMap<>();
            this.methodsToLog = new HashSet<>();

            for (Method ifaceMethod : TestLoggingInterface.class.getMethods()) {
                try {
                    Method implMethod =
                            target.getClass().getMethod(ifaceMethod.getName(), ifaceMethod.getParameterTypes());
                    implByInterface.put(ifaceMethod, implMethod);
                    if (implMethod.isAnnotationPresent(Log.class)) {
                        methodsToLog.add(ifaceMethod);
                    }
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException(
                            "Failed to find implementation for interface method: " + ifaceMethod, e);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method implMethod = implByInterface.get(method);
            boolean log = methodsToLog.contains(method);

            if (log) {
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
