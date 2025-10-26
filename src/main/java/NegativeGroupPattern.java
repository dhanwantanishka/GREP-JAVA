public class NegativeGroupPattern extends AbstractPattern {

    @Override
    public PatternResult matchPattern(String inputLine, String pattern) {

        String unauthorizedChars = pattern.substring(2, pattern.length() - 1);
        System.err.println("Unauthorized characters: " + unauthorizedChars);
        PatternResult result = new PatternResult(false);

        if (inputLine.isEmpty()) {
            result.setMatch(false);
        } else {
            result.setMatch(!unauthorizedChars.contains(inputLine.substring(0, 1)));
        }

        return result;

    }
}