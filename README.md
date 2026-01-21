# TrackBack - Lost & Found Item Tracking System

TrackBack is a JavaFX-based desktop application designed to help users report and track lost and found items. The application provides an intuitive interface for users to register, log in, report found items with images, and browse through a gallery of lost items.

## 🎯 Features

- **User Authentication**
  - Secure user registration and login system
  - Password-protected accounts
  - Session management

- **Report Found Items**
  - Upload images of found items
  - Categorize items (e.g., electronics, clothing, documents)
  - Record location and date found
  - Contact information storage

- **Browse Lost Items**
  - Paginated gallery view (6 items per page)
  - Image preview with rounded corners
  - Detailed item information display
  - Contact details for item owners

- **User-Friendly Interface**
  - Modern JavaFX GUI
  - Responsive design
  - Intuitive navigation
  - Confirmation dialogs for important actions

## 🛠️ Technologies Used

- **Java** - Core programming language
- **JavaFX 23.0.2** - GUI framework
  - JavaFX Controls
  - JavaFX FXML for UI design
- **MySQL 8.0** - Database management
- **Maven** - Build automation and dependency management
- **JDBC** - Database connectivity

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java Development Kit (JDK) 17 or higher**
- **Maven 3.6+**
- **MySQL Server 8.0+**
- **MySQL Connector/J** (included via Maven dependency)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/TrackBack.git
cd TrackBack
```

### 2. Database Setup

#### Create MySQL Databases

You need to create two databases:

1. **Login Database** (`login`)
   ```sql
   CREATE DATABASE login;
   USE login;
   
   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL
   );
   ```

2. **Report Found Item Database** (`reportfounditem`)
   ```sql
   CREATE DATABASE reportfounditem;
   USE reportfounditem;
   
   CREATE TABLE founditems (
       id INT AUTO_INCREMENT PRIMARY KEY,
       Name VARCHAR(100) NOT NULL,
       phone_number VARCHAR(20) NOT NULL,
       location_found VARCHAR(255) NOT NULL,
       date_found DATE NOT NULL,
       category VARCHAR(50) NOT NULL,
       image_path VARCHAR(500) NOT NULL
   );
   ```

#### Configure Database Connection

Update the database credentials in `src/main/java/database/DatabaseConnection.java`:

```java
private static final String USER = "your_mysql_username";
private static final String PASSWORD = "your_mysql_password";
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn javafx:run
```

Or run the main class directly:

```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.example.trackback.HelloApplication
```

## 📁 Project Structure

```
TrackBack/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/trackback/
│   │   │   │   ├── HelloApplication.java      # Main application entry point
│   │   │   │   ├── LoginController.java       # Login functionality
│   │   │   │   ├── RegisterController.java    # User registration
│   │   │   │   ├── ChoiceController.java      # Main menu after login
│   │   │   │   ├── ReportFoundItem.java       # Report found items
│   │   │   │   ├── ViewLostItems.java         # Browse lost items
│   │   │   │   └── LostItem.java              # Data model
│   │   │   └── database/
│   │   │       └── DatabaseConnection.java    # Database connection utility
│   │   └── resources/
│   │       ├── com/example/trackback/
│   │       │   ├── login.fxml                 # Login UI
│   │       │   ├── register.fxml              # Registration UI
│   │       │   ├── choice.fxml                # Main menu UI
│   │       │   ├── ReportFoundItem.fxml       # Report form UI
│   │       │   └── ViewLostItems.fxml         # Gallery UI
│   │       ├── image/                          # Application images
│   │       └── styles.css                      # CSS styling
├── pom.xml                                     # Maven configuration
└── README.md                                   # This file
```

## 💻 Usage

1. **Launch the Application**
   - Run the application using Maven or your IDE
   - The login window will appear

2. **Register a New Account**
   - Click on the register option
   - Enter a unique username and password
   - Submit to create your account

3. **Login**
   - Enter your username and password
   - Click login to access the main menu

4. **Report a Found Item**
   - Select "Report Found Item" from the main menu
   - Fill in all required fields:
     - Name and phone number
     - Location where item was found
     - Date found
     - Category
     - Upload an image of the item
   - Submit the report

5. **Browse Lost Items**
   - Select "View Lost Items" from the main menu
   - Browse through items using Next/Previous buttons
   - Click on any item to view detailed information and contact details

6. **Logout**
   - Click the logout button to exit the application

## 🔧 Configuration

### Database Connection

The application uses two separate databases:
- `login` - Stores user credentials
- `reportfounditem` - Stores found item reports

Modify `DatabaseConnection.java` to change database connection settings.

### JavaFX Module Path

If you encounter module path issues, ensure JavaFX is properly configured in your IDE or use the Maven JavaFX plugin.

## 🧪 Testing

The project includes JUnit 5 for testing. Run tests with:

```bash
mvn test
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👤 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)

## 🙏 Acknowledgments

- JavaFX community for excellent documentation
- MySQL for robust database management
- All contributors who have helped improve this project

---

⭐ If you find this project helpful, please consider giving it a star!

