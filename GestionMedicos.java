import java.util.ArrayList;
import java.util.Scanner;

public class GestionMedicos {
    public static void main(String[] args) {
        // Lista de citas médicas
        ArrayList<String[]> citas = new ArrayList<>();
        citas.add(new String[]{"2025-10-15", "08:00", "Juan Pérez", "Chequeo general"});
        citas.add(new String[]{"2025-10-15", "10:30", "María López", "Dolor de cabeza"});
        citas.add(new String[]{"2025-10-16", "09:00", "Carlos Gómez", "Control postoperatorio"});
        citas.add(new String[]{"2025-10-18", "11:00", "Ana Torres", "Consulta de resultados"});
        citas.add(new String[]{"2025-10-20", "08:30", "Laura Ríos", "Examen de laboratorio"});

        Scanner sc = new Scanner(System.in);

        System.out.println("=== AGENDA MÉDICA ===");
        System.out.println("1. Ver todas las citas");
        System.out.println("2. Filtrar citas por rango de fechas");
        System.out.print("Elige una opción (1 o 2): ");
        String opcion = sc.nextLine();

        if (opcion.equals("1")) {
            System.out.println("\n--- TODAS LAS CITAS ---");
            for (String[] cita : citas) {
                System.out.println("Fecha: " + cita[0] + " | Hora: " + cita[1] + " | Paciente: " + cita[2] + " | Motivo: " + cita[3]);
            }
        } else if (opcion.equals("2")) {
            System.out.print("Ingresa la fecha de inicio (YYYY-MM-DD): ");
            String inicio = sc.nextLine();
            System.out.print("Ingresa la fecha final (YYYY-MM-DD): ");
            String fin = sc.nextLine();

            System.out.println("\n--- CITAS DESDE " + inicio + " HASTA " + fin + " ---");
            for (String[] cita : citas) {
                if (cita[0].compareTo(inicio) >= 0 && cita[0].compareTo(fin) <= 0) {
                    System.out.println("Fecha: " + cita[0] + " | Hora: " + cita[1] + " | Paciente: " + cita[2] + " | Motivo: " + cita[3]);
                }
            }
        } else {
            System.out.println("Opción no válida.");
        }

        sc.close();
    }
}
