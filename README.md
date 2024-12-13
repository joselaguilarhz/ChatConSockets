Lógica del Chat
El sistema está compuesto por dos aplicaciones: servidor y cliente. El servidor se ejecuta
una vez, mientras que el cliente puede lanzarse tantas veces como usuarios deseen
participar en el chat.
Toda la comunicación pasa a través del servidor. Cuando un cliente quiere enviar un
mensaje a otro:
1. El mensaje se envía al servidor, especificando el destinatario.
2. El servidor redirige el mensaje al cliente correspondiente.
Además, el servidor notifica a todos los clientes cuando:
● Un usuario nuevo se conecta (para agregarlo a la lista de contactos).
● Un usuario cierra su sesión (para removerlo de la lista de contactos).
No existe comunicación directa entre clientes; el servidor actúa como intermediario. Si hay
un servidor activo y cuatro clientes conectados, las interacciones entre ellos serán
gestionadas exclusivamente por el servidor.
Funcionamiento del Servidor
La implementación del servidor se basa en tres clases principales:
1. VentanaS: Administra la interfaz gráfica del servidor, donde se muestra un registro
de los eventos más importantes, como las conexiones y desconexiones de usuarios.
2. Servidor: Es la clase principal del servidor y actúa como un hilo que escucha
continuamente para aceptar conexiones de nuevos clientes.
3. HiloCliente: Cada vez que un cliente se conecta, se crea una instancia de esta
clase. Esta clase maneja la comunicación entre el servidor y el cliente conectado
mediante un socket, escuchando y enviando mensajes según sea necesario.
Funcionamiento del Cliente
El cliente consta de dos clases principales:
1. VentanaC: Administra la interfaz gráfica del cliente. Incluye una lista de contactos,
un área para ver el historial de conversación, un cuadro de texto para escribir
nuevos mensajes y un botón para enviarlos.
2. Cliente: Una vez conectado al servidor, esta clase inicia un hilo que escucha
constantemente los mensajes provenientes del servidor. Usa un socket para enviar y
recibir mensajes del servidor.
2ºDAM - PSP
IES Antonio Gala
Este sistema se basa en el modelo cliente-servidor, garantizando que todas las
comunicaciones sean centralizadas y supervisadas por el servidor, lo que simplifica
la gestión y la seguridad del chat.
