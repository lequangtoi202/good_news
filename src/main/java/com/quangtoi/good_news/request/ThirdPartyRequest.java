package com.quangtoi.good_news.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThirdPartyRequest {
    private String username;
    private String fullName;
    private String email;
    private String avatar;
}
