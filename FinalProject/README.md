This project is a multiplayer client-server trivia game built in Java using sockets and Swing, where multiple users can connect to a central server, log in or 
register through a MySQL-backed database, and participate in synchronized quiz sessions hosted from a dedicated server GUI. The server manages game state, question 
flow, scoring, and player sessions using multithreading (one thread per client), while clients communicate through a custom text-based protocol using BufferedReader
and PrintWriter. Each player receives real-time updates on questions, submits answers within a timed window, and sees results with automatic UI feedback, while the 
host can control the game flow, skip questions, and view live scores. The system ensures synchronization across clients by treating the server as the single source 
of truth for game progress, and includes features such as leaderboards, multiple question categories loaded from files, and dynamic UI updates for both players and 
the host.
