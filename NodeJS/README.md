# TROQUE-TROC - NODEJS API

## Project Description



## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/FelanaRasata/MDBS-Projet-Transversal.git
   ```
2. Navigate to the project directory:
   ```bash 
   cd MDBS-PROjet-Transversal/NodeJS
   ```
3. Install dependencies:
- You need `pnpm` to be installed
   ```bash
   pnpm install
   ```

## Usage
To run the application locally, follow these steps:
1. Create a `.env` file in the root directory and define the following environment variables:
   ```dotenv
   # GLOBAL
   API_PORT=
   API_HOST=
   API_SERVER_TIMEOUT=
   ```
3. Start the application:
   ```bash
   pnpm run start:dev
   ```
4. Access the application at `http://localhost:<API_PORT>`

## Project Structure
*Note : This project uses a reviewed clean architecture by myself [Rasatarivony Andriamalala Sitraka ](andriamalala.rasatarivony@stellar-ix.com)*
```bash
.
|__ public/                       # Contains public assets.
|__ src/                          # Contains source code.
    |__ application/              # Application Server and gateways (controllers) folder.
    |__ core/                     # Business code.
        |__ domain/               # Modules source code.
        |__ shared/               # Shared modules source code such as services, types, etc.
    |__ infrastructure/           # App infra such as config, persistance and adapters.
    |__ aliases.ts                # Create aliases of directories and register custom module paths in NodeJS.
    |__ main.ts                   # App launcher.
|__ views/                        # Contains EJS view files.
```

## Additional Libraries
- Typescript
- Routing-controllers with Swagger
- Express.js
- EJS view engine

## API Reference
Swagger documentation is available at `http://localhost:<API_PORT>/api-docs` when the server is running.

---
This README.md serves as a guide to install, run, and understand the TROQUE-TROC project. For more detailed information, please refer to the codebase and additional resources provided.
