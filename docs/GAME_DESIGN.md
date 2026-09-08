# Game Design v2 — Mobile Translation

## Design thesis
The phone is the table, not the participant. The application coordinates state, strategy, consent, timing, accessibility, and presentation while keeping physical interaction outside the device.

## Five-act loop
1. **Calibration** — both players define boundaries and preferences. Consent is explicit, revocable, and stored only for the session.
2. **Discovery** — a player draws from their bag and selects a safe mapped interaction. The app resolves token effects and score.
3. **Engine** — players unlock node upgrades using collected token combinations. Each upgrade changes scoring or bag control.
4. **Risk** — the active player may stop banking points or draw again. The passive player controls the visible tension value. Reaching the overload threshold ends the risk sequence without penalty to safety state.
5. **Resolution** — the app offers eligible non-explicit closing scenarios, then enters an aftercare timer and post-session reflection.

## Core improvements
- Replace ambiguous physical “bust” semantics with a deterministic **Risk Budget**: `blackCount >= controlThreshold` ends the streak.
- Separate **game score** from **body/tension state**; neither is presented as medical or biometric measurement.
- Add **consent gates** before every mapped interaction class. Any player can pause, stop, or edit boundaries without losing the ability to exit.
- Add a **solo rules sandbox** for learning the strategy engine without physical prompts.
- Add **practice mode** with simulated token draws and replayable deterministic seeds.
- Add **accessibility mode**: large targets, reduced animation, haptics toggle, screen-reader labels, high-contrast palette, and one-handed layout.
- Add **session resume** with local encrypted storage boundary documented for future native wrapper implementation.

## Balance model
Each token has a base value, a class, and optional tags. Engine cards provide additive bonuses first; multiplicative effects are resolved last. This prevents order-dependent scoring bugs.

Pseudo-resolution:

`raw = Σ(token.base + token.nodeBonus + token.cardBonus)`

`multiplier = Π(eligible multipliers)`

`streakScore = floor(raw * multiplier)`

When overload occurs, the current streak score is discarded but prior banked score remains intact.

## Mobile UX architecture
- **Home:** New Session / Resume / Learn / Accessibility / Settings.
- **Calibration:** 6 node map, boundary legend, private-confirmation modal.
- **Play:** central tension meter, compact bag state, current node, score bank, Draw / Bank / Pause.
- **Engine:** horizontally scrollable node cards with explicit unlock costs and previewed effects.
- **Resolution:** eligible scenario cards, consent confirmation, timer, aftercare.
- **Reflection:** private two-player check-in, session statistics, replay seed.

## Failure prevention
- Never auto-trigger a physical action.
- Never infer consent from previous sessions.
- Never use camera/microphone/biometric sensors without an explicit future opt-in feature.
- Timer can be paused immediately.
- Stop exits to a neutral screen.
