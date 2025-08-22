# 📓 Project Inside Out

---

## 📖 Descripción  

**Project Inside Out** es una aplicación de consola en Java que permite registrar, consultar, eliminar y filtrar momentos vividos, cada uno asociado a una emoción y una fecha.  

**Funcionalidades principales:**  
- Añadir nuevos momentos con título, descripción, emoción y fecha.  
- Listar todos los momentos registrados.  
- Eliminar momentos por su identificador.  
- Filtrar por emoción o por fecha.  

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

<img width="337" height="466" alt="image" src="https://github.com/user-attachments/assets/7ec7da28-75bb-4399-bf64-579be5dd2a6c" />

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
