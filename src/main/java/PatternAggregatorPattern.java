public class PatternAggregatorPattern extends AbstractPattern {

    @Override
    public PatternResult matchPattern(String input, String patternAsString) {

        String innerPattern = patternAsString.substring(1, patternAsString.length() - 1);

        try {

            System.out.println("*** PAP  input=" + input + " pattern=" + innerPattern + " ***");
            GrepResult grepResult = GrepService.matchPattern(input, innerPattern);

            PatternResult r = new PatternResult(grepResult.getMatch());
            r.setCaptureGroup(grepResult.getCapture());
            // Store the captured text for backreferences
            if (grepResult.getCapture() != null) {
                setCapturedText(grepResult.getCapture());
            }
            System.err.println("PatternAggregatorPattern r=" + r.match + " capture=" + r.captureGroup);
            System.out.println("*** END PAP ***");

            return r;
        } catch (Exception e) {
            return new PatternResult(false);
        }
    }

}