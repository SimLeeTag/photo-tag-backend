INSERT INTO user (email, name, social_id)
VALUES ("poogle@mail.com", "poogle", 123456);
INSERT INTO user (email, name, social_id)
VALUES ("suhyun@mail.com", "suhyun", 654987);

INSERT INTO tag (tag_name, activated, user_id)
VALUES ("ootd", TRUE, 1);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("shopping", TRUE, 2);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("운동", TRUE, 1);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("Flex", TRUE, 1);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("Nike", TRUE, 1);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("존맛", TRUE, 1);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("존맛", TRUE, 2);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("Flex", TRUE, 2);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("운동", TRUE, 2);
INSERT INTO tag (tag_name, activated, user_id)
VALUES ("ootd", TRUE, 2);

INSERT INTO note (created, is_deleted, raw_memo, user_id)
VALUES ("2020-06-19 13:34:11", FALSE, "Today I had a great lunch. #Flex #존맛", 1);
INSERT INTO note (created, is_deleted, raw_memo, user_id)
VALUES ("2020-06-22 16:21:34", FALSE, "아침 #운동 #Nike #ootd", 1);
INSERT INTO note (created, is_deleted, raw_memo, user_id)
VALUES ("2020-07-22 16:21:34", FALSE, "Happy Birthday #Flex #존맛", 2);
INSERT INTO note (created, is_deleted, raw_memo, user_id)
VALUES ("2020-10-20 11:20:30", FALSE, "#ootd #shopping #Flex", 2);
INSERT INTO note (created, is_deleted, raw_memo, user_id)
VALUES ("2020-10-31 08:34:10", FALSE, "#운동 열심히 합시다. #ootd", 2);

INSERT INTO note_tag (note_id, tag_id) VALUES (1, 4);
INSERT INTO note_tag (note_id, tag_id) VALUES (1, 6);
INSERT INTO note_tag (note_id, tag_id) VALUES (2, 3);
INSERT INTO note_tag (note_id, tag_id) VALUES (2, 5);
INSERT INTO note_tag (note_id, tag_id) VALUES (2, 1);
INSERT INTO note_tag (note_id, tag_id) VALUES (3, 8);
INSERT INTO note_tag (note_id, tag_id) VALUES (3, 7);
INSERT INTO note_tag (note_id, tag_id) VALUES (4, 10);
INSERT INTO note_tag (note_id, tag_id) VALUES (4, 2);
INSERT INTO note_tag (note_id, tag_id) VALUES (4, 8);
INSERT INTO note_tag (note_id, tag_id) VALUES (5, 9);
INSERT INTO note_tag (note_id, tag_id) VALUES (5, 10);
