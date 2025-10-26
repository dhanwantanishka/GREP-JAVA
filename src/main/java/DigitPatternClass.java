public class DigitPatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        return "\\d".equals(pattern);
    }

    @Override
    public DigitPattern newPatternInt() {
        return new DigitPattern();
    }

}