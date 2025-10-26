import java.util.List;

public class CapturePattern extends AbstractPattern {

    private List<PatternInt> listCaptures;

    // ajoute une variable patternAggregator avec un getter et un setter
    public List<PatternInt> getPatternAggregator() {
        return listCaptures;
    }

    public void setPatternAggregator(List<PatternInt> listCaptures) {
        this.listCaptures = listCaptures;
    }

    @Override
    public PatternResult matchPattern(String input, String patternAsString) {
        int captureIndex = Integer.parseInt(patternAsString.substring(1));
        PatternInt patternAggregator = listCaptures.get(captureIndex - 1);
        System.err.println("CapturePattern captureGroupPattern=" + patternAggregator.getPatternString());
        
        // For backreferences, we need to check if the input starts with the captured text
        String capturedText = patternAggregator.getCapturedText();
        if (capturedText != null && input.startsWith(capturedText)) {
            PatternResult result = new PatternResult(true);
            result.setCaptureGroup(capturedText);
            return result;
        }
        
        return new PatternResult(false);
    }
}