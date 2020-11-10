# bank-api
 
<h2>Correr la aplicación</h2>

<h3>Profile Local</h3>

El perfil local ejecuta una base de datos local MySQL.

<b>Desde IJ Idea</b>

Paso 1: Ir al menu Run > Edit Configurations > Environment Variables agregar SPRING_PROFILES_ACTIVE=local
Paso 2: Botón derecho sobre la clase Application.java, seleccionar "Run 'Application.main()'"

<b>Desde línea de comandos</b>
 
./gradlew bootRun --args='--spring.profiles.active=local'<br>

<h3>Profile Default</h3>

El perfil default (no profile active) ejecuta una base de datos remota MySQL, hosteada en Heroku.

<b>Desde IJ Idea</b>

Botón derecho sobre la clase Application.java, seleccionar "Run 'Application.main()'"

<b>Desde línea de comandos</b>

./gradlew bootRun
