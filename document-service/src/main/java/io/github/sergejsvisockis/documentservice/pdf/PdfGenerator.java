package io.github.sergejsvisockis.documentservice.pdf;

import io.github.sergejsvisockis.documentservice.utils.JsonUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Slf4j
@Component
public class PdfGenerator {

    /**
     * NOTE: that this implementation is very simplistic and is not supposed to be production-ready.
     *
     * @param request PDF documentAsBytes content.
     * @param <T>     the type out of which to generate.
     * @return generated PDF metadata holder.
     */
    @SneakyThrows
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
            contentStream.showText(JsonUtil.toJson(request));
            contentStream.endText();
            contentStream.close();
        } catch (Exception e) {
            document.close();
            throw new IllegalStateException("Failed to write page content", e);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);

        document.close();

        return new GeneratedPdfHolder(documentName, out.toByteArray());
    }

}
