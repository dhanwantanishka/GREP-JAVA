public class GrepResult {
    private boolean match;
    private String capture;

    public GrepResult(boolean match, String capture) {
        this.match = match;
        this.capture = capture;
    }

    public boolean isMatch() {
        return this.match;
    }

    public boolean getMatch() {
        return this.match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getCapture() {
        return this.capture;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }

}