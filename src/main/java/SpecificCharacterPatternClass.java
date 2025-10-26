import java.util.ArrayList;
import java.util.List;

public class SpecificCharacterPatternClass implements PatternClass {

    private List<String> illegalCharacters = new ArrayList<>();

    public SpecificCharacterPatternClass() {
        illegalCharacters.add("[");
        illegalCharacters.add("\\");
        illegalCharacters.add("^");
        illegalCharacters.add("$");
        illegalCharacters.add("+");
        illegalCharacters.add(".");
        illegalCharacters.add("(");
        illegalCharacters.add(")");
    }

    @Override
    public boolean is(String pattern) {

        return (pattern.length() == 2 && pattern.equals("\\\\"))
                || (pattern.length() == 1 && !illegalCharacters.contains(pattern));
    }

    @Override
    public PatternInt newPatternInt() {
        return new SpecificCharacterPattern();
    }
}