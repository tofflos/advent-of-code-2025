void main() throws IOException {

    var lines = Files.readAllLines(Path.of("06.txt"));
    var last = lines.removeLast();
    var operations = getOperations(last);

    var sum1 = operations.stream()
            .mapToLong(column -> lines.stream()
                    .map(line -> line.substring(column.begin, column.end).trim())
                    .mapToLong(Long::parseLong)
                    .reduce(column.identity, column.operator))
            .sum();

    System.out.println(sum1);

    var results = new ArrayList<Long>();

    for (var operation : operations) {
        var numbers = new ArrayList<Long>();

        for (var x = operation.end; x > operation.begin; x--) {
            var builder = new StringBuilder();

            for (String line : lines) {
                builder.append(line.charAt(x - 1));
            }

            numbers.add(Long.parseLong(builder.toString().trim()));
        }

        results.add(numbers.stream()
                .mapToLong(Long::valueOf)
                .reduce(operation.identity, operation.operator));
    }
    
    var sum2 = results.stream().mapToLong(Long::valueOf).sum();

    System.out.println(sum2);
}

List<Operation> getOperations(String last) {
    var operations = new ArrayList<Operation>();
    var current = 0;

    while (current < last.length()) {
        var next = current + 1;

        while (next < last.length() && last.charAt(next) == ' ') {
            next++;
        }

        var isMultipliy = last.charAt(current) == '*';

        operations.add(new Operation(
                isMultipliy ? Math::multiplyExact : Math::addExact,
                isMultipliy ? 1L : 0L,
                current, next - 1));

        current = next;
    }
    
    return operations;
}

record Operation(LongBinaryOperator operator, long identity, int begin, int end) {
}