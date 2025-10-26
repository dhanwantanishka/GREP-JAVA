import java.util.Objects;

public abstract class AbstractPattern implements PatternInt {

    protected Quantifier quantifier = Quantifier.EXACTLY_ONE;
    protected String patternString;
    protected String capturedText;

    private int countMatches = 0;

    @Override
    public PatternResult matchPattern(String input) {
        PatternResult result = matchPattern(input, getPatternString());
        if (result.match) {
            countMatches++;
        }

        return result;
    }

    @Override
    public abstract PatternResult matchPattern(String input, String pattern);

    @Override
    public void setQuantifier(Quantifier quantifier) {
        this.quantifier = quantifier;
    }

    @Override
    public Quantifier getQuantifier() {
        return this.quantifier;
    }

    @Override
    public void setPatternString(String patternString) {
        this.patternString = patternString;
    }

    @Override
    public String getPatternString() {
        return this.patternString;
    }

    @Override
    public int countMatches() {
        return countMatches;
    }

    @Override
    public void decrementCountMatches() {
        countMatches--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractPattern that = (AbstractPattern) o;
        return quantifier == that.quantifier && Objects.equals(patternString, that.patternString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantifier, patternString);
    }
    
    @Override
    public void setCapturedText(String capturedText) {
        this.capturedText = capturedText;
    }
    
    @Override
    public String getCapturedText() {
        return capturedText;
    }
}