package pl.kartografiazmyslow.app.domain

import kotlin.random.Random

data class GameState(
    val seed: Long,
    val tension: Int = 1,
    val score: Int = 0,
    val bankedScore: Int = 0,
    val blackDrawn: Int = 0,
    val phase: Phase = Phase.CALIBRATION,
    val bag: List<Token> = initialBag()
) {
    val controlThreshold: Int get() = (11 - tension).coerceIn(1, 10)

    companion object {
        fun new(seed: Long = System.nanoTime()): GameState = GameState(seed = seed, bag = initialBag())
        private fun initialBag() = buildList {
            repeat(5) { add(Token.BLUE) }
            add(Token.BLACK)
        }
    }
}

enum class Phase { CALIBRATION, DISCOVERY, ENGINE, RISK, RESOLUTION, AFTERCARE, PAUSED, SAFE_EXIT }
enum class Token { BLUE, RED, BLACK }

data class DrawResult(val state: GameState, val token: Token, val overloaded: Boolean)

object GameEngine {
    fun start(state: GameState): GameState = state.copy(phase = Phase.DISCOVERY)

    fun setTension(state: GameState, value: Int): GameState = state.copy(tension = value.coerceIn(1, 10))

    fun draw(state: GameState): DrawResult {
        require(state.phase != Phase.SAFE_EXIT) { "Session already ended" }
        val rng = Random(state.seed + state.score + state.blackDrawn.toLong() * 31L)
        val token = state.bag[rng.nextInt(state.bag.size)]
        val nextBlack = state.blackDrawn + if (token == Token.BLACK) 1 else 0
        val nextScore = state.score + when (token) { Token.BLUE -> 1; Token.RED -> 3; Token.BLACK -> 0 }
        val overload = nextBlack >= state.controlThreshold
        return DrawResult(
            state.copy(score = if (overload) 0 else nextScore, blackDrawn = if (overload) 0 else nextBlack, phase = Phase.RISK),
            token,
            overload
        )
    }

    fun bank(state: GameState): GameState = state.copy(bankedScore = state.bankedScore + state.score, score = 0, blackDrawn = 0, phase = Phase.ENGINE)

    fun pause(state: GameState): GameState = if (state.phase == Phase.PAUSED) state else state.copy(phase = Phase.PAUSED)

    fun resume(state: GameState, previous: Phase): GameState = state.copy(phase = previous)

    fun stop(state: GameState): GameState = state.copy(phase = Phase.SAFE_EXIT)
}
