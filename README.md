# TracePulse

---

**TracePulse:** 一个应用性能监控工具，针对Java及微服务项目设计，与spring-boot无缝结合，同时集成钉钉告警功能

# 概述
- 与springboot无缝结合，只需引入依赖即可使用
- 线程分析： 
  - 根据线程名称推断出线程池名称，且对线程池中的线程根据其执行状态进行分组统计
- 集成常见的告警软件，如钉钉

# 使用指南

- maven使用
```xml
<dependency>
    <groupId>io.github.lijunweiz</groupId>
    <artifactId>tracepulse-boot</artifactId>
    <version>${tracepulse.version}</version>
</dependency>
```

- gradle使用
```groovy
dependencies {
    implementation 'io.github.lijunweiz:tracepulse-boot:${tracepulse.version}'
}
```

springboot配置文件须暴露端点tracepulse

启动项目后访问：[http://ip:port/actuator/tracepulse]()


# 联系作者
有任何问题或建议，欢迎随时联系作者
- 邮箱：takealeaf@yeah.net
