# Development

This document describes the high-level overview of the system and explains some design considerations worth mentionable.

## Workflow

Before a client can subscribe to the `broadcast` topic, it needs to register itself with a name. Trying to subscribe
without a successful registration leads to the client being disconnected immediately.

This diagram shows the simplified process:

![Client Registration](../assets/client-reg.png)
