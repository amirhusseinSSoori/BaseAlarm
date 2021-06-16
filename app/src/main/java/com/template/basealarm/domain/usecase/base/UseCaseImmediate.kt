package com.arad.domain.usecase.base

abstract class UseCaseImmediate<out R> {

    protected abstract fun buildUseCaseImmediate() : R

    fun execute(): R = buildUseCaseImmediate()
}