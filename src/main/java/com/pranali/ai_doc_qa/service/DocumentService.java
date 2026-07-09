package com.pranali.ai_doc_qa.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pranali.ai_doc_qa.controller.DocumentResponse;
import com.pranali.ai_doc_qa.dto.ChatHistoryResponse;
import com.pranali.ai_doc_qa.dto.ChatRequest;
import com.pranali.ai_doc_qa.dto.ChatResponse;
import com.pranali.ai_doc_qa.model.ChatHistory;
import com.pranali.ai_doc_qa.model.Document;
import com.pranali.ai_doc_qa.repository.ChatHistoryRepository;
import com.pranali.ai_doc_qa.repository.DocumentRepository;

import jakarta.transaction.Transactional;

@Service
public class DocumentService {
	private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;
    private final PdfExtractorService pdfExtractorService;
    private final GeminiService geminiService;
    private final ChatHistoryRepository chatHistoryRepository;

    public DocumentService(DocumentRepository documentRepository,
                           FileStorageService fileStorageService,
                           PdfExtractorService pdfExtractorService,
                           GeminiService geminiService,
                          ChatHistoryRepository chatHistoryRepository) {

        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
        this.pdfExtractorService = pdfExtractorService;
        this.geminiService = geminiService;
        this.chatHistoryRepository = chatHistoryRepository;
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

        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setQuestion(request.getQuestion());
        chatHistory.setAnswer(answer);
        chatHistory.setAskedAt(LocalDateTime.now());
        chatHistory.setDocument(document);
        chatHistoryRepository.save(chatHistory);

        return new ChatResponse(answer);
    }
    
    public List<ChatHistoryResponse> getChatHistory(Long documentId) {

        return chatHistoryRepository.findByDocumentId(documentId)
                .stream()
                .map(chat -> new ChatHistoryResponse(
                        chat.getId(),
                        chat.getQuestion(),
                        chat.getAnswer(),
                        chat.getAskedAt()
                ))
                .collect(Collectors.toList());
    }
    
    public List<DocumentResponse> getAllDocuments() {

        return documentRepository.findAll()
                .stream()
                .map(document -> new DocumentResponse(
                        document.getId(),
                        document.getFileName(),
                        document.getFileType(),
                        document.getUploadedAt()
                ))
                .collect(Collectors.toList());
    }
    @Transactional
    public void deleteDocument(Long documentId) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        // Delete chat history first
        chatHistoryRepository.deleteByDocumentId(documentId);

        // Delete document
        documentRepository.delete(document);
    }
    }

