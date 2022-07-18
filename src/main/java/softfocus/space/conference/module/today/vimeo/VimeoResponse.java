package softfocus.space.conference.module.today.vimeo;

import org.json.JSONObject;

public class VimeoResponse {

    private final JSONObject json;
    private final JSONObject headers;
    private final int statusCode;

    public VimeoResponse(JSONObject json, JSONObject headers, int statusCode) {
        this.json = json;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public JSONObject getJson() {
        return json;
    }

    public JSONObject getHeaders() {
        return headers;
    }

    public int getRateLimit() {
        return getHeaders().getInt("X-RateLimit-Limit");
    }

    public int getRateLimitRemaining() {
        return getHeaders().getInt("X-RateLimit-Remaining");
    }

    public String getRateLimitReset() {
        return getHeaders().getString("X-RateLimit-Reset");
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String toString() {
        return "HTTP Status Code: \n" + getStatusCode()
                + "\nJson: \n"        + getJson().toString(2)
                + "\nHeaders: \n"     + getHeaders().toString(2);
    }
}