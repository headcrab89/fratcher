-- Remove everything.
DELETE FROM Text;
DELETE FROM USER_;

-- Insert new users.
INSERT INTO USER_ (id, email, PASSWORD) VALUES
  (1, 'blub', 'b80e09ccf074b8cd1c19933071f11925a00765cf091b46d5877eb69a05689c90faf6ee92611528b05c0aaa8949754f7cd51b4911822195e242c9e413256feed4'), --foo
  (2, 'test', '2b97e61878fa6d1221e80d13f719892128c2806939ff5efae428198b3512efb7f631f0e47fac63a96dd0569e75f9574ca3a72116197cc57771f7d4ad2dd76607'); --bar

-- Add some posts.
INSERT INTO Text (ID, USER_TEXT, CREATED_AT, AUTHOR_ID) VALUES
  (1, 'Hallo', parsedatetime('2017-07-20 05:01', 'yyyy-MM-dd HH:mm'), 1),
  (2, 'Huhu', parsedatetime('2017-07-21 12:01', 'yyyy-MM-dd HH:mm'), 2);
