package br.ufg.inf.sdd_ufg.resource.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
	@JsonIgnore
	public static final Integer RESULTS_PER_PAGE = 20;
	
	@JsonIgnore
	private Integer page;
	
	@JsonIgnore
	private Integer totalCount;
	
	public Pagination() {
        // Jackson deserialization
    }
	
	public Pagination(Integer page, Integer totalCount) {
		setPage(page);
		setTotalCount(totalCount);;
	}

	@JsonProperty("page")
	public int getPage() {
		if(getMaxPages() < page) {
			page = getMaxPages();
		}
		return page;
	}

	void setPage(int page) {
		this.page = page;
	}

	@JsonProperty("max_pages")
	public Integer getMaxPages() {
		Integer pages = (totalCount / RESULTS_PER_PAGE);
		if(pages <= 0) {
			pages = 1;
		}
		return (int) Math.floor(pages);
	}
	
	@JsonProperty("page_count")
	public Integer getPageCount() {
		if (page.equals(getMaxPages())) {
			return totalCount % RESULTS_PER_PAGE;
		}
		return RESULTS_PER_PAGE;
	}
	
	@JsonProperty("total_count")
	public Integer getTotalCount() {
		return totalCount;
	}

	void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
