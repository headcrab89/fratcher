-- Remove everything.
DELETE FROM Text;
DELETE FROM USER_;

-- Insert new users.
INSERT INTO USER_ (id, email, PASSWORD) VALUES
  (1, 'blub', 'foo'),
  (2, 'test', 'bar');

-- Add some posts.
INSERT INTO Text (id, user_text, AUTHOR_ID) VALUES
  (1, 'Hallo', 1),
  (2, 'Huhu', 2);
