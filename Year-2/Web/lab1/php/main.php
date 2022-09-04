<?php

// START | Validation |

function validateX($xVal){
    $X_MIN = -3;
    $X_MAX = +5;

    if(!isset($xVal)) return false;
    $numX = str_replace(',','.',$xVal);
    return is_numeric($xVal) && $xVal > $X_MIN && $xVal < $X_MAX;
}

function validateY($yVal){
    return isset($yVal);
}

function validateR($rVal){
    return isset($rVal);
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
  