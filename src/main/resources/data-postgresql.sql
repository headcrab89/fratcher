-- Remove everything.
DELETE FROM MATCH__COMMENTS;
DELETE FROM MATCH_;
DELETE FROM COMMENT;
DELETE FROM TEXT;
DELETE FROM USER_;

  -- Insert new users.
  INSERT INTO USER_ (ID, USER_NAME, LAST_ACTIVITY, PASSWORD) VALUES
  (1, 'Max',TIMESTAMP '2017-08-27 16:01', '8e8fa00bd2aab16021566676b222b6942305a056044b0bac144b14716a54ff8983274fe965303e4e38a1454132b9de7408e9e9beb446f2f608fdfe0907d10e30'), --foo
  (2, 'Anna',TIMESTAMP '2017-08-27 13:00', '593cf7bb46038cb3a9b2ccfa76d7c62657cfc64429f574f90ad415407739f8f2a8666ee9cad21afb09793138277326bc0a9fa74f39600c1a5731ec08d809dbe1'), --bar
  (3, 'Stefan',TIMESTAMP '2017-08-27 11:30', '479f9325ab944991d0d6d2f281679a43362ac13d45a32d32a8226182e83ce5f9ca2a0dd6f1fe04f00cc700f60d90e94529bdc71857a0faff457ed458bfb36a51'), --test;
  (4, 'Alice',TIMESTAMP '2017-08-27 10:45', '8e8fa00bd2aab16021566676b222b6942305a056044b0bac144b14716a54ff8983274fe965303e4e38a1454132b9de7408e9e9beb446f2f608fdfe0907d10e30'), --foo
  (5, 'Bob',TIMESTAMP '2017-08-27 10:45', '8e8fa00bd2aab16021566676b222b6942305a056044b0bac144b14716a54ff8983274fe965303e4e38a1454132b9de7408e9e9beb446f2f608fdfe0907d10e30'), --foo
  (6, 'Eve',TIMESTAMP '2017-08-27 10:45', '8e8fa00bd2aab16021566676b222b6942305a056044b0bac144b14716a54ff8983274fe965303e4e38a1454132b9de7408e9e9beb446f2f608fdfe0907d10e30'); --foo

  -- Add some posts.
  INSERT INTO TEXT (ID, USER_TEXT, CREATED_AT, AUTHOR_ID) VALUES
  (1, 'Dies ist nicht Vietnam, sondern Bowling, da gibt es Regeln.',TIMESTAMP '2017-07-20 05:01', 1),
  (2, 'Ich weiß, dass ich nichts weiß.',TIMESTAMP '2017-07-21 12:01', 2),
  (3, 'Die Frage: nach dem Leben, dem Universum und dem ganzen Rest. - 42.',TIMESTAMP '2017-07-23 12:01', 3),
  (4, 'Bis zur Unendlichkeit und noch viel weiter!',TIMESTAMP '2017-08-07 12:01', 4),
  (5, 'Hallo, ich bin Eve',TIMESTAMP '2017-08-07 12:01', 6);

  -- Add some matches
  INSERT INTO MATCH_ (ID, INIT_USER_ID, MATCH_USER_ID, MATCH_STATUS) VALUES
  (1, 1, 2, 'BOTH_LIKE'),
  (2, 2, 3, 'LIKE'),
  (3, 3, 1, 'DISLIKE'),
  (4, 4, 1, 'BOTH_LIKE'),
  (5, 5, 3, 'LIKE'),
  (6, 3, 6, 'LIKE');

  -- Add some comments.
  INSERT INTO COMMENT (ID, CREATED_AT, AUTHOR_ID, TEXT) VALUES
  (1, TIMESTAMP '2017-08-27 13:01',1, 'Hallo Anna'),
  (2, TIMESTAMP '2017-08-27 15:01',2, 'Hi'),
  (3, TIMESTAMP '2017-08-27 15:02',1, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr,' ||
   'sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.' ||
    'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
  (4, TIMESTAMP '2017-08-27 18:01', 4, 'Hey, wir haben einen Match!!');

--   --Link matches and comments
  INSERT INTO MATCH__COMMENTS (MATCH__ID, COMMENTS_ID) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (4, 4);

