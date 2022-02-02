// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class MarkdownParseSolo {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            ArrayList<Integer> indices = new ArrayList<>();

            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            indices.add(nextOpenBracket);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            indices.add(nextCloseBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            indices.add(openParen);
            int closeParen = markdown.indexOf(")", openParen);
            indices.add(closeParen);
            int exclamationP = markdown.indexOf("!", nextOpenBracket-1);

            Collections.sort(indices);

            if(nextOpenBracket-exclamationP == 1 && exclamationP >= 0){
                currentIndex = indices.get(indices.size()-1);
            }
            else if(nextOpenBracket == -1 || nextCloseBracket == -1 || openParen == -1 || closeParen == -1){
                currentIndex = indices.get(indices.size()-1);
            }
            else{
                toReturn.add(markdown.substring(openParen + 1, closeParen));
                currentIndex = closeParen + 1;
            }
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}
