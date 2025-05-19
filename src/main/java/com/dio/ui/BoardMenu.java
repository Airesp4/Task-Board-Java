package com.dio.ui;

import com.dio.persistence.entity.BoardColumnEntity;
import com.dio.persistence.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardMenu {
    
    private final BoardEntity entity;

    public void execute() {
        System.out.println("=== Detalhes do Board Selecionado ===");
        System.out.printf("ID: %d\n", entity.getId());
        System.out.printf("Nome: %s\n", entity.getName());

        var columns = entity.getBoardColumns();
        if (columns == null || columns.isEmpty()) {
            System.out.println("Este board ainda não possui colunas.");
            return;
        }

        System.out.println("\n--- Colunas do Board ---");
        for (BoardColumnEntity column : columns) {
            System.out.printf("ID: %d | Nome: %s | Ordem: %d | Tipo: %s\n",
                column.getId(),
                column.getName(),
                column.getOrder(),
                column.getKind().name()
            );
        }
    }
}
