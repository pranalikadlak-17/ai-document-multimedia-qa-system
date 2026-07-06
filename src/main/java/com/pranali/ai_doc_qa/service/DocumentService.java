package com.pranali.ai_doc_qa.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pranali.ai_doc_qa.model.Document;
import com.pranali.ai_doc_qa.repository.DocumentRepository;
import com.pranali.ai_doc_qa.dto.ChatRequest;
import com.pranali.ai_doc_qa.dto.ChatResponse;

@Service
public class DocumentService {
	private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    private final PdfExtractorService pdfExtractorService;
    private final GeminiService geminiService;

    public DocumentService(DocumentRepository documentRepository,
                           FileStorageService fileStorageService,
                           PdfExtractorService pdfExtractorService,
                           GeminiService geminiService) {

        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
        this.pdfExtractorService = pdfExtractorService;
        this.geminiService = geminiService;
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
    public ChatResponse askQuestion(ChatRequest request) {

        Document document = documentRepository.findById(request.getDocumentId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        String prompt = """
                You are an AI assistant.

                Answer the question only using the document below.

                Document:
                %s

                Question:
                %s
                """.formatted(
                        document.getExtractedText(),
                        request.getQuestion());

        String answer = geminiService.askGemini(prompt);

        return new ChatResponse(answer);
    }
}
