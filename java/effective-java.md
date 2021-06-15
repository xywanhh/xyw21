# 1. 前言
经验规则
基本类库
java.lang
java.util
java.io
java.util.concurrent

# 2. 创建和销毁对象
何时以及如何创建对象。
何时以及如何避免创建对象。
何时确保对象及时销毁。
如何管理对象销毁之前必须进行的各种清理动作。

## 2.1 考虑用静态工厂代替构造器

这里的静态工厂并不直接对应设计模式中的工厂模式。
在选择提供公有构造器的时候，优先考虑静态工厂。

```
public static Boolean ValueOf(boolean f) {
    return f ? Boolean.TRUE : Boolean.FALSE;
}

优势：
通过适当的方法名称可以清晰描述返回对象
可以做到对象的复用，这在构造方法里做不到
可以返回类型的任何子类型，很容易实现多态。在返回对象上有很大的灵活性

服务提供者框架(service provider framework)，例如jdbc
// service interface
public interface Service {
    ... // service-specific methods here
}

// service provider interface
public interface Provider {
    Service newService();
}

// noninstantiable class for service registration and access
public class Services {
    private Services() {} // prevents instantiation
    
    // maps service names to servides
    private static final Map<String, Provider> providers =
        new ConcurrentHashMap<String,Provider>();
    public static final String DEFAULT_PROVIDER_NAME = "<def>";

    // provider registration API
    public static void registerDefaultProvider(Provide p) {
        registerProvider(DEFAULT_PROVIDER_NAME, p);
    }
    public static void registerProvider(String name, Provide p) {
        providers.put(name, p);
    }

    // service access api
    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }
    public static Service newInstance(String name) {
        Provider p = providers.get(name);
        if (p == null) {
            throw new IllegalArgumentException("No provider registered with name: " + name);
        }
        return p.newService();
    }
}




```