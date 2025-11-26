#  Mutant Detector API

API REST para detectar mutantes basándose en su secuencia de ADN. Proyecto desarrollado con Spring Boot y desplegado en Render.

## Links del Proyecto
- **URL de la API (Producción):** [https://mutantes-m5xp.onrender.com](https://mutantes-m5xp.onrender.com/swagger-ui.html)
- **Documentación Swagger:** [Ver Documentación Interactiva](https://mutantes-m5xp.onrender.com/swagger-ui.html)
- **Repositorio GitHub:** [https://github.com/natalia15012/Mutantes](https://github.com/natalia15012/Mutantes)

---

## Cómo Ejecutar Localmente
Clonar el repositorio:
   git clone [https://github.com/natalia15012/Mutantes.git](https://github.com/natalia15012/Mutantes.git)

## Diagrama de Secuencia.
```mermaid
sequenceDiagram
    actor Client as Cliente
    participant Controller as MutantController
    participant Service as MutantService
    participant Repo as DnaRecordRepository
    participant Detector as MutantDetector

    Client->>Controller: POST /mutant {dna: [...]}
    activate Controller
    
    Controller->>Service: analyzeDna(dna)
    activate Service
    
    Service->>Service: Calcular Hash SHA-256
    
    Service->>Repo: findByDnaHash(hash)
    activate Repo
    Repo-->>Service: (Optional) existingRecord
    deactivate Repo
    
    alt Registro ya existe (Caché)
        Service-->>Controller: return existingRecord.isMutant()
    else Registro es nuevo
        Service->>Detector: isMutant(dna)
        activate Detector
        Detector-->>Service: boolean result
        deactivate Detector
        
        Service->>Repo: save(new DnaRecord)
        activate Repo
        Repo-->>Service: savedRecord
        deactivate Repo
        
        Service-->>Controller: return result
    end
    
    deactivate Service

    alt Es Mutante
        Controller-->>Client: 200 OK
    else Es Humano
        Controller-->>Client: 403 Forbidden
    end
    deactivate Controller
```
