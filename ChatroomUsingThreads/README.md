This is a chatroom using threads to allow for more than one user. We use PrintWriter and BufferedReader because it is safer to use when handling multiple threads.
We use append to safely deliver messages from all users. We also use invokeLater so that anything in the IO stream runs first so that we dont cause crashes due to bad timing.
