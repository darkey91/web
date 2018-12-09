package ru.itmo.wm4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.wm4.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByNoticeId(Long noticeId);
    Comment findById(long id);
}
