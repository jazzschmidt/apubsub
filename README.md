# apubsub | Simple Publish-Subscriber System

[![CI](https://github.com/jazzschmidt/apubsub/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/jazzschmidt/apubsub/actions/workflows/ci.yml)

A very basic example of a pub-sub system, where a client can broadcast messages via a central server to all connected
clients.

The server is written as a simple _Spring Boot_ web app that exposes STOMP endpoints via WebSockets. Also, a rudimentary
client can be accessed over http.

The client is built using [NW.js](https://nwjs.io/), which seems to be a more efficient alternative
to [Electron](https://www.electronjs.org/).

## [Reference Docs](https://jazzschmidt.github.io/apubsub)

- [About](https://jazzschmidt.github.io/apubsub)
- [Getting Started](https://jazzschmidt.github.io/apubsub/#/getting-started/index)
    - [Build](https://jazzschmidt.github.io/apubsub/#/getting-started/index?id=build)
    - [Usage](https://jazzschmidt.github.io/apubsub/#/getting-started/index?id=usage)
        - [Starting the Server](https://jazzschmidt.github.io/apubsub/#/getting-started/index?id=starting-the-server)
        - [Configuring the Client](https://jazzschmidt.github.io/apubsub/#/getting-started/index?id=configuring-the-client)
        - [Sending Messages](https://jazzschmidt.github.io/apubsub/#/getting-started/index?id=sending-messages)
- [Development](https://jazzschmidt.github.io/apubsub/#/development/index)
    - [Client Registration](https://jazzschmidt.github.io/apubsub/#/development/index?id=client-registration)
    - [Event Publishing](https://jazzschmidt.github.io/apubsub/#/development/index?id=event-publishing)
    - [Channel Interceptors](https://jazzschmidt.github.io/apubsub/#/development/index?id=channel-interceptors)
    - [NW.js Client App](https://jazzschmidt.github.io/apubsub/#/development/index?id=nwjs-client-app)
    - [Spock Testing Framework](https://jazzschmidt.github.io/apubsub/#/development/index?id=spock-testing-framework)
    - [Further Details](https://jazzschmidt.github.io/apubsub/#/development/index?id=further-details)
        - [Lombok](https://jazzschmidt.github.io/apubsub/#/development/index?id=lombok)
        - [Maven](https://jazzschmidt.github.io/apubsub/#/development/index?id=maven)
- [Caveats](http://localhost:3000/#/caveats/caveats)
