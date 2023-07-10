%macro colon 2
    %ifstr %1                                       ; If first element is a string
        %ifid %2                                    ; And second is an identifier
            %2:
                %ifdef next                         ; Check if next exist
                dq next                             ; If exist -> set it as identifier
                %else
                dq 0                                ; Else set 0
                %endif 
                db %1, 0                            ; Set key
                %define next %2                     ; Define "next" for the next element
        %else
            %error "Tag isn't an identifier!"       ; Error of identifier
        %endif
    %else 
        %error "Key is not a string!"               ; Error of key
    %endif
%endmacro
