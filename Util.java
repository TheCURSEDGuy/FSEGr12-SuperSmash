import java.util.Random;

class Util {
    Random r = new Random();
    int randint(int min, int max) {
        return r.nextInt(max - min + 1) + min;
    }
}
