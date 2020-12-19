package com.poogle.phog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT url FROM Photo WHERE note_id = :id")
    List<Photo> findPhotoByNoteId(@Param("id") Long noteId);
}
