# apubsub | Simple Publish-Subscriber System

[![CI](https://github.com/jazzschmidt/apubsub/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/jazzschmidt/gradle-docker-plugin/actions/workflows/ci.yml)

A very basic example of a pub-sub system, where a client can broadcast messages via a central server to all connected
clients.

The server is written as a simple _Spring Boot_ web app that exposes STOMP endpoints via WebSockets. Also, a rudimentary
client can be accessed over http.

The client is built using [NW.js](https://nwjs.io/), which seems to be a more efficient alternative
to [Electron](https://www.electronjs.org/).

## Reference Docs

- [Build](https://jazzschmidt.github.io/apubsub/#/?id=build)
- [Usage](https://jazzschmidt.github.io/apubsub/#/?id=usage)
- [Caveats](https://jazzschmidt.github.io/apubsub/#/?id=caveats)
