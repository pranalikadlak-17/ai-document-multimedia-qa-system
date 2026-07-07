package com.pranali.ai_doc_qa.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatHistory {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String question;

	    @Column(columnDefinition = "TEXT")
	    private String answer;

	    private LocalDateTime askedAt;

	    @ManyToOne
	    @JoinColumn(name = "document_id")
	    private Document document;

	    public ChatHistory() {
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getQuestion() {
	        return question;
	    }

	    public void setQuestion(String question) {
	        this.question = question;
	    }

	    public String getAnswer() {
	        return answer;
	    }

	    public void setAnswer(String answer) {
	        this.answer = answer;
	    }

	    public LocalDateTime getAskedAt() {
	        return askedAt;
	    }

	    public void setAskedAt(LocalDateTime askedAt) {
	        this.askedAt = askedAt;
	    }

	    public Document getDocument() {
	        return document;
	    }

	    public void setDocument(Document document) {
	        this.document = document;
	    }

}
