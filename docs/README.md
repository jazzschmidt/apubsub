# apubsub | Simple Publish-Subscriber System

[![CI](https://github.com/jazzschmidt/apubsub/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/jazzschmidt/apubsub/actions/workflows/ci.yml)

A very basic example of a pub-sub system, where a client can broadcast messages via a central server to all connected
clients.

The server is written as a simple [Spring Boot](https://spring.io/projects/spring-boot) web app that
exposes [SockJS](https://github.com/sockjs/sockjs-client) compatible [STOMP](https://stomp.github.io/) endpoints through
WebSockets. Also, a rudimentary client can be accessed over http.

The client application is built using [NW.js](https://nwjs.io/), which seems to be a more efficient alternative
to [Electron](https://www.electronjs.org/).

## Features

- Centralized real time broadcasting chat server
- Registration workflow for subscription of topics
- Over 90% test coverage of the server module
- Simple event-driven application
- Cross-OS compatible client app

## Tech Stack

- Java 17
- Spring Boot 2.6.3
- STOMP
- Spock Framework
- Maven
- Docker
- JavaScript
- npm
- NW.js
- Docsify
- Bash
