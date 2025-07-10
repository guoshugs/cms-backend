# LeadNews CMS Backend

LeadNews is a modular, microservices-based **Content Management System (CMS)** backend built with **Java**, **Spring Boot**, and **Spring Cloud Alibaba**. Designed for managing a complete digital news publishing system, it supports user management, content moderation, article publishing workflows, search indexing, real-time statistics, and more.

This project is built for learning, academic demonstration, and private deployment purposes.

---

## 🌐 Features

- ✅ **Microservices architecture** with Spring Cloud Gateway and Nacos
- ✅ **Modular service separation** for API, user, admin, content, search, and statistics
- ✅ **Database design** optimized for relational and non-relational data (MySQL + Redis)
- ✅ **Content audit and publishing workflows**
- ✅ **Search engine integration** with Elasticsearch
- ✅ **Asynchronous communication** using RabbitMQ
- ✅ **Real-time data analysis** with log collection and user feedback handling
- ✅ **Authentication and role-based access control**
- ✅ **Containerized deployment** ready via Docker

---

## 🧰 Tech Stack

| Layer        | Technology                                    |
|--------------|-----------------------------------------------|
| Language     | Java 8                                       |
| Framework    | Spring Boot, Spring Cloud, Spring Security    |
| Microservices| Nacos, Gateway, OpenFeign                     |
| Database     | MySQL, Redis                                  |
| Messaging    | RabbitMQ                                      |
| Search       | Elasticsearch                                 |
| Logging      | Logback, Spring AOP                           |
| Tools        | Docker, Lombok, Maven                         |
| Dev Tools    | IDEA, Git, GitHub                             |

---

## 🧱 Module Breakdown

| Module                     | Description                                                                 |
|----------------------------|-----------------------------------------------------------------------------|
| `leadnews-api`             | OpenFeign interface declarations for inter-service communication            |
| `leadnews-common`          | Shared constants, utilities, and configuration                             |
| `leadnews-common-db`       | MyBatis-Plus configuration and base entities                               |
| `leadnews-core`            | Core business logic shared across services                                 |
| `leadnews-gateway`         | Spring Cloud Gateway for routing and token validation                      |
| `leadnews-service`         | Concrete services for user, admin, content, media, and statistics handling |

---

## ⚙️ How to Run (Local Dev)

1. **Prepare Local Services**:
   - MySQL (`192.168.211.128:3306`)
   - Redis (`6379`)
   - Nacos (`192.168.211.128:8848`)
   - RabbitMQ and Elasticsearch if needed

2. **Set up MySQL Database**:
   - Create databases (e.g., `leadnews_admin`, `leadnews_article`, etc.)
   - Import SQL files if available

3. **Edit Configuration**:
   - Adjust DB config in each `application.yml` (host, port, password)
   - Ensure all services point to correct Nacos and DB

4. **Run Services**:
   - Start `leadnews-gateway`
   - Start needed services in `leadnews-service/*`

5. **Access API**:
   - Visit: `http://localhost:9000/` (via gateway)

---

## 🔒 Security Notes

- All secrets (e.g., `aliyun.properties`) have been removed before publishing.
- The system uses default local DB credentials (e.g., `root/123456`) and **is not exposed to the public internet**.
- Environment-specific settings (such as `Nacos`, `Redis`, `RabbitMQ`) are designed for **internal/private use only**.

---

### 📦 Module Details

| Module Name             | Description                                                                 |
|-------------------------|-----------------------------------------------------------------------------|
| `leadnews-api`          | Feign client interfaces for remote service communication                   |
| `leadnews-common`       | Shared utility classes, constants, enums                                    |
| `leadnews-common-db`    | MyBatis-Plus base config, auto-fill, ID generation                          |
| `leadnews-core`         | Core service logic, JWT utils, request context tools                        |
| `leadnews-gateway`      | API Gateway using Spring Cloud Gateway with token filter                    |
| `leadnews-service`      | Grouped submodules for business services:                                   |
| └─ `leadnews-admin`     | Admin user login, content audit, permission management                      |
| └─ `leadnews-article`   | Article CRUD, draft & publish logic, audit workflow                         |
| └─ `leadnews-media`     | Media platform article/media management                                     |
| └─ `leadnews-search`    | Elasticsearch indexing and search                                            |
| └─ `leadnews-user`      | App-side user registration, login, profile update, behavior tracking         |


---

## 📄 License

This project is intended for academic use only and is published under the MIT License.

---

## 🔗 Project Link

GitHub Repository: [https://github.com/guoshugs/cms-backend](https://github.com/guoshugs/cms-backend)

---
