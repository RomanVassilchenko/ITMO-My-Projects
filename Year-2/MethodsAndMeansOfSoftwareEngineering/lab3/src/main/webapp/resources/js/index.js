$(function () {
    function clock() {
        let date = new Date();
        let day = date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear();
        let hours = date.getHours();
        let minutes = date.getMinutes();
        let seconds = date.getSeconds();

        hours = hours < 10 ? "0" + hours : hours;
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        let time = hours + ":" + minutes + ":" + seconds;
        document.getElementById("clock-date").innerHTML = day;
        document.getElementById("clock-time").innerHTML = time;
        setTimeout(clock, 12000);
    };

    clock();
});