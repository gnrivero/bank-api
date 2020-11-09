# bank-api

<h2>Configuracion de entorno/perfiles</h2>

<b>Profile Local (apuntando a DB local)</b>
 
En IntelliJ Idea: Run > Edit Configurations > Environment Variables agregar SPRING_PROFILES_ACTIVE=local

<b>Profile default (apunta a base de datos externa)</b>

Simplemente correr la aplicacion sin perfil activo.

<h2>Correr la aplicación</h2>

<b>Desde Idea:</b> Botón derecho sobre la clase Application, seleccionar "Run 'Application.main()'". 

<b>Desde línea de comandos</b>

Profile Local: ./gradlew bootRun --args='--spring.profiles.active=local'<br>
Profile Default: ./gradlew bootRun<br>
