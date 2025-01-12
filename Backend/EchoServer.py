import socket

HOST = 'localhost'
PORT = 5000

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    print(f"Servidor escuchando en {HOST}:{PORT}")

    conn, addr = s.accept()
    with conn:
        print(f"Conectado por {addr}")
        while True:
            data = conn.recv(1024)
            if not data:
                break
            mensaje = data.decode().strip()
            print(f"Recibido: {mensaje}")
            if mensaje.lower() == "bye":
                print("Cliente desconectado")
            respuesta = f"Eco: {mensaje}\n"
            conn.sendall(respuesta.encode('utf-8'))
