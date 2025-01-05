package com.controller.ExportFile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.BisagN.validation.DateWithTimeStampController;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class DownloadPdf extends AbstractPdfView {
	int totalRecords=0;
	String Type = "",Heading = "",username = "",hideColIndex="",url_name1="";
	List<String> TH;	
	List<Map<String, Object>> TH1 = new ArrayList<Map<String, Object>>();
	
	public DownloadPdf(String url_name,String Type,List<String> TH,String Heading,String username,List<Map<String, Object>> TH1,String hideColIndex){
		System.out.println("hi--");
		this.Type = Type;
		this.TH = TH;
		this.Heading = Heading.toUpperCase();
		this.username = username;
		this.TH1 = TH1;
		this.hideColIndex = hideColIndex;
		this.url_name1 = url_name;
		
	}
	
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		document.open();
		Image logo = null;
		try {
			@SuppressWarnings("deprecation")   
			String dgis_logo =  request.getRealPath("/")+"admin"+File.separator+"js"+File.separator+"img"+File.separator+"brahma2.png";
			System.err.println("-----dgis_logo-----------"+dgis_logo);
			logo = Image.getInstance(dgis_logo);
		} catch (BadElementException e) {
			e.getMessage();
		} catch (MalformedURLException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
//		logo.setAlignment(Image.MIDDLE);
//		logo.scaleAbsoluteHeight(20);
//		logo.scaleAbsoluteWidth(20);
//		logo.scalePercent(52);
//		Chunk chunk = new Chunk(logo, 0, -4);

		Image logo2 = null;
		try {
			@SuppressWarnings("deprecation") 
			String indian_Army =  request.getRealPath("/")+"admin"+File.separator+"js"+File.separator+"img"+File.separator+"brahma1.png";
			logo2 = Image.getInstance(indian_Army);
		} catch (BadElementException e) {
			e.getMessage();
		} catch (MalformedURLException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		}
//		logo2.setAlignment(Image.RIGHT);
//		logo2.scaleAbsoluteHeight(20);
//		logo2.scaleAbsoluteWidth(20);
//		logo2.scalePercent(35);  //logo2.scalePercent(22);
//		Chunk chunk2 = new Chunk(logo2, 90, -4);
		
		Phrase p = new Phrase();
		PdfPTable headtable = new PdfPTable(3);
		headtable.setWidthPercentage(100);
		
		try {
			headtable.setWidths(new int[] {2, 6, 2 });
		} catch (DocumentException e) {
			e.getMessage();
		}

		@SuppressWarnings("deprecation")
		String indian_Army1 =  request.getRealPath("/")+"admin"+File.separator+"js"+File.separator+"arial"+File.separator+"arial.ttf";
		FontFactory.register(indian_Army1);
		
		headtable.setSpacingAfter(10f);
		PdfPCell cell1 = new PdfPCell();
		cell1.setBorder(Rectangle.NO_BORDER);
		PdfPCell cell2 = new PdfPCell();
		cell2.setBorder(Rectangle.NO_BORDER);
		PdfPCell cell3 = new PdfPCell();
		cell3.setBorder(Rectangle.NO_BORDER);

//		cell1.addElement(chunk);
		//Font fontTableHeading = FontFactory.getFont(FontFactory.TIMES_BOLD, 20);  //16 
		Font fontTableHeading = FontFactory.getFont("Arial", 20,Font.BOLD);
		Paragraph pr = new Paragraph("APPRAISAL CONFIDENTIAL REPORTS",fontTableHeading);
		pr.setAlignment(Element.ALIGN_CENTER);
		
		//Font fontTableHeading23 = FontFactory.getFont(FontFactory.TIMES_BOLD, 13); 
		Font fontTableHeading23 =	FontFactory.getFont("Arial", 13,Font.BOLD);
		Paragraph pr23 = new Paragraph("",fontTableHeading23);
		pr23.setAlignment(Element.ALIGN_CENTER);
		
		//Font fontTableHeading22 = FontFactory.getFont(FontFactory.TIMES_BOLD, 11); 
		Font fontTableHeading22 = FontFactory.getFont("Arial", 11,Font.BOLD);
		Paragraph pr22 = new Paragraph("",fontTableHeading22);
		pr22.setAlignment(Element.ALIGN_CENTER);
		
		//Font fontTableHeading2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14); 
		Font fontTableHeading2 = FontFactory.getFont("Arial", 14,Font.BOLD);
		Paragraph pr2 = new Paragraph(Heading.toUpperCase(),fontTableHeading2);
		pr2.setAlignment(Element.ALIGN_CENTER);
		cell2.addElement(pr);
		cell2.addElement(pr23);
		cell2.addElement(pr22);
		cell2.addElement(pr2);		
		
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell3.setVerticalAlignment(Element.ALIGN_RIGHT);
		cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//		cell3.addElement(chunk2);

		headtable.addCell(cell1);
		headtable.addCell(cell2);
		headtable.addCell(cell3);
		p.add(headtable);		
				
		HeaderFooter header = new HeaderFooter(p, false);
		header.setBorder(Rectangle.BOTTOM);
		header.setAlignment(Element.ALIGN_CENTER);
		document.setHeader(header);				
		
		Phrase p1 = new Phrase();		
		HeaderFooter footer = new HeaderFooter(p1, false);
		footer.setAlignment(Element.ALIGN_CENTER);
		footer.setBorder(Rectangle.TOP);
		document.setFooter(footer);
		
		document.setPageCount(1);
		
		if(Type.equals("L")) {
			document.setPageSize(PageSize.A3.rotate()); // set document landscape
		}
		super.buildPdfMetadata(model, document, request);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter arg2,HttpServletRequest request, HttpServletResponse response) throws Exception {	
		DateWithTimeStampController datetimestamp = new DateWithTimeStampController();
		String file_name = datetimestamp.currentDateWithTimeStampString();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\""+file_name+".pdf\"");
		
		@SuppressWarnings("unchecked")
		ArrayList<List<String>> aList = (ArrayList<List<String>>) model.get("userList"); 
		totalRecords=aList.size();		
		List<String> l1 = aList.get(0);
		int colunmSize = l1.size(); // get Colunm Size
		if(!hideColIndex.equals(""))
			colunmSize= colunmSize - 1;
		
		PdfPTable table = new PdfPTable(colunmSize);
		
		PdfPCell blankRow = new PdfPCell(new Phrase("\n"));
		blankRow.setColspan(colunmSize);
		blankRow.setBorder(Rectangle.NO_BORDER);
		table.addCell(blankRow);
		
		Paragraph p;

		table.setWidthPercentage(100);
		Integer arr[] = {1};  
		ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(arr));  
		for(int h2=0;h2<colunmSize - 1;h2++) {
			arrayList.add(3);  
			//System.out.println("arr================="+arrayList);
		}
		 
        	int[] primitive = arrayList.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();
		
		//table.setWidths(primitive);
		
        table.setWidthPercentage(100);
		if(url_name1.equals("pdfTAPAReport_url")) {
			table.setWidths(new int[] {2,4,4}); 
		} 
		if(url_name1.equals("pdfAreaWise_url")) {
			table.setWidths(new int[] {1,4,4,4,3,2,2}); 
		}
		if(url_name1.equals("pdfEAFSReport_url")) {
			table.setWidths(new int[] {1,2,2,2,4,2,2,2,2,2}); 
		} 
		if(url_name1.equals("pdfESSIGRReport_url")) {
			table.setWidths(new int[] {1,2,2,2,2,2,2,2,2,2,2,2,2}); 
		} 
		if(url_name1.equals("pdfListofCancelledPostingOrderReport_Url")) {
			table.setWidths(new int[] {1,2,2,2,2,2,2,2,2,2,2,2,2,2}); 
		} 
		
		//table.setWidths(new int[] {1,3,3,3,3,3,3 });
		//table.setWidths(new int[] {1, 3, 3, 3 , 3,3,3 });
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		//Font fontTableHeading = FontFactory.getFont(FontFactory.TIMES_BOLD, 12); 
		Font fontTableHeading =  FontFactory.getFont("Arial", 10,Font.BOLD);
		for(int h2=0;h2<TH1.size();h2++) {
			Paragraph pth1 = new Paragraph(TH1.get(h2).get("header").toString(),fontTableHeading);
			
			PdfPCell cellspan = new PdfPCell(pth1);
			cellspan.setVerticalAlignment(Element.ALIGN_CENTER);
			cellspan.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cellspan.setNoWrap(false);			
			
			if(TH1.get(h2).get("spanType").toString().equals("C")) {
				cellspan.setColspan(Integer.parseInt(TH1.get(h2).get("size").toString()));
			}
			else if(TH1.get(h2).get("spanType").toString().equals("R")) {
				cellspan.setRowspan(Integer.parseInt(TH1.get(h2).get("size").toString()));
			}
			table.addCell(cellspan);
		}
		
