package common.interaction;

import java.io.Serializable;

/**
 * Class for get response value.
 */
public class Response implements Serializable {
    private ResponseResult responseResult;
    private String responseBody;

    public Response(ResponseResult responseResult, String responseBody) {
        this.responseResult = responseResult;
        this.responseBody = responseBody;
    }

    /**
     * @return Response result
     */

    public ResponseResult getResponseResult() {
        return responseResult;
    }

    /**
     *
     * @return Response body
     */
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public String toString() {
        return "Response[" + responseResult + ", " + responseBody + "]";
    }
}