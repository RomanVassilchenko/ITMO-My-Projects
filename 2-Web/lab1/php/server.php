<?php>
$start_time = microtime(true);
header("Content-Type: application/json; charset=utf-8");

function send_result(int $code, string $output){
    http_response_code($code);
    echo "{\"output\": $output}";
    exit;
}

function parse_json(array $required_params){
    if($_SERVER["CONTENT_TYPE"] !== "application/json"){
        send_result(400, "Not a json");
    }
    $request_body = file_get_contents("php://input");
    $json = json_decode($request_body, true);
    if($json === null){
        send_result(400, "Not a json");
    }

    foreach($required_params as $key => $type){
        if(!array_key_exists($key,$json)){
            send_result(400, "No $key in json");
        }
        if(!call_user_func($type, $key)){
            send_result(400, "$key must be $type");
        }
    }
    return $json;
}

$method = $_SERVER["REQUEST_METHOD"];
if($method !== "POST"){
    send_result(405, "$method is not POST")
}

$data = parse_json(
    "x" => "is_numeric",
    "y" => "is_numeric",
    "r" => "is_numeric"
);

$x = $data["x"];
$y = $data["y"];
$r = $data["r"];

if($r <= 0) {
    send_result(400, "R must be positive");
}

function is_in_area(array $coordinates){
    $x = $coordinates[0];
    $y = $coordinates[1];
    $r = $coordinates[2];

    if($x >= 0 && $y >= 0){
        $hit = $r - $r*$x >= $y;
    } else if ($x < 0 && $y > 0) {
        $hit = false;
    } else if($x <= 0 && $y <= 0){
        $hit = ($r >= sqrt($x*$x + $y*$y));
    } else if($x >= 0 && $y <= 0){
        $hit = ($x <= $r/2 && $y <= -$r);
    }
    return $hit;
}

$hit = is_in_area([$x,$y,$r]);


$end_time = microtime(true);
	$script_time = number_format($end_time - $start_time, 5, '.', '');
	$end_datetime = date("d/m/Y H:i:s", $end_time);
?>

{
    "result": <?=$hit ? "true" : "false" ?>,
    "time_now": <?=$end_time?>,
    "script_time": <?$script_time?>
}