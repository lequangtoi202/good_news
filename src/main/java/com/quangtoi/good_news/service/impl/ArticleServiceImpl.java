package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.ArticleData;
import com.quangtoi.good_news.dto.ArticleDto;
import com.quangtoi.good_news.exception.ForbiddenException;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.*;
import com.quangtoi.good_news.repository.*;
import com.quangtoi.good_news.service.ArticleService;
import com.quangtoi.good_news.service.ImageService;
import com.quangtoi.good_news.dto.enumeration.EArticleStatus;
import com.quangtoi.good_news.dto.enumeration.ERoleName;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserArticleRepository userArticleRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public Article addArticle(ArticleDto articleDto, User currentUser, MultipartFile image) {
        Category category = categoryRepository.findById(articleDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", articleDto.getCategoryId()));
        Authors authors = authorRepository.findByUser(currentUser);
        if (authors == null) {
            throw new ResourceNotFoundException("Author", "userId", currentUser.getId());
        }
        Set<String> allowedRoles = Set.of(ERoleName.ROLE_ADMIN.toString(), ERoleName.ROLE_AUTHOR.toString());
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .map(Role::getName)
                .anyMatch(allowedRoles::contains);
        if (hasRoleAdminOrAuthor) {
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            Article article = Article.builder()
                    .active(true)
                    .authors(authors)
                    .category(category)
                    .content(articleDto.getContent())
                    .createdAt(now)
                    .source(articleDto.getSource())
                    .status(EArticleStatus.DRAFT.toString())
                    .title(articleDto.getTitle())
                    .updatedAt(now)
                    .image(image == null ? null : imageService.uploadImage(image))
                    .build();
            return articleRepository.save(article);
        } else {
            throw new ForbiddenException("Insufficient privilege");
        }
    }

    @Override
    public Article updateArticle(ArticleDto articleDto, Long articleId, User currentUser) {
        Category category = categoryRepository.findById(articleDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", articleDto.getCategoryId()));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals(ERoleName.ROLE_ADMIN.toString()) || r.getName().equals(ERoleName.ROLE_AUTHOR.toString()));
        if (hasRoleAdminOrAuthor) {
            mapper.map(articleDto, article);
            article.setCategory(category);
            article.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return articleRepository.save(article);
        }
        throw new BadCredentialsException("You don not have permission to update article");
    }

    @Override
    public Article updateStatusArticle(String status, Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals(ERoleName.ROLE_ADMIN.toString()) || r.getName().equals(ERoleName.ROLE_APPROVE.toString()));
        if (hasRoleAdminOrAuthor) {
            article.setStatus(status);
            article.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return articleRepository.save(article);
        }
        throw new ForbiddenException("Insufficient privilege");
    }

    @Override
    public void deleteArticle(Long articleId, User currentUser) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdminOrAuthor = roles.stream()
                .anyMatch(r -> r.getName().equals(ERoleName.ROLE_ADMIN.toString()) || r.getName().equals(ERoleName.ROLE_APPROVE.toString()));
        if (hasRoleAdminOrAuthor) {
            article.setActive(false);
            article.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            articleRepository.save(article);
        } else {
            throw new ForbiddenException("Insufficient privilege");
        }
    }

    @Override
    public boolean addTagToArticle(Long articleId, Long tagId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        ArticleTag articleTag = ArticleTag.builder()
                .articleId(article.getId())
                .tagId(tag.getId())
                .build();
        articleTagRepository.save(articleTag);
        return true;
    }

    @Override
    public boolean deleteTagFromArticle(Long articleId, Long tagId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        ArticleTag articleTag = articleTagRepository.findByArticleIdAndTagId(article.getId(), tag.getId());
        articleTagRepository.delete(articleTag);
        return true;
    }

    @Override
    public List<Article> getLimitNewestArticles(Long cateId, int limit) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
        Pageable pageable = PageRequest.of(0, limit);
        return articleRepository.findLimitNewestArticlesByCate(category.getId(), pageable);
    }

    @Override
    public List<Article> getAllArticlesWithStatus(boolean active, String type) {
        return articleRepository.findAllByActiveAndStatus(active, type);
    }

    @Override
    public Page<Article> getAllArticles(Pageable pageable, boolean active) {
        return articleRepository.findAllByActive(active, pageable);
    }

    @Override
    public List<Article> getAllArticlesByCategory(Long cateId) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", cateId));
        return articleRepository.findAllByCategoryAndActive(category, true);
    }

    @Override
    public List<Article> getAllArticlesByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        return articleRepository.findAllByTag(tag.getId());
    }

    @Override
    public List<Article> getAllArticlesNewestByTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        return articleRepository.findAllNewestByTag(tag.getId());
    }

    @Override
    public List<Article> getTop3ArticleNewest() {
        Pageable pageable = PageRequest.of(0, 3);
        return articleRepository.findLimitNewestArticles(pageable);
    }

    @Override
    public List<Article> getAllArticlesByAuthor(Long authorId) {
        Authors authors = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        return articleRepository.findAllByAuthorsAndActive(authors, true);
    }

    @Override
    public Article getArticleById(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
    }

    @Override
    public UserArticle addArticleRead(Long articleId, User user) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
        UserArticle userArticleExist = userArticleRepository.findByUserIdAndArticleId(user.getId(), articleId);
        if (userArticleExist == null) {
            UserArticle userArticle = new UserArticle();
            userArticle.setArticleId(article.getId());
            userArticle.setUserId(user.getId());
            userArticle.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            userArticle.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
            return userArticleRepository.save(userArticle);
        }
        return userArticleExist;
    }

    @Override
    public void crawlData(String category, User user) throws IOException {
        List<Role> roles = roleRepository.getAllByUser(user.getId());
        Set<String> roleNames = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        boolean hasRoleAdmin = roleNames.contains(ERoleName.ROLE_ADMIN.toString());
        if (hasRoleAdmin) {
            Document doc = Jsoup.connect("https://vnexpress.net/" + category).get();
            List<ArticleData> articleDataList = new ArrayList<>();
            Elements titles = doc.select(".list-news-subfolder .item-news");
            String authorName = "";
            String allText = "";
            String categoryName = "";
            String title = "";
            String href = "";
            String src = "";
            for (Element element : titles) {
                title = element.select("h3.title-news a").text();
                href = element.select("h3.title-news a").attr("href");
                src = element.select(".thumb-art a picture img").attr("data-src");

                if (src != "" && href != "" && title != "") {
                    Document detailDoc = Jsoup.connect(href).get();
                    if (detailDoc != null) {
                        Element elementDetail = detailDoc.selectFirst(".sidebar-1");
                        categoryName = elementDetail.selectFirst(".header-content .breadcrumb li a").text();
                        String description = elementDetail.selectFirst("p.description").text();
                        Elements paragraphs = elementDetail.select("article.fck_detail p");
                        if (!paragraphs.isEmpty()) {
                            paragraphs.remove(paragraphs.size() - 1);

                        }
                        Element last = elementDetail.selectFirst(".fck_detail p:last-of-type[style*=text-align:right]");

                        if (last != null) {
                            authorName = last.text();
                        }
                        StringBuilder contentBuilder = new StringBuilder();
                        contentBuilder.append(description).append("\n");
                        for (Element paragraph : paragraphs) {
                            String paragraphText = paragraph.text();
                            contentBuilder.append(paragraphText).append("\n");
                        }
                        allText = contentBuilder.toString().trim();
                    }
                    ArticleData articleData = new ArticleData();
                    articleData.setAuthor(authorName);
                    articleData.setCategoryName(categoryName);
                    articleData.setContent(allText);
                    articleData.setImage(src);
                    articleData.setTitle(title);
                    articleData.setSource(href);
                    articleDataList.add(articleData);
                }
            }

            for (ArticleData a : articleDataList) {
                Category categorySave = categoryRepository.findByName(a.getCategoryName());
                if (categorySave == null) {
                    categorySave = Category.builder()
                            .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                            .description(category)
                            .name(a.getCategoryName())
                            .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                            .image(null)
                            .isActive(true)
                            .build();
                    categoryRepository.save(categorySave);
                }
                User anonymousUser = new User();
                anonymousUser.setFullName(a.getAuthor());
                anonymousUser.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                anonymousUser.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                userRepository.save(anonymousUser);
                Authors authors = new Authors();
                authors.setAuthorName(a.getAuthor());
                authors.setConfirmed(true);
                authors.setUser(anonymousUser);
                authors.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                authors.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                authorRepository.save(authors);
                Article article = Article.builder()
                        .title(a.getTitle())
                        .content(a.getContent())
                        .source(a.getSource())
                        .status(String.valueOf(EArticleStatus.PUBLISHED))
                        .active(true)
                        .image(a.getImage())
                        .category(categorySave)
                        .authors(authors)
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build();
                articleRepository.save(article);
            }
        } else {
            throw new ForbiddenException("Insufficient privilege");
        }
    }
}
