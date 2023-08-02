public class App {
    public static void main(String[] args) throws Exception {
        double temp = 0;
        String result = "result + " + String.valueOf(temp++);
        System.out.println(result);
        result = "result + " + String.valueOf(temp++);
        System.out.println(result);
        System.out.println("Hello, World!");
    }
}
