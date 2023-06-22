package com.gimeast.mreview.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepositoryCustom {
    Page<Object[]> getSearchPage(String keyword, Pageable pageable);
}
