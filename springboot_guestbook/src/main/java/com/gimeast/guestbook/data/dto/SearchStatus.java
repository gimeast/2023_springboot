package com.gimeast.guestbook.data.dto;

import lombok.Data;

@Data
public class SearchStatus {
    private String type;
    private String keyword;

    public SearchStatus(String type, String keyword) {
        this.type = type;
        this.keyword = keyword;
    }
}
