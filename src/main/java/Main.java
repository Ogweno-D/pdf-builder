import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class Main {

    // Generate pdf from html file
    public static  void generatePDFFromHTMLFile( String htmlFilePath, String outputPath){
        try {
            Path htmlPath = Paths.get("src/main/resources/account-statements.html");
            String htmlContent = Files.readString(Paths.get(htmlFilePath));

            try (OutputStream os = new FileOutputStream(outputPath)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withHtmlContent(htmlContent, htmlPath.getParent().toUri().toString());
                builder.toStream(os);
                builder.run();
            }
            System.out.println("PDF generated successfully from HTML file: " + outputPath);

        } catch (Exception e) {
            System.err.println("Error generating PDF from HTML file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            // From HTML File
            generatePDFFromHTMLFile("C:\\Users\\Ogweno\\Desktop\\SkyWorld\\Frontend\\pdf-builder\\src\\main\\resources\\account-statements.html", "C:\\Users\\Ogweno\\Desktop\\SkyWorld\\Frontend\\pdf-builder\\src\\out\\account-statements.pdf");
            generatePDFFromHTMLFile("C:\\Users\\Ogweno\\Desktop\\SkyWorld\\Frontend\\pdf-builder\\src\\main\\resources\\p-nine-report.html", "C:\\Users\\Ogweno\\Desktop\\SkyWorld\\Frontend\\pdf-builder\\src\\out\\KRAp9.pdf");
            // Output pdf code...

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
