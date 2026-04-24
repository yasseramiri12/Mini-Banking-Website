# Mini Banking Website

A Jakarta EE (Java EE) web application for managing basic banking operations, clients, and bank accounts. Built with Servlets, JSP, JSTL, and Maven.

## 🚀 Features

- **Client Management**: Add, update, view, and manage bank clients.
- **Account Management**: Manage bank accounts associated with clients.
- **Banking Operations**: Perform and track transactions/operations on bank accounts.
- **MVC Architecture**: Clean separation of concerns using Models, Views (JSP), and Controllers (Servlets) along with the DAO pattern for database access.

## 🛠️ Technologies Used

- **Backend**: Java 17, Jakarta EE 11 (Servlets, JSP)
- **Frontend**: HTML, CSS, JSP, JSTL
- **Build Tool**: Maven
- **Database**: Relational Database (SQL) mapped via DAOs
- **Deployment**: Any standard Servlet Container / Java EE App Server (e.g., Apache Tomcat, GlassFish, Payara)

## 📁 Project Structure

- `src/main/java/.../model`: Domain entities (`Client`, `CompteBancaire`, `Operation`).
- `src/main/java/.../dao`: Data Access Objects for database interactions.
- `src/main/java/.../controller`: Servlets handling HTTP requests and routing.
- `src/main/webapp/WEB-INF/views`: JSP pages for the user interface.

## ⚙️ Setup and Installation

### Prerequisites

- [Java JDK 17+](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/)
- A Servlet container like [Apache Tomcat 10+](https://tomcat.apache.org/)
- A Relational Database (e.g., MySQL, PostgreSQL, Oracle)

### Running Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/Mini-Banking-Website.git
   cd Mini-Banking-Website
   ```

2. **Database Configuration:**
   - Ensure your database server is running.
   - Update the database connection credentials in the relevant DAO classes or configuration files.

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Deploy:**
   - Deploy the generated `.war` file located in the `target/` directory to your web server (e.g., copy `target/banking-app-1.0-SNAPSHOT.war` to Tomcat's `webapps` folder).
   - Alternatively, run via an IDE (IntelliJ IDEA, Eclipse) configured with a local application server.

5. **Access the application:**
   Open your browser and navigate to: `http://localhost:8080/banking-app-1.0-SNAPSHOT/` (Port and path may vary based on your server configuration).

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
