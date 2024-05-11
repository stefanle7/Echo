# Echo
***
## Overview
Echo is a simple client-server framework designed for building network applications that use socket programming. The framework includes a basic echo server and client, which can be extended to handle more complex requests and functionalities.
***
### Components
- **SimpleClient:** A console user interface that continuously prompts the user for input, sends requests to the server, and displays the server's response.
- **RequestHandler:** Handles client requests on the server side. Each request spawns a new thread for processing.
- **Server:** Listens for incoming client requests and creates a new `RequestHandler` for each request
