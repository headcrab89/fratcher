-- Remove everything.
-- DELETE FROM MATCH__COMMENTS;
-- DELETE FROM MATCH_;
-- DELETE FROM COMMENT;
-- DELETE FROM TEXT;
-- DELETE FROM USER_;
--
--   -- Insert new users.
--   INSERT INTO USER_ (id, email, PASSWORD) VALUES
--   (1, 'blub', '8e8fa00bd2aab16021566676b222b6942305a056044b0bac144b14716a54ff8983274fe965303e4e38a1454132b9de7408e9e9beb446f2f608fdfe0907d10e30'), --foo
--   (2, 'test', '521fbbcc0d0441af50f7d6ae31bdb30496302d9ba0df2bb0607dcd83e8b71a3368765b03c2a459ae200ab0b35aade15cad74c0fa13dcadef3e477e03982de25b'), --bar
--   (3, 'initUser', 'b0dba4f33dfa79e5c076a92f04499ae4ce1d082c3753c187cc249565c94e24038c6bd1ad3d8d97004af84ba8b1a12a095822b2d0b904fe24e492790dd5f7c5db'); --test;

SELECT * FROM USER_;