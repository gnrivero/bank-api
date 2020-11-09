# bank-api

<h2>Configuracion de entorno/perfiles</h2>

Profile Local (apuntando a DB local)
 
En IntelliJ Idea: Run > Edit Configurations > Environment Variables agregar SPRING_PROFILES_ACTIVE=local

Profile default (apunta a base de datos externa)

Simplemente correr la aplicacion sin perfil activo.

<h2>Correr la aplicación</h2>

Desde Idea: Botón derecho sobre la clase Application, seleccionar "Run 'Application.main()'". 
Desde línea de comandos

Profile Local: ./gradlew bootRun --args='--spring.profiles.active=local'
Profile Default: ./gradlew bootRun
