void main() throws IOException {

    var rolls = Files.lines(Path.of("04.txt")).toList();

    var initial = rolls.stream().flatMapToInt(String::chars).filter(c -> c == '@').count();

    var accessible = getAccessible(rolls);

    IO.println(accessible.size());

    while (accessible.size() > 0) {
        rolls = remove(rolls, accessible);
        accessible = getAccessible(rolls);
    }

    var remaining = rolls.stream().flatMapToInt(String::chars).filter(c -> c == '@').count();
    
    IO.println(initial - remaining);
}

List<int[]> getAccessible(List<String> rolls) {
    var directions = new int[][]{{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
    var result = new ArrayList<int[]>();

    for (int y = 0; y < rolls.size(); y++) {
        for (int x = 0; x < rolls.get(y).length(); x++) {

            if (rolls.get(y).charAt(x) != '@') {
                continue;
            }

            var count = 0;

            for (var direction : directions) {
                var px = x + direction[0];
                var py = y + direction[1];

                if (0 <= py && py < rolls.size() && 0 <= px && px < rolls.get(py).length()) {
                    if (rolls.get(py).charAt(px) == '@') {
                        count++;
                    }
                }
            }

            if (count < 4) {
                result.add(new int[]{x, y});
            }
        }
    }

    return result;
}

List<String> remove(List<String> rolls, List<int[]> removals) {
    var result = new ArrayList<>(rolls);

    for (var removal : removals) {
        var y = removal[1];
        var x = removal[0];

        var t = result.get(y);
        var arr = t.getBytes();
        arr[x] = '.';
        result.set(y, new String(arr));
    }

    return result;
}