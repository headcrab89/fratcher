-- Remove everything.
DELETE FROM MATCH__COMMENTS;
DELETE FROM MATCH_;
DELETE FROM COMMENT;
DELETE FROM TEXT;
DELETE FROM USER_;

  -- Insert new users.
  INSERT INTO USER_ (ID, USER_NAME, LAST_ACTIVITY, PASSWORD) VALUES
  (1, 'Max',parsedatetime('2017-08-27 16:01', 'yyyy-MM-dd HH:mm'),'b80e09ccf074b8cd1c19933071f11925a00765cf091b46d5877eb69a05689c90faf6ee92611528b05c0aaa8949754f7cd51b4911822195e242c9e413256feed4'), --foo
  (2, 'Anna',parsedatetime('2017-08-27 13:00', 'yyyy-MM-dd HH:mm'),'2b97e61878fa6d1221e80d13f719892128c2806939ff5efae428198b3512efb7f631f0e47fac63a96dd0569e75f9574ca3a72116197cc57771f7d4ad2dd76607'), --bar
  (3, 'Stefan',parsedatetime('2017-08-27 11:30', 'yyyy-MM-dd HH:mm'),'4dd8c1a950d6ce914dc8fab783324bb506d1355d5d6d2cca4521e4aaa9a1c7a3c5499fed215bd98e98418d3e623b22b0f01bad0d11eedc4f111858d2f1d9aee5'), --test;
  (4, 'Alice',parsedatetime('2017-08-27 10:45', 'yyyy-MM-dd HH:mm'),'b80e09ccf074b8cd1c19933071f11925a00765cf091b46d5877eb69a05689c90faf6ee92611528b05c0aaa8949754f7cd51b4911822195e242c9e413256feed4'), --foo
  (5, 'Bob',parsedatetime('2017-08-27 10:45', 'yyyy-MM-dd HH:mm'),'b80e09ccf074b8cd1c19933071f11925a00765cf091b46d5877eb69a05689c90faf6ee92611528b05c0aaa8949754f7cd51b4911822195e242c9e413256feed4'), --foo
  (6, 'Eve',parsedatetime('2017-08-27 10:45', 'yyyy-MM-dd HH:mm'),'b80e09ccf074b8cd1c19933071f11925a00765cf091b46d5877eb69a05689c90faf6ee92611528b05c0aaa8949754f7cd51b4911822195e242c9e413256feed4'); --foo

  -- Add some posts.
  INSERT INTO TEXT (ID, USER_TEXT, CREATED_AT, AUTHOR_ID) VALUES
  (1, 'Dies ist nicht Vietnam, sondern Bowling, da gibt es Regeln.', parsedatetime('2017-07-20 05:01', 'yyyy-MM-dd HH:mm'), 1),
  (2, 'Ich weiß, dass ich nichts weiß.', parsedatetime('2017-07-21 12:01', 'yyyy-MM-dd HH:mm'), 2),
  (3, 'Die Frage: nach dem Leben, dem Universum und dem ganzen Rest. - 42.', parsedatetime('2017-07-23 12:01', 'yyyy-MM-dd HH:mm'), 3),
  (4, 'Bis zur Unendlichkeit und noch viel weiter!',  parsedatetime('2017-08-07 12:01', 'yyyy-MM-dd HH:mm'), 4),
  (5, 'Hallo, ich bin Eve',  parsedatetime('2017-08-07 12:01', 'yyyy-MM-dd HH:mm'), 6);

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
  (1, parsedatetime('2017-08-27 13:01', 'yyyy-MM-dd HH:mm'), 1, 'Hallo Anna'),
  (2, parsedatetime('2017-08-27 15:01', 'yyyy-MM-dd HH:mm'), 2, 'Hi'),
  (3, parsedatetime('2017-08-27 15:02', 'yyyy-MM-dd HH:mm'), 1, 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr,' ||
   'sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.' ||
    'At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
  (4, parsedatetime('2017-08-27 18:01', 'yyyy-MM-dd HH:mm'), 4, 'Hey, wir haben einen Match!!');

--   --Link matches and comments
  INSERT INTO MATCH__COMMENTS (MATCH__ID, COMMENTS_ID) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (4, 4);

