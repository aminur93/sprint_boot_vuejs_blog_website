-- V1__create_acl_all_table.sql

-- Create users table
CREATE TABLE users (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) UNIQUE NOT NULL,
   email_verified_at TIMESTAMP NULL,
   password VARCHAR(255) NOT NULL,
   remember_token VARCHAR(100) NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create roles table
CREATE TABLE roles (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(255) UNIQUE NOT NULL
);

-- Create permissions table
CREATE TABLE permissions (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) UNIQUE NOT NULL
);

-- Create role_has_permission join table
CREATE TABLE role_has_permission (
     role_id INT NOT NULL,
     permission_id INT NOT NULL,
     PRIMARY KEY (role_id, permission_id),
     FOREIGN KEY (role_id) REFERENCES roles(id),
     FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

-- Create model_has_role join table
CREATE TABLE model_has_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);