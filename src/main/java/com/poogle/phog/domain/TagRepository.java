package com.poogle.phog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findTagsByUserIdAndTagNameIn(Long userId, List<String> tags);
    List<Tag> findTagsByUserIdAndActivatedTrueOrderByTagNameAsc(Long userId);
}
