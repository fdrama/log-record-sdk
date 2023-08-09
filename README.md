# 仿照`spring-cache` 利用注解和SPEL表达式 实现的日志组件

## 基本使用

### 开启注解 @EnableLogRecord

```java

@SpringBootApplication
@EnableLogRecord
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

###   

### 使用注解 @LogRecord 支持多个注解

```java
@LogRecord(
        type = LogType.Constants.USER, // 日志类型
        subType = LogSubType.Constants.ADD, // 日志子类型 
        bizNo = "{{#user.userId}}", // 日志业务号
        condition = "{{#user.userId != null}}",// 记录日志条件，默认记录
        successCondition = "{{#_result != null}}", // 记录成功日志条件 否则记录失败记录，默认记录成功日志
        successTemplate = "新增用户 {{#user.userId?:#user.email}}", // 成功日志模板 和失败日志模板不能同时为空
        failTemplate = "新增用户失败 {{#user.userId?:#user.email}}",  // 失败日志模板 和成功日志模板不能同时为空
        extra = "extra info" // 额外信息
)
User add(User user);
```

### 操作人获取方式

两种方式

1. @LogRecord 显示指定操作人 `operator = "{{#user.userId}}"`
2. 通过 `IOperatorGetService` 接口实现类获取操作人

```java

@Component
public class DefaultOperatorGetServiceImpl implements IOperatorGetService {

    @Override
    public String get() {
        // todo
        return null;
    }
}
```

### 日志拓展支持function

在有些场景我们需要对参数进行处理，比如加密、根据id获取名称等等，来构建日志内容，这里就需要使用到function

1. 实现`IParseFunction`接口，指定functionName，实现自己的function逻辑
2. 在注解中使用`{functionName{#user.id,#user.name}}`的方式使用function，支持多参数

```java
@LogRecord(
    successTemplate = "新增用户 {{#user.userId?:#user.email}} {_SENSITIVE{#user.email,'EMAIL'}}"
)
@Component
public class SensitiveParseFunction implements IParseFunction {
    @Override
    public String functionName() {
        return "_SENSITIVE";
    }

    @Override
    public String apply(Object... value) {
        value[0] = value[0].toString().trim();
        if (value.length == 2) {
            switch (value[1].toString()) {
                case "EMAIL":
                    return email(value[0].toString());
                case "PHONE":
                    return phone(value[0].toString());
                default:
                    break;
            }
        }
        return value[0].toString();
    }
}
```

### 日志记录上下文 LogRecordThreadContext使用

在一些场景里面我们无法只通过参数获取到所有日志信息，这里就需要使用到`LogRecordThreadContext`

```java
LogRecordThreadContext.putVariable("content","extra info");

@LogRecord(
    extra = "{{#content}}"
)
```

需要注意的是,
LogRecordThreadContext是使用栈的方式实现的，所以在方法调用结束后会自动清理，如果存在多个方法调用，共享同一个上下文，则需要使用`LogRecordThreadContext.putGlobalVariable`方法

### 拓展点

#### 1. 操作人获取 IOperatorGetService

上面介绍了两种方式指定操作人，如果需要自定义操作人获取方式，可以实现`IOperatorGetService`接口

#### 2. 日志记录错误处理器 ILogRecordErrorHandler

1. 默认实现类 `DefaultLogRecordErrorHandler` 会将错误日志记录到日志文件中
2. 自定义实现类可以实现`ILogRecordErrorHandler`接口，实现自己的错误处理逻辑，全局通用
3. 可以在类上使用`@LogRecordConfig`注解指定错误处理器的全限定类名，优先级高于全局配置

#### 3. 日志记录解析器 ILogRecordResolver

1. 默认实现类 `DefaultLogRecordResolver` 会将日志记录到日志文件中
2. 自定义实现类可以实现`ILogRecordResolver`接口，实现自己的日志记录逻辑，全局通用
3. 可以在类上使用`@LogRecordConfig`注解指定日志记录解析器的全限定类名，优先级高于全局配置

```java

@Component
public class DefaultLogRecordResolver implements ILogRecordResolver {

    @Override
    public void resolveLog(LogRecordDetail recordDetail) {
        // todo something
        logger.info(recordDetail);
    }
}
```

LogRecordDetail 最终构建出来的日志记录对象，包含了日志记录的所有信息，用户可以自定义日志记录逻辑

```java
public class LogRecordDetail {

    /**
     * 日志内容
     */
    private String content;
    /**
     * 保存的操作日志的类型
     */
    private String type;
    /**
     * 日志的子类型
     */
    private String subType;
    /**
     * 日志绑定的业务标识
     */
    private String bizNo;
    /**
     * 额外信息
     */
    private String extra;

    /**
     * 操作人
     */
    private String operatorId;
    /**
     * 操作开始时间
     */
    private long operateTimeBegin;
    /**
     * 操作结束时间
     */
    private long operateTimeEnd;

    /**
     * 操作方法名称
     */
    private String methodName;

    /**
     * 操作类名
     */
    private String className;

    /**
     * 操作方法执行状态 抛出异常为失败
     */
    private boolean methodSuccess;

}
```