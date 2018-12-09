package ru.itmo.wm4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.wm4.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    int countByLogin(String login);

    @Query(value = "SELECT * FROM user WHERE login=?1 AND passwordSha=SHA1(CONCAT('e979614203d4fd9f', ?2))", nativeQuery = true)
    User findByLoginAndPassword(String login, String password);

    @Query(value = "SELECT * FROM user WHERE login=?1", nativeQuery = true)
    User findByLogin(String login);

    @Transactional
    @Modifying
    @Query(value="UPDATE user SET disabled=disabled^1 WHERE id=?1", nativeQuery = true)
    void updateStatus(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET passwordSha=SHA1(CONCAT('e979614203d4fd9f', ?2)) WHERE id=?1", nativeQuery = true)
    void updatePasswordSha(long id, String password);

}

//спроси, что такое native sql queries