package com.pranali.ai_doc_qa.service;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.Loader;



@Service

public class PdfExtractorService {
	
	public String extractText(String filePath) throws IOException {

        File file = new File(filePath);

        try (PDDocument document = Loader.loadPDF(file)) {

            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);

        }
    }

}
