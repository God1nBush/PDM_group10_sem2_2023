USE ArticleSchema


-- Create Author table
CREATE TABLE Author (
    AuthorID VARCHAR(5) PRIMARY KEY,
    Fullname VARCHAR(255) NOT NULL,
    OrganizationName VARCHAR(255),
    Email VARCHAR(255) UNIQUE NOT NULL
);

-- Create Copyright table
CREATE TABLE Copyright (
    CopyrightID VARCHAR(5) PRIMARY KEY,
    CopyrightHolder VARCHAR(255) NOT NULL,
    CopyrightYear INT NOT NULL
);

-- Create Organization table
CREATE TABLE Organization (
    Name VARCHAR(255) PRIMARY KEY,
    Address VARCHAR(255),
    PhoneNumber VARCHAR(20)
);

-- Create Journal table
CREATE TABLE Journal (
    JournalID VARCHAR(6) PRIMARY KEY,
    JournalName VARCHAR(255) NOT NULL,
    CategoryField VARCHAR(255),
    Platform VARCHAR(255)
);

-- Create Keywords table
CREATE TABLE Keywords (
    KeywordsID VARCHAR(5) PRIMARY KEY,
    KeywordsName VARCHAR(255) NOT NULL
);

-- Create Article table
CREATE TABLE Article (
    ArticleID VARCHAR(5) PRIMARY KEY,
    Abstract TEXT,
    PublicationDate DATE,
    Title VARCHAR(255) NOT NULL,
    JournalID VARCHAR(6),
    CopyrightID VARCHAR(5),
    Views INT,
    Content TEXT,
    FOREIGN KEY (JournalID) REFERENCES Journal(JournalID),
    FOREIGN KEY (CopyrightID) REFERENCES Copyright(CopyrightID)
);

-- Create Article_Keywords table (many-to-many relationship)
CREATE TABLE ArticleKeywords (
    ArticleID VARCHAR(5),
    KeywordsID VARCHAR(5),
    PRIMARY KEY (ArticleID, KeywordsID),
    FOREIGN KEY (ArticleID) REFERENCES Article(ArticleID),
    FOREIGN KEY (KeywordsID) REFERENCES Keywords(KeywordsID)
);

-- Create Article_Author table (many-to-many relationship)
CREATE TABLE Article_Author (
    ArticleID VARCHAR(5),
    AuthorID VARCHAR(5),
    PRIMARY KEY (ArticleID, AuthorID),
    FOREIGN KEY (ArticleID) REFERENCES Article(ArticleID),
    FOREIGN KEY (AuthorID) REFERENCES Author(AuthorID)
);
