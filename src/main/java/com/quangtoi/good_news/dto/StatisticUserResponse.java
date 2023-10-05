package com.quangtoi.good_news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticUserResponse {
    private String roleName;
    private long user;
}
