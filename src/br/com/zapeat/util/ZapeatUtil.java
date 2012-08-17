package br.com.zapeat.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.file.TSFile;
import br.com.topsys.util.TSUtil;

public class ZapeatUtil {

	private ZapeatUtil() {

	}

	public static String[] getVetor(String... parameters) {

		String[] vetor = null;

		if (!TSUtil.isEmpty(parameters)) {

			vetor = new String[parameters.length];
			int i = 0;

			for (String str : parameters) {

				vetor[i] = str;
				i++;

			}

		}

		return vetor;

	}

	public static void addErrorMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", msg));
	}

	public static void addInfoMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", msg));
	}

	public static void addWarnMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", msg));
	}

	public static String tratarString(String str) {
		return (str == null) ? "%" : "%" + str.toLowerCase() + "%";
	}

	public static String semAcento(String campo) {
		return "translate(lower(trim(".concat(campo).concat(")), 'áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC')");
	}
	
	public static String getStringParamSemAcento(String campo) {
		return "translate(lower(trim(".concat(campo).concat(")), 'áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC')" +
				" like translate(?, 'áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC')");
	}

	public static void criaArquivo(InputStream file, String arquivo) {

		try {
			FileUtils.copyInputStreamToFile(file, new File(arquivo));
		} catch (Exception ex) {
			throw new TSSystemException(ex);
		}

	}

	public static void criaArquivo(UploadedFile file, String arquivo) {
		try {
			criaArquivo(file.getInputstream(), arquivo);
		} catch (IOException e) {
			throw new TSSystemException(e);
		}
	}

	public static long gerarNumeroAleatorio() {
		return (long) ((10000 * Math.random()) * (100 * Math.random()));
	}

	public static String obterNomeArquivo(UploadedFile file) {

		if (!TSUtil.isEmpty(file)) {
			if (file.getFileName().contains("\\")) {
				String[] fileName = file.getFileName().split("\\\\");
				return fileName[fileName.length - 1];
			} else {
				return file.getFileName();
			}
		} else {
			return null;
		}
	}

	public static String criarImagemTemp(FileUploadEvent event) {

		String nomeArquivo = gerarNumeroAleatorio() + TSFile.obterExtensaoArquivo(event.getFile().getFileName());
		String arquivo = Constantes.PASTA_UPLOAD_TEMP + nomeArquivo;

		criaArquivo(event.getFile(), arquivo);

		return Constantes.PASTA_DOWNLOAD_TEMP + nomeArquivo;

	}
	
	public static String criarImagemTemp(UploadedFile file) {

		String nomeArquivo = gerarNumeroAleatorio() + TSFile.obterExtensaoArquivo(file.getFileName());
		String arquivo = Constantes.PASTA_UPLOAD_TEMP + nomeArquivo;

		criaArquivo(file, arquivo);

		return Constantes.PASTA_DOWNLOAD_TEMP + nomeArquivo;

	}
	
	public static Double tratarDouble(Double valor) {

		if (!TSUtil.isEmpty(valor) && valor.equals(0.0)) {

			valor = null;

		}

		return valor;

	}
	
	public static <ObjetoGenerico> List<ObjetoGenerico> movimentarLista(List<ObjetoGenerico> lista, ObjetoGenerico selecao, int mover) {

		int tamanho = lista.size();

		int posicao = lista.indexOf(selecao);

		ObjetoGenerico elemento = lista.get(posicao);

		if (mover == -1) { // UP

			if (posicao == 0) {

				lista.add(tamanho, elemento);

				lista.remove(posicao);

			} else {

				lista.add(posicao + mover, elemento);

				lista.remove(posicao + 1);
			}

		} else { // DOWN

			if (posicao == --tamanho) {

				lista.add(0, elemento);

				lista.remove(tamanho + 1);

			} else {

				elemento = lista.get(++posicao);

				lista.add(posicao - 1, elemento);

				lista.remove(posicao + 1);

			}
		}

		return lista;
	}

}
