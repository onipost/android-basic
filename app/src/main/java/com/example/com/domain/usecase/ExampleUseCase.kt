package com.example.com.domain.usecase

import com.example.com.domain.repository.ExampleRepository
import javax.inject.Inject

/**
 * Example of use case.
 * This can be is interface, bus i think regular application no need interface-implementation of use cases
 */
class ExampleUseCase @Inject constructor(private val repository: ExampleRepository)
