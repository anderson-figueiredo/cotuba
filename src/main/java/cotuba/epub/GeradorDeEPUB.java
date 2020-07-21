package cotuba.epub;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GeradorDeEPUB {
    private Path diretorioDosMD;
    private Path arquivoDeSaida;

    public void gera(Ebook ebook) {
        Path arquivoDeSaida = ebook.getArquivoDeSaida();
        Book epub = new Book();
        for (Capitulo capitulo : ebook.getCapitulos()) {
            String conteudoHTML = capitulo.getConteudoHTML();
            String titulo = capitulo.getTitulo();

            epub.addSection(titulo, new Resource(conteudoHTML.getBytes(), MediatypeService.XHTML));
        }

        EpubWriter epubWriter = new EpubWriter();

        try {
            epubWriter.write(epub, Files.newOutputStream(arquivoDeSaida));
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao criar arquivo EPUB: " + arquivoDeSaida.toAbsolutePath(), ex);
        }
    }
}
