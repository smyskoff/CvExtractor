import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


/**
 * 
 */

/**
 * @author Sergii Myskov
 *
 */

public class PdfDocument implements IDocument {
    private final String fileName;
    
    public PdfDocument(String fileName) {
        this.fileName = fileName;
    }

    /* (non-Javadoc)
     * @see IDocument#getText()
     */
    @Override
    public String getText() {
        try {
            RandomAccessFile rar = new RandomAccessFile(new File(fileName), "r");
            PDFParser parser = new PDFParser(rar);

            parser.parse();

            COSDocument cosDoc = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDoc = new PDDocument(cosDoc);
            
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(pdDoc.getNumberOfPages());

            return pdfStripper.getText(pdDoc);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        PdfDocument doc = new PdfDocument("test-data/test.pdf");
        System.out.println(doc.getText());
    }

}
