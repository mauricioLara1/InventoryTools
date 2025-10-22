# ToolTracker

ToolTracker es una aplicación Android desarrollada en Kotlin para gestionar inventario de herramientas en talleres o empresas. Permite registrar herramientas, controlar préstamos, actualizar cantidades y mantener un historial completo de préstamos.

## Características

- Registro de herramientas con nombre, cantidad y estado (prestada o disponible)
- Gestión de préstamos con información de quién tomó la herramienta, fecha y comentarios
- Actualización de cantidad con validación para que nunca sea negativa
- Patrón arquitectónico MVVM
- Base de datos local con Room

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Room (SQLite)
- Coroutines
- LiveData y ViewModel