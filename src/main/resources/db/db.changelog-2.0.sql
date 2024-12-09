CREATE FUNCTION avg_attack_level()
    RETURNS numeric AS $$
DECLARE
    avg_attack numeric;
BEGIN
    SELECT AVG(book_creatures.attack_level) INTO avg_attack FROM book_creatures;
    RETURN avg_attack;
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION creature_ids_by_name_containing(substring_to_search varchar)
    RETURNS TABLE (creature_id BIGINT) AS $$
BEGIN
    RETURN QUERY
        SELECT id
        FROM book_creatures
        WHERE name LIKE '%' || substring_to_search || '%';
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION creature_ids_by_name_starting_with(substring_to_search varchar)
    RETURNS TABLE (creature_id BIGINT) AS $$
BEGIN
    RETURN QUERY
        SELECT id
        FROM book_creatures
        WHERE name LIKE substring_to_search || '%';
END;
$$ LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION creature_ids_with_strongest_ring()
    RETURNS TABLE (creature_id BIGINT) AS $$
    DECLARE
        ring_power BIGINT;
BEGIN
        select max(power) from rings where rings.id in (select ring_id from book_creatures) into ring_power;
        RETURN QUERY
            SELECT book_creatures.id
            FROM book_creatures
            JOIN rings r on r.id = book_creatures.ring_id
            WHERE r.power = ring_power;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION exchange_rings(id1 BIGINT, id2 BIGINT)
    RETURNS VOID AS $$
    DECLARE ring1_id BIGINT;
    DECLARE ring2_id BIGINT;
BEGIN
    SELECT rings.id from rings
    JOIN book_creatures bc on rings.id = bc.ring_id
    WHERE bc.id = id1 INTO ring1_id;
    SELECT rings.id from rings
    JOIN book_creatures bc on rings.id = bc.ring_id
    WHERE bc.id = id2 INTO ring2_id;
    UPDATE book_creatures SET ring_id = ring1_id WHERE book_creatures.id = id2;
    UPDATE book_creatures SET ring_id = ring2_id WHERE book_creatures.id = id1;
END;
$$ LANGUAGE 'plpgsql';