package com.pranali.ai_doc_qa.dto;
import java.util.List;
public class GeminiResponse {

	
	 private List<Candidate> candidates;

	    public GeminiResponse() {
	    }

	    public List<Candidate> getCandidates() {
	        return candidates;
	    }

	    public void setCandidates(List<Candidate> candidates) {
	        this.candidates = candidates;
	    }
}
