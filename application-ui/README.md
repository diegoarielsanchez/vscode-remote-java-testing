# vscode-remot-java-testing-ui

Mobile-first React + Tailwind CSS frontend for consuming the backend `MedicalSalesRepController` endpoints.

## Dev Container

Open this folder in VS Code and use:

- `Dev Containers: Reopen in Container`

Container name:

- `vscode-remot-java-testing-ui`

## Run UI

1. Install dependencies:

```bash
npm install
```

2. Start dev server:

```bash
npm run dev
```

3. Open the local Vite URL (usually `http://localhost:5173`).

## Backend requirements

Run the Spring Boot backend on port `8085`.

Environment variable (optional):

- `VITE_API_BASE_URL` (default: `http://localhost:8085`)

Copy `.env.example` to `.env` to override base URL.

## Consumed endpoints

- `POST /auth/login`
- `POST /api/v1/medicalsalesrep/list`
- `POST /api/v1/medicalsalesrep/create`
- `PUT /api/v1/medicalsalesrep/update`
- `POST /api/v1/medicalsalesrep/activate`
- `POST /api/v1/medicalsalesrep/deactivate`

The app sends JWT as `Authorization: Bearer <token>` after login.
