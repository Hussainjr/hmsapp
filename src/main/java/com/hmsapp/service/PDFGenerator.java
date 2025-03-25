package com.hmsapp.service;

import com.hmsapp.entity.Booking;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

@Service
public class PDFGenerator {

    public void generatePdf(String path, Booking booking){

        try(PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);)
            {
                document.add(new Paragraph("Hello, this is a simple test"));

                //define table column widths(3 columns)
                float[] columnWidths = {10, 20, 15};
                Table table = new Table(columnWidths);

                // Add table headers
                table.addHeaderCell("Name");
                table.addHeaderCell("Property");
                table.addHeaderCell("Mobile");

                // Add table data
                table.addCell(booking.getGuestName());
                table.addCell(booking.getProperty().getName()); // Assuming Property has a getName() method
                table.addCell(booking.getMobile());

                // Add table to the document
                document.add(table);

                System.out.println("PDF created successfully!");

            } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
