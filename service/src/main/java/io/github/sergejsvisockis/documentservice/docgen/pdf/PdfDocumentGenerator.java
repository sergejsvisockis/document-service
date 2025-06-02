package io.github.sergejsvisockis.documentservice.docgen.pdf;

import io.github.sergejsvisockis.documentservice.docgen.DocumentGenerator;
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
public class PdfDocumentGenerator<T> implements DocumentGenerator<T, GeneratedPdfHolder> {

    /**
     * NOTE: that this implementation is very simplistic and is not supposed to be production-ready.
     *
     * @param request PDF document content.
     * @return generated PDF metadata holder.
     */
    @Override
    @SneakyThrows
    public GeneratedPdfHolder generate(T request) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        String documentName = UUID.randomUUID() + ".pdf";
        PDPageContentStream contentStream;
        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(FontName.COURIER), 12);
            contentStream.newLineAtOffset(0, 500);
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
