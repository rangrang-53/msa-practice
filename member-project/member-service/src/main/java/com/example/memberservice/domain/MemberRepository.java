package com.example.memberservice.domain;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    @Modifying
    @Transactional
    @Query("DELETE FROM member where phone_number = :phoneNumber")
    void deleteByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
