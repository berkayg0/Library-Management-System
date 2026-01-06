# Library-Management-System
A Java Swing application to manage library books, users, and borrowing transactions with a MySQL database backend.

### 🔐 User Management & Security
* **Role-Based Access Control:** Distinct functionalities for **Admins** and **Members**.
* **Secure Authentication:** Password hashing (SHA-256) for secure login and registration.
* **Admin Approval System:** New user registrations default to "Pending" status and require Admin approval to access the system.
* **User Profiles:** Track reading history, overdue books, and account details.

### 📚 Inventory & Book Management
* **CRUD Operations:** Add, update, and delete books with automatic input validation.
* **Real-Time Stock Tracking:** Prevents borrowing if stock is insufficient.
* **Dynamic UI:** Responsive grid layout that adjusts automatically based on the book count.
