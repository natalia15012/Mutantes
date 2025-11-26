package org.example.service;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component // Para que Spring lo inyecte
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final Set<Character> VALID_BASES = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        if(dna == null || dna.length == 0){
            return false;
        }
        int n = dna.length;

        if (n < SEQUENCE_LENGTH){
            return false;
        }

        char[][] matrix = new char[n][n];

        for (int i=0; i<n; i++){
            if (dna[i] == null || dna[i].length()!= n){
                return false;
            }
            char[] rowChars = dna[i].toCharArray();
            for (char c : rowChars){
                if (!VALID_BASES.contains(c)){
                    return false;
                }
            }
            matrix[i] = rowChars;
        }

        int sequenceCount = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // ¡Optimización clave!
                    }
                }

                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (checkAntiDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }

        return false; // Si llegamos acá, es Humano
    }
    // --- MÉTODOS HELPER (Comparaciones directas para rendimiento) ---
    private boolean checkVertical(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        // Compara la base con las 3 siguientes a su DERECHA
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }
    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    private boolean checkAntiDiagonal(char[][] matrix, int row, int col) {
        char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }

}