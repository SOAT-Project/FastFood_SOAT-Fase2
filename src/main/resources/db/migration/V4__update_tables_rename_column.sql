CREATE OR REPLACE FUNCTION rename_update_at_columns()
RETURNS void AS $$
DECLARE
r RECORD;
BEGIN
FOR r IN
SELECT table_schema, table_name
FROM information_schema.columns
WHERE column_name = 'update_at'
  AND table_schema NOT IN ('information_schema', 'pg_catalog')
    LOOP
        EXECUTE format('ALTER TABLE %I.%I RENAME COLUMN update_at TO updated_at;',
                       r.table_schema, r.table_name);
RAISE NOTICE 'Renomeada coluna update_at para updated_at na tabela %.%',
                     r.table_schema, r.table_name;
END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT rename_update_at_columns();

DO $$
DECLARE
r RECORD;
BEGIN
FOR r IN
SELECT event_object_table AS table_name,
       trigger_name
FROM information_schema.triggers
WHERE trigger_schema = 'public'
    LOOP
        EXECUTE format('DROP TRIGGER IF EXISTS %I ON %I;', r.trigger_name, r.table_name);
END LOOP;
END $$;

CREATE
OR REPLACE FUNCTION update_timestamp()
   RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


DO $$
DECLARE
tbl_name text;
BEGIN
FOR tbl_name IN
SELECT table_name
FROM information_schema.columns WHERE column_name = 'updated_at'
    LOOP
   EXECUTE format(
   'CREATE TRIGGER set_updated_at_%I
   BEFORE UPDATE ON %I
   FOR EACH ROW
   EXECUTE FUNCTION update_timestamp();',
   tbl_name,
   tbl_name
  );
END LOOP;
END;
$$;