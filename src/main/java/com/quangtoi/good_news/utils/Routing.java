package com.quangtoi.good_news.utils;

public final class Routing {
    public static final String BASE_URL = "/api/v1";
    //auth
    public static final String LOGIN = BASE_URL + "/auth/login";
    public static final String REGISTER = BASE_URL + "/auth/register";
    //article
    public static final String ARTICLES = BASE_URL + "/articles";
    public static final String TOP3_ARTICLES = BASE_URL + "/articles/top3";
    public static final String ARTICLES_STATUS = BASE_URL + "/articles-status";
    public static final String NEWEST_ARTICLES_BY_CATEGORY = BASE_URL + "/categories/{cateId}/articles/newest";
    public static final String ARTICLES_BY_AUTHOR = BASE_URL + "/authors/{authorId}/articles";
    public static final String ARTICLES_BY_TAG = BASE_URL + "/tags/{tagId}/articles";
    public static final String ARTICLES_BY_CATEGORY = BASE_URL + "/categories/{cateId}/articles";
    public static final String ARTICLE_BY_ID = BASE_URL + "/articles/{articleId}";
    public static final String ADD_TAG_TO_ARTICLE = BASE_URL + "/articles/add-tag";
    public static final String DELETE_TAG_TO_ARTICLE = BASE_URL + "/articles/delete-tag";
    public static final String UPDATE_STATUS_ARTICLE = BASE_URL + "/articles/{articleId}/update-status";
    public static final String ADD_READING_TURN = BASE_URL + "/articles/{articleId}/user-acticles";
    public static final String CRAWL_DATA_FROM_VNEXPRESS = BASE_URL + "/crawl-articles-vnExpress";

    //author
    public static final String AUTHORS = BASE_URL + "/authors";
    public static final String AUTHOR_BY_ID = BASE_URL + "/authors/{authorId}";
    public static final String CONFIRM_AUTHOR = BASE_URL + "/authors/{authorId}/confirm";
    public static final String AUTHOR_BY_USERID = BASE_URL + "/users/{userId}/authors";
    public static final String AUTHOR_ME = BASE_URL + "/authors/me";

    //bookmark
    public static final String BOOKMARKS = "/bookmarks";
    public static final String BOOKMARK_OF_USER = "/users/{userId}/bookmarks";
    public static final String BOOKMARK_OF_ME = "/bookmarks/me";
    public static final String BOOKMARK_BY_ID = "/bookmarks/{bookmarkId}";

    //category
    public static final String CATEGORIES = "/categories";
    public static final String CATEGORY_BY_ID = "/categories/{cateId}";

    //comment
    public static final String COMMENTS = "/comments";
    public static final String COMMENT_BY_ID = "/comments/{commentId}";
    public static final String COMMENT_BY_PARENT_ID = "/comments/parent/{parentId}";
    public static final String COMMENTS_BY_ARTICLE = "/articles/{articleId}/comments";
    public static final String COMMENTS_BY_ARTICLE_AND_PARENT_ID = "/articles/{articleId}/comments/{parentId}/comments";
    public static final String COMMENT_BY_ARTICLE_AND_COMMENT_ID = "/articles/{articleId}/comments/{commentId}";

    //notification
    public static final String NOTIFICATIONS = "/notifications";
    public static final String NOTIFICATION_BY_ID = "/notifications/{notifiId}";
    public static final String NOTIFICATION_BY_USER = "/users/{userId}/notifications";

    //role
    public static final String ROLES = "/roles";
    public static final String ROLE_BY_ID = "/roles/{roleId}";
    public static final String ROLE_OF_USER = "/users/{userId}/roles";

    //search
    public static final String SEARCH = "/search";

    //statistic
    public static final String STAT_COUNT_ARTICLES = "/statistic/count-all-articles";
    public static final String STAT_COUNT_USERS = "/statistic/count-all-users";
    public static final String STAT_MOST_VIEWS_ARTICLE= "/statistic/most-views-article";
    public static final String STAT_CATEGORY_ARTICLE= "/statistic/article-category";
    public static final String STAT_ROLE_USER= "/statistic/user";

    //tag
    public static final String TAGS= "/tags";
    public static final String TAGS_OF_ARTICLE= "/articles/{articleId}/tags";
    public static final String TAG_BY_ID= "/tags/{tagId}";
    public static final String FOLLOW_TAG= "/tags/{tagId}/follow";
    public static final String FOLLOW_TAG_STATUS= "/tags/{tagId}/follow-status";

    //user
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{userId}";
    public static final String ME = "/users/me";
    public static final String USER_BY_EMAIL = "/users/by-email";
    public static final String USER_BY_USERNAME = "/users/by-username";
    public static final String FORGOT_PASSWORD = "/users/forgot-password";
    public static final String RESET_PASSWORD = "/users/reset-password";
    public static final String CHANGE_PASSWORD = "/users/change-password";
    public static final String RECEIVE_NOTIFICATION = "/users/register-receive-notification";
    public static final String CANCEL_RECEIVE_NOTIFICATION = "/users/cancel-receive-notification";

}
