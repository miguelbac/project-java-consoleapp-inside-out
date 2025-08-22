# 📓 Project Inside Out

---

## 📖 Descripción  

Aplicación de consola en **Java** que permite gestionar un diario de **momentos personales** y una colección de **películas**, asociando cada entrada a una emoción.  

## 🚀 Funcionalidades principales
- **Gestión de momentos**
  - Añadir momentos con título, descripción, emoción, fecha y tipo (bueno/malo).
  - Listar todos los momentos guardados.
  - Eliminar momentos por ID.
  - Filtrar momentos por emoción, fecha o tipo.
  - Exportar momentos a CSV.

- **Gestión de películas**
  - Añadir películas con ID de IMDB, título, géneros, emoción y año de estreno.
  - Listar todas las películas.
  - Eliminar películas por IMDB Id.
  - Filtrar películas por género.
  - Persistencia en CSV.

## 🏗️ Arquitectura
El proyecto sigue una estructura modular inspirada en **Clean Architecture**:
- **controllers** → Controladores de la lógica de flujo.
- **services** → Lógica de negocio.
- **repositories** → Acceso a datos (CSV o memoria).
- **models** → Entidades y DTOs.
- **views** → Interfaz de usuario por consola.
- **utils** → Utilidades (validaciones, exportaciones).
- **mappers** → Conversión entre DTOs y modelos.


---

## 📋 Pre-requisitos  

Para ejecutar este proyecto necesitas:  

- **Java 21**  
- **Maven 3.6.3** o superior  
- Un IDE compatible con Maven (por ejemplo: IntelliJ IDEA, Eclipse, VS Code con extensión Java)  

---

## ⚙️ Instalación  

1️⃣ **Clonar el repositorio:**  
\`\`\`bash
git clone https://github.com/miguelbac/project-java-consoleapp-inside-out.git
\`\`\`

2️⃣ **Acceder al directorio del proyecto:**  
\`\`\`bash
cd project-java-consoleapp-inside-out
\`\`\`

3️⃣ **Compilar el proyecto:**  
\`\`\`bash
mvn clean install
\`\`\`

---

## ▶️ Ejecución  

Para ejecutar la aplicación desde la consola:  
\`\`\`bash
mvn exec:java -Dexec.mainClass="org.factoriaf5.project_inside_out.Main"
\`\`\`

---

## 🧪 Ejecución de tests  

Para ejecutar los tests:  
\`\`\`bash
mvn test
\`\`\`

**Dependencias de testing:**  
- **JUnit 5.6.0**  
- **Mockito 5.18.0**  
- **Hamcrest 2.2**  

<img width="358" height="219" alt="image" src="https://github.com/user-attachments/assets/94e910c0-a4a2-4c0b-be6e-31a7ef11543e" />

---

## 📊 Diagramas  

```mermaid
classDiagram
    class App

    class AppController {
        -ConsoleMenu menu
        -MomentController momentController
        -MovieController movieController
        +run()
    }

    class MomentController {
        -ConsoleMenu menu
        -MomentService service
        +run()
    }

    class MovieController {
        -ConsoleMenu menu
        -MovieService service
        +run()
    }

    class ConsoleMenu {
        +showMainMenu()
        +showMomentMenu()
        +showMovieMenu()
        +printEmotionMenu()
        +showFilterMenu()
        +readInt()
        +readLine()
        +printMessage()
        +printError()
        +printMoments()
        +printMovies()
    }

    class MomentService {
        -MomentRepository repository
        +addMoment(dto: MomentDTO) Moment
        +getAllMoments() List~Moment~
        +deleteMoment(id: int)
        +filterByEmotion(emotion: Emotion) List~Moment~
        +filterByDate(date: LocalDate) List~Moment~
        +filterByType(isGood: boolean) List~Moment~
        +exportToCSV(filename: String)
    }

    class MovieService {
        -MovieRepository repository
        +addMovie(dto: MovieDTO) Movie
        +getAllMovies() List~Movie~
        +deleteMovie(imdbId: String)
        +filterByGenre(genre: String) List~Movie~
    }

    class MomentRepository {
        <<interface>>
        +save(moment: Moment) Moment
        +findAll() List~Moment~
        +deleteById(id: int)
        +getNextId() int
    }

    class MovieRepository {
        <<interface>>
        +save(movie: Movie) Movie
        +findAll() List~Movie~
        +deleteById(imdbId: String)
        +findByGenre(genre: String) List~Movie~
    }

    class InMemoryMomentRepository {
        -MomentDB db
        -int counter
        +save(moment: Moment) Moment
        +findAll() List~Moment~
        +deleteById(id: int)
        +getNextId() int
    }

    class CsvMovieRepository {
        -File file
        +save(movie: Movie) Movie
        +findAll() List~Movie~
        +deleteById(imdbId: String)
        +findByGenre(genre: String) List~Movie~
    }

    class MomentDB {
        -List~Moment~ moments
        +add(moment: Moment)
        +getAll() List~Moment~
        +deleteById(id: int) boolean
    }

    class MomentMapper {
        +fromDTO(dto: MomentDTO) Moment
    }

    class MovieMapper {
        +fromDTO(dto: MovieDTO) Movie
    }

    class Emotion {
        <<enum>>
        ALEGRIA
        TRISTEZA
        IRA
        ASCO
        MIEDO
        ANSIEDAD
        ENVIDIA
        VERGUENZA
        ABURRIMIENTO
        NOSTALGIA
    }

    class Moment {
        -int id
        -String title
        -String description
        -Emotion emotion
        -LocalDate eventDate
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        -boolean isGood
        +setId(id: int)
    }

    class MomentDTO {
        -String title
        -String description
        -Emotion emotion
        -LocalDate eventDate
        -boolean isGood
    }

    class Movie {
        -String imdbId
        -String title
        -List~String~ genres
        -Emotion emotion
        -int releaseYear
        -LocalDateTime createdAt
    }

    class MovieDTO {
        -String imdbId
        -String title
        -List~String~ genres
        -Emotion emotion
        -int releaseYear
    }

    class InputValidator {
        +parseDate(input: String) LocalDate
        +isValidEmotionOption(option: int) boolean
    }

    class MomentExporter {
        +exportToCSV(moments: List~Moment~, filename: String)
    }

    %% Relaciones
    App --> AppController
    AppController --> ConsoleMenu
    AppController --> MomentController
    AppController --> MovieController
    MomentController --> ConsoleMenu
    MomentController --> MomentService
    MovieController --> ConsoleMenu
    MovieController --> MovieService
    MomentService --> MomentRepository
    MomentService --> Moment
    MomentService --> MomentDTO
    MomentService --> Emotion
    MovieService --> MovieRepository
    MovieService --> Movie
    MovieService --> MovieDTO
    InMemoryMomentRepository ..|> MomentRepository
    CsvMovieRepository ..|> MovieRepository
    InMemoryMomentRepository --> MomentDB
    MomentMapper --> Moment
    MomentMapper --> MomentDTO
    MovieMapper --> Movie
    MovieMapper --> MovieDTO
    Moment --> Emotion
    MomentDTO --> Emotion
    Movie --> Emotion
    MovieDTO --> Emotion

```
---

## 👥 Autores  

- **Miguel Ballesteros**
- **Saúl Otero**
