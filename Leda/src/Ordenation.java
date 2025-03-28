import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Ordenation {
    // Função para adicionar pessoas mencionadas
    public static void addMentionedPersons(String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int counter = 0;

            while ((line = reader.readLine()) != null && counter < 1000) {
                String[] columns = line.split(",");

                // Verifica se a linha possui pelo menos 6 colunas antes de acessar columns[5]
                if (columns.length < 6) {
                    System.out.println("Linha ignorada (colunas insuficientes): " + line);
                    continue;
                }

                // Adiciona cabeçalho para as novas colunas
                if (counter == 0) {
                    String[] newHeader = Arrays.copyOf(columns, columns.length + 2);
                    newHeader[columns.length] = "mentioned_person";
                    newHeader[columns.length + 1] = "mentioned_person_count";
                    writer.write(String.join(",", newHeader) + "\n");
                } else {
                    String text = columns[5]; // Coluna que contém o texto

                    // Encontrar menções no texto (@fulanodetal)
                    Pattern pattern = Pattern.compile("@(\\w+)");
                    Matcher matcher = pattern.matcher(text);
                    List<String> mentionedPersons = new ArrayList<>();

                    while (matcher.find()) {
                        mentionedPersons.add(matcher.group(1));
                    }

                    // Adicionar as novas colunas
                    String[] newRow = Arrays.copyOf(columns, columns.length + 2);
                    newRow[columns.length] = mentionedPersons.isEmpty() ? "" : String.join("/", mentionedPersons);
                    newRow[columns.length + 1] = String.valueOf(mentionedPersons.size()); // Contagem de menções
                    writer.write(String.join(",", newRow) + "\n");
                }
                counter++;
            }
            System.out.println("Arquivo com pessoas mencionadas gerado com sucesso: " + outputFile);

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
