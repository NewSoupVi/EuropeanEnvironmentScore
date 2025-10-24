# European Environment Score

A simple GIS Web Application (Written in Spring Boot & Vue.js)

## Project Structure

```
Environment Score/
├── src/main/java/          # Spring Boot backend
├── frontend/               # Vue.js frontend
│   ├── src/
│   │   ├── App.vue        # Main Vue component
│   │   ├── main.js        # Vue app entry point
│   │   └── style.css      # Global styles
│   └── index.html         # HTML template
├── package.json           # Node.js dependencies
├── vite.config.js         # Vite build configuration
└── pom.xml               # Maven dependencies
```

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

### Available Scripts

- `npm run dev` - Start Vue.js development server with hot reload
- `npm run build` - Build Vue.js app for production (outputs to Spring Boot static folder)
- `npm run preview` - Preview production build

### API Endpoints

- `GET /api/score` - Get current environment score
- `GET /api/locations` - Get location data with scores

### Features

- **Vue.js 3** with Composition API
- **Vite** for fast development and building
- **Leaflet** for interactive maps
- **Spring Boot** REST API
- **CORS** configured for development
- **Responsive design** with CSS Grid

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
