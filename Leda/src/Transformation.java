import java.io.*; //fornece classes usadas para ler arquivos
import java.text.SimpleDateFormat; //trabalha com o formato de datas
import java.util.Date; //armazena e manipula datas
import java.util.Locale;

//funcao para abrir o arquivo csv e ler
public class Transformation{
    public static void dateFormatter(String openArchive, String closeArchive){
        try(BufferedReader readArchive = new BufferedReader(new FileReader(openArchive));
        BufferedWriter writerArchive = new BufferedWriter(new FileWriter(closeArchive))){
   
            String line;
            
            //adicionando um contador para nao estourar memoria, mude de acordo com a quantidade e twiiter que voce quer ver
            int counter = 0;
            while((line = readArchive.readLine()) != null && counter <= 10000){
                String[] colun = line.split(",");
            
                if(counter > 0){
                    //as datas estao na coluna 2, contando do 0
                    colun[2] = converterFormatoData(colun[2]);
                }
                writerArchive.write(String.join(",", colun) + "\n");
                counter++;
                }
                System.out.println("Arquivo formatado com sucesso: " + closeArchive);
            } 
        
        //caso ocorra algum erro
        catch(IOException e){
            System.out.println("Erro ao processar arquivo: " + e.getMessage());
        }
    }

    //funcao para mudar a data no formato brasileiro
    private static String converterFormatoData(String atualDate){
        try{
            //data do formato original dos tt
            SimpleDateFormat entryDate= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            //data que sera tarnsformada
            SimpleDateFormat exitDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            Date date = entryDate.parse(atualDate);
            return exitDate.format(date);
        }
        //caso de algum erro retorna a data original
        catch(Exception e){
            return atualDate;
        }
    }
}