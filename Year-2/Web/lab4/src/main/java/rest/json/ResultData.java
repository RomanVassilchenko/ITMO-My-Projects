package rest.json;

import lombok.Data;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Data
public class ResultData {
    @DecimalMin(value = "-2", message = "X_VALUE_WRONG_RANGE")
    @DecimalMax(value = "2", message = "X_VALUE_WRONG_RANGE")
    @NotNull(message = "X_NULL_VALUE")
    private double x;
    @DecimalMin(value = "-5", message = "Y_VALUE_WRONG_RANGE")
    @DecimalMax(value = "5", message = "Y_VALUE_WRONG_RANGE")
    @NotNull(message = "Y_NULL_VALUE")
    private double y;
    @DecimalMin(value = "0.5", message = "R_VALUE_WRONG_RANGE")
    @DecimalMax(value = "2", message = "R_VALUE_WRONG_RANGE")
    @NotNull(message = "R_NULL_VALUE")
    private double r;
}
