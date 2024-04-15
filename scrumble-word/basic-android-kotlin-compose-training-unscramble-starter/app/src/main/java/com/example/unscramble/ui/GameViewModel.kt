package com.example.unscramble.ui

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.MAX_NO_OF_WORDS
import com.example.unscramble.data.SCORE_INCREASE
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class GameState(
    val word: String = "",
    val usedWords: MutableSet<String> = mutableSetOf(),
    val currentWord: String = "",
    val scrumbledWord: String = "",
    val userGuess: String = "",
    val isGuessedWordWrong: Boolean = false,
    val score: Int = 0,
    val currentWordCount: Int = 1,
    val gameOVer: Boolean = false
)

class GameViewModel : ViewModel() {

    private val _state = MutableStateFlow<GameState>(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    init {
        resetGame()
    }


    fun skip() {

        if (usedAll()) {
            gameOver()
            return
        }
        val newWord = pickupRandomWordAndShuffle()
        _state.update {
            it.copy(
                currentWord = newWord,
                currentWordCount = it.currentWordCount.plus(1),
                isGuessedWordWrong = false
            )
        }
    }


    private fun usedAll(): Boolean {
        return _state.value.usedWords.size == MAX_NO_OF_WORDS
    }

    private fun gameOver() {
        _state.update {
            it.copy(gameOVer = true)

        }
    }

    fun checkUserGuess() {

        if (usedAll()) {
            gameOver()
            return
        }
        val guess = state.value.userGuess
        val word = state.value.word
        if (guess.equals(word, ignoreCase = true) && guess.isNotEmpty()) {
            val newWord = pickupRandomWordAndShuffle()
            _state.update {
                it.copy(
                    score = it.score + SCORE_INCREASE,
                    currentWord = newWord,
                    userGuess = "",
                    currentWordCount = it.currentWordCount.plus(1)
                )
            }
        } else {
            _state.update {
                it.copy(isGuessedWordWrong = true)
            }
        }
    }

    fun updateUserGuess(guess: String) {
        _state.value = _state.value.copy(
            userGuess = guess
        )
    }

    private fun pickupRandomWordAndShuffle(): String {
        val currentWord = allWords.random()
        return if (_state.value.usedWords.contains(currentWord)) {
            pickupRandomWordAndShuffle()
        } else {


            val shuffled = shuffleCurrentWord(currentWord)
            val used = _state.value.usedWords
            used.add(currentWord)
            _state.update {
                it.copy(
                    usedWords = used,
                    word = currentWord,

                    )
            }

            return shuffled
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val array = word.toCharArray()
        array.shuffle()
        while (String(array) == word) {
            array.shuffle()
        }
        return String(array)
    }

    fun resetGame() {
        val scrumbledWord = pickupRandomWordAndShuffle()
        _state.value =
            GameState(
                currentWord = scrumbledWord,
                
                )
    }


}
