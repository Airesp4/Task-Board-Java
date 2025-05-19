package com.dio.ui;

import static com.dio.persistence.config.ConnectionConfig.getConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dio.persistence.entity.BoardColumnEntity;
import com.dio.persistence.entity.BoardColumnKindEnum;

import static com.dio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static com.dio.persistence.entity.BoardColumnKindEnum.FINAL;
import static com.dio.persistence.entity.BoardColumnKindEnum.INITIAL;
import static com.dio.persistence.entity.BoardColumnKindEnum.PENDING;

import com.dio.persistence.entity.BoardEntity;
import com.dio.service.BoardService;

public class MainMenu {
    
    private final Scanner teclado = new Scanner(System.in);

    public void execute() throws SQLException{
        System.out.println("Bem vindo ao gerenciador de boards, escolha a opção desejada: ");
        var option = -1;

        while (true) {
            System.out.println("1 - Cria um novo board");
            System.out.println("2 - Selecionar um board existente");
            System.out.println("3 - excluir um board");
            System.out.println("4 - Sair");
            option = teclado.nextInt();
            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu");
            }
        }
    }

    private void createBoard() throws SQLException{
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu board");
        entity.setName(teclado.next());

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim informe quantas, senão digite '0'");
        var additionalColumns = teclado.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        var initialColumnName = teclado.next();
        var initialColumn = createColumn(initialColumnName, INITIAL, 0);
        columns.add(initialColumn);

        for (int i = 0; i < additionalColumns; i++){
            System.out.println("Informe o nome da coluna de tarefa pendente do board");
            var pendingColumnName = teclado.next();
            var pendingColumn = createColumn(pendingColumnName, PENDING, i+1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final do board");
        var finalColumnName = teclado.next();
        var finalColumn = createColumn(finalColumnName, FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamentodo board");
        var cancelColumnName = teclado.next();
        var cancelColumn = createColumn(cancelColumnName, CANCEL, additionalColumns + 1);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        try(var connection = getConnection()){
            var service = new BoardService(connection);
            service.insert(entity);
        }
    }

    private void selectBoard() throws SQLException{
        System.out.println("Informe o id do board que deseja selecionar");
        var id = teclado.nextLong();

        try(var connection = getConnection()){
            var service = new BoardService(connection);
            var optional = service.findById(id);

            optional.ifPresentOrElse(
                b -> {
                    var menu = new BoardMenu(b);
                    menu.execute();
                },
                () -> System.out.printf("Não foi encontrado um board com id %s\n", id)
            );
        }
    }

    private void deleteBoard() throws SQLException{
        System.out.println("Informe o id do board que será excluído");
        var id = teclado.nextLong();

        try(var connection = getConnection()){
            var service = new BoardService(connection);
            if (service.delete(id)) {
                System.out.printf("O board %s foi excluído\n", id);
            } else {
                System.out.printf("Não foi encontrado um board com id %s\n", id);
            }
        }
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
