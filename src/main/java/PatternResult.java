public class PatternResult {

    public boolean match;
    public String captureGroup;

    public PatternResult(boolean match) {
        this.match = match;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public void setCaptureGroup(String captureGroup) {
        this.captureGroup = captureGroup;
    }

    public String getCaptureGroup() {
        return captureGroup;
    }

}