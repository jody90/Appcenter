function showNotification(message, type, title, delay) {
    type = "undefined" != typeof type && 0 != type.length ? type : "default",
    title = "undefined" != typeof title && 0 != title.length ? title : false,
    delay = "undefined" != typeof delay && 0 != delay.length ? delay : 5000;
    var icon = "";
    switch (type) {
    case "success":
        icon = "glyphicon glyphicon-ok";
        break;
    case "error":
        icon = "glyphicon glyphicon-remove";
        break;
    case "warning":
        icon = "glyphicon glyphicon-exclamation-sign"
    }
    Lobibox.notify(type, {
        msg: message,
        title: title,
        showClass: "flip",
        hideClass: "zoomOut",
        delay: delay,
        icon: icon,
        pauseDelayOnHover: true,
        continueDelayOnInactiveTab: false,
        size: "normal",
        width: 99999,
        position: "center top",
        messageHeight: 150,
        sound: false,
        showAfterPrevious: false
    })
}

$(document).on("click", function() {
	console.log("clicked");
	showNotification("message", "success", "Hat alles geklappt");
	showNotification("message", "error", "Hat nichts geklappt");
	showNotification("message", "warning", "war nicht alles ok");
})