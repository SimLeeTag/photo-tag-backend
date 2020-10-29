INSERT INTO tag (tag_name, activated)
VALUES ("ootd", TRUE);
INSERT INTO tag (tag_name, activated)
VALUES ("shopping", TRUE);
INSERT INTO tag (tag_name, activated)
VALUES ("운동", TRUE);
INSERT INTO tag (tag_name, activated)
VALUES ("Flex", TRUE);
INSERT INTO tag (tag_name, activated)
VALUES ("Nike", TRUE);
INSERT INTO tag (tag_name, activated)
VALUES ("존맛", TRUE);

INSERT INTO note (created, is_deleted, raw_memo)
VALUES ("2020-06-19 13:34:11", FALSE, "Today I had a great lunch. #Flex #존맛");
INSERT INTO note (created, is_deleted, raw_memo)
VALUES ("2020-06-22 16:21:34", FALSE, "아침 #운동 #Nike #ootd");
INSERT INTO note (created, is_deleted, raw_memo)
VALUES ("2020-07-22 16:21:34", FALSE, "Happy Birthday #Flex #존맛");
INSERT INTO note (created, is_deleted, raw_memo)
VALUES ("2020-10-20 11:20:30", FALSE, "#ootd #shopping #Flex");
INSERT INTO note (created, is_deleted, raw_memo)
VALUES ("2020-10-31 08:34:10", FALSE, "#운동 열심히 합시다. #ootd");
--
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (1, 4);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (1, 6);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (2, 3);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (2, 5);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (2, 1);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (3, 4);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (3, 6);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (4, 1);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (4, 2);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (4, 4);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (5, 3);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (5, 6);
-- INSERT INTO note_has_tag (note_id, tag_id) VALUES (5, 1);

INSERT INTO note_has_tag (note_id, tag_name) VALUES (1, "ootd");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (1, "존맛");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (2, "운동");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (2, "Nike");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (2, "ootd");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (3, "Flex");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (3, "존맛");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (4, "ootd");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (4, "shopping");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (4, "Flex");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (5, "운동");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (5, "존맛");
INSERT INTO note_has_tag (note_id, tag_name) VALUES (5, "ootd");
