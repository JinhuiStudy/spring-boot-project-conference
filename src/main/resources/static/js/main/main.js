let ws;
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#sendmessage").show();
    } else {
        $("#sendmessage").hide();
    }
}

function connect() {
    // let url = 'wss://softfocus.website:8080/web-socket';
    let url = 'wss://localhost:8080/web-socket';
    ws = new WebSocket(url);
    ws.onopen = function () {
        showBroadcastMessage('<div class="alert alert-success">Connected to server</div>');
    };
    ws.onmessage = function (data) {
        showBroadcastMessage(createTextNode(data.data));
    };
    setConnected(true);
}

function disconnect() {
    if (ws != null) {
        ws.close();
        showBroadcastMessage('<div class="alert alert-warning">Disconnected from server</div>');
    }
    setConnected(false);
}

function sendChat() {
    ws.send("닉네임 : "+$("#message").val());
    $("#message").val("");
}

function createTextNode(msg) {
    // Json { idx: 1, msg: "" } idx -> focus #2
    return '<div class="alert alert-info">' + msg + '</div>';
}

function showBroadcastMessage(message) {
    $("#content").html($("#content").html() + message);
}
