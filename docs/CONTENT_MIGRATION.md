# Content Migration

The original physical card catalog is preserved conceptually but adapted for a mobile product boundary.

## Mobile content policy
The app may ask reflective, relationship-oriented calibration questions, but it does not display explicit instructions for sexual acts or generate erotic roleplay. Physical interaction stays outside the application and under the players' control.

## Three content families
- **Mapy Miłości:** relationship calibration and boundary prompts.
- **Karty Silnika:** abstract strategic upgrades that modify score, bag composition, tension, or turn economy.
- **Ultimatum / Resolution:** abstract end-state cards framed as mood, pacing, connection, or reflection rather than explicit sexual activity.

## Content schema
Every card has:
- stable id
- deck
- localized title
- abstract description
- unlock cost
- node tags
- effects
- consent requirement
- ruleset version

The mobile client should ship content as versioned data rather than hard-code card text into screens.
