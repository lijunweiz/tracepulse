# TracePulse

**TracePulse:** 一个应用性能监控工具，针对Java及微服务项目设计，与spring-boot无缝结合，同时集成钉钉告警功能

# 概述
- 与springboot无缝结合，只需引入依赖即可使用
- 线程分析： 
  - 根据线程名称推断出线程池名称，且对线程池中的线程根据其执行状态进行分组统计，其中Summary为线程的汇总信息，UNKNOWN为不能  
    判定的线程归属信息，JVM为系统本身运行需要的线程汇总信息
- 内存、CPU监控：
  - 统计JVM进程及系统本身的内存、CPU使用占比
- 集成常见的告警软件，如钉钉

# 使用指南

- maven使用
```xml
<dependencies>
    <dependency>
      <groupId>io.github.lijunweiz</groupId>
      <artifactId>tracepulse-boot</artifactId>
      <version>${tracepulse.version}</version>
    </dependency>
</dependencies>
```

- gradle使用
```groovy
dependencies {
    implementation 'io.github.lijunweiz:tracepulse-boot:${tracepulse.version}'
}
```

访问端点  
springboot配置文件须暴露端点tracepulse   
```yml
management:
  endpoints:
    web:
      exposure:
        include: tracepulse
```
启动项目后访问：[http://ip:port/actuator/tracepulse]()


# 联系作者
有任何问题或建议，欢迎随时联系作者
- 邮箱：takealeaf@yeah.net
