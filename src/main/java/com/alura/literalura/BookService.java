package com.alura.literalura;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final String API_URL = "https://gutendex.com/books/";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // Buscar libro por título
    public Book getBookByTitle(String title) {
        try {
            String requestUrl = API_URL + "?search=" + URLEncoder.encode(title, "UTF-8");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode rootNode = mapper.readTree(response.body()).path("results").get(0); // Primer resultado
            return mapper.treeToValue(rootNode, Book.class);
        } catch (Exception e) {
            System.out.println("Error al obtener el libro: " + e.getMessage());
        }
        return null;
    }

    // Listar todos los libros (limitado a los primeros 10 resultados por ejemplo)
    public List<Book> getAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode results = mapper.readTree(response.body()).path("results");
            return mapper.readerForListOf(Book.class).readValue(results);
        } catch (Exception e) {
            System.out.println("Error al obtener los libros: " + e.getMessage());
        }
        return List.of();
    }

    // Listar autores
    public List<String> getAuthors() {
        return getAllBooks().stream()
                .map(Book::getAuthor)
                .distinct()
                .collect(Collectors.toList());
    }

    // Autores vivos en un año específico (simulado)
    public List<String> getAuthorsAliveInYear(int year) {
        // Esta funcionalidad sería más compleja con datos reales sobre fechas de nacimiento/muerte.
        // Aquí, simplemente devuelve autores de libros recientes como ejemplo.
        return getAuthors().stream()
                .filter(author -> author.length() > 5) // Filtro de ejemplo (debe reemplazarse con lógica real)
                .collect(Collectors.toList());
    }

    // Cantidad de libros por idioma
    public long countBooksByLanguage(String language) {
        return getAllBooks().stream()
                .filter(book -> List.of(book.getLanguages()).contains(language))
                .count();
    }
}
