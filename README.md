![Cannoli-world homepage](uploads/Logo Cannoli.png "CannoliApp Logo")

# Cannoli-world App | 2025 | Novi College | Eindopdracht Full Stack Developer
Dit is het Backend gedeelte van mijn eindopdracht voor de Bootcamp Fullstack Development. 

[Mijn Github voor de BACK-END vind je hier](https://github.com/AngelTromper/cannoli-world-backend)
[Mijn Github voor de FRONT-END vind je hier](https://github.com/AngelTromper/cannoli-world-frontend-new)

# Installatie-handleiding 

### database
Dit project is opgesteld met behulp van Spring Boot, waardoor de bestandstructuur opgedeeld is in de verschillende lagen.
De database gegevens staan geconfigureerd in het application.properies bestand. Voordat het project opgestart wordt deze veranderen naar
je eigen gegevens.

### API REST Endpoints
De API REST Endpoints staan uitgebreid beschreven in de Installatie handleiding.
Deze is te vinden in Postman. 

## Aplicatie starten
De eerste stap is het _clonen_ van de repository. Gebruik de Github clone feature.

of onderstaande info om de code manueel te clonen.
- SSH: `git@github.com:AngelTromper/cannoli-world-backend.git`
- HTTPS: `https://github.com/AngelTromper/cannoli-world-backend.git`

## Bestanden
- main klasse CannoliWorldApplication (main)

### config
configuratie bestanden

- GlobalCorsConfiguration
- PasswordEncoderBean
- SpringSecurityConfig

### controllers
De controller laag zorgt voor de endpoints van de API

- AuthenticationController
- CannoliController
- DeliveryRequestController
- ExceptionController
- PersonController
- PhotoController
- UserController

### dtos
De DTO's zijn onderverdeeld in mappen Request en Response
De DTO's dienen als Data Transfer Object tussen Request en Response.

- CannoliDto
- CannoliInputDto
- CannoliItemDto
- CreateDeliveryRequestDto
- DeliveryRequestDto
- DeliveryRequestStatusDto
- ImageDto
- PersonDto
- PersonInputDto
- UserDto

### exeptions

- BadRequestExeption
- RecordNotFoundException
- UserNameAlreadyExistException
- UsernameNotFoundException

### filter
In de filtermap staat JwtRequestFilter die samen met JwtUtils verantwoordelijk zijn voor de authenticatie door middel van een JWT token.
- 
- JwtRequestFilter

### models
De data objecten van de applicatie ook wel POJO's (Plain Old Java Objects)
op basis van deze objecten zijn de rest van de lagen opgebouwd.

- Authority
- AuthorityKey
- Cannoli
- CannoliItem
- DeliveryRequest
- DeliveryRequestStatus
- FileUploadResponse
- Person
- Status
- User

### payload
- AuthenticationRequest
- AuthenticationResponse

### repositories
Repositories wordt gebruikt om data naar de database te sturen om deze op te slaan
of om specifiek data uit te lezen.

- CannoliRepository
- DeliveryRequestRepository
- FileUploadRepository
- PersonRepository
- UserRepository

### security

- DeliveryRequestSecurity

### service
De service laag dient voor de filtering van de dataobjecten voordat deze verzonden worden naar de controller.
De service laag communiceert met de repository. 

- CannoliService
- CannoliServiceImpl
- CustomerUserDetailsService
- DeliveryRequestService
- DeliveryRequestServiceImpl
- PersonService
- PersonServiceImpl
- PhotoService
- UserService
- UserServiceImpl

### utils
- JwtUtil
- RandomStringGenerator

# Rollen en Gebruikers
Dit zijn de geconfigureerde testgebruikers. Username + wachtwoord.

**USER**
1. user - password

**ADMIN**
1.admin - password
 
## Cannoli een onvergetelijk Italiaanse delicatesse

Cannoli is een onvergetelijk Italiaanse delicatesse. Een koekje gevuld met een cremé in diverse smaken.
Vers gemaakt in de banketbakkerij in Sicilië. Assorti bestaat uit _snack_ , _glutenfree_ en _vegan_ cannoli.
Bereid voor diverse gelegenheden en te verkrijgen met cadeauverpakking. Sr Ruffino is een groothandel in verkoop van 
cannoli's die de mogelijkheid biedt om franchisenemer te worden.

Kortom cannoli is een lekkernij die je echt geproefd moet hebben om de intense smaak te kunnen ervaren!


###### Eindopdracht Fullstack Developer NOVI College | Angelique Tromper | Copyright © 2024-2025 Cannoli-world App | Alle Rechten Voorbehouden.






