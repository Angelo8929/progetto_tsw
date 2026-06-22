package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrdineBean;
import model.OrdineDAO;
import model.ProdottoOrdineBean;
import model.ProdottoOrdineDAO;
import model.UtenteBean;

@WebServlet("/FatturaServlet")
public class FatturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrdineDAO ordineDAO;
	private ProdottoOrdineDAO prodottoOrdineDAO;

	public void init() throws ServletException {
		ordineDAO = new OrdineDAO();
		prodottoOrdineDAO = new ProdottoOrdineDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("user");
		String idParam = request.getParameter("id");

		if (utenteLoggato == null || idParam == null || idParam.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		try {
			int idOrdine = Integer.parseInt(idParam);
			OrdineBean ordine = ordineDAO.doRetrieveByKey(idOrdine);

			// Controllo sicurezza: Un cliente normale può scaricare solo le SUE fatture.
			// L'admin tutte.
			if (ordine == null
					|| (!utenteLoggato.getIsAdmin() && !ordine.getEmail_utente().equals(utenteLoggato.getEmail()))) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,
						"Non sei autorizzato a visualizzare questa fattura.");
				return;
			}

			// PREPARAZIONE RISPOSTA HTTP PER IL DOWNLOAD DEL PDF
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=Fattura_AlcoMarket_#" + idOrdine + ".pdf");

			// CREAZIONE DOCUMENTO PDF CON OPENPDF
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());

			document.open();

			// Stili dei Font
			Font fontTitolo = new Font(Font.HELVETICA, 20, Font.BOLD);
			Font fontSub = new Font(Font.HELVETICA, 12, Font.NORMAL);
			Font fontBold = new Font(Font.HELVETICA, 11, Font.BOLD);

			// Intestazione Azienda
			document.add(new Paragraph("AlcoMarket S.r.l.", fontTitolo));
			document.add(new Paragraph("Via giovanni paolo II, Fisciano (SA)", fontSub));
			document.add(new Paragraph("Email: {a.verolla, m.korovskyy}@studenti.unisa.it", fontSub));
			document.add(new Paragraph(" ", fontSub)); // Spazio vuoto
			document.add(new Paragraph(
					"---------------------------------------------------------------------------------------------------------------------------------"));
			document.add(new Paragraph(" ", fontSub));

			// Dettagli Fattura / Cliente
			document.add(new Paragraph("FATTURA ORDINE RELATIVA AL DOCUMENTO N. #" + ordine.getId_ordine(), fontBold));
			document.add(new Paragraph("Data Spedizione: " + ordine.getData_ordine(), fontSub));
			document.add(new Paragraph("Cliente: " + ordine.getEmail_utente(), fontSub));
			document.add(new Paragraph(" ", fontSub));

			// TABELLA DEI PRODOTTI ACQUISTATI
			PdfPTable table = new PdfPTable(4); // 4 colonne
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 40f, 20f, 20f, 20f }); // Proporzioni colonne

			// Intestazioni della tabella
			table.addCell(new PdfPCell(new Phrase("Prodotto", fontBold)));
			table.addCell(new PdfPCell(new Phrase("Prezzo Unitario", fontBold)));
			table.addCell(new PdfPCell(new Phrase("Quantità", fontBold)));
			table.addCell(new PdfPCell(new Phrase("Totale", fontBold)));

			// Recuperiamo gli articoli legati a questo ordine dal tuo DAO
			// NOTA: adatta questo metodo e il Bean a come recuperi i singoli articoli
			// ordinati
			Collection<ProdottoOrdineBean> articoliOrdinati = prodottoOrdineDAO.doRetrieveByOrdine(idOrdine);

			if (articoliOrdinati != null) {
				for (ProdottoOrdineBean articolo : articoliOrdinati) {
					table.addCell(articolo.getNome_prodotto());
					table.addCell(String.format("%.2f €", articolo.getPrezzo()));
					table.addCell(String.valueOf(articolo.getQuantita()));

					double totaleArticolo = articolo.getPrezzo() * articolo.getQuantita();
					table.addCell(String.format("%.2f €", totaleArticolo));
				}
			}

			document.add(table);
			document.add(new Paragraph(" ", fontSub));

			// Totale Complessivo Speso
			Paragraph totaleFinale = new Paragraph(
					"TOTALE PAGATO: " + String.format("%.2f", ordine.getCosto_totale() / 100.0) + " €", fontTitolo);
			totaleFinale.setAlignment(Element.ALIGN_RIGHT);
			document.add(totaleFinale);

			// Chiusura Scrittura Documento
			document.close();

		} catch (NumberFormatException | SQLException | DocumentException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/AreaRiservataServlet");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}