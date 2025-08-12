INSERT INTO address (street_name, city, state, zip_code, longitude, Latitude)
    VALUES ('112th ST', 'Manhattan', 'New York', '11421', 92.5, 99.2);
INSERT INTO address (street_name, city, state, zip_code, longitude, Latitude)
    VALUES ('85th ST Jamaica Avenue', 'Queens', 'New York', '11432', 92.5, 99.2);
INSERT INTO address (street_name, city, state, zip_code, longitude, Latitude)
    VALUES ('2025 Meadow DR', 'Richardson', 'Texas', '75243', 90.25, 20.35);

INSERT INTO users (first_name, last_name, telephone, address_id) VALUES ('Robert', 'Kiyosaki', '123-456-7890', 1);
INSERT INTO users (first_name, last_name, telephone, address_id) VALUES ('Frank', 'Herbert', '232-112-5641', 2);
INSERT INTO users (first_name, last_name, telephone, address_id) VALUES ('Samantha', 'Kiyosaki', '123-456-6589', 1);
INSERT INTO users (first_name, last_name, telephone, address_id) VALUES ('Jordon', 'DeVille', '441-256-7851', 3);

INSERT INTO authors (first_name, last_name, description) VALUES ('Conan', 'Doyle', 'This is a test description');
INSERT INTO authors (first_name, last_name, description) VALUES ('Charles', 'Dickens', 'Another test description');
INSERT INTO authors (first_name, last_name, description) VALUES ('George', 'Orwell', 'Another NEW test description');

INSERT INTO tags (name) VALUES ('personal finance');
INSERT INTO tags (name) VALUES ('science fiction');
INSERT INTO tags (name) VALUES ('fantasy');

INSERT INTO articles (title, blurb, article, created_at, updated_at) VALUES (
    'Rich Dad Poor Dad',
    'Rich Dad Poor Dad is a 1997 book written by Robert T. Kiyosaki and Sharon Lechter. It advocates the importance of financial literacy, financial independence and building wealth through investing in assets, real estate investing, starting and owning businesses, as well as increasing one''s financial intelligence',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
    NOW(),
    NOW()
);

INSERT INTO articles (title, blurb, article, created_at, updated_at) VALUES (
    'Dune: Children of Dune',
    'In the far future, on a remote planet, an epic adventure awaits. Here are the first six novels of Frank Herbert’s magnificent Dune saga—a triumph of the imagination and one of the bestselling science fiction series of all time.',
    'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of ''de Finibus Bonorum et Malorum'' (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, ''Lorem ipsum dolor sit amet..'', comes from a line in section 1.10.32.',
    NOW(),
    NOW()
);

INSERT INTO article_tag (article_id, tag_id) VALUES (1, 1);
INSERT INTO article_tag (article_id, tag_id) VALUES (2, 2);
INSERT INTO article_tag (article_id, tag_id) VALUES (2, 3);

INSERT INTO articles_authors (article_id, author_id) VALUES(
    1,
    1
);
INSERT INTO articles_authors (article_id, author_id) VALUES(
    1,
    3
);
INSERT INTO articles_authors (article_id, author_id) VALUES(
    2,
    2
);