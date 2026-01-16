# Technical Proposal: Morning Handover Report

---

## Overview

**Project Name:** Morning Handover Report  

**Purpose:**  
Generate a unified report for the evening team to hand over to the morning team for smoother operation.

**Background and Context:**  
Currently, handover reports are typed manually onto Teams. This requires reviewing texts from previous days to ensure nothing is missed. Automating the report will make it more unified and reduce errors.

**Target Audience:**  
Evening Ops users who need to inform morning Ops members what has been done and what still needs doing.

**Rationale:**  
- Easier onboarding for new team members since the report template guides expected tasks.  
- For current members, reduces report generation time, enforces consistency, and lowers the chance of incorrect entries (e.g., delivery stacks, unbroken stacks).

---

## Architecture Overview

The following diagram shows the end-to-end flow of delivery data from the depot to the report output. It illustrates the options for API ingestion or manual entry, the single-entry store-specific database, and the text export to Teams.

![Architecture Diagram](https://github.com/gleungkj/Morning-Handover-Report/blob/main/Handover_architectural_diagram.png)

---

## Goals and Non-Goals

**Goals**  
- Generate a report based on EV (Evening Vehicle) delivery that can be copied and pasted easily into Teams chat. This ensures end users only need to interact with Teams.

**Non-Goals**  
- Allowing custom parameters to be added. An ‘additional comments’ textbox suffices for scaling to other stores.

---

## Functional Scope

**In Scope**  
- Structured templates for manual entry and/or EV delivery info to record stocks received, tasks completed, and additional notes.  
- Auto-compilation of completed data into a text payload with a copy button for Teams.  
- Storage layer allowing completed data to be revisited and copied until overwritten by the next day’s report.

**Out of Scope**  
- Integration with central database.  
- Keeping an audit trail (would incur storage costs; reports are only relevant until the next morning).

---

## Tech Stack

**Platform (Provisional Access):**  
- Microsoft PowerApps (low-code, part of M&S apps ecosystem; easier for others to maintain)  
- Otherwise, custom app built with code (see below)

**Cloud Provider:**  
- Cloudflare for architecture familiarity and low cost  
- Code repo: GitHub  
- CI/CD: GitHub Actions  
- Cloudflare Pages – PaaS to serve sites  
- Cloudflare D1 – DB layer (stores transformed/cleaned data)  
- Cloudflare Workers – fetches data from GIST API and sends to D1 (provisional)  
- Cloudflare Pages Functions – handles API calls from Pages to D1

**Language:**  
- Server: NodeJS, ExpressJS  
- Client: TypeScript, React  

---

## Alternatives Considered

**Microsoft Teams / Forms**  
- Cannot ingest delivery data via API, would require manual entry.

**PowerApps**  
- May be viable (other apps exist in store, e.g., Safety Super App)  
- Store users familiar with the app environment  
- Currently no access; uncertain if all required features (API ingestion) are supported  

---

## State Management & Data Handling

- Fetch delivery information from GIST API when available.  
- Save onto DB to allow CRUD operations on the payload.  
- Populate the input form with received payload.  
- Changes via the form update the payload. Exportable as text for Teams.  
- **Latest data wins:** new delivery info (API or manual) overwrites prior payload.

---

## Tooling & Developer Experience

- GitHub Actions to spin off services  
- Separate live and dev environments; each PR spins up a PaaS connected to mock storage/DB layers  
- No containers due to cost (scalability may require containers long-term)  
- Separate storage per environment (mock vs live)  
- PR-associated PaaS torn down when PR merged/closed

---

## Success Criteria

- Users can generate the text payload with minimal guidance  
- Adoption to the point where Teams handover messages follow the app’s format  
- Report format remains consistent even if delivery info is manually entered instead of API

---

## User Flow

**Evening Flow**  
1. Delivery data becomes available:  
   - a) System fetches automatically via API, or  
   - b) User inputs manually using Honeywell data  
2. Evening Ops review/edit entries  
3. Report is generated and copied  
4. Report is pasted into Teams  

**Morning Flow**  
1. Morning Ops read Teams message  
2. No system interaction required  

---

## Data Model

**Report Fields:**  
- Evening Delivery (number of)  
  - Cold Chain  
  - Ambient  
  - Milk  
- Fully broken down/segregated? Yes/No  
  - If No: Remaining stacks (number)
- Frozen stacks binned? Yes/No
  - If No: Remaining/overflow stacks (number)
- Ambient Tasks – List  
- Store Tasks (e.g., till trash, orange recycle trash) – List  
- Additional Comments – Text (default N/A if blank)  

---

## Fallback Behaviour

- Manual entry essential if delivery system unavailable/late  
- Input data not saved until submit; latest submission overwrites previous  
- If app or DB route unavailable, users must revert to manual Teams entry or prior report  
- Alternative: client-only rendering, but data tied to user and cookies not cleared  

---

## Access Levels

- App restricted to M&S employees as live GIST API data may be used (stock levels sensitive)  
- Request GIST API to supply store-specific data for scalability  
- Restriction tied to M&S email (PowerApps login system)

---

## Operational Constraints

- DB stores single entry; updates overwrite prior  
- Keeps storage minimal; report relevance only until next delivery (morning) 
- Store-specific DB; no centralised DB needed  
- No regulatory retention requirement

---

## Ownership and Maintenance

- **PowerApps:** Lower barrier; other staff can maintain  
- **Custom app:** Requires digital team maintenance if original author unavailable
