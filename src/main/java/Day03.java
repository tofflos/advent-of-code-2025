void main() throws IOException {

    var banks = Files.lines(Path.of("03.txt"))
            .map(line -> line.split(""))
            .map(numbers -> Arrays.stream(numbers).map(Integer::parseInt).toList())
            .toList();

    var jolt1 = banks.stream()
            .mapToInt(batteries -> {
                var max = Integer.MIN_VALUE;

                for (int i = 0; i < batteries.size() - 1; i++) {
                    for (int j = i + 1; j < batteries.size(); j++) {
                        var number = Integer.parseInt(batteries.get(i) + "" + batteries.get(j));

                        if (number > max) {
                            max = number;
                        }
                    }
                }

                return max;
            })
            .sum();

    IO.println(jolt1);

    var jolt2 = banks.stream()
            .mapToLong(batteries -> {

                var t = new ArrayList<Integer>(batteries);
                var r = new ArrayList<Integer>();

                while (!t.isEmpty()) {
                    r.addFirst(t.removeLast());

                    if (r.size() > 12) {
                        var index = -1;

                        for (int i = 0; i < r.size() - 1; i++) {
                            if (r.get(i) < r.get(i + 1)) {
                                index = i;
                                break;
                            }
                        }

                        if (index != -1) {
                            r.remove(index);
                        }
                    }
                }

                var jolt = r.stream().limit(12).map(Object::toString).collect(Collectors.joining());

                return Long.parseLong(jolt);

            })
            .sum();

    IO.println(jolt2);
}
