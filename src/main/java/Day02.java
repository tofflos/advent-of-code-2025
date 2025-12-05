void main() throws IOException {

    var pattern = Pattern.compile("\\d*");

    var identifiers = pattern.matcher(Files.readString(Path.of("02.txt"))).results()
            .map(MatchResult::group)
            .filter(s -> !s.isEmpty())
            .map(Long::parseLong)
            .gather(Gatherers.windowFixed(2))
            .flatMapToLong(window -> LongStream.rangeClosed(window.get(0), window.get(1)))
            .mapToObj(Long::toString)
            .toList();

    var sum1 = identifiers.stream()
            .filter(id -> id.substring(0, id.length() / 2).equals(id.substring(id.length() / 2)))
            .mapToLong(Long::parseLong)
            .sum();

    IO.println(sum1);

    var sum2 = identifiers.stream()
            .filter(id ->
                    IntStream.rangeClosed(1, id.length() / 2)
                            .mapToObj(windowSize ->
                                    id.chars().mapToObj(Character::toString).gather(Gatherers.windowFixed(windowSize))
                                            .distinct()
                                            .count() == 1)
                            .anyMatch(b -> b)
            )
            .mapToLong(Long::parseLong)
            .sum();

    IO.println(sum2);
}
