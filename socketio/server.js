const express = require('express');
const http = require('http');
const socketIo = require('socket.io');

const app = express();
const server = http.createServer(app);
const io = socketIo(server, {
    cors: {
        origin: "http://localhost:4000",
        methods: ["GET", "POST"]
    },
    transports:['websocket']
});
io.on('connection', (socket) => {
    console.log('New client connected');

    // Listen for incoming integer array
    socket.on('sendArray', (data) => {
        console.log('Received array:', data);

        // Broadcast the array to all connected clients
        io.emit('updateArray', data);
    });

    socket.on('disconnect', () => {
        console.log('Client disconnected');
    });
});

server.listen(4000, () => console.log('Server listening on port 4000'));
