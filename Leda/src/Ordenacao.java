import java.io.*; //fornece classes usadas para ler arquivos
import java.text.SimpleDateFormat; //trabalha com o formato de datas
import java.util.ArrayList;
import java.util.Date; //armazena e manipula datas
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// função para adicionar pessoas mencionadas
public static void addMentionedPersons(String inputFile, String outputFile) {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

        String line;
        int counter = 0;

       //contador pra não estourar a memória, apenas para teste
        while ((line = reader.readLine()) != null && counter < 1000) {
            String[] columns = line.split(",");

            // adiciona cabeçalho para as novas colunas da tabela
            if (counter == 0) {
                String[] newHeader = new String[columns.length + 2];
                System.arraycopy(columns, 0, newHeader, 0, columns.length);
                newHeader[columns.length] = "mentioned_person";
                newHeader[columns.length + 1] = "mentioned_person_count";
                writer.write(String.join(",", newHeader) + "\n");
            } else {
                // processa cada linha para adicionar as menções
                String text = columns[5]; 

                // encontrar menções no texto (@fulanodetal)
                Pattern pattern = Pattern.compile("@(\\w+)");
                Matcher matcher = pattern.matcher(text);
                List<String> mentionedPersons = new ArrayList<>();
                while (matcher.find()) {
                    mentionedPersons.add(matcher.group(1));
                }

                // Adicionar as novas colunas
                String[] newRow = new String[columns.length + 2];
                System.arraycopy(columns, 0, newRow, 0, columns.length);
                newRow[columns.length] = mentionedPersons.isEmpty() ? "null" : String.join("/", mentionedPersons);
                newRow[columns.length + 1] = String.valueOf(mentionedPersons.size()); //conta quantas pessoas foram mencionadas
                writer.write(String.join(",", newRow) + "\n");
            }
            counter++;
        }
        System.out.println("Arquivo com pessoas mencionadas gerado com sucesso: " + outputFile);
    } catch (IOException e) {
        System.out.println("Erro ao processar arquivo: " + e.getMessage());
    }
}

public static void main(String[] args) {
    // arquivos de entrada e saida
    String inputFile = "data/tweets.csv";
    String formattedDatesFile = "data/tweets_formated_data.csv";
    String mentionedPersonsFile = "data/tweets_mentioned_persons.csv";

    // passo 1: transformar as datas
    formatarDatas(inputFile, formattedDatesFile);

    // passo 2: adicionar pessoas mencionadas
    addMentionedPersons(formattedDatesFile, mentionedPersonsFile);
}
