# Covoiturage Full-Stack Platform

A full-stack ride-sharing application featuring secure JWT authentication, ride offers/requests, in-app conversations, and real-time messaging via WebSocket STOMP.

## Features
- User registration, login, profile management.
- Ride post creation, editing, status updates.
- Browse/filter posts.
- Conversation and message threads.
- Real-time chat broadcast using STOMP.
- Light/dark theme state in frontend.

## Architecture
```
[React SPA] --REST/STOMP--> [Spring Boot API] --> [PostgreSQL]
```

## Prerequisites
- Java 17+
- Maven 3.9+
- Node 20+
- PostgreSQL 15+

## Local setup
1. `psql -f init.sql`
2. `cd backend && mvn spring-boot:run`
3. `cd frontend && npm install && npm run dev`

## API endpoints
| Method | Path | Auth | Description |
|---|---|---|---|
| POST | /api/auth/register | No | Register |
| POST | /api/auth/login | No | Login |
| GET | /api/auth/me | Yes | Current user |
| GET | /api/posts | No | List posts |
| POST | /api/posts | Yes | Create post |
| GET | /api/conversations | Yes | List conversations |
| GET | /api/conversations/{id}/messages | Yes | Paginated messages |

## Environment variables
Backend: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `APP_JWT_SECRET`.

## Contributing
1. Create a feature branch.
2. Keep services/controllers small and testable.
3. Open PR with screenshots for UI changes.
