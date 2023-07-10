<?php

// START | Validation |

function validateX($xVal){
    $X_MIN = -3;
    $X_MAX = +5;
    if(!isset($xVal)) return false;
    $numX = trim(str_replace(',','.',$xVal));
    return is_numeric($xVal) && $xVal > $X_MIN && $xVal < $X_MAX;
}

function validateY($yVal){
    $Y_MIN = -5;
    $Y_MAX = +3;
    if(!isset($yVal)) return false;
    $numY = trim(str_replace(',','.',$yVal));
    return is_numeric($yVal) && $yVal >= $Y_MIN && $yVal <= $Y_MAX;
}

function validateR($rVal){
    $R_MIN = 1;
    $R_MAX = 5;
    if(!isset($rVal)) return false;
    $numR = trim(str_replace(',','.',$rVal));
    return is_numeric($rVal) && $rVal >= $R_MIN && $rVal <= $R_MAX;
}

function validateForm($xVal, $yVal, $rVal){
    return validateX($xVal) && validateY($yVal) && validateR($rVal);
}

// END | Validation |

// START | Hit Check |

function checkTriangle($xVal, $yVal, $rVal){
    return $xVal >= 0 && $yVal >= 0 && $xVal <= $rVal && $yVal <= $rVal && $yVal <= $rVal - $xVal;
}

function checkRectangle($xVal, $yVal, $rVal){
    return $xVal >= 0 && $yVal <= 0 && $xVal <= $rVal / 2 && $yVal >= $rVal;
}

function checkSector($xVal, $yVal, $rVal){
    return $xVal <= 0 && $yVal <= 0 && $xVal * $xVal + $yVal * $yVal <= $rVal * $rVal;
}

function checkHit($xVal, $yVal, $rVal) {
    return checkTriangle($xVal, $yVal, $rVal) || checkRectangle($xVal, $yVal, $rVal) ||
      checkSector($xVal, $yVal, $rVal);
  }
  
  // Main logic
  $xVal = $_POST['xval'];
  $yVal = $_POST['yval'];
  $rVal = $_POST['rval'];
  
  $timezoneOffset = $_POST['timezone'];

  $isValid = validateForm($xVal, $yVal, $rVal);
  if(!$isValid) http_response_code(404);
  $converted_isValid = $isValid ? 'true' : 'false';
  $isHit = $isValid ? checkHit($xVal, $yVal, $rVal) : 'Easter egg!';
  $converted_isHit = $isHit ? 'true' : 'false';
  
  $currentTime = date('H:i:s', time()-$timezoneOffset*60);
  $executionTime = round(microtime(true) - $_SERVER['REQUEST_TIME_FLOAT'], 7);
  
  $jsonData = '{' .
    "\"validate\":$converted_isValid," .
    "\"xval\":\"$xVal\"," .
    "\"yval\":\"$yVal\"," .
    "\"rval\":\"$rVal\"," .
    "\"curtime\":\"$currentTime\"," .
    "\"exectime\":\"$executionTime\"," .
    "\"hitres\":$converted_isHit" .
    "}";
  
  echo $jsonData;
  