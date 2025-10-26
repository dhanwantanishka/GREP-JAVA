
public class StartOfLinePattern extends AbstractPattern {

    public PatternResult matchPattern(String inputLine, String pattern) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setQuantifier(Quantifier quantifier) {
        // Quantifiers are ignored for start-of-line anchor
    }

    @Override
    public Quantifier getQuantifier() {
        return Quantifier.EXACTLY_ONE; // Anchors are not quantifiable
    }
}