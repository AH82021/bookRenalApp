-- Insert sample categories
INSERT INTO categories (id, name, slug, description, created_at) VALUES
(1, 'Fiction', 'fiction', 'Fictional books and novels', NOW()),
(2, 'Science Fiction', 'science-fiction', 'Science fiction and fantasy books', NOW()),
(3, 'Non-Fiction', 'non-fiction', 'Non-fictional educational books', NOW()),
(4, 'Technology', 'technology', 'Books about technology and programming', NOW()),
(5, 'Business', 'business', 'Business and management books', NOW());

-- Set parent relationships
UPDATE categories SET parent_id = 1 WHERE id = 2;
UPDATE categories SET parent_id = 3 WHERE id = 4;
UPDATE categories SET parent_id = 3 WHERE id = 5;

-- Insert sample books
INSERT INTO books (id, title, author, isbn, description, price, rental_price, publication_date, publisher, pages, language, cover_image_url, total_copies, available_copies, status, created_at, updated_at, deleted) VALUES
(1, 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'A classic American novel set in the Jazz Age', 12.99, 3.99, '1925-04-10', 'Scribner', 180, 'English', 'https://example.com/gatsby.jpg', 10, 8, 'AVAILABLE', NOW(), NOW(), false),
(2, 'Dune', 'Frank Herbert', '9780441013593', 'Epic science fiction novel about desert planet Arrakis', 15.99, 4.99, '1965-08-01', 'Ace Books', 688, 'English', 'https://example.com/dune.jpg', 5, 3, 'AVAILABLE', NOW(), NOW(), false),
(3, 'Clean Code', 'Robert C. Martin', '9780132350884', 'A handbook of agile software craftsmanship', 42.99, 12.99, '2008-08-01', 'Prentice Hall', 464, 'English', 'https://example.com/cleancode.jpg', 8, 5, 'AVAILABLE', NOW(), NOW(), false),
(4, 'The Lean Startup', 'Eric Ries', '9780307887894', 'How constant innovation creates radically successful businesses', 26.99, 8.99, '2011-09-13', 'Crown Business', 336, 'English', 'https://example.com/leanstartup.jpg', 6, 4, 'AVAILABLE', NOW(), NOW(), false),
(5, 'Foundation', 'Isaac Asimov', '9780553293357', 'First book in the Foundation series', 13.99, 4.49, '1951-05-01', 'Bantam Spectra', 244, 'English', 'https://example.com/foundation.jpg', 7, 7, 'AVAILABLE', NOW(), NOW(), false);

-- Create book-category associations
INSERT INTO book_categories (book_id, category_id) VALUES
(1, 1), -- The Great Gatsby -> Fiction
(2, 2), -- Dune -> Science Fiction
(2, 1), -- Dune -> Fiction (also fiction)
(3, 4), -- Clean Code -> Technology
(4, 5), -- The Lean Startup -> Business
(5, 2), -- Foundation -> Science Fiction
(5, 1); -- Foundation -> Fiction

-- Reset sequences for PostgreSQL auto-increment
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
SELECT setval('books_id_seq', (SELECT MAX(id) FROM books));