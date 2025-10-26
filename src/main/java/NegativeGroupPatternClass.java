public class NegativeGroupPatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        return patternIsNegativeCharacterGroup(pattern);
    }

    private boolean patternIsNegativeCharacterGroup(String pattern) {
        return pattern.startsWith("[^") && pattern.endsWith("]");
    }

    @Override
    public PatternInt newPatternInt() {
        return new NegativeGroupPattern();
    }
}