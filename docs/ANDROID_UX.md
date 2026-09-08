# Android UX/UI — Premium System

## Visual language
A dark, editorial interface inspired by museum cartography: deep charcoal surfaces, warm ivory typography, restrained copper accents, glass-like cards, fine contour lines, and subtle grain. Avoid neon/gamey gradients.

## Navigation
Bottom navigation is limited to `Session`, `Engine`, `Rules`, `Settings`. During an active session, the primary safety control remains pinned above navigation.

## Main play screen
- Top: phase + session progress.
- Center: animated contour-map body silhouette with six nodes.
- Right/upper card: tension 1–10 and control threshold.
- Lower: current score, banked score, bag composition.
- Bottom action rail: `DRAW`, `BANK`, `PAUSE`, `STOP`.

All primary targets meet comfortable touch size and maintain large hit areas independent of visual icon size.

## Motion
Motion communicates state, never decoration: draw = short lift, engine unlock = radial reveal, overload = brief shake with immediate settle, pause = freeze. Respect reduced-motion settings.

## Consent interaction
Each node displays a three-state control: Green / Caution / Red. Changes require a deliberate tap and confirmation. Red is visually unmistakable and never hidden behind menus.

## Haptics
One-shot haptic cues are paired with visual feedback so no action depends on vibration alone. A global haptics toggle is exposed in settings.

## Accessibility
- Screen-reader content descriptions for every interactive element.
- Dynamic type / font scaling without clipping.
- High-contrast theme.
- Reduced motion.
- Color is never the sole state indicator; use labels/icons.
- One-handed mode.
- Landscape support for tablets/foldables.

## Privacy UX
No profile names are required. The default session identity is `Player A` / `Player B`. Reset Session permanently discards the current state after confirmation.