//		Font fontTableHeading = FontFactory.getFont(FontFactory.TIMES_BOLD, 10); 
//		for(int h=0;h<TH.size();h++) {
//			  p = new Paragraph(TH.get(h),fontTableHeading);
//			  p.setAlignment(Element.ALIGN_CENTER); 
//			  table.addCell(p); 
//		}	 
		
		table.setHeaderRows(1); // table first row will be repeated in all pages
		
		//Font fontTableValue = FontFactory.getFont(FontFactory.TIMES, 10);
		Font fontTableValue = FontFactory.getFont("Arial", 10); 
		
		for (int i = 0; i < aList.size(); i++) {
			List<String> l = aList.get(i);
			for (int j = 0; j < l.size(); j++) {
				if (hideColIndex.equals("")) {
					try {
						if (!l.get(j).toString().isEmpty() || !l.get(j).toString().equals("null")
								|| !l.get(j).toString().equals("undefined"))
							p = new Paragraph(l.get(j).replace("<br>", ", "), fontTableValue);
						else
							p = new Paragraph(l.get(j), fontTableValue);
					} catch (Exception e) {
						p = new Paragraph(l.get(j), fontTableValue);
					}
					 /// Table Head
					PdfPCell cellspan = new PdfPCell(p);
					if(j == 1 || j == 3 || j == 4 || j == 5 || j == 6 || j == 7 || j == 8 || j == 13 || j == 14 || j == 15 || j == 16 || j == 17 || j == 18) // Name, Present Unit, Task Force, Project
					{
						//System.err.println("Table Head==if=else="+j);
						cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					else if(j == 9 || j == 10 || j == 11 || j == 12)
					{
						//System.err.println("Table Head=if=if="+j);
						cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					else 
					{
						//System.err.println("Table Head=if=if="+j);
						cellspan.setHorizontalAlignment(Element.ALIGN_CENTER);
					}					
					table.addCell(cellspan);
					
				} else {
					if (Integer.parseInt(hideColIndex) == j) {
					} else {
						try {
							if (!l.get(j).toString().isEmpty() || !l.get(j).toString().equals("null")
									|| !l.get(j).toString().equals("undefined"))
								p = new Paragraph(l.get(j).replace("<br>", ", "), fontTableValue);
							else
								p = new Paragraph(l.get(j), fontTableValue);
						} catch (Exception e) {
							p = new Paragraph(l.get(j), fontTableValue);
						}
						/// Table Head
						PdfPCell cellspan = new PdfPCell(p);
						if(j == 1 || j == 3 || j == 4 || j == 5 || j == 6 || j == 7 || j == 8 || j == 13 || j == 14 || j == 15 || j == 16 || j == 17 || j == 18) // Name, Present Unit, Task Force, Project
						{
							//System.err.println("Table Head==if=else="+j);
							cellspan.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
						else if(j == 9 || j == 10 || j == 11 || j == 12)
						{
							//System.err.println("Table Head=if=if="+j);
							cellspan.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else 
						{
							//System.err.println("Table Head=if=if="+j);
							cellspan.setHorizontalAlignment(Element.ALIGN_CENTER);
						}						
						table.addCell(cellspan);
					}
				}
			}
		}
		
		PageNumeration event = new PageNumeration(arg2);
		arg2.setPageEvent(event);
		document.setPageCount(1);
		
		PdfPTable table1 = new PdfPTable(1);
		
		table1.setWidthPercentage(100);
		table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell1;
		cell1 = new PdfPCell();
		cell1.setBorder(Rectangle.NO_BORDER);		
		cell1.addElement(table);
		table1.addCell(cell1);		
		
		table1.setTableEvent(new ImageBackgroundEvent(request));  // use for watermark
		
		document.add(table1);		
		super.buildPdfMetadata(model, document, request);
	}
	
	class PageNumeration extends PdfPageEventHelper {
		PdfTemplate total;
		PdfTemplate total1;

		public PageNumeration(PdfWriter writer) {
			try {
				total = writer.getDirectContent().createTemplate(30, 16);
				total1 = writer.getDirectContent().createTemplate(30, 16);
			} catch (Exception e) {
				e.getMessage();
			}
		}

		public void onOpenDocument(PdfWriter writer, Document document) {
			// total = writer.getDirectContent().createTemplate(30, 12);
		}

		public void onEndPage(PdfWriter writer, Document document) {
			PdfPTable table = new PdfPTable(4);
			try {
				table.setWidths(new int[] { 8, 19, 21, 2 });  
				table.setTotalWidth(document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
				table.setLockedWidth(true);
				table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				table.addCell("TOTAL RECORDS :");  //table.addCell("TOTAL SHEET :");
				
				
				PdfPCell cell1 = new PdfPCell(Image.getInstance(total1));
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
				Paragraph prTR = new Paragraph(new Paragraph(String.valueOf(totalRecords)));
				table.addCell(prTR);
				//table.addCell(cell1);
								
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(String.format("SHEET %d of", writer.getPageNumber()));
				
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				cell.setBorder(Rectangle.NO_BORDER);				
				table.addCell(cell);
				
				table.writeSelectedRows(0, -1, document.leftMargin(), document.topMargin() + 700, writer.getDirectContent());
			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}

		String tr = String.valueOf(totalRecords);
		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
			//ColumnText.showTextAligned(total1, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
			ColumnText.showTextAligned(total1, Element.ALIGN_LEFT, new Phrase(String.valueOf(tr)), 2, 2, 0);
		}	}
	
	int page = 1;
	class ImageBackgroundEvent implements PdfPTableEvent {
		protected Image image;
		HttpServletRequest request;
		
		ImageBackgroundEvent(HttpServletRequest request){
			this.request = request;
		}
		
		public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart,PdfContentByte[] canvases) {
			String ip = "";
			if (request != null) {
		        ip = request.getHeader("X-FORWARDED-FOR");
		        if (ip == null || "".equals(ip)) {
		            ip = request.getRemoteAddr();
		        }
		    }
			Date now = new Date();
			String dateString = new SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss", Locale.ENGLISH).format(now);
			String watermark = " GENERATED BY GREF RECORD" ;
			
			Image img = null;
			BufferedImage bufferedImage = new BufferedImage((int) table.getTotalWidth(), 30,BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = bufferedImage.getGraphics();
			graphics.setColor(Color.white);
			graphics.setFont(new java.awt.Font("Arial Black", Font.NORMAL, 25));
			graphics.drawString(watermark+watermark,0, 20);
			try {
				try {
					img = Image.getInstance(bufferedImage, null);
				} catch (IOException e) {
					e.getMessage();
				}
			} catch (BadElementException e) {
				e.getMessage();
			}
			this.image = img;
			
			try {
				PdfContentByte cb = canvases[PdfPTable.BACKGROUNDCANVAS];

				int tableWidth = (int) table.getTotalWidth();
				int first = 0;
				if (tableWidth == 523) {
					first = 300;
				}
				if (tableWidth == 770) {
					first = 250;
				}

				int last = first - (int) table.getRowHeight(0);
				/*
				 * Phrase p = new Phrase(); p.add(String.valueOf(page)); float width =
				 * ColumnText.getWidth( p ); ColumnText.showTextAligned(cb,
				 * PdfContentByte.ALIGN_RIGHT, p, cb.getPdfDocument( ).right( ) - width,
				 * cb.getPdfDocument( ).top( ) + 9, 0); page += 1;
				 */
		        if (first > last) {
		        	//image.setRotation(45);
		        	//image.setAbsolutePosition(first, last)
					image.setAbsolutePosition(100, last);
					image.setRotationDegrees(35);
					
					cb.addImage(image, false);
					first -= 30;
				}
			} catch (DocumentException e) {
				throw new ExceptionConverter(e);
			}
		}
	}
}