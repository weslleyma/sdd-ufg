package br.ufg.inf.sdd_ufg.resource.utils;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.sdd_ufg.model.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultSetResponse<E extends Entity<E>> {

	@JsonProperty
	private List<E> data;
	
	@JsonProperty
	private Pagination pagination;
	
	public ResultSetResponse() {
		
	}
	
	public ResultSetResponse(List<E> data, Integer page) {
		this.pagination = new Pagination(page, data.size());
		this.data = new ArrayList<E>();
		
		int offset = pagination.getMaxPages().equals(1) ? 0 : (pagination.getPage() * Pagination.RESULTS_PER_PAGE);
		for (int i = offset; i < data.size() || i < pagination.getPageCount() ; i++) {
			this.data.add(data.get(i));
		}
		
	}
	
	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}
	
	public Pagination getPagination() {
		return pagination;
	}
	
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}
