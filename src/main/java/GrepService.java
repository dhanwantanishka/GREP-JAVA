import java.util.ArrayList;
import java.util.List;

public class GrepService {

    public static GrepResult matchPattern(String inputLine, String patternAsString) throws Exception {

        boolean startOfLineActive = false;
        int currentUnmatchCount = 0;
        PatternService patternService = new PatternService();
        patternService.setPattern(patternAsString);

        List<Boolean> matches = new ArrayList<>();

        int i = 0;

        PatternInt pattern = patternService.returnNextPattern();

        if (pattern instanceof StartOfLinePattern) {
            startOfLineActive = true;
            pattern = patternService.returnNextPattern();

        }

        String nextInput = inputLine;
        while (pattern != null) {

            /*
             * if (nextInput.length() == 0) {
             * System.err.println("*** ICI ***");
             * if (!(pattern instanceof EndOfLinePattern)
             * && !(pattern.getQuantifier() == Quantifier.ZERO_OR_ONE)) {
             * matches.add(false);
             * }
             * 
             * break;
             * }
             */

            String c = nextInput.length() > 0 ? nextInput.substring(0, 1) : "";

            String patternClass = pattern != null ? pattern.getClass().getSimpleName() : "No Pattern found";
            PatternResult result;
            if (pattern instanceof AlternationPattern || pattern instanceof PatternAggregatorPattern
                    || pattern instanceof CapturePattern) {
                result = pattern.matchPattern(nextInput);
            } else {
                result = pattern.matchPattern(c);
            }

            System.err.println(
                    "\n*** GS i=" + i + " current CharInput = " + c + " input=" + nextInput + " pattern="
                            + patternClass
                            + " match=" + result.match + " numberOfPatternPassed="
                            + patternService.getNumberOfPatternPassed() + "***\n");

            if (result.match) {

                // patternAsWorkingString =
                // patternAsWorkingString.substring(result.captureGroup.length());
                matches.add(result.match);

                if (pattern.getQuantifier() == Quantifier.ONE_OR_MORE) {
                    System.err.println("\nGS i=" + i + " QUANTIFIER " + pattern.getQuantifier() + "\n\n");
                } else {
                    pattern = patternService.returnNextPattern();
                }

                currentUnmatchCount = 0;

                System.err.println("ICI 3");
            } else {

                System.err.println(
                        "\nGS i=" + i + " UNMATCH numberOfPatternPassed="
                                + patternService.getNumberOfPatternPassed() + "\n");

                PatternInt previousPattern = patternService.getPreviousPatternWithMultipleTimeQuantitier();
                if (previousPattern != null && previousPattern.countMatches() > 1
                        && ((patternService.isSamePatternWithoutQuantifier(pattern, previousPattern)
                                || previousPattern instanceof WilcardCharacterPattern))) {
                    System.err.println(
                            "\nGS i=" + i + " Pattern is covered by previous with ONE OR MORE countMatches="
                                    + previousPattern.countMatches() + "\n");

                    previousPattern.decrementCountMatches();
                    pattern = patternService.returnNextPattern();
                    continue;
                }

                if (pattern.getQuantifier() == Quantifier.ONE_OR_MORE && pattern.countMatches() > 0) {
                    System.err.println("\nGS i=" + i + " Return next pattern because ONE_OR_MORE\n");
                    pattern = patternService.returnNextPattern();
                    continue;
                }

                if (pattern.getQuantifier() == Quantifier.ZERO_OR_ONE && pattern.countMatches() == 0) {
                    System.err.println("\nGS i=" + i + " Return next pattern because ZERO_OR_ONE\n");
                    pattern = patternService.returnNextPattern();
                    continue;
                }

                currentUnmatchCount++;

                if (startOfLineActive && patternService.getNumberOfPatternPassed() >= 2) {
                    System.err.println("Error startOfLine");
                    matches.add(false);
                    break;
                }
                if (!startOfLineActive && currentUnmatchCount == 1
                        && patternService.getNumberOfPatternPassed() == 2) {
                    System.err.println("\nGS i=" + i + " Restore previous pattern\n");
                    pattern = patternService.rewindPattern();

                    continue;
                }

                if (!startOfLineActive && patternService.getNumberOfPatternPassed() > 1) {
                    System.err.println("GS i=" + i + " numberOfPatternPassed="
                            + patternService.getNumberOfPatternPassed() + " Pattern failed in the middle");
                    matches.add(false);
                    break;
                }

                System.err.println("** ICI ** ");

                if (nextInput.length() == 0) {
                    System.err.println("** ICI 2** ");
                    matches.add(false);
                    break;
                }

            }

            if (result.getCaptureGroup() != null) {

                int max = Math.min(result.getCaptureGroup().length(), nextInput.length());
                System.err.println("cg " + result.getCaptureGroup() + " min " + max);
                nextInput = nextInput.substring(max);
            } else {
                nextInput = "".equals(nextInput) ? "" : nextInput.substring(1);
            }
            System.out.println("GS next Pattern=" + pattern + " NextInput=" + nextInput);
            i++;

        }

        System.err.println("\nResult : ");
        java.util.stream.IntStream.range(0, matches.size())
                .forEach(idx -> System.err.println("i=" + idx + " result=" + matches.get(idx)));
        System.err
                .println("Capture=" + inputLine.substring(0, inputLine.length() - nextInput.length())
                        + " NextPattern=" + nextInput);

        return new GrepResult(!matches.isEmpty() && matches.stream().allMatch(m -> m),
                inputLine.substring(0, inputLine.length() - nextInput.length()));

    }

}