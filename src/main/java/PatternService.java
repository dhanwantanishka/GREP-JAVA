import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class PatternService {

    private int numberOfPatternPassed = 0;
    private String rawPattern;

    private List<PatternInt> previousPatterns;
    private PatternInt currentPattern;
    private String previousRawPattern, currentRawPattern, nextRawPattern;

    private List<PatternClass> patterns;

    public PatternService() {
        patterns = new ArrayList<>();
        patterns.add(new DigitPatternClass());
        patterns.add(new NegativeGroupPatternClass());
        patterns.add(new PositiveGroupPatternClass());
        patterns.add(new AlphanumericOrUnderscorePatternClass());
        patterns.add(new SpecificCharacterPatternClass());
        patterns.add(new StartOfLinePatternClass());
        patterns.add(new WilcardCharacterPatternClass());
        patterns.add(new EndOfLinePatternClass());
        patterns.add(new AlternationPatternClass());
        patterns.add(new PatternAggregatorClass());
        patterns.add(new CapturePatternClass());

        previousPatterns = new ArrayList<>();
    }

    public void setPattern(String pattern) {
        this.rawPattern = pattern;
        this.currentRawPattern = rawPattern;
        this.nextRawPattern = rawPattern;
    }

    public PatternInt returnNextPattern() throws Exception {

        StringBuffer pattern = new StringBuffer();

        if (currentPattern != null) {
            previousPatterns.add(currentPattern);
        }
        currentPattern = null;

        previousRawPattern = currentRawPattern;
        currentRawPattern = nextRawPattern;

        int i = 0;
        for (; i < currentRawPattern.length(); i++) {
            Character c = currentRawPattern.charAt(i);
            pattern.append(c);
            // System.err.println("RNP i=" + i + " Current pattern: " + pattern.toString() +
            // " current Char=" + c);
            Optional<PatternClass> optPatternClass = patterns.stream().filter(p -> p.is(pattern.toString()))
                    .findFirst();

            if (optPatternClass.isPresent()) {
                // si pattern instance of PatternAggragtorPattern, ajoute l'instance dans une
                // liste PatternCapture en variabl
                if (optPatternClass.get() instanceof CapturePatternClass) {
                    CapturePattern capturePattern = (CapturePattern) optPatternClass.get().newPatternInt();
                    capturePattern.setPatternAggregator(
                            previousPatterns.stream().filter(
                                    p -> p instanceof PatternAggregatorPattern || p instanceof AlternationPattern)
                                    .toList());
                    currentPattern = capturePattern;
                } else {
                    currentPattern = optPatternClass.get().newPatternInt();
                }

            } else {
                currentPattern = null;
            }
            if (currentPattern != null) {
                numberOfPatternPassed++;

                nextRawPattern = currentRawPattern.substring(i + 1);
                affectQuantifierIfNeeded();
                currentPattern.setPatternString(currentRawPattern.substring(0, i + 1));
                System.err.println("RNP CURRENT PATTERN: " + currentRawPattern + " currentRawPattern="
                        + currentRawPattern.substring(0, i + 1) + " NEXT PATTERN: " + nextRawPattern
                        + " QUANTIFIER: " + currentPattern.getQuantifier());

                break;
            }
        }

        System.err.println("RNP currentPattern=" + currentPattern + " i=" + i + " currentRawPattern.lenght="
                + currentRawPattern.length());
        if (currentPattern == null && i != 0) {
            throw new Exception("RNP Invalid pattern");
        }

        return currentPattern;

    }

    private void affectQuantifierIfNeeded() {

        Stream.of(Quantifier.values()).forEach(q -> {
            affectQuantifierIfNeeded(q);
        });

    }

    private void affectQuantifierIfNeeded(Quantifier quantifier) {
        if (!nextRawPattern.isEmpty() && quantifier.c == nextRawPattern.charAt(0)) {
            nextRawPattern = nextRawPattern.substring(1);
            currentPattern.setQuantifier(quantifier);
        }
    }

    public int getNumberOfPatternPassed() {
        return numberOfPatternPassed;
    }

    public PatternInt getPreviousPattern() {

        System.err.println("RNP PreviousPattern ");
        java.util.stream.IntStream.range(0, previousPatterns.size())
                .forEach(idx -> System.err.println("i=" + idx + " result=" + previousPatterns.get(idx)));
        return previousPatterns.isEmpty() ? null : previousPatterns.get(previousPatterns.size() - 1);
    }

    public boolean isSamePatternWithoutQuantifier(PatternInt patternA, PatternInt patternB) {
        if (patternA == patternB)
            return true;

        System.out.println("RNP isSamePattern " + patternA.getPatternString() + " " + patternB.getPatternString());
        if (patternB == null || patternA.getClass() != patternB.getClass())
            return false;
        System.out.println("RNP isSamePattern " + patternA.getPatternString() + " " + patternB.getPatternString());
        return Objects.equals(patternA.getPatternString(), patternB.getPatternString());
    }

    public PatternInt rewindPattern() {
        if (previousPatterns.size() == 0) {
            return null;
        }
        int lastIndex = previousPatterns.size() - 1;
        PatternInt previousPattern = previousPatterns.get(lastIndex);
        previousPatterns.remove(lastIndex);

        nextRawPattern = previousRawPattern;
        currentRawPattern = null;
        numberOfPatternPassed--; // -1 to get bach and -1 because we have to relaunch getNextPattern

        return previousPattern;
    }

    public PatternInt getPreviousPatternWithMultipleTimeQuantitier() {

        for (int i = previousPatterns.size() - 1; i >= 0; i--) {
            PatternInt previousPattern = previousPatterns.get(i);
            if (previousPattern.getQuantifier().multiple == true) {
                System.err.println("RNP PreviousPattern " + previousPattern.getPatternString());
                return previousPattern;
            }
        }
        System.err.println("RNP PreviousPattern NULL");
        return null;
    }

}