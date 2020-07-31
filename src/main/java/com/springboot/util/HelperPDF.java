package com.springboot.util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.springboot.biz.OperatorBiz;
import com.springboot.entity.Company;
import com.springboot.entity.Item;
import com.springboot.entity.Operator;
import com.springboot.entity.Pipe;
import com.springboot.entity.Project;

@Component
public class HelperPDF extends PdfPageEventHelper {

	@Value(value = "${myfile}")
	private String myfile;

	@Resource
	private OperatorBiz operatorBiz;

	private Project project = null;
	private Document document = null;
	private Paragraph wrap = new Paragraph("\n"); // 换行符
	private Map<String, Object> map = null;

	public void initPDF(Project project, String FileName) {
		try {
			document = new Document(PageSize.A4);
			document.setMargins(20, 20, 15, 10); // 左右上下间距
			OutputStream output = new FileOutputStream(FileName);
			PdfWriter writer = PdfWriter.getInstance(document, output);
			writer.setPageEvent(this);
			document.open(); // 打开文档
			/*****************************************************************/
			this.project = project;
			List<Pipe> pipes = project.getPipes();
			Map<String, List<Pipe>> pipeMap = new TreeMap<String, List<Pipe>>();
			for (int i = 0; pipes != null && i < pipes.size(); i++) {
				Pipe pipe = pipes.get(i);
				if (pipe.getWorkorder() == null)
					pipe.setWorkorder("");
				if (pipe.getSmanholeno() == null)
					pipe.setSmanholeno("");
				if (pipe.getFmanholeno() == null)
					pipe.setFmanholeno("");
				if (pipe.getUses() == null)
					pipe.setUses("");
				if (pipe.getDire() == null)
					pipe.setDire("");
				if (pipe.getHsize() == null)
					pipe.setHsize("");
				if (pipe.getShape() == null)
					pipe.setShape("");
				if (pipe.getMater() == null)
					pipe.setMater("");
				if (pipe.getVideono() == null)
					pipe.setVideono("");
				String key = pipe.getWorkorder() + pipe.getUses();
				if (!pipeMap.containsKey(key)) {
					List<Pipe> list = new ArrayList<Pipe>();
					pipeMap.put(key, list);
				}
				pipeMap.get(key).add(pipe);
			}
			/*****************************************************************/
			Font font = getFont(6, 0, null);
			int height_A = 14; // 表格A的行高
			PdfPTable PipeTitleA = null;
			DecimalFormat foramt1 = new DecimalFormat("#0");
			DecimalFormat foramt2 = new DecimalFormat("#0.0");
			// 输出表格FromA
			for (String key : pipeMap.keySet()) {
				double PipeLength_A = 0.0; // 记录管线长度
				double TableHeightA = 0.0; // 记录表格高度
				List<Pipe> list = pipeMap.get(key);
				PdfPTable table_a = getTableFormA(list.get(0));
				for (int i = 0; list != null && i < list.size(); i++) {
					Pipe pipe = list.get(i);
					if (TableHeightA == 0.0) {
						document.newPage();
						writeTitle(document, "fm1");
						document.add(wrap); // 添加空行
						document.add(table_a);
						document.add(wrap); // 添加空行
						PipeTitleA = getPipeTitleA();
					}
					PipeLength_A += pipe.getTestlength(); // 计算管道长度
					String direct = "U".equals(pipe.getDire()) ? "Upstream" : "Downstream";
					PipeTitleA.addCell(getCell(pipe.getNo() + "", font, 1, 1, 1, height_A, 1));
					PipeTitleA.addCell(getCell(pipe.getSmanholeno(), font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(pipe.getFmanholeno(), font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(direct, font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(pipe.getTestlength() + "", font, 1, 1, 1, 0, 1));
					if (StringUtils.isEmpty(pipe.getWsize()))
						PipeTitleA.addCell(getCell(pipe.getHsize(), font, 1, 1, 1, 0, 1));
					else
						PipeTitleA.addCell(getCell(pipe.getWsize() + "X" + pipe.getHsize(), font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(pipe.getMater(), font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(pipe.getSinvertlevel(), font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(pipe.getScoverlevel(), font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(pipe.getSdepth(), font, 1, 1, 1, 0, 1));
					for (int s = 0; s < pipe.getScore().length; s++)
						PipeTitleA.addCell(getCell(foramt2.format(pipe.getScore()[s]), font, 1, 1, 1, 0, 1));
					for (int s = 0; s < pipe.getGrade().length; s++)
						PipeTitleA.addCell(getCell(foramt1.format(pipe.getGrade()[s]), font, 1, 1, 1, 0, 1));
					PipeTitleA.addCell(getCell(pipe.getComment(), font, 1, 1, 1, 0, 1));
					if (i == list.size() - 1) {
						PipeTitleA.addCell(getCell("Total Length", font, 4, 1, 1, 16, 1));
						PipeTitleA.addCell(getCell(foramt2.format(PipeLength_A), font, 25, 1, 1, 16, 1));
					}
					TableHeightA = PipeTitleA.getTotalHeight(); // 计算表格高度
					double height = TableHeightA + table_a.getTotalHeight();
					if (height >= 600 || i == list.size() - 1) {
						document.add(PipeTitleA);
						appendWrap(document, 630 - height);
						document.add(getStandar(project));
						TableHeightA = 0.0;
					}
				}
			}
			/*****************************************************************/
			// 输出表格FromB
			int height_B = 14; // 表格A的行高
			int surve[] = new int[20];
			PdfPTable PipeTitleB1 = getPipeTitleB1();
			PdfPTable PipeTitleB2 = null;
			for (String key : pipeMap.keySet()) {
				double PipeLength_B = 0.0; // 记录管线长度
				double TableHeightB = 0.0; // 记录表格高度
				List<Pipe> list = pipeMap.get(key);
				PdfPTable table_b = getTableFormA(list.get(0));
				for (int i = 0; list != null && i < list.size(); i++) {
					Pipe pipe = list.get(i);
					if (TableHeightB == 0.0) {
						document.newPage();
						writeTitle(document, "fm2");
						document.add(wrap); // 添加空行
						document.add(table_b);
						document.add(wrap); // 添加空行
						document.add(PipeTitleB1); // 添加空行
						PipeTitleB2 = getPipeTitleB2();
					}
					PipeLength_B += pipe.getTestlength();
					String direct = "U".equals(pipe.getDire()) ? "Upstream" : "Downstream";
					PipeTitleB2.addCell(getCell(pipe.getNo() + "", font, 1, 1, 1, height_B, 1));
					PipeTitleB2.addCell(getCell(pipe.getSmanholeno(), font, 1, 1, 1, 0, 1));
					PipeTitleB2.addCell(getCell(pipe.getFmanholeno(), font, 1, 1, 1, 0, 1));
					PipeTitleB2.addCell(getCell(direct, font, 1, 1, 1, 0, 1));
					PipeTitleB2.addCell(getCell(pipe.getTestlength() + "", font, 1, 1, 1, 0, 1));
					for (int j = 0; j < pipe.getSurve().length; j++) {
						String value = pipe.getSurve()[j] == 0 ? "" : pipe.getSurve()[j] + "";
						PipeTitleB2.addCell(getCell(value, font, 1, 1, 1, 0, 1));
						surve[j] += pipe.getSurve()[j];
					}
					if (i == list.size() - 1) {
						PipeTitleB2.addCell(getCell("Total", font, 4, 1, 1, 16, 1));
						PipeTitleB2.addCell(getCell(foramt2.format(PipeLength_B), font, 1, 1, 1, 0, 1));
						for (int j = 0; j < surve.length; j++) {
							String value = surve[j] == 0 ? "" : surve[j] + "";
							PipeTitleB2.addCell(getCell(value, font, 1, 1, 1, 0, 1));
							surve[j] = 0;
						}
					}
					TableHeightB = PipeTitleB2.getTotalHeight();
					double height = TableHeightB + table_b.getTotalHeight();
					if (height >= 600 || i == list.size() - 1) {
						document.add(PipeTitleB2);
						TableHeightB = 0.0;
					}
				}
			}
			/*****************************************************************/
			font = getFont(12, 1, null);
			Paragraph paragraph = new Paragraph("Coding", font);
			PdfPCell Coding = new PdfPCell(paragraph);
			Coding.setVerticalAlignment(Element.ALIGN_MIDDLE);
			Coding.setHorizontalAlignment(Element.ALIGN_RIGHT);
			Coding.setUseAscender(true);
			Coding.setRotation(90);
			Coding.setBorder(0);
			/*****************************************************************/
			PdfPTable TableFormC = null;
			PdfPTable explain = getExplain();
			// 输出表格FromC
			for (int i = 0; i < pipes.size(); i++) {
				int index = 0;
				Pipe pipe = pipes.get(i);
				List<Item> items = pipe.getItems();
				while (items != null && items.size() > index) {
					document.newPage();
					writeTitle(document, "fm3");
					TableFormC = getTableFormC(pipe);
					tableWrap(TableFormC, 2, 5);
					TableFormC.addCell(Coding);

					int z = index + 30 < items.size() ? index + 30 : items.size();
					double size1 = Double.valueOf(items.get(index).getDist());
					double size2 = Double.valueOf(items.get(z - 1).getDist());
					int length = 1; // 绘画管道的长度
					int propor = (int) (Math.ceil((size2 - size1) / 3.075) * 25);
					if (propor != 0)
						length = (int) ((size2 - size1) / propor * 100 / 12.3 * 350);
					font = getFont(8, 1, null);
					int[] widths = new int[] { 60, 40, 55, 40, 400, 40, 40 };
					PdfPTable title = getTable(7, 505, widths);
					title.addCell(getCell("Video No", font, 1, 1, 0, 16, 1));
					title.addCell(getCell("1:" + propor, font, 1, 1, 0, 0, 1));
					title.addCell(getCell("Chainage", font, 1, 1, 0, 0, 1));
					title.addCell(getCell("Code", font, 1, 1, 0, 0, 1));
					title.addCell(getCell("Observation", font, 1, 1, 0, 0, 0));
					title.addCell(getCell("Photo", font, 1, 1, 0, 0, 1));
					title.addCell(getCell("Grade", font, 1, 1, 0, 0, 1));
					PdfPCell pdfPCell = new PdfPCell(title);
					pdfPCell.setHorizontalAlignment(1);
					pdfPCell.setBorderWidthBottom(0);
					TableFormC.addCell(pdfPCell);

					TableFormC.addCell(getImage(pipe.getDire()));
					Paragraph pragraph = new Paragraph("", font);
					pdfPCell = new PdfPCell(pragraph);
					pdfPCell.setBorderWidthTop(0);
					pdfPCell.setBorderWidthBottom(0);
					pdfPCell.setFixedHeight(460);
					TableFormC.addCell(pdfPCell);

					PdfContentByte content = writer.getDirectContent();
					PdfTemplate template = content.createTemplate(570, 540);
					PdfGraphics2D graphics = new PdfGraphics2D(content, 570, 540);
					graphics.setFont(new java.awt.Font("", 0, 9));
					graphics.setColor(new Color(150, 150, 150));
					graphics.fillRect(90, 50, 12, length); // 画管道

					double pipesize = size2 - size1 == 0.0 ? 1 : size2 - size1;
					for (int j = index; items != null && j < z; j++) {
						Item item = items.get(j);
						double itemdist = Double.valueOf(item.getDist()) - size1;
						if (item.getCode().equals("MH")) { // 画出MH
							double dist = Double.valueOf(item.getDist());
							int distance = "0.0".equals(item.getDist()) ? 20 : 50;
							int location = (int) (itemdist / pipesize * length + distance);
							if ("F".equals(pipe.getUses()))
								graphics.fillRect(81, location, 30, 30);
							else
								graphics.fillArc(81, location, 30, 30, 0, 360);
							if (item.getDist().equals("0.0"))
								graphics.drawString(pipe.getSmanholeno() + "", 88, 16);
							if (dist == pipe.getTestlength() && pipe.getTestlength() != 0)
								graphics.drawString(pipe.getFmanholeno() + "", 88, length + 90);
						}
						if (item.getCode().equals("JN")) { // 画出JN
							int location = (int) (itemdist / pipesize * length + 50);
							graphics.fillRect(78, location - 6, 12, 6);
						}
					}
					int j = index, itemlentg = 50, distance = 0, location = 0; // 位置
					graphics.setFont(new java.awt.Font("", 0, 6)); // 设置画笔
					while (items != null && j < z) {
						Item item = items.get(j);
						distance = (int) ((Double.valueOf(item.getDist()) - size1) / pipesize * length) + 50;
						location = distance - itemlentg < 0 ? itemlentg : distance;

						List<String> list = getTextList(item.getDepict(), graphics);
						itemlentg = location + 10 * list.size();
						String type = item.getType3();
						if (item.getGrade() >= 4) {
							graphics.setFont(new java.awt.Font("Helvetica", 1, 9));
							if ("Service".equals(type))
								graphics.setColor(new Color(139, 69, 19));
							else
								graphics.setColor(new Color(255, 60, 60));
						} else {
							// graphics.setFont(new java.awt.Font("TimesRoman", 0, 9));
							graphics.setFont(new java.awt.Font("Helvetica", 0, 9));
							if ("Miscel-laneous".equals(type))
								graphics.setColor(new Color(0, 0, 0));
							else if ("Node".equals(type) || "Repair Points".equals(type))
								graphics.setColor(new Color(0, 128, 0));
							else if ("Service".equals(type))
								graphics.setColor(new Color(139, 69, 19));
							else
								graphics.setColor(new Color(255, 60, 60));
						}
						if (item.getCode().length() > 2 && item.getCode().indexOf("-") != -1)
							item.setCode(item.getCode().substring(0, item.getCode().length() - 2));
						graphics.drawLine(90, distance, 102, distance);
						graphics.drawLine(102, distance, 125, location);
						graphics.drawLine(125, location, 145, location);
						graphics.drawString(foramt2.format(AppHelper.getDoule(item.getDist())), 150, location + 3);
						graphics.drawString(item.getCode() + "", 180, location + 3);

						for (int no = 0; no < list.size(); no++)
							graphics.drawString(list.get(no), 208, location + 3 + no * 10);
						graphics.drawString(item.getPhoto() + "", 510, location + 3);
						graphics.drawString((int) item.getGrade() + "", 542, location + 3);
						j++;
					}
					// 结束绘画
					graphics.dispose();
					content.addTemplate(template, 0, 0);
					// 添加备注
					pdfPCell = new PdfPCell(explain);
					pdfPCell.setBorderWidthTop(0);
					pdfPCell.setFixedHeight(36);
					TableFormC.addCell(pdfPCell);
					document.add(TableFormC);
					/****************************************************/
					index += 30;
				}
				int imgNo = 0;
				int[] widths = new int[] { 1 };
				Image image = null;
				PdfPCell pdfPCell = null;
				PdfPTable tableFormD = null;
				PdfPTable PipeFormD = getPipeFormD(pipe);
				for (int k = 0; items != null && k < items.size(); k++) {
					Item item = items.get(k);
					if (StringUtils.isEmpty(item.getPicture()))
						continue;
					if (imgNo % 2 == 0) {
						document.newPage(); // 新建页面
						writeTitle(document, "fm4");
						tableFormD = getTable(1, 500, widths);
						pdfPCell = new PdfPCell(PipeFormD);
						pdfPCell.setBorderWidthBottom(0);
						pdfPCell.setFixedHeight(40);
						pdfPCell.setPadding(4);
						tableFormD.addCell(pdfPCell);
					}
					// 添加图片
					image = Image.getInstance(myfile + "ItemImage/" + item.getPicture() + ".png");
					float ipropor = image.getWidth() / image.getHeight();
					if (ipropor > (4f / 3))
						image.scaleAbsolute(360, 360 / ipropor);
					else if (ipropor < (4f / 3))
						image.scaleAbsolute(270 * ipropor, 270);
					else
						image.scaleAbsolute(360, 270);
					pdfPCell = new PdfPCell(image);
					pdfPCell.setFixedHeight(275);
					pdfPCell.setUseAscender(true);
					pdfPCell.setHorizontalAlignment(1);
					pdfPCell.setVerticalAlignment(5);
					pdfPCell.setBorderWidthTop(0);
					pdfPCell.setBorderWidthBottom(0);
					tableFormD.addCell(pdfPCell);
					// 添加说明
					pdfPCell = new PdfPCell(getItemFormD(pipe, item));
					pdfPCell.setUseAscender(true);
					pdfPCell.setFixedHeight(36);
					pdfPCell.setHorizontalAlignment(1);
					pdfPCell.setVerticalAlignment(5);
					pdfPCell.setBorderWidthTop(0);
					pdfPCell.setBorderWidthBottom(0);
					tableFormD.addCell(pdfPCell);
					imgNo++;

					if (tableFormD != null && imgNo % 2 == 0) {
						float height = 665 - tableFormD.getTotalHeight();
						pdfPCell = new PdfPCell();
						pdfPCell.setBorderWidthTop(0);
						pdfPCell.setBorderWidthBottom(0);
						pdfPCell.setFixedHeight(height);
						tableFormD.addCell(pdfPCell);
						// 添加说明
						pdfPCell = new PdfPCell(explain);
						pdfPCell.setBorderWidthTop(0);
						pdfPCell.setFixedHeight(34);
						tableFormD.addCell(pdfPCell);
						document.add(tableFormD);
						imgNo = 0;
					}
				}
				if (tableFormD != null && imgNo != 0) {
					float height = 665 - tableFormD.getTotalHeight();
					pdfPCell = new PdfPCell();
					pdfPCell.setBorderWidthTop(0);
					pdfPCell.setBorderWidthBottom(0);
					pdfPCell.setFixedHeight(height);
					tableFormD.addCell(pdfPCell);
					// 添加说明
					pdfPCell = new PdfPCell(explain);
					pdfPCell.setBorderWidthTop(0);
					pdfPCell.setFixedHeight(34);
					tableFormD.addCell(pdfPCell);
					document.add(tableFormD);
					imgNo = 0;
				}
			}
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onEndPage(PdfWriter writer, Document document) {
		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
			Font font = new Font(bfChinese, 8, 0);
			File file = ResourceUtils.getFile("classpath:static/picture/footer.png");
			Image footerIamge = Image.getInstance(file.getPath());
			footerIamge.scaleAbsolute(560, 32);
			footerIamge.setAbsolutePosition(20, 0);
			writer.getDirectContent().addImage(footerIamge);
			Phrase phrase = new Phrase(writer.getPageNumber() + "", font);
			ColumnText.showTextAligned(writer.getDirectContent(), 1, phrase, 300, 16, 0);
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private Font getFont(int size, int bold, BaseColor color) {
		try {
			// BaseFont baseFont = BaseFont.createFont("Times-Roman", "Cp1252", false);
			BaseFont baseFont = BaseFont.createFont("Helvetica", "Cp1252", false);
			Font font = new Font(baseFont, size, bold);
			if (color != null)
				font.setColor(color);
			return font;
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Font getChineseFont(int size, int bold, BaseColor color) {
		try {
			BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
			Font font = new Font(baseFont, size, bold);
			font.setFamily("Times New Roman");
			if (color != null)
				font.setColor(color);
			return font;
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private PdfPTable getTable(int colspan, float width, int[] widths) {
		try {
			PdfPTable table = new PdfPTable(colspan);
			table.setLockedWidth(true);
			table.setTotalWidth(width);
			table.setWidths(widths);
			return table;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void getWidths(int[] widths, int index, int... value) {
		for (int i = 0; i < value.length; i++)
			widths[index + i] = value[i];
	}

	private PdfPCell getCell(String text, Font font, int col, int row, int border, int height, int align) {
		text = text == null ? "" : text;
		Paragraph pragraph = new Paragraph(text, font);
		PdfPCell cell = new PdfPCell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setHorizontalAlignment(align);
		cell.setVerticalAlignment(5);
		if (border == 0)
			cell.setBorder(border);
		if (height != 0)
			cell.setMinimumHeight(height);
		return cell;
	}

	private PdfPCell getCell(String text, Font font, int col, int row, int height, int rotate) {
		text = text == null ? "" : text;
		Paragraph pragraph = new Paragraph(text, font);
		PdfPCell cell = new PdfPCell(pragraph);
		cell.setColspan(col);
		cell.setRowspan(row);
		if (rotate == 90)
			cell.setHorizontalAlignment(0);
		else
			cell.setHorizontalAlignment(1);
		cell.setVerticalAlignment(5);
		cell.setRotation(rotate);
		if (height != 0)
			cell.setMinimumHeight(height);
		return cell;
	}

	private void appendTableTitle(PdfPTable table, String text, Font font, int height) {
		text = text == null ? "" : text;
		Paragraph paragraph = new Paragraph(text, font);
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setHorizontalAlignment(2);
		cell.setFixedHeight(height);
		cell.setUseAscender(true);
		cell.setBorder(0);
		table.addCell(cell);
	}

	private void appendTableValue(PdfPTable table, String text, Font font, int height, int colspan, int border[]) {
		text = text == null ? "" : text;
		Paragraph paragraph = new Paragraph(text, font);
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setFixedHeight(height);
		cell.setUseAscender(true);
		cell.setColspan(colspan);
		if (border[0] == 0)
			cell.setBorderWidthTop(0);
		if (border[1] == 0)
			cell.setBorderWidthRight(0);
		if (border[2] == 0)
			cell.setBorderWidthBottom(0);
		if (border[3] == 0)
			cell.setBorderWidthLeft(0);
		table.addCell(cell);
	}

	private void TableValue(PdfPTable table, String text, Font font, int height, int colspan, int border[], int align) {
		text = text == null ? "" : text;
		Paragraph paragraph = new Paragraph(text, font);
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setHorizontalAlignment(align);
		cell.setFixedHeight(height);
		cell.setUseAscender(true);
		cell.setColspan(colspan);
		if (border[0] == 0)
			cell.setBorderWidthTop(0);
		if (border[1] == 0)
			cell.setBorderWidthRight(0);
		if (border[2] == 0)
			cell.setBorderWidthBottom(0);
		if (border[3] == 0)
			cell.setBorderWidthLeft(0);
		table.addCell(cell);
	}

	private void appendWrap(Document document, double height) {
		try {
			int[] width = new int[] { 1 };
			PdfPTable table = getTable(1, 560f, width);
			PdfPCell cell = new PdfPCell();
			cell.setFixedHeight((float) height);
			cell.setBorder(0);
			table.addCell(cell);
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void tableWrap(PdfPTable table, int colspan, int height) {
		PdfPCell cell = new PdfPCell();
		cell.setColspan(colspan);
		cell.setFixedHeight(height);
		cell.setBorder(0);
		table.addCell(cell);
	}

	private void nestTable(PdfPTable table1, PdfPTable table2, int col, int row, int padding, int border, int height) {
		PdfPCell cell = new PdfPCell(table2);
		cell.setColspan(col);
		cell.setRowspan(row);
		cell.setPadding(padding);
		cell.setHorizontalAlignment(1);
		if (border == 0)
			cell.setBorder(border);
		if (height != 0)
			cell.setMinimumHeight(height);
		table1.addCell(cell);
	}

	private void writeTitle(Document document, String name) {
		try {
			String path = "classpath:static/picture/";
			File file = ResourceUtils.getFile(path + name + ".png");
			Image image = Image.getInstance(file.getPath());
			image.scaleAbsolute(400, 80);
			image.setAlignment(1);
			document.add(image);
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private PdfPCell getImage(String direct) {
		try {
			String path = "classpath:static/picture/";
			String name = "U".equals(direct) ? "Flow_up.png" : "Flow_dp.png";
			File file = ResourceUtils.getFile(path + name);
			Image image = Image.getInstance(file.getPath());
			image.scaleAbsolute(18, 110); // 设置图片大小
			PdfPCell pdfPCell = new PdfPCell(image);
			pdfPCell.setVerticalAlignment(5);
			pdfPCell.setRowspan(2);
			pdfPCell.setBorder(0);
			return pdfPCell;
		} catch (IOException | BadElementException e) {
			e.printStackTrace();
			return null;
		}
	}

	private PdfPTable getTableFormA(Pipe pipe) {
		Company company = project.getUser().getCompany();
		map = AppHelper.getMap("name", pipe.getOperator(), "company", company);
		Operator operator = operatorBiz.findInfoOperator(map);
		String member = ""; // 会员编号
		if (!StringUtils.isEmpty(operator))
			member = operator.getMembernumber();
		String use = "F".equals(pipe.getUses()) ? "Foul" : "Surface water";
		int height = 14;
		Font font1 = getFont(10, 0, null); // 正常字体
		Font font2 = getFont(10, 1, null); // 加粗字体
		int[] widths = new int[] { 16, 20, 1, 16, 20 };// 按百分比分配单元格宽带
		PdfPTable table = getTable(5, 520, widths); // 创建信息表格1
		/********************************************************************/
		table.addCell(getCell("Project No", font1, 1, 1, 0, height, 0));
		table.addCell(getCell(project.getName(), font2, 1, 1, 1, 0, 0));
		table.addCell(getCell("", font1, 1, 1, 0, 0, 0));
		table.addCell(getCell("Works Order No", font1, 1, 1, 0, height, 0));
		table.addCell(getCell(pipe.getWorkorder(), font2, 1, 1, 1, 0, 0));
		table.addCell(getCell("", font1, 5, 1, 0, 4, 0)); // 插入空白行

		table.addCell(getCell("Slope Reference No", font1, 1, 1, 0, 0, 0));
		table.addCell(getCell(pipe.getSloperef(), font2, 1, 1, 1, 0, 0));
		table.addCell(getCell("", font1, 1, 1, 0, 0, 0));
		table.addCell(getCell("Operator", font1, 1, 1, 0, 0, 0));
		table.addCell(getCell(pipe.getOperator(), font2, 1, 1, 1, 0, 0));
		table.addCell(getCell("", font1, 5, 1, 0, 4, 0)); // 插入空白行

		table.addCell(getCell("Drain/Sewer Use", font1, 1, 1, 0, height, 0));
		table.addCell(getCell(use, font2, 1, 1, 1, 0, 0));
		table.addCell(getCell("", font1, 1, 1, 0, 0, 0));
		table.addCell(getCell("Operator No", font1, 1, 1, 0, 0, 0));
		table.addCell(getCell(member, font2, 1, 1, 1, 0, 0));
		table.addCell(getCell("", font1, 5, 1, 0, 4, 0)); // 插入空白行

		table.addCell(getCell("Company", font1, 1, 1, 0, height, 0));
		table.addCell(getCell(project.getClient(), font2, 4, 1, 1, 0, 0));
//		table.addCell(getCell("", font2, 1, 1, 0, 0, 0));
//		table.addCell(getCell("Date", font1, 1, 1, 0, height, 0));
//		table.addCell(getCell(pipe.getDate(), font2, 1, 1, 1, 0, 0));
		table.addCell(getCell("", font1, 5, 1, 0, 4, 0)); // 插入空白行

		table.addCell(getCell("Road Name", font1, 1, 1, 0, height, 0));
		table.addCell(getCell(pipe.getRoadname(), font2, 4, 1, 1, 0, 0));
//		table.addCell(getCell("", font2, 1, 1, 0, 0, 0));
//		table.addCell(getCell("Time", font1, 1, 1, 0, height, 0));
//		table.addCell(getCell(pipe.getTime(), font2, 1, 1, 1, 0, 0));
		return table;
	}

	private PdfPTable getPipeTitleA() {
		Font font = getFont(8, 1, null);
		int[] widths = new int[29];
		getWidths(widths, 0, 25, 30, 30, 48, 24, 24, 24, 25, 25, 25, 24, 24, 24, 24, 24);
		getWidths(widths, 15, 24, 24, 24, 24, 15, 15, 15, 15, 15, 15, 15, 15, 15, 80);
		PdfPTable pipeTitleA = getTable(29, 570, widths);
		pipeTitleA.addCell(getCell("ID", font, 1, 3, 0, 0));
		pipeTitleA.addCell(getCell("Manhole", font, 2, 1, 25, 0));
		pipeTitleA.addCell(getCell("Direction", font, 1, 3, 0, 0));
		pipeTitleA.addCell(getCell("Pipe", font, 3, 1, 0, 0));
		pipeTitleA.addCell(getCell("Manhole(From)", font, 3, 1, 0, 0));
		pipeTitleA.addCell(getCell("Score", font, 9, 1, 0, 0));
		pipeTitleA.addCell(getCell("Grades", font, 9, 1, 0, 0));
		pipeTitleA.addCell(getCell("Remarks", font, 1, 3, 0, 0));
		pipeTitleA.addCell(getCell("From", font, 1, 2, 0, 0));
		pipeTitleA.addCell(getCell("To", font, 1, 2, 0, 0));
		pipeTitleA.addCell(getCell("Length(m)", font, 1, 2, 0, 90));
		pipeTitleA.addCell(getCell("Size(mm)", font, 1, 2, 0, 90));
		pipeTitleA.addCell(getCell("Material", font, 1, 2, 0, 90));
		pipeTitleA.addCell(getCell("I.L", font, 1, 2, 0, 0));
		pipeTitleA.addCell(getCell("C.L", font, 1, 2, 0, 0));
		pipeTitleA.addCell(getCell("Depths(m)", font, 1, 2, 0, 90));
		pipeTitleA.addCell(getCell("SCG", font, 3, 1, 25, 0));
		pipeTitleA.addCell(getCell("ICG", font, 3, 1, 0, 0));
		pipeTitleA.addCell(getCell("SPG", font, 3, 1, 0, 0));
		pipeTitleA.addCell(getCell("SCG", font, 3, 1, 0, 0));
		pipeTitleA.addCell(getCell("ICG", font, 3, 1, 0, 0));
		pipeTitleA.addCell(getCell("SPG", font, 3, 1, 0, 0));
		for (int i = 0; i < 6; i++) {
			pipeTitleA.addCell(getCell("P", font, 1, 1, 25, 0));
			pipeTitleA.addCell(getCell("M", font, 1, 1, 0, 0));
			pipeTitleA.addCell(getCell("T", font, 1, 1, 0, 0));
		}
		return pipeTitleA;
	}

	private PdfPTable getPipeTitleB1() {
		Font font1 = getFont(12, 1, null);
		int[] widths = new int[] { 219, 180, 160, 40 };
		PdfPTable pipeTitleB1 = getTable(4, 570, widths);
		pipeTitleB1.addCell(getCell("Works Order No", font1, 1, 2, 0, 0));
		pipeTitleB1.addCell(getCell("Colour CCTV Drainage Survey", font1, 3, 1, 20, 0));
		pipeTitleB1.addCell(getCell("Pipe", font1, 1, 1, 20, 0));
		pipeTitleB1.addCell(getCell("Service Condition", font1, 1, 1, 20, 0));
		pipeTitleB1.addCell(getCell("MISC", font1, 1, 1, 20, 0));
		return pipeTitleB1;
	}

	private PdfPTable getPipeTitleB2() {
		int[] widths = new int[25];
		this.getWidths(widths, 0, 25, 45, 45, 48, 36, 20, 20, 20, 20, 20, 20, 20);
		this.getWidths(widths, 12, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20);
		Font font = getFont(8, 1, null);
		PdfPTable pipeTitleB2 = getTable(25, 570, widths);
		pipeTitleB2.addCell(getCell("ID", font, 1, 2, 0, 0));
		pipeTitleB2.addCell(getCell("Manhole", font, 2, 1, 42, 0));
		pipeTitleB2.addCell(getCell("Direction", font, 1, 2, 0, 0));
		pipeTitleB2.addCell(getCell("Meters", font, 1, 2, 0, 0));
		pipeTitleB2.addCell(getCell("Urgent", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Cracked", font, 1, 2, 84, 90));
		pipeTitleB2.addCell(getCell("Fractured", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Broken", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Deformed", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Collapsed", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Hole", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Surface Spalling/Wear", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Joint Displaced", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Open Joint", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Roots", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Infiltration", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Encrustation", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Silt", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Grease", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Obstruction", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Water Line", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Line", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Survey Abandoned", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Camera Under Water", font, 1, 2, 0, 90));
		pipeTitleB2.addCell(getCell("Form", font, 1, 1, 42, 0));
		pipeTitleB2.addCell(getCell("To", font, 1, 1, 42, 0));
		return pipeTitleB2;
	}

	private PdfPTable getTableFormC(Pipe pipe) {
		int[] widths = new int[] { 30, 680 };
		Font font = getFont(12, 1, null);
		PdfPTable report = getTable(2, 540, widths);
		Paragraph paragraph = new Paragraph("Heading", font);
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setVerticalAlignment(5);
		cell.setHorizontalAlignment(2);
		cell.setUseAscender(true);
		cell.setRotation(90);
		cell.setBorder(0);
		report.addCell(cell);
		/*********************************************************************/
		Font font1 = getFont(7, 0, null);
		Font font2 = getFont(7, 1, null);
		widths = new int[] { 80, 120, 80, 120, 80, 120, 80, 120 };
		PdfPTable pipeInfo = getTable(8, 505, widths);
		// 第一行
		/** border为边框数组，分别代表上右下左 */
		int[] border = new int[] { 1, 1, 0, 1 };
		appendTableTitle(pipeInfo, "Project No", font1, 10);
		appendTableValue(pipeInfo, project.getName(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Operator", font1, 10);
		appendTableValue(pipeInfo, pipe.getOperator(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Date", font1, 10);
		appendTableValue(pipeInfo, pipe.getDate(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "ID", font1, 10);
		appendTableValue(pipeInfo, pipe.getNo() + "", font2, 10, 1, border);

		border = new int[] { 0, 1, 1, 1 };
		appendTableTitle(pipeInfo, "Works Order", font1, 10);
		appendTableValue(pipeInfo, pipe.getWorkorder(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Purpose", font1, 10);
		appendTableValue(pipeInfo, pipe.getPurposes(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Time", font1, 10);
		appendTableValue(pipeInfo, pipe.getTime(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "PLR", font1, 10);
		appendTableValue(pipeInfo, pipe.getReference(), font2, 10, 1, border);
		tableWrap(pipeInfo, 8, 5); // 空行
		// 第二行
		border = new int[] { 1, 1, 0, 1 };
		appendTableTitle(pipeInfo, "Start MH", font1, 10);
		appendTableValue(pipeInfo, pipe.getSmanholeno(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Finish MH", font1, 10);
		appendTableValue(pipeInfo, pipe.getFmanholeno(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Weather", font1, 10);
		appendTableValue(pipeInfo, CameHelper.getCameText(pipe.getWeather(), "wea"), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Use", font1, 10);
		appendTableValue(pipeInfo, CameHelper.getCameText(pipe.getUses(), "use"), font2, 10, 1, border);

		border = new int[] { 0, 1, 0, 1 };
		appendTableTitle(pipeInfo, "Depth", font1, 10);
		if ("--".equals(pipe.getSdepth()))
			appendTableValue(pipeInfo, "N/A", font2, 10, 1, border);
		else
			appendTableValue(pipeInfo, pipe.getSdepth(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Depth", font1, 10);
		if ("--".equals(pipe.getFdepth()))
			appendTableValue(pipeInfo, "N/A", font2, 10, 1, border);
		else
			appendTableValue(pipeInfo, pipe.getFdepth(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Cleaned", font1, 10);
		appendTableValue(pipeInfo, pipe.getCleaned(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Direction", font1, 10);
		appendTableValue(pipeInfo, CameHelper.getCameText(pipe.getDire(), "dir"), font2, 10, 1, border);

		appendTableTitle(pipeInfo, "Cover Level", font1, 10);
		if ("--".equals(pipe.getScoverlevel()))
			appendTableValue(pipeInfo, "N/A", font2, 10, 1, border);
		else
			appendTableValue(pipeInfo, pipe.getScoverlevel(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Cover Level", font1, 10);
		if ("--".equals(pipe.getFcoverlevel()))
			appendTableValue(pipeInfo, "N/A", font2, 10, 1, border);
		else
			appendTableValue(pipeInfo, pipe.getFcoverlevel(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Score", font1, 10);
		appendTableValue(pipeInfo, pipe.getScore()[3] + "(ST)    " + pipe.getScore()[0] + "(SE)", font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Pipe Length", font1, 10);
		if (pipe.getPipelength() == 0)
			appendTableValue(pipeInfo, "Unknow", font2, 10, 1, border);
		else
			appendTableValue(pipeInfo, pipe.getPipelength() + "", font2, 10, 1, border);

		border = new int[] { 0, 1, 1, 1 };
		appendTableTitle(pipeInfo, "Invert Leve", font1, 10);
		if ("--".equals(pipe.getSinvertlevel()))
			appendTableValue(pipeInfo, "N/A", font2, 10, 1, border);
		else
			appendTableValue(pipeInfo, pipe.getSinvertlevel(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Invert Leve", font1, 10);
		if ("--".equals(pipe.getFinvertlevel()))
			appendTableValue(pipeInfo, "N/A", font2, 10, 1, border);
		else
			appendTableValue(pipeInfo, pipe.getFinvertlevel(), font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Grade", font1, 10);
		appendTableValue(pipeInfo, pipe.getGrade()[3] + "(ST)    " + pipe.getGrade()[0] + "(SE)", font2, 10, 1, border);
		appendTableTitle(pipeInfo, "Total Length", font1, 10);
		appendTableValue(pipeInfo, pipe.getTestlength() + "", font2, 10, 1, border);
		tableWrap(pipeInfo, 8, 5); // 空行
		// 第三行
		border = new int[] { 1, 1, 0, 1 };
		appendTableTitle(pipeInfo, "Building", font1, 10);
		appendTableValue(pipeInfo, pipe.getBuilding(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Size", font1, 10);
		if (StringUtils.isEmpty(pipe.getWsize()))
			appendTableValue(pipeInfo, pipe.getHsize(), font2, 10, 3, border);
		else
			appendTableValue(pipeInfo, pipe.getWsize() + "X" + pipe.getHsize(), font2, 10, 3, border);

		border = new int[] { 0, 1, 0, 1 };
		appendTableTitle(pipeInfo, "House No", font1, 10);
		appendTableValue(pipeInfo, pipe.getHousenum(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Shape", font1, 10);
		appendTableValue(pipeInfo, CameHelper.getCameText(pipe.getShape(), "sha"), font2, 10, 3, border);

		appendTableTitle(pipeInfo, "Road Name", font1, 10);
		appendTableValue(pipeInfo, pipe.getRoadname(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Material", font1, 10);
		appendTableValue(pipeInfo, CameHelper.getCameText(pipe.getMater(), "mat"), font2, 10, 3, border);

		appendTableTitle(pipeInfo, "District3", font1, 10);
		appendTableValue(pipeInfo, pipe.getDistrict3(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Lining", font1, 10);
		appendTableValue(pipeInfo, CameHelper.getCameText(pipe.getLining(), "lin"), font2, 10, 3, border);

		appendTableTitle(pipeInfo, "District2", font1, 10);
		appendTableValue(pipeInfo, pipe.getDistrict2(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Slope No", font1, 10);
		appendTableValue(pipeInfo, pipe.getSloperef(), font2, 10, 3, border);

		appendTableTitle(pipeInfo, "District1", font1, 10);
		appendTableValue(pipeInfo, pipe.getDistrict1(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Category", font1, 10);
		appendTableValue(pipeInfo, pipe.getCategory(), font2, 10, 3, border);

		appendTableTitle(pipeInfo, "Division", font1, 10);
		appendTableValue(pipeInfo, pipe.getDivision(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Comment", font1, 10);
		appendTableValue(pipeInfo, pipe.getComment(), font2, 10, 3, border);

		border = new int[] { 0, 1, 1, 1 };
		appendTableTitle(pipeInfo, "Area Code", font1, 10);
		appendTableValue(pipeInfo, pipe.getAreacode(), font2, 10, 3, border);
		appendTableTitle(pipeInfo, "Video No", font1, 10);
		appendTableValue(pipeInfo, pipe.getVideono(), font2, 10, 3, border);
		nestTable(report, pipeInfo, 1, 1, 4, 1, 1);
		return report;
	}

	private PdfPTable getPipeFormD(Pipe pipe) {
		Font font = getFont(8, 0, null);
		int[] widths = new int[] { 40, 110, 40, 60, 40, 110, 30, 60 };
		PdfPTable table = getTable(8, 480, widths);
		int[] border = new int[] { 1, 1, 0, 1 };
		appendTableTitle(table, "Road Name", font, 10);
		appendTableValue(table, pipe.getRoadname(), font, 10, 1, border);
		appendTableTitle(table, "Start MH", font, 10);
		appendTableValue(table, pipe.getSmanholeno(), font, 10, 1, border);
		appendTableTitle(table, "Size", font, 10);
		if (StringUtils.isEmpty(pipe.getWsize()))
			appendTableValue(table, pipe.getHsize(), font, 10, 1, border);
		else
			appendTableValue(table, pipe.getWsize() + "X" + pipe.getHsize(), font, 10, 1, border);
		appendTableTitle(table, "ID", font, 10);
		appendTableValue(table, pipe.getNo() + "", font, 10, 1, border);

		border = new int[] { 0, 1, 0, 1 };
		appendTableTitle(table, "", font, 10);
		appendTableValue(table, "", font, 10, 1, border);
		appendTableTitle(table, "", font, 10);
		appendTableValue(table, "", font, 10, 1, border);
		appendTableTitle(table, "Shape", font, 10);
		appendTableValue(table, CameHelper.getCameText(pipe.getShape(), "sha"), font, 10, 1, border);
		appendTableTitle(table, "", font, 10);
		appendTableValue(table, "", font, 10, 1, border);

		border = new int[] { 0, 1, 1, 1 };
		appendTableTitle(table, "Building", font, 10);
		appendTableValue(table, pipe.getBuilding(), font, 10, 1, border);
		appendTableTitle(table, "Finish MH", font, 10);
		appendTableValue(table, pipe.getFmanholeno(), font, 10, 1, border);
		appendTableTitle(table, "Material", font, 10);
		appendTableValue(table, CameHelper.getCameText(pipe.getMater(), "mat"), font, 10, 1, border);
		appendTableTitle(table, "PLR", font, 10);
		appendTableValue(table, pipe.getReference(), font, 10, 1, border);
		return table;
	}

	private PdfPTable getItemFormD(Pipe pipe, Item item) {
		BaseColor color = null;
		String type = item.getType3();
		Font font1 = getFont(8, 0, null);
		Font font2 = getFont(8, 0, null);
		if (item.getGrade() >= 4) {
			if ("Service".equals(type))
				color = new BaseColor(139, 69, 19);
			else
				color = new BaseColor(255, 60, 60);
			font2 = getFont(8, 1, color);
		} else {
			if ("Miscel-laneous".equals(type))
				color = new BaseColor(0, 0, 0);
			else if ("Node".equals(type) || "Repair Points".equals(type))
				color = new BaseColor(0, 128, 0);
			else if ("Service".equals(type))
				color = new BaseColor(139, 69, 19);
			else
				color = new BaseColor(255, 60, 60);
			font2 = getFont(8, 0, color);
		}
		String name = project.getName() + "/" + pipe.getWorkorder() + "/" + pipe.getNo();
		int[] widths = new int[] { 80, 240, 80, 160 };
		PdfPTable table = getTable(4, 360, widths);

		int[] border = new int[] { 1, 0, 0, 1 };
		TableValue(table, "Video No:", font1, 10, 1, border, 2);
		border = new int[] { 1, 0, 0, 0 };
		TableValue(table, pipe.getVideono(), font1, 10, 1, border, 0);
		TableValue(table, "Chainage:", font1, 10, 1, border, 2);
		border = new int[] { 1, 1, 0, 0 };
		TableValue(table, item.getDist() + " m", font1, 10, 1, border, 0);

		border = new int[] { 0, 0, 0, 1 };
		TableValue(table, "Photo Ref:", font1, 10, 1, border, 2);
		border = new int[] { 0, 1, 0, 0 };
		TableValue(table, name + "/" + item.getPhoto(), font1, 10, 3, border, 0);

		border = new int[] { 0, 0, 1, 1 };
		TableValue(table, "Observation:", font1, 10, 1, border, 2);
		border = new int[] { 0, 1, 1, 0 };
		TableValue(table, item.getDepict(), font2, 10, 3, border, 0);
		return table;
	}

	private PdfPTable getStandar(Project project) {
		String standar1 = "", standar2 = "";
		String standar = project.getStandard();
		if (standar != null && standar.indexOf("H") != -1)
			standar1 = "√";
		if (standar != null && standar.indexOf("M") != -1)
			standar2 = "√";
		String slope1 = "Y".equals(project.getSlope()) ? "√" : "";
		String slope2 = "N".equals(project.getSlope()) ? "√" : "";
		Font font1 = getFont(8, 1, null);
		Font font2 = getChineseFont(8, 0, null);
		int[] widths = new int[] { 60, 12, 60, 12, 140, 60, 12, 60, 12, 60 };
		PdfPTable standarTable = getTable(10, 500, widths);
		standarTable.addCell(getCell("Standard: ", font1, 1, 1, 0, 12, 2));
		standarTable.addCell(getCell(standar1, font2, 1, 1, 1, 12, 1));
		standarTable.addCell(getCell("HKCCEC 2009", font1, 1, 1, 0, 12, 1));
		standarTable.addCell(getCell(standar2, font2, 1, 1, 1, 12, 1));
		standarTable.addCell(getCell("MSCC 2004", font1, 1, 1, 0, 12, 0));
		standarTable.addCell(getCell("Slope: ", font1, 1, 1, 0, 12, 2));
		standarTable.addCell(getCell(slope1, font2, 1, 1, 1, 12, 1));
		standarTable.addCell(getCell("YES", font1, 1, 1, 0, 12, 0));
		standarTable.addCell(getCell(slope2, font2, 1, 1, 1, 12, 1));
		standarTable.addCell(getCell("NO", font1, 1, 1, 0, 12, 0));
		standarTable.addCell(getCell("", font1, 10, 1, 0, 8, 0)); // 插入间隔行

		standarTable.addCell(getCell("KEYCCTV Project Sequence No:001", font1, 5, 1, 0, 12, 0));
		standarTable.addCell(getCell("NOTE: ", font1, 1, 1, 0, 12, 2));
		standarTable.addCell(getCell("P--Peak; M--Mean; T--Total", font1, 4, 1, 0, 12, 0));
		return standarTable;
	}

	private PdfPTable getExplain() {
		int[] widths = new int[] { 3, 4, 2 };
		PdfPTable explain = getTable(3, 480, widths);
		BaseColor color = new BaseColor(255, 60, 60);
		Font font1 = getFont(7, 0, color);
		explain.addCell(getCell("•Structural Defects", font1, 1, 1, 0, 12, 0));
		font1 = getFont(7, 1, color);
		explain.addCell(getCell("•Structural Defects with Grade 4 or 5", font1, 1, 1, 0, 10, 0));
		color = new BaseColor(0, 108, 0);
		font1 = getFont(7, 0, color);
		explain.addCell(getCell("•Constructional Features", font1, 1, 1, 0, 10, 0));
		color = new BaseColor(139, 69, 19);
		font1 = getFont(7, 0, color);
		explain.addCell(getCell("•Service Defects", font1, 1, 1, 0, 10, 0));
		font1 = getFont(7, 1, color);
		explain.addCell(getCell("•Service Defects with Grade 4 or 5", font1, 1, 1, 0, 10, 0));
		color = new BaseColor(0, 0, 0);
		font1 = getFont(7, 0, color);
		explain.addCell(getCell("•Miscellaneous Features", font1, 1, 1, 0, 10, 0));
		return explain;
	}

	private List<String> getTextList(String text, PdfGraphics2D graphics) {
		List<String> list = new ArrayList<String>();
		FontMetrics fontMetrics = graphics.getFontMetrics();
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < text.length(); i++) {
			buffer.append(text.charAt(i));
			int length = fontMetrics.stringWidth(buffer.toString());
			if (length >= 280 || i == text.length() - 1) {
				if (i != text.length() - 1 && text.charAt(i + 1) == ',')
					buffer.append(text.charAt(++i));
				else if (i != text.length() - 1 && text.charAt(i + 1) == '.')
					buffer.append(text.charAt(++i));
				else if (i != text.length() - 1 && text.charAt(i + 1) == ':')
					buffer.append(text.charAt(++i));
				else if (i != text.length() - 1 && text.charAt(i + 1) == ' ')
					buffer.append(text.charAt(++i));
				else if (i != text.length() - 1 && text.charAt(i + 1) != ' ')
					buffer.append('-');
				list.add(buffer.toString());
				buffer = new StringBuffer("");
			}
		}
		return list;
	}
}
