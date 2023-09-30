package com.quangtoi.good_news.service.impl;

import com.quangtoi.good_news.dto.AuthorResponse;
import com.quangtoi.good_news.exception.ResourceNotFoundException;
import com.quangtoi.good_news.pojo.Authors;
import com.quangtoi.good_news.pojo.Role;
import com.quangtoi.good_news.pojo.User;
import com.quangtoi.good_news.repository.AuthorRepository;
import com.quangtoi.good_news.repository.RoleRepository;
import com.quangtoi.good_news.repository.UserRepository;
import com.quangtoi.good_news.request.AuthorRequest;
import com.quangtoi.good_news.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public AuthorResponse registerAuthor(User currentUser, AuthorRequest authorRequest) throws Exception {
        Authors authorSaved = authorRepository.findByUser(currentUser);
        if (authorSaved != null) {
            throw new Exception("Tài khoản này đã gửi yêu cầu. Vui lòng chờ xác nhận.");
        }
        Authors authors = Authors.builder()
                .isConfirmed(false)
                .authorName(authorRequest.getAuthorName())
                .user(currentUser)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Authors authorsSaved = authorRepository.save(authors);
        AuthorResponse authorResponse = AuthorResponse.builder()
                .authorName(authorsSaved.getAuthorName())
                .createdAt(authorsSaved.getCreatedAt())
                .address(currentUser.getAddress())
                .avatar(currentUser.getAvatar())
                .dateOfBirth(currentUser.getDateOfBirth())
                .email(currentUser.getEmail())
                .fullName(currentUser.getFullName())
                .username(currentUser.getUsername())
                .id(currentUser.getId())
                .isActive(currentUser.isActive())
                .isConfirmed(authorsSaved.isConfirmed())
                .password(currentUser.getPassword())
                .passwordResetToken(currentUser.getPasswordResetToken())
                .updatedAt(authorsSaved.getUpdatedAt())
                .build();
        return authorResponse;
    }

    @Override
    public AuthorResponse updateAuthor(User currentUser, AuthorRequest authorRequest) {
        Authors authors = authorRepository.findByUser(currentUser);
        if (authors == null) {
            throw new ResourceNotFoundException("Author", "userId", currentUser.getId());
        }
        authors.setAuthorName(authorRequest.getAuthorName());
        Authors authorsSaved = authorRepository.save(authors);
        AuthorResponse authorResponse = AuthorResponse.builder()
                .authorName(authorsSaved.getAuthorName())
                .createdAt(authorsSaved.getCreatedAt())
                .address(currentUser.getAddress())
                .avatar(currentUser.getAvatar())
                .dateOfBirth(currentUser.getDateOfBirth())
                .email(currentUser.getEmail())
                .fullName(currentUser.getFullName())
                .username(currentUser.getUsername())
                .id(currentUser.getId())
                .isActive(currentUser.isActive())
                .isConfirmed(authorsSaved.isConfirmed())
                .password(currentUser.getPassword())
                .passwordResetToken(currentUser.getPasswordResetToken())
                .updatedAt(authorsSaved.getUpdatedAt())
                .build();
        return authorResponse;
    }

    @Override
    public Authors updateAuthorById(User currentUser, AuthorRequest authorRequest, Long authorId) {
        Authors authors = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdmin = roles.stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"));
        if (!hasRoleAdmin) {
            throw new BadCredentialsException("You do not have permission");
        }
        authors.setAuthorName(authorRequest.getAuthorName());
        return authorRepository.save(authors);
    }

    @Override
    public Authors confirmAuthor(User currentUser, Long authorId) {
        Authors authors = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdmin = roles.stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"));
        if (hasRoleAdmin) {
            authors.setConfirmed(true);
        } else {
            throw new BadCredentialsException("You do not have permission");
        }
        return authorRepository.save(authors);
    }

    @Override
    public List<Authors> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Authors getAuthorsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return authorRepository.findByUser(user);
    }

    @Override
    public void deleteAuthorById(User currentUser, Long authorId) {
        Authors authors = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", authorId));
        List<Role> roles = roleRepository.getAllByUser(currentUser.getId());
        boolean hasRoleAdmin = roles.stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"));
        if (hasRoleAdmin) {
            authorRepository.delete(authors);
        } else {
            throw new BadCredentialsException("You do not have permission to delete");
        }
    }
}
