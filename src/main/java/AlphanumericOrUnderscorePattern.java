public class AlphanumericOrUnderscorePattern extends AbstractPattern {

    @Override
    public PatternResult matchPattern(String inputLine, String pattern) {
        return new PatternResult(inputLine.chars().anyMatch(this::isAlphanumericOrUnderscore));
    }

    public boolean isAlphanumericOrUnderscore(int c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }
}