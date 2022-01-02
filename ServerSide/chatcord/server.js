const express = require("express");
const http = require("http");
const socketio = require("socket.io");
const cors = require("cors");
const { Stream } = require("stream");
const app = express();
app.use(cors());
app.options("*", cors());
const server = http.createServer(app);
const io = socketio(server);
const PORT = 5000;

app.set("views", "./Views");
app.use(express.static("./Views"));
app.get("/", (req, res) => {
  res.render("index.html");
});

io.on("connection", (Stream) => {
  Stream.on("send-message", (data) => {
    io.sockets.emit("send-message", data);
    console.log(data);
  });
  console.log("new connection");
});

server.listen(PORT, () => {
  console.log(`Server runing on port ${PORT} ....`);
});
