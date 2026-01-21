# TrackBack - Lost & Found Item Tracking System

TrackBack is a JavaFX-based desktop application designed to help users report and track lost and found items. The application provides an intuitive interface for users to register, log in, report found items with images, and browse through a gallery of lost items.

##  Features

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

##  Technologies Used

- **Java** - Core programming language
- **JavaFX 23.0.2** - GUI framework
  - JavaFX Controls
  - JavaFX FXML for UI design
- **MySQL 8.0** - Database management
- **Maven** - Build automation and dependency management
- **JDBC** - Database connectivity

##  Prerequisites

Before running this application, ensure you have the following installed:

- **Java Development Kit (JDK) 17 or higher**
- **Maven 3.6+**
- **MySQL Server 8.0+**
- **MySQL Connector/J** (included via Maven dependency)

##  How to Run

1. Make sure MySQL is running and set up the databases (`login` and `reportfounditem`)
2. Update your database username and password in `DatabaseConnection.java`
3. Run the app: `mvn clean javafx:run`

##  Project Structure

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

##  Usage

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

##  Configuration

### Database Connection

The application uses two separate databases:
- `login` - Stores user credentials
- `reportfounditem` - Stores found item reports

Modify `DatabaseConnection.java` to change database connection settings.

### JavaFX Module Path

If you encounter module path issues, ensure JavaFX is properly configured in your IDE or use the Maven JavaFX plugin.

##  Testing

The project includes JUnit 5 for testing. Run tests with:

```bash
mvn test
```







