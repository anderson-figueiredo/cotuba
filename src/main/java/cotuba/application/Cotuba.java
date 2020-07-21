package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.epub.GeradorDeEPUB;
import cotuba.pdf.GeradorDePDF;
import cotuba.md.RenderizadorDeMDParaHtml;
import cotuba.domain.Capitulo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Cotuba {
    public void executa(String formato, Path diretorioDosMD, Path arquivoDeSaida) throws IOException {
        RenderizadorDeMDParaHtml renderizadorDeMDParaHtml = new RenderizadorDeMDParaHtml();
        List<Capitulo> capitulos = renderizadorDeMDParaHtml.renderiza(diretorioDosMD);

        Ebook ebook = new Ebook();
        ebook.setFormato(formato);
        ebook.setArquivoDeSaida(arquivoDeSaida);
        ebook.setCapitulos(capitulos);

        if ("pdf".equals(formato)) {

            GeradorDePDF geradorDePDF = new GeradorDePDF();
            geradorDePDF.gera(ebook);

        } else if ("epub".equals(formato)) {

            GeradorDeEPUB geradorDeEPUB = new GeradorDeEPUB();
            geradorDeEPUB.gera(ebook);

        } else {
            throw new RuntimeException("Formato do ebook inválido: " + formato);
        }
    }
}
