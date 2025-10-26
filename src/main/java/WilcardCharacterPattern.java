
public class WilcardCharacterPattern extends AbstractPattern {

    public PatternResult matchPattern(String inputLine, String pattern) {

        boolean result = false;
        if (!inputLine.isEmpty()) {
            result = Character.isDefined(inputLine.charAt(0));
        }

        System.err
                .println("WilcardCharacterPattern inputLine=" + inputLine
                        + " result=" + result);

        return new PatternResult(result);
    }

}