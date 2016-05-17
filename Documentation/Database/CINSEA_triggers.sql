-- Trigger: update_log on actor

-- DROP TRIGGER update_log ON actor;

CREATE TRIGGER update_log
  BEFORE UPDATE
  ON actor
  FOR EACH ROW
  EXECUTE PROCEDURE update_log();

-- Trigger: update_log_insert on actor

-- DROP TRIGGER update_log_insert ON actor;

CREATE TRIGGER update_log_insert
  BEFORE INSERT
  ON actor
  FOR EACH ROW
  EXECUTE PROCEDURE update_log_insert();

-- Trigger: update_log on director

-- DROP TRIGGER update_log ON director;

CREATE TRIGGER update_log
  BEFORE INSERT OR UPDATE
  ON director
  FOR EACH ROW
  EXECUTE PROCEDURE update_log();

-- Trigger: update_log_insert on director

-- DROP TRIGGER update_log_insert ON director;

CREATE TRIGGER update_log_insert
  BEFORE INSERT
  ON director
  FOR EACH ROW
  EXECUTE PROCEDURE update_log_insert();

-- Trigger: update_log on scenarist

-- DROP TRIGGER update_log ON scenarist;

CREATE TRIGGER update_log
  BEFORE INSERT OR UPDATE
  ON scenarist
  FOR EACH ROW
  EXECUTE PROCEDURE update_log();

-- Function: update_log()

-- Trigger: update_log_insert on scenarist

-- DROP TRIGGER update_log_insert ON scenarist;

CREATE TRIGGER update_log_insert
  BEFORE INSERT
  ON scenarist
  FOR EACH ROW
  EXECUTE PROCEDURE update_log_insert();

-- Trigger: update_log on movie

-- DROP TRIGGER update_log ON movie;

CREATE TRIGGER update_log
  BEFORE UPDATE
  ON movie
  FOR EACH ROW
  EXECUTE PROCEDURE update_log();
  
-- Trigger: update_log_insert on movie

-- DROP TRIGGER update_log_insert ON movie;

CREATE TRIGGER update_log_insert
  BEFORE INSERT
  ON movie
  FOR EACH ROW
  EXECUTE PROCEDURE update_log_insert();


-- Function: update_log()

-- DROP FUNCTION update_log();

CREATE OR REPLACE FUNCTION update_log()
  RETURNS trigger AS
$BODY$BEGIN
   NEW."last_edited" := NOW();

   IF (OLD."edit_count" IS NULL)
      THEN
      NEW."edit_count" = 1;
   ELSE
      NEW."edit_count" = OLD."edit_count"+1;
   END IF;
   
   RETURN NEW;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION update_log()
  OWNER TO db16_loffldav;
  
-- Function: update_log_insert()

-- DROP FUNCTION update_log_insert();

CREATE OR REPLACE FUNCTION update_log_insert()
  RETURNS trigger AS
$BODY$BEGIN
   NEW."last_edited" := NOW();
   NEW."edit_count" = 0;
   
   RETURN NEW;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION update_log_insert()
  OWNER TO db16_loffldav;


-- Trigger: rating_logs on rating_related

-- DROP TRIGGER rating_logs ON rating_related;

CREATE TRIGGER rating_logs
  BEFORE INSERT OR UPDATE
  ON rating_related
  FOR EACH ROW
  EXECUTE PROCEDURE count_rating();
  

-- Function: count_rating()

-- DROP FUNCTION count_rating();

CREATE OR REPLACE FUNCTION count_rating()
  RETURNS trigger AS
$BODY$DECLARE rating int;
BEGIN
   SELECT rate_count FROM movie 
   WHERE id_movie = NEW.id_movie
   INTO rating;

   IF (rating IS NULL)
   THEN
   UPDATE movie
   SET ("last_rated","rate_count") = (NOW(),1)
   WHERE id_movie = NEW.id_movie;
   ELSE
   UPDATE movie
   SET ("last_rated","rate_count") = (NOW(),"rate_count"+1)
   WHERE id_movie = NEW.id_movie;
   END IF;

   RETURN NEW;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION count_rating()
  OWNER TO db16_loffldav;


-- Trigger: registration_timestamp on users

-- DROP TRIGGER registration_timestamp ON users;

CREATE TRIGGER registration_timestamp
  BEFORE INSERT
  ON users
  FOR EACH ROW
  EXECUTE PROCEDURE registration_time();


-- Function: registration_time()

-- DROP FUNCTION registration_time();

CREATE OR REPLACE FUNCTION registration_time()
  RETURNS trigger AS
$BODY$BEGIN
NEW."registration_time" := NOW();

RETURN NEW;
END$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION registration_time()
  OWNER TO db16_loffldav;

