# Simple Publish-Subscriber System

A very basic example of a pub-sub system, where a client can broadcast messages via a central server to all connected
clients.

The server is written as a simple _Spring Boot_ web app that simply exposes STOMP endpoints via WebSockets. Also, a
rudimentary client can be accessed over http. This enables testing between multiple clients, since the client
implementation is limited to run in one instance only.

The client is built using [NW.js](https://nwjs.io/), which seems to be a more efficient alternative
to [Electron](https://www.electronjs.org/).

## Build

The individual parts of this project are being built with different tools:

- Maven Wrapper
- Docker
- npm

There is a convenience bash script, that will perform all necessary build steps for you and will fail when an error
occurs: `full-build`. When targeting ARM architecture, supply the `--arm` flag. Erroneous builds will emit a log file to
dig into the problems.

The output should look like this:

```
$ ./full-build --arm # omit parameter for x64 architectures
Building Server module... done!
Building Docker image... done!
Building Client module... done!


================================ SUMMARY ================================
    -- TYPE --     |                      -- DEST --
=========================================================================
Server jar         | server/target/server-0.0.1-SNAPSHOT.jar
Docker Image       | apubsub-server:latest
Client exe (win64) | client/dist/apubsub-client/win64/apubsub-client.exe
Client app (osx)   | client/dist/apubsub-client/osx64/apubsub-client.app
Client bin (linux) | client/dist/apubsub-client/linux64/apubsub-client
```
