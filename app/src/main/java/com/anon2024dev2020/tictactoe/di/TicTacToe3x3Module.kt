package com.anon2024dev2020.tictactoe.di

import com.anon2024dev2020.tictactoe.domain.model.TicTacToe3x3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TicTacToe3x3Module {

    @Provides
    fun provideTicTacToe3x3(): TicTacToe3x3 {
        return TicTacToe3x3()
    }
}
