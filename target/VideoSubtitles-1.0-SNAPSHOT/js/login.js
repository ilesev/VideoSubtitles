$(document).ready(function () {
    $("input[type=text]", "input[type=password]").keydown(function (e) {
        if (e.keyCode == 13) {
            $(this).closest("form").submit();
        }
    });
});
