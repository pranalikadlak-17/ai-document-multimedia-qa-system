package com.pranali.ai_doc_qa.dto;

import java.time.LocalDateTime;

public class ChatHistoryResponse {
	
	private Long id;
    private String question;
    private String answer;
    private LocalDateTime askedAt;

    public ChatHistoryResponse(Long id, String question, String answer, LocalDateTime askedAt) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.askedAt = askedAt;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public LocalDateTime getAskedAt() {
        return askedAt;
    }

}
