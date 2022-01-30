# Caveats

While the server can be configured in many aspects and even validates its properties, the client does not fully comply
to those settings. More specific, modifying any endpoint prevents the client to connect.

The client registration is not enforced, which leads to unregistered clients being shown as `unregistered` in the
messages table.

The client has no error handling at all, since this would exceed the scope of this experiment.

Multiple instances of the client aren't supported, so you need to use the server rendered UI for multi-client scenarios.

The server rendered client UI has a fixed name: `server-ui`. Using multiple windows or tabs of that UI will work, but
messages cannot be specifically distinguished.
