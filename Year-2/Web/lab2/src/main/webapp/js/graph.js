const drawGraph=function() {
    const plot_canvas = document.getElementById("plot");

    const plot_context = plot_canvas.getContext("2d");
    let canvWidth = plot_canvas.width;
    plot_context.strokeStyle = '#ffffff';
    plot_context.beginPath();
    plot_context.moveTo(canvWidth/2, canvWidth/2);
    plot_context.arc(canvWidth/2,canvWidth/2,canvWidth/3,Math.PI/2, Math.PI);
    plot_context.lineTo(canvWidth/2, canvWidth/2);
    plot_context.fillStyle = '#3399ff';
    plot_context.fill();
    plot_context.closePath();
    plot_context.beginPath();
    plot_context.rect(canvWidth/2, canvWidth/6, canvWidth/6, canvWidth/3);
    plot_context.fillStyle = '#3399ff';
    plot_context.fill();
    plot_context.closePath();
    plot_context.beginPath();
    plot_context.moveTo(canvWidth/2,canvWidth/2);
    plot_context.lineTo(canvWidth/2, canvWidth/1.5);
    plot_context.lineTo(canvWidth/1.5, canvWidth/2);
    plot_context.lineTo(canvWidth/2, canvWidth/2);
    plot_context.fillStyle = '#3399ff';
    plot_context.fill();
    plot_context.closePath();
    plot_context.beginPath();
//Ox
    plot_context.moveTo(canvWidth/10, canvWidth/2);
    plot_context.lineTo(canvWidth*0.9, canvWidth/2);
    plot_context.lineTo(260, 140);
    plot_context.moveTo(270, 150);
    plot_context.lineTo(260, 160);
//Oy
    plot_context.moveTo(150, 30);
    plot_context.lineTo(140, 40);
    plot_context.moveTo(150, 30);
    plot_context.lineTo(160, 40);
    plot_context.moveTo(150, 30);
    plot_context.lineTo(150, 270);

    plot_context.moveTo(30, 150);
    plot_context.closePath();
    plot_context.stroke();

};

function onLoad(){

    const table = document.getElementById('result-table');
    var row = table.rows[ table.rows.length - 1 ];
    var x = parseFloat(row.cells[0].innerText);
    var y = parseFloat(row.cells[1].innerText);
    var r = parseFloat(row.cells[2].innerText);


    const plot_canvas = document.getElementById("plot");

    const plot_context = plot_canvas.getContext("2d");
    let canvWidth = plot_canvas.width;
    plot_context.strokeStyle = '#ffffff';
    plot_context.beginPath();
    // plot_context.moveTo(canvWidth/2, canvWidth/2);
    plot_context.arc(canvWidth/2 + 100*(x/r),canvWidth/2 - 100*(y/r),10,0,2*Math.PI);
    // plot_context.lineTo(canvWidth/2, canvWidth/2);
    plot_context.fillStyle = '#ff2ff2';
    plot_context.fill();
    plot_context.closePath();
    console.log("plot finished");

    var url = window.location.pathname;
    if(url !== '/lab2/')
        window.location.replace("/lab2/");
}

function drawPoint(e) {
    const r = $('input[name="r"]:checked').val();
    if (r === undefined) {
        // document.querySelector('#error-log').textContent = "Choose r!";
    } else {
        const point = getCursorPosition(e);
        const plot_canvas = document.getElementById("plot");
        const plot_context = plot_canvas.getContext("2d");
        plot_context.beginPath();
        plot_context.rect(point.x, point.y, 5, 5);
        point.x = (point.x - 150)/100*r;
        point.y = (-point.y + 150)/100*r;
        plot_context.fillStyle = 'green';
        plot_context.fill();

        const params = {'x': point.x.toFixed(2), 'y': point.y.toFixed(2), 'r':r}
        window.location.replace("/lab2/process" + formatParams(params));

        // $.ajax({
        //     type: "GET",
        //     url: "/process",
        //     data:
        //         {
        //             x: point.x.toFixed(2),
        //             y: point.y.toFixed(2),
        //             r: r
        //         },
        //     success: data => {
        //
        //
        //     },
        //     error: (jqXHR, textStatus, errorThrown) =>
        //         document.querySelector('#error-log').innerHTML = "Ошибка HTTP: " + jqXHR.status + "(" + errorThrown + ")",
        //     dataType: "html"
        // });
    }
}

function getCursorPosition(e) {
    let x;
    let y;
    const plot_canvas = document.getElementById("plot");
    if (e.pageX !== undefined && e.pageY !== undefined) {
        x = e.pageX;
        y = e.pageY;
    } else {
        x = e.clientX + document.body.scrollLeft +
            document.documentElement.scrollLeft;
        y = e.clientY + document.body.scrollTop +
            document.documentElement.scrollTop;
    }
    return {
        x: x - plot_canvas.getBoundingClientRect().left,
        y: y - plot_canvas.getBoundingClientRect().top
    }
}

function formatParams(params) {
    return "?" + Object
        .keys(params)
        .map(function (key) {
            return key + "=" + encodeURIComponent(params[key])
        })
        .join("&")
}