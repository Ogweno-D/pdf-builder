import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.*;
import java.nio.file.*;
import java.util.Map;

public class JSONPdfGenerator {

    public static void generatePDFFromJson(String htmlTemplatePath, String jsonDataPath, String outputPdfPath) {
        try {
            // Load JSON data
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = objectMapper.readValue(new File(jsonDataPath), Map.class);

            // Load and compile template
            Path templatePath = Paths.get(htmlTemplatePath);
            String rawHtml = Files.readString(templatePath);

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.compileInline(rawHtml);

            // Render HTML with data
            String renderedHtml = template.apply(data);

            // Render to PDF
            try (OutputStream os = new FileOutputStream(outputPdfPath)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(renderedHtml, templatePath.getParent().toUri().toString());
                builder.toStream(os);
                builder.run();
            }

            System.out.println("PDF generated successfully: " + outputPdfPath);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Entry point
    public static void main(String[] args) {
        generatePDFFromJson(
                "src/main/resources/account-statements.html",
                "src/main/resources/json/account-statement.json",
                "src/out/json-account-statements.pdf.pdf"
        );
        generatePDFFromJson(
                "src/main/resources/p-nine-report.html",
                "src/main/resources/json/account-statement.json",
                "src/out/json-p-nine-report.pdf"
        );
    }
}
