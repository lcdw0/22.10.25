package run;

import entities.Autor;
import services.dao.MyDao;
import services.interfaces.ICRUD;
import java.util.List;

public class Main {

    public static final ICRUD dao = new MyDao();

    public static void insertarAutor() {
        Autor a = new Autor();
        a.setNombre("Gabriel Garcia Marquez");
        a.setNacionalidad("Mexicana");
        dao.insert(a);

        Autor r = new Autor();
        r.setNombre("Ruben Dario");
        r.setNacionalidad("Nicaraguense");
        dao.insert(r);
    }

    public static void listarAutores() {
        System.out.println("=== Registros almacenados ===");
        List<Autor> autores = dao.getAll("autores.All", Autor.class);
        autores.forEach(autor ->
                System.out.println(autor.getId() + " - " + autor.getNombre() + " (" + autor.getNacionalidad() + ")")
        );
    }

    public static void editarAutor() {
        Autor a = dao.findById(1, Autor.class);
        if (a != null) {
            a.setNacionalidad("Colombiana");
            dao.update(a);
        }
    }

    public static void eliminarAutor() {
        Autor a = dao.findById(2, Autor.class);
        if (a != null) {
            dao.delete(a);
        }
    }

    public static void main(String[] args) {
        insertarAutor();
        listarAutores();
        editarAutor();
        listarAutores();
        eliminarAutor();
        listarAutores();
    }
}
