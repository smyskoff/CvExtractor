import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 */

/**
 * @author Sergii
 *
 */
public class DocumentFactory {
    public static IDocument getDocument(final String fileName) throws IOException {
        IDocument doc = null;
        String fileType = Files.probeContentType(Paths.get(fileName)).toLowerCase();
        
        switch (fileType) {
        case "application/pdf":
            doc = new PdfDocument(fileName);
            
            break;
        case "docx":
            doc = new DocxDocument(fileName);
            break;
        default:
            
        }

        return doc;
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        System.out.println(DocumentFactory.getDocument(args[0]).getText());
    }

}
