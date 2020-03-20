package ua.epam.springapp.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import ua.epam.springapp.annotation.Timed;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimedAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> classes = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(TimedAnnotationBeanPostProcessor.class.getName());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Timed.class)) {
            classes.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = classes.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), getInvocationHandler(bean));
        }
        return bean;
    }

    private InvocationHandler getInvocationHandler(Object bean) {
        return (proxy, method, args) -> {
            long before = System.nanoTime();
            Object returnValue = method.invoke(bean, args);
            long after = System.nanoTime();
            LOGGER.log(Level.INFO, method.getName() + " method executed in " + (after - before) + " ns");
            return returnValue;
        };
    }
}
