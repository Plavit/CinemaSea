-- Trigger: update_logger on actor

-- DROP TRIGGER update_logger ON actor;

CREATE TRIGGER update_logger
  BEFORE INSERT OR UPDATE
  ON actor
  FOR EACH ROW
  EXECUTE PROCEDURE update_log();

-- Trigger: update_log on director

-- DROP TRIGGER update_log ON director;

CREATE TRIGGER update_log
  BEFORE INSERT OR UPDATE
  ON director
  FOR EACH ROW
  EXECUTE PROCEDURE update_log();

-- Trigger: update_log on scenarist

-- DROP TRIGGER update_log ON scenarist;

CREATE TRIGGER update_log
  BEFORE INSERT OR UPDATE
  ON scenarist
  FOR EACH ROW
  EXECUTE PROCEDURE update_log();

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
END
$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
ALTER FUNCTION update_log()
  OWNER TO db16_loffldav;

-- Trigger: rating_log on rating_related

-- DROP TRIGGER rating_log ON rating_related;

CREATE TRIGGER rating_log
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
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION count_rating()
  OWNER TO db16_loffldav;


