window.onload = updateClock;

function updateClock() {
    let dt = new Date();
    let time = dt.getHours()
        + ":" + ((dt.getMinutes() < 10) ? ("0" + dt.getMinutes()) : dt.getMinutes())
        + ":" + ((dt.getSeconds() < 10) ? ("0" + dt.getSeconds()) : dt.getSeconds());
    let date = ((dt.getDay() < 10) ? ("0" + dt.getDay()) : dt.getDay())
        + "-" + ((dt.getMonth() < 10) ? ("0" + dt.getMonth()) : dt.getMonth())
        + "-" + dt.getFullYear();

    document.getElementById("clock-time").innerText = time;
    document.getElementById("clock-date").innerText = date;
    setTimeout(updateClock, 13000);
}



