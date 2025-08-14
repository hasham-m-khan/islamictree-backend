INSERT IGNORE INTO addresses (id, street, city, state, country, zip_code)
    VALUES (1, '112th ST', 'Manhattan', 'New York', 'USA', '11421');
INSERT IGNORE INTO addresses (id, street, city, state, country, zip_code)
    VALUES (2, '85th ST Jamaica Avenue', 'Queens', 'New York', 'USA', '11432');
INSERT IGNORE INTO addresses (id, street, city, state, country, zip_code)
    VALUES (3, '2025 Meadow DR', 'Richardson', 'Texas', 'USA', '75243');

INSERT IGNORE INTO users (id, first_name, last_name, telephone, address_id)
       VALUES (1, 'Robert', 'Kiyosaki', '123-456-7890', 1);
INSERT IGNORE INTO users (id, first_name, last_name, telephone, address_id)
       VALUES (2, 'Frank', 'Herbert', '232-112-5641', 2);
INSERT IGNORE INTO users (id, first_name, last_name, telephone, address_id)
       VALUES (3, 'Samantha', 'Kiyosaki', '123-456-6589', 1);
INSERT IGNORE INTO users (id, first_name, last_name, telephone, address_id)
       VALUES (4, 'Jordon', 'DeVille', '441-256-7851', 3);

INSERT IGNORE INTO authors (id, first_name, last_name, description)
       VALUES (1, 'Conan', 'Doyle', 'This is a test description');
INSERT IGNORE INTO authors (id, first_name, last_name, description)
       VALUES (2, 'Charles', 'Dickens', 'Another test description');
INSERT IGNORE INTO authors (id, first_name, last_name, description)
       VALUES (3, 'George', 'Orwell', 'Another NEW test description');

INSERT IGNORE INTO tags (id, name) VALUES (1, 'personal finance');
INSERT IGNORE INTO tags (id, name) VALUES (2, 'science fiction');
INSERT IGNORE INTO tags (id, name) VALUES (3, 'fantasy');

INSERT IGNORE INTO articles (id, title, blurb, article, created_at, last_updated) VALUES (
    1,
    'Rich Dad Poor Dad',
    'Rich Dad Poor Dad is a 1997 book written by Robert T. Kiyosaki and Sharon Lechter. It advocates the importance of financial literacy, financial independence and building wealth through investing in assets, real estate investing, starting and owning businesses, as well as increasing one''s financial intelligence',
    'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
    NOW(),
    NOW()
);

INSERT IGNORE INTO articles (id, title, blurb, article, created_at, last_updated) VALUES (
    2,
    'Dune: Children of Dune',
    'In the far future, on a remote planet, an epic adventure awaits. Here are the first six novels of Frank Herbert’s magnificent Dune saga—a triumph of the imagination and one of the bestselling science fiction series of all time.',
    'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of ''de Finibus Bonorum et Malorum'' (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, ''Lorem ipsum dolor sit amet..'', comes from a line in section 1.10.32.',
    NOW(),
    NOW()
);

INSERT IGNORE INTO article_tag (article_id, tag_id) VALUES (1, 1);
INSERT IGNORE INTO article_tag (article_id, tag_id) VALUES (2, 2);
INSERT IGNORE INTO article_tag (article_id, tag_id) VALUES (2, 3);

INSERT IGNORE INTO author_article (article_id, author_id) VALUES(1, 1);
INSERT IGNORE INTO author_article (article_id, author_id) VALUES(1, 3);
INSERT IGNORE INTO author_article (article_id, author_id) VALUES(2, 2);
