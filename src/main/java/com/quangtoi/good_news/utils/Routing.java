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
    public static final String ADD_READING_TURN = BASE_URL + "/articles/{articleId}/user-articles";
    public static final String CRAWL_DATA_FROM_VNEXPRESS = BASE_URL + "/crawl-articles-vnExpress";

    //author
    public static final String AUTHORS = BASE_URL + "/authors";
    public static final String AUTHOR_BY_ID = BASE_URL + "/authors/{authorId}";
    public static final String CONFIRM_AUTHOR = BASE_URL + "/authors/{authorId}/confirm";
    public static final String AUTHOR_BY_USERID = BASE_URL + "/users/{userId}/authors";
    public static final String AUTHOR_ME = BASE_URL + "/authors/me";

    //bookmark
    public static final String BOOKMARKS = BASE_URL + "/bookmarks";
    public static final String BOOKMARK_OF_USER = BASE_URL + "/users/{userId}/bookmarks";
    public static final String BOOKMARK_OF_ME = BASE_URL + "/bookmarks/me";
    public static final String BOOKMARK_BY_ID = BASE_URL + "/bookmarks/{bookmarkId}";

    //category
    public static final String CATEGORIES = BASE_URL + "/categories";
    public static final String CATEGORY_BY_ID = BASE_URL + "/categories/{cateId}";

    //comment
    public static final String COMMENTS = BASE_URL + "/comments";
    public static final String COMMENT_BY_ID = BASE_URL + "/comments/{commentId}";
    public static final String COMMENT_BY_PARENT_ID = BASE_URL + "/comments/parent/{parentId}";
    public static final String COMMENTS_BY_ARTICLE = BASE_URL + "/articles/{articleId}/comments";
    public static final String COMMENTS_BY_ARTICLE_AND_PARENT_ID = BASE_URL + "/articles/{articleId}/comments/{parentId}/comments";
    public static final String COMMENT_BY_ARTICLE_AND_COMMENT_ID = BASE_URL + "/articles/{articleId}/comments/{commentId}";

    //notification
    public static final String NOTIFICATIONS = BASE_URL + "/notifications";
    public static final String NOTIFICATION_BY_ID = BASE_URL + "/notifications/{notifiId}";
    public static final String NOTIFICATION_BY_USER = BASE_URL + "/users/{userId}/notifications";

    //role
    public static final String ROLES = BASE_URL + "/roles";
    public static final String ROLE_BY_ID = BASE_URL + "/roles/{roleId}";
    public static final String ROLE_OF_USER = BASE_URL + "/users/{userId}/roles";

    //search
    public static final String SEARCH = BASE_URL + "/search";

    //statistic
    public static final String STAT_COUNT_ARTICLES = BASE_URL + "/statistic/count-all-articles";
    public static final String STAT_COUNT_USERS = BASE_URL + "/statistic/count-all-users";
    public static final String STAT_MOST_VIEWS_ARTICLE = BASE_URL + "/statistic/most-views-article";
    public static final String STAT_CATEGORY_ARTICLE = BASE_URL + "/statistic/article-category";
    public static final String STAT_ROLE_USER = BASE_URL + "/statistic/user";

    //tag
    public static final String TAGS = BASE_URL + "/tags";
    public static final String TAGS_OF_ARTICLE = BASE_URL + "/articles/{articleId}/tags";
    public static final String TAG_BY_ID = BASE_URL + "/tags/{tagId}";
    public static final String FOLLOW_TAG = BASE_URL + "/tags/{tagId}/follow";
    public static final String FOLLOW_TAG_STATUS = BASE_URL + "/tags/{tagId}/follow-status";

    //user
    public static final String USERS = BASE_URL + "/users";
    public static final String USER_BY_ID = BASE_URL + "/users/{userId}";
    public static final String ME = BASE_URL + "/users/me";
    public static final String USER_BY_EMAIL = BASE_URL + "/users/by-email";
    public static final String USER_BY_USERNAME = BASE_URL + "/users/by-username";
    public static final String FORGOT_PASSWORD = BASE_URL + "/users/forgot-password";
    public static final String RESET_PASSWORD = BASE_URL + "/users/reset-password";
    public static final String CHANGE_PASSWORD = BASE_URL + "/users/change-password";
    public static final String RECEIVE_NOTIFICATION = BASE_URL + "/users/register-receive-notification";
    public static final String CANCEL_RECEIVE_NOTIFICATION = BASE_URL + "/users/cancel-receive-notification";

}
