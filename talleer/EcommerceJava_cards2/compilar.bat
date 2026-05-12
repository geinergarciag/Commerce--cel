@echo off
:: ============================================================
::  TiendaJava -- Script de compilacion y ejecucion (Windows)
:: ============================================================

mkdir out 2>nul
mkdir datos 2>nul

echo Compilando TiendaJava...

javac -encoding UTF-8 -d out ^
  src\modelo\Usuario.java ^
  src\modelo\Producto.java ^
  src\modelo\ItemCarrito.java ^
  src\modelo\Carrito.java ^
  src\modelo\Pedido.java ^
  src\modelo\Cliente.java ^
  src\modelo\Administrador.java ^
  src\pago\Pago.java ^
  src\pago\PagoEfectivo.java ^
  src\pago\PagoTarjeta.java ^
  src\datos\GestorDatos.java ^
  src\sistema\SistemaEcommerce.java ^
  src\vista\VentanaPrincipal.java ^
  src\vista\PanelLogin.java ^
  src\vista\PanelCliente.java ^
  src\vista\PanelAdmin.java ^
  src\Main.java

if %errorlevel% == 0 (
    echo Compilacion exitosa!
    echo Iniciando TiendaJava...
    java -cp out Main
) else (
    echo Error de compilacion. Revisa los mensajes anteriores.
    pause
)
