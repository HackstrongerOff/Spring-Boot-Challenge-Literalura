package com.challenge2.literalura.principal;

import com.challenge2.literalura.model.*;
import com.challenge2.literalura.model.records.DatosLibro;
import com.challenge2.literalura.repository.iAutorRepository;
import com.challenge2.literalura.repository.iLibroRepository;
import com.challenge2.literalura.service.ConsumoAPI;
import com.challenge2.literalura.service.ConvierteDatos;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    private iLibroRepository libroRepository;
    private iAutorRepository autorRepository;
    public Principal(iLibroRepository libroRepository, iAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    :: ------------------------------ ::
                    :: 1 :: Añadir libro por Titulo
                    :: ------------------------------ ::
                    :: 2 :: Búsqueda de libro por título
                    :: 3 :: Búsqueda de autor por nombre
                    :: ------------------------------ ::
                    :: 4 :: Listar libros guardados
                    :: 5 :: Lista de autores
                    :: 6 :: Listar autores vivos por año
                    :: 7 :: Listar libros por idioma
                    :: ------------------------------ ::
                    :: 0 :: Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1: //Busca libro en la API
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibroTitulo();
                    break;
                case 3:
                    buscarAutorNombre();
                    break;
                case 4:
                    listarLibros();
                    break;
                case 5:
                    listarAutores();
                    break;
                case 6:
                    listarAutoresAnio();
                    break;
                case 7:
                    listarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida. Ingrese opcion valida.");
            }
        }
    }

    private Libro getDatosLibro() {
        System.out.println("Escribe el Titulo del libro que desea buscar");
        var nombreLibro = teclado.nextLine().toLowerCase();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        ResultadoBusqueda datos = conversor.obtenerDatos(json, ResultadoBusqueda.class);

            if (datos != null && datos.getResultadoLibros() != null && !datos.getResultadoLibros().isEmpty()) {
                DatosLibro primerLibro = datos.getResultadoLibros().get(0); // Obtener el primer libro de la lista
                return new Libro(primerLibro);
            } else {
                System.out.println("No se encontraron resultados.");
                return null;
            }
    }

    @Transactional(readOnly = true)
    private void listarLibros(){
        //datosLibro.forEach(System.out::println);
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos.");
        } else {
            System.out.println("Libros encontrados en la base de datos:");
            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        }
    }

    private void buscarLibroWeb() {
        Libro libro = getDatosLibro();

        if (libro == null){
            System.out.println("Libro no encontrado. el valor es null");
            return;
        }
        try{
            boolean libroExists = libroRepository.existsByTitulo(libro.getTitulo());
            if (libroExists){
                System.out.println("El libro ya existe en la base de datos!");
            }else {
                libroRepository.save(libro);
                System.out.println(libro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("No se puede persistir el libro buscado!");
        }
    }

    private void buscarLibroTitulo() {
        System.out.println("Ingrese Titulo libro que quiere buscar: ");
        var titulo = teclado.nextLine();
        Libro libroBuscado = libroRepository.findByTituloContainsIgnoreCase(titulo);
        if (libroBuscado != null) {
            System.out.println("El libro buscado fue: " + libroBuscado);
        } else {
            System.out.println("El libro con el titulo '" + titulo + "' no se encontró.");
        }
    }

    private void buscarLibroPorNombre() {
        System.out.println("Ingrese Titulo del libro a buscar: ");
        var titulo = teclado.nextLine();
        Libro libroBuscado = libroRepository.findByTituloContainsIgnoreCase(titulo);
        if (libroBuscado != null) {
            System.out.println("El libro buscado fue: " + libroBuscado);
        } else {
            System.out.println("El libro con el titulo '" + titulo + "' no se encontró.");
        }
    }

    private void buscarAutorNombre() {
        System.out.println("Ingrese nombre del escritor que quiere buscar: ");
        var escritor = teclado.nextLine();
        Optional<Autor> escritorBuscado = autorRepository.findFirstByNombreContainsIgnoreCase(escritor);
        if (escritorBuscado.isPresent()) {
            System.out.println("\nEl escritor buscado fue: " + escritorBuscado.get().getNombre());
        } else {
            System.out.println("\nEl escritor con el titulo '" + escritor + "' no se encontró.");
        }
    }

    private  void listarAutores(){
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos. \n");
        } else {
            System.out.println("Autores encontrados: \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {
                // add() retorna true si el nombre no estaba presente y se añade correctamente
                if (autoresUnicos.add(autor.getNombre())){
                    System.out.println(autor.getNombre()+'\n');
                }
            }
        }
    }

    private void listarLibrosIdioma(){
        System.out.println("""
        Ingrese Idioma en el que quiere buscar:
        ***********************************
        es : Libros en español.
        en : Libros en ingles.
        ***********************************""");

        var idioma = teclado.nextLine();
        List<Libro> librosPorIdioma = libroRepository.findByIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en la base de datos.");
        } else {
            System.out.println("Libros segun idioma encontrados en la base de datos:");
            for (Libro libro : librosPorIdioma) {
                System.out.println(libro.toString());
            }
        }
    }

    private void listarAutoresAnio() {
        System.out.println("Ingrese año para consultar autores vivos: \n");
        var anioBuscado = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresVivos = autorRepository.findByCumpleaniosLessThanOrFechaFallecimientoGreaterThanEqual(anioBuscado, anioBuscado);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores que estuvieran vivos en el año " + anioBuscado + ".");
        } else {
            System.out.println("Autores que estaban vivos en el año " + anioBuscado + " son:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Autor autor : autoresVivos) {
                if (autor.getCumpleanios() != null && autor.getFechaFallecimiento() != null) {
                    if (autor.getCumpleanios() <= anioBuscado && autor.getFechaFallecimiento() >= anioBuscado) {
                        if (autoresUnicos.add(autor.getNombre())) {
                            System.out.println("Autor: " + autor.getNombre());
                        }
                    }
                }
            }
        }
    }

// Fin de metodos
}

