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
