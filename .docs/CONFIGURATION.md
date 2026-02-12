# Configuration Documentation

## ``amqp``

- ``amqp.central-exchange`` : ``String``
    - Default: ``null``
    - Describes the central exchange name, which will be used in routing naming
- ``amqp.central-application`` : ``String``
    - Default: ``null``
    - Describes the central application name, which will be used in routing naming
- ``amqp.declare-exchange-durable`` : ``Boolean``
    - Default: ``true``
    - Automatically declares exchanges as durable
- ``amqp.declare-exchange-deletable`` : ``Boolean``
    - Default: ``false``
    - Automatically declares exchanges as deletable
- ``amqp.declare-queue-durable`` : ``Boolean``
    - Default: ``true``
    - Automatically declares queues as durable
- ``amqp.declare-queue-exclusive`` : ``Boolean``
    - Default: ``false``
    - Automatically declares queues as exclusive
- ``amqp.declare-queue-deletable`` : ``Boolean``
    - Default: ``false``
    - Automatically declares queues as deletable

## ``amqp.skip``

- ``amqp.skip.register-listeners`` : ``Boolean``
    - Default: ``false``
    - Declares if the automatic registration of listeners should take place