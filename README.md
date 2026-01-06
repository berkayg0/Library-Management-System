# Library-Management-System
A Java Swing application to manage library books, users, and borrowing transactions with a MySQL database backend.

### 🔐 User Management & Security
* **Role-Based Access Control:** Distinct functionalities for **Admins** and **Members**.
* **Secure Authentication:** Password hashing (SHA-256) for secure login and registration.
* **Admin Approval System:** New user registrations default to "Pending" status and require Admin approval to access the system.
* **User Profiles:** Track reading history, overdue books, and account details.

### 📚 Book Management
* **CRUD Operations:** Add, update, and delete books with automatic input validation.
* **Real-Time Stock Tracking:** Prevents borrowing if stock is insufficient.

### 🔄 Transaction Management
* **ACID Compliance:** Uses SQL Transactions (`commit`/`rollback`) to ensure data consistency during borrowing and returning processes.
* **Race Condition Prevention:** Stock quantities are managed directly at the database level to prevent concurrency issues.
* **Overdue Tracking:** Automatically calculates and flags overdue books based on the return date.

### 🛡️ Technical Highlights
* **SQL Injection Protection:** All database queries use `PreparedStatement`.
* **Resource Management:** Proper closing of SQL connections using try-with-resources.

## 💻 Installation & Setup

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/berkayg0/LibraryManagementSystem.git](https://github.com/berkayg0/LibraryManagementSystem.git)
    ```

2.  **Database Setup**
    * Open **MySQL Workbench**.
    * Create a schema named `library_db`.
    * Import the `database.sql` file located in the root directory of this project.

3.  **Configure Connection**
    * Navigate to `src/db/DB.java`.
    * Update the `username` and `password` fields with your local MySQL credentials.

4.  **Run the Application**
    * Open the project in **NetBeans** (or any Java IDE).
    * Run `StartScreen.java` to launch the application.

---

## 📷 Screenshots

## Admin Dashboard

<img width="746" height="708" alt="Image" src="https://github.com/user-attachments/assets/3e7193ef-7417-4814-b56c-1dfa7203ec4b" />


## User Dashboard

<img width="747" height="709" alt="Image" src="https://github.com/user-attachments/assets/d70a920c-d7e9-4f5e-ac2d-fd707970bfa9" />


## Book List

<img width="648" height="518" alt="Image" src="https://github.com/user-attachments/assets/2ced659c-c65c-4b4c-af7f-921982d9b3a0" />


## User Management

<img width="753" height="538" alt="Image" src="https://github.com/user-attachments/assets/89f17f06-b2e4-4303-9f69-563d6c4fb750" />

---





