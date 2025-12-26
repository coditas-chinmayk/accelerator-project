--seeding
INSERT INTO category (name, description)
SELECT 'Oncology', 'Cancer-related assessments'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Oncology');

INSERT INTO category (name, description)
SELECT 'Hereditary Cancer', 'Family history and genetic risk'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Hereditary Cancer');

INSERT INTO category (name, description)
SELECT 'Reproductive Health', 'Pregnancy, fertility, and related screenings'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Reproductive Health');

INSERT INTO category (name, description)
SELECT 'Other', 'General or uncategorized assessments'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Other');