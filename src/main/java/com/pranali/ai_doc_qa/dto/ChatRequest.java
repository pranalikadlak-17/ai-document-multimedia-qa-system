package com.pranali.ai_doc_qa.dto;

public class ChatRequest {
	private Long documentId;
    private String question;

    public ChatRequest() {
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
