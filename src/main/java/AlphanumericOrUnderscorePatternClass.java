public class AlphanumericOrUnderscorePatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        return "\\w".equals(pattern);
    }

    @Override
    public PatternInt newPatternInt() {
        return new AlphanumericOrUnderscorePattern();
    }
}