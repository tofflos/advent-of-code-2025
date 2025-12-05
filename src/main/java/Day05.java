void main() throws IOException {

    var lines = Files.readAllLines(Path.of("05.txt"));

    var ranges = lines.stream()
            .takeWhile(s -> !s.isEmpty())
            .map(Range::of)
            .toList();

    var ingredients = lines.stream()
            .dropWhile(s -> s.contains("-") || s.isEmpty())
            .map(Long::parseLong)
            .toList();

    var count1 = ingredients.stream()
            .filter(ingredient -> ranges.stream().anyMatch(range -> range.low() <= ingredient && ingredient <= range.high()))
            .count();

    IO.println(count1);

    var current = ranges;

    while (true) {
        var next = new ArrayList<Range>();

        outer:
        for (var r1 : current) {
            for (var r2 : current) {
                if (r1.equals(r2)) {
                    continue;
                }

                if (Range.overlaps(r1, r2)) {
                    var m = Range.merge(r1, r2);

                    next.addAll(current);
                    next.remove(r1);
                    next.remove(r2);
                    next.add(m);
                    break outer;
                }
            }
        }

        if (next.isEmpty()) {
            break;
        }

        current = next;
    }

    var count2 = current.stream().distinct().mapToLong(r -> r.high - r.low + 1).sum();

    IO.println(count2);
}

record Range(long low, long high) {

    static Range of(String s) {
        var t = s.split("-");

        return new Range(Long.parseLong(t[0]), Long.parseLong(t[1]));
    }

    static boolean overlaps(Range a, Range b) {
        return a.low <= b.high && b.low <= a.high;
    }

    static Range merge(Range a, Range b) {
        return new Range(Math.min(a.low, b.low), Math.max(a.high, b.high));
    }
}