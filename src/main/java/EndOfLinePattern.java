
public class EndOfLinePattern extends AbstractPattern {

    @Override
    public PatternResult matchPattern(String inputLine, String pattern) {
        // Quantifiers don't apply to anchors like $
        System.err.println("EndOfLinePattern inputLine=" + inputLine.length() + "");
        return new PatternResult(inputLine.length() == 0);
    }

    @Override
    public void setQuantifier(Quantifier quantifier) {
        // Quantifiers are ignored for end-of-line anchor
        // Do nothing to override the default behavior
    }

    @Override
    public Quantifier getQuantifier() {
        return Quantifier.EXACTLY_ONE; // Anchors are not quantifiable
    }
}