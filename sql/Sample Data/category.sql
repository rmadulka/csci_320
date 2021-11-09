/* INSERT QUERY NO: 1 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        1, 'Hand', 'User0'
    );

/* INSERT QUERY NO: 2 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        2, 'Shovel', 'User1'
    );

/* INSERT QUERY NO: 3 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        3, 'Hammer', 'User2'
    );

/* INSERT QUERY NO: 4 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        4, 'Screwdriver', 'User3'
    );

/* INSERT QUERY NO: 5 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        5, 'Wrench', 'User4'
    );

/* INSERT QUERY NO: 6 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        6, 'Key', 'User5'
    );

/* INSERT QUERY NO: 7 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        7, 'Pliers', 'User5'
    );

/* INSERT QUERY NO: 8 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        8, 'Saw', 'User5'
    );

/* INSERT QUERY NO: 9 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        9, 'Knife', 'User5'
    );

/* INSERT QUERY NO: 10 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        10, 'Cutters', 'User9'
    );

/* INSERT QUERY NO: 11 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        11, 'Maintenance', 'User10'
    );

/* INSERT QUERY NO: 12 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        12, 'Tape', 'User11'
    );

/* INSERT QUERY NO: 13 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        13, 'Power', 'User12'
    );

/* INSERT QUERY NO: 14 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        14, 'Drill', 'User13'
    );

/* INSERT QUERY NO: 15 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        15, 'Saw', 'User14'
    );

/* INSERT QUERY NO: 16 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        16, 'Vehicle', 'User15'
    );

/* INSERT QUERY NO: 17 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        17, 'Electrical', 'User16'
    );

/* INSERT QUERY NO: 18 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        18, 'Mining', 'User17'
    );

/* INSERT QUERY NO: 19 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        19, 'Gardening', 'User18'
    );

/* INSERT QUERY NO: 20 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        20, 'Air', 'User19'
    );

/* INSERT QUERY NO: 21 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        21, 'Vacuum', 'User20'
    );

/* INSERT QUERY NO: 22 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        22, 'Gas', 'User21'
    );

/* INSERT QUERY NO: 23 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        23, 'Chisel', 'User22'
    );

/* INSERT QUERY NO: 24 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        24, 'Level', 'User22'
    );

/* INSERT QUERY NO: 25 */
INSERT INTO "category"(category_id, category_name, category_created_user)
VALUES
    (
        25, 'File', 'User22'
    );

SELECT setval('category_category_id_seq',(SELECT GREATEST(MAX(category_id)+1,nextval('category_category_id_seq'))-1 FROM category));