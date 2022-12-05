<b>Pacman</b>

This application allows the compilation and execution
of a simple Java Clone of Pacman.
To run;

<ul>
<li><b>Ensure project is open within the development container</b></li>
<li><b>Hit F5</b></li>
</ul>

This application demonstrates the following;

<ul>
<li>VS code debugging and breakpoint functionality</li>
<li>Docker development container configuration</li>
<li>Maven Building</li>
</ul>

Creating a new Maven project;

```
mvn archetype:generate -DgroupId=com.phillholland.app -DartifactId=pacman -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

To Build;

```
mvn package
```

To Run;

```
java -cp target/pacman-1.0-SNAPSHOT.jar com.phillholland.app.App
```

Requirements;

The VSCode development container plugin is installed;

https://code.visualstudio.com/docs/remote/containers

Docker must also be installed;

https://docs.docker.com/get-docker/

This application, however is configured with linux based containers, and will not work correctly on Windows without modification.
