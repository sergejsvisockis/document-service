package io.github.sergejsvisockis.documentservice.docgen.pdf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PdfDocumentGeneratorTest {

    @InjectMocks
    private PdfDocumentGenerator<TestRequest> pdfDocumentGenerator;

    @Test
    void shouldCreatePdfWithCorrectContent() {
        // given
        TestRequest request = new TestRequest("Test Value");

        // when
        GeneratedPdfHolder result = pdfDocumentGenerator.generate(request);

        // then
        assertNotNull(result);
        assertNotNull(result.fileName());
        assertTrue(result.fileName().endsWith(".pdf"));
        assertNotNull(result.documentAsBytes());
        assertTrue(result.documentAsBytes().length > 0);
    }

    @Test
    void shouldCreateUniqueFileName() {
        // given
        TestRequest request = new TestRequest("Test Value");

        // when
        GeneratedPdfHolder firstResult = pdfDocumentGenerator.generate(request);
        GeneratedPdfHolder secondResult = pdfDocumentGenerator.generate(request);

        // then
        assertNotEquals(firstResult.fileName(), secondResult.fileName());
    }

    private record TestRequest(String value) {
    }
}
