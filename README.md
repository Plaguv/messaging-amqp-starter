![Apache](https://img.shields.io/badge/apache-%23D42029.svg?style=for-the-badge&logo=apache&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)

# Messaging AMQP Starter

This module defines the **configurations and service beans** for messages exchanged between applications.

More specifically, for applications that utilize [Spring Boots AMQP](https://github.com/spring-projects/spring-amqp)

It includes:
- Autoconfiguration for listeners, publishers and general messaging configurations
- Basic bean implementations for reusable generic AMQP publishers and listeners

# Dependencies
- `Java LTS >= 17`
- [`io.github.plaguv:messaging-amqp-contracts:1.0.0-alpha-1`](https://github.com/plaguv/messaging-amqp-starter)

# Installation
You can easily add the `io.github.plaguv:messaging-amqp-starter` library to
your project using the **Maven Central Repository**.

> At the time of release the libarary might **still be private** and therefore not publically available. 
> In that case, check out the repository yourself and run `mvn clean install`.

```xml
<dependency>
    <groupId>io.github.plaguv</groupId>
    <artifactId>messaging-amqp-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```