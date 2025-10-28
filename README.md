# European Environment Score

A GIS Web Application (Written in Spring Boot & Vue.js)
that scores a location of your choice for various environmental metrics.

Only works in Europe.

## Getting Started

### Prerequisites

- Java 17+
- Node.js 16+
- Maven 3.6+

### Development Setup

1. **Install Node.js dependencies:**

   ```bash
   npm install
   ```

2. **Start the Vue.js development server:**

   ```bash
   npm run dev
   ```

   This will start the frontend on http://localhost:3000

3. **Start the Spring Boot backend:**
   ```bash
   ./mvnw spring-boot:run
   ```
   This will start the backend on http://localhost:8080

### Production Build

To build for production:

1. Build the Vue.js frontend:

   ```bash
   npm run build
   ```

2. The built files will be automatically placed in `src/main/resources/static/`

3. Run the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will be available at http://localhost:8080 with the Vue.js frontend served as static content.
