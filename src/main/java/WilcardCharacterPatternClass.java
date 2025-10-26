import java.util.ArrayList;
import java.util.List;

public class WilcardCharacterPatternClass implements PatternClass {

    @Override
    public boolean is(String pattern) {
        return ".".equals(pattern);
    }

    @Override
    public PatternInt newPatternInt() {
        return new WilcardCharacterPattern();
    }
}