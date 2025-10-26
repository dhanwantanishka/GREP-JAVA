public class PatternAggregatorClass implements PatternClass {

    @Override
    public boolean is(String pattern) {

        if (pattern.startsWith("(")) {
            int countOpenedParenthesis = 0, countClosedParenthesis = 0;
            for (int i = 0; i < pattern.length(); i++) {
                char c = pattern.charAt(i);
                if (c == '(') {
                    countOpenedParenthesis++;
                }
                if (c == ')') {
                    countClosedParenthesis++;
                }
            }

            return countOpenedParenthesis == countClosedParenthesis && pattern.endsWith(")");
        }

        return false;
    }

    @Override
    public PatternInt newPatternInt() {
        return new PatternAggregatorPattern();
    }

}