## Simple Spring Boot Email Waitlist Microservice ("Tapioca")

### Overview

#### Background

- We need to implement an email waitlist microservice prior to the launch of our website.
- Consumer of this microservice would be front-end dev team.

#### Core

*Users can submit their email to join a waitlist.*

#### Functional Requirements

- As a user, I want to submit my email to join a waitlist.
  - I want to be notified if I've already joined.
  - I want to know if my email is invalid.

#### Testing Scope

- Ensure valid emails are added, invalid ones are not, and duplicate emails are handled gracefully.

### Test Scenarios

#### ✅ Happy Path

| ID | Scenario | Description | Expected Outcome |
|----|----------|-------------|------------------|
| H1 | Valid email submitted | `"user@example.com"` sent to `/waitlist` | 201 Created + success message |
| H2 | Valid email with plus alias | `"user+alias@example.com"` | 201 Created |

---

#### ❌ Bad Cases (Negative Testing)

| ID | Scenario | Description | Expected Outcome |
|----|----------|-------------|------------------|
| B1 | Invalid email format | `"not-an-email"` | 400 Bad Request + error message |
| B2 | Missing email field | `{}` or `null` | 400 Bad Request |
| B3 | Duplicate email submission | Email already submitted | 409 Conflict |
| B4 | Invalid request content type | Not `application/json` | 415 Unsupported Media Type |
| B5 | Malformed JSON payload | Broken request body | 400 Bad Request |

---

#### ⚠️ Edge Cases

| ID | Scenario | Description | Expected Outcome |
|----|----------|-------------|------------------|
| E1 | Max length email | 254 chars total (valid max per RFC) | 201 Created |
| E2 | Email with uppercase letters | `"User@Example.COM"` | 201 Created (case-insensitive) |
| E3 | Email with Unicode (IDN) | `"用户@例子.广告"` | Possibly reject or accept — define policy |
| E4 | Rapid submissions | Submit same email rapidly | Only first accepted, others 409 |

### Tooling

- Initially starting with JUnit for automated unit tests. Will eventually incorporate integration tests and e2e tests with playwright.
- GitHub actions pipeline to automate tests further.