package com.quangtoi.good_news.dto.enumeration;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum CacheName {
    @FieldNameConstants.Include USER,
    @FieldNameConstants.Include SESSION;
}

