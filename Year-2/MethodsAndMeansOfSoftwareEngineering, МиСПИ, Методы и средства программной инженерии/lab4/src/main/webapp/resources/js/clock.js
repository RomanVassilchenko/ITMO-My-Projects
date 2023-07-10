function updateClock() {
    const date = document.getElementById('date')
    const time = document.getElementById('time')
    date.innerHTML = moment().format('DD.MM.YYYY')
    time.innerHTML = moment().format('HH:mm:ss')
}

updateClock()
setInterval(updateClock, 1000)