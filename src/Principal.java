import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConversorDeMonedas conversor = new ConversorDeMonedas();

        while (true) {
            System.out.println("Bienvenido al Conversor de Monedas");
            System.out.println("1. USD a ARS");
            System.out.println("2. BRL a USD");
            System.out.println("3. EUR a USD");
            System.out.println("4. GBP a USD");
            System.out.println("5. JPY a USD");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            if (opcion.equals("6")) {
                break;
            }

            System.out.print("Ingrese el valor que desea convertir: ");
            double cantidad = Double.parseDouble(scanner.nextLine());

            double resultado = 0;
            switch (opcion) {
                case "1":
                    resultado = conversor.convertir("USD", "ARS", cantidad);
                    System.out.printf("El valor de %.2f USD es %.2f ARS%n", cantidad, resultado);
                    break;
                case "2":
                    resultado = conversor.convertir("BRL", "USD", cantidad);
                    System.out.printf("El valor de %.2f BRL es %.2f USD%n", cantidad, resultado);
                    break;
                case "3":
                    resultado = conversor.convertir("EUR", "USD", cantidad);
                    System.out.printf("El valor de %.2f EUR es %.2f USD%n", cantidad, resultado);
                    break;
                case "4":
                    resultado = conversor.convertir("GBP", "USD", cantidad);
                    System.out.printf("El valor de %.2f GBP es %.2f USD%n", cantidad, resultado);
                    break;
                case "5":
                    resultado = conversor.convertir("JPY", "USD", cantidad);
                    System.out.printf("El valor de %.2f JPY es %.2f USD%n", cantidad, resultado);
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
                    break;
            }
        }

        scanner.close();
        System.out.println("Gracias por usar el Conversor de Monedas.");
    }
}
