# 🛒 Ecommerce Java — Proyecto Final POO

Sistema de comercio electrónico desarrollado en Java como proyecto final del curso de **Programación Orientada a Objetos**, aplicando los cuatro pilares de la POO: herencia, encapsulación, polimorfismo y abstracción.

---

## 👥 Integrantes del grupo

| Nombre completo | Usuario GitHub |
|---|---|
| Sebastian Berrio | @sebastian-berrio29 |
| Geiner Garcia | @geinergarciag|
| Marcos Torrealba| @marcostorrealba3324 |
| Alfonso Acoosta | @alfoac |

---

## 📋 Descripción del proyecto

Este sistema permite a los usuarios registrarse, iniciar sesión, navegar por un catálogo de productos, agregar artículos a su carrito y realizar pedidos con diferentes métodos de pago. Los administradores pueden gestionar el inventario de productos y consultar todos los pedidos registrados.

---

## ✅ Funcionalidades implementadas

### Cliente
- 🔐 Registro e inicio de sesión
- 🛍️ Ver catálogo de productos y buscar por nombre
- 🛒 Carrito de compras: agregar, eliminar y calcular total
- 💳 Selección de método de pago (tarjeta o efectivo)
- 📦 Flujo completo de pedido con actualización de inventario
- 📜 Historial de pedidos con estado y detalle

### Administrador
- ➕ Agregar, editar y eliminar productos
- 📊 Control de stock en tiempo real
- 👥 Ver usuarios registrados
- 📋 Consultar todos los pedidos del sistema

---

## 🧱 Conceptos de POO aplicados

| Concepto | Implementación |
|---|---|
| **Herencia** | `Usuario` → `Cliente` / `Administrador` |
| **Herencia** | `Pago` → `PagoTarjeta` / `PagoEfectivo` |
| **Encapsulación** | Atributos privados con getters y setters en todas las clases |
| **Polimorfismo** | `@Override` en `getTipoUsuario()` y `procesar()` |
| **Abstracción** | Clase abstracta `Usuario`, clase abstracta `Pago`, interfaz `Pagable` |
| **Colecciones** | `ArrayList` para productos, pedidos y usuarios |
| **Excepciones** | `try/catch` en control de stock, login y persistencia |

---

## 🛠️ Tecnologías usadas

| Categoría | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Interfaz gráfica | Java Swing |
| Persistencia | Serialización de objetos (archivos `.dat`) |
| Control de versiones | Git + GitHub |
| IDE | NetBeans |

---

## 📁 Estructura del proyecto

```
ecommerce-java-poo/
│
├── src/
│   └── ecommerce/
│       ├── modelo/
│       │   ├── Usuario.java          ← Clase abstracta base
│       │   ├── Cliente.java          ← Hereda de Usuario
│       │   ├── Administrador.java    ← Hereda de Usuario
│       │   ├── Producto.java
│       │   ├── Carrito.java
│       │   ├── Pedido.java
│       │   ├── Pagable.java          ← Interface
│       │   ├── Pago.java             ← Clase abstracta
│       │   ├── PagoTarjeta.java      ← Hereda de Pago
│       │   └── PagoEfectivo.java     ← Hereda de Pago
│       │
│       ├── gestor/
│       │   ├── GestorProductos.java  ← CRUD + persistencia
│       │   ├── GestorUsuarios.java   ← Registro, login + persistencia
│       │   └── GestorPedidos.java    ← Historial + persistencia
│       │
│       ├── vista/
│       │   ├── VentanaLogin.java     ← Pantalla de inicio de sesión
│       │   ├── VentanaCliente.java   ← Panel del cliente
│       │   └── VentanaAdmin.java     ← Panel del administrador
│       │
│       └── MainApp.java              ← Punto de entrada del programa
│
├── docs/
│   └── uml/
│       ├── diagrama-clases.png       ← Diagrama UML de clases
│       └── diagrama-casos-uso.png    ← Diagrama de casos de uso
│
├── .gitignore
└── README.md
```

---

## ▶️ Instrucciones de ejecución

### Opción A — Desde NetBeans

1. Clona el repositorio:
   ```
   git clone https://github.com/TU_USUARIO/ecommerce-java-poo.git
   ```
2. Abre NetBeans → **File → Open Project**
3. Selecciona la carpeta del proyecto
4. Haz clic derecho en el proyecto → **Run**

### Opción B — Desde la terminal

1. Clona el repositorio:
   ```
   git clone https://github.com/TU_USUARIO/ecommerce-java-poo.git
   cd ecommerce-java-poo
   ```
2. Compila el proyecto:
   ```
   javac -d out -sourcepath src src/ecommerce/MainApp.java
   ```
3. Ejecuta:
   ```
   java -cp out ecommerce.MainApp
   ```

> **Requisito:** tener instalado Java 17 o superior. Verifica con `java -version`.

---

## 🖼️ Diagramas UML

Los diagramas se encuentran en la carpeta `docs/uml/`:

- `diagrama-clases.png` — Diagrama de clases con jerarquías de herencia
- `diagrama-casos-uso.png` — Diagrama de casos de uso por actor

---

## 📌 Notas adicionales

- Los datos persisten entre sesiones mediante serialización de objetos Java (archivos `.dat` generados automáticamente al ejecutar).
- El sistema incluye un usuario administrador por defecto:
  - **Email:** `admin@tienda.com`
  - **Contraseña:** `admin123`
- No se deben subir los archivos `.dat` al repositorio (están en `.gitignore`).

---

*Proyecto desarrollado para el curso de Programación Orientada a Objetos — Java POO*
