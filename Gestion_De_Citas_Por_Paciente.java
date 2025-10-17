import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Gestion_De_Citas_Por_Paciente {
    public static void main(String[] args) {
       
        GestorCitas gestor = new GestorCitas();

        gestor.registrarPaciente("1001", "Mariana Santos");
        gestor.registrarPaciente("1002", "Juan Arias");

     
        gestor.agendar("1001", LocalDateTime.of(2025, 10, 20, 9, 0), "Control general");
        gestor.agendar("1001", LocalDateTime.of(2025, 10, 20, 10, 0), "Análisis de laboratorio");
        gestor.agendar("1002", LocalDateTime.of(2025, 10, 20, 9, 0), "Odontología");

        
        boolean ok = gestor.agendar("1001", LocalDateTime.of(2025, 10, 20, 9, 0), "Consulta repetida");
        System.out.println("¿Se pudo agendar cita repetida? " + ok);

       
        System.out.println("\nCitas de 1001:");
        gestor.listarCitas("1001").forEach(System.out::println);

       
        gestor.cancelar("1001", LocalDateTime.of(2025, 10, 20, 10, 0));
        System.out.println("\nCitas de 1001 tras cancelar de las 10:00:");
        gestor.listarCitas("1001").forEach(System.out::println);
    }
}


class Cita {
    private final LocalDateTime fechaHora;
    private final String motivo;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Cita(LocalDateTime fechaHora, String motivo) {
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }

    @Override
    public String toString() {
        return "- " + FMT.format(fechaHora) + " | " + motivo;
    }
}


class GestorCitas {
    
    private final Map<String, String> pacientes = new HashMap<>();
    
    private final Map<String, List<Cita>> citasPorPaciente = new HashMap<>();

    
    public void registrarPaciente(String idPaciente, String nombre) {
        pacientes.putIfAbsent(idPaciente, nombre);
        citasPorPaciente.putIfAbsent(idPaciente, new ArrayList<>());
    }

    public boolean agendar(String idPaciente, LocalDateTime fechaHora, String motivo) {
        if (!pacientes.containsKey(idPaciente)) registrarPaciente(idPaciente, "Paciente " + idPaciente);

        List<Cita> citas = citasPorPaciente.get(idPaciente);
       
        boolean existeChoque = citas.stream().anyMatch(c -> c.getFechaHora().equals(fechaHora));
        if (existeChoque) return false;

        citas.add(new Cita(fechaHora, motivo));
        
        citas.sort(Comparator.comparing(Cita::getFechaHora));
        return true;
    }


    public boolean cancelar(String idPaciente, LocalDateTime fechaHora) {
        List<Cita> citas = citasPorPaciente.getOrDefault(idPaciente, Collections.emptyList());
        return citas.removeIf(c -> c.getFechaHora().equals(fechaHora));
    }

   
    public List<Cita> listarCitas(String idPaciente) {
        return List.copyOf(citasPorPaciente.getOrDefault(idPaciente, Collections.emptyList()));
    }
}
