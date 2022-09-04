function onClickR(input){
    document.getElementById("r-textinput").value = input;
}

function resetPreviousData(){
  if (localStorage.getItem("attempts") !== null) {
    var attempts = localStorage['attempts'].split('|');
    attempts.forEach(element => {
      $('#result-table').append(element);
    }); 
  }
}

$(function() {

    function isNumeric(n) {
      return !isNaN(parseFloat(n)) && isFinite(n);
    }
  
    function validateX() {
        const X_MIN = -3;
        const X_MAX = +5;
        let xField = $('#x-textinput');
        let numX = xField.val().replace(',','.');
        if(isNumeric(numX) && numX > X_MIN && numX < X_MAX) {
            xField.removeClass('text-error');
            return true;
        } else {
            xField.addClass('text-error');
            return false;
        }
    }
    function validateY() {
        if ($('.y-radio').is(':checked')) {
          $('.ybox-label').removeClass('box-error');
          return true;
        } else {
          $('.ybox-label').addClass('box-error');
          return false;
        }
      }
    function validateR() {
        const R_VALUES = ["1","2","3","4","5","6"];
        let rButtonLabel = $('#r-textinput');
        let numR = rButtonLabel.val().replace(',', '.');
        if(isNumeric(numR) && numR in R_VALUES){
            rButtonLabel.removeClass('button-error');
            return true;
        } else {
            rButtonLabel.addClass('button-error');
            return false;
        }
    }
    function validateForm() {
        return validateX() & validateY() & validateR();
    }

    $('#input-form').on('submit', function(event) {
        event.preventDefault();
        if (!validateForm()) return;
        $.ajax({
          url: 'php/main.php',
          method: 'POST',
          data: $(this).serialize() + '&timezone=' + new Date().getTimezoneOffset(),
          dataType: "json",
          beforeSend: function() {
            $('.button').attr('disabled', 'disabled');
          },
          success: function(data) {
            $('.button').attr('disabled', false);
            if (data.validate) {

              newRow = `<tr>
              <td class="table-text">${data.xval}</td>
              <td class="table-text">${data.yval}</td>
              <td class="table-text">${data.rval}</td>
              <td class="table-text">${data.curtime}</td>
              <td class="table-text">${data.exectime}</td>
              <td class="${(data.hitres) ? "hit-text":"miss-text"}">${data.hitres}</td>
              </tr>`;

              $('#result-table').append(newRow);
              
              if (localStorage.getItem("attempts") !== null) {
                localStorage['attempts'] += "|" + newRow;
              } else {
                localStorage['attempts'] = newRow;
              }
            }
          }
        });
      });

      $('#input-form').on('reset', function(event) {
        $("#result-table").empty().append(`<tr class="table-header">
              <th class="coords-col">X</th>
              <th class="coords-col">Y</th>
              <th class="coords-col">R</th>
              <th class="time-col">Current time</th>
              <th class="time-col">Execution time</th>
              <th class="hitres-col">Hit result</th>
            </tr>`); 

            localStorage['attempts'] = null;
      });

}
);