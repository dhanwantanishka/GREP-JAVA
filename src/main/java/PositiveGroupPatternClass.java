public class PositiveGroupPatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        return patternIsPositiveCharacterGroup(pattern);
    }

    public boolean patternIsPositiveCharacterGroup(String pattern) {
        return pattern.startsWith("[") && pattern.endsWith("]");
    }

    @Override
    public PatternInt newPatternInt() {
        return new PositiveGroupPattern();
    }
}