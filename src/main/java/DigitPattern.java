public class DigitPattern extends AbstractPattern {

    @Override
    public PatternResult matchPattern(String inputLine, String pattern) {
        PatternResult result = new PatternResult(inputLine.chars().anyMatch(Character::isDigit));
        result.setCaptureGroup(inputLine);
        return result;
    }
}