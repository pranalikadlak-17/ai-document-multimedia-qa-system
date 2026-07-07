package com.pranali.ai_doc_qa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranali.ai_doc_qa.model.ChatHistory;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
	List<ChatHistory> findByDocumentId(Long documentId);

}
