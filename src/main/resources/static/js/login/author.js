window.onload = function () {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {

            $.ajax({
                url: '/member/gps',
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify({"lon": position.coords.longitude,"lat":position.coords.latitude}),
                type: 'post',
                success: function (data) {

                },
                error: function (err) {

                }
            });
        })
}

}


function selectAuthor(id){
    location.href="/main/"+id;
}