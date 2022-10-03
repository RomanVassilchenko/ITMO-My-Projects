%macro test 3
db %1
db ', '
db %2 
db ', '
db %3
%endmacro
     
test "hello", "my", "world"