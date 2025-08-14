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

<img width="404" height="696" alt="image" src="https://github.com/user-attachments/assets/64b7fcbf-a04b-4b53-90b3-418e23931be4" />

---

## 📊 Diagramas  

```mermaid
classDiagram
    %% Clases principales
    class MomentController {
        - ConsoleMenu menu
        - MomentService service
        + run()
        - addMoment()
        - deleteMoment()
        - showAllMoments()
        - filterMoments()
        - exitApplication()
        - getValidDate()
        - getValidEmotion()
    }

    class MomentService {
        - MomentRepository repository
        + addMoment(MomentDTO) : Moment
        + getAllMoments() : List~Moment~
        + deleteMoment(int)
        + getMomentsByEmotion(int) : List~Moment~
        + getMomentsByMonthYear(int, int) : List~Moment~
    }

    class MomentRepository {
        <<interface>>
        + save(Moment) : Moment
        + findAll() : List~Moment~
        + deleteById(int)
    }

    class InMemoryMomentRepository {
        - MomentDB db
        + save(Moment) : Moment
        + findAll() : List~Moment~
        + deleteById(int)
    }

    class MomentDB {
        - List~Moment~ moments
        + add(Moment)
        + getAll() : List~Moment~
        + deleteById(int) : boolean
    }

    class Moment {
        - int id
        - String title
        - String description
        - LocalDate eventDate
        - Emotion emotion
    }

    class MomentDTO {
        - String title
        - String description
        - int emotionOption
        - String eventDate
    }

    class MomentMapper {
        + fromDTO(MomentDTO) : Moment
    }

    class ConsoleMenu {
        + showMainMenu()
        + printEmotionMenu()
        + readInt(String) : int
        + readLine(String) : String
        + printMessage(String)
        + printError(String)
        + printMoments(List~Moment~)
    }

    class InputValidator {
        + parseDate(String) : LocalDate
        + isValidEmotionOption(int) : boolean
        + getFormatter() : DateTimeFormatter
    }

    %% Relaciones
    MomentController --> ConsoleMenu
    MomentController --> MomentService
    MomentService --> MomentRepository
    InMemoryMomentRepository --> MomentDB
    MomentService --> MomentMapper
    MomentMapper --> MomentDTO
    MomentMapper --> Moment
    MomentService --> Moment
    MomentRepository <|.. InMemoryMomentRepository

```
---

## 👥 Autores  

- **Miguel Ballesteros**
- **Saúl Otero**
