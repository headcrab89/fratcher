-- Remove everything.
DELETE FROM MATCH_;
DELETE FROM COMMENT;
DELETE FROM MATCH_COMMENTS;
DELETE FROM TEXT;
DELETE FROM USER_;

  -- Insert new users.
  INSERT INTO USER_ (id, email, PASSWORD) VALUES
  (1, 'blub', 'b80e09ccf074b8cd1c19933071f11925a00765cf091b46d5877eb69a05689c90faf6ee92611528b05c0aaa8949754f7cd51b4911822195e242c9e413256feed4'), --foo
  (2, 'test', '2b97e61878fa6d1221e80d13f719892128c2806939ff5efae428198b3512efb7f631f0e47fac63a96dd0569e75f9574ca3a72116197cc57771f7d4ad2dd76607'), --bar
  (3, 'firstUser', 'e47c99116679030a474594c215ca7512c2a2a4fd3cf29b72fdb24e6d7ae491bc49854497b9268e5b1b29d1b4c8179d6fba2b8a3842415056b38d8411fde7daab'); --test;


  -- Add some posts.
  INSERT INTO TEXT (ID, USER_TEXT, CREATED_AT, AUTHOR_ID) VALUES
  (1, 'Hallo', parsedatetime('2017-07-20 05:01', 'yyyy-MM-dd HH:mm'), 1),
  (2, 'Huhu', parsedatetime('2017-07-21 12:01', 'yyyy-MM-dd HH:mm'), 2),
  (3, 'Hi', parsedatetime('2017-07-23 12:01', 'yyyy-MM-dd HH:mm'), 3);

  -- Add some matches
  INSERT INTO MATCH_ (ID, FIRST_USER_ID, SECOND_USER_ID, BOTH_MATCHING) VALUES
  (1, 1, 2, TRUE ),
  (2, 2, 3, FALSE);

  -- Add some comments.
INSERT INTO COMMENT (ID, CREATED_AT, AUTHOR_ID, TEXT) VALUES
  (1, parsedatetime('2017-07-21 13:01', 'yyyy-MM-dd HH:mm'), 1, 'comment-1'),
  (2, parsedatetime('2017-07-21 15:01', 'yyyy-MM-dd HH:mm'), 2, 'comment-2');

--   --Link matches and comments
  INSERT INTO MATCH_COMMENTS (MATCH_ID, COMMENTS_ID) VALUES
  (1, 1),
  (1, 2);

