package cotuba.pdf;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.AreaBreakType;
import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeradorDePDF {
    private Path arquivoDeSaida;
    private Path diretorioDosMD;

    public void gera(Ebook ebook) throws IOException {
        try (PdfWriter writer = new PdfWriter(Files.newOutputStream(ebook.getArquivoDeSaida()));
             PdfDocument pdf = new PdfDocument(writer);
             Document pdfDocument = new Document(pdf)) {

            for (Capitulo capitulo : ebook.getCapitulos()) {  // inserido

                String html = capitulo.getConteudoHTML(); // inserido

                List<IElement> convertToElements = HtmlConverter.convertToElements(html);
                for (IElement element : convertToElements) {
                    pdfDocument.add((IBlockElement) element);
                }

                pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            }

        } catch(Exception ex){
            throw new RuntimeException("Erro ao criar arquivo PDF: " + ebook.getArquivoDeSaida().toAbsolutePath(), ex);
        }
    }
}
