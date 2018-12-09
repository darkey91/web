package ru.itmo.wm4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
