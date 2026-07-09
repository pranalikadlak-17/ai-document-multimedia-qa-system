package com.pranali.ai_doc_qa.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pranali.ai_doc_qa.dto.ChatRequest;
import com.pranali.ai_doc_qa.dto.ChatResponse;
import com.pranali.ai_doc_qa.dto.UploadResponse;
import com.pranali.ai_doc_qa.model.ChatHistory;
import com.pranali.ai_doc_qa.model.Document;
import com.pranali.ai_doc_qa.service.DocumentService;
@RestController
@RequestMapping("/api/documents")
public class DocumentController {
	
	private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file) throws IOException {

        Document document = documentService.uploadDocument(file);

        UploadResponse response = new UploadResponse(
                document.getId(),
                document.getFileName(),
                document.getFileType(),
                "File uploaded successfully");

        return ResponseEntity.ok(response);
    }
    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> askQuestion(
            @RequestBody ChatRequest request) {

        ChatResponse response = documentService.askQuestion(request);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{documentId}/history")
    public ResponseEntity<List<ChatHistory>> getChatHistory(
            @PathVariable Long documentId) {

        List<ChatHistory> history = documentService.getChatHistory(documentId);

        return ResponseEntity.ok(history);
    }
}
