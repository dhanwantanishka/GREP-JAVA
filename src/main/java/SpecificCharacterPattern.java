
public class SpecificCharacterPattern extends AbstractPattern {

    public PatternResult matchPattern(String inputLine, String pattern) {
        boolean result = false;
        if (inputLine.isEmpty()) {
            result = false;
        } else {

            String internalInputLine = inputLine.substring(0, 1);
            String internalPattern = pattern.substring(0, 1);
            result = internalPattern.equals(inputLine);
            System.err
                    .println("SpecificCharacterPattern matchPattern inputLine=" + inputLine + " pattern="
                            + internalPattern
                            + " result=" + internalPattern.equals(inputLine));

        }

        return new PatternResult(result);
    }

}