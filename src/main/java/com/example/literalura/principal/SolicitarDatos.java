package com.example.literalura.principal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SolicitarDatos {

    private Scanner lr = new Scanner(System.in);

    public void listarTodos(){

        Connection connection = null;
        String sqlInyeccion = "SELECT b.gutendex_id, b.title, b.translators, b.languages, a.name "+
                " FROM books b JOIN book_authors ba ON b.id = ba.book_id "+
                " JOIN authors a ON ba.author_id = a.id";
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlInyeccion);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int gutendexId = rs.getInt("gutendex_id");
                String title = rs.getString("title");
                String translators = rs.getString("translators");
                String languages = rs.getString("languages");
                String names = rs.getString("name");

                System.out.println("Gutendex ID: "+gutendexId);
                System.out.println("Titulo: "+title);
                System.out.println("Autor: "+names);
                System.out.println("Traductores (Si aplica): "+translators);
                System.out.println("Idioma: "+languages);
                System.out.println("***/////////////////////////////////////////////***");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
    }

    public void listarAutor(){

        System.out.println("Ingrese el nombre del autor de los libros a citar:");
        String autorName = lr.nextLine();
        Connection connection = null;
        String sqlInyeccion = "SELECT b.gutendex_id, b.title, b.translators, b.languages, a.name "+
                " FROM authors a JOIN book_authors ba ON a.id = ba.author_id "+
                " JOIN books b ON ba.book_id = b.id "+
                " WHERE a.name LIKE ?";
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlInyeccion);
            ps.setString(1, "%"+autorName+"%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int gutendexId = rs.getInt("gutendex_id");
                String title = rs.getString("title");
                String translators = rs.getString("translators");
                String languages = rs.getString("languages");
                String names = rs.getString("name");

                System.out.println("Gutendex ID: "+gutendexId);
                System.out.println("Titulo: "+title);
                System.out.println("Autor: "+names);
                System.out.println("Traductores (Si aplica): "+translators);
                System.out.println("Idioma: "+languages);
                System.out.println("***/////////////////////////////////////////////***");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
    }

    public void listarIdioma(){

        Connection connection = null;
        System.out.println("""
                Que lenguaje quiere listar (Escribalo)?
                
                en-Inglés
                es-Español
                fr-Francés
                de-Alemán
                it-Italiano
                pt-Portugués
                nl-Neerlandés
                sv-Sueco
                ru-Ruso
                zh-Chino
                ja-Japonés
                ar-Árabe
                ko-Coreano
                el-Griego
                hu-Húngaro
                pl-Polaco
                tr-Turco
                cs-Checo
                da-Danés
                fi-Finés
                
                """);
        String language = lr.nextLine();
        String sqlInyeccion = "SELECT b.gutendex_id, b.title, b.translators, b.languages, a.name "+
                " FROM books b JOIN book_authors ba ON b.id = ba.book_id "+
                " JOIN authors a ON ba.author_id = a.id WHERE b.languages LIKE ?";
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlInyeccion);
            ps.setString(1, "%"+language+"%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int gutendexId = rs.getInt("gutendex_id");
                String title = rs.getString("title");
                String translators = rs.getString("translators");
                String languages = rs.getString("languages");
                String names = rs.getString("name");

                System.out.println("Gutendex ID: "+gutendexId);
                System.out.println("Titulo: "+title);
                System.out.println("Autor: "+names);
                System.out.println("Traductores (Si aplica): "+translators);
                System.out.println("Idioma: "+languages);
                System.out.println("***/////////////////////////////////////////////***");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConectarBD.closeConnection(connection);
        }
    }

    public void listarFecha(){

        Connection connection = null;
        System.out.println("Ingrese la fecha en la que se buscara al autor: ");
        int fecha = lr.nextInt();
        String sqlInyeccion = "SELECT b.gutendex_id, b.title, b.translators, b.languages, a.name "+
                " FROM authors a JOIN book_authors ba ON a.id = ba.author_id "+
                " JOIN books b ON ba.book_id = b.id "+
                " WHERE a.birth_year <= ? AND a.death_year >= ?";
        try{
            connection = ConectarBD.getConnection();
            PreparedStatement ps = connection.prepareStatement(sqlInyeccion);
            ps.setInt(1, fecha);
            ps.setInt(2, fecha);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int gutendexId = rs.getInt("gutendex_id");
                String title = rs.getString("title");
                String translators = rs.getString("translators");
                String languages = rs.getString("languages");
                String names = rs.getString("name");

                System.out.println("Gutendex ID: "+gutendexId);
                System.out.println("Titulo: "+title);
                System.out.println("Autor: "+names);
                System.out.println("Traductores (Si aplica): "+translators);
                System.out.println("Idioma: "+languages);
                System.out.println("***/////////////////////////////////////////////***");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
