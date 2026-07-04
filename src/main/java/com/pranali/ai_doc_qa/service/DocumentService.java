package com.pranali.ai_doc_qa.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pranali.ai_doc_qa.model.Document;
import com.pranali.ai_doc_qa.repository.DocumentRepository;

@Service
public class DocumentService {
	private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    private final PdfExtractorService pdfExtractorService;

    public DocumentService(DocumentRepository documentRepository,
                           FileStorageService fileStorageService,
                           PdfExtractorService pdfExtractorService) {

        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
        this.pdfExtractorService = pdfExtractorService;
    }

    public Document uploadDocument(MultipartFile file) throws IOException {

        String storedPath = fileStorageService.store(file);

        String extractedText = "";

        if (file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            extractedText = pdfExtractorService.extractText(storedPath);
        }

        Document document = new Document();

        document.setFileName(file.getOriginalFilename());
        document.setFilePath(storedPath);
        document.setExtractedText(extractedText);
        document.setUploadedAt(LocalDateTime.now());
        document.setFileType("PDF");

        return documentRepository.save(document);
    }
}
