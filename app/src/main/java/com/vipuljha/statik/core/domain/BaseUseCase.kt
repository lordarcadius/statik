package com.vipuljha.statik.core.domain

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Params, out SuccessType> {
    operator fun invoke(params: Params): Flow<Response<SuccessType>>
}

object NoParams