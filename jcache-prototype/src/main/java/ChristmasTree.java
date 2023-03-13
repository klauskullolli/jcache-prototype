import java.util.logging.Logger;

public class ChristmasTree {

    private static final Logger logger = Logger.getLogger(ChristmasTree.class.getName());

    public static void christmasTree(int height) {

        String pattern = "\n";
        for (int i = 0; i < height; i++) {


            for (int j = 0; j < height - i - 1; j++) {
                pattern += " ";
            }
            for (int j = 0; j < 2 * i + 1; j++) {
                pattern += "*";
            }
            pattern += "\n";

        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < height - 2; j++) {
                pattern += " ";
            }
            pattern += "***\n";

        }

        logger.info(pattern);

    }

    public static void main(String[] args) {

        christmasTree(10);
    }

}
