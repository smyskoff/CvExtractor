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
    public static IDocument getDocument(final String fileName) {
        IDocument doc = null;
        String fileType = "";
        
        try {
            fileType = Files.probeContentType(Paths.get(fileName));
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
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
