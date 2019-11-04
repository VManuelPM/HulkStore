# HulkStore #

HulkStore, Inventario de productos y control de usuarios

### Comenzando 🚀

![login](https://user-images.githubusercontent.com/18172318/68139913-f6f35f00-fef8-11e9-99db-cf97e30eb71f.PNG)

![producto](https://user-images.githubusercontent.com/18172318/68139991-17231e00-fef9-11e9-85c1-aac335515b1f.PNG)


Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas.

Mira Deployment para conocer como desplegar el proyecto.

### Pre-requisitos 📋

Spring tool suite 4
Mysql

### Instalación 🔧

  1. Descargar el proyecto o clonarlo
  2. Abrir con Spring tool suite
  3. Crear una Base de datos con el nombre hulk_store
  4. ir a src/main/java/util e importar en Mysql el archivo hulk_store.sql
  5. Dar clic derecho en el proyecto -> Maven -> Update Project
  6. Correr el proyecto como Spring Boot app
  7. En un navegador escribir localhost:8010
  8. Existen dos usuarios funcionando actualmente: <br />
    8.1. Usuario admin: Tiene Control absoluto de todas las operaciones en la BD <br />
   ***** user -> admin -> password: admin <br />
    8.2  Usuario vendedor: Puede modificar su información unicamente, en los productos si el stock de un producto llega a cero no puede realizar acciones en este, unicamente el admin puede volver a aumentar el stock  <br />
  ***** user -> juana -> password: juana

### Deployment 📦

  1.proyecto -> Maven -> Update Project
  2. Run as -> Spring Boot app
  3. localhost:8010

### Construido con 🛠️

  1. Spring --> El framework mas popular de Java que facilita el desarrollo con J2EE
  2. Spring tool suite 4 --> Ide de desarollo basada en eclipse para Spring
  3. Spring Security --> Gestiona todo lo relacionado con la seguridad de nuestra aplicación
  4. Spring Boot --> Simplifica los pasos de seleccionar los jars con Maven y desplegarlos
  5. Mysql --> Base de datos mas popular en aplicativos web
  6. Bootstrap --> Framework que permite agilizar el estilo de la página
  7. JPA y Hibernate --> Facilidad de Persistencia en la base de datos
  8. Thymleaf --> Permite añadir atributos y etiquetas en el HTML, permite que las tareas de diseño y programación se pueden llevar     conjuntamente.
  
###### Este proyecto está bajo la Licencia de VManuelPM

### Expresiones de Gratitud 🎁

⌨️ con ❤️ por VManuelPM 😊
