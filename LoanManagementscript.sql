create database loanmanagement;
use loanmanagement;

drop table if exists Customer; 
drop table if exists Loan;

-- 1. Customer Table
CREATE TABLE Customer (
    customerId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    emailAddress VARCHAR(100) UNIQUE NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    address TEXT NOT NULL,
    creditScore INT NOT NULL
);

-- 2. Loan Table 
CREATE TABLE Loan (
    loanId INT AUTO_INCREMENT PRIMARY KEY,
    customerId INT NOT NULL,
    principalAmount DECIMAL(12, 2) NOT NULL,
    interestRate DECIMAL(5, 2) NOT NULL,
    loanTerm INT NOT NULL,
    loanType ENUM('CarLoan', 'HomeLoan') NOT NULL,
    loanStatus ENUM('Pending', 'Approved','Rejected') NOT NULL,
    propertyAddress TEXT,
    propertyValue INT,
    carModel VARCHAR(100),
    carValue INT,

    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
        ON DELETE CASCADE
);

select * from customer;
select * from loan;
INSERT INTO Customer (name, emailAddress, phoneNumber, address, creditScore)
VALUES ('John Doe', 'john.doe@example.com', '9876543210', '123 Main Street, New York, NY', 700);

INSERT INTO Customer (name, emailAddress, phoneNumber, address, creditScore)
VALUES 
('Emma Watson', 'emma.watson@example.com', '9876512340', '12 Baker Street, London', 410),
('Robert Downey', 'robert.d@example.com', '9876501234', '45 Stark Tower, New York', 690),
('Chris Evans', 'chris.evans@example.com', '9876523456', '67 Captain Road, Boston', 730),
('Scarlett Johansson', 'scarlett.j@example.com', '9876534567', '89 Black Widow Lane, LA', 680),
('Mark Ruffalo', 'mark.r@example.com', '9876545678', '23 Hulk Avenue, Ohio', 700),
('Tom Holland', 'tom.h@example.com', '9876556789', '56 Spider Street, Queens', 660),
('Zendaya Coleman', 'zendaya.c@example.com', '9876567890', '34 MJ Drive, California', 720),
('Chris Hemsworth', 'chris.h@example.com', '9876578901', '77 Thor Crescent, Asgard', 740),
('Benedict Cumberbatch', 'benedict.c@example.com', '9876589012', '99 Strange Blvd, NYC', 710);

update customer set creditscore = 430 where customerid = 4;