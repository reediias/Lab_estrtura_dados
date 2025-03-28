public class Main{
    public static void main(String[] args){
        String inputFile = "data/tweets.csv";
        String formateDatesFile = "data/tweets_formated_data.csv";
        String mentionedPersonsFile = "data/tweets_mentioned_persons.csv";

        Transformation.dateFormatter(formateDatesFile, mentionedPersonsFile);
        Mentions.addMentionedPersons(inputFile, inputFile);
    }
}