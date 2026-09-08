# QA / Verification Plan

## Domain tests
- Seeded shuffle produces identical draw sequences for identical seed + ruleset.
- Black threshold: equality (`blackCount == threshold`) overloads exactly once.
- Threshold is clamped to 1–10.
- Additive bonuses resolve before multiplicative effects.
- Banking preserves all banked points through overload.
- Pause/resume preserves exact state and timer offset.
- Stop produces a neutral terminal state without hidden continuation.
- Consent changes never retroactively reinterpret stored game events.

## Property tests
For random legal states: score is finite/non-negative; no token count becomes negative; bag + discard + play area equals session inventory; node unlocks cannot occur without a valid cost.

## UX tests
- No accidental activation on scroll.
- System back never bypasses pause/stop safeguards.
- All critical actions reachable with one hand.
- Dynamic font scaling does not clip critical controls.
- Reduced-motion removes decorative transitions.

## Release gate
A release is blocked by: crash on launch, state corruption, unsafe navigation around stop/pause, inaccessible critical controls, missing offline fallback, or nondeterministic rule resolution.
