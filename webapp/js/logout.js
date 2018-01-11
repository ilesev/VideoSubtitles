$(document).ready(function () {
    $('#logout').click(function () {
        $.ajax({
            type: 'POST',
            url: "/logout",
            success: function () {
                window.location.href = "/login";
            }
        })
    })
});