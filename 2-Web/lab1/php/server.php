<?php>
$start_time = microtime(true);
session_start();
unset($_SESSION['hit']);

$x = floatval($_POST['x']);
$y = floatval($_POST['y']);
$r = floatval($_POST['r']);
$hit = false;

if($x >= 0 && $y >= 0){
    $hit = $r - $r*$x >= $y;
} else if ($x < 0 && $y > 0) {
    $hit = false;
} else if($x <= 0 && $y <= 0){
    $hit = ($r >= sqrt($x*$x + $y*$y));
} else if($x >= 0 && $y <= 0){
    $hit = ($x <= $r/2 && $y <= -$r);
}

if($hit) {
    $_SESSION['hit'] = 'Successful hit';
} else {
    $_SESSION['hit'] = 'You missed';
}

$attempt = ["x" => $x, "y" => $y, "r" => $r, "hit" => $hit, "attempt_time" => $start_time, "process_time" => round((microtime(true) - $start_time), 5)];
if (isset($_SESSION['attempts'])) {
    array_push($_SESSION['attempts'], $attempt);
} else {
    $_SESSION['attempts'] = array($attempt);
}