package com.example.literalura.principal;

import java.util.Scanner;

public class Principal {

private Scanner lr = new Scanner(System.in);

    public void iniciarMenu(){
        int opcion = 0;

        do{
            System.out.println("""
                    1- Buscar libro por titulo
                    2- Listar todos los libros
                    3- Listar libros por autor
                    4- Listar libros por idioma
                    5- Listar autores vivos en determinado año
                    
                    0- Salir                    
                    """);
            opcion = lr.nextInt();
            lr.nextLine();
            SolicitarDatos solicitarDatos = new SolicitarDatos();

            switch (opcion){
                case 0:
                    System.out.println("Nos vemos! =D");
                    System.exit(0);
                    break;
                case 1:
                    ConsumirAPI consumirAPI = new ConsumirAPI();
                    consumirAPI.buscarTitulo();
                    break;
                case 2:
                    solicitarDatos.listarTodos();
                    break;
                case 3:
                    solicitarDatos.listarAutor();
                    break;
                case 4:
                    solicitarDatos.listarIdioma();
                    break;
                case 5:
                    solicitarDatos.listarFecha();
                    break;
            }
        }while (opcion != 0);
    }
}

