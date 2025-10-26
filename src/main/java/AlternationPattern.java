public class AlternationPattern extends AbstractPattern {

    @Override
    public PatternResult matchPattern(String inputLine, String pattern) {
        // Extract content between parentheses, e.g., "cat|dog" from "(cat|dog)"
        String innerPattern = pattern.substring(1, pattern.length() - 1);

        // Split by the alternation character '|'. We need to escape it for the split
        // method.
        String[] alternatives = innerPattern.split("\\|");

        PatternResult result = new PatternResult(false);
        // Find the first alternative that matches the beginning of the input line
        for (String alternative : alternatives) {
            GrepResult grepResult;
            try {
                grepResult = GrepService.matchPattern(inputLine, alternative);

                if (grepResult.isMatch()) {
                    PatternResult r = new PatternResult(grepResult.getMatch());
                    r.setCaptureGroup(grepResult.getCapture());
                    return r;
                }
            } catch (Exception e) {
            }
        }

        // If no alternative matches
        System.err.println("AlternationPattern no match: input='" + inputLine + "', pattern='" + pattern + "'");
        return result;
    }

}