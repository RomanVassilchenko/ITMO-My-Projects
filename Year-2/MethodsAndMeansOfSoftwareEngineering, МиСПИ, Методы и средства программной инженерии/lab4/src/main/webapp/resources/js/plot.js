document.querySelector('svg').addEventListener('click', event => {
    const position = getCursorPosition(event)
    const r = document.getElementById('main-form:r').value
    const x = (position.x - 150) / 100 * r
    const y = (150 - position.y) / 100 * r
    document.getElementById('main-form:x').value = x.toFixed(3)
    document.getElementById('main-form:y').value = y.toFixed(3)
    document.getElementById('main-form:submit-button').click()
})

function renderPlot(results) {
    const r = document.getElementById('main-form:r').value
    document.getElementById('dots').innerHTML = ''
    results.forEach(result => {
        const x = (result.coordinates.x * 100 / r) + 150
        const y = (result.coordinates.y * -100 / r) + 150
        const color = result.successful ? 'green' : 'red'
        createPointer(x, y, color)
    })
}

function getCursorPosition(event) {
    const rect = document.querySelector('svg').getBoundingClientRect()
    return {
        x: event.clientX - rect.left,
        y: event.clientY - rect.top
    }
}

function createPointer(x, y, color) {
    const pointer = document.createElementNS("http://www.w3.org/2000/svg", 'circle');
    pointer.setAttribute('cx', x)
    pointer.setAttribute('cy', y)
    pointer.setAttribute('r', '3')
    pointer.setAttribute('fill', color)
    document.getElementById('dots').appendChild(pointer)
}