$(function() {

        function isNumeric(n) {
            return !isNaN(parseFloat(n)) && isFinite(n);
        }

        function validateX() {
            const X_MIN = -5;
            const X_MAX = +5;
            let xField = $('#x-textinput');
            let numX = xField.val().trim().replace(',','.');

            xField.val(numX);
            if(isNumeric(numX) && numX > X_MIN && numX < X_MAX) {
                xField.removeClass('text-error');
                return true;
            } else {
                xField.addClass('text-error');
                return false;
            }
        }
        function validateR() {
            if ($('.r-radio').is(':checked')) {
                $('.rbox-label').removeClass('box-error');
                return true;
            } else {
                $('.rbox-label').addClass('box-error');
                return false;
            }
        }
        function validateY() {
            const Y_VALUES = ["-2","-1.5","-1","-0.5","0","0.5","1","1.5","2"];
            let selector = document.getElementById("y");
            let ySelector = $('#r-textinput');
            let numY = selector.options[selector.selectedIndex].text.trim().replace(',', '.');
            if(isNumeric(numY) && (numY in Y_VALUES || (numY >= -2 && numY <= 2))){
                ySelector.removeClass('text-error');
                return true;
            } else {
                ySelector.addClass('text-error');
                return false;
            }
        }
        function validateForm() {
            return validateX() & validateY() & validateR();
        }


    }
);

const clearBtn = document.getElementById("resetBtn");
clearBtn.addEventListener("click", e => {
    e.preventDefault();
    const params = {'clear': true}
    window.location.replace("/lab2/process" + formatParams(params));
})

const submitBtn = document.getElementById("submitBtn");
function toggleSubmitBtn() {
    alert("Submit is working");
    submitBtn.disabled = !(xValid && yValid && rValid)
}

document.addEventListener('DOMContentLoaded', ()=> {
    document.getElementById("plot").addEventListener('click', drawPoint);
});

