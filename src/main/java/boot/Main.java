package boot;

public class Main {
    public static void main(String[] args) {
        if (!Bootstrap.load()) {
            System.out.println("FAILED TO BOOT-UP");
            System.exit(-1);
        }
    }
}
