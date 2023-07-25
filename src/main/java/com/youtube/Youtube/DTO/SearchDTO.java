package com.youtube.Youtube.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDTO {
    private String searchKeyword ;

    public SearchDTO(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public SearchDTO() {
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}
