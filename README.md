# Tinder UD - Proyecto Microservicios

Este proyecto implementa un clon funcional de Tinder utilizando una arquitectura de microservicios con Spring Boot (Backend) y una interfaz web con Vanilla JS (Frontend).

## 🚀 Guía de Ejecución Rápida

### 1. Requisitos Previos
- **Java 21**: Asegúrate de tener el JDK 21 instalado y configurado.
- **MySQL**: Debes tener un servidor MySQL corriendo en el puerto 3306.
- **Base de Datos**: Crea una base de datos llamada `tinder_db`.
  ```sql
  CREATE DATABASE IF NOT EXISTS tinder_db;
  ```

### 2. Ejecución del Backend (Microservicios)
El proyecto no utiliza Docker ni API Gateway (por requisitos del examen), así que debes iniciar cada servicio **manualmente** y en este orden preferible (aunque son independientes):

1.  **auth-service** (Puerto 8081)
2.  **usuario-service** (Puerto 8082)
3.  **seguidor-service** (Puerto 8083)
4.  **swipe-service** (Puerto 8084)
5.  **match-service** (Puerto 8085)
6.  **media-service** (Puerto 8086)
7.  **notification-service** (Puerto 8087)

**Cómo ejecutar:**
- Desde NetBeans: Click derecho en cada proyecto -> *Run*.
- Desde Terminal (en la carpeta de cada servicio):
  ```bash
  mvn spring-boot:run
  ```

### 3. Ejecución del Frontend
1. Ve a la carpeta `Front-End`.
2. Abre el archivo `index.html` en tu navegador.
   - Recomiendo usar una extensión como "Live Server" en VSCode, o simplemente doble click si el navegador permite CORS local (aunque para peticiones fetch es mejor un servidor local simple).

---

## 📱 Funcionalidades Implementadas

### A. Autenticación y Perfil
- **Login/Registro:** Funcional con validación.
- **Recuperación de Identidad:** El sistema prioriza el **Email** para identificar al usuario, corrigiendo problemas de desincronización de IDs.
- **Persistencia:** La sesión se mantiene al recargar (localStorage).

### B. Descubrimiento (Swipes)
- **Cards Dinámicas:** Muestra otros usuarios registrados (filtro automático de "no mostrarme a mí mismo").
- **Swipes:**
  - Botón ❌ (Nope): Descarta el perfil.
  - Botón 💚 (Like): Envía un like al backend (`POST /swipe`).
- **Match:** Si el like es mutuo, aparece inmediatamente un popup de "It's a Match!".

### C. Matches y Chat
- **Lista de Matches:** En `matches.html` se cargan dinámicamente las burbujas con foto y nombre de tus matches.
- **Chat:** Al dar click en un match, se abre la interfaz de chat (mensajería en tiempo real no implementada, solo UI y apertura de canal).

---

## ⚙️ Arquitectura de Servicios

| Servicio | Puerto | Función Principal |
|----------|--------|-------------------|
| **auth-service** | 8081 | Login, Registro, Tokens. |
| **usuario-service** | 8082 | Datos del perfil (Nombre, Edad, Foto). |
| **swipe-service** | 8084 | Registra likes/dislikes. Detecta Matches. call `match-service`. |
| **match-service** | 8085 | Almacena matches confirmados. |
| **media-service** | 8086 | Simulación de subida de fotos (retorna URLs). |

## ⚠️ Solución de Problemas Comunes

- **"No salen matches":** Asegúrate de reiniciar `swipe-service` si has hecho cambios en el código Java.
- **"El usuario es Andrea":** Limpia el localStorage (`F12 -> Application -> Clear Storage`) y vuelve a loguearte. El sistema se auto-corrige usando tu email.
- **Errores CORS:** Los controladores tienen `@CrossOrigin("*")` habilitado. Si fallan, verifica que estás apuntando al puerto correcto (ej: Auth es 8081, Swipe es 8084).
