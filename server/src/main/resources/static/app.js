// Adapted from https://spring.io/guides/gs/messaging-stomp-websocket/

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        register();
        setTimeout(function () {
            // Suppose registration is complete after 200ms
            stompClient.subscribe('/topic/messages', function (greeting) {
                showMessage(JSON.parse(greeting.body));
            });
        }, 200)
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function register() {
    stompClient.send("/app/register", {}, JSON.stringify({'clientName': 'server-ui'}))
}

function sendMessage() {
    stompClient.send("/app/messages", {}, JSON.stringify({'message': $("#name").val()}));
}

function showMessage(message) {
    let element

    if (message.type === "broadcast") {
        element = "<b>" + message.clientName + "</b>: " + message.message
    } else {
        element = "<i>" + message.message + "</i>"
    }

    $("#greetings").append("<tr><td>" + element + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
});
