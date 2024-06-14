-- Create blog table
CREATE TABLE blogs (
   id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    sub_category_id INT NOT NULL,
    author VARCHAR(255) NOT NULL,
   title VARCHAR(255) NOT NULL,
   slogan VARCHAR(255) UNIQUE NOT NULL,
    slug varchar(255) UNIQUE NOT NULL,
   description TEXT NULL,
   date date NOT NULL,
    image TEXT NUll,
   status TINYINT(4) DEFAULT 0,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);