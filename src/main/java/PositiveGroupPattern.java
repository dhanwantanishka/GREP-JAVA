public class PositiveGroupPattern extends AbstractPattern {

    public PatternResult matchPattern(String inputLine, String pattern) {
        String authorizedChars = pattern.substring(1, pattern.length() - 1);
        System.err.println("Authorized characters: " + authorizedChars);
        return new PatternResult(inputLine.chars()
                .anyMatch(c -> authorizedChars.indexOf(c) != -1));
    }

}
