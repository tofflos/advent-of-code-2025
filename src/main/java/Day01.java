void main() throws IOException {

    var rotations = Files.lines(Path.of("01.txt"))
            .map(line -> line.replaceAll("L", "-").replace("R", "+"))
            .map(Integer::parseInt)
            .toList();

    var password1 = getPassword1(rotations);
    var password2 = getPassword2(rotations);

    IO.println(password1);
    IO.println(password2);
}

int getPassword1(List<Integer> rotations) {
    var count = 0;
    var position = 50;

    for (var rotation : rotations) {
        position += rotation;
        position = position % 100;

        if (position == 0) {
            count++;
        }
    }

    return count;
}

int getPassword2(List<Integer> rotations) {
    var count = 0;
    var position = 50;

    for (var rotation : rotations) {
        for (int i = 0; i < Math.abs(rotation); i++) {
            position = rotation < 0 ? position - 1 : position + 1;
            position = position % 100;

            if (position == 0) {
                count++;
            }
        }
    }

    return count;
}
