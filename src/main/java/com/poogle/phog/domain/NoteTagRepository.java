package com.poogle.phog.domain;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteTagRepository extends JpaRepository<NoteTag, Long> {

    @Query(value = "SELECT tag FROM NoteTag WHERE note_id = :id")
    List<Tag> findTagByNoteId(@Param("id") Long noteId);

    @SQLDelete(sql = "DELETE FROM note_tag WHERE note_id = :id")
    void deleteByNoteId(@Param("id") Long noteId);
}
