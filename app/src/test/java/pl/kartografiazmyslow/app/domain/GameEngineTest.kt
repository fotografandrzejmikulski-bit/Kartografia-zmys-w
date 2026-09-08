package pl.kartografiazmyslow.app.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameEngineTest {
    @Test fun threshold_is_clamped() {
        assertEquals(10, GameState.new().copy(tension = -10).controlThreshold)
        assertEquals(1, GameState.new().copy(tension = 50).controlThreshold)
    }

    @Test fun banking_preserves_points_and_clears_streak() {
        val state = GameState.new().copy(score = 12)
        val next = GameEngine.bank(state)
        assertEquals(12, next.bankedScore)
        assertEquals(0, next.score)
        assertEquals(Phase.ENGINE, next.phase)
    }

    @Test fun stop_is_terminal() {
        val next = GameEngine.stop(GameState.new())
        assertEquals(Phase.SAFE_EXIT, next.phase)
    }

    @Test fun seeded_draw_is_reproducible() {
        val a = GameEngine.draw(GameState.new(seed = 42))
        val b = GameEngine.draw(GameState.new(seed = 42))
        assertEquals(a.token, b.token)
        assertEquals(a.state.score, b.state.score)
    }

    @Test fun draw_score_never_negative() {
        val result = GameEngine.draw(GameState.new())
        assertTrue(result.state.score >= 0)
    }
}
