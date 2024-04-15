package com.example.racetracker

import com.example.racetracker.ui.RaceParticipant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RaceParticipantTest {
    private val raceParticipant = RaceParticipant(
        name = "Test",
        maxProgress = 100,
        progressDelayMillis = 500L,
        initialProgress = 0,
        progressIncrement = 1
    )

    @Test
    fun `whenn race started it should increment progress`() = runTest {

        val expeced = 1
        launch {
            raceParticipant.run()
        }
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(expeced, raceParticipant.currentProgress)

    }


    @Test
    fun `whenn race finished  it should increment progress`() = runTest {

        launch {
            raceParticipant.run()
        }

        // this simulates as if the time passed to it was already past
        // like if you past 1000 this tills system hey  a one second was passed do the
        //work you intended to after one second
        // if you want to start a work after 10 minutes for example . you don't have to wait it
        // in the test as you need to test it so this tells sysytem to add an advance time
        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(100, raceParticipant.currentProgress)

    }
}
