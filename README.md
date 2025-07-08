# Student Data Persistence: Repository Pattern in Java

This project showcases two ways to manage student data:

1.  **File-based Persistence:** Storing student objects directly to a local file using Java's serialization.
2.  **Database Persistence (JPA/Hibernate):** Persisting student records to a relational database using Java Persistence API (JPA) with Hibernate as the provider.

Both approaches adhere to the **Repository Pattern**, providing a clean and consistent API for data operations regardless of the underlying storage mechanism.

## Table of Contents

* [Features](#features)
* [Project Structure](#project-structure)
* [Data Models](#data-models)
* [Repository Layer](#repository-layer)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Database Setup](#database-setup)
    * [Running the Application](#running-the-application)
* [How It Works](#how-it-works)
* [Contributing](#contributing)
* [License](#license)

-----

## Features

* **Repository Pattern Implementation:** Abstracted data access operations (`create`, `getAll`, `update`, `delete`) via a generic `StudentRepository` interface.
* **Dual Persistence Strategies:**
    * **File Persistence:** Uses `ObjectInputStream` and `ObjectOutputStream` to serialize/deserialize `Student` objects to/from a `.dat` file.
    * **Database Persistence:** Leverages **JPA (Jakarta Persistence API)** and **Hibernate** to map Java objects to database tables.
* **Polymorphic Data Models:** Supports different types of students (basic `Student`, `Undergrad`, `GradStudent`) and persists their specific attributes correctly.
* **Clear Separation of Concerns:**
    * **`domain` package:** Contains simple POJOs for file-based persistence, implementing `Serializable`.
    * **`model` package:** Contains JPA-annotated entities for database persistence.
* **Transactional Operations:** Database operations are correctly wrapped in JPA transactions for data integrity.
* **Improved Readability:** `toString()` methods are overridden in all student classes to provide clean and informative output when printing student objects.

-----

## Project Structure

The project is organized into the following key packages:

```
src/main/java/
├── domain/                  // Plain Java Objects for file-based persistence (Serializable)
│   ├── GradStudent.java
│   ├── Student.java
│   ├── Undergrad.java
│   └── YearRank.java        // Enum for undergraduate year rank
├── model/                   // JPA Entities for database persistence
│   ├── GradStudent.java
│   ├── Student.java
│   ├── Undergrad.java
│   └── YearRank.java        // Enum for undergraduate year rank
├── repository/              // Abstraction layer for data access
│   ├── FileStudentRepository.java
│   ├── JpaStudentRepository.java
│   └── StudentRepository.java // Generic interface defining CRUD operations
└── Main.java                // Entry point for demonstrating both persistence methods
```

-----

## Data Models

Both the `domain` and `model` packages contain the following student types, demonstrating inheritance:

* ### `Student`

  The base class with common properties for all students:

    * `firstName`
    * `lastName`
    * `emailAddress` (unique)
    * `gpa`
    * **`domain.Student`**: Implements `Serializable` for file persistence.
    * **`model.Student`**: Annotated with `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, and uses `InheritanceType.TABLE_PER_CLASS` for JPA.

* ### `Undergrad`

  Extends `Student` and adds:

    * `yearRank` (an enum: `FRESHMAN`, `SOPHOMORE`, `JUNIOR`, `SENIOR`).
    * **`model.Undergrad`**: Uses `@Enumerated(EnumType.STRING)` for database storage of the enum.

* ### `GradStudent`

  Extends `Student` and adds:

    * `hasFacultyAdvisor` (boolean)
    * `hasTuitionCredit` (boolean)

All student classes include overridden `toString()` methods for clear console output, eliminating verbose printing logic in `Main`.

-----

## Repository Layer

The `repository` package defines the contract for data operations and provides two concrete implementations:

* ### `StudentRepository<T>` (Interface)

  A generic interface defining the standard CRUD (Create, Retrieve, Update, Delete) operations:

    * `void create(T t)`
    * `List<T> getAll()`
    * `void update(T t)`
    * `void delete(String emailAddress)`

* ### `FileStudentRepository`

  Implements `StudentRepository<domain.Student>`. It handles reading and writing lists of `domain.Student` objects (and its subclasses) to a binary file using Java's object serialization.

* ### `JpaStudentRepository`

  Implementations `StudentRepository<model.Student>`. It manages persistence operations for `model.Student` entities (and its subclasses) to a relational database using JPA (Hibernate). It ensures transactions are properly managed for all modifying operations.

-----

## Getting Started

Follow these steps to set up and run the application.

### Prerequisites

* **Java Development Kit (JDK) 11 or higher:** Ensure you have a compatible JDK installed.
* **Maven:** For dependency management and project building.
* **MySQL Database:** The JPA part of the application is configured to connect to a MySQL database. You'll need a running MySQL server.

### Database Setup

1.  **Create a Database:**
    Open your MySQL client (e.g., MySQL Workbench, command line) and create a new database. For example:

    ```sql
    CREATE DATABASE schooldb;
    ```

2.  **Configure `persistence.xml`:**
    You'll need a `META-INF/persistence.xml` file in your `src/main/resources` directory. This file configures your JPA persistence unit.
    Make sure the database connection details (URL, username, password) match your MySQL setup.

    **Example `persistence.xml`:**

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
                                     http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd"
                 version="2.2">
        <persistence-unit name="SchoolDBPersistenceUnit" transaction-type="RESOURCE_LOCAL">
            <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
            <class>model.Student</class>
            <class>model.Undergrad</class>
            <class>model.GradStudent</class>
            <properties>
                <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/schooldb?createDatabaseIfNotExist=true&amp;useSSL=false&amp;serverTimezone=UTC"/>
                <property name="jakarta.persistence.jdbc.user" value="your_mysql_username"/>
                <property name="jakarta.persistence.jdbc.password" value="your_mysql_password"/>

                <property name="hibernate.hbm2ddl.auto" value="update"/> <property name="hibernate.show_sql" value="true"/>       <property name="hibernate.format_sql" value="true"/>     <property name="hibernate.dialect" value="org.hibernate.dialect.MySQL8Dialect"/>
            </properties>
        </persistence-unit>
    </persistence>
    ```

    **Remember to replace `your_mysql_username` and `your_mysql_password` with your actual database credentials.**

3.  **Maven Dependencies (pom.xml):**
    Ensure your `pom.xml` includes the necessary dependencies for JPA (Hibernate) and the MySQL JDBC driver.

    **Example `pom.xml` snippet:**

    ```xml
    <dependencies>
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.5.2.Final</version> </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.4.0</version> </dependency>
        <dependency>
            <groupId>jakarta.persistence</groupId>
            <artifactId>jakarta.persistence-api</artifactId>
            <version>3.1.0</version>
        </dependency>
    </dependencies>
    ```

### Running the Application

1.  **Clone the Repository (if applicable):**
    If this is part of a larger project, clone it to your local machine.

2.  **Navigate to Project Root:**
    Open your terminal or command prompt and navigate to the root directory of the project where `pom.xml` is located.

3.  **Build the Project:**

    ```bash
    mvn clean install
    ```

    This command compiles the code and downloads all necessary dependencies.

4.  **Run the `Main` Class:**
    You can run the `Main` class directly from your IDE (IntelliJ IDEA, Eclipse, etc.) or via Maven:

    ```bash
    mvn exec:java -Dexec.mainClass="Main"
    ```

    The application will execute a series of CRUD operations, first using file persistence and then database persistence, printing the results to the console.

-----

## How It Works

The `Main` class serves as a demonstration. It instantiates both a `FileStudentRepository` and a `JpaStudentRepository` and performs the following operations for each:

* **Create:** Adds new `Student`, `Undergrad`, and `GradStudent` records.
* **Retrieve All:** Fetches all stored student records and prints them to the console (using the `toString()` methods).
* **Update:** Modifies an existing student's data.
* **Delete:** Removes a student record by email address.
* **Verify Operations:** Retrieves all records again to confirm updates and deletions.

For JPA, Hibernate will automatically create the necessary `students`, `undergrads`, and `gradstudents` tables in your `schooldb` database the first time you run the application (due to `hibernate.hbm2ddl.auto=update`).

-----

## Contributing

Feel free to fork this repository, open issues, or submit pull requests if you have suggestions for improvements or new features.

-----

## License

This project is open-source and available under the [MIT License](License.md).