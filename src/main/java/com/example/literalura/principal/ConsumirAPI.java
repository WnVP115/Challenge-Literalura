package com.example.literalura.principal;

import com.example.literalura.data.Authors;
import com.example.literalura.data.Books;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConsumirAPI {

    private Scanner lr = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books?search=";

    public void buscarTitulo(){

        System.out.println("Ingrese el titulo del libro a buscar:");
        String titulo = lr.nextLine();
        titulo = titulo.replace(" ", "+");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(URL_BASE+titulo).build();

        try(Response response = client.newCall(request).execute()){

            if(response.isSuccessful()){
                String jsonResponse = response.body().string();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                JsonNode bookNode = rootNode.path("results").path(0);

                Books book = new Books();
                Authors author = new Authors();

                book.setGutendexId(bookNode.path("id").asInt());
                book.setTitle(bookNode.path("title").asText());
                book.setTranslators(translatorNode(bookNode.path("translators")));
                book.setSubjects(processListString(bookNode.path("subjects")));
                book.setBookshelves(processListString(bookNode.path("bookshelves")));
                book.setLanguages(processListString(bookNode.path("languages")));
                book.setCopyright(bookNode.path("copyright").asBoolean());

                JsonNode authorNode = bookNode.path("authors").path(0);
                author.setName(authorNode.path("name").asText());
                author.setBirthYear(authorNode.path("birth_year").asInt());
                author.setDeathYear(authorNode.path("death_year").asInt());

                if(existeLibro(book))
                    System.out.println("Libro guardado anteriormente!");
                else
                    agregarLibro(book);

                if(existeAutor(author)){}else
                    agregarAutor(author);
                if(existeRelacion(book, author)){}else
                    agregarRelacion(book, author);
            }else{
                System.out.println("Error en la solicitud!");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean existeLibro(Books book){

        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM books WHERE gutendex_id = ?";
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, book.getGutendexId());
            ResultSet rs = ps.executeQuery();

            if(rs.next() && rs.getInt(1) > 0)
                return true;

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
        return false;
    }

    public boolean existeAutor(Authors author){

        Connection connection = null;
        String sql = "SELECT COUNT(*) FROM authors WHERE name = ?";
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, author.getName());
            ResultSet rs = ps.executeQuery();

            if(rs.next() && rs.getInt(1) > 0)
                return true;

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
        return false;
    }

    public boolean existeRelacion(Books book, Authors author){

        Connection connection = null;
        String sqlInyeccion = "SELECT COUNT(*) FROM book_authors WHERE book_id = ? AND author_id = ?";
        Integer idAuthor = 0;
        Integer idBook = 0;
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM authors WHERE name = ?");

            ps.setString(1, author.getName());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                idAuthor = rs.getInt("id");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM books WHERE gutendex_id = ?");

            ps.setInt(1, book.getGutendexId());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                idBook = rs.getInt("id");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlInyeccion);

            ps.setInt(1, idBook);
            ps.setInt(2, idAuthor);

            ResultSet rs = ps.executeQuery();

            if(rs.next() && rs.getInt(1) > 0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
        return false;
    }

    public void agregarLibro(Books book){

        Connection connection = null;
        String sqlBOOKS = "INSERT INTO books (gutendex_id, title, translators, subjects, bookshelves, languages, copyright) "+
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlBOOKS);

            ps.setInt(1, book.getGutendexId());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getTranslators());
            ps.setString(4, book.getSubjects());
            ps.setString(5, book.getBookshelves());
            ps.setString(6, book.getLanguages());
            ps.setBoolean(7, book.isCopyright());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
    }

    public void agregarAutor(Authors author){

        Connection connection = null;
        String sqlAUTHOR = "INSERT INTO authors (name, birth_year, death_year) "+
                "VALUES (?, ?, ?)";

        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlAUTHOR);

            ps.setString(1, author.getName());
            ps.setInt(2, author.getBirthYear());
            ps.setInt(3, author.getDeathYear());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
    }

    public void agregarRelacion(Books book, Authors author){

        Connection connection = null;
        String sqlInyeccion = "INSERT INTO book_authors (book_id, author_id) "+
                "VALUES (?, ?)";
        Integer idAuthor = 0;
        Integer idBook = 0;
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM authors WHERE name = ?");

            ps.setString(1, author.getName());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                idAuthor = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM books WHERE gutendex_id = ?");

            ps.setInt(1, book.getGutendexId());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                idBook = rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlInyeccion);

            ps.setInt(1, idBook);
            ps.setInt(2, idAuthor);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
    }

    public static String translatorNode(JsonNode translatorArray){
        List<String> datos = new ArrayList<>();
        if(translatorArray.isArray()){
            for(JsonNode translator : translatorArray){
                String dato = translator.path("name").asText();
                datos.add(dato);
            }
        }
        return String.join(",", datos);
    }

    public static String processListString(JsonNode listNode){
        List<String> elementos = new ArrayList<>();
        if(listNode.isArray()){
            for(JsonNode element : listNode){
                elementos.add(element.asText());
            }
        }
        return String.join(",", elementos);
    }
}
