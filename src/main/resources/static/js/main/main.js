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
    let map;
    function initMap() {
    if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
    alert(position.coords.latitude + ' ' + position.coords.longitude);
    map = new google.maps.Map(document.getElementById("map"), {
    center: {  lat: Number(position.coords.latitude), lng: Number(position.coords.longitude)},
    zoom: 16,

});

    new google.maps.Marker({
    position: {lat: Number(position.coords.latitude), lng: Number(position.coords.longitude)},
    map: map
})

}, function(error) {
    console.error(error);
}, {
    enableHighAccuracy: false,
    maximumAge: 0,
    timeout: Infinity
});
} else {
    alert('GPS를 지원하지 않습니다');
}
}
    window.initMap = initMap;