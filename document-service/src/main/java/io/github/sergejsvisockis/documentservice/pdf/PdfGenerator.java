package io.github.sergejsvisockis.documentservice.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PdfGenerator {

    public <T> GeneratedPdfHolder generatePdf(T request) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        String documentName = UUID.randomUUID() + ".pdf";
        PDPageContentStream contentStream;
        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(FontName.COURIER), 12);
            contentStream.showText(request.toString().replace("\n", ""));
            contentStream.endText();
            contentStream.close();

            document.save(documentName);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new GeneratedPdfHolder(documentName, document);
    }

}
