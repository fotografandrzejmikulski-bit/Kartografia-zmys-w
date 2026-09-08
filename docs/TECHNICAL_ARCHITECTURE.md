# Technical Architecture — Android-first

## Target
A production-capable Android application with an offline-first deterministic game engine and a premium touch UI. The repository is intentionally framework-agnostic at the domain layer so a native Android client, Compose UI, or a future web companion can share the same rules contract.

## Layers
- `domain/`: pure game rules, immutable state transitions, seeded RNG, scoring, card eligibility.
- `content/`: versioned JSON card/token data with schema validation.
- `presentation/`: mobile screen specification, design tokens, motion rules, accessibility contracts.
- `platform/`: Android wrapper, secure local persistence, haptics/audio adapters, backup policy.
- `qa/`: property tests, snapshot fixtures, deterministic scenario tests, accessibility checklist.

## State machine
`LOBBY → CALIBRATION → DISCOVERY → ENGINE → RISK → ENGINE/DISCOVERY → RESOLUTION → AFTERCARE → SUMMARY`.

Any state can transition to `PAUSED` and then return to its prior state, or to `SAFE_EXIT`.

## Determinism
A session stores:
- ruleset version
- content pack version
- random seed
- event sequence
- player configuration
- boundary map

A replay can therefore reproduce all computational outcomes without collecting sensitive physical data.

## Storage
Local-first. Do not store intimate answers by default. If reflection persistence is later added, it must be opt-in, encrypted, and deletable from inside the app.

## Rendering
60/90/120 Hz friendly motion budget. Prefer GPU-backed transforms, short composited transitions, and no continuous background effects when the screen is idle. Support reduced motion.

## Haptics
Haptic feedback is semantic: button confirmation, phase transition, overload, pause, and success. It never attempts to simulate or claim to measure human arousal.

## Audio
Optional ambient sound layer with local assets only. All sound must have independent volume control and a global mute shortcut.

## Security/privacy
No analytics by default for gameplay content. No contact, camera, microphone, location, or health permissions are required for core play. Any future cloud synchronization must be explicit opt-in.
