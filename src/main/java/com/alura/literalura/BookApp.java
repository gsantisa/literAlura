package com.alura.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BookApp implements CommandLineRunner {
    private final BookService bookService;

    @Autowired
    public BookApp(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookApp.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Mostrar autores");
            System.out.println("4. Autores vivos en año específico");
            System.out.println("5. Cantidad de libros por idioma");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            if (opcion == 6) break;

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    scanner.nextLine(); // Consumir nueva línea
                    String title = scanner.nextLine();
                    Book book = bookService.getBookByTitle(title);
                    System.out.println(book != null ? book : "Libro no encontrado.");
                    break;

                case 2:
                    System.out.println("Lista de libros:");
                    bookService.getAllBooks().forEach(System.out::println);
                    break;

                case 3:
                    System.out.println("Lista de autores:");
                    bookService.getAuthors().forEach(System.out::println);
                    break;

                case 4:
                    System.out.print("Ingrese el año: ");
                    int year = scanner.nextInt();
                    List<String> authorsAlive = bookService.getAuthorsAliveInYear(year);
                    System.out.println("Autores vivos en " + year + ": " + authorsAlive);
                    break;

                case 5:
                    System.out.print("Ingrese el idioma (por ejemplo, 'en' para inglés): ");
                    scanner.nextLine(); // Consumir nueva línea
                    String language = scanner.nextLine();
                    long count = bookService.countBooksByLanguage(language);
                    System.out.println("Cantidad de libros en " + language + ": " + count);
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }
}

