package com.quangtoi.good_news.utils;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {
    public static String getSiteURL(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        return url.replace(request.getServletPath(), "");
    }

    public static boolean isValidArticleStatus(String input) {
        for (EArticleStatus status : EArticleStatus.values()) {
            if (status.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
