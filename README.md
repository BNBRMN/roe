Conversor de Monedas
Este es un proyecto de consola en Java que permite convertir entre diferentes monedas utilizando la API de Exchange Rate. La aplicación proporciona un menú interactivo donde los usuarios pueden seleccionar el tipo de conversión y la cantidad que desean convertir. Los resultados se obtienen en tiempo real mediante una consulta a la API.

Funcionalidades
Convertir USD a ARS
Convertir BRL a USD
Convertir EUR a USD
Convertir GBP a USD
Convertir JPY a USD
Salir de la aplicación
Requisitos
Java 11 o superior
API Key de Exchange Rate (Obtén tu API Key aquí)
Instalación
Clona el repositorio:

bash
Copiar código
git clone https://github.com/tu-usuario/conversor-de-monedas.git
cd conversor-de-monedas
Abre el proyecto en tu IDE preferido (IntelliJ IDEA, Eclipse, etc.).

Asegúrate de tener configurado tu entorno de desarrollo con Java 11 o superior.

Configuración
Obtén una API Key de Exchange Rate y reemplaza YOUR_API_KEY en la clase ConversorDeMonedas con tu clave API.

java
Copiar código
private static final String API_KEY = "YOUR_API_KEY";
Uso
Ejecuta la clase Principal para iniciar la aplicación.

Sigue las instrucciones del menú interactivo para seleccionar el tipo de conversión y la cantidad.

La aplicación mostrará el resultado de la conversión en la consola.

Estructura del Proyecto
src
Principal.java: Clase principal que contiene el menú interactivo.
ConversorDeMonedas.java: Clase que maneja las consultas a la API y realiza las conversiones.
Ejemplo
Al ejecutar la aplicación, verás el siguiente menú:

markdown
Copiar código
Bienvenido al Conversor de Monedas
1. USD a ARS
2. BRL a USD
3. EUR a USD
4. GBP a USD
5. JPY a USD
6. Salir
Seleccione una opción: 1
Ingrese el valor que desea convertir: 25
El valor de 25.00 USD es 20.293,75 ARS
Contribuciones
Las contribuciones son bienvenidas. Si tienes alguna mejora o corrección, por favor abre un issue o un pull request.

Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

